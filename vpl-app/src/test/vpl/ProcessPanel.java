package vpl;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

/**
 * Title:        Virtual Production Line<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Anna Bruce<p>
 * Company:      UNSW PV Engineering<p>
 * @author Anna Bruce
 * @version 1.0
 */

class ProcessPanel extends JPanel implements ActionListener, ChangeListener{
    Vector rangeModels;
    Vector sliderPanels;
    String[] classArray;
    String buttonName;
    int bs;
    int assignmentNo;
    boolean disabled = false;
    Settings settings;
    int panelHeight;
    javax.swing.JButton button;
    String[] strandDiameters = {"20", "30", "50", "80", "120"};
    JComboBox strandDiameterComboBox = new JComboBox(strandDiameters);
    JRadioButton solid, grid;
    ButtonGroup patternGroup;
    String[] fingerWidths = {"50", "100", "150", "200", "250"};
    boolean[] demoArray;

    JComboBox fingerWidthComboBox = new JComboBox(fingerWidths);
    String pattern;
    Settings sets;

    VirtualProductionLine vpl;

    ProcessPanel(VirtualProductionLine myvpl, String name, Settings s){
        vpl = myvpl;
        sets = null;
        bs = ((Batch)vpl.batches.elementAt(vpl.cbIndex)).batchStatus;
        assignmentNo = ((Batch)vpl.batches.elementAt(vpl.cbIndex)).assignmentNo;
        switch(assignmentNo){
            case -1:
                disabled = false;
                break;
//            case 0:
  //              disabled = true;
    //            break;
            case 1:
                if (bs == 1)
                    disabled = false;
                else
                    disabled = true;
                break;
            case 2:
                if (bs == 3)
                    disabled = false;
                else
                    disabled = true;
                break;
            case 3:
                if (bs == 4)
                    disabled = false;
                else
                    disabled = true;
                break;
            case 4:
                if (bs == 5)
                    disabled = false;
                else
                    disabled = true;
                break;
            case 5:
                if (bs == 6 || bs == 7 || bs ==8)
                    disabled = false;
                else
                    disabled = true;
                break;
            case 6:
                if (bs == 10)
                    disabled = false;
                else
                    disabled = true;
                break;
            case 7:
                if (bs == 13 || bs == 14 || bs == 15)
                    disabled = false;
                else
                    disabled = true;
                break;
            case 8:
                if (bs == 11)
                    disabled = false;
                else
                    disabled = true;
                break;
            case 9:
                if (bs == 5 || bs == 13 || bs == 14 || bs == 15)
                    disabled = false;
                else
                    disabled = true;
                break;
        }
        if (vpl.demo)
            disabled = true;
        if (disabled && !vpl.demo){
            settings = myvpl.defaultSettings;
        }
        else{
            settings = s;
        }
        setVisible(true);
    }

//	Dimension preferredSize = this.getPreferredSize();
        Dimension minimumSize = new Dimension(240,panelHeight);

        public Dimension getMinimumSize() {
            return getPreferredSize();
	}
	public Dimension getPreferredSize() {
	    return new Dimension(240,panelHeight);
        }
	public Dimension getMaximumSize() {
	    return getPreferredSize();
        }

    void button_actionPerformed(java.awt.event.ActionEvent event)
    {
        Vector tempVector = new Vector();
//        if (classArray == null)
        for (int i=0;i<classArray.length;i++){
                if (classArray[i].equals("i")){
                    Integer a = new Integer(((WholeNumberSliderPanel)sliderPanels.elementAt(i)).getValue());
                    tempVector.addElement(a);
                }
                else if (classArray[i].equals("d")){
                    Double b = new Double(((DecimalSliderPanel)sliderPanels.elementAt(i)).getValue());
                    tempVector.addElement(b);
                }
                else if (classArray[i].equals("ds")){
                    Double b = new Double(((DecimalSliderPanel)sliderPanels.elementAt(i)).getValue());
                    tempVector.addElement(b);
                }
                else if (classArray[i].equals("sd")){
                    int tempInt = Integer.parseInt((String)strandDiameterComboBox.getSelectedItem());
                    tempVector.add(new Integer(tempInt));
                }
                else if (classArray[i].equals("p")){
                    if (solid.isSelected())
                        pattern = "solid";
                    else
                        pattern = "grid";
                    tempVector.add(new String(pattern));
                }
                else if (classArray[i].equals("fw")){
                    int tempInt = Integer.parseInt((String)fingerWidthComboBox.getSelectedItem());
                    tempVector.add(new Integer(tempInt));
                }
        }
        switch(bs){
            case 1: vpl.etch(tempVector, disabled); break;

            // add acidtexture and acidRinseAcidClean by Bobh
            case 21: vpl.acidTexture(tempVector, disabled); break;
            case 22: vpl.acidRinseAcidClean(tempVector, disabled); break;
            case 23: vpl.rinseAcidClean(tempVector, disabled); break;
            // end add Bobh

            case 2: vpl.postWaferEtchRinse(tempVector, disabled); break;
            case 3: vpl.texture(tempVector, disabled); break;
            case 4: vpl.rinseAcidClean(tempVector, disabled); break;
            case 5: vpl.spinOnDiffusion(tempVector, disabled); break;
            case 6: vpl.alScreenSetup(tempVector, disabled); break;
            case 7: vpl.screenPrint(tempVector, disabled); break;
            case 8: vpl.alFiring(tempVector, disabled); break;
            case 9: vpl.diffusionOxideRemoval(tempVector, disabled); break;
            case 10: vpl.plasmaEtch(tempVector, disabled); break;
            case 11: vpl.tarCoating(tempVector, disabled); break;
            case 12: vpl.sarCoating(tempVector, disabled); break;
            case 13: vpl.silverScreenSetup(tempVector, disabled); break;
            case 14: vpl.silverScreenPrint(tempVector, disabled); break;
            case 15: vpl.silverFiring(tempVector, disabled); break;
            case 26: vpl.diffusionOxideRemoval(tempVector, disabled); break;
            case 27: vpl.plasmaEtch(tempVector, disabled); break;
            case 28: vpl.tarCoating(tempVector, disabled); break;
            case 29: vpl.sarCoating(tempVector, disabled); break;
            case 30: vpl.silverScreenSetup(tempVector, disabled); break;
            case 31: vpl.silverScreenPrint(tempVector, disabled); break;
            case 32: vpl.alScreenSetup(tempVector, disabled); break;
            case 33: vpl.screenPrint(tempVector, disabled); break;
            case 34: vpl.cofiring(tempVector, disabled); break;

        }
        button.setEnabled(false);
    }

    public void actionPerformed(ActionEvent event)
    {
		Object object = event.getSource();
		if (object == button)
			button_actionPerformed(event);
                else
                    updateSettings();
 //               else if (object == strandDiameterComboBox)
   //                 System.out.println("implement this");
//                    vpl.alGraphic.updateThis(0,-1.0,-1.0,Integer.parseInt((String)strandDiameterComboBox.getSelectedItem()), "0");
     //           else if (object == solid)
       //             System.out.println("implement this");
//                    vpl.alGraphic.updateThis(0,-1.0,-1.0,0,"solid");
         //       else if (object == grid)
           //         System.out.println("implement this");
//                    vpl.alGraphic.updateThis(0,-1.0,-1.0,0,"grid");
    }

    public void stateChanged(ChangeEvent e) {
/*        vpl.alGraphic.updateThis(((WholeNumberSliderPanel)sliderPanels.elementAt(0)).getValue(),
            ((DecimalSliderPanel)sliderPanels.elementAt(1)).getValue(),
            ((DecimalSliderPanel)sliderPanels.elementAt(2)).getValue(),0,"grid");*/
    }

    public void updateSettings(){
        Vector tempVector = new Vector();
//        if (classArray == null)
        for (int i=0;i<classArray.length;i++){
                if (classArray[i].equals("i")){
                    Integer a = new Integer(((WholeNumberSliderPanel)sliderPanels.elementAt(i)).getValue());
                    tempVector.addElement(a);
                }
                else if (classArray[i].equals("d")){
                    Double b = new Double(((DecimalSliderPanel)sliderPanels.elementAt(i)).getValue());
                    tempVector.addElement(b);
                }
                else if (classArray[i].equals("ds")){
                    Double b = new Double(((DecimalSliderPanel)sliderPanels.elementAt(i)).getValue());
                    tempVector.addElement(b);
                }
                else if (classArray[i].equals("sd")){
                    int tempInt = Integer.parseInt((String)strandDiameterComboBox.getSelectedItem());
                    tempVector.add(new Integer(tempInt));
                }
                else if (classArray[i].equals("p")){
                    if (solid.isSelected())
                        pattern = "solid";
                    else
                        pattern = "grid";
                    tempVector.add(new String(pattern));
                }
                else if (classArray[i].equals("fw")){
                    int tempInt = Integer.parseInt((String)fingerWidthComboBox.getSelectedItem());
                    tempVector.add(new Integer(tempInt));
                }
        }
        if (sets == null)
            sets = new Settings("default");
        switch(bs){
            case 1: sets.setEtchSettings(tempVector); break;
            // added acid texture and acid Rinse settings by Bobh
            case 21: sets.setAcidTextureSettings(tempVector); break;
            case 22: sets.setAcidRinseAcidCleanSettings(tempVector); break;
            case 23: sets.setRinseAcidCleanSettings(tempVector); break;
            // add end Bobh
            case 2: sets.setRinseSettings(tempVector); break;
            case 3: sets.setTextureSettings(tempVector); break;
            case 4: sets.setRinseAcidCleanSettings(tempVector); break;
            case 5: sets.setDiffusionSettings(tempVector); break;
            case 6: sets.setAlScreenSetupSettings(tempVector); break;
            case 7: sets.setAlScreenPrintSettings(tempVector); break;
            case 8: sets.setAlFiringSettings(tempVector); break;
            case 9: sets.setDiffusionOxideRemovalSettings(tempVector); break;
            case 10: sets.setPlasmaEtchSettings(tempVector); break;
            case 11: sets.setTArCoatingSettings(tempVector); break;
            case 12: sets.setSArCoatingSettings(tempVector); break;
            case 13: sets.setSilverScreenSetupSettings(tempVector); break;
            case 14: sets.setSilverScreenPrintSettings(tempVector); break;
            case 15: sets.setSilverFiringSettings(tempVector); break;
            case 26: sets.setDiffusionOxideRemovalSettings(tempVector); break;
            case 27: sets.setPlasmaEtchSettings(tempVector); break;
            case 28: sets.setTArCoatingSettings(tempVector); break;
            case 29: sets.setSArCoatingSettings(tempVector); break;
            case 30: sets.setSilverScreenSetupSettings(tempVector); break;
            case 31: sets.setSilverScreenPrintSettings(tempVector); break;
            case 32: sets.setAlScreenSetupSettings(tempVector); break;
            case 33: sets.setAlScreenPrintSettings(tempVector); break;
            case 34: sets.setCofiringSettings(tempVector); break;
        }
    }

    public void setVisible(boolean b){
        if (b){
        if (sets != null){
            updateSettings();
            settings = sets;
        }
        this.removeAll();
        Vector values = new Vector();
        rangeModels = new Vector();
        sliderPanels = new Vector();
//        String[] classArray = null;
        String[] labelArray = null;
        String[] descArray = null;
        String[] units = null;
        int[] minArray = null;
        int[] maxArray = null;
        switch (bs){
            case 1: values = settings.getEtchSettings();
                    classArray = settings.etchClassArray;
                    labelArray = settings.etchArray;
                    descArray = settings.etchDescArray;
                    units = settings.etchUnitsArray;
                    minArray = settings.etchMinArray;
                    maxArray = settings.etchMaxArray;
                    demoArray = settings.etchDemoArray;
                    buttonName = "Etch";
                    break;

            // added by Bobh
            case 21: values = settings.getAcidTextureSettings();
                    classArray = settings.acidTextureClassArray;
                    labelArray = settings.acidTextureArray;
                    descArray = settings.acidTextureDescArray;
                    units = settings.acidTextureUnitsArray;
                    minArray = settings.acidTextureMinArray;
                    maxArray = settings.acidTextureMaxArray;
                    demoArray = settings.acidTextureDemoArray;
                    buttonName = "Acidic Texture";
                    break;
            case 22: values = settings.getAcidRinseAcidCleanSettings();
                    classArray = settings.acidRinseAcidClassArray;
                    labelArray = settings.acidRinseAcidCleanArray;
                    descArray = settings.acidRinseAcidCleanDescArray;
                    units = settings.acidRinseAcidCleanUnitsArray;
                    minArray = settings.acidRacMinArray;
                    maxArray = settings.acidRacMaxArray;
                    demoArray = settings.acidRacDemoArray;
                    buttonName = "Alkaline Clean/Rinse";
                    break;
            case 23: values = settings.getRinseAcidCleanSettings();
                    classArray = settings.rinseAcidClassArray;
                    labelArray = settings.rinseAcidCleanArray;
                    descArray = settings.rinseAcidCleanDescArray;
                    units = settings.rinseAcidCleanUnitsArray;
                    minArray = settings.racMinArray;
                    maxArray = settings.racMaxArray;
                    demoArray = settings.racDemoArray;
                    buttonName = "Rinse & Acid Clean";
                    break;
                   // end added Bobh

            case 2: values = settings.getRinseSettings();
                    classArray = settings.rinseClassArray;
                    labelArray = settings.rinseArray;
                    descArray = settings.rinseDescArray;
                    units = settings.rinseUnitsArray;
                    minArray = settings.rinseMinArray;
                    maxArray = settings.rinseMaxArray;
                    demoArray = settings.rinseDemoArray;
                    buttonName = "Rinse";
                    break;
            case 3: values = settings.getTextureSettings();
                    classArray = settings.textureClassArray;
                    labelArray = settings.textureArray;
                    descArray = settings.textureDescArray;
                    units = settings.textureUnitsArray;
                    minArray = settings.textureMinArray;
                    maxArray = settings.textureMaxArray;
                    demoArray = settings.textureDemoArray;
                    buttonName = "Texture";
                    break;
            case 4: values = settings.getRinseAcidCleanSettings();
                    classArray = settings.rinseAcidClassArray;
                    labelArray = settings.rinseAcidCleanArray;
                    descArray = settings.rinseAcidCleanDescArray;
                    units = settings.rinseAcidCleanUnitsArray;
                    minArray = settings.racMinArray;
                    maxArray = settings.racMaxArray;
                    demoArray = settings.racDemoArray;
                    buttonName = "Rinse & Acid Clean";
                    break;
            case 5: values = settings.getDiffusionSettings();
                    classArray = settings.diffusionClassArray;
                    labelArray = settings.diffusionArray;
                    descArray = settings.diffusionDescArray;
                    units = settings.diffusionUnitsArray;
                    minArray = settings.diffusionMinArray;
                    maxArray = settings.diffusionMaxArray;
                    demoArray = settings.diffusionDemoArray;
                    buttonName = "Diffusion";
                    break;
            case 6: values = settings.getAlScreenSetupSettings();
                    classArray = settings.alScreenSetupClassArray;
                    labelArray = settings.alScreenSetupArray;
                    descArray = settings.alScreenSetupDescArray;
                    units = settings.alScreenSetupUnitsArray;
                    minArray = settings.alScreenSetupMinArray;
                    maxArray = settings.alScreenSetupMaxArray;
                    demoArray = settings.alScreenSetupDemoArray;
                    buttonName = "Setup Al Screen";
                    break;
            case 7: values = settings.getAlScreenPrintSettings();
                    classArray = settings.alScreenClassArray;
                    labelArray = settings.alScreenPrintArray;
                    descArray = settings.alScreenPrintDescArray;
                    units = settings.alScreenPrintUnitsArray;
                    minArray = settings.alScreenPrintMinArray;
                    maxArray = settings.alScreenPrintMaxArray;
                    demoArray = settings.alScreenDemoArray;
                    buttonName = "Al Screen Print";
                    break;
            case 8: values = settings.getAlFiringSettings();
                    classArray = settings.alFiringClassArray;
                    labelArray = settings.alFiringArray;
                    descArray = settings.alFiringDescArray;
                    units = settings.alFiringUnitsArray;
                    minArray = settings.alFiringMinArray;
                    maxArray = settings.alFiringMaxArray;
                    demoArray = settings.alFiringDemoArray;
                    buttonName = "Al Firing";
                    break;
            case 9: values = settings.getDiffusionOxideRemovalSettings();
                    classArray = settings.dorClassArray;
                    labelArray = settings.diffusionOxideRemovalArray;
                    descArray = settings.diffusionOxideRemovalDescArray;
                    units = settings.diffusionOxideRemovalUnitsArray;
                    minArray = settings.dorMinArray;
                    maxArray = settings.dorMaxArray;
                    demoArray = settings.dorDemoArray;
                    buttonName = "Diffusion Oxide Removal";
                    break;
            case 10: values = settings.getPlasmaEtchSettings();
                    classArray = settings.plasmaEtchClassArray;
                    labelArray = settings.plasmaEtchArray;
                    descArray = settings.plasmaEtchDescArray;
                    units = settings.plasmaEtchUnitsArray;
                    minArray = settings.plasmaEtchMinArray;
                    maxArray = settings.plasmaEtchMaxArray;
                    demoArray = settings.plasmaEtchDemoArray;
                    buttonName = "Plasma Etch";
                    break;
            case 11: values = settings.getTArCoatingSettings();
                    classArray = settings.tarCoatingClassArray;
                    labelArray = settings.tarCoatingArray;
                    descArray = settings.tarCoatingDescArray;
                    units = settings.tarCoatingUnitsArray;
                    minArray = settings.tarCoatingMinArray;
                    maxArray = settings.tarCoatingMaxArray;
                    demoArray = settings.tarCoatingDemoArray;
                    buttonName = "TiO2 AR Coating";
                    break;
            case 12: values = settings.getSArCoatingSettings();
                    classArray = settings.sarCoatingClassArray;
                    labelArray = settings.sarCoatingArray;
                    descArray = settings.sarCoatingDescArray;
                    units = settings.sarCoatingUnitsArray;
                    minArray = settings.sarCoatingMinArray;
                    maxArray = settings.sarCoatingMaxArray;
                    demoArray = settings.sarCoatingDemoArray;
                    buttonName = "SiNx AR Coating";
                    break;
            case 13: values = settings.getSilverScreenSetupSettings();
                    classArray = settings.silverScreenSetupClassArray;
                    labelArray = settings.silverScreenSetupArray;
                    descArray = settings.silverScreenSetupDescArray;
                    units = settings.silverScreenSetupUnitsArray;
                    minArray = settings.silverScreenSetupMinArray;
                    maxArray = settings.silverScreenSetupMaxArray;
                    demoArray = settings.silverScreenSetupDemoArray;
                    buttonName = "Setup Silver Screen";
                    break;
            case 14: values = settings.getSilverScreenPrintSettings();
                    classArray = settings.silverScreenPrintClassArray;
                    labelArray = settings.silverScreenPrintArray;
                    descArray = settings.silverScreenPrintDescArray;
                    units = settings.silverScreenPrintUnitsArray;
                    minArray = settings.silverScreenPrintMinArray;
                    maxArray = settings.silverScreenPrintMaxArray;
                    demoArray = settings.silverScreenPrintDemoArray;
                    buttonName = "Silver Screen Print";
                    break;
            case 15: values = settings.getSilverFiringSettings();
                    classArray = settings.silverFiringClassArray;
                    labelArray = settings.silverFiringArray;
                    descArray = settings.silverFiringDescArray;
                    units = settings.silverFiringUnitsArray;
                    minArray = settings.silverFiringMinArray;
                    maxArray = settings.silverFiringMaxArray;
                    demoArray = settings.silverFiringDemoArray;
                    buttonName = "Silver Firing";
                    break;
            case 26: values = settings.getDiffusionOxideRemovalSettings();
                    classArray = settings.dorClassArray;
                    labelArray = settings.diffusionOxideRemovalArray;
                    descArray = settings.diffusionOxideRemovalDescArray;
                    units = settings.diffusionOxideRemovalUnitsArray;
                    minArray = settings.dorMinArray;
                    maxArray = settings.dorMaxArray;
                    demoArray = settings.dorDemoArray;
                    buttonName = "Diffusion Oxide Removal";
                    break;
            case 27: values = settings.getPlasmaEtchSettings();
                    classArray = settings.plasmaEtchClassArray;
                    labelArray = settings.plasmaEtchArray;
                    descArray = settings.plasmaEtchDescArray;
                    units = settings.plasmaEtchUnitsArray;
                    minArray = settings.plasmaEtchMinArray;
                    maxArray = settings.plasmaEtchMaxArray;
                    demoArray = settings.plasmaEtchDemoArray;
                    buttonName = "Plasma Etch";
                    break;
            case 28: values = settings.getTArCoatingSettings();
                    classArray = settings.tarCoatingClassArray;
                    labelArray = settings.tarCoatingArray;
                    descArray = settings.tarCoatingDescArray;
                    units = settings.tarCoatingUnitsArray;
                    minArray = settings.tarCoatingMinArray;
                    maxArray = settings.tarCoatingMaxArray;
                    demoArray = settings.tarCoatingDemoArray;
                    buttonName = "TiO2 AR Coating";
                    break;
            case 29: values = settings.getSArCoatingSettings();
                    classArray = settings.sarCoatingClassArray;
                    labelArray = settings.sarCoatingArray;
                    descArray = settings.sarCoatingDescArray;
                    units = settings.sarCoatingUnitsArray;
                    minArray = settings.sarCoatingMinArray;
                    maxArray = settings.sarCoatingMaxArray;
                    demoArray = settings.sarCoatingDemoArray;
                    buttonName = "SiNi AR Coating";
                    break;
            case 30: values = settings.getSilverScreenSetupSettings();
                    classArray = settings.silverScreenSetupClassArray;
                    labelArray = settings.silverScreenSetupArray;
                    descArray = settings.silverScreenSetupDescArray;
                    units = settings.silverScreenSetupUnitsArray;
                    minArray = settings.silverScreenSetupMinArray;
                    maxArray = settings.silverScreenSetupMaxArray;
                    demoArray = settings.silverScreenSetupDemoArray;
                    buttonName = "Setup Silver Screen";
                    break;
            case 31: values = settings.getSilverScreenPrintSettings();
                    classArray = settings.silverScreenPrintClassArray;
                    labelArray = settings.silverScreenPrintArray;
                    descArray = settings.silverScreenPrintDescArray;
                    units = settings.silverScreenPrintUnitsArray;
                    minArray = settings.silverScreenPrintMinArray;
                    maxArray = settings.silverScreenPrintMaxArray;
                    demoArray = settings.silverScreenPrintDemoArray;
                    buttonName = "Silver Screen Print";
                    break;
            case 32: values = settings.getAlScreenSetupSettings();
                    classArray = settings.alScreenSetupClassArray;
                    labelArray = settings.alScreenSetupArray;
                    descArray = settings.alScreenSetupDescArray;
                    units = settings.alScreenSetupUnitsArray;
                    minArray = settings.alScreenSetupMinArray;
                    maxArray = settings.alScreenSetupMaxArray;
                    demoArray = settings.alScreenSetupDemoArray;
                    buttonName = "Setup Al Screen";
                    break;
            case 33: values = settings.getAlScreenPrintSettings();
                    classArray = settings.alScreenClassArray;
                    labelArray = settings.alScreenPrintArray;
                    descArray = settings.alScreenPrintDescArray;
                    units = settings.alScreenPrintUnitsArray;
                    minArray = settings.alScreenPrintMinArray;
                    maxArray = settings.alScreenPrintMaxArray;
                    demoArray = settings.alScreenDemoArray;
                    buttonName = "Al Screen Print";
                    break;
            case 34: values = settings.getCofiringSettings();
                    classArray = settings.cofiringClassArray;
                    labelArray = settings.cofiringArray;
                    descArray = settings.cofiringDescArray;
                    units = settings.cofiringUnitsArray;
                    minArray = settings.cofiringMinArray;
                    maxArray = settings.cofiringMaxArray;
                    demoArray = settings.cofiringDemoArray;
                    buttonName = "Co-Fire Metal Contacts";
                    break;
        }

        for (int i=0;i<values.size();i++){
            if (classArray[i].equals("d")){
                RangeModel rm = new RangeModel();
                rm.setRangeProperties(((Double)values.elementAt(i)).doubleValue()*100, 0, minArray[i]*100, maxArray[i]*100, false);
                DecimalSliderPanel dsp = new DecimalSliderPanel(descArray[i], units[i], rm, labelArray[i], 1, 0);
                rangeModels.addElement(rm);
                sliderPanels.addElement(dsp);
                this.add(dsp);
                if (vpl.demo && demoArray[i])
                    dsp.setEnabled(true);
                else if (disabled)
                    dsp.setEnabled(false);
                if (bs == 9){
                    dsp.slider.addChangeListener(this);
                }
            }
            else if (classArray[i].equals("i")){
                DefaultBoundedRangeModel dbrm = new DefaultBoundedRangeModel();
                dbrm.setRangeProperties(((Integer)values.elementAt(i)).intValue(), 0, minArray[i], maxArray[i], false);
                WholeNumberSliderPanel wnsp = new WholeNumberSliderPanel(descArray[i], units[i], dbrm, labelArray[i], 0, 0);
                rangeModels.addElement(dbrm);
                sliderPanels.addElement(wnsp);
                this.add(wnsp);
                if (vpl.demo && demoArray[i])
                    wnsp.setEnabled(true);
                else if (disabled)
                    wnsp.setEnabled(false);
                if (bs == 9){
                    wnsp.slider.addChangeListener(this);
                }
            }
            else if (classArray[i].equals("ds")){
                RangeModel rm = new RangeModel();
                rm.setRangeProperties(((Double)values.elementAt(i)).doubleValue()*100, 0, minArray[i]*10, maxArray[i]*100, false);
                DecimalSliderPanel dsp = new DecimalSliderPanel(descArray[i], units[i], rm, labelArray[i], 1, 0);
                rangeModels.addElement(rm);
                sliderPanels.addElement(dsp);
                this.add(dsp);
                if (disabled)
                    dsp.setEnabled(false);
                if (bs == 9){
                    dsp.slider.addChangeListener(this);
                }
            }
            else if (classArray[i].equals("sd")){
                strandDiameterComboBox.setSelectedItem((String)values.elementAt(i).toString());
    	        JLabel strandDiameterLabel = new JLabel("Diameter");
        	strandDiameterLabel.setFont(new Font("Dialog", Font.BOLD, 10));
	        JLabel strandDiameterUnitsLabel = new JLabel(units[i]);
        	strandDiameterUnitsLabel.setFont(vpl.buttonFont);
	        strandDiameterUnitsLabel.setForeground(vpl.buttonTextColour);
        	JPanel strandDiameterPanel = new JPanel(){
                    public Dimension getMinimumSize() {
        	    	return getPreferredSize();
	            }
        	    public Dimension getPreferredSize() {
	        	return new Dimension(210,
		                     super.getPreferredSize().height);
        	    }
	            public Dimension getMaximumSize() {
		        return getPreferredSize();
        	    }
                };
                strandDiameterPanel.add(strandDiameterLabel);
                strandDiameterPanel.add(strandDiameterComboBox);
                strandDiameterPanel.add(strandDiameterUnitsLabel);
                strandDiameterPanel.setBorder(BorderFactory.createCompoundBorder(
	            BorderFactory.createTitledBorder(labelArray[i]),
	            BorderFactory.createEmptyBorder(2,2,2,2)));

                sliderPanels.addElement(strandDiameterPanel);
                this.add(strandDiameterPanel);
                if (disabled)
                    strandDiameterComboBox.setEnabled(false);
                strandDiameterComboBox.addActionListener(this);
            }
            else if (classArray[i].equals("p")){
                patternGroup = new ButtonGroup();
                solid = new JRadioButton("Solid Pattern", false);
                patternGroup.add(solid);
                grid = new JRadioButton("Grid Pattern (bifacial operation)", false);
                patternGroup.add(grid);
                pattern = (String)values.elementAt(i);
                if (pattern.equals("solid"))
                    solid.setSelected(true);
                else if (pattern.equals("grid"))
                    grid.setSelected(true);

	        JPanel patternButtonPanel = new JPanel();
                patternButtonPanel.setLayout(new GridLayout(2,0));
                patternButtonPanel.setSize(220,220);
        	patternButtonPanel.add(solid);
	        patternButtonPanel.add(grid);
        	patternButtonPanel.setBorder(BorderFactory.createCompoundBorder(
	            BorderFactory.createTitledBorder(labelArray[i]),
        	    BorderFactory.createEmptyBorder(2,2,2,2)));

	        sliderPanels.addElement(patternButtonPanel);
                this.add(patternButtonPanel);
                if (disabled){
                    solid.setEnabled(false);
                    grid.setEnabled(false);
                }
                solid.addActionListener(this);
                grid.addActionListener(this);
            }
            else if (classArray[i].equals("fw")){
                fingerWidthComboBox.setSelectedItem((String)values.elementAt(i).toString());
    	        JLabel fingerWidthLabel = new JLabel("Width");
        	fingerWidthLabel.setFont(new Font("Dialog", Font.BOLD, 10));
	        JLabel fingerWidthUnitsLabel = new JLabel(units[i]);
        	fingerWidthUnitsLabel.setFont(vpl.buttonFont);
	        fingerWidthUnitsLabel.setForeground(vpl.buttonTextColour);
        	JPanel fingerWidthPanel = new JPanel(){
                    public Dimension getMinimumSize() {
        	    	return getPreferredSize();
	            }
        	    public Dimension getPreferredSize() {
	        	return new Dimension(210,
		                     super.getPreferredSize().height);
        	    }
	            public Dimension getMaximumSize() {
		        return getPreferredSize();
        	    }
                };
                fingerWidthPanel.add(fingerWidthLabel);
                fingerWidthPanel.add(fingerWidthComboBox);
                fingerWidthPanel.add(fingerWidthUnitsLabel);
                fingerWidthPanel.setBorder(BorderFactory.createCompoundBorder(
	            BorderFactory.createTitledBorder(labelArray[i]),
	            BorderFactory.createEmptyBorder(2,2,2,2)));

                sliderPanels.addElement(fingerWidthPanel);
                this.add(fingerWidthPanel);
                if (disabled)
                    fingerWidthComboBox.setEnabled(false);
            }

        }
        if (sets == null)
            updateSettings();
        panelHeight = sliderPanels.size()*75 +120;
	setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
        button = new JButton(buttonName);
	add(button);

        setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        setFont(new Font("SansSerif", Font.PLAIN, 10));

        button.addActionListener(this);
        }
    }

}
