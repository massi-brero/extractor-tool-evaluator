package de.mbrero.see.api;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import de.mbrero.see.controllers.extractors.Extractor;
import de.mbrero.see.controllers.extractors.ExtractorFactory;
import de.mbrero.see.exceptions.ExtractorExecutionException;
import de.mbrero.see.models.TestRunModel;
import de.mbrero.see.parser.AbstractParser;
import de.mbrero.see.parser.MetaMapParser;
import de.mbrero.see.parser.ParserFactory;
import de.mbrero.see.parser.QuickUmlsParser;
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
	private AnnotationsService annotationsService;
	private AbstractParser parser = null;
	public static int testRunId = 0;

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
		annotationsService = new AnnotationsService();
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
				parser = (MetaMapParser) ParserFactory.getInstance(ParserType.METAMAP);
				parser.parse(outputExtractorResult);
				annotations = parser.getAnnotations();
				break;
			case QUICKUMLS:
				parser = (QuickUmlsParser) ParserFactory.getInstance(ParserType.QUICKUMLS);
				parser.parse(outputExtractorResult);
				annotations = parser.getAnnotations();
				break;
			default:
				throw new ExtractorExecutionException("This extractor is currently not supported.");
		}
	}

	public void saveAnnotationsToDatabase() throws Exception {
		annotationsService.saveAnnotationsToDatabase(annotations);
	}

	public void saveAnnotationsToTrecFile() throws IOException {
		annotationsService.saveAnnotationsToTRECResultUsingCUI(annotations, outputTRECFile);
	}

	public void setResult(TestRunResults result) {
		run.setResult(result.toString());
		model.update(run);
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
			TestRunController.testRunId = run.getId();
		} else {
			throw new ExtractorExecutionException("There was an error while executing the extraction process.");
		}

	}

}
