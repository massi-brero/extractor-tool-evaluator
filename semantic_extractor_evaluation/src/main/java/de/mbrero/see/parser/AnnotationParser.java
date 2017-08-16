package de.mbrero.see.parser;

import java.io.File;
import java.io.FileNotFoundException;

import types.OutputType;

public interface AnnotationParser {
	
	public void parse(File source) throws Exception;
	public void read() throws FileNotFoundException;
	public void setOutputType(OutputType type);
	
}
