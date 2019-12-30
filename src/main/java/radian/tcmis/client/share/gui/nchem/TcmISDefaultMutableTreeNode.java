
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Rodrigo Zerlotti
//Company:      Radian
//Description:  Your description

package radian.tcmis.client.share.gui.nchem;

import javax.swing.tree.*;


public class TcmISDefaultMutableTreeNode extends DefaultMutableTreeNode {
  String ID;
  public TcmISDefaultMutableTreeNode(String o, String ID) {
    super(o);
    this.ID = ID;
  }
  public String getID() {
    return ID;
  }
}