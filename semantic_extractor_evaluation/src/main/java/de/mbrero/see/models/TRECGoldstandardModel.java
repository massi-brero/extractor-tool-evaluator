package de.mbrero.see.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.mbrero.see.persistance.dto.Annotation;

/**
 * Saves annotation to a TREC qrel file for goldstandards with a fixed line structure.
 * The CRAFT gold standard uses the UMLS CUI as concept identifier.
 * 
 * @author massimiliano.brero@studium.fernuni-hagen.de
 *
 */
public class TRECGoldstandardModel implements IEntityWriter<Annotation> {
	
	File resultFile = null;
	/**
	 * Format of qrel line... has a fixed structure
	 */
	private String trecLine = "%s 0 %s 1".concat(System.getProperty("line.separator"));

	/**
	 * (NO parameter) constructor
	 */
	public TRECGoldstandardModel() {
	}

	/**
	 * Constructor that sets where the results from the parsing process of the goldstandard texts<br>
	 * have to be saved as TREC goldstandard file named "qrel".
	 * @param resultFile
	 */
	public TRECGoldstandardModel(File resultFile) {
		this();
		setResultFile(resultFile);
	}

	@Override
	public void saveEntity(Annotation annotation) throws IOException {

		if (getResultFile() != null) {
			
			for (int idx = 0; idx < annotation.getCount(); idx++) {
				String line = String.format(trecLine, 
						annotation.getDocumentID(), annotation.getCui() + "_" + idx);

				Files.write(Paths.get(getResultFile().getPath()), line.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);				
			}

		} else {
			throw new FileNotFoundException();
		}

	}

	@Override
	public void saveEntityList(ArrayList<Annotation> annotations) throws IOException {
		
		for(Annotation annotation : annotations) {
			saveEntity(annotation);
		}

	}

	@Override
	public void saveEntitiesInCorpus(HashMap<String, HashMap<String, Annotation>> annotations) throws IOException {

		for (Map.Entry<String, HashMap<String, Annotation>> entry : annotations.entrySet()) {
			saveEntityList(new ArrayList<Annotation>((entry.getValue().values())));
		}
	}

	/**
	 * @return the resultFile
	 */
	public File getResultFile() {
		return resultFile;
	}

	/**
	 * @param resultFile
	 *            the resultFile to set
	 */
	public void setResultFile(File resultFile) {
		this.resultFile = resultFile;
	}


}
