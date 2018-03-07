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
import de.mbrero.see.services.AnnotationsService;
import types.Extractors;
import types.ParserType;

/**
 * This class initializes the test run for an extraction experiment. It stores
 * the technical and non-technical environment variables of the test run.
 * 
 * @author massimiliano.brero@studium.fernuni-hagen.de
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
	private boolean skipExtraction = false;
	/*
	 * Id to be stored in the database with this test run
	 */
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
	 *            String Parameters that have to be used with the extractor
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

	/**
	 * Sets all needed environment parameters for a test run.
	 */
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

	/**
	 * Configure the concept mapper to retrieve the annotations extractedfrom the input files.<br>
	 * Expand switch case with additional concept mappers.
	 * 
	 * @throws Exception
	 */
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

	/**
	 * Save the extracted concepts to a MySQL database.
	 * @throws Exception
	 */
	public void saveAnnotationsToDatabase() throws Exception {
		annotationsService.saveAnnotationsToDatabase(annotations);
	}

	/**
	 * Save the extracted annotations to a trec result file.<br>
	 * @see <a href="https://github.com/usnistgov/trec_eval">TREC Evaluation Tool Information</a>
	 * @throws IOException
	 */
	public void saveAnnotationsToTrecFile() throws IOException {
		annotationsService.saveAnnotationsToTRECResultUsingCUI(annotations, outputTRECFile);
	}

	/**
	 * Set one of the possible test result types @see {@link TestRunResults}
	 * @param result
	 */
	public void setResult(TestRunResults result) {
		run.setResult(result.toString());
		model.update(run);
	}

	/**
	 * Set the time the concept mapper took an that was measured.
	 * @param duration
	 */
	public void setDuration(long duration) {
		run.setDuration(duration);
	}

	/**
	 * Instantiate the chosen extractor from the factory {@link ExtractorFactory} and start the test run with the given parameters.
	 * 
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ExtractorExecutionException
	 */
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

	/**
	 * Test if the parameter to skip the extraction step was set.
	 * 
	 * @return booleean
	 */
	public boolean isSkipExtraction() {
		return skipExtraction;
	}

	/**
	 * Set if the extraction step should be skipped. use this if there was an error after a succesful extraction<br>
	 * and you do not want it to run again but continue with the following steps of the execution pipeline.<br>
	 * See manual for detailed information.
	 * 
	 * @param skipExtraction
	 */
	public void setSkipExtraction(boolean skipExtraction) {
		this.skipExtraction = skipExtraction;
	}

}
