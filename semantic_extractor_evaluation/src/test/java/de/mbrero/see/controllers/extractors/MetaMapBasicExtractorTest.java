package de.mbrero.see.controllers.extractors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.mbrero.see.exceptions.ExtractorExecutionException;
import types.Extractors;

/**
 * Test in console with: ./bin/metamap
 * /home/massi/projects/extractor_benchmarker/semantic_extractor_evaluation/src/
 * test/resources/mm_test/input/mm_input.txt
 * /home/massi/projects/extractor_benchmarker/semantic_extractor_evaluation/
 * src/test/resources/mm_test/output/output.txt --XMLf
 * 
 * @author massi.brero@gmail.com
 *
 */
public class MetaMapBasicExtractorTest {
	private File inputFile = null;
	private File outputFileTxt = null;
	private File outputFileXml = null;
	private MetaMapController mmCtrl = null;

	@Before
	public void setUp() throws Exception {
		inputFile = new File(getClass().getClassLoader().getResource("mm_test/input/mm_input.txt").getFile());
		outputFileTxt = new File(System.getProperty("user.dir") + "/src/test/resources/mm_test/output/output.txt");
		outputFileXml = new File(System.getProperty("user.dir") + "/src/test/resources/mm_test/output/output.xml");
		HashMap<String, String> params = new HashMap<>();
		mmCtrl = (MetaMapController) ExtractorFactory.getExtractor(Extractors.METAMAP, null, inputFile,
				outputFileTxt, params);
	}

	@After
	public void tearDown() throws Exception {
		// outputFile.delete();
	}

	@Test
	public void testStartExtractor() throws Exception {
		mmCtrl.start();
		assertTrue(outputFileTxt.exists());
	}

	@Test
	public void testgetExecutionTime() throws IOException, InterruptedException, ExtractorExecutionException {
		mmCtrl.start();
		Duration duration = mmCtrl.getExecutionTime();

		assertTrue(duration.getSeconds() > 0);
	}

	@Test
	public void testSetSimpleParameterPairs() throws IOException, InterruptedException, ExtractorExecutionException {
		HashMap<String, String> params1 = new HashMap<>();
		params1.put("-foo", "0");
		params1.put("-bar", "1");

		mmCtrl.setParams(params1);
		ArrayList<String> result1 = mmCtrl.getParams();

		assertEquals(4, result1.size());
		assertEquals("-bar", result1.get(0));
		assertEquals("1", result1.get(1));
		assertEquals("-foo", result1.get(2));
		assertEquals("0", result1.get(3));

	}

	@Test
	public void testSetParameterWithoutValue() throws IOException, InterruptedException, ExtractorExecutionException {
		HashMap<String, String> params1 = new HashMap<>();
		params1.put("-foo", "0");
		params1.put("-bar", null);

		mmCtrl.setParams(params1);
		ArrayList<String> result1 = mmCtrl.getParams();

		assertEquals(3, result1.size());
		assertEquals("-bar", result1.get(0));
		assertEquals("-foo", result1.get(1));
		assertEquals("0", result1.get(2));

		HashMap<String, String> params2 = new HashMap<>();
		params2.put("-foo", "0");
		params2.put("-bar", "");

		mmCtrl.setParams(params2);
		ArrayList<String> result2 = mmCtrl.getParams();

		assertEquals(3, result2.size());
		assertEquals("-bar", result2.get(0));
		assertEquals("-foo", result2.get(1));
		assertEquals("0", result2.get(2));

	}

	@Test
	public void testSetValueWithoutCorrespondingParameter()
			throws IOException, InterruptedException, ExtractorExecutionException {
		HashMap<String, String> params1 = new HashMap<>();
		params1.put("-foo", "0");
		params1.put(null, "1");

		mmCtrl.setParams(params1);
		ArrayList<String> result1 = mmCtrl.getParams();

		assertEquals(3, result1.size());
		assertEquals("1", result1.get(0));
		assertEquals("-foo", result1.get(1));
		assertEquals("0", result1.get(2));

		HashMap<String, String> params2 = new HashMap<>();
		params2.put("-foo", "0");
		params2.put("", "1");

		mmCtrl.setParams(params2);
		ArrayList<String> result2 = mmCtrl.getParams();

		assertEquals(3, result2.size());
		assertEquals("1", result2.get(0));
		assertEquals("-foo", result2.get(1));
		assertEquals("0", result2.get(2));

	}

	@Test
	public void testSetParameterForXMLOutput() throws IOException, InterruptedException, ExtractorExecutionException {
		HashMap<String, String> params = new HashMap<>();
		params.put("--XMLn1", "");
		MetaMapController mCtrlXml = (MetaMapController) ExtractorFactory.getExtractor(Extractors.METAMAP,
				null, inputFile, outputFileXml, params);

		mCtrlXml.setParams(params);
		mCtrlXml.start();
		BufferedReader br = new BufferedReader(new FileReader(outputFileXml));
		String text = new String(Files.readAllBytes(Paths.get(outputFileXml.getAbsolutePath())),
				StandardCharsets.UTF_8);
		br.close();

		assertTrue(outputFileXml.exists());
		assertTrue(text.contains("<CandidateCUI>C0011849</CandidateCUI>"));
	}

	@Test
	public void testStartWithDisanbuguationServer()
			throws IOException, InterruptedException, ExtractorExecutionException {

		mmCtrl.setProcessBuilder(new ProcessBuilder());
		mmCtrl.setWithDisambiguiation(true);
		mmCtrl.start();
		String output = mmCtrl.getProcessOutput();
		System.out.println();
			
		assertTrue(output.contains("Stopping wsdserverctl"));
	}

	@Test
	public void testgetErrorWhileExecutingCommand() throws IOException, InterruptedException, ExtractorExecutionException {
		HashMap<String, String> params = new HashMap<>();
		params.put("--ABCDE", "");
		MetaMapController mCtrlXml = (MetaMapController) ExtractorFactory.getExtractor(Extractors.METAMAP,
				null, inputFile, outputFileXml, params);

		mCtrlXml.setProcessBuilder(new ProcessBuilder());
		mCtrlXml.setParams(params);
		mCtrlXml.start();
		
		String output = mCtrlXml.getProcessErrors();
			
		assertTrue(output.contains("MetaMap ERROR"));
	}

}
