package org.smgame.client.frontend;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.EventListenerList;

import org.smgame.client.ClientProxy;

/**internal frame new game
 *frame interno nuovo gioco
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
@SuppressWarnings("serial")
public class NewGameJIF extends JInternalFrame implements ICustomDM {

    private JPanel playerJP;
    private GridBagConstraints labelGBC, textFieldGBC, comboBoxGBC, checkBoxGBC, buttonGBC;
    private JLabel playersNumberJL, cpuflagJL;
    private JComboBox playersNumberJCB;
    private JButton cancelJB;
    private JButton okJB;
    private JCheckBox allcpuflagJCKB;
    private JCheckBox cpuflagJCKB[];
    private JLabel gameNameJL;
    private JLabel playerJL[];
    private JTextField gameNameJTF;
    private JTextField playerJTF[];
    private NewGameVO newGameVO = new NewGameVO();
    private int previousPlayersNumber = 0;
    private int currentPlayersNumber;
    private boolean online;
    protected EventListenerList eventListenerList = new javax.swing.event.EventListenerList();

    /**Costruttore
     *
     * @param online true = partita online
     */
    public NewGameJIF(boolean online) {
        super("Nuova Partita ", false, true, false, false);
        String text = "Offline";
        if (online) {
            text = "Online";
        }
        this.setTitle(getTitle() + text);
        this.online = online;

        JTabbedPane tabbedPane = new JTabbedPane();

        playerJP = new JPanel();
        tabbedPane.addTab("Giocatori", null, playerJP,
                "Giocatori della partita");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        playerJP.setLayout(new GridBagLayout());

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

        comboBoxGBC = new GridBagConstraints();
        comboBoxGBC.weightx = 0;
        comboBoxGBC.weighty = 0;
        comboBoxGBC.insets = new Insets(2, 2, 2, 2);
        comboBoxGBC.anchor = GridBagConstraints.NORTHWEST;

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
        playerJP.add(gameNameJL, labelGBC);

        gameNameJTF = new JTextField(20);
        textFieldGBC.gridx = 1;
        textFieldGBC.gridy = 0;
        textFieldGBC.gridwidth = 2;
        textFieldGBC.fill = GridBagConstraints.HORIZONTAL;
        playerJP.add(gameNameJTF, textFieldGBC);

        playersNumberJL = new JLabel("Numero di giocatori:");
        labelGBC.gridx = 0;
        labelGBC.gridy = 1;
        playerJP.add(playersNumberJL, labelGBC);

        Integer playersNumber[] = {null, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

        playersNumberJCB = new JComboBox(playersNumber);
        comboBoxGBC.gridx = 1;
        comboBoxGBC.gridy = 1;
        playerJP.add(playersNumberJCB, comboBoxGBC);
        playersNumberJCB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });

        cpuflagJL = new JLabel("CPU flag");
        labelGBC.gridx = 2;
        labelGBC.gridy = 1;
        playerJP.add(cpuflagJL, labelGBC);

        allcpuflagJCKB = new JCheckBox();
        if (online) {
            allcpuflagJCKB.setSelected(true);
            allcpuflagJCKB.setEnabled(false);
        }
        checkBoxGBC.gridx = 2;
        checkBoxGBC.gridy = 2;
        checkBoxGBC.ipadx = 50;
        allcpuflagJCKB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });
        playerJP.add(allcpuflagJCKB, checkBoxGBC);

        cancelJB = new JButton("Annulla");
        cancelJB.setName("cancelJB");
        cancelJB.setPreferredSize(new Dimension(70, 20));
        cancelJB.setVisible(true);
        buttonGBC.gridx = 2;
        buttonGBC.gridy = 15;
        cancelJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });
        playerJP.add(cancelJB, buttonGBC);

        okJB = new JButton("OK");
        okJB.setPreferredSize(new Dimension(70, 20));
        okJB.setEnabled(false);
        okJB.setVisible(true);
        buttonGBC.gridx = 1;
        buttonGBC.gridy = 15;
        buttonGBC.weightx = 1;
        okJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });
        playerJP.add(okJB, buttonGBC);

        textFieldGBC.weightx = 0;
        textFieldGBC.weighty = 0;
        textFieldGBC.fill = GridBagConstraints.NONE;

        add(tabbedPane);
    }

    /** gestisce le azioni e gli eventi della newgame
     *
     * @param evt evento da gestire
     */
    private void newGameActionPerformed(ActionEvent evt) {
        int i;
        int y = 3;

        if (evt.getSource() instanceof JComboBox) {

            if (playersNumberJCB.getSelectedItem() == null) {
                return;
            }

            currentPlayersNumber = ((Integer) playersNumberJCB.getSelectedItem()).intValue();
            if (currentPlayersNumber != previousPlayersNumber && previousPlayersNumber != 0) {
                for (int j = 0; j < previousPlayersNumber; j++) {
                    playerJP.remove(playerJL[j]);
                    playerJP.remove(playerJTF[j]);
                    playerJP.remove(cpuflagJCKB[j]);
                    validate();
                }
                if (online) {
                    allcpuflagJCKB.setSelected(true);
                } else {
                    allcpuflagJCKB.setSelected(false);
                }
            }

            i = currentPlayersNumber;
            playerJL = new JLabel[i];
            playerJTF = new JTextField[i];
            cpuflagJCKB = new JCheckBox[i];

            for (int j = 0; j < i; j++) {

                playerJL[j] = new JLabel("Giocatore " + (j + 1) + ":");
                labelGBC.gridx = 0;
                labelGBC.gridy = y;
                playerJP.add(playerJL[j], labelGBC);
                playerJL[j].setVisible(true);

                playerJTF[j] = new JTextField();
                playerJTF[j].setPreferredSize(new Dimension(80, 20));
                if (online && j != 0) {
                    playerJTF[j].setEnabled(false);
                }
                textFieldGBC.gridx = 1;
                textFieldGBC.gridy = y;
                textFieldGBC.ipadx = 100;
                playerJP.add(playerJTF[j], textFieldGBC);
                playerJTF[j].setVisible(true);


                cpuflagJCKB[j] = new JCheckBox();
                if (online) {
                    if (j != 0) {
                        cpuflagJCKB[j].setSelected(true);
                        cpuflagJCKB[j].setEnabled(false);
                        playerJTF[j].setText("CPU Player " + j);
                    }
                }
                checkBoxGBC.gridx = 2;
                checkBoxGBC.gridy = y;
                checkBoxGBC.ipadx = 50;
                playerJP.add(cpuflagJCKB[j], checkBoxGBC);
                if (j == 0) {
                    cpuflagJCKB[j].setVisible(false);
                } else {
                    cpuflagJCKB[j].setVisible(true);
                }

                y++;
            }
            previousPlayersNumber = currentPlayersNumber;
            okJB.setEnabled(true);
            validate();
        } else if (evt.getSource() instanceof JCheckBox) {
            if (((JCheckBox) evt.getSource()).equals(allcpuflagJCKB)) {
                for (int j = 0; j < currentPlayersNumber; j++) {
                    if (j != 0) {
                        cpuflagJCKB[j].setSelected(allcpuflagJCKB.isSelected());
                        if (playerJTF[j].getText().length() == 0) {
                            playerJTF[j].setText("CPU Player " + j);
                        }
                    }
                }
                validate();
            }
        } else if (evt.getSource() instanceof JButton) {

            if ((JButton) evt.getSource() == okJB) {
                if (gameNameJTF.getText().length() == 0) {
                    JOptionPane.showInternalMessageDialog(this, "Tutti i campi devo essere valorizzati!!!", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for (int j = 0; j < currentPlayersNumber; j++) {
                    if (playerJTF[j].getText().length() == 0) {
                        JOptionPane.showInternalMessageDialog(this, "Tutti i campi devo essere valorizzati!!!", "Errore", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                String gameName = gameNameJTF.getText();
                List<String> playerNameList = new ArrayList<String>();
                List<Boolean> playerTypeList = new ArrayList<Boolean>();

                for (int j = 0; j < currentPlayersNumber; j++) {
                    playerNameList.add(playerJTF[j].getText());
                    playerTypeList.add(new Boolean(cpuflagJCKB[j].isSelected()));
                }

                for (String s : playerNameList) {
                    if (Collections.frequency(playerNameList, s) > 1) {
                        JOptionPane.showInternalMessageDialog(this, "Non è possibile avere più Giocatori con o stesso nome!!!", "Errore", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                
                if (online) {
                    ClientProxy.getInstance().createOnLineGame(gameName, playerNameList, playerTypeList);
                } else {
                    ClientProxy.getInstance().createOffLineGame(gameName, playerNameList, playerTypeList);
                }

                newGameVO = ClientProxy.getInstance().requestNewGameVO();

                if (newGameVO.getMessageType() == MessageType.ERROR) {
                    JOptionPane.showInternalMessageDialog(this,
                            newGameVO.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                } else {
                    fireNewGameEvent(new NewGameEvent(this));
                }

            } else {
                dispose();
            }
        }
    }

    /**Permette alle classi di registrarsi per l'evento
     *
     * @param listener ascoltatore
     */
    public void addMyEventListener(NewGameListener listener) {
        listenerList.add(NewGameListener.class, listener);
    }

    /**Permette alle classi di de-registrarsi per l'evento
     *
     * @param listener ascoltatore
     */
    public void removeMyEventListener(NewGameListener listener) {
        listenerList.remove(NewGameListener.class, listener);
    }

    /**Gestisce gli eventi
     *
     * @param e evento
     */
    void fireNewGameEvent(NewGameEvent e) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == NewGameListener.class) {
                ((NewGameListener) listeners[i + 1]).newGameCreating(e);
            }
        }
    }
}
