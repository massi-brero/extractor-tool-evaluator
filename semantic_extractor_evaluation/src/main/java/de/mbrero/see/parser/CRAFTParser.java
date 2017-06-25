package de.mbrero.see.parser;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import de.mbrero.see.persistance.dto.Annotation;

public class CRAFTParser extends AbstractParser {

	@Override
	protected Annotation buildAnnotation(Element elem, String cui)
			throws ParserConfigurationException, SAXException, IOException {
		Annotation annotation = new Annotation();

//		annotation.setOntology((elem.getAttribute(ONTOLOGY_TAG).toUpperCase()));
//		annotation.setConceptId(cui);
//		annotation.setPreferredText(elem.getAttribute(PREFERRED_TEXT_TAG));
//		annotation.setDocumentID(getAnnotatedFileName());
//		annotation.setExtractor(extractorName);
//		annotation.setCount(1);
//
//		return annotation;
		
		return null;
	}

	@Override
	protected String getAnnotatedFileName() throws ParserConfigurationException, SAXException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
