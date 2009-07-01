package org.smgame.server.frontend;

import org.smgame.client.frontend.MessageType;

/**Oggetto serverVo per i messaggi da spedire al client
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class ServerVO {

    private MessageType messageType;
    private String message;

    /**restituisce il messaggio
     *
     * @return msg
     */
    public String getMessage() {
        return message;
    }

    /**restituisce il tipo di messaggio
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

    /**imposta il tipo dei messaggi
     *
     * @param messageType tipo
     */
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    /**Azzera l'istanza*/
    public void clear() {
        messageType = null;
        message = null;
    }
}
