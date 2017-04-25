package com.clue.route;

import com.clue.app.Instance;
import com.clue.app.Logger;

public class SubscriptionAllOutgoing implements Subscription {
  private static Logger logger = new Logger(SubscriptionAllOutgoing.class);

  @Override
  public boolean matches(Message msg) {

    logger.debug("destination = " + Integer.toString(msg.getHeader().getDestination())
                 + "  instance id = " + Integer.toString(Instance.getId()));
    return msg.getHeader().getDestination() != Instance.getId();
  }
}
