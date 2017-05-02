package com.clue.route;

public interface MessageHandler {
  public void handleMessage(Message msg);
  
  default boolean shouldCallHandleOnGuiThread() {
    return false;
  }
}
