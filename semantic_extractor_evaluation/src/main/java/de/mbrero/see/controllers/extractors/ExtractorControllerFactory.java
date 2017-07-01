package de.mbrero.see.controllers.extractors;

import java.io.File;
import java.util.HashMap;

import types.Extractors;

public class ExtractorControllerFactory {

	private final static String STANDARD_METAMAP_PATH = "";
	private static File inputFile = null;
	private static File outputFile = null;
	private static File basePath = null;
	private static HashMap<String, String> params = null;

	public static ExtractorController getExstractor(Extractors extractor, 
													File path,
													File input, 
													File output, 
													HashMap parameters) {
		inputFile = input;
		outputFile = output;
		params = parameters;
		basePath = path;
		return buildExtractorController(extractor);
	}
	
	private static ExtractorController buildExtractorController(Extractors extractor) {
		switch (extractor) {
		case METAMAP:
			File path = basePath == null || !basePath.isFile() ? STANDARD_METAMAP_PATH : basePath;
			MetaMapController mmCtrl = new MetaMapController(inputFile, outputFile, params);
			mmCtrl.setBasePath(basePath);
			
			default:
		}
	}
	
}
