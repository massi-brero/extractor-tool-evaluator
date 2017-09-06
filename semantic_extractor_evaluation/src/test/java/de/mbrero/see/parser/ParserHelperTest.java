package de.mbrero.see.parser;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

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
		File mappingSource = new File(getClass().getClassLoader().getResource("mapping/mapping.rrf").getFile());
		String id = "10088";
		String ontology = "NCBI";
		
		helper.setUmlsMappingSource(mappingSource);
		
		assertEquals("error mapping umls cui to source vocabulary id", "C100", 
				helper.getCuiForOntologyId(id, ontology));
	}
	
	@Test
	public void testFindOntologyForCUI() throws IOException {
		File mappingSource = new File(getClass().getClassLoader().getResource("mapping/mapping.rrf").getFile());
		String cui = "C100";
		String ontology = "NCBI";
		
		helper.setUmlsMappingSource(mappingSource);
		
		assertEquals("error mapping umls cui to ontology 1", ontology, 
				helper.getOntologyForCui(cui));
		
		cui = "C0001076";
		ontology = "GO";
		
		assertEquals("error mapping umls cui to ontology21", ontology, 
				helper.getOntologyForCui(cui));
	}

}
