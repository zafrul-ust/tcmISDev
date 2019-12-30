
//Title:        TCM - CMIS
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian International
//Description:  TCM - CMIS - Gui components


package radian.tcmis.client.share.gui.admin;
import java.awt.event.*;
import javax.swing.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.client.share.gui.*;

public abstract class AdminInputPanel extends JPanel {
  protected AdminWin adminWin;
  protected JToggleButton aipButton;
  protected boolean oopsReload = false;

  protected AdminWinNew adminWinNew;
  public AdminInputPanel(AdminWinNew aw,String title) {
    this.adminWinNew = aw;
    aipButton = new JToggleButton(title);
    aipButton.setEnabled(false);
    aipButton.addActionListener(new AdminWinToggleButtonActionListener(this));
  }

  public AdminInputPanel(AdminWin aw,String title) {
    this.adminWin = aw;
    aipButton = new JToggleButton(title);
    aipButton.setEnabled(false);
    aipButton.addActionListener(new AdminWinToggleButtonActionListener(this));
  }
  void jbInit() throws Exception {
  }
  public String getTitle(){
    return aipButton.getText();
  }
  public JToggleButton getButton(){
    return aipButton;
  }
  public void selectButtonClicked(AdminInputPanel aip) {
    adminWin.showThisPanel(this);
  }
  protected CmisApp getCmisApp() {
    return adminWin.getCmisApp();
  }
  protected void enableButton(boolean b){
    aipButton.setEnabled(b);
    if(b)adminWin.buttonEnabled();
  }
  protected void startLoad(){
    AdminInputPanelLoadThread aiplt = new AdminInputPanelLoadThread(this);
    aiplt.start();
  }

  // subclasses should override if they use a facility Combo box
  public synchronized void setFacCombo(FacilityCombo fc){
  }
  public abstract void loadIt();
  public abstract void loadThisPanel();
}

class AdminWinToggleButtonActionListener implements ActionListener {
  AdminInputPanel aip;
  AdminWinToggleButtonActionListener(AdminInputPanel aip) {
    this.aip = aip;
  }
  public void actionPerformed(ActionEvent e){
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    aip.selectButtonClicked(aip);
  }
}
class AdminInputPanelLoadThread extends Thread {
  AdminInputPanel parent;

  public AdminInputPanelLoadThread(AdminInputPanel parent){
     super("AdminInputPanelLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadThisPanel();
  }
}

