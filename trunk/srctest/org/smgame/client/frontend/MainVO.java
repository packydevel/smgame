/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.smgame.client.frontend;

/**
 *
 * @author packyuser
 */
public class MainVO {
    MessageType messageType;
    String message;

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
}