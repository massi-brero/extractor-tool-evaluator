/**
 * 
 */
package de.mbrero.see.parser;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.junit.Test;

import de.mbrero.see.persistance.dto.Annotation;

/**
 * @author massi
 *
 */
public class CTakesParserTest {
	private CTakesParser parser = new CTakesParser();
	private File workingFile = new File(getClass().getClassLoader().getResource("texts/output.long.xml").getFile());
	private File multipleAnnotationsFile = new File(getClass().getClassLoader().getResource("texts/output.short.multiple.xml").getFile());
	
	public void setUp()
	{
		
	}
	
	@Test
	public void testInjectFileWithConstructor() throws FileNotFoundException
	{
		CTakesParser parser2 = new CTakesParser(workingFile);
		
		assertTrue(parser2.getSourceTextFile() instanceof File);
	}
	
	@Test
	public void testInjectFileWithSet() throws FileNotFoundException
	{
		parser.setSourceTextFile(workingFile);
		
		assertTrue(parser.getSourceTextFile() instanceof File);
	}
	

	@Test
	public void parseTinyXMLResult() throws FileNotFoundException {
		
		parser.setSourceTextFile(workingFile);
		HashMap<String, Annotation> annotations = parser.parse();
		
		assertEquals(1, annotations.size());
		assertEquals("3115", annotations.get("3115").getCui());
		
	}
	
	@Test
	public void parseBigXMLResult() {
		fail("not implemented");
	}
	
	@Test
	public void parseResultWithDuplicateAnnotations() {
		parser.setSourceTextFile(multipleAnnotationsFile);
		HashMap<String, Annotation> annotations = parser.parse();
		
		assertEquals(1, annotations.size());
		assertEquals(2, annotations.get("3115").getCount());
	}
	
	
}
