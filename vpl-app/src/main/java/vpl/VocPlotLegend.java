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

public class VocPlotLegend extends JPanel {

    static protected Color[] colors = {
        new Color(0,0,0),   // darkest
        new Color(153,0,0),
        new Color(204,51,51),
        new Color(255,153,51),
        new Color(255,255,153)   // lightest
    };

    static protected Color[] colors1 = {
        new Color(48,36,138),   // darkest
        new Color(114,100,213),
        new Color(150,147,225)   // lightest
    };

    private Vector legendStrings;
    private Vector legendStrings1;
    private String title;

  public VocPlotLegend(String graphName, Vector labels, Vector labels1) {
        setLayout(new FlowLayout(FlowLayout.LEFT,10,5));
        title = graphName;
        legendStrings = labels;
        legendStrings1 = labels1;
        JPanel littlePanel = new JPanel(){
	    public Dimension getMinimumSize() {
		return getPreferredSize();
	    }
	    public Dimension getPreferredSize() {
		return new Dimension(200, 25+(20*(legendStrings.size()+legendStrings1.size())));
	    }
	    public Dimension getMaximumSize() {
		return getPreferredSize();
	    }

            public void paintComponent(Graphics g) {
                super.paintComponent( g );
                int spacing = 20;
                for (int i=0; i<legendStrings.size(); i++){
                    String legend = (String)legendStrings.elementAt(i) + " mV";
                    g.setColor(colors[i]);
                    g.fillRect(5,28+(i*spacing), 6, 6);
                    g.setColor(Color.black);
                    g.drawString(legend, 20, 35+(i*spacing));
                }
                for (int i=0; i<legendStrings1.size()-1; i++){
                    String legend = (String)legendStrings1.elementAt(i) + " mV";
                    g.setColor(colors1[i]);
                    int newi = i+legendStrings.size();
                    g.fillRect(5,28+(newi*spacing), 6, 6);
                    g.setColor(Color.black);
                    g.drawString(legend, 20, 35+(newi*spacing));
                }
                String legend = (String)legendStrings1.elementAt((legendStrings1.size()-1)) + " mV";
                int newesti = legendStrings.size() + legendStrings1.size()-1;
                g.setColor(colors1[2]);
                g.fillRect(5,28+(newesti*spacing), 6, 6);
                g.setColor(Color.black);
                g.drawString(legend, 20, 35+(newesti*spacing));

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

        JLabel unitsLabel = new JLabel("Open Circuit Voltage Contour Map (mV)");
        unitsLabel.setForeground(Color.black);
        unitsLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
        unitsLabel.setHorizontalAlignment(JLabel.CENTER);
        add(unitsLabel);

        add(littlePanel);
        littlePanel.repaint();
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
