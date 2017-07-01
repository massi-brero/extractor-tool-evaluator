package de.mbrero.see.controllers.extractors;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
public class ExtractorFactoryTest {
	File inputFile = null;
	File outputFile = null;
	ExtractorController mmCtrl = null;

	@Before
	public void setUp() throws Exception {
		mmCtrl = ExtractorControllerFactory.getExstractor(Extractors.METAMAP);
		inputFile = new File(getClass().getClassLoader().getResource("mm_test/input/mm_input.txt").getFile());
		outputFile = new File(getClass().getClassLoader().getResource("mm_test/output/output.txt").getFile());
	}

	@After
	public void tearDown() throws Exception {
		//outputFile.delete();
	}

	@Test
	public void testStartExtractor() {
		mmCtrl.start(new HashMap<>());
		assertTrue(outputFile.exists());
	}
	
	@Test
	public void testCheckOutputFile() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testgetExecutionTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetParaneters() {
	}
	
	private ArrayList<String> getParams() {
		ArrayList<String> params = new ArrayList<>();
		params.add(inputFile.getAbsolutePath());
		params.add(outputFile.getAbsolutePath());
		
		return params;
	}
	
}
