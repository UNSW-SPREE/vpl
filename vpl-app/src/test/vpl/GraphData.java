package vpl;
/**
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) Anna Bruce<p>
 * Company:      UNSW PV Engineering<p>
 * @author Anna Bruce
 * @version 1.0
 */

import java.io.*;
import java.util.*;

public class GraphData implements Serializable{
    String graphName;
    String xaxisname;
    String yaxisname;
    String zaxisname;
    String waxisname;
    String vaxisname;
    Vector xpoints;
    Vector ypoints;
    Vector zpoints;
    Vector wpoints;
    Vector vpoints;
    double voc;
    double isc;
    double maxPower;

    public GraphData(String graphName, String xaxisname, String yaxisname,
                    String zaxisname, String waxisname, String vaxisname,
                    Vector xVector, Vector yVector, Vector zVector,
                    Vector wVector, Vector vVector, double voc, double isc, double maxPower) {
      this.graphName = graphName;
      this.xaxisname = xaxisname;
      this.yaxisname = yaxisname;
      this.zaxisname = zaxisname;
      this.waxisname = waxisname;
      this.vaxisname = vaxisname;
      this.xpoints = xVector;
      this.ypoints = yVector;
      this.zpoints = zVector;
      this.wpoints = wVector;
      this.vpoints = vVector;
      this.voc = voc;
      this.isc = isc;
      this.maxPower = maxPower;
    }
}