/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.server;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Date;
import java.util.Set;

//import org.monopoly.client.Game;

/**
 *
 * @author Administrator
 */
public class RequestExecutor {
/*
    private final String vFileString = "games.dat";
    private HashMap<Long, Game> gameList;

    public long getNewGameID() {
        return (new Date()).getTime();
    }
    
    public Set<Long> getGamesList() throws Exception {
        try {
            loadFile();
//            return gameList.keySet();
            
        } catch (Exception e) {
            throw new Exception("Impossibile recuperare le partite salvate!!!");
        }
    }

    private void loadFile() throws Exception {
        FileInputStream fis = new FileInputStream(vFileString);
        ObjectInputStream ois = new ObjectInputStream(fis);
//        gameList = (HashMap<Long, Game>) ois.readObject();
        fis.close();
        ois.close();
    }

    public Game loadGame(long pGameID) throws Exception {
        Exception ex = null;

        try {
            loadFile();
            if (gameList.containsKey(new Long(pGameID))) {
                return gameList.get(new Long(pGameID));
            } else {
                ex = new Exception("Partita inesistente!!!");
                throw ex;
            }
        } catch (Exception e) {
            if (ex != null) {
                throw ex;
            } else {
                throw new Exception("Impossibile caricare la partita!!!");
            }
        }
    }

    public void saveGame(Game pGame) throws Exception {
        long gameID = pGame.getGameID();

        try {
            loadFile();
            gameList.put(new Long(gameID), pGame);
            saveFile();
        } catch (Exception e) {
            throw new Exception("Impossibile salvare la partita!!!");
        }
    }

    private void saveFile() throws Exception {

        FileOutputStream fos = new FileOutputStream(vFileString);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(gameList);
        fos.close();
        oos.close();
    }
 */
}
