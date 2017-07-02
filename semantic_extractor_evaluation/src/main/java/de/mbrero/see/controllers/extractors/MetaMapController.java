package de.mbrero.see.controllers.extractors;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class MetaMapController extends AbstractExtractorController {
	
	private final String START_EXTRACTION_CMD = "./bin/metamap";
	private final String START_TAGGER_CMD = "/bin/skrmedpostctl start";
	private final String STOP_TAGGER_CMD = "/bin/skrmedpostctl stop";
	private final String START_DIAMBIGUATION_SERVER_CMD = "/bin/wsdserverctl start";
	private final String STOP_DIAMBIGUATION_SERVER_CMD = "/bin/wsdserverctl stop";
	
	
	public MetaMapController(File inputFile, File outputFile, HashMap<String, String> params) {
		setInputFile(inputFile);
		setOutputFile(outputFile);
		setParams(params);
	}
	
	/**
	 * Starts all the server for an annotation run.
	 * 
	 * @param params HashMap<String, String> for the console run.
	 * @param startDisambiguation starts disambiguation server. This is optional.
	 * @throws IOException 
	 * 
	 * @override
	 */
	public void start(Boolean startDisambiguation) throws IOException {

		if (startDisambiguation) 
			startDisambiguationServer();
		
		start();
		stopDisambiguationServer();
	}
	
	public void start() throws IOException {
		
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
	
	

	private void startDisambiguationServer() throws IOException {
		Process proc = Runtime.getRuntime().exec(new String[]{"bash","-c", getBasePath() + START_DIAMBIGUATION_SERVER_CMD});
		
	}

	public void stopDisambiguationServer() throws IOException {
		Process proc = Runtime.getRuntime().exec(new String[]{"bash","-c", getBasePath() + STOP_DIAMBIGUATION_SERVER_CMD});
	
	}
	
	private void startTagger() throws IOException {
		Process proc = Runtime.getRuntime().exec(new String[]{"bash","-c", getBasePath() + START_TAGGER_CMD});
		
	}
	
	private void stopTagger() throws IOException {
		Process proc = Runtime.getRuntime().exec(new String[]{"bash","-c", getBasePath() + STOP_TAGGER_CMD});
		
	}
	
}
