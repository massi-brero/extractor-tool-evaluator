package de.mbrero.see.console.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Scanner;

import de.mbrero.see.console.commands.ConsoleCommand;
import de.mbrero.see.console.commands.ICommand;
import de.mbrero.see.console.io.CommandInterpreter;
import exceptions.ParameterException;
import exceptions.UnknownCommandException;

public class MainController {
	
	CommandInterpreter interpreter;
	
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
	public  ConsoleCommand readFromConsole() {

		ConsoleCommand cmd = new ConsoleCommand();
		BufferedReader br = null;
		String input = "";
		
		br = new BufferedReader(new InputStreamReader(System.in));


		while (true) {
			
			Scanner in;
			in = new Scanner(System.in);
			
			System.out.print("$see> ");
			input = in.nextLine();

			if ("quit".equals(input)) {
				System.out.println("Exit!");
				in.close();
				System.exit(0);
			} 
			
			try {
				
				cmd =  this.getInterpreter().buildCommand(input);
				this.executeCommand(cmd);
					
			} catch (Exception e) {
				e.printStackTrace();
				this.output(e.getMessage());	
			}
		}
		
	}
	
	private void output(String msg) {
		msg = "***" + msg + "***";
		System.out.println(msg);
	}
	
	private void executeCommand(ConsoleCommand cmd) throws Exception {
		
		/*
		 * get corresponding Comman class an execute command
		 */
		if (cmd.getCommand().isEmpty()) {
			throw new UnknownCommandException();
		} else {
			Class<?> commandClass = Class.forName("de.mbrero.see.console.commands.CreateCommand");
			ICommand commandObj = (ICommand) commandClass.newInstance();
			commandClass.getDeclaredMethod("execute").invoke(commandObj);
		}
		
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
	 * @param interpreter the interpreter to set
	 */
	public void setInterpreter(CommandInterpreter interpreter) {
		this.interpreter = interpreter;
	}	

}
