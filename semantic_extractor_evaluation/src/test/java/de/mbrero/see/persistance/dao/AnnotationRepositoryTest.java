package de.mbrero.see.persistance.dao;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.mbrero.see.persistance.DBConnection;
import de.mbrero.see.persistance.dto.Annotation;
import types.Ontology;



public class AnnotationRepositoryTest {
	private Repository<Annotation> repo = null;
	private DBConnection conn = null;

	@Before
	public void setUp() throws Exception {
		URL url = getClass().getClassLoader().getResource("hibernate.cfg.xml");
		conn = new DBConnection(url);
		this.repo = new Repository<>(Annotation.class, conn);
	}

	@After
	public void tearDown() throws Exception {
		Session session = this.conn.getNewSession();
		Transaction t = session.beginTransaction();
	    Query query = session.createQuery("delete from Annotation");
	    query.executeUpdate();
	    t.commit();
	    session.close();
	}
	
	@Test
	public void testSave() {
		Annotation ann = new Annotation();
		ann.setCount(1);
		ann.setExtractor("ctakes");
		ann.setDocumentID("#123");
		ann.setMatchedChunk("test");
		ann.setSourceConceptId("123");
		ann.setOntology(Ontology.GO.toString());
		ann.setPreferredText("test");
		ann.setTestRunId(123);
		
		repo.save(ann);
		ArrayList<Annotation> items = (ArrayList<Annotation>) repo.getAll();

		assertEquals(1, items.size());
	}

	@Test
	public void testGet() {
		Annotation ann = new Annotation();
		ann.setCount(1);
		ann.setExtractor("ctakes");
		ann.setDocumentID("#123");
		ann.setMatchedChunk("test");
		ann.setSourceConceptId("123");
		ann.setOntology(Ontology.GO.toString());
		ann.setPreferredText("test");
		ann.setTestRunId(123);
		
		repo.save(ann);
		Annotation item = (Annotation) repo.get(1);

		assertEquals("123", item.getSourceConceptId());
	}

	@Test
	public void testGetAll() {
		ArrayList<Annotation> items= (ArrayList<Annotation>) repo.getAll();

		assertEquals(0, items.size());
	}

	@Test
	public void testDeleteById() {
		Annotation ann = new Annotation();
		ann.setCount(1);
		ann.setExtractor("ctakes");
		ann.setDocumentID("#123");
		ann.setMatchedChunk("test");
		ann.setSourceConceptId("123");
		ann.setOntology(Ontology.GO.toString());
		ann.setPreferredText("test");
		ann.setTestRunId(123);
		
		repo.save(ann);
		repo.delete(1);
		ArrayList<Annotation> items = (ArrayList<Annotation>) repo.getAll();

		assertEquals(0, items.size());
	}
	
	@Test
	public void testDeleteByEntity() {
		Annotation ann = new Annotation();
		ann.setCount(1);
		ann.setExtractor("ctakes");
		ann.setDocumentID("#123");
		ann.setMatchedChunk("test");
		ann.setSourceConceptId("123");
		ann.setOntology(Ontology.GO.toString());
		ann.setPreferredText("test");
		ann.setTestRunId(123);
		
		repo.save(new Annotation());
		Annotation item = repo.get(1);
		
		repo.delete(item);
		ArrayList<Annotation> items = (ArrayList<Annotation>) repo.getAll();

		assertEquals(0, items.size());
	}

	@Test
	public void testUpdate() {
		Annotation ann = new Annotation();
		ann.setCount(1);
		ann.setExtractor("ctakes");
		ann.setDocumentID("#123");
		ann.setMatchedChunk("test");
		ann.setSourceConceptId("123");
		ann.setOntology(Ontology.GO.toString());
		ann.setPreferredText("test");
		ann.setTestRunId(123);
		
		repo.save(ann);
		
		Annotation item = (Annotation) repo.get(1);
		item.setSourceConceptId("124");
		repo.update(item);

		assertEquals("124", item.getSourceConceptId());
	}

}
