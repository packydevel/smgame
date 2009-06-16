package org.smgame.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.sql.SQLException;

import org.smgame.backend.DBAccess;
import org.smgame.client.frontend.MessageType;
import org.smgame.server.frontend.ServerVO;

/**Server RMI
 *
 * @author Pc City
 */
public class RMIServer {

    private static RMIServer server;
    private static ServerVO serverVO = new ServerVO();
    private String rmiRegistryCommand = "";
    private Process rmiregistryProcess;
    private Registry rmiregistry;
    private Runtime runtime;
    private String bindName;

    // Must implement constructor to throw RemoteException:
    private RMIServer() {
        runtime = Runtime.getRuntime();

        if (System.getProperty("os.name").toLowerCase().equals("linux")) {
            rmiRegistryCommand = "rmiregistry";
        } else if (System.getProperty("os.name").toLowerCase().equals("windows xp")) {
            rmiRegistryCommand = "javaw rmiregistry";
        }

        bindName = "//localhost/ServerMediator";
    }

    public static RMIServer getInstance() {
        if (server == null) {
            server = new RMIServer();
        }

        return server;
    }

    public void start() {
        IGameMediator stub;
        serverVO.clear();

        try {
            rmiregistryProcess = runtime.exec(rmiRegistryCommand);
            Thread.sleep(5000);

            rmiregistry = LocateRegistry.getRegistry();
            stub = (IGameMediator) UnicastRemoteObject.exportObject(new Stub(), 0);//, 2005);
            rmiregistry.rebind(bindName, stub);
            System.out.println("Ready to do time");

            if (DBAccess.testConnection()) {
                try {
                    DBAccess.closeConnection();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        } catch (Exception e) {
            serverVO.setMessage("Impossibile avviare il server");
            serverVO.setMessageType(MessageType.ERROR);
        }
    }

    public void stop() {
        if (rmiregistryProcess != null) {
            try {
                rmiregistry.unbind(bindName);
                rmiregistryProcess.destroy();
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ServerVO requestServerVO() {
        return serverVO;
    }
}