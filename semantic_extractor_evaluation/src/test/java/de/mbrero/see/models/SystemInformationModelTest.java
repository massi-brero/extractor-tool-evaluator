package de.mbrero.see.models;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.mbrero.see.persistance.dto.SystemInformation;

public class SystemInformationModelTest {
	private SystemInformationModel systemModel = new SystemInformationModel();
	
	@Before
	public void setUp() throws Exception {
	}


	@Test
	public void testGetSystemInformation() {	

		SystemInformation sysInfo = systemModel.getSystemInformation();
		
		System.out.println(sysInfo.getAvailableProcessors());
		System.out.println(sysInfo.getFreeMemory());
		System.out.println(sysInfo.getMaxMemoryForJVM());
		System.out.println(sysInfo.getTotalMemoryForJVM());
		System.out.println(sysInfo.getOS());
		
		assertTrue(sysInfo.getAvailableProcessors() > -1);
		assertTrue(sysInfo.getFreeMemory() > -1);
		assertTrue(sysInfo.getMaxMemoryForJVM() > -1);
		assertTrue(sysInfo.getTotalMemoryForJVM() > -1);
		assertFalse(sysInfo.getOS().isEmpty());
		assertFalse(sysInfo.getJavaVendor().isEmpty());
		assertFalse(sysInfo.getJavaVendor().isEmpty());
		assertFalse(sysInfo.getJavaVersion().isEmpty());

	}

}
