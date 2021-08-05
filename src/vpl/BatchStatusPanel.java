package vpl;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.BorderFactory;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * Title:        Virtual Production Line<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Anna Bruce<p>
 * Company:      UNSW PV Engineering<p>
 * @author Anna Bruce
 * @version 1.0
 */

public class BatchStatusPanel extends javax.swing.JPanel implements ListSelectionListener
{
    JPanel topPanel;
    //JLabel sparePartsLabel = new javax.swing.JLabel();
    Batch batch;
    VirtualProductionLine vpl;
    JList historyList;
    JTree historyTree;
    String[] detailsArray;
    JList detailsList;
    JPanel detailsPanel;
    JLabel assignmentLabel;

    public BatchStatusPanel(VirtualProductionLine vpl)
    {
	this.batch = (Batch)vpl.batches.elementAt(vpl.cbIndex);
	this.vpl = vpl;
	this.setLayout(new GridLayout());
	setVisible(true);
	String currentBatchString = batch.batchName + ": " + (batch.noOfCells + batch.brokenCells.size()) + " wafers";
        JLabel currentBatchLabel = new JLabel(){
            Dimension minimumSize = new Dimension(200, super.getPreferredSize().height);

            public Dimension getMinimumSize() {
                return getPreferredSize();
            }
            public Dimension getPreferredSize() {
                return new Dimension(200, super.getPreferredSize().height);
            }
            public Dimension getMaximumSize() {
                return getPreferredSize();
            }
        };
	currentBatchLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	currentBatchLabel.setText(currentBatchString);
	currentBatchLabel.setFont(vpl.boldFont);
	currentBatchLabel.setForeground(vpl.buttonTextColour);

        if (batch.assignmentNo != -1){
	    String assignmentString = vpl.defaultSettings.assignmentStrings[batch.assignmentNo-1];
            assignmentLabel = new JLabel(){
                Dimension minimumSize = new Dimension(200, super.getPreferredSize().height);

                public Dimension getMinimumSize() {
                    return getPreferredSize();
                }
                public Dimension getPreferredSize() {
                    return new Dimension(200, super.getPreferredSize().height);
                }
                public Dimension getMaximumSize() {
                    return getPreferredSize();
                }
            };
	    assignmentLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	    assignmentLabel.setText(assignmentString);
    	    assignmentLabel.setFont(vpl.boldFont);
    	    assignmentLabel.setForeground(vpl.buttonTextColour);
      }

	topPanel = new JPanel(){
            Dimension minimumSize = new Dimension(200, super.getPreferredSize().height);

            public Dimension getMinimumSize() {
                return getPreferredSize();
            }
            public Dimension getPreferredSize() {
                return new Dimension(200, super.getPreferredSize().height);
            }
            public Dimension getMaximumSize() {
                return getPreferredSize();
            }
        };

        topPanel.setBorder(BorderFactory.createEtchedBorder());
        //by ly
	//topPanel.add(currentBatchLabel);
        //by ly
        if (batch.assignmentNo != -1){
            topPanel.add(assignmentLabel);
        }
        
	//sparePartsLabel.setText(batch.spareParts + " Spare Parts Remaining");
	//topPanel.add(sparePartsLabel);
        
        // Change from Jlist to Jtree
        
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(" "+currentBatchString);
        String[] detailsTree;
        JList detailsTreeList;
        for (int i=0;i<batch.batchHistory.size();i++){
            DefaultMutableTreeNode bs = new DefaultMutableTreeNode(batch.batchHistory.get(i));
            detailsTree = (String[])batch.batchHistoryDetails.elementAt(i);
            detailsTreeList = new JList(detailsTree);
            for (int j=0; j<detailsTree.length; j++){
                DefaultMutableTreeNode bsDetails = new DefaultMutableTreeNode(detailsTreeList.getModel().getElementAt(j));
                bs.add(bsDetails);
            }         
            rootNode.add(bs);
        }
        historyTree = new JTree(rootNode);
        historyTree.setBorder(BorderFactory.createBevelBorder(1));
        historyTree.setFont(new Font("Dialog", Font.PLAIN, 9));
        historyTree.setSelectionRow(batch.batchHistory.size());
        historyTree.expandRow(batch.batchHistory.size());
        
        DefaultTreeCellRenderer cellRenderer = new DefaultTreeCellRenderer();
        cellRenderer.setLeafIcon(null);
        // cellRenderer.setIcon(null);
        cellRenderer.setClosedIcon(null);
        cellRenderer.setOpenIcon(null);
        historyTree.setCellRenderer(cellRenderer);
        historyTree.setRowHeight(20);
        
        //historyList
        historyList = new JList(batch.batchHistory);
	historyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        historyList.setSelectionBackground(vpl.yellowColour);
	historyList.setSelectionForeground(new java.awt.Color(16,9,115));
	historyList.setFont(new Font("Dialog", Font.PLAIN, 9));
	historyList.setVisibleRowCount(batch.batchHistory.size());
	historyList.setBorder(BorderFactory.createBevelBorder(1));
	historyList.setSelectedIndex(batch.batchHistory.size()-1);
	historyList.addListSelectionListener(this);
        //</historyList>
        //topPanel.add(historyList);
        topPanel.add(historyTree);

        
        detailsPanel = new JPanel(){
	    Dimension minimumSize = new Dimension(210, super.getPreferredSize().height);

            public Dimension getMinimumSize() {
	        return getPreferredSize();
            }
            public Dimension getPreferredSize() {
	        return new Dimension(210, super.getPreferredSize().height);
            }
            public Dimension getMaximumSize() {
	        return getPreferredSize();
            }
        };
        //by ly
        //refreshDetailsPanel();
	//topPanel.add(detailsPanel);
        //by ly
        add(topPanel);

	this.doLayout();
	this.validate();
    }

    //sets the size of the panel
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }
    public Dimension getPreferredSize() {
        int temp = 0;
        if (batch.assignmentNo == -1)
            temp = 45;
        else
            temp = 75;
        int bhsize = topPanel.getPreferredSize().height;
        int bdsize = detailsPanel.getPreferredSize().height;
        return new Dimension(215, temp + bhsize + bdsize);
    }
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    public void refreshDetailsPanel(){
        detailsPanel.removeAll();
     	int result = historyList.getSelectedIndex();
		detailsArray = (String[])batch.batchHistoryDetails.elementAt(result);
		detailsList = new JList(detailsArray);
		detailsList.setFont(new Font("Dialog", Font.PLAIN, 9));
		detailsList.setVisibleRowCount(detailsArray.length);
		detailsList.setBorder(BorderFactory.createBevelBorder(1));
		detailsPanel.add(detailsList);
                this.setPreferredSize(getPreferredSize());
		//this.doLayout();
		this.validate();
                vpl.bsPanel.doLayout();
                vpl.validate();
    }

    public void valueChanged(ListSelectionEvent e) {
        refreshDetailsPanel();
    }

}