package de.mbrero.see.models;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.mbrero.see.persistance.dto.Annotation;
import types.Ontology;

public class TRECGoldstandardResultWriterTest {

	File resultFile = null;
	private HashMap<String, HashMap<String, Annotation>> allAnnotations;
	private Annotation annotation1;
	private Annotation annotation2;
	private TRECGoldstandardModel model;

	@Before
	public void setUp() throws Exception {
		model = new TRECGoldstandardModel();
		allAnnotations = new HashMap<>();
		resultFile = new File("src/test/resources/trec/qrel");
		setUpAnnotationsFixture();
	}
	
	@After
	public void tearDown() throws Exception {
		resultFile.delete();
	}

	@Test
	public void testSaveSingleAnnotation() throws IOException {
		model.setResultFile(resultFile);
		model.saveEntity(annotation1);
		Stream<String> stream = Files.lines(Paths.get(resultFile.getPath()));

		stream.forEach((str) -> {
			assertEquals("testText1.txt 0 C001_0 1", str);
		});
		
		stream.close();

	}
	@Test
	public void testSaveAnnotationList() throws IOException {
		ArrayList<Annotation> annotations = new ArrayList<>();
		annotations.add(annotation1);
		annotations.add(annotation2);
		ArrayList<String> annotationsResult = new ArrayList<>();
		model.setResultFile(resultFile);
		model.saveEntityList(annotations);
		
		Stream<String> stream = Files.lines(Paths.get(resultFile.getPath()));
		
		stream.forEach((str) -> {
			annotationsResult.add(str);
		});
		
		assertEquals(3, annotationsResult.size());
		assertEquals("testText1.txt 0 C001_0 1", annotationsResult.get(0));
		assertEquals("testText2.txt 0 C002_0 1", annotationsResult.get(1));
		assertEquals("testText2.txt 0 C002_1 1", annotationsResult.get(2));
		
		stream.close();
		
	}
	
	@Test
	public void testSaveAnnotationFromMultipleDocuments() throws IOException {

		ArrayList<String> annotationsResult = new ArrayList<>();
		model.setResultFile(resultFile);
		model.saveEntitiesInCorpus(allAnnotations);
		
		Stream<String> stream = Files.lines(Paths.get(resultFile.getPath()));
		
		stream.forEach((str) -> {
			annotationsResult.add(str);
		});
		
		Collections.sort(annotationsResult, new Comparator<String>() {
		    @Override
		    public int compare(String l1, String l2) {
		        return l1.compareTo(l2);
		    }
		});
		assertEquals(3, annotationsResult.size());
		assertEquals("testText1.txt 0 C001_0 1", annotationsResult.get(0));
		assertEquals("testText2.txt 0 C002_0 1", annotationsResult.get(1));
		assertEquals("testText2.txt 0 C002_1 1", annotationsResult.get(2));
		
		stream.close();
		
	}
	
	private void setUpAnnotationsFixture() {
		String document1 = "testText1.txt";
		String document2 = "testText2.txt";
		
		annotation1 = new Annotation();
		annotation1.setCount(1);
		annotation1.setSourceConceptId("0011");
		annotation1.setCui("C001");
		annotation1.setDocumentID(document1);
		annotation1.setMatchedChunk("test text");
		annotation1.setOntology(Ontology.NCBI.name());
		annotation1.setTestRunId(0);
		
		annotation2 = new Annotation();
		annotation2.setCount(2);
		annotation2.setSourceConceptId("0022");
		annotation2.setCui("C002");
		annotation2.setDocumentID(document2);
		annotation2.setMatchedChunk("test text");
		annotation2.setOntology(Ontology.NCBI.name());
		annotation2.setTestRunId(1);
		
		HashMap<String, Annotation> put1 = new HashMap<>();
		put1.put(annotation1.getSourceConceptId(), annotation1);
				
		HashMap<String, Annotation> put2 = new HashMap<>();
		put2.put(annotation2.getSourceConceptId(), annotation2);
		
		allAnnotations.put(document1, put1);
		allAnnotations.put(document2, put2);
	}

}
