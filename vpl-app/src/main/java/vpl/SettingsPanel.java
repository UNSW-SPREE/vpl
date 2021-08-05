package vpl;
/**
 * Title:        Virtual Production Line<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Anna Bruce<p>
 * Company:      UNSW PV Engineering<p>
 * @author Anna Bruce
 * @version 1.0
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
import java.text.NumberFormat;

public class SettingsPanel extends JPanel implements ActionListener{
    JComboBox settingsChoiceComboBox;
    String[] settingsChoiceArray;
    Vector previousValues;
    Vector preferredValues;
    Vector defaultValues;
    String[] labels;
    int[] minValues;
    int[] maxValues;
    String[] units;
    String[] classes;
    String type;
    JPanel panel;
    Vector valueVector;
    String[] strandDiameters = {"20", "30", "50", "80", "120"};
    JComboBox strandDiameterComboBox = new JComboBox(strandDiameters);
    String[] patterns = {"solid", "grid"};
    JComboBox patternComboBox = new JComboBox(patterns);
    String[] fingerWidths = {"50", "100", "150", "200", "250"};
    JComboBox fingerWidthComboBox = new JComboBox(fingerWidths);
    int strandDiameter;
    String pattern;
    int fingerWidth;
    boolean malo = false;

    public SettingsPanel(String type, String[] mylabels, Vector defValues, Vector prevValues, Vector prefValues, int[] myMinValues, int[] myMaxValues, String[] myunits, String[] myclasses, VirtualProductionLine vpl){
        type = type;
        previousValues = prevValues;
        if (vpl.demo){
            preferredValues = defValues;
        }
        else{
            preferredValues = prefValues;
        }
        defaultValues = defValues;
        labels = mylabels;
        minValues = myMinValues;
        maxValues = myMaxValues;
        units = myunits;
        classes = myclasses;
        JLabel settingsLabel = new JLabel("View:  ", SwingConstants.LEFT);
        JPanel box1 = new JPanel();
        box1.add(settingsLabel);
        String[] array1 = {"Default settings", "Last used settings"};
        String[] array2 = {"Default settings", "Last used settings", "Preferred settings"};
        if (vpl.demo){
            settingsChoiceArray = array1;
        }
        else{
            settingsChoiceArray = array2;
        }
        settingsChoiceComboBox = new JComboBox(settingsChoiceArray);
        if (vpl.demo){
            settingsChoiceComboBox.setSelectedItem("Default settings");
        }
        else{
            settingsChoiceComboBox.setSelectedItem("Preferred settings");
        }
        box1.add(settingsChoiceComboBox);
        add(box1);
        panel = makeSettingsList();
        panel.doLayout();
        panel.validate();
        panel.setAlignmentX(SwingConstants.LEFT);
        add(panel);

        settingsChoiceComboBox.addActionListener(this);
    }

    public JPanel makeSettingsList(){
        JPanel processSettingsPanel = new JPanel();
        processSettingsPanel.setLayout(new GridLayout(labels.length, 1));
        if (valueVector != null)
            valueVector.clear();
        valueVector = new Vector();

        for (int i=0; i<labels.length; i++){
    	    JPanel unitGroup = new JPanel(){
	        public Dimension getMinimumSize() {
		    return getPreferredSize();
    	        }
	        public Dimension getPreferredSize() {
		    return new Dimension(260, 25);
    	        }
	        public Dimension getMaximumSize() {
		    return getPreferredSize();
	        }
	    };
    	    JPanel unitGroupBig = new JPanel(){
	        public Dimension getMinimumSize() {
		    return getPreferredSize();
    	        }
	        public Dimension getPreferredSize() {
		    return new Dimension(260, 35);
    	        }
	        public Dimension getMaximumSize() {
		    return getPreferredSize();
	        }
	    };
            JLabel label = new JLabel(labels[i]);
            if (classes[i] == "i" || classes[i] == "d" || classes[i] == "s" || classes[i] == "ds")
                unitGroup.add(label);
            else
                unitGroupBig.add(label);

//**********special**********
            if (classes[i] == "sd")
            {
                strandDiameterComboBox.setSelectedItem(String.valueOf(preferredValues.elementAt(i)));
                unitGroupBig.add(strandDiameterComboBox);
                valueVector.addElement(strandDiameterComboBox);
            }
            else if (classes[i] == "p")
            {
                patternComboBox.setSelectedItem(String.valueOf(preferredValues.elementAt(i)));
                unitGroupBig.add(patternComboBox);
                valueVector.addElement(patternComboBox);
            }
            else if (classes[i] == "fw")
            {
                fingerWidthComboBox.setSelectedItem(String.valueOf(preferredValues.elementAt(i)));
                unitGroupBig.add(fingerWidthComboBox);
                valueVector.addElement(fingerWidthComboBox);
            }

//**********integers**********
            else if (classes[i] == "i")
            {
                WholeNumberField field = new WholeNumberField(0, 4);
                field.setValue(((Integer)(preferredValues.elementAt(i))).intValue());
                field.setBackground(new java.awt.Color(251,231,170));
                unitGroup.add(field);
                valueVector.addElement(field);
            }
//**********strings**********

            else if (classes[i] == "s"){
                JTextField field = new JTextField((String)preferredValues.elementAt(i));
	        field.setBackground(new java.awt.Color(251,231,170));
                unitGroup.add(field);
                valueVector.addElement(field);
            }
//**********doubles***********
            else {
                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                DecimalField field = new DecimalField(0.0, 4 , numberFormat, 2);
                field.setValue(((Double)(preferredValues.elementAt(i))).doubleValue());
	        field.setBackground(new java.awt.Color(251,231,170));
                unitGroup.add(field);
                valueVector.addElement(field);
            }
            JLabel unit = new JLabel(units[i]);
            if (classes[i] == "i" || classes[i] == "d" || classes[i] == "s" || classes[i] == "ds"){
    	        unitGroup.add(unit);
                unitGroup.setAlignmentX(SwingConstants.LEFT);
                processSettingsPanel.add(unitGroup);
            }
            else{
    	        unitGroupBig.add(unit);
                unitGroupBig.setAlignmentX(SwingConstants.LEFT);
            processSettingsPanel.add(unitGroupBig);
            }

        }
        return processSettingsPanel;
    }

    public JPanel makeSettingsList(Vector myValues){
        JPanel processSettingsPanel = new JPanel();
        processSettingsPanel.setLayout(new GridLayout(labels.length, 1));
        if (valueVector != null)
            valueVector.clear();
        valueVector = new Vector();

        for (int i=0; i<labels.length; i++){
    	    JPanel unitGroup = new JPanel(){
	        public Dimension getMinimumSize() {
		    return getPreferredSize();
    	        }
	        public Dimension getPreferredSize() {
		    return new Dimension(260, 25);
    	        }
	        public Dimension getMaximumSize() {
		    return getPreferredSize();
	        }
	    };
    	    JPanel unitGroupBig = new JPanel(){
	        public Dimension getMinimumSize() {
		    return getPreferredSize();
    	        }
	        public Dimension getPreferredSize() {
		    return new Dimension(260, 35);
    	        }
	        public Dimension getMaximumSize() {
		    return getPreferredSize();
	        }
	    };
            JLabel label = new JLabel(labels[i]);
            if (classes[i] == "i" || classes[i] == "d" || classes[i] == "s" || classes[i] == "ds")
                unitGroup.add(label);
            else
                unitGroupBig.add(label);

//**********special**********
            if (classes[i] == "sd")
            {
                strandDiameterComboBox.setSelectedItem(String.valueOf(myValues.elementAt(i)));
                unitGroupBig.add(strandDiameterComboBox);
                valueVector.addElement(strandDiameterComboBox);
            }
            else if (classes[i] == "p")
            {
                patternComboBox.setSelectedItem(String.valueOf(myValues.elementAt(i)));
                unitGroupBig.add(patternComboBox);
                valueVector.addElement(patternComboBox);
            }
            else if (classes[i] == "fw")
            {
                fingerWidthComboBox.setSelectedItem(String.valueOf(myValues.elementAt(i)));
                unitGroupBig.add(fingerWidthComboBox);
                valueVector.addElement(fingerWidthComboBox);
            }

//**********integers**********
            else if (classes[i] == "i")
            {
                WholeNumberField field = new WholeNumberField(0, 4);
                field.setValue(((Integer)(myValues.elementAt(i))).intValue());
                field.setBackground(new java.awt.Color(251,231,170));
                unitGroup.add(field);
                valueVector.addElement(field);
            }
//**********strings**********

            else if (classes[i] == "s"){
                JTextField field = new JTextField((String)myValues.elementAt(i));
	        field.setBackground(new java.awt.Color(251,231,170));
                unitGroup.add(field);
                valueVector.addElement(field);
            }
//**********doubles***********
            else {
                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                DecimalField field = new DecimalField(0.0, 4, numberFormat, 2);
                field.setValue(((Double)(myValues.elementAt(i))).doubleValue());
	        field.setBackground(new java.awt.Color(251,231,170));
                unitGroup.add(field);
                valueVector.addElement(field);
            }
            JLabel unit = new JLabel(units[i]);
            if (classes[i] == "i" || classes[i] == "d" || classes[i] == "s" || classes[i] == "ds"){
    	        unitGroup.add(unit);
                unitGroup.setAlignmentX(SwingConstants.LEFT);
                processSettingsPanel.add(unitGroup);
            }
            else{
    	        unitGroupBig.add(unit);
                unitGroupBig.setAlignmentX(SwingConstants.LEFT);
            processSettingsPanel.add(unitGroupBig);
            }

        }
        return processSettingsPanel;
    }

	    public Dimension getMinimumSize() {
		return getPreferredSize();
	    }
	    public Dimension getPreferredSize() {
		return new Dimension(290,
		                     (60+25*labels.length));
	    }
	    public Dimension getMaximumSize() {
		return getPreferredSize();
	    }

    public void actionPerformed(java.awt.event.ActionEvent event)
    {
        Object object = event.getSource();
        if (object == settingsChoiceComboBox){
            int mySettings = settingsChoiceComboBox.getSelectedIndex();
            switch(mySettings){
                case(0):
                    this.remove(panel);
                    this.panel = makeSettingsList(defaultValues);
                    this.add(panel);
                    this.revalidate();
                    break;
                case(1):
                    this.remove(panel);
                    this.panel = makeSettingsList(previousValues);
                    this.add(panel);
                    this.revalidate();
                    break;
                case(2):
                    this.remove(panel);
                    this.panel = makeSettingsList(preferredValues);
                    this.add(panel);
                    this.revalidate();
                    break;
            }
        this.repaint();
        }
    }

    boolean saveSettings(){
            preferredValues = getValues();
            settingsChoiceComboBox.setSelectedItem("Preferred settings");
            return malo;
    }

    public Vector getValues(){
        Vector myVector = new Vector();
        malo = false;
        for (int i=0; i<valueVector.size(); i++){
            if (classes[i] == "sd"){
                int tempInt = Integer.parseInt((String)strandDiameterComboBox.getSelectedItem());
                myVector.add(new Integer(tempInt));
            }
            else if (classes[i] == "p"){
                String tempString = (String)patternComboBox.getSelectedItem();
                myVector.add(new String(tempString));
            }
            else if (classes[i] == "fw"){
                int tempInt = Integer.parseInt((String)fingerWidthComboBox.getSelectedItem());
                myVector.add(new Integer(tempInt));
            }
            else if (classes[i] == "i"){
                int tempInt = ((WholeNumberField)valueVector.elementAt(i)).getValue();
                if (minValues[i]<=tempInt && tempInt<=maxValues[i]){
//                    WholeNumberField wnfield = (WholeNumberField)valueVector.elementAt(i);
                    myVector.add(new Integer(tempInt));
                }
                else{
                    JOptionPane.showMessageDialog(this,
                    "You have entered a value for " + labels[i] + " outside the allowed range.\nPlease enter a value between " + minValues[i] + " and " + maxValues[i] + ".",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                    malo = true;
                    if (minValues[i]>tempInt)
                      myVector.add(new Integer(minValues[i]));
                    else if (tempInt>maxValues[i])
                      myVector.add(new Integer(maxValues[i]));
                }
            }
            else if (classes[i] == "s"){
                JTextField tfield = (JTextField)valueVector.elementAt(i);
                myVector.add(tfield.getText());
            }
            else if (classes[i] == "d"){
                double tempDouble = ((DecimalField)valueVector.elementAt(i)).getValue();
                if (minValues[i]<=tempDouble && tempDouble<=maxValues[i]){
//                    DecimalField dfield = (DecimalField)valueVector.elementAt(i);
                    myVector.add(new Double(tempDouble));
                }
                else{
                    JOptionPane.showMessageDialog(this,
                    "You have entered a value for " + labels[i] + " outside the allowed range.\nPlease enter a value between " + minValues[i] + " and " + maxValues[i] + ".",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                    malo = true;
                    if (minValues[i]>tempDouble)
                      myVector.add(new Double(minValues[i]));
                    else if (tempDouble>maxValues[i])
                      myVector.add(new Double(maxValues[i]));
                }
            }
            else if (classes[i] == "ds"){
                double tempDouble = ((DecimalField)valueVector.elementAt(i)).getValue();
                if (0.1*minValues[i]<=tempDouble && tempDouble<=maxValues[i]){
//                    DecimalField dfield = (DecimalField)valueVector.elementAt(i);
                    myVector.add(new Double(tempDouble));
                }
                else{
                    JOptionPane.showMessageDialog(this,
                    "You have entered a value for " + labels[i] + " outside the allowed range.\nPlease enter a value between " + 0.1*minValues[i] + " and " + maxValues[i] + ".",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                  malo = true;
                  if (minValues[i]>tempDouble)
                    myVector.add(new Double(minValues[i]));
                  else if (tempDouble>maxValues[i])
                    myVector.add(new Double(maxValues[i]));
                }
            }
        }
        return myVector;
    }
}
