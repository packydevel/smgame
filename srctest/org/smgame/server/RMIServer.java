package org.smgame.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.text.DateFormat;
import java.util.Date;
import org.smgame.client.frontend.MessageType;
import org.smgame.server.frontend.ServerVO;
import org.smgame.util.Logging;

/**Server RMI
 *
 * 
 */
public class RMIServer {

    private static RMIServer server;
    private IGameMediator istub;
    private Stub stub;
    private static ServerVO serverVO = new ServerVO();
    private String rmiRegistryCommand = "";
    private Process rmiregistryProcess;
    private Registry rmiregistry;
    private Runtime runtime;
    private String bindName;
    private static final DateFormat dateFormat = DateFormat.getInstance();

    // Must implement constructor to throw RemoteException:
    private RMIServer() {
        runtime = Runtime.getRuntime();

        if (System.getProperty("os.name").toLowerCase().equals("linux")) {
            rmiRegistryCommand = "rmiregistry";
        } else if (System.getProperty("os.name").toLowerCase().equals("windows xp")) {            
            rmiRegistryCommand = "rmiregistry.exe";
        }
        bindName = "rmi://localhost/ServerMediator";
    }

    public static RMIServer getInstance() {
        if (server == null) {
            server = new RMIServer();
        }

        return server;
    }

    public void start() {
        serverVO.clear();

        try {
            rmiregistryProcess = runtime.exec(rmiRegistryCommand);
            Thread.sleep(1000);

            rmiregistry = LocateRegistry.getRegistry();

            stub = new Stub();

            istub = (IGameMediator) UnicastRemoteObject.exportObject(stub, 0);
            rmiregistry.rebind(bindName, stub);

            serverVO.setMessage(dateFormat.format(new Date()) + "- RMI Server Avviato su localhost");
            serverVO.setMessageType(MessageType.INFO);
            Logging.logInfo(dateFormat.format(new Date()) + "- RMI Server Avviato su localhost");
        } catch (Exception e) {
            serverVO.setMessage("Impossibile avviare il server");
            serverVO.setMessageType(MessageType.ERROR);
            Logging.logExceptionSevere(this.getClass(), e);
        }
    }

    public void stop() {
        if (rmiregistryProcess != null) {
            try {
                UnicastRemoteObject.unexportObject(stub, true);
                rmiregistryProcess.destroy();
                stub = null;
                rmiregistryProcess = null;
                serverVO.setMessage(dateFormat.format(new Date()) + "- RMIServer Interrotto su localhost");
                serverVO.setMessageType(MessageType.INFO);
                Logging.logInfo("RMIServer Interrotto su localhost");
            } catch (Exception e) {
            }
        }
    }

    public ServerVO requestServerVO() {
        return serverVO;
    }
}