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
import java.net.URL;

import javax.swing.*;

public class PicPanel extends JPanel{
    ImageIcon pic;
    Image image;

    public PicPanel(URL location){
        pic = new ImageIcon(location);
        repaint();
    }

    public void paintComponent(Graphics g){
        super.paintComponent( g );
        if (pic.getImageLoadStatus() == MediaTracker.COMPLETE ) {
            image = pic.getImage();
            g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
      }
    }

}
