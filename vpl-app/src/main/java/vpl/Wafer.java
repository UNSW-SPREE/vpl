package vpl;
import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.lang.Double;
import java.lang.Math.*;
import java.text.NumberFormat;
import java.awt.geom.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Title:        Virtual Production Line<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Anna Bruce<p>
 * Company:      UNSW PV Engineering<p>g
 * @author Anna Bruce
 * @version 1.0
 */

class Wafer implements Serializable{
    public int cellIndex;
    public String cellName;
    public String type;
    public String quality;

    public double maxpower = 0;
    public double voc = 0;
    public double isc = 0;

    // ----------Parameters required by PC1D-------------- //
    // A = area (cm2)
    public double A;
    public String Au = "cm\u00B2";
    // BBR = broad band reflectance (%)
    public double BBR;
    public String BBRu = "%";
    // IT = inner layer thickness in nm.
    public double IT;
    public String ITu = "nm";
    // RS = rear surface internal optical reflectance (%)
    public double RS;
    public String RSu = "%";
    // RE = emitter resistance - internal (ohm)
    public double RE;
    public String REu = "\u03A9";
    // RB = base resistance - internal (ohm)
    public double RB;
    public String RBu = "\u03A9";
    // RSH = shunt resistance (seimens)
    public double RSH;
    public String RSHu = "seimens";
    // SD = shunt diode current in mA.
    public double SD;
    public String SDu = "mA";
    // T1 = region 1 thickness in µm.
    public double T1;
    public String T1u = "µm";
    // BD = region 1 background doping resistivity in ohmcm.
    public double BD;
    public String BDu = "\u03A9cm";
    // PD1 = peak doping (1) in cm-3 ** could be phosphorous doping surface ?? **
    public double PD1;
//    public String PD1u = "<p>Employee N<sup>o</sup></p>";
    public String PD1u = "cm-\u00B3";
    // JD1 = junstion depth (1) in µm.
    public double JD1;
    public String JD1u = "µm";
    // FSRV = front surface recombination velocity in cm/s.
    public double FSRV;
    public String FSRVu = "cm/s";
    // T2 = region 2 thickness in µm.
    public double T2;
    public String T2u = "µm";
    // BR = bulk recombination in µsec (minority carrier lifetime)
    public double BR;
    public String BRu = "µs";
    // JD2 = junction depth (2) in µm.
    public double JD2;
    public String JD2u = "µm";
    // PD2 = peak doping of back surface
    public double PD2;
    public String PD2u = "cm-\u00B3";
    // RI = refractive index
    public double RI;
    public String RIu = "%";

    transient Vector wavelengthData = new Vector();
    transient Vector reflectionData = new Vector();

    // ----------Parameters to be returned by tests-------------- //
//    public double resistivity;
//    public String resistivityu = "ohmcm-3";
//    public double reflection;
        double Voc1 = 0.0;
        double Voc2 = 0.0;
        double Voc3 = 0.0;

    // -----------intermediate variables-----------------------//
    // SW = starting wafer
    // starting wafer thickness in m, SWTu is in µm.
    private double SWT;
    private String SWTu = "µm";
    // wire damage
    private double SWWD;
    private String SWWDu = "µm";

    // E = etch
    //etch temp
    private double ET;
    //etch chem conc
    private double EC;
    //etch time
    private double Et;
    //sodium silicate batches
    private int SSB;
    //etch removal
    private double ER;

    // add start here Bobh
    // acid texture = AT
    //acid texture temperature
    private double ATT;
    //acid texture time 
    private double ATt;
    //acid texture HF Concentration Ratio
    private double ATHFC;
    //acid texture HCL Concentration Ratio
    private double ATHNOC;
    //acid texture COOH Concentration Ratio
    private double ATCOOHC;
    //acid texture batches
    private int ATB = 0;
    //acid texture etch removal
    private double ATER;
    //acid texture broad band reflectance element 1
    //private double acidTextureCV = 0.0;
    //private double acidTextureBR1;
    
    //acid texture probability of cell breakage (acid texture)
   //private double acidTexturePi  = 0.0;
    //acid texture boolean cell broken - true/false
    //private boolean acidTextureCellBroken;
    
     // Acid Texture rinse/acid clean
    //acid texture rac pre acid rinse time
    private double ATARt1;
    //acid texture rac HCL bath time
    private double AThclt;
    //acid texture rac HF bath time
    private double AThft;
    //acid texture rac HCl conc
    private int ATAHCL;
    //acid texture rac HF conc
    private int ATAHF;
    //acid texture rac post acid rinse time
    private double ATARt2;
    //acid texutre model reflectance
    private double ATReflection;
        // add end here Bobh
    
    // T = texture
    //testure sodium silicate batches
    private int TSSB = 0;
    //texture time
    private double Tt;
    //texture temp
    private double TT;
    //texture NaOH conc
    private double TC1;
    //texture isopropanol conc
    private double TC2;
    //texture exhaust flow rate
    private double TEFR;
    //pyramid size
    private double PS;
    //pyramid growth
    private double PG;
    //coverage (of pyramids)
    private double COV;
    //broad band reflectance element 1
    private double CV = 0.0;
    private double BR1;
    //probability of cell breakage (texture)
    private double Pi  = 0.0;
    //boolean cell broken - true/false
    public boolean cellBroken;

    // A = rinse/acid clean
    //rac pre acid rinse
    private double ARt1;
    //rac acid bath time
    private double hclt;
    //rac acid bath time
    private double hft;
    //rac HCl conc
    private int AHCL;
    //rac HF conc
    private int AHF;
    //rac post acid rinse
    private double ARt2;

    // D = diffusion
    //diffusion belt speed
    private double DBS;
    //diffusion drying temp
    private double DT1;
    //diffusion temp zone 2
    private double DT2;
    //diffusion dilution factor
    private double DDF;
    //time in zone 2 of diffusion furnace
    private double Dt2;
    // drying diffusion source
    private double DDS;

    // PE = plasma etch
    //plasma etch power
    private double PEP;
    //plasma etch time
    private double PEt;
    // shunt resistance (inverse of RSH)
    public double shuntResistance;
    public String shuntResistanceu = "\u03A9";

    // AR = anti-reflection coating
    //TiO2 Anti-reflection Coating belt speed
    private double ARBS;
    //Anti-reflection Coating temperature
    private double ART;
    //Anti-reflection Coating gas flow rates
    private double ARGF;
    private double ARGF2;
    //Anti-reflection Coating hardness
    private double ARH;
    //SiNi Anti-reflection Coating Time
    private double ARTM;

    //Al = aluminium
    //aluminium mesh density
    private int ALMD;
    //aluminium emulsion thickness above
    private double AETA;
    //aluminium emulsion thickness below
    private double AETB;
    //aluminium strand diameter
    private int ALSD;
    //(AEA is the Al thickness reduction factor for too thick emulsion above)
    private double AEA;

    //AlScreenPrintPressure
    private int ALPP;
    //AlScreenPrintViscosity
    private int ALV;
    //AlScreenPrintSpeed
    private int ALPS;
    //(ALS is the Al thickness reduction factor for wrong screen density and high viscosity paste)
    private double ALS;
    //(APP is the Al thickness reduction factor for excessive or too low squeegee pressure)
    private double APP;
    //(APS is the Al thickness reduction factor resulting from high print speeds)
    private double APS;
    //Al thickness ALT
    private double ALT;

    //AlFiringBeltSpeed
    private double AFBS;
    //AlFiringDryingTemp
    private int AFDT;
    //AlFiringZone2Temp
    private int AFZ2;
    //AlFiringGasFlow (Nitrogen)
    private double AFGF;
    //AlFiringOxygen
    private int AFOP;
    //(PBAG is probability of breakage during Ag printing)
    private double PBAG;
    //(DOSET is the thermal dose during drying such that if too small,
    //explosive drying during firing leads to increased PBAG)
    private double DOSET;
    private double DOSEO;
    //(DOSEFZ is the thermal dose in the firing zone)
    private double DOSEFZ;
    //(PBAL is the probability of breakage in % during the Al firing process
    //as a function of belt speed and temperature)
    private double PBAL;

    private boolean shuntError;

    // S = silver
    //silver mesh density
    private double SMD;
    //silver emulsion thickness above
    private double SETA;
    //silver emulsion thickness below
    private double SETB;
    //silver strand diameter
    private double SSD;
    //silver finger spacing
    private int SFS;
    //silver mesh density (strands/cm)
    private double silverMeshDensity;

    //silver squeegee pressure
    private double SSP;
    //silver paste viscosity
    private double SPV;
    //probability of cell breakage (silver screen print)
    private double Ps  = 0.0;

    //silver firing time
    private double SFt;
    //silver firing temp (zone 2)
    private int SFT2;
    //metal depth
    private double MD;
    // metal width
    private double MW;
    // Rhos = sheet resistivity in ohmcm-2
    public double Rhos;
    public String Rhosu = "\u03A9/\u20DE";
    //used to calculate RE
    private double R1A;
    private double R1B;
    private double R1C;
    private double R1;
    private double R2;
    private double R3;
    private double R4;
    //height reduction factor 1
    private double HRF1;
    //height reduction factor 2
    private double HRF2;
    //metal height
    private double MH;
    private double QQ;
    //strand spacing
    private double SS;
    private double Q;
    //final metal width
    private double FMW;
    //shading loss component 1
    private double SL1;
    //pc1d foler
    private String pc1dFolder;
    private String pc1dExe;

    // vectors for storing test results
    public Vector testNames;
    public Vector testResults;
    public Vector testTimes;
    public Vector testStages;

    // vectors for storing visual inspection results
    public Vector visualURLStrings;
    public Vector visualTimes;
    public Vector visualStages;

    // vectors for storing graphed results
    public Vector graphDatas;
    public Vector graphTimes;
    public Vector graphStages;

    public Wafer(){
    }

    public Wafer(int cellIndex, int thickness, double resistivity, String surfaceFinish, String type, String quality){
        createStockWafer(cellIndex, thickness, resistivity, surfaceFinish, type, quality);
    }

    public Wafer createStockWafer(int cellIndex, int thick, double resistivity, String surfaceFinish, String type, String quality){
        this.cellIndex = cellIndex;
        this.type = type;
        this.quality = quality;
        cellName = "wafer " + cellIndex;
        SWT = (1 + Math.random()*0.04 - 0.02) * thick;
        if (resistivity == 3){
            BR = (1 + Math.random()*0.2 - 0.1) * 35;
            BD = 3*(0.98 + (Math.random()*0.04));
        }
        else if (resistivity == 1){
            BR = (1 + Math.random()*0.2 - 0.1) * 20;
            BD = 1*(0.98 + (Math.random()*0.04));
        }
        else if (resistivity == 0.3){
            BR = (1 + Math.random()*0.2 - 0.1) * 10;
            BD = 0.3*(0.98 + (Math.random()*0.04));
        }
        if (type.equals("multicrystalline")){
            BR = BR/2;
        }
        if (quality.equals("standard"))
          BR = BR*2;
        else if (quality.equals("good"))
          BR = BR*5;
        else if (quality.equals("semiconductor"))
          BR = BR*10;
        else if (quality.equals("float zone"))
          BR = BR*100;

        if (surfaceFinish.equals("wire sawn"))
            SWWD = (10*Math.random() + 10);
        else if (surfaceFinish.equals("diamond tipped saw"))
            SWWD = (20*Math.random() + 20);

        PD1 = 3*Math.pow(10,20);

        A = 1;
        BBR = 0;
        IT = 0;

        RS = 64;
        RE = 0.000001;
        RB = 0.000001;
        RSH = 0.002;
        SD = 1e-7;
        T1 = 0.01;
        FSRV = 1e5;
        JD1 = 0;
        PD1 = 0;
        T2 = 250;
        JD2 = 0;
        PD2 = 0;
        Rhos = 0;
	RI = 2;

        testNames = new Vector();
        testResults = new Vector();
        testTimes = new Vector();
        testStages = new Vector();

        visualURLStrings = new Vector();
        visualTimes = new Vector();
        visualStages = new Vector();

        graphDatas = new Vector();
        graphTimes = new Vector();
        graphStages = new Vector();
        
        //  try {
        var jarPath = new File("").getAbsolutePath();
        pc1dFolder = jarPath + System.getProperty("file.separator") + "pc1d";
        pc1dExe = pc1dFolder + System.getProperty("file.separator") + "Pc1d.exe"; //This is the path inside the resources folder but unlikely it will work
        // } catch (IllegalArgumentException ex) {
        //     Logger.getLogger(Wafer.class.getName()).log(Level.SEVERE, null, ex);
        // } catch (IllegalAccessException ex) {
        //     Logger.getLogger(Wafer.class.getName()).log(Level.SEVERE, null, ex);
        // } catch (InvocationTargetException ex) {
        //     Logger.getLogger(Wafer.class.getName()).log(Level.SEVERE, null, ex);
        // }
        
  
        return this;
    }
    
    

    public String runTest(String testName, int currentBatchStage){
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
        String testResultString = new String();
        String testResultTimeString = new String();
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
	testResultTimeString = date.format(new Date()) + "  " + time.format(new Date());
        if (testName == "Wafer Values")
            testResultString = "A: " + format.format(A) + " " + Au + "\n" +
            "BBR: " + BBR + " " + BBRu + "\n" +
            "IT: " + IT + " " + ITu + "\n" +
            "RS: " + format.format(RS) + " " + RSu + "\n" +
            "RE: " + format.format(RE) + " " + REu + "\n" +
            "RB: " + format.format(RB) + " " + RBu + "\n" +
            "RSH: " + RSH + " " + RSHu + "\n" +
            "SD: " + SD + " " + SDu + "\n" +
            "T1: " + format.format(T1) + " " + T1u + "\n" +
            "BD: " + format.format(BD) + " " + BDu + "\n" +
            "PD1: " + PD1 + " " + PD1u + "\n" +
            "JD1: " + format.format(JD1) + " " + JD1u + "\n" +
            "FSRV: " + format.format(FSRV) + " " + FSRVu + "\n" +
            "T2: " + format.format(T2) + " " + T2u + "\n" +
            "BR: " + format.format(BR) + " " + BRu + "\n" +
            "JD2: " + format.format(JD2) + " " + JD2u + "\n" +
            "PD2: " + format.format(PD2) + " " + PD2u + "\n" +
	    "RI: " + format.format(RI) + " " + RIu + "\n" + 

            "SWT: " + format.format(SWT) + " " + SWTu + "  " +
            "SWWD: " + format.format(SWWD) + " " + SWWDu + "  " +
            "ET: " + format.format(ET) + " " + "  " +
            "EC: " + format.format(EC) + " " + "  " +
            "Et: " + format.format(Et) + " " + "  " +
            "SSB: " + format.format(SSB) + " " + "  " +
            "ER: " + format.format(ER) + " " + "\n" +

            "TSSB: " + format.format(TSSB) + " " + "  " +
            "Tt: " + format.format(Tt) + " " + "  " +
            "TT: " + format.format(TT) + " " + "  " +
            "TC1: " + format.format(TC1) + " " + "  " +
            "TC2: " + format.format(TC2) + " " + "  " +
            "TEFR: " + format.format(TEFR) + " " + "  " +
            "PS: " + format.format(PS) + " " + "  " +
            "PG: " + format.format(PG) + " " + "  " +
            "COV: " + format.format(COV) + " " + "  " +
            "CV: " + format.format(CV) + " " + "  " +
            "Pi: " + format.format(Pi) + " " + "\n" +
//            "cellBroken: " + (cellBroken.toString()) + " " + "\n" +
            "AHF: " + format.format(AHF) + " " + "  " +
            "hft: " + format.format(hft) + " " + "  " +
            "ARt1: " + format.format(ARt1) + " " + "  " +
            "AHCL: " + format.format(AHCL) + " " + "  " +
            "hclt: " + format.format(hclt) + " " + "  " +
            "ARt2: " + format.format(ARt2) + " " + "\n" +

            "DBS: " + format.format(DBS) + " " + "  " +
            "DT1: " + format.format(DT1) + " " + "  " +
            "DT2: " + format.format(DT2) + " " + "  " +
            "DDF: " + format.format(DDF) + " " + "  " +
            "Dt2: " + format.format(Dt2) + " " + "  " +
            "DDS: " + format.format(DDS) + " " + " " +
            "Rhos: " + format.format(Rhos) + " " + "\n" +

            "PEP: " + format.format(PEP) + " " + "  " +
            "PEt: " + format.format(PEt) + " " + " " +
            "ShuntResistance: " + format.format(shuntResistance) + " " + "\n" +

            "ARBS: " + format.format(ARBS) + " " + "  " +
            "ART: " + format.format(ART) + " " + "  " +
            "ARGF: " + format.format(ARGF) + " " + "  " +
            "ARGF2: " + format.format(ARGF2) + " " + "  " +
            "ARH: " + format.format(ARH) + " " + " " +
            "ARTM: " + format.format(ARTM) + " " + "\n" +

            "ALMD: " + format.format(ALMD) + " " + " " +
            "AETA: " + format.format(AETA) + " " + " " +
            "AETB: " + format.format(AETB) + " " + " " +
            "ALSD: " + format.format(ALSD) + " " + " " +
            "AEA: " + format.format(AEA) + " " + "\n" +

            "ALPP: " + format.format(ALPP) + " " + " " +
            "ALV: " + format.format(ALV) + " " + " " +
            "ALPS: " + format.format(ALPS) + " " + " " +
            "ALS: " + format.format(ALS) + " " + " " +
            "APP: " + format.format(APP) + " " + " " +
            "APS: " + format.format(APS) + " " + " " +
            "ALT: " + format.format(ALT) + " " + "\n" +

            "AFBS: " + format.format(AFBS) + " " + " " +
            "AFDT: " + format.format(AFDT) + " " + " " +
            "AFZ2: " + format.format(AFZ2) + " " + " " +
            "AFGF: " + format.format(AFGF) + " " + " " +
            "AFOP: " + format.format(AFOP) + " " + " " +
            "PBAG: " + format.format(PBAG) + " " + " " +
            "DOSET: " + format.format(DOSET) + " " + " " +
            "DOSEO: " + format.format(DOSEO) + " " + " " +
            "DOSEFZ: " + format.format(DOSEFZ) + " " + " " +
            "PBAL: " + format.format(PBAL) + " " + " " +
            "shuntError: " + shuntError + " " + "\n" +

            "SMD: " + format.format(SMD) + " " + "  " +
            "SETA: " + format.format(SETA) + " " + "  " +
            "SETB: " + format.format(SETB) + " " + "  " +
            "SSD: " + format.format(SSD) + " " + "  " +
            "SFS: " + format.format(SFS) + " " + " " +
            "MW: " + format.format(MW) + " " + "  " +
            "silverMeshDensity: " + format.format(silverMeshDensity) + " " + "\n" +

            "SSP: " + format.format(SSP) + " " + "  " +
            "SPV: " + format.format(SPV) + " " + " " +
            "HRF1: " + format.format(HRF1) + " " + "  " +
            "HRF2: " + format.format(HRF2) + " " + "  " +
            "MH: " + format.format(MH) + " " + "  " +
            "SS: " + format.format(SS) + " " + "  " +
            "Ps: " + format.format(Ps) + " " + "  " +
            "Q: " + format.format(Q) + " " + "  " +
            "SL1: " + format.format(SL1) + " " + " " +
            "FMW: " + format.format(FMW) + " " + "\n" +

            "SFt: " + format.format(SFt) + " " + "  " +
            "SFT2: " + format.format(SFT2) + " " + "  " +
            "MD: " + MD + " " + "  " +
            "R1A: " + format.format(R1A) + " " + "  " +
            "R1B: " + format.format(R1B) + " " + "  " +
            "R1C: " + format.format(R1C) + " " + "  " +
            "R1: " + format.format(R1) + " " + "  " +
            "R2: " + format.format(R2) + " " + "\n" +
            "QQ: " + format.format(QQ) + " " + "  " +
            "R3: " + format.format(R3) + " " + "  " +
            "R4: " + R4 + " " + "";


        else if (testName == "Thickness"){
            format.setMaximumFractionDigits(0);
            format.setMinimumFractionDigits(0);
	    if ((type == "multicrystalline" && currentBatchStage == 21)||currentBatchStage <= 1)
                testResultString = format.format(SWT) + " " + SWTu;
            else
                testResultString = format.format(T2) + " " + T2u;
        }
        else if (testName == "Pyramid Size Visual Inspection"){
            format.setMaximumFractionDigits(1);
            format.setMinimumFractionDigits(1);
            if (PS<PG)
                testResultString = format.format(PS*(0.95+0.1*Math.random())) + " " + SWTu;
            else
                testResultString = format.format(PG) + " " + SWTu;
        }
        else if (testName == "Pyramid Coverage Visual Inspection"){
            format.setMaximumFractionDigits(1);
            format.setMinimumFractionDigits(1);
            testResultString = format.format(CV*100) + " %";
        }
        else if (testName == "AR Coating Thickness"){
            format.setMaximumFractionDigits(1);
            format.setMinimumFractionDigits(1);
            testResultString = format.format(IT*(0.98+0.04*Math.random())) + " " + ITu;
        }
        else if (testName == "Al Paste Thickness"){
            format.setMaximumFractionDigits(1);
            format.setMinimumFractionDigits(1);
            testResultString = format.format(ALT*(0.98+0.04*Math.random())) + " " + SWTu;
        }
        else if (testName == "Silver Paste Thickness"){
            format.setMaximumFractionDigits(1);
            format.setMinimumFractionDigits(1);
            testResultString = format.format(MH*(0.98+0.04*Math.random())) + " " + SWTu;
        }
        else if (testName == "Silver Resistance"){
            format.setMaximumFractionDigits(1);
            format.setMinimumFractionDigits(1);
            testResultString = format.format((0.98+0.04*Math.random())*270*(1.6e-6)*(1e7)*SFS/(MW*MH)) + " mV/finger";
        }
	else if (testName == "Resistivity"){
            format.setMaximumFractionDigits(2);
            format.setMinimumFractionDigits(2);
            testResultString = format.format(BD) + " " + BDu;
        }
        else if (testName == "Minority Carrier Lifetime"){
            format.setMaximumFractionDigits(1);
            format.setMinimumFractionDigits(1);
	    testResultString = format.format(BR) + " " + BRu;
        }
        else if (testName == "Sheet Resistivity"){
            format.setMaximumFractionDigits(1);
            format.setMinimumFractionDigits(1);
            testResultString = format.format(getTopSurfaceSheetResistivity()) + " "+ Rhosu;
        }
        else if (testName == "Shunt Resistance"){
            format.setMaximumFractionDigits(0);
            format.setMinimumFractionDigits(0);
            testResultString = format.format(shuntResistance) + " " + shuntResistanceu;
        }
        else if (testName == "AR Coating Refractive Index"){
            format.setMaximumFractionDigits(2);
            format.setMinimumFractionDigits(2);
            double tempdouble = RI - 0.03 + (Math.random()*0.06);
            testResultString = format.format(tempdouble);
        }
        else if (testName == "Series Resistance"){
            format.setMaximumFractionDigits(2);
            format.setMinimumFractionDigits(2);
            double seriesR = RE + RB;
            testResultString = format.format(seriesR) + " " + REu;
            }

        testStages.addElement(new Integer(currentBatchStage));
        testTimes.addElement(testResultTimeString);
        testResults.addElement(testResultString);
        testNames.addElement(testName);
        return cellName + ": " + testResultString;
    }

    public GraphData runVocTest(String graphName, int currentBatchStage){
        double val = 0.0;
        Voc1 = getVoc(0);
        if (currentBatchStage > 7 && currentBatchStage < 11){

            if (Rhos == 0.0)
                val = 10/Rhos;
            else
                val = 10/getTopSurfaceSheetResistivity();
            Voc2 = getVoc(val);
        }
        else{
            Voc2 = getVoc(10*RSH);
        }

        if (shuntError == true){
            double sr = 100 + 150*Math.random();
            val = 50/sr;
            Voc3 = getVoc(val);
        }

        String graphTimeString = new String();
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
	graphTimeString = date.format(new Date()) + "  " + time.format(new Date());
        graphStages.addElement(new Integer(currentBatchStage));
        graphTimes.addElement(graphTimeString);
        Vector xVector = new Vector();
        Vector yVector = new Vector();
        Vector zVector = new Vector();
        Vector wVector = new Vector();
        Vector vVector = new Vector();
        String mgname = null;
        String mxaname;
        if (shuntError)
            mxaname = "true";
        else
            mxaname = "false";
        String myaname = null;
        String mzaname = null;
        String mwaname = null;
        String mvaname = null;
        xVector.addElement(new Double(Voc1));
        xVector.addElement(new Double (Voc2));
        if (shuntError == true)
            xVector.addElement(new Double(Voc3));
        GraphData graph = new GraphData(graphName, mxaname, myaname, mzaname, mwaname, mvaname,
            xVector, yVector, zVector, wVector, vVector, voc, isc, maxpower);
        graphDatas.addElement(graph);

        return graph;
    }

    public GraphData runGraph(String graphName, int currentBatchStage){
        GraphData graph;
        String rb;
        String re;
        String t2;
        String jd2;
        String pd2;

        if (graphName.equals("IV Curve")){
            if (PBAG > Math.random()*100){
                cellBroken = true;
                return null;
            }
        }

        String graphtype = null;
        String a = " -a " + A;
        String bbr = " -bbr " + BBR*0.01;
        String it = " -it " +  IT;
        String rs = " -rs " +  RS*0.01;
        if (graphName.equals("Pseudo IV Curve"))
            re = " -re " + 0.1;
        else
            re = " -re " + RE;
        if (graphName.equals("Pseudo IV Curve"))
            rb = " -rb " + 0.1;
        else
            rb = " -rb " + RB;
        String rsh = " -rsh " + RSH;
        String sd = " -sd " + SD;
        String t1 = " -t1 " + T1;
        String bd = " -bd " + BD;
        String fsrv = " -fsrv " + FSRV;
        if (graphName.equals("Doping Profile"))
            t2 = " -t2 " + 50;
        else
            t2 = " -t2 " + T2; //debug
        String pd1 = " -pd1 " + PD1;
        String jd1 = " -jd1 " + JD1;
        String br = " -br " + BR;
        if (graphName.equals("Pseudo IV Curve"))
            jd2 = " -jd2 " + 0.02;
        else
            jd2 = " -jd2 " + JD2;
        if (graphName.equals("Pseudo IV Curve"))
            pd2 = " -pd2 " + Math.pow(10,19);
        else
            pd2 = " -pd2 " + PD2;
	String ri = " -fei_i " + RI;

        String bigString = a+bbr+it+rs+re+rb+rsh+sd+t1+bd+fsrv+t2+pd1+jd1+br+jd2+pd2+ri;
        String commandString = null;
        String lineSep = System.getProperty("line.separator");
       

        if (graphName != "Doping Profile" && graphName != "Pseudo IV Curve"){
            graphtype = " -gt 2";
            //commandString = System.getProperty("user.dir")
            commandString =  pc1dExe
                                + graphtype
                                + bigString;
            runG(commandString);
            GraphData tempgraph = spectralResponse("Reflection Test", -10);
            wavelengthData = tempgraph.xpoints;
            reflectionData = tempgraph.wpoints;
            double l = 0.0;
            double nsr = 0.0;
            double srv = 0.0;
            Vector newReflectionData = new Vector();
            if (currentBatchStage < 3){
              //prior to post wafer etch rinse
                for (int i=0; i<wavelengthData.size(); i++){
                    srv = ((Double)reflectionData.elementAt(i)).doubleValue()*0.01;
                    nsr = srv - ((SWWD-ER)/600);
                    newReflectionData.addElement(new Double(nsr));
                }
            }
            else if (currentBatchStage >= 3 && (type != "multicrystalline")){
                for (int i=0; i<wavelengthData.size(); i++){
                    l = ((Double)wavelengthData.elementAt(i)).doubleValue()*0.001;
                    srv = ((Double)reflectionData.elementAt(i)).doubleValue()*0.01;
                   // if (l<PS) //change by ly: causing discontinuous reflection curve at high propanol concentraion (small pyramid)
                        nsr = Math.pow((CV*srv),2) + (srv*(1-CV)); 
                    //else
                    //    nsr = srv;
                    newReflectionData.addElement(new Double(nsr));
                }
            }
            else if((type == "multicrystalline")&& currentBatchStage !=21){
                double reflectBase = ((Double)reflectionData.elementAt(wavelengthData.size()/2)).doubleValue()*0.01;
                if ((currentBatchStage >= 13 && currentBatchStage <= 16) || (currentBatchStage >= 30) || (currentBatchStage >=7  && currentBatchStage <= 9))
                    reflectBase = 0.31;
                //double reflectTh = -20.949*Math.pow((0.2672*ATER+5.1314),-3)+0.35;
                double multifactor = ATReflection/reflectBase;
                
                //if(ATHFC>15 && ATHFC<=30 && ATHNOC>50 && ATHNOC<=60 ){
                //    multifactor=multifactor*(1-ATHFC-ATHNOC)/35*0.93;
                //
                    
                for (int i=0; i<wavelengthData.size(); i++){
                    srv = ((Double)reflectionData.elementAt(i)).doubleValue()*0.01;
                    nsr = srv*multifactor;
                    if (nsr > 1)
                        nsr = 0.99;
                    newReflectionData.addElement(new Double(nsr));
                }
            }
            reflectionData = newReflectionData;
            if ((currentBatchStage > 13 && currentBatchStage <= 16) ||
                (currentBatchStage > 30)){
                Vector postSilverReflectionData = new Vector();
                nsr = 0.0;
                srv = 0.0;
                for (int i=0; i<wavelengthData.size(); i++){
                    srv = ((Double)reflectionData.elementAt(i)).doubleValue();
                    nsr = srv + SL1;
                    postSilverReflectionData.addElement(new Double(nsr));
                }
                reflectionData = postSilverReflectionData;
            }

            //String reflect_filename = System.getProperty("user.home") + 
				      //System.getProperty("file.separator") + 
            //String reflect_filename = "reflect.dat";
            
            //String reflect_filename = System.getProperty("user.dir")
            String reflect_filename = pc1dFolder
                + System.getProperty("file.separator") + "reflect.dat";
            File reflectionFile = new File(reflect_filename);
            double newRef=0.0;
            try {
                FileWriter out = new FileWriter(reflectionFile);
                for (int i=0; i<wavelengthData.size(); i++){
                    newRef = ((Double)reflectionData.elementAt(i)).doubleValue();
                    out.write(((Double)wavelengthData.elementAt(i)).toString());
                    out.write('\t');
                    out.write((new Double(newRef)).toString());
                    out.write(lineSep);
                }
                out.close();
            } catch (IOException e){
            }
        }

        if (graphName == "IV Curve"){
            graphtype = " -gt 0 -hrf";
        }
        else if (graphName == "Pseudo IV Curve"){
            graphtype = " -gt 0";
        }
        else if (graphName == "Reflection Test"){
            graphtype = " -gt 2 -hrf";
        }
        else if (graphName == "Reflection & QE Spectral Response"){
            graphtype = " -gt 2 -hrf";
        }

        else if (graphName == "Doping Profile"){
            graphtype = " -gt 1";
        }

        //commandString = System.getProperty("user.dir")
        commandString = pc1dExe
                            + graphtype
                            + bigString;

        runG(commandString);
        graph = null;

        if (graphName == "IV Curve"){
            graph = ivCurve(graphName, currentBatchStage);
        }
        else if (graphName == "Pseudo IV Curve"){
            graph = pivCurve(graphName, currentBatchStage);
        }
        else if (graphName == "Reflection & QE Spectral Response" || graphName == "Reflection Test"){
            graph = spectralResponse(graphName, currentBatchStage);
        }
        else if (graphName == "Doping Profile"){
            graph = dopingProfile(graphName, currentBatchStage);
        }
        return graph;

    }

    void runG(String commandString){
        try {
            //run pc1dRuntime.getRuntime().exec
            Process p = Runtime.getRuntime().exec(commandString,null,
                    new File(pc1dExe));
            p.waitFor();
        } catch (IOException e) {
            System.out.println( "An IO exception occurred while executing the cmd <" + commandString + ">:" + e.getMessage() );
            e.printStackTrace() ;
        } catch (InterruptedException e){
            System.out.println( "An Interrupted Exception occurred while executing the cmd <" + commandString + ">:" + e.getMessage() );
        }
    }

    public GraphData ivCurve(String graphName, int currentBatchStage){
        StringBuffer myGraphName = new StringBuffer(30);
        StringBuffer myxaxisName = new StringBuffer(20);
        StringBuffer myyaxisName = new StringBuffer(20);
        Vector xVector = new Vector();
        Vector yVector = new Vector();
        Vector zVector = new Vector();
        Vector wVector = new Vector();
        Vector vVector = new Vector();
        String mgname = null;
        String myaname = null;
        String mxaname = null;
        String mzaname = null;
        String mwaname = null;
        String mvaname = null;


        //String filename = System.getProperty("user.dir")+ System.getProperty("file.separator") + "pc1d.dat";
        String filename = pc1dFolder + System.getProperty("file.separator") + "pc1d.dat";
        double x = 0;
        double y = 0;
        try {
            FileInputStream in = new FileInputStream(filename);
            int c;
            char lineSep = System.getProperty("line.separator").charAt(0);
            StringBuffer tempString = new StringBuffer(10);
            while ((c = in.read()) != lineSep){
                 tempString.append((char)c);
            }
            voc = Double.parseDouble(tempString.toString());
            tempString = new StringBuffer(10);
            while ((c = in.read()) != lineSep){
                 tempString.append((char)c);
            }
            isc = -Double.parseDouble(tempString.toString());
            tempString = new StringBuffer(10);
            while ((c = in.read()) != lineSep){
                 tempString.append((char)c);
            }
            maxpower = Double.parseDouble(tempString.toString());
            tempString = new StringBuffer(10);
            while ((c = in.read()) != lineSep){
                 myGraphName.append((char)c);
            }
            mgname = myGraphName.toString();
            while ((c = in.read()) != 9){
                if (c!=10 && c!=13)
                    myxaxisName.append((char)c);
            }
            mxaname = myxaxisName.toString();
            while ((c = in.read()) != 10 && c!=13){
                myyaxisName.append((char)c);
            }
            myaname = myyaxisName.toString();
            StringBuffer numString = new StringBuffer(10);
            while ((c = in.read()) != -1) {
                if (c == 9){
                    x = Double.parseDouble(numString.toString())*1000;
                    numString = new StringBuffer(10);
                }
                else if (c == 10){
                    if (numString.length() > 0){
                        y = -Double.parseDouble(numString.toString())*1000;
                        if (x>=0.00 && y>=0.00){
                            xVector.addElement(new Double(x));
                            yVector.addElement(new Double(y));
                        }
                        numString = new StringBuffer(10);
                    }
                }
                else if (c == 13){
                }
                else{
                    numString.append((char)c);
                }
            }
            in.close();
        } catch (IOException e) {
            System.out.println( "An IO exception occurred while reading the file PC1D.dat" + e.getMessage() );
            e.printStackTrace() ;
        }

        String graphTimeString = new String();
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
	graphTimeString = date.format(new Date()) + "  " + time.format(new Date());
        graphStages.addElement(new Integer(currentBatchStage));
        graphTimes.addElement(graphTimeString);
//        double xlo = Double.MIN_VALUE;
  //      double xhi = Double.MAX_VALUE;
        if (isc == 0 || isc == -0){
            double newisc = ((Double)yVector.elementAt(0)).doubleValue()*0.001;
            isc = newisc;
        }
        boolean finished = false;
        GraphData graph = new GraphData(graphName, mxaname, myaname, mzaname, mwaname, mvaname,
            xVector, yVector, zVector, wVector, vVector, voc, isc, maxpower);
        graphDatas.addElement(graph);

        return graph;
    }

    public GraphData pivCurve(String graphName, int currentBatchStage){
        StringBuffer myGraphName = new StringBuffer(30);
        StringBuffer myxaxisName = new StringBuffer(20);
        StringBuffer myyaxisName = new StringBuffer(20);
        StringBuffer myzaxisName = new StringBuffer(20);
        StringBuffer mywaxisName = new StringBuffer(20);
        StringBuffer myvaxisName = new StringBuffer(20);
        Vector xVector = new Vector();
        Vector yVector = new Vector();
        Vector zVector = new Vector();
        Vector wVector = new Vector();
        Vector vVector = new Vector();
        String mgname = null;
        String myaname = null;
        String mxaname = null;
        String mzaname = null;
        String mwaname = null;
        String mvaname = null;

        //get pc1d.dat
        //String filename = System.getProperty("user.home") + System.getProperty("file.separator") + "pc1d.dat";
        //String filename = "pc1d.dat";
        //String filename = System.getProperty("user.dir")
        //+ System.getProperty("file.separator") + "pc1d"
        String filename = pc1dFolder + System.getProperty("file.separator") + "pc1d.dat";
        double x = 0;
        double y = 0;
        double z = 0;
        double w = 0;
        double v = 0;

        double pisc = 0;
        double pvoc = 0;
        double pmaxpower = 0;

        try {
            FileInputStream in = new FileInputStream(filename);
            int c;
            char lineSep = System.getProperty("line.separator").charAt(0);
            StringBuffer tempString = new StringBuffer(10);
            while ((c = in.read()) != lineSep){
                 tempString.append((char)c);
            }
            pvoc = Double.parseDouble(tempString.toString());
            tempString = new StringBuffer(10);
            while ((c = in.read()) != lineSep){
                 tempString.append((char)c);
            }
            pisc = -Double.parseDouble(tempString.toString());
            tempString = new StringBuffer(10);
            while ((c = in.read()) != lineSep){
                 tempString.append((char)c);
            }
            pmaxpower = Double.parseDouble(tempString.toString());
            tempString = new StringBuffer(10);

            while ((c = in.read()) != lineSep){
                 myGraphName.append((char)c);
            }
            mgname = myGraphName.toString();
            while ((c = in.read()) != 9){
                if (c!=10 && c!=13)
                    myxaxisName.append((char)c);
            }
            mxaname = myxaxisName.toString();
            while ((c = in.read()) != 10 && c!=13){
                myyaxisName.append((char)c);
            }
            myaname = myyaxisName.toString();
            StringBuffer numString = new StringBuffer(10);
            while ((c = in.read()) != -1) {
                if (c == 9){
                    x = Double.parseDouble(numString.toString())*1000;
                    numString = new StringBuffer(10);
                }
                else if (c == 10){
                    if (numString.length() > 0){
                        y = -Double.parseDouble(numString.toString());
                        if (x>=0.00 && y>=0.00){
                            xVector.addElement(new Double(x));
                            yVector.addElement(new Double(y));
                        }
                        numString = new StringBuffer(10);
                    }
                }
                else if (c == 13){
                }
                else{
                    numString.append((char)c);
                }
            }
            in.close();
        } catch (IOException e) {
            System.out.println( "An IO exception occurred while reading the file PC1D.dat" + e.getMessage() );
            e.printStackTrace() ;
        }

        String graphTimeString = new String();
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
	graphTimeString = date.format(new Date()) + "  " + time.format(new Date());
        graphStages.addElement(new Integer(currentBatchStage));
        graphTimes.addElement(graphTimeString);
//        double xlo = Double.MIN_VALUE;
  //      double xhi = Double.MAX_VALUE;
        if (pisc == 0 || pisc == -0){
            double newisc = ((Double)yVector.elementAt(0)).doubleValue();
            pisc = newisc;
        }
        double newi = 0;
        Vector newyVector = new Vector();
        for (int i=0; i<yVector.size(); i++){
            newi = 30*((Double)yVector.elementAt(i)).doubleValue()/pisc;
            newyVector.addElement(new Double(newi));
        }
        pisc = 0.03;
        yVector = newyVector;
        boolean finished = false;
        GraphData graph = new GraphData(graphName, mxaname, myaname, mzaname, mwaname, mvaname,
            xVector, yVector, zVector, wVector, vVector, pvoc, pisc, pmaxpower);
        graphDatas.addElement(graph);

        return graph;
    }

    public GraphData spectralResponse(String graphName, int currentBatchStage){
        StringBuffer myGraphName = new StringBuffer(30);
        StringBuffer myxaxisName = new StringBuffer(20);
        StringBuffer myyaxisName = new StringBuffer(20);
        StringBuffer myzaxisName = new StringBuffer(20);
        StringBuffer mywaxisName = new StringBuffer(20);
        StringBuffer myvaxisName = new StringBuffer(20);
        Vector xVector = new Vector();
        Vector yVector = new Vector();
        Vector zVector = new Vector();
        Vector wVector = new Vector();
        Vector vVector = new Vector();
        String mgname = null;
        String myaname = null;
        String mxaname = null;
        String mzaname = null;
        String mwaname = null;
        String mvaname = null;
        boolean QE = false;
        if (graphName.equals("Reflection & QE Spectral Response"))
            QE = true;

        //get pc1d.dat
        //String filename = System.getProperty("user.home") + System.getProperty("file.separator") + "pc1d.dat";
        //String filename = "pc1d.dat";
        
        //String filename = System.getProperty("user.dir")
        //+ System.getProperty("file.separator") + "pc1d"
        String filename = pc1dFolder + System.getProperty("file.separator") + "pc1d.dat";

        double x = 0;
        double y = 0;
        double z = 0;
        double w = 0;
        double v = 0;
        try {
            FileInputStream in = new FileInputStream(filename);
            int c;
            char lineSep = System.getProperty("line.separator").charAt(0);

            while ((c = in.read()) != lineSep){
                 myGraphName.append((char)c);
            }
            mgname = myGraphName.toString();
            while ((c = in.read()) != 9){
                if (c!=10 && c!=13)
                        myxaxisName.append((char)c);
            }
            mxaname = myxaxisName.toString();
            while ((c = in.read()) != 9){
                if (c!=10 && c!=13)
                        myyaxisName.append((char)c);
            }
            if (QE)
                myaname = myyaxisName.toString();
            while ((c = in.read()) != 9){
                if (c!=10 && c!=13)
                        myzaxisName.append((char)c);
            }
            if (QE)
                mzaname = myzaxisName.toString();
            while ((c = in.read()) != 9){
                if (c!=10 && c!=13)
                        mywaxisName.append((char)c);
            }
            mwaname = mywaxisName.toString();
            while ((c = in.read()) != 10    && c!=13){
                 myvaxisName.append((char)c);
            }
            mvaname = myvaxisName.toString();
            StringBuffer numString = new StringBuffer(10);
            String nextno = "x";
            while ((c = in.read()) != -1) {
                if (c == 9 && nextno == "x"){
                    x = Double.parseDouble(numString.toString());
                    xVector.addElement(new Double(x));
                    numString = new StringBuffer(10);
                    nextno = "y";
                }
                else if (c == 9 && nextno == "y"){
                    if (QE){
                        y = Double.parseDouble(numString.toString());
                        yVector.addElement(new Double(y));
                    }
                    numString = new StringBuffer(10);
                    nextno = "z";
                }
                else if (c == 9 && nextno == "z"){
                    if (QE){
                        z = Double.parseDouble(numString.toString());
                        zVector.addElement(new Double(z));
                    }
                    numString = new StringBuffer(10);
                    nextno = "w";
                }
                else if (c == 9 && nextno == "w"){
                    w = Double.parseDouble(numString.toString());
                    wVector.addElement(new Double(w));
                    numString = new StringBuffer(10);
                    nextno = "v";
                }

                else if (c == 10 && nextno == "v"){
                   v = Double.parseDouble(numString.toString());
                   vVector.addElement(new Double(v));
                    numString = new StringBuffer(10);
                    nextno = "x";
                }
                else if (c == 13){
                }
                else{
                    numString.append((char)c);
                }
            }
            in.close();
        } catch (IOException e) {
            System.out.println( "An IO exception occurred while reading the file PC1D.dat" + e.getMessage() );
            e.printStackTrace() ;
        }
        if (currentBatchStage != -10){
            String graphTimeString = new String();
            DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
            DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
    	    graphTimeString = date.format(new Date()) + "  " + time.format(new Date());
            graphStages.addElement(new Integer(currentBatchStage));
            graphTimes.addElement(graphTimeString);
        }
        double xlo = Double.MIN_VALUE;
        double xhi = Double.MAX_VALUE;
        boolean finished = false;

        test:
            for (int i=0; i<xVector.size()-1; i++){
                xlo = ((Double)xVector.elementAt(i)).doubleValue();
                xhi = ((Double)xVector.elementAt(i+1)).doubleValue();
                if (xhi < xlo){
                    for (int j=i+1; j<xVector.size(); j++){
                        xVector.removeElementAt(j);
                        wVector.removeElementAt(j);
                        if (QE){
                            yVector.removeElementAt(j);
                            zVector.removeElementAt(j);
                        }
                        vVector.removeElementAt(j);
                    }
                        break test;
                }
                else if (i==xVector.size()-2){
                    break test;
                }
            }

        Vector newW = new Vector();
        double nw = 0;
        for (int i=0; i<wVector.size(); i++){
            nw = ((Double)wVector.elementAt(i)).doubleValue() + ((Double)vVector.elementAt(i)).doubleValue();
            newW.addElement(new Double(nw));
        }
        wVector = newW;
        vVector = new Vector();
        mvaname = null;
        mwaname = "Total Reflection";

        GraphData graph = new GraphData(graphName, mxaname, myaname, mzaname, mwaname, mvaname,
            xVector, yVector, zVector, wVector, vVector, voc, isc, maxpower);
        if (currentBatchStage != -10)
            graphDatas.addElement(graph);

        return graph;
    }

    public GraphData dopingProfile(String graphName, int currentBatchStage){
        StringBuffer myGraphName = new StringBuffer(30);
        StringBuffer myxaxisName = new StringBuffer(20);
        StringBuffer myyaxisName = new StringBuffer(20);
        StringBuffer myzaxisName = new StringBuffer(20);
        StringBuffer mywaxisName = new StringBuffer(20);
        StringBuffer myvaxisName = new StringBuffer(20);
        Vector xVector = new Vector();
        Vector yVector = new Vector();
        Vector zVector = new Vector();
        Vector wVector = new Vector();
        Vector vVector = new Vector();
        String mgname = null;
        String myaname = null;
        String mxaname = null;
        String mzaname = null;
        String mwaname = null;
        String mvaname = null;

        //get pc1d.dat
 	       //String filename = "pc1d.dat";
        //String filename = System.getProperty("user.dir")
        //+ System.getProperty("file.separator") + "pc1d"
        String filename = pc1dFolder + System.getProperty("file.separator") + "pc1d.dat";
        //String filename = System.getProperty("user.home") + System.getProperty("file.separator") + "pc1d.dat";
        double x = 0;
        double y = 0;
        double z = 0;
        double w = 0;
        double v = 0;
        try {
            FileInputStream in = new FileInputStream(filename);
            int c;
            char lineSep = System.getProperty("line.separator").charAt(0);

            while ((c = in.read()) != lineSep){
                 myGraphName.append((char)c);
            }
            mgname = myGraphName.toString();
            while ((c = in.read()) != 9){
                if (c!=10 && c!=13)
                    myxaxisName.append((char)c);
            }
            mxaname = myxaxisName.toString();
            while ((c = in.read()) != 9){
                if (c!=10 && c!=13)
                    myyaxisName.append((char)c);
            }
            myaname = myyaxisName.toString();
            while ((c = in.read()) != 10 && c!=13){
                myzaxisName.append((char)c);
            }
            mzaname = myzaxisName.toString();
            StringBuffer numString = new StringBuffer(10);
            String nextno = "x";
            while ((c = in.read()) != -1) {
                if (c == 9 && nextno == "x"){
                    x = 1e9*Double.parseDouble(numString.toString());
                    xVector.addElement(new Double(x));
                    numString = new StringBuffer(10);
                    nextno = "y";
                }
                else if (c == 9 && nextno == "y"){
                    y = Double.parseDouble(numString.toString());
                    yVector.addElement(new Double(y));
                    numString = new StringBuffer(10);
                    nextno = "z";
                }
                else if (c == 10 && nextno == "z"){
                    z = Double.parseDouble(numString.toString());
                    zVector.addElement(new Double(z));
                    numString = new StringBuffer(10);
                    nextno = "x";
                }

                else if (c == 13){
                }
                else{
                    numString.append((char)c);
                }
            }
            in.close();
        } catch (IOException e) {
            System.out.println( "An IO exception occurred while reading the file PC1D.dat" + e.getMessage() );
            e.printStackTrace() ;
        }

        int myi = 0;
        test2:
            for (int i=0; i<yVector.size(); i++){
                myi = i;
                if (((Double)yVector.elementAt(myi)).doubleValue()<((Double)zVector.elementAt(myi)).doubleValue()){
                    break test2;
                }
            }
        for (int i=xVector.size()-1; i>myi; i--){
            xVector.removeElementAt(i);
            yVector.removeElementAt(i);
            zVector.removeElementAt(i);
        }

        String graphTimeString = new String();
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
	graphTimeString = date.format(new Date()) + "  " + time.format(new Date());
        graphStages.addElement(new Integer(currentBatchStage));
        graphTimes.addElement(graphTimeString);
        boolean finished = false;
        GraphData graph = new GraphData(graphName, mxaname, myaname, mzaname, mwaname, mvaname,
            xVector, yVector, zVector, wVector, vVector, voc, isc, maxpower);
        graphDatas.addElement(graph);

        return graph;
    }

    public String visualInspection(int currentBatchStage){
        String visualTimeString = new String();
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
	visualTimeString = date.format(new Date()) + "  " + time.format(new Date());
        String visualURLString = null;
        switch (currentBatchStage){
            case 3: visualURLString = "postEtching.jpg"; break;
            case 5: visualURLString = "postTexturing.jpg"; break;
            case 9: visualURLString = "postARCoating.jpg"; break;
            case 12: visualURLString = "postAlFiring.jpg"; break;
            case 15: visualURLString = "postSilverFiring.jpg"; break;
            // added a cases for acid texture and rinse Bobh
            case 23: visualURLString ="postEtching.jpg";break;
                   }
        visualStages.addElement(new Integer(currentBatchStage));
        visualTimes.addElement(visualTimeString);
        visualURLStrings.addElement(visualURLString);
        return visualURLString;
    }

    public void etch(int ssBatches, double bathTemp, double chemConc, double etchTime){
        SSB = ssBatches;
        // ET (etch temperature for 30%) = user specified +/- 2 degreesC
        ET = bathTemp + (4 * Math.random()) - 2;
        // EC = user specified concentration of NaOH etch
        EC = chemConc;
        // Et = time - user specified (mins)
        Et = etchTime;
        // ER (30% etch removal)
        ER = (Math.pow(ET,2)*Et)/(80*(20+ssBatches));

        T2 = SWT - (2 * ER);
        
    }

    //add in process for acid texturing and acidRinseCleanRisnce BobH

    public void acidTexture(int acidBatches, double acidTextureTime, double acidTextureBathTemp, double HFC, double HCLC, double COOHC){
        ATB = acidBatches;
        // ET (etch temperature for 30%) = user specified +/- 2 degreesC
        ATT = acidTextureBathTemp + (4 * Math.random()) - 2;
       // Concentration Ratio of HNA - user specified %
        ATHFC = HFC;
        ATHNOC = HCLC;
        ATCOOHC = COOHC;
        // Et = time - user specified (mins)
        ATt = (2.2/acidTextureTime);

        // ER (30% etch removal)
        ATER = ATt*acidTexturingRate(HFC-0.01,HCLC-0.01, ATT);
        T2 = SWT - 2*ATER;
        //

        //surface recommbination
        ATReflection = -20.949*Math.pow((0.2672*ATER+5.1314),-3)+0.35;
        if(ATHFC>15 && ATHFC<=30 && ATHNOC>50 && ATHNOC<=60 ){
                    //ATReflection=ATReflection*(1-((1-Math.abs(ATHFC+ATHNOC-100)/35)*0.08));
            ATReflection=ATReflection-(ATHFC+ATHNOC-65)/25*0.02;
        }

        if(ATER < 5)
            BR = (1-(0.2*((5-ATER)/5)))*BR;
       

    }
    //add by ly
    public double  acidTexturingRate(double HFC, double HNO3C, double Temperature){
        Vector HFVector = new Vector();
	Vector HNO3Vector = new Vector();
	Vector RateVector = new Vector();

	String filename = System.getProperty("user.dir")+ System.getProperty("file.separator") + "acidtexturing.dat";
        double tempHF = -1;
        double tempHNO3 = -1;
	double tempRate = -1;
	double x = -1;
        try {
	    FileInputStream in = new FileInputStream(filename);
            int c;
            StringBuffer numString = new StringBuffer(10);
            while ((c = in.read()) != -1) {

                if (c == '\t'){
                    x = Double.parseDouble(numString.toString());
                    numString = new StringBuffer(10);
		    if(tempHF < 0)
			 tempHF = x;
		    else
                        tempHNO3 = x;
                }
                else if (c == '\n'){
                    if (numString.length() > 0){
                        tempRate = Double.parseDouble(numString.toString());
                        if (tempHF>=0.00 && tempHNO3>=0.00 && tempRate>=0.00){
                            HFVector.addElement(new Double(tempHF));
                            HNO3Vector.addElement(new Double(tempHNO3));
                            RateVector.addElement(new Double(tempRate));
                            tempHF = -1;
                            tempHNO3 = -1;
                            tempRate = -1;
                        }
                        numString = new StringBuffer(10);
                    }
                }
                else if (c == '\r'){
                }
                else{
                    numString.append((char)c);
                }

            }
            in.close();
        } catch (IOException e) {
            System.out.println( "An IO exception occurred while reading the file acidtexturing.dat" + e.getMessage() );
            e.printStackTrace() ;
        }

        int index[] = new int[3];
        int i[] = new int[3];
        int j[] = new int[3];

        i[0] = (int)Math.floor(HFC);
        j[0] = (int)Math.floor(HNO3C);
        index[0] = i[0]*61+j[0]-1;
        index[1] = (i[0]+1)*61+j[0]-1;
        index[2] = i[0]*61+j[0]+1-1;
        double HFC100 = HFC/100;
        double HNO3C100 =HNO3C/100;
        double A0 = (HFC100-(Double)HFVector.elementAt(index[0]))*((Double)HNO3Vector.elementAt(index[1])-(Double)HNO3Vector.elementAt(index[0]))*((Double)RateVector.elementAt(index[2])-(Double)RateVector.elementAt(index[0]));
        double B = (HNO3C100-(Double)HNO3Vector.elementAt(index[0]))*((Double)RateVector.elementAt(index[1])-(Double)RateVector.elementAt(index[0]))*((Double)HFVector.elementAt(index[2])-(Double)HFVector.elementAt(index[0]));
        double C = ((Double)HFVector.elementAt(index[1])-(Double)HFVector.elementAt(index[0]))*((Double)HNO3Vector.elementAt(index[2])-(Double)HNO3Vector.elementAt(index[0]));
        double D = ((Double)HNO3Vector.elementAt(index[1])-(Double)HNO3Vector.elementAt(index[0]))*((Double)HFVector.elementAt(index[2])-(Double)HFVector.elementAt(index[0]));
        double E = (HNO3C100-(Double)HNO3Vector.elementAt(index[0]))*((Double)HFVector.elementAt(index[1])-(Double)HFVector.elementAt(index[0]))*((Double)RateVector.elementAt(index[2])-(Double)RateVector.elementAt(index[0]));
        double F = (HFC100-(Double)HFVector.elementAt(index[0]))*((Double)RateVector.elementAt(index[1])-(Double)RateVector.elementAt(index[0]))*((Double)HNO3Vector.elementAt(index[2])-(Double)HNO3Vector.elementAt(index[0]));
        double Rate0 = (E+F-(Double)RateVector.elementAt(index[0])*D-A0-B+(Double)RateVector.elementAt(index[0])*C)/(C-D);

        //Temperature dependance
        double Ea = 3035;
        double Rate1 = Rate0/Math.exp(-1*Ea/(8+273))*Math.exp(-1*Ea/(273+Temperature));

        return Rate1;
    }
    //end add by ly
    public boolean[] acidRinseAcidClean(int kohChemConc, double kohTime, double kohRinseTime){
	//ARt1 = time in post koh bath rinse
	ATARt1 = kohRinseTime;
	
	//AHF = KOH concentration
	ATAHF = kohChemConc;
	
	//At = time in Koh bath
	AThft = kohTime;
	
        boolean message;

        if (T2<150)
            Pi = (150-T2)/2;
        else
            Pi = 0.2;

	if (((ATARt2/ATAHCL) < 0.3) || ((ATARt1/ATAHF) < 0.5))
            message = true;
        else
            message = false;

        double r;
        r = Math.random()*100;
        if (Pi > r)
            cellBroken = true;
        boolean[] b = {cellBroken, message};
        return b;
    }
    // add end here Bobh

    public void postWaferEtchRinse(){
    }

    public void texture(int tssBatches, double textureTime, double temp, double chemConc, double propanol, int efr){
        // Texture NaOH batches
        TSSB = tssBatches;
        // Tt = user specified time for texturing (mins)
        Tt = textureTime;
	// TT = user specified temperature for NaOH bath (degreesC) +/- 2 degreesC
	TT = temp + (4 * Math.random()) - 2;
        // TC1 = user specified concentration for NaOH in texturing solution (%)
        TC1 = chemConc;
        // TC2 = user specified concentration for isopropanol in texturing solution (%)
        TC2 = propanol;
        // PS = pyramid size (base to base)
         // ie - size pyramids would need to grow to give 100% coverage
        PS = 625/Math.pow(TC2,3);
        // PG = actual pyramid growth size
        PG = (TT*Tt*TC1)/760;
        // COV = coverage of pyramids where 1 = 100%
        COV = Math.pow(PG,2)/Math.pow(PS,2);
        // TEFR = exhaust flow rate's effect on pyramid quality - user specified efr very high - (TEFR = 0,1,2,3,4) - nil
	switch(efr){
		case 0: TEFR=4;
		case 1: TEFR=3;
		case 2: TEFR=2;
		case 3: TEFR=1;
		case 4: TEFR=0;
	}

	if (SSB >= 20)
		COV = COV;
	else
		COV = COV*(SSB/20 + 9)/10;

        if (COV <= 1)
            CV = COV*(1-(TEFR*0.05));
        else
            CV = (1-(TEFR*0.05))/Math.sqrt(COV);

        if (type.equals("multicrystalline"))
            CV = CV/3;
    }

    public boolean[] rinseAcidClean(int hfChemConc, double hfTime, double rinseTime1, int hclChemConc, double hclTime, double rinseTime2){
	//ARt1 = time in post HF acid bath rinse
	ARt1 = rinseTime1;
	//AHCL = hcl acid concentration
	AHCL = hclChemConc;
	//AHF = hydroflouric acid concentration
	AHF = hfChemConc;
	//At = time in acid bath
	hclt = hclTime;
	//At = time in acid bath
	hft = hfTime;
	//ARt2 = time in post HCl acid bath rinse
	ARt2 = rinseTime2;
        boolean message;

        if (T2<150)
            Pi = (150-T2)/2;
        else
            Pi = 0.2;

	if (((ARt2/AHCL) < 0.3) || ((ARt1/AHF) < 0.5))
            message = true;
        else
            message = false;

        double r;
        r = Math.random()*100;
        if (Pi > r)
            cellBroken = true;
        boolean[] b = {cellBroken, message};
        return b;
    }

    public boolean spinOnDiffusion(double beltSpeed, double dryingTemp,
                         double zone1Temp, double zone2Temp,
                         double zone3Temp, double gasFlow, double dilutionFactor){

	if ((AHCL*hclt)/50 <= 1)
		BR = (BR*AHCL*hclt)/50;
        if (TSSB >3){
            BR = (BR*AHF*hft)/50;
        }
        else if(TSSB == 0){
            if (SSB > 3)
                BR = (BR*AHF*hft)/50;
        }
	if (ARt1<5)
		BR = (BR/2) + ((BR * ARt1)/10);

        DBS = beltSpeed;
        DDF = dilutionFactor/100;
        // DT2 = temperature of zone 2 - user specified +/-4 degreesC
        DT2 = zone2Temp + (8*Math.random()) - 4;
        // Dt2 = time in zone 2 of diffusion furnace (mins)
        Dt2 = (100/DBS)/60;
        JD1 = (Math.sqrt(3*Dt2)/1700)*Math.pow(((DT2 + 273)/873), 17);
        // add by ly, in case of Multi,the ER < SWWD
        if (ER >= SWWD || type.equals("multicrystalline"))
            T1 = 1.5*JD1;
        else
            T1 = SWWD - ER + (1.5*JD1);
        //drying temp for diffusion source = user specified +/- 4 degreesC
        DT1 = dryingTemp + (8*Math.random()) - 4;

        //multicrystlline
        if (type.equals("multicrystalline")){
           //added by ly
            DDS = DT1/(200*DBS * (6+0.00001));
        }
        else{
            //drying diffusion source
            DDS = DT1/(200*DBS * (PG+0.00001));
        }
        
        if (DDS>1)
            DDS=1;
        PD1 = 3*Math.pow(10,20)*DDF*DDS;


        Rhos = getTopSurfaceSheetResistivity();
        SD = Math.pow(10,-7);
        RE = 0.000001;
        RB = 0.000001;

        //multicrystlline
        if (type.equals("multicrystalline")){
            BR = BR*Math.pow((800/DT2),2)*(60/Rhos);
        }
        else{
            BR = BR*Math.sqrt(Math.pow((800/DT2),2)*(60/Rhos));
        }

        if (Pi > Math.random()*100)
            cellBroken = true;
        return cellBroken;
    }

    public boolean diffusionOxideRemoval(double acidCleanTime, int chemConc, double rinseTime){

        if (Pi > Math.random()*100)
            cellBroken = true;
        return cellBroken;
    }

    public boolean plasmaEtch(int power, double time){
        PEP = power/1500.0;
        PEt = time/20;
        SD = 1e-6*Math.pow(PEP,2)*PEt;
        shuntResistance = 100 + (PEP*PEt*500/JD1);
        RSH = 1/shuntResistance;

        if (Pi > Math.random()*100)
            cellBroken = true;
        return cellBroken;
    }

    public boolean tarCoating(double beltSpeed, double temp, int gasFlow){
        ARBS = beltSpeed;
        ART = temp;
        ARGF = gasFlow;

        IT = (0.8 + ARGF/10)*(2/ARBS)*70;
        ARH = Math.pow(ART/130,2);


        if (Pi > Math.random()*100)
            cellBroken = true;
        return cellBroken;
    }

    public boolean sarCoating(double time, double temp, int gasFlow1, int gasFlow2){
        ARTM = time;
        ART = temp;
        ARGF = gasFlow1;
        ARGF2 = gasFlow2;


        IT = (0.8 + (ARGF+ARGF2)/20)*(ARTM/15)*70;
        ARH = Math.pow(ART/130,2);

        //FSRV change with the etch depth
        //reflection--FSRV
        if (type.equals("multicrystalline") && ATReflection<=0.267)
             FSRV = Math.floor(1*Math.pow(10,13)*Math.exp(-86.35*ATReflection));
             //FSRV = -1*Math.pow(10,7)*ATReflection+3*Math.pow(10,6);
        else if (type.equals("multicrystalline") && ATReflection>0.267)
            FSRV = Math.floor(2*Math.pow(10,6)*Math.exp(-28.43*ATReflection));
        else
            FSRV = 100;
        //etch depth--FSRV
        if (type.equals("multicrystalline") && ATER < 4.4){
            FSRV = FSRV + 10000-10000/4.4*ATER;
        }
        //[HF]&[HNO3]--FSRV
        if (type.equals("multicrystalline") && ATHFC>15  && ATHNOC>50){
            FSRV = FSRV + 10000*(ATHFC+ATHNOC-65)/25;
        }



        //original value is 0
        double tempdouble = 1;
	if(type.equals("multicrystalline"))
            tempdouble = 1+((ARGF-ARGF2+5)/20);
        BR = BR*tempdouble;
        RI = 1.97+(ARGF/9)-(ARGF2/9);
		

        if (Pi > Math.random()*100)
            cellBroken = true;
        return cellBroken;
    }

    public void alScreenSetup(int meshDensity, double emulsionThicknessAbove,
            double emulsionThicknessBelow, int strandDiameter, String pattern){
            ALMD = meshDensity;
            AETA = emulsionThicknessAbove;
            AETB = emulsionThicknessBelow;
            ALSD = strandDiameter;

            //(AEA is the Al thickness reduction factor for too thick emulsion above)
            if (AETA < 20)
                AEA=1;
            else
             	AEA=20/AETA;

    }

    public boolean screenPrint(int squeegeePressure, int pasteViscosity, int squeegeeSpeed){
        ALPP = squeegeePressure;
        ALV = pasteViscosity;
        ALPS = squeegeeSpeed;

        //(ALS is the Al thickness reduction factor for wrong screen density and high viscosity paste)
        double td = ALMD*3*ALSD*(ALV+10);
        ALS = 140000/td;
        if (ALS>1)
	    ALS=1;

        //(APP is the Al thickness reduction factor for excessive or too low squeegee pressure)
        APP=(25-Math.pow(ALPP-6,2))/25;

        //(APS is the Al thickness reduction factor resulting from high print speeds)
        if (ALPS<4){
	    APS=1;
        }
            //and give message “at this slow printing speed, throughput will be poor!”
        else{
          double t = 10 + ALPS;
          APS = 14/t;
        }

        //Al thickness ALT is given by
        ALT=AETB*AEA*ALS*APP*APS;

        if (Pi > Math.random()*100)
            cellBroken = true;
        return cellBroken;
    }

    public boolean alFiring(double beltSpeed, int dryingTemp,
	    int zone1Temp, int zone2Temp, int zone3Temp,
            double gasFlow, int o2Percent, Vector cellsBrokenInAl){
        AFBS = beltSpeed;
        AFDT = dryingTemp;
        AFZ2 = zone2Temp;
        AFGF = gasFlow;
        AFOP = o2Percent;

        for (int i=0; i<cellsBrokenInAl.size(); i++){
            if (((Integer)cellsBrokenInAl.elementAt(i)).intValue() < cellIndex){
              if(40 > Math.random()*100)
                  shuntError = true;
            }
        }
        //(PBAG is probability of breakage during Ag printing)
        //(DOSET is the thermal dose during drying such that if too small, explosive drying during firing leads to increased PBAG)
        DOSET=AFDT/AFBS;
        if (DOSET<30)
	    PBAG=(1-(DOSET/30))*100;  //(as %)
        else
            PBAG=Pi;

        //(insufficient oxygen can lead to pimples on rear and increased PBAG)
        DOSEO=AFGF*AFOP/100;   //(AFOP as %)
        if (DOSEO<1)
	    PBAG=80;    //(as a %)
        
        double tempdouble = 5*(Math.pow(10,19));
        if (ALT>10)
	    PD2 = tempdouble*(Math.pow((AFZ2*0.001),10));
        else
            PD2 = tempdouble*(Math.pow((AFZ2*0.001),10))*(ALT/10);

        //(DOSEFZ is the thermal dose in the firing zone)
        DOSEFZ= Math.pow((AFZ2-700)/100,4)/AFBS;	//(AFBS in cm/s and AFZ2 in degrees C)

        //(series R in base due to DOSEFZ consuming too much Al + R due to base resistivity)
        //(ALT & T2 in microns, RB in ohms)
        RB=((10/ALT)*DOSEFZ) + ((BD*T2*2)/10000);
        if (Double.isInfinite(RB))
            RB = 1000;
        double SiDensity = 2.33;
        double AlDensity = 3.5;//should be al paste density
        double FT0 = 0.122;// si atomic weight percentage of eutectic temperature 577
        double FT800 = 0.3;// si atomic weight percentage of 800'c
        double FT;
        FT = 0.0008*AFZ2 - 0.3;
        JD2 = ALT*AlDensity/SiDensity*(FT/(1-FT)-FT0/(1-FT0));
        if (JD2 < 0)
            JD2 = 0;
        //JD2=10;   //microns //Changed by ly
        //(PBAL is the probability of breakage in % during the Al firing process
        //as a function of belt speed and temperature)
        PBAL= Math.pow((AFBS/100),3)*Math.pow((AFZ2/700),3);

        //(gettering effects on lifetime as function of belt speed and temperature)
        BR=(BR*(100+AFBS)/100)*(AFZ2/700);

        if (PBAL > Math.random()*100)
            cellBroken = true;
        return cellBroken;
    }

    public void silverScreen(double meshDensity, double emulsionThicknessAbove,
            double emulsionThicknessBelow, int fingerSpacing, int strandDiameter, int fingerWidth){
        SMD = (0.0045*meshDensity) - 0.125;
        SETA = emulsionThicknessAbove;
        SETB = emulsionThicknessBelow;
        SSD = (0.01125*strandDiameter) - 0.125;
        SFS = fingerSpacing;
        MW = fingerWidth;
        silverMeshDensity = meshDensity;
    }

    public boolean[] silverScreenPrint(int squeegeePressure, int pasteViscosity, int squeegeeSpeed){
        SSP = 0.1*squeegeePressure;
        SPV = 0.1*pasteViscosity;

        //HRF1 - height reduction factor 1 (of metal fingers)
        HRF1 = (SSP*0.10)/(SMD*SSD*SPV);
        if (HRF1>1)
            HRF1 = 1;

        if (SETB>22)
            SETB = 22-(SETB-22);

        //HRF2 - height reduction factor 2 (of metal fingers)
        if (SETA>6)
            HRF2 = 0.5+(3/SETA);
        else
            HRF2 = 1;

        //MH = metal height
        MH = SETB*HRF1*HRF2;

        //SS
        SS = 1/SMD - SSD;

        //Ps - probability of each cell being 'destroyed' during printing
        if (SS>4)
            Ps = (SS*5)/100;
        else
            Ps =0;

        //Q
        if (MW <= (10000*Math.sqrt(2))/silverMeshDensity)
            Q = ((10000*Math.sqrt(2))/silverMeshDensity) - MW;
        else
            Q = 0;

        //SL1
        if (SPV<0.7)
            FMW = (Q + MW)*(1 + (2*(0.7 - SPV)));
        else
            FMW = MW + Q;

        // SL1 + 0.05 for busbars & *1.05 for smudging of fingers
        SL1 = (1.0*(FMW*0.001)/SFS) + 0.02;//0.3&&1.0 changed by ly, used to be 1.05 and 0.05

        if (PBAG > Math.random()*100)
            cellBroken = true;
        boolean cellDestroyed = false;
        if (Ps > Math.random())
            cellDestroyed = true;
        boolean[] b = {cellBroken, cellDestroyed};
        return b;
    }

    public boolean silverFiring(double beltSpeed, int dryingTemp,
	    int zone1Temp, int zone2Temp, int zone3Temp){

        SFt = 70/(beltSpeed); //in minutes
        SFT2 = zone2Temp;

        // RE = R1 + R2 + R3 + R4
        // R1 = R1A||R1B + R1C
        R1A = 2*Math.sqrt(3e20/PD1)/JD1;

        //MD - metal depth?? (distance metal penetrates)
        double temp = SFT2+273;
        double temp1 = temp/873;
        MD = (Math.sqrt(SFt)/500)*Math.pow(temp1,17);
        R1B = 0.1/(IT*ARH*0.001 + (JD1-MD))*2*Math.sqrt(3e20/PD1);
        if (R1B <= 0)
            R1B = 1000;

        // if metal depth less than AR thickness, metal doesn't reach the Silicon
	if (MD<(IT*ARH*0.001))
		R1C = 80*(1-MD/(IT*ARH*0.001))+20*((1-MD/(IT*ARH*0.001)))*Math.random();
	else
          R1C = 0;

        //R1
        R1 = ((R1A*R1B)/(R1A+R1B)) + R1C;

        //R2
        R2 = getTopSurfaceSheetResistivity()*Math.pow(SFS,2)*(1e-2/12);

        //MH = final metal height
        MH = (SETB/2)*HRF1*HRF2;  // * V - V varies from 0.9-1.1 using RNG for each cell

        //QQ
        if (SFT2>720){
            QQ = 1;
        }
        else{
            if (SFT2>650)
                QQ = 1+((720-SFT2)/70);
            else
                QQ = 2+((650-SFT2)/5);
        }

        //R3
        R3 = ((SFS*200)/(MH*MW))*QQ;

        //R4
        if (SS <= 2.6)
            R4 = (2.6-SS)/2;
        else
            R4 = 0;

        //RE
        RE = R1+R2+R3+R4;
        if (Double.isInfinite(RE))
            RE = 1000;

        if (Pi > Math.random()*100)
            cellBroken = true;
        return cellBroken;
    }

    public boolean cofiring(double beltSpeed, int dryingTemp,
	    int zone1Temp, int zone2Temp, int zone3Temp,
            double gasFlow, int o2Percent, Vector cellsBrokenInAl){
        AFBS = beltSpeed;
        AFDT = dryingTemp;
        AFZ2 = zone2Temp;
        AFGF = gasFlow;
        AFOP = o2Percent;

        for (int i=0; i<cellsBrokenInAl.size(); i++){
            if (((Integer)cellsBrokenInAl.elementAt(i)).intValue() < cellIndex){
              if(40 > Math.random()*100)
                  shuntError = true;
            }
        }
        //(PBAG is probability of breakage during IV testing)
        //(DOSET is the thermal dose during drying such that if too small, explosive drying during firing leads to increased PBAG)
        DOSET=AFDT/AFBS;
        if (DOSET<30)
	    PBAG=(1-(DOSET/30))*100;  //(as %)
        else
            PBAG=Pi;

        //(insufficient oxygen can lead to pimples on rear and increased PBAG)
        DOSEO=AFGF*AFOP/100;   //(AFOP as %)
        if (DOSEO<1)
        	PBAG=80;    //(as a %)

        double tempdouble = 5*(Math.pow(10,19));
        if (ALT>10)
        	PD2 = tempdouble*(Math.pow((AFZ2*0.001),10));
        else
            PD2 = tempdouble*(Math.pow((AFZ2*0.001),10))*(ALT/10);

        //(DOSEFZ is the thermal dose in the firing zone)
        DOSEFZ= Math.pow((AFZ2-700)/100,4)/AFBS;	//(AFBS in cm/s and AFZ2 in degrees C)

        //(series R in base due to DOSEFZ consuming too much Al + R due to base resistivity)
        //(ALT & T2 in microns, RB in ohms)
        RB=((10/ALT)*DOSEFZ) + ((BD*T2)/10000);
        if (Double.isInfinite(RB))
            RB = 1000;
        double SiDensity = 2.33;
        double AlDensity = 3.5;//should be al paste density
        double FT0 = 0.122;// si atomic weight percentage of eutectic temperature 577
        double FT800 = 0.3;// si atomic weight percentage of 800'c
        double FT;
        FT = 0.0008*AFZ2 - 0.3;
        JD2 = ALT*AlDensity/SiDensity*(FT/(1-FT)-FT0/(1-FT0));
        if (JD2 < 0)
            JD2 = 0;
        //if (JD2 > 12)
        //    JD2 = 12;
        //JD2=10;   //microns changed by ly
        //(PBAL is the probability of breakage in % during the Al firing process
        //as a function of belt speed and temperature)
        PBAL= Math.pow((AFBS/100),3)*Math.pow((AFZ2/700),3);

        //(gettering effects on lifetime as function of belt speed and temperature)
        BR=(BR*(100+AFBS)/100)*(AFZ2/700);

        SFt = 70/(beltSpeed); //in minutes
        SFT2 = zone2Temp;

        // RE = R1 + R2 + R3 + R4
        // R1 = R1A||R1B + R1C
        R1A = 2*Math.sqrt(3e20/PD1)/JD1;

        //MD - metal depth?? (distance metal penetrates)
        double temp = SFT2+273;
        double temp1 = temp/873;
        MD = (Math.sqrt(SFt)/500)*Math.pow(temp1,17);
        R1B = 0.1/(IT*ARH*0.001 + (JD1-MD))*2*Math.sqrt(3e20/PD1);
        if (R1B <= 0)
            R1B = 1000;

        // if metal depth less than AR thickness, metal doesn't reach the Silicon
	if (MD<(IT*ARH*0.001))
		R1C = 80*(1-MD/(IT*ARH*0.001))+20*((1-MD/(IT*ARH*0.001)))*Math.random();
	else
          R1C = 0;

        //R1
        R1 = 0.5 * ((R1A*R1B)/(R1A+R1B)) + R1C;

        //R2
        R2 = getTopSurfaceSheetResistivity() * SFS /600;

        //MH = final metal height
        MH = (SETB/2)*HRF1*HRF2;  // * V - V varies from 0.9-1.1 using RNG for each cell

        //QQ
        if (SFT2>720){
            QQ = 1;
        }
        else{
            if (SFT2>650)
                QQ = 1+((720-SFT2)/70);
            else
                QQ = 2+((650-SFT2)/5);
        }

        //R3
        R3 = 133/(MH*MW)*QQ;

        //R4
        if (SS <= 2.6)
            R4 = (2.6-SS)/2;
        else
            R4 = 0;

        //RE
        RE = R1+R2+R3+R4;
        if (Double.isInfinite(RE))
            RE = 1000;

        if (PBAL > Math.random()*100)
            cellBroken = true;
        return cellBroken;
    }

    public double getTopSurfaceSheetResistivity(){
    	//System.out.println(Rhos);
    if (Rhos == 0.0){
        String graphtype = " -gt 9";
        String bd = " -bd " + BD;
        String pd1 = " -pd1 " + PD1;
        String jd1 = " -jd1 " + JD1;
        String ri = " -fei_i " + RI;
        //String commandString = System.getProperty("user.dir")
        String commandString = pc1dExe
                            + graphtype
                            +bd+pd1+jd1+ri;

        try {
            //run pc1d
            Process p = Runtime.getRuntime().exec(commandString,
                    null,
                    new File(pc1dFolder));
            p.waitFor();
        } catch (IOException e) {
            System.out.println( "An IO exception occurred while executing the cmd <" + commandString + ">:" + e.getMessage() );
            e.printStackTrace() ;
        } catch (InterruptedException e){
            System.out.println( "An Interrupted Exception occurred while executing the cmd <" + commandString + ">:" + e.getMessage() );
        }

        //String filename = System.getProperty("user.dir")+ System.getProperty("file.separator") + "pc1d.dat";
        String filename = pc1dFolder + System.getProperty("file.separator") + "pc1d.dat";

        try{
	    FileInputStream in = new FileInputStream(filename);
            int c;
            StringBuffer numString = new StringBuffer(10);
            while ((c = in.read()) !=10){
                numString.append((char)c);
            }
            Rhos = Double.parseDouble(numString.toString());
        	//System.out.println(Rhos);
            in.close();
  	}
	catch(IOException e){
            System.out.println("io exception");
        }
    }
    //System.out.println(Rhos);
    return Rhos;
    }

    public double getVoc(double shuntr){
        double Voc = 0.0;
        String graphtype = " -gt 8";
        String a = " -a " + A;
        String bbr = " -bbr " + BBR*0.01;
        String it = " -it " +  IT;
        String rs = " -rs " +  RS*0.01;
        String re = " -re " + RE;
        String rb = " -rb " + RB;
        String rsh = " -rsh " + shuntr;
        String sd = " -sd " + SD;
        String t1 = " -t1 " + T1;
        String bd = " -bd " + BD;
        String fsrv = " -fsrv " + FSRV;
        String t2 = " -t2 " + T2;
        String pd1 = " -pd1 " + PD1;
        String jd1 = " -jd1 " + JD1;
        String br = " -br " + BR;
        String jd2 = " -jd2 " + JD2;
        String pd2 = " -pd2 " + PD2;
	String ri = " -fei_i " + RI;
        //String commandString = System.getProperty("user.dir")
        String commandString = pc1dExe
                            + graphtype
                            +a+bbr+it+rs+re+rb+rsh+sd+t1+bd+fsrv+t2+pd1+jd1+br+jd2+pd2+ri;
        try {
            //run pc1d
            Process p = Runtime.getRuntime().exec(commandString);
            p.waitFor();
        } catch (IOException e) {
            System.out.println( "An IO exception occurred while executing the cmd <" + commandString + ">:" + e.getMessage() );
            e.printStackTrace() ;
        } catch (InterruptedException e){
            System.out.println( "An Interrupted Exception occurred while executing the cmd <" + commandString + ">:" + e.getMessage() );
        }

        //String filename = System.getProperty("user.dir") + System.getProperty("file.separator") + "pc1d.dat";
        String filename = pc1dFolder + System.getProperty("file.separator") + "pc1d.dat";
        try{
	    FileInputStream in = new FileInputStream(filename);
            int c;
            StringBuffer numString = new StringBuffer(10);
            while ((c = in.read()) !=10){
                numString.append((char)c);
            }
            Voc = Double.parseDouble(numString.toString());
            in.close();
  	}
	catch(IOException e){
            System.out.println("io exception");
        }
        return Voc;
    }
}
