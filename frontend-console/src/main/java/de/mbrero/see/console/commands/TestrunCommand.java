package de.mbrero.see.console.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;

import de.mbrero.see.api.TestRunController;
import de.mbrero.see.persistance.dto.types.TestRunResults;
import exceptions.ParameterException;
import types.Extractors;

/**
 * Triggers the execution of a semantic extractor. The Type supported can be
 * found in {@link Extractors}.<br>
 * A valid call from the console may look like this<br>
 * testrun -type metamap -tester massi@gmail.com<br>
 * -input {path to text files}/txt_test/*<br>
 * -outTrec {path to result files}/mm_test<br>
 * -outEx {path to chosen extractor result files}<br>
 * -params [--XMLf]
 * 
 * The "tester" parameter is mandatory for reproduction reasons. Input folder or
 * file and output file are also mandatory.
 * 
 * The "params" option takes the parameters the user wants to call the extractor
 * with. The syntax is<br>
 * [paramname_1=value_1, paramname_2=value_2, ...]
 * 
 * @author massi.brero@gmail.com
 *
 */
public class TestrunCommand implements ICommand {

	/*
	 * constants for received parameters. These have to be validated
	 */
	public static final String TYPE_PARAMETER = "type";
	public static final String EXTRACTOR_PARAMS_PARAMETER = "params";
	public static final String TESTER_PARAMETER = "tester";
	public static final String INPUT_PATH_PARAMETER = "input";
	public static final String OUTPUT_PATH_EXTRACTOR_PARAMETER = "outEx";
	public static final String OUTPUT_PATH_TREC_PARAMETER = "outTrec";
	public static final String TEST_PARAMETER = "test";

	/*
	 * Values für skip parameter. These allow to omit one of the steps of
	 * the<br> computing pipeline.
	 */
	public static final String SKIP_PARAMETER = "skip";
	public static final String SKIP_EXTRACTION_VALUE = "extraction";

	/*
	 * Controller that processes the computing pipeline
	 */
	private TestRunController testrunController = null;
	private String extractorParamsString = "";
	private HashMap<String, String> params = new HashMap<>();
	private ConsoleCommand cmd = new ConsoleCommand();
	private boolean isTest = false;

	/*
	 * Paths for input and output validation.
	 */
	File inputPath = new File("");
	File outputPathExtractorResult = null;
	File outputPathTRECFile = null;
	Extractors type = null;

	/**
	 * Triggers the whole test run for an extractor, including:
	 * <ol>
	 * <li>Initializing the test and storing environment data for
	 * reproducibility purposes.</li>
	 * <li>Starting the extractor with the given parameters.</li>
	 * <li>Saving the result file from the extraction process.</li>
	 * <li>Saving the annotations to a SQL database</li>
	 * <li>Saving the annotation in a TREC result file</li>
	 * </ol>
	 * 
	 * @param {@link
	 * 			ConsoleCommand} The command received from the command line
	 *            including parameters.
	 * @throws Exception 
	 */
	public void execute(ConsoleCommand command) throws Exception {

		cmd = command;
		type = Extractors.valueOf(cmd.getParameters().get(TYPE_PARAMETER).toUpperCase());
		String extractorParamsValues = command.getParameters().get(EXTRACTOR_PARAMS_PARAMETER);
		extractorParamsString = extractorParamsValues != null ? extractorParamsValues : "";

		validateParameters();

		if (extractorParamsString != null && !extractorParamsString.isEmpty()) {
			parseExtractorParameters();
		}

		TestRunController ctrl = getTestrunController();

		if (cmd.getParameters().get(TEST_PARAMETER) != null) {
			setTest(true);
			//ctrl.setSkipExtraction(true);
		}
		

		if (!(skipParameterIsSet() 
				&& command.getParameters().get(SKIP_PARAMETER).equals(SKIP_EXTRACTION_VALUE))) {
			
			/*
			 * Initialize Test run
			 */
			System.out.println("\n\n>>>Initialize Test run...");
			ctrl.initializeTestRun();

			/*
			 * Start extractor with given parameters
			 */
			System.out.println("\n\n>>>Start extractor with given parameters...");
			ctrl.runExtractor();
		}

		/*
		 * Getting concepts from extractor result
		 */
		System.out.println("\n\n>>>Getting concepts from extractor result...");
		ctrl.getAnnotationsFromExtractorResult();

		/*
		 * Save annotations to database
		 */
		System.out.println("\n\n>>>Save annotations to database...");
		ctrl.saveAnnotationsToDatabase();

		/*
		 * Save annotations to TREC file
		 */
		System.out.println("\n\n>>>Save annotationsto TREC file...");
		ctrl.saveAnnotationsToTrecFile();

		if (!skipParameterIsSet())
		{
			if (isTest())
				ctrl.setResult(TestRunResults.TEST);
			else
				ctrl.setResult(TestRunResults.SUCCESS);			
		}

	}

	/**
	 * @see ICommand
	 */
	@Override
	public void validateParameters() throws FileNotFoundException, IllegalArgumentException, ParameterException {

		String normalizedInputPathAsString = "";
		
		/*
		 * If extraction is skipped we do not need a input path for the texts.
		 */
		if (!skipParameterIsSet())
		{
			normalizedInputPathAsString = cmd.getParameters().get(INPUT_PATH_PARAMETER).replace("*", "");
			inputPath = new File(cmd.getParameters().get(INPUT_PATH_PARAMETER));			
		}

		outputPathExtractorResult = new File(cmd.getParameters().get(OUTPUT_PATH_EXTRACTOR_PARAMETER));
		outputPathTRECFile = new File(cmd.getParameters().get(OUTPUT_PATH_TREC_PARAMETER));

		if (type == null)
			throw new IllegalArgumentException("Not a valid extractor type");

		if (!(new File(normalizedInputPathAsString)).exists() && !skipParameterIsSet()) {
			throw new FileNotFoundException("Path for input files does not exist");
		}

		/*
		 * If extraction is skipped there is no need to set a tester!
		 */
		if ((getCmd().getParameters().get(TESTER_PARAMETER) == null
				|| getCmd().getParameters().get(TESTER_PARAMETER) == "")
					&& !skipParameterIsSet()) {
			throw new FileNotFoundException("Please enter the email of the tester!");
		}

		// if (outputPathExtractorResult.isDirectory()) {
		// throw new FileNotFoundException("Please specify a file name in an
		// existing "
		// + "directory for the extractor result!");
		// }

		if (outputPathTRECFile.isDirectory()) {
			throw new FileNotFoundException(
					"Please specify a file name in an existing " + "directory for the TREC result file!");
		}
		
		if (outputPathExtractorResult.isDirectory() 
				&& outputPathExtractorResult.list().length > 0
					&& !skipParameterIsSet()) {
			throw new ParameterException("Please specify an empty folder for extracor result files!");
		}

		if (extractorParamsString.length() > 0) {
			if (extractorParamsString.charAt(0) != '['
					|| extractorParamsString.charAt(extractorParamsString.length() - 1) != ']') {
				throw new IllegalArgumentException(
						"The parameter list for the chosen extractor must start with '[' " + "and end with ']'");
			}
		}

	}

	/**
	 * Analyzes and casts paramaters that shall be handed over to the extractor.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void parseExtractorParameters() throws IllegalArgumentException {

		extractorParamsString = extractorParamsString.replaceAll("[\\[|\\]]", "");

		String[] paramsArray = extractorParamsString.split(",");

		Arrays.stream(paramsArray).forEach(paramsPair -> {
			String[] paramsPairAsArray = paramsPair.split("=");

			if (paramsPairAsArray.length == 1) {
				params.put(paramsPairAsArray[0], "");
			} else if (paramsPairAsArray.length == 2) {
				params.put(paramsPairAsArray[0], paramsPairAsArray[1]);
			} else {
				throw new IllegalArgumentException("The paramater syntax is: " + "[-param1 value1 -param2 ....]");
			}

		});
	}

	/**
	 * Returns {@link TestRunController} as a singleton.
	 * @return
	 */
	public TestRunController getTestrunController() {
		if (testrunController == null) {
			testrunController = new TestRunController(inputPath, type, outputPathExtractorResult, outputPathTRECFile,
					cmd.getParameters().get(TESTER_PARAMETER), getParams());
		}

		return testrunController;
	}

	/**
	 * setter
	 * @param testrunController
	 */
	public void setTestrunController(TestRunController testrunController) {
		this.testrunController = testrunController;
	}

	/**
	 * getter
	 * @return {@link ConsoleCommand}
	 */
	public ConsoleCommand getCmd() {
		return cmd;
	}

	/**
	 * setter
	 * @param cmd
	 */
	public void setCmd(ConsoleCommand cmd) {
		this.cmd = cmd;
	}

	/**
	 * getter 
	 * @return {@link File}
	 */
	public File getInputPath() {
		return inputPath;
	}

	/**
	 * setter
	 * 
	 * @param inputPath
	 */
	public void setInputPath(File inputPath) {
		this.inputPath = inputPath;
	}

	/**
	 * getter
	 * @return {@link File}
	 */
	public File getOutputPathExtractorResult() {
		return outputPathExtractorResult;
	}

	/**
	 * setter
	 * @param outputPathExtractorResult
	 */
	public void setOutputPathExtractorResult(File outputPathExtractorResult) {
		this.outputPathExtractorResult = outputPathExtractorResult;
	}

	/**
	 * getter
	 * @return {@link File}
	 */
	public File getOutputPathTRECFile() {
		return outputPathTRECFile;
	}

	/**
	 * setter
	 * @param outputPathTRECFile
	 */
	public void setOutputPathTRECFile(File outputPathTRECFile) {
		this.outputPathTRECFile = outputPathTRECFile;
	}

	/**
	 * getter 
	 * @return {@link Extractors}
	 */
	public Extractors getType() {
		return type;
	}

	/**
	 * setter
	 * @param {@link {@link Extractors} type
	 */
	public void setType(Extractors type) {
		this.type = type;
	}

	/**
	 * getter
	 * @return {@link HashMap<String, String>} 
	 */
	public HashMap<String, String> getParams() {
		return params;
	}

	/**
	 * setter 
	 * @param {@link HashMap<String, String>} params
	 */
	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}

	/**
	 * getter
	 * @return {@link String}
	 */
	public String getParamsString() {
		return extractorParamsString;
	}

	/**
	 * setter
	 * @param String paramsString
	 */
	public void setParamsString(String paramsString) {
		this.extractorParamsString = paramsString;
	}

	/**
	 * Checks if this run is just a dry run for testing.
	 * @return {@link Boolean}
	 */
	public boolean isTest() {
		return isTest;
	}

	/**
	 * Sets if this run is just a dry run for testing.
	 * @param boolean isTest
	 */
	public void setTest(boolean isTest) {
		this.isTest = isTest;
	}
	
	/**
	 * Checks if a parameter is set that says to skip parts of the processing pipeline.
	 * @return boolean
	 */
	private boolean skipParameterIsSet() {
		return cmd.getParameters().get(SKIP_PARAMETER) != null;
	}

}
