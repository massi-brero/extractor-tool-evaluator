/**
 * 
 */
package de.mbrero.see.parser;

import static org.junit.Assert.assertEquals;
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
	
	public void setUp()
	{
		
	}
	
	@Test
	public void test_3_parseTinyXMLResult() throws SAXException, IOException, ParserConfigurationException {
		
		parser.parse(workingFile);
		HashMap<String, Annotation> annotations = parser.getAnnotations();
		Annotation annotation = annotations.get("C1622890");
		
		assertEquals(2, annotations.size());
		assertEquals("error matching cui", "C1622890", annotation.getCui());
		assertEquals("error matching ontology", Ontology.GO, annotation.getOntology());
		assertEquals("error matching preferred text", "test", annotation.getPreferredText());
		assertEquals("error matching file", workingFile, annotation.getSourceText());
		assertEquals("error matching extractor", "cTakes", annotation.getExtractor());
		assertEquals("error matching count", 1, annotation.getCount());
		
	}

	
	@Test
	public void test_4_parseResultWithDuplicateAnnotations() throws SAXException, IOException, ParserConfigurationException {
		parser.parse(multipleAnnotationsFile);
		HashMap<String, Annotation> annotations = parser.getAnnotations();
		Annotation annotation = annotations.get("C1622890");
		
		assertEquals("error matching cui", "C1622890", annotation.getCui());
		assertEquals("error matching count", 2, annotation.getCount());
		
	}
	
	@Test 
	public void test_5_parseFilesInDirectory() {
		
	}
	
	@Test 
	public void test_56parseFilesInNestedDirectory() {
		
	}
	
	
	@Test
	public void test_99_parseBigXMLResult() {
		fail("not implemented");
	}
	
	
}
