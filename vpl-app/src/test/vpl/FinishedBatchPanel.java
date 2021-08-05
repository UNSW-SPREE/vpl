package vpl;
/**
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) Anna Bruce<p>
 * Company:      UNSW PV Engineering<p>
 * @author Anna Bruce
 * @version 1.0
 */
import java.awt.*;
import java.util.Vector;
import javax.swing.*;
import java.text.NumberFormat;

public class FinishedBatchPanel extends JPanel {
    private NumberFormat format;
    Batch batch;

  public FinishedBatchPanel(Batch batch) {
        this.batch = batch;
        format = NumberFormat.getNumberInstance();
        setVisible(true);

  }

        Dimension minimumSize = new Dimension(240,400);

        public Dimension getMinimumSize() {
            return getPreferredSize();
	}
	public Dimension getPreferredSize() {
	    return new Dimension(240,400);
        }
	public Dimension getMaximumSize() {
	    return getPreferredSize();
        }

        public void setVisible(boolean b){
            if (b){
                removeAll();
                batch.calculateTotals();
                double noc;
                if (batch.special)
                    noc = batch.noOfCells;
                else
                    noc = batch.noOfCells + batch.brokenCells.size();

                setLayout(new FlowLayout(FlowLayout.LEFT,10,5));
                JLabel titleLabel = new JLabel(batch.batchName + " - Processing Complete");
                Dimension titleSize = new Dimension(200,30);
                titleLabel.setPreferredSize(titleSize);
                titleLabel.setForeground(new Color(16,9,115));
                titleLabel.setHorizontalAlignment(JLabel.CENTER);
                add(titleLabel);

                if (batch.special){
                    JLabel specialLabel = new JLabel("Special Batch: Averages calculated");
                    add(specialLabel);

                    JLabel specialLabela = new JLabel("only for non-broken cells.");
                    add(specialLabela);

                    JLabel specialLabel1 = new JLabel("Number of wafers broken: " + batch.brokenCells.size());
                    add(specialLabel1);
                }

                if (batch.noOfCells >0){
                    format.setMaximumFractionDigits(0);
                    format.setMinimumFractionDigits(0);
                    JLabel vocLabel = new JLabel("Average Voc = " + format.format((batch.totalVoc*1000)/noc) + " mV");
                    vocLabel.setForeground(Color.darkGray);
                    add(vocLabel);
                    format.setMaximumFractionDigits(1);
                    format.setMinimumFractionDigits(1);
                    JLabel iscLabel = new JLabel("Average Jsc = " + format.format(batch.totalJsc*1000/noc) + " mA/cm\u00B2");
                    iscLabel.setForeground(Color.darkGray);
                    add(iscLabel);
                    format.setMaximumFractionDigits(2);
                    format.setMinimumFractionDigits(2);
                    JLabel maxPowerLabel = new JLabel("Average Max Power = " + format.format(batch.totalMaxPower*1000/noc) + " mW/cm\u00B2");
                    maxPowerLabel.setForeground(Color.darkGray);
                    add(maxPowerLabel);
                    format.setMaximumFractionDigits(2);
                    format.setMinimumFractionDigits(2);
                    JLabel efficiencyLabel = new JLabel("Average Efficiency = " + format.format(batch.totalMaxPower*1000/noc) + " %");
                    efficiencyLabel.setForeground(Color.darkGray);
                    format.setMaximumFractionDigits(2);
                    format.setMinimumFractionDigits(2);
                    add(efficiencyLabel);
                    JLabel fillFactorLabel = new JLabel("Average Fill Factor = " + format.format(batch.totalFillFactor/noc));
                    fillFactorLabel.setForeground(Color.darkGray);
                    add(fillFactorLabel);
                    JLabel waferCostLabel = new JLabel("Average Wafer Cost= " + format.format(batch.totalCost/noc) + " c/wafer");
                    add(waferCostLabel);
                    JLabel costOfOwnershipLabel = new JLabel("Average CoO= " + format.format(batch.costOfOwnership) + " c/W");
                    add(costOfOwnershipLabel);
                    Wafer c = batch.getMostEfficientCell();
                    JLabel mostEfficientCellLabel = new JLabel("Highest Efficiency Cell - " + c.cellName + ": ");
                    fillFactorLabel.setForeground(Color.darkGray);
                    add(mostEfficientCellLabel);
                    JLabel highestEfficiencyLabel = new JLabel("efficiency = " + format.format(c.maxpower*1000) + " %");
                    add(highestEfficiencyLabel);
                    
                }
                else {
                    JLabel emptyBatchLabel0 = new JLabel("All the wafers in " + batch.batchName);
                    JLabel emptyBatchLabel1 = new JLabel("have been broken.");
                    JLabel emptyBatchLabel2 = new JLabel("This batch cannot be processed.");
                    JLabel emptyBatchLabel3 = new JLabel("");
                    emptyBatchLabel0.setForeground(Color.darkGray);
                    emptyBatchLabel1.setForeground(Color.darkGray);
                    emptyBatchLabel2.setForeground(Color.darkGray);
                    emptyBatchLabel3.setForeground(Color.darkGray);
                    add(emptyBatchLabel0);
                    add(emptyBatchLabel1);
                    add(emptyBatchLabel2);
                    add(emptyBatchLabel3);
                }
            }
        }

}
