package de.mbrero.see.controllers.extractors;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Interface to be implemented by all Extractor controllers.
 * 
 * @author massi.brero@gmail.com
 *
 */
public interface ExtractorController {

	void setParams(HashMap<String, String> params);
	Float getExecutionTime();
	void setInputFile(File inputFile);
	void setOutputFile(File outputFile);
	void start() throws IOException;
	
}
