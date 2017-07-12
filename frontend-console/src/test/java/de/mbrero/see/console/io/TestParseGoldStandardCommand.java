package de.mbrero.see.console.io;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.mbrero.see.console.commands.ConsoleCommand;

public class TestParseGoldStandardCommand {
	
	private ConsoleCommand cmd = new ConsoleCommand();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCommandAnalysis() {
		cmd.setCommand("");
	}

}
