package de.mbrero.see.console.commands;

import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import de.mbrero.see.controllers.GoldStandardController;
import types.GoldStandardType;

/**
 * Triggers the parsing of a gold standard {@link GoldStandardType} so it can be
 * used for the extractor evaluation as reference resource.
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

	public void execute(ConsoleCommand command) throws Exception {

		cmd = command;
		GoldStandardType gsType = GoldStandardType.valueOf(cmd.getParameters().get(TYPE_PARAMETER).toUpperCase());

		validateParameters();
		GoldStandardController ctrl = new GoldStandardController(gsType, inputPath, outputPath);

		ctrl.persistGoldStanstandard();

	}

	@Override
	public void validateParameters() throws FileNotFoundException, IllegalArgumentException {

		inputPath = new File(cmd.getParameters().get(INPUT_PATH_PARAMETER));
		outputPath = new File(cmd.getParameters().get(INPUT_PATH_PARAMETER));
		type = GoldStandardType.valueOf(cmd.getParameters().get(TYPE_PARAMETER).toUpperCase());

		if (type == null)
			throw new IllegalArgumentException("Not a valid goldstandard type");

		if (!inputPath.exists())
			throw new FileNotFoundException("Path for input files does not exist");

		if (!outputPath.exists())
			throw new FileNotFoundException("Path for output files does not exist");

	}

}
