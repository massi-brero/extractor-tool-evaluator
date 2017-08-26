package de.mbrero.see.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import de.mbrero.see.controllers.extractors.Extractor;
import de.mbrero.see.controllers.extractors.ExtractorFactory;
import de.mbrero.see.exceptions.ExtractorExecutionException;
import de.mbrero.see.models.TestRunModel;
import de.mbrero.see.parser.CRAFTParser;
import de.mbrero.see.parser.MetaMapParser;
import de.mbrero.see.parser.ParserFactory;
import de.mbrero.see.persistance.dto.Annotation;
import de.mbrero.see.persistance.dto.TestRun;
import de.mbrero.see.persistance.dto.types.TestRunResults;
import types.Extractors;
import types.ParserType;

/**
 * This class initializes the test run for an extraction experiment. It stores
 * the technical and non-technical environment variables of the test run.
 * 
 * @author massi.brero@gmail.com
 *
 */
public class TestRunController {

	private TestRun run = new TestRun();
	private TestRunModel model = new TestRunModel();
	private File inputPath = null;
	private File outputExtractorResult = null;
	private File outputTRECFile = null;
	private String tester = "";
	private Extractors type;
	private HashMap<String, String> params = new HashMap<>();
	private HashMap<String, HashMap<String, Annotation>> annotations;

	/**
	 * 
	 * @param input
	 *            Path where the texts for the extraction process are stored.
	 * @param output
	 *            Path Path and filename for the TREC result file of the
	 *            annotation process.
	 * @param tester
	 *            String Email of the person doing the extraction test.
	 * @param params
	 *            String Paramaters that have to be used with the extractor
	 *            call.
	 */
	public TestRunController(File input, Extractors type, File outputExtractorResult, File outputTRECFile,
			String tester, HashMap<String, String> params) {
		this.inputPath = input;
		this.type = type;
		this.outputExtractorResult = outputExtractorResult;
		this.outputTRECFile = outputTRECFile;
		this.tester = tester;
		this.params = params;
	}

	public void initializeTestRun() {

		run.setInputPath(inputPath.getAbsolutePath());
		run.setOutputPathExtractorResult(outputExtractorResult.getAbsolutePath());
		run.setOutputPathTRECFile(outputTRECFile.getAbsolutePath());
		run.setExtractor(type.toString());
		run.setDate(new Date());
		run.setResult(TestRunResults.PENDING.toString());
		run.setParameters(params.toString());
		run.setTester(tester);
		run.setSystemInformation(model.getSystemInformation().toString());

	}

	public void getAnnotationsFromExtractorResult() throws Exception {
		switch (type) {
			case METAMAP:
				MetaMapParser mmParser = (MetaMapParser) ParserFactory.getInstance(ParserType.METAMAP);
				mmParser.parse(outputExtractorResult);
				annotations = mmParser.getAnnotations();
			default:
				throw new ExtractorExecutionException("This extractor is currently not supported.");
		}
	}

	public void saveAnnotationsToDatabase() throws Exception {

	}

	public void saveAnnotationsToTrecFile() {

	}

	public void setResult(TestRunResults result) {
		run.setResult(result.toString());
	}

	public void setDuration(long duration) {
		run.setDuration(duration);
	}

	public void runExtractor()
			throws IllegalArgumentException, IOException, InterruptedException, ExtractorExecutionException {

		Extractor extractorCtrl = ExtractorFactory.getExtractor(type, null, inputPath, outputExtractorResult, params);
		int result = extractorCtrl.start();

		if (result == 0) {
			setDuration(extractorCtrl.getExecutionTime().getSeconds());
			model.save(run);
		} else {
			throw new ExtractorExecutionException("There was an error while executiong the extraction process.");
		}

	}

	private void parseAnnotations() {

	}

}
