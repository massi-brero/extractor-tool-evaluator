package de.mbrero.see.console.io;

import de.mbrero.see.console.commands.ConsoleCommand;
import errors.ParameterError;
import junit.framework.TestCase;

public class ConsoleCommandReaderTest extends TestCase {
	
	private ConsoleCommandReader reader;

	protected void setUp() throws Exception {
		reader = new ConsoleCommandReader();
		reader.setConsole(System.console());
		super.setUp();
	}

	public void testReadFromConsole() {
		//fail("Not yet implemented");
	}

	public void testBuildCommandWithNoParametersAndNoArguments() throws ParameterError {
		String cmdString = "create";
		
		ConsoleCommand cmd = reader.buildCommand(cmdString);
		
		assertEquals(cmdString, cmd.getCommand());
		
	}

}
