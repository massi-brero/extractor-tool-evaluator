package e2e;


import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.mbrero.see.console.commands.ConsoleCommand;
import de.mbrero.see.console.commands.ParsegoldCommand;
import de.mbrero.see.console.controllers.MainController;

public class TestEndToEndGoldStandard {

	private ConsoleCommand cmd = new ConsoleCommand();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void endToEntTestParsing() {
		HashMap<String, String> params = new HashMap<>();
		params.put("type", "craft");
		params.put("input", getClass().getClassLoader().getResource("test.txt").getFile());
		params.put("output", getClass().getClassLoader().getResource("output/test2.txt").getPath());
		cmd.setCommand("parsegold");
		cmd.setParameters(params);

		MainController ctrl = new MainController();

		int result = ctrl.executeCommand(cmd);
		assertTrue(ctrl.getCommandObject() instanceof ParsegoldCommand);
		assertTrue(result == 0);

	}

}
