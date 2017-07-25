package de.mbrero.see.console.commands;

import java.io.File;
import java.io.FileNotFoundException;

import de.mbrero.see.controllers.GoldStandardController;
import de.mbrero.see.controllers.TestRunController;
import types.Extractors;
import types.GoldStandardType;

/**
 * Triggers the execution of a semantic extractor. The Type supported cn be found in {@link Extractors}.<br>
 * A valid call from the console may look like this<br>
 *  * testrun -type metamap -tester massi@gmail.com -input xxx -output xxx -params [a=b,c=d,e=f] 
 * 
 * The "tester" parameter is mandatory for reproduction reasons. Input folder or file and output file<br>
 * are also mandatory.
 * 
 * The "params" option takes the parameters the user wants to call the extractor with. The syntax is<br>
 * [paramname_1=value_1, paramname_2=value_2, ...]
 * 
 * @author massi.brero@gmail.com
 *
 */
public class TestrunCommand implements ICommand {

	public final String TYPE_PARAMETER = "type";
	public final String EXTRACTOR_PARAMS_PARAMETER = "type";
	public final String INPUT_PATH_PARAMETER = "input";
	public final String OUTPUT_PATH_PARAMETER = "output";
	private ConsoleCommand cmd = new ConsoleCommand();
	File inputPath = null;
	File outputPath = null;
	Extractors type = null;

	public void execute(ConsoleCommand command) throws Exception {

		cmd = command;
		Extractors eType = Extractors.valueOf(cmd.getParameters().get(TYPE_PARAMETER).toUpperCase());

		validateParameters();
		TestRunController ctrl = new TestRunController();

		//ctrl.persistGoldStanstandard();

	}

	@Override
	public void validateParameters() throws FileNotFoundException, IllegalArgumentException {

		inputPath = new File(cmd.getParameters().get(INPUT_PATH_PARAMETER));
		outputPath = new File(cmd.getParameters().get(OUTPUT_PATH_PARAMETER));

		if (type == null)
			throw new IllegalArgumentException("Not a valid extractor type");

		if (!inputPath.exists()) {
			throw new FileNotFoundException("Path for input files does not exist");			
		}
		
		if (!outputPath.exists() || outputPath.isDirectory()) {
			throw new FileNotFoundException("Please specify a file name in an existing directory!");			
		}
		

	}
	
	private void parseExtractorParameters() {
		
	}

}
