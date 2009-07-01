package org.smgame.client.frontend;

import java.io.Serializable;

/**Value objects di newgame, contenente i messaggi che deve memorizzare e restituire
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class NewGameVO implements Serializable {

    private MessageType messageType;
    private String message;

    /**Restituisce il messaggio
     *
     * @return msg
     */
    public String getMessage() {
        return message;
    }

    /**restituisce il tipo di messaggio
     *
     * @return tipo msg
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

    /**azzera l'istanza
     *
     */
    public void clear() {
        messageType = null;
        message = null;
    }
}