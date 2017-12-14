package e2e;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when; 

import de.mbrero.see.api.TestRunController;
import de.mbrero.see.console.commands.ConsoleCommand;
import de.mbrero.see.console.commands.TestrunCommand;
import de.mbrero.see.console.controllers.MainController;
import de.mbrero.see.exceptions.ExtractorExecutionException;
import types.Extractors;


public class TestEndToEndTestRun {

	private TestrunCommand testCmd = new TestrunCommand();
	private ConsoleCommand consoleCmd = new ConsoleCommand();
	File outputExtractorResultFile = null;
	MainController ctrl = null;


	@Before
	public void setUp() throws Exception {
		ctrl = new MainController();
	}

	@After
	public void tearDown() throws Exception {
		Arrays.stream(outputExtractorResultFile.listFiles()).forEach(File::delete);
		//outputTrecFile.delete();
	}

	@Test
	public void proceedThroughCompletePipeline() throws Exception {
		String type = "quickumls";
		String tester = "example@example.com";
		HashMap<String, String> params = new HashMap<>();
		File inputFile = new File(getClass().getClassLoader().getResource("input/ncbi/testrun_allsteps.txt").getFile());
		File outputTrecFile = new File(getClass().getClassLoader().getResource("output/trec/qrel_testrun_allsteps").getFile());
		outputExtractorResultFile = new File("src/test/resources/output/extractor-result");
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
		File inputFile = new File(getClass().getClassLoader().getResource("input/ncbi/testrun_skip.txt").getFile());
		File outputTrecFile = new File(getClass().getClassLoader().getResource("output/trec/qrel_testrun_skip").getFile());
		outputExtractorResultFile = new File(getClass().getClassLoader().getResource("output/extractor-result-skip-test").getFile());
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
	
	@Test(expected=Exception.class)
	public void ThrowsNoExceptionIfExtractorParametersAreMissingAndSkipParameterIsNotSet() throws Exception
	{
		String type = "quickumls";
		HashMap<String, String> params = new HashMap<>();
		File inputFile = new File(getClass().getClassLoader().getResource("input/ncbi/testrun_skip.txt").getFile());
		File outputTrecFile = new File(getClass().getClassLoader().getResource("output/trec/qrel_testrun_skip").getFile());
		outputExtractorResultFile = new File(getClass().getClassLoader().getResource("output/extractor-result-skip-test").getFile());
		params.put(TestrunCommand.TYPE_PARAMETER, type);
		params.put(TestrunCommand.INPUT_PATH_PARAMETER, inputFile.getAbsolutePath());
		params.put(TestrunCommand.OUTPUT_PATH_EXTRACTOR_PARAMETER, outputExtractorResultFile.getAbsolutePath());
		params.put(TestrunCommand.OUTPUT_PATH_TREC_PARAMETER, outputTrecFile.getAbsolutePath());
		consoleCmd.setCommand("testrun");
		consoleCmd.setParameters(params);
		
		testCmd.setTestrunController(getMockTestRunController());
		testCmd.execute(consoleCmd);
		
	}

	
	@Test(expected=Exception.class)
	public void ThrowsAnExceptionIfInputPathMissingAndSkipParameterIsNotSet() throws Exception
	{
		String type = "quickumls";
		String tester = "example@example.com";
		HashMap<String, String> params = new HashMap<>();
		File inputFile = new File(getClass().getClassLoader().getResource("input/ncbi/testrun_skip.txt").getFile());
		File outputTrecFile = new File(getClass().getClassLoader().getResource("output/trec/qrel_testrun_skip").getFile());
		outputExtractorResultFile = new File(getClass().getClassLoader().getResource("output/extractor-result-skip-test").getFile());
		params.put(TestrunCommand.TYPE_PARAMETER, type);
		params.put(TestrunCommand.TESTER_PARAMETER, tester);
		params.put(TestrunCommand.OUTPUT_PATH_TREC_PARAMETER, outputTrecFile.getAbsolutePath());
		params.put(TestrunCommand.TEST_PARAMETER, "");
		consoleCmd.setCommand("testrun");
		consoleCmd.setParameters(params);
		
		testCmd.setTestrunController(getMockTestRunController());
		testCmd.execute(consoleCmd);
	}
	
	@Test
	public void ThrowsNoExceptionIfTesterParametersIsMissingAndSkipParameterIsSet() throws Exception
	{
		String type = "quickumls";
		String tester = "example@example.com";
		HashMap<String, String> params = new HashMap<>();
		File inputFile = new File(getClass().getClassLoader().getResource("input/ncbi/testrun_skip.txt").getFile());
		File outputTrecFile = new File(getClass().getClassLoader().getResource("output/trec/qrel_testrun_skip").getFile());
		outputExtractorResultFile = new File(getClass().getClassLoader().getResource("output/extractor-result-skip-test").getFile());
		params.put(TestrunCommand.TYPE_PARAMETER, type);
		params.put(TestrunCommand.INPUT_PATH_PARAMETER, inputFile.getAbsolutePath());
		params.put(TestrunCommand.OUTPUT_PATH_TREC_PARAMETER, outputTrecFile.getAbsolutePath());
		params.put(TestrunCommand.SKIP_PARAMETER, TestrunCommand.SKIP_EXTRACTION_VALUE);
		consoleCmd.setCommand("testrun");
		consoleCmd.setParameters(params);
		
		testCmd.setTestrunController(getMockTestRunController());
		testCmd.execute(consoleCmd);
	}
		
	@Ignore
	public void ThrowsNoExceptionIfInputPathIsMissingAndSkipParameterIsSet() throws Exception
	{
		String type = "quickumls";
		String tester = "example@example.com";
		HashMap<String, String> params = new HashMap<>();
		File inputFile = new File(getClass().getClassLoader().getResource("input/ncbi/testrun_skip.txt").getFile());
		File outputTrecFile = new File(getClass().getClassLoader().getResource("output/trec/qrel_testrun_skip").getFile());
		outputExtractorResultFile = new File(getClass().getClassLoader().getResource("output/extractor-result-skip-test").getFile());
		params.put(TestrunCommand.TYPE_PARAMETER, type);
		params.put(TestrunCommand.TESTER_PARAMETER, tester);
		params.put(TestrunCommand.INPUT_PATH_PARAMETER, inputFile.getAbsolutePath());
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
		
	}
	
	
	private TestRunController getMockTestRunController()
			throws IOException, InterruptedException, ExtractorExecutionException, Exception {
		TestRunController mockCtrl = Mockito.mock(TestRunController.class);
		Mockito.doNothing().when(mockCtrl).initializeTestRun();
		Mockito.doNothing().when(mockCtrl).runExtractor();
		Mockito.doNothing().when(mockCtrl).getAnnotationsFromExtractorResult();
		Mockito.doNothing().when(mockCtrl).saveAnnotationsToDatabase();
		Mockito.doNothing().when(mockCtrl).saveAnnotationsToTrecFile();
		return mockCtrl;
	}
	

}
