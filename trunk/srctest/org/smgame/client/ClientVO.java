/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.client;

import org.smgame.client.frontend.MessageType;

/**
 *
 * @author packyuser
 */
public class ClientVO {

    private MessageType messageType;
    private String message;

    public String getMessage() {
        return message;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public void clear() {
        messageType = null;
        message = null;
    }
}
