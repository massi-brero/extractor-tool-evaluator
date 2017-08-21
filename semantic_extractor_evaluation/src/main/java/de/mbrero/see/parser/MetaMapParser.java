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
	private final String ONTOLOGY_NODE = "codingScheme";
	private final String SCORE_TAG = "CandidateScore";
	// private final String PREFERRED_TEXT_TAG = "preferredText";

	private int scoreThreshHold = -1000;

	@Override
	protected String getAnnotatedFileName() throws ParserConfigurationException, SAXException, IOException {

		return "implement.this";

	}

	protected HashMap<String, Annotation> parseFile() throws Exception {

		NodeList nList = getNodeList(conceptInformationTag);
		HashMap<String, Annotation> fileAnnotations = new HashMap<>();

		for (int idx = 0; idx < nList.getLength(); idx++) {
			Annotation annotation = new Annotation();

			Node conceptNode = nList.item(idx);

			if (conceptNode.getNodeType() == Node.ELEMENT_NODE) {

				Element elem = (Element) conceptNode;

				if (getConceptIdentifierNode().isEmpty())
					throw new ParserConfigurationException("No tag where the concept id can be found was set");

				String conceptIdNode = elem.getAttribute(getConceptIdentifierNode());

				annotation = buildAnnotation(elem, conceptIdNode);

				if (fileAnnotations.get(conceptIdNode) == null) {
					fileAnnotations.put(conceptIdNode, annotation);
				} else {
					fileAnnotations.get(conceptIdNode).incrementCounter();
				}
			}
		}

		return fileAnnotations;
	}

	@Override
	protected Annotation buildAnnotation(Element elem, String conceptId)
			throws ParserConfigurationException, SAXException, IOException {
		Annotation annotation = new Annotation();

		String ontology = elem.getAttribute(ONTOLOGY_NODE).toUpperCase();
		annotation.setOntology(ontology);
		annotation.setCui(conceptId);
		annotation.setPreferredText(""); // not used at the moment
		annotation.setDocumentID(getAnnotatedFileName());
		annotation.setExtractor(extractorName);
		annotation.setMatchedChunk(elem.getTextContent());
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
