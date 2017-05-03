package de.mbrero.see.console.commands;

import errors.ParameterError;
import errors.UnknownCommandError;

public interface ICommand {

	public void execute() throws UnknownCommandError, ParameterError;
		
}
