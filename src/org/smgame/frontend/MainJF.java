/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.frontend;

/**
 *
 * @author packyuser
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainJF.java
 *
 * Created on 18-mag-2009, 18.34.16
 */
import de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class MainJF extends JFrame {

    private JDesktopPane desktop;
    private int frameNumber = 0;
    private int xPos = 0;
    private int yPos = 0;
    private JTextField titleTextField;
    private JCheckBox resizableCheckBox;
    private JCheckBox closableCheckBox;
    private JCheckBox maximizableCheckBox;
    private JCheckBox iconifiableCheckBox;
    private javax.swing.JMenu gameJM;
    private javax.swing.JMenu playerJM;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem newGameJMI;
    private javax.swing.JMenuItem loadGameJMI;
    private javax.swing.JMenuItem saveGameJMI;
    private javax.swing.JMenuItem closeGameJMI;
    private javax.swing.JMenuItem exitGameJMI;
    private GameJIF gameJIF;

    public MainJF() {
        super("InternalFrameExample");
        try {
            UIManager.setLookAndFeel(new SyntheticaStandardLookAndFeel());
        } catch (Exception e) {
        }
        setSize(1024, 768);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        JToolBar toolbar = createToolBar();
        desktop = new JDesktopPane();
        getContentPane().add(BorderLayout.NORTH, toolbar);
        getContentPane().add(BorderLayout.CENTER, desktop);
        setVisible(true);

        jMenuBar1 = new javax.swing.JMenuBar();

        gameJM = new javax.swing.JMenu("GameJM");
        gameJM.setText("Game");

        newGameJMI = new javax.swing.JMenuItem("NewGameJMI");
        newGameJMI.setText("New");
        loadGameJMI = new javax.swing.JMenuItem("LoadGameJMI");
        loadGameJMI.setText("Load ...");
        saveGameJMI = new javax.swing.JMenuItem("SaveGameJMI");
        saveGameJMI.setText("Save ...");
        closeGameJMI = new javax.swing.JMenuItem("CloseGameJMI");
        closeGameJMI.setText("Close");
        exitGameJMI = new javax.swing.JMenuItem("ExitGameJMI");
        exitGameJMI.setText("Exit");

        gameJM.add(newGameJMI);
        gameJM.add(loadGameJMI);
        gameJM.add(saveGameJMI);
        gameJM.add(closeGameJMI);
        gameJM.add(exitGameJMI);

        playerJM = new javax.swing.JMenu("PlayerJM");
        playerJM.setText("Player"); // NOI18N

        jMenuBar1.add(gameJM);
        jMenuBar1.add(playerJM);

        newGameJMI.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        closeGameJMI.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        setJMenuBar(jMenuBar1);
    }

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        gameJIF = (GameJIF) createFrame("Test", false, false, false, false);
        desktop.add(gameJIF);
        if ((JMenuItem) evt.getSource() == newGameJMI) {
            gameJIF.setVisible(true);
            newGameJMI.setEnabled(false);
            closeGameJMI.setEnabled(true);
        } else if ((JMenuItem) evt.getSource() == closeGameJMI) {
            //frame.setVisible(false);
            gameJIF.doDefaultCloseAction();
            newGameJMI.setEnabled(true);
            closeGameJMI.setEnabled(false);
        }
    }

    protected JToolBar createToolBar() {
        JToolBar tb = new JToolBar(JToolBar.VERTICAL);
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        JLabel titleLabel = new JLabel("Titolo");
        titleTextField = new JTextField("Frame 0", 10);
        titlePanel.add(titleLabel);
        titlePanel.add(titleTextField);
        resizableCheckBox = new JCheckBox("Ridimensionabile");
        closableCheckBox = new JCheckBox("Richiudibile");
        maximizableCheckBox = new JCheckBox("Massimizzabile");
        iconifiableCheckBox = new JCheckBox("Iconificabile");
        JButton generateButton = new JButton("Genera un JInternalFrame");

        ActionListener listener = new GenerateButtonActionListener();
        generateButton.addActionListener(listener);
        titleTextField.addActionListener(listener);

        tb.add(titlePanel);
//        tb.add(resizableCheckBox);
//        tb.add(closableCheckBox);
//        tb.add(maximizableCheckBox);
//        tb.add(iconifiableCheckBox);
//        tb.add(generateButton);

        tb.setFloatable(false);
        tb.setSize(20, 1024);
        return tb;
    }

    protected JInternalFrame createFrame(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
        gameJIF = new GameJIF();
        return gameJIF;
    }

    class GenerateButtonActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String title = titleTextField.getText();
            boolean resizable = resizableCheckBox.isSelected();
            boolean closable = closableCheckBox.isSelected();
            boolean maximizable = maximizableCheckBox.isSelected();
            boolean iconifiable = iconifiableCheckBox.isSelected();
            JInternalFrame frame = createFrame(title,
                    resizable,
                    closable,
                    maximizable,
                    iconifiable);
            // aggiunge al JDesktopPane
            desktop.add(frame);
            // lo mette in cima agli altri JInternalFrame
            frame.moveToFront();
            // lo rende visibile
            frame.setVisible(true);
            titleTextField.setText("Frame " + String.valueOf(
                    frameNumber++));
        }
    }

    public static void main(String[] args) {
        MainJF frame = new MainJF();
    }
} 