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
    private MenuJMB menuJMB;
    private GameJIF gameJIF;

    public MainJF() {
        super("InternalFrameExample");

        setSize(1024, 768);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        JToolBar toolbar = createToolBar();
        desktop = new JDesktopPane();
        desktop.setDesktopManager(new GameDM());
        getContentPane().add(BorderLayout.NORTH, toolbar);
        getContentPane().add(BorderLayout.CENTER, desktop);
        setVisible(true);

        menuJMB = new MenuJMB();

        menuJMB.getNewGameJMI().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        menuJMB.getCloseGameJMI().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        menuJMB.getExitGameJMI().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });


        setJMenuBar(menuJMB.getJMenuBar1());
    }

    private void jMenu1ActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
        gameJIF = (GameJIF) createFrame("Test", false, false, false, false);
        desktop.add(gameJIF);
        if ((JMenuItem) evt.getSource() == menuJMB.getNewGameJMI()) {
            gameJIF.setVisible(true);
            menuJMB.getNewGameJMI().setEnabled(false);
            menuJMB.getCloseGameJMI().setEnabled(true);
        } else if ((JMenuItem) evt.getSource() == menuJMB.getCloseGameJMI()) {
            //frame.setVisible(false);
            //gameJIF.doDefaultCloseAction();
            gameJIF.dispose();
            menuJMB.getNewGameJMI().setEnabled(true);
            menuJMB.getCloseGameJMI().setEnabled(false);
        } else if ((JMenuItem) evt.getSource() == menuJMB.getExitGameJMI()) {
            this.dispose();
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
} 