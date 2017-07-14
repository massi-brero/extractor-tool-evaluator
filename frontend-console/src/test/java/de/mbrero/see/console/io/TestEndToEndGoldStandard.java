package de.mbrero.see.console.io;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.mbrero.see.console.commands.ConsoleCommand;
import de.mbrero.see.console.commands.ParsegoldCommand;
import de.mbrero.see.console.controllers.MainController;

public class TestParseGoldStandardCommand {

	private ConsoleCommand cmd = new ConsoleCommand();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCommandAnalysis() {
		HashMap<String, String> params = new HashMap<>();
		params.put("type", "craft");
		params.put("path", getClass().getClassLoader().getResource("test.txt").getFile());
		cmd.setCommand("parsegold");
		cmd.setParameters(params);

		MainController ctrl = new MainController();

		int result = ctrl.executeCommand(cmd);
		assertTrue(ctrl.getCommandObject() instanceof ParsegoldCommand);
		assertTrue(result == 0);

	}

}
