package com.clue.app;

public class Instance {
  private static Config config = Config.getInstance();
  
  public enum Type {
    CLIENT,
    SERVER
  }

  public static int getId() {
    return config.getProperty("instance.id", Integer.class).intValue();
  }

  public static int getServerId() {
    return config.getProperty("server.id", Integer.class).intValue();
  }
  
  public static int getBroadcastId() {
    return config.getProperty("instance.broadcast_id", Integer.class).intValue();
  }

  public static Type getType() {
    String instance_type = config.getProperty("instance.type", String.class);
    Type type = Type.CLIENT;
    if (instance_type.toLowerCase().equals("client")) {
      type = Type.CLIENT;
    } else if (instance_type.toLowerCase().equals("server")) {
      type = Type.SERVER;
    }
    return type;
  }
  
  public static boolean isServer() {
    return getType() == Type.SERVER;
  }
  
  public static boolean isClient() {
    return getType() == Type.CLIENT;
  }
}
