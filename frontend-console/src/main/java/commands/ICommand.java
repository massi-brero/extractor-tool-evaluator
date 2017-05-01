package commands;

import errors.CommandError;

public interface ICommand {

	public void execute() throws CommandError;
		
}
