package de.mbrero.see.parser;

import javax.xml.bind.TypeConstraintException;

import types.ParserType;

public class ParserFactory {
	
	/*
	 * cTakes parameters
	 */
	private final static String CTAKES_UMLS_INFORMATION_TAG = "org.apache.ctakes.typesystem.type.refsem.UmlsConcept";
	private final static String CTAKES_TEXT_INFORMATION_TAG = "org.apache.ctakes.typesystem.type.structured.DocumentID";
	private final static String CTAKES_CONCEPT_NODE = "oid";


	/**
	 * Returns a configured parser for extracting the found annotations in a text file.
	 * 
	 * @param type
	 * @return
	 * @throws TypeConstraintException
	 */
	public static AnnotationParser instantiateParser(ParserType type) throws TypeConstraintException  {
		return createParser(type);
	}
	

	private static AnnotationParser createParser(ParserType type) throws TypeConstraintException {
		AnnotationParser parser = null;
		
		switch (type) {
		case CTAKES:
			parser = setUpCTakesParser(type);
			break;
		case QUICKUMLS:
			
			break;
		case METAMAP:
			
			break;
		case CRAFT:
			
			break;
		default:
			throw new TypeConstraintException("No parser exists for this type!");
		}
		
		return parser; 
		
	}


	private static CTakesParser setUpCTakesParser(ParserType type) {
		CTakesParser parser = new CTakesParser();
		parser.setExtractorName(type.toString());
		parser.setUmlsInformationTag(CTAKES_UMLS_INFORMATION_TAG);
		parser.setTextInformationTag(CTAKES_TEXT_INFORMATION_TAG);
		parser.setConceptIdentifierNode(CTAKES_CONCEPT_NODE);
		
		return parser;
	}

}
