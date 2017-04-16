package com.clue.app;

import com.clue.app.Logger;
import com.clue.app.Config;

/**
 * Class that boots up the application
 */
public class Main  {

	private static final long serialVersionUID = 0;

  private static Logger logger = new Logger(Main.class);
  private static Config config = Config.getInstance();
  
  public static <T> T instantiate(final String className, final Class<T> type){
    try {
      return type.cast(Class.forName(className).newInstance());
    } catch(InstantiationException
            | IllegalAccessException
            | ClassNotFoundException e){
      throw new IllegalStateException(e);
    }
  }
  
  /**
   * Main entry point into the program
   * @param args command line arguments
   */
	public static void main(final String[] args) throws Exception {
    // Initialize the logger
    logger.debug("Logger initialized");

    // Initialize our config file
    // To override this on the commnad line simply do:
    //  java -Dconfig.file=/path/to/config.yaml -jar clue.jar 
    String configFile = System.getProperty("config.file");
    logger.debug("Property config file = " + configFile);
    if (configFile == null) {
      configFile = Config.defaultConfigFile;
    }
    logger.debug("Loading config file = " + configFile);
    config.getConfig(configFile);
    logger.debug("Config initialized");
    
    logger.debug("Hello World");
    for (int i = 0; i < args.length; ++i) {
      logger.debug("Command line argument[" + Integer.toString(i) + "] = "
                        + args[i]);
    }
    
    config.printProperty("main.class");
    String main_classname = config.getProperty("main.class", String.class);
    Runnable main_class = instantiate(main_classname, Runnable.class);
    main_class.run();
	} // end main()
  
} // end class Main
