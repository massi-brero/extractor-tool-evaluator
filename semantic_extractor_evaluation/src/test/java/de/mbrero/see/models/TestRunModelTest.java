package de.mbrero.see.models;

import static org.junit.Assert.*;

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
import de.mbrero.see.persistance.dto.SystemInformation;
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
	    Query query = session.createQuery("delete from TestRun");
	    query.executeUpdate();
	    t.commit();
	    session.close();
	}


	@Test
	public void save() {
		String testParam = "-param test";
		String path = "foo/test.xml";
		TestRunResults result = TestRunResults.SUCCESS;
		String tester = "m@b.de";
		
		TestRun run = new TestRun();
		run.setPath(path);
		run.setDate(new Date());
		run.setResult(result);
		run.setParameters(testParam);
		run.setTester(tester);
		run.setSystemInformation(getSystemInfoFixture());
		TestRunModel model = new TestRunModel();
		
		model.save(run);
		ArrayList<TestRun> items = (ArrayList<TestRun>) repo.getAll();
		TestRun item = items.get(0);

		assertEquals(1, items.size());
		assertEquals(path, item.getPath());
		assertEquals(result, item.getResult());	
		assertEquals(testParam, item.getParameters());
		assertEquals(tester, item.getTester());
		assertTrue(item.getSystemInformation() instanceof SystemInformation);
		assertEquals("1.8", item.getSystemInformation().getJavaVersion());
		
	}
	
	public SystemInformation getSystemInfoFixture()
	{
		SystemInformation system = new SystemInformation();
		
		system.setAvailableProcessors(1);
		
		system.setFreeMemory(12);
		
		system.setMaxMemoryForJVM(23);
		
		system.setTotalMemoryForJVM(34);
		
		system.setOS("Linux");
		
		system.setJavaVersion("1.8");
		
		system.setJvmVersion("1.8");
		
		system.setJavaVendor("ACMA");
		
		return system;
	}

}
