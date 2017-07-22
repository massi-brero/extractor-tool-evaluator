package de.mbrero.see.persistance.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import de.mbrero.see.persistance.DBConnection;

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
		this.init();
	}
	
	public Repository(Class<T> cl, DBConnection c) {
		CLASS_TYPE = cl;
		this.conn = c;
		this.init();
	}

	private void init() {
		if (conn == null)
			this.conn = new DBConnection();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T get(int id) {
		T item = null;
        try {
        	Transaction t = this.getSession().beginTransaction();
            item = (T) this.getSession().get(CLASS_TYPE, id);
            t.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            this.getSession().close();
        }
        return item;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
        List<T> items = new ArrayList<T>();
        try {
        	Transaction t = this.getSession().beginTransaction();
            items = this.getSession().createCriteria(CLASS_TYPE).list();
            t.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            this.getSession().flush();
            this.getSession().close();
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
            t = this.getSession().beginTransaction();
			this.getSession().save(item);
            t.commit();
	        } catch (RuntimeException e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        } finally {
            this.closeSession();
        }
	}

	public void delete(T item) {
        Transaction t = null;
        try {
            t = this.getSession().beginTransaction();
            this.getSession().delete(item);
            t.commit();
        } 
        catch (RuntimeException e) {
            if (t != null) 
                t.rollback();
            e.printStackTrace();
        } 
        finally {
            this.getSession().flush();
            this.getSession().close();
        }
	}
	
	@SuppressWarnings("unchecked")
	public void delete(int id) {
		T item = (T) this.getSession().load(CLASS_TYPE, new Integer(id));
		this.delete(item);
	}

	public void update(T item) {

        try {
        	Transaction t = this.getSession().beginTransaction();
            this.getSession().update(item);
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
		if (this.session != null)
			this.session.close();
	}
	
	private Session getSession() {

		if (this.session == null || !this.session.isConnected())
			this.session = conn.getNewSession();
		
		return this.session;
	}

	
}
