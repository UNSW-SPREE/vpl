package vpl;
import java.util.*;
import java.io.*;
import java.text.DateFormat;

/**
 * Title:        Virtual Production Line<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Anna Bruce<p>
 * Company:      UNSW PV Engineering<p>
 * @author Anna Bruce
 * @version 1.0
 */

public class Settings implements Serializable{
    //variables pertaining to VPL settings
    public File userDirectory;

    //assignment Strings
    public String[] assignmentStrings = {"Assignment 1 (Wafer Etch)",
                            "Assignment 2 (Texturing)",
                            "Assignment 3 (Acid Clean)",
                            "Assignment 4 (Phos. Diffusion)",
                            "Assignment 5 (Al Contacts)",
                            "Assignment 6 (Plasma Etch)",
                            "Assignment 7 (Silver Contacts)",
                            "Assignment 8 (TiO2 AR Coating)",
                            "Mid-session Assessment"};

    //variables pertaining to VPL maintenance
    // added acid texturing batches Bob H
    public int ATB;
    //end add Bobh

    //sodium silicate batches through etch solution
    public int SSB;
    //no of cells textured in the texture solution
    public int TSSB;
    //probability of breakage of silver screen
    public int PSB;


    //incoming
    public String incomingType;
    public String incomingQuality;
    public String name;
    public int incomingThickness;
    public double incomingResistivity;
    public String incomingSurfaceFinish;
    public String[] incomingArray = {"Wafer Type", "Grade", "Thickness", "Resistivity", "Surface Finish"};
    public String[] incomingUnitsArray = {"", "", "µm", "\u03A9/cm\u00B3", ""};
    public String[] incomingClassArray = {"s","s","i","d","s"};
    //etch
    public double etchTemp;
    public double etchChemConc;
    public double etchTime;
    public String[] etchArray = {"Etch Temperature", "NaOH Concentration","Etch Time"};
    public String[] etchDescArray = {"Temperature", "Concentration", "Time"};
    public int[] etchMinArray = {0,20,0};
    public int[] etchMaxArray = {90,40,40};
    public String[] etchUnitsArray = {"°C", "%", "mins"};
    public String[] etchClassArray = {"d","d","d"};
    public boolean[] etchDemoArray = {false, false, true};
    //rinse
    public double rinseTime;
    public String[] rinseArray = {"Rinse Time"};
    public String[] rinseDescArray = {"Time"};
    public int[] rinseMinArray = {0};
    public int[] rinseMaxArray = {10};
    public String[] rinseUnitsArray = {"mins"};
    public String[] rinseClassArray = {"d"};
    public boolean[] rinseDemoArray = {false};
    //texture
    public double textureTime;
    public double textureTemp;
    public double textureChemConc;
    public double texturePropanol;
    public int textureEfr;
    public String[] textureArray = {"NaOH Texture Time", "NaOH Texture Temperature",
        "NaOH Concentration", "Propanol", "Exhaust Flow Rate"};
    public String[] textureDescArray = {"Time", "Temperature", "Concentration",
        "Concentration", "Choose 0=nil to 4=high"};
    public int[] textureMinArray = {0,50,1,0,0};
    public int[] textureMaxArray = {30,100,3,10,4};
    public String[] textureUnitsArray = {"mins", "°C", "%", "%", ""};
    public String[] textureClassArray = {"d","d","d","d","i"};
    public boolean[] textureDemoArray = {false, false, false, false, false};

    //acidic texture added by Bobh
    public double acidTextureTime;
    public double acidTextureTemp;
    public double acidTextureHFConc;
    public double acidTextureHCLConc;
    //public double acidTextureCOOHConc;
    public String[] acidTextureArray = {"Belt Speed", "Acid Texture Temperature",
        "HF Concentration", "HNO3 Concentration"};
    public String[] acidTextureDescArray = {"Speed", "Temperature", "Concentration",
        "Concentration"};
    public int[] acidTextureMinArray = {5,5,5,5};
    public int[] acidTextureMaxArray = {3,25,30,60};
    public String[] acidTextureUnitsArray = {"m/min", "°C", "% (v/v)", "% (v/v)"};
    public String[] acidTextureClassArray = {"ds","d","d","d"};
    public boolean[] acidTextureDemoArray = {false, false, false, false};
    

    //acid texture clean

    public int acidRacHfConc;
    public double acidRacHfTime;
    public double acidRacHfRinseTime;
    //public int acidRacHclConc;
    //public double acidRacHclTime;
    //public double acidRacHclRinseTime;
    public String[] acidRinseAcidCleanArray = {"KOH Concentration", "KOH Temeperature", "KOH Rinse Time"};
    public String[] acidRinseAcidCleanDescArray = {"Concentration", "Temeperature", "Time"};
    public int[] acidRacMinArray = {0,0,0};
    public int[] acidRacMaxArray = {5,30,10};
    public String[] acidRinseAcidCleanUnitsArray = {"%", "°C", "mins"};
    public String[] acidRinseAcidClassArray = {"i","d","d"};
    public boolean[] acidRacDemoArray = {false, false, false};

   // add end by Bobh modified by ly

    //rinse & acid clean
    public int racHfConc;
    public double racHfTime;
    public double racHfRinseTime;
    public int racHclConc;
    public double racHclTime;
    public double racHclRinseTime;
    public String[] rinseAcidCleanArray = {"HF Concentration", "HF Time", "HF Rinse Time",
        "HCl Concentration", "HCl Time", "HCl Rinse Time"};
    public String[] rinseAcidCleanDescArray = {"Concentration", "Time", "Time", "Concentration", "Time", "Time"};
    public int[] racMinArray = {0,0,0,0,0,0};
    public int[] racMaxArray = {30,10,10,30,10,10};
    public String[] rinseAcidCleanUnitsArray = {"%", "mins", "mins", "%", "mins", "mins"};
    public String[] rinseAcidClassArray = {"i","d","d","i","d","d"};
    public boolean[] racDemoArray = {false, false, false, false, false, false};
    //diffusion
    public double diffusionBeltSpeed;
    public int diffusionDryingTemp;
    public int diffusionZone1Temp;
    public int diffusionZone2Temp;
    public int diffusionZone3Temp;
    public double diffusionGasFlow;
    public int diffusionDilutionFactor;
    public String[] diffusionArray = {"Belt Speed", "Drying Temperature",
        "Zone 1 Temperature", "Zone 2 Temperature", "Zone 3 Temperature",
        "Nitrogen Gas Flow", "Phosphorous Source Conc"};
    public String[] diffusionDescArray = {"Speed", "Temperature", "Temperature",
        "Temperature", "Temperature", "Flow Rate", "Percent"};
    public int[] diffusionMinArray = {1,100,500,700,500,0,10};
    public int[] diffusionMaxArray = {1,300,800,1000,800,50,100};
    public String[] diffusionUnitsArray = {"cm/sec", "°C", "°C", "°C", "°C",
        "l/sec", "%"};
    public String[] diffusionClassArray = {"ds","i","i","i","i","d","i"};
    public boolean[] diffusionDemoArray = {false, false, false, true, false, false, false};
    //diffusion oxide removal
    public double dorCleanTime;
    public int dorChemConc;
    public double dorRinseTime;
    public String[] diffusionOxideRemovalArray = {"HF Clean Time",
        "HF Acid Concentration", "Rinse Time"};
    public String[] diffusionOxideRemovalDescArray = {"Time", "Concentration", "Time"};
    public int[] dorMinArray = {0,0,0};
    public int[] dorMaxArray = {10,30,10};
    public String[] diffusionOxideRemovalUnitsArray = {"mins", "%", "mins"};
    public String[] dorClassArray = {"d","i","d"};
    public boolean[] dorDemoArray = {false, false, false};
    //plasma etch
    public int plasmaEtchPower;
    public double plasmaEtchTime;
    public String[] plasmaEtchArray = {"Power", "Time"};
    public String[] plasmaEtchDescArray = {"Power", "Time"};
    public String[] plasmaEtchUnitsArray = {"W", "mins"};
    public String[] plasmaEtchClassArray = {"i","d"};
    public int[] plasmaEtchMinArray = {150,2};
    public int[] plasmaEtchMaxArray = {1500,20};
    public boolean[] plasmaEtchDemoArray = {false, true};
    //titanium dioxide arCoating
    public double tarCoatingBeltSpeed;
    public double tarCoatingTemp;
    public int tarCoatingGasFlow;
    public String[] tarCoatingArray = {"Belt Speed", "Temperature", "Titanium-Based Gas Flow"};
    public String[] tarCoatingDescArray = {"Speed", "Temperature", "Choose 1=low to 5=high"};
    public int[] tarCoatingMinArray = {1,0,1};
    public int[] tarCoatingMaxArray = {10,300,5};
    public String[] tarCoatingUnitsArray = {"cm/sec", "°C", "l/sec"};
    public String[] tarCoatingClassArray = {"d","d","i"};
    public boolean[] tarCoatingDemoArray = {true, false, false};
    //silicon nitride arCoating
    public double sarCoatingTime;
    public double sarCoatingTemp;
    public int sarCoatingGasFlow1;
    public int sarCoatingGasFlow2;
    public String[] sarCoatingArray = {"Time", "Temperature", "Silane Gas Flow", "Ammonia Gas Flow"};
    public String[] sarCoatingDescArray = {"Temp", "Temperature", "Choose 1=low to 5=high", "Choose 1=low to 5=high"};
    public int[] sarCoatingMinArray = {1,0,1,1};
    public int[] sarCoatingMaxArray = {30,400,5,5};
    public String[] sarCoatingUnitsArray = {"mins", "°C", "l/sec", "l/sec"};
    public String[] sarCoatingClassArray = {"d","d","i","i"};
    public boolean[] sarCoatingDemoArray = {true, false, false, false};
    //al screen setup
    public int alMeshDensity;
    public double alEmulsionThicknessAbove;
    public double alEmulsionThicknessBelow;
    public int alStrandDiameter;
    public String alPattern;
    public String[] alScreenSetupArray = {"Mesh Density", "Emulsion Thickness Above Mesh",
        "Emulsion Thickness Below Mesh", "Strand Diameter","Pattern"};
    public String[] alScreenSetupDescArray = {"Density", "Thickness",
        "Thickness", "",""};
    public int[] alScreenSetupMinArray = {50,0,0,20,0};
    public int[] alScreenSetupMaxArray = {250,30,30,120,0};
    public String[] alScreenSetupUnitsArray = {"strands/cm", "µm",
        "µm", "µm", ""};
    public String[] alScreenSetupClassArray = {"i","d","d","sd","p"};
    public boolean[] alScreenSetupDemoArray = {false, false, false, false, false};
    //al screen print
    public int alScreenPrintPressure;
    public int alScreenPrintViscosity;
    public int alScreenPrintSpeed;
    public String[] alScreenPrintArray = {"Squeegee Pressure", "Paste Viscosity",
        "Squeegee Speed"};
    public String[] alScreenPrintDescArray = {"Choose 1=low to 10=high", "Choose 1=low to 10=high",
        "Choose 1=low to 10=high"};
    public int[] alScreenPrintMinArray = {1,1,1};
    public int[] alScreenPrintMaxArray = {10,10,10};
    public String[] alScreenPrintUnitsArray = {"", "", ""};
    public String[] alScreenClassArray = {"i","i","i"};
    public boolean[] alScreenDemoArray = {false, false, false};
    //al firing
    public double alFiringBeltSpeed;
    public int alFiringDryingTemp;
    public int alFiringZone1Temp;
    public int alFiringZone2Temp;
    public int alFiringZone3Temp;
    public double alFiringGasFlow;
    public int alFiringOxygen;
    public String[] alFiringArray = {"Belt Speed", "Drying Temperature",
        "Zone 1 Temperature", "Zone 2 Temperature", "Zone 3 Temperature",
        "Nitrogen Gas Flow", "Oxygen Percentage"};
    public String[] alFiringDescArray = {"Speed", "Temperature", "Temperature",
        "Temperature", "Temperature", "Flow Rate", "Percentage"};
    public int[] alFiringMinArray = {1,100,500,700,500,0,0};
    public int[] alFiringMaxArray = {10,300,800,1000,800,20,100};
    public String[] alFiringUnitsArray = {"cm/sec", "°C",
        "°C", "°C", "°C", "l/sec", "%"};
    public String[] alFiringClassArray = {"d","i","i","i","i","d","i"};
    public boolean[] alFiringDemoArray = {false, false, false, true, false, false, false};
    //silver screen setup
    public int silverMeshDensity;
    public double silverEmulsionThicknessAbove;
    public double silverEmulsionThicknessBelow;
    public int silverFingerSpacing;
    public int silverStrandDiameter;
    public int silverFingerWidth;
    public String[] silverScreenSetupArray = {"Mesh Density", "Emulsion Thickness Above Mesh",
        "Emulsion Thickness Below Mesh", "Finger Spacing", "Strand Diameter","Finger Width"};
    public String[] silverScreenSetupDescArray = {"Density", "Thickness", "Thickness","Spacing","",""};
    public int[] silverScreenSetupMinArray = {50,0,0,1,20,50};
    public int[] silverScreenSetupMaxArray = {250,30,30,6,120,250};
    public String[] silverScreenSetupUnitsArray = {"strands/cm", "µm",
        "µm", "mm", "µm", "µm"};
    public String[] silverScreenSetupClassArray = {"i","d","d","i","sd","fw"};
    public boolean[] silverScreenSetupDemoArray = {false, false, false, false, false, false};
    //silver screen print
    public int silverScreenPrintPressure;
    public int silverScreenPrintViscosity;
    public int silverScreenPrintSpeed;
    public String[] silverScreenPrintArray = {"Squeegee Pressure", "Paste Viscosity",
        "Squeegee Speed"};
    public String[] silverScreenPrintDescArray = {"Choose 1=low to 10=high", "Choose 1=low to 10=high",
        "Choose 1=low to 10=high"};
    public int[] silverScreenPrintMinArray = {1,1,1};
    public int[] silverScreenPrintMaxArray = {10,10,10};
    public String[] silverScreenPrintUnitsArray = {"", "", ""};
    public String[] silverScreenPrintClassArray = {"i","i","i"};
    public boolean[] silverScreenPrintDemoArray = {false, false, false};
    //silver firing
    public double silverFiringBeltSpeed;
    public int silverFiringDryingTemp;
    public int silverFiringZone1Temp;
    public int silverFiringZone2Temp;
    public int silverFiringZone3Temp;
    public String[] silverFiringArray = {"Belt Speed", "Drying Temperature",
        "Zone 1 Temperature", "Zone 2 Temperature", "Zone 3 Temperature"};
    public String[] silverFiringDescArray = {"Speed", "Temperature", "Temperature",
        "Temperature", "Temperature"};
    public int[] silverFiringMinArray = {1,100,500,600,500};
    public int[] silverFiringMaxArray = {10,300,800,1000,800};
    public String[] silverFiringUnitsArray = {"cm/sec", "°C",
        "°C", "°C", "°C"};
    public String[] silverFiringClassArray = {"d","i","i","i","i"};
    public boolean[] silverFiringDemoArray = {false, false, false, true, false};
    //cofiring
    public double cofiringBeltSpeed;
    public int cofiringDryingTemp;
    public int cofiringZone1Temp;
    public int cofiringZone2Temp;
    public int cofiringZone3Temp;
    public double cofiringGasFlow;
    public int cofiringOxygen;
    public String[] cofiringArray = {"Belt Speed", "Drying Temperature",
        "Zone 1 Temperature", "Zone 2 Temperature", "Zone 3 Temperature",
        "Nitrogen Gas Flow", "Oxygen Percentage"};
    public String[] cofiringDescArray = {"Speed", "Temperature", "Temperature",
        "Temperature", "Temperature", "Flow Rate", "Percentage"};
    public int[] cofiringMinArray = {1,100,500,700,500,0,0};
    public int[] cofiringMaxArray = {10,300,800,1000,800,20,100};
    public String[] cofiringUnitsArray = {"cm/sec", "°C",
        "°C", "°C", "°C", "l/sec", "%"};
    public String[] cofiringClassArray = {"d","i","i","i","i","d","i"};
    public boolean[] cofiringDemoArray = {false, false, false, false, false, false, false};
    DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
    DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);


    public Settings(String base){
        if (base == "default" || base == null){
            userDirectory = new File(System.getProperty("user.dir")
                + System.getProperty("file.separator")
                + "My Batches");
            // added by Bobh
            ATB = 0;
            SSB = 0;
            TSSB = 0;
            PSB = 0;

            incomingType = "single crystal";
            incomingQuality = "high";
            incomingThickness = 200;
            incomingResistivity = 1;
            incomingSurfaceFinish = "diamond tipped saw";
            etchTemp = 40;
            etchChemConc = 30;
            etchTime = 20;
            rinseTime = 5;
            textureTime = 20;
            textureTemp = 70;
            textureChemConc = 2;
            texturePropanol = 5;
            textureEfr = 2;

            //add in acid texture and acid clean default settings by Bobh and ly
            acidTextureTime = 1.2;
            acidTextureTemp = 10;
            acidTextureHFConc = 20;
            acidTextureHCLConc = 55;
            //acidTextureCOOHConc = 30;

            acidRacHfConc = 3;
            acidRacHfTime = 3;
            acidRacHfRinseTime = 3;
            // end add by Bobh

            racHfConc = 10;
            racHfTime = 5;
            racHfRinseTime = 5;
            racHclConc = 10;
            racHclTime = 4;
            racHclRinseTime = 3;
            diffusionBeltSpeed = 0.5;
            diffusionDryingTemp = 200;
            diffusionZone1Temp = 600;
            diffusionZone2Temp = 1000;
            diffusionZone3Temp = 700;
            diffusionGasFlow = 25;
            diffusionDilutionFactor = 100;
            dorCleanTime = 5;
            dorChemConc = 10;
            dorRinseTime = 5;
            plasmaEtchPower = 700;
            plasmaEtchTime = 10;
            tarCoatingBeltSpeed = 5;
            tarCoatingTemp = 150;
            tarCoatingGasFlow = 2;
            sarCoatingTime = 15;
            sarCoatingTemp = 150;
            sarCoatingGasFlow1 = 2;
            sarCoatingGasFlow2 = 2;
            alMeshDensity = 100;
            alEmulsionThicknessAbove = 15;
            alEmulsionThicknessBelow = 15;
            alStrandDiameter = 50;
            alPattern = "solid";
            alScreenPrintPressure = 5;
            alScreenPrintViscosity = 5;
            alScreenPrintSpeed = 5;
            alFiringBeltSpeed = 5;
            alFiringDryingTemp = 250;
            alFiringZone1Temp = 600;
            alFiringZone2Temp = 800;
            alFiringZone3Temp = 800;
            alFiringGasFlow = 10;
            alFiringOxygen = 21;
            silverMeshDensity = 100;
            silverEmulsionThicknessAbove = 20;
            silverEmulsionThicknessBelow = 20;
            silverFingerSpacing = 2;
            silverStrandDiameter = 80;
            silverFingerWidth = 200;
            silverScreenPrintPressure = 5;
            silverScreenPrintViscosity = 5;
            silverScreenPrintSpeed = 5;
            silverFiringBeltSpeed = 5;
            silverFiringDryingTemp = 250;
            silverFiringZone1Temp = 650;
            silverFiringZone2Temp = 850;
            silverFiringZone3Temp = 650;
            cofiringBeltSpeed = 5;
            cofiringDryingTemp = 250;
            cofiringZone1Temp = 600;
            cofiringZone2Temp = 800;
            cofiringZone3Temp = 800;
            cofiringGasFlow = 10;
            cofiringOxygen = 21;
        }
        else if (base == "demo"){
            userDirectory = new File(System.getProperty("user.dir")
                + System.getProperty("file.separator")
                + "My Batches");
            // added by Bobh
            ATB = 0;
            // end add by Bobh
            SSB = 0;
            TSSB = 0;
            PSB = 0;

            incomingType = "multicrystalline";
            incomingQuality = "high";
            incomingThickness = 200;
            incomingResistivity = 1;
            incomingSurfaceFinish = "wire sawn";
            etchTemp = 71.13;
            etchChemConc = 30.11;
            etchTime = 3;

            rinseTime = 5;
            //
             //add in and use acid texture default settings as demo settings
            acidTextureTime = 1.2;
            acidTextureTemp = 15;
            acidTextureHFConc = 40;
            acidTextureHCLConc = 30;
            //acidTextureCOOHConc = 30;

            //add in and use clean and rinse default settings as demo settings
            acidRacHfConc = 3;
            acidRacHfTime = 3;
            acidRacHfRinseTime = 3;
            // end add by Bobh

            textureTime = 20;
            textureTemp = 70;
            textureChemConc = 2;
            texturePropanol = 5;
            textureEfr = 2;
            racHfConc = 10;
            racHfTime = 5;
            racHfRinseTime = 5;
            racHclConc = 10;
            racHclTime = 4.95;
            racHclRinseTime = 5.54;
            diffusionBeltSpeed = 0.25;
            diffusionDryingTemp = 300;
            diffusionZone1Temp = 600;
            diffusionZone2Temp = 921;
            diffusionZone3Temp = 700;
            diffusionGasFlow = 25;
            diffusionDilutionFactor = 100;
            dorCleanTime = 5;
            dorChemConc = 10;
            dorRinseTime = 5;
            plasmaEtchPower = 433;
            plasmaEtchTime = 10;
            tarCoatingBeltSpeed = 3.52;
            tarCoatingTemp = 200;
            tarCoatingGasFlow = 4;
            sarCoatingTime = 15;
            sarCoatingTemp = 150;
            sarCoatingGasFlow1 = 2;
            sarCoatingGasFlow2 = 2;
            alMeshDensity = 100;
            alEmulsionThicknessAbove = 2.9;
            alEmulsionThicknessBelow = 30;
            alStrandDiameter = 30;
            alPattern = "solid";
            alScreenPrintPressure = 5;
            alScreenPrintViscosity = 5;
            alScreenPrintSpeed = 4;
            alFiringBeltSpeed = 8.98;
            alFiringDryingTemp = 300;
            alFiringZone1Temp = 600;
            alFiringZone2Temp = 840;
            alFiringZone3Temp = 800;
            alFiringGasFlow = 10.01;
            alFiringOxygen = 21;
            silverMeshDensity = 100;
            silverEmulsionThicknessAbove = 0.81;
            silverEmulsionThicknessBelow = 22.1;
            silverFingerSpacing = 4;
            silverStrandDiameter = 30;
            silverFingerWidth = 150;
            silverScreenPrintPressure = 6;
            silverScreenPrintViscosity = 5;
            silverScreenPrintSpeed = 3;
            silverFiringBeltSpeed = 1.77;
            silverFiringDryingTemp = 289;
            silverFiringZone1Temp = 652;
            silverFiringZone2Temp = 725;
            silverFiringZone3Temp = 650;
            cofiringBeltSpeed = 5;
            cofiringDryingTemp = 250;
            cofiringZone1Temp = 600;
            cofiringZone2Temp = 800;
            cofiringZone3Temp = 800;
            cofiringGasFlow = 10;
            cofiringOxygen = 21;
        }
        else if (base == "previous"){
        }
        else if (base == "preferred"){
        }
    }


    public void setIncomingSettings(String incomingType, String incomingQuality, int incomingThickness, double incomingResistivity,
        String incomingSurfaceFinish){
        this.incomingType = incomingType;
        this.incomingQuality = incomingQuality;
        this.incomingThickness = incomingThickness;
        this.incomingResistivity = incomingResistivity;
        this.incomingSurfaceFinish = incomingSurfaceFinish;
    }

    public void setIncomingSettings(Object[] values){
        String ity = (String)values[0];
        String iq = (String)values[1];
        Integer it = (Integer)values[2];
        Double ir = (Double)values[3];
        String isf = (String)values[4];
        this.incomingType = ity;
        this.incomingQuality = iq;
        this.incomingThickness = it.intValue();
        this.incomingResistivity = ir.doubleValue();
        this.incomingSurfaceFinish = isf;
    }

    public Vector getIncomingSettings(){
        Vector incomingVector = new Vector();
        incomingVector.add(new String(incomingType));
        incomingVector.add(new String(incomingQuality));
        incomingVector.add(new Integer(incomingThickness));
        incomingVector.add(new Double(incomingResistivity));
        incomingVector.add(new String(incomingSurfaceFinish));
        return incomingVector;
    }

    public void setEtchSettings(Vector values){
        Double et = (Double)values.elementAt(0);
        Double ecc = (Double)values.elementAt(1);
        Double etm = (Double)values.elementAt(2);
        this.etchTemp = et.doubleValue();
        this.etchChemConc = ecc.doubleValue();
        this.etchTime = etm.doubleValue();
    }

    public Vector getEtchSettings(){
        Vector etchVector = new Vector();
        etchVector.add(new Double(etchTemp));
        etchVector.add(new Double(etchChemConc));
        etchVector.add(new Double(etchTime));
        return etchVector;
    }


    public void setRinseSettings(Vector values){
        Double rt = (Double)values.elementAt(0);
        this.rinseTime = rt.doubleValue();
    }

    public Vector getRinseSettings(){
        Vector rinseVector = new Vector();
        rinseVector.add(new Double(rinseTime));
        return rinseVector;
    }


     public void setTextureSettings(Vector values){
            Double tt = (Double)values.elementAt(0);
            Double ttm = (Double)values.elementAt(1);
            Double tcc = (Double)values.elementAt(2);
            Double tp = (Double)values.elementAt(3);
            Integer te = (Integer)values.elementAt(4);
            this.textureTime = tt.doubleValue();
            this.textureTemp = ttm.doubleValue();
            this.textureChemConc = tcc.doubleValue();
            this.texturePropanol = tp.doubleValue();
            this.textureEfr = te.intValue();
    }

    public Vector getTextureSettings(){
        Vector textureVector = new Vector();
        textureVector.add(new Double(textureTime));
        textureVector.add(new Double(textureTemp));
        textureVector.add(new Double(textureChemConc));
        textureVector.add(new Double(texturePropanol));
        textureVector.add(new Integer(textureEfr));
        return textureVector;
    }

    // add AcidTextureSettings set and get methods here BobH
       public void setAcidTextureSettings(Vector values){
            Double tt = (Double)values.elementAt(0);
            Double ttm = (Double)values.elementAt(1);
            Double thfcc = (Double)values.elementAt(2);
            Double thclcc = (Double)values.elementAt(3);
            //Double tcoohcc = (Double)values.elementAt(4);
            this.acidTextureTime = tt.doubleValue();
            this.acidTextureTemp = ttm.doubleValue();
            this.acidTextureHFConc = thfcc.doubleValue();
            this.acidTextureHCLConc = thclcc.doubleValue();
            //this.acidTextureCOOHConc = tcoohcc.doubleValue();
    }


        public Vector getAcidTextureSettings(){
        Vector acidTextureVector = new Vector();
        acidTextureVector.add(new Double(acidTextureTime));
        acidTextureVector.add(new Double(acidTextureTemp));
        acidTextureVector.add(new Double(acidTextureHFConc));
        acidTextureVector.add(new Double(acidTextureHCLConc));
        //acidTextureVector.add(new Double(acidTextureCOOHConc));
        return acidTextureVector;
    }

        //add acid clean get and set method BobH

    public void setAcidRinseAcidCleanSettings(Vector values){
            Integer ahfc = (Integer)values.elementAt(0);
            Double ahft = (Double)values.elementAt(1);
            Double ahfrt = (Double)values.elementAt(2);
            this.acidRacHfConc = ahfc.intValue();
            this.acidRacHfTime = ahft.doubleValue();
            this.acidRacHfRinseTime = ahfrt.doubleValue();
    }

    public Vector getAcidRinseAcidCleanSettings(){
        Vector acidRinseAcidCleanVector = new Vector();
        acidRinseAcidCleanVector.add(new Integer(acidRacHfConc));
        acidRinseAcidCleanVector.add(new Double(acidRacHfTime));
        acidRinseAcidCleanVector.add(new Double(acidRacHfRinseTime));
        return acidRinseAcidCleanVector;
    }



    public void setRinseAcidCleanSettings(Vector values){
            Integer hfc = (Integer)values.elementAt(0);
            Double hft = (Double)values.elementAt(1);
            Double hfrt = (Double)values.elementAt(2);
            Integer hclc = (Integer)values.elementAt(3);
            Double hclt = (Double)values.elementAt(4);
            Double hclrt = (Double)values.elementAt(5);
            this.racHfConc = hfc.intValue();
            this.racHfTime = hft.doubleValue();
            this.racHfRinseTime = hfrt.doubleValue();
            this.racHclConc = hclc.intValue();
            this.racHclTime = hclt.doubleValue();
            this.racHclRinseTime = hclrt.doubleValue();
    }

    public Vector getRinseAcidCleanSettings(){
        Vector rinseAcidCleanVector = new Vector();
        rinseAcidCleanVector.add(new Integer(racHfConc));
        rinseAcidCleanVector.add(new Double(racHfTime));
        rinseAcidCleanVector.add(new Double(racHfRinseTime));
        rinseAcidCleanVector.add(new Integer(racHclConc));
        rinseAcidCleanVector.add(new Double(racHclTime));
        rinseAcidCleanVector.add(new Double(racHclRinseTime));
        return rinseAcidCleanVector;
    }

    public void setDiffusionSettings(Vector values){
            Double dbs = (Double)values.elementAt(0);
            Integer ddt = (Integer)values.elementAt(1);
            Integer dz1t = (Integer)values.elementAt(2);
            Integer dz2t = (Integer)values.elementAt(3);
            Integer dz3t = (Integer)values.elementAt(4);
            Double dgf = (Double)values.elementAt(5);
            Integer ddf = (Integer)values.elementAt(6);
            this.diffusionBeltSpeed = dbs.doubleValue();
            this.diffusionDryingTemp = ddt.intValue();
            this.diffusionZone1Temp = dz1t.intValue();
            this.diffusionZone2Temp = dz2t.intValue();
            this.diffusionZone3Temp = dz3t.intValue();
            this.diffusionGasFlow = dgf.doubleValue();
            this.diffusionDilutionFactor = ddf.intValue();
    }

    public Vector getDiffusionSettings(){
        Vector diffusionVector = new Vector();
        diffusionVector.add(new Double(diffusionBeltSpeed));
        diffusionVector.add(new Integer(diffusionDryingTemp));
        diffusionVector.add(new Integer(diffusionZone1Temp));
        diffusionVector.add(new Integer(diffusionZone2Temp));
        diffusionVector.add(new Integer(diffusionZone3Temp));
        diffusionVector.add(new Double(diffusionGasFlow));
        diffusionVector.add(new Integer(diffusionDilutionFactor));
        return diffusionVector;
    }

    public void setDiffusionOxideRemovalSettings(Vector values){
            Double ct = (Double)values.elementAt(0);
            Integer cc = (Integer)values.elementAt(1);
            Double rt = (Double)values.elementAt(2);
            this.dorCleanTime = ct.doubleValue();
            this.dorChemConc = cc.intValue();
            this.dorRinseTime = rt.doubleValue();
    }

    public Vector getDiffusionOxideRemovalSettings(){
        Vector diffusionOxideRemovalVector = new Vector();
        diffusionOxideRemovalVector.add(new Double(dorCleanTime));
        diffusionOxideRemovalVector.add(new Integer(dorChemConc));
        diffusionOxideRemovalVector.add(new Double(dorRinseTime));
        return diffusionOxideRemovalVector;
    }

    public void setPlasmaEtchSettings(Vector values){
            Integer pep = (Integer)values.elementAt(0);
            Double pet = (Double)values.elementAt(1);
            this.plasmaEtchPower = pep.intValue();
            this.plasmaEtchTime = pet.doubleValue();
    }

    public Vector getPlasmaEtchSettings(){
        Vector plasmaEtchVector = new Vector();
        plasmaEtchVector.add(new Integer(plasmaEtchPower));
        plasmaEtchVector.add(new Double(plasmaEtchTime));
        return plasmaEtchVector;
    }

    public void setTArCoatingSettings(Vector values){
            Double tarbs = (Double)values.elementAt(0);
            Double tart = (Double)values.elementAt(1);
            Integer targf = (Integer)values.elementAt(2);
            this.tarCoatingBeltSpeed = tarbs.doubleValue();
            this.tarCoatingTemp = tart.doubleValue();
            this.tarCoatingGasFlow = targf.intValue();
    }

    public Vector getTArCoatingSettings(){
        Vector tarCoatingVector = new Vector();
        tarCoatingVector.add(new Double(tarCoatingBeltSpeed));
        tarCoatingVector.add(new Double(tarCoatingTemp));
        tarCoatingVector.add(new Integer(tarCoatingGasFlow));
        return tarCoatingVector;
    }

    public void setSArCoatingSettings(Vector values){
            Double sartime = (Double)values.elementAt(0);
            Double sart = (Double)values.elementAt(1);
            Integer sargf1 = (Integer)values.elementAt(2);
            Integer sargf2 = (Integer)values.elementAt(3);
            this.sarCoatingTime = sartime.doubleValue();
            this.sarCoatingTemp = sart.doubleValue();
            this.sarCoatingGasFlow1 = sargf1.intValue();
            this.sarCoatingGasFlow2 = sargf2.intValue();
    }

    public Vector getSArCoatingSettings(){
        Vector sarCoatingVector = new Vector();
        sarCoatingVector.add(new Double(sarCoatingTime));
        sarCoatingVector.add(new Double(sarCoatingTemp));
        sarCoatingVector.add(new Integer(sarCoatingGasFlow1));
        sarCoatingVector.add(new Integer(sarCoatingGasFlow2));
        return sarCoatingVector;
    }

    public void setAlScreenSetupSettings(Vector values){
            Integer amd = (Integer)values.elementAt(0);
            Double aeta = (Double)values.elementAt(1);
            Double aetb = (Double)values.elementAt(2);
            Integer asd = (Integer)values.elementAt(3);
            String ap = (String)values.elementAt(4);
            this.alMeshDensity = amd.intValue();
            this.alEmulsionThicknessAbove = aeta.doubleValue();
            this.alEmulsionThicknessBelow = aetb.doubleValue();
            this.alStrandDiameter = asd.intValue();
            this.alPattern = ap;
    }

    public Vector getAlScreenSetupSettings(){
        Vector alScreenSetupVector = new Vector();
        alScreenSetupVector.add(new Integer(alMeshDensity));
        alScreenSetupVector.add(new Double(alEmulsionThicknessAbove));
        alScreenSetupVector.add(new Double(alEmulsionThicknessBelow));
        alScreenSetupVector.add(new Integer(alStrandDiameter));
        alScreenSetupVector.add(new String(alPattern));
        return alScreenSetupVector;
    }

    public void setAlScreenPrintSettings(Vector values){
        Integer aspp = (Integer)values.elementAt(0);
        Integer aspv = (Integer)values.elementAt(1);
        Integer asps = (Integer)values.elementAt(2);
        this.alScreenPrintPressure = aspp.intValue();
        this.alScreenPrintViscosity = aspv.intValue();
        this.alScreenPrintSpeed = asps.intValue();
    }

    public Vector getAlScreenPrintSettings(){
        Vector alScreenPrintVector = new Vector();
        alScreenPrintVector.add(new Integer(alScreenPrintPressure));
        alScreenPrintVector.add(new Integer(alScreenPrintViscosity));
        alScreenPrintVector.add(new Integer(alScreenPrintSpeed));
        return alScreenPrintVector;
    }

    public void setAlFiringSettings(Vector values){
            Double afbs = (Double)values.elementAt(0);
            Integer afdt = (Integer)values.elementAt(1);
            Integer afz1t = (Integer)values.elementAt(2);
            Integer afz2t = (Integer)values.elementAt(3);
            Integer afz3t = (Integer)values.elementAt(4);
            Double afgf = (Double)values.elementAt(5);
            Integer afo = (Integer)values.elementAt(6);
            this.alFiringBeltSpeed = afbs.doubleValue();
            this.alFiringDryingTemp = afdt.intValue();
            this.alFiringZone1Temp = afz1t.intValue();
            this.alFiringZone2Temp = afz2t.intValue();
            this.alFiringZone3Temp = afz3t.intValue();
            this.alFiringGasFlow = afgf.doubleValue();
            this.alFiringOxygen = afo.intValue();
    }

    public Vector getAlFiringSettings(){
        Vector alFiringVector = new Vector();
        alFiringVector.add(new Double(alFiringBeltSpeed));
        alFiringVector.add(new Integer(alFiringDryingTemp));
        alFiringVector.add(new Integer(alFiringZone1Temp));
        alFiringVector.add(new Integer(alFiringZone2Temp));
        alFiringVector.add(new Integer(alFiringZone3Temp));
        alFiringVector.add(new Double(alFiringGasFlow));
        alFiringVector.add(new Integer(alFiringOxygen));
        return alFiringVector;
    }

    public void setSilverScreenSetupSettings(Vector values){
            Integer smd = (Integer)values.elementAt(0);
            Double seta = (Double)values.elementAt(1);
            Double setb = (Double)values.elementAt(2);
            Integer sfs = (Integer)values.elementAt(3);
            Integer ssd = (Integer)values.elementAt(4);
            Integer sfw = (Integer)values.elementAt(5);
            this.silverMeshDensity = smd.intValue();
            this.silverEmulsionThicknessAbove = seta.doubleValue();
            this.silverEmulsionThicknessBelow = setb.doubleValue();
            this.silverFingerSpacing = sfs.intValue();
            this.silverStrandDiameter = ssd.intValue();
            this.silverFingerWidth = sfw.intValue();
    }

    public Vector getSilverScreenSetupSettings(){
        Vector silverScreenSetupVector = new Vector();
        silverScreenSetupVector.add(new Integer(silverMeshDensity));
        silverScreenSetupVector.add(new Double(silverEmulsionThicknessAbove));
        silverScreenSetupVector.add(new Double(silverEmulsionThicknessBelow));
        silverScreenSetupVector.add(new Integer(silverFingerSpacing));
        silverScreenSetupVector.add(new Integer(silverStrandDiameter));
        silverScreenSetupVector.add(new Integer(silverFingerWidth));
        return silverScreenSetupVector;
    }

    public void setSilverScreenPrintSettings(Vector values){
        Integer sspp = (Integer)values.elementAt(0);
        Integer sspv = (Integer)values.elementAt(1);
        Integer ssps = (Integer)values.elementAt(2);
        this.silverScreenPrintPressure = sspp.intValue();
        this.silverScreenPrintViscosity = sspv.intValue();
        this.silverScreenPrintSpeed = ssps.intValue();
    }

    public Vector getSilverScreenPrintSettings(){
        Vector silverScreenPrintVector = new Vector();
        silverScreenPrintVector.add(new Integer(silverScreenPrintPressure));
        silverScreenPrintVector.add(new Integer(silverScreenPrintViscosity));
        silverScreenPrintVector.add(new Integer(silverScreenPrintSpeed));
        return silverScreenPrintVector;
    }

    public void setSilverFiringSettings(Vector values){
            Double sfbs = (Double)values.elementAt(0);
            Integer sfdt = (Integer)values.elementAt(1);
            Integer sfz1t = (Integer)values.elementAt(2);
            Integer sfz2t = (Integer)values.elementAt(3);
            Integer sfz3t = (Integer)values.elementAt(4);
            this.silverFiringBeltSpeed = sfbs.doubleValue();
            this.silverFiringDryingTemp = sfdt.intValue();
            this.silverFiringZone1Temp = sfz1t.intValue();
            this.silverFiringZone2Temp = sfz2t.intValue();
            this.silverFiringZone3Temp = sfz3t.intValue();
    }

    public Vector getSilverFiringSettings(){
        Vector silverFiringVector = new Vector();
        silverFiringVector.add(new Double(silverFiringBeltSpeed));
        silverFiringVector.add(new Integer(silverFiringDryingTemp));
        silverFiringVector.add(new Integer(silverFiringZone1Temp));
        silverFiringVector.add(new Integer(silverFiringZone2Temp));
        silverFiringVector.add(new Integer(silverFiringZone3Temp));
        return silverFiringVector;
    }

    public void setCofiringSettings(Vector values){
            Double afbs = (Double)values.elementAt(0);
            Integer afdt = (Integer)values.elementAt(1);
            Integer afz1t = (Integer)values.elementAt(2);
            Integer afz2t = (Integer)values.elementAt(3);
            Integer afz3t = (Integer)values.elementAt(4);
            Double afgf = (Double)values.elementAt(5);
            Integer afo = (Integer)values.elementAt(6);
            this.cofiringBeltSpeed = afbs.doubleValue();
            this.cofiringDryingTemp = afdt.intValue();
            this.cofiringZone1Temp = afz1t.intValue();
            this.cofiringZone2Temp = afz2t.intValue();
            this.cofiringZone3Temp = afz3t.intValue();
            this.cofiringGasFlow = afgf.doubleValue();
            this.cofiringOxygen = afo.intValue();
    }

    public Vector getCofiringSettings(){
        Vector cofiringVector = new Vector();
        cofiringVector.add(new Double(cofiringBeltSpeed));
        cofiringVector.add(new Integer(cofiringDryingTemp));
        cofiringVector.add(new Integer(cofiringZone1Temp));
        cofiringVector.add(new Integer(cofiringZone2Temp));
        cofiringVector.add(new Integer(cofiringZone3Temp));
        cofiringVector.add(new Double(cofiringGasFlow));
        cofiringVector.add(new Integer(cofiringOxygen));
        return cofiringVector;
    }
}
