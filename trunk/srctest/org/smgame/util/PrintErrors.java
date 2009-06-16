package org.smgame.util;

import javax.swing.JOptionPane;

/**Classe visualizza errori/eccezioni
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class PrintErrors {

    /**Eccezione da visualizzare in popup
     *
     * @param message messaggio
     */
    public static void exception(String message) {
        see_message(message);
    }

    /**Visualizza il messaggio d'errore in un popup
     *
     * @param text messaggio
     */
    private static void see_message(String text) {
        JOptionPane.showMessageDialog(null, text);
    }
}