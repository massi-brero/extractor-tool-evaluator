package de.mbrero.see.console;

import de.mbrero.see.console.controllers.MainController;
import de.mbrero.see.models.TestRunModel;
import de.mbrero.see.persistance.dto.TestRun;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        MainController.bootstrap();
        
//        TestRun test = new TestRun();
//        test.setPath("test/test");
//        
//        TestRunModel model = new TestRunModel();
//        model.save(test);
        
    }
}
