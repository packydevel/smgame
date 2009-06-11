/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.frontend;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 *
 * @author packyuser
 */
public class ToolBarJTB{

    private JToolBar tb;

    public ToolBarJTB() {

        tb = new JToolBar(JToolBar.HORIZONTAL);
        tb.setPreferredSize(new Dimension(1024, 20));
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        //titlePanel.setSize(20, 1024);
        //titlePanel.PreferredSize(20, 1024);
        //JLabel titleLabel = new JLabel("Titolo");
        //titleTextField = new JTextField("Frame 0", 10);
        //titlePanel.add(titleLabel);
        //titlePanel.add(titleTextField);
//        resizableCheckBox = new JCheckBox("Ridimensionabile");
//        closableCheckBox = new JCheckBox("Richiudibile");
//        maximizableCheckBox = new JCheckBox("Massimizzabile");
//        iconifiableCheckBox = new JCheckBox("Iconificabile");
//        JButton generateButton = new JButton("Genera un JInternalFrame");
//        ActionListener listener = new GenerateButtonActionListener();

//        generateButton.addActionListener(listener);
        //titleTextField.addActionListener(listener);

//        tb.add(titlePanel);
//        tb.add(resizableCheckBox);
//        tb.add(closableCheckBox);
//        tb.add(maximizableCheckBox);
//        tb.add(iconifiableCheckBox);
//        tb.add(generateButton);

        tb.setFloatable(false);
    }

    public JToolBar getTb() {
        return tb;
    }
}
