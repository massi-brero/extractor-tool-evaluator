package de.mbrero.see.console.commands;

public interface ICommand {

	public void execute(ConsoleCommand cmd) throws Exception;
	public void validateParameters() throws Exception;
		
}
