package de.mbrero.see.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

/**
 * Contains some delegate methods that are useful for<br>
 * the parsing process.
 * 
 * @author massi.brero@gmail.com
 *
 */
public final class ParserHelper {
	/*
	 * The location of the MRCONSO.RFF file is an InputStream, so the identification
	 * will work even if the MRCONSO.RFF file is put as a resource in the same jar as the application.
	 */
	private InputStream umlsMappingSource = null;
	/*
	 * Standard location of the MRCONSO.rff, if no custom value is set.
	 */
	private final String MRCONCO_URI = "mapping/MRCONSO.RRF";
	/*
	 * Defines where the ontology information can be found in the MRCONSO.RFF PSV
	 * file can be found.
	 */
	private final int POS_ONTOLOGY = 11;
	
	/**
	 * Returns a UMLS ID (CUI) that maps the given id from the source <br>
	 * ontology. Of course it will only return a value if the given ontology is
	 * part of the metathesaurus.
	 * 
	 * Since the cardinality of UMLS id and vocabulary id is 1:n the result will<br>
	 * be unambiguous.
	 * 
	 * @return String
	 * @throws IOException 
	 */
	public String getCuiForOntologyId(String vid, String ontology) throws IOException {
		String id = "";
		
		if (!vid.isEmpty() && !ontology.isEmpty()) {
			Stream<String> stream = (new BufferedReader(new InputStreamReader(getUmlsMappingSource()))).lines();
			
			String result = stream
						    .filter(line -> line.contains(ontology) && line.contains(vid))
						    .findFirst()
						    .orElse("");
	    	stream.close();
	    	 
	    	if(!result.isEmpty())
	    	{
	    		id = result.substring(0, result.indexOf("|"));	    		
	    	}
	    	
		}

		return id;
		
	}
	
	/**
	 * Returns the Ontology the CUI from UMLS refers to. Of course it will only return a value if the given ontology is
	 * part of the metathesaurus.
	 * 
	 * 
	 * @return String
	 * @throws IOException 
	 */
	public String getOntologyForCui(String cui) throws IOException {
		String ontology = "";
		InputStreamReader isReader= new InputStreamReader(getUmlsMappingSource());
		BufferedReader buffReader = new BufferedReader(isReader);
		
		
		if (!cui.isEmpty()) {
			Stream<String> stream = buffReader.lines();
			
			String result = stream
						    .filter(line -> line.contains(cui))
						    .findFirst()
						    .orElse("");
	    	
			stream.close();
	    	buffReader.close();
	    	isReader.close();
	    	
	    	 
	    	if(!result.isEmpty())
	    	{
	    		ontology = result.split("\\|")[POS_ONTOLOGY];	    		
	    	}
	    	
		}

		return ontology;
		
	}
	
	/**
	 * The stream from where the MRCONSO file of the UMLS installation
	 * is located.
	 * @return InputStream 
	 * 
	 */
	public InputStream getUmlsMappingSource() {
		
		if(umlsMappingSource == null) {
			return getClass().getClassLoader().getResourceAsStream(MRCONCO_URI);			
		} 
		
		return umlsMappingSource;
	}

	/**
	 * Sets the MRCONSO resource from the UMLS installation as stream.
	 * 
	 * @param sourceURI
	 */
	public void setUmlsMappingSource(String sourceURI) {
		umlsMappingSource = getClass().getClassLoader().getResourceAsStream(sourceURI);
	}

}
