package org.smgame.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.text.DateFormat;
import java.util.Date;
import org.smgame.client.frontend.MessageType;
import org.smgame.server.frontend.ServerVO;
import org.smgame.util.Logging;
import org.smgame.util.ResourceLocator;

/**Server RMI
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
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

    /**Costruttore privato
     *
     */
    private RMIServer() {
        // Must implement constructor to throw RemoteException:
        runtime = Runtime.getRuntime();

        if (ResourceLocator.isWindows()) {
            rmiRegistryCommand = "rmiregistry.exe";
        } else {
            rmiRegistryCommand = "rmiregistry";
        }
        bindName = "rmi://localhost/ServerMediator";
    }

    /**restituisce l'istanza del server
     *
     * @return istanza server, se nulla, viene creata
     */
    public static RMIServer getInstance() {
        if (server == null) {
            server = new RMIServer();
        }

        return server;
    }

    /**Lancia il server
     *
     */
    public void start() {
        serverVO.clear();

        try {
            rmiregistryProcess = runtime.exec(rmiRegistryCommand);
            Thread.sleep(3000);

            rmiregistry = LocateRegistry.getRegistry();

            stub = new Stub();

            istub = (IGameMediator) UnicastRemoteObject.exportObject(stub, 0);
            rmiregistry.rebind(bindName, istub);

            serverVO.setMessage(dateFormat.format(new Date()) + "- RMI Server Avviato su localhost");
            serverVO.setMessageType(MessageType.INFO);
            Logging.logInfo(dateFormat.format(new Date()) + "- RMI Server Avviato su localhost");
        } catch (Exception e) {
            serverVO.setMessage("Impossibile avviare il server");
            serverVO.setMessageType(MessageType.ERROR);
            Logging.logExceptionSevere(this.getClass(), e);
        }
    }

    /**Arresta il server
     *
     */
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

    /**Richiede il value objects server
     *
     * @return serverVO
     */
    public ServerVO requestServerVO() {
        return serverVO;
    }
}
