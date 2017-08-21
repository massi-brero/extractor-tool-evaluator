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

	@Before
	public void setUp()
	{
		// setup  parser	
		parser = new MetaMapParser();
		parser.setExtractorName("METAMAP");
		parser.setIdInformationTag("Candidate");
		parser.setConceptIdentifierNode("CandidateCUI");
		
		//set up working files
		workingFile = new File(getClass().getClassLoader().getResource("mm_test/input/output.short.xml").getFile());
			
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
	
}
