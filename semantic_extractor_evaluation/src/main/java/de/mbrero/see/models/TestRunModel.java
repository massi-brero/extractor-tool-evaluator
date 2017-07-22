package de.mbrero.see.models;

import java.util.Date;
import java.util.List;

import de.mbrero.see.persistance.dao.Repository;
import de.mbrero.see.persistance.dto.TestRun;

public class TestRunModel implements IModelPersistance<TestRun> {

	private Repository<TestRun> repository;
	
	public TestRunModel() {
		this.repository = new Repository<>(TestRun.class);
	}

	@Override
	public TestRun get(int id) {
		return repository.get(id);
	}

	@Override
	public List<TestRun> getAll() {
		return repository.getAll();
	}

	@Override
	public void save(TestRun item) {
		if (!item.getPath().isEmpty()) {
			
			if (item.getDate() == null)
				item.setDate(new Date());

			repository.save(item);
		}

	}

	@Override
	public void delete(int id) {
		repository.delete(id);
	}

	@Override
	public void delete(TestRun item) {
		repository.delete(item);
	}

	@Override
	public void update(TestRun item) {
		repository.update(item);
	}

	public Repository<TestRun> getRepository() {
		return repository;
	}

	public void setRepository(Repository<TestRun> repository) {
		this.repository = repository;
	}

}
