package de.mbrero.see.controllers.extractors;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import de.mbrero.see.exceptions.ExtractorExecutionException;

/**
 * Base Class for starting the extractor software from the console.
 * 
 * @author massi.brero@gmail.com
 *
 */
public abstract class AbstractExtractorController implements Extractor {

	private final int OUTPUT_TYPE_STDOUT = 0;
	private final int OUTPUT_TYPE_STDERR = 1;

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
	 * The started process {@link Process}
	 */
	private Process process = null;

	/**
	 * The started process {@link Process}
	 */
	private ProcessBuilder processBuilder = null;

	/*
	 * Process output as String
	 */
	private StringBuilder processOutput = new StringBuilder();
	/*
	 * Process error messages as String
	 */
	private StringBuilder processErrors = new StringBuilder();
	/**
	 * Parameters the extractors allows when started from the command line.
	 */
	protected ArrayList<String> params = null;

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
	protected abstract ArrayList<String> buildStartCommand();

	/**
	 * Returns the params as a simple array to be used with the {@link Process#}
	 * 
	 * @return the params
	 */
	public ArrayList<String> getParams() {
		return params;
	}

	/**
	 * The parameters are accepted as a key/value pair of a HashMap. Example:
	 * key = "-output" / value = "file.txt" results to ... -output file.txt ...
	 * <ol>
	 * <li>If the there is no value for a param like for "-XMLf" leave the
	 * corresponding value null or set the blank string "";</li>
	 * <li>If the there is no key for a param like "/folder/file.txt" leave the
	 * corresponding key null or set the blank string "";</li>
	 * </ol>
	 * 
	 * @Override
	 * @param params
	 *            the params to set
	 */
	public void setParams(HashMap<String, String> params) {

		ArrayList<String> paramsAsArray = new ArrayList<>();

		if (params != null) {
			params.forEach((key, value) -> {
				if (key != null && !key.isEmpty())
					paramsAsArray.add(key);
				
				if (value != null && !value.isEmpty())
					paramsAsArray.add(value);
			});			
		}

		this.params = paramsAsArray;
	}
	
	/**
	 * Runs the command to start the extract on an Linux OS.
	 * 
	 * @param command
	 *            {@link String} the Linux command to start the extraction
	 *            process.
	 * @param addParams Add user params for extraction process
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ExtractorExecutionException
	 */
	protected int runLinuxExec(ArrayList<String> command, boolean addParams)
			throws IOException, InterruptedException, ExtractorExecutionException {


		if (addParams) {
			command.addAll(getParams());
		}
	
		if (getProcessBuilder() == null) {
			setProcessBuilder(new ProcessBuilder().redirectError(Redirect.INHERIT).redirectOutput(Redirect.INHERIT));
		}
		
		process = getProcessBuilder().command(command).start();
		writeOutput(OUTPUT_TYPE_STDOUT);
		writeOutput(OUTPUT_TYPE_STDERR);

		int result = process.waitFor();
		/**
		 * @todo: return error from process
		 */
		if (result != 0)
			throw new ExtractorExecutionException();

		return result;

	}

	private void writeOutput(int type) {
		StringBuilder output = new StringBuilder();

		Thread ioThread = new Thread() {
			BufferedReader reader = null;
			@Override
			public void run() {
				try {
					if (type == OUTPUT_TYPE_STDOUT)
						reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					else if (type == OUTPUT_TYPE_STDERR) {
						reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
					}
					String line = null;

					while ((line = reader.readLine()) != null) {
						switch (type) {
							case OUTPUT_TYPE_STDERR:
								processErrors.append(line);
								break;
							case OUTPUT_TYPE_STDOUT:
							default:
								processOutput.append(line);
						}
					}
					
				} catch (final Exception e) {
					e.printStackTrace();
				} finally {
					//TODO: avoid this nested error handling
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		ioThread.start();

	}

	/**
	 * @param executionTime
	 *            the executionTime to set
	 */
	public void setExecutionTime(Duration executionTime) {
		this.executionTime = executionTime;
	}

	/**
	 * @param executionTime
	 *            get the executionTime
	 */
	public Duration getExecutionTime() {
		return executionTime;
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
	 * @return the process
	 */
	public Process getProcess() {
		return process;
	}

	/**
	 * @return the processBuilder
	 */
	public ProcessBuilder getProcessBuilder() {
		return processBuilder;
	}

	/**
	 * @param processBuilder
	 *            the processBuilder to set
	 */
	public void setProcessBuilder(ProcessBuilder processBuilder) {
		this.processBuilder = processBuilder;
	}

	/**
	 * @return the processOutput
	 */
	public String getProcessOutput() {
		return processOutput.toString();
	}

	/**
	 * @return the processErrors
	 */
	public String getProcessErrors() {
		return processErrors.toString();
	}

}
