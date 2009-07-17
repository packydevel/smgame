package org.smgame.server.frontend;

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

import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;

import org.smgame.backend.DBAccess;
import org.smgame.backend.DBPropertiesVO;
import org.smgame.core.CoreProxy;
import org.smgame.client.frontend.MessageType;
import org.smgame.server.RMIServer;

/**frame interno server
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class ServerJF extends JFrame implements WindowListener {

    ServerVO serverVO;
    JPanel statusJP, configJP, databaseJP;
    JScrollPane monitorJSP;
    GridBagConstraints panelGBC, labelGBC, textFieldGBC, buttonGBC;
    JLabel pathJL;
    JButton startJB, stopJB, testJB;
    JTextField hostnameJTF, portJTF, dbnameJTF, usernameJTF, passwordJTF;
    JTextArea monitorJTA;

    /**Costruttore
     *
     */
    public ServerJF() {
        super("SMGame Server");

        JTabbedPane tabbedPane = new JTabbedPane();

        statusJP = new JPanel();
        statusJP.setLayout(new GridBagLayout());
        tabbedPane.addTab("Server RMI", null, statusJP,
                "Esegui Start/Stop del Server RMI");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        configJP = new JPanel();
        configJP.setLayout(new GridBagLayout());
        tabbedPane.addTab("Database", null, configJP,
                "Configurazione del Database");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        initMonitorJSP();
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

        databaseJP = new JPanel();
        databaseJP.setPreferredSize(new Dimension(470, 170));
        databaseJP.setLayout(new GridBagLayout());
        databaseJP.setBorder(BorderFactory.createTitledBorder("Parametri di Connessione al Database"));
        panelGBC.gridx = 0;
        panelGBC.gridy = 1;
        configJP.add(databaseJP, panelGBC);

        startJB = new JButton("Start");
        startJB.setName("startJB");
        startJB.setPreferredSize(new Dimension(70, 20));
        startJB.setVisible(true);
        buttonGBC.gridx = 0;
        buttonGBC.gridy = 0;
        buttonGBC.weightx = 0;
        buttonGBC.weighty = 0.5;
        buttonGBC.anchor = GridBagConstraints.CENTER;
        startJB.addActionListener(new ActionListener() {

            @Override
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
        buttonGBC.weightx = 0;
        buttonGBC.weighty = 0.5;
        buttonGBC.anchor = GridBagConstraints.CENTER;
        stopJB.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                serverAction(evt);
            }
        });
        statusJP.add(stopJB, buttonGBC);

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

            @Override
            public void actionPerformed(ActionEvent evt) {
                serverAction(evt);
            }
        });
        databaseJP.add(testJB, buttonGBC);

        labelGBC.gridx = 0;
        labelGBC.gridy = 0;
        databaseJP.add(new JLabel("Database Server:"), labelGBC);

        hostnameJTF = new JTextField();
        hostnameJTF.setPreferredSize(new Dimension(150, 20));
        hostnameJTF.setEnabled(false);
        textFieldGBC.gridx = 1;
        textFieldGBC.gridy = 0;
        databaseJP.add(hostnameJTF, textFieldGBC);

        labelGBC.gridx = 2;
        labelGBC.gridy = 0;
        databaseJP.add(new JLabel("Porta:"), labelGBC);

        portJTF = new JTextField();
        portJTF.setPreferredSize(new Dimension(70, 20));
        portJTF.setEnabled(false);
        textFieldGBC.gridx = 3;
        textFieldGBC.gridy = 0;
        databaseJP.add(portJTF, textFieldGBC);

        labelGBC.gridx = 0;
        labelGBC.gridy = 1;
        databaseJP.add(new JLabel("Nome DataBase:"), labelGBC);

        dbnameJTF = new JTextField();
        dbnameJTF.setPreferredSize(new Dimension(150, 20));
        dbnameJTF.setEnabled(false);
        textFieldGBC.gridx = 1;
        textFieldGBC.gridy = 1;
        databaseJP.add(dbnameJTF, textFieldGBC);

        labelGBC.gridy = 2;
        databaseJP.add(new JLabel("Username:"), labelGBC);

        usernameJTF = new JTextField();
        usernameJTF.setPreferredSize(new Dimension(150, 20));
        usernameJTF.setEnabled(false);
        textFieldGBC.gridy = 2;
        databaseJP.add(usernameJTF, textFieldGBC);

        labelGBC.gridy = 3;
        databaseJP.add(new JLabel("Password:"), labelGBC);

        passwordJTF = new JTextField();
        passwordJTF.setPreferredSize(new Dimension(150, 20));
        passwordJTF.setEnabled(false);
        textFieldGBC.gridy = 3;
        databaseJP.add(passwordJTF, textFieldGBC);

        try {
            setTextDatabaseParameters();
        } catch (IOException ex) {
        }

        add(tabbedPane);
        setSize(500, 250);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
    }

    /**inizializza il pannello monitor */
    private void initMonitorJSP() {
        monitorJTA = new JTextArea();
        monitorJTA.setLineWrap(true);
        monitorJTA.setWrapStyleWord(true);
        monitorJTA.setEditable(false);
        monitorJTA.setBackground(Color.BLACK);
        monitorJTA.setForeground(Color.GREEN);

        monitorJSP = new JScrollPane(monitorJTA);
        monitorJSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        monitorJSP.setLayout(new ScrollPaneLayout());
    }

    /**imposta i parametri del database nelle textfield*/
    private void setTextDatabaseParameters() throws IOException {
        DBPropertiesVO dbPropVO = DBAccess.requestDBPropertiesVO();
        hostnameJTF.setText(dbPropVO.getServer());
        portJTF.setText(dbPropVO.getPort());
        dbnameJTF.setText(dbPropVO.getDatabase());
        usernameJTF.setText(dbPropVO.getUser());
        passwordJTF.setText(dbPropVO.getPassword());
    }

    /**azioni che deve fare il server in seguto alla ricezione di actionevent */
    private void serverAction(ActionEvent e) {
        JFileChooser fileJFC;

        if (((JButton) e.getSource()).equals(startJB)) {
            RMIServer.getInstance().start();
            serverVO = RMIServer.getInstance().requestServerVO();
            if (serverVO.getMessageType() == MessageType.ERROR) {
                JOptionPane.showMessageDialog(this, serverVO.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            } else {
                startJB.setEnabled(false);
                stopJB.setEnabled(true);
            }
            monitorJTA.append(serverVO.getMessageType().toString() + " - " + serverVO.getMessage() + "\n");
        } else if (((JButton) e.getSource()).equals(stopJB)) {
            RMIServer.getInstance().stop();
            serverVO = RMIServer.getInstance().requestServerVO();
            startJB.setEnabled(true);
            stopJB.setEnabled(false);
            monitorJTA.append(serverVO.getMessageType().toString() + " - " + serverVO.getMessage() + "\n");
        } else if (((JButton) e.getSource()).equals(testJB)) {
            CoreProxy.testDBConnection();
            serverVO = CoreProxy.requestServerVO();
            if (serverVO.getMessageType() == MessageType.ERROR) {
                JOptionPane.showMessageDialog(this, serverVO.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, serverVO.getMessage(), "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            monitorJTA.append(serverVO.getMessageType().toString() + " - " + serverVO.getMessage() + "\n");
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        int i;
        i = JOptionPane.showConfirmDialog(this, "Chiudendo il Server non sarà più possibile eseguire partite OnLine. Sei Sicuro?", "Info", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (i == 0) {
            RMIServer.getInstance().stop();
            dispose();
        }
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }
}
