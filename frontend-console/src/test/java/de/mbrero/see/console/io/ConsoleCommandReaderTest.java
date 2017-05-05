package de.mbrero.see.console.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.mbrero.see.console.commands.ConsoleCommand;
import exceptions.ParameterException;

public class ConsoleCommandReaderTest {

	private CommandInterpreter reader;

	@Before
	public void setUp() throws Exception {
		reader = new CommandInterpreter();
	}


	public void testReadFromConsole() {
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildCommandWithNoParametersAndNoArguments() throws ParameterException {
		String cmdString = "create";

		ConsoleCommand cmd = reader.buildCommand(cmdString);

		assertEquals(cmdString, cmd.getCommand());

	}

	@Test
	public void testBuildCommandWithNoParametersAndNoArgumentsbutALotOSpacesBefore() throws ParameterException {
		String cmdString = "  create";

		ConsoleCommand cmd = reader.buildCommand(cmdString);

		assertEquals("create", cmd.getCommand());

	}

	@Test
	public void testBuildCommandWithNoParametersAndNoArgumentsbutALotOSpacesAfter() throws ParameterException {
		String cmdString = "create   ";

		ConsoleCommand cmd = reader.buildCommand(cmdString);

		assertEquals("create", cmd.getCommand());

	}

	@Test
	public void testBuildCommandWithNoInput() throws ParameterException {

		String cmdString = "";
		ConsoleCommand cmd = reader.buildCommand(cmdString);
		
		assertTrue(cmd.getCommand().isEmpty());
		assertTrue(cmd.getParameters().size() == 0);
		
	}

	@Test
	public void testBuildCommandWithOneParametersAndOneArguments() throws ParameterException {
		String cmdString = "create -p test/test";

		ConsoleCommand cmd = reader.buildCommand(cmdString);

		assertEquals(1, cmd.getParameters().size());
		assertEquals("test/test", cmd.getParameters().get("p"));

	}

	@Test
	public void testBuildCommandWith2ParamsAndArguments() throws ParameterException {
		String cmdString = "create -p test/test -f foo";
		ConsoleCommand cmd = reader.buildCommand(cmdString);
		
		assertEquals(2, cmd.getParameters().size());
	}

	@Test (expected = ParameterException.class)  
	public void testParameterExceptionWhenMissingParam() throws ParameterException {

		String cmdString = "create test/test";
		reader.buildCommand(cmdString);
		
	}

	@Test
	public void testParameterWhenMissingArgument() throws ParameterException {

		String cmdString = "create -p";
		ConsoleCommand cmd = reader.buildCommand(cmdString);
		
		assertEquals(1, cmd.getParameters().size());

	}

}
