package org.smgame.util;

/**Classe eccezione puntata fuori range credito massimo
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
@SuppressWarnings("serial")
public class BetOverflowException extends Exception {

    /**Costruttore
     *
     * @param message messaggio di eccezione
     */
    public BetOverflowException(String message) {
        super(message);
    }
}
