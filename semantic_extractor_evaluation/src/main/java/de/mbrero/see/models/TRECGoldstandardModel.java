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
 * Saves annotation to a database table defined by the annotate bean.
 * The CRAFT gold standard uses the UMLS CUI as concept identifier.
 * 
 * @author massi.brero@gmail.com
 *
 */
public class TRECGoldstandardModel implements IEntityWriter<Annotation> {
	
	File resultFile = null;
	private String trecLine = "%s 0 CUI%s 1".concat(System.getProperty("line.separator"));

	public TRECGoldstandardModel() {
	}

	public TRECGoldstandardModel(File resultFile) {
		this();
		setResultFile(resultFile);
	}

	@Override
	public void saveEntity(Annotation annotation) throws IOException {

		if (getResultFile() != null) {
			
			for (int idx = 0; idx < annotation.getCount(); idx++) {
				String line = String.format(trecLine, 
						annotation.getDocumentID(), annotation.getSourceConceptId() + "_" + idx);

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
