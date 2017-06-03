package de.mbrero.see.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import de.mbrero.see.persistance.dto.Annotation;

/**
 * Saves annotation to a database table defined by the annotate bean.
 * @author massi.brero@gmail.com
 *
 */
public class TRECResultModel implements IEntityWriter<Annotation> {
	File resultFile = null;

	public TRECResultModel() {
	}
	
	public TRECResultModel(File resultFile) {
		this();
		setResultFile(resultFile);
	}

	@Override
	public void saveEntity(Annotation annotations) {
		try {
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void saveEntityList(ArrayList<Annotation> annotations) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveEntityInCorpus(HashMap<String, HashMap<String, Annotation>> annotations) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return the resultFile
	 */
	public File getResultFile() {
		return resultFile;
	}

	/**
	 * @param resultFile the resultFile to set
	 */
	public void setResultFile(File resultFile) {
		this.resultFile = resultFile;
	}



}
