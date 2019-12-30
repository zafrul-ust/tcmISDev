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

public class AddFacilityDlg extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JPanel bevelPanel1 = new JPanel();
  JButton okB = new JButton();
  JButton cancelB = new JButton();
  JPanel jPanel1 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  CmisApp cmis;
  String facType = "";

  // fac types
  public static final String NORMAL = "";
  public static final String DROP_FAC = "drop";

  boolean wasCanceled = false;
  boolean recAdded = false;
  boolean updating = false;
  boolean locsOnly = false;
  boolean loading = true;

  StaticJLabel facL = new StaticJLabel();
  JTextField facT = new JTextField();
  StaticJLabel facNameL = new StaticJLabel();
  JTextField facNameT = new JTextField();
  XYLayout xYLayout2 = new XYLayout();

  public AddFacilityDlg(Frame frame, String title, boolean modal, CmisApp cmis) {
    super(frame, title, modal);
    this.cmis = cmis;
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
    panel1.setMaximumSize(new Dimension(360, 230));
    panel1.setPreferredSize(new Dimension(360, 230));
    panel1.setMinimumSize(new Dimension(360, 230));
    xYLayout1.setWidth(360);
    xYLayout1.setHeight(230);
    bevelPanel1.setLayout(xYLayout2);
    okB.setText("OK");
    okB.addActionListener(new AddFacilityDlg_okB_actionAdapter(this));
    cancelB.setText("Cancel");
    facL.setText("Facility ID:");
    facL.setHorizontalAlignment(4);
    facNameL.setText("Facility Name:");
    facNameL.setHorizontalAlignment(4);
    cancelB.addActionListener(new AddFacilityDlg_cancelB_actionAdapter(this));
    this.addWindowListener(new AddFacilityDlg_this_windowAdapter(this));
    panel1.setLayout(xYLayout1);
    bevelPanel1.setMaximumSize(new Dimension(360, 230));
    panel1.add(bevelPanel1, new XYConstraints(10, 10, 340, 150));
    bevelPanel1.add(facT, new XYConstraints(104, 37, 164, 27));
    bevelPanel1.add(facNameT, new XYConstraints(104, 70, 164, 27));



    bevelPanel1.add(facL, new XYConstraints(0, 34, 101, 33));
    bevelPanel1.add(facNameL, new XYConstraints(1, 67, 97, 33));
    panel1.add(jPanel1, new XYConstraints(10, 170, 340, 50));
    jPanel1.add(okB, null);
    jPanel1.add(cancelB, null);

    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR");
    */
    ImageIcon ss = null;
    ss = ResourceLoader.getImageIcon("images/button/ok.gif");
    okB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/cancel.gif");
    cancelB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/submit.gif","Search");
    bevelPanel1.setPreferredSize(new Dimension(360, 230));
    bevelPanel1.setMinimumSize(new Dimension(360, 230));
    ClientHelpObjs.makeDefaultColors(this);
  }

  public boolean wasCanceled(){
    return wasCanceled;
  }
  public boolean auditFields() {
    String msg = "";
    if(facT.getText().length() < 1) {
      msg = "You must enter a Facility ID.";
    }else if(facNameT.getText().length() < 1){
      msg = "You must enter a Facility Name.";
    }
    if(msg.length() > 0) {
      failedAudit(msg);
      return false;
    }
    return true;
  }

  public boolean submitChange(){
    if(!auditFields()){
      return false;
    }
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.ADMIN,cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","ADD_FACILITY");
      query.put("USERID",(new Integer(cmis.getUserId().intValue())).toString()); //String, String
      query.put("FACILITY_ID",facT.getText());
      query.put("FACILITY_NAME", facNameT.getText());
      query.put("FAC_TYPE",facType);

      Hashtable result = cgi.getResultHash(query);
      if (result == null){
         String no = new String("Access denied. Your session is corrupted, please restart the application.");
         GenericDlg err = new GenericDlg(cmis.getMain(),"Denied",true);
         err.setMsg(no);
         err.setVisible(true);
         return false;
      }

      Vector info = (Vector) result.get("DATA");
      GenericDlg gd = new GenericDlg(cmis.getMain(),true);
      if(info.elementAt(0).toString().equalsIgnoreCase("true")){
        gd.setMsg("The facility was added.");
        gd.show();
        recAdded = true;
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
    return true;
  }
  public String getFacId(){
    return facT.getText();
  }
  public boolean getCanceled(){
    return wasCanceled;
  }
  public boolean recAdded() {
    return recAdded;
  }
  public void setFacType(String s){
    facType = s;
  }

  void failedAudit(String s) {
    GenericDlg gd1 = new GenericDlg(cmis.getMain(),true);
    gd1.setMsg(s);
    gd1.show();
  }

  // OK
  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if(submitChange()) {
      dispose();
    }
  }

  // Cancel
  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    wasCanceled = true;
    setVisible(false);
  }

  void this_windowClosing(WindowEvent e) {
    setVisible(false);
  }

}

class AddFacilityDlg_okB_actionAdapter implements ActionListener{
  AddFacilityDlg adaptee;

  AddFacilityDlg_okB_actionAdapter(AddFacilityDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okB_actionPerformed(e);
  }
}

class AddFacilityDlg_cancelB_actionAdapter implements ActionListener{
  AddFacilityDlg adaptee;

  AddFacilityDlg_cancelB_actionAdapter(AddFacilityDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelB_actionPerformed(e);
  }
}

class AddFacilityDlg_this_windowAdapter extends WindowAdapter {
  AddFacilityDlg adaptee;

  AddFacilityDlg_this_windowAdapter(AddFacilityDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}
