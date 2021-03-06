package org.smgame.client;

import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.smgame.server.IStub;

/**Classe client rmi
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class RMIClient {

    private static IStub stub = null;

    /**Costruttore privato
     * ottiene la rappresentazione in locale dell'oggetto remoto
     *
     * @throws java.lang.Exception
     */
    private RMIClient() throws Exception {
        System.setSecurityManager(new RMISecurityManager());
        Registry registry = LocateRegistry.getRegistry("localhost");
        stub = (IStub) registry.lookup("rmi://localhost/ServerMediator");
    }

    /**restituisce l'oggetto stub/surrogato
     *
     * @return stub
     *
     * @throws java.lang.Exception
     */
    public static IStub getStub() throws Exception {
        try {
            if (stub == null) {
                new RMIClient();
            }
            stub.test();
        } catch (Exception e) {
            stub = null;
            throw new Exception();
        }
        
        return stub;
    }
}
