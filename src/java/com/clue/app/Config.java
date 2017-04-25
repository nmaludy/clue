package com.clue.app;

import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.provider.GenericTypeInterface;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.context.environment.Environment;
import org.cfg4j.source.context.environment.ImmutableEnvironment;
import org.cfg4j.source.context.filesprovider.ConfigFilesProvider;
import org.cfg4j.source.files.FilesConfigurationSource;
import org.cfg4j.source.reload.ReloadStrategy;
import org.cfg4j.source.reload.strategy.PeriodicalReloadStrategy;


import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class Config {

  public static final String defaultConfigFile = "./config/application.yaml";
  private static Logger logger = new Logger(Config.class);
  
  private ConfigurationProvider configInstance = null;

  private Config() {
  }

  private static class SingletonHelper {
    private static final Config INSTANCE = new Config();
  }
  
  public static Config getInstance() {
    return SingletonHelper.INSTANCE;
  }

  // Lazy Initialization (If required then only)
	public ConfigurationProvider getConfig(String configFile) {
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
  
	public ConfigurationProvider getConfig() {
    return getConfig(defaultConfigFile);
  }

  public void printProperty(String cfgKey) {
    ConfigurationProvider config = getConfig();
    String cfg_value = config.getProperty(cfgKey, String.class);
    logger.debug("Config property [" + cfgKey+ "] = " + cfg_value);
  }

  public Properties allConfigurationAsProperties() {
    return getConfig().allConfigurationAsProperties();
  }

  public <T> T getProperty(String key, Class<T> type) {
    return getConfig().getProperty(key, type);
  }

  public <T> T getProperty(String key, GenericTypeInterface genericType) {
    return getConfig().getProperty(key, genericType);
  }
  
  public <T> T bind(String prefix, Class<T> type) {
    return getConfig().bind(prefix, type);
  }
}
