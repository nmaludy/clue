package com.clue.route;

public interface Subscription {
  public boolean matches(Message msg);
}
