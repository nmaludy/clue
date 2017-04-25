package com.clue.route;

import com.clue.app.Instance;
import com.clue.app.Logger;

public class SubscriptionAllOutgoing implements Subscription {
  private static Logger logger = new Logger(SubscriptionAllOutgoing.class);

  @Override
  public boolean matches(Message msg) {
    int destination = msg.getHeader().getDestination();
    logger.debug("destination = " + Integer.toString(destination)
                 + "  instance id = " + Integer.toString(Instance.getId()));
    if (Instance.isServer()) {
      return destination != Instance.getId();
    } else {
      // client's should not send broadcast messages
      return destination != Instance.getId() &&
          destination != Instance.getBroadcastId();
    }
  }
}
