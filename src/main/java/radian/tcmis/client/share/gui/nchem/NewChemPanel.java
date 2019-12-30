
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Rodrigo Zerlotti
//Company:      Radian
//Description:  Your description

package radian.tcmis.client.share.gui.nchem;

import java.awt.*;
import javax.swing.*;


public abstract class  NewChemPanel extends JPanel {
  BorderLayout borderLayout1 = new BorderLayout();

  public NewChemPanel() {
    try  {
      jbInit();
      // 589 x 407
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
  }

  public abstract void setPane();

}