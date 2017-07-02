package de.mbrero.see.controllers.extractors;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Base Class for strating the extractor software from the console.
 * @author massi.brero@gmail.com
 *
 */
public abstract class AbstractExtractorController implements ExtractorController{

	private Float executionTime = 0f;
	/**
	 * The console command that has to be executed.
	 */
	private String consoleCmd;
	/**
	 * The path the bin directory/files are stored.
	 */
	private File basePath = null;
	/*
	 * Path to the files that need to be processed.
	 */
	private File inputFile = null;
	/**
	 * The path where the output files from the extractors shall be saved to.
	 */
	private File outputFile = null;
	/**
	 * Parameters the extractors allows when started from the command line.
	 */
	private HashMap<String, String> params = null;
	
	

	/**
	 * Get the processing time the run needed for extracting the given documents.
	 */
	public Float getExecutionTime()
	{
		return executionTime;
	}
	
	/**
	 * Builds the command line statement to start the extractor.
	 * @return
	 */
	protected abstract String buildCommand();
	
	private String buildParams(ArrayList params)
	{
		return null;
	}

	/**
	 * @Override	
	 * return the commandPath
	 */
	public File getBasePath() {
		return basePath;
	}

	/**
	 * @param commandPath the commandPath to set
	 */
	public void setBasePath(File commandPath) {
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
