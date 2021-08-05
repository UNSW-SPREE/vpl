package vpl;
/**
 * Title:        Virtual Production Line<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Anna Bruce<p>
 * Company:      UNSW PV Engineering<p>
 * @author Anna Bruce
 * @version 1.0
 */

import javax.swing.*;
import javax.swing.text.*;
import java.awt.Toolkit;

import java.text.*;

public class DecimalField extends JTextField {

    private NumberFormat format;
    FormattedDocument doc;

    public DecimalField(double value, int columns, NumberFormat f, int decimalPlaces) {
	super(columns);
        doc = new FormattedDocument(f);
	setDocument(doc);
	format = f;
	f.setMaximumFractionDigits(decimalPlaces);
	f.setMinimumFractionDigits(decimalPlaces);
        setValue(value);
    	}

    public double getValue() {
        double retVal = 0.0;

        try {
            retVal = format.parse(getText()).doubleValue();
        } catch (ParseException e) {
            // This should never happen because insertString allows
            // only properly formatted data to get in the field.
            Toolkit.getDefaultToolkit().beep();
            System.err.println("getValue: could not parse: " + getText());
        }
        return retVal;
    }

    public void setValue(double value) {
	setText(format.format(value));
    }

}