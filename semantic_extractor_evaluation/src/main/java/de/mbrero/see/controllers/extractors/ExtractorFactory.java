package de.mbrero.see.controllers.extractors;

import java.io.File;
import java.util.HashMap;

import types.Extractors;

/**
 * This class is used to instantiate one of the possible concept mappers.
 * @see Extractors
 * 
 * @author massimiliano.brero@studium.fernuni-hagen.de
 *
 */
public class ExtractorFactory {

	/*
	 * Path to Metamap bin folder.
	 */
	private final static String STANDARD_METAMAP_PATH = System.getProperty("user.dir")
			+ "/../../resources/extractors/metamap/public_mm";
	
	/*
	 * Path to the QuickUMLS python adapter written for this testing software.
	 */
	private final static String STANDARD_QUICKUMLS_PATH = System.getProperty("user.dir")
			+ "/../../resources/extractors/quickumls/QuickUMLS-1.2";
	
	private static File inputFile = null;
	private static File outputFile = null;
	private static File basePath = null;
	private static HashMap<String, String> params = null;

	/**
	 * Use this to generate a extractor of the Type {@link Extractor} and return and configured and<br>
	 * instantiated object.
	 * 
	 * @param extractor
	 * @param bPath
	 * @param input
	 * @param output
	 * @param parameters
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static Extractor getExtractor(Extractors extractor, File bPath, File input, File output,
			HashMap<String, String> parameters) throws IllegalArgumentException {
		basePath = bPath;
		inputFile = input;
		outputFile = output;
		params = parameters;
		return buildExtractor(extractor);
	}

	/**
	 * 
	 * @param extractor
	 * @return
	 * @throws IllegalArgumentException
	 */
	private static Extractor buildExtractor(Extractors extractor) throws IllegalArgumentException {
		switch (extractor) {
		case QUICKUMLS:
			File quickPath = basePath == null || !basePath.isFile() ? new File(STANDARD_QUICKUMLS_PATH) : basePath;
			QuickUmlsController quickCtrl = new QuickUmlsController(inputFile, outputFile, params);
			quickCtrl.setBasePath(quickPath);
			return quickCtrl;
		case METAMAP:
			File mmPath = basePath == null || !basePath.isFile() ? new File(STANDARD_METAMAP_PATH) : basePath;
			MetaMapController mmCtrl = new MetaMapController(inputFile, outputFile, params);
			mmCtrl.setBasePath(mmPath);
			return mmCtrl;
		default:
			throw new IllegalArgumentException("That extractor is not possible.");
		}

	}

}
