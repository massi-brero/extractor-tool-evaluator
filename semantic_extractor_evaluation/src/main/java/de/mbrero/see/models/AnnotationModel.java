package de.mbrero.see.models;

import java.util.List;

import de.mbrero.see.persistance.dao.Repository;
import de.mbrero.see.persistance.dto.Article;

/**
 * Model 
 * @author massi.brero@gmail.com
 *
 */
public class AnnotationModel implements IModelPersistance<Article> {

	private Repository<Article> repository;

	public AnnotationModel() {
		this.repository = new Repository<>(Article.class);
	}
	
	@Override
	public Article get(int id) {
		return repository.get(id);
	}

	@Override
	public List<Article> getAll() {
		return repository.getAll();
	}

	@Override
	public void save(Article item) {
		repository.save(item);
	}

	@Override
	public void delete(int id) {
		repository.delete(id);
	}

	@Override
	public void delete(Article item) {
		repository.delete(item);
	}

	@Override
	public void update(Article item) {
		repository.update(item);
	}

	/**
	 * Getter
	 * @return {@link Repository}
	 */
	public Repository<Article> getRepository() {
		return repository;
	}

	/**
	 * Getter
	 * @return {@link Repository}
	 */
	public void setRepository(Repository<Article> repository) {
		this.repository = repository;
	}
	
	

}
