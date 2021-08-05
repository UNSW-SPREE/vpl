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
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

public class TestResultsPanel extends JPanel implements ItemListener
{
    Batch batch;
    JPanel testCards;
    Vector comboList;
    JLabel noTestsLabel = new JLabel("No Tests Performed");
    String string0 = "Incoming Wafer Tests";
    String string1 = "Post Wafer Etch Tests";
    String string2 = "Post Texture Tests";
    String string3 = "Post Diffusion Tests";
    String string4 = "Post Al Screen Print Tests";
    String string5 = "Post Al Firing Tests";
    String string6 = "Post Plasma Etch Tests";
    String string7 = "Post AR Coating Tests";
    String string8 = "Post Silver Screen Print Tests";
    String string9 = "Finished Cell Tests";

    Vector incomingWaferTests = new Vector();
    Vector postEtchTests = new Vector();
    Vector postTextureTests = new Vector();
    Vector postDiffusionTests = new Vector();
    Vector postAlScreenPrintTests = new Vector();
    Vector postAlFiringTests = new Vector();
    Vector postPlasmaEtchTests = new Vector();
    Vector postArCoatingTests = new Vector();
    Vector postSilverScreenPrintTests = new Vector();
    Vector finishedCellTests = new Vector();
    Vector cfpostPlasmaEtchTests = new Vector();
    Vector cfpostArCoatingTests = new Vector();
    Vector cfpostSilverScreenPrintTests = new Vector();
    Vector cfpostAlScreenPrintTests = new Vector();

    public TestResultsPanel(Batch batch){
	this.batch = batch;
	setVisible(true);
        testCards = new JPanel();
        testCards.setLayout(new CardLayout());

	String name = new String();
	for (int i=0; i<batch.noOfCells; i++){
	    Wafer cell = (Wafer)batch.batchCells.elementAt(i);
            sortTests(cell, false);
        }
        if (batch.brokenCells!=null){
            for (int i=0; i<batch.brokenCells.size(); i++){
                Wafer cell = (Wafer)batch.brokenCells.elementAt(i);
                sortTests(cell, true);
            }
        }
    comboList = new Vector();
    if (incomingWaferTests.size()!=0){
        addTests(string0, incomingWaferTests);
    }
    if (postEtchTests.size()!=0){
        addTests(string1, postEtchTests);
    }
    if (postTextureTests.size()!=0){
        addTests(string2, postTextureTests);
    }
    if (postDiffusionTests.size()!=0){
        addTests(string3, postDiffusionTests);
    }
    if (postAlScreenPrintTests.size()!=0){
        addTests(string4, postAlScreenPrintTests);
    }
    if (postAlFiringTests.size()!=0){
        addTests(string5, postAlFiringTests);
    }
    if (postPlasmaEtchTests.size()!=0){
        addTests(string6, postPlasmaEtchTests);
    }
    if (postArCoatingTests.size()!=0){
        addTests(string7, postArCoatingTests);
    }
    if (postSilverScreenPrintTests.size()!=0){
        addTests(string8, postSilverScreenPrintTests);
    }
    if (cfpostPlasmaEtchTests.size()!=0){
        addTests(string6, cfpostPlasmaEtchTests);
    }
    if (cfpostArCoatingTests.size()!=0){
        addTests(string7, cfpostArCoatingTests);
    }
    if (cfpostSilverScreenPrintTests.size()!=0){
        addTests(string8, cfpostSilverScreenPrintTests);
    }
    if (cfpostAlScreenPrintTests.size()!=0){
        addTests(string4, cfpostAlScreenPrintTests);
    }

    if (finishedCellTests.size()!=0){
        addTests(string9, finishedCellTests);
    }


    //Put the JComboBox in a JPanel to get a nicer look.
    JPanel cp = new JPanel();
    if (comboList.size() != 0){
        String[] list = new String[comboList.size()];
        comboList.copyInto(list);
        JComboBox c = new JComboBox(list);
        c.setEditable(false);
        c.setSelectedItem(comboList.lastElement());
        c.addItemListener(this);
        cp.add(c);
        cp.setSize(new Dimension(200,40));
    }
    else
        cp.add(noTestsLabel);
        add(cp);
        add(testCards);
        CardLayout tcl = (CardLayout)(testCards.getLayout());
        tcl.last(testCards);
    }

    protected Component makeTestsTable(Vector values){
        JPanel panel = new JPanel();
        MyTableModel myModel = new MyTableModel();
        myModel.setObject(values);
        JTable table = new JTable(myModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        //Set up column sizes.
//        initColumnSizes(table, myModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(new Dimension(500,100));
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    class MyTableModel extends AbstractTableModel{
        final String[] columnNames = {"Wafer", "Test Name","Result", "Time"};
        Object[][] data;

        public void setObject(Vector values){
            Object[][] tempObject = new Object[values.size()][4];
            values.copyInto(tempObject);
            this.data = tempObject;
        }
        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

    }

    /*
     * This method picks good column sizes.
     * If all column heads are wider than the column's cells'
     * contents, then you can just use column.sizeWidthToFit().
     */
    private void initColumnSizes(JTable table, MyTableModel model) {
        TableColumn column = null;
        Component comp = null;
        int headerWidth = 0;

        for (int i = 0; i < 4; i++) {
            column = table.getColumnModel().getColumn(i);
            //column.sizeWidthToFit();


            comp = column.getHeaderRenderer().
                             getTableCellRendererComponent(
                                 null, column.getHeaderValue(),
                                 false, false, 0, 0);
            headerWidth = comp.getPreferredSize().width;

            column.setPreferredWidth(headerWidth);
        }
    }


    public void itemStateChanged(java.awt.event.ItemEvent evt){
        CardLayout cl = (CardLayout)(testCards.getLayout());
        cl.show(testCards, (String)evt.getItem());
    }

    public void addTests(String string,Vector tests){
        comboList.add(string);
        Component table = makeTestsTable(tests);
        JPanel panel = new JPanel();
        panel.add(table);
        testCards.add(string, panel);
    }

    public void sortTests(Wafer cell, boolean broken){
        for (int j=0; j<cell.testResults.size(); j++){
            String cellName;
            if (broken)
                cellName = cell.cellName + " (BROKEN)";
            else
                cellName = cell.cellName;
            int testStage = ((Integer)cell.testStages.elementAt(j)).intValue();
            String testName = (String)cell.testNames.elementAt(j);
            String result = (String)cell.testResults.elementAt(j);
            String time = (String)cell.testTimes.elementAt(j);
            String[] tempArray = {cellName, testName, result, time};
            switch(testStage){
                case 1: incomingWaferTests.addElement(tempArray); break;

                // added by Bobh for multicrystaline batch
                case 21: incomingWaferTests.addElement(tempArray); break;
                // added end BObh
                
                case 2: postEtchTests.addElement(tempArray); break;
                // added by Bobh for acid texture and rinse
                case 22: postEtchTests.addElement(tempArray); break;
                case 23: postEtchTests.addElement(tempArray); break;
                // end add Bobh

                case 3: postEtchTests.addElement(tempArray); break;
                case 4: if (batch.texture){
                    postTextureTests.addElement(tempArray);
                    break;
                  }
                  else {
                    postEtchTests.addElement(tempArray); break;
                  }
                case 5: if (batch.texture){
                    postTextureTests.addElement(tempArray);
                    break;
                  }
                  else {
                    postEtchTests.addElement(tempArray); break;
                  }
                case 6: postDiffusionTests.addElement(tempArray); break;
                case 7: postDiffusionTests.addElement(tempArray); break;
                case 8: postAlScreenPrintTests.addElement(tempArray); break;
                case 9: postAlFiringTests.addElement(tempArray); break;
                case 10: postAlFiringTests.addElement(tempArray); break;
                case 11: postPlasmaEtchTests.addElement(tempArray); break;
                case 12: postPlasmaEtchTests.addElement(tempArray); break;
                case 13: if (batch.arc){
                    postArCoatingTests.addElement(tempArray);
                    break;
                  }
                  else {
                    postPlasmaEtchTests.addElement(tempArray); break;
                  }
                case 14: if (batch.arc){
                    postArCoatingTests.addElement(tempArray);
                    break;
                  }
                  else {
                    postPlasmaEtchTests.addElement(tempArray); break;
                  }
                case 15: postSilverScreenPrintTests.addElement(tempArray); break;
                case 16: finishedCellTests.addElement(tempArray); break;
                case 26: postDiffusionTests.addElement(tempArray); break;
                case 27: postDiffusionTests.addElement(tempArray); break;
                case 28: cfpostPlasmaEtchTests.addElement(tempArray); break;
                case 29: cfpostPlasmaEtchTests.addElement(tempArray); break;
                case 30: if (batch.arc){
                    postArCoatingTests.addElement(tempArray);
                    break;
                  }
                  else {
                    postPlasmaEtchTests.addElement(tempArray); break;
                  }
                case 31: if (batch.arc){
                    postArCoatingTests.addElement(tempArray);
                    break;
                  }
                  else {
                    postPlasmaEtchTests.addElement(tempArray); break;
                  }
                case 32: cfpostSilverScreenPrintTests.addElement(tempArray); break;
                case 33: cfpostSilverScreenPrintTests.addElement(tempArray); break;
                case 34: cfpostAlScreenPrintTests.addElement(tempArray); break;
            }
        }
    }

}
