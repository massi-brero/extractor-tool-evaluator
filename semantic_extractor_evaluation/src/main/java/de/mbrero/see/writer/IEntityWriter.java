package de.mbrero.see.writer;

import java.util.HashMap;

/**
 * Very generic interface (pun intended) to save entities like Annotations and the like.
 * Provides to API to either conveniantelly single entities, entities in documents or an entite corpus.
 * @author massi.brero@gmail.com
 *
 * @param <T>
 */
public interface IEntityWriter<T> {
	
	public void saveEntity(T annotations);
	public void saveEntitiesInDocument(HashMap<String, T> annotations);
	public void saveEntityInCorpus(HashMap<String, HashMap<String, T>>  annotations);

}
