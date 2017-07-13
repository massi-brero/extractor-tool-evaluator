package de.mbrero.see.console.controllers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Scanner;

import org.hibernate.bytecode.buildtime.ExecutionException;

import de.mbrero.see.console.commands.ConsoleCommand;
import de.mbrero.see.console.commands.ICommand;
import de.mbrero.see.console.io.CommandInterpreter;
import exceptions.ParameterException;
import exceptions.UnknownCommandException;

public class MainController {

	CommandInterpreter interpreter;
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

			Scanner in;
			in = new Scanner(System.in);

			System.out.print("$see> ");
			input = in.nextLine();

			if ("quit".equals(input) || "q".equals(input)) {
				System.out.println("Exit!");
				in.close();
				System.exit(0);
			}

			try {
				
				cmd = this.getInterpreter().buildCommand(input);
				int result = this.executeCommand(cmd);
				
				if (result > 0) {
					throw new ExecutionException("Your command could not be executed.");
				}

			} catch (Exception e) {
				e.printStackTrace();
				output(e.getMessage());
			}
		}

	}

	/**
	 * Maps the console input to a Command class and tries to execute the command
	 * with the given parameters.
	 * Return 0 on succes or else 1.
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
			System.out.println(e1.getCause());
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
		msg = "***" + msg + "***";
		System.out.println(msg);
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
