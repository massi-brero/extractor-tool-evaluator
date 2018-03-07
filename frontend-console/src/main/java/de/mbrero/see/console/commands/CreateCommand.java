package de.mbrero.see.console.commands;

import exceptions.ParameterException;
import exceptions.UnknownCommandException;

/**
 * Defines the public interface for all possible commands the application will accept.
 * @author massimiliano.brero@studium.fernuni-hagen.de
 *
 */
public class CreateCommand implements ICommand {

	/**
	 * @see ICommand#execute(ConsoleCommand)
	 */
	public void execute(ConsoleCommand cmd) throws UnknownCommandException, ParameterException {
		System.out.println("create new test run");
	}

	/**
	 * @see ICommand#validateParameters()
	 */
	@Override
	public void validateParameters() throws Exception {
		// TODO Auto-generated method stub
		
	}

	
}
