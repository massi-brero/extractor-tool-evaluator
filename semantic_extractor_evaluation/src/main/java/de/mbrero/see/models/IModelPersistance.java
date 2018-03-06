package de.mbrero.see.models;

import java.util.List;
/**
 * Generic interface defining public class methods for all database operations of a given entity.
 * 
 * @author massi@gmail.com
 *
 * @param <T> generiy type
 */
public interface IModelPersistance<T> {
	
	/**
	 * Get the entoty's id.
	 * 
	 * @param int id
	 * @return
	 */
	public T get(int id);
	
	/**
	 * Get all entities stored in the table.
	 * 
	 * @return T
	 */
	public List<T> getAll();
	
	/**
	 * Save entity into table.
	 * @param T item
	 */
	public void save(T item);
	
	/**
	 * Delete  entity with given id.
	 * 
	 * @param int id
	 */
	public void delete(int id);
	
	/**
	 * Delete item from the database as row.
	 * @param T item
	 */
	public void delete(T item);
	
	/**
	 * Update given item.
	 * 
	 * @param T item
	 */
	public void update(T item);

}
