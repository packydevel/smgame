package org.smgame.util;

import javax.swing.JOptionPane;

public class PrintErrors {

    public static void exception(BetOverflowException boe) {        
        see_message(boe.getMessage());
    }
    
    public static void exception(ScoreOverflowException soe) {        
        see_message(soe.getMessage());
    }
    
    private static void see_message(String text) {
        JOptionPane.showMessageDialog(null, text);
    }
}
