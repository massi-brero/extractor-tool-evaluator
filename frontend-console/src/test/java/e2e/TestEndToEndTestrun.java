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


public class TestEndToEndTestrun {

	private File input = null;
	private File extractorResult = null;
	private File trecResult = null;
	ConsoleCommand cmd = null;
	

	@Before
	public void setUp() throws Exception {
		cmd = new ConsoleCommand();
		input = new File(getClass().getClassLoader().getResource("input/ncbi/test1.txt").getFile());
		extractorResult = new File(getClass().getClassLoader().getResource("output/extractor_result").getFile());
		trecResult = new File(getClass().getClassLoader().getResource("output/trec").getFile());
		
		buildCommandFixture();
	}


	@After
	public void tearDown() throws Exception {
		extractorResult.delete();
		trecResult.delete();
	}

	@Test
	public void endToEndTestSkipExtractionParameter() {

		MainController ctrl = new MainController();

		int result = ctrl.executeCommand(cmd);
		assertTrue(ctrl.getCommandObject() instanceof ParsegoldCommand);
		assertTrue(result == 0);

	}
	
	private void buildCommandFixture() {
		
		HashMap<String, String> params = null;
		params = new HashMap<>();
		
		params.put("type", "quickumls");
		params.put("input", input.getAbsolutePath());
		params.put("outTrec", trecResult.getAbsolutePath());
		params.put("outEx", extractorResult.getAbsolutePath());
		params.put("outTrec", trecResult.getAbsolutePath());
		
		cmd.setCommand("testrun");
		cmd.setParameters(params);

	}


}
