package de.mbrero.see.controllers.extractors;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.junit.runner.notification.RunListener;

public class MetaMapController extends AbstractExtractorController {
	
	private final String START_EXTRACTION_CMD = "./bin/metamap";
	private final String TAGGER_CMD = "/bin/skrmedpostctl";
	private final String DISMBIGUATION_SERVER_CMD = "/bin/wsdserverctl";
	
	public MetaMapController(File inputFile, File outputFile, HashMap<String, String> params) {
		super(inputFile, outputFile, params);
	}
	
	/**
	 * Starts all the server for an annotation run.
	 * 
	 * @param params HashMap<String, String> for the console run.
	 * @param startDisambiguation starts disambiguation server. This is optional.
	 * @throws IOException 
	 * @throws InterruptedException 
	 * 
	 * @override
	 */
	public void start(Boolean startDisambiguation) throws IOException, InterruptedException {

		if (startDisambiguation) 
			startDisambiguationServer();
		
		start();
		stopDisambiguationServer();
	}
	
	public void start() throws IOException, InterruptedException {
		
		buildCommand();
		startTagger();
		
		Process proc = Runtime.getRuntime().exec(new String[]{"bash","-c", buildCommand()});
		
		stopTagger();
	}
	

	/**
	 * @todo add params
	 */
	@Override
	protected String buildCommand() {
		return getBasePath() + START_EXTRACTION_CMD + " " 
							+ getInputFile().getAbsolutePath() + " " 
							+ getOutputFile().getAbsolutePath();
	}
	
	

	private void startDisambiguationServer() throws IOException, InterruptedException {
		String[] command = { getBasePath() + DISMBIGUATION_SERVER_CMD, "start" };
		runLinuxExec(command);
		
	}

	public void stopDisambiguationServer() throws IOException, InterruptedException {
		String[] command = { getBasePath() + DISMBIGUATION_SERVER_CMD, "stop" };
		runLinuxExec(command);
	
	}
	
	private void startTagger() throws IOException, InterruptedException {
		String[] command = { getBasePath() + TAGGER_CMD, "start" };
		runLinuxExec(command);
		
	}
	
	private void stopTagger() throws IOException, InterruptedException {
		String[] command = { getBasePath() + TAGGER_CMD, "stop" };
		runLinuxExec(command);

	}
	

}
