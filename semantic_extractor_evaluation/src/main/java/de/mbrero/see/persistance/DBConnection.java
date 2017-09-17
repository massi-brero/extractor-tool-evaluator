package de.mbrero.see.persistance;

import java.net.URL;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBConnection {
	private Configuration config = null;
	private Session session = null;
	private URL standardConfigUrl;
	private static SessionFactory factory = null;

	public DBConnection() 
	{
		this.standardConfigUrl = getClass().getClassLoader().getResource("hibernate.cfg.xml");
		this.init();
	}
	
	public DBConnection(URL url) throws HibernateException
	{
		this.config = new Configuration();
		this.config.configure(url);		
		this.init();
	}
	
	/*
	 * Sets up the configuration and session.
	 */
	private void init() throws HibernateException
	{
		if (this.config == null)
		{
			this.config = new Configuration();
			this.config.configure(standardConfigUrl);					
		}
	}
	
	/**
	 * Provides a new session. Should be closed as soon as possible to be 
	 * returned to the pool.
	 * @return
	 */
	public Session getNewSession()
	{
		factory = this.config.buildSessionFactory();
		session = factory.openSession();
		session.setFlushMode(FlushMode.COMMIT);
		factory.close();
		
		return session;
	}
	
	
}
