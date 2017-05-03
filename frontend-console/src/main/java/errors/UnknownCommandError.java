package errors;

/**
 * Error class for console command errors.
 * 
 * @author massi
 *
 */
public class UnknownCommandError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnknownCommandError() {
		super("Unknown Command");
	}

}
