package de.mbrero.see.controllers.extractors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import de.mbrero.see.exceptions.ExtractorExecutionException;

/**
 * This class is for managing the MetaMap extractor.
 * 
 * 
 * @author massi.brero@gmail.com
 *
 */
public class MetaMapController extends AbstractExtractorController {

	private final String START_EXTRACTION_CMD = "/bin/metamap";
	private final String TAGGER_CMD = "/bin/skrmedpostctl";
	private final String DISMBIGUATION_SERVER_CMD = "/bin/wsdserverctl";
	private final String OUTPUT_SUFFIX = ".out";
	private final HashMap<String, String> PARAMS_FROM_USER_INPUT;
	private final String USE_DISAMBIGUATION_SERVER = "--DISAMB";
	private final String PYTHON_RUN_PATH = "../semantic_extractor_evaluation/src/main/resources/scripts/metamap_corrector/run.py";
	private boolean withDisambiguiation = false;

	public MetaMapController(File inputFile, File outputFile, HashMap<String, String> params) {
		super(inputFile, outputFile, new HashMap<String, String>());
		
		if(params.containsKey(USE_DISAMBIGUATION_SERVER)) {
			setWithDisambiguiation(true);
			params.remove(USE_DISAMBIGUATION_SERVER);
		}
		
		PARAMS_FROM_USER_INPUT = params;
	}


	/**
	 * Starts all the server for an annotation run.
	 * 
	 * It can be set if the disambiguation server has to be started first.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ExtractorExecutionException 
	 * 
	 * @override
	 */
	public int start() throws IOException, InterruptedException, ExtractorExecutionException {	
		Instant start = Instant.now();
		
		if (isWithDisambiguiation())
			startDisambiguationServer();
		startTagger();
		int result = runExtractionProcess();
		stopTagger();
		
		Instant end = Instant.now();
		setExecutionTime(Duration.between(start, end));

		
		if (isWithDisambiguiation())
			stopDisambiguationServer();
		
		return result;
	}


	private int runExtractionProcess() throws IOException, InterruptedException, ExtractorExecutionException {
		int result = 0;
		int resultXMLCorrection = 0;
		File inputPathFromUser = getInputFile();
		File outputPathFromUser = getOutputFile();
		
		if (inputPathFromUser.isFile()) {
			adaptParamsForMetaMap();
			result = runLinuxExec(buildStartCommand(), true);
				

		} else if (inputPathFromUser.isDirectory()) {

			File[] files = inputPathFromUser.listFiles();

			for (File file : files) {
				setInputFile(file);
				
				if(outputPathFromUser.isDirectory()) {
					File newOutputFile = new File(
							outputPathFromUser.getAbsolutePath()
							.concat("/")
							.concat(file.getName())
							.concat(OUTPUT_SUFFIX)
							);
					setOutputFile(newOutputFile);
				}
				adaptParamsForMetaMap();
				result = runLinuxExec(buildStartCommand(), true);
				
				if (result != 0) break;
			}

		} else {
			throw new FileNotFoundException("Could not parse given path!");
		}
		
		/**
		 * Eventually correct xml output;
		 */
		if (result == 0) {
			System.out.println("\n\n>>> Checking and correcting XML-Output..\n");
			resultXMLCorrection = runLinuxExec(buildCorrectorCommand(outputPathFromUser.getAbsolutePath()), false);
			System.out.println("\n>>> Finished checking XML-Output...\n\n");
		}
		
		/*
		 * Returns error from extraction process if that went wrong else from the correction process.
		 */
		return result != 0 ? result : resultXMLCorrection;
	}
	
	/**
	 * For Metamap the input and output paths must be placed at the end of the parameter list.
	 */
	public void adaptParamsForMetaMap() {

		ArrayList<String> paramsAsArray = new ArrayList<>();

		if (PARAMS_FROM_USER_INPUT != null) {
			PARAMS_FROM_USER_INPUT.forEach((key, value) -> {
				if (key != null && !key.isEmpty() && !key.contains("/"))
					paramsAsArray.add(key);
				
				//the paths have to be filtered in order to put them after the other parameter calls
				if (value != null && !value.isEmpty())
					paramsAsArray.add(value);
				
			});			
		}
		
		paramsAsArray.add(getInputFile().getAbsolutePath());
		paramsAsArray.add(getOutputFile().getAbsolutePath());

		this.params = paramsAsArray;
	}


	protected ArrayList<String> buildStartCommand() {
		ArrayList<String> startCmd = new ArrayList<>();
		startCmd.add(getBasePath() + START_EXTRACTION_CMD);
		
		return startCmd;
	}
	

	protected ArrayList<String> buildCorrectorCommand(String outputPath) {
		ArrayList<String> correctorCommand = new ArrayList<>();
		String pathScript = (new File(PYTHON_RUN_PATH)).getAbsolutePath();
		
		correctorCommand.add("python");
		correctorCommand.add(pathScript);
		correctorCommand.add(outputPath);
		
		return correctorCommand;
	}


	private void startDisambiguationServer() throws IOException, InterruptedException, ExtractorExecutionException {
		ArrayList<String> command = new ArrayList<>();
		command.add(getBasePath() + DISMBIGUATION_SERVER_CMD);
		command.add("start");
		runLinuxExec(command, false);

	}

	public void stopDisambiguationServer() throws IOException, InterruptedException, ExtractorExecutionException {
		ArrayList<String> command = new ArrayList<>();
		command.add(getBasePath() + DISMBIGUATION_SERVER_CMD);
		command.add("stop");
		runLinuxExec(command, false);

	}

	private void startTagger() throws IOException, InterruptedException, ExtractorExecutionException {
		ArrayList<String> command = new ArrayList<>();
		command.add(getBasePath() + TAGGER_CMD);
		command.add("start");
		runLinuxExec(command, false);

	}

	private void stopTagger() throws IOException, InterruptedException, ExtractorExecutionException {
		ArrayList<String> command = new ArrayList<>();
		command.add(getBasePath() + TAGGER_CMD);
		command.add("stop");
		runLinuxExec(command, false);

	}

	public boolean isWithDisambiguiation() {
		return withDisambiguiation;
	}

	public void setWithDisambiguiation(boolean withDisambiguiation) {
		this.withDisambiguiation = withDisambiguiation;
	}

}
