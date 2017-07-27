package de.mbrero.see.console.io;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import de.mbrero.see.console.commands.TestrunCommand;

public class TestRunCommandTest {

	TestrunCommand trCmd = null;

	@Before
	public void setUp() throws Exception {
		trCmd = new TestrunCommand();
	}


	@Test
	public void testBuildParamsWithKeyValuePairs() {
		String params = "[a=b,c=d,e=f]";
		
		trCmd.setParamsString(params);
		trCmd.parseExtractorParameters();
		
		HashMap<String, String> paramsExpected = new HashMap<>();
		paramsExpected.put("a", "b");
		paramsExpected.put("c", "d");
		paramsExpected.put("e", "f");
		assertEquals(paramsExpected, trCmd.getParams());
	}
	
	@Test
	public void testBuildParamsWithOneKeyMissingHisValue() {
		String params = "[a=b,c,e=f]";
		
		trCmd.setParamsString(params);
		trCmd.parseExtractorParameters();
		
		HashMap<String, String> paramsExpected = new HashMap<>();
		paramsExpected.put("a", "b");
		paramsExpected.put("c", "");
		paramsExpected.put("e", "f");
		assertEquals(paramsExpected, trCmd.getParams());;
	}


}
