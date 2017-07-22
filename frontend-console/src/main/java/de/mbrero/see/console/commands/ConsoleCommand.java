package de.mbrero.see.console.commands;

import java.util.HashMap;

public class ConsoleCommand {
	
	private String command;
	private HashMap<String, String> parameters;
	
	public ConsoleCommand() {
		this.parameters = new HashMap<String, String>();
		this.command = "";
	}
	
	/**
	 * 
	 * @return {@link String}
	 */
	public String getCommand() {
		return command;
	}
	/**
	 * 
	 * @param command
	 */
	public void setCommand(String command) {
		this.command = command;
	}
	/**
	 * 
	 * @return {@link String}
	 */
	public HashMap<String, String> getParameters() {
		return parameters;
	}
	/**
	 * 
	 * @param arguments
	 */
	public void setParameters(HashMap<String, String> parameters) {
		this.parameters = parameters;
	}
	

}
