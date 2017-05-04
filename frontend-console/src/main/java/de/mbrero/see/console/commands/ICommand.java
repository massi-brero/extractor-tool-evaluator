package de.mbrero.see.console.commands;

import exceptions.ParameterException;
import exceptions.UnknownCommandException;

public interface ICommand {

	public void execute() throws UnknownCommandException, ParameterException;
		
}
