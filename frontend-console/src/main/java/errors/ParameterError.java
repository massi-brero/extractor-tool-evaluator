package errors;

/**
 * Error class for console command errors.
 * 
 * @author massi
 *
 */
public class ParameterError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ParameterError() {
		super("Argument Error");
	}

}
