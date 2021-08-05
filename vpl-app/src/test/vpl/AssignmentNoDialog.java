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
public class AssignmentNoDialog extends javax.swing.JDialog
{
    javax.swing.JList assignmentList;
    VirtualProductionLine vpl;
    javax.swing.JLabel selectAssignmentLabel = new javax.swing.JLabel();
    javax.swing.JButton createAssignmentButton = new javax.swing.JButton();

	public AssignmentNoDialog(VirtualProductionLine vpl)
	{
	    super(vpl);
	    this.vpl = vpl;
	    //{{INIT_CONTROLS
	    setModal(true);
	    getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
	    setSize(300,170);
	    //setVisible(false);
            String[] data = vpl.defaultSettings.assignmentStrings;
            assignmentList = new JList(data);
	    selectAssignmentLabel.setText("Select assignment:");
	    getContentPane().add(selectAssignmentLabel);
	    assignmentList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
	    assignmentList.setVisibleRowCount(5);
	    getContentPane().add(new JScrollPane(assignmentList));
	    createAssignmentButton.setAlignmentY(1.0F);
	    createAssignmentButton.setText("Create New Assignment Batch");
	    getContentPane().add(createAssignmentButton);

		SymWindow aSymWindow = new SymWindow();
		this.addWindowListener(aSymWindow);
		SymAction lSymAction = new SymAction();
		createAssignmentButton.addActionListener(lSymAction);
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
			if (object == AssignmentNoDialog.this)
				AssignmentNoDialog_windowClosing(event);
		}
	}

	void AssignmentNoDialog_windowClosing(java.awt.event.WindowEvent event)
	{

		try {
			// TestDialog Hide the TestDialog
			this.setVisible(false);
		} catch (Exception e) {
		}
	}

	class SymAction implements java.awt.event.ActionListener
	{
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();
			if (object == createAssignmentButton)
				createAssignmentButton_actionPerformed(event);
		}
	}

	void createAssignmentButton_actionPerformed(java.awt.event.ActionEvent event)
	{
	    try {
			this.setVisible(false);
	    } catch (Exception e) {
	    }
	    int assignmentNo = assignmentList.getSelectedIndex()+1;
            String url = null;
            switch(assignmentNo){
                case 1: url = "VPLAssignment1.html"; break;
                case 2: url = "VPLAssignment2.html"; break;
                case 3: url = "VPLAssignment3.html"; break;
                case 4: url = "VPLAssignment4.html"; break;
                case 5: url = "VPLAssignment5.html"; break;
                case 6: url = "VPLAssignment6.html"; break;
                case 7: url = "VPLAssignment7.html"; break;
                case 8: url = "VPLAssignment8.html"; break;
                case 9: url = "VPLMid-sessionAssessment.html"; break;
            }
            AssignmentInfoDialog aiDialog = new AssignmentInfoDialog(vpl, vpl.defaultSettings.assignmentStrings[assignmentNo-1], url, assignmentNo);
            aiDialog.setVisible(true);

    }


}