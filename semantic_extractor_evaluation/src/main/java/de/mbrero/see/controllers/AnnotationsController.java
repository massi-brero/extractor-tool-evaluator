package de.mbrero.see.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import de.mbrero.see.models.DBAnnotationModel;
import de.mbrero.see.models.TRECGoldstandardModel;
import de.mbrero.see.models.TRECResultModel;
import de.mbrero.see.persistance.dto.Annotation;

public class AnnotationsController {

	private TRECGoldstandardModel gsTrecModel = null;
	private TRECResultModel resultTrecModel = null;
	private	DBAnnotationModel dbModel = null;
	
	public AnnotationsController() {
		gsTrecModel = new TRECGoldstandardModel();
		resultTrecModel = new TRECResultModel();
		dbModel = new DBAnnotationModel();
	}
	
	public void saveAnnotationsToDatabase(Annotation annotation) {
		dbModel.saveEntity(annotation);
	}
	
	public void saveAnnotationsToDatabase(ArrayList<Annotation> annotations) {
		dbModel.saveEntityList(annotations);
	}
	
	public void saveAnnotationsToDatabase(HashMap<String, HashMap<String, Annotation>> annotations)
	{
		dbModel.saveEntitiesInCorpus(annotations);
	}
	
	public void saveAnnotationsToTRECResult(Annotation annotation) throws IOException {
		resultTrecModel.saveEntity(annotation);
	}
	
	public void saveAnnotationsToTRECResult(ArrayList<Annotation> annotations) throws IOException {
		resultTrecModel.saveEntityList(annotations);
	}
	
	public void saveAnnotationsToTRECResult(HashMap<String, HashMap<String, Annotation>> annotations) throws IOException {
		resultTrecModel.saveEntitiesInCorpus(annotations);
	}
	
	public void saveAnnotationsToTRECGoldStandard(Annotation annotation,
			File outputPath) throws IOException {
		gsTrecModel.setResultFile(outputPath);
		gsTrecModel.saveEntity(annotation);
	}
	
	public void saveAnnotationsToTRECGoldStandard(ArrayList<Annotation> annotations,
			File outputPath) throws IOException {
		gsTrecModel.setResultFile(outputPath);
		gsTrecModel.saveEntityList(annotations);
	}
	
	public void saveAnnotationsToTRECGoldStandard(HashMap<String, HashMap<String, Annotation>> annotations, 
			File outputPath) throws IOException {
		gsTrecModel.setResultFile(outputPath);
		gsTrecModel.saveEntitiesInCorpus(annotations);
	}
	
	
}
