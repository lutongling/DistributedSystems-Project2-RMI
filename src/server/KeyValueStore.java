package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a key-value storage of memory. It implements KeyValue interface.
 * It extends UnicastRemoteObject to apply RMI.
 * It is set up with using a Hashmap to store key value pairs.
 */
public class KeyValueStore extends UnicastRemoteObject implements KeyValue {
  private Map<String, String> dictionary;
  private ServerLogger logger;

  /**
   * Must explicit throw the RemoteException in the constructor.
   * The reference of the object is used to connect Server and Client.
   * @throws RemoteException
   */
  public KeyValueStore() throws RemoteException {
    super();
    dictionary = new HashMap<>();
    logger = new ServerLogger("ServerController");
  }

  @Override
  public boolean put(String key, String value) {
    // Since put operations modify the key value store,
    // in order to handle mutual exclusion, we applied synchronization here.
    synchronized (this) {
      try {
        dictionary.put(key, value);
        logger.logInfoMessage("REQUEST - PUT; KEY => " + key + "; VALUE => " + value);
        logger.logInfoMessage("Response => code: 200; "
                + "message: Put operation successful");

        // normally it will always be expected to be true -- put operation successful
        return true;
      } catch (Exception e) {
        logger.logErrorMessage("Server crashed. " + e.getMessage());
        return false;
      }
    }
  }

  @Override
  public String get(String key) {
    logger.logInfoMessage("REQUEST - GET; KEY => " + key);

    if(!dictionary.containsKey(key)) {
      logger.logWarningMessage("Response => code: 404; "
              + "message: key not found");
      return null;
    }

    String val = dictionary.get(key);
    logger.logInfoMessage("Response => code: 200; "
            + "message: " + val);

    return val;
  }

  @Override
  public boolean delete(String key) {
    // Since delete operations may modify the key value store,
    // in order to handle mutual exclusion, we applied synchronization here.
    synchronized (this) {
      logger.logInfoMessage("REQUEST - DELETE; KEY => " + key);

      if(!dictionary.containsKey(key)) {
        logger.logWarningMessage("Response => code: 404; "
                + "message: key not found");
        return false;
      }

      logger.logInfoMessage("Response => code: 200; "
              + "message: Delete operation successful");

      dictionary.remove(key);
      return true;
    }

  }
}
