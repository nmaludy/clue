package com.clue.route;

import com.clue.proto.Msg.Header;

public class TransportMessage {

  private Header header = null;
  private byte[] data = null;

  public TransportMessage(Header header, byte[] data) {
    this.header = header;
    this.data = data;
  }
  
  public Header getHeader() {
    return header;
  }
  public void setHeader(Header header) {
    this.header = header;
  }

  public byte[] getData() {
    return data;
  }
  public void setData(byte[] data) {
    this.data = data;
  }
}
