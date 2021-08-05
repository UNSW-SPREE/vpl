package vpl;
import javax.swing.*;
import java.awt.event.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) Anna Bruce
 * Company:      UNSW PV Engineering
 * @author Anna Bruce
 * @version 1.0
 */

public class OverviewPanel extends JPanel implements ActionListener{
    JButton button;
    VirtualProductionLine vpl;

  public OverviewPanel(VirtualProductionLine vpl) {
      this.vpl = vpl;
      button = new JButton("Close Overview Video");
      button.addActionListener(this);
      add(button);
  }

  public void actionPerformed(ActionEvent e){
      Object object = e.getSource();
      if (object == button){
          vpl.currentVideo.playerBean.stopAndDeallocate();
          vpl.currentVideo.playerBean.close();
          vpl.finishProcess();
      }
  }
}