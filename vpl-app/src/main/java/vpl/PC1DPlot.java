package vpl;
import java.awt.*;
import javax.swing.*;
import java.util.*;

/**
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) Anna Bruce<p>
 * Company:      UNSW PV Engineering<p>
 * @author Anna Bruce
 * @version 1.0
 */


public class PC1DPlot extends JPanel{

    Plot plot;
    boolean yexists = false;
    boolean zexists = false;
    boolean wexists = false;
    boolean vexists = false;
    Vector xpoints = new Vector();
    Vector ypoints = new Vector();
    Vector zpoints = new Vector();
    Vector wpoints = new Vector();
    Vector vpoints = new Vector();

  public PC1DPlot(Vector graphs) {
        GraphData graph0 = (GraphData)graphs.elementAt(0);
        setSize(300,300);
        setBorder(BorderFactory.createBevelBorder(0));
        setLayout( new BorderLayout() );

        plot = new Plot();

        // find out if there is more than one set of points to plot
        if (graph0.ypoints.size() > 0)
            yexists = true;
        if (graph0.zpoints.size() > 0)
            zexists = true;
        if (graph0.wpoints.size() > 0)
            wexists = true;
        if (graph0.vpoints.size() > 0)
            vexists = true;

        xpoints = graph0.xpoints;
        if (yexists){
            ypoints = graph0.ypoints;
        }
        if (zexists){
            zpoints = graph0.zpoints;
        }
        if (wexists){
            wpoints = graph0.wpoints;
        }
        if (vexists){
            vpoints = graph0.vpoints;
        }

        if (graph0.graphName.equals("IV Curve")){
            plot.setXLabel("Voltage (mV)");
//            addXTick(String label, double position)
            plot.addXTick("0",0);
            plot.addXTick("100",100);
            plot.addXTick("200",200);
            plot.addXTick("300",300);
            plot.addXTick("400",400);
            plot.addXTick("500",500);
            plot.addXTick("600",600);
            plot.addXTick("700",700);
            plot.addXTick("800",800);
            plot.setYLabel("Current Density");
            plot.addYTick("0",0);
            plot.addYTick("5",5);
            plot.addYTick("10",10);
            plot.addYTick("15",15);
            plot.addYTick("20",20);
            plot.addYTick("25",25);
            plot.addYTick("30",30);
            plot.addYTick("35",35);
        }
        else if (graph0.graphName.equals("Pseudo IV Curve")){
            plot.setXLabel("Voltage (mV)");
//            addXTick(String label, double position)
            plot.addXTick("0",0);
            plot.addXTick("100",100);
            plot.addXTick("200",200);
            plot.addXTick("300",300);
            plot.addXTick("400",400);
            plot.addXTick("500",500);
            plot.addXTick("600",600);
            plot.addXTick("700",700);
            plot.addXTick("800",800);
            plot.setYLabel("Jsc Normalised");
            plot.addYTick("0",0);
            plot.addYTick("5",5);
            plot.addYTick("10",10);
            plot.addYTick("15",15);
            plot.addYTick("20",20);
            plot.addYTick("25",25);
            plot.addYTick("30",30);
            plot.addYTick("35",35);
        }
        else if (graph0.graphName.equals("Doping Profile")){
            plot.setXLabel("Distance From Front");
            plot.setYLabel("Doping Density");
            plot.setYLog(true);
        }
        else if (graph0.graphName.equals("Reflection & QE Spectral Response")){
            plot.setXLabel("Wavelength (nm)");
            plot.addXTick("0",0);
            plot.addXTick("200",200);
            plot.addXTick("400",400);
            plot.addXTick("600",600);
            plot.addXTick("800",800);
            plot.addXTick("1000",1000);
            plot.addXTick("1200",1200);
            plot.setYLabel("%");
            ///////////////////////////////////////////
            plot.addYTick("0",0);
            plot.addYTick("20",20);
            plot.addYTick("40",40);
            plot.addYTick("60",60);
            plot.addYTick("80",80);
            plot.addYTick("100",100);
            /////////////////////////////////////////////

        }
        else if (graph0.graphName.equals("Reflection Test")){
            plot.setXLabel("Wavelength (nm)");
            plot.addXTick("0",0);
            plot.addXTick("200",200);
            plot.addXTick("400",400);
            plot.addXTick("600",600);
            plot.addXTick("800",800);
            plot.addXTick("1000",1000);
            plot.addXTick("1200",1200);
            plot.setYLabel("%");
            //plot.addYTick("0",0);
            //plot.addYTick("5",5);
            //plot.addYTick("10",10);
            //plot.addYTick("15",15);
            //plot.addYTick("20",20);
            //plot.addYTick("25",25);
            //plot.addYTick("30",30);
            //plot.addYTick("35",35);
            //plot.addYTick("40",40);
            //plot.addYTick("45",45);
            //plot.addYTick("50",50);
            //plot.addYTick("55",55);
            //plot.addYTick("60",60);
            //plot.addYTick("65",65);
            //plot.addYTick("70",70);
        }
        if (yexists)
            plot.addPoint(0, ((Double)xpoints.elementAt(0)).doubleValue(), ((Double)ypoints.elementAt(0)).doubleValue(), false);
        if (zexists)
            plot.addPoint(1, ((Double)xpoints.elementAt(0)).doubleValue(), ((Double)zpoints.elementAt(0)).doubleValue(), false);
        if (wexists)
            plot.addPoint(2, ((Double)xpoints.elementAt(0)).doubleValue(), ((Double)wpoints.elementAt(0)).doubleValue(), false);
        if (vexists)
            plot.addPoint(3, ((Double)xpoints.elementAt(0)).doubleValue(), ((Double)vpoints.elementAt(0)).doubleValue(), false);

        for (int i=1; i<xpoints.size(); i++){
            if (yexists)
                plot.addPoint(0, ((Double)xpoints.elementAt(i)).doubleValue(), ((Double)ypoints.elementAt(i)).doubleValue(), true);
            if (zexists)
                plot.addPoint(1, ((Double)xpoints.elementAt(i)).doubleValue(), ((Double)zpoints.elementAt(i)).doubleValue(), true);
            if (wexists)
                plot.addPoint(2, ((Double)xpoints.elementAt(i)).doubleValue(), ((Double)wpoints.elementAt(i)).doubleValue(), true);
            if (vexists)
                plot.addPoint(3, ((Double)xpoints.elementAt(i)).doubleValue(), ((Double)vpoints.elementAt(i)).doubleValue(), true);
        }
        plot.fillPlot();
        add("Center", plot);
        repaint();
        }
}