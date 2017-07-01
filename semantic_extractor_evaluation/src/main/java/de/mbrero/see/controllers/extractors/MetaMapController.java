package de.mbrero.see.controllers.extractors;

import java.io.File;
import java.util.HashMap;

public class MetaMapController extends AbstractExtractorController {
	private String publicMMPath = "";
	private String startExtractionCmd = "./bin/wsdserverctl start";
	private String mmStartCmd = "./bin/skrmedpostctl start";
	private String mmStopCmd = "./bin/skrmedpostctl stop";
	private String disambiguationStartCmd = "./bin/wsdserverctl start";
	private String disambiguationStopCmd = "./bin/wsdserverctl stop";
	
	
	public MetaMapController(File inputFile, File outputFile, HashMap<String, String> params) {
		setInputFile(inputFile);
		setOutputFile(outputFile);
		setParams(params);
	}
	
	/**
	 * Starts all the server for an annotation run.
	 * 
	 * @param params HashMap<String, String> for the console run.
	 * @param startDisambiguation starts disambiguation server. This is optional.
	 */
	public void start(HashMap<String, String> params, Boolean startDisambiguation) {
		
		startTagger();
		if (startDisambiguation) 
			startDisambiguationServer();
		super.start(params);
		
		stopDisambiguationServer();
		stopMMServer();
	}

	private void startDisambiguationServer() {
		// TODO Auto-generated method stub
		
	}

	private void startTagger() {
		// TODO Auto-generated method stub
		
	}
	
	public void stopDisambiguationServer() {
		// TODO Auto-generated method stub
		
	}

	public void stopMMServer() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return the publicMMPath
	 */
	public String getPublicMMPath() {
		return publicMMPath;
	}

	/**
	 * @param publicMMPath the publicMMPath to set
	 */
	public void setPublicMMPath(String publicMMPath) {
		this.publicMMPath = publicMMPath;
	}

	/**
	 * @return the mmStartCmd
	 */
	public String getMmStartCmd() {
		return mmStartCmd;
	}

	/**
	 * @param mmStartCmd the mmStartCmd to set
	 */
	public void setMmStartCmd(String mmStartCmd) {
		this.mmStartCmd = mmStartCmd;
	}

	/**
	 * @return the mmStopCmd
	 */
	public String getMmStopCmd() {
		return mmStopCmd;
	}

	/**
	 * @param mmStopCmd the mmStopCmd to set
	 */
	public void setMmStopCmd(String mmStopCmd) {
		this.mmStopCmd = mmStopCmd;
	}

	/**
	 * @return the disambiguationStartCmd
	 */
	public String getDisambiguationStartCmd() {
		return disambiguationStartCmd;
	}

	/**
	 * @param disambiguationStartCmd the disambiguationStartCmd to set
	 */
	public void setDisambiguationStartCmd(String disambiguationStartCmd) {
		this.disambiguationStartCmd = disambiguationStartCmd;
	}

	/**
	 * @return the disambiguationStopCmd
	 */
	public String getDisambiguationStopCmd() {
		return disambiguationStopCmd;
	}

	/**
	 * @param disambiguationStopCmd the disambiguationStopCmd to set
	 */
	public void setDisambiguationStopCmd(String disambiguationStopCmd) {
		this.disambiguationStopCmd = disambiguationStopCmd;
	}
	
}
