package de.mbrero.see.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import types.OutputType;

public interface AnnotationParser {
	
	public void parse(File source) throws SAXException, IOException, ParserConfigurationException;
	public void read() throws FileNotFoundException;
	public void setOutputType(OutputType type);
	
}
