package com.clue.app;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.context.environment.Environment;
import org.cfg4j.source.context.environment.ImmutableEnvironment;
import org.cfg4j.source.context.filesprovider.ConfigFilesProvider;
import org.cfg4j.source.files.FilesConfigurationSource;
import org.cfg4j.source.reload.ReloadStrategy;
import org.cfg4j.source.reload.strategy.PeriodicalReloadStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that boots up the application
 */
public class Main  {

	private static final long serialVersionUID = 0;

  private static final String defaultConfigFile = "./config/application.yaml";

  private static ConfigurationProvider configInstance = null;

  private static Logger logger = null;
   
	// Lazy Initialization (If required then only)
	public static ConfigurationProvider getConfig(String configFile) {
		if (configInstance == null) {
			// Thread Safe. Might be costly operation in some case
			synchronized (ConfigurationProvider.class) {
				if (configInstance == null) {
          // Specify which files to load. Configuration from both files will be merged.
          ConfigFilesProvider configFilesProvider =
              () -> Arrays.asList(Paths.get(configFile));

          // Use local files as configuration store
          ConfigurationSource source = new FilesConfigurationSource(configFilesProvider);

          // Select path to use, without this all paths are treated relative
          // to the users home directory
          Environment environment = new ImmutableEnvironment(".");

          // Create provider
          configInstance = new ConfigurationProviderBuilder()
              .withConfigurationSource(source)
              .withEnvironment(environment)
              .build();
				}
			}
		}
		return configInstance;
	}
  
	public static ConfigurationProvider getConfig() {
    return getConfig(defaultConfigFile);
  }

  public static Logger getLogger() {
    if (logger == null) {
      synchronized (Logger.class) {
        if (logger == null) {
          logger = LoggerFactory.getLogger(Main.class);
        }
      }
    }
    return logger;
  }

  public static void printConfig(String cfgKey) {
    ConfigurationProvider config = getConfig();
    String cfg_value = config.getProperty(cfgKey, String.class);
    getLogger().debug("Config property [" + cfgKey+ "] = " + cfg_value);
  }
  
  /**
   * Main entry point into the program
   * @param args command line arguments
   */
	public static void main(final String[] args) throws Exception {
    // Initialize the logger
    // To override this on the command line simply do:
    //  java -Dlog4j.configurationFile=/path/to/log4j2.config -jar clue.jar
    System.setProperty("log4j.configurationFile", "./config/log4j2.properties");
    getLogger().debug("Logger initialized");

    // Initialize our config file
    // To override this on the commnad line simply do:
    //  java -Dconfig.file=/path/to/config.yaml -jar clue.jar 
    String configFile = System.getProperty("config.file");
    if (configFile == null) {
      configFile = defaultConfigFile;
    }
    getConfig(configFile);
    getLogger().debug("Config initialized");
    
    getLogger().debug("Hello World");
    for (int i = 0; i < args.length; ++i) {
      getLogger().debug("Command line argument[" + Integer.toString(i) + "] = "
                        + args[i]);
    }

    printConfig("name");
    printConfig("testObj.otherName");
	} // end main()
  
} // end class Main
