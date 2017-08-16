/**
 * 
 */
package de.mbrero.see.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

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
		parser.setIdInformationTag("org.apache.ctakes.typesystem.type.refsem.UmlsConcept");
		parser.setConceptIdentifierNode("cui");
		
		//set up working files
		workingFile = new File(getClass().getClassLoader().getResource("texts/output.short.xml").getFile());
		multipleAnnotationsFile = new File(getClass().getClassLoader().getResource("texts/output.short.multiple.xml").getFile());
		folder = new File(getClass().getClassLoader().getResource("texts/folder1").getFile());
		nestedFolder = new File(getClass().getClassLoader().getResource("texts/folder2").getFile());
		nestedFolderWithDuplicates = new File(getClass().getClassLoader().getResource("texts/folder4").getFile());
	
	}
	
	@Test
	public void test_2_textFileIsProcessed() throws Exception {
		
		parser.parse(workingFile);
		HashMap<String, HashMap<String, Annotation>> allAnnotations = parser.getAnnotations();
		
		assertEquals(1, allAnnotations.size());
		assertNotNull(allAnnotations.get("test-input.txt"));
	}
		
		
	
	@Test
	public void test_3_parseTinyXMLResult() throws Exception {
		
		parser.parse(workingFile);
		HashMap<String, HashMap<String, Annotation>> allAnnotations = parser.getAnnotations();
		HashMap<String, Annotation> annotations = allAnnotations.get("test-input.txt");
		
		Annotation annotation = annotations.get("C0086418");
		
		assertEquals(2, annotations.size());
		assertEquals("error matching cui", "C0086418", annotation.getCui());
		assertEquals("error matching ontology", Ontology.NCBI.name(), annotation.getOntology());
		//assertEquals("error matching preferred text", "test", annotation.getPreferredText());
		assertEquals("error matching file", "test-input.txt", annotation.getDocumentID());
		assertEquals("error matching extractor", ParserType.CTAKES.toString(), annotation.getExtractor());
		assertEquals("error matching count", 1, annotation.getCount());
		
	}

	
	@Test
	public void test_4_parseResultWithDuplicateAnnotations() throws Exception {
		parser.parse(multipleAnnotationsFile);
		HashMap<String, HashMap<String, Annotation>> allAnnotations = parser.getAnnotations();
		HashMap<String, Annotation> annotations = allAnnotations.get("test-input_m.txt");
		
		Annotation annotation = annotations.get("C1622890");
		
		assertEquals("error matching cui", "C1622890", annotation.getCui());
		assertEquals("error matching file", "test-input_m.txt", annotation.getDocumentID());
		assertEquals("error matching count", 2, annotation.getCount());
	}
	
	@Test 
	public void test_5_parseAllFilesInDirectory() throws Exception {
		parser.parse(folder);
		HashMap<String, HashMap<String, Annotation>> allAnnotations = parser.getAnnotations();
		HashMap<String, Annotation> annotations1 = allAnnotations.get("test-input1.txt");
		HashMap<String, Annotation> annotations2 = allAnnotations.get("test-input2.txt");
		Annotation annotation1 = annotations1.get("C1111111");
		Annotation annotation2 = annotations2.get("C0086418");
		
		assertEquals(2, allAnnotations.size());
		assertEquals("error matching cui", "C1111111", annotation1.getCui());
		assertEquals("error matching file", "test-input1.txt", annotation1.getDocumentID());
		assertEquals("error matching count", 1, annotation1.getCount());
		assertEquals("error matching cui", "C0086418", annotation2.getCui());
		assertEquals("error matching file", "test-input2.txt", annotation2.getDocumentID());
		assertEquals("error matching count", 1, annotation2.getCount());
	}
	
	@Test 
	public void test_6_parseFilesInNestedDirectory() throws Exception {
		parser.parse(nestedFolder);
		HashMap<String, HashMap<String, Annotation>> allAnnotations = parser.getAnnotations();
		HashMap<String, Annotation> annotations1 = allAnnotations.get("test-input1.txt");
		HashMap<String, Annotation> annotations2 = allAnnotations.get("test-input2.txt");
		Annotation annotation1 = annotations1.get("C1111111");
		Annotation annotation2 = annotations2.get("C0086418");
		
		assertEquals(2, allAnnotations.size());
		assertEquals("error matching cui", "C1111111", annotation1.getCui());
		assertEquals("error matching file", "test-input1.txt", annotation1.getDocumentID());
		assertEquals("error matching count", 1, annotation1.getCount());
		assertEquals("error matching cui", "C0086418", annotation2.getCui());
		assertEquals("error matching file", "test-input2.txt", annotation2.getDocumentID());
		assertEquals("error matching count", 1, annotation2.getCount());
	}
	
	@Test 
	public void test_7_parseFilesInNestedDirectoryWithDuplicateCui() throws Exception {
		parser.parse(nestedFolderWithDuplicates);
		HashMap<String, HashMap<String, Annotation>> allAnnotations = parser.getAnnotations();
		HashMap<String, Annotation> annotations1 = allAnnotations.get("test-input1.txt");
		HashMap<String, Annotation> annotations2 = allAnnotations.get("test-input_m.txt");
		Annotation annotation1 = annotations1.get("C1111111");
		Annotation annotation2 = annotations2.get("C2222222");
		
		assertEquals(2, allAnnotations.size());
		assertEquals("error matching cui", "C1111111", annotation1.getCui());
		assertEquals("error matching file", "test-input1.txt", annotation1.getDocumentID());
		assertEquals("error matching count", 1, annotation1.getCount());
		assertEquals("error matching cui", "C2222222", annotation2.getCui());
		assertEquals("error matching file", "test-input_m.txt", annotation2.getDocumentID());
		assertEquals("error matching count", 2, annotation2.getCount());
	}
	
	@Test (expected = ParserConfigurationException.class)
	public void test_8_errorThrownWhenNoTagForTheConceptIdIsSet() throws Exception {
		CTakesParser parser = new CTakesParser();
		parser.setExtractorName("GO");
		parser.setIdInformationTag("org.apache.ctakes.typesystem.type.refsem.UmlsConcept");
		
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
