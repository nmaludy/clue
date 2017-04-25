package com.clue.route;

import com.clue.proto.Msg;
import com.google.protobuf.Parser;

public class Serializer {

  public Serializer() {
  }
  
  public String convertToBinary(Msg.Header header) {
    return new String(header.toByteArray());
  }

  public Msg.Header convertToHeader(String data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return Msg.Header.parseFrom(data.getBytes());
  }

  public TransportMessage convertToBinary(Message message) {
    Msg.Header header = message.getHeader();
    byte[] data = message.getMessage().toByteArray();
    return new TransportMessage(header, data);
  }
  
  public Message convertToMessage(TransportMessage tportMessage)
      throws com.google.protobuf.InvalidProtocolBufferException {
    Msg.Header header = tportMessage.getHeader();
    byte[] data = tportMessage.getData();
    
    Parser<? extends com.google.protobuf.Message> parser =
        MessageParserFactory.getInstance().getParser(header.getMsgType());
    com.google.protobuf.Message msg = parser.parseFrom(data);
    return new Message(header, msg);
  }
}
