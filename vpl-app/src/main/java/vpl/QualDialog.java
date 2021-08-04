package vpl;
import java.awt.*;
import javax.swing.*;
import java.util.*;

/**
 * Title:        Virtual Production Line<p>se
 * Description:  <p>
 * Copyright:    Copyright (c) Anna Bruce<p>
 * Company:      UNSW PV Engineering<p>
 * @author Anna Bruce
 * @version 1.0
 */

/**
 * A basic implementation of the JDialog class.
 */
public class QualDialog extends javax.swing.JDialog
{
//    Batch batch;
    javax.swing.JLabel currentBatchLabel = new javax.swing.JLabel();
    javax.swing.JLabel qualLabel = new javax.swing.JLabel();
    javax.swing.JButton createNewBatchButton = new javax.swing.JButton();
    CheckboxGroup qualCheckboxGroup;
    Checkbox pCheckbox;
    Checkbox stCheckbox;
    Checkbox gCheckbox;
    Checkbox scCheckbox;
    Checkbox fzCheckbox;

    VirtualProductionLine vpl;
    String type;
    int noOfCells;
    int thickness;
    double resistivity;
    String surfaceFinish;
    int assignmentNo;
    String qual;

        public QualDialog(VirtualProductionLine vpl, String type, int noOfCells, int thickness, double resistivity, String surfaceFinish, int assignmentNo)
        {
            super(vpl);
            this.vpl = vpl;
            this.type = type;
            this.noOfCells = noOfCells;
            this.thickness = thickness;
            this.resistivity = resistivity;
            this.surfaceFinish = surfaceFinish;
            this.assignmentNo = assignmentNo;

            //{{INIT_CONTROLS
            qualLabel.setText("Choose wafer grade:");
            setTitle("Wafer Grade");
            setModal(true);
            getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
            setSize(300,250);
            if (type.equals("multicrystalline")){
              setSize(300,200);
            }
            //setVisible(false);

            JPanel textPanel = new JPanel(new GridLayout(2,1));
            textPanel.add(qualLabel);
            getContentPane().add(textPanel);

            qualCheckboxGroup = new CheckboxGroup();
            pCheckbox = new Checkbox("poor commercial grade", qualCheckboxGroup, false);
            stCheckbox = new Checkbox("standard commercial grade", qualCheckboxGroup, false);
            gCheckbox = new Checkbox("good commercial grade", qualCheckboxGroup, true);
            scCheckbox = new Checkbox("semiconductor grade", qualCheckboxGroup, false);
            fzCheckbox = new Checkbox("float zone", qualCheckboxGroup, false);

            JPanel qualCheckboxPanel = new JPanel();
            qualCheckboxPanel.setLayout(new GridLayout(5,0));
            if (type.equals("multicrystalline")){
               qualCheckboxPanel.setLayout(new GridLayout(3,0));
            }
//            qualCheckboxPanel.setSize(220,350);
            qualCheckboxPanel.add(pCheckbox);
            qualCheckboxPanel.add(stCheckbox);
            qualCheckboxPanel.add(gCheckbox);
            if (type.equals("single crystal")){
              qualCheckboxPanel.add(scCheckbox);
              qualCheckboxPanel.add(fzCheckbox);
            }
            qualCheckboxPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder("Wafer Grade"),
                    BorderFactory.createEmptyBorder(2,2,2,2)));

            getContentPane().add(qualCheckboxPanel);

            createNewBatchButton.setAlignmentY(1.0F);
            createNewBatchButton.setText("Create New Batch");
            getContentPane().add(createNewBatchButton);
            
            //if ((assignmentNo != -1) && (!vpl.demo)){
            //	pCheckbox.setEnabled(false);
            //	stCheckbox.setEnabled(false);
            //	gCheckbox.setEnabled(false);
            //	scCheckbox.setEnabled(false);
            //	fzCheckbox.setEnabled(false);
            //

                SymWindow aSymWindow = new SymWindow();
                this.addWindowListener(aSymWindow);
                SymAction lSymAction = new SymAction();
                createNewBatchButton.addActionListener(lSymAction);
        }

        public void addNotify()
        {
                // Record the size of the window prior to calling parents addNotify.
                Dimension d = getSize();

                super.addNotify();

                if (fComponentsAdjusted)
                        return;
                // Adjust components according to the insets
                Insets insets = getInsets();
                setSize(insets.left + insets.right + d.width, insets.top + insets.bottom + d.height);
                Component components[] = getContentPane().getComponents();
                for (int i = 0; i < components.length; i++)
                {
                        Point p = components[i].getLocation();
                        p.translate(insets.left, insets.top);
                        components[i].setLocation(p);
                }
                fComponentsAdjusted = true;
        }

        // Used for addNotify check.
        boolean fComponentsAdjusted = false;

    public void setVisible(boolean b)
        {
                if(b)
                {
                        Rectangle bounds = getParent().getBounds();
                        Rectangle abounds = getBounds();

                        setLocation(bounds.x + (bounds.width - abounds.width)/ 2,
                                 bounds.y + (bounds.height - abounds.height)/2);
                }
                super.setVisible(b);
        }

        class SymWindow extends java.awt.event.WindowAdapter
        {
                public void windowClosing(java.awt.event.WindowEvent event)
                {
                        Object object = event.getSource();
                        if (object == QualDialog.this)
                                QualDialog_windowClosing(event);
                }
        }

        void QualDialog_windowClosing(java.awt.event.WindowEvent event)
        {

                try {
                        // QualDialog Hide the QualDialog
                        this.setVisible(false);
                } catch (Exception e) {
                }
        }

        class SymAction implements java.awt.event.ActionListener
        {
                public void actionPerformed(java.awt.event.ActionEvent event)
                {
                        Object object = event.getSource();
                        if (object == createNewBatchButton)
                                createNewBatchButton_actionPerformed(event);
                }
        }

        void createNewBatchButton_actionPerformed(java.awt.event.ActionEvent event)
        {
          if (qualCheckboxGroup.getSelectedCheckbox() == pCheckbox)
              qual = "poor";
          else if (qualCheckboxGroup.getSelectedCheckbox() == stCheckbox)
              qual = "standard";
          else if (qualCheckboxGroup.getSelectedCheckbox() == gCheckbox)
              qual = "good";
          else if  (qualCheckboxGroup.getSelectedCheckbox() == scCheckbox)
              qual = "semiconductor";
          else
              qual = "float zone";

                try {
                        // TestDialog Hide the TestDialog
                        this.setVisible(false);
                } catch (Exception e) {
                }
        this.vpl.createNewBatch(type, noOfCells, thickness, resistivity, surfaceFinish, assignmentNo, qual);
    }


}
