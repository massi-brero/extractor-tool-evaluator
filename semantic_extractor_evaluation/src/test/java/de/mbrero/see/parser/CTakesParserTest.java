/**
 * 
 */
package de.mbrero.see.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.xml.sax.SAXException;

import de.mbrero.see.persistance.dto.Annotation;
import types.Ontology;

/**
 * @author massi
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CTakesParserTest {
	private CTakesParser parser = new CTakesParser();
	private File workingFile = new File(getClass().getClassLoader().getResource("texts/output.short.xml").getFile());
	private File multipleAnnotationsFile = new File(getClass().getClassLoader().getResource("texts/output.short.multiple.xml").getFile());
	private File folder = new File(getClass().getClassLoader().getResource("texts/folder1").getFile());
	private File nestedFolder = new File(getClass().getClassLoader().getResource("texts/folder2").getFile());
	private File nestedFolderWithDuplicates = new File(getClass().getClassLoader().getResource("texts/folder4").getFile());

		
	public void setUp()
	{
		
	}
	
	@Test
	public void test_2_textFileIsProcessed() throws SAXException, IOException, ParserConfigurationException {
		
		parser.parse(workingFile);
		HashMap<String, HashMap<String, Annotation>> allAnnotations = parser.getAnnotations();
		
		assertEquals(1, allAnnotations.size());
		assertNotNull(allAnnotations.get(workingFile.getName()));
	}
		
		
	
	@Test
	public void test_3_parseTinyXMLResult() throws SAXException, IOException, ParserConfigurationException {
		
		parser.parse(workingFile);
		HashMap<String, HashMap<String, Annotation>> allAnnotations = parser.getAnnotations();
		HashMap<String, Annotation> annotations = allAnnotations.get(workingFile.getName());
		
		Annotation annotation = annotations.get("C1622890");
		
		assertEquals(2, annotations.size());
		assertEquals("error matching cui", "C1622890", annotation.getCui());
		assertEquals("error matching ontology", Ontology.GO, annotation.getOntology());
		assertEquals("error matching preferred text", "test", annotation.getPreferredText());
		assertEquals("error matching file", "test-input.txt", annotation.getDocumentID());
		assertEquals("error matching extractor", "cTakes", annotation.getExtractor());
		assertEquals("error matching count", 1, annotation.getCount());
		
	}

	
	@Test
	public void test_4_parseResultWithDuplicateAnnotations() throws SAXException, IOException, ParserConfigurationException {
//		parser.parse(multipleAnnotationsFile);
//		HashMap<String, Annotation> annotations = parser.getAnnotations();
//		Annotation annotation = annotations.get("C1622890");
//		
//		assertEquals("error matching cui", "C1622890", annotation.getCui());
//		assertEquals("error matching file", "test-input_m.txt", annotation.getDocumentID());
//		assertEquals("error matching count", 2, annotation.getCount());
	}
	
	@Test 
	public void test_5_parseAllFilesInDirectory() throws SAXException, IOException, ParserConfigurationException {
//		parser.parse(folder);
//		HashMap<String, HashMap<String, Annotation>> annotations = parser.getAnnotations();
//		HashMap<String, Annotation>
//		Annotation annotation1 = annotations.get("C1111111");
//		Annotation annotation2 = annotations.get("C2222222");
//		
//		assertEquals(4, annotations.size());
//		assertEquals("error matching cui", "C1111111", annotation1.getCui());
//		assertEquals("error matching file", "test-input1.txt", annotation1.getDocumentID());
//		assertEquals("error matching count", 1, annotation1.getCount());
//		assertEquals("error matching cui", "C2222222", annotation2.getCui());
//		assertEquals("error matching file", "test-input2.txt", annotation2.getDocumentID());
//		assertEquals("error matching count", 1, annotation2.getCount());
	}
	
	@Test 
	public void test_6_parseFilesInNestedDirectory() throws SAXException, IOException, ParserConfigurationException {
//		parser.parse(nestedFolder);
//		HashMap<String, Annotation> annotations = parser.getAnnotations();
//		Annotation annotation1 = annotations.get("C1111111");
//		Annotation annotation2 = annotations.get("C2222222");
//		
//		assertEquals(4, annotations.size());
//		assertEquals("error matching cui", "C1111111", annotation1.getCui());
//		assertEquals("error matching file", "test-input1.txt", annotation1.getDocumentID());
//		assertEquals("error matching count", 1, annotation1.getCount());
//		assertEquals("error matching cui", "C2222222", annotation2.getCui());
//		assertEquals("error matching file", "test-input2.txt", annotation2.getDocumentID());
//		assertEquals("error matching count", 1, annotation2.getCount());
		
	}
	
	@Test 
	public void test_7_parseFilesInNestedDirectoryWithDuplicateCui() throws SAXException, IOException, ParserConfigurationException {
//		parser.parse(nestedFolderWithDuplicates);
//		HashMap<String, Annotation> annotations = parser.getAnnotations();
//		Annotation annotation1 = annotations.get("C1111111");
//		Annotation annotation2 = annotations.get("C1622890");
//		
//		assertEquals(5, annotations.size());
//		assertEquals("error matching cui", "C1111111", annotation1.getCui());
//		assertEquals("error matching file", "test-input1.txt", annotation1.getDocumentID());
//		assertEquals("error matching count", 1, annotation1.getCount());
//		assertEquals("error matching cui", "C1622890", annotation2.getCui());
//		assertEquals("error matching file", "test-input_m.txt", annotation2.getDocumentID());
//		assertEquals("error matching count", 2, annotation2.getCount());
	}
	
	
	@Test
	public void test_99_parseBigXMLResult() {
		fail("not implemented");
	}
	
	
}
