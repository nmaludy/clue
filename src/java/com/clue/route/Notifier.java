package com.clue.route;

import com.clue.app.Logger;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Poller;
import org.zeromq.ZMQ.Socket;

import java.util.Random;

public class Notifier {
  
  private static Logger logger = new Logger(Notifier.class);
  
  private Context context = null;
  private TransportMessageHandler callback = null;
  private String connectStr = null;

  private Socket socketRcvr = null;
  private Socket socketSndr = null;
  
  public Notifier(Context context,
                  TransportMessageHandler callback) {
    this.context = context;
    this.callback = callback;

    // use random int for unique address
    Random rand = new Random(System.currentTimeMillis());
    String hex_id = Integer.toHexString(rand.nextInt());
    connectStr = "inproc://zmqsub" + hex_id;

    socketRcvr = context.socket(ZMQ.PAIR);
    socketRcvr.setSndHWM(0); // no limit
    socketRcvr.setRcvHWM(0); // no limit
    socketRcvr.bind(connectStr);
  }
  
  public void sndNotify() {
    synchronized (this) {
      if (socketSndr == null) {
        init();
      }
      socketSndr.send("");
    }
  }

  public void init() {
    if (socketSndr != null) {
      logger.error("Notifier::init() called more than once");
      return;
    }

    // create sender & connect to recvr
    socketSndr = context.socket(ZMQ.PAIR);
    socketSndr.connect(connectStr);
    socketSndr.setSndHWM(0); // no limit
    socketSndr.setRcvHWM(0); // no limit
  }
  
  public void clearRecv() {
    synchronized (this) {
      socketRcvr.recv();
    }
  }

  public TransportMessageHandler getCallback() {
    return callback;
  }

  // main thread polls on this socket
  public Socket rcvrSocket()
  {
    return socketRcvr;
  }
}
