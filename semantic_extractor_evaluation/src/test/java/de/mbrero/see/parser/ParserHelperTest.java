package de.mbrero.see.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class ParserHelperTest {
	
	private ParserHelper helper = null;
	
	@Before
	public void setUp()
	{
		helper = new ParserHelper();
	}
	
	@Test
	public void testConceptMapping() throws IOException {
		String mappingSource = "mapping/mapping.rrf";
		String id1 = "10088";
		String id2 = "10232";
		String id3 = "40050";

		String ontology = "NCBI";
		
		helper.setUmlsMappingSourceAsStream(mappingSource);
		
		assertEquals("error mapping umls cui to source vocabulary id", "C0026809", 
				helper.getCuiForOntologyId(id1, ontology));
		assertEquals("error mapping umls cui to source vocabulary id", "C0000881", 
				helper.getCuiForOntologyId(id2, ontology));
		assertEquals("error mapping umls cui to source vocabulary id", "C0001749", 
				helper.getCuiForOntologyId(id3, ontology));
	}
	
	@Test
	public void testFindOntologyForCUI() throws IOException {
		String mappingSource = "mapping/mapping.rrf";
		String cui = "C100";
		String ontology = "NCBI";
		
		helper.setUmlsMappingSourceAsStream(mappingSource);
		
		assertEquals("error mapping umls cui to ontology 1", ontology, 
				helper.getOntologyForCui(cui));
		
		cui = "C0001076";
		ontology = "GO";
		
		helper.setUmlsMappingSourceAsStream(mappingSource);
		
		assertEquals("error mapping umls cui to ontology 2", ontology, 
				helper.getOntologyForCui(cui));
	}
	
	@Test
	public void testBuildSourceIdOnCuiMapping() throws Exception {
		HashMap<String, String>	map = helper.getIDMappingForSourceIDs();
				
		assertTrue(map.size() > 0);
	}

}
