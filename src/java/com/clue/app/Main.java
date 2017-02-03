package com.clue.app;

/**
 * Class that boots up the application
 */
public class Main  {

	private static final long serialVersionUID = 0;

  /**
   * Main entry point into the program
   * @param args command line arguments
   */
	public static void main(final String[] args) throws Exception {
    System.out.println("Hello World");
    for (int i = 0; i < args.length; ++i) {
      System.out.println("Command line argument[" + Integer.toString(i) + "] = "
                         + args[i]);
    }
	} // end main()
  
} // end class Main
