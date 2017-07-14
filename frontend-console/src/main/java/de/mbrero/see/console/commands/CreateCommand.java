package de.mbrero.see.console.commands;

import exceptions.ParameterException;
import exceptions.UnknownCommandException;

public class CreateCommand implements ICommand {

	
	public void execute(ConsoleCommand cmd) throws UnknownCommandException, ParameterException {
		System.out.println("create new test run");
	}

	@Override
	public void validateParameters() throws Exception {
		// TODO Auto-generated method stub
		
	}

	
}
