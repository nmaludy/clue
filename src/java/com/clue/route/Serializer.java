package com.clue.route;

import com.clue.proto.Msg.Header;
import com.google.protobuf.Parser;

public class Serializer {

  public Serializer() {
  }
  
  public String convertToBinary(Header header) {
    return new String(header.toByteArray());
  }

  public Header convertToHeader(String data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return Header.parseFrom(data.getBytes());
  }

  public TransportMessage convertToBinary(Message message) {
    Header header = message.getHeader();
    byte[] data = message.getMessage().toByteArray();
    return new TransportMessage(header, data);
  }
  
  public Message convertToMessage(TransportMessage tportMessage)
      throws com.google.protobuf.InvalidProtocolBufferException {
    Header header = tportMessage.getHeader();
    byte[] data = tportMessage.getData();
    
    Parser<? extends com.google.protobuf.Message> parser =
        MessageParserFactory.getInstance().getParser(header.getMsgType());
    com.google.protobuf.Message msg = parser.parseFrom(data);
    return new Message(header, msg);
  }
}
