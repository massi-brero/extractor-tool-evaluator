package de.mbrero.see.controllers;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import de.mbrero.see.models.TestRunModel;
import de.mbrero.see.persistance.dto.TestRun;
import de.mbrero.see.persistance.dto.types.TestRunResults;

/**
 * This class initializes the test run for an extraction experiment. It stores the technical
 * and non-technical environment variables of the test run.
 * 
 * @author massi.brero@gmail.com
 *
 */
public class TestRunController {
	TestRun run = new TestRun();
	TestRunModel model = new TestRunModel();
	File input = null;
	File outputExtractorResult = null;
	File outputTRECFile = null;
	String tester = "";
	HashMap<String, String> params = new HashMap<>();

	/**
	 * 
	 * @param input Path where the text for the extraction process are stored.
	 * @param output Path Path and filename for the TREC result file of the annotation process.
	 * @param tester String Email of the person doing the extraction test.
	 * @param params String Paramaters that have to be used with the extractor call.
	 */
	public TestRunController(File input, 
							 File outputExtractorResult, 
							 File outputTRECFile, 
							 String tester, 
							 HashMap<String, String> params) {
		this.input = input;
		this.outputExtractorResult = outputExtractorResult;
		this.outputTRECFile = outputTRECFile;
		this.tester = tester;
		this.params = params;
	}

	public void initializeTestRun() {
		

		run.setInputPath(input.getAbsolutePath());
		run.setOutputPathExtractorResult(outputExtractorResult.getAbsolutePath());
		run.setOutputPathTRECFile(outputTRECFile.getAbsolutePath());
		run.setDate(new Date());
		run.setResult(TestRunResults.PENDING);
		run.setParameters(params.toString());
		run.setTester(tester);
		run.setSystemInformation(model.getSystemInformation().toString());
		
		model.save(run);
	}
	
	public void setResult(TestRunResults result) {
		run.setResult(result);
	}
	
	public void setDuration(TestRunResults result) {
		//run.setDuration(result);
	}

}
