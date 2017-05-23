package de.mbrero.see.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import de.mbrero.see.persistance.dto.Annotation;

public class cTakesParser implements IParser {

	protected File sourceTextFile;
	private String umlsInformationTag = "";
	protected HashMap<String, Annotation> annotations;

	@Override
	public void read(File file) throws FileNotFoundException {

		if (file.exists())
			this.setSourceTextFile(file);
		else
			throw new FileNotFoundException("No textfile found!");

	}

	@Override
	public void parse() {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the sourceTextFile
	 */
	public File getSourceTextFile() {
		return sourceTextFile;
	}

	/**
	 * @param sourceTextFile
	 *            the sourceTextFile to set
	 */
	public void setSourceTextFile(File sourceTextFile) {
		this.sourceTextFile = sourceTextFile;
	}

	/**
	 * @return the umlsInformationTag
	 */
	public String getUmlsInformationTag() {
		return umlsInformationTag;
	}

	/**
	 * @param umlsInformationTag
	 *            the umlsInformationTag to set
	 */
	public void setUmlsInformationTag(String umlsInformationTag) {
		this.umlsInformationTag = umlsInformationTag;
	}

}
