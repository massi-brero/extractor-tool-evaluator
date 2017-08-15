package e2e;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
		cmd.setCommand("parsegold");
		cmd.setParameters(params);

		MainController ctrl = new MainController();

		int result = ctrl.executeCommand(cmd);
		assertTrue(ctrl.getCommandObject() instanceof ParsegoldCommand);
		assertTrue(result == 0);

	}
	
	/*
	 * Please copy the MRCONSOLE.rff to the resources folder for this test
	 */
	@Test
	//@Ignore
	public void endToEndTestTRECFileANDatabaseRowsWritten() {
		HashMap<String, String> params = new HashMap<>();
		params.put("type", "craft");
		params.put("input", getClass().getClassLoader().getResource("input/ncbi/craft-test.xmi").getFile());
		params.put("output", trecFile.getAbsolutePath());
		cmd.setCommand("parsegold");
		cmd.setParameters(params);

		MainController ctrl = new MainController();	

		ctrl.executeCommand(cmd);
		assertTrue(trecFile.isFile());
	}
	
	@Test
	public void endToEndTestTRECFileThrowsErrorBecauseOfWronOntologyNameOfFolder() {
		HashMap<String, String> params = new HashMap<>();
		params.put("type", "craft");
		params.put("input", getClass().getClassLoader().getResource("input/abcd/craft-test.xmi").getFile());
		params.put("output", trecFile.getAbsolutePath());
		cmd.setCommand("parsegold");
		cmd.setParameters(params);

		MainController ctrl = new MainController();	

		int result = ctrl.executeCommand(cmd);
		assertEquals(1, result);

	}


}
