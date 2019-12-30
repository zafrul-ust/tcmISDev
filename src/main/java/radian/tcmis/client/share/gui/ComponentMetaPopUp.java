package radian.tcmis.client.share.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.Component;
//import com.borland.dx.dataset.*;
//import com.borland.jbcl.view.*;

import javax.swing.*;
import radian.tcmis.client.share.helpers.*;


public class ComponentMetaPopUp extends JPopupMenu {
  CmisApp cmis;
  String matID;
  String specID= "";
  String facID;
  Integer itemID;
  String onLine;


  public ComponentMetaPopUp(CmisApp cmis,String matID, String itemID, String facID, String onLine) {
    super();
    this.cmis = cmis;
    this.matID = matID;
    this.facID = facID;
    this.itemID = new Integer(itemID);
    this.onLine = onLine;
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void jbInit() throws Exception{
    JMenuItem mi;
    CompMetaActionListener mal = new CompMetaActionListener(this);

    mi = new JMenuItem("View MSDS");
    mi.setActionCommand("MSDS");
    mi.setFont(new java.awt.Font("Dialog", 0, 10));
    add(mi);
    mi.addActionListener(mal);
    if (!"Y".equalsIgnoreCase(onLine)) {
      mi.setEnabled(false);
    }
  }

  void goUrl(ActionEvent e) {
    int kind = 0;

    if (e.getActionCommand().toString().startsWith("MSDS")) {
      System.out.println("View MSDS clicked:");
      kind = URLGrab.MSDS;
    }
    System.out.println("--- what going on");
    ClientHelpObjs.goURL(cmis,matID,specID,this.cmis.getResourceBundle().getString("APP_NAME"),facID,itemID.toString(),kind,false);
  }

  public void show(Component comp, int x, int y) {
    // make sure the entire menu is showing
    // this is a real kludge
    int offset = 185;
    int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;
    if(comp.getLocationOnScreen().y + y > screenH - offset) {
      y = screenH - offset - comp.getLocationOnScreen().y;
    }
    super.show(comp,x,y);
  }
}


class CompMetaActionListener implements ActionListener {
  ComponentMetaPopUp you;
  public CompMetaActionListener(ComponentMetaPopUp you){
    this.you = you;
  }
  public void actionPerformed(ActionEvent e) {
    you.goUrl(e);
  }
}