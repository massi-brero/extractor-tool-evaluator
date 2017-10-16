package de.mbrero.see.console;

import java.io.IOException;
import java.util.Map;

import de.mbrero.see.console.controllers.MainController;
import de.mbrero.see.persistance.DBConnection;

/**
 * Starts the application.
 *
 */
public class App {
	
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	
    public static void main( String[] args )
    {
    	App.printHeader();
    	bootstrap();
    }
    
    
    private static void printHeader() {
    	try {
			Process p = Runtime.getRuntime().exec("clear");
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	System.out.println("\n");
    	
    	System.out.println(App.ANSI_GREEN);
    	System.out.println(" _____ _____ _____    _____             _                 _           ");
    	System.out.println("|   __|   __|   __|  | __  |___ ___ ___| |_ _____ ___ ___| |_ ___ ___ ");
    	System.out.println("|__   |   __|   __|  | __ -| -_|   |  _|   |     | .'|  _| '_| -_|  _|");
    	System.out.println("|_____|_____|_____|  |_____|___|_|_|___|_|_|_|_|_|__,|_| |_,_|___|_|  ");
    	System.out.println(App.ANSI_RESET);
    	
    }
    
    public static void shutdown()
    {
    	System.out.println("Closing all connections.");
    	DBConnection.closeDBConnection();
    	System.out.println("\nBye...");
    }
    
	public static void bootstrap() {
		Thread consoleThread = new Thread(new MainController());
		consoleThread.start();

	}
}
