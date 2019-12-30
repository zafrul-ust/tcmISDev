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
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import com.borland.jbcl.layout.*;

import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class AddRoleDlg extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JPanel bevelPanel1 = new JPanel();
  JButton okB = new JButton();
  JButton cancelB = new JButton();
  JPanel jPanel1 = new JPanel();
  StaticJLabel roleL = new StaticJLabel();
  JTextField roleT = new JTextField();
  CmisApp cmis;
  boolean updating = false;

  String facId;
  boolean canceled = false;
  JComboBox typeC = new JComboBox();
  StaticJLabel jLabel1 = new StaticJLabel();
  XYLayout xYLayout2 = new XYLayout();

  public AddRoleDlg(Frame frame, String title, boolean modal,String facId) {
    super(frame, title, modal);
    this.facId = facId;
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
  public void setVisible(boolean b){
    if(b){
      if(updating) roleT.setEnabled(false);
      CenterComponent.centerCompOnScreen(this);
    }
    super.setVisible(b);
  }

  private void jbInit() throws Exception {
    setResizable(false);
    panel1.setPreferredSize(new Dimension(374, 190));
    xYLayout1.setWidth(374);
    xYLayout1.setHeight(190);
    bevelPanel1.setLayout(xYLayout2);
    okB.setText("OK");
    okB.addActionListener(new AddRoleDlg_okB_actionAdapter(this));
    cancelB.setText("Cancel");
    roleL.setText("Role:");
    loadTypeCombo();
    roleL.setHorizontalAlignment(4);
    cancelB.addActionListener(new AddRoleDlg_cancelB_actionAdapter(this));
    this.addWindowListener(new AddRoleDlg_this_windowAdapter(this));
    panel1.setLayout(xYLayout1);
    panel1.add(bevelPanel1, new XYConstraints(10, 10, 354, 115));
    bevelPanel1.add(roleL, new XYConstraints(0, 25, 81, -1));
    //trong 3/27
    bevelPanel1.add(roleT, new XYConstraints(92, 22, 242, -1));
    bevelPanel1.add(typeC, new XYConstraints(92, 58, 242, -1)); //bevelPanel1.add(typeC, new XYConstraints(92, 58, 200, -1));
    bevelPanel1.add(jLabel1, new XYConstraints(0, 65, 81, -1));
    panel1.add(jPanel1, new XYConstraints(10, 135, 354, 45));
    jPanel1.add(okB, null);
    jPanel1.add(cancelB, null);

    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR");
    */
    ImageIcon ss = null;
    ss = ResourceLoader.getImageIcon("images/button/ok.gif");
    okB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/cancel.gif");
    jLabel1.setText("Approval Type:");
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
    panel1.setMinimumSize(new Dimension(353, 281));
    cancelB.setIcon(ss);
    ClientHelpObjs.makeDefaultColors(this);
    validate();
  }
  void loadTypeCombo(){
  /* original trong 3/27
    typeC.addItem("Standard Approval");
    typeC.addItem("approval type 1");
    typeC.addItem("approval type 2");
    typeC.addItem("approval type 3"); */
    typeC.addItem("New Chem; No change");
    typeC.addItem("New Eng Eval; No change");
    typeC.addItem("New Chem, Eng Eval; No change");
    typeC.addItem("New Chem; Change sect 2");
    typeC.addItem("New Chem; Change sect 3");
    typeC.addItem("New Chem; Change sect 2,3");
    typeC.addItem("Eng Eval; Change sect 2");
    typeC.addItem("Eng Eval; Change sect 3");
    typeC.addItem("Eng Eval; Change sect 2,3");
    typeC.addItem("New Chem, Eng Eval; Change sect 2");
    typeC.addItem("New Chem, Eng Eval; Change sect 3");
    typeC.addItem("New Chem, Eng Eval; Change sect 2,3");
    typeC.setSelectedIndex(0);
  }

  public boolean isCanceled(){
    return canceled;
  }
  public void setCmis(CmisApp cmis) {
    this.cmis = cmis;
  }
  public void setUpdating(boolean b){
    updating = b;
  }
  public void setRole(String s){
    roleT.setText(s);
  }
  // OK
  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    addRole();
    setVisible(false);
  }

  // Cancel
  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    canceled = true;
    setVisible(false);
  }

  public boolean getCanceled(){
    return canceled;
  }

  void this_windowClosing(WindowEvent e) {
  }

  void addRole() {
    if(BothHelpObjs.isBlankString(roleT.getText())){
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Please Enter a Role",true);
      gd.setMsg("You must enter a Role.");
      gd.setVisible(true);
      return;
    }
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.ADMIN,cmis);
    Hashtable query = new Hashtable();

    if(updating){
      query.put("ACTION","UPDATE_ROLE"); //String, String
    }else{
      query.put("ACTION","ADD_ROLE"); //String, String
    }

    query.put("USERID",(new Integer(cmis.getUserId().intValue())).toString()); //String, String
    query.put("FACILITY_ID",facId);
    query.put("ROLE",roleT.getText());

    //approval type
    String t = "";
    int x = typeC.getSelectedIndex() + 1;  //trong add 1 to selected index
    /* before trong change 3/27
    if(x>0){
      t = new Integer(x).toString();
    }                                  */
    t = new Integer(x).toString();   //trong 3/27



    query.put("APPROVAL_TYPE",t);
  System.out.println("where are you at: " + query);

    Hashtable result = cgi.getResultHash(query);
    if (result == null){
         String no = new String("Access denied. Your session is corrupted, please restart the application.");
         GenericDlg err = new GenericDlg(cmis.getMain(),"Denied",true);
         err.setMsg(no);
         err.setVisible(true);
         return;
    }
    Vector v = (Vector) result.get("DATA");

    GenericDlg gd = new GenericDlg(cmis.getMain(),true);
    String title = "";
    String msg = "";

    if(v == null || v.isEmpty() || !v.elementAt(0).toString().equalsIgnoreCase("true")){
      gd.setTitle("Role Add Failed");
      gd.setMsg("The new Role was NOT added.");
    }else{
      gd.setTitle("Role Added");
      gd.setMsg("The new Role was added.");
    }
    gd.setVisible(true);
    return;
  }
}

class AddRoleDlg_okB_actionAdapter implements ActionListener{
  AddRoleDlg adaptee;

  AddRoleDlg_okB_actionAdapter(AddRoleDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okB_actionPerformed(e);
  }
}

class AddRoleDlg_cancelB_actionAdapter implements ActionListener{
  AddRoleDlg adaptee;

  AddRoleDlg_cancelB_actionAdapter(AddRoleDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelB_actionPerformed(e);
  }
}

class AddRoleDlg_this_windowAdapter extends WindowAdapter {
  AddRoleDlg adaptee;

  AddRoleDlg_this_windowAdapter(AddRoleDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}

