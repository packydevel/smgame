package org.smgame.client.frontend;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;

/**Pannello per visualizzare i pdf
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
@SuppressWarnings("serial")
public class PDFViewerJP extends JPanel {

    /**the actual JPanel/decoder object*/
    private PdfDecoder pdfDecoder;
    /**name of current PDF file*/
    private String currentFile = null;
    /**current page number (first page is 1)*/
    private int currentPage = 1;
    private final JLabel pageCounter1 = new JLabel("Pagina ");
    private JTextField pageCounter2 = new JTextField(4);//000 used to set prefered size
    private JLabel pageCounter3 = new JLabel("di");//000 used to set prefered size

    /**Costruttore
     *
     * @param name nome del file da aprire
     */
    public PDFViewerJP(String name) {
        pdfDecoder = new PdfDecoder(true);
        //ensure non-embedded font map to sensible replacements
        PdfDecoder.setFontReplacements(pdfDecoder);
        currentFile = name;//store file name for use in page changer
        try {
            //this opens the PDF and reads its internal details
            pdfDecoder.openPdfFile(new URI(currentFile).getPath());

            //these 2 lines opens page 1 at 100% scaling
            pdfDecoder.decodePage(currentPage);
            pdfDecoder.setPageParameters(1, 1); //values scaling (1=100%). page number
        } catch (Exception e) {
        }
        //setup our GUI display
        initializeViewer();

        //set page number display
        pageCounter2.setText(String.valueOf(currentPage));
        pageCounter3.setText("di " + pdfDecoder.getPageCount());
    }

    /**Verifica se il criptaggio è presente ed un'eventuale passsword
     *
     * @return true = pdf accessibile, false = password o criptazione presente
     */
    @SuppressWarnings("unused")
    private boolean checkEncryption() {
//    check if file is encrypted
        if (pdfDecoder.isEncrypted()) {
            //if file has a null password it will have been decoded and isFileViewable will return true
            while (!pdfDecoder.isFileViewable()) {
                /** popup window if password needed */
                String password = JOptionPane.showInputDialog(this, "Please enter password");
                /** try and reopen with new password */
                if (password != null) {
                    try {
                        pdfDecoder.setEncryptionPassword(password);
                    } catch (PdfException e) {
                    }
                    //pdfDecoder.verifyAccess();
                }
            }
            return true;
        }
        //if not encrypted return true
        return true;
    }

    /**Inizializza la visione e i suoi componenti
     * 
     */
    private void initializeViewer() {
        setLayout(new BorderLayout());

        Component[] itemsToAdd = initChangerPanel();//setup page display and changer

        JPanel topBar = new JPanel();
        topBar.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));

        for (int i = 0; i < itemsToAdd.length; i++) {
            topBar.add(itemsToAdd[i]);
        }

        add(topBar, BorderLayout.NORTH);

        JScrollPane display = initPDFDisplay();//setup scrollpane with pdf display inside
        add(display, BorderLayout.CENTER);
    }

    /**
     * returns the scrollpane with pdfDecoder set as the viewport
     */
    private JScrollPane initPDFDisplay() {
        JScrollPane currentScroll = new JScrollPane();
        currentScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        currentScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        currentScroll.getVerticalScrollBar().setUnitIncrement(20);
        currentScroll.setViewportView(pdfDecoder);

        return currentScroll;
    }

    /**Inizializza le componenti e le restituisce
     *
     * @return array di componenti
     */
    private Component[] initChangerPanel() {
        Component[] list = new Component[11];

        /**back to page 1*/
        JButton start = new JButton();
        start.setBorderPainted(false);
        URL startImage = getClass().getResource("/org/jpedal/examples/simpleviewer/res/start.gif");
        start.setIcon(new ImageIcon(startImage));
        start.setToolTipText("Rewind to page 1");
//    currentBar1.add(start);
        list[0] = start;
        start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentFile != null && currentPage != 1) {
                    currentPage = 1;
                    try {
                        pdfDecoder.decodePage(currentPage);
                        pdfDecoder.invalidate();
                        repaint();
                    } catch (Exception e1) {
                    }
                    //set page number display
                    pageCounter2.setText(String.valueOf(currentPage));
                }
            }
        });

        /**back 10 icon*/
        JButton fback = new JButton();
        fback.setBorderPainted(false);
        URL fbackImage = getClass().getResource("/org/jpedal/examples/simpleviewer/res/fback.gif");
        fback.setIcon(new ImageIcon(fbackImage));
        fback.setToolTipText("Rewind 10 pages");
//    currentBar1.add(fback);
        list[1] = fback;
        fback.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentFile != null && currentPage > 10) {
                    currentPage -= 10;
                    try {
                        pdfDecoder.decodePage(currentPage);
                        pdfDecoder.invalidate();
                        repaint();
                    } catch (Exception e1) {
                    }

//            set page number display
                    pageCounter2.setText(String.valueOf(currentPage));
                }
            }
        });

        /**back icon*/
        JButton back = new JButton();
        back.setBorderPainted(false);
        URL backImage = getClass().getResource("/org/jpedal/examples/simpleviewer/res/back.gif");
        back.setIcon(new ImageIcon(backImage));
        back.setToolTipText("Rewind one page");
//    currentBar1.add(back);
        list[2] = back;
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentFile != null && currentPage > 1) {
                    currentPage -= 1;
                    try {
                        pdfDecoder.decodePage(currentPage);
                        pdfDecoder.invalidate();
                        repaint();
                    } catch (Exception e1) {
                    }

//          set page number display
                    pageCounter2.setText(String.valueOf(currentPage));
                }
            }
        });

        pageCounter2.setEditable(true);
        pageCounter2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent a) {

                String value = pageCounter2.getText().trim();
                int newPage;

                //allow for bum values
                try {
                    newPage = Integer.parseInt(value);

                    if ((newPage > pdfDecoder.getPageCount()) | (newPage < 1)) {
                        return;
                    }

                    currentPage = newPage;
                    try {
                        pdfDecoder.decodePage(currentPage);
                        pdfDecoder.invalidate();
                        repaint();
                    } catch (Exception e) {
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, '>' + value + "< is Not a valid Value.\nPlease enter a number between 1 and " + pdfDecoder.getPageCount());
                }

            }
        });

        /**put page count in middle of forward and back*/
//    currentBar1.add(pageCounter1);
//    currentBar1.add(new JPanel());//add gap
//    currentBar1.add(pageCounter2);
//    currentBar1.add(new JPanel());//add gap
//    currentBar1.add(pageCounter3);
        list[3] = pageCounter1;
        list[4] = new JPanel();
        list[5] = pageCounter2;
        list[6] = new JPanel();
        list[7] = pageCounter3;

        /**forward icon*/
        JButton forward = new JButton();
        forward.setBorderPainted(false);
        URL fowardImage = getClass().getResource("/org/jpedal/examples/simpleviewer/res/forward.gif");
        forward.setIcon(new ImageIcon(fowardImage));
        forward.setToolTipText("forward 1 page");
//    currentBar1.add(forward);
        list[8] = forward;
        forward.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentFile != null && currentPage < pdfDecoder.getPageCount()) {
                    currentPage += 1;
                    try {
                        pdfDecoder.decodePage(currentPage);
                        pdfDecoder.invalidate();
                        repaint();
                    } catch (Exception e1) {
                    }

//        set page number display
                    pageCounter2.setText(String.valueOf(currentPage));
                }
            }
        });

        /**fast forward icon*/
        JButton fforward = new JButton();
        fforward.setBorderPainted(false);
        URL ffowardImage = getClass().getResource("/org/jpedal/examples/simpleviewer/res/fforward.gif");
        fforward.setIcon(new ImageIcon(ffowardImage));
        fforward.setToolTipText("Fast forward 10 pages");
//    currentBar1.add(fforward);
        list[9] = fforward;
        fforward.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentFile != null && currentPage < pdfDecoder.getPageCount() - 9) {
                    currentPage += 10;
                    try {
                        pdfDecoder.decodePage(currentPage);
                        pdfDecoder.invalidate();
                        repaint();
                    } catch (Exception e1) {
                    }

//        set page number display
                    pageCounter2.setText(String.valueOf(currentPage));
                }
            }
        });

        /**goto last page*/
        JButton end = new JButton();
        end.setBorderPainted(false);
        URL endImage = getClass().getResource("/org/jpedal/examples/simpleviewer/res/end.gif");
        end.setIcon(new ImageIcon(endImage));
        end.setToolTipText("Fast forward to last page");
//    currentBar1.add(end);
        list[10] = end;
        end.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentFile != null && currentPage < pdfDecoder.getPageCount()) {
                    currentPage = pdfDecoder.getPageCount();
                    try {
                        pdfDecoder.decodePage(currentPage);
                        pdfDecoder.invalidate();
                        repaint();
                    } catch (Exception e1) {
                    }

//        set page number display
                    pageCounter2.setText(String.valueOf(currentPage));
                }
            }
        });

        return list;
    }
}
