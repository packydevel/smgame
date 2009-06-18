/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.client.frontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

/**
 *
 * @author packyuser
 */
public class HelpJF extends JFrame implements ActionListener, HyperlinkListener {

    JPanel buttonJP;
    JTextPane editorPaneJEP;
    JSplitPane splitPaneJSP;
    GridBagConstraints panelGBC, labelGBC, textFieldGBC, buttonGBC;
    JLabel pathJL, playersNumberJL, cpuflagJL, hostnameJL, portJL, dbnameJL, usernameJL, passwordJL;
    JButton userGuideJB, refGuideJB, javadocJB;

    /**Costruttore
     *
     */
    public HelpJF() {
        super("SMGame JavaDoc");

        setSize(new Dimension(1024, 768));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        userGuideJB = new JButton("Manuale d'uso");
        refGuideJB = new JButton("Documento di Analisi");
        javadocJB = new JButton("SMGame JavaDoc");
        javadocJB.addActionListener(this);


        buttonJP = new JPanel();
        buttonJP.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonJP.add(userGuideJB);
        buttonJP.add(refGuideJB);
        buttonJP.add(javadocJB);
        getContentPane().add(buttonJP, BorderLayout.NORTH);

        splitPaneJSP = new JSplitPane();

        editorPaneJEP = new JTextPane();
        editorPaneJEP.setSize(new Dimension(1024, 740));
        editorPaneJEP.setMargin(new Insets(5, 5, 5, 5));
        editorPaneJEP.setEditable(false);
        editorPaneJEP.setContentType("text/html");
        System.out.println(editorPaneJEP.getContentType());
        editorPaneJEP.addHyperlinkListener(this);

        System.out.println(getClass().getResource("/").getPath());

        try {
            editorPaneJEP.setPage(getClass().getResource("/index.html"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


        getContentPane().add(editorPaneJEP, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            editorPaneJEP.setPage(getClass().getResource("/index.html"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            JTextPane pane = (JTextPane) e.getSource();
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
