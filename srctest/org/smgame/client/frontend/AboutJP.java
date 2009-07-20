package org.smgame.client.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.smgame.util.ResourceLocator;

/**Pannello about/ info autori
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
@SuppressWarnings("serial")
public class AboutJP extends JPanel {

    private JLabel headerJL,  footerJL;
    private JLabel[] authorImagesJL;
    private ImageIcon[] authorImages;

    /**Costruttore
     *
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
        authorImages[0] = new ImageIcon(ResourceLocator.convertStringToURL(ResourceLocator.getResourceAuthors() + "packyavatar.jpg"));
        authorImages[1] = new ImageIcon(ResourceLocator.convertStringToURL(ResourceLocator.getResourceAuthors() + "judgeavatar.jpg"));

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
    }
}//end class