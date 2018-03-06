package de.mbrero.see.parser;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.mbrero.see.persistance.dto.Annotation;

/**
 * First implementation for the test of the cTakes extractor. Currently not used.
 * 
 * @author massi.brero@gmail.com
 *
 */
public class CTakesParser extends AbstractParser {
	private final String DOCUMENT_ID_TAG = "org.apache.ctakes.typesystem.type.structured.DocumentID";
	private final String DOCUMENT_ID_NODE = "documentID";
	private final String ONTOLOGY_NODE = "codingScheme";
	//private final String PREFERRED_TEXT_TAG = "preferredText";

	
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
			throws ParserConfigurationException, SAXException, IOException {
		Annotation annotation = new Annotation();

		String ontology = elem.getAttribute(ONTOLOGY_NODE).toUpperCase();
		annotation.setOntology(ontology);
		annotation.setCui(conceptId);
		annotation.setPreferredText(""); //not used at the moment
		annotation.setDocumentID(getAnnotatedFileName());
		annotation.setExtractor(extractorName);
		annotation.setMatchedChunk(elem.getTextContent());
		annotation.setCount(1);

		return annotation;
	}

}
