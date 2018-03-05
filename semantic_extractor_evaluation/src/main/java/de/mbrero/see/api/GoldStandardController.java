package de.mbrero.see.api;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import de.mbrero.see.parser.CRAFTParser;
import de.mbrero.see.parser.ParserFactory;
import de.mbrero.see.persistance.dto.Annotation;
import de.mbrero.see.services.AnnotationsService;
import types.GoldStandardType;
import types.ParserType;

/**
 * Parses the Goldstandard and saves the annotations to the database and creates
 * a qrel File for the TREC tool
 * 
 * @author massi.brero@gmail.com
 *
 */
public class GoldStandardController {

	private GoldStandardType type = null;
	private File inputPath = new File("");
	private File outputPath = new File("");
	private HashMap<String, HashMap<String, Annotation>> annotations = new HashMap<>();
	private AnnotationsService annotationsService;

	public GoldStandardController(GoldStandardType type, File input, File output) {
		setType(type);
		setInputPath(input);
		setOutputPath(output);
		annotationsService = new AnnotationsService();
	}

	/**
	 * Manages complete Job. Extracts the annotations from the files in the
	 * given path and saves them to the database and creates a qrel File for the
	 * TREC tool evaluation.
	 * 
	 * @param type
	 * @throws Exception
	 */
	public void persistGoldStanstandard() throws Exception {
		System.out.println("Starting Job\n\n");

		switch (type) {
		case CRAFT:
		default:
			runGoldStandardJob();
			break;
		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void retrieveAnnotations() throws Exception {
		CRAFTParser crParser = (CRAFTParser) ParserFactory.getInstance(ParserType.CRAFT);
		crParser.parse(getInputPath());
		annotations = crParser.getAnnotations();
	}

	/**
	 * 
	 * @return {@link GoldStandardType}
	 */
	public GoldStandardType getType() {
		return type;
	}

	/**
	 * 
	 * @param {@link GoldStandardType} type
	 */
	public void setType(GoldStandardType type) {
		this.type = type;
	}

	/**
	 * 
	 * @return {@link File}
	 */
	public File getInputPath() {
		return inputPath;
	}

	/**
	 * 
	 * @param {@link File} inputPath
	 */
	public void setInputPath(File inputPath) {
		this.inputPath = inputPath;
	}

	/**
	 * 
	 * @return {@link File}
	 */
	public File getOutputPath() {
		return outputPath;
	}

	/**
	 * 
	 * @param {@link File} outputPath
	 */
	public void setOutputPath(File outputPath) {
		this.outputPath = outputPath;
	}

	/**
	 * 
	 * @return {@link HashMap}
	 */
	public HashMap<String, HashMap<String, Annotation>> getAnnotations() {
		return annotations;
	}

	/**
	 * 
	 * @param {@link HashMap} annotations
	 */
	public void setAnnotations(HashMap<String, HashMap<String, Annotation>> annotations) {
		this.annotations = annotations;
	}

	/**
	 * 
	 * @return {@link AnnotationsService}
	 */
	public AnnotationsService getAnnCtrl() {
		return annotationsService;
	}

	/**
	 * {@link AnnotationsService}
	 * @param annCtrl
	 */
	public void setAnnCtrl(AnnotationsService annCtrl) {
		this.annotationsService = annCtrl;
	}
	
	/**
	 * @throws Exception
	 * @throws IOException
	 */
	private void runGoldStandardJob() throws Exception, IOException {
		/*
		 * Get the annotations
		 */
		writeToConsole("\n\nStarting Job...");
		retrieveAnnotations();

		/*
		 * Write the parsed results to the database
		 */
		writeToConsole("\n\nSaving annotations to MySQL Database...");
		annotationsService.saveAnnotationsToDatabase(annotations);

		/*
		 * Write the annotations to the TREC qrel files
		 */
		writeToConsole("\n\nSaving annotations to TREC file...");
		annotationsService.saveAnnotationsToTRECGoldStandard(annotations, outputPath);
	}

	/**
	 * 
	 * @param msg
	 */
	private void writeToConsole(String msg) {
		msg = ">>> " + msg + "\n";
		System.out.println(msg);
	}

}
