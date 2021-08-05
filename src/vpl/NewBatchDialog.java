package vpl;
import java.awt.*;
import javax.swing.*;
import java.lang.*;
import java.awt.event.*;
import java.util.Vector;

/**
 * Title:        Virtual Production Line<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Anna Bruce<p>
 * Company:      UNSW PV Engineering<p>
 * @author Anna Bruce
 * @version 1.0
 */

public class NewBatchDialog extends JDialog implements ActionListener
{
	javax.swing.JLabel howManyCellsLabel = new javax.swing.JLabel();
	javax.swing.JButton createNewBatchButton = new javax.swing.JButton();

        WholeNumberField noOfCellsTextField = new WholeNumberField(5,2);
        CheckboxGroup typeCheckboxGroup;
        Checkbox singleCheckbox;
        Checkbox multiCheckbox;
	CheckboxGroup thicknessCheckboxGroup;
        //changed by ly: changed from 250,300,350,400
        // to 160, 180, 200, 250
        Checkbox thicknessCheckbox160;
        Checkbox thicknessCheckbox180;
        Checkbox thicknessCheckbox200;
        Checkbox thicknessCheckbox250;
      	CheckboxGroup resistivityCheckboxGroup;
        Checkbox resistivityCheckbox3;
        Checkbox resistivityCheckbox1;
        Checkbox resistivityCheckbox03;
        CheckboxGroup surfaceFinishCheckboxGroup;
        Checkbox surfaceFinishCheckboxW;
        Checkbox surfaceFinishCheckboxI;

        String type;
        String quality;
        int thickness;
        double resistivity;
        String surfaceFinish;
        int assignmentNo;
        VirtualProductionLine vpl;
        boolean special;

	public NewBatchDialog(VirtualProductionLine myvpl, Vector values, int assNo, boolean special)
	{
		super(myvpl);
		vpl = myvpl;
                this.special = special;
		setSize(240,400);
 		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		setVisible(false);

                type = (String)values.elementAt(0);
                quality = (String)values.elementAt(1);
                thickness = ((Integer)values.elementAt(2)).intValue();
                resistivity = ((Double)values.elementAt(3)).doubleValue();
                surfaceFinish = (String)values.elementAt(4);
                assignmentNo = assNo;

		howManyCellsLabel.setText("How many cells? (1-30)");
		getContentPane().add(howManyCellsLabel);
                noOfCellsTextField.setBackground(vpl.yellowColour);
                noOfCellsTextField.setText("5");
                getContentPane().add(noOfCellsTextField);

                typeCheckboxGroup = new CheckboxGroup();
                singleCheckbox = new Checkbox("single crystal", typeCheckboxGroup, true);
                multiCheckbox = new Checkbox("multicrystalline", typeCheckboxGroup, false);
                if (type.equals("single crystal"))
                    typeCheckboxGroup.setSelectedCheckbox(singleCheckbox);
                else if (type.equals("multicrystalline"))
                    typeCheckboxGroup.setSelectedCheckbox(multiCheckbox);

 		JPanel typeCheckboxPanel = new JPanel();
                typeCheckboxPanel.setLayout(new GridLayout(2,0));
                typeCheckboxPanel.setSize(220,220);
                typeCheckboxPanel.add(singleCheckbox);
                typeCheckboxPanel.add(multiCheckbox);
		typeCheckboxPanel.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createTitledBorder("Wafer Type"),
			BorderFactory.createEmptyBorder(2,2,2,2)));

		getContentPane().add(typeCheckboxPanel);

                thicknessCheckboxGroup = new CheckboxGroup();
                thicknessCheckbox160 = new Checkbox("160 µm", thicknessCheckboxGroup, false);
                thicknessCheckbox180 = new Checkbox("180 µm", thicknessCheckboxGroup, false);
                thicknessCheckbox200 = new Checkbox("200 µm", thicknessCheckboxGroup, false);
                thicknessCheckbox250 = new Checkbox("250 µm", thicknessCheckboxGroup, false);
                if (thickness == 160)
                    thicknessCheckboxGroup.setSelectedCheckbox(thicknessCheckbox160);
                else if (thickness == 180)
                    thicknessCheckboxGroup.setSelectedCheckbox(thicknessCheckbox180);
                else if (thickness == 200)
                    thicknessCheckboxGroup.setSelectedCheckbox(thicknessCheckbox200);
                else
                    thicknessCheckboxGroup.setSelectedCheckbox(thicknessCheckbox250);

 		JPanel thicknessCheckboxPanel = new JPanel();
                thicknessCheckboxPanel.setLayout(new GridLayout(2,0));
                thicknessCheckboxPanel.setSize(220,220);
                thicknessCheckboxPanel.add(thicknessCheckbox160);
                thicknessCheckboxPanel.add(thicknessCheckbox180);
                thicknessCheckboxPanel.add(thicknessCheckbox200);
                thicknessCheckboxPanel.add(thicknessCheckbox250);
		thicknessCheckboxPanel.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createTitledBorder("Wafer Thickness"),
			BorderFactory.createEmptyBorder(2,2,2,2)));

		getContentPane().add(thicknessCheckboxPanel);

                resistivityCheckboxGroup = new CheckboxGroup();
                resistivityCheckbox3 = new Checkbox("3 \u03A9cm", resistivityCheckboxGroup, false);
                resistivityCheckbox1 = new Checkbox("1 \u03A9cm", resistivityCheckboxGroup, true);
                resistivityCheckbox03 = new Checkbox("0.3 \u03A9cm", resistivityCheckboxGroup, false);
                if (resistivity == 3)
                    resistivityCheckboxGroup.setSelectedCheckbox(resistivityCheckbox3);
                else if (resistivity == 1)
                    resistivityCheckboxGroup.setSelectedCheckbox(resistivityCheckbox1);
                else
                    resistivityCheckboxGroup.setSelectedCheckbox(resistivityCheckbox03);

 		JPanel resistivityCheckboxPanel = new JPanel();
                resistivityCheckboxPanel.setLayout(new GridLayout(2,0));
                resistivityCheckboxPanel.setSize(220,220);
                resistivityCheckboxPanel.add(resistivityCheckbox3);
                resistivityCheckboxPanel.add(resistivityCheckbox1);
                resistivityCheckboxPanel.add(resistivityCheckbox03);
		resistivityCheckboxPanel.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createTitledBorder("Wafer Resistivity"),
			BorderFactory.createEmptyBorder(2,2,2,2)));

		getContentPane().add(resistivityCheckboxPanel);

                surfaceFinishCheckboxGroup = new CheckboxGroup();
                surfaceFinishCheckboxW = new Checkbox("wire sawn", surfaceFinishCheckboxGroup, true);
                surfaceFinishCheckboxI = new Checkbox("inner diameter diamond tipped saw", surfaceFinishCheckboxGroup, false);
                if (surfaceFinish.equals("wire sawn"))
                    surfaceFinishCheckboxGroup.setSelectedCheckbox(surfaceFinishCheckboxW);
                else if (surfaceFinish.equals("diamond tipped saw"))
                    surfaceFinishCheckboxGroup.setSelectedCheckbox(surfaceFinishCheckboxI);

 		JPanel surfaceFinishCheckboxPanel = new JPanel();
                surfaceFinishCheckboxPanel.setLayout(new GridLayout(2,0));
                surfaceFinishCheckboxPanel.setSize(220,220);
                surfaceFinishCheckboxPanel.add(surfaceFinishCheckboxW);
                surfaceFinishCheckboxPanel.add(surfaceFinishCheckboxI);
		surfaceFinishCheckboxPanel.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createTitledBorder("Wafer Surface Finish"),
			BorderFactory.createEmptyBorder(2,2,2,2)));

		getContentPane().add(surfaceFinishCheckboxPanel);

		createNewBatchButton.setText("Create New Batch");
		getContentPane().add(createNewBatchButton);
		setTitle("New Batch of Cells");
                if ((assignmentNo != -1) || (vpl.demo)){
                    if (special)
                        noOfCellsTextField.setValue(100);
                    else
                        noOfCellsTextField.setValue(10);
                    noOfCellsTextField.setEnabled(false);
                    if ((assignmentNo != 0) && (!vpl.demo)){
                        singleCheckbox.setEnabled(false);
                        multiCheckbox.setEnabled(false);
                        thicknessCheckbox160.setEnabled(false);
                        thicknessCheckbox180.setEnabled(false);
                        thicknessCheckbox200.setEnabled(false);
                        thicknessCheckbox250.setEnabled(false);
                        resistivityCheckbox3.setEnabled(false);
                        resistivityCheckbox1.setEnabled(false);
                        resistivityCheckbox03.setEnabled(false);
                        surfaceFinishCheckboxW.setEnabled(false);
                        surfaceFinishCheckboxI.setEnabled(false);
                    }
                }
		SymWindow aSymWindow = new SymWindow();
		this.addWindowListener(aSymWindow);
		createNewBatchButton.addActionListener(this);
	}

	public NewBatchDialog(VirtualProductionLine myvpl, Vector values, boolean modal)
	{
		this(myvpl, values, 0, false);
        setModal(modal);
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
		Component components[] = getComponents();
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


	public NewBatchDialog(VirtualProductionLine myvpl, String title, Vector values, boolean modal)
	{
		this(myvpl, values, modal);
		setTitle(title);
	}

    /**
     * Shows or hides the component depending on the boolean flag b.
     * @param b  if true, show the component; otherwise, hide the component.
     * @see java.awt.Component#isVisible
     */
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
			if (object == NewBatchDialog.this)
				NewBatchDialog_WindowClosing(event);
		}
	}

	void NewBatchDialog_WindowClosing(java.awt.event.WindowEvent event)
	{
		setVisible(false);
	}


	public void actionPerformed(java.awt.event.ActionEvent event)
	{
		Object object = event.getSource();
		if (object == createNewBatchButton)
			createNewBatchButton_actionPerformed(event);
	}


	void createNewBatchButton_actionPerformed(java.awt.event.ActionEvent event)
	{
            int cells = noOfCellsTextField.getValue();
            if (!special){
                if ((cells < 1) || (cells > 30)){
                    JOptionPane.showMessageDialog(this,
                    "Number of cells must be betwwen 1 and 30",
                    "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
//            else{
                if (thicknessCheckboxGroup.getSelectedCheckbox() == thicknessCheckbox160)
                    thickness = 160;
                else if (thicknessCheckboxGroup.getSelectedCheckbox() == thicknessCheckbox180)
                    thickness = 180;
                else if (thicknessCheckboxGroup.getSelectedCheckbox() == thicknessCheckbox200)
                    thickness = 200;
                else
                    thickness = 250;

                if (resistivityCheckboxGroup.getSelectedCheckbox() == resistivityCheckbox3)
                    resistivity = 3;
                else if (resistivityCheckboxGroup.getSelectedCheckbox() == resistivityCheckbox1)
                    resistivity = 1;
                else
                    resistivity = 0.3;

                if (surfaceFinishCheckboxGroup.getSelectedCheckbox() == surfaceFinishCheckboxW)
                    surfaceFinish = "wire sawn";
                else
                    surfaceFinish = "diamond tipped saw";

                if (typeCheckboxGroup.getSelectedCheckbox() == singleCheckbox)
                    type = "single crystal";
                else
                    type = "multicrystalline";

                if ((assignmentNo != -1) && (!vpl.demo)){
                	String qual = "semiconductor";
                	this.vpl.createNewBatch(type, cells, thickness, resistivity, surfaceFinish, assignmentNo, qual);
                }
                else{
                	QualDialog qualDialog = new QualDialog(vpl, type, cells, thickness, resistivity, surfaceFinish, assignmentNo);
                	qualDialog.setVisible(true);
                }

                if (special){
                    ((Batch)vpl.batches.elementAt(vpl.cbIndex)).special = true;
                }
                this.setVisible(false);
  //          }
	}
}
