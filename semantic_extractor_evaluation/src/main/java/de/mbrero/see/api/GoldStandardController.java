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
 * Parses the Goldstandard and saves the annotations to the satabase and creates
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

	public void retrieveAnnotations() throws Exception {
		CRAFTParser crParser = (CRAFTParser) ParserFactory.getInstance(ParserType.CRAFT);
		crParser.parse(getInputPath());
		annotations = crParser.getAnnotations();
	}

	public GoldStandardType getType() {
		return type;
	}

	public void setType(GoldStandardType type) {
		this.type = type;
	}

	public File getInputPath() {
		return inputPath;
	}

	public void setInputPath(File inputPath) {
		this.inputPath = inputPath;
	}

	public File getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(File outputPath) {
		this.outputPath = outputPath;
	}

	public HashMap<String, HashMap<String, Annotation>> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(HashMap<String, HashMap<String, Annotation>> annotations) {
		this.annotations = annotations;
	}

	public AnnotationsService getAnnCtrl() {
		return annotationsService;
	}

	public void setAnnCtrl(AnnotationsService annCtrl) {
		this.annotationsService = annCtrl;
	}
	
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

	private void writeToConsole(String msg) {
		msg = ">>> " + msg + "\n";
		System.out.println(msg);
	}

}
