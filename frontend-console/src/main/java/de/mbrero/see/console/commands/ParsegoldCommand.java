package de.mbrero.see.console.commands;

import java.io.File;
import java.io.FileNotFoundException;

import de.mbrero.see.controllers.GoldStandardController;
import types.GoldStandardType;

/**
 * Triggers the parsing of a gold standard {@link GoldStandardType} so it can be used
 * for the extractor evaluation as reference resource.
 * 
 * @author massi.brero@gmail.com
 *
 */
public class ParsegoldCommand implements ICommand {
	
	public final String TYPE_PARAMETER = "type";
	public final String Path_PARAMETER = "path";

	public void execute(ConsoleCommand cmd) throws FileNotFoundException {
		
		GoldStandardType gsType = GoldStandardType.valueOf(cmd.getParameters().get(TYPE_PARAMETER).toUpperCase());
		File path = new File(cmd.getParameters().get(Path_PARAMETER));
		
		GoldStandardController ctrl = new GoldStandardController(gsType, path);
		
		ctrl.persistGoldStanstandard();

	}

}
