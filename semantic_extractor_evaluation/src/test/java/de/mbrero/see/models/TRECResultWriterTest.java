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


public class TRECResultWriterTest {
	File resultFile = null;
	private HashMap<String, HashMap<String, Annotation>> allAnnotationsWithCUI;
	//private HashMap<String, HashMap<String, Annotation>> allAnnotationsWithSourceId;
	private Annotation annotation1;
	private Annotation annotation2;
	private TRECResultModel model;

	@Before
	public void setUp() throws Exception {
		model = new TRECResultModel();
		allAnnotationsWithCUI = new HashMap<>();
		//allAnnotationsWithSourceId = new HashMap<>();
		resultFile = new File("src/test/resources/trec/result");
		setUpAnnotationsFixture();
	}
	
	@After
	public void tearDown() throws Exception {
		resultFile.delete();
	}
	
	
	/****************************************************
	 * 					Test with CUI
	 ****************************************************/

	@Test
	public void testSaveSingleAnnotationWithCui() throws IOException {
		model.setResultFile(resultFile);
		model.setUseSourceId(false);
		model.saveEntity(annotation1);
		Stream<String> stream = Files.lines(Paths.get(resultFile.getPath()));

		stream.forEach((str) -> {
			assertEquals("testText1.txt 0 CUI001_0 0 0 0", str);
		});
		
		stream.close();

	}
	@Test
	public void testSaveAnnotationListWithCui() throws IOException {
		ArrayList<Annotation> annotations = new ArrayList<>();
		annotations.add(annotation1);
		annotations.add(annotation2);
		ArrayList<String> annotationsResult = new ArrayList<>();
		model.setResultFile(resultFile);
		model.setUseSourceId(false);
		model.saveEntityList(annotations);
		
		Stream<String> stream = Files.lines(Paths.get(resultFile.getPath()));
		
		stream.forEach((str) -> {
			annotationsResult.add(str);
		});
		
		assertEquals(3, annotationsResult.size());
		assertEquals("testText1.txt 0 CUI001_0 0 0 0", annotationsResult.get(0));
		assertEquals("testText2.txt 0 CUI002_0 0 0 0", annotationsResult.get(1));
		assertEquals("testText2.txt 0 CUI002_1 0 0 0", annotationsResult.get(2));
		
		stream.close();
		
	}
	
	@Test
	public void testSaveAnnotationFromMultipleDocumentsWithCUI() throws IOException {

		ArrayList<String> annotationsResult = new ArrayList<>();
		model.setResultFile(resultFile);
		model.setUseSourceId(false);
		model.saveEntitiesInCorpus(allAnnotationsWithCUI);
		
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
		assertEquals("testText1.txt 0 CUI001_0 0 0 0", annotationsResult.get(0));
		assertEquals("testText2.txt 0 CUI002_0 0 0 0", annotationsResult.get(1));
		assertEquals("testText2.txt 0 CUI002_1 0 0 0", annotationsResult.get(2));
		
		stream.close();
		
	}
	
	/****************************************************
	 * 			Test with concept's source id
	 ****************************************************/
	
	@Test
	public void testSaveSingleAnnotationWithSourceId() throws IOException {
		model.setResultFile(resultFile);
		model.setUseSourceId(true);
		model.saveEntity(annotation1);
		Stream<String> stream = Files.lines(Paths.get(resultFile.getPath()));

		stream.forEach((str) -> {
			assertEquals("testText1.txt 0 001_0 0 0 0", str);
		});
		
		stream.close();

	}
	@Test
	public void testSaveAnnotationListWithSourceId() throws IOException {
		ArrayList<Annotation> annotations = new ArrayList<>();
		annotations.add(annotation1);
		annotations.add(annotation2);
		ArrayList<String> annotationsResult = new ArrayList<>();
		model.setResultFile(resultFile);
		model.setUseSourceId(true);
		model.saveEntityList(annotations);
		
		Stream<String> stream = Files.lines(Paths.get(resultFile.getPath()));
		
		stream.forEach((str) -> {
			annotationsResult.add(str);
		});
		
		assertEquals(3, annotationsResult.size());
		assertEquals("testText1.txt 0 001_0 0 0 0", annotationsResult.get(0));
		assertEquals("testText2.txt 0 002_0 0 0 0", annotationsResult.get(1));
		assertEquals("testText2.txt 0 002_1 0 0 0", annotationsResult.get(2));
		
		stream.close();
		
	}
	
	@Test
	public void testSaveAnnotationFromMultipleDocumentsWithSourceId() throws IOException {

		ArrayList<String> annotationsResult = new ArrayList<>();
		model.setResultFile(resultFile);
		model.setUseSourceId(true);
		model.saveEntitiesInCorpus(allAnnotationsWithCUI);
		
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
		assertEquals("testText1.txt 0 001_0 0 0 0", annotationsResult.get(0));
		assertEquals("testText2.txt 0 002_0 0 0 0", annotationsResult.get(1));
		assertEquals("testText2.txt 0 002_1 0 0 0", annotationsResult.get(2));
		
		stream.close();
		
	}
	
	
	
	private void setUpAnnotationsFixture() {
		String document1 = "testText1.txt";
		String document2 = "testText2.txt";
		
		annotation1 = new Annotation();
		annotation1.setCount(1);
		annotation1.setCui("CUI001");
		annotation1.setSourceConceptId("001");
		annotation1.setDocumentID(document1);
		annotation1.setExtractor("extractor1");
		annotation1.setMatchedChunk("test text");
		annotation1.setOntology(Ontology.NCBI.name());
		annotation1.setPreferredText("test text");
		annotation1.setTestRunId(0);
		
		annotation2 = new Annotation();
		annotation2.setCount(2);
		annotation2.setCui("CUI002");
		annotation2.setSourceConceptId("002");
		annotation2.setDocumentID(document2);
		annotation2.setExtractor("extractor2");
		annotation2.setMatchedChunk("test text");
		annotation2.setOntology(Ontology.NCBI.name());
		annotation2.setPreferredText("test text");
		annotation2.setTestRunId(1);
		
		HashMap<String, Annotation> put1 = new HashMap<>();
		put1.put(annotation1.getCui(), annotation1);
				
		HashMap<String, Annotation> put2 = new HashMap<>();
		put2.put(annotation2.getCui(), annotation2);
		
		allAnnotationsWithCUI.put(document1, put1);
		allAnnotationsWithCUI.put(document2, put2);
	}

}
