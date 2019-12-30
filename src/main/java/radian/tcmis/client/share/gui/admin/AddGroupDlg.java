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

public class AddGroupDlg extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JPanel bevelPanel1 = new JPanel();
  JButton okB = new JButton();
  JButton cancelB = new JButton();
  JPanel jPanel1 = new JPanel();
  StaticJLabel groupL = new StaticJLabel();
  StaticJLabel descL = new StaticJLabel();
  JTextField groupT = new JTextField();
  JTextField descT = new JTextField();
  CmisApp parent;

  boolean recAdded = false;
  boolean updating = false;
  boolean loading = true;
  XYLayout xYLayout2 = new XYLayout();

  public AddGroupDlg(Frame frame, String title, boolean modal) {
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

  public AddGroupDlg(Frame frame, String title) {
    this(frame, title, false);
  }

  public AddGroupDlg(Frame frame) {
    this(frame, "", false);
  }

  private void jbInit() throws Exception {
    setResizable(false);
    panel1.setPreferredSize(new Dimension(370, 230));
    xYLayout1.setWidth(371);
    xYLayout1.setHeight(230);
    bevelPanel1.setLayout(xYLayout2);
    okB.setText("OK");
    okB.addActionListener(new AddGroupDlg_okB_actionAdapter(this));
    cancelB.setText("Cancel");
    groupL.setText("Group:");
    groupL.setHorizontalAlignment(4);
    descL.setText("Description:");
    descL.setHorizontalAlignment(4);
    descT.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        descT_keyPressed(e);
      }
    });
    groupT.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        groupT_keyPressed(e);
      }
    });
    cancelB.addActionListener(new AddGroupDlg_cancelB_actionAdapter(this));
    this.addWindowListener(new AddGroupDlg_this_windowAdapter(this));
    panel1.setLayout(xYLayout1);
    panel1.add(bevelPanel1, new XYConstraints(10, 10, 350, 150));
    bevelPanel1.add(groupL, new XYConstraints(27, 41, 63, 24));
    bevelPanel1.add(descL, new XYConstraints(27, 80, 63, 29));
    bevelPanel1.add(groupT, new XYConstraints(99, 41, 224, 24));
    bevelPanel1.add(descT, new XYConstraints(99, 80, 224, 28));
    panel1.add(jPanel1, new XYConstraints(10, 170, 350, 45));

    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR");
    */
    ImageIcon ss = null;
    ss = ResourceLoader.getImageIcon("images/button/ok.gif");
    okB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/cancel.gif");
    cancelB.setIcon(ss);

    jPanel1.add(okB, null);
    jPanel1.add(cancelB, null);
    ClientHelpObjs.makeDefaultColors(this);
  }

  public void setParent(CmisApp parent) {
    this.parent = parent;
  }

  public void setGroupName(String s) {
    groupT.setText(s);
  }

  public void loadIt(){
    AddGroupThread agt = new AddGroupThread(this);
    agt.start();
  }


  public void loadScreen(){
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    CursorChange.setCursor(this.getContentPane(),Cursor.WAIT_CURSOR);
    ClientHelpObjs.setEnabledContainer(bevelPanel1,false);
    recAdded = false;
    if(groupT.getText().length() < 1) {
      ClientHelpObjs.setEnabledContainer(bevelPanel1,true);
      updating = false;
      groupT.setEnabled(true);
      groupT.requestFocus();
      CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
      CursorChange.setCursor(this.getContentPane(),Cursor.DEFAULT_CURSOR);
      return;
    }
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.ADMIN,parent);
      Hashtable query = new Hashtable();
      query.put("ACTION","GROUP"); //String, String
      query.put("USERID",(new Integer(parent.getUserId().intValue())).toString()); //String, String
      query.put("GROUP_NAME",groupT.getText()); //String, String
      query.put("FUNCTION","INFO");
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
         String no = new String("Access denied. Your session is corrupted, please restart the application.");
         GenericDlg err = new GenericDlg(parent.getMain(),"Denied",true);
         err.setMsg(no);
         err.setVisible(true);
         CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
         CursorChange.setCursor(this.getContentPane(),Cursor.DEFAULT_CURSOR);
         return;
      }

      Vector info = (Vector) result.get("DATA");
      String uOrA = (String)info.elementAt(0);
      ClientHelpObjs.setEnabledContainer(this,true);
      if(uOrA.equalsIgnoreCase("update")) {
        updating = true;
        descT.setText((String)info.elementAt(1));
        groupT.setEnabled(false);
        descT.requestFocus();
      }else{
        updating = false;
        descT.setText("");
        groupT.setEnabled(true);
        groupT.requestFocus();
      }
    } catch (Exception ie) {
      //parent.getMain().stopImage();
      ie.printStackTrace();
    }

    loading = false;
    this.repaint();
    CursorChange.setCursor(this.getContentPane(),Cursor.DEFAULT_CURSOR);
    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
  }


  public boolean submitChange(){
    if(!updating && groupT.getText().length() < 1) {
      GenericDlg gd1 = new GenericDlg(parent.getMain(),true);
      gd1.setMsg("Group Name can not be blank");
      gd1.show();
      return false;
    }
    if(descT.getText().length() < 1) {
      GenericDlg gd1 = new GenericDlg(parent.getMain(),true);
      gd1.setMsg("Group Description can not be blank");
      gd1.show();
      return false;
    }
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.ADMIN,parent);
      Hashtable query = new Hashtable();
      query.put("ACTION","GROUP");
      query.put("USERID",(new Integer(parent.getUserId().intValue())).toString()); //String, String
      query.put("GROUP_NAME",groupT.getText());
      query.put("GROUP_DESC",descT.getText());
      if(updating) {
        query.put("FUNCTION","UPDATE");
      }else{
        query.put("FUNCTION","ADD");
      }
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
         String no = new String("Access denied. Your session is corrupted, please restart the application.");
         GenericDlg err = new GenericDlg(parent.getMain(),"Denied",true);
         err.setMsg(no);
         err.setVisible(true);
         return false;
      }

      Vector info = (Vector) result.get("DATA");
      String yesNo = (String)info.elementAt(0);
      GenericDlg gd = new GenericDlg(parent.getMain(),true);
      if(yesNo.equalsIgnoreCase("yes")) {
        if(updating) {
          gd.setMsg("The group was updated.");
        }else{
          gd.setMsg("The group was added.");
          recAdded = true;
        }
        gd.show();
        return true;
      }else{
        gd.setMsg("Error on change. No changes were made.");
        gd.show();
        return false;
      }
    } catch (Exception ie) {
      //parent.getMain().stopImage();
      ie.printStackTrace();
      return false;
    }

  }
  public boolean recAdded() {
    return recAdded;
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
    dispose();
  }

  void this_windowClosing(WindowEvent e) {
    dispose();
  }

  void groupT_keyPressed(KeyEvent e) {
     if (e.getKeyCode() == KeyEvent.VK_ENTER) {
       if(submitChange()) {
         dispose();
       }
     }
  }

  void descT_keyPressed(KeyEvent e) {
     if (e.getKeyCode() == KeyEvent.VK_ENTER) {
       if(submitChange()) {
         dispose();
       }
     }
  }
}

class AddGroupDlg_okB_actionAdapter implements ActionListener{
  AddGroupDlg adaptee;

  AddGroupDlg_okB_actionAdapter(AddGroupDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okB_actionPerformed(e);
  }
}

class AddUseApprovalGroupDlg_cancelB_actionAdapter implements ActionListener{
  AddUseApprovalGroupDlg adaptee;

  AddUseApprovalGroupDlg_cancelB_actionAdapter(AddUseApprovalGroupDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelB_actionPerformed(e);
  }
}

class AddUseApprovalGroupDlg_this_windowAdapter extends WindowAdapter {
  AddUseApprovalGroupDlg adaptee;

  AddUseApprovalGroupDlg_this_windowAdapter(AddUseApprovalGroupDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}

class AddGroupThread extends Thread {
  AddGroupDlg parent;

  public AddGroupThread(AddGroupDlg parent){
     super("AddGroupThread");
     this.parent = parent;
  }

  public void run(){
     parent.loadScreen();
  }
}

