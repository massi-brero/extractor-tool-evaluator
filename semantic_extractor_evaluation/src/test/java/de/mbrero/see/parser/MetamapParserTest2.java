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
public class MetamapParserTest2 {
	private MetaMapParser parser = null;
	private File workingFile = null;
	private File multipleFile = null;

	@Before
	public void setUp()
	{
		// setup  parser	
		parser = new MetaMapParser();
		parser.setExtractorName("METAMAP");
		parser.setIdInformationTag("Candidate");
		parser.setConceptIdentifierNode("CandidateCUI");
		
		//set up working files
		workingFile = new File(getClass().getClassLoader().getResource("mm_test/input/res.short.xml").getFile());
		multipleFile = new File(getClass().getClassLoader().getResource("mm_test/input/res.multiple.xml").getFile());
			
	}
	
	@Test
	public void test_2_textFileIsProcessed() throws Exception {
		
		parser.parse(workingFile);
		HashMap<String, HashMap<String, Annotation>> allAnnotations = parser.getAnnotations();
		
		assertEquals(1, allAnnotations.size());
		assertNotNull(allAnnotations.get("11532192.txt"));
	}
		
		
	
	@Test
	public void test_3_parseTinyXMLResult() throws Exception {
		
		parser.parse(workingFile);
		HashMap<String, HashMap<String, Annotation>> allAnnotations = parser.getAnnotations();
		HashMap<String, Annotation> annotations = allAnnotations.get("11532192.txt");
		
		Annotation annotation1 = annotations.get("C0025914");
		
		assertEquals(2, annotations.size());
		assertEquals("error matching cui", "C0025914", annotation1.getCui());
		assertEquals("error matching ontology", Ontology.NCBI.name(), annotation1.getOntology());
		assertEquals("error matching extractor", ParserType.METAMAP.toString(), annotation1.getExtractor());
		assertEquals("error matching count", 2, annotation1.getCount());
		assertEquals("error matching file", "11532192.txt", annotation1.getDocumentID());
		
		Annotation annotation2 = annotations.get("C0026809");

		assertEquals("error matching cui", "C0026809", annotation2.getCui());
		assertEquals("error matching ontology", Ontology.NCBI.name(), annotation2.getOntology());
		assertEquals("error matching extractor", ParserType.METAMAP.toString(), annotation2.getExtractor());
		assertEquals("error matching count", 1, annotation2.getCount());
		assertEquals("error matching file", "11532192.txt", annotation2.getDocumentID());
		
	}
	
	@Test
	public void test_3_parseMUltipleConceptXMLResult() throws Exception {
		
		parser.parse(multipleFile);
		HashMap<String, HashMap<String, Annotation>> allAnnotations = parser.getAnnotations();
		HashMap<String, Annotation> annotations = allAnnotations.get("11532192.txt");
		
		Annotation annotation1 = annotations.get("C0025914");
		
		assertEquals(3, annotations.size());
		assertEquals("error matching cui", "C0025914", annotation1.getCui());
		assertEquals("error matching ontology", Ontology.NCBI.name(), annotation1.getOntology());
		assertEquals("error matching extractor", ParserType.METAMAP.toString(), annotation1.getExtractor());
		assertEquals("error matching count", 2, annotation1.getCount());
		assertEquals("error matching file", "11532192.txt", annotation1.getDocumentID());
		
		Annotation annotation2 = annotations.get("C0026809");

		assertEquals("error matching cui", "C0026809", annotation2.getCui());
		assertEquals("error matching ontology", Ontology.NCBI.name(), annotation2.getOntology());
		assertEquals("error matching extractor", ParserType.METAMAP.toString(), annotation2.getExtractor());
		assertEquals("error matching count", 1, annotation2.getCount());
		assertEquals("error matching file", "11532192.txt", annotation2.getDocumentID());
	}
		
	
}
