package de.mbrero.see.controllers.extractors;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class AbstractExtractorController implements ExtractorController{

	private Float executionTime = 0f;
	private String basePath = "";
	File inputFile = null;
	File outputFile = null;
	private HashMap<String, String> params = null;
	
	@Override
	public void start(HashMap<String, String> params)
	{
		String cmd = this.buildCommand();
				
	}
	
	public Float getExecutionTime()
	{
		return executionTime;
	}
	
	private String buildCommand() {
				
		return null;
	}
	
	private String buildParams(ArrayList params)
	{
		return null;
	}

	/**
	 * @Override	
	 * return the commandPath
	 */
	public String getBasePath() {
		return basePath;
	}

	/**
	 * @param commandPath the commandPath to set
	 */
	public void setBasePath(String commandPath) {
		this.basePath = commandPath;
	}

	/**
	 * @return the inputFile
	 */
	public File getInputFile() {
		return inputFile;
	}

	/**
	 * @Override
	 * @param inputFile the inputFile to set
	 */
	public void setInputFile(File inputFile) {
		this.inputFile = inputFile;
	}

	/**
	 * @return the outputFile
	 */
	public File getOutputFile() {
		return outputFile;
	}

	/**
	 * @Override
	 * @param outputFile the outputFile to set
	 */
	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}

	/**
	 * @return the params
	 */
	public HashMap<String, String> getParams() {
		return params;
	}

	/**
	 * @Override
	 * @param params the params to set
	 */
	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}
	
	
}
