package de.mbrero.see.console.io;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import de.mbrero.see.console.commands.ConsoleCommand;
import errors.ParameterError;

public class ConsoleCommandReader {


	public String readFromConsole() {

		BufferedReader br = null;
		String input = "";

		br = new BufferedReader(new InputStreamReader(System.in));

		Console console = System.console();
		// read user name, using java.util.Formatter syntax :
		input = console.readLine("see > ");

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

		return input;

	}

	private ConsoleCommand buildCommand(String str) throws ParameterError {

		List<String> parameters = null;
		String cmd = "";
		String lastParameter = "";

		String[] components = str.split(" ");

		for (String cmp : components) {

			if (cmd.isEmpty()) {

				if (cmd.charAt(0) != '-') {
					throw new ParameterError();
				}

				cmd = cmp;
			} else {

				/*
				 * start parsing arguments
				 */
				if (lastParameter.isEmpty()) {

				}

			}

		}

		return null;
	}

}
