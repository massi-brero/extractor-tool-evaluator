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
 * @author massi.brero@gmail.com
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CRAFTParserTest {
	private CRAFTParser parser = null;
	private File workingFile = null;
	private File multipleAnnotationsFile = null;
	private File folder = null;
	private File nestedFolder = null;
	private File nestedFolderWithDuplicates = null;

	@Before
	public void setUp()
	{
		parser = (CRAFTParser)ParserFactory.instantiateParser(ParserType.CRAFT);
		workingFile = new File(getClass().getClassLoader().getResource("texts/craft/craft-1.xml").getFile());
	}
	
	@Test
	public void test_2_textFileIsProcessed() throws SAXException, IOException, ParserConfigurationException {
		
		parser.parse(workingFile);
		HashMap<String, HashMap<String, Annotation>> allAnnotations = parser.getAnnotations();
		
		assertEquals(1, allAnnotations.size());
		assertNotNull(allAnnotations.get("11597317.txt"));
	}
		
		
	
	@Test
	public void test_3_parseTinyXMLResult() throws SAXException, IOException, ParserConfigurationException {
		
		parser.parse(workingFile);
		HashMap<String, HashMap<String, Annotation>> allAnnotations = parser.getAnnotations();
		HashMap<String, Annotation> annotations = allAnnotations.get("test-input.txt");
		
		Annotation annotation = annotations.get("GO:0005634");
		
		assertEquals(3, annotations.size());
		assertEquals("error matching cui", "GO:0005634", annotation.getConceptId());
		assertEquals("error matching ontology", Ontology.GO.name(), annotation.getOntology());
		assertEquals("error matching preferred text", "", annotation.getPreferredText());
		//assertEquals("error matching file", "craft-1.txt.xml", annotation.getDocumentID());
		assertEquals("error matching extractor", ParserType.CRAFT.toString(), annotation.getExtractor());
		assertEquals("error matching count", 2, annotation.getCount());
		
		Annotation annotation2 = annotations.get("GO:0005737");
		assertEquals("error matching count annotation2", 1, annotation2.getCount());
		
	}

	
}
