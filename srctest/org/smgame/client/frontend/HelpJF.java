package org.smgame.client.frontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import org.smgame.util.ResourceLocator;

/**frame help/documentazione
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class HelpJF extends JFrame implements ActionListener, HyperlinkListener {

    private JPanel buttonJP, contentJP;
    private JTextPane editorPaneJEP;
    private JSplitPane splitPaneJSP;
    private JButton userGuideJB, refGuideJB, javadocJB;
    private String userGuidePDF, refGuidePDF, javadocIndex;

    /**Costruttore
     *
     */
    public HelpJF(String doc) {
        super("Documentazione SMGame");

        setSize(new Dimension(1024, 768));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        userGuideJB = new JButton("Manuale d'uso");
        userGuideJB.addActionListener(this);

        refGuideJB = new JButton("Documento di Analisi");
        refGuideJB.addActionListener(this);

        javadocJB = new JButton("SMGame JavaDoc");
        javadocJB.addActionListener(this);


        buttonJP = new JPanel();
        buttonJP.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonJP.setPreferredSize(new Dimension(1020,30));
        buttonJP.add(userGuideJB);
        buttonJP.add(refGuideJB);
        buttonJP.add(javadocJB);
        getContentPane().add(buttonJP, BorderLayout.NORTH);

        contentJP = new JPanel();
        contentJP.setLayout(new BorderLayout());
        getContentPane().add(contentJP, BorderLayout.CENTER);

        splitPaneJSP = new JSplitPane();

        editorPaneJEP = new JTextPane();
        editorPaneJEP.setPreferredSize(new Dimension(1020,690));
        editorPaneJEP.setMargin(new Insets(2, 2, 2, 2));
        editorPaneJEP.setEditable(false);
        editorPaneJEP.setContentType("text/html");
        editorPaneJEP.addHyperlinkListener(this);
        contentJP.add(editorPaneJEP, BorderLayout.CENTER);

        userGuidePDF = ResourceLocator.getWorkspace() + "doc/Analisys.pdf";
        refGuidePDF = ResourceLocator.getWorkspace() + "doc/Analisys.pdf";
        javadocIndex = ResourceLocator.getWorkspace() + "javadoc/index.html";

        if (doc.equals("UserGuide")) {
            showPDF(userGuidePDF);
        } else if (doc.equals("UserGuide")) {
            showPDF(refGuidePDF);
        } else {
            showHTML(javadocIndex);
        }
    }

    @Override
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

    /**visualizza pdf
     *
     * @param file nome file
     */
    private void showPDF(String file) {
        try {
            getContentPane().remove(contentJP);
            contentJP = new PDFViewerJP(file);
            getContentPane().add(contentJP, BorderLayout.CENTER);

        } catch (Exception e) {
        }
        validate();
    }

    /**visualizza html
     *
     * @param file nomefile
     */
    private void showHTML(String file) {
        getContentPane().remove(contentJP);
        contentJP = new JPanel();
        try {
            System.out.println(file);
            editorPaneJEP.setPage(file);
            contentJP.add(editorPaneJEP, BorderLayout.CENTER);
        } catch (Exception e) {
        }
        getContentPane().add(contentJP, BorderLayout.CENTER);
        validate();
    }

    @Override
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
