package de.mbrero.see.controllers;

import java.util.Map;
import java.util.Properties;

import io.ConfigurationReader;

public class MainController {
	
	public static Map<String, String> bootstrap() {
		
		Properties props = (new ConfigurationReader()).readCommandConfiguration();
		
		return null;
		
	}

}
