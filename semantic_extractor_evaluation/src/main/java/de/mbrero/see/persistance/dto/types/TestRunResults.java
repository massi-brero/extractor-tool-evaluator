package de.mbrero.see.persistance.dto.types;

import de.mbrero.see.persistance.dto.TestRun;

/**
 * Possible Outcomes for a test run. Stored in database:
 * @see TestRun
 * 
 * @author massimiliano.brero@studium.fernuni-hagen.de
 *
 */
public enum TestRunResults {
	SUCCESS,
	FAILED,
	PENDING,
	TEST
}
