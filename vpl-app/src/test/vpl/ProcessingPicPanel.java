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

public class ProcessingPicPanel extends JPanel{
    ImageIcon pic;
    Image image;
    Dimension size;

    public ProcessingPicPanel(String urlString, Dimension d){
        pic = new ImageIcon(urlString);
        this.size = d;
        repaint();
    }
    Dimension minimumSize = size;
    public Dimension getMinimumSize() {
        return size;
    }
    public Dimension getPreferredSize() {
        return size;
    }
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    public void paintComponent(Graphics g){
        super.paintComponent( g );
        if (pic.getImageLoadStatus() == MediaTracker.COMPLETE ) {
            image = pic.getImage();
            g.drawImage(image,0,0,size.width,size.height,this);
      }
    }

}
