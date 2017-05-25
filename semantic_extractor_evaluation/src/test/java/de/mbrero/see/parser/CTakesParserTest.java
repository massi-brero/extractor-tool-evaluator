/**
 * 
 */
package de.mbrero.see.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

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
	public void test_1_InjectFileWithConstructor() throws FileNotFoundException
	{
		CTakesParser parser2 = new CTakesParser(workingFile);
		
		assertTrue(parser2.getSourceTextFile() instanceof File);
	}
	
	@Test
	public void test_2_InjectFileWithSet() throws FileNotFoundException
	{
		parser.setSourceTextFile(workingFile);
		
		assertTrue(parser.getSourceTextFile() instanceof File);
	}
	

	@Test
	public void test_3_parseTinyXMLResult() throws FileNotFoundException {
		
		parser.setSourceTextFile(workingFile);
		parser.parse();
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
	public void test_4_parseResultWithDuplicateAnnotations() {
		parser.setSourceTextFile(multipleAnnotationsFile);
		parser.parse();
		HashMap<String, Annotation> annotations = parser.getAnnotations();
		Annotation annotation = annotations.get("C1622890");
		
		assertEquals("error matching cui", "C1622890", annotation.getCui());
		assertEquals("error matching count", 2, annotation.getCount());
		
	}
	
	
	@Test
	public void test_5_parseBigXMLResult() {
		fail("not implemented");
	}
	
	
}
