package vpl;
import javax.media.bean.playerbean.*;
import javax.media.*;
import com.sun.media.ui.*;
import javax.media.protocol.*;
import javax.media.format.*;
import javax.media.control.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.Vector;

/**
 * Title:        Virtual Production Line<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Anna Bruce<p>
 * Company:      UNSW PV Engineering<p>
 * @author Anna Bruce
 * @version 1.0
 */

public class Video extends JPanel implements ControllerListener, Runnable {
    Component visual = null;
    Component control = null;
    public MediaPlayer playerBean = null;
    VirtualProductionLine vpl;
    String file;
    Thread videoThread = null;

    static void Fatal(String s) {
        // MessageBox mb = new MessageBox("JMF Error", s); ///Commented out for Maven Compile
    }

    public Video(String file, VirtualProductionLine vpl) {
        this.vpl = vpl;
        this.file = file;
        setLayout( new BorderLayout() );
        setVisible(true);

        Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, new Boolean(true));
        openFile("file:" + file);
    }

    public Dimension getMinimumSize() {
        return getPreferredSize();
    }
    public Dimension getPreferredSize() {
        return new Dimension(230, 200);
    }
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    /**
     * Open a media file.
     */
    public void openFile(String filename) {
        String mediaFile = filename;
        // Create an instance of a player for this media
        playerBean = new MediaPlayer();
	if (!mediaFile.equals("")) {
	    playerBean.setMediaLocator( new MediaLocator(mediaFile) );
	    if (playerBean.getPlayer() == null) {
	        Fatal ( "Can't create player for " + mediaFile );

	    }
	}

 	if (playerBean != null) {
	    playerBean.addControllerListener( this );
	    playerBean.realize();
            setLayout(new BorderLayout());
            setVisible(true);
            setDoubleBuffered(true);
        }
    }

    public void controllerUpdate(ControllerEvent ce) {
        if (ce instanceof RealizeCompleteEvent) {
            playerBean.prefetch();
        } else if (ce instanceof PrefetchCompleteEvent) {
            if (visual != null)
                return;

            if ((visual = playerBean.getVisualComponent()) != null) {
                add("Center", visual);
            }

            if ((control = playerBean.getControlPanelComponent()) != null) {
                add("South", control);
            }
            playerBean.start();
            validate();
        } else if (ce instanceof EndOfMediaEvent) {
            playerBean.stopAndDeallocate();
            playerBean.close();
            vpl.finishProcess();
        }
        else if (ce instanceof ControllerErrorEvent){
            playerBean.stopAndDeallocate();
            playerBean.close();
//            stop();
            vpl.finishProcess();
        }
    }

    public void start(){
        if (videoThread == null){
            videoThread = new Thread(this);
            videoThread.start();
        }
    }

    public void run(){
        Thread myThread = Thread.currentThread();
        while (videoThread == myThread){
            if (playerBean.getState() == playerBean.Started){
            }
            else {
                vpl.finishProcess();
            }
            try{
                Thread.sleep(2000);
            } catch (InterruptedException e){
            }
        }
    }

    public void stop(){
        videoThread = null;
    }

}