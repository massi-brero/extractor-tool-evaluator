package de.mbrero.see.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.hamcrest.core.IsNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.mbrero.see.persistance.dto.Annotation;
import types.Ontology;

public class CTakesParser implements IParser {

	private String umlsInformationTag = "org.apache.ctakes.typesystem.type.refsem.UmlsConcept";
	protected HashMap<String, Annotation> annotations;
	protected File sourceTextFile;
	
	final String EXTRACTOR_NAME = "cTakes";

	public CTakesParser(File file) {
		this();
		setSourceTextFile(file);
	}

	public CTakesParser() {
		sourceTextFile = null;
		annotations = new HashMap<>();
	}

	@Override
	public void read() throws FileNotFoundException {

	}

	/**
	 * Runs through the result xml file to find the tags containing the UMLS concet information.
	 */
	@Override
	public void parse() {

		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(this.getSourceTextFile());

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName(this.getUmlsInformationTag());

			for (int idx = 0; idx < nList.getLength(); idx++) {

				Node nNode = nList.item(idx);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element elem = (Element) nNode;
					String cui = elem.getAttribute("cui");
					
					if (getAnnotations().get(cui) == null) {
						addAnnotation(elem, cui);
					} else {
						getAnnotations().get(cui)
						.incrementCounter();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void addAnnotation(Element elem, String cui) {
		Annotation annotation = new Annotation();

		annotation.setOntology(Ontology.valueOf((elem.getAttribute("codingScheme").toUpperCase())));
		annotation.setCui(cui);
		annotation.setPreferredText(elem.getAttribute("preferredText"));
		annotation.setSourceText(this.getSourceTextFile());
		annotation.setExtractor(EXTRACTOR_NAME);
		annotation.setCount(1);	
		getAnnotations().put(annotation.getCui(), annotation);
	}

	/**
	 * @return the sourceTextFile
	 */
	public File getSourceTextFile() {
		return sourceTextFile;
	}

	/**
	 * @param sourceTextFile
	 *            the sourceTextFile to set
	 * @throws FileNotFoundException
	 */
	public void setSourceTextFile(File file) {
		this.sourceTextFile = file;

	}

	/**
	 * @return the umlsInformationTag
	 */
	public String getUmlsInformationTag() {
		return umlsInformationTag;
	}

	/**
	 * @param umlsInformationTag
	 *            the umlsInformationTag to set
	 */
	public void setUmlsInformationTag(String umlsInformationTag) {
		this.umlsInformationTag = umlsInformationTag;
	}

	/**
	 * @return the annotations
	 */
	public HashMap<String, Annotation> getAnnotations() {
		return annotations;
	}

	/**
	 * @param annotations the annotations to set
	 */
	public void setAnnotations(HashMap<String, Annotation> annotations) {
		this.annotations = annotations;
	}

}
