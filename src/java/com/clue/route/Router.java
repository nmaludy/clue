package com.clue.route;

import com.clue.app.Config;
import com.clue.app.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Router {
  private static Logger logger = new Logger(Router.class);
  private static Config config = Config.getInstance();
  
  private ConcurrentHashMap<Subscription, MessageHandler> subscriptions;
  
  public Router() {
    this.subscriptions = new ConcurrentHashMap<Subscription, MessageHandler>();
  }

  private static class SingletonHelper {
    private static final Router INSTANCE = new Router();
  }
  
  public static Router getInstance() {
    return SingletonHelper.INSTANCE;
  }

  public void register(Subscription sub, MessageHandler handler) {
    subscriptions.put(sub, handler);
    logger.debug("register() - number of subs = " + Integer.toString(subscriptions.size()));
  }

  public void unregister(Subscription sub) {
    subscriptions.remove(sub);
  }

  public void route(Message msg) {
    logger.debug("routing message");
    for (Map.Entry<Subscription, MessageHandler> entry : subscriptions.entrySet()) {
      
      if (entry.getKey().matches(msg)) {
        
        logger.debug("message matched");
        entry.getValue().handleMessage(msg);
      }
    }
  }
}
