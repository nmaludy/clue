package com.clue.route;

import com.clue.app.Logger;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ClientRegistry {

  private static Logger logger = new Logger(ClientRegistry.class);
  
  private ConcurrentHashMap<Integer, String> idToTransportIdentity;
  private ConcurrentHashMap<String, Integer> transportIdentityToId;

  public ClientRegistry() {
    idToTransportIdentity = new ConcurrentHashMap<Integer, String>();
    transportIdentityToId = new ConcurrentHashMap<String, Integer>();
  }

  private static class SingletonHelper {
    private static final ClientRegistry INSTANCE = new ClientRegistry();
  }
  
  public static ClientRegistry getInstance() {
    return SingletonHelper.INSTANCE;
  }

  public void register(String tportIdentity, int clientId) {
    logger.debug("register tport=" + tportIdentity + " clientId="
                 + Integer.toString(clientId));
    Integer i = new Integer(clientId);
    idToTransportIdentity.put(i, tportIdentity);
    transportIdentityToId.put(tportIdentity, i);
  }
  
  public int getClientId(String tportIdentity) {
    Integer clientId = transportIdentityToId.get(tportIdentity);
    logger.debug("getClientId tport=" + tportIdentity + " clientId="
                 + Integer.toString(clientId));
    return clientId.intValue();
  }

  public String getTransportIdentity(int clientId) {
    Integer i = new Integer(clientId);
    String tportIdentity = idToTransportIdentity.get(i);
    logger.debug("getTransportIdentity tport=" + tportIdentity + " clientId="
                 + Integer.toString(clientId));
    return tportIdentity;
  }

  public String getTransportIdentityInteger(Integer clientId) {
    String tportIdentity = idToTransportIdentity.get(clientId);
    logger.debug("getTransportIdentity tport=" + tportIdentity + " clientId="
                 + clientId.toString());
    return tportIdentity;
  }

  public Set<Integer> allClientIds() {
    return idToTransportIdentity.keySet();
  }
}
