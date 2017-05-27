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
import types.Ontology;
import types.OutputType;

public class CTakesParser implements IParser {

	protected String umlsInformationTag = "org.apache.ctakes.typesystem.type.refsem.UmlsConcept";
	protected HashMap<String, Annotation> annotations;
	protected OutputType outputType = null;
	protected File outputFile;
	
	final String EXTRACTOR_NAME = "cTakes";

	public CTakesParser() {
		annotations = new HashMap<>();
	}

	@Override
	public void read() throws FileNotFoundException {

	}

	/**
	 * Runs through the result xml file to find the tags containing the UMLS concet information.
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	@Override
	public void parse(File source) throws SAXException, IOException, ParserConfigurationException {


		if (source.isFile()) {
			
			parseFile(source);
			
		} else if (source.isDirectory()) {
			
			File[] files = source.listFiles();
			
			for (File file : files) {
				
				if (file.isDirectory()) {
					
					parse(file);
					
				} else {
					
				}
				 
			}
			
		} else {

		}

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
	public HashMap<String, Annotation> getAnnotations() {
		return annotations;
	}

	/**
	 * @param annotations the annotations to set
	 */
	public void setAnnotations(HashMap<String, Annotation> annotations) {
		this.annotations = annotations;
	}

	/**
	 * @return the outputFile
	 */
	public File getOutputFile() {
		return outputFile;
	}

	/**
	 * @param outputFile the outputFile to set
	 */
	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}
	
	private void addAnnotation(Element elem, String cui, File source) {
		Annotation annotation = new Annotation();

		annotation.setOntology(Ontology.valueOf((elem.getAttribute("codingScheme").toUpperCase())));
		annotation.setCui(cui);
		annotation.setPreferredText(elem.getAttribute("preferredText"));
		annotation.setSourceText(source);
		annotation.setExtractor(EXTRACTOR_NAME);
		annotation.setCount(1);	
		getAnnotations().put(annotation.getCui(), annotation);
	}

	
	private void parseFile(File source) throws SAXException, IOException, ParserConfigurationException {
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(source);

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
					addAnnotation(elem, cui, source);
				} else {
					getAnnotations().get(cui).incrementCounter();
				}
			}
		}
	}


}
