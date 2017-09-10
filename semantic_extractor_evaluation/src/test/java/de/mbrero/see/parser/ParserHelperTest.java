package de.mbrero.see.parser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URL;

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
		String id = "10088";
		String ontology = "NCBI";
		
		helper.setUmlsMappingSource(mappingSource);
		
		assertEquals("error mapping umls cui to source vocabulary id", "C100", 
				helper.getCuiForOntologyId(id, ontology));
	}
	
	@Test
	public void testFindOntologyForCUI() throws IOException {
		String mappingSource = "mapping/mapping.rrf";
		String cui = "C100";
		String ontology = "NCBI";
		
		helper.setUmlsMappingSource(mappingSource);
		
		assertEquals("error mapping umls cui to ontology 1", ontology, 
				helper.getOntologyForCui(cui));
		
		cui = "C0001076";
		ontology = "GO";
		
		helper.setUmlsMappingSource(mappingSource);
		
		assertEquals("error mapping umls cui to ontology 2", ontology, 
				helper.getOntologyForCui(cui));
	}

}
