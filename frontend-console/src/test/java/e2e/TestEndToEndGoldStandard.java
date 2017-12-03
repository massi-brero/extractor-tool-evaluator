package e2e;


import static org.junit.Assert.assertEquals;
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
	
	private File trecFile = null;

	@Before
	public void setUp() throws Exception {
		trecFile = new File(getClass().getClassLoader().getResource("output").getFile() + "/qrel_test");
	}

	@After
	public void tearDown() throws Exception {
		//trecFile.delete();
	}

	@Test
	public void endToEndTestTRECWritingWithoutErrors() {
		HashMap<String, String> params = new HashMap<>();
		params.put("type", "craft");
		params.put("input", getClass().getClassLoader().getResource("input/ncbi/test1.txt").getFile());
		params.put("output", trecFile.getAbsolutePath());
		params.put("test", "");
		cmd.setCommand("parsegold");
		cmd.setParameters(params);

		MainController ctrl = new MainController();

		int result = ctrl.executeCommand(cmd);
		assertTrue(ctrl.getCommandObject() instanceof ParsegoldCommand);
		assertTrue(result == 0);

	}

}
