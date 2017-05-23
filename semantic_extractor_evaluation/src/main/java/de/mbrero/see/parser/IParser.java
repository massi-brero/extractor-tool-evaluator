package de.mbrero.see.parser;

import java.io.File;
import java.io.FileNotFoundException;

public interface IParser {
	
	public void read(File text) throws FileNotFoundException;
	public void parse();
	
}
