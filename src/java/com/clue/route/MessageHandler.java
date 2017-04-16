package com.clue.route;

public interface MessageHandler {
  public void receiveMessage(TransportMessage tportMsg);

  public void sendMessage(Message msg);
}
