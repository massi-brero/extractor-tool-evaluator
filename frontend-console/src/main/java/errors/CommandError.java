package errors;

/**
 * Error class for console command errors.
 * 
 * @author massi
 *
 */
public class CommandError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CommandError(String msg) {
		super(msg);
	}

}
