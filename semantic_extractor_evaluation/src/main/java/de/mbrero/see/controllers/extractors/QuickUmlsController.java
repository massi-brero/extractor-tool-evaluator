package de.mbrero.see.controllers.extractors;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import de.mbrero.see.exceptions.ExtractorExecutionException;

/**
 * This class is for managing the QuickUMLS extractor.
 * It will use the python adapter written for this testing software since<br>
 * QuickUMLS is a barebone extractor that returns the results a a list of python<br>
 * dictionary objects.
 * 
 * 
 * @author massimiliano.brero@studium.fernuni-hagen.de
 *
 */
public class QuickUmlsController extends AbstractExtractorController {

	private final String RUN_SCRIPT_PATH = "/run.py";

	/**
	 * Set information needed for a extration process. 
	 * @param File inputFile The texts that should be annotated.
	 * @param File outputFile Where the extractor files shall be stored.
	 * @param HashMap<String, String> params Parameters handed over to QuickUMLS
	 * <ul>
	 * <li>-q <umls data></li>
	 * <li>-l <overlapping criteria></li>
	 * <li>-t <threshhold></li>
	 * <li>-m <minimum matched length> </li>
	 * <li>-s <similarity name> -w <window> </li>
	 * <li>-i <input_path file path></li>
	 * <li>-o <output_path file path></li>
	 * </ul>
	 * 
	 */
	public QuickUmlsController(File inputFile, File outputFile, HashMap<String, String> params) {
		super(inputFile, outputFile, params);
	}


	/**
	 * Starts the extractor for an annotation run.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ExtractorExecutionException 
	 * 
	 * @override
	 */
	public int start() throws IOException, InterruptedException, ExtractorExecutionException {	
		Instant start = Instant.now();
		
		int result = runExtractionProcess();
		
		Instant end = Instant.now();
		setExecutionTime(Duration.between(start, end));
		
		return result;
	}

	/**
	 * 
	 * @return int
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ExtractorExecutionException
	 */
	private int runExtractionProcess() throws IOException, InterruptedException, ExtractorExecutionException {
		int result = 0;
		
		result = runLinuxExec(buildStartCommand(), true);
		
		return result;
	}
	
	/**
	 * @return ArrayList<String>
	 */
	protected ArrayList<String> buildStartCommand() {
		ArrayList<String> startCmd = new ArrayList<>();
		startCmd.add("python");
		startCmd.add(getBasePath() + RUN_SCRIPT_PATH);
		startCmd.add("-i");
		startCmd.add(getInputFile().getAbsolutePath());
		startCmd.add("-o");
		startCmd.add(getOutputFile().getAbsolutePath());
		
		return startCmd;
	}

}
