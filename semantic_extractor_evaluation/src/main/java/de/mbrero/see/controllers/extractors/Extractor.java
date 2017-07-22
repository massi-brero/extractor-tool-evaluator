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

	void setParams(HashMap<String, String> params);
	Duration getExecutionTime();
	void setInputFile(File inputFile);
	void setOutputFile(File outputFile);
	void start() throws IOException, InterruptedException, ExtractorExecutionException;
	
}
