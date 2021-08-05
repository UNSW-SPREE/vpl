package vpl;
import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import java.net.URL;

public class JAboutDialog extends javax.swing.JDialog
{
    JButton okButton = new JButton();

    public JAboutDialog(Frame parentFrame)
    {
        super(parentFrame);
        setTitle("About Virtual Production Line");
        setModal(true);
        getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
        setSize(340,235);
        setVisible(false);
        URL iconURL = null;
        String urlPrefix = "file:" + System.getProperty("user.dir")
            + System.getProperty("file.separator");
        String filename = "icons" + System.getProperty("file.separator") + "VPL3_Icon.gif";
        try {
            iconURL = new URL(urlPrefix + filename);
        } catch (java.net.MalformedURLException exc) {
            System.err.println("Attempted to create an icon "
                          + "with a bad URL: " + iconURL);
            iconURL = null;
        }
        ImageIcon icon = new ImageIcon(iconURL);
        Font bigFont = new Font("Arial Narrow", Font.ITALIC, 20);
	JLabel iconLabel = new JLabel(icon);
        iconLabel.setBorder(BorderFactory.createBevelBorder(1));
        getContentPane().add(iconLabel);
        JLabel nameLabel = new JLabel("Virtual Production Line");
        nameLabel.setFont(bigFont);
        nameLabel.setForeground(Color.darkGray);
        getContentPane().add(nameLabel);
        JLabel infoLabel = new JLabel("Version 4.0: Screen Printed Solar Cells");
        infoLabel.setForeground(Color.darkGray);
	getContentPane().add(infoLabel);
	JLabel cLabel = new JLabel("Copyright © Stuart Wenham, Alison Lennon (technical)");
	getContentPane().add(cLabel);
        JLabel c1Label = new JLabel("Anna Bruce, Bob Hum (programming)");
        getContentPane().add(c1Label);
        JLabel c2Label = new JLabel("Zhongtian Li, Yang Li (programming)");
        getContentPane().add(c2Label);
        JLabel c3Label = new JLabel("Quanzhou Yu (programming)");
        getContentPane().add(c3Label);
        JLabel c4Label = new JLabel("Giles Alexander, Rob Largent (multimedia)");
        getContentPane().add(c4Label);
//        cLabel.setForeground(Color.darkGray);

        JLabel centreLabel = new JLabel("School Of Photovoltaic & Renewable Energy Engineering");
        getContentPane().add(centreLabel);
        //JLabel centre1Label = new JLabel("Renewable Energy Engineering");
        //getContentPane().add(centre1Label);

        JLabel unswLabel = new JLabel("               The University of New South Wales               ");
        getContentPane().add(unswLabel);
	okButton.setText("OK");
	okButton.setActionCommand("OK");
	getContentPane().add(okButton);

	SymWindow aSymWindow = new SymWindow();
	this.addWindowListener(aSymWindow);
	SymAction lSymAction = new SymAction();
	okButton.addActionListener(lSymAction);
    }

    public void setVisible(boolean b)
    {
        if (b){
            Rectangle bounds = (getParent()).getBounds();
            Dimension size = getSize();
    	    setLocation(bounds.x + (bounds.width - size.width)/2,
    		        bounds.y + (bounds.height - size.height)/2);
	}

	super.setVisible(b);
    }

    public void addNotify()
    {
    // Record the size of the window prior to calling parents addNotify.
	Dimension d = getSize();

	super.addNotify();

	if (fComponentsAdjusted)
            return;
	// Adjust components according to the insets
        Insets insets = getInsets();
	setSize(insets.left + insets.right + d.width, insets.top + insets.bottom + d.height);
	Component components[] = getContentPane().getComponents();
	for (int i = 0; i < components.length; i++)
	    {
	    Point p = components[i].getLocation();
	    p.translate(insets.left, insets.top);
	    components[i].setLocation(p);
	}
	fComponentsAdjusted = true;
    }

    // Used for addNotify check.
    boolean fComponentsAdjusted = false;

    class SymWindow extends java.awt.event.WindowAdapter
    {
	public void windowClosing(java.awt.event.WindowEvent event)
	{
	    Object object = event.getSource();
	    if (object == JAboutDialog.this)
	        jAboutDialog_windowClosing(event);
	}
    }

    void jAboutDialog_windowClosing(java.awt.event.WindowEvent event)
    {
    // to do: code goes here.

        jAboutDialog_windowClosing_Interaction1(event);
    }

    void jAboutDialog_windowClosing_Interaction1(java.awt.event.WindowEvent event) {
	try {
	// JAboutDialog Hide the JAboutDialog
            this.setVisible(false);
	} catch (Exception e) {
        }
    }

    class SymAction implements java.awt.event.ActionListener
    {
	public void actionPerformed(java.awt.event.ActionEvent event)
            {
	    Object object = event.getSource();
	    if (object == okButton)
		okButton_actionPerformed(event);
	}
    }

    void okButton_actionPerformed(java.awt.event.ActionEvent event)
    {
	try {
	// JAboutDialog Hide the JAboutDialog
            this.setVisible(false);
	} catch (Exception e) {
	}
    }
}