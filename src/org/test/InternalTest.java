/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.test;

/**
 *
 * @author packyuser
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;

public class InternalTest extends JFrame {

    JInternalFrame f;

    public static void main(String[] args) {
        new InternalTest();
    }

    public InternalTest() {
        JDesktopPane dp = new JDesktopPane();
        setContentPane(dp);
        f = new JInternalFrame("Test", true, true, true, true);
        JPanel p = new JPanel();
        JButton close = new JButton("Close Me!");
        close.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    f.setClosed(true);
                } catch (PropertyVetoException x) {
                    System.err.println("Closing exception!");
                }
            }
        });
        p.add(close);
        f.getContentPane().add(p);
        dp.add(f);
        f.setBounds(10, 10, 180, 100);
        f.setVisible(true);
        setSize(400, 400);
//        addWindowListener(new WindowAdapter() {
//
//            public void windowClosing(WindowEvent e) {
//                System.exit(0);
//            }
//        });
        setVisible(true);
    }
}