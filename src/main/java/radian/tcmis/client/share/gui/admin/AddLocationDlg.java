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

public class AddLocationDlg extends JDialog {
  JPanel panel1 = new JPanel();
  JPanel bevelPanel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JButton okB = new JButton();
  JButton cancelB = new JButton();
  JPanel jPanel1 = new JPanel();
  CmisApp cmis;
  boolean canceled;

  StaticJLabel locIdL = new StaticJLabel();
  JTextField locIdT = new JTextField();
  XYLayout xYLayout2 = new XYLayout();

  public AddLocationDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
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
  public void setCmisApp(CmisApp cmis){
    this.cmis = cmis;
  }
  public void setVisible(boolean b){
    super.setVisible(b);
    if(b) locIdT.grabFocus();
  }

  private void jbInit() throws Exception {
    setResizable(false);
    panel1.setSize(new Dimension(422, 152));
    xYLayout1.setWidth(422);
    xYLayout1.setHeight(152);
    bevelPanel1.setLayout(xYLayout2);
    okB.setText("OK");
    okB.addActionListener(new AddLocationDlg_okB_actionAdapter(this));
    cancelB.setText("Cancel");
    locIdL.setText("Address ID:");
    locIdL.setHorizontalAlignment(4);
    locIdT.requestFocus();
    locIdT.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        someKeyPressed(e);
      }
    });
    cancelB.addActionListener(new AddLocationDlg_cancelB_actionAdapter(this));
    this.addWindowListener(new AddLocationDlg_this_windowAdapter(this));
    panel1.setLayout(xYLayout1);
    panel1.add(bevelPanel1, new XYConstraints(10, 10, 400, 88));

    bevelPanel1.add(locIdT, new XYConstraints(105, 34, 263, 22));
    bevelPanel1.add(locIdL, new XYConstraints(31, 25, 72, 43));
    panel1.add(jPanel1, new XYConstraints(10, 104, 400, 40));
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
    ClientHelpObjs.makeDefaultColors(this);
  }

  public boolean submitNewLoc(){
    if(BothHelpObjs.isBlankString(locIdT.getText())){
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Add New Address",true);
      gd.setMsg("You must enter an Address ID.");
      gd.setVisible(true);
      return false;
    }
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    CursorChange.setCursor(this.getContentPane(),Cursor.WAIT_CURSOR);
    ClientHelpObjs.setEnabledContainer(bevelPanel1,false);
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.ADMIN,cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","ADD_LOCATION"); //String, String
      query.put("USERID",(new Integer(cmis.getUserId().intValue())).toString()); //String, String
      query.put("LOCATION_ID",locIdT.getText()); //String, String
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
        CursorChange.setCursor(this.getContentPane(),Cursor.DEFAULT_CURSOR);
        return false;
      }
      boolean returnVal = false;
      String msg = "";
      Vector v = (Vector)result.get("DATA");
      String s = v.elementAt(0).toString();
      if(BothHelpObjs.isBlankString(s)){
        msg = "The new Address ID was NOT added.";
      }else if(s.equalsIgnoreCase("true")){
        msg = "The new Address ID was added. Please enter the address information.";
        returnVal = true;
      }else if(s.equalsIgnoreCase("dup")){
        msg = "The Address ID you entered is already in use. Please enter another Address ID.";
      }else{
        msg = "The new Address ID was NOT added.";
      }
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Add New Address",true);
      gd.setMsg(msg);
      gd.setVisible(true);
      CursorChange.setCursor(this.getContentPane(),Cursor.DEFAULT_CURSOR);
      CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
      return returnVal;
    }catch(Exception e){e.printStackTrace();return false;}
  }

  public boolean getCanceled(){
    return canceled;
  }
  public String getNewLocId(){
    return locIdT.getText();
  }

  // OK
  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if(submitNewLoc()) {
      setVisible(false);
    }else{
      ClientHelpObjs.setEnabledContainer(this,true);
    }
  }

  // Cancel
  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    canceled = true;
    locIdT.setText("");
    setVisible(false);
  }

  void this_windowClosing(WindowEvent e) {
    canceled = true;
    setVisible(false);
  }

  void someKeyPressed(KeyEvent e) {
     if (e.getKeyCode() == KeyEvent.VK_ENTER) {
       if(submitNewLoc()) {
         setVisible(false);
       }
     }
  }
}

class AddLocationDlg_okB_actionAdapter implements ActionListener{
  AddLocationDlg adaptee;

  AddLocationDlg_okB_actionAdapter(AddLocationDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okB_actionPerformed(e);
  }
}

class AddLocationDlg_cancelB_actionAdapter implements ActionListener{
  AddLocationDlg adaptee;

  AddLocationDlg_cancelB_actionAdapter(AddLocationDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelB_actionPerformed(e);
  }
}

class AddLocationDlg_this_windowAdapter extends WindowAdapter {
  AddLocationDlg adaptee;

  AddLocationDlg_this_windowAdapter(AddLocationDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}
