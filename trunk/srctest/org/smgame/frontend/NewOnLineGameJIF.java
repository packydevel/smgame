package org.smgame.frontend;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.EventListenerList;

import org.smgame.core.GUICoreMediator;

/**
 *
 * @author packyuser
 */
public class NewOnLineGameJIF extends JInternalFrame {

    JPanel playersJP, preferencesJP;
    GridBagConstraints labelGBC, textFieldGBC, checkBoxGBC, buttonGBC;
    JLabel playersNumberJL;
    JButton cancelJB;
    JButton okJB;
    JCheckBox cpuflagJCKB;
    JLabel gameNameJL;
    JLabel playerJL;
    JTextField gameNameJTF;
    JTextField playerJTF; //textfield nome giocatore
    String eventSource;

    /**Costruttore
     *
     */
    public NewOnLineGameJIF() {
        super("Nuova Partita", false, true, false, false);
        setSize(400, 450);

        JTabbedPane tabbedPane = new JTabbedPane();
//ImageIcon icon = createImageIcon("images/middle.gif");

        playersJP = new JPanel();
        tabbedPane.addTab("Giocatori", null, playersJP, "Inserisci il tuo nome");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        preferencesJP = new JPanel();
        tabbedPane.addTab("Preferenze", null, preferencesJP,
                "Prefernze della partita");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);


        playersJP.setLayout(new GridBagLayout());
        preferencesJP.setLayout(new GridBagLayout());

        labelGBC = new GridBagConstraints();
        labelGBC.weightx = 0;
        labelGBC.weighty = 0;
        labelGBC.insets = new Insets(2, 2, 2, 2);
        labelGBC.anchor = GridBagConstraints.NORTHWEST;

        textFieldGBC = new GridBagConstraints();
        textFieldGBC.weightx = 0;
        textFieldGBC.weighty = 0;
        textFieldGBC.insets = new Insets(2, 2, 2, 2);
        textFieldGBC.anchor = GridBagConstraints.NORTHWEST;

        checkBoxGBC = new GridBagConstraints();
        checkBoxGBC.weightx = 0;
        checkBoxGBC.weighty = 0;
        checkBoxGBC.insets = new Insets(2, 2, 2, 2);
        checkBoxGBC.anchor = GridBagConstraints.CENTER;

        buttonGBC = new GridBagConstraints();
        buttonGBC.weightx = 0;
        buttonGBC.weighty = 1;
        buttonGBC.insets = new Insets(2, 2, 2, 2);
        buttonGBC.anchor = GridBagConstraints.SOUTHEAST;


        gameNameJL = new JLabel("Nome Partita:");
        labelGBC.gridx = 0;
        labelGBC.gridy = 0;
        playersJP.add(gameNameJL, labelGBC);

        gameNameJTF = new JTextField(20);
        textFieldGBC.gridx = 1;
        textFieldGBC.gridy = 0;
        textFieldGBC.gridwidth = 2;
        textFieldGBC.fill = GridBagConstraints.HORIZONTAL;
        playersJP.add(gameNameJTF, textFieldGBC);

        playerJL = new JLabel("Nome Giocatore:");
        labelGBC.gridx = 0;
        labelGBC.gridy = 2;
        playersJP.add(playerJL, labelGBC);

        playerJTF = new JTextField(20);
        textFieldGBC.gridx = 1;
        textFieldGBC.gridy = 2;
        textFieldGBC.gridwidth = 2;
        textFieldGBC.fill = GridBagConstraints.HORIZONTAL;
        playersJP.add(playerJTF, textFieldGBC);

        cancelJB = new JButton("Annulla");
        cancelJB.setName("cancelJB");
        cancelJB.setPreferredSize(new Dimension(70, 20));
        cancelJB.setVisible(true);
        buttonGBC.gridx = 2;
        buttonGBC.gridy = 14;
        cancelJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });
        playersJP.add(cancelJB, buttonGBC);

        okJB = new JButton("OK");
        okJB.setPreferredSize(new Dimension(70, 20));
        okJB.setEnabled(true);
        okJB.setVisible(true);
        buttonGBC.gridx = 1;
        buttonGBC.gridy = 14;
        buttonGBC.weightx = 1;
        okJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });
        playersJP.add(okJB, buttonGBC);

        textFieldGBC.fill = GridBagConstraints.NONE;

        add(tabbedPane);
    }

    public String getEventSource() {
        return eventSource;
    }

    private void newGameActionPerformed(ActionEvent evt) {

        if ((JButton) evt.getSource() == okJB) {
            if (gameNameJTF.getText().length() == 0) {
                return;
            }

            if (playerJTF.getText().length() == 0) {
                return;
            }

            String gameName = gameNameJTF.getText();
            String playerName = playerJTF.getText();
            GUICoreMediator.createOnLineGame(gameName, null, playerName);
            fireNewOnLineGameEvent(new NewOnLineGameEvent(this));
        }
        dispose();
    }

    protected EventListenerList eventListenerList = new javax.swing.event.EventListenerList();

    // This methods allows classes to register for MyEvents
    public void addMyEventListener(NewOnLineGameListener listener) {
        listenerList.add(NewOnLineGameListener.class, listener);
    }

    // This methods allows classes to unregister for MyEvents
    public void removeMyEventListener(NewOnLineGameListener listener) {
        listenerList.remove(NewOnLineGameListener.class, listener);
    }

    // This private class is used to fire MyEvents
    void fireNewOnLineGameEvent(NewOnLineGameEvent e) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i <
                listeners.length; i +=
                        2) {
            if (listeners[i] == NewOnLineGameListener.class) {
                ((NewOnLineGameListener) listeners[i + 1]).newOnLineGameCreating(e);
            }
        }
    }
}
