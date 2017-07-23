package de.mbrero.see.persistance.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import de.mbrero.see.persistance.DBConnection;
import types.OutputType;

/**
 * 
 * @author massi.brero@gmail.com
 *
 */

public class Repository<T> implements IRepository<T> {

	/*
	 * Get the Connection to the database
	 */
	private DBConnection conn = null;
	/*
	 * We can share a session over different method calls ... if we really want to.
	 */
	private Session session = null;
	/*
	 * This is needed to create a generic repository for all tables.
	 */
	private final Class<T> CLASS_TYPE;
	
	public Repository(Class<T> cl) {
		CLASS_TYPE = cl;
		init();
	}
	
	public Repository(Class<T> cl, DBConnection c) {
		CLASS_TYPE = cl;
		conn = c;
		init();
	}

	private void init() {
		if (conn == null)
			conn = new DBConnection();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T get(int id) {
		T item = null;
        try {
        	Transaction t = getSession().beginTransaction();
            item = (T) getSession().get(CLASS_TYPE, id);
            t.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            getSession().flush();
            getSession().close();
        }
        return item;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
        List<T> items = new ArrayList<T>();
        try {
        	Transaction t = getSession().beginTransaction();
            items = getSession().createCriteria(CLASS_TYPE).list();
            t.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            getSession().flush();
            getSession().close();
        }
        return items;
	}

	/**
	 * 
	 * @param item
	 */
	@Override
	public void save(T item) {
        Transaction t = null;
        try {
            t = getSession().beginTransaction();
			getSession().save(item);
            t.commit();
	        } catch (RuntimeException e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        } finally {
            closeSession();
        }
	}

	public void delete(T item) {
        Transaction t = null;
        try {
            t = getSession().beginTransaction();
            getSession().delete(item);
            t.commit();
        } 
        catch (RuntimeException e) {
            if (t != null) 
                t.rollback();
            e.printStackTrace();
        } 
        finally {
            getSession().flush();
            getSession().close();
        }
	}
	
	@SuppressWarnings("unchecked")
	public void delete(int id) {
		T item = (T) getSession().load(CLASS_TYPE, new Integer(id));
		delete(item);
	}

	public void update(T item) {

        try {
        	Transaction t = getSession().beginTransaction();
            getSession().update(item);
            t.commit();
        } 
        catch (RuntimeException e) {
            e.printStackTrace();
        } 
        finally {
            session.flush();
            session.close();
        }

	}
	
	public void closeSession() {
		if (session != null)
			session.close();
	}
	
	private Session getSession() {
		outputProgress();
		if (session == null || !session.isConnected())
			session = conn.getNewSession();
		
		return session;
	}
	
	private void outputProgress() {
		System.out.print(".");
	}

	
}
