package vpl;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableCellRenderer;
import javax.swing.border.Border;
import java.lang.reflect.Array;

/**
 * Title:        Virtual Production Line<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Anna Bruce<p>
 * Company:      UNSW PV Engineering<p>
 * @author Anna Bruce
 * @version 1.0
 */

public class GraphListPanel extends javax.swing.JPanel implements ItemListener
{
    VirtualProductionLine vpl;
    Batch batch;
    JPanel graphCards;
    Vector comboList;
    Vector comboTracker;
    JLabel noGraphsLabel = new JLabel("No Graphs Performed");
    String string0 = "Incoming Wafer Graphs";
    String string1 = "Post Wafer Etch Graphs";
    String string2 = "Post Texture Graphs";
    String string3 = "Post Diffusion Graphs";
    String string4 = "Post Al Screen Print Graphs";
    String string5 = "Post Al Firing Graphs";
    String string6 = "Post Plasma Etch Graphs";
    String string7 = "Post AR Coating Graphs";
    String string8 = "Post Silver Screen Print Graphs";
    String string9 = "Finished Cell Graphs";

    JComboBox c;
	Vector incomingWaferGraphs = new Vector();
	Vector postEtchGraphs = new Vector();
        Vector postTextureGraphs = new Vector();
        Vector postDiffusionGraphs = new Vector();
        Vector postAlScreenPrintGraphs = new Vector();
        Vector postAlFiringGraphs = new Vector();
        Vector postPlasmaEtchGraphs = new Vector();
        Vector postArCoatingGraphs = new Vector();
        Vector postSilverScreenPrintGraphs = new Vector();
        Vector finishedCellGraphs = new Vector();
        Vector cfpostPlasmaEtchGraphs = new Vector();
        Vector cfpostArCoatingGraphs = new Vector();
        Vector cfpostSilverScreenPrintGraphs = new Vector();
        Vector cfpostAlScreenPrintGraphs = new Vector();
        Vector cffinishedCellGraphs = new Vector();

    Vector graphDatas[] = {new Vector(), new Vector(), new Vector(), new Vector(),
        new Vector(), new Vector(), new Vector(), new Vector(),
        new Vector(), new Vector(), new Vector(), new Vector(),
        new Vector(), new Vector(), new Vector()};
    Vector graphSelected[] = {new Vector(), new Vector(), new Vector(), new Vector(),
        new Vector(), new Vector(), new Vector(), new Vector(),
        new Vector(), new Vector(), new Vector(), new Vector(),
        new Vector(), new Vector(), new Vector()};

    public GraphListPanel(VirtualProductionLine vpl, Batch batch){
        this.vpl = vpl;
	this.batch = batch;
	setVisible(true);
        graphCards = new JPanel();
        graphCards.setLayout(new CardLayout());
        for (int k=0;k<15;k++){
            graphDatas[k] = new Vector();
            graphSelected[k] = new Vector();
        }
	String name = new String();
	for (int i=0; i<batch.noOfCells; i++){
	    Wafer cell = (Wafer)batch.batchCells.elementAt(i);
            sortGraphs(cell, false);
        }
        if (batch.brokenCells!=null){
            for (int i=0; i<batch.brokenCells.size(); i++){
                Wafer cell = (Wafer)batch.brokenCells.elementAt(i);
                sortGraphs(cell, true);
            }
        }

    comboList = new Vector();
    comboTracker = new Vector();
    if (incomingWaferGraphs.size()!=0){
        addGraphs(string0, incomingWaferGraphs);
        comboTracker.addElement(new Integer(0));
    }
    if (postEtchGraphs.size()!=0){
        addGraphs(string1, postEtchGraphs);
        comboTracker.addElement(new Integer(1));
    }
    if (postTextureGraphs.size()!=0){
        addGraphs(string2, postTextureGraphs);
        comboTracker.addElement(new Integer(2));
    }
    if (postDiffusionGraphs.size()!=0){
        addGraphs(string3, postDiffusionGraphs);
        comboTracker.addElement(new Integer(3));
    }
    if (postAlScreenPrintGraphs.size()!=0){
        addGraphs(string4, postAlScreenPrintGraphs);
        comboTracker.addElement(new Integer(4));
    }
    if (postAlFiringGraphs.size()!=0){
        addGraphs(string5, postAlFiringGraphs);
        comboTracker.addElement(new Integer(5));
    }
    if (postPlasmaEtchGraphs.size()!=0){
        addGraphs(string6, postPlasmaEtchGraphs);
        comboTracker.addElement(new Integer(6));
    }
    if (postArCoatingGraphs.size()!=0){
        addGraphs(string7, postArCoatingGraphs);
        comboTracker.addElement(new Integer(7));
    }
    if (postSilverScreenPrintGraphs.size()!=0){
        addGraphs(string8, postSilverScreenPrintGraphs);
        comboTracker.addElement(new Integer(8));
    }

    if (cfpostPlasmaEtchGraphs.size()!=0){
        addGraphs(string6, cfpostPlasmaEtchGraphs);
        comboTracker.addElement(new Integer(10));
    }
    if (cfpostArCoatingGraphs.size()!=0){
        addGraphs(string7, cfpostArCoatingGraphs);
        comboTracker.addElement(new Integer(11));
    }
    if (cfpostSilverScreenPrintGraphs.size()!=0){
        addGraphs(string8, cfpostSilverScreenPrintGraphs);
        comboTracker.addElement(new Integer(12));
    }
    if (cfpostAlScreenPrintGraphs.size()!=0){
        addGraphs(string4, cfpostAlScreenPrintGraphs);
        comboTracker.addElement(new Integer(13));
    }

    if (finishedCellGraphs.size()!=0){
        addGraphs(string9, finishedCellGraphs);
        comboTracker.addElement(new Integer(9));
    }

    //Put the JComboBox in a JPanel to get a nicer look.
    JPanel cp = new JPanel();
    if (comboList.size() != 0){
        String[] list = new String[comboList.size()];
        comboList.copyInto(list);
        c = new JComboBox(list);
        c.setEditable(false);
        c.setSelectedItem(comboList.lastElement());
        c.addItemListener(this);
        cp.add(c);
        cp.setSize(new Dimension(200,40));
    }
    else
        cp.add(noGraphsLabel);
        add(cp);
        add(graphCards);
        CardLayout tcl = (CardLayout)(graphCards.getLayout());
        tcl.last(graphCards);
    }

    protected Component makeGraphTable(Vector values){
        JPanel panel = new JPanel();
        MyTableModel myModel = new MyTableModel();
        myModel.setObject(values);
        JTable table = new JTable(myModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        //Set up column sizes.
        setUpButtonRenderer(table);
        setUpButtonEditor(table);
//        initColumnSizes(table, myModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(new Dimension(500,100));
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        Border unselectedBorder = null;
        Border selectedBorder = null;
        boolean isBordered = true;
        int rowNo;

        public ButtonRenderer(boolean isBordered) {
            super();
            this.isBordered = isBordered;
            setOpaque(true); //MUST do this for background to show up.
        }

        public ButtonRenderer(String text){
            super();
            this.setText(text);
        }

        public Component getTableCellRendererComponent(
                                JTable table, Object result,
                                boolean isSelected, boolean hasFocus,
                                int row, int column) {
            if (isBordered) {
                if (((Boolean)graphSelected[getIndex(c.getSelectedIndex())].elementAt(row)).booleanValue()) {
                    if (selectedBorder == null) {
                        selectedBorder = BorderFactory.createMatteBorder(2,5,2,5,
                                                  table.getSelectionBackground());
                    }
                    setBorder(selectedBorder);
                    setText("Hide Graph");
                } else {
                    if (unselectedBorder == null) {
                        unselectedBorder = BorderFactory.createMatteBorder(2,5,2,5,
                                                  table.getBackground());
                    }
                    setBorder(unselectedBorder);
                    setText("Show Graph...");
                }
            }
            return this;
        }
    }

    public void redo(){
        boolean isGraph = false;
        if (vpl.clearPicturePane() == true){
                vpl.clearPicturePane();
                if (c!=null){
                for (int i=0; i<graphSelected[getIndex(c.getSelectedIndex())].size(); i++){
                    if (((Boolean)graphSelected[getIndex(c.getSelectedIndex())].elementAt(i)).booleanValue()){
                        vpl.showGraph((GraphData)graphDatas[getIndex(c.getSelectedIndex())].elementAt(i));
                        isGraph = true;
                    }
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(this,"Processing Wafers, please wait","VPL Busy",
         			    JOptionPane.ERROR_MESSAGE);
        }
        if (!isGraph){
            vpl.refreshPane();
        }
    }

    private void setUpButtonRenderer(JTable myTable) {
        myTable.setDefaultRenderer(Integer.class, new ButtonRenderer(true));
    }

    //Set up the editor for the Button cells.
    private void setUpButtonEditor(JTable table) {
        //First, set up the button that brings up the dialog.
        ButtonRenderer button = new ButtonRenderer("");
        button.setBackground(Color.white);
        button.setBorderPainted(false);
        button.setMargin(new Insets(0,0,0,0));

        //Now create an editor to encapsulate the button, and
        //set it up as the editor for all Button cells.
        final ButtonEditor buttonEditor = new ButtonEditor(button);
        table.setDefaultEditor(Integer.class, buttonEditor);

        //Here's the code that brings up the picture.
        button.addActionListener(new ActionListener() {
  	    public void actionPerformed(ActionEvent event)
    	    {
                ButtonRenderer b = (ButtonRenderer)event.getSource();
                if (((Boolean)graphSelected[getIndex(c.getSelectedIndex())].elementAt(b.rowNo)).booleanValue()){
                    int s = graphSelected[getIndex(c.getSelectedIndex())].size();
                    for (int i=0; i<s; i++){
                        graphSelected[getIndex(c.getSelectedIndex())].setElementAt(new Boolean(false),i);
                    }
                }
                else{
                    int s = graphSelected[getIndex(c.getSelectedIndex())].size();
                    for (int i=0; i<s; i++){
                        graphSelected[getIndex(c.getSelectedIndex())].setElementAt(new Boolean(false),i);
                    }
                graphSelected[getIndex(c.getSelectedIndex())].setElementAt(new Boolean(true), (int)buttonEditor.currentGraph);
                }
            redo();
            }
        });
    }

    /*
     * The editor button that brings up the dialog.
     * We extend DefaultCellEditor for convenience,
     * even though it mean we have to create a dummy
     * check box.  Another approach would be to copy
     * the implementation of TableCellEditor methods
     * from the source code for DefaultCellEditor.
     */
    class ButtonEditor extends DefaultCellEditor {
        int currentGraph = -1;

        public ButtonEditor(ButtonRenderer b) {
                super(new JCheckBox()); //Unfortunately, the constructor
                                        //expects a check box, combo box,
                                        //or text field.
            editorComponent = b;
            setClickCountToStart(1); //This is usually 1 or 2.

            //Must do this so that editing stops when appropriate.
            b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }

        public Object getCellEditorValue() {
            Integer i = new Integer(currentGraph);
            return i;
        }

        public Component getTableCellEditorComponent(JTable table,
                                                     Object value,
                                                     boolean isSelected,
                                                     int row,
                                                     int column) {
            ((ButtonRenderer)editorComponent).setText(value.toString());
            currentGraph = ((Integer)value).intValue();
            ((ButtonRenderer)editorComponent).rowNo = currentGraph;
            return editorComponent;
        }
    }

    class MyTableModel extends AbstractTableModel{
        final String[] columnNames = {"Wafer", "Graph Type", "    ", "Time"};
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

        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 2 || col > 2) {
                return false;
            } else {
                return true;
            }
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
    private void initColumnSizes(JTable myTable, MyTableModel model) {
        TableColumn column = null;
        Component comp = null;
        int headerWidth = 0;

        for (int i = 0; i < 3; i++) {
            column = myTable.getColumnModel().getColumn(i);
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
        CardLayout cl = (CardLayout)(graphCards.getLayout());
        cl.show(graphCards, (String)evt.getItem());
        redo();
    }

    public void addGraphs(String string, Vector graphs){
        comboList.add(string);
        Component table = makeGraphTable(graphs);
        JPanel panel = new JPanel();
        panel.add(table);
        graphCards.add(string, panel);
    }

    public int getIndex(int comboIndex){
        return ((Integer)comboTracker.elementAt(comboIndex)).intValue();
    }

    public void sortGraphs(Wafer cell, boolean broken){
        String cellName;
        for (int j=0; j<cell.graphDatas.size(); j++){
            if (broken)
                cellName = cell.cellName + " (BROKEN)";
            else
                cellName = cell.cellName;
            int graphStage = ((Integer)cell.graphStages.elementAt(j)).intValue();
            GraphData graphData = (GraphData)cell.graphDatas.elementAt(j);
            String graphName = graphData.graphName;
//            graphDatas[j].addElement(graphData);
//            graphSelected[j].addElement(new Boolean(false));
            String time = (String)cell.graphTimes.elementAt(j);
            Integer result = null;
            Object tempArray = Array.newInstance(Object.class, 4);
            switch(graphStage){
                case 1: result = new Integer(((Vector)graphDatas[0]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        incomingWaferGraphs.addElement(tempArray);
                        ((Vector)graphDatas[0]).addElement(graphData);
                        ((Vector)graphSelected[0]).addElement(new Boolean(false));
                        break;
                // added by Bobh
                case 21: result = new Integer(((Vector)graphDatas[0]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        incomingWaferGraphs.addElement(tempArray);
                        ((Vector)graphDatas[0]).addElement(graphData);
                        ((Vector)graphSelected[0]).addElement(new Boolean(false));
                        break;

                        // end add Bobh

                case 2: result = new Integer(((Vector)graphDatas[1]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        postEtchGraphs.addElement(tempArray);
                        ((Vector)graphDatas[1]).addElement(graphData);
                        ((Vector)graphSelected[1]).addElement(new Boolean(false));
                        break;

                case 3: result = new Integer(((Vector)graphDatas[1]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        postEtchGraphs.addElement(tempArray);
                        ((Vector)graphDatas[1]).addElement(graphData);
                        ((Vector)graphSelected[1]).addElement(new Boolean(false));
                        break;

                 // add by for acid texturing graph tag under incoming batch and etch Bobh

                
               case 22: result = new Integer(((Vector)graphDatas[1]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        postEtchGraphs.addElement(tempArray);
                        ((Vector)graphDatas[1]).addElement(graphData);
                        ((Vector)graphSelected[1]).addElement(new Boolean(false));
                        break;

               case 23: result = new Integer(((Vector)graphDatas[1]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        postEtchGraphs.addElement(tempArray);
                        ((Vector)graphDatas[1]).addElement(graphData);
                        ((Vector)graphSelected[1]).addElement(new Boolean(false));
                        break;

                       // end add Bobh

                case 4: result = new Integer(((Vector)graphDatas[2]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        if (batch.texture){
                          postTextureGraphs.addElement(tempArray);
                          ((Vector)graphDatas[2]).addElement(graphData);
                          ((Vector)graphSelected[2]).addElement(new Boolean(false));
                        }
                        else {
                          postEtchGraphs.addElement(tempArray);
                          ( (Vector) graphDatas[1]).addElement(graphData);
                          ( (Vector) graphSelected[1]).addElement(new Boolean(false));
                        }
                        break;
                case 5: result = new Integer(((Vector)graphDatas[2]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        if (batch.texture){
                          postTextureGraphs.addElement(tempArray);
                          ((Vector)graphDatas[2]).addElement(graphData);
                          ((Vector)graphSelected[2]).addElement(new Boolean(false));
                        }
                        else {
                          postEtchGraphs.addElement(tempArray);
                          ( (Vector) graphDatas[1]).addElement(graphData);
                          ( (Vector) graphSelected[1]).addElement(new Boolean(false));
                        }
                        break;
                case 6: result = new Integer(((Vector)graphDatas[3]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        postDiffusionGraphs.addElement(tempArray);
                        ((Vector)graphDatas[3]).addElement(graphData);
                        ((Vector)graphSelected[3]).addElement(new Boolean(false));
                        break;
                case 7: result = new Integer(((Vector)graphDatas[3]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        postDiffusionGraphs.addElement(tempArray);
                        ((Vector)graphDatas[3]).addElement(graphData);
                        ((Vector)graphSelected[3]).addElement(new Boolean(false));
                        break;
                case 8: result = new Integer(((Vector)graphDatas[4]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        postAlScreenPrintGraphs.addElement(tempArray);
                        ((Vector)graphDatas[4]).addElement(graphData);
                        ((Vector)graphSelected[4]).addElement(new Boolean(false));
                        break;
                case 9: result = new Integer(((Vector)graphDatas[5]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        postAlFiringGraphs.addElement(tempArray);
                        ((Vector)graphDatas[5]).addElement(graphData);
                        ((Vector)graphSelected[5]).addElement(new Boolean(false));
                        break;
                case 10: result = new Integer(((Vector)graphDatas[5]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        postAlFiringGraphs.addElement(tempArray);
                        ((Vector)graphDatas[5]).addElement(graphData);
                        ((Vector)graphSelected[5]).addElement(new Boolean(false));
                        break;
                case 11: result = new Integer(((Vector)graphDatas[6]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        postPlasmaEtchGraphs.addElement(tempArray);
                        ((Vector)graphDatas[6]).addElement(graphData);
                        ((Vector)graphSelected[6]).addElement(new Boolean(false));
                        break;
                case 12: result = new Integer(((Vector)graphDatas[7]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        postPlasmaEtchGraphs.addElement(tempArray);
                        ((Vector)graphDatas[6]).addElement(graphData);
                        ((Vector)graphSelected[6]).addElement(new Boolean(false));
                        break;
                case 13: result = new Integer(((Vector)graphDatas[7]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        if (batch.arc){
                          postArCoatingGraphs.addElement(tempArray);
                          ((Vector)graphDatas[7]).addElement(graphData);
                          ((Vector)graphSelected[7]).addElement(new Boolean(false));
                        }
                        else {
                          postPlasmaEtchGraphs.addElement(tempArray);
                          ((Vector)graphDatas[6]).addElement(graphData);
                          ((Vector)graphSelected[6]).addElement(new Boolean(false));
                        }
                        break;
                case 14: result = new Integer(((Vector)graphDatas[7]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        if (batch.arc){
                          postArCoatingGraphs.addElement(tempArray);
                          ((Vector)graphDatas[7]).addElement(graphData);
                          ((Vector)graphSelected[7]).addElement(new Boolean(false));
                        }
                        else {
                          postPlasmaEtchGraphs.addElement(tempArray);
                          ((Vector)graphDatas[6]).addElement(graphData);
                          ((Vector)graphSelected[6]).addElement(new Boolean(false));
                        }
                        break;
                case 15: result = new Integer(((Vector)graphDatas[8]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        postSilverScreenPrintGraphs.addElement(tempArray);
                        ((Vector)graphDatas[8]).addElement(graphData);
                        ((Vector)graphSelected[8]).addElement(new Boolean(false));
                        break;
                case 16: result = new Integer(((Vector)graphDatas[9]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        finishedCellGraphs.addElement(tempArray);
                        ((Vector)graphDatas[9]).addElement(graphData);
                        ((Vector)graphSelected[9]).addElement(new Boolean(false));
                        break;

                case 26: result = new Integer(((Vector)graphDatas[3]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        postDiffusionGraphs.addElement(tempArray);
                        ((Vector)graphDatas[3]).addElement(graphData);
                        ((Vector)graphSelected[3]).addElement(new Boolean(false));
                        break;
                case 27: result = new Integer(((Vector)graphDatas[3]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        postDiffusionGraphs.addElement(tempArray);
                        ((Vector)graphDatas[3]).addElement(graphData);
                        ((Vector)graphSelected[3]).addElement(new Boolean(false));
                        break;
                case 28: result = new Integer(((Vector)graphDatas[10]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        cfpostPlasmaEtchGraphs.addElement(tempArray);
                        ((Vector)graphDatas[10]).addElement(graphData);
                        ((Vector)graphSelected[10]).addElement(new Boolean(false));
                        break;
                case 29: result = new Integer(((Vector)graphDatas[11]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        cfpostPlasmaEtchGraphs.addElement(tempArray);
                        ((Vector)graphDatas[10]).addElement(graphData);
                        ((Vector)graphSelected[10]).addElement(new Boolean(false));
                        break;
                case 30: result = new Integer(((Vector)graphDatas[11]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        if (batch.arc){
                          cfpostArCoatingGraphs.addElement(tempArray);
                          ((Vector)graphDatas[11]).addElement(graphData);
                          ((Vector)graphSelected[11]).addElement(new Boolean(false));
                        }
                        else {
                          cfpostPlasmaEtchGraphs.addElement(tempArray);
                          ((Vector)graphDatas[10]).addElement(graphData);
                          ((Vector)graphSelected[10]).addElement(new Boolean(false));
                        }
                        break;
                case 31: result = new Integer(((Vector)graphDatas[12]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        if (batch.arc){
                          cfpostArCoatingGraphs.addElement(tempArray);
                          ((Vector)graphDatas[11]).addElement(graphData);
                          ((Vector)graphSelected[11]).addElement(new Boolean(false));
                        }
                        else {
                          cfpostPlasmaEtchGraphs.addElement(tempArray);
                          ((Vector)graphDatas[10]).addElement(graphData);
                          ((Vector)graphSelected[10]).addElement(new Boolean(false));
                        }
                        break;
                case 32: result = new Integer(((Vector)graphDatas[12]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        cfpostSilverScreenPrintGraphs.addElement(tempArray);
                        ((Vector)graphDatas[12]).addElement(graphData);
                        ((Vector)graphSelected[12]).addElement(new Boolean(false));
                        break;
                case 33: result = new Integer(((Vector)graphDatas[12]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        cfpostSilverScreenPrintGraphs.addElement(tempArray);
                        ((Vector)graphDatas[12]).addElement(graphData);
                        ((Vector)graphSelected[12]).addElement(new Boolean(false));
                        break;
                case 34: result = new Integer(((Vector)graphDatas[13]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        cfpostAlScreenPrintGraphs.addElement(tempArray);
                        ((Vector)graphDatas[13]).addElement(graphData);
                        ((Vector)graphSelected[13]).addElement(new Boolean(false));
                        break;
                case 35: result = new Integer(((Vector)graphDatas[14]).size());
                        Array.set(tempArray,0,cellName);
                        Array.set(tempArray,1,graphName);
                        Array.set(tempArray,2,result);
                        Array.set(tempArray,3,time);
                        cffinishedCellGraphs.addElement(tempArray);
                        ((Vector)graphDatas[14]).addElement(graphData);
                        ((Vector)graphSelected[14]).addElement(new Boolean(false));
                        break;


            }
        }
    }

}
