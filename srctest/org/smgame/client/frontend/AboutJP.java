package org.smgame.client.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.smgame.util.Common;

/**JPanel del giocatore e delle carte
 *
 * @author luca
 * @author pasquale
 */
public class AboutJP extends JPanel {

    private JLabel headerJL,  footerJL;
    private JLabel[] authorImagesJL;
    private ImageIcon[] authorImages;

    /**Costruttore
     *
     * @param tplayer
     * @param tcash
     */
    public AboutJP() {
        setPreferredSize(new Dimension(500, 300));
        setLayout(new BorderLayout());

        headerJL = new JLabel("SMGame - Gioco Italiano del Sette e Mezzo");
        headerJL.setFont(new Font("Dialog", Font.PLAIN, 20));
        headerJL.setForeground(Color.RED);
        headerJL.setHorizontalAlignment(JLabel.CENTER);
        add(headerJL, BorderLayout.NORTH);

        authorImages = new ImageIcon[2];
        authorImages[0] = new ImageIcon(Common.convertStringToURL(Common.getResourceAuthors() + "packyavatar.jpg"));
        authorImages[1] = new ImageIcon(Common.convertStringToURL(Common.getResourceAuthors() + "judgeavatar.jpg"));

        authorImagesJL = new JLabel[2];
        authorImagesJL[0] = new JLabel(authorImages[0]);
        authorImagesJL[1] = new JLabel(authorImages[1]);

        add(authorImagesJL[0], BorderLayout.WEST);
        add(authorImagesJL[1], BorderLayout.EAST);

        footerJL = new JLabel("Development GUI & Engine by Pasquale Traetta and Luca Mignogna");
        footerJL.setFont(new Font("Dialog", Font.PLAIN, 14));
        footerJL.setForeground(Color.BLUE);
        footerJL.setHorizontalAlignment(JLabel.CENTER);
        add(footerJL, BorderLayout.SOUTH);
    }//end initComponents
}//end class