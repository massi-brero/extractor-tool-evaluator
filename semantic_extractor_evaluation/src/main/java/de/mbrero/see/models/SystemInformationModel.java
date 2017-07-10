package de.mbrero.see.models;

import java.util.HashMap;

public class SystemInformationModel {
	
	HashMap<String, String>system = new HashMap<>();
	
	public HashMap<String, String> getSystemInformation()
	{
		
		system.put("available_processors", String.valueOf(Runtime.getRuntime().availableProcessors()));
		
		system.put("free_memory", String.valueOf(Runtime.getRuntime().freeMemory()));
		
		long maxMemory = Runtime.getRuntime().maxMemory();
		
		system.put("max_memory_for_jvm", String.valueOf(maxMemory == Long.MAX_VALUE ? 0 : maxMemory));
		
		system.put("total_memory_for_jvm", String.valueOf((Runtime.getRuntime().totalMemory())));
		
		system.put("os", String.valueOf(System.getProperty("os.name")));
		
		system.put("Java_version", String.valueOf(System.getProperty("java.version")));
		
		system.put("jvm_version", String.valueOf(System.getProperty("java.vm.version")));
		
		system.put("java_vendor", String.valueOf(System.getProperty("java.vendor")));
		
		//System.getProperties().list(System.out);
		    
		return system;
	}

	

}
