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
public class AddUseApprovalGroupDlg extends JDialog {
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

  String facId = null;
  String groupType = null;

  boolean changeMade = false;
  boolean updating = false;
  StaticJLabel jLabel1 = new StaticJLabel();
  DataJLabel facNameL = new DataJLabel();
  XYLayout xYLayout2 = new XYLayout();
  boolean cancelledFlag = false;

  public AddUseApprovalGroupDlg(Frame frame, String title, boolean modal,CmisApp cmis,String facName, String facId, String grpId, String grpDesc, String groupType, boolean updating) {
    super(frame, title, modal);
    this.updating = updating;
    this.facId = facId;
    facNameL.setText(facName);
    groupT.setText(grpId);
    descT.setText(grpDesc);
    this.groupType = groupType;
    parent = cmis;
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
    if(updating) groupT.setEnabled(false);
    setResizable(false);
    panel1.setPreferredSize(new Dimension(370, 220));
    xYLayout1.setWidth(371);
    xYLayout1.setHeight(220);
    bevelPanel1.setLayout(xYLayout2);
    okB.setText("OK");
    okB.addActionListener(new AddUseApprovalGroupDlg_okB_actionAdapter(this));
    cancelB.setText("Cancel");
    groupL.setText("Group ID:");
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
    cancelB.addActionListener(new AddUseApprovalGroupDlg_cancelB_actionAdapter(this));
    this.addWindowListener(new AddUseApprovalGroupDlg_this_windowAdapter(this));
    panel1.setLayout(xYLayout1);
    panel1.add(bevelPanel1, new XYConstraints(10, 10, 350, 140));

    bevelPanel1.add(groupL, new XYConstraints(40, 56, 60, -1));
    bevelPanel1.add(descL, new XYConstraints(40, 97, 60, -1));
    bevelPanel1.add(groupT, new XYConstraints(104, 53, 210, 24));
    bevelPanel1.add(descT, new XYConstraints(104, 94, 210, 24));
    bevelPanel1.add(jLabel1, new XYConstraints(40, 17, 60, -1));
    bevelPanel1.add(facNameL, new XYConstraints(104, 16, 209, -1));

    panel1.add(jPanel1, new XYConstraints(10, 160, 350, 45));

    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR");
    */
    ImageIcon ss = null;
    ss = ResourceLoader.getImageIcon("images/button/ok.gif");
    okB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/cancel.gif");
    jLabel1.setText("Facility:");
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
    cancelB.setIcon(ss);

    jPanel1.add(okB, null);
    jPanel1.add(cancelB, null);
  }

  public void setParent(CmisApp parent) {
    this.parent = parent;
  }
  public boolean changeMade(){
    return changeMade;
  }

  public boolean submitChange(){
    if(!updating && groupT.getText().length() < 1) {
      GenericDlg gd1 = new GenericDlg(parent.getMain(),"Group ID",true);
      gd1.setMsg("Group ID can not be blank");
      gd1.show();
      return false;
    }
    if(descT.getText().length() < 1) {
      GenericDlg gd1 = new GenericDlg(parent.getMain(),"Group Description",true);
      gd1.setMsg("Group Description can not be blank");
      gd1.show();
      return false;
    }
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.ADMIN,parent);
      Hashtable query = new Hashtable();
      query.put("ACTION","CHANGE_GROUP_INFO");
      query.put("USERID",(new Integer(parent.getUserId().intValue())).toString()); //String, String
      query.put("GROUP_NAME",groupT.getText());
      query.put("GROUP_DESC",descT.getText());
      query.put("GROUP_TYPE",groupType);
      query.put("FACILITY_ID",facId);
      if(updating) {
        query.put("FUNCTION","UPDATE");
      }else{
        query.put("FUNCTION","ADD");
      }
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
         GenericDlg.showAccessDenied(parent.getMain());
         return false;
      }

      Vector info = (Vector) result.get("DATA");
      String yesNo = (String)info.elementAt(0);
      GenericDlg gd = new GenericDlg(parent.getMain(),"Group Status",true);
      if(yesNo.equalsIgnoreCase("true")) {
        if(updating) {
          gd.setMsg("The group was updated.");
        }else{
          gd.setMsg("The group was added.");
        }
        gd.show();
        changeMade = true;
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
  // OK
  void okB_actionPerformed(ActionEvent e) {
    if(submitChange()) {
      setVisible(false);
    }
  }

  // Cancel
  void cancelB_actionPerformed(ActionEvent e) {
    cancelledFlag = true;
    setVisible(false);
  }

  void this_windowClosing(WindowEvent e) {
  }

  void groupT_keyPressed(KeyEvent e) {
     if (e.getKeyCode() == KeyEvent.VK_ENTER) {
       if(submitChange()) {
         setVisible(false);
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

class AddUseApprovalGroupDlg_okB_actionAdapter implements ActionListener{
  AddUseApprovalGroupDlg adaptee;

  AddUseApprovalGroupDlg_okB_actionAdapter(AddUseApprovalGroupDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okB_actionPerformed(e);
  }
}

class AddGroupDlg_cancelB_actionAdapter implements ActionListener{
  AddGroupDlg adaptee;

  AddGroupDlg_cancelB_actionAdapter(AddGroupDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelB_actionPerformed(e);
  }
}

class AddGroupDlg_this_windowAdapter extends WindowAdapter {
  AddGroupDlg adaptee;

  AddGroupDlg_this_windowAdapter(AddGroupDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}

