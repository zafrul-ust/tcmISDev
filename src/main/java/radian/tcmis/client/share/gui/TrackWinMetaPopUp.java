package radian.tcmis.client.share.gui;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.Component;
import javax.swing.*;


public class TrackWinMetaPopUp extends JPopupMenu {
  CmisApp cmis;
  TrackWin trackWin;
  boolean showSchedule = false;
  boolean showStatusDetail = false;

  public TrackWinMetaPopUp(CmisApp cmis, TrackWin tw, boolean showSchedule, boolean showStatusDetail) {
    super();
    this.cmis = cmis;
    this.trackWin = tw;
    this.showSchedule = showSchedule;
    this.showStatusDetail = showStatusDetail;
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void jbInit() throws Exception{
    JMenuItem mi;
    TrackWinMetaPopUpActionListener mal = new TrackWinMetaPopUpActionListener(this);

    mi = new JMenuItem("Material Request");
    mi.setActionCommand("Material Request");
    mi.setFont(new java.awt.Font("Dialog", 0, 10));
    add(mi);
    mi.addActionListener(mal);

    if (showStatusDetail) {
      mi = new JMenuItem("MR Allocation");
      mi.setActionCommand("MR Allocation");
      mi.setFont(new java.awt.Font("Dialog", 0, 10));
      add(mi);
      mi.addActionListener(mal);
    }

    if (showStatusDetail) {
      mi = new JMenuItem("MR Line Allocation");
      mi.setActionCommand("MR Line Allocation");
      mi.setFont(new java.awt.Font("Dialog", 0, 10));
      add(mi);
      mi.addActionListener(mal);
    }
    /*
    if (showStatusDetail) {
      mi = new JMenuItem("MR Line Status");
      mi.setActionCommand("MR Line Status");
      mi.setFont(new java.awt.Font("Dialog", 0, 10));
      add(mi);
      mi.addActionListener(mal);
    }
    */
    if (showSchedule) {
      mi = new JMenuItem("MR Line Schedule");
      mi.setActionCommand("MR Line Schedule");
      mi.setFont(new java.awt.Font("Dialog", 0, 10));
      add(mi);
      mi.addActionListener(mal);
    }
  }

  void goRequestStatus() {
    trackWin.mrAllocation = true;
    trackWin.showRequestStatus();
  }

  void goRequestLineStatus() {
    trackWin.mrAllocation = false;
    trackWin.showRequestStatus();
  }


  void goSchedule() {
    trackWin.showScheduleDelivery();
  }

  void goMaterialRequest() {
    trackWin.goMaterialRequestDetail();
  }

  void goStatusDetail() {
    trackWin.createDetailWindow();
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
  } //end of method
} //end of class


class TrackWinMetaPopUpActionListener implements ActionListener {
  TrackWinMetaPopUp you;
  public TrackWinMetaPopUpActionListener(TrackWinMetaPopUp you){
    this.you = you;
  }
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equalsIgnoreCase("MR Line Status")) {
      you.goStatusDetail();
    }else if (e.getActionCommand().equalsIgnoreCase("Material Request")) {
      you.goMaterialRequest();
    }else if (e.getActionCommand().equalsIgnoreCase("MR Line Schedule")) {
      you.goSchedule();
    }else if (e.getActionCommand().equalsIgnoreCase("MR Allocation")) {
      you.goRequestStatus();
    }else if (e.getActionCommand().equalsIgnoreCase("MR Line Allocation")) {
      you.goRequestLineStatus();
    }

  }
}