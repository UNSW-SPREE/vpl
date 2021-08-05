package vpl;
import java.util.*;
import java.io.*;
import java.text.DateFormat;
import javax.swing.*;
import java.text.NumberFormat;
import vpl.Cost;
/**
 * Title:        Virtual Production Line<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Anna Bruce<p>
 * Company:      UNSW PV Engineering<p>
 * @author Anna Bruce
 * @version 1.0
 */

public class Batch implements Serializable{
    public int noOfCells = 0;
    public Vector<Wafer> batchCells;
    public Vector<Cost> batchCellCosts;
    public Vector brokenCells;
    public int assignmentNo;
    public String batchName;
    public Vector cellNames;
    public Vector batchHistory;
    public Vector batchHistoryDetails;
    public int batchStatus;
    // added by Bobh for multiCBatch;
    public boolean multiCBatch = false;
    // end add Bobh
    public boolean cofiringBatch;
    public boolean arc = false;
    public boolean texture = false;
    private Vector cellsBrokenInAlProcess;
    public boolean special;
//    public LimitedStyledDocument batchNotes;
    public String bnotes;
    public File directory = null;
    public boolean batchChanged = false;
    //Averages
    public double totalMaxPower = 0;
    public double totalVoc = 0;
    public double totalJsc = 0;
    public double totalFillFactor = 0;
    public double totalSeriesResistance = 0;
    public double totalShuntResistance = 0;
    public double totalCost = 0;
    public double costOfOwnership = 0;

    public Batch(String type, String quality, int noOfCells, int thickness, double resistivity,
        String surfaceFinish, int assNo){

        this.noOfCells = noOfCells;
        batchCells = new Vector<Wafer>();
        batchCellCosts = new Vector<Cost>();
        brokenCells = new Vector();
        assignmentNo = assNo;
        batchName = "unnamed";
        Vector names = new Vector();
        for (int i=0; i<noOfCells; i++){
            Wafer wafer = new Wafer(i, thickness, resistivity, surfaceFinish, type, quality);
            Cost cost = new Cost();
            batchCells.addElement(wafer);
            batchCellCosts.addElement(cost);
            names.addElement(wafer.cellName);
        }
        cellNames = names;
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
        String createdHistory = "created on " + date.format(new Date()) + "  " + time.format(new Date());
        batchHistory = new Vector();
        batchHistory.addElement(createdHistory);
        Settings settings = new Settings("default");
        String[] labels = (settings.incomingArray);
        String[] units = (settings.incomingUnitsArray);
        String[] details = {labels[0] + ": " + type + " " + units[0],
                            labels[1] + ": " + quality + " " + units[1],
                            labels[2] + ": " + thickness + " " + units[2],
                            labels[3] + ": " + resistivity + " " + units[3],
                            labels[4] + ": " + surfaceFinish + " " + units[4]};
        batchHistoryDetails = new Vector();
        batchHistoryDetails.addElement(details);
        // added single crystalline or multicrystalline
        if (type == "multicrystalline"){
            multiCBatch = true;
            batchStatus = 21;
            } else
        {   batchStatus = 1;
                      }

//        batchNotes = new LimitedStyledDocument(10000);
        batchChanged = true;
    }

    public Batch(String newBatchName, Vector batchHistory, Vector batchHistoryDetails, Vector cells,
        int assignmentNo, Vector cellNames, int batchStatus, boolean cofiringBatch, boolean arc, boolean texture)
    {
        this.noOfCells = cells.size();
        this.batchCells = cells;
        this.batchStatus = batchStatus;
        this.assignmentNo = assignmentNo;
        this.batchName = newBatchName;
        this.cellNames = cellNames;
        this.batchHistory = batchHistory;
        this.batchHistoryDetails = batchHistoryDetails;
//        this.batchNotes = new LimitedStyledDocument(10000);
        this.cofiringBatch = cofiringBatch;
        this.arc = arc;
        this.texture = texture;
    }

    public void createTextFile(VirtualProductionLine vpl){
        NumberFormat format = NumberFormat.getNumberInstance();
	try {
    	    // saveFileDialog Show the FileDialog
            vpl.textfileDialog.rescanCurrentDirectory();
            Batch myBatch = (Batch)vpl.batches.elementAt(vpl.cbIndex);
            vpl.textfileDialog.setSelectedFile(null);
	    int result = vpl.textfileDialog.showSaveDialog(vpl);
	    if (result == JFileChooser.CANCEL_OPTION)
	        return;
	    File fileName = vpl.textfileDialog.getSelectedFile();
            String nm = fileName.toString();
            if (nm.substring(nm.length()-4, nm.length()).equals(".txt")){
            }
            else{
                fileName = new File(fileName.toString() + ".txt");
            }
            if (vpl.textfileDialog.getSelectedFile().exists()){
                int selectedValue = JOptionPane.showConfirmDialog(vpl,"Do you want to overwrite the existing " + fileName.getName() + "?",
                    "Overwrite File?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (selectedValue == JOptionPane.CANCEL_OPTION)
                    return;
            }
  	    if (fileName == null || fileName.getName().equals("")){
	        JOptionPane.showMessageDialog(vpl,"Invalid File Name",
			    "Invalid File Name",JOptionPane.ERROR_MESSAGE);
            }
	    else {
                TestResultsPanel trp = new TestResultsPanel(this);
                GraphListPanel glp = new GraphListPanel(vpl,this);
                String lineSep = System.getProperty("line.separator");
                String[] detailsArray;
                Wafer w;
                FileWriter out = new FileWriter(fileName);
                    out.write(batchName);
                    out.write(lineSep);
                    if (assignmentNo != -1){
                        out.write("**** Assignment No " + assignmentNo + " (" + vpl.defaultSettings.assignmentStrings[assignmentNo-1] + ") ****");
                        out.write(lineSep);
                    }
                    calculateTotals();
                    if (totalVoc !=0){
                        out.write(lineSep);
                        Double d = new Double(totalVoc/(noOfCells + brokenCells.size())*1000);
                        format.setMaximumFractionDigits(0);
	                format.setMinimumFractionDigits(0);
                        out.write("Avg Voc = " + format.format(d) + " mV");
                    }
                    if (totalJsc !=0){
                        Double d = new Double(totalJsc/(noOfCells + brokenCells.size())*1000);
                        out.write(lineSep);
                        format.setMaximumFractionDigits(1);
	                format.setMinimumFractionDigits(1);
                        out.write("Avg Jsc = " + format.format(d) + " mA/cm2");
                    }
                    format.setMaximumFractionDigits(2);
	            format.setMinimumFractionDigits(2);
                    if (totalMaxPower !=0){
                        Double d = new Double(totalMaxPower/(noOfCells + brokenCells.size())*1000);
                        out.write(lineSep);
                        out.write("Avg Max Power = " + format.format(d) + " mW/cm2");
                    }
                    if (totalFillFactor !=0){
                        Double d = new Double(totalFillFactor/(noOfCells + brokenCells.size()));
                        out.write(lineSep);
                        out.write("Avg Fill Factor = " + format.format(d));
                    }
                    out.write(lineSep);
                    if (batchHistory.size()>0){
                        for (int i=0; i<batchHistory.size(); i++){
                            out.write((String)batchHistory.elementAt(i));
                            out.write(lineSep);
                            detailsArray = (String[])batchHistoryDetails.elementAt(i);
                            for (int j=0; j<detailsArray.length; j++){
                                out.write((String)detailsArray[j]);
                                out.write(lineSep);
                            }
                            out.write(lineSep);
                        }
                    }
                    if (brokenCells.size() >0){
                        out.write("Broken Cells");
                        out.write(lineSep);
                        for (int j=0; j<brokenCells.size(); j++){
                            out.write(((Wafer)brokenCells.elementAt(j)).cellName);
                            out.write(lineSep);
                        }
                        out.write(lineSep);
                    }
                    out.write("Test Results");
                    out.write(lineSep);
                    if (trp.incomingWaferTests.size()!=0){
                        sortTests(trp.string0, trp.incomingWaferTests, out);
                    }
                    if (trp.postEtchTests.size()!=0){
                        sortTests(trp.string1, trp.postEtchTests, out);
                    }
                    if (trp.postTextureTests.size()!=0){
                        sortTests(trp.string2, trp.postTextureTests, out);
                    }
                    if (trp.postDiffusionTests.size()!=0){
                        sortTests(trp.string3, trp.postDiffusionTests, out);
                    }
                    if (trp.postAlScreenPrintTests.size()!=0){
                        sortTests(trp.string4, trp.postAlScreenPrintTests, out);
                    }
                    if (trp.postAlFiringTests.size()!=0){
                        sortTests(trp.string5, trp.postAlFiringTests, out);
                    }
                    if (trp.postPlasmaEtchTests.size()!=0){
                        sortTests(trp.string6, trp.postPlasmaEtchTests, out);
                    }
                    if (trp.postArCoatingTests.size()!=0){
                        sortTests(trp.string7, trp.postArCoatingTests, out);
                    }
                    if (trp.postSilverScreenPrintTests.size()!=0){
                        sortTests(trp.string8, trp.postSilverScreenPrintTests, out);
                    }
                    if (trp.cfpostPlasmaEtchTests.size()!=0){
                        sortTests(trp.string6, trp.cfpostPlasmaEtchTests, out);
                    }
                    if (trp.cfpostArCoatingTests.size()!=0){
                        sortTests(trp.string7, trp.cfpostArCoatingTests, out);
                    }
                    if (trp.cfpostSilverScreenPrintTests.size()!=0){
                        sortTests(trp.string8, trp.cfpostSilverScreenPrintTests, out);
                    }
                    if (trp.cfpostAlScreenPrintTests.size()!=0){
                        sortTests(trp.string4, trp.cfpostAlScreenPrintTests, out);
                    }

                    if (trp.finishedCellTests.size()!=0){
                        sortTests(trp.string9, trp.finishedCellTests, out);
                    }
                    out.write(lineSep);
                    out.write("Graphed Results");
                    out.write(lineSep);
                    if (glp.incomingWaferGraphs.size()!=0){
                        sortGraphs(glp.string0, glp.incomingWaferGraphs,0, (Vector)glp.graphDatas[0], out);
                    }
                    if (glp.postEtchGraphs.size()!=0){
                        sortGraphs(glp.string1, glp.postEtchGraphs,1, (Vector)glp.graphDatas[1], out);
                    }
                    if (glp.postTextureGraphs.size()!=0){
                        sortGraphs(glp.string2, glp.postTextureGraphs,2, (Vector)glp.graphDatas[2], out);
                    }
                    if (glp.postDiffusionGraphs.size()!=0){
                        sortGraphs(glp.string3, glp.postDiffusionGraphs,3, (Vector)glp.graphDatas[3], out);
                    }
                    if (glp.postAlScreenPrintGraphs.size()!=0){
                        sortGraphs(glp.string4, glp.postAlScreenPrintGraphs,4, (Vector)glp.graphDatas[4], out);
                    }
                    if (glp.postAlFiringGraphs.size()!=0){
                        sortGraphs(glp.string5, glp.postAlFiringGraphs,5, (Vector)glp.graphDatas[5], out);
                    }
                    if (glp.postPlasmaEtchGraphs.size()!=0){
                        sortGraphs(glp.string6, glp.postPlasmaEtchGraphs,6, (Vector)glp.graphDatas[6], out);
                    }
                    if (glp.postArCoatingGraphs.size()!=0){
                        sortGraphs(glp.string7, glp.postArCoatingGraphs,7, (Vector)glp.graphDatas[7], out);
                    }
                    if (glp.postSilverScreenPrintGraphs.size()!=0){
                        sortGraphs(glp.string8, glp.postSilverScreenPrintGraphs,8, (Vector)glp.graphDatas[8], out);
                    }

                    if (glp.cfpostPlasmaEtchGraphs.size()!=0){
                        sortGraphs(glp.string6, glp.cfpostPlasmaEtchGraphs,10, (Vector)glp.graphDatas[10], out);
                    }
                    if (glp.cfpostArCoatingGraphs.size()!=0){
                        sortGraphs(glp.string7, glp.cfpostArCoatingGraphs,11, (Vector)glp.graphDatas[11], out);
                    }
                    if (glp.cfpostSilverScreenPrintGraphs.size()!=0){
                        sortGraphs(glp.string8, glp.cfpostSilverScreenPrintGraphs,12, (Vector)glp.graphDatas[12], out);
                    }
                    if (glp.cfpostAlScreenPrintGraphs.size()!=0){
                        sortGraphs(glp.string4, glp.cfpostAlScreenPrintGraphs,13, (Vector)glp.graphDatas[13], out);
                    }
                    if (glp.finishedCellGraphs.size()!=0){
                        sortGraphs(glp.string9, glp.finishedCellGraphs,9, (Vector)glp.graphDatas[9], out);
                    }

                    out.close();
            }
	} catch (Exception e) {
	}

    }

    public void sortTests(String string, Vector tests, FileWriter out){
        String[] array;
        String lineSep = System.getProperty("line.separator");
        try {
            out.write(string);
            out.write(lineSep);
            out.write("Wafer Name");
            out.write("\t");
            out.write("Test Name");
            out.write("\t");
            out.write("Result");
            out.write("\t");
            out.write("Time");
            out.write(lineSep);
            for (int k=0; k<tests.size(); k++){
                array = (String[])tests.elementAt(k);
                out.write(array[0]);
                out.write("\t");
                out.write(array[1]);
                out.write("\t");
                out.write(array[2]);
                out.write("\t");
                out.write(array[3]);
                out.write(lineSep);
            }
            out.write(lineSep);
        }
        catch (IOException e){
        }
    }

    public void sortGraphs(String string, Vector graphs, int index, Vector vector, FileWriter out){
//        Vector vector = new Vector();
        String lineSep = System.getProperty("line.separator");
        NumberFormat format = NumberFormat.getNumberInstance();
        try {
            if (graphs.size()>0){
                Object[][] tempObject = new Object[graphs.size()][4];
                graphs.copyInto(tempObject);
                for (int k=0; k<graphs.size(); k++){
                    out.write(string);
                    out.write(lineSep);
                    out.write("Wafer Name");
                    out.write("\t");
                    out.write("Graph Name");
                    out.write("\t");
                    out.write("Time");
                    out.write(lineSep);
                    out.write((String)tempObject[k][0]);
                    out.write("\t");
                    out.write((String)tempObject[k][1]);
                    out.write("\t");
                    out.write((String)tempObject[k][3]);
                    out.write(lineSep);
//                    vector = (Vector)glp.graphDatas[index];
                    GraphData graphData = (GraphData)vector.elementAt(((Integer)tempObject[k][2]).intValue());
                    if (graphData.graphName.equals("IV Curve")){
                        double voc = graphData.voc;
                        double isc = graphData.isc;
                        double maxpower = graphData.maxPower;
                        double fillfactor = maxpower/(voc*isc);
                        out.write("Voc");
                        out.write("\t");
                        format.setMaximumFractionDigits(0);
                        format.setMinimumFractionDigits(0);
                        out.write(format.format(voc*1000) + " mV");
                        out.write(lineSep);
                        out.write("Jsc");
                        out.write("\t");
                        format.setMaximumFractionDigits(1);
                        format.setMinimumFractionDigits(1);
                        out.write(format.format(isc*1000) + " mA/cm\u00B2");
                        out.write(lineSep);
                        out.write("MaxPower");
                        out.write("\t");
                        format.setMaximumFractionDigits(2);
                        format.setMinimumFractionDigits(2);
                        out.write(format.format(maxpower*1000) + " mW/cm\u00B2");
                        out.write(lineSep);
                        out.write("Efficiency");
                        out.write("\t");
                        out.write(format.format(maxpower*1000) + " %");
                        out.write(lineSep);
                        out.write("FillFactor");
                        out.write("\t");
                        out.write(format.format(fillfactor));
                        out.write(lineSep);
                    }
                    out.write(graphData.xaxisname);
                    out.write("\t");
                    if (graphData.ypoints.size()>0){
                        out.write(graphData.yaxisname);
                        out.write("\t");
                    }
                    if (graphData.zpoints.size()>0){
                        out.write(graphData.zaxisname);
                        out.write("\t");
                    }
                    if (graphData.wpoints.size()>0){
                        out.write(graphData.waxisname);
                        out.write("\t");
                    }
                    if (graphData.vpoints.size()>0){
                        out.write(graphData.vaxisname);
                        out.write(lineSep);
                    }
                    out.write(lineSep);
                    for (int m=0; m<graphData.xpoints.size(); m++){
                        out.write(((Double)graphData.xpoints.elementAt(m)).toString());
                        out.write("\t");
                        if (graphData.ypoints.size()>0){
                            out.write(((Double)graphData.ypoints.elementAt(m)).toString());
                            out.write("\t");
                        }
                        if (graphData.zpoints.size()>0){
                            out.write(((Double)graphData.zpoints.elementAt(m)).toString());
                            out.write("\t");
                        }
                        if (graphData.wpoints.size()>0){
                            out.write(((Double)graphData.wpoints.elementAt(m)).toString());
                            out.write("\t");
                        }
                        if (graphData.vpoints.size()>0){
                            out.write(((Double)graphData.vpoints.elementAt(m)).toString());
                            out.write("\t");
                        }
                        out.write(lineSep);
                    }
                }
            out.write(lineSep);
            }
            out.write(lineSep);
        }
        catch (IOException e){
            System.out.println("Invalid Format");
        }
    }



    public Vector runTest(String testName, int cellIndexArray[]){
        Vector results = new Vector();
        for (int i=0; i<cellIndexArray.length; i++){
            int j = cellIndexArray[i];
		    Wafer cell = (Wafer)batchCells.elementAt(j);
		    String resultString = cell.runTest(testName, batchStatus);
		    results.add(resultString);
                    batchChanged = true;
        }
        return results;
    }

    public String visualInspection(int mycell, VirtualProductionLine vpl){
	Wafer cell = (Wafer)batchCells.elementAt(mycell);
        if (cell.visualStages.contains(new Integer(batchStatus))){
            JOptionPane.showMessageDialog(vpl,"This Visual Inspection has already been performed","Redundant Inspection",
         			    JOptionPane.ERROR_MESSAGE);
        }
        else{
            String resultImage = cell.visualInspection(batchStatus);
            batchChanged = true;
            return resultImage;
        }
        return null;
    }

    public GraphData runGraph(String graphName, int mycell, VirtualProductionLine vpl){
	Wafer cell = (Wafer)batchCells.elementAt(mycell);
        String gn = graphName;
/*        for (int i=0; i<cell.graphDatas.size(); i++){
            if (((GraphData)cell.graphDatas.elementAt(i)).graphName.equals(gn) &&
                ((Integer)cell.graphStages.elementAt(i)).intValue() == batchStatus){
                JOptionPane.showMessageDialog(vpl,"This Graph has already been performed","Redundant Test",
         			    JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }*/
        GraphData graphData = cell.runGraph(gn, batchStatus);
        if (graphName.equals("IV Curve") && (graphData == null)){
                JOptionPane.showMessageDialog(vpl, cell.cellName +
                          " has been broken during IV Testing\nthis cell will be removed from the batch",
                          "Broken Cell", JOptionPane.INFORMATION_MESSAGE);
                brokenCells.addElement(batchCells.elementAt(mycell));
                batchCells.removeElementAt(mycell);
                cellNames.removeElementAt(mycell);
                noOfCells = batchCells.size();
            if (noOfCells == 0){
                JOptionPane.showMessageDialog(vpl,"All the wafers in " + batchName + " have been broken.\nThis batch cannot be processed.",
                          "No Wafers to Process", JOptionPane.INFORMATION_MESSAGE);
                batchStatus = -2;
            }
        }
        batchChanged = true;
        return graphData;
    }

    public GraphData runVocTest(String graphName, int mycell, VirtualProductionLine vpl){
	Wafer cell = (Wafer)batchCells.elementAt(mycell);
        String gn = graphName;
/*        for (int i=0; i<cell.graphDatas.size(); i++){
            if (((GraphData)cell.graphDatas.elementAt(i)).graphName.equals(gn) &&
                ((Integer)cell.graphStages.elementAt(i)).intValue() == batchStatus){
                JOptionPane.showMessageDialog(vpl,"This Graph has already been performed","Redundant Test",
         			    JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }*/
        GraphData graphData = cell.runVocTest(gn, batchStatus);
            if ((batchStatus > 14 && batchStatus < 17) || (batchStatus > 31)){
                brokenCells.addElement(batchCells.elementAt(mycell));
                batchCells.removeElementAt(mycell);
                cellNames.removeElementAt(mycell);
                noOfCells = batchCells.size();
            }
        batchChanged = true;
        return graphData;
    }

    void finish(VirtualProductionLine vpl){
        int j = 0;
        for (int i = 0; i < noOfCells; ++i) {
                Wafer cell = (Wafer)batchCells.elementAt(j);
                String gn = "IV Curve";
                boolean done = false;
                for (int k=0; k<cell.graphDatas.size(); k++){
                    if (((GraphData)cell.graphDatas.elementAt(k)).graphName.equals(gn) &&
                        ((Integer)cell.graphStages.elementAt(k)).intValue() == batchStatus){
                            done = true;
                    }
                }
                if (!done){
                //cell.runGraph(gn,batchStatus);
                    if (cell.runGraph(gn, batchStatus) == null){
                        JOptionPane.showMessageDialog(vpl, cell.cellName +
                            " has been broken during IV Testing\nthis cell will be removed from the batch",
                            "Broken Cell", JOptionPane.INFORMATION_MESSAGE);
                        brokenCells.addElement(batchCells.elementAt(j));
                        batchCells.removeElementAt(j);
                        cellNames.removeElementAt(j);
                    }
                    else
                        ++j;
              }
        }
        
        noOfCells = batchCells.size();
        if (noOfCells == 0){
                JOptionPane.showMessageDialog(vpl,"All the wafers in " + batchName + " have been broken.\nThis batch cannot be processed.",
                  "No Wafers to Process", JOptionPane.INFORMATION_MESSAGE);
                batchStatus = -2;
        }
        calculateTotals();
        batchChanged = true;
    }

    void calculateTotals(){
        totalMaxPower = 0;
        totalVoc = 0;
        totalJsc = 0;
        totalFillFactor = 0;
        totalSeriesResistance = 0;
        totalShuntResistance = 0;
        totalCost = 0;
        costOfOwnership = 0;
        for (int i=0; i<noOfCells; i++){
    	    Wafer cell = (Wafer)batchCells.elementAt(i);
            totalMaxPower = totalMaxPower + cell.maxpower;
            totalJsc = totalJsc + cell.isc;
            totalVoc = totalVoc + cell.voc;
            totalFillFactor = totalFillFactor + (cell.maxpower/(cell.isc*cell.voc));
            totalSeriesResistance = totalSeriesResistance + cell.RE + cell.RB;
            totalShuntResistance = totalShuntResistance + cell.shuntResistance;
        }
        for (int i = 0;i < batchCellCosts.size();i++){
            Cost cellCost = (Cost)batchCellCosts.elementAt(i);
            totalCost = totalCost + cellCost.getTotalWaferCost();
        }
        double cellArea = Constant.getGlobalConstant("CellArea");
        double totalPower = totalMaxPower * cellArea;
        costOfOwnership = totalCost / totalPower;
    }

    Wafer getMostEfficientCell(){
        double max = 0;
        Wafer w;
        Wafer bw = null;
        for (int i=0; i<batchCells.size(); i++){
            w = (Wafer)batchCells.elementAt(i);
            if (w.maxpower > max){
                max = w.maxpower;
                bw = w;
            }
        }
        if (bw != null)
            return bw;
        else if (noOfCells != 0)
            return (Wafer)batchCells.elementAt(0);
        else
            return null;
    }

    void etch(int ssBatches, Vector v){
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
        String etchedHistory = "etched " + date.format(new Date()) + "  " + time.format(new Date());
        batchHistory.addElement(etchedHistory);
        double bathTemp = ((Double)v.elementAt(0)).doubleValue();
        double chemConc = ((Double)v.elementAt(1)).doubleValue();
        double etchTime = ((Double)v.elementAt(2)).doubleValue();
        Settings settings = new Settings("default");
        String[] labels = (settings.etchArray);
        String[] units = (settings.etchUnitsArray);
        int sb;
        if (assignmentNo != -1)
            sb = 10;
        else
            sb = ssBatches;
        String[] details = {"NaOH solution previously used " + sb + " times",
                            labels[0] + ": " + bathTemp + " " + units[0],
                            labels[1] + ": " + chemConc + " " + units[1],
                            labels[2] + ": " + etchTime + " " + units[2]};
        batchHistoryDetails.addElement(details);
        for (int i=0; i<noOfCells; i++){
            Wafer cell = (Wafer)batchCells.elementAt(i);
            cell.etch(sb, bathTemp, chemConc, etchTime);
            Cost cellCost = (Cost)batchCellCosts.elementAt(i);
            cellCost.etch(bathTemp, chemConc, etchTime);
        }
        batchStatus = 2;
        batchChanged = true;
    }

// add acid texture and rinse batch here BobH

    void acidTexture(int acidBatches, Vector v){
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
        String acidTextureHistory = "acid textured " + date.format(new Date()) + "  " + time.format(new Date());
        batchHistory.addElement(acidTextureHistory);


        double acidTextureTime = ((Double)v.elementAt(0)).doubleValue();
        double acidTextureBathTemp = ((Double)v.elementAt(1)).doubleValue();
        double HFC = ((Double)v.elementAt(2)).doubleValue();
        double HCLC = ((Double)v.elementAt(3)).doubleValue();
        //double COOHC = ((Double)v.elementAt(4)).doubleValue();

        Settings settings = new Settings("default");
        String[] labels = (settings.acidTextureArray);
        String[] units = (settings.acidTextureUnitsArray);
        // remain at sb instead of changing to acidb
        int sb;
        if (assignmentNo != -1)
            sb = 10;
        else
            sb = acidBatches;
        String[] details = {"Acid solution previously used " + sb + " times",
                            labels[0] + ": " + acidTextureTime + " " + units[0],
                            labels[1] + ": " + acidTextureBathTemp + " " + units[1],
                            labels[2] + ": " + HFC + " " + units[2],
                            //labels[3] + ": " + COOHC + " " + units[3],
                            labels[3] + ": " + HCLC + " " + units[3]};
        batchHistoryDetails.addElement(details);
        for (int i=0; i<noOfCells; i++){
            Wafer cell = (Wafer)batchCells.elementAt(i);
            cell.acidTexture(sb,acidTextureTime, acidTextureBathTemp, HFC,HCLC,HFC);
            //cell.acidTexture(sb,acidTextureTime, acidTextureBathTemp, HFC,HCLC,COOHC);
        }
        batchStatus = 22;
        batchChanged = true;
    }


    void acidRinseAcidClean(Vector v, VirtualProductionLine vpl){
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
        String acidRinseAcidCleanHistory = "alkaline rinsed/cleaned " + date.format(new Date()) + "  " + time.format(new Date());
        batchHistory.addElement(acidRinseAcidCleanHistory);
        int athfc = ((Integer)v.elementAt(0)).intValue();
        double athft = ((Double)v.elementAt(1)).doubleValue();
        double athfrt = ((Double)v.elementAt(2)).doubleValue();

        Settings settings = new Settings("default");
        String[] labels = (settings.acidRinseAcidCleanArray);
        String[] units = (settings.acidRinseAcidCleanUnitsArray);
        String[] details = {labels[0] + ": " + athfc + " " + units[0],
                            labels[1] + ": " + athft + " " + units[1],
                            labels[2] + ": " + athfrt + " " + units[2]};
        batchHistoryDetails.addElement(details);

        boolean[][] result = new boolean[noOfCells][2];
        for (int i=0; i<noOfCells; i++){
            Wafer cell = (Wafer)batchCells.elementAt(i);
            result[i] = cell.acidRinseAcidClean(athfc, athft, athfrt);
        }
        batchStatus = 23;
        batchChanged = true;
        for (int i=noOfCells-1; i>-1; i--){
            if (result[i][0] == true){
  	        JOptionPane.showMessageDialog(vpl,((Wafer)batchCells.elementAt(i)).cellName +
                      " has been broken during rinse/acid clean\nthis cell will be removed from the batch",
                      "Broken Cell", JOptionPane.INFORMATION_MESSAGE);
            brokenCells.addElement(batchCells.elementAt(i));
            batchCells.removeElementAt(i);
            cellNames.removeElementAt(i);
            noOfCells = batchCells.size();
            }
        }
        if (noOfCells == 0){
            JOptionPane.showMessageDialog(vpl,"All the wafers in " + batchName + " have been broken.\nThis batch cannot be processed.",
                      "No Wafers to Process", JOptionPane.INFORMATION_MESSAGE);
            batchStatus = -2;
        }
        for (int i=0; i<noOfCells; i++){
            if (result[i][1] == true){
  	        JOptionPane.showMessageDialog(vpl,"Wafers have not been rinsed properly,\nyou are endangering your operator",
                      "Wafers not Rinsed Properly", JOptionPane.ERROR_MESSAGE);
                      return;
            }
        }
    }

    // add ends here Bobh

    void postWaferEtchRinse(Vector v){
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
        String rinsedHistory = "rinsed " + date.format(new Date()) + "  " + time.format(new Date());
        batchHistory.addElement(rinsedHistory);
        double rinseTime = ((Double)v.elementAt(0)).doubleValue();
        Settings settings = new Settings("default");
        String[] labels = (settings.rinseArray);
        String[] units = (settings.rinseUnitsArray);
        String[] details = {labels[0] + ": " + rinseTime + " " + units[0]};
        batchHistoryDetails.addElement(details);

        for (int i=0; i<noOfCells; i++){
            Wafer cell = (Wafer)batchCells.elementAt(i);
            cell.postWaferEtchRinse();
        }
        batchStatus = 3;
        batchChanged = true;
    }

    void texture(int tssBatches, Vector v){
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
        String texturedHistory = "textured " + date.format(new Date()) + "  " + time.format(new Date());
        batchHistory.addElement(texturedHistory);
        double textureTime = ((Double)v.elementAt(0)).doubleValue();
        double temp = ((Double)v.elementAt(1)).doubleValue();
        double chemConc = ((Double)v.elementAt(2)).doubleValue();
        double propanol = ((Double)v.elementAt(3)).doubleValue();
        int efr = ((Integer)v.elementAt(4)).intValue();

        Settings settings = new Settings("default");
        String[] labels = (settings.textureArray);
        String[] units = (settings.textureUnitsArray);
        int tsb;
        if (assignmentNo != -1)
            tsb = 5;
        else
            tsb = tssBatches;
        String[] details = {"NaOH solution previously used " + tsb + " times",
                            labels[0] + ": " + textureTime + " " + units[0],
                            labels[1] + ": " + temp + " " + units[1],
                            labels[2] + ": " + chemConc + " " + units[2],
                            labels[3] + ": " + propanol + " " + units[3],
                            labels[4] + ": " + efr + " " + units[4]};
        batchHistoryDetails.addElement(details);

        for (int i=0; i<noOfCells; i++){
            Wafer cell = (Wafer)batchCells.elementAt(i);
            cell.texture(tsb, textureTime, temp, chemConc, propanol, efr);
            Cost cellCost = (Cost)batchCellCosts.elementAt(i);
            cellCost.texture(textureTime, temp, chemConc, propanol, efr);
        }
        texture = true;
        batchStatus = 4;
        batchChanged = true;
    }

    void rinseAcidClean(Vector v, VirtualProductionLine vpl){
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
        String rinseAcidCleanHistory = "rinsed/acid cleaned " + date.format(new Date()) + "  " + time.format(new Date());
        batchHistory.addElement(rinseAcidCleanHistory);
        int hfc = ((Integer)v.elementAt(0)).intValue();
        double hft = ((Double)v.elementAt(1)).doubleValue();
        double hfrt = ((Double)v.elementAt(2)).doubleValue();
        int hclc = ((Integer)v.elementAt(3)).intValue();
        double hclt = ((Double)v.elementAt(4)).doubleValue();
        double hclrt = ((Double)v.elementAt(5)).doubleValue();

        Settings settings = new Settings("default");
        String[] labels = (settings.rinseAcidCleanArray);
        String[] units = (settings.rinseAcidCleanUnitsArray);
        String[] details = {labels[0] + ": " + hfc + " " + units[0],
                            labels[1] + ": " + hft + " " + units[1],
                            labels[2] + ": " + hfrt + " " + units[2],
                            labels[3] + ": " + hclc + " " + units[3],
                            labels[4] + ": " + hclt + " " + units[4],
                            labels[5] + ": " + hclrt + " " + units[5]};
        batchHistoryDetails.addElement(details);

        boolean[][] result = new boolean[noOfCells][2];
        for (int i=0; i<noOfCells; i++){
            Wafer cell = (Wafer)batchCells.elementAt(i);
            result[i] = cell.rinseAcidClean(hfc, hft, hfrt, hclc, hclt, hclrt);
        }
        batchStatus = 5;
        batchChanged = true;
        for (int i=noOfCells-1; i>-1; i--){
            if (result[i][0] == true){
  	        JOptionPane.showMessageDialog(vpl,((Wafer)batchCells.elementAt(i)).cellName +
                      " has been broken during rinse/acid clean\nthis cell will be removed from the batch",
                      "Broken Cell", JOptionPane.INFORMATION_MESSAGE);
            brokenCells.addElement(batchCells.elementAt(i));
            batchCells.removeElementAt(i);
            cellNames.removeElementAt(i);
            noOfCells = batchCells.size();
            }
        }
        if (noOfCells == 0){
            JOptionPane.showMessageDialog(vpl,"All the wafers in " + batchName + " have been broken.\nThis batch cannot be processed.",
                      "No Wafers to Process", JOptionPane.INFORMATION_MESSAGE);
            batchStatus = -2;
        }
        for (int i=0; i<noOfCells; i++){
            if (result[i][1] == true){
  	        JOptionPane.showMessageDialog(vpl,"Wafers have not been rinsed properly,\nyou are endangering your operator",
                      "Wafers not Rinsed Properly", JOptionPane.ERROR_MESSAGE);
                      return;
            }
        }
    }

    void spinOnDiffusion(Vector v, VirtualProductionLine vpl){
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
        String diffusionHistory = "diffused " + date.format(new Date()) + "  " + time.format(new Date());
        batchHistory.addElement(diffusionHistory);
        double beltSpeed = ((Double)v.elementAt(0)).doubleValue();
        int dryingTemp = ((Integer)v.elementAt(1)).intValue();
        int zone1Temp = ((Integer)v.elementAt(2)).intValue();
        int zone2Temp = ((Integer)v.elementAt(3)).intValue();
        int zone3Temp = ((Integer)v.elementAt(4)).intValue();
        double gasFlow = ((Double)v.elementAt(5)).doubleValue();
        int dilutionFactor = ((Integer)v.elementAt(6)).intValue();

        Settings settings = new Settings("default");
        String[] labels = (settings.diffusionArray);
        String[] units = (settings.diffusionUnitsArray);
        String[] details = {labels[0] + ": " + beltSpeed + " " + units[0],
                            labels[1] + ": " + dryingTemp + " " + units[1],
                            labels[2] + ": " + zone1Temp + " " + units[2],
                            labels[3] + ": " + zone2Temp + " " + units[3],
                            labels[4] + ": " + zone3Temp + " " + units[4],
                            labels[5] + ": " + gasFlow + " " + units[5],
                            labels[6] + ": " + dilutionFactor + " " + units[6]};
        batchHistoryDetails.addElement(details);

        boolean[] result = new boolean[noOfCells];
        for (int i=0; i<noOfCells; i++){
            Wafer cell = (Wafer)batchCells.elementAt(i);
            result[i] = cell.spinOnDiffusion(beltSpeed, dryingTemp,
                         zone1Temp, zone2Temp,
                         zone3Temp, gasFlow, dilutionFactor);
            Cost cellCost = (Cost)batchCellCosts.elementAt(i);
            cellCost.spinOnDiffusion(beltSpeed, dryingTemp, 
                                     zone1Temp, zone2Temp,
                                     zone3Temp, gasFlow,
                                     dilutionFactor);
        }
        if (cofiringBatch)
            batchStatus = 26;
        else
            batchStatus = 6;
        batchChanged = true;
        for (int i=noOfCells-1; i>-1; i--){
            if (result[i] == true){
  	        JOptionPane.showMessageDialog(vpl,((Wafer)batchCells.elementAt(i)).cellName +
                      " has been broken during diffusion\nthis cell will be removed from the batch",
                      "Broken Cell", JOptionPane.INFORMATION_MESSAGE);
            brokenCells.addElement(batchCells.elementAt(i));
            batchCells.removeElementAt(i);
            cellNames.removeElementAt(i);
            noOfCells = batchCells.size();
            }
        }
        if (noOfCells == 0){
            JOptionPane.showMessageDialog(vpl,"All the wafers in " + batchName + " have been broken.\nThis batch cannot be processed.",
                      "No Wafers to Process", JOptionPane.INFORMATION_MESSAGE);
            batchStatus = -2;
        }
    }

    void diffusionOxideRemoval(Vector v, VirtualProductionLine vpl){
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
        String diffusionOxideRemovalHistory = "diffusion oxide removed " + date.format(new Date()) + "  " + time.format(new Date());
        batchHistory.addElement(diffusionOxideRemovalHistory);
        double acidCleanTime = ((Double)v.elementAt(0)).doubleValue();
        int chemConc = ((Integer)v.elementAt(1)).intValue();
        double rinseTime = ((Double)v.elementAt(2)).doubleValue();

        Settings settings = new Settings("default");
        String[] labels = (settings.diffusionOxideRemovalArray);
        String[] units = (settings.diffusionOxideRemovalUnitsArray);
        String[] details = {labels[0] + ": " + acidCleanTime + " " + units[0],
                            labels[1] + ": " + chemConc + " " + units[1],
                            labels[2] + ": " + rinseTime + " " + units[2]};
        batchHistoryDetails.addElement(details);

        boolean[] result = new boolean[noOfCells];
        for (int i=0; i<noOfCells; i++){
            Wafer cell = (Wafer)batchCells.elementAt(i);
            result[i] = cell.diffusionOxideRemoval(acidCleanTime, chemConc, rinseTime);
        }
        if (cofiringBatch)
            batchStatus = 27;
        else
            batchStatus = 10;
        batchChanged = true;
        for (int i=noOfCells-1; i>-1; i--){
            if (result[i] == true){
  	        JOptionPane.showMessageDialog(vpl,((Wafer)batchCells.elementAt(i)).cellName +
                      " has been broken during diffusion oxide removal\nthis cell will be removed from the batch",
                      "Broken Cell", JOptionPane.INFORMATION_MESSAGE);
                brokenCells.addElement(batchCells.elementAt(i));
                batchCells.removeElementAt(i);
                cellNames.removeElementAt(i);
                noOfCells = batchCells.size();
            }
        }
        if (noOfCells == 0){
            JOptionPane.showMessageDialog(vpl,"All the wafers in " + batchName + " have been broken.\nThis batch cannot be processed.",
                      "No Wafers to Process", JOptionPane.INFORMATION_MESSAGE);
            batchStatus = -2;
        }
    }

    void plasmaEtch(Vector v, VirtualProductionLine vpl){
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
        String plasmaEtchHistory = "plasma etched " + date.format(new Date()) + "  " + time.format(new Date());
        batchHistory.addElement(plasmaEtchHistory);
        int power = ((Integer)v.elementAt(0)).intValue();
        double petime = ((Double)v.elementAt(1)).doubleValue();

        Settings settings = new Settings("default");
        String[] labels = (settings.plasmaEtchArray);
        String[] units = (settings.plasmaEtchUnitsArray);
        String[] details = {labels[0] + ": " + power + " " + units[0],
                            labels[1] + ": " + petime + " " + units[1]};
        batchHistoryDetails.addElement(details);

        boolean[] result = new boolean[noOfCells];
        for (int i=0; i<noOfCells; i++){
            Wafer cell = (Wafer)batchCells.elementAt(i);
            result[i] = cell.plasmaEtch(power, petime);
            Cost cellCost = (Cost)batchCellCosts.elementAt(i);
            cellCost.plasmaEtch(power, petime);
        }
        if (cofiringBatch)
            batchStatus = 28;
        else
            batchStatus = 11;
        batchChanged = true;
        for (int i=noOfCells-1; i>-1; i--){
            if (result[i] == true){
  	        JOptionPane.showMessageDialog(vpl,((Wafer)batchCells.elementAt(i)).cellName +
                      " has been broken during plasma etch\nthis cell will be removed from the batch",
                      "Broken Cell", JOptionPane.INFORMATION_MESSAGE);
            brokenCells.addElement(batchCells.elementAt(i));
            batchCells.removeElementAt(i);
            cellNames.removeElementAt(i);
            noOfCells = batchCells.size();
            }
        }
        if (noOfCells == 0){
            JOptionPane.showMessageDialog(vpl,"All the wafers in " + batchName + " have been broken.\nThis batch cannot be processed.",
                      "No Wafers to Process", JOptionPane.INFORMATION_MESSAGE);
            batchStatus = -2;
        }
    }

    void tarCoating(Vector v, VirtualProductionLine vpl){
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
        String tarCoatingHistory = "TiO2 AR coated " + date.format(new Date()) + "  " + time.format(new Date());
        batchHistory.addElement(tarCoatingHistory);
        double beltSpeed = ((Double)v.elementAt(0)).doubleValue();
        double temp = ((Double)v.elementAt(1)).doubleValue();
        int gasFlow = ((Integer)v.elementAt(2)).intValue();

        Settings settings = new Settings("default");
        String[] labels = (settings.tarCoatingArray);
        String[] units = (settings.tarCoatingUnitsArray);
        String[] details = {labels[0] + ": " + beltSpeed + " " + units[0],
                            labels[1] + ": " + temp + " " + units[1],
                            labels[2] + ": " + gasFlow + " " + units[2]};
        batchHistoryDetails.addElement(details);

        boolean[] result = new boolean[noOfCells];
        for (int i=0; i<noOfCells; i++){
            Wafer cell = (Wafer)batchCells.elementAt(i);
            result[i] = cell.tarCoating(beltSpeed, temp, gasFlow);
        }
        arc = true;
        if (cofiringBatch)
            batchStatus = 30;
        else
            batchStatus = 13;
        batchChanged = true;
        for (int i=noOfCells-1; i>-1; i--){
            if (result[i] == true){
  	        JOptionPane.showMessageDialog(vpl,((Wafer)batchCells.elementAt(i)).cellName +
                      " has been broken during AR coating\nthis cell will be removed from the batch",
                      "Broken Cell", JOptionPane.INFORMATION_MESSAGE);
            brokenCells.addElement(batchCells.elementAt(i));
            batchCells.removeElementAt(i);
            cellNames.removeElementAt(i);
            noOfCells = batchCells.size();
            }
        }
        if (noOfCells == 0){
            JOptionPane.showMessageDialog(vpl,"All the wafers in " + batchName + " have been broken.\nThis batch cannot be processed.",
                      "No Wafers to Process", JOptionPane.INFORMATION_MESSAGE);
            batchStatus = -2;
        }
    }

    void sarCoating(Vector v, VirtualProductionLine vpl){
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
        String sarCoatingHistory = "SiNx AR coated " + date.format(new Date()) + "  " + time.format(new Date());
        batchHistory.addElement(sarCoatingHistory);
        double artime = ((Double)v.elementAt(0)).doubleValue();
        double temp = ((Double)v.elementAt(1)).doubleValue();
        int gasFlow1 = ((Integer)v.elementAt(2)).intValue();
        int gasFlow2 = ((Integer)v.elementAt(3)).intValue();

        Settings settings = new Settings("default");
        String[] labels = (settings.sarCoatingArray);
        String[] units = (settings.sarCoatingUnitsArray);
        String[] details = {labels[0] + ": " + artime + " " + units[0],
                            labels[1] + ": " + temp + " " + units[1],
                            labels[2] + ": " + gasFlow1 + " " + units[2],
                            labels[3] + ": " + gasFlow2 + " " + units[3]};
        batchHistoryDetails.addElement(details);

        boolean[] result = new boolean[noOfCells];
        for (int i=0; i<noOfCells; i++){
            Wafer cell = (Wafer)batchCells.elementAt(i);
            result[i] = cell.sarCoating(artime, temp, gasFlow1, gasFlow2);
            Cost cellCost = (Cost)batchCellCosts.elementAt(i);
            cellCost.arCoating(artime, temp, gasFlow1, gasFlow2);
        }
        arc = true;
        if (cofiringBatch)
            batchStatus = 30;
        else
            batchStatus = 13;
        batchChanged = true;
        for (int i=noOfCells-1; i>-1; i--){
            if (result[i] == true){
                JOptionPane.showMessageDialog(vpl,((Wafer)batchCells.elementAt(i)).cellName +
                      " has been broken during AR coating\nthis cell will be removed from the batch",
                      "Broken Cell", JOptionPane.INFORMATION_MESSAGE);
            brokenCells.addElement(batchCells.elementAt(i));
            batchCells.removeElementAt(i);
            cellNames.removeElementAt(i);
            noOfCells = batchCells.size();
            }
        }
        if (noOfCells == 0){
            JOptionPane.showMessageDialog(vpl,"All the wafers in " + batchName + " have been broken.\nThis batch cannot be processed.",
                      "No Wafers to Process", JOptionPane.INFORMATION_MESSAGE);
            batchStatus = -2;
        }
    }

    void alScreenSetup(Vector v, VirtualProductionLine vpl){
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
        String alScreenSetupHistory = "Al screen prepared " + date.format(new Date()) + "  " + time.format(new Date());
        batchHistory.addElement(alScreenSetupHistory);
        int meshDensity = ((Integer)v.elementAt(0)).intValue();
        double emulsionThicknessAbove = ((Double)v.elementAt(1)).doubleValue();
        double emulsionThicknessBelow = ((Double)v.elementAt(2)).doubleValue();
        int strandDiameter = ((Integer)v.elementAt(3)).intValue();
        String pattern = (String)v.elementAt(4);

        Settings settings = new Settings("default");
        String[] labels = (settings.alScreenSetupArray);
        String[] units = (settings.alScreenSetupUnitsArray);
        String[] details = {labels[0] + ": " + meshDensity + " " + units[0],
                            labels[1] + ": " + emulsionThicknessAbove + " " + units[1],
                            labels[2] + ": " + emulsionThicknessBelow + " " + units[2],
                            labels[3] + ": " + strandDiameter + " " + units[3],
                            labels[4] + ": " + pattern + " " + units[4]};
        batchHistoryDetails.addElement(details);

        for (int i=0; i<noOfCells; i++){
            Wafer cell = (Wafer)batchCells.elementAt(i);
            cell.alScreenSetup(meshDensity, emulsionThicknessAbove,
                emulsionThicknessBelow, strandDiameter, pattern);
            Cost cellCost = (Cost)batchCellCosts.elementAt(i);
            cellCost.alScreenSetup(meshDensity, emulsionThicknessAbove,
                emulsionThicknessBelow, strandDiameter, pattern);
        }
        if (cofiringBatch)
            batchStatus = 33;
        else
            batchStatus = 7;
        batchChanged = true;
    }

    void screenPrint(Vector v, VirtualProductionLine vpl){
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
        String screenPrintedHistory = "Al screen printed " + date.format(new Date()) + "  " + time.format(new Date());
        batchHistory.addElement(screenPrintedHistory);
        int squeegeePressure = ((Integer)v.elementAt(0)).intValue();
        int pasteViscosity = ((Integer)v.elementAt(1)).intValue();
        int squeegeeSpeed = ((Integer)v.elementAt(2)).intValue();

        Settings settings = new Settings("default");
        String[] labels = (settings.alScreenPrintArray);
        String[] units = (settings.alScreenPrintUnitsArray);
        String[] details = {labels[0] + ": " + squeegeePressure + " " + units[0],
                            labels[1] + ": " + pasteViscosity + " " + units[1],
                            labels[2] + ": " + squeegeeSpeed + " " + units[2]};
        batchHistoryDetails.addElement(details);

        boolean[] result = new boolean[noOfCells];
        for (int i=0; i<noOfCells; i++){
            Wafer cell = (Wafer)batchCells.elementAt(i);
            result[i] = cell.screenPrint(squeegeePressure, pasteViscosity, squeegeeSpeed);
        }
        if (cofiringBatch)
            batchStatus = 34;
        else
            batchStatus = 8;
        batchChanged = true;
        cellsBrokenInAlProcess = new Vector();
        for (int i=noOfCells-1; i>-1; i--){
            if (result[i] == true){
  	        JOptionPane.showMessageDialog(vpl,((Wafer)batchCells.elementAt(i)).cellName +
                      " has been broken during Al screen print\nthis cell will be removed from the batch",
                      "Broken Cell", JOptionPane.INFORMATION_MESSAGE);
            cellsBrokenInAlProcess.addElement(new Integer(((Wafer)batchCells.elementAt(i)).cellIndex));
            brokenCells.addElement(batchCells.elementAt(i));
            batchCells.removeElementAt(i);
            cellNames.removeElementAt(i);
            noOfCells = batchCells.size();
            }
        }
        if (noOfCells == 0){
            JOptionPane.showMessageDialog(vpl,"All the wafers in " + batchName + " have been broken.\nThis batch cannot be processed.",
                      "No Wafers to Process", JOptionPane.INFORMATION_MESSAGE);
            batchStatus = -2;
        }
    }

    void alFiring(Vector v, VirtualProductionLine vpl){
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
        String alFiredHistory = "Al fired " + date.format(new Date()) + "  " + time.format(new Date());
        batchHistory.addElement(alFiredHistory);
        double beltSpeed = ((Double)v.elementAt(0)).doubleValue();
        int dryingTemp = ((Integer)v.elementAt(1)).intValue();
        int zone1Temp = ((Integer)v.elementAt(2)).intValue();
        int zone2Temp = ((Integer)v.elementAt(3)).intValue();
        int zone3Temp = ((Integer)v.elementAt(4)).intValue();
        double gasFlow = ((Double)v.elementAt(5)).doubleValue();
        int o2Percent = ((Integer)v.elementAt(6)).intValue();

        Settings settings = new Settings("default");
        String[] labels = (settings.alFiringArray);
        String[] units = (settings.alFiringUnitsArray);
        String[] details = {labels[0] + ": " + beltSpeed + " " + units[0],
                            labels[1] + ": " + dryingTemp + " " + units[1],
                            labels[2] + ": " + zone1Temp + " " + units[2],
                            labels[3] + ": " + zone2Temp + " " + units[3],
                            labels[4] + ": " + zone3Temp + " " + units[4],
                            labels[5] + ": " + gasFlow + " " + units[5],
                            labels[6] + ": " + o2Percent + " " + units[6]};
        batchHistoryDetails.addElement(details);

        boolean[] result = new boolean[noOfCells];
        for (int i=noOfCells-1; i>-1; i--){
            Wafer cell = (Wafer)batchCells.elementAt(i);
            result[i] = cell.alFiring(beltSpeed, dryingTemp, zone1Temp, zone2Temp, zone3Temp,
              gasFlow, o2Percent, cellsBrokenInAlProcess);
            if (result[i] == true){
  	        JOptionPane.showMessageDialog(vpl,((Wafer)batchCells.elementAt(i)).cellName +
                      " has been broken during Al firing\nthis cell will be removed from the batch",
                      "Broken Cell", JOptionPane.INFORMATION_MESSAGE);
            cellsBrokenInAlProcess.addElement(new Integer(((Wafer)batchCells.elementAt(i)).cellIndex));
            brokenCells.addElement(batchCells.elementAt(i));
            batchCells.removeElementAt(i);
            cellNames.removeElementAt(i);
            noOfCells = batchCells.size();
            }
        }
        batchStatus = 9;
        batchChanged = true;
   //     for (int i=0; i<noOfCells; i++){
     //   }
        if (noOfCells == 0){
            JOptionPane.showMessageDialog(vpl,"All the wafers in " + batchName + " have been broken.\nThis batch cannot be processed.",
                      "No Wafers to Process", JOptionPane.INFORMATION_MESSAGE);
            batchStatus = -2;
        }
    }

    void silverScreenSetup(Vector v){
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
        String silverScreenHistory = "Silver screen set up " + date.format(new Date()) + "  " + time.format(new Date());
        batchHistory.addElement(silverScreenHistory);
        int meshDensity = ((Integer)v.elementAt(0)).intValue();
        double emulsionThicknessAbove = ((Double)v.elementAt(1)).doubleValue();
        double emulsionThicknessBelow = ((Double)v.elementAt(2)).doubleValue();
        int fingerSpacing = ((Integer)v.elementAt(3)).intValue();
        int strandDiameter = ((Integer)v.elementAt(4)).intValue();
        int fingerWidth = ((Integer)v.elementAt(5)).intValue();

        Settings settings = new Settings("default");
        String[] labels = (settings.silverScreenSetupArray);
        String[] units = (settings.silverScreenSetupUnitsArray);
        String[] details = {labels[0] + ": " + meshDensity + " " + units[0],
                            labels[1] + ": " + emulsionThicknessAbove + " " + units[1],
                            labels[2] + ": " + emulsionThicknessBelow + " " + units[2],
                            labels[3] + ": " + fingerSpacing + " " + units[3],
                            labels[4] + ": " + strandDiameter + " " + units[4],
                            labels[5] + ": " + fingerWidth + " " + units[5]};
        batchHistoryDetails.addElement(details);
        for (int i=0; i<noOfCells; i++){
            Wafer cell = (Wafer)batchCells.elementAt(i);
            cell.silverScreen(meshDensity, emulsionThicknessAbove,
                emulsionThicknessBelow, fingerSpacing, strandDiameter, fingerWidth);
            Cost cellCost = (Cost)batchCellCosts.elementAt(i);
            cellCost.silverScreenPrint(meshDensity, emulsionThicknessAbove,
                emulsionThicknessBelow, fingerSpacing, strandDiameter, fingerWidth);
        }
        if (cofiringBatch)
            batchStatus = 31;
        else
            batchStatus = 14;
        batchChanged = true;
    }

    void silverScreenPrint(Vector v, VirtualProductionLine vpl){
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
        String silverScreenPrintedHistory = "Silver screen printed " + date.format(new Date()) + "  " + time.format(new Date());
        batchHistory.addElement(silverScreenPrintedHistory);
        int squeegeePressure = ((Integer)v.elementAt(0)).intValue();
        int pasteViscosity = ((Integer)v.elementAt(1)).intValue();
        int squeegeeSpeed = ((Integer)v.elementAt(2)).intValue();

        Settings settings = new Settings("default");
        String[] labels = (settings.silverScreenPrintArray);
        String[] units = (settings.silverScreenPrintUnitsArray);
        String[] details = {labels[0] + ": " + squeegeePressure + " " + units[0],
                            labels[1] + ": " + pasteViscosity + " " + units[1],
                            labels[2] + ": " + squeegeeSpeed + " " + units[2]};
        batchHistoryDetails.addElement(details);

        boolean[][] result = new boolean[noOfCells][2];
        for (int i=0; i<noOfCells; i++){
            Wafer cell = (Wafer)batchCells.elementAt(i);
            result[i] = cell.silverScreenPrint(squeegeePressure, pasteViscosity, squeegeeSpeed);
        }
        if (cofiringBatch)
            batchStatus = 32;
        else
            batchStatus = 15;
        batchChanged = true;
        for (int i=noOfCells-1; i>-1; i--){
            if (result[i][0] == true){
  	        JOptionPane.showMessageDialog(vpl,((Wafer)batchCells.elementAt(i)).cellName +
                      " has been broken during silver screen printing\nthis cell will be removed from the batch",
                      "Broken Cell", JOptionPane.INFORMATION_MESSAGE);
            brokenCells.addElement(batchCells.elementAt(i));
            batchCells.removeElementAt(i);
            cellNames.removeElementAt(i);
            noOfCells = batchCells.size();
            }
        }
        for (int i=noOfCells-1; i>-1; i--){
            if (result[i][1] == true){
  	        JOptionPane.showMessageDialog(vpl,((Wafer)batchCells.elementAt(i)).cellName +
                      " has been destroyed during silver printing\nthis cell will be removed from the batch",
                      "Cell Destroyed", JOptionPane.INFORMATION_MESSAGE);
            brokenCells.addElement(batchCells.elementAt(i));
            batchCells.removeElementAt(i);
            cellNames.removeElementAt(i);
            noOfCells = batchCells.size();
            }
        }
        if (noOfCells == 0){
            JOptionPane.showMessageDialog(vpl,"All the wafers in " + batchName + " have been broken.\nThis batch cannot be processed.",
                      "No Wafers to Process", JOptionPane.INFORMATION_MESSAGE);
            batchStatus = -2;
        }
    }

    void silverFiring(Vector v, VirtualProductionLine vpl){
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
        String silverFiredHistory = "Silver fired " + date.format(new Date()) + "  " + time.format(new Date());
        batchHistory.addElement(silverFiredHistory);
        double beltSpeed = ((Double)v.elementAt(0)).doubleValue();
        int dryingTemp = ((Integer)v.elementAt(1)).intValue();
        int zone1Temp = ((Integer)v.elementAt(2)).intValue();
        int zone2Temp = ((Integer)v.elementAt(3)).intValue();
        int zone3Temp = ((Integer)v.elementAt(4)).intValue();

        Settings settings = new Settings("default");
        String[] labels = (settings.silverFiringArray);
        String[] units = (settings.silverFiringUnitsArray);
        String[] details = {labels[0] + ": " + beltSpeed + " " + units[0],
                            labels[1] + ": " + dryingTemp + " " + units[1],
                            labels[2] + ": " + zone1Temp + " " + units[2],
                            labels[3] + ": " + zone2Temp + " " + units[3],
                            labels[4] + ": " + zone3Temp + " " + units[4]};
        batchHistoryDetails.addElement(details);

        boolean[] result = new boolean[noOfCells];
        for (int i=0; i<noOfCells; i++){
            Wafer cell = (Wafer)batchCells.elementAt(i);
            result[i] = cell.silverFiring(beltSpeed, dryingTemp, zone1Temp, zone2Temp, zone3Temp);
        }
        batchStatus = 16;
        batchChanged = true;
        for (int i=noOfCells-1; i>-1; i--){
            if (result[i] == true){
  	        JOptionPane.showMessageDialog(vpl,((Wafer)batchCells.elementAt(i)).cellName +
                      " has been broken during silver firing\nthis cell will be removed from the batch",
                      "Broken Cell", JOptionPane.INFORMATION_MESSAGE);
            brokenCells.addElement(batchCells.elementAt(i));
            batchCells.removeElementAt(i);
            cellNames.removeElementAt(i);
            noOfCells = batchCells.size();
            }
        }
        if (noOfCells == 0){
            JOptionPane.showMessageDialog(vpl,"All the wafers in " + batchName + " have been broken.\nThis batch cannot be processed.",
                      "No Wafers to Process", JOptionPane.INFORMATION_MESSAGE);
            batchStatus = -2;
        }
    }

    void cofiring(Vector v, VirtualProductionLine vpl){
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
        String cofiringHistory = "Contacts co-fired " + date.format(new Date()) + "  " + time.format(new Date());
        batchHistory.addElement(cofiringHistory);
        double beltSpeed = ((Double)v.elementAt(0)).doubleValue();
        int dryingTemp = ((Integer)v.elementAt(1)).intValue();
        int zone1Temp = ((Integer)v.elementAt(2)).intValue();
        int zone2Temp = ((Integer)v.elementAt(3)).intValue();
        int zone3Temp = ((Integer)v.elementAt(4)).intValue();
        double gasFlow = ((Double)v.elementAt(5)).doubleValue();
        int o2Percent = ((Integer)v.elementAt(6)).intValue();

        Settings settings = new Settings("default");
        String[] labels = (settings.cofiringArray);
        String[] units = (settings.cofiringUnitsArray);
        String[] details = {labels[0] + ": " + beltSpeed + " " + units[0],
                            labels[1] + ": " + dryingTemp + " " + units[1],
                            labels[2] + ": " + zone1Temp + " " + units[2],
                            labels[3] + ": " + zone2Temp + " " + units[3],
                            labels[4] + ": " + zone3Temp + " " + units[4],
                            labels[5] + ": " + gasFlow + " " + units[5],
                            labels[6] + ": " + o2Percent + " " + units[6]};
        batchHistoryDetails.addElement(details);

        boolean[] result = new boolean[noOfCells];
        for (int i=noOfCells-1; i>-1; i--){
            Wafer cell = (Wafer)batchCells.elementAt(i);
            result[i] = cell.cofiring(beltSpeed, dryingTemp, zone1Temp, zone2Temp, zone3Temp,
              gasFlow, o2Percent, cellsBrokenInAlProcess);
            Cost cellCost = (Cost)batchCellCosts.elementAt(i);
            cellCost.cofiring(beltSpeed, dryingTemp, zone1Temp, zone2Temp, zone3Temp,
                            gasFlow, o2Percent);
            if (result[i] == true){
  	        JOptionPane.showMessageDialog(vpl,((Wafer)batchCells.elementAt(i)).cellName +
                      " has been broken during cofiring\nthis cell will be removed from the batch",
                      "Broken Cell", JOptionPane.INFORMATION_MESSAGE);
            cellsBrokenInAlProcess.addElement(new Integer(((Wafer)batchCells.elementAt(i)).cellIndex));
            brokenCells.addElement(batchCells.elementAt(i));
            batchCells.removeElementAt(i);
            cellNames.removeElementAt(i);
            noOfCells = batchCells.size();
            }
        }
        batchStatus = 16;
        batchChanged = true;
        if (noOfCells == 0){
            JOptionPane.showMessageDialog(vpl,"All the wafers in " + batchName + " have been broken.\nThis batch cannot be processed.",
                      "No Wafers to Process", JOptionPane.INFORMATION_MESSAGE);
            batchStatus = -2;
        }
    }

}
