package de.mbrero.see.parser;

import java.io.File;
import java.io.FileNotFoundException;

import types.OutputType;

public interface IAnnotationParser {
	
	/**
	 * Principal method to be called to process a concept mapper's result output.
	 * 
	 * @param source
	 * @throws Exception
	 */
	public void parse(File source) throws Exception;
	
	/**
	 * Alternative method to {@link IAnnotationParser}
	 * @throws FileNotFoundException
	 */
	public void read() throws FileNotFoundException;
	
	/**
	 * Define if annotations shall be savedas:<br>
	 * <ul>
	 * <li>to a database</li>
	 * <li>to a TREC file</li>
	 * </ul>
	 * @param type
	 */
	public void setOutputType(OutputType type);
	
}
