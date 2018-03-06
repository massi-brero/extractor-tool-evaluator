package de.mbrero.see.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import de.mbrero.see.persistance.dto.Annotation;
import util.ProgressBar;

/**
 * Parses the result files returned by MetaMap.
 * 
 * @author massi
 *
 */
public class MetaMapParser extends AbstractParser {
	private final String OPTION_TAG = "Option";
	private final String OPTION_NAME_TAG = "OptName";
	private final String OPTION_VALUE_TAG = "OptValue";
	private final String OPTION_NAME_INPUT_FILE = "infile";
	private final String ONTOLOGY_NODE = "Source";
	private final String SCORE_TAG = "CandidateScore";
	private final String CANDIDATE_MATCHED = "CandidateMatched";
	// private final String PREFERRED_TEXT_TAG = "preferredText";

	private int scoreThreshHold = 0;
	
	/**
	 * Return the lower limit from which on the candidates in the MetaMap output
	 * xml file<br /> shall be considered. Sets Metamap's "t" parameter.
	 * 
	 * @return int
	 */
	public int getScoreThreshHold() {
		return scoreThreshHold;
	}

	@Override
	protected String getAnnotatedFileName() throws ParserConfigurationException, SAXException, IOException {

		String filename = "";

		NodeList nList = getNodeList(OPTION_TAG);

		for (int idx = 0; idx < nList.getLength(); idx++) {
			Element elem = (Element) nList.item(idx);

			String optname = elem.getElementsByTagName(OPTION_NAME_TAG).item(0).getTextContent();

			if (optname.equals(OPTION_NAME_INPUT_FILE)) {
				String path = elem.getElementsByTagName(OPTION_VALUE_TAG).item(0).getTextContent();
				String[] pathComponents = path.split("/");
				filename = pathComponents[pathComponents.length - 1];
			}
		}

		return filename;

	}

	/**
	 * Parses a single text file.
	 */
	protected HashMap<String, Annotation> parseFile() throws Exception {

		NodeList nList = getNodeList(conceptInformationTag);
		HashMap<String, Annotation> fileAnnotations = new HashMap<>();
		ProgressBar progressBar = new ProgressBar();

		for (int idx = 0; idx < nList.getLength(); idx++) {
			Annotation annotation = new Annotation();

			Element conceptElement = (Element) nList.item(idx);
			
			progressBar.showProgress(nList.getLength(), idx, 10);

			if (conceptElement.getNodeType() == Node.ELEMENT_NODE) {

				Element elem = (Element) conceptElement;

				if (getConceptIdentifierNode().isEmpty())
					throw new ParserConfigurationException("No tag where the concept id can be found was set");

				String conceptId = elem.getElementsByTagName(getConceptIdentifierNode()).item(0).getTextContent();
				String score = elem.getElementsByTagName(SCORE_TAG).item(0).getTextContent();

				if (Math.abs(Integer.parseInt(score)) >= getScoreThreshHold()) {
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
		StringBuffer ontology = new StringBuffer();

		for (int idx = 0; idx < ontologies.getLength(); idx++) {
			Element ontologyElem = (Element) ontologies.item(idx);
			ontology.append(ontologyElem.getTextContent() + " ");
		}

		String candidateMatched = elem.getElementsByTagName(CANDIDATE_MATCHED).item(0).getTextContent();
		annotation.setOntology(ontology.toString().trim());
		annotation.setCui(conceptId);
		annotation.setPreferredText(""); // not used at the moment
		annotation.setDocumentID(getAnnotatedFileName());
		annotation.setExtractor(extractorName);
		annotation.setMatchedChunk(candidateMatched);
		annotation.setCount(1);

		return annotation;
	}

	/**
	 * Get a list of nodes from the xml file filtered by their tag name.
	 */
	protected NodeList getNodeList(String tagName) throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		dBuilder.reset();
		dBuilder.setEntityResolver(new EntityResolver() {
			public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
				return new InputSource(new StringReader(""));
			}
		});
		document = dBuilder.parse(getInputFile());
		document.getDocumentElement().normalize();

		NodeList nList = document.getElementsByTagName(tagName);

		return nList;
	}

}
