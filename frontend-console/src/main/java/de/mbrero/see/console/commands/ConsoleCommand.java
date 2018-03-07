package de.mbrero.see.console.commands;

import java.util.HashMap;

/**
 * Encapsulates the commands from the console.<br>
 * Part of the command pattern.
 *  * 
 * @author massimiliano.brero@studium.fernuni-hagen.de
 *
 */
public class ConsoleCommand {
	
	private String command;
	private HashMap<String, String> parameters;
	
	/**
	 * Constructor that initialises the class attributes.
	 */
	public ConsoleCommand() {
		this.parameters = new HashMap<String, String>();
		this.command = "";
	}
	
	/**
	 * Getter
	 * @return {@link String}
	 */
	public String getCommand() {
		return command;
	}
	/**
	 * Setter
	 * @param command
	 */
	public void setCommand(String command) {
		this.command = command;
	}
	/**
	 * Getter 
	 * @return @see HashMap
	 */
	public HashMap<String, String> getParameters() {
		return parameters;
	}
	/**
	 * Setter
	 * @param arguments
	 */
	public void setParameters(HashMap<String, String> parameters) {
		this.parameters = parameters;
	}
	

}
