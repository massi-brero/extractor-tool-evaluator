package de.mbrero.see.models;

import java.util.List;

import de.mbrero.see.persistance.dao.Repository;
import de.mbrero.see.persistance.dto.Annotation;
import de.mbrero.see.persistance.dto.Article;

public class ArticleModel implements IModelPersistance<Annotation> {

	private Repository<Annotation> repository;

	public ArticleModel() {
		this.repository = new Repository<>(Annotation.class);
	}
	
	@Override
	public Annotation get(int id) {
		return repository.get(id);
	}

	@Override
	public List<Annotation> getAll() {
		return repository.getAll();
	}

	@Override
	public void save(Annotation item) {
		repository.save(item);
	}

	@Override
	public void delete(int id) {
		repository.delete(id);
	}

	@Override
	public void delete(Annotation item) {
		repository.delete(item);
	}

	@Override
	public void update(Annotation item) {
		repository.update(item);
	}

	public Repository<Annotation> getRepository() {
		return repository;
	}

	public void setRepository(Repository<Annotation> repository) {
		this.repository = repository;
	}
	
	

}
