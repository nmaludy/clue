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
    checkHeader();
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
    checkHeader();
  }

  public com.google.protobuf.Message getMessage() {
    return message;
  }
  public void setMessage(com.google.protobuf.Message message) {
    this.message = message;
    checkHeader();
  }

  public void checkHeader() {
    if (header != null && message != null) {
      String type_msg = message.getDescriptorForType().getFullName();
      String type_hdr = header.getMsgType();
      if (!type_msg.equals(type_hdr)) {
        throw new java.lang.IllegalArgumentException(
            "Message created with a header that doesn't match its message type!\n"
            + "type name of message = " + type_msg + "\n"
            + "type name in header  = " + type_hdr + "\n"
            + "header = " + header.toString() + "\n"
            + "message = " + message.toString());
      }
    }
  }
}
