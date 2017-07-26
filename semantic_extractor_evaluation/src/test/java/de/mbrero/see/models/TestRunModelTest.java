package de.mbrero.see.models;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
	    Query query = session.createQuery("delete from TestRun");
	    query.executeUpdate();
	    t.commit();
	    session.close();
	}


	@Test
	public void save() {
		String testParam = "-param test";
		String inPath = "foo/test.xml";
		String outPathEx = "foo/testEx.xml";
		String outPathTrec = "foo/testTr.xml";
		TestRunResults result = TestRunResults.SUCCESS;
		String tester = "m@b.de";
		
		TestRun run = new TestRun();
		run.setInputPath(inPath);
		run.setOutputPathExtractorResult(outPathEx);
		run.setOutputPathTRECFile(outPathTrec);
		run.setDate(new Date());
		run.setResult(result);
		run.setParameters(testParam);
		run.setTester(tester);
		run.setSystemInformation(getSystemInfoFixture().toString());
		TestRunModel model = new TestRunModel();
		model.save(run);
		
		ArrayList<TestRun> items = (ArrayList<TestRun>) repo.getAll();
		TestRun item = items.get(0);

		assertEquals(1, items.size());
		assertEquals(inPath, item.getInputPath());
		assertEquals(outPathEx, item.getOutputPathExtractorResult());
		assertEquals(outPathTrec, item.getOutputPathTRECFile());
		assertEquals(result, item.getResult());	
		assertEquals(testParam, item.getParameters());
		assertEquals(tester, item.getTester());
		assertFalse(item.getSystemInformation().isEmpty());
		assertTrue(item.getSystemInformation().contains("java_vendor") 
				&& item.getSystemInformation().contains("1.8"));
	}
	
	public HashMap<String, String> getSystemInfoFixture()
	{
		HashMap<String, String> system = new HashMap<>();
		
		system.put("available_processors", "2");
		system.put("free_memory", "123");
		system.put("max_memory_for_jvm", "789");
		system.put("total_memory_for_jvm", "101112");
		system.put("os", "testOS");
		system.put("Java_version", "1.8");
		system.put("jvm_version", "1.8");
		system.put("java_vendor", "testVendor");
		
		return system;
	}

}
