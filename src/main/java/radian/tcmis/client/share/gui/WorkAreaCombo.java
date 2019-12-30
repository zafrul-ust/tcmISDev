
//Title:        TCM - CMIS
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian International
//Description:  TCM - CMIS - Gui components


package radian.tcmis.client.share.gui;

import java.awt.event.*;

import javax.swing.*;

public class WorkAreaCombo extends JComboBox {
  FacilityCombo fc;
  boolean loading = false;

  public WorkAreaCombo(FacilityCombo fc) {
    super();
    this.fc = fc;
    fc.setWorkAreaCombo(this);
    addActionListener(new WorkAreaComboActionListener(this));
  }
  public void setLoading(boolean b){
    loading = b;
  }
  public void loadIt() {
    fc.loadIt();
  }
  public String getSelectedWorkAreaID() {
    return fc.getSelectedWorkAreaID();
  }
  public String getSelectedFacId() {
    return fc.getSelectedFacId();
  }
  void selectionChanged(){
    if(!loading)firePropertyChange("selection changed",0,1);
  }
  public boolean isLoading(){
    return loading;
  }
}
class WorkAreaComboActionListener implements ActionListener {
  WorkAreaCombo wc;
  public WorkAreaComboActionListener(WorkAreaCombo wc) {
    this.wc = wc;
  }
   public void actionPerformed(ActionEvent e){
     wc.selectionChanged();
   }
}
