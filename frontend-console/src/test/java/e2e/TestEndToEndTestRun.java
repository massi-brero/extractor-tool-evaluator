package e2e;


import java.io.File;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import de.mbrero.see.api.TestRunController;
import de.mbrero.see.console.commands.ConsoleCommand;
import de.mbrero.see.console.commands.TestrunCommand;
import de.mbrero.see.console.controllers.MainController;
import types.Extractors;


public class TestEndToEndTestRun {

	private TestrunCommand testCmd = new TestrunCommand();
	private ConsoleCommand consoleCmd = new ConsoleCommand();
	private File inputFile = null;
	private File outputTrecFile = null;
	private File outputExtractorResultFile = null;
	MainController ctrl = null;


	@Before
	public void setUp() throws Exception {
		outputExtractorResultFile = new File(getClass().getClassLoader().getResource("output/extractor-result").getFile());
		ctrl = new MainController();
	}

	@After
	public void tearDown() throws Exception {
		//outputExtractorResultFile.delete();
		//outputTrecFile.delete();
	}

	@Test
	public void proceedThroughCompletePipeline() throws Exception {
		String type = "quickumls";
		String tester = "example@example.com";
		HashMap<String, String> params = new HashMap<>();
		inputFile = new File(getClass().getClassLoader().getResource("input/ncbi/testrun_allsteps.txt").getFile());
		outputTrecFile = new File(getClass().getClassLoader().getResource("output/trec/qrel_testrun_allsteps").getFile());
		params.put(TestrunCommand.TYPE_PARAMETER, type);
		params.put(TestrunCommand.TESTER_PARAMETER, tester);
		params.put(TestrunCommand.INPUT_PATH_PARAMETER, inputFile.getAbsolutePath());
		params.put(TestrunCommand.OUTPUT_PATH_EXTRACTOR_PARAMETER, outputExtractorResultFile.getAbsolutePath());
		params.put(TestrunCommand.OUTPUT_PATH_TREC_PARAMETER, outputTrecFile.getAbsolutePath());
		params.put(TestrunCommand.TEST_PARAMETER, "");
		consoleCmd.setCommand("testrun");
		consoleCmd.setParameters(params);

		TestRunController ctrl  =  new TestRunController(inputFile, 
														 Extractors.QUICKUMLS, 
														 outputExtractorResultFile,
														 outputTrecFile,
														 tester, 
														 new HashMap<>());
		
		TestRunController spyCtrl = Mockito.spy(ctrl);
		testCmd.setTestrunController(spyCtrl);
		testCmd.execute(consoleCmd);
		
		Mockito.verify(spyCtrl, Mockito.times(1)).runExtractor();
		Mockito.verify(spyCtrl, Mockito.times(1)).initializeTestRun();
		Mockito.verify(spyCtrl, Mockito.times(1)).getAnnotationsFromExtractorResult();
		Mockito.verify(spyCtrl, Mockito.times(1)).saveAnnotationsToDatabase();
		Mockito.verify(spyCtrl, Mockito.times(1)).saveAnnotationsToTrecFile();
	
	}
	
	//@Ignore
	@Test
	/**
	 * results in ~/projects/extractor_benchmarker/frontend-console/target/test-classes/output/
	 * @throws Exception
	 */
	public void skipExtraction() throws Exception {
		String type = "quickumls";
		String tester = "example@example.com";
		HashMap<String, String> params = new HashMap<>();
		inputFile = new File(getClass().getClassLoader().getResource("input/ncbi/testrun_skip.txt").getFile());
		outputTrecFile = new File(getClass().getClassLoader().getResource("output/trec/qrel_testrun_skip").getFile());
		params.put(TestrunCommand.TYPE_PARAMETER, type);
		params.put(TestrunCommand.TESTER_PARAMETER, tester);
		params.put(TestrunCommand.INPUT_PATH_PARAMETER, inputFile.getAbsolutePath());
		params.put(TestrunCommand.OUTPUT_PATH_EXTRACTOR_PARAMETER, outputExtractorResultFile.getAbsolutePath());
		params.put(TestrunCommand.OUTPUT_PATH_TREC_PARAMETER, outputTrecFile.getAbsolutePath());
		params.put(TestrunCommand.SKIP_PARAMETER, TestrunCommand.SKIP_EXTRACTION_VALUE);
		params.put(TestrunCommand.TEST_PARAMETER, "");
		consoleCmd.setCommand("testrun");
		consoleCmd.setParameters(params);

		TestRunController ctrl  =  new TestRunController(inputFile, 
														 Extractors.QUICKUMLS, 
														 outputExtractorResultFile,
														 outputTrecFile,
														 tester, 
														 new HashMap<>());
		
		TestRunController spyCtrl = Mockito.spy(ctrl);
		testCmd.setTestrunController(spyCtrl);
		testCmd.execute(consoleCmd);
		
		Mockito.verify(spyCtrl, Mockito.times(0)).runExtractor();
		Mockito.verify(spyCtrl, Mockito.times(0)).initializeTestRun();
		Mockito.verify(spyCtrl, Mockito.times(1)).getAnnotationsFromExtractorResult();
		Mockito.verify(spyCtrl, Mockito.times(1)).saveAnnotationsToDatabase();
		Mockito.verify(spyCtrl, Mockito.times(1)).saveAnnotationsToTrecFile();

	}

}
