package com.clue.app;

public class Logger {

  private org.slf4j.Logger logger = null;
  private Class<?> clazz = null;
  
  private Logger() {
  }
  
  public Logger(Class<?> cls) {
    // To override this on the command line simply do:
    //  java -Dlog4j.configurationFile=/path/to/log4j2.config -jar clue.jar
    if (System.getProperty("log4j.configurationFile") == null) {
      System.setProperty("log4j.configurationFile", "./config/log4j2.properties");
    }
    this.clazz  = cls;
    this.logger = getLogger();
  }
  
  public org.slf4j.Logger getLogger() {
    if (logger == null) {
      synchronized (org.slf4j.Logger.class) {
        if (logger == null) {
          logger = org.slf4j.LoggerFactory.getLogger(clazz);
        }
      }
    }
    return logger;
  }

  public void debug(String msg) {
    getLogger().debug(msg);
  }
  
  public void info(String msg) {
    getLogger().info(msg);
  }

  public void warn(String msg) {
    getLogger().warn(msg);
  }

  public void error(String msg) {
    getLogger().error(msg);
  }

  public void trace(String msg) {
    getLogger().trace(msg);
  }
}
