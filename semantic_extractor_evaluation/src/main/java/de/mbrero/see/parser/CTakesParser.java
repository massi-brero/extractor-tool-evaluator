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

	/**
	 * Tag name in the cTakes result xml where the umls concepts are displayed.
	 */
	protected String umlsInformationTag = "org.apache.ctakes.typesystem.type.refsem.UmlsConcept";
	/**
	 * Tag name in the cTakes result xml where the document id are displayed.
	 */
	protected String textInformationTag = "org.apache.ctakes.typesystem.type.structured.DocumentID";
	/**
	 * The result xml fom thr cTAkes rum.
	 */
	protected File sourceFile;
	/**
	 * All annotations for every parsed result file.
	 * Structure {@link Annotation}:
	 * <ul>[document_id]
	 * <li>[cui 1] -> Annotation object</li>	
	 * <li>[cui 1] -> Annotation object</li>
	 * <li>...</li>
	 * <ul>[document_id]
	 * <li>[cui 2] -> Annotation object</li>	
	 * <li>[cui 1] -> Annotation object</li>
	 * <li>...</li>
	 */
	protected HashMap<String, HashMap<String, Annotation>> annotations;
	protected OutputType outputType = null;
	
	/**
	 * Where to write the TREC result file.
	 */
	protected File outputFile;
	final String EXTRACTOR_NAME = "cTakes";

	public CTakesParser() {
		annotations = new HashMap<>();
		setSourceFile(new File(""));
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
		setSourceFile(source);
		HashMap<String, Annotation> fileAnnotations = new HashMap<>();
		
		if (getSourceFile().isFile()) {
			
			parseFile();
			getAnnotations().put(getParsedFileName(), fileAnnotations);	
			
		} else if (getSourceFile().isDirectory()) {
			
			File[] files = getSourceFile().listFiles();
			
			for (File file : files) {
				
				if (file.isDirectory())
				{
					parse(file);
					getAnnotations().put(getParsedFileName(), fileAnnotations);					
				} else 
				{
					setSourceFile(file);
					parseFile();					
				}
				
			}
			
		} else {
			throw new FileNotFoundException("Could not parsed given path!");
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
	public HashMap<String, HashMap<String, Annotation>> getAnnotations() {
		return annotations;
	}

	/**
	 * @param annotations the annotations to set
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
	 * @param sourceFile the sourceFile to set
	 */
	public void setSourceFile(File sourceFile) {
		this.sourceFile = sourceFile;
	}

	/**
	 * @param outputFile the outputFile to set
	 */
	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}
	
	private HashMap<String, Annotation> parseFile() throws SAXException, IOException, ParserConfigurationException {
		
		NodeList nList = getNodeList(umlsInformationTag);
		HashMap<String, Annotation> fileAnnotations= new HashMap<>();

		for (int idx = 0; idx < nList.getLength(); idx++) {
			Annotation annotation = new Annotation();

			Node node = nList.item(idx);

			if (node.getNodeType() == Node.ELEMENT_NODE) {

				Element elem = (Element) node;
				String cui = elem.getAttribute("cui");
				annotation = buildAnnotation(elem, cui);
				
				if (fileAnnotations.get(cui) == null) {
					fileAnnotations.put(cui, annotation);
				} else {
					fileAnnotations.get(cui).incrementCounter();
				}
			}
		}
		
		return fileAnnotations;
	}

	private NodeList getNodeList(String tagName) throws ParserConfigurationException, SAXException, IOException {
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(getSourceFile());
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName(tagName);
		
		return nList;
	}
	
	private Annotation buildAnnotation(Element elem, String cui) throws ParserConfigurationException, SAXException, IOException {
		Annotation annotation = new Annotation();

		annotation.setOntology(Ontology.valueOf((elem.getAttribute("codingScheme").toUpperCase())));
		annotation.setCui(cui);
		annotation.setPreferredText(elem.getAttribute("preferredText"));
		annotation.setDocumentID(getParsedFileName());
		annotation.setExtractor(EXTRACTOR_NAME);
		annotation.setCount(1);	
		
		return annotation;
	}

	private String getParsedFileName() throws ParserConfigurationException, SAXException, IOException {
		String filename = "";
		
		NodeList nList = getNodeList(textInformationTag);
		Node node = nList.item(0);
		
		if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
			Element elem = (Element) node;
			filename = elem.getAttribute("documentID");
		}
		
		return filename;
	}
	

}
