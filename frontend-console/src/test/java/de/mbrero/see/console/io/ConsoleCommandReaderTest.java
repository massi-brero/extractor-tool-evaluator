package de.mbrero.see.console.io;

import org.junit.Test;

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
		// fail("Not yet implemented");
	}

	@Test
	public void testBuildCommandWithNoParametersAndNoArguments() throws ParameterError {
		String cmdString = "create";

		ConsoleCommand cmd = reader.buildCommand(cmdString);

		assertEquals(cmdString, cmd.getCommand());

	}

	@Test
	public void testBuildCommandWithNoParametersAndNoArgumentsbutALotOSpacesBefore() throws ParameterError {
		String cmdString = "  create";

		ConsoleCommand cmd = reader.buildCommand(cmdString);

		assertEquals("create", cmd.getCommand());

	}

	@Test
	public void testBuildCommandWithNoParametersAndNoArgumentsbutALotOSpacesAfter() throws ParameterError {
		String cmdString = "create   ";

		ConsoleCommand cmd = reader.buildCommand(cmdString);

		assertEquals("create", cmd.getCommand());

	}

	@Test(expected = ParameterError.class)
	public void testBuildCommandWithNoInput() throws ParameterError {

		String cmdString = "create -p ";

		ConsoleCommand cmd = reader.buildCommand(cmdString);

	}

	@Test
	public void testBuildCommandWithOneParametersAndOneArguments() throws ParameterError {
		String cmdString = "create -p test/test";

		ConsoleCommand cmd = reader.buildCommand(cmdString);

		assertEquals(1, cmd.getParameters().size());
		assertEquals("test/test", cmd.getParameters().get("p"));

	}
	
	@Test
	public void testBuildCommandWith2ParamsAndArguments() throws ParameterError {
		fail("not implemented");

	}
	
	@Test(expected = ParameterError.class)
	public void testBuildCommandMissingParam() throws ParameterError {

		String cmdString = "create test/test";

		ConsoleCommand cmd = reader.buildCommand(cmdString);

	}
	
	@Test(expected = ParameterError.class)
	public void testBuildCommandWMissingArgument() throws ParameterError {

		String cmdString = "create -p";

		ConsoleCommand cmd = reader.buildCommand(cmdString);

	}

}
