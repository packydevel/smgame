/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.client.frontend;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.io.IOException;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

/**
 *
 * @author packyuser
 */
public class JavaDocJF extends JFrame implements HyperlinkListener {

    JScrollPane monitorJSP;
    GridBagConstraints panelGBC, labelGBC, textFieldGBC, buttonGBC;
    JLabel pathJL, playersNumberJL, cpuflagJL, hostnameJL, portJL, dbnameJL, usernameJL, passwordJL;
    JButton startJB, stopJB, pathJB, testJB;
    JTextField hostnameJTF, portJTF, dbnameJTF, usernameJTF, passwordJTF;
    JTextArea monitorJTA;

    /**Costruttore
     *
     */
    public JavaDocJF() {
        super("SMGame JavaDoc");
        URL helpURL = null;

        setSize(new Dimension(1024, 768));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorPane.addHyperlinkListener(this);
        //URL helpURL = testHTML.class.getResource("/dist/javadoc/index.html");
        try {
            helpURL = new URL(System.getProperty("user.dir") + "/javindex.html");
        } catch (Exception e) {
        }

        System.out.println(getClass().getClassLoader().getResource("index.html"));

        try {
            editorPane.setPage(getClass().getClassLoader().getResource("index.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        add(editorPane);
        setVisible(true);

    }

    public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            JEditorPane pane = (JEditorPane) e.getSource();
            if (e instanceof HTMLFrameHyperlinkEvent) {
                HTMLFrameHyperlinkEvent evt = (HTMLFrameHyperlinkEvent) e;
                HTMLDocument doc = (HTMLDocument) pane.getDocument();
                doc.processHTMLFrameHyperlinkEvent(evt);
            } else {
                try {
                    pane.setPage(e.getURL());
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }
}
