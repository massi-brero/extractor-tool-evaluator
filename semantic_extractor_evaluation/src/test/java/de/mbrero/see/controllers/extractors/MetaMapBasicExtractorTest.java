package de.mbrero.see.controllers.extractors;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.mbrero.see.exceptions.ExtractorExecutionException;
import types.Extractors;

/**
 * Test in console with:
 * ./bin/metamap /home/massi/projects/extractor_benchmarker/semantic_extractor_evaluation/src/
 * test/resources/mm_test/input/mm_input.txt /home/massi/projects/extractor_benchmarker/semantic_extractor_evaluation/
 * src/test/resources/mm_test/output/output.txt --XMLf
 * 
 * @author massi.brero@gmail.com
 *
 */
public class MetaMapBasicExtractorTest {
	private File inputFile = null;
	private File outputFile = null;
	private MetaMapController mmCtrl = null;
	

	@Before
	public void setUp() throws Exception {
		inputFile = new File(getClass().getClassLoader().getResource("mm_test/input/mm_input.txt").getFile());
		outputFile = new File(System.getProperty("user.dir") + "/src/test/resources/mm_test/output/output.txt");
		HashMap<String, String> params= new HashMap<>();
		mmCtrl = (MetaMapController)ExtractorControllerFactory.getExtractor(Extractors.METAMAP, null, inputFile, outputFile, params);
	}

	@After
	public void tearDown() throws Exception {
		//outputFile.delete();
	}

	@Test
	public void testStartExtractor() throws Exception {
		mmCtrl.start(false);
		assertTrue(outputFile.exists());
	}
	
	@Test
	public void testCheckOutputFile() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testgetExecutionTime() throws IOException, InterruptedException, ExtractorExecutionException {
		mmCtrl.start(false);
		Duration duration = mmCtrl.getExecutionTime();	
		
		assertTrue(duration.getSeconds() > 0);
	}
	
	@Test
	public void testSetSimpleParameterPairs() throws IOException, InterruptedException, ExtractorExecutionException {
		HashMap<String, String> params1 = new HashMap<>();
		params1.put("-foo", "0");
		params1.put("-bar", "1");
		
		mmCtrl.setParams(params1);
		String[] result1 = mmCtrl.getParams();
		
		assertEquals(4, result1.length);
		assertEquals("-bar", result1[0]);
		assertEquals("1", result1[1]);
		assertEquals("-foo", result1[2]);
		assertEquals("0", result1[3]);

	}
	
	@Test
	public void testSetParameterWithoutValue() throws IOException, InterruptedException, ExtractorExecutionException {
		HashMap<String, String> params1 = new HashMap<>();
		params1.put("-foo", "0");
		params1.put("-bar", null);
		
		mmCtrl.setParams(params1);
		String[] result1 = mmCtrl.getParams();
		
		assertEquals(3, result1.length);
		assertEquals("-bar", result1[0]);
		assertEquals("-foo", result1[1]);
		assertEquals("0", result1[2]);
		
		HashMap<String, String> params2 = new HashMap<>();
		params1.put("-foo", "0");
		params1.put("-bar", "");
		
		mmCtrl.setParams(params1);
		String[] result2 = mmCtrl.getParams();
		
		assertEquals(3, result2.length);
		assertEquals("-bar", result2[0]);
		assertEquals("-foo", result2[1]);
		assertEquals("0", result2[2]);

	}

	@Test
	public void testSetParameterForXMLOutput() throws IOException, InterruptedException, ExtractorExecutionException {
		HashMap<String, String> params = new HashMap<>();
		params.put("-XMLn1", "");
		mmCtrl.setParams(params);
		mmCtrl.start(false);
		assertTrue(outputFile.exists());
		assertEquals("xml", outputFile.getName().substring(outputFile.getName().lastIndexOf(".") + 1));
	}
	
	private ArrayList<String> getParams() {
		ArrayList<String> params = new ArrayList<>();
		params.add(inputFile.getAbsolutePath());
		params.add(outputFile.getAbsolutePath());
		
		return params;
	}
	
}
