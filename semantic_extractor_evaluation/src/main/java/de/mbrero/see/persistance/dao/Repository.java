package de.mbrero.see.persistance.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import de.mbrero.see.persistance.DBConnection;

/**
 * Generic repository for entities.
 * 
 * @author massimiliano.brero@studium.fernuni-hagen.de
 *
 */

public class Repository<T> implements IRepository<T> {

	/*
	 * Get the Connection to the database
	 */
	private DBConnection conn = null;
	/*
	 * This is needed to create a generic repository for all tables.
	 */
	private final Class<T> CLASS_TYPE;

	/**
	 * Constructor: Initialize Repository for type T.
	 * @param T cl
	 */
	public Repository(Class<T> cl) {
		CLASS_TYPE = cl;
		init();
	}

	/**
	 * Constructor: Initialize Repository for type T and with given database connection.
	 * @param T cl
	 * @param DBConnection c
	 */
	public Repository(Class<T> cl, DBConnection c) {
		CLASS_TYPE = cl;
		conn = c;
		init();
	}

	/**
	 * 
	 */
	private void init() {
		if (conn == null)
			conn = new DBConnection();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T get(int id) {
		T item = null;
		Session session = getSession();
		
		try {
			Transaction t = session.beginTransaction();
			item = (T) session.get(CLASS_TYPE, id);
			t.commit();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return item;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		List<T> items = new ArrayList<T>();
		Session session = getSession();
		
		try {
			Transaction t = session.beginTransaction();
			items = session.createCriteria(CLASS_TYPE).list();
			t.commit();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return items;
	}

	@Override
	public void save(T item) {
		Transaction t = null;
		Session session = getSession();
		
		try {
			t = session.beginTransaction();
			session.save(item);
			t.commit();
		} catch (RuntimeException e) {
			if (t != null) {
				t.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
	}

	@Override
	public void delete(T item) {
		Transaction t = null;
		Session session = getSession();
		
		try {
			t = session.beginTransaction();
			session.delete(item);
			t.commit();
		} catch (RuntimeException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
	}

	@SuppressWarnings("unchecked")
	public void delete(int id) {
		Session session = getSession();
		T item = (T) session.load(CLASS_TYPE, new Integer(id));
		delete(item);
	}

	
	public void update(T item) {
		Session session = getSession();
		
		try {
			Transaction t = session.beginTransaction();
			session.update(item);
			t.commit();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}

	}

	/**
	 * 
	 * @param session
	 */
	public void closeSession(Session session) {
			session.flush();
			session.close();			
	}

	/**
	 * 
	 * @return
	 */
	private Session getSession() {
		return conn.getNewSession();
	}

}
