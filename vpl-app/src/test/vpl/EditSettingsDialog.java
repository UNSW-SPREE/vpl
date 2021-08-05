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


public class EditSettingsDialog extends javax.swing.JDialog implements ActionListener
{
    javax.swing.JTabbedPane settingsPane = new javax.swing.JTabbedPane();
    JButton exitButton = new JButton("Exit without Saving");
    JButton saveexitButton = new JButton("Save Changes & Exit");
    SettingsPanel etchPanel;
    SettingsPanel rinsePanel;

    // add acidtexturePanel and acidRinceAcidCleanPanel BobH
    SettingsPanel acidTexturePanel;
    SettingsPanel acidRinseAcidCleanPanel;

    SettingsPanel texturePanel;
    SettingsPanel rinseAcidCleanPanel;
    SettingsPanel diffusionPanel;
    SettingsPanel diffusionOxideRemovalPanel;
    SettingsPanel plasmaEtchPanel;
    SettingsPanel tarCoatingPanel;
    SettingsPanel sarCoatingPanel;
    SettingsPanel alScreenSetupPanel;
    SettingsPanel alScreenPrintPanel;
    SettingsPanel alFiringPanel;
    SettingsPanel silverScreenSetupPanel;
    SettingsPanel silverScreenPrintPanel;
    SettingsPanel silverFiringPanel;
    SettingsPanel cofiringPanel;
    VirtualProductionLine vpl;

    public EditSettingsDialog(VirtualProductionLine vpl)
    {
    	super(vpl);
        this.vpl = vpl;
    	this.setTitle("Edit Preferred Settings");
        this.setContentPane(new JPanel());
        getContentPane().setLayout(new BorderLayout());
        this.setSize(330,420);
        //getContentPane().setSize(200,150);

        etchPanel = new SettingsPanel("etch", vpl.defaultSettings.etchArray,
                                                vpl.defaultSettings.getEtchSettings(),
                                                vpl.previousSettings.getEtchSettings(),
                                                vpl.preferredSettings.getEtchSettings(),
                                                vpl.defaultSettings.etchMinArray,
                                                vpl.defaultSettings.etchMaxArray,
                                                vpl.defaultSettings.etchUnitsArray,
                                                vpl.defaultSettings.etchClassArray, vpl);
        settingsPane.addTab("Etch", etchPanel);

        rinsePanel = new SettingsPanel("rinse", vpl.defaultSettings.rinseArray,
                                                  vpl.defaultSettings.getRinseSettings(),
                                                  vpl.previousSettings.getRinseSettings(),
                                                  vpl.preferredSettings.getRinseSettings(),
                                                  vpl.defaultSettings.rinseMinArray,
                                                  vpl.defaultSettings.rinseMaxArray,
                                                  vpl.defaultSettings.rinseUnitsArray,
                                                  vpl.defaultSettings.rinseClassArray, vpl);
        settingsPane.addTab("Rinse", rinsePanel);

        //add acidTexture and acidRinseAcidClean Panel Bobh

        acidTexturePanel = new SettingsPanel("acidTexture", vpl.defaultSettings.acidTextureArray,
                                                  vpl.defaultSettings.getAcidTextureSettings(),
                                                  vpl.previousSettings.getAcidTextureSettings(),
                                                  vpl.preferredSettings.getAcidTextureSettings(),
                                                  vpl.defaultSettings.acidTextureMinArray,
                                                  vpl.defaultSettings.acidTextureMaxArray,
                                                  vpl.defaultSettings.acidTextureUnitsArray,
                                                  vpl.defaultSettings.acidTextureClassArray, vpl);
        settingsPane.addTab("Acid Texture", acidTexturePanel);


        acidRinseAcidCleanPanel = new SettingsPanel("Alkaline clean/rinse", vpl.defaultSettings.acidRinseAcidCleanArray,
                                                          vpl.defaultSettings.getAcidRinseAcidCleanSettings(),
                                                          vpl.previousSettings.getAcidRinseAcidCleanSettings(),
                                                          vpl.preferredSettings.getAcidRinseAcidCleanSettings(),
                                                          vpl.defaultSettings.acidRacMinArray,
                                                          vpl.defaultSettings.acidRacMaxArray,
                                                          vpl.defaultSettings.acidRinseAcidCleanUnitsArray,
                                                          vpl.defaultSettings.acidRinseAcidClassArray, vpl);
        settingsPane.addTab("Alkaline clean/rinse", acidRinseAcidCleanPanel);
        //the name of this tab is used to be "Acid Rinse/Acid Clean"

        // addition of codes end here BobH

        texturePanel = new SettingsPanel("texture", vpl.defaultSettings.textureArray,
                                                  vpl.defaultSettings.getTextureSettings(),
                                                  vpl.previousSettings.getTextureSettings(),
                                                  vpl.preferredSettings.getTextureSettings(),
                                                  vpl.defaultSettings.textureMinArray,
                                                  vpl.defaultSettings.textureMaxArray,
                                                  vpl.defaultSettings.textureUnitsArray,
                                                  vpl.defaultSettings.textureClassArray, vpl);
        settingsPane.addTab("Texture", texturePanel);


        rinseAcidCleanPanel = new SettingsPanel("rinse/acid clean", vpl.defaultSettings.rinseAcidCleanArray,
                                                          vpl.defaultSettings.getRinseAcidCleanSettings(),
                                                          vpl.previousSettings.getRinseAcidCleanSettings(),
                                                          vpl.preferredSettings.getRinseAcidCleanSettings(),
                                                          vpl.defaultSettings.racMinArray,
                                                          vpl.defaultSettings.racMaxArray,
                                                          vpl.defaultSettings.rinseAcidCleanUnitsArray,
                                                          vpl.defaultSettings.rinseAcidClassArray, vpl);
        settingsPane.addTab("Rinse/Acid Clean", rinseAcidCleanPanel);
        diffusionPanel = new SettingsPanel("diffusion", vpl.defaultSettings.diffusionArray,
                                                    vpl.defaultSettings.getDiffusionSettings(),
                                                    vpl.previousSettings.getDiffusionSettings(),
                                                    vpl.preferredSettings.getDiffusionSettings(),
                                                    vpl.defaultSettings.diffusionMinArray,
                                                    vpl.defaultSettings.diffusionMaxArray,
                                                    vpl.defaultSettings.diffusionUnitsArray,
                                                    vpl.defaultSettings.diffusionClassArray, vpl);
        settingsPane.addTab("Diffusion", diffusionPanel);
        diffusionOxideRemovalPanel = new SettingsPanel("diffusion oxide removal", vpl.defaultSettings.diffusionOxideRemovalArray,
                                                          vpl.defaultSettings.getDiffusionOxideRemovalSettings(),
                                                          vpl.previousSettings.getDiffusionOxideRemovalSettings(),
                                                          vpl.preferredSettings.getDiffusionOxideRemovalSettings(),
                                                          vpl.defaultSettings.dorMinArray,
                                                          vpl.defaultSettings.dorMaxArray,
                                                          vpl.defaultSettings.diffusionOxideRemovalUnitsArray,
                                                          vpl.defaultSettings.dorClassArray, vpl);
        settingsPane.addTab("Diffusion Oxide Removal", diffusionOxideRemovalPanel);
        plasmaEtchPanel = new SettingsPanel("plasma etch", vpl.defaultSettings.plasmaEtchArray,
                                                        vpl.defaultSettings.getPlasmaEtchSettings(),
                                                        vpl.previousSettings.getPlasmaEtchSettings(),
                                                        vpl.preferredSettings.getPlasmaEtchSettings(),
                                                        vpl.defaultSettings.plasmaEtchMinArray,
                                                        vpl.defaultSettings.plasmaEtchMaxArray,
                                                        vpl.defaultSettings.plasmaEtchUnitsArray,
                                                        vpl.defaultSettings.plasmaEtchClassArray, vpl);
        settingsPane.addTab("Plasma Etch", plasmaEtchPanel);
        tarCoatingPanel = new SettingsPanel("TiO2 AR coating", vpl.defaultSettings.tarCoatingArray,
                                                        vpl.defaultSettings.getTArCoatingSettings(),
                                                        vpl.previousSettings.getTArCoatingSettings(),
                                                        vpl.preferredSettings.getTArCoatingSettings(),
                                                        vpl.defaultSettings.tarCoatingMinArray,
                                                        vpl.defaultSettings.tarCoatingMaxArray,
                                                        vpl.defaultSettings.tarCoatingUnitsArray,
                                                        vpl.defaultSettings.tarCoatingClassArray, vpl);
        settingsPane.addTab("TiO2 AR Coating", tarCoatingPanel);
        sarCoatingPanel = new SettingsPanel("SiNx AR coating", vpl.defaultSettings.sarCoatingArray,
                                                        vpl.defaultSettings.getSArCoatingSettings(),
                                                        vpl.previousSettings.getSArCoatingSettings(),
                                                        vpl.preferredSettings.getSArCoatingSettings(),
                                                        vpl.defaultSettings.sarCoatingMinArray,
                                                        vpl.defaultSettings.sarCoatingMaxArray,
                                                        vpl.defaultSettings.sarCoatingUnitsArray,
                                                        vpl.defaultSettings.sarCoatingClassArray, vpl);
        settingsPane.addTab("SiNx AR Coating", sarCoatingPanel);
        alScreenSetupPanel = new SettingsPanel("Al screen setup", vpl.defaultSettings.alScreenSetupArray,
                                                  vpl.defaultSettings.getAlScreenSetupSettings(),
                                                  vpl.previousSettings.getAlScreenSetupSettings(),
                                                  vpl.preferredSettings.getAlScreenSetupSettings(),
                                                  vpl.defaultSettings.alScreenSetupMinArray,
                                                  vpl.defaultSettings.alScreenSetupMaxArray,
                                                  vpl.defaultSettings.alScreenSetupUnitsArray,
                                                  vpl.defaultSettings.alScreenSetupClassArray, vpl);
        settingsPane.addTab("Al Screen Setup", alScreenSetupPanel);
        alScreenPrintPanel = new SettingsPanel("Al screen print", vpl.defaultSettings.alScreenPrintArray,
                                                        vpl.defaultSettings.getAlScreenPrintSettings(),
                                                        vpl.previousSettings.getAlScreenPrintSettings(),
                                                        vpl.preferredSettings.getAlScreenPrintSettings(),
                                                        vpl.defaultSettings.alScreenPrintMinArray,
                                                        vpl.defaultSettings.alScreenPrintMaxArray,
                                                        vpl.defaultSettings.alScreenPrintUnitsArray,
                                                        vpl.defaultSettings.alScreenClassArray, vpl);
        settingsPane.addTab("Al Screen Print", alScreenPrintPanel);
        alFiringPanel = new SettingsPanel("Al firing", vpl.defaultSettings.alFiringArray,
                                                        vpl.defaultSettings.getAlFiringSettings(),
                                                        vpl.previousSettings.getAlFiringSettings(),
                                                        vpl.preferredSettings.getAlFiringSettings(),
                                                        vpl.defaultSettings.alFiringMinArray,
                                                        vpl.defaultSettings.alFiringMaxArray,
                                                        vpl.defaultSettings.alFiringUnitsArray,
                                                        vpl.defaultSettings.alFiringClassArray, vpl);
        settingsPane.addTab("Al Firing", alFiringPanel);
        silverScreenSetupPanel = new SettingsPanel("silver screen setup", vpl.defaultSettings.silverScreenSetupArray,
                                                  vpl.defaultSettings.getSilverScreenSetupSettings(),
                                                  vpl.previousSettings.getSilverScreenSetupSettings(),
                                                  vpl.preferredSettings.getSilverScreenSetupSettings(),
                                                  vpl.defaultSettings.silverScreenSetupMinArray,
                                                  vpl.defaultSettings.silverScreenSetupMaxArray,
                                                  vpl.defaultSettings.silverScreenSetupUnitsArray,
                                                  vpl.defaultSettings.silverScreenSetupClassArray, vpl);
        settingsPane.addTab("Silver Screen Setup", silverScreenSetupPanel);
        silverScreenPrintPanel = new SettingsPanel("silver screen print", vpl.defaultSettings.silverScreenPrintArray,
                                                        vpl.defaultSettings.getSilverScreenPrintSettings(),
                                                        vpl.previousSettings.getSilverScreenPrintSettings(),
                                                        vpl.preferredSettings.getSilverScreenPrintSettings(),
                                                        vpl.defaultSettings.silverScreenPrintMinArray,
                                                        vpl.defaultSettings.silverScreenPrintMaxArray,
                                                        vpl.defaultSettings.silverScreenPrintUnitsArray,
                                                        vpl.defaultSettings.silverScreenPrintClassArray, vpl);
        settingsPane.addTab("Silver Screen Print", silverScreenPrintPanel);
        silverFiringPanel = new SettingsPanel("silver firing", vpl.defaultSettings.silverFiringArray,
                                                        vpl.defaultSettings.getSilverFiringSettings(),
                                                        vpl.previousSettings.getSilverFiringSettings(),
                                                        vpl.preferredSettings.getSilverFiringSettings(),
                                                        vpl.defaultSettings.silverFiringMinArray,
                                                        vpl.defaultSettings.silverFiringMaxArray,
                                                        vpl.defaultSettings.silverFiringUnitsArray,
                                                        vpl.defaultSettings.silverFiringClassArray, vpl);
        settingsPane.addTab("Silver Firing", silverFiringPanel);
        cofiringPanel = new SettingsPanel("Cofiring", vpl.defaultSettings.cofiringArray,
                                                        vpl.defaultSettings.getCofiringSettings(),
                                                        vpl.previousSettings.getCofiringSettings(),
                                                        vpl.preferredSettings.getCofiringSettings(),
                                                        vpl.defaultSettings.cofiringMinArray,
                                                        vpl.defaultSettings.cofiringMaxArray,
                                                        vpl.defaultSettings.cofiringUnitsArray,
                                                        vpl.defaultSettings.cofiringClassArray, vpl);
        settingsPane.addTab("Cofiring", cofiringPanel);
        settingsPane.setSize(280,270);
        getContentPane().add(settingsPane, BorderLayout.CENTER);


        JPanel panel3 = new JPanel();
        //CENTER_ALIGNMENT;
        panel3.add(exitButton);
        panel3.add(saveexitButton);
        if (vpl.demo){
            saveexitButton.setEnabled(false);
        }
        getContentPane().add(panel3, BorderLayout.SOUTH);
        //box3.validate();

        this.validate();
        this.doLayout();

	//**********LISTENERS**********
	exitButton.addActionListener(this);
	saveexitButton.addActionListener(this);
	}

	public void setVisible(boolean b)
	{
	    if (b)
	    {
    		Rectangle bounds = (getParent()).getBounds();
    		Dimension size = getSize();
    		setLocation(bounds.x + (bounds.width - size.width)/2,
    			        bounds.y + (bounds.height - size.height)/2);
	    }

		super.setVisible(b);
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

	class SymWindow extends java.awt.event.WindowAdapter
	{
		public void windowClosing(java.awt.event.WindowEvent event)
		{
			Object object = event.getSource();
			if (object == EditSettingsDialog.this)
				EditSettingsDialog_windowClosing(event);
		}
	}

	void EditSettingsDialog_windowClosing(java.awt.event.WindowEvent event)
	{

		try {
			this.setVisible(false);
		} catch (Exception e) {
		}
	}


	public void actionPerformed(java.awt.event.ActionEvent event)
	{
		Object object = event.getSource();
		if (object == exitButton)
			exitButton_actionPerformed(event);
		if (object == saveexitButton)
			saveexitButton_actionPerformed(event);
	}

	void exitButton_actionPerformed(java.awt.event.ActionEvent event)
	{
  	    try {
		// Hide the EditSettingsDialog
		this.setVisible(false);
	    } catch (Exception e) {
	    }
	}

	void saveexitButton_actionPerformed(java.awt.event.ActionEvent event)
	{
            boolean outOfBounds = false;

            outOfBounds = etchPanel.saveSettings();
            if (outOfBounds){
              settingsPane.setSelectedIndex(0);
              return;
            }

            outOfBounds = rinsePanel.saveSettings();
            if (outOfBounds){
              settingsPane.setSelectedIndex(1);
              return;
            }

            //add saveSettings for acidtextPanel and acidRinseAcidCleanPanel
            // and update the setSelectedIndex() for subsequent tab BobH

            outOfBounds = acidTexturePanel.saveSettings();
            if (outOfBounds){
              settingsPane.setSelectedIndex(2);
              return;
            }

            outOfBounds = acidRinseAcidCleanPanel.saveSettings();
            if (outOfBounds){
              settingsPane.setSelectedIndex(3);
              return;
            }

            outOfBounds = texturePanel.saveSettings();
            if (outOfBounds){
              settingsPane.setSelectedIndex(4);
              return;
            }

            outOfBounds = rinseAcidCleanPanel.saveSettings();
            if (outOfBounds){
              settingsPane.setSelectedIndex(5);
              return;
            }
            outOfBounds = diffusionPanel.saveSettings();
            if (outOfBounds){
              settingsPane.setSelectedIndex(6);
              return;
            }
            outOfBounds = diffusionOxideRemovalPanel.saveSettings();
            if (outOfBounds){
              settingsPane.setSelectedIndex(7);
              return;
            }
            outOfBounds = plasmaEtchPanel.saveSettings();
            if (outOfBounds){
              settingsPane.setSelectedIndex(8);
              return;
            }
            outOfBounds = tarCoatingPanel.saveSettings();
            if (outOfBounds){
              settingsPane.setSelectedIndex(9);
              return;
            }
            outOfBounds = sarCoatingPanel.saveSettings();
            if (outOfBounds){
              settingsPane.setSelectedIndex(10);
              return;
            }
            outOfBounds = alScreenSetupPanel.saveSettings();
            if (outOfBounds){
              settingsPane.setSelectedIndex(11);
              return;
            }
            outOfBounds = alScreenPrintPanel.saveSettings();
            if (outOfBounds){
              settingsPane.setSelectedIndex(12);
              return;
            }
            outOfBounds = alFiringPanel.saveSettings();
            if (outOfBounds){
              settingsPane.setSelectedIndex(13);
              return;
            }
            outOfBounds = silverScreenSetupPanel.saveSettings();
            if (outOfBounds){
              settingsPane.setSelectedIndex(14);
              return;
            }
            outOfBounds = silverScreenPrintPanel.saveSettings();
            if (outOfBounds){
              settingsPane.setSelectedIndex(15);
              return;
            }
            outOfBounds = silverFiringPanel.saveSettings();
            if (outOfBounds){
              settingsPane.setSelectedIndex(16);
              return;
            }
            outOfBounds = cofiringPanel.saveSettings();
            if (outOfBounds){
              settingsPane.setSelectedIndex(17);
              return;
            }

            // addition and update of index end here Bobh

            vpl.preferredSettings.setEtchSettings(etchPanel.preferredValues);
            vpl.preferredSettings.setRinseSettings(rinsePanel.preferredValues);

            //add setAcidTextureSettings and setAcidRinseAcidCleanSettings BobH

            vpl.preferredSettings.setAcidTextureSettings(acidTexturePanel.preferredValues);
            vpl.preferredSettings.setAcidRinseAcidCleanSettings(acidRinseAcidCleanPanel.preferredValues);

            //addition end here Bobh

            vpl.preferredSettings.setTextureSettings(texturePanel.preferredValues);
            vpl.preferredSettings.setRinseAcidCleanSettings(rinseAcidCleanPanel.preferredValues);
            vpl.preferredSettings.setDiffusionSettings(diffusionPanel.preferredValues);
            vpl.preferredSettings.setDiffusionOxideRemovalSettings(diffusionOxideRemovalPanel.preferredValues);
            vpl.preferredSettings.setPlasmaEtchSettings(plasmaEtchPanel.preferredValues);
            vpl.preferredSettings.setTArCoatingSettings(tarCoatingPanel.preferredValues);
            vpl.preferredSettings.setSArCoatingSettings(sarCoatingPanel.preferredValues);
            vpl.preferredSettings.setAlScreenSetupSettings(alScreenSetupPanel.preferredValues);
            vpl.preferredSettings.setAlScreenPrintSettings(alScreenPrintPanel.preferredValues);
            vpl.preferredSettings.setAlFiringSettings(alFiringPanel.preferredValues);
            vpl.preferredSettings.setSilverScreenSetupSettings(silverScreenSetupPanel.preferredValues);
            vpl.preferredSettings.setSilverScreenPrintSettings(silverScreenPrintPanel.preferredValues);
            vpl.preferredSettings.setSilverFiringSettings(silverFiringPanel.preferredValues);
            vpl.preferredSettings.setCofiringSettings(cofiringPanel.preferredValues);
  	    try {
		// Hide the EditSettingsDialog
		this.setVisible(false);
	    } catch (Exception e) {
	    }
        }


}
