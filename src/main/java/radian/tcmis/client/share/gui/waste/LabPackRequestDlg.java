//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui.waste;


import java.awt.*;
import java.awt.event.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.client.share.gui.*;
import java.util.*;
import radian.tcmis.client.share.httpCgi.*;
import java.text.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class LabPackRequestDlg extends JDialog {
  JPanel panel1 = new JPanel();
  JButton bOk = new JButton();

  Frame parent;
  JTextField dateT = new JTextField();
  StaticJLabel dateL = new StaticJLabel();
  JButton cancelB = new JButton();
  CmisApp cmis ;
  StaticJLabel jLabel1 = new StaticJLabel();
  WasteManage wasteManage;
  String containerId = null;
  int selectedR = 0;
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  StaticJLabel drumL = new StaticJLabel();
  JTextField drumT = new JTextField();

  public LabPackRequestDlg(JFrame frame, String title, WasteManage wm) {
    super(frame, title);
    parent = frame;
    wasteManage = wm;

    try {
      jbInit();
      pack();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setParent(CmisApp c){
    cmis = c;
  }

  private void jbInit() throws Exception{
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    */
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });
    panel1.addKeyListener(new LabPackRequestDlg_panel1_keyAdapter(this));
    bOk.setMaximumSize(new Dimension(80, 25));
    bOk.setMinimumSize(new Dimension(80, 25));
    bOk.setPreferredSize(new Dimension(80, 25));
    bOk.setActionCommand("");
    bOk.setText("Ok");
    bOk.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    bOk.addKeyListener(new LabPackRequestDlg_bOk_keyAdapter(this));
    bOk.addActionListener(new LabPackRequestDlg_bOk_actionAdapter(this));




    panel1.setLayout(gridBagLayout1);
    panel1.setMaximumSize(new Dimension(340, 170));
    panel1.setMinimumSize(new Dimension(340, 170));
    panel1.setPreferredSize(new Dimension(340, 170));
    dateL.setText("Preferred Service Date:");
    cancelB.setMaximumSize(new Dimension(120, 25));
    cancelB.setMinimumSize(new Dimension(120, 25));
    cancelB.setPreferredSize(new Dimension(120, 25));
    cancelB.setText("Cancel");
    cancelB.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    cancelB.addActionListener(new LabPackRequestDlg_cancelB_actionAdapter(this));
    jLabel1.setMaximumSize(new Dimension(120, 21));
    jLabel1.setMinimumSize(new Dimension(120, 21));
    jLabel1.setPreferredSize(new Dimension(120, 21));
    jLabel1.setText("mm/dd/yyyy");
    dateT.setMaximumSize(new Dimension(80, 21));
    dateT.setMinimumSize(new Dimension(80, 21));
    dateT.setPreferredSize(new Dimension(80, 21));
    drumL.setText("Estimated # of Drums:");
    drumT.setMaximumSize(new Dimension(80, 21));
    drumT.setMinimumSize(new Dimension(80, 21));
    drumT.setPreferredSize(new Dimension(80, 21));
    panel1.add(dateT, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(dateL, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(bOk, new GridBagConstraints2(0, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(20, 5, 5, 5), 0, 0));
    panel1.add(cancelB, new GridBagConstraints2(1, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(20, 5, 5, 5), 0, 0));
    panel1.add(jLabel1, new GridBagConstraints2(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(drumL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(drumT, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    bOk.requestFocus();

    getContentPane().add(panel1);

    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
  }

 void loadItAction() {
  CursorChange.setCursor(cmis.getMain(),Cursor.WAIT_CURSOR);
  Calendar cal = Calendar.getInstance();
  String cdate = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
  dateT.setText(cdate);
  CursorChange.setCursor(cmis.getMain(),Cursor.DEFAULT_CURSOR);
 }

 boolean updateRequest() {
  boolean val = true;
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","SEAL_CONTAINER"); //String, String
      query.put("CONTAINER_ID",this.containerId);

      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        val = false;
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return false;
      }
      Boolean b = (Boolean)result.get("RETURN_CODE");
      if(!b.booleanValue()){
        val = false;
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg(result.get("MSG").toString());
        gd.setVisible(true);
      }else{
        GenericDlg rd = new GenericDlg(cmis.getMain(),"Action Succeeded",true);
        rd.setMsg(result.get("MSG").toString());
        rd.setVisible(true);
      }
      ClientHelpObjs.setEnabledContainer(this,true);
      this.validate();
    } catch (Exception e) {
        e.printStackTrace();
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return false;
    }
    return val;
 }

 void bOk_actionPerformed(ActionEvent e) {
  ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
  int numDrum = 0;
  String serviceDate = BothHelpObjs.makeBlankFromNull(dateT.getText().toString());
  //if user enter a date than audit date
  SimpleDateFormat numF = new SimpleDateFormat("MM/dd/yyyy");
  if (!BothHelpObjs.isBlankString(serviceDate)) {
    try {
      Date myDate = numF.parse(serviceDate);
      //now check to make sure that the preferred date is not in the pass
      Date d = new Date();
      Calendar cal = Calendar.getInstance();
      String cdate = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
      if ( d.parse(cdate) > d.parse(serviceDate)) {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg("The date you entered is invalid.\n Please check and re-enter another date (MM/DD/YYYY).");
        gd.setVisible(true);
        return;
      }
    }catch (ParseException eeee) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg("Invalid Date Format. Please enter a preferred service\n date in the following format(MM/DD/YYYY)");
      gd.setVisible(true);
      return;
    }

    //now check to see if user enter the right format
    if (!BothHelpObjs.isDateFormatRight(serviceDate)) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg("Invalid Date Format. Please enter a preferred service\n date in the following format(MM/DD/YYYY)");
      gd.setVisible(true);
      return;
    }

  }


  //audit the estimated number of drum -- whole number only
  try {
    numDrum = Integer.parseInt(drumT.getText());
    if (numDrum < 1) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg("Please enter a positive integer for Estimated # of Drums.");
      gd.setVisible(true);
      return;
    }
  }catch (Exception ee) {
    ee.printStackTrace();
    GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
    gd.setMsg("Please enter a positive integer for Estimated # of Drums.");
    gd.setVisible(true);
    return;
  }

  wasteManage.estimatedNumDrum = numDrum;
  wasteManage.preferredServiceDate = serviceDate;
  setVisible(false);
  wasteManage.addLabPackToCart();
 }

  void bOk_keyPressed(KeyEvent e) {
  }
  void panel1_keyPressed(KeyEvent e) {
  }
  void this_windowClosing(WindowEvent e) {
    this.setVisible(false);
  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    this.setVisible(false);
  }

}

class LabPackRequestDlg_bOk_actionAdapter implements java.awt.event.ActionListener{
  LabPackRequestDlg adaptee;

  LabPackRequestDlg_bOk_actionAdapter(LabPackRequestDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.bOk_actionPerformed(e);
  }
}

class LabPackRequestDlg_bOk_keyAdapter extends java.awt.event.KeyAdapter {
  LabPackRequestDlg adaptee;

  LabPackRequestDlg_bOk_keyAdapter(LabPackRequestDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.bOk_keyPressed(e);
  }
}

class LabPackRequestDlg_panel1_keyAdapter extends java.awt.event.KeyAdapter {
  LabPackRequestDlg adaptee;

  LabPackRequestDlg_panel1_keyAdapter(LabPackRequestDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.panel1_keyPressed(e);
  }
}

class LabPackRequestDlg_cancelB_actionAdapter implements java.awt.event.ActionListener {
  LabPackRequestDlg adaptee;

  LabPackRequestDlg_cancelB_actionAdapter(LabPackRequestDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelB_actionPerformed(e);
  }
}

