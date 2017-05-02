package com.clue.route;

import com.clue.app.Instance;

public class SubscriptionAllIncoming implements Subscription {
  
  public SubscriptionAllIncoming() {
  }

  @Override
  public boolean matches(Message msg) {
    if (msg.getHeader().getDestination() == Instance.getId()) {
      return true;
    }
    if (Instance.isClient() &&
        msg.getHeader().getDestination() == Instance.getBroadcastId()) {
      return true;
    }
    return false;
  }
}
