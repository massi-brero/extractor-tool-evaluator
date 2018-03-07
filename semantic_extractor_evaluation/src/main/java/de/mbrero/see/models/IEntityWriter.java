package de.mbrero.see.models;

import java.util.ArrayList;
import java.util.HashMap;

import de.mbrero.see.persistance.dto.Annotation;

/**
 * Very generic interface to save entities like Annotations and the like.
 * Provides to API to either conveniently single entities, entities in documents or an entire corpus.
 * @author massimiliano.brero@studium.fernuni-hagen.de
 *
 * @param <T>
 */
public interface IEntityWriter<T> {
	
	/**
	 * Save a single entity of type T to a database.
	 * @param annotations
	 * @throws Exception
	 */
	public void saveEntity(T annotations) throws Exception;
	
	/**
	 * Save an ArrayList of entities to a database.
	 * @param {@link ArrayList<Annotation>} annotations
	 * @throws Exception
	 */
	public void saveEntityList(ArrayList<Annotation> annotations) throws Exception;
	
	/**
	 * Save all entities of all texts in an corpus to a database.
	 * 
	 * @param {@link HashMap<String, HashMap<String, T>> } annotations
	 * @throws Exception
	 */
	public void saveEntitiesInCorpus(HashMap<String, HashMap<String, T>>  annotations) throws Exception;

}
