package de.mbrero.see.parser;

import java.io.FileNotFoundException;
import java.util.HashMap;

import de.mbrero.see.persistance.dto.Annotation;

public interface IParser {
	
	public HashMap<String, Annotation> parse();
	void read() throws FileNotFoundException;
	
}
