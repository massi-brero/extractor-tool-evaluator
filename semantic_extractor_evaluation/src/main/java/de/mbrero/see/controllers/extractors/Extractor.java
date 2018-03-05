package de.mbrero.see.controllers.extractors;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;

import de.mbrero.see.exceptions.ExtractorExecutionException;

/**
 * Interface to be implemented by all Extractor controllers.
 * 
 * @author massi.brero@gmail.com
 *
 */
public interface Extractor {

	/**
	 * Set the params needed for a test run. Especially: input/output paths, tester,<br>
	 * params to hand over to the concepts mapper.
	 * @param {@link {@link HashMap}} params
	 */
	void setParams(HashMap<String, String> params);
	
	/**
	 * Return the time the extractor took for the extraction process.
	 * @return {@link Duration}
	 */
	Duration getExecutionTime();
	
	/**
	 * set the folder or the file the concepts have to be extracted from.
	 * @param {@link File} inputFile
	 */
	void setInputFile(File inputFile);
	
	/**
	 * set the path the extractor results should be saved into.
	 * 
	 * @param {@link File} outputFile
	 */
	void setOutputFile(File outputFile);
	
	/**
	 * Start the concept mapper.
	 * 
	 * @return int
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ExtractorExecutionException
	 */
	int start() throws IOException, InterruptedException, ExtractorExecutionException;
	
}
