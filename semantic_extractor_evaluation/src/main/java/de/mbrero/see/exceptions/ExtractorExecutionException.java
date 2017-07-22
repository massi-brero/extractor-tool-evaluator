package de.mbrero.see.exceptions;

/**
 * Error class for errors occuring when an extractor crashing inmidst of a process
 * 
 * @author massi.brero@gmail.com
 *
 */
public class ExtractorExecutionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ExtractorExecutionException(String msg) {
		super(msg);
	}
	
	
	public ExtractorExecutionException() {
		super("Extractor Execution Error");
	}

}
