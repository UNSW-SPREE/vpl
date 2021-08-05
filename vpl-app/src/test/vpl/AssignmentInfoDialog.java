package vpl;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.io.IOException;

/**
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) Anna Bruce<p>
 * Company:      UNSW PV Engineering<p>
 * @author Anna Bruce
 * @version 1.0
 */


public class AssignmentInfoDialog extends JDialog{
    private int assignmentNo;
    private VirtualProductionLine vpl;
    private JButton createAssignmentButton;
    private JButton cancelButton;
    private URL assURL;

  public AssignmentInfoDialog(VirtualProductionLine myvpl, String title, String assignmentURL, int assNo) {
        super(myvpl);
        vpl = myvpl;
        assignmentNo = assNo;
        setModal(true);
        getContentPane().setLayout(new BorderLayout());
        setSize(500,500);
        setTitle(title);
	JEditorPane pane = new JEditorPane();
        JScrollPane htmlView = new JScrollPane(pane);
        htmlView.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        String s = null;
        try {
            s = "file:"
                + System.getProperty("user.dir")
                + System.getProperty("file.separator")
                + "help"
                + System.getProperty("file.separator")
                + assignmentURL;
            assURL = new URL(s);
	    pane.setPage(assURL);
        } catch (Exception e) {
            System.err.println("Couldn't create help URL: " + s);
        }
        getContentPane().add(htmlView, BorderLayout.CENTER);
        createAssignmentButton = new JButton("Continue");
        cancelButton = new JButton("Cancel");
	SymAction lSymAction = new SymAction();
	createAssignmentButton.addActionListener(lSymAction);
	cancelButton.addActionListener(lSymAction);
        JPanel littlePanel = new JPanel();
        littlePanel.add(createAssignmentButton);
        littlePanel.add(cancelButton);
	getContentPane().add(littlePanel, BorderLayout.SOUTH);
    }

	public void setVisible(boolean b)
	{
	    if (b)
	    {
    		Rectangle bounds = (getParent()).getBounds();
    		Dimension size = getSize();
    		setLocation(bounds.x + (bounds.width - size.width)/2,
    			        bounds.y + (bounds.height - size.height)/2);
	    }

		super.setVisible(b);
	}

	class SymAction implements java.awt.event.ActionListener
	{
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();
			if (object == createAssignmentButton)
				createAssignmentButton_actionPerformed(event);
			if (object == cancelButton)
				cancelButton_actionPerformed(event);
		}
	}

	void createAssignmentButton_actionPerformed(java.awt.event.ActionEvent event)
	{
	    try {
		this.setVisible(false);
	    } catch (Exception e) {
	    }
            vpl.newAssignment(assignmentNo);
        }

	void cancelButton_actionPerformed(java.awt.event.ActionEvent event)
	{
	    try {
		this.setVisible(false);
	    } catch (Exception e) {
	    }
        }
}
