package de.mbrero.see.parser;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.mbrero.see.persistance.dto.Annotation;

public class MetaMapParser extends AbstractParser {
	private final String DOCUMENT_ID_TAG = "org.apache.ctakes.typesystem.type.structured.DocumentID";
	private final String DOCUMENT_ID_NODE = "documentID";
	private final String ONTOLOGY_NODE = "Sources";
	private final String SCORE_TAG = "CandidateScore";
	private final String CANDIDATE_MATCHED = "CandidateMatched";
	// private final String PREFERRED_TEXT_TAG = "preferredText";

	private int scoreThreshHold = 1000;

	@Override
	protected String getAnnotatedFileName() throws ParserConfigurationException, SAXException, IOException {

		return "implement.this";

	}

	protected HashMap<String, Annotation> parseFile() throws Exception {

		NodeList nList = getNodeList(conceptInformationTag);
		HashMap<String, Annotation> fileAnnotations = new HashMap<>();

		for (int idx = 0; idx < nList.getLength(); idx++) {
			Annotation annotation = new Annotation();

			Element conceptElement = (Element)nList.item(idx);

			if (conceptElement.getNodeType() == Node.ELEMENT_NODE) {

				Element elem = (Element) conceptElement;

				if (getConceptIdentifierNode().isEmpty())
					throw new ParserConfigurationException("No tag where the concept id can be found was set");

				String conceptId = elem.getElementsByTagName(getConceptIdentifierNode()).item(0).getTextContent();
				String score = elem.getElementsByTagName(SCORE_TAG).item(0).getTextContent();

				if (Math.abs(Integer.parseInt(score)) > getScoreThreshHold())
				{
					annotation = buildAnnotation(elem, conceptId);

					if (fileAnnotations.get(conceptId) == null) {
						fileAnnotations.put(conceptId, annotation);
					} else {
						fileAnnotations.get(conceptId).incrementCounter();
					}
				}
			}
		}

		return fileAnnotations;
	}

	@Override
	protected Annotation buildAnnotation(Element elem, String conceptId)
			throws ParserConfigurationException, SAXException, IOException {
		Annotation annotation = new Annotation();
		
		NodeList ontologies = elem.getElementsByTagName(ONTOLOGY_NODE);
		String candidateMatched = elem.getElementsByTagName(CANDIDATE_MATCHED).item(0).getTextContent();
		annotation.setOntology(ontologies.toString());
		annotation.setCui(conceptId);
		annotation.setPreferredText(""); // not used at the moment
		annotation.setDocumentID(getAnnotatedFileName());
		annotation.setExtractor(extractorName);
		annotation.setMatchedChunk(candidateMatched);
		annotation.setCount(1);

		return annotation;
	}

	/**
	 * Return the lower limit from which on the candidates in the MetaMap output
	 * xml file<br /> shall be considered.
	 * 
	 * @return int
	 */
	public int getScoreThreshHold() {
		return scoreThreshHold;
	}

	/**
	 * Return the lower limit from which on the candidates in the MetaMap output
	 * xml file<br />shall be considered.
	 * 
	 * @param scoreThreshHold int
	 */
	public void setScoreThreshHold(int scoreThreshHold) {
		this.scoreThreshHold = scoreThreshHold;
	}

}
