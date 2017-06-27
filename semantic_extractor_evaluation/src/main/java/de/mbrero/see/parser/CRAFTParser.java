package de.mbrero.see.parser;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.mbrero.see.persistance.dto.Annotation;

public class CRAFTParser extends AbstractParser {
	private final String DOCUMENT_ID_TAG = "annotations";
	private final String DOCUMENT_ID_NODE = "textSource";
	private final String PREFERRED_TEXT_TAG = "preferredText";
	private final String MATCHED_CHUNK_TAG = "mentionClass";
	
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

		annotation.setOntology((extractOntology(conceptId).toUpperCase()));
		annotation.setConceptId(conceptId);
		annotation.setPreferredText(elem.getAttribute(""));
		annotation.setDocumentID(getAnnotatedFileName());
		//annotation.setMatchedChunk(matchedChunk);
		annotation.setExtractor(extractorName);
		annotation.setCount(1);

		return annotation;
		
	}
	
	private String extractOntology(String conceptId)
	{
		return conceptId.split(":")[0].toUpperCase();
	}
	
}
