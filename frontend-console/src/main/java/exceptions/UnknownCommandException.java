package exceptions;

/**
 * Error class for console command errors.
 * 
 * @author massimiliano.brero@studium.fernuni-hagen.de
 *
 */
public class UnknownCommandException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Throw when a command was entered that is not accepted by the application.
	 */
	public UnknownCommandException() {
		super("Unknown Command");
	}

}
