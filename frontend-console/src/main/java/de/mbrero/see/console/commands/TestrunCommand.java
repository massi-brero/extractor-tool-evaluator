package de.mbrero.see.console.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;

import de.mbrero.see.api.TestRunController;
import de.mbrero.see.persistance.dto.types.TestRunResults;
import types.Extractors;

/**
 * Triggers the execution of a semantic extractor. The Type supported can be
 * found in {@link Extractors}.<br>
 * A valid call from the console may look like this<br>
 * testrun -type metamap -tester massi@gmail.com -input
 * /home/massi/projects/result_files/txt_test/* -outTrec
 * /home/massi/projects/result_files/TREC/mm_test -outEx
 * /home/massi/projects/result_files/extractor_results/mm/res1.xml -params
 * [--XMLf]
 * 
 * The "tester" parameter is mandatory for reproduction reasons. Input folder or
 * file and output file<br>
 * are also mandatory.
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
	public final String TYPE_PARAMETER = "type";
	public final String EXTRACTOR_PARAMS_PARAMETER = "params";
	public final String TESTER_PARAMETER = "tester";
	public final String INPUT_PATH_PARAMETER = "input";
	public final String OUTPUT_PATH_EXTRACTOR_PARAMETER = "outEx";
	public final String OUTPUT_PATH_TREC_PARAMETER = "outTrec";
	public final String TEST_PARAMETER = "test";

	private String paramsString = null;
	private HashMap<String, String> params = new HashMap<>();
	private ConsoleCommand cmd = new ConsoleCommand();
	private boolean isTest = false;

	/*
	 * Paths for input and output validation.
	 */
	File inputPath = null;
	File outputPathExtractorResult = null;
	File outputPathTRECFile = null;
	Extractors type = null;

	/**
	 * Triggers the whole test run for an extractor, including:
	 * <ol>
	 * <li>Initializing the test and storing enveronment data for
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
	 */
	public void execute(ConsoleCommand command) throws Exception {

		cmd = command;
		type = Extractors.valueOf(cmd.getParameters().get(TYPE_PARAMETER).toUpperCase());
		paramsString = command.getParameters().get(EXTRACTOR_PARAMS_PARAMETER);

		validateParameters();

		if (paramsString != null && !paramsString.isEmpty()) {
			parseExtractorParameters();
		}

		TestRunController ctrl = new TestRunController(inputPath, type, outputPathExtractorResult, outputPathTRECFile,
				cmd.getParameters().get(TESTER_PARAMETER), getParams());

		setTest(cmd.getParameters().get(TEST_PARAMETER) != null ? true : false);

		/*
		 * Initiallize Test run
		 */
		System.out.println("\n\n>>>Initiallize Test run...");
		ctrl.initializeTestRun();

		/*
		 * Start extractor with given parameters
		 */
		System.out.println("\n\n>>>Start extractor with given parameters...");
		ctrl.runExtractor();

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

		if (isTest())
			ctrl.setResult(TestRunResults.TEST);
		else
			ctrl.setResult(TestRunResults.SUCCESS);

	}

	@Override
	public void validateParameters() throws FileNotFoundException, IllegalArgumentException {

		String normalizedInputPathAsString = cmd.getParameters().get(INPUT_PATH_PARAMETER).replace("*", "");
		inputPath = new File(cmd.getParameters().get(INPUT_PATH_PARAMETER));

		outputPathExtractorResult = new File(cmd.getParameters().get(OUTPUT_PATH_EXTRACTOR_PARAMETER));
		outputPathTRECFile = new File(cmd.getParameters().get(OUTPUT_PATH_TREC_PARAMETER));

		if (type == null)
			throw new IllegalArgumentException("Not a valid extractor type");

		if (!(new File(normalizedInputPathAsString)).exists()) {
			throw new FileNotFoundException("Path for input files does not exist");
		}

		if (getCmd().getParameters().get(TESTER_PARAMETER) == null
				|| getCmd().getParameters().get(TESTER_PARAMETER) == "") {
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

		if (paramsString.length() > 0) {
			if (paramsString.charAt(0) != '[' || paramsString.charAt(paramsString.length() - 1) != ']') {
				throw new IllegalArgumentException(
						"The parameter list for the chosen extractor must start with '[' " + "and end with ']'");
			}
		}

	}

	public void parseExtractorParameters() throws IllegalArgumentException {

		paramsString = paramsString.replaceAll("[\\[|\\]]", "");

		String[] paramsArray = paramsString.split(",");

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

	public ConsoleCommand getCmd() {
		return cmd;
	}

	public void setCmd(ConsoleCommand cmd) {
		this.cmd = cmd;
	}

	public File getInputPath() {
		return inputPath;
	}

	public void setInputPath(File inputPath) {
		this.inputPath = inputPath;
	}

	public File getOutputPathExtractorResult() {
		return outputPathExtractorResult;
	}

	public void setOutputPathExtractorResult(File outputPathExtractorResult) {
		this.outputPathExtractorResult = outputPathExtractorResult;
	}

	public File getOutputPathTRECFile() {
		return outputPathTRECFile;
	}

	public void setOutputPathTRECFile(File outputPathTRECFile) {
		this.outputPathTRECFile = outputPathTRECFile;
	}

	public Extractors getType() {
		return type;
	}

	public void setType(Extractors type) {
		this.type = type;
	}

	public HashMap<String, String> getParams() {
		return params;
	}

	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}

	public String getParamsString() {
		return paramsString;
	}

	public void setParamsString(String paramsString) {
		this.paramsString = paramsString;
	}

	public boolean isTest() {
		return isTest;
	}

	public void setTest(boolean isTest) {
		this.isTest = isTest;
	}

}
