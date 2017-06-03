package de.mbrero.see.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.mbrero.see.persistance.dao.Repository;
import de.mbrero.see.persistance.dto.Annotation;
import types.Ontology;


public class TRECResultWriterTest {
	File resultFile = null;
	private HashMap<String, HashMap<String, Annotation>> allAnnotations;
	private Annotation annotation1;
	private Annotation annotation2;

	@Before
	public void setUp() throws Exception {
		resultFile = new File("src/test/resources/result");
		setUpAnnotationsFixture();
	}
	
	@After
	public void tearDown() throws Exception {
		resultFile.delete();
	}

	@Test
	public void testSaveHashMapOfStringAnnotation() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveSingleAnnotation() throws IOException {
		Stream<String> stream = Files.lines(Paths.get(resultFile.getPath()));

		stream.forEach((str) -> {
			assertEquals("testText1.txt 0 CUI001 0 0 0", str);
		});
		
		stream.close();

	}
	@Test
	public void testSaveAnnotationList() throws IOException {
//		Stream<String> stream = Files.lines(Paths.get(resultFile.getPath()));
//
//		stream.forEach((str) -> {
//			assertTrue("testText1.txt 0 CUI001 0 0 0" = str);
//		});
//		
//		stream.close();

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
		annotation1.setOntology(Ontology.NCBI.name());
		annotation1.setPreferredText("test text");
		annotation1.setTestRunId(0);
		
		annotation2 = new Annotation();
		annotation2.setCount(2);
		annotation2.setCui("CUI002");
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
		
		allAnnotations.put(document2, put2);
		allAnnotations.put(document1, put1);
	}

}
