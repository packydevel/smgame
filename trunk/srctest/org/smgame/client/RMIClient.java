package org.smgame.client;

import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.smgame.server.IGameMediator;

/**Classe client rmi
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class RMIClient {

    private static IGameMediator stub = null;

    /**Costruttore privato
     * ottiene la rappresentazione in locale dell'oggetto remoto
     *
     * @throws java.lang.Exception
     */
    private RMIClient() throws Exception {
        System.setSecurityManager(new RMISecurityManager());
        Registry registry = LocateRegistry.getRegistry("localhost");
        stub = (IGameMediator) registry.lookup("rmi://localhost/ServerMediator");
    }

    /**restituisce l'oggetto stub/surrogato
     *
     * @return stub
     *
     * @throws java.lang.Exception
     */
    public static IGameMediator getStub() throws Exception {
        if (stub == null) {
            new RMIClient();
        } else {
            try {
                stub.test();
            } catch (Exception e) {
                stub = null;
                throw new Exception();
            }
        }
        return stub;
    }
}