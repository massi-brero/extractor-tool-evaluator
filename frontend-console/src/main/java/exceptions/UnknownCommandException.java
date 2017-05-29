package exceptions;

/**
 * Error class for console command errors.
 * 
 * @author massi.brero@gmail.com
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
