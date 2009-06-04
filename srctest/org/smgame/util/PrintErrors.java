package org.smgame.util;

import javax.swing.JOptionPane;

public class PrintErrors {

    public static void exception(String message) {
        see_message(message);
    }
    
    private static void see_message(String text) {
        JOptionPane.showMessageDialog(null, text);
    }
}
