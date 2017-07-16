package de.mbrero.see.parser;

import javax.xml.bind.TypeConstraintException;

import types.ParserType;

public class ParserFactory {
	
	private static ParserType parserType = null;
	
	/*
	 * cTakes parameters
	 */
	private final static String CTAKES_UMLS_INFORMATION_TAG = "org.apache.ctakes.typesystem.type.refsem.UmlsConcept";
	private final static String CTAKES_CONCEPT_NODE = "code";
	
	/*
	 * CRAFT parameters
	 */
	private final static String CRAFT_UMLS_INFORMATION_TAG = "integerSlotMentionValue";
	private final static String CRAFT_CONCEPT_NODE = "value";


	/**
	 * Returns a configured parser for extracting the found annotations in a text file.
	 * 
	 * @param type
	 * @return
	 * @throws TypeConstraintException
	 */
	public static AnnotationParser getInstance(ParserType type) throws TypeConstraintException  {
		parserType = type;
		return createParser();
	}
	

	private static AnnotationParser createParser() throws TypeConstraintException {
		AnnotationParser parser = null;
		
		switch (parserType) {
		case CTAKES:
			parser = setUpCTakesParser();
			break;
		case QUICKUMLS:
			break;
		case METAMAP:
			break;
		case CRAFT:
			parser = setUpCRAFTParser();
			break;
		default:
			throw new TypeConstraintException("No parser exists for this type!");
		}
		
		return parser; 
		
	}


	private static CTakesParser setUpCTakesParser() {
		CTakesParser parser = new CTakesParser();
		parser.setExtractorName(parserType.toString());
		parser.setUmlsInformationTag(CTAKES_UMLS_INFORMATION_TAG);
		parser.setConceptIdentifierNode(CTAKES_CONCEPT_NODE);
		
		return parser;
	}
	
	private static CRAFTParser setUpCRAFTParser() {
		CRAFTParser parser = new CRAFTParser();
		parser.setExtractorName(parserType.name());
		parser.setUmlsInformationTag(CRAFT_UMLS_INFORMATION_TAG);
		parser.setConceptIdentifierNode(CRAFT_CONCEPT_NODE);
		
		return parser;
	}


}
