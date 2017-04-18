package com.clue.route;

public class Message {

  private com.google.protobuf.Message message = null;
  private com.clue.proto.Msg.Header header = null;

  public Message() {
  }
  
  public Message(com.clue.proto.Msg.Header header,
                 com.google.protobuf.Message message) {
    this.message = message;
    this.header = header;
  }

  public Message(com.clue.proto.Msg.Header header) {
    this(header, null);
  }
  
  public Message(com.google.protobuf.Message message) {
    this(null, message);
  }
  
  public com.clue.proto.Msg.Header getHeader() {
    return header;
  }
  public void setHeader(com.clue.proto.Msg.Header header) {
    this.header = header;
  }

  public com.google.protobuf.Message getMessage() {
    return message;
  }
  public void setMessage(com.google.protobuf.Message message) {
    this.message = message;
  }

}
