package de.mbrero.see.console.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.hibernate.bytecode.buildtime.ExecutionException;

import de.mbrero.see.console.App;
import de.mbrero.see.console.commands.ConsoleCommand;
import de.mbrero.see.console.commands.ICommand;
import de.mbrero.see.console.io.CommandInterpreter;
import exceptions.ParameterException;
import exceptions.UnknownCommandException;

/**
 * Gets and prepares input for execution.
 * 
 * @author massi.brero@gmail.com
 *
 */
public class MainController {
	
	/*
	 * Output colors
	 */
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";

	private CommandInterpreter interpreter;
	private final String CLASSNAME_SUFFIX = "Command";
	/**
	 * The object that wraps the console command.
	 * 
	 * @return
	 */
	private ICommand commandObject = null;

	public static Map<String, String> bootstrap() {
		MainController mainCtrl = new MainController();
		mainCtrl.readFromConsole();

		return null;
	}

	/**
	 * Reads the user input.
	 * 
	 * @return {@link ConsoleCommand}
	 * @throws ParameterException
	 */
	public ConsoleCommand readFromConsole() {

		ConsoleCommand cmd = new ConsoleCommand();
		String input = "";

		while (true) {

			try {
				
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
				
				System.out.print(ANSI_GREEN + "$see> ");
				input = bufferedReader.readLine().trim();
				
				//System.out.print(bufferedReader.read());
				
				if ("exit".equals(input) || "quit".equals(input)) {
					System.out.println("Exit!");
					bufferedReader.close();
					App.shutdown();
					System.exit(0);
				}
				
				cmd = this.getInterpreter().buildCommand(input);
				int result = this.executeCommand(cmd);
				
				if (result > 0) {
					throw new ExecutionException("Your command could not be executed.");
				}
				
				output("Job done!");

			} catch (IOException e1) {
				output("Error while reading from console..." + e1.getMessage());
			}
			catch (Exception e2) {
				output(e2.getMessage());
			}
		}

	}

	/**
	 * Maps the console input to a Command class and tries to execute the command
	 * with the given parameters.
	 * Return 0 on success or else 1.
	 * 
	 * @param cmd
	 * @return
	 */
	public int executeCommand(ConsoleCommand cmd) {

		/*
		 * get corresponding Command class an execute command
		 */
		try {
			if (cmd.getCommand().isEmpty()) {
				throw new UnknownCommandException();
			} else {

				Class<?> commandClass = Class.forName("de.mbrero.see.console.commands." + buildClassname(cmd));
				setCommandObject((ICommand) commandClass.newInstance());
				Method m = commandClass.getDeclaredMethod("execute", ConsoleCommand.class);
				m.invoke(getCommandObject(), cmd);
				
				return 0;
			}
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		return 1;

	}

	/**
	 * @return the interpreter
	 */
	public CommandInterpreter getInterpreter() {
		if (this.interpreter == null)
			return new CommandInterpreter();
		else
			return this.interpreter;
	}

	/**
	 * @param interpreter
	 *            the interpreter to set
	 */
	public void setInterpreter(CommandInterpreter interpreter) {
		this.interpreter = interpreter;
	}

	private void output(String msg) {
		msg = "\n*** " + msg + " ***\n";
		System.out.print(ANSI_RED + msg + ANSI_RESET);
	}

	private String buildClassname(ConsoleCommand cmd) {
		return cmd.getCommand().substring(0, 1).toUpperCase() + cmd.getCommand().substring(1) + CLASSNAME_SUFFIX;
	}

	/**
	 * @return the commandObject
	 */
	public ICommand getCommandObject() {
		return commandObject;
	}

	/**
	 * @param commandObject the commandObject to set
	 */
	public void setCommandObject(ICommand commandObject) {
		this.commandObject = commandObject;
	}

}
