package vpl;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.text.NumberFormat;

/**
 * Title:        Virtual Production Line<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Anna Bruce<p>
 * Company:      UNSW PV Engineering<p>
 * @author Anna Bruce
 * @version 1.0
 */

class WholeNumberSliderPanel extends JPanel{
	javax.swing.JLabel label = new javax.swing.JLabel();
	javax.swing.JLabel units = new javax.swing.JLabel();
	WholeNumberField wholeNumberField;
	JSlider slider;
        DefaultBoundedRangeModel wholeNumberSliderModel;
	String labelString;
	String unitString;
        int decPlaces;

    Font unitsFont = new Font("Dialog", Font.PLAIN, 12);
    Color colour = new java.awt.Color(16,9,115);

    WholeNumberSliderPanel(String myLabelString, String myUnitString, DefaultBoundedRangeModel myRangeModel, String myTitle, int myDecPlaces, int tickSpacing){
	    setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createTitledBorder(myTitle),
			BorderFactory.createEmptyBorder(2,2,2,2)));
        decPlaces = myDecPlaces;
        labelString = myLabelString;
        unitString = myUnitString;
        wholeNumberSliderModel = myRangeModel;
        slider = new JSlider(wholeNumberSliderModel);
        if (tickSpacing != 0){
            slider.setMajorTickSpacing(tickSpacing);
            slider.setSnapToTicks(true);
        }
	JPanel unitGroup = new JPanel(){
	    public Dimension getMinimumSize() {
		return getPreferredSize();
	    }
	    public Dimension getPreferredSize() {
		return new Dimension(200,
		                     super.getPreferredSize().height);
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
            this.wholeNumberField = new WholeNumberField(0, 4);
	    wholeNumberField.setValue(wholeNumberSliderModel.getValue());
            wholeNumberField.setEditable(false);
	    wholeNumberField.setBackground(new java.awt.Color(251,231,170));
            unitGroup.add(wholeNumberField);

	unitGroup.add(units);
	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	setDoubleBuffered(false);
        add(slider);
        add(unitGroup);
	unitGroup.setAlignmentY(TOP_ALIGNMENT);
	slider.setAlignmentY(TOP_ALIGNMENT);

	    wholeNumberSliderModel.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
		    wholeNumberField.setValue(wholeNumberSliderModel.getValue());
                }
	    });

	}

    public int getValue() {
          return wholeNumberSliderModel.getValue();
    }

    public void setEnabled(boolean b){
        if (b == false){
            slider.setEnabled(false);
        }
    }
}