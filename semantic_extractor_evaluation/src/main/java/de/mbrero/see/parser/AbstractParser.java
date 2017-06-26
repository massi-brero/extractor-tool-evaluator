package de.mbrero.see.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.mbrero.see.persistance.dto.Annotation;
import types.OutputType;

/**
 * Parses the content of the result xml generated by a ctakes annotation run.
 * 
 * @author massi.brero@gmail.com
 *
 */
public abstract class AbstractParser implements AnnotationParser {

	/**
	 * Tag name in the cTakes result xml where the umls concepts are displayed.
	 */
	protected String umlsInformationTag = "";
	
	/**
	 * The tag where the id of the concept is stored
	 */
	protected String conceptIdentifierNode = "";
	
	/**
	 * The result xml from the cTakes run.
	 */
	protected File sourceFile;
	
	/**
	 * All annotations for every parsed result file. Structure
	 * {@link Annotation}:
	 * <ul>
	 * [document_id]
	 * <li>[cui 1] -> Annotation object</li>
	 * <li>[cui 1] -> Annotation object</li>
	 * <li>...</li>
	 * <ul>
	 * [document_id]
	 * <li>[cui 2] -> Annotation object</li>
	 * <li>[cui 1] -> Annotation object</li>
	 * <li>...</li>
	 */
	protected HashMap<String, HashMap<String, Annotation>> annotations;
	
	/*
	 * File for the parsed concepts.
	 */
	protected OutputType outputType = null;

	/**
	 * Where to write the TREC result file.
	 */
	protected File outputFile;
	protected String extractorName = "";

	public AbstractParser() {
		annotations = new HashMap<>();
		setSourceFile(new File(""));
	}

	@Override
	public void read() throws FileNotFoundException {

	}

	/**
	 * Runs through the result xml file to find the tags containing the UMLS
	 * concet information.
	 * 
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	@Override
	public void parse(File source) throws SAXException, IOException, ParserConfigurationException {
		setSourceFile(source);

		if (getSourceFile().isFile()) {

			getAnnotations().put(getAnnotatedFileName(), parseFile());

		} else if (getSourceFile().isDirectory()) {

			File[] files = getSourceFile().listFiles();

			for (File file : files) {
				parse(file);
			}

		} else {
			throw new FileNotFoundException("Could not parse given path!");
		}

	}

	protected HashMap<String, Annotation> parseFile() throws SAXException, IOException, ParserConfigurationException {

		NodeList nList = getNodeList(umlsInformationTag);
		HashMap<String, Annotation> fileAnnotations = new HashMap<>();

		for (int idx = 0; idx < nList.getLength(); idx++) {
			Annotation annotation = new Annotation();

			Node node = nList.item(idx);

			if (node.getNodeType() == Node.ELEMENT_NODE) {

				Element elem = (Element) node;
				
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

	protected NodeList getNodeList(String tagName) throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(getSourceFile());
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName(tagName);

		return nList;
	}

	protected abstract Annotation buildAnnotation(Element elem, String cui) 
			throws ParserConfigurationException, SAXException, IOException;

	protected abstract String getAnnotatedFileName() 
			throws ParserConfigurationException, SAXException, IOException;

	/**
	 * 
	 * @param name
	 */
	public void setExtractorName(String name) {
		extractorName = name;
	}

	/**
	 * @return the extractorName
	 */
	public String getExtractorName() {
		return extractorName;
	}
	
	@Override
	public void setOutputType(OutputType type) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the umlsInformationTagreturn
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
	public HashMap<String, HashMap<String, Annotation>> getAnnotations() {
		return annotations;
	}

	/**
	 * @param annotations
	 *            the annotations to set
	 */
	public void setAnnotations(HashMap<String, HashMap<String, Annotation>> annotations) {
		this.annotations = annotations;
	}

	/**
	 * @return the outputFile
	 */
	public File getOutputFile() {
		return outputFile;
	}

	/**
	 * @return the sourceFile
	 */
	public File getSourceFile() {
		return sourceFile;
	}

	/**
	 * @param sourceFile
	 *            the sourceFile to set
	 */
	public void setSourceFile(File sourceFile) {
		this.sourceFile = sourceFile;
	}

	/**
	 * @param outputFile
	 *            the outputFile to set
	 */
	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}

	/**
	 * @return the conceptIdTag
	 */
	public String getConceptIdentifierNode() {
		return conceptIdentifierNode;
	}

	/**
	 * @param conceptIdTag the conceptIdTag to set
	 */
	public void setConceptIdentifierNode(String node) {
		this.conceptIdentifierNode = node;
	}

}
