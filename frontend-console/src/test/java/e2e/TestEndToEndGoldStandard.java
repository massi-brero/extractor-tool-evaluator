package e2e;


import static org.junit.Assert.assertTrue;

import java.io.File;
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
	public void endToEntTestTRECWritingWithoutErrors() {
		HashMap<String, String> params = new HashMap<>();
		params.put("type", "craft");
		params.put("input", getClass().getClassLoader().getResource("test1.txt").getFile());
		params.put("output", (new File("src/test/resources/output/qrel_test")).getAbsolutePath());
		cmd.setCommand("parsegold");
		cmd.setParameters(params);

		MainController ctrl = new MainController();

		int result = ctrl.executeCommand(cmd);
		assertTrue(ctrl.getCommandObject() instanceof ParsegoldCommand);
		assertTrue(result == 0);

	}
	
	@Test
	public void endToEntTestTRECFileWritten() {
		HashMap<String, String> params = new HashMap<>();
		params.put("type", "craft");
		params.put("input", getClass().getClassLoader().getResource("craft-test.xmi").getFile());
		File trecFile = new File("src/test/resources/output/qrel_test");
		params.put("output", trecFile.getAbsolutePath());
		cmd.setCommand("parsegold");
		cmd.setParameters(params);

		MainController ctrl = new MainController();	

		ctrl.executeCommand(cmd);
		assertTrue(trecFile.exists());

	}

}
