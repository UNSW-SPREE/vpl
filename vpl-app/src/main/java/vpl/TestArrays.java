package vpl;
import java.lang.reflect.Array;
import javax.swing.*;
import java.util.Vector;
import javax.swing.event.*;
import java.awt.event.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) Anna Bruce
 * Company:      UNSW PV Engineering
 * @author Anna Bruce
 * @version 1.0
 */

public class TestArrays{
    Vector baseTestVector = new Vector();
    Vector baseTestTypeVector = new Vector();
    Vector testTypeVector;

    String string0 = "(Incoming Wafer Tests)";
    String string1 = "(Post Wafer Etch Tests)";
    String string2 = "(Post Texture Tests)";
    String string3 = "(Post Diffusion Tests)";
    String string4 = "(Post Al Screen Print Tests)";
    String string5 = "(Post Al Firing Tests)";
    String string6 = "(Post Plasma Etch Tests)";
    String string7 = "(Post AR Coating Tests)";
    String string8 = "(Post Silver Screen Print Tests)";
    String string9 = "(Finished Cell Tests)";

    public TestArrays() {
        baseTestVector.addElement("Thickness");
        baseTestVector.addElement("Resistivity");
        baseTestVector.addElement("Minority Carrier Lifetime");
        //changed by ly
        //baseTestVector.addElement("Wafer Values"); //this is wafer values

        baseTestTypeVector.addElement("t");
        baseTestTypeVector.addElement("t");
        baseTestTypeVector.addElement("t");
        //changed by ly
        //baseTestTypeVector.addElement("t"); //this is wafer values
    }

    public Vector getTestVector(int batchStatus, boolean cofiringBatch,
                                boolean arCoating, boolean textured){
        Vector testVector = new Vector();
        testTypeVector = new Vector();

        if (cofiringBatch == false) {
            switch (batchStatus){
              case 1: testVector.addElement(string0); break;
              // added by Bobh
              case 21: testVector.addElement(string0); break;
              case 22: testVector.addElement(string1); break;
              case 23: testVector.addElement(string1); break;
                // added end Bobh

              case 2: testVector.addElement(string1); break;
              case 3: testVector.addElement(string1); break;
              case 4:
                if (textured){
                  testVector.addElement(string2);
                  break;
                }
                else{
                  testVector.addElement(string1);
                  break;
                }
              case 5:
                if (textured){
                  testVector.addElement(string2);
                  break;
                }
                else{
                  testVector.addElement(string1);
                  break;
                }
              case 6: testVector.addElement(string3); break;
              case 7: testVector.addElement(string3); break;
              case 8: testVector.addElement(string4); break;
              case 9: testVector.addElement(string5); break;
              case 10: testVector.addElement(string5); break;
              case 11: testVector.addElement(string6); break;
              case 12: testVector.addElement(string6); break;
              case 13:
                if (arCoating){
                  testVector.addElement(string7);
                  break;
                }
                else{
                  testVector.addElement(string6); break;
                }
              case 14:
                if (arCoating){
                  testVector.addElement(string7);
                  break;
                }
                else{
                  testVector.addElement(string6); break;
                }
              case 15: testVector.addElement(string8); break;
              case 16: testVector.addElement(string9); break;
            }
                testTypeVector.addElement(" ");

                testVector.addAll(baseTestVector);
                testTypeVector.addAll(baseTestTypeVector);

            if ((batchStatus > 1 && batchStatus < 6) || (batchStatus > 21 && batchStatus < 24) ){
                testVector.addElement("Reflection Test");
                testTypeVector.addElement("g");
            }
            if (batchStatus > 3 && textured){
                testVector.addElement("Pyramid Size Visual Inspection");
                testTypeVector.addElement("t");
                testVector.addElement("Pyramid Coverage Visual Inspection");
                testTypeVector.addElement("t");
            }
            if ((batchStatus > 5) && !(batchStatus > 20 && batchStatus < 24)){
                testVector.addElement("Reflection & QE Spectral Response");
                testTypeVector.addElement("g");
                testVector.addElement("Doping Profile");
                testTypeVector.addElement("g");
                testVector.addElement("Sheet Resistivity");
                testTypeVector.addElement("t");
                testVector.addElement("Pseudo IV Curve");
                testTypeVector.addElement("g");
            }
            if (batchStatus > 7 && batchStatus < 9){
                testVector.addElement("Al Paste Thickness");
                testTypeVector.addElement("t");
            }
            if ((batchStatus > 8) && !(batchStatus > 20 && batchStatus < 24)){
                testVector.addElement("Open Circuit Voltage Contour Map");
                testTypeVector.addElement("v");
            }
            if (batchStatus > 12 && arCoating){
               testVector.addElement("AR Coating Thickness");
                testTypeVector.addElement("t");
               testVector.addElement("AR Coating Refractive Index");
                testTypeVector.addElement("t");
            }
            if ((batchStatus > 14) && !(batchStatus > 20 && batchStatus < 24)){
                testVector.addElement("Silver Paste Thickness");
                testTypeVector.addElement("t");
            }
            if ((batchStatus > 15) && !(batchStatus > 20 && batchStatus < 24)){
                testVector.addElement("Silver Resistance");
                testTypeVector.addElement("t");
                testVector.addElement("Series Resistance");
                testTypeVector.addElement("t");
                testVector.addElement("Shunt Resistance");
                testTypeVector.addElement("t");
                testVector.addElement("IV Curve");
                testTypeVector.addElement("g");
            }
            return testVector;
        }
        else{
          switch (batchStatus){
            case 1: testVector.addElement(string0); break;
           // added by Bobh
            case 21: testVector.addElement(string0); break;
            case 22: testVector.addElement(string1); break;
            case 23: testVector.addElement(string1); break;
           // added end Bobh
            case 2: testVector.addElement(string1); break;
            case 3: testVector.addElement(string1); break;
            case 4:
              if (textured){
                testVector.addElement(string2);
                break;
              }
              else{
                testVector.addElement(string1);
                break;
              }
            case 5:
              if (textured){
                testVector.addElement(string2);
                break;
              }
              else{
                testVector.addElement(string1);
                break;
              }
            case 26: testVector.addElement(string3); break;
            case 27: testVector.addElement(string3); break;
            case 28: testVector.addElement(string6); break;
            case 29: testVector.addElement(string6); break;
            case 30:
              if (arCoating){
                testVector.addElement(string7);
                break;
              }
              else{
                testVector.addElement(string6); break;
              }
            case 31:
              if (arCoating){
                testVector.addElement(string7);
                break;
              }
              else{
                testVector.addElement(string6); break;
              }
            case 32: testVector.addElement(string8); break;
            case 33: testVector.addElement(string8); break;
            case 34: testVector.addElement(string4); break;
            case 16: testVector.addElement(string9); break;
          }
              testTypeVector.addElement(" ");

              testVector.addAll(baseTestVector);
              testTypeVector.addAll(baseTestTypeVector);

            if ((batchStatus > 1 && batchStatus < 6) || (batchStatus > 21 && batchStatus < 24)){
                testVector.addElement("Reflection Test");
                testTypeVector.addElement("g");
            }
            if (batchStatus > 3 && textured){
                testVector.addElement("Pyramid Size Visual Inspection");
                testTypeVector.addElement("t");
                testVector.addElement("Pyramid Coverage Visual Inspection");
                testTypeVector.addElement("t");
            }
            if ((batchStatus > 5) && !(batchStatus > 20 && batchStatus < 24)){
                testVector.addElement("Reflection & QE Spectral Response");
                testTypeVector.addElement("g");
                testVector.addElement("Doping Profile");
                testTypeVector.addElement("g");
                testVector.addElement("Sheet Resistivity");
                testTypeVector.addElement("t");
                testVector.addElement("Pseudo IV Curve");
                testTypeVector.addElement("g");
            }
            if ((batchStatus > 28 || batchStatus == 16) && arCoating){
               testVector.addElement("AR Coating Thickness");
                testTypeVector.addElement("t");
               testVector.addElement("AR Coating Refractive Index");
                testTypeVector.addElement("t");
            }
            if (batchStatus > 31 || batchStatus == 16){
                testVector.addElement("Silver Paste Thickness");
                testTypeVector.addElement("t");
            }
            if (batchStatus > 33){
                testVector.addElement("Al Paste Thickness");
                testTypeVector.addElement("t");
            }
            if (batchStatus == 16){
                testVector.addElement("Open Circuit Voltage Contour Map");
                testTypeVector.addElement("v");
                testVector.addElement("Silver Resistance");
                testTypeVector.addElement("t");
                testVector.addElement("Series Resistance");
                testTypeVector.addElement("t");
                testVector.addElement("Shunt Resistance");
                testTypeVector.addElement("t");
                testVector.addElement("IV Curve");
                testTypeVector.addElement("g");
            }
            return testVector;
        }

    }

    public Vector getTestTypeVector(){
        return testTypeVector;
    }

}
