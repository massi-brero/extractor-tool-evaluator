package de.mbrero.see.console.commands;

import java.util.HashMap;

public class ConsoleCommand {
	
	private String command;
	private HashMap<String, String> arguments;
	/**
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}
	/**
	 * @param command the command to set
	 */
	public void setCommand(String command) {
		this.command = command;
	}
	/**
	 * @return the arguments
	 */
	public HashMap<String, String> getArguments() {
		return arguments;
	}
	/**
	 * @param arguments the arguments to set
	 */
	public void setArguments(HashMap<String, String> arguments) {
		this.arguments = arguments;
	}
	

}
