package com.clue.route;

import com.clue.app.Instance;

public class SubscriptionAllIncoming implements Subscription {
  
  public SubscriptionAllIncoming() {
  }

  @Override
  public boolean matches(Message msg) {
    return msg.getHeader().getDestination() == Instance.getId();
  }
}
