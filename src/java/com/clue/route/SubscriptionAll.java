package com.clue.route;

public class SubscriptionAll implements Subscription {

  public SubscriptionAll() {
  }

  @Override
  public boolean matches(Message msg) {
    return true;
  }
}
