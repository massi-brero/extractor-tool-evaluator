package de.mbrero.see.parser;

import java.io.IOException;

import javax.naming.ConfigurationException;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.mbrero.see.persistance.dto.Annotation;
import types.Ontology;

/**
 * 
 * This class parses the CRAFT goldstandard so it may be used with the extracted annotations
 * lists from the extractor software tested and work as a rerefernce point.
 * 
 * Since the files in this gold standard sometimes lack the information about the ontology the 
 * concepts were derived from, the extractOntology method will get the ontology name from the folder name the 
 * annotation file is stored into.
 * 
 * This is also the case in the original CRAFT folder structure. So beware to store  annotation
 * files in folders bearing the name of the ontology the concepts are coming from.
 * 
 * @author massi.brero@gmail.com
 *
 */
public class CRAFTParser extends AbstractParser {
	
	private final String DOCUMENT_ID_TAG = "annotations";
	private final String DOCUMENT_ID_NODE = "textSource";
	//private final String PREFERRED_TEXT_TAG = "preferredText";
	//private final String MATCHED_CHUNK_TAG = "mentionClass";
	
	public CRAFTParser() {
		setUmlsInformationTag("org.apache.ctakes.typesystem.type.refsem.UmlsConcept");
		setConceptIdentifierNode("code");
	}
	
	@Override
	protected String getAnnotatedFileName() throws ParserConfigurationException, SAXException, IOException {

		String filename = "";

		NodeList nList = getNodeList(DOCUMENT_ID_TAG);
		Node node = nList.item(0);

		if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
			Element elem = (Element) node;
			filename = elem.getAttribute(DOCUMENT_ID_NODE);
		}

		return filename;

	}

	@Override
	protected Annotation buildAnnotation(Element elem, String conceptId)
			throws ParserConfigurationException, SAXException, IOException, ConfigurationException {
		Annotation annotation = new Annotation();

		String ontology = extractOntology(conceptId);
		annotation.setOntology(ontology);
		annotation.setConceptId(ontology + "_" + conceptId);
		annotation.setPreferredText(elem.getAttribute(""));
		annotation.setDocumentID(getAnnotatedFileName());
		//annotation.setMatchedChunk(matchedChunk);
		annotation.setExtractor(extractorName);
		annotation.setCount(1);

		return annotation;
		
	}
	
	private String extractOntology(String conceptId) throws ConfigurationException
	{
		String ontology = getInputFile().getParentFile().getName().toUpperCase();
		
		try {
			if (Ontology.valueOf(ontology) == null) {
				return "";
			}
		} catch (Exception e) {
			throw new ConfigurationException("Please name the folder after the ontology the concespts are derived from.");
		}
		
		return ontology;
	}
	
}
