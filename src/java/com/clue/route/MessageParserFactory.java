package com.clue.route;

import java.util.HashMap;

public class MessageParserFactory {

  private HashMap<String, com.google.protobuf.Message> registry = null;
  
  private MessageParserFactory() {
    registry = new HashMap<String, com.google.protobuf.Message>();
    
    register(com.clue.proto.Msg.Header.getDefaultInstance());
    register(com.clue.proto.Msg.ConnectRequest.getDefaultInstance());
    register(com.clue.proto.Msg.ConnectResponse.getDefaultInstance());
    register(com.clue.proto.Msg.GameStateRequest.getDefaultInstance());
    register(com.clue.proto.Msg.GameState.getDefaultInstance());
    register(com.clue.proto.Msg.RevealClues.getDefaultInstance());
    register(com.clue.proto.Msg.SuspectClaimRequest.getDefaultInstance());
    register(com.clue.proto.Msg.SuspectClaimResponse.getDefaultInstance());
    register(com.clue.proto.Msg.PlayerMove.getDefaultInstance());
    register(com.clue.proto.Msg.PlayerSuggestion.getDefaultInstance());
    register(com.clue.proto.Msg.PlayerAccusation.getDefaultInstance());
    register(com.clue.proto.Msg.PlayerAccusationFailed.getDefaultInstance());
    register(com.clue.proto.Msg.DisproveRequest.getDefaultInstance());
    register(com.clue.proto.Msg.DisproveResponse.getDefaultInstance());
    register(com.clue.proto.Msg.GameEnd.getDefaultInstance());
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
