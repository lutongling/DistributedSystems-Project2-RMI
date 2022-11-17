package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * This class is the Server controller/app with a main method to take 1 command line argument:
 * 1. port number
 * It will then create a service object reference and rebind it to remote control by giving it a
 * specific name. Then the reference can be accessed by the client.
 */
public class ServerController {
  private static ServerLogger logger = new ServerLogger("ServerController");

  public static void main(String[] args) {
    if (args.length != 1 || !args[0].matches("\\d+")) {
      logger.logErrorMessage("Please enter in valid format: java ServerController <Port Number>");
      System.exit(1);
    }

    // valid CLI args: a port number
    int portNum = Integer.parseInt(args[0]);

    try {
      KeyValue kv = new KeyValueStore();
      LocateRegistry.createRegistry(portNum);
      Naming.rebind("rmi://localhost:" + portNum + "/KeyValueService", kv);

    } catch (RemoteException | MalformedURLException e) {
      logger.logErrorMessage(e.getMessage());
    }

  }
}
