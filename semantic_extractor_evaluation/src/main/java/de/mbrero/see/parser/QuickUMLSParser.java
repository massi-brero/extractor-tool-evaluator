package de.mbrero.see.parser;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import de.mbrero.see.persistance.dto.Annotation;

public class QuickUMLSParser extends AbstractParser {

	@Override
	protected Annotation buildAnnotation(Element elem, String cui)
			throws ParserConfigurationException, SAXException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getAnnotatedFileName() throws ParserConfigurationException, SAXException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
