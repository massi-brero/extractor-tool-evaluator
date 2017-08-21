package de.mbrero.see.parser;

import javax.xml.bind.TypeConstraintException;

import types.ParserType;

/**
 * This factory "produces" various extractor and goldstandard parsers
 * to extract the annotations from the result files of an extractor run 
 * on an biomedical text.
 * 
 * @author massi.brero@gmail.com
 *
 */
public class ParserFactory {
	
	private static ParserType parserType = null;
	
	/*
	 * cTakes parameters
	 */
	private final static String CTAKES_ID_INFORMATION_TAG = "org.apache.ctakes.typesystem.type.refsem.UmlsConcept";
	private final static String CTAKES_CONCEPT_NODE = "cui";
	
	/*
	 * MetaMap parameters
	 */
	private final static String METAMAP_ID_INFORMATION_TAG = "Candidate";
	private final static String METAMAP_CONCEPT_NODE = "CandidateCUI";
	
	/*
	 * CRAFT parameters
	 */
	private final static String CRAFT_ID_INFORMATION_TAG = "integerSlotMentionValue";
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
			setUpMetaMapParser();
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
		parser.setIdInformationTag(CTAKES_ID_INFORMATION_TAG);
		parser.setConceptIdentifierNode(CTAKES_CONCEPT_NODE);
		
		return parser;
	}
	
	private static CRAFTParser setUpCRAFTParser() {
		CRAFTParser parser = new CRAFTParser();
		parser.setExtractorName(parserType.name());
		parser.setIdInformationTag(CRAFT_ID_INFORMATION_TAG);
		parser.setConceptIdentifierNode(CRAFT_CONCEPT_NODE);
		
		return parser;
	}
	
	private static MetaMapParser setUpMetaMapParser() {
		MetaMapParser parser = new MetaMapParser();
		parser.setExtractorName(parserType.name());
		parser.setIdInformationTag(METAMAP_ID_INFORMATION_TAG);
		parser.setConceptIdentifierNode(METAMAP_CONCEPT_NODE);
		
		return parser;
	}


}
