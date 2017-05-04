package exceptions;

/**
 * Error class for console command errors.
 * 
 * @author massi
 *
 */
public class UnknownCommandException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnknownCommandException() {
		super("Unknown Command");
	}

}
