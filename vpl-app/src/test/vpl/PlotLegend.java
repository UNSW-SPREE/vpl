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

public class PlotLegend extends JPanel {
    // Default _colors, by data set.
    // There are 11 colors so that combined with the
    // 10 marks of the Plot class, we can distinguish 110
    // distinct data sets.
    static protected Color[] colors = {
        new Color(0xff0000),   // red
        new Color(0x0000ff),   // blue
        new Color(0x00aaaa),   // cyan-ish
        new Color(0x000000),   // black
        new Color(0xffa500),   // orange
        new Color(0x53868b),   // cadetblue4
        new Color(0xff7f50),   // coral
        new Color(0x45ab1f),   // dark green-ish
        new Color(0x90422d),   // sienna-ish
        new Color(0xa0a0a0),   // grey-ish
        new Color(0x14ff14),   // green-ish
    };

    private Vector legendStrings;
    private String title;
    private NumberFormat format;

  public PlotLegend(String graphName, String[] labels, double[] values, double unitsTest) {
        format = NumberFormat.getNumberInstance();

        setLayout(new FlowLayout(FlowLayout.LEFT,10,5));
        title = graphName;
        legendStrings = new Vector();
        if (graphName.equals("Pseudo IV Curve") || graphName.equals("IV Curve")){
            legendStrings.add(labels[1]);
        }
        else if (graphName.equals("Doping Profile")){
            legendStrings.add(labels[1]);
            legendStrings.add(labels[2]);
        }
        else if (graphName.equals("Reflection & QE Spectral Response")){
            legendStrings.add(labels[1]);
            legendStrings.add(labels[2]);
            legendStrings.add(labels[3]);
        }
        else if (graphName.equals("Reflection Test")){
            legendStrings.add(labels[3]);
        }
        JPanel littlePanel = new JPanel(){
	    public Dimension getMinimumSize() {
		return getPreferredSize();
	    }
	    public Dimension getPreferredSize() {
		return new Dimension(200, 25+20*legendStrings.size());
	    }
	    public Dimension getMaximumSize() {
		return getPreferredSize();
	    }

            public void paintComponent(Graphics g) {
                super.paintComponent( g );
                int spacing = 20;
                for (int i=0; i<legendStrings.size(); i++){
                    String legend = (String)legendStrings.elementAt(i);
                    if (title.equals("Reflection Test"))
                        g.setColor(colors[2]);
                    else
                        g.setColor(colors[i]);
                    g.fillRect(5,28+i*spacing, 6, 6);
                    g.setColor(Color.black);
                    g.drawString(legend, 20, 35+i*spacing);
                }
            }
        };
        littlePanel.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createTitledBorder("Legend"),
                        BorderFactory.createEmptyBorder(2,2,2,2)));
        JLabel titleLabel = new JLabel(title);
        Dimension titleSize = new Dimension(200,30);
        titleLabel.setPreferredSize(titleSize);
        titleLabel.setForeground(new Color(16,9,115));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel);

        JLabel ul = new JLabel("Units: ");
        ul.setForeground(Color.black);
        ul.setFont(new Font("Dialog", Font.PLAIN, 10));
        add(ul);

        JLabel unitsLabel = null;
        if (graphName.equals("Pseudo IV Curve") || graphName.equals("IV Curve")){
            unitsLabel = new JLabel("Current Density (mA/cm\u00B2) vs Voltage (mV)");
        }
        else if (graphName.equals("Doping Profile")){
            if (unitsTest >10)
                unitsLabel = new JLabel("Doping Density (cm-3) vs Dist from front (nm)");
            else
                unitsLabel = new JLabel("Doping Density (cm-3) vs Dist from front (µm)");
        }
        else if (graphName.equals("Reflection & QE Spectral Response")){
            unitsLabel = new JLabel("Quantum Efficiency (%) vs Wavelength (nm)");
        }
        else if (graphName.equals("Reflection Test")){
            unitsLabel = new JLabel("Reflection (%) vs Wavelength (nm)");
        }
        unitsLabel.setForeground(Color.black);
        unitsLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
//        unitsLabel.setHorizontalAlignment(JLabel.CENTER);
        add(unitsLabel);

        add(littlePanel);
        littlePanel.repaint();

        if (graphName.equals("Pseudo IV Curve") || graphName.equals("IV Curve")){
    	    format.setMaximumFractionDigits(0);
	    format.setMinimumFractionDigits(0);
            JLabel vocLabel = new JLabel("Voc = " + format.format(values[0]*1000) + " mV"){
                public Dimension getMinimumSize() {
                    return getPreferredSize();
        	}
	        public Dimension getPreferredSize() {
        	    return new Dimension(200,super.getPreferredSize().height);
                }
        	public Dimension getMaximumSize() {
	            return getPreferredSize();
                }
            };
            vocLabel.setForeground(Color.darkGray);
            add(vocLabel);
    	    format.setMaximumFractionDigits(1);
	    format.setMinimumFractionDigits(1);
            JLabel iscLabel;
            if (graphName.equals("Pseudo IV Curve"))
                iscLabel = new JLabel("Normalised Jsc = " + format.format(values[1]*1000) + " mA/cm\u00B2");
            else
                iscLabel = new JLabel("Jsc = " + format.format(values[1]*1000) + " mA/cm\u00B2");
            iscLabel.setForeground(Color.darkGray);
            add(iscLabel);
    	    format.setMaximumFractionDigits(2);
	    format.setMinimumFractionDigits(2);
            JLabel maxPowerLabel = new JLabel("Maximum Power = " + format.format(values[2]*1000) + " mW/cm\u00B2");
            maxPowerLabel.setForeground(Color.darkGray);
            add(maxPowerLabel);
            JLabel efficiencyLabel = new JLabel("Efficiency = " + format.format(values[2]*1000) + " %");
            efficiencyLabel.setForeground(Color.darkGray);
            add(efficiencyLabel);
    	    format.setMaximumFractionDigits(2);
	    format.setMinimumFractionDigits(2);
            JLabel fillFactorLabel = new JLabel("Fill Factor = " + format.format(values[2]/(values[1]*values[0])));
            fillFactorLabel.setForeground(Color.darkGray);
            add(fillFactorLabel);
            if (graphName.equals("Pseudo IV Curve")){
                JLabel extraLabel = new JLabel("(For Jsc normalised to 30 mA/cm\u00B2)");
                add(extraLabel);
                extraLabel.setForeground(Color.darkGray);
            }
        }

        JLabel infoLabel1 = new JLabel("To zoom in, drag the mouse");
        JLabel infoLabel2 = new JLabel("downward to draw a box.");
        JLabel infoLabel3 = new JLabel("To zoom out, drag the mouse upward");
        Font infoFont = new Font("Dialog", Font.PLAIN, 12);
        infoLabel1.setFont(infoFont);
        infoLabel2.setFont(infoFont);
        infoLabel3.setFont(infoFont);
        add(infoLabel1);
        add(infoLabel2);
        add(infoLabel3);
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

}
