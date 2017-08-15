/**
 * 
 */
package de.mbrero.see.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

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
	private ParserHelper helper = null;


	@Before
	public void setUp()
	{
		parser = (CRAFTParser)ParserFactory.getInstance(ParserType.CRAFT);
		workingFile = new File(getClass().getClassLoader().getResource("texts/craft/ncbi/craft-1.xml").getFile());
		helper = new ParserHelper();
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
		
		Annotation annotation = annotations.get("10088");
		
		assertEquals(8, annotations.size());
		assertEquals("error matching concept id", "10088", annotation.getConceptId());
		assertEquals("error matching ontology", Ontology.NCBI.name(), annotation.getOntology());
		assertEquals("error matching preferred text", "", annotation.getPreferredText());
		assertEquals("error matching file", "11532192.txt", annotation.getDocumentID());
		assertEquals("error matching extractor", ParserType.CRAFT.toString(), annotation.getExtractor());
		assertEquals("error matching count", 148, annotation.getCount());
		
		Annotation annotation2 = annotations.get("960666");
		assertEquals("error matching count annotation2", 1, annotation2.getCount());
		
	}
	
	@Test
	//@Ignore
	public void test_3_parseTinyXMLResultWithCUICheck() throws Exception {
		
		parser.parse(workingFile);
		HashMap<String, HashMap<String, Annotation>> allAnnotations = parser.getAnnotations();
		HashMap<String, Annotation> annotations = allAnnotations.get("11532192.txt");
		
		Annotation annotation = annotations.get("10088");
		
		assertEquals(8, annotations.size());
		assertEquals("error matching concept id", "10088", annotation.getConceptId());
		assertEquals("error matching ontology", Ontology.NCBI.name(), annotation.getOntology());
		assertEquals("error matching umls cui", "0026809", annotation.getCui());
		assertEquals("error matching preferred text", "", annotation.getPreferredText());
		assertEquals("error matching file", "11532192.txt", annotation.getDocumentID());
		assertEquals("error matching extractor", ParserType.CRAFT.toString(), annotation.getExtractor());
		assertEquals("error matching count", 148, annotation.getCount());
		
		Annotation annotation2 = annotations.get("960666");
		assertEquals("error matching count annotation2", 1, annotation2.getCount());
		
	}
	
	@Test
	public void test_4_concepMapping() throws IOException {
		File mappingSource = new File(getClass().getClassLoader().getResource("mapping/mapping.rrf").getFile());
		String id = "10088";
		String ontology = "NCBI";
		
		helper.setUmlsMappingSource(mappingSource);
		
		assertEquals("error mapping umls cui to source vocabulary id", "100", 
				helper.getCuiForOntologyId(id, ontology));
	}

	
}
