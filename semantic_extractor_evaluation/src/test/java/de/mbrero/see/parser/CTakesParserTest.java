/**
 * 
 */
package de.mbrero.see.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.xml.sax.SAXException;

import de.mbrero.see.persistance.dto.Annotation;
import types.Ontology;
import types.ParserType;

/**
 * This test class also tests the methods provided by the abstract class. You may see the test for the 
 * cTakes Parser as a model checking all the funcionalities. This is not reall TDD but faster ;-) 
 * 
 * @author massi.brero@gmail.com
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CTakesParserTest {
	private CTakesParser parser = null;
	private File workingFile = null;
	private File multipleAnnotationsFile = null;
	private File folder = null;
	private File nestedFolder = null;
	private File nestedFolderWithDuplicates = null;

	@Before
	public void setUp()
	{
		// setup  parser	
		parser = new CTakesParser();
		parser.setExtractorName("CTAKES");
		parser.setUmlsInformationTag("org.apache.ctakes.typesystem.type.refsem.UmlsConcept");
		parser.setConceptIdentifierNode("code");
		
		//set up working files
		workingFile = new File(getClass().getClassLoader().getResource("texts/output.short.xml").getFile());
		multipleAnnotationsFile = new File(getClass().getClassLoader().getResource("texts/output.short.multiple.xml").getFile());
		folder = new File(getClass().getClassLoader().getResource("texts/folder1").getFile());
		nestedFolder = new File(getClass().getClassLoader().getResource("texts/folder2").getFile());
		nestedFolderWithDuplicates = new File(getClass().getClassLoader().getResource("texts/folder4").getFile());
	
	}
	
	@Test
	public void test_2_textFileIsProcessed() throws SAXException, IOException, ParserConfigurationException {
		
		parser.parse(workingFile);
		HashMap<String, HashMap<String, Annotation>> allAnnotations = parser.getAnnotations();
		
		assertEquals(1, allAnnotations.size());
		assertNotNull(allAnnotations.get("test-input.txt"));
	}
		
		
	
	@Test
	public void test_3_parseTinyXMLResult() throws SAXException, IOException, ParserConfigurationException {
		
		parser.parse(workingFile);
		HashMap<String, HashMap<String, Annotation>> allAnnotations = parser.getAnnotations();
		HashMap<String, Annotation> annotations = allAnnotations.get("test-input.txt");
		
		Annotation annotation = annotations.get("GO:0043336");
		
		assertEquals(2, annotations.size());
		assertEquals("error matching cui", "GO:0043336", annotation.getConceptId());
		assertEquals("error matching ontology", Ontology.GO.name(), annotation.getOntology());
		//assertEquals("error matching preferred text", "test", annotation.getPreferredText());
		assertEquals("error matching file", "test-input.txt", annotation.getDocumentID());
		assertEquals("error matching extractor", ParserType.CTAKES.toString(), annotation.getExtractor());
		assertEquals("error matching count", 1, annotation.getCount());
		
	}

	
	@Test
	public void test_4_parseResultWithDuplicateAnnotations() throws SAXException, IOException, ParserConfigurationException {
		parser.parse(multipleAnnotationsFile);
		HashMap<String, HashMap<String, Annotation>> allAnnotations = parser.getAnnotations();
		HashMap<String, Annotation> annotations = allAnnotations.get("test-input_m.txt");
		
		Annotation annotation = annotations.get("GO:0043336");
		
		assertEquals("error matching cui", "GO:0043336", annotation.getConceptId());
		assertEquals("error matching file", "test-input_m.txt", annotation.getDocumentID());
		assertEquals("error matching count", 2, annotation.getCount());
	}
	
	@Test 
	public void test_5_parseAllFilesInDirectory() throws SAXException, IOException, ParserConfigurationException {
		parser.parse(folder);
		HashMap<String, HashMap<String, Annotation>> allAnnotations = parser.getAnnotations();
		HashMap<String, Annotation> annotations1 = allAnnotations.get("test-input1.txt");
		HashMap<String, Annotation> annotations2 = allAnnotations.get("test-input2.txt");
		Annotation annotation1 = annotations1.get("GO:0043336");
		Annotation annotation2 = annotations2.get("GO:0043336");
		
		assertEquals(2, allAnnotations.size());
		assertEquals("error matching cui", "GO:0043336", annotation1.getConceptId());
		assertEquals("error matching file", "test-input1.txt", annotation1.getDocumentID());
		assertEquals("error matching count", 1, annotation1.getCount());
		assertEquals("error matching cui", "GO:0043336", annotation2.getConceptId());
		assertEquals("error matching file", "test-input2.txt", annotation2.getDocumentID());
		assertEquals("error matching count", 1, annotation2.getCount());
	}
	
	@Test 
	public void test_6_parseFilesInNestedDirectory() throws SAXException, IOException, ParserConfigurationException {
		parser.parse(nestedFolder);
		HashMap<String, HashMap<String, Annotation>> allAnnotations = parser.getAnnotations();
		HashMap<String, Annotation> annotations1 = allAnnotations.get("test-input1.txt");
		HashMap<String, Annotation> annotations2 = allAnnotations.get("test-input2.txt");
		Annotation annotation1 = annotations1.get("GO:0043336");
		Annotation annotation2 = annotations2.get("GO:0043336");
		
		assertEquals(2, allAnnotations.size());
		assertEquals("error matching cui", "GO:0043336", annotation1.getConceptId());
		assertEquals("error matching file", "test-input1.txt", annotation1.getDocumentID());
		assertEquals("error matching count", 1, annotation1.getCount());
		assertEquals("error matching cui", "GO:0043336", annotation2.getConceptId());
		assertEquals("error matching file", "test-input2.txt", annotation2.getDocumentID());
		assertEquals("error matching count", 1, annotation2.getCount());
	}
	
	@Test 
	public void test_7_parseFilesInNestedDirectoryWithDuplicateCui() throws SAXException, IOException, ParserConfigurationException {
		parser.parse(nestedFolderWithDuplicates);
		HashMap<String, HashMap<String, Annotation>> allAnnotations = parser.getAnnotations();
		HashMap<String, Annotation> annotations1 = allAnnotations.get("test-input1.txt");
		HashMap<String, Annotation> annotations2 = allAnnotations.get("test-input_m.txt");
		Annotation annotation1 = annotations1.get("GO:0043336");
		Annotation annotation2 = annotations2.get("GO:0043336");
		
		assertEquals(2, allAnnotations.size());
		assertEquals("error matching cui", "GO:0043336", annotation1.getConceptId());
		assertEquals("error matching file", "test-input1.txt", annotation1.getDocumentID());
		assertEquals("error matching count", 1, annotation1.getCount());
		assertEquals("error matching cui", "GO:0043336", annotation2.getConceptId());
		assertEquals("error matching file", "test-input_m.txt", annotation2.getDocumentID());
		assertEquals("error matching count", 2, annotation2.getCount());
	}
	
	@Test (expected = ParserConfigurationException.class)
	public void test_8_errorThrownWhenNoTagForTheConceptIdIsSet() throws SAXException, IOException, ParserConfigurationException {
		CTakesParser parser = new CTakesParser();
		parser.setExtractorName("GO");
		parser.setUmlsInformationTag("org.apache.ctakes.typesystem.type.refsem.UmlsConcept");
		
		parser.parse(workingFile);		
	}
	
	@Ignore
	@Test 
	public void test_saveResultToRable() {
		
	}

	
	@Ignore
	@Test
	public void test_99_parseBigXMLResult() {
		fail("not implemented");
	}
	
	
}
