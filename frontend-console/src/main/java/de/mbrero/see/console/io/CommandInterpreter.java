package de.mbrero.see.console.io;

import java.util.List;

import de.mbrero.see.console.commands.ConsoleCommand;
import de.mbrero.see.console.controllers.CommandController;
import exceptions.ParameterException;

/**
 * Class to use the console as a user interface. Quite straightforward.
 * 
 * @author massi.brero@gmail.com
 *
 */
public class CommandInterpreter {


	/**
	 * This method parses the console input into a command parameters and
	 * corresponding arguments. It will use a {@link ConsoleCommand} object for
	 * storing them.
	 * 
	 * The parameters are expected in the form
	 * 
	 * {command} -{param1} {valueForParam1} -{param1} {valueForParam1} [...]
	 * 
	 * @param str
	 * @return {@link ConsoleCommand}
	 * @throws ParameterException
	 */
	public ConsoleCommand buildCommand(String str) throws ParameterException {

		String lastParameter = "";
		ConsoleCommand cmd = new ConsoleCommand();
		
		if (str.isEmpty()) return cmd;

		String[] components = str.trim().split(" ");

		for (String chunk : components) {

			if (cmd.getCommand().isEmpty()) {

				if (chunk.charAt(0) == '-') {
					throw new ParameterException();
				}

				cmd.setCommand(chunk);
			} else {

				/*
				 * start parsing arguments
				 */
				if (lastParameter.isEmpty()) {
					
					if (chunk.charAt(0) == '-') {
						
						chunk = chunk.substring(1);
						cmd.getParameters().put(chunk, "");	
						lastParameter = chunk;
						
					}
					else {
						throw new ParameterException();
					}
					
				} else {
					
					if (chunk.isEmpty())
						throw new ParameterException("There is a parameter with no arguments!");

					cmd.getParameters().put(lastParameter, chunk);		
					lastParameter = "";
				}
			}

		}

		return cmd;
	}


}
