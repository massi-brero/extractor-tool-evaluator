package de.mbrero.see.console.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationReader {

	
	public Properties readCommandConfiguration() {
		
		File configFile = new File(getClass().getClassLoader().getResource("command.cfg.xml").getFile());

		Properties props = new Properties();
		InputStream inputStream;
		
		try {
			inputStream = new FileInputStream(configFile);
			props.load(inputStream);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return props;
	}
}
