package de.mbrero.see.console.commands;

import java.io.File;
import java.io.FileNotFoundException;

import de.mbrero.see.api.GoldStandardController;
import types.GoldStandardType;

/**
 * Triggers the parsing of a gold standard {@link GoldStandardType} so it can be
 * used for the extractor evaluation as reference resource.<br>
 * 
 * The folder containing the texts should be named after the ontology/ontologies used.
 	*@see Ontology.
 * 
 * Example:<br>
 * parsegold -type craft -input {path to goldstandard}/ncbi -output {path to chosen result folder}/qrel
 * 
 * @author massi.brero@gmail.com
 *
 */
public class ParsegoldCommand implements ICommand {

	public final String TYPE_PARAMETER = "type";
	public final String INPUT_PATH_PARAMETER = "input";
	public final String OUTPUT_PATH_PARAMETER = "output";
	private ConsoleCommand cmd = new ConsoleCommand();
	File inputPath = null;
	File outputPath = null;
	GoldStandardType type = null;

	/*
	 * @see de.mbrero.see.console.commands.ICommand#execute(de.mbrero.see.console.commands.ConsoleCommand)
	 */
	public void execute(ConsoleCommand command) throws Exception {

		cmd = command;
		type = GoldStandardType.valueOf(cmd.getParameters().get(TYPE_PARAMETER).toUpperCase());

		validateParameters();
		GoldStandardController ctrl = new GoldStandardController(type, inputPath, outputPath);

		ctrl.persistGoldStanstandard();

	}

	/*
	 * @see de.mbrero.see.console.commands.ICommand#validateParameters()
	 */
	@Override
	public void validateParameters() throws FileNotFoundException, IllegalArgumentException {

		inputPath = new File(cmd.getParameters().get(INPUT_PATH_PARAMETER));
		outputPath = new File(cmd.getParameters().get(OUTPUT_PATH_PARAMETER));

		if (type == null)
			throw new IllegalArgumentException("Not a valid goldstandard type");

		if (!inputPath.exists()) {
			throw new FileNotFoundException("Path for input files does not exist");			
		}
		
		if (outputPath.isDirectory()) {
			throw new FileNotFoundException("Please specify a file name in an existing directory!");			
		}
		

	}

}
