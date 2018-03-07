package exceptions;

/**
 * Error class for console command errors.
 * 
 * @author massimiliano.brero@studium.fernuni-hagen.de
 *
 */
public class ParameterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ParameterException(String msg) {
		super(msg);
	}
	
	
	public ParameterException() {
		super("Parameter Error");
	}

}
