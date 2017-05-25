package de.mbrero.see.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.mbrero.see.persistance.dto.Annotation;

public class CTakesParser implements IParser {

	protected File sourceTextFile;
	private String umlsInformationTag = "";
	protected HashMap<String, Annotation> annotations;

	public CTakesParser(File file) {
		this();
		this.setSourceTextFile(file);
	}

	public CTakesParser() {
		sourceTextFile = null;
		annotations = new HashMap<>();
	}

	@Override
	public void read() throws FileNotFoundException {

	}

	@Override
	public HashMap<String, Annotation> parse() {

		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(this.getSourceTextFile());

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName(this.getUmlsInformationTag());

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element elem = (Element) nNode;
					Annotation annotation = new Annotation();

					elem.getAttribute("codingScheme");
					System.out.println(
							"First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
					System.out.println(
							"Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
					System.out.println(
							"Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
					System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

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

}
