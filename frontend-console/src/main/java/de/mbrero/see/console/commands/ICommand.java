package de.mbrero.see.console.commands;

public interface ICommand {

	/**
	 *  Run the command.
	 *  
	 * @param cmd
	 * @throws Exception
	 */
	public void execute(ConsoleCommand cmd) throws Exception;
	
	/**
	 * 
	 * Parameters should be checked according to the specific requirements of the command.
	 *
	 * @throws Exception
	 */
	public void validateParameters() throws Exception;
		
}
