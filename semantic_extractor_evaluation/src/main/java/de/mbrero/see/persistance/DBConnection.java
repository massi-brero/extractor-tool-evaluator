package de.mbrero.see.persistance;

import java.net.URL;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.impl.SessionFactoryImpl;

/**
 * This class manages the connection to the MySQL database.
 * Uses Hibernate @see http://hibernate.org/
 * 
 * @author massimiliano.brero@studium.fernuni-hagen.de
 *
 */
public class DBConnection {
	private Configuration config = null;
	private Session session = null;
	private static SessionFactory factory = null;
	private URL standardConfigUrl;

	/**
	 * (no parameter) constructor
	 */
	public DBConnection() {
		this.standardConfigUrl = getClass().getClassLoader().getResource("hibernate.cfg.xml");
		this.init();
	}

	/*
	 * Constructor with path to Hibernate configuration.
	 */
	public DBConnection(URL url) throws HibernateException {
		this.config = new Configuration();
		this.config.configure(url);
		this.init();
	}

	/**
	 * Provides a new session. Should be closed as soon as possible to be
	 * returned to the pool.
	 * 
	 * @return
	 */
	public Session getNewSession() {
		if (factory == null)
			factory = this.config.buildSessionFactory();
		session = factory.openSession();
		session.clear();
		session.setFlushMode(FlushMode.COMMIT);
		factory.evictQueries();

		return session;
	}

	/*
	 * Sets up the configuration and session.
	 */
	private void init() throws HibernateException {
		if (this.config == null) {
			this.config = new Configuration();
			this.config.configure(standardConfigUrl);
		}
	}

	/**
	 * Close the connection to the database, e. g. when shutting down the application.
	 */
	public static void closeDBConnection() {
		if (factory != null && factory instanceof SessionFactoryImpl) {
			SessionFactoryImpl sf = (SessionFactoryImpl) factory;
			ConnectionProvider conn = sf.getConnectionProvider();
			conn.close();
			factory.close();
		}
	}

}
