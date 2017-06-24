package de.mbrero.see.parser;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.mbrero.see.persistance.dto.Annotation;

public class CTakesParser extends AbstractParser {
	
	@Override
	protected String getAnnotatedFileName() throws ParserConfigurationException, SAXException, IOException {

		String filename = "";

		NodeList nList = getNodeList(textInformationTag);
		Node node = nList.item(0);

		if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
			Element elem = (Element) node;
			filename = elem.getAttribute("documentID");
		}

		return filename;

	}
	
	@Override
	protected Annotation buildAnnotation(Element elem, String cui)
			throws ParserConfigurationException, SAXException, IOException {
		Annotation annotation = new Annotation();

		annotation.setOntology((elem.getAttribute("codingScheme").toUpperCase()));
		annotation.setCui(cui);
		annotation.setPreferredText(elem.getAttribute("preferredText"));
		annotation.setDocumentID(getAnnotatedFileName());
		annotation.setExtractor(extractorName);
		annotation.setCount(1);

		return annotation;
	}

}
