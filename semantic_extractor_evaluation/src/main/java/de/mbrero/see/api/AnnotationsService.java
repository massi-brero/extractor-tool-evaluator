package de.mbrero.see.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import de.mbrero.see.models.DBAnnotationModel;
import de.mbrero.see.models.TRECGoldstandardModel;
import de.mbrero.see.models.TRECResultModel;
import de.mbrero.see.persistance.dto.Annotation;

/**
 * This class is one oof the main components of this' modules API.
 * It exposes the methods to save annotations retrieved by one of the parsers.
 * You may save single annotations, annotations sets from files or sets of
 * annotations sets gotten from a folder with different articles.
 * 
 * The annotations can be saved to the annotations table in the MySQL database or
 * as a TREC result file.
 * 
 * @author massi
 *
 */
public class AnnotationsService {

	private TRECGoldstandardModel gsTrecModel = null;
	private TRECResultModel resultTrecModel = null;
	private	DBAnnotationModel dbModel = null;
	
	/**
	 * 
	 */
	public AnnotationsService() {
		gsTrecModel = new TRECGoldstandardModel();
		resultTrecModel = new TRECResultModel();
		dbModel = new DBAnnotationModel();
	}
	
	/**
	 * 
	 * @param annotation
	 */
	public void saveAnnotationsToDatabase(Annotation annotation) {
		dbModel.saveEntity(annotation);
	}
	
	/**
	 * 
	 * @param annotations
	 */
	public void saveAnnotationsToDatabase(ArrayList<Annotation> annotations) {
		dbModel.saveEntityList(annotations);
	}
	
	/**
	 * 
	 * @param annotations
	 */
	public void saveAnnotationsToDatabase(HashMap<String, HashMap<String, Annotation>> annotations)
	{
		dbModel.saveEntitiesInCorpus(annotations);
	}
	
	/**
	 * 
	 * @param annotation
	 * @throws IOException
	 */
	public void saveAnnotationsToTRECResultUsingCUI(Annotation annotation,
			File outputPath) throws IOException {
		resultTrecModel.setResultFile(outputPath);
		resultTrecModel.saveEntity(annotation);
	}
	
	/**
	 * 
	 * @param annotations
	 * @throws IOException
	 */
	public void saveAnnotationsToTRECResultUsingCUI(ArrayList<Annotation> annotations,
			File outputPath) throws IOException {
		resultTrecModel.setResultFile(outputPath);
		resultTrecModel.saveEntityList(annotations);
	}
	
	/**
	 * 
	 * @param annotations
	 * @throws IOException
	 */
	public void saveAnnotationsToTRECResultUsingCUI(HashMap<String, HashMap<String, Annotation>> annotations,
			File outputPath) throws IOException {
		resultTrecModel.setResultFile(outputPath);
		resultTrecModel.saveEntitiesInCorpus(annotations);
	}
	
	/**
	 * 
	 * @param annotation
	 * @param outputPath
	 * @throws IOException
	 */
	public void saveAnnotationsToTRECGoldStandard(Annotation annotation,
			File outputPath) throws IOException {
		gsTrecModel.setResultFile(outputPath);
		gsTrecModel.saveEntity(annotation);
	}
	
	/**
	 * 
	 * @param annotations
	 * @param outputPath
	 * @throws IOException
	 */
	public void saveAnnotationsToTRECGoldStandard(ArrayList<Annotation> annotations,
			File outputPath) throws IOException {
		gsTrecModel.setResultFile(outputPath);
		gsTrecModel.saveEntityList(annotations);
	}
	
	/**
	 * 
	 * @param annotations
	 * @param outputPath
	 * @throws IOException
	 */
	public void saveAnnotationsToTRECGoldStandard(HashMap<String, HashMap<String, Annotation>> annotations, 
			File outputPath) throws IOException {
		gsTrecModel.setResultFile(outputPath);
		gsTrecModel.saveEntitiesInCorpus(annotations);
	}
	
	
}
