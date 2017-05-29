package de.mbrero.see.persistance.dao;

import java.util.List;

/**
 * Interface for CRUD Operations on databases.
 * 
 * @author massi.brero@gmail.com
 *
 * @param <T>
 */
public interface IRepository<T> {

	public T get(int id);
	
	public List<T> getAll();
	
	public void save(T item);
	
	public void delete(int id);
	
	public void delete(T item);

	public void update(T item);
	
	
}
