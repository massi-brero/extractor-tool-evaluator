package de.mbrero.see.console.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;

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
	private HashMap<String, String> params = new HashMap<>();
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
		outputPathExtractorResult = new File(cmd.getParameters().get(OUTPUT_PATH_EXTRACTOR_PARAMETER));
		outputPathTRECFile = new File(cmd.getParameters().get(OUTPUT_PATH_TREC_PARAMETER));

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

	public void parseExtractorParameters() throws IllegalArgumentException {

		paramsString = paramsString.replaceAll("[\\[|\\]]", "");

		String[] paramsArray = paramsString.split(",");

		Arrays.stream(paramsArray).forEach(paramsPair -> {
			String[] paramsPairAsArray = paramsPair.split("=");
			
			if(paramsArray.length == 1) {
				params.put(paramsPairAsArray[0], "");				
			}
			else if (paramsPairAsArray.length == 2) {
				params.put(paramsPairAsArray[0], paramsPairAsArray[1]);				
			}
			else {
				throw new IllegalArgumentException("The paramater syntax is: "
						+ "[-param1 value1 -param2 ....]");				
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

}
