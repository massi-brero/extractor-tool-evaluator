package de.mbrero.see.persistance.dao;

import java.util.List;

/**
 * Generic interface for CRUD Operations on databases.
 * 
 * @author massimiliano.brero@studium.fernuni-hagen.de
 *
 * @param <T>
 */
public interface IRepository<T> {

	/**
	 * Get entity
	 * @param T id
	 * @return
	 */
	public T get(int id);
	
	/**
	 * Get all entities
	 * @return {@link List}
	 */
	public List<T> getAll();
	
	/**
	 * Save entity
	 * @param T item
	 */
	public void save(T item);
	
	/**
	 * Delete a single entity by the id.
	 * @param int id
	 */
	public void delete(int id);
	
	/**
	 * Delete entity item.
	 * @param T item
	 */
	public void delete(T item);

	/**
	 * Update entity.
	 * @param T item
	 */
	public void update(T item);
	
	
}
