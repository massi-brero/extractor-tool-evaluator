package de.mbrero.see.models;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.mbrero.see.api.TestRunController;
import de.mbrero.see.persistance.DBConnection;
import de.mbrero.see.persistance.dao.Repository;
import de.mbrero.see.persistance.dto.Annotation;
import types.Ontology;

public class DBAnnotationModelTest {
	private HashMap<String, HashMap<String, Annotation>> allAnnotations;
	private Repository<Annotation> repo;
	private Annotation annotation1;
	private Annotation annotation2;
	private DBConnection conn;
	private DBAnnotationModel annotationModel = new DBAnnotationModel();

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
	    Query query = session.createQuery("delete from Annotation");
	    query.executeUpdate();
	    t.commit();
	    session.close();
		DBConnection.closeDBConnection();
	}

	@Test
	public void testSaveAnnotation() {
	
		annotationModel.saveEntity(annotation1);
		Annotation item = repo.get(annotation1.getId());

		assertEquals(1, item.getCount());
		assertEquals("001", item.getSourceConceptId());
		assertEquals("C001", item.getCui());
		assertEquals("testText1.txt", item.getDocumentID());
		assertEquals("extractor1", item.getExtractor());
		assertEquals("test text", item.getMatchedChunk());
		assertEquals(Ontology.NCBI.name(), item.getOntology());
		assertEquals("test text", item.getPreferredText());
		assertEquals(0, item.getTestRunId());
		
	}
	
	@Test
	public void testSaveAnnotationList() {
		ArrayList<Annotation> annotationList = new ArrayList<>();
		annotationList.add(annotation1);
		annotationList.add(annotation2);
		
		annotationModel.saveEntityList(annotationList);
		ArrayList<Annotation> items = (ArrayList<Annotation>)repo.getAll();
		
		assertEquals(2, items.size());
		assertEquals(1, items.get(0).getCount());
		assertEquals("001", items.get(0).getSourceConceptId());
		assertEquals("C001", items.get(0).getCui());
		assertEquals("testText1.txt", items.get(0).getDocumentID());
		assertEquals("extractor1", items.get(0).getExtractor());
		assertEquals("test text", items.get(0).getMatchedChunk());
		assertEquals(Ontology.NCBI.name(), items.get(0).getOntology());
		assertEquals("test text", items.get(0).getPreferredText());
		assertEquals(0, items.get(0).getTestRunId());

		assertEquals(2, items.get(1).getCount());
		assertEquals("002", items.get(1).getSourceConceptId());
		assertEquals("C002", items.get(1).getCui());
		assertEquals("testText2.txt", items.get(1).getDocumentID());
		assertEquals("extractor2", items.get(1).getExtractor());
		assertEquals("test text", items.get(1).getMatchedChunk());
		assertEquals(Ontology.NCBI.name(), items.get(1).getOntology());
		assertEquals("test text", items.get(1).getPreferredText());
		assertEquals(0, items.get(1).getTestRunId());
	}
	
	@Test
	public void testSaveAnnotationsinCorpus() {
	
		annotationModel.saveEntitiesInCorpus(allAnnotations);
		ArrayList<Annotation> items = (ArrayList<Annotation>)repo.getAll();
		
		Collections.sort(items, new Comparator<Annotation>() {
		    @Override
		    public int compare(Annotation a1, Annotation a2) {
		        return a1.getSourceConceptId().compareTo(a2.getSourceConceptId());
		    }
		});
		
		assertEquals(1, items.get(0).getCount());
		assertEquals("001", items.get(0).getSourceConceptId());
		assertEquals("C001", items.get(0).getCui());
		assertEquals("testText1.txt", items.get(0).getDocumentID());
		assertEquals("extractor1", items.get(0).getExtractor());
		assertEquals("test text", items.get(0).getMatchedChunk());
		assertEquals(Ontology.NCBI.name(), items.get(0).getOntology());
		assertEquals("test text", items.get(0).getPreferredText());
		assertEquals(0, items.get(0).getTestRunId());
		
		assertEquals(2, items.get(1).getCount());
		assertEquals("002", items.get(1).getSourceConceptId());
		assertEquals("C002", items.get(1).getCui());
		assertEquals("testText2.txt", items.get(1).getDocumentID());
		assertEquals("extractor2", items.get(1).getExtractor());
		assertEquals("test text", items.get(1).getMatchedChunk());
		assertEquals(Ontology.NCBI.name(), items.get(1).getOntology());
		assertEquals("test text", items.get(1).getPreferredText());
		assertEquals(0, items.get(1).getTestRunId());
	}
	
	private void setUpAnnotationsFixture() {
		String document1 = "testText1.txt";
		String document2 = "testText2.txt";
		
		annotation1 = new Annotation();
		annotation1.setCount(1);
		annotation1.setSourceConceptId("001");
		annotation1.setCui("C001");
		annotation1.setDocumentID(document1);
		annotation1.setExtractor("extractor1");
		annotation1.setMatchedChunk("test text");
		annotation1.setOntology(Ontology.NCBI.name());
		annotation1.setPreferredText("test text");
		annotation1.setTestRunId(TestRunController.testRunId);
		
		annotation2 = new Annotation();
		annotation2.setCount(2);
		annotation2.setSourceConceptId("002");
		annotation2.setCui("C002");
		annotation2.setDocumentID(document2);
		annotation2.setExtractor("extractor2");
		annotation2.setMatchedChunk("test text");
		annotation2.setOntology(Ontology.NCBI.name());
		annotation2.setPreferredText("test text");
		annotation2.setTestRunId(TestRunController.testRunId);
		
		HashMap<String, Annotation> put1 = new HashMap<>();
		put1.put(annotation1.getSourceConceptId(), annotation1);
				
		HashMap<String, Annotation> put2 = new HashMap<>();
		put2.put(annotation2.getSourceConceptId(), annotation2);
		
		allAnnotations.put(document1, put1);
		allAnnotations.put(document2, put2);
	}

}
