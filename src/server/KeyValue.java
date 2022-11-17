package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This Interface represents key-value pair operations applied to the Server.
 * It extends Remote interface to apply RMI.
 */
public interface KeyValue extends Remote {

  /**
   * Insert a Key-Value pair to the storage. Return true if it is successful, otherwise return false
   * NOTICE: Putting in an already-in-the-store key with a new value will override the old value
   * @param key the unique identifier of the key value pair to be inserted
   * @param value the value of the unique identifier key to be inserted
   * @return true if it is successful, otherwise return false.
   * @throws RemoteException
   */
  boolean put(String key, String value) throws RemoteException;

  /**
   * Return the value of the given key, otherwise return null if the given key is not in the store
   * @param key the given key to get
   * @return the value of the given key, otherwise return null if the given key is not in the store
   * @throws RemoteException
   */
  String get(String key) throws RemoteException;

  /**
   * Return true if the given key is in the store, and delete the key-value pair in the store,
   * otherwise return false if the given key cannot be found in the store
   * @param key given key to be deleted
   * @return true if the given key is in the store, and delete the key-value pair in the store,
   * otherwise return false if the given key cannot be found in the store
   * @throws RemoteException
   */
  boolean delete(String key) throws RemoteException;
}
