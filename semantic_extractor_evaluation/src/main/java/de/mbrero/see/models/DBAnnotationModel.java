package de.mbrero.see.models;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import de.mbrero.see.api.TestRunController;
import de.mbrero.see.persistance.DBConnection;
import de.mbrero.see.persistance.dao.Repository;
import de.mbrero.see.persistance.dto.Annotation;
import util.ProgressBar;

/**
 * Saves annotations to a database table defined by the {@link Annotation} bean.
 * 
 * @author massi.brero@gmail.com
 *
 */
public class DBAnnotationModel implements IEntityWriter<Annotation> {
	private Repository<Annotation> repo;
	private final String HBM_CONFIG_PATH = "hibernate.cfg.xml";
	private DBConnection conn;

	/**
	 * Constructor
	 */
	public DBAnnotationModel() {
		init();
	}

	/**
	 * Initialize connection and instantiate repository.
	 */
	public void init() {
		URL url = getClass().getClassLoader().getResource(HBM_CONFIG_PATH);
		conn = new DBConnection(url);
		repo = new Repository<>(Annotation.class, conn);
	}

	@Override
	public void saveEntity(Annotation annotation) {
		repo.save(annotation);
	}

	@Override
	public void saveEntityList(ArrayList<Annotation> annotations) {
		int count = 0;
		ProgressBar progressBar = new ProgressBar();
		
		for (Annotation annotation : annotations) {
			
			progressBar.showProgress(annotations.size(), count, 10);
			annotation.setTestRunId(TestRunController.testRunId);
			saveEntity(annotation);
			
			count++;
		}

	}

	@Override
	@SuppressWarnings("unchecked")
	public void saveEntitiesInCorpus(HashMap<String, HashMap<String, Annotation>> allAnnotations) {
		
		Iterator<?> it = allAnnotations.entrySet().iterator();
		
		while (it.hasNext()) {
			Map.Entry<String, HashMap<String, Annotation>> entry = (Map.Entry<String, HashMap<String, Annotation>>) it.next();
			
			System.out.println("\n".concat(entry.getKey()));
			
			ArrayList<Annotation> annList = new ArrayList<Annotation>((entry.getValue().values()));
			saveEntityList(annList);
		}
	}

	/**
	 * @return {@link Repository<Annotation>} the repository
	 */
	public Repository<Annotation> getRepo() {
		return repo;
	}

	/**
	 * @param {@link Repository<Annotation>} repo
	 *            the repo to set
	 */
	public void setRepo(Repository<Annotation> repo) {
		this.repo = repo;
	}

	/**
	 * @return {@link DBConnection} the connection to the database
	 */
	public DBConnection getConn() {
		return conn;
	}

	/**
	 * @param {@link DBConnection} conn
	 *            the database connection to set
	 */
	public void setConn(DBConnection conn) {
		this.conn = conn;
	}

}
