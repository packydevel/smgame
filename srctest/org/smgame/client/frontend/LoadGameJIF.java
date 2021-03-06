package org.smgame.client.frontend;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.EventListenerList;
import org.smgame.client.ClientProxy;

/**frame interno per il caricamento delle partite
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
@SuppressWarnings("serial")
public class LoadGameJIF extends JInternalFrame implements ICustomDM {

    JTable gameJT;
    LoadGameATM gameATM;
    JPanel rootJP;
    JScrollPane tableJSP;
    GridBagConstraints rootGBC, tableGBC, buttonGBC;
    JButton cancelJB, okJB;
    JLabel gameNameJL;
    int previousPlayersNumber = 0, currentPlayersNumber;

    /**Costruttore
     *
     */
    public LoadGameJIF() {
        super("Carica Partita", false, true, false, false);

        gameATM = new LoadGameATM();
        gameJT = new JTable(gameATM);
        gameJT.setFillsViewportHeight(true);
        gameJT.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gameJT.setColumnSelectionAllowed(false);
        gameJT.getColumnModel().getColumn(0).setMinWidth(0);
        gameJT.getColumnModel().getColumn(0).setMaxWidth(0);

        rootJP = new JPanel();
        rootJP.setLayout(new GridBagLayout());

        rootGBC = new GridBagConstraints();
        rootGBC.weightx = 0;
        rootGBC.weighty = 0;
        rootGBC.insets = new Insets(2, 2, 2, 2);
        rootGBC.anchor = GridBagConstraints.NORTHWEST;

        tableGBC = new GridBagConstraints();
        tableGBC.weightx = 0;
        tableGBC.weighty = 0;
        tableGBC.gridwidth = 2;
        tableGBC.fill = GridBagConstraints.HORIZONTAL;
        tableGBC.insets = new Insets(2, 2, 2, 2);
        tableGBC.anchor = GridBagConstraints.CENTER;

        tableJSP = new JScrollPane(gameJT);
        tableJSP.setPreferredSize(new Dimension(450, 150));
        tableGBC.gridx = 0;
        tableGBC.gridy = 0;
        rootJP.add(tableJSP, tableGBC);

        buttonGBC = new GridBagConstraints();
        buttonGBC.weightx = 0;
        buttonGBC.weighty = 1;
        buttonGBC.insets = new Insets(2, 2, 2, 2);
        buttonGBC.anchor = GridBagConstraints.SOUTHEAST;

        cancelJB = new JButton("Annulla");
        cancelJB.setName("cancelJB");
        cancelJB.setPreferredSize(new Dimension(70, 20));
        cancelJB.setVisible(true);
        buttonGBC.gridx = 1;
        buttonGBC.gridy = 1;
        cancelJB.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                loadGame(evt);
            }
        });
        rootJP.add(cancelJB, buttonGBC);

        okJB = new JButton("OK");
        okJB.setPreferredSize(new Dimension(70, 20));
        okJB.setVisible(true);
        buttonGBC.gridx = 0;
        buttonGBC.gridy = 1;
        buttonGBC.weightx = 1;
        okJB.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                loadGame(evt);
            }
        });
        rootJP.add(okJB, buttonGBC);

        add(rootJP);
    }

    /**carica la partita
     *
     * @param evt evento
     */
    private void loadGame(ActionEvent evt) {
        if (evt.getSource().equals(cancelJB)) {
            dispose();
        } else {
            if (gameJT.getSelectedRow() != -1) {
                ClientProxy.getInstance().loadGame((Long) gameJT.getValueAt(gameJT.getSelectedRow(), 0));
                fireNewGameEvent(new NewGameEvent(this));
                dispose();
            }
        }
    }
    protected EventListenerList eventListenerList = new javax.swing.event.EventListenerList();

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
        for (int i = 0; i <
                listeners.length; i +=
                        2) {
            if (listeners[i] == NewGameListener.class) {
                ((NewGameListener) listeners[i + 1]).newGameCreating(e);
            }
        }
    }
}
