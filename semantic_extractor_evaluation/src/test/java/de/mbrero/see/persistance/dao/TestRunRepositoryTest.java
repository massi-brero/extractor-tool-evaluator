package de.mbrero.see.persistance.dao;

import static org.junit.Assert.assertEquals;

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
import de.mbrero.see.persistance.dto.TestRun;
import de.mbrero.see.persistance.dto.types.TestRunResults;

public class TestRunRepositoryTest {
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
	    Query query = session.createQuery("delete from TestRun");
	    query.executeUpdate();
	    t.commit();
	    session.close();
	}
	
	@Test
	public void testSave() {
		TestRun run = new TestRun();
		run.setInputPath("foo/test.xml");
		run.setDate(new Date());
		run.setResult(TestRunResults.SUCCESS.toString());
		
		repo.save(run);
		ArrayList<TestRun> items = (ArrayList<TestRun>) repo.getAll();

		assertEquals(1, items.size());
	}

	@Test
	public void testGet() {
		TestRun run = new TestRun();
		run.setInputPath("foo/test.xml");
		run.setDate(new Date());
		run.setResult(TestRunResults.SUCCESS.toString());
		
		repo.save(run);
		TestRun item = (TestRun) repo.get(1);

		assertEquals("foo/test.xml", item.getInputPath());
	}

	@Test
	public void testGetAll() {
		ArrayList<TestRun> items= (ArrayList<TestRun>) repo.getAll();

		assertEquals(0, items.size());
	}

	@Test
	public void testDeleteById() {
		TestRun run = new TestRun();
		run.setInputPath("foo/test.xml");
		run.setDate(new Date());
		run.setResult(TestRunResults.SUCCESS.toString());
		
		repo.save(run);
		repo.delete(1);
		ArrayList<TestRun> items = (ArrayList<TestRun>) repo.getAll();

		assertEquals(0, items.size());
	}
	
	@Test
	public void testDeleteByEntity() {
		TestRun run = new TestRun();
		run.setInputPath("foo/test.xml");
		run.setDate(new Date());
		run.setResult(TestRunResults.SUCCESS.toString());
		
		repo.save(new TestRun());
		TestRun item = repo.get(1);
		
		repo.delete(item);
		ArrayList<TestRun> items = (ArrayList<TestRun>) repo.getAll();

		assertEquals(0, items.size());
	}

	@Test
	public void testUpdate() {
		TestRun run = new TestRun();
		run.setInputPath("foo/test.xml");
		run.setDate(new Date());
		run.setResult(TestRunResults.SUCCESS.toString());
		
		repo.save(run);
		
		TestRun item = (TestRun) repo.get(1);
		item.setInputPath("foo/test2.xml");
		repo.update(item);

		assertEquals("foo/test2.xml", item.getInputPath());
	}

}
