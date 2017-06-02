package de.mbrero.see.writer;

import java.net.URL;
import java.util.HashMap;

import de.mbrero.see.persistance.DBConnection;
import de.mbrero.see.persistance.dao.Repository;
import de.mbrero.see.persistance.dto.Annotation;

/**
 * Saves annotations to a database table defined by the {@link Annotation} bean.
 * 
 * @author massi.brero@gmail.com
 *
 */
public class DBAnnotationWriter implements IEntityWriter<Annotation> {
	private Repository<Annotation> repo;
	private final String HBM_CONFIG_PATH = "hibernate.cfg.xml";
	private DBConnection conn;
	
	public DBAnnotationWriter() {
		init();
	}
	
	
	public void init() {
		repo = new Repository<>(Annotation.class, conn);
		
		URL url = getClass().getClassLoader().getResource(HBM_CONFIG_PATH);
		conn = new DBConnection(url);
	}



	@Override
	public void saveEntity(Annotation annotation) {
		repo.save(annotation);
	}

	@Override
	public void saveEntitiesInDocument(HashMap<String, Annotation> annotations) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveEntityInCorpus(HashMap<String, HashMap<String, Annotation>> annotations) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * @return the repo
	 */
	public Repository<Annotation> getRepo() {
		return repo;
	}


	/**
	 * @param repo the repo to set
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
	 * @param conn the conn to set
	 */
	public void setConn(DBConnection conn) {
		this.conn = conn;
	}

}
