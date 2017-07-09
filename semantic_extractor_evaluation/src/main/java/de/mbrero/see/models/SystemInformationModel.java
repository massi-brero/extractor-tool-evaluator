package de.mbrero.see.models;

import de.mbrero.see.persistance.dto.SystemInformation;

public class SystemInformationModel {
	
	private SystemInformation system = new SystemInformation();
	
	
	public SystemInformation getSystemInformation()
	{
		system.setAvailableProcessors( Runtime.getRuntime().availableProcessors());
		
		system.setFreeMemory(Runtime.getRuntime().freeMemory());
		
		long maxMemory = Runtime.getRuntime().maxMemory();
		
		system.setMaxMemoryForJVM(maxMemory == Long.MAX_VALUE ? 0 : maxMemory);
		
		system.setTotalMemoryForJVM(Runtime.getRuntime().totalMemory());
		
		system.setOS(System.getProperty("os.name"));
		
		system.setJavaVersion(System.getProperty("java.version"));
		
		system.setJvmVersion(System.getProperty("java.vm.version"));
		
		system.setJavaVendor(System.getProperty("java.vendor"));
		
		System.getProperties().list(System.out);
		    
		return system;
	}

	

}
