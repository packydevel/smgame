package org.smgame.server.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.ScrollPaneLayout;
import org.smgame.core.GUICoreMediator;
import org.smgame.client.frontend.MessageType;
import org.smgame.server.RMIServer;

/**internal frame new game
 *frame interno nuovo gioco
 *
 * @author luca
 * @author pasquale
 */
public class ServerJF extends JFrame implements WindowListener {

    ServerVO serverVO;
    JPanel statusJP, configJP, pathJP, fileJP, databaseJP;
    JScrollPane monitorJSP;
    GridBagConstraints panelGBC, labelGBC, textFieldGBC, buttonGBC, progressBarGBC;
    JLabel pathJL, hostnameJL, portJL, dbnameJL, usernameJL, passwordJL;
    JButton startJB, stopJB, pathJB, testJB;
    JTextField hostnameJTF, portJTF, dbnameJTF, usernameJTF, passwordJTF;
    JTextArea monitorJTA;
    JProgressBar progressBarJPG;

    /**Costruttore
     *
     */
    public ServerJF() {
        super("SMGame Server");

        JTabbedPane tabbedPane = new JTabbedPane();

        statusJP = new JPanel();
        statusJP.setLayout(new GridBagLayout());
        tabbedPane.addTab("Start/Stop", null, statusJP,
                "Esegui Start/Stop del Server");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        configJP = new JPanel();
        configJP.setLayout(new GridBagLayout());
        tabbedPane.addTab("Configurazione", null, configJP,
                "Configurazione del Server");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        monitorJTA = new JTextArea();
        monitorJTA.setLineWrap(true);
        monitorJTA.setWrapStyleWord(true);
        monitorJTA.setEditable(false);
        monitorJTA.setBackground(Color.BLACK);
        monitorJTA.setForeground(Color.GREEN);

        monitorJSP = new JScrollPane(monitorJTA);
        monitorJSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        monitorJSP.setLayout(new ScrollPaneLayout());
        tabbedPane.addTab("Monitor", null, monitorJSP,
                "Monitor delle attività del Server");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        panelGBC = new GridBagConstraints();
        panelGBC.weightx = 0;
        panelGBC.weighty = 0;
        panelGBC.insets = new Insets(2, 2, 2, 2);
        panelGBC.anchor = GridBagConstraints.NORTHWEST;

        panelGBC = new GridBagConstraints();
        panelGBC.weightx = 0;
        panelGBC.weighty = 0.5;
        panelGBC.insets = new Insets(2, 2, 2, 2);
        panelGBC.anchor = GridBagConstraints.NORTHWEST;

        labelGBC = new GridBagConstraints();
        labelGBC.weightx = 0;
        labelGBC.weighty = 0;
        labelGBC.insets = new Insets(2, 2, 2, 2);
        labelGBC.anchor = GridBagConstraints.EAST;

        textFieldGBC = new GridBagConstraints();
        textFieldGBC.weightx = 1;
        textFieldGBC.weighty = 0;
        textFieldGBC.insets = new Insets(2, 2, 2, 2);
        textFieldGBC.anchor = GridBagConstraints.WEST;

        buttonGBC = new GridBagConstraints();
        buttonGBC.weightx = 0;
        buttonGBC.weighty = 0;
        buttonGBC.insets = new Insets(2, 2, 2, 2);
        buttonGBC.anchor = GridBagConstraints.SOUTH;

        progressBarGBC = new GridBagConstraints();
        progressBarGBC.weightx = 0;
        progressBarGBC.weighty = 0;
        progressBarGBC.insets = new Insets(2, 2, 2, 2);
        progressBarGBC.anchor = GridBagConstraints.SOUTH;

        fileJP = new JPanel();
        fileJP.setPreferredSize(new Dimension(470, 100));
        fileJP.setLayout(new BorderLayout());
        fileJP.setBorder(BorderFactory.createTitledBorder("Path del File delle Partite"));
        panelGBC.gridx = 0;
        panelGBC.gridy = 0;
        configJP.add(fileJP, panelGBC);

        databaseJP = new JPanel();
        databaseJP.setPreferredSize(new Dimension(470, 170));
        databaseJP.setLayout(new GridBagLayout());
        databaseJP.setBorder(BorderFactory.createTitledBorder("Parametri di Connessione al Database"));
        panelGBC.gridx = 0;
        panelGBC.gridy = 1;
        configJP.add(databaseJP, panelGBC);

        pathJP = new JPanel();
        pathJP.setPreferredSize(new Dimension(70, 30));
        fileJP.add(BorderLayout.SOUTH, pathJP);

        pathJL = new JLabel("Path: " + GUICoreMediator.getSaveDirectory());
        fileJP.add(BorderLayout.NORTH, pathJL);

        startJB = new JButton("Start");
        startJB.setName("startJB");
        startJB.setPreferredSize(new Dimension(70, 20));
        startJB.setVisible(true);
        buttonGBC.gridx = 0;
        buttonGBC.gridy = 0;
        buttonGBC.weightx = 0.5;
        buttonGBC.weighty = 0.5;
        buttonGBC.anchor = GridBagConstraints.SOUTHEAST;
        startJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                serverAction(evt);
            }
        });
        statusJP.add(startJB, buttonGBC);

        stopJB = new JButton("Stop");
        stopJB.setPreferredSize(new Dimension(70, 20));
        stopJB.setEnabled(false);
        stopJB.setVisible(true);
        buttonGBC.gridx = 1;
        buttonGBC.gridy = 0;
        buttonGBC.weightx = 0.5;
        buttonGBC.weighty = 0.5;
        buttonGBC.anchor = GridBagConstraints.SOUTHWEST;
        stopJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                serverAction(evt);
            }
        });
        statusJP.add(stopJB, buttonGBC);

        progressBarJPG = new JProgressBar(0, 100);
        progressBarJPG.setPreferredSize(new Dimension(470, 20));
        progressBarGBC.gridx = 0;
        progressBarGBC.gridy = 3;
        progressBarGBC.gridwidth = 2;
        progressBarGBC.weighty = 0.5;
        statusJP.add(progressBarJPG, progressBarGBC);

        pathJB = new JButton("Scegli il Path");
        pathJB.setSize(new Dimension(70, 20));
        pathJB.setEnabled(true);
        pathJB.setVisible(true);
        pathJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                serverAction(evt);
            }
        });
        pathJP.add(pathJB);

        testJB = new JButton("Testa la Connessione al DataBase");
        testJB.setSize(new Dimension(70, 20));
        testJB.setEnabled(true);
        testJB.setVisible(true);
        buttonGBC.gridx = 0;
        buttonGBC.gridy = 4;
        buttonGBC.gridwidth = 4;
        buttonGBC.weighty = 1;
        buttonGBC.anchor = GridBagConstraints.SOUTH;
        testJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                serverAction(evt);
            }
        });
        databaseJP.add(testJB, buttonGBC);

        hostnameJL = new JLabel("Database Server:");
        labelGBC.gridx = 0;
        labelGBC.gridy = 0;
        databaseJP.add(hostnameJL, labelGBC);

        hostnameJTF = new JTextField();
        hostnameJTF.setPreferredSize(new Dimension(150, 20));
        hostnameJTF.setText("localhost");
        hostnameJTF.setEnabled(false);
        textFieldGBC.gridx = 1;
        textFieldGBC.gridy = 0;
        databaseJP.add(hostnameJTF, textFieldGBC);

        portJL = new JLabel("Porta:");
        labelGBC.gridx = 2;
        labelGBC.gridy = 0;
        databaseJP.add(portJL, labelGBC);

        portJTF = new JTextField();
        portJTF.setPreferredSize(new Dimension(70, 20));
        portJTF.setText("3306");
        portJTF.setEnabled(false);
        textFieldGBC.gridx = 3;
        textFieldGBC.gridy = 0;
        databaseJP.add(portJTF, textFieldGBC);

        dbnameJL = new JLabel("Nome DataBase:");
        labelGBC.gridx = 0;
        labelGBC.gridy = 1;
        databaseJP.add(dbnameJL, labelGBC);

        dbnameJTF = new JTextField();
        dbnameJTF.setPreferredSize(new Dimension(150, 20));
        dbnameJTF.setText("SMGame");
        dbnameJTF.setEnabled(false);
        textFieldGBC.gridx = 1;
        textFieldGBC.gridy = 1;
        databaseJP.add(dbnameJTF, textFieldGBC);

        usernameJL = new JLabel("Username:");
        labelGBC.gridx = 0;
        labelGBC.gridy = 2;
        databaseJP.add(usernameJL, labelGBC);

        usernameJTF = new JTextField();
        usernameJTF.setPreferredSize(new Dimension(150, 20));
        usernameJTF.setText("smgameuser");
        usernameJTF.setEnabled(false);
        textFieldGBC.gridx = 1;
        textFieldGBC.gridy = 2;
        databaseJP.add(usernameJTF, textFieldGBC);

        passwordJL = new JLabel("Password:");
        labelGBC.gridx = 0;
        labelGBC.gridy = 3;
        databaseJP.add(passwordJL, labelGBC);

        passwordJTF = new JTextField();
        passwordJTF.setPreferredSize(new Dimension(150, 20));
        passwordJTF.setText("smgamepassword");
        passwordJTF.setEnabled(false);
        textFieldGBC.gridx = 1;
        textFieldGBC.gridy = 3;
        databaseJP.add(passwordJTF, textFieldGBC);

        add(tabbedPane);
        setSize(500, 370);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
    }

    private void serverAction(ActionEvent e) {

        JFileChooser fileJFC;

        if (((JButton) e.getSource()).equals(startJB)) {
            progressBarJPG.setIndeterminate(true);

            RMIServer.getInstance().start();
            serverVO = RMIServer.getInstance().requestServerVO();
            if (serverVO.getMessageType() == MessageType.ERROR) {
                JOptionPane.showMessageDialog(this, serverVO.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            } else {
                startJB.setEnabled(false);
                stopJB.setEnabled(true);
            }
            monitorJTA.append(serverVO.getMessageType().toString() + " - " + serverVO.getMessage() + "\n");
            progressBarJPG.setIndeterminate(false);
        } else if (((JButton) e.getSource()).equals(stopJB)) {
            RMIServer.getInstance().stop();
            serverVO = RMIServer.getInstance().requestServerVO();
            startJB.setEnabled(true);
            stopJB.setEnabled(false);
            monitorJTA.append(serverVO.getMessageType().toString() + " - " + serverVO.getMessage() + "\n");
        } else if (((JButton) e.getSource()).equals(pathJB)) {
            fileJFC = new JFileChooser();
            fileJFC.setDialogTitle("Seleziona una Directory");
            fileJFC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileJFC.setFileHidingEnabled(true);
            fileJFC.setMultiSelectionEnabled(false);

            if (fileJFC.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                GUICoreMediator.setSaveDirectory(fileJFC.getSelectedFile());
                serverVO = GUICoreMediator.requestServerVO();
                if (serverVO.getMessageType() == MessageType.ERROR) {
                    JOptionPane.showMessageDialog(this, serverVO.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                } else {
                    pathJL.setText(fileJFC.getSelectedFile().getPath());
                }
            }
        } else if (((JButton) e.getSource()).equals(testJB)) {
            GUICoreMediator.testDBConnection();
            serverVO = GUICoreMediator.requestServerVO();
            if (serverVO.getMessageType() == MessageType.ERROR) {
                JOptionPane.showMessageDialog(this, serverVO.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, serverVO.getMessage(), "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            monitorJTA.append(serverVO.getMessageType().toString() + " - " + serverVO.getMessage() + "\n");
        }
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        int i;
        i = JOptionPane.showConfirmDialog(this, "Chiudendo il server non sarà più possibile eseguire partite OnLine. Sei Sicuro?", "Info", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (i == 0) {
            RMIServer.getInstance().stop();
            dispose();
        }
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowOpened(WindowEvent e) {
    }
}
