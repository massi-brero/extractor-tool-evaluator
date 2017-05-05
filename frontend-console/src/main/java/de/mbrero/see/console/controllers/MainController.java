package de.mbrero.see.console.controllers;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;

import de.mbrero.see.console.commands.ConsoleCommand;
import de.mbrero.see.console.io.CommandInterpreter;
import de.mbrero.see.console.io.ConfigurationReader;
import exceptions.ParameterException;

public class MainController {
	
	CommandInterpreter interpreter = null;
	CommandController controller = null;
	
	public static Map<String, String> bootstrap() {
		
		return null;
	}
	

	/**
	 * Reads the user input.
	 * 
	 * @return {@link ConsoleCommand}
	 * @throws ParameterException 
	 */
	public ConsoleCommand readFromConsole() throws ParameterException {

		ConsoleCommand cmd = new ConsoleCommand();
		BufferedReader br = null;
		String input = "";
		

		br = new BufferedReader(new InputStreamReader(System.in));


		while (true) {
			
			Console console = System.console();
			// read user name, using java.util.Formatter syntax :
			input = console.readLine("$see > ");

			
			if ("quit".equals(input)) {
				System.out.println("Exit!");
				System.exit(0);
			} 
			
			cmd =  this.getInterpreter().buildCommand(input);
			
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}


	}
	
	private void invokeCommandClass() {
		
	}


	/**
	 * @return the interpreter
	 */
	public CommandInterpreter getInterpreter() {
		return interpreter;
	}


	/**
	 * @param interpreter the interpreter to set
	 */
	public void setInterpreter(CommandInterpreter interpreter) {
		this.interpreter = interpreter;
	}


	/**
	 * @return the controller
	 */
	public CommandController getController() {
		return controller;
	}


	/**
	 * @param controller the controller to set
	 */
	public void setController(CommandController controller) {
		this.controller = controller;
	}

}
