package de.mbrero.see.controllers.extractors;

import java.io.File;
import java.util.HashMap;

import types.Extractors;

public class ExtractorControllerFactory {

	private final static String STANDARD_METAMAP_PATH = System.getProperty("user.dir")
			+ "/../../resources/extractors/metamap/public_mm";
	private static File inputFile = null;
	private static File outputFile = null;
	private static File basePath = null;
	private static HashMap<String, String> params = null;

	public static ExtractorController getExtractor(Extractors extractor, File path, File input, File output,
			HashMap<String, String> parameters) throws IllegalArgumentException {
		inputFile = input;
		outputFile = output;
		params = parameters;
		basePath = path;
		return buildExtractorController(extractor);
	}

	private static ExtractorController buildExtractorController(Extractors extractor) throws IllegalArgumentException {
		switch (extractor) {
		case METAMAP:
			File path = basePath == null || !basePath.isFile() ? new File(STANDARD_METAMAP_PATH) : basePath;
			MetaMapController mmCtrl = new MetaMapController(inputFile, outputFile, params);
			mmCtrl.setBasePath(basePath);
			return mmCtrl;
		default:
			throw new IllegalArgumentException("That Extractor is not possible.");
		}

	}

}
