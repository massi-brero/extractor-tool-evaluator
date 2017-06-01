package de.mbrero.see.writer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.URL;
import java.util.HashMap;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.mbrero.see.persistance.DBConnection;
import de.mbrero.see.persistance.dao.Repository;
import de.mbrero.see.persistance.dto.Annotation;
import types.Ontology;

public class DBAnnotationWriterTest {
	private HashMap<String, HashMap<String, Annotation>> allAnnotations;
	private Repository<Annotation> repo;
	private Annotation annotation1;
	DBConnection conn;

	@Before
	public void setUp() throws Exception {
		allAnnotations = new HashMap<>();
		repo = new Repository<>(Annotation.class, conn);
		setUpAnnotationsFixture();
		
		URL url = getClass().getClassLoader().getResource("hibernate.cfg.xml");
		conn = new DBConnection(url);

		
	}
	
	@After
	public void tearDown() throws Exception {
		Session session = conn.getNewSession();
		Transaction t = session.beginTransaction();
	    Query query = session.createQuery("delete from Article");
	    query.executeUpdate();
	    t.commit();
	    session.close();
	}


	@Test
	public void testSaveAnnotationsFromMultipleDocuments() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveAnnotation() {
	
		repo.save(annotation1);
		Annotation item = repo.get(0);

		assertEquals(item.getCount(), 1);
		assertEquals(item.getCui(), "CUI001");
		assertEquals(item.getDocumentID(), "testText1.txt");
		assertEquals(item.getExtractor(), "extractor2");
		assertEquals(item.getMatchedChunk(), "test text");
		assertEquals(item.getOntology(), Ontology.NCBI);
		assertEquals(item.getPreferredText(), "test text");
		assertEquals(item.getTestRunId(), 1);
		
	}
	
	private void setUpAnnotationsFixture() {
		String document1 = "testText1.txt";
		String document2 = "testText2.txt";
		
		annotation1 = new Annotation();
		annotation1.setCount(1);
		annotation1.setCui("CUI001");
		annotation1.setDocumentID(document1);
		annotation1.setExtractor("extractor1");
		annotation1.setMatchedChunk("test text");
		annotation1.setOntology(Ontology.NCBI);
		annotation1.setPreferredText("test text");
		annotation1.setTestRunId(0);
		
		Annotation annotation2 = new Annotation();
		annotation2.setCount(1);
		annotation2.setCui("CUI002");
		annotation2.setDocumentID(document2);
		annotation2.setExtractor("extractor2");
		annotation2.setMatchedChunk("test text");
		annotation2.setOntology(Ontology.NCBI);
		annotation2.setPreferredText("test text");
		annotation2.setTestRunId(1);
		
		HashMap<String, Annotation> put1 = new HashMap<>();
		put1.put(annotation1.getCui(), annotation1);
		allAnnotations.put(document1, put1);
		
		HashMap<String, Annotation> put2 = new HashMap<>();
		put1.put(annotation1.getCui(), annotation2);
		allAnnotations.put(document1, put2);
	}

}
