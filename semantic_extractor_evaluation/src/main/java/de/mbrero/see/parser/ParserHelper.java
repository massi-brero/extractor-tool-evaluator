package de.mbrero.see.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Contains some delegate methods that are useful for<br>
 * the parsing process.
 * 
 * @author massi.brero@gmail.com
 *
 */
public final class ParserHelper {
	private File umlsMappingSource = null;
	private final int POS_CUI = 0;
	
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
	@SuppressWarnings("resource")
	public String getCuiForOntologyId(String vid, String ontology) throws IOException {
		String id = "";
		
		if (!vid.isEmpty() && !ontology.isEmpty()) {
			Stream<String> stream = Files.lines(Paths.get(getUmlsMappingSource().getAbsolutePath()));
			
			String result = stream
				    .filter(line -> line.contains(ontology) && line.contains(vid))
				    .findFirst()
				    .orElse("");
	    	stream.close();
	    	 
	    	if(!result.isEmpty())
	    	{
	    		id = result.substring(1, result.indexOf("|"));	    		
	    	}
	    	
		}

		return id;
		
	}
	
	public File getUmlsMappingSource() {
		
		if(umlsMappingSource == null) {
			return new File(getClass().getClassLoader().getResource("mapping/MRCONSO.RRF").getFile());			
		} 
		
		return umlsMappingSource;
	}

	public void setUmlsMappingSource(File mappingSource) {
		umlsMappingSource = mappingSource;
	}

}
