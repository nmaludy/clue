package com.clue.route;

import com.clue.proto.Msg;

public class TransportMessage {

  private Msg.Header header = null;
  private byte[] data = null;

  public TransportMessage(Msg.Header header, byte[] data) {
    this.header = header;
    this.data = data;
  }
  
  public Msg.Header getHeader() {
    return header;
  }
  public void setHeader(Msg.Header header) {
    this.header = header;
  }

  public byte[] getData() {
    return data;
  }
  public void setData(byte[] data) {
    this.data = data;
  }
}
