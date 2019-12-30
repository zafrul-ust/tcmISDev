package radian.tcmis.client.share.gui;

import java.awt.*;
import java.util.*;

import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.helpers.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;


public class MSDSRevReport extends ReportInputPanel {
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  StaticJLabel jLabel1 = new StaticJLabel();
  JComboBox facilityC = new JComboBox();
  ButtonGroup bg = new ButtonGroup();
  ButtonGroup bg1 = new ButtonGroup();
  ButtonGroup bg2 = new ButtonGroup();

  boolean firstTime = true;
  Hashtable dataH;

  public void showReport() {
    ReportOptionDlg rod = new ReportOptionDlg(cmis.getMain());
    rod.setCmisApp(cmis);
    rod.setFacId(getFacilityID());
    rod.setScreen("MSDS");
    rod.loadIt();
  }

  public void openTemplate() {
  }
  public void saveTemplate() {
  }
  public void clearTemplate() {
  }

  public String getFacilityDesc(String facID) {
    Vector facIDV = (Vector) dataH.get("FACILITY_ID");
    Vector facNameV = (Vector) dataH.get("FACILITY_NAME");
    int index = facIDV.indexOf(facID);
    if (index < 0) {
      index = 0;
    }
    return ((String)facNameV.elementAt(index));
  }

  public String getFacilityID() {
    Vector facIDV = (Vector) dataH.get("FACILITY_ID");
    return ((String)facIDV.elementAt(facilityC.getSelectedIndex()));
  }

  public void loadIt(){
    MSDSRevisionReportThread aw = new MSDSRevisionReportThread(this);
    aw.start();
  } //end of method

  void loadItAction() {
    TcmisHttpConnection cgi = new TcmisHttpConnection("ReportObj",cmis);
    Hashtable query = new Hashtable();
    query.put("ACTION","GET_MSDS_REVISION_INITAL_DATA"); //String, String
    query.put("USERID",(new Integer(cmis.getUserId().intValue())).toString()); //String, String
    Hashtable result = cgi.getResultHash(query);
    if (result == null){
     String no = new String("Access denied. Your session is corrupted, please restart the application.");
     GenericDlg err = new GenericDlg(cmis.getMain(),"Denied",true);
     err.setMsg(no);
     err.setVisible(true);
     return;
    }
    dataH = (Hashtable) result.get("DATA");
    Vector facNameV = (Vector) dataH.get("FACILITY_NAME");
    facilityC.removeAllItems();
    facilityC = ClientHelpObjs.loadChoiceFromVector(facilityC,facNameV);
    facilityC.setSelectedItem(getFacilityDesc(cmis.getPrefFac()));
  } //end of method


  public void loadScreen() {
    if (firstTime) {
      try  {
        jbInit();
        loadIt();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      firstTime = false;
    }
  }


  public MSDSRevReport(CmisApp cmis) {
    super(cmis,"Share","Formatted MSDS Revision");
  }

  private void jbInit() throws Exception {
    jLabel1.setText("Facility:");
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
    this.setLayout(gridBagLayout1);
    facilityC.setMaximumSize(new Dimension(220, 24));
    facilityC.setMinimumSize(new Dimension(220, 24));
    facilityC.setPreferredSize(new Dimension(220, 24));
    this.add(jLabel1, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    this.add(facilityC, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));

    this.validate();

    ClientHelpObjs.makeDefaultColors(this);
  }
  public void setFacCombo(FacilityCombo fc){
  }
}

class MSDSRevisionReportThread extends Thread {
 MSDSRevReport parent;
  public MSDSRevisionReportThread(MSDSRevReport parent){
     super("MSDSRevisionReportThread");
     this.parent = parent;
  }

  public void run(){
     parent.loadItAction();
  }
}

