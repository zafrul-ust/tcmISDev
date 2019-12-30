
//Title:        TCM - CMIS
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian International
//Description:  TCM - CMIS - Gui components


package radian.tcmis.client.share.gui;
import javax.swing.*;
public abstract class ReportInputPanel extends JPanel {
  protected CmisApp cmis;
  String reportOwner;
  String reportTitle;

  public ReportInputPanel(CmisApp cmis,String reportOwner,String reportTitle) {
    this.cmis = cmis;
    this.reportOwner = reportOwner;
    this.reportTitle = reportTitle;
  }
  public String getOwner() {
    return reportOwner;
  }
  public String getTitle() {
    return reportTitle;
  }
  public void setReportTitle(String s){
    reportTitle = s;
  }
  protected boolean checkDates(String begMon, String begYear, String endMon, String endYear) {
    if(begMon.length() == 1) begMon = "0" + begMon;
    if(endMon.length() == 1) endMon = "0" + endMon;
    if(begMon.length() != 2 || endMon.length() != 2 || begYear.length() != 4 || endYear.length() != 4) return false;
    String beg = begYear + begMon;
    String end = endYear + endMon;
    try{
      int b = Integer.parseInt(beg);
      int e = Integer.parseInt(end);
      return e >= b;
    }catch(Exception e){e.printStackTrace();}
    return false;
  }

  protected void showBadDatesDlg(){
    String no = new String("The end date cannot be before the begin date.");
    GenericDlg err = new GenericDlg(cmis.getMain(),"Date Error",true);
    err.setMsg(no);
    err.setVisible(true);
  }

  // sub class should override if it uses a facility or workarea combo
  public void setFacCombo(FacilityCombo fc){
  }

  public abstract void showReport();

  public abstract void openTemplate();
  public abstract void saveTemplate();
  public abstract void clearTemplate();

  public abstract void loadScreen();
}
