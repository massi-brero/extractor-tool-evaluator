package de.mbrero.see.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.mbrero.see.persistance.DBConnection;
import de.mbrero.see.persistance.dao.Repository;
import de.mbrero.see.persistance.dto.TestRun;
import de.mbrero.see.persistance.dto.types.TestRunResults;

public class TestRunModelTest {
	private Repository<TestRun> repo = null;
	private DBConnection conn = null;
	
	@Before
	public void setUp() throws Exception {
		URL url = getClass().getClassLoader().getResource("hibernate.cfg.xml");
		conn = new DBConnection(url);
		this.repo = new Repository<>(TestRun.class, conn);
	}

	@After
	public void tearDown() throws Exception {
		Session session = this.conn.getNewSession();
		Transaction t = session.beginTransaction();
	    Query query = session.createQuery("delete from Article");
	    query.executeUpdate();
	    t.commit();
	    session.close();
	}


	@Test
	public void save() {
		TestRun run = new TestRun();
		run.setPath("foo/test.xml");
		run.setDate(new Date());
		run.setResult(TestRunResults.SUCCESS);
		TestRunModel model = new TestRunModel();
		
		model.save(run);
		ArrayList<TestRun> items = (ArrayList<TestRun>) repo.getAll();

		assertEquals(1, items.size());
	}

}
