package de.mbrero.see.models.bo;

import java.util.List;

public interface IModelPersistance<T> {
	
	public T get(int id);
	
	public List<T> getAll();
	
	public void save(T item);
	
	public void delete(int id);
	
	public void delete(T item);
	
	public void update(T item);

}
