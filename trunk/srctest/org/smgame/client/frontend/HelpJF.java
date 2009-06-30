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

    private JPanel buttonJP,  contentJP;
    private JTextPane editorPaneJEP;
    private JSplitPane splitPaneJSP;
    private GridBagConstraints panelGBC,  labelGBC,  textFieldGBC,  buttonGBC;
    private JLabel pathJL,  playersNumberJL,  cpuflagJL,  hostnameJL,  portJL,  dbnameJL,  usernameJL,  passwordJL;
    private JButton userGuideJB,  refGuideJB,  javadocJB;
    private String userGuidePDF,  refGuidePDF,  javadocIndex;

    /**Costruttore
     *
     */
    public HelpJF() {
        super("Documentazione SMGame");

        setSize(new Dimension(1024, 768));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        userGuideJB = new JButton("Manuale d'uso");
        userGuideJB.addActionListener(this);

        refGuideJB = new JButton("Documento di Analisi");
        refGuideJB.addActionListener(this);

        javadocJB = new JButton("SMGame JavaDoc");
        javadocJB.addActionListener(this);


        buttonJP = new JPanel();
        buttonJP.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonJP.add(userGuideJB);
        buttonJP.add(refGuideJB);
        buttonJP.add(javadocJB);
        getContentPane().add(buttonJP, BorderLayout.NORTH);

        contentJP = new JPanel();
        contentJP.setLayout(new BorderLayout());
        getContentPane().add(contentJP, BorderLayout.CENTER);

        splitPaneJSP = new JSplitPane();

        editorPaneJEP = new JTextPane();
        editorPaneJEP.setSize(new Dimension(1024, 740));
        editorPaneJEP.setMargin(new Insets(5, 5, 5, 5));
        editorPaneJEP.setEditable(false);
        editorPaneJEP.setContentType("text/html");
        editorPaneJEP.addHyperlinkListener(this);
        contentJP.add(editorPaneJEP, BorderLayout.CENTER);

        System.out.println(getClass().getResource("/").getPath());

        userGuidePDF = "/home/packyuser/Scrivania/Analisys.pdf";
        refGuidePDF = "/home/packyuser/Scrivania/Analisys.pdf";
        javadocIndex = getClass().getResource("/index.html").getPath();
        showPDF(userGuidePDF);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        JButton button = (JButton) ae.getSource();
        if (button.equals(userGuideJB)) {
            showPDF(userGuidePDF);
        } else if (button.equals(refGuideJB)) {
            showPDF(refGuidePDF);
        } else {
            showHTML(javadocIndex);

        }
    }

    private void showPDF(String file) {
        try {
            getContentPane().remove(contentJP);
            contentJP = new PDFViewerJP(file);
            getContentPane().add(contentJP, BorderLayout.CENTER);

        } catch (Exception e) {
            e.printStackTrace();
        }
        validate();
    }

    private void showHTML(String file) {
        getContentPane().remove(contentJP);
        contentJP = new JPanel();
        try {
            editorPaneJEP.setPage("file://" + file);
            contentJP.add(editorPaneJEP, BorderLayout.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getContentPane().add(contentJP, BorderLayout.CENTER);
        validate();
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
