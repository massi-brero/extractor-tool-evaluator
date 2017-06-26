package de.mbrero.see.parser;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.mbrero.see.persistance.dto.Annotation;

public class CTakesParser extends AbstractParser {
	private final String DOCUMENT_ID_TAG = "org.apache.ctakes.typesystem.type.structured.DocumentID";
	private final String DOCUMENT_ID_NODE = "documentID";
	private final String ONTOLOGY_TAG = "codingScheme";
	private final String PREFERRED_TEXT_TAG = "preferredText";

	
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
	protected Annotation buildAnnotation(Element elem, String cui)
			throws ParserConfigurationException, SAXException, IOException {
		Annotation annotation = new Annotation();

		annotation.setOntology((elem.getAttribute(ONTOLOGY_TAG).toUpperCase()));
		annotation.setConceptId(cui);
		annotation.setPreferredText(elem.getAttribute(PREFERRED_TEXT_TAG));
		annotation.setDocumentID(getAnnotatedFileName());
		annotation.setExtractor(extractorName);
		annotation.setCount(1);

		return annotation;
	}

}
