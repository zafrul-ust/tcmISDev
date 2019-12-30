// This snippet creates a new dialog box
// with buttons on the bottom.

//Title:
//Version:
//Copyright:
//Author:
//Company:
//Description:

package radian.tcmis.client.share.gui.admin;

import java.awt.*;
import java.awt.event.*;

import java.util.*;
import javax.swing.*;

import radian.tcmis.client.share.gui.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class AddWorkAreaDlg extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JPanel bevelPanel1 = new JPanel();
  JButton okB = new JButton();
  JButton cancelB = new JButton();
  JPanel jPanel1 = new JPanel();
  StaticJLabel workAreaL = new StaticJLabel();
  StaticJLabel descL = new StaticJLabel();
  JTextField waT = new JTextField();
  JTextField waDescT = new JTextField();
  CmisApp cmis;

  boolean recAdded = false;
  boolean loading = true;
  boolean canceled = false;
  String facId = "";

  String initFacName = "";
  String initFacId = "";
  StaticJLabel facL = new StaticJLabel();
  StaticJLabel locL = new StaticJLabel();
  JComboBox locC = new JComboBox();
  Vector locs;
  XYLayout xYLayout2 = new XYLayout();
  DataJLabel jLabel1 = new DataJLabel();

  // load from resource bundle
  String WORK_AREA = "Work Area";
  String WORK_AREAS = "Work Areas";


  public AddWorkAreaDlg(Frame frame, String title, boolean modal,String facId, String facName, Vector locations,CmisApp cmis) {
    super(frame, title, modal);
    this.cmis = cmis;
    WORK_AREA = cmis.getResourceBundle().getString("WORK_AREA_NAME");
    WORK_AREAS = cmis.getResourceBundle().getString("WORK_AREA_NAME_PLURAL");
    this.locs = locations;
    this.facId = facId;
    jLabel1.setText(facName);
    locC = ClientHelpObjs.loadChoiceFromVector(locC,locs);
    locC = ClientHelpObjs.choiceSort(locC);
    locC.setSelectedIndex(0);
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    getContentPane().add(panel1);
    pack();
     // Colors
    ClientHelpObjs.makeDefaultColors(this);
    validate();
    repaint();
  }

  private void jbInit() throws Exception {
    setResizable(false);
    panel1.setMaximumSize(new Dimension(350, 275));
    panel1.setPreferredSize(new Dimension(350, 275));
    panel1.setMinimumSize(new Dimension(350, 275));
    xYLayout1.setWidth(350);
    xYLayout1.setHeight(275);
    bevelPanel1.setLayout(xYLayout2);
    okB.setText("OK");
    cancelB.setText("Cancel");
    workAreaL.setText(WORK_AREA +" ID:");
    workAreaL.setHorizontalAlignment(4);
    facL.setText("Facility:");
    facL.setHorizontalAlignment(4);
    locL.setHorizontalAlignment(4);
    locL.setText("Location:");
    descL.setText("Description:");
    descL.setHorizontalAlignment(4);
    this.addWindowListener(new AddWorkAreaDlg_this_windowAdapter(this));
    panel1.setLayout(xYLayout1);
    panel1.add(bevelPanel1, new XYConstraints(10, 10, 330, 200));
    bevelPanel1.add(workAreaL, new XYConstraints(6, 71, 89, 24));
    bevelPanel1.add(descL, new XYConstraints(32, 100, 63, 33));
    bevelPanel1.add(waT, new XYConstraints(98, 70, 64, 27));
    bevelPanel1.add(waDescT, new XYConstraints(98, 103, 169, 27));
    bevelPanel1.add(facL, new XYConstraints(32, 38, 62, 24));
    bevelPanel1.add(locL, new XYConstraints(32, 133, 63, 33));
    bevelPanel1.add(locC, new XYConstraints(98, 136, 169, -1));
    bevelPanel1.add(jLabel1, new XYConstraints(99, 39, 206, 22));
    panel1.add(jPanel1, new XYConstraints(10, 220, 330, 45));
    jPanel1.add(okB, null);
    jPanel1.add(cancelB, null);

    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR");
    */
    ImageIcon ss = null;
    ss = ResourceLoader.getImageIcon("images/button/ok.gif");
    okB.setIcon(ss);
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });
    ss = ResourceLoader.getImageIcon("images/button/cancel.gif");
    cancelB.setIcon(ss);
    cancelB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });

    //ss = ResourceLoader.getImageIcon("images/button/submit.gif");

    ss = ResourceLoader.getImageIcon("images/button/submit.gif");
    bevelPanel1.setMaximumSize(new Dimension(305, 166));
    ClientHelpObjs.makeDefaultColors(this);
  }


  public boolean auditField() {
    if(waT.getText().length() < 1) {
      auditFailed(WORK_AREA + " ID can not be blank");
      return false;
    }
    if(waDescT.getText().length() < 1) {
      auditFailed(WORK_AREA + " Description can not be blank");
      return false;
    }
    return true;
  }

  public boolean submitChange(){
    if(!auditField()){
      return false;
    }
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.ADMIN,cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","ADD_WORK_AREA");
      query.put("USERID",(new Integer(cmis.getUserId().intValue())).toString()); //String, String
      query.put("WORK_AREA_ID",waT.getText());
      query.put("WORK_AREA_DESC",waDescT.getText());
      query.put("LOCATION_ID",locC.getSelectedItem().toString());
      query.put("FACILITY_ID",facId);

      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return false;
      }

      Vector info = (Vector) result.get("DATA");
      String yesNo = (String)info.elementAt(0);
      GenericDlg gd = new GenericDlg(cmis.getMain(),true);
      if(yesNo.equalsIgnoreCase("true")) {
        gd.setMsg("The "+WORK_AREA + " was added.");
          recAdded = true;
        gd.show();
        return true;
      }else{
        gd.setMsg("Error on change. No changes were made.");
        gd.show();
        return false;
      }
    } catch (Exception ie) {
      //cmis.getMain().stopImage();
      ie.printStackTrace();
      return false;
    }
  }

  void auditFailed(String s) {
    GenericDlg gd1 = new GenericDlg(cmis.getMain(),true);
    gd1.setMsg(s);
    gd1.show();
  }
  public String getWorkAreaId(){
    return waT.getText();
  }
  public String getWorkAreaDesc(){
    return waDescT.getText();
  }
  public String getFacId(){
    return facId;
  }
  public String getLocId(){
    return locC.getSelectedItem().toString();
  }

  public boolean getRecAdded() {
    return recAdded;
  }
  public boolean wasCanceled(){
    return canceled;
  }

  // OK
  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if(submitChange()) {
      setVisible(false);
    }
  }

  // Cancel
  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    canceled = true;
    setVisible(false);
  }

  void this_windowClosing(WindowEvent e) {
    canceled = true;
    setVisible(false);
  }

}

class AddWorkAreaDlg_this_windowAdapter extends WindowAdapter {
  AddWorkAreaDlg adaptee;

  AddWorkAreaDlg_this_windowAdapter(AddWorkAreaDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}

class AddWorkAreaDlg_okB_actionAdapter implements ActionListener{
  AddWorkAreaDlg adaptee;

  AddWorkAreaDlg_okB_actionAdapter(AddWorkAreaDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okB_actionPerformed(e);
  }
}

class AddWorkAreaDlg_cancelB_actionAdapter implements ActionListener{
  AddWorkAreaDlg adaptee;

  AddWorkAreaDlg_cancelB_actionAdapter(AddWorkAreaDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelB_actionPerformed(e);
  }
}
