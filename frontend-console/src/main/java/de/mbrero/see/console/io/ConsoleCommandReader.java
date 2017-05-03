package de.mbrero.see.console.io;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import de.mbrero.see.console.commands.ConsoleCommand;
import errors.ParameterError;

/**
 * Class to use the console as a user interface. Quite straightforward.
 * 
 * @author massi
 *
 */
public class ConsoleCommandReader {

	Console console = null;

	/**
	 * Reads the user input.
	 * 
	 * @return {@link ConsoleCommand}
	 * @throws ParameterError 
	 */
	public ConsoleCommand readFromConsole() throws ParameterError {

		BufferedReader br = null;
		String input = "";

		br = new BufferedReader(new InputStreamReader(System.in));

		this.console = System.console();
		// read user name, using java.util.Formatter syntax :
		input = this.console.readLine("$see > ");

		if ("quit".equals(input)) {
			System.out.println("Exit!");
			System.exit(0);
		}

		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

		return this.buildCommand(input);

	}

	/**
	 * This method parses the console input into a command parameters and
	 * corresponding arguments. It will use a {@link ConsoleCommand} object for
	 * storing them.
	 * 
	 * @param str
	 * @return {@link ConsoleCommand}
	 * @throws ParameterError
	 */
	protected ConsoleCommand buildCommand(String str) throws ParameterError {

		List<String> parameters = null;
		String lastParameter = "";
		ConsoleCommand cmd = new ConsoleCommand();

		String[] components = str.split(" ");

		for (String chunk : components) {

			if (cmd.getCommand().isEmpty()) {

				if (chunk.charAt(0) == '-') {
					throw new ParameterError();
				}

				cmd.setCommand(chunk);
			} else {

				/*
				 * start parsing arguments
				 */
				if (lastParameter.isEmpty())
					cmd.getParameters().put(chunk, "");
				else
					cmd.getParameters().put(lastParameter, chunk);
			}

		}

		return cmd;
	}

	/**
	 * @return the console
	 */
	public Console getConsole() {
		return console;
	}

	/**
	 * @param console the console to set
	 */
	public void setConsole(Console console) {
		this.console = console;
	}
	
	

}
