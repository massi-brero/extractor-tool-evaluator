package de.mbrero.see.parser;

import java.io.File;

/**
 * Contains some delegate methods that are useful for<br>
 * the parsing process.
 * 
 * @author massi.brero@gmail.com
 *
 */
public final class ParserHelper {
	private static File umlsMappingSource = null;
	
	/**
	 * Returns a UMLS ID (CUI) that maps the given id from the source <br>
	 * ontology. Of courseit will only return a value if the given ontology is
	 * part of the metathesaurus.
	 * 
	 * Since the cardinality of UMLS id and vocabulary id is 1:n the result will<br>
	 * be unambiguous.
	 * 
	 * @return String
	 */
	public static String getCuiForOntologyId(String vid, String ontology) {
		String id = "";
		
		if (!vid.isEmpty() && !ontology.isEmpty()) {
			
		}
		
		return id;
		
	}
	
	public static File getUmlsMappingSource() {
		
		if(getUmlsMappingSource() == null) {
			return new File((new Object()).getClass().getClassLoader().getResource("mapping/MRCONSO.RRF").getFile());			
		} 
		
		return umlsMappingSource;
	}

	public static void setUmlsMappingSource(File mappingSource) {
		umlsMappingSource = mappingSource;
	}

}
