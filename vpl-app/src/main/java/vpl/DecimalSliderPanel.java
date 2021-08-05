package vpl;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.text.NumberFormat;
import java.beans.*;

/**
 * Title:        Virtual Production Line<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Anna Bruce<p>
 * Company:      UNSW PV Engineering<p>
 * @author Anna Bruce
 * @version 1.0
 */

class DecimalSliderPanel extends JPanel{
	javax.swing.JLabel label = new javax.swing.JLabel();
	javax.swing.JLabel units = new javax.swing.JLabel();
        DecimalField decimalField;
	JSlider slider;
	RangeModel decimalSliderModel;
	String labelString;
	String unitString;
        int decPlaces;

    Font unitsFont = new Font("Dialog", Font.PLAIN, 12);
    Color colour = new java.awt.Color(16,9,115);

    DecimalSliderPanel(String myLabelString, String myUnitString, RangeModel myRangeModel, String myTitle, int myDecPlaces, int tickSpacing){
      setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createTitledBorder(myTitle),
                        BorderFactory.createEmptyBorder(2,2,2,2)));
        decPlaces = myDecPlaces;
        labelString = myLabelString;
        unitString = myUnitString;
        decimalSliderModel = myRangeModel;
        slider = new JSlider(decimalSliderModel);
        if (tickSpacing != 0){
            slider.setMajorTickSpacing(tickSpacing);
            slider.setSnapToTicks(true);
        }
	JPanel unitGroup = new JPanel(){
	    public Dimension getMinimumSize() {
		return getPreferredSize();
	    }
	    public Dimension getPreferredSize() {
		return new Dimension(200, super.getPreferredSize().height);
	    }
	    public Dimension getMaximumSize() {
		return getPreferredSize();
	    }
	};
	    setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
	    setFont(new Font("SanSerif", Font.PLAIN, 10));
		label.setFont(new Font("Dialog", Font.BOLD, 10));
		label.setText(labelString);
		label.setLabelFor(slider);
		label.setSize(120,15);
		units.setForeground(colour);
		units.setFont(unitsFont);
		units.setText(unitString);
		units.setSize(45,15);
	unitGroup.setBorder(BorderFactory.createEmptyBorder(
						0,0,0,5));
	unitGroup.add(label);

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        JPanel textFieldHolder = new JPanel();
        this.decimalField = new DecimalField(decimalSliderModel.getDoubleValue()/100, 4, numberFormat, decPlaces);
	decimalField.setEditable(false);
	decimalField.setBackground(new java.awt.Color(251,231,170));
        unitGroup.add(decimalField);

	unitGroup.add(units);
	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	setDoubleBuffered(false);
        add(slider);
        add(unitGroup);
	unitGroup.setAlignmentY(TOP_ALIGNMENT);
	slider.setAlignmentY(TOP_ALIGNMENT);

        decimalSliderModel.addChangeListener(new ChangeListener() {
	    public void stateChanged(ChangeEvent e) {
                decimalField.setValue(decimalSliderModel.getDoubleValue()/100);
            }
	});
    }

    public double getValue() {
          return decimalSliderModel.getDoubleValue()/100;
    }

    public void setEnabled(boolean b){
        if (b == false){
            slider.setEnabled(false);
        }
    }
}
