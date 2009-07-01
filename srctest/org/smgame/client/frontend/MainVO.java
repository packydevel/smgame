package org.smgame.client.frontend;

import java.io.Serializable;

/**Oggetto mainVO che contiene i messaggi che verranno visualizzati
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class MainVO implements Serializable {

    private MessageType messageType;
    private String message;

    /**Restituisce il messaggio
     *
     * @return messaggio
     */
    public String getMessage() {
        return message;
    }

    /**Restituisce il tipo di messaggiio
     *
     * @return tipo
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**imposta il messaggio
     *
     * @param message messaggio
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**imposta il tipo di messaggio
     *
     * @param messageType tipo
     */
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    /**Azzera le istanze
     *
     */
    public void clear() {
        messageType = null;
        message = null;
    }
}