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
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class SealContainerDlg extends JDialog {
  XYLayout xYLayout1 = new XYLayout();
  JPanel panel1 = new JPanel();
  JButton bOk = new JButton();

  Frame parent;
  JTextField sealDateT = new JTextField();
  JLabel sealDateL = new JLabel();
  JButton cancelB = new JButton();
  CmisApp cmis ;
  StaticJLabel jLabel1 = new StaticJLabel();
  WasteManage wasteManage;
  String containerId = null;
  int selectedR = 0;

  public SealContainerDlg(JFrame frame, String title, WasteManage wm, String contId, int row) {
    super(frame, title);
    parent = frame;
    this.wasteManage = wm;
    this.containerId = contId;
    this.selectedR = row;

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

  public void loadIt(){
    SealContainerLoadThread x = new SealContainerLoadThread(this);
    x.start();
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
    xYLayout1.setWidth(460);
    xYLayout1.setHeight(179);
    panel1.addKeyListener(new SealContainerDlg_panel1_keyAdapter(this));
    bOk.setMaximumSize(new Dimension(53, 24));
    bOk.setMinimumSize(new Dimension(53, 24));
    bOk.setPreferredSize(new Dimension(53, 24));
    bOk.setActionCommand("");
    bOk.setText("Ok");
    bOk.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    bOk.addKeyListener(new SealContainerDlg_bOk_keyAdapter(this));
    bOk.addActionListener(new SealContainerDlg_bOk_actionAdapter(this));




    panel1.setLayout(xYLayout1);
    panel1.setMaximumSize(new Dimension(340, 100));
    panel1.setMinimumSize(new Dimension(340, 100));
    panel1.setPreferredSize(new Dimension(340, 100));
    sealDateL.setText("Seal Date:");
    cancelB.setMaximumSize(new Dimension(53, 24));
    cancelB.setMinimumSize(new Dimension(53, 24));
    cancelB.setPreferredSize(new Dimension(53, 24));
    cancelB.setText("Cancel");
    cancelB.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    cancelB.addActionListener(new SealContainerDlg_cancelB_actionAdapter(this));
    jLabel1.setText("mm/dd/yyyy");
    panel1.add(sealDateT, new XYConstraints(108, 17, 101, 30));
    panel1.add(sealDateL, new XYConstraints(41, 18, 67, 30));
    panel1.add(bOk, new XYConstraints(35, 61, 95, 28));
    panel1.add(cancelB, new XYConstraints(140, 61, 108, 28));
    panel1.add(jLabel1, new XYConstraints(211, 18, 67, 25));
    bOk.requestFocus();

    getContentPane().add(panel1);



    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
  }

 void loadItAction() {
  CursorChange.setCursor(cmis.getMain(),Cursor.WAIT_CURSOR);
  Calendar cal = Calendar.getInstance();
  String cdate = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
  sealDateT.setText(cdate);
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
  boolean failed = false;
  ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
  String sealDate = sealDateT.getText().toString();
  if (BothHelpObjs.isBlankString(sealDate)) {
    failed = true;
    GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
    gd.setMsg("Please enter a 'Seal Date'.");
    gd.setVisible(true);
  } else if (!BothHelpObjs.isDateFormatRight(sealDate)) {
    failed = true;
    GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
    gd.setMsg("Please enter 'Seal Date' in the following format 'mm/dd/yyyy/.");
    gd.setVisible(true);
  }

  if (!failed) {
    if (updateRequest()) {
      wasteManage.doSearch();
      wasteManage.setSelectedRow(selectedR);
    }
    setVisible(false);
  }
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

class SealContainerDlg_bOk_actionAdapter implements java.awt.event.ActionListener{
  SealContainerDlg adaptee;

  SealContainerDlg_bOk_actionAdapter(SealContainerDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.bOk_actionPerformed(e);
  }
}

class SealContainerDlg_bOk_keyAdapter extends java.awt.event.KeyAdapter {
  SealContainerDlg adaptee;

  SealContainerDlg_bOk_keyAdapter(SealContainerDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.bOk_keyPressed(e);
  }
}

class SealContainerDlg_panel1_keyAdapter extends java.awt.event.KeyAdapter {
  SealContainerDlg adaptee;

  SealContainerDlg_panel1_keyAdapter(SealContainerDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.panel1_keyPressed(e);
  }
}

class SealContainerDlg_cancelB_actionAdapter implements java.awt.event.ActionListener {
  SealContainerDlg adaptee;

  SealContainerDlg_cancelB_actionAdapter(SealContainerDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelB_actionPerformed(e);
  }
}

class SealContainerLoadThread extends Thread {
  SealContainerDlg parent;
  public SealContainerLoadThread(SealContainerDlg parent){
     super("SealContainerLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}

