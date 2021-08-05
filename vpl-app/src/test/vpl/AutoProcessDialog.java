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
public class AutoProcessDialog extends JDialog
{
    Batch batch;
    JList coFiringList;
    JList sepFiringList;
    JList demoList;
    VirtualProductionLine vpl;
    JButton autoProcessButton = new javax.swing.JButton();
    JRadioButton cofiring;
    JRadioButton sepfiring;
    ButtonGroup firingGroup;
    JComboBox settingsChoiceComboBox;
    int mySettings = 0;
    Vector cfBatchStatusVector;
    Vector sfBatchStatusVector;
    Settings s = null;

	public AutoProcessDialog(VirtualProductionLine vpl)
	{
	    super(vpl);
	    this.vpl = vpl;
	    batch = (Batch)vpl.batches.elementAt(vpl.cbIndex);
	    //{{INIT_CONTROLS
	    setTitle("Automatic Processing");
	    setModal(true);
	    getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
	    setSize(380,250);

	    //setVisible(false);
            JLabel chooseLabel = new JLabel("Choose Processing Sequence for" + batch.batchName + ",");
            getContentPane().add(chooseLabel);
            firingGroup = new ButtonGroup();
	    JLabel selectProcessLabel = new JLabel("then select last process to complete automatically:");
	    getContentPane().add(selectProcessLabel);

            JPanel p = new JPanel(new GridLayout(1,2));

            JPanel p1 = new JPanel(new BorderLayout());
            sepfiring = new JRadioButton("Seperate Firing", true);
            firingGroup.add(sepfiring);
            p1.add(sepfiring,BorderLayout.NORTH);

            JPanel p2 = new JPanel(new BorderLayout());
            cofiring = new JRadioButton("Co-Firing", false);
            firingGroup.add(cofiring);
            if (vpl.demo){
                cofiring.setEnabled(false);
            }
            p2.add(cofiring,BorderLayout.NORTH);

            cfBatchStatusVector = new Vector();
            sfBatchStatusVector = new Vector();
            // add to include multicrystalline wafer
            String[] coFiringListData;
            int[] coFiringNumbers;
            String[] sepFiringListData;
            int[] sepFiringNumbers;

            Vector sepFiringVector = new Vector();
            Vector coFiringVector = new Vector();
                int bs = batch.batchStatus;

            if (batch.multiCBatch){
            coFiringListData = new String[]{"", "Acidic Texturing", "Alkaline Rinse","Rinse Acid Clean",
                "Diffusion", "Diffusion Oxide Removal",
                "Plasma Etch", "AR Coating", "Silver Screen Setup", "Silver Screen Print",
                "Al Screen Setup", "Al Screen Print", "Cofiring"};
            coFiringNumbers = new int[]{0,21,22,23,5,26,27,28,30,31,32,33,34};


            
            sepFiringListData = new String[]{"", "Acidic Texturing", "Alkaline Rinse","Rinse Acid Clean",
                "Diffusion", "Al Screen Setup",
                "Al Screen Print", "Al Firing", "Diffusion Oxide Removal",
                "Plasma Etch", "AR Coating", "Silver Screen Setup", "Silver Screen Print",
                "Silver Firing"};
            sepFiringNumbers = new int[]{0,21,22,23,5,6,7,8,9,10,11,13,14,15};

            if(bs>20 && bs<24){
                    for (int i= bs-20; i<coFiringListData.length; i++){
                        coFiringVector.addElement(coFiringListData[i]);
                        cfBatchStatusVector.addElement(new Integer(coFiringNumbers[i]));
                    }
                }
            else if (bs == 5){
                    for (int i= 4; i<coFiringListData.length; i++){
                        coFiringVector.addElement(coFiringListData[i]);
                        cfBatchStatusVector.addElement(new Integer(coFiringNumbers[i]));
                    }
                }
            else if (bs>25 && bs<29){
                  for (int i = bs - 20-1; i < coFiringListData.length; i++) {
                    coFiringVector.addElement(coFiringListData[i]);
                    cfBatchStatusVector.addElement(new Integer(coFiringNumbers[i]));
                  }
                }
            else if (bs >28){
                    for (int i= bs-21-1; i<coFiringListData.length; i++){
                        coFiringVector.addElement(coFiringListData[i]);
                        cfBatchStatusVector.addElement(new Integer(coFiringNumbers[i]));
                    }
             }
                //sep_m is for multi, the process step of multi is one less than mono
                int sep_m = 1;
                /*
              if (bs<=11){
                    for (int i= bs; i<3; i++){
                        sepFiringVector.addElement(sepFiringListData[i]);
                        sfBatchStatusVector.addElement(new Integer(sepFiringNumbers[i]));
                    }
                    if (!vpl.demo && bs<=3){
                        sepFiringVector.addElement(sepFiringListData[3]);
                        sfBatchStatusVector.addElement(new Integer(sepFiringNumbers[3]));
                    }
                    if (bs < 4)
                        bs = 4;
                    for (int i= bs; i<11-sep_m; i++){
                        sepFiringVector.addElement(sepFiringListData[i]);
                        sfBatchStatusVector.addElement(new Integer(sepFiringNumbers[i]));
                    }
                    if ((batch.assignmentNo == -1 || batch.assignmentNo == 8)){
                        sepFiringVector.addElement(sepFiringListData[11-sep_m]);
                        sfBatchStatusVector.addElement(new Integer(sepFiringNumbers[11-sep_m]));
                    }
                    for (int i = 12-sep_m; i<sepFiringListData.length; i++){
                        sepFiringVector.addElement(sepFiringListData[i]);
                        sfBatchStatusVector.addElement(new Integer(sepFiringNumbers[i]));
                    }
              }
              else */
              if(bs<16){
                    for (int i = bs-1-sep_m; i<sepFiringListData.length; i++){
                        sepFiringVector.addElement(sepFiringListData[i]);
                        sfBatchStatusVector.addElement(new Integer(sepFiringNumbers[i]));
                    }
              }
              else if((bs>20 && bs<24)||bs<=11) {
                  for (int i= bs-20; i<4; i++){
                        sepFiringVector.addElement(sepFiringListData[i]);
                        sfBatchStatusVector.addElement(new Integer(sepFiringNumbers[i]));
                    }

                    if (bs < 24)
                        bs = 4;
                    for (int i= bs; i<11-sep_m; i++){
                        sepFiringVector.addElement(sepFiringListData[i]);
                        sfBatchStatusVector.addElement(new Integer(sepFiringNumbers[i]));
                    }
                    if ((batch.assignmentNo == -1 || batch.assignmentNo == 8)){
                        sepFiringVector.addElement(sepFiringListData[11-sep_m]);
                        sfBatchStatusVector.addElement(new Integer(sepFiringNumbers[11-sep_m]));
                    }
                    for (int i = 12-sep_m; i<sepFiringListData.length; i++){
                        sepFiringVector.addElement(sepFiringListData[i]);
                        sfBatchStatusVector.addElement(new Integer(sepFiringNumbers[i]));
                    }
              }
            
            } else {
            
            coFiringListData = new String[]{"", "Saw Damage Removal Etch", "Rinse",
                "Texturing", "Rinse Acid Clean", "Diffusion", "Diffusion Oxide Removal",
                "Plasma Etch", "AR Coating", "Silver Screen Setup", "Silver Screen Print",
                "Al Screen Setup", "Al Screen Print", "Cofiring"};
            coFiringNumbers = new int[]{0,1,2,3,4,5,26,27,28,30,31,32,33,34};
            
            sepFiringListData = new String[]{"", "Saw Damage Removal Etch", "Rinse",
                "Texturing", "Rinse Acid Clean", "Diffusion", "Al Screen Setup",
                "Al Screen Print", "Al Firing", "Diffusion Oxide Removal",
                "Plasma Etch", "AR Coating", "Silver Screen Setup", "Silver Screen Print",
                "Silver Firing"};
            sepFiringNumbers = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,13,14,15};

             if (( bs <16)){
                    for (int i= bs; i<coFiringListData.length; i++){
                        coFiringVector.addElement(coFiringListData[i]);
                        cfBatchStatusVector.addElement(new Integer(coFiringNumbers[i]));
                    }
                }
                else if (bs>15 && bs<29) {
                  for (int i = bs - 20; i < coFiringListData.length; i++) {
                    coFiringVector.addElement(coFiringListData[i]);
                    cfBatchStatusVector.addElement(new Integer(coFiringNumbers[i]));
                  }
                }
                else if (bs >28){
                    for (int i= bs-21; i<coFiringListData.length; i++){
                        coFiringVector.addElement(coFiringListData[i]);
                        cfBatchStatusVector.addElement(new Integer(coFiringNumbers[i]));
                    }
                }
                
                //sep_m is for multi, the process step of multi is one less than mono

              if (bs<=11){
                    for (int i= bs; i<3; i++){
                        sepFiringVector.addElement(sepFiringListData[i]);
                        sfBatchStatusVector.addElement(new Integer(sepFiringNumbers[i]));
                    }
                    if (!vpl.demo && bs<=3){
                        sepFiringVector.addElement(sepFiringListData[3]);
                        sfBatchStatusVector.addElement(new Integer(sepFiringNumbers[3]));
                    }
                    if (bs < 4)
                        bs = 4;
                    for (int i= bs; i<11; i++){
                        sepFiringVector.addElement(sepFiringListData[i]);
                        sfBatchStatusVector.addElement(new Integer(sepFiringNumbers[i]));
                    }
                    if ((batch.assignmentNo == -1 || batch.assignmentNo == 8)){
                        sepFiringVector.addElement(sepFiringListData[11]);
                        sfBatchStatusVector.addElement(new Integer(sepFiringNumbers[11]));
                    }
                    for (int i = 12; i<sepFiringListData.length; i++){
                        sepFiringVector.addElement(sepFiringListData[i]);
                        sfBatchStatusVector.addElement(new Integer(sepFiringNumbers[i]));
                    }
              }
              else if(bs<16){
                    for (int i = bs-1; i<sepFiringListData.length; i++){
                        sepFiringVector.addElement(sepFiringListData[i]);
                        sfBatchStatusVector.addElement(new Integer(sepFiringNumbers[i]));
                    }
              }
            }
            // add end By Bobh
            /*
            String[] coFiringListData = {"", "Saw Damage Removal Etch", "Rinse",
                "Texturing", "Rinse Acid Clean", "Diffusion", "Diffusion Oxide Removal",
                "Plasma Etch", "AR Coating", "Silver Screen Setup", "Silver Screen Print",
                "Al Screen Setup", "Al Screen Print", "Cofiring"};
            int[] coFiringNumbers = {0,1,2,3,4,5,26,27,28,30,31,32,33,34};

            String[] sepFiringListData = {"", "Saw Damage Removal Etch", "Rinse",
                "Texturing", "Rinse Acid Clean", "Diffusion", "Al Screen Setup",
                "Al Screen Print", "Al Firing", "Diffusion Oxide Removal",
                "Plasma Etch", "AR Coating", "Silver Screen Setup", "Silver Screen Print",
                "Silver Firing"};
            int[] sepFiringNumbers = {0,1,2,3,4,5,6,7,8,9,10,11,13,14,15};
            */

               
             


            sepFiringList = new JList(sepFiringVector);
            coFiringList= new JList(coFiringVector);
	    sepFiringList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
	    sepFiringList.setVisibleRowCount(5);
            sepFiringList.setFixedCellWidth(165);
	    p1.add(new JScrollPane(sepFiringList),BorderLayout.CENTER);
	    coFiringList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
	    coFiringList.setVisibleRowCount(5);
            coFiringList.setFixedCellWidth(165);
            coFiringList.setEnabled(false);
	    p2.add(new JScrollPane(coFiringList),BorderLayout.CENTER);
            p.add(p1);
            p.add(p2);
            getContentPane().add(p);
            JLabel settingsLabel = new JLabel("Process batch using:");
            getContentPane().add(settingsLabel);
            String[] settingsChoiceArray = {"Default settings", "Last used settings",
                "Preferred settings"};
            settingsChoiceComboBox = new JComboBox(settingsChoiceArray);
            settingsChoiceComboBox.setSelectedItem("Default settings");
            getContentPane().add(settingsChoiceComboBox);
            if (batch.assignmentNo != -1)
                settingsChoiceComboBox.setEnabled(false);
            JPanel tempPanel = new JPanel(){
            //    public Dimension getMinimumSize() {
              //      return getPreferredSize();
                //}
      //          public Dimension getMaximumSize() {
        //            return getPreferredSize();
          //      }
//                public Dimension getPreferredSize() {
  //                  return new Dimension(300, super.getPreferredSize().height);
    //            }
            };
            tempPanel.add(autoProcessButton);
	    autoProcessButton.setAlignmentY(1.0F);
	    autoProcessButton.setText("Auto Process");
	    getContentPane().add(tempPanel);
//            setVisible(true);
            if (batch.cofiringBatch){
                cofiring.setSelected(true);
                sepFiringList.setSelectedIndex(0);
                sepFiringList.setEnabled(false);
                coFiringList.setEnabled(true);
                cofiring.setEnabled(false);
                sepfiring.setEnabled(false);
            }
            else if ((!batch.multiCBatch && batch.batchStatus>5)||(batch.multiCBatch && batch.batchStatus>25) || batch.assignmentNo != -1){
                sepfiring.setSelected(true);
                coFiringList.setSelectedIndex(0);
                coFiringList.setEnabled(false);
                sepFiringList.setEnabled(true);
                cofiring.setEnabled(false);
                sepfiring.setEnabled(false);
            }
//            validate();

            SymWindow aSymWindow = new SymWindow();
            this.addWindowListener(aSymWindow);
            SymAction lSymAction = new SymAction();
            autoProcessButton.addActionListener(lSymAction);
            cofiring.addActionListener(lSymAction);
            sepfiring.addActionListener(lSymAction);
            settingsChoiceComboBox.addActionListener(lSymAction);
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
                addNotify();
		super.setVisible(b);
	}

	class SymWindow extends java.awt.event.WindowAdapter
	{
		public void windowClosing(java.awt.event.WindowEvent event)
		{
			Object object = event.getSource();
			if (object == AutoProcessDialog.this)
				AutoProcessDialog_windowClosing(event);
		}
	}

	void AutoProcessDialog_windowClosing(java.awt.event.WindowEvent event)
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
			if (object == autoProcessButton)
				autoProcessButton_actionPerformed(event);
                        else if (object == sepfiring){
                            coFiringList.setSelectedIndex(0);
                            coFiringList.setEnabled(false);
                            sepFiringList.setEnabled(true);
                        }
                        else if (object == cofiring){
                            sepFiringList.setSelectedIndex(0);
                            sepFiringList.setEnabled(false);
                            coFiringList.setEnabled(true);
                        }
                        else if (object == settingsChoiceComboBox){
                            mySettings = settingsChoiceComboBox.getSelectedIndex();
                            if (vpl.demo){
                                if (settingsChoiceComboBox.getSelectedIndex() == 2){
                                    JOptionPane.showMessageDialog(vpl,
                                    "No preferred settings exist in demo mode, settings are reverting to default settings", "Information",
                                    JOptionPane.INFORMATION_MESSAGE);
                                    settingsChoiceComboBox.setSelectedIndex(0);
                                }
                            }
                        }
		}
	}

	void autoProcessButton_actionPerformed(java.awt.event.ActionEvent event)
	{
            try {
                    // Hide the Dialog
                    this.setVisible(false);
            } catch (Exception e) {
            }
            if (mySettings == 0)
                s = vpl.defaultSettings;
            else if (mySettings == 1)
                s = vpl.previousSettings;
            else
                s = vpl.preferredSettings;
            int p;
            if (sepfiring.isSelected()){
              int startindex;
              if (batch.batchStatus == 12){
                startindex = sfBatchStatusVector.indexOf(new Integer(11));
              }
              else{
                startindex = sfBatchStatusVector.indexOf(new Integer(batch.batchStatus));
              }
                int endindex = startindex + sepFiringList.getSelectedIndex() + 1;
                for (int i=startindex; i<endindex; i++){
                    p = ((Integer)sfBatchStatusVector.elementAt(i)).intValue();
                    if (batch.batchStatus == p)
                        doProcess(p);
                    if (batch.batchStatus == 12 && p == 11)
                        doProcess(12);
                }
            }

            else if (cofiring.isSelected()){
              int startindex;
              if (batch.batchStatus == 29){
                startindex = cfBatchStatusVector.indexOf(new Integer(28));
              }
              else {
                startindex = cfBatchStatusVector.indexOf(new Integer(batch.batchStatus));
              }
                int endindex = startindex + coFiringList.getSelectedIndex() + 1;
                for (int i=startindex; i<endindex; i++){
                    p = ((Integer)cfBatchStatusVector.elementAt(i)).intValue();
                    if (batch.batchStatus == 5){
                      batch.cofiringBatch = true;
                      //batch.batchStatus = 26;
                    }
                    if (batch.batchStatus == p)
                        doProcess(p);
                    if (batch.batchStatus == 29 && p == 28)
                        doProcess(29);
                }
            }
            vpl.finishProcess();
    }

    void doProcess(int p){
        switch(p){
            case 1: batch.etch(vpl.SSBatches, s.getEtchSettings());
                    vpl.SSBatches = vpl.SSBatches + 1;
                    break;
            case 2: batch.postWaferEtchRinse(s.getRinseSettings());
                    vpl.finishProcess();
                    break;
            case 3: batch.texture(vpl.TSSBatches, s.getTextureSettings());
                    vpl.TSSBatches = vpl.TSSBatches + 1;
                    break;
            case 4: batch.rinseAcidClean(s.getRinseAcidCleanSettings(), vpl);
                    break;
            case 5: batch.spinOnDiffusion(s.getDiffusionSettings(), vpl);
                    break;
            case 6: batch.alScreenSetup(s.getAlScreenSetupSettings(), vpl);
                    break;
            case 7: batch.screenPrint(s.getAlScreenPrintSettings(), vpl);
                    break;
            case 8: batch.alFiring(s.getAlFiringSettings(), vpl);
                    break;
            case 9: batch.diffusionOxideRemoval(s.getDiffusionOxideRemovalSettings(), vpl);
                    break;
            case 10: batch.plasmaEtch(s.getPlasmaEtchSettings(), vpl);
                    vpl.finishProcess();
                    break;
            case 11: batch.tarCoating(s.getTArCoatingSettings(), vpl);
                    break;
            case 12: batch.sarCoating(s.getSArCoatingSettings(), vpl);
                    break;
            case 13: batch.silverScreenSetup(s.getSilverScreenSetupSettings());
                    break;
            case 14: batch.silverScreenPrint(s.getSilverScreenPrintSettings(), vpl);
                    break;
            case 15: batch.silverFiring(s.getSilverFiringSettings(), vpl);
                    break;
            case 21: batch.acidTexture(vpl.acidBatches, s.getAcidTextureSettings());
                    vpl.acidBatches = vpl.acidBatches+1;
                    break;
            case 22: batch.acidRinseAcidClean(s.getAcidRinseAcidCleanSettings(), vpl);
                    break;
            case 23: batch.rinseAcidClean(s.getRinseAcidCleanSettings(), vpl);
                    break;
            case 26: batch.diffusionOxideRemoval(s.getDiffusionOxideRemovalSettings(), vpl);
                    break;
            case 27: batch.plasmaEtch(s.getPlasmaEtchSettings(), vpl);
                    vpl.finishProcess();
                    break;
            case 28: batch.tarCoating(s.getTArCoatingSettings(), vpl);
                    break;
            case 29: batch.sarCoating(s.getSArCoatingSettings(), vpl);
                    break;
            case 30: batch.silverScreenSetup(s.getSilverScreenSetupSettings());
                    break;
            case 31: batch.silverScreenPrint(s.getSilverScreenPrintSettings(), vpl);
                    break;
            case 32: batch.alScreenSetup(s.getAlScreenSetupSettings(), vpl);
                    break;
            case 33: batch.screenPrint(s.getAlScreenPrintSettings(), vpl);
                    break;
            case 34: batch.cofiring(s.getCofiringSettings(), vpl);
                    break;



        }
    }

}
