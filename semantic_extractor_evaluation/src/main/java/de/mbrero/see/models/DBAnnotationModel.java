package de.mbrero.see.models;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import de.mbrero.see.controllers.TestRunController;
import de.mbrero.see.persistance.DBConnection;
import de.mbrero.see.persistance.dao.Repository;
import de.mbrero.see.persistance.dto.Annotation;

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

	public DBAnnotationModel() {
		init();
	}

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
		for (Annotation annotation : annotations) {
			annotation.setTestRunId(TestRunController.testRunId);
			saveEntity(annotation);
		}

	}

	@Override
	@SuppressWarnings("unchecked")
	public void saveEntitiesInCorpus(HashMap<String, HashMap<String, Annotation>> allAnnotations) {
		
		Iterator it = allAnnotations.entrySet().iterator();
		
		while (it.hasNext()) {
			Map.Entry<String, HashMap<String, Annotation>> entry = (Map.Entry<String, HashMap<String, Annotation>>) it.next();
			HashMap<String, Annotation> foo = entry.getValue();
			ArrayList<Annotation> annList = new ArrayList<Annotation>((entry.getValue().values()));
			saveEntityList(annList);
		}
	}

	/**
	 * @return the repo
	 */
	public Repository<Annotation> getRepo() {
		return repo;
	}

	/**
	 * @param repo
	 *            the repo to set
	 */
	public void setRepo(Repository<Annotation> repo) {
		this.repo = repo;
	}

	/**
	 * @return the conn
	 */
	public DBConnection getConn() {
		return conn;
	}

	/**
	 * @param conn
	 *            the conn to set
	 */
	public void setConn(DBConnection conn) {
		this.conn = conn;
	}

}
