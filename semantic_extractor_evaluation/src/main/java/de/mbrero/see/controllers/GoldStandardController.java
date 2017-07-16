package de.mbrero.see.controllers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import de.mbrero.see.parser.CRAFTParser;
import de.mbrero.see.parser.ParserFactory;
import de.mbrero.see.persistance.dto.Annotation;
import types.GoldStandardType;
import types.ParserType;

/**
 * Parses the Goldstandard and saves the annotations to the satabase and creates
 * a qrel File for the TREK tool
 * 
 * @author massi.brero@gmail.com
 *
 */
public class GoldStandardController {

	private GoldStandardType type = null;
	private File inputPath = new File("");
	private File outputPath = new File("");
	private HashMap<String, HashMap<String, Annotation>>  annotations = new HashMap<>();
	private AnnotationsController annCtrl;

	public GoldStandardController(GoldStandardType type, File input, File output) {
		setType(type);
		setInputPath(input);
		setOutputPath(output);
		annCtrl = new AnnotationsController();
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
			
			switch (type) {
				case CRAFT:
				default:
					System.out.println("Starting Job\n\n");
					retrieveAnnotations();
					writeToConsole("Starting Job...");
					//annCtrl.saveAnnotationsToDatabase(annotations);
					//writeToConsole("Saving annotations MySQL Database...");
					annCtrl.saveAnnotationsToTRECGoldStandard(annotations, outputPath);
					writeToConsole("Saving annotations to TREC file...");
					break;
			}

	}

	public void retrieveAnnotations() throws Exception {
		CRAFTParser crParser = (CRAFTParser)ParserFactory.getInstance(ParserType.CRAFT);
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

	public AnnotationsController getAnnCtrl() {
		return annCtrl;
	}

	public void setAnnCtrl(AnnotationsController annCtrl) {
		this.annCtrl = annCtrl;
	}

	private void writeToConsole(String msg) {
		msg = ">>> " + msg + "\n";
		System.out.println(msg);
	}

}
