package org.test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import javax.swing.JFormattedTextField;

public class FormattedTextField extends JFormattedTextField implements PropertyChangeListener {

    private double amount = 0;
    private NumberFormat amountFormat;

    public FormattedTextField() {
        amountFormat = NumberFormat.getNumberInstance();
        new JFormattedTextField(amountFormat);
        setValue(new Double(amount));
        setColumns(10);
        addPropertyChangeListener("value", this);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == this)
            amount = ((Number)getValue()).doubleValue();
    }
}
