package com.clue.route;

import java.util.Set;

public class SubscriptionMsgName implements Subscription {
  private Set<String> msgNames;
  
  public SubscriptionMsgName(Set<String> msgNames) {
    this.msgNames = msgNames;
  }

  @Override
  public boolean matches(Message msg) {
    return msgNames.contains(msg.getHeader().getMsgType());
  }
}
