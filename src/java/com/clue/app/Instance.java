package com.clue.app;

public class Instance {
  private static Config config = Config.getInstance();

  public static int getId() {
    return config.getProperty("instance.id", Integer.class).intValue();
  }

  public static int getServerId() {
    return config.getProperty("server.id", Integer.class).intValue();
  }
}
