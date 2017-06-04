package de.mbrero.see.models;

import java.util.ArrayList;
import java.util.HashMap;

import de.mbrero.see.persistance.dto.Annotation;

/**
 * Very generic interface (pun intended) to save entities like Annotations and the like.
 * Provides to API to either conveniently single entities, entities in documents or an entire corpus.
 * @author massi.brero@gmail.com
 *
 * @param <T>
 */
public interface IEntityWriter<T> {
	
	public void saveEntity(T annotations) throws Exception;
	public void saveEntityList(ArrayList<Annotation> annotations) throws Exception;
	public void saveEntitiesInCorpus(HashMap<String, HashMap<String, T>>  annotations) throws Exception;

}
