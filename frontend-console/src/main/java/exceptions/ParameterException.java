package exceptions;

/**
 * Error class for console command errors.
 * 
 * @author massi.brero@gmail.com
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
