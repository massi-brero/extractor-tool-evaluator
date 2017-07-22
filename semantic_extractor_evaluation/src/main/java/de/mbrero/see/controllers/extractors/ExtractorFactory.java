package de.mbrero.see.controllers.extractors;

import java.io.File;
import java.util.HashMap;

import types.Extractors;

public class ExtractorFactory {

	private final static String STANDARD_METAMAP_PATH = System.getProperty("user.dir")
			+ "/../../resources/extractors/metamap/public_mm";
	private static File inputFile = null;
	private static File outputFile = null;
	private static File basePath = null;
	private static HashMap<String, String> params = null;

	public static Extractor getExtractor(Extractors extractor, File bPath, File input, File output,
			HashMap<String, String> parameters) throws IllegalArgumentException {
		basePath = bPath;
		inputFile = input;
		outputFile = output;
		params = parameters;
		return buildExtractor(extractor);
	}

	private static Extractor buildExtractor(Extractors extractor) throws IllegalArgumentException {
		switch (extractor) {
		case METAMAP:
			File path = basePath == null || !basePath.isFile() ? new File(STANDARD_METAMAP_PATH) : basePath;
			MetaMapController mmCtrl = new MetaMapController(inputFile, outputFile, params);
			mmCtrl.setBasePath(path);
			return mmCtrl;
		default:
			throw new IllegalArgumentException("That Extractor is not possible.");
		}

	}

}
