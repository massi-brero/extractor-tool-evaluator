package de.mbrero.see.controllers;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.TypeConstraintException;

import types.GoldStandardType;

/**
 * Parses the Goldstandard and saves the annotations to the satabase and creates
 * a qrel File for the TREK tool
 * 
 * @author massi.brero@gmail.com
 *
 */
public class GoldStandardController {

	public GoldStandardType type = null;
	public File path = new File("");

	public GoldStandardController(GoldStandardType type, File path) {
		setType(type);
		setPath(path);
	}

	/**
	 * Manages complete Job. Extracts the annotations from the files in the
	 * given path and saves them to the database and creates a qrel File for the
	 * TREC tool evaluation.
	 * 
	 * @param type
	 * @throws FileNotFoundException 
	 */
	public void persistGoldStanstandard() throws FileNotFoundException {

		if (type == null)
			throw new IllegalArgumentException("Not a valid goldstandard type");
			
			if (!path.exists())
				throw new FileNotFoundException();
			
			switch (type) {
				case CRAFT:
				default:
					System.out.println("Starting Job");
					break;
			}


	}

	public void getAnnotations() {

	}

	public void saveAnnotationsToDataBase() {

	}

	public void saveAnnotationToTRECFile() {

	}

	/**
	 * @return the type
	 */
	public GoldStandardType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(GoldStandardType type) {
		this.type = type;
	}

	/**
	 * @return the path
	 */
	public File getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(File path) {
		this.path = path;
	}
}
