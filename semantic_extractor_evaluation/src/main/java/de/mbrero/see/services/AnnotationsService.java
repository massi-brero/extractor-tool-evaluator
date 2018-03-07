package de.mbrero.see.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import de.mbrero.see.models.DBAnnotationModel;
import de.mbrero.see.models.TRECGoldstandardModel;
import de.mbrero.see.models.TRECResultModel;
import de.mbrero.see.persistance.dto.Annotation;

/**
 * This class is one of the main components of this' modules API.
 * It exposes the methods to save annotations retrieved by one of the parsers.
 * You may save single annotations, annotations sets from files or sets of
 * annotations sets gotten from a folder with different articles.
 * 
 * The annotations can be saved to the annotations table in the MySQL database or
 * as a TREC result file.
 * 
 * @author massi.brero@gmail.com
 *
 */
public class AnnotationsService {

	private TRECGoldstandardModel gsTrecModel = null;
	private TRECResultModel resultTrecModel = null;
	private	DBAnnotationModel dbModel = null;
	
	/**
	 * No parameter constructor
	 */
	public AnnotationsService() {
		gsTrecModel = new TRECGoldstandardModel();
		resultTrecModel = new TRECResultModel();
		dbModel = new DBAnnotationModel();
	}
	
	/**
	 * Save a single annotation to the database table.
	 * @param {@link Annotation} annotation
	 */
	public void saveAnnotationsToDatabase(Annotation annotation) {
		dbModel.saveEntity(annotation);
	}
	
	/**
	 * Save a annotations list to the database table. Used to store the annotations extracted<br>
	 * from a single text.
	 * @param {@link ArrayList<Annotation>} annotations
	 */
	public void saveAnnotationsToDatabase(ArrayList<Annotation> annotations) {
		dbModel.saveEntityList(annotations);
	}
	
	/**
	 * Save a map of annotations list to the database table. Used to store the annotations extracted<br>
	 * from a multiple texts, e. g. from a goldstandard corpus.<br>
	 * The key/value pairs in the map are meant like this:<br>
	 * key: {name-of-text} --> value: {list-of-annotations-from-that-text}
	 * 
	 * @param {@link HashMap<String, HashMap<String, Annotation>>} annotation
	 */
	public void saveAnnotationsToDatabase(HashMap<String, HashMap<String, Annotation>> annotations)
	{
		dbModel.saveEntitiesInCorpus(annotations);
	}
	
	/**
	 * Save a single annotation from a extractor run to a TREC result file.
	 * The annotation will be stored with the concept's UMLS id (CUI).
	 * @param {@link Annotation} annotation
	 * @throws IOException
	 */
	public void saveAnnotationsToTRECResultUsingCUI(Annotation annotation,
			File outputPath) throws IOException {
		resultTrecModel.setResultFile(outputPath);
		resultTrecModel.saveEntity(annotation);
	}
	
	/**
	 * Save a list of annotations from a extractor run to a TREC result file.<br>
	 * Use this for saving annotations of a single text.
	 * The annotations will be stored with the concepts' UMLS id (CUI).
	 * 
	 * @param {@link ArrayList<Annotation>} annotations
	 * @throws IOException
	 */
	public void saveAnnotationsToTRECResultUsingCUI(ArrayList<Annotation> annotations,
			File outputPath) throws IOException {
		resultTrecModel.setResultFile(outputPath);
		resultTrecModel.saveEntityList(annotations);
	}
	
	/**
	 * Save a map of annotations list to o a TREC result files. use this method to store all results<br>
	 * from a goldstandard corpus.
	 * The annotations will be stored with the concepts' UMLS id (CUI).<br>
	 * The key/value pairs in the map are meant like this:<br>
	 * key: {name-of-text} --> value: {list-of-annotations-from-that-text}
	 * 
	 * @param {@link HashMap<String, HashMap<String, Annotation>>} annotations
	 * @throws IOException
	 */
	public void saveAnnotationsToTRECResultUsingCUI(HashMap<String, HashMap<String, Annotation>> annotations,
			File outputPath) throws IOException {
		resultTrecModel.setResultFile(outputPath);
		resultTrecModel.saveEntitiesInCorpus(annotations);
	}
	
	/**
	 * Save a single annotation from a extractor run to a TREC goldstandrd file (qrel).
	 * 
	 * @param {@link Annotation} annotation
	 * @param outputPath
	 * @throws IOException
	 */
	public void saveAnnotationsToTRECGoldStandard(Annotation annotation,
			File outputPath) throws IOException {
		gsTrecModel.setResultFile(outputPath);
		gsTrecModel.saveEntity(annotation);
	}
	
	/**
	 * Save a list of annotations from a extractor run to a TREC goldstandard file (qrel).<br>
	 * Use this for saving annotations of a single text.
	 * 
	 * @param {@link ArrayList<Annotation>} annotations
	 * @param outputPath
	 * @throws IOException
	 */
	public void saveAnnotationsToTRECGoldStandard(ArrayList<Annotation> annotations,
			File outputPath) throws IOException {
		gsTrecModel.setResultFile(outputPath);
		gsTrecModel.saveEntityList(annotations);
	}
	
	/**
	 * Save a map of annotations list to o a TREC goldstandard file (qrel). use this method to store all results<br>
	 * from a goldstandard corpus.<br>
	 * The key/value pairs in the map are meant like this:<br>
	 * key: {name-of-text} --> value: {list-of-annotations-from-that-text}
	 * 
	 * @param {@link HashMap<String, HashMap<String, Annotation>>} annotations
	 * @param outputPath
	 * @throws IOException
	 */
	public void saveAnnotationsToTRECGoldStandard(HashMap<String, HashMap<String, Annotation>> annotations, 
			File outputPath) throws IOException {
		gsTrecModel.setResultFile(outputPath);
		gsTrecModel.saveEntitiesInCorpus(annotations);
	}
	
	
}
