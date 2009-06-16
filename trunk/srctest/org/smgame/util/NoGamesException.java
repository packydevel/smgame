package org.smgame.util;

/**Classe eccezione non ci sono partite
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class NoGamesException extends Exception {

    /**Costruttore
     *
     * @param message messaggio d'eccezione
     */
    public NoGamesException(String message) {
        super(message);
    }
}