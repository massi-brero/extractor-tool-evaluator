package de.mbrero.see.controllers.extractors;

import java.io.File;
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
	private boolean withDisambiguiation = false;

	public MetaMapController(File inputFile, File outputFile, HashMap<String, String> params) {
		super(inputFile, outputFile, params);
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
		int result = runLinuxExec(buildStartCommand());
		stopTagger();
		
		Instant end = Instant.now();
		setExecutionTime(Duration.between(start, end));

		
		if (isWithDisambiguiation())
			stopDisambiguationServer();
		
		return result;
	}

	/**
	 * @todo add params
	 */
	protected ArrayList<String> buildStartCommand() {
		ArrayList<String> startCmd = new ArrayList<>();
		startCmd.add(getBasePath() + START_EXTRACTION_CMD);
		startCmd.add(getInputFile().getAbsolutePath());
		startCmd.add(getOutputFile().getAbsolutePath());
		
		return startCmd;
		
	}

	private void startDisambiguationServer() throws IOException, InterruptedException, ExtractorExecutionException {
		ArrayList<String> command = new ArrayList<>();
		command.add(getBasePath() + DISMBIGUATION_SERVER_CMD);
		command.add("start");
		runLinuxExec(command);

	}

	public void stopDisambiguationServer() throws IOException, InterruptedException, ExtractorExecutionException {
		ArrayList<String> command = new ArrayList<>();
		command.add(getBasePath() + DISMBIGUATION_SERVER_CMD);
		command.add("stop");
		runLinuxExec(command);

	}

	private void startTagger() throws IOException, InterruptedException, ExtractorExecutionException {
		ArrayList<String> command = new ArrayList<>();
		command.add(getBasePath() + TAGGER_CMD);
		command.add("start");
		runLinuxExec(command);

	}

	private void stopTagger() throws IOException, InterruptedException, ExtractorExecutionException {
		ArrayList<String> command = new ArrayList<>();
		command.add(getBasePath() + TAGGER_CMD);
		command.add("stop");
		runLinuxExec(command);

	}

	public boolean isWithDisambiguiation() {
		return withDisambiguiation;
	}

	public void setWithDisambiguiation(boolean withDisambiguiation) {
		this.withDisambiguiation = withDisambiguiation;
	}

}
