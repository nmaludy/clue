package com.clue.route;

import java.util.HashMap;

import com.clue.proto.Msg;

public class MessageParserFactory {

  private HashMap<String, com.google.protobuf.Message> registry = null;
  
  private MessageParserFactory() {
    registry = new HashMap<String, com.google.protobuf.Message>();
    
    register(Msg.Header.getDefaultInstance());
    register(Msg.ConnectRequest.getDefaultInstance());
    register(Msg.ConnectResponse.getDefaultInstance());
    register(Msg.StartGameRequest.getDefaultInstance());
    register(Msg.GameStateRequest.getDefaultInstance());
    register(Msg.GameState.getDefaultInstance());
    register(Msg.RevealClues.getDefaultInstance());
    register(Msg.SuspectClaimRequest.getDefaultInstance());
    register(Msg.SuspectClaimResponse.getDefaultInstance());
    register(Msg.PlayerMove.getDefaultInstance());
    register(Msg.InvalidMove.getDefaultInstance());
    register(Msg.PlayerSuggestion.getDefaultInstance());
    register(Msg.PlayerAccusation.getDefaultInstance());
    register(Msg.PlayerAccusationFailed.getDefaultInstance());
    register(Msg.DisproveRequest.getDefaultInstance());
    register(Msg.DisproveResponse.getDefaultInstance());
    register(Msg.GameEnd.getDefaultInstance());
  }

  private static class SingletonHelper {
    private static final MessageParserFactory INSTANCE = new MessageParserFactory();
  }
  
  public static MessageParserFactory getInstance() {
    return SingletonHelper.INSTANCE;
  }

  private void register(com.google.protobuf.Message message) {
    registry.put(message.getDescriptorForType().getFullName(), message);
  }

  public com.google.protobuf.Parser<? extends com.google.protobuf.Message>
  getParser(String typeName) {
    if (registry.containsKey(typeName)) {
      return registry.get(typeName).getParserForType();
    } else {
      throw new java.util.NoSuchElementException(
          "No message registered with name: " + typeName);
    }
  }
}
