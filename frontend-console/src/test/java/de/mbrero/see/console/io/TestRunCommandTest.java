package de.mbrero.see.console.io;

import static org.junit.Assert.assertEquals;

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
		
		String expected = "-a b -c d -e f ";
		assertEquals(expected, trCmd.getParamsString());
	}
	
	@Test
	public void testBuildParamsWithOneKeyMissingHisValue() {
		String params = "[a=b,c,e=f]";
		
		trCmd.setParamsString(params);
		trCmd.parseExtractorParameters();
		
		String expected = "-a b -c -e f ";
		assertEquals(expected, trCmd.getParamsString());
	}


}
