package de.mbrero.see;

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
        System.out.println( "Hello World!" );
        
        TestRun test = new TestRun();
        test.setPath("test/test");
        
        TestRunModel model = new TestRunModel();
        model.save(test);
        
    }
}
