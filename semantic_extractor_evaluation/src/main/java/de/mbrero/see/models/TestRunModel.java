package de.mbrero.see.models;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.mbrero.see.persistance.dao.Repository;
import de.mbrero.see.persistance.dto.TestRun;

public class TestRunModel implements IModelPersistance<TestRun> {

	private Repository<TestRun> repository;
	private HashMap<String, String>system = new HashMap<>();
	
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
		if (!item.getInputPath().isEmpty()) {
			
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
	
	public HashMap<String, String> getSystemInformation()
	{
		
		system.put("available_processors", String.valueOf(Runtime.getRuntime().availableProcessors()));
		
		system.put("free_memory", String.valueOf(Runtime.getRuntime().freeMemory()));
		
		long maxMemory = Runtime.getRuntime().maxMemory();
		
		system.put("max_memory_for_jvm", String.valueOf(maxMemory == Long.MAX_VALUE ? 0 : maxMemory));
		
		system.put("total_memory_for_jvm", String.valueOf((Runtime.getRuntime().totalMemory())));
		
		system.put("os", String.valueOf(System.getProperty("os.name")));
		
		system.put("Java_version", String.valueOf(System.getProperty("java.version")));
		
		system.put("jvm_version", String.valueOf(System.getProperty("java.vm.version")));
		
		system.put("java_vendor", String.valueOf(System.getProperty("java.vendor")));
		
		//System.getProperties().list(System.out);
		    
		return system;
	}

}
