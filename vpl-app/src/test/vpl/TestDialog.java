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
public class TestDialog extends javax.swing.JDialog
{
    String testName;
    Batch batch;
    javax.swing.JList cellList;
    VirtualProductionLine vpl;
    javax.swing.JLabel selectCellsLabel = new javax.swing.JLabel();
    javax.swing.JLabel currentBatchLabel = new javax.swing.JLabel();
    javax.swing.JButton runTestButton = new javax.swing.JButton();

	public TestDialog(VirtualProductionLine vpl, String testName)
	{
	    super(vpl);
	    this.vpl = vpl;
	    this.testName = testName;
	    batch = (Batch)vpl.batches.elementAt(vpl.cbIndex);
	    //{{INIT_CONTROLS
	    setTitle(testName);
	    setModal(true);
	    getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
	    setSize(350,120);
	    //setVisible(false);
            Vector data = batch.cellNames;
            cellList = new JList(data);
	    currentBatchLabel.setText(batch.batchName);
	    selectCellsLabel.setText("Select cells to test:");
            JPanel textPanel = new JPanel(new GridLayout(2,1));
            textPanel.add(currentBatchLabel);
            textPanel.add(selectCellsLabel);
	    getContentPane().add(textPanel);
	    cellList.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	    cellList.setVisibleRowCount(5);
	    getContentPane().add(new JScrollPane(cellList));
	    runTestButton.setAlignmentY(1.0F);
	    runTestButton.setText("Run Test");
	    getContentPane().add(runTestButton);

		SymWindow aSymWindow = new SymWindow();
		this.addWindowListener(aSymWindow);
		SymAction lSymAction = new SymAction();
		runTestButton.addActionListener(lSymAction);
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
			if (object == TestDialog.this)
				TestDialog_windowClosing(event);
		}
	}

	void TestDialog_windowClosing(java.awt.event.WindowEvent event)
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
			if (object == runTestButton)
				runTestButton_actionPerformed(event);
		}
	}

	void runTestButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		try {
			// TestDialog Hide the TestDialog
			this.setVisible(false);
		} catch (Exception e) {
		}
		int[] cells = cellList.getSelectedIndices();
		Vector results = batch.runTest(testName, cells);

        String testString;

        String[] testArray = new String[results.size()];
        for (int i=0; i<testArray.length; i++){
            testArray[i] = (String)results.elementAt(i);
        }
        this.vpl.refreshTestResultsPanel();
        if (batch.special){
            testString = testName + " now available";
            JOptionPane.showMessageDialog(this, testString,
                      "Information", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            testString = "Result of " + testName;
            JOptionPane.showMessageDialog(this, testArray,
                testString , JOptionPane.PLAIN_MESSAGE);
        }
            //Clipboard history = new Clipboard(testHistoryString);
            //StringSelection historyString = new StringSelection();
    }


}