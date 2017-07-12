package de.mbrero.see.console.commands;

import exceptions.ParameterException;
import exceptions.UnknownCommandException;

public interface ICommand {

	public void execute(ConsoleCommand cmd) throws Exception;
		
}
