package de.mbrero.see.console.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import de.mbrero.see.controllers.TestRunController;
import types.Extractors;

/**
 * Triggers the execution of a semantic extractor. The Type supported can be
 * found in {@link Extractors}.<br>
 * A valid call from the console may look like this<br>
 * testrun -type metamap -tester massi@gmail.com -input xxx -output xxx
 * -params [a=b,c=d,e=f]
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

	public final String TYPE_PARAMETER = "type";
	public final String EXTRACTOR_PARAMS_PARAMETER = "type";
	public final String INPUT_PATH_PARAMETER = "input";
	public final String OUTPUT_PATH_EXTRACTOR_PARAMETER = "outEx";
	public final String OUTPUT_PATH_TREC_PARAMETER = "outTrec";
	private String paramsString = null;
	private ConsoleCommand cmd = new ConsoleCommand();
	File inputPath = null;
	File outputPathExtractorResult = null;
	File outputPathTRECFile = null;
	Extractors type = null;

	public void execute(ConsoleCommand command) throws Exception {

		cmd = command;
		type = Extractors.valueOf(cmd.getParameters().get(TYPE_PARAMETER).toUpperCase());
		paramsString = command.getParameters().get(EXTRACTOR_PARAMS_PARAMETER);

		validateParameters();

		if (paramsString != null && !paramsString.isEmpty()) {
			parseExtractorParameters();
		}

		//TestRunController ctrl = new TestRunController();
		
		/*
		 * Initiallize Test run
		 */
		System.out.println(">>>Initiallize Test run...");
		
		/*
		 * Start extractor with given parameters
		 */
		System.out.println(">>>Start extractor with given parameters...");
		
		/*
		 * Save annotations to database
		 */
		System.out.println(">>>Save annotations to database...");
		
		/*
		 * Save annotationsto TREC file
		 */
		System.out.println(">>>Save annotationsto TREC file...");


	}

	@Override
	public void validateParameters() throws FileNotFoundException, IllegalArgumentException {

		inputPath = new File(cmd.getParameters().get(INPUT_PATH_PARAMETER));
		outputPathExtractorResult = new File(cmd.getParameters().get(OUTPUT_PATH_PARAMETER));

		if (type == null)
			throw new IllegalArgumentException("Not a valid extractor type");

		if (!inputPath.exists()) {
			throw new FileNotFoundException("Path for input files does not exist");
		}

		if (!outputPathExtractorResult.exists() || outputPathExtractorResult.isDirectory()) {
			throw new FileNotFoundException("Please specify a file name in an existing "
					+ "directory for the extractor result!");
		}
		
		if (!outputPathTRECFile.exists() || outputPathTRECFile.isDirectory()) {
			throw new FileNotFoundException("Please specify a file name in an existing "
					+ "directory for the TREC result file!");
		}

		if (paramsString.length() > 0) {
			if (paramsString.charAt(0) != '[' || paramsString.charAt(paramsString.length() - 1) != ']') {
				throw new IllegalArgumentException(
						"The parameter list for the cosen extracot must start with '[' " + "and end with ']'");
			}
		}

	}

	public void parseExtractorParameters() {

		paramsString = paramsString.replaceAll("[\\[|\\]]", "");
		StringBuffer result = new StringBuffer();

		String[] paramsArray = paramsString.split(",");

		Arrays.stream(paramsArray).forEach(paramsPair -> {
			paramsPair = "-" + paramsPair;
			Arrays.stream(paramsPair.split("=")).forEach(value -> {
				result.append(value).append(" ");
			});
		});
		
		setParamsString(result.toString());

	}

	public String getParamsString() {
		return paramsString;
	}

	public void setParamsString(String paramsString) {
		this.paramsString = paramsString;
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

	public File getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(File outputPath) {
		this.outputPath = outputPath;
	}

	public Extractors getType() {
		return type;
	}

	public void setType(Extractors type) {
		this.type = type;
	}

}
