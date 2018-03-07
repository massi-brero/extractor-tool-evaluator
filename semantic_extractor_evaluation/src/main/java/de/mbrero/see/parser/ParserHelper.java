package de.mbrero.see.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Contains some delegate methods that are useful for<br>
 * the parsing process.
 * 
 * @author massimiliano.brero@studium.fernuni-hagen.de
 *
 */
public final class ParserHelper {
	/*
	 * The location of the MRCONSO.RFF file is an InputStream, so the identification
	 * will work even if the MRCONSO.RFF file is put as a resource in the same jar as the application.
	 */
	private InputStream umlsMappingSourceAsStream = null;
	/*
	 * Standard location of the MRCONSO.rff, if no custom value is set.
	 */
	private final String MRCONSO_URI = "mapping/MRCONSO.RRF";
	/*
	 * Defines where the ontology information can be found in the MRCONSO.RFF PSV
	 * file can be found.
	 */
	private final int POS_ONTOLOGY = 11;
	
	private static HashMap<String, String> sourceCuiIdMapping = new HashMap<>();
	
	
	/*
	 * Defines where the source id information of the original ontology can be found in the 
	 * MRCONSO.RFF PSV file can be found.
	 */
	private final int POS_SOURCE_ID= 9;
	
	/*
	 * Defines where UMLS' CUI id information can be found in the 
	 * MRCONSO.RFF PSV file can be found.
	 */
	private final int POS_CUI= 0;

	
	/**
	 * Returns a UMLS ID (CUI) that maps the given id from the source <br>
	 * ontology. Of course it will only return a value if the given ontology is
	 * part of the metathesaurus.
	 * 
	 * Since the cardinality of UMLS id and vocabulary id is 1:n the result will<br>
	 * be unambiguous.
	 * 
	 * 
	 * @param String vid id from the original source
	 * @param ontology
	 * @return
	 * @throws IOException
	 */
	public String getCuiForOntologyId(String vid, String ontology) throws IOException {
		
		HashMap<String, String> map = getIDMappingForSourceIDs();
		
		return map.get(vid);
		
	}
	
	/**
	 * Returns the Ontology the CUI from UMLS refers to. Of course it will only return a value if the given ontology is
	 * part of the Metathesaurus.
	 * 
	 * 
	 * @return String
	 * @throws IOException 
	 */
	public String getOntologyForCui(String cui) throws IOException {
		String ontology = "";
		
		
		if (!cui.isEmpty()) {
			BufferedReader bufferedReader = getUMLSMappingReader();
			Stream<String> stream =  bufferedReader.lines();
			
			String result = stream
						    .filter(line -> line.contains(cui))
						    .findFirst()
						    .orElse("");
	    	
			stream.close();
	    	bufferedReader.close();
   	
	    	 
	    	if(!result.isEmpty())
	    	{
	    		ontology = result.split("\\|")[POS_ONTOLOGY];	    		
	    	}
	    	
		}

		return ontology;
		
	}
	
	/**
	 * Gets the ids that correspond to the original source ids from the ontologies.<br>
	 * The corresponding dictionary is {@link ParserHelper#MRCONSO_URI}. This method is generic but<br>
	 * in the prototype it's used to map the UMLS CUI to the concept' original ontology id.
	 * @return
	 * @throws IOException
	 */
	public HashMap<String, String> getIDMappingForSourceIDs() throws IOException
	{
		
		if (sourceCuiIdMapping.size() == 0) {
			
			BufferedReader bufferedReader = getUMLSMappingReader();
			Stream<String> stream = bufferedReader.lines();
			
			stream.forEach((line) -> {
				String[] conceptInformation = line.split("\\|");
				sourceCuiIdMapping.put(conceptInformation[POS_SOURCE_ID], conceptInformation[POS_CUI]);
			});
			
			stream.close();
			bufferedReader.close();
			
		}
		
		return sourceCuiIdMapping;
	}
	
	/**
	 * The stream from where the MRCONSO file of the UMLS installation
	 * is located.
	 * @return InputStream 
	 * 
	 */
	public InputStream getUmlsMappingSourceAsStream() {
		
		try {
			if(umlsMappingSourceAsStream == null) {
				umlsMappingSourceAsStream = getClass().getClassLoader().getResourceAsStream(MRCONSO_URI);			
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return umlsMappingSourceAsStream;
	}

	/**
	 * Sets the MRCONSO resource from the UMLS installation as stream.
	 * 
	 * @param sourceURI
	 */
	public void setUmlsMappingSourceAsStream(String sourceURI) {
		try {
			umlsMappingSourceAsStream = getClass().getClassLoader().getResourceAsStream(sourceURI);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return {@link BufferedReader}
	 */
	private BufferedReader getUMLSMappingReader() {
		InputStreamReader isReader= new InputStreamReader(getUmlsMappingSourceAsStream());
		BufferedReader buffReader = new BufferedReader(isReader);
		
		return buffReader;
	}

}
