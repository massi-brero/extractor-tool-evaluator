package de.mbrero.see.console;

import de.mbrero.see.console.controllers.MainController;
import de.mbrero.see.persistance.DBConnection;

/**
 * Starts the application.
 *
 */
public class App {
    public static void main( String[] args )
    {
    	App.printHeader();
        MainController.bootstrap();
    }
    
    
    private static void printHeader() {
    	System.out.println("\n");
    	
    	System.out.println("                                                                      ");
    	System.out.println(" _____ _____ _____    _____             _                 _           ");
    	System.out.println("|   __|   __|   __|  | __  |___ ___ ___| |_ _____ ___ ___| |_ ___ ___ ");
    	System.out.println("|__   |   __|   __|  | __ -| -_|   |  _|   |     | .'|  _| '_| -_|  _|");
    	System.out.println("|_____|_____|_____|  |_____|___|_|_|___|_|_|_|_|_|__,|_| |_,_|___|_|  ");
    	System.out.println("                                                                      ");
    	
    }
    
    public static void shutdown()
    {
    	System.out.println("Closing all connections.");
    	DBConnection.closeDBConnection();
    	System.out.println("\nBye...");
    }
}
