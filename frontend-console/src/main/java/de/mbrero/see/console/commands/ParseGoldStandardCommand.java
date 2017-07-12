package de.mbrero.see.console.commands;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.TypeConstraintException;

import de.mbrero.see.controllers.GoldStandardController;
import exceptions.ParameterException;
import exceptions.UnknownCommandException;
import types.GoldStandardType;

public class ParseGoldStandardCommand implements ICommand {
	
	public final String TYPE_PARAMETER = "-type";
	public final String Path_PARAMETER = "path";

	public void execute(ConsoleCommand cmd) throws UnknownCommandException, ParameterException, FileNotFoundException, TypeConstraintException {
		
		GoldStandardType gsType = GoldStandardType.valueOf(cmd.getParameters().get(TYPE_PARAMETER).toUpperCase());
		File path = new File(cmd.getParameters().get(Path_PARAMETER));
		
		GoldStandardController ctrl = new GoldStandardController(gsType, path);
		
		ctrl.persistGoldStanstandard();

	}

}
