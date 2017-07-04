package de.mbrero.see.controllers.extractors;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

import de.mbrero.see.exceptions.ExtractorExecutionException;
import javassist.expr.NewArray;

/**
 * Base Class for starting the extractor software from the console.
 * 
 * @author massi.brero@gmail.com
 *
 */
public abstract class AbstractExtractorController implements ExtractorController {

	private Duration executionTime = null;
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
	private String[] params = null;

	public AbstractExtractorController(File inputFile, File outputFile, HashMap<String, String> params) {
		setInputFile(inputFile);
		setOutputFile(outputFile);
		setParams(params);
	}

	/**
	 * Builds the command line statement to start the extractor.
	 * 
	 * @return
	 */
	protected abstract String[] buildStartCommand();

	private String buildParams(ArrayList params) {
		return null;
	}

	/**
	 * @Override return the commandPath
	 */
	public File getBasePath() {
		return basePath;
	}

	/**
	 * @param commandPath
	 *            the commandPath to set
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
	 * @param inputFile
	 *            the inputFile to set
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
	 * @param outputFile
	 *            the outputFile to set
	 */
	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}

	/**
	 * Returns  the params as a simple array to be used with the {@link Process#}
	 * 
	 * @return the params
	 */
	public String[] getParams() {
		return params;
	}

	/**
	 * The parameters are accepted as a key/value pair of a HashMap.
	 * Example: key = "-output" / value = "file.txt" results to
	 *  ... -output file.txt ...
	 * <ol>
	 * <li>
	 * 		If the there is no value for a param like for "-XMLf"
	 * 		leave the corresponding value null or set the blank string "";
	 * </li>
	 * <li>
	 *		If the there is no key for a param like "/folder/file.txt"
	 * 		leave the corresponding key null or set the blank string "";
	 * </li>
	 * </ol>
	 * 
	 * @Override
	 * @param params
	 *            the params to set
	 */
	public void setParams(HashMap<String, String> params) {
		
		ArrayList<String> paramsAsArray = new ArrayList<>();
		
		params.forEach( (key, value) -> {
			if (key != null && !key.isEmpty())
				paramsAsArray.add(key);
			
			if (value != null && !value.isEmpty())
				paramsAsArray.add(value);
		});
		
		 String[] result = new String[params.size() * 2];
		
		this.params =  paramsAsArray.toArray(result);
	}

	protected int runLinuxExec(String[] command)
			throws IOException, InterruptedException, ExtractorExecutionException {

		String[] cmd = command;
		Process p = new ProcessBuilder(cmd).redirectError(Redirect.INHERIT).redirectOutput(Redirect.INHERIT).start();

		int result = p.waitFor();
		
		/** 
		 * @todo: return error from process
		 */
		if (result != 0)
			throw new ExtractorExecutionException();
		
		return result;

	}

	/**
	 * @param executionTime the executionTime to set
	 */
	public void setExecutionTime(Duration executionTime) {
		this.executionTime = executionTime;
	}
	
	/**
	 * @param executionTime get the executionTime
	 */
	public Duration getExecutionTime() {
		return executionTime;
	}

}
