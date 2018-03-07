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
 * Saves annotation to a TREC result file with a fixed line structure.
 * The result file is per default named like the text file it originates from.
 * 
 * @author massimiliano.brero@studium.fernuni-hagen.de
 *
 */
public class TRECResultModel implements IEntityWriter<Annotation> {
	File resultFile = null;
	/**
	 * Format of qrel line... has a fixed structure
	 */
	private String trecLine = "%s 0 %s 0 0 0".concat(System.getProperty("line.separator"));
	private boolean useSourceId;

	/*
	 * (no parameter) constructor
	 */
	public TRECResultModel() {
	}

	/**
	 * Constructor that is given the file the results have to be stored in the TREC result format.
	 * 
	 * @param resultFile
	 */
	public TRECResultModel(File resultFile) {
		this();
		setResultFile(resultFile);
		setUseSourceId(false);
	}

	@Override
	public void saveEntity(Annotation annotation) throws IOException {

		if (getResultFile() != null) {
			String id = useSourceId() ? annotation.getSourceConceptId() : annotation.getCui();
			
			for (int idx = 0; idx < annotation.getCount(); idx++) {
				String line = String.format(trecLine, 
						annotation.getDocumentID(), id + "_" + idx);
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

	/**
	 * Has the original concept id from the ontology to be used.
	 * @return
	 */
	public boolean useSourceId() {
		return useSourceId;
	}

	/**
	 * Set if the concept ids from the ontologies shall be used.
	 * @param useSourceId
	 */
	public void setUseSourceId(boolean useSourceId) {
		this.useSourceId = useSourceId;
	}

}
