package radian.tcmis.client.share.gui;

import java.awt.*;
import java.awt.event.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.client.share.gui.waste.*;
import java.util.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
import java.awt.Component;

public class CreditCardInfoDlg extends JDialog {
  JPanel panel1 = new JPanel();
  JButton bOk = new JButton();

  Frame parent;
  JTextField ccNumberT = new JTextField();
  StaticJLabel creditCL = new StaticJLabel();
  JButton cancelB = new JButton();
  CmisApp cmis ;
  String containerId = null;
  int selectedR = 0;
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  StaticJLabel jLabel1 = new StaticJLabel();
  JComboBox ccTypeC = new JComboBox();
  StaticJLabel jLabel2 = new StaticJLabel();
  JTextField exDateT = new JTextField();
  StaticJLabel jLabel3 = new StaticJLabel();
  JTextField lastNameT = new JTextField();
  StaticJLabel jLabel4 = new StaticJLabel();
  StaticJLabel jLabel5 = new StaticJLabel();
  JTextField firstNameT = new JTextField();
  JTextField middleInitialT = new JTextField();
  Hashtable ccInfoH = null;
  String name = "";

  Component comp;
  RequestsWin requestsWin;
  WastePickupRequest wastePickupRequest;
  WasteAddEditMaterialRequest wasteAddEditMaterialRequest;
  AddLabPackContainer addLabPackContainer;

  boolean editable = true;

  //public CreditCardInfoDlg(JFrame frame, String title, RequestsWin rw,Hashtable ccInfo, String name) {
  public CreditCardInfoDlg(JFrame frame, String title, Component c,Hashtable ccInfo, String name) {
    super(frame, title);
    parent = frame;
    //this.requestsWin = rw;
    this.comp = c;
    this.ccInfoH = ccInfo;
    this.name = name;
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

  public void setEditable(boolean editable) {
    this.editable = editable;
  }

  public void loadIt(){
    CreditCardInfoLoadThread x = new CreditCardInfoLoadThread(this);
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
    panel1.addKeyListener(new CreditCardInfoDlg_panel1_keyAdapter(this));
    bOk.setMaximumSize(new Dimension(120, 24));
    bOk.setMinimumSize(new Dimension(120, 24));
    bOk.setPreferredSize(new Dimension(120, 24));
    bOk.setActionCommand("");
    bOk.setText("Ok");
    bOk.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    bOk.addKeyListener(new CreditCardInfoDlg_bOk_keyAdapter(this));
    bOk.addActionListener(new CreditCardInfoDlg_bOk_actionAdapter(this));

    panel1.setBorder(ClientHelpObjs.groupBox(""));
    panel1.setLayout(gridBagLayout1);
    panel1.setMaximumSize(new Dimension(340, 250));
    panel1.setMinimumSize(new Dimension(340, 200));
    panel1.setPreferredSize(new Dimension(340, 200));
    creditCL.setText("Credit Card Number:");
    cancelB.setMaximumSize(new Dimension(120, 24));
    cancelB.setMinimumSize(new Dimension(120, 24));
    cancelB.setPreferredSize(new Dimension(120, 24));
    cancelB.setText("Cancel");
    cancelB.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    cancelB.addActionListener(new CreditCardInfoDlg_cancelB_actionAdapter(this));
    ccNumberT.setMaximumSize(new Dimension(100, 21));
    ccNumberT.setMinimumSize(new Dimension(100, 21));
    ccNumberT.setPreferredSize(new Dimension(100, 21));
    jLabel1.setText("Credit Card Type:");
    ccTypeC.setMaximumSize(new Dimension(100, 21));
    ccTypeC.setMinimumSize(new Dimension(100, 21));
    ccTypeC.setPreferredSize(new Dimension(100, 21));
    jLabel2.setText("Expiration Date:");
    exDateT.setMaximumSize(new Dimension(100, 21));
    exDateT.setMinimumSize(new Dimension(100, 21));
    exDateT.setPreferredSize(new Dimension(100, 21));
    jLabel3.setText("Name Appears on Credit Card:");
    lastNameT.setMaximumSize(new Dimension(100, 21));
    lastNameT.setMinimumSize(new Dimension(100, 21));
    lastNameT.setPreferredSize(new Dimension(100, 21));
    jLabel4.setText("First Name:");
    jLabel5.setText("Middle Initial:");
    firstNameT.setMaximumSize(new Dimension(100, 21));
    firstNameT.setMinimumSize(new Dimension(100, 21));
    firstNameT.setPreferredSize(new Dimension(100, 21));
    middleInitialT.setMaximumSize(new Dimension(50, 21));
    middleInitialT.setMinimumSize(new Dimension(50, 21));
    middleInitialT.setPreferredSize(new Dimension(50, 21));
    panel1.add(ccNumberT, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(creditCL, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
    panel1.add(bOk, new GridBagConstraints2(0, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(15, 5, 5, 5), 0, 0));
    panel1.add(cancelB, new GridBagConstraints2(1, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(15, 5, 5, 5), 0, 0));
    panel1.add(jLabel1, new GridBagConstraints2(0, 0, 1, 1, 0.2, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(ccTypeC, new GridBagConstraints2(1, 0, 1, 1, 0.5, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(jLabel2, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
    panel1.add(exDateT, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(jLabel3, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(lastNameT, new GridBagConstraints2(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    /*
    panel1.add(jLabel4, new GridBagConstraints2(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(jLabel5, new GridBagConstraints2(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(firstNameT, new GridBagConstraints2(1, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(middleInitialT, new GridBagConstraints2(1, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    */
    bOk.requestFocus();

    getContentPane().add(panel1);

    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
  }

  int getCreditCardTypeDesc(String ccID) {
    Vector ccTDesc = (Vector)ccInfoH.get("CREDIT_CARD_TYPE_DESC");
    Vector ccV = (Vector)ccInfoH.get("CREDIT_CARD_TYPE_ID");
    int index = 0;
    for (int i = 0; i < ccV.size(); i++) {
      String tmp = (String)ccV.elementAt(i);
      if (tmp.equalsIgnoreCase(ccID)) {
        index = i;
        break;
      }
    }
    return (index);
  }

  String getSelectedCreditCardTypeID() {
    String ccT = ccTypeC.getSelectedItem().toString();
    Vector ccTDesc = (Vector)ccInfoH.get("CREDIT_CARD_TYPE_DESC");
    Vector ccV = (Vector)ccInfoH.get("CREDIT_CARD_TYPE_ID");
    int index = 0;
    for (int i = 0; i < ccTDesc.size(); i++) {
      String tmp = (String)ccTDesc.elementAt(i);
      if (tmp.equalsIgnoreCase(ccT)) {
        index = i;
        break;
      }
    }
    return (ccV.elementAt(index).toString());
  }


  void loadItAction() {
    CursorChange.setCursor(cmis.getMain(),Cursor.WAIT_CURSOR);
    ccTypeC = ClientHelpObjs.loadChoiceFromVector(ccTypeC,(Vector)ccInfoH.get("CREDIT_CARD_TYPE_DESC"));

    String ccType = (String)ccInfoH.get("CREDIT_CARD_TYPE");
    ccTypeC.setSelectedIndex(getCreditCardTypeDesc(ccType));
    ccNumberT.setText((String)ccInfoH.get("CREDIT_CARD_NUMBER"));
    exDateT.setText((String)ccInfoH.get("CREDIT_CARD_EXP_DATE"));
    lastNameT.setText((String)ccInfoH.get("CREDIT_CARD_NAME"));

    ccTypeC.setEnabled(editable);
    ccNumberT.setEnabled(editable);
    exDateT.setEnabled(editable);
    lastNameT.setEnabled(editable);

    CursorChange.setCursor(cmis.getMain(),Cursor.DEFAULT_CURSOR);
 }

 boolean updateRequest() {
    boolean val = true;

    ccInfoH.put("CREDIT_CARD_TYPE",getSelectedCreditCardTypeID());
    ccInfoH.put("CREDIT_CARD_NUMBER",BothHelpObjs.makeBlankFromNull(ccNumberT.getText()));
    ccInfoH.put("CREDIT_CARD_EXP_DATE",BothHelpObjs.makeBlankFromNull(exDateT.getText()));
    ccInfoH.put("CREDIT_CARD_NAME",BothHelpObjs.makeBlankFromNull(lastNameT.getText()));
       /*
    if (comp instanceof WastePickupRequest){
      ((WastePickupRequest)comp).headerInfo.put("CREDIT_CARD_INFO",ccInfoH);
    }else if (comp instanceof RequestsWin) {
      ((RequestsWin)comp).headerInfo.put("CREDIT_CARD_INFO",ccInfoH);
      ((RequestsWin)comp).dataChanged = true;
    }else if (comp instanceof WasteAddEditMaterialRequest) {
      ((WasteAddEditMaterialRequest)comp).headerInfo.put("CREDIT_CARD_INFO",ccInfoH);
    }else if (comp instanceof AddInto2) {
      //((WasteAddEditMaterialRequest)comp).headerInfo.put("CREDIT_CARD_INFO",ccInfoH);
    }*/
    return val;
 }

 void bOk_actionPerformed(ActionEvent e) {
  boolean failed = false;
  ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
  String expDate = exDateT.getText().toString();
  GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);

  if (BothHelpObjs.isBlankString(ccNumberT.getText())) {
    failed = true;
    gd.setMsg("Please enter a 'Credit Card Number'.");
    gd.setVisible(true);
    return;
  }else {
    try {
      float f = Float.parseFloat(ccNumberT.getText());
    }catch (Exception eee) {
      failed = true;
      gd.setMsg("Please enter a 'Credit Card Number' as number only.");
      gd.setVisible(true);
      return;
    }
  }

  if (BothHelpObjs.isBlankString(expDate)) {
    failed = true;
    gd.setMsg("Please enter the 'Experation Date'.");
    gd.setVisible(true);
    return;
  } else if (!BothHelpObjs.isDateFormatRight(expDate,"MM/yyyy")) {
    failed = true;
    gd.setMsg("Please enter 'Expiration Date' in the following format 'mm/yyyy.");
    gd.setVisible(true);
    return;
  }

  /*JRE1.4+
  String[] tmpExpDate = expDate.split("/");
  String newExpDate = tmpExpDate[0]+"/01/"+tmpExpDate[1];
  */

  //JRE1.3
  int tmpIndex = expDate.indexOf("/");
  String newExpDate = expDate.substring(0,tmpIndex)+"/01/"+expDate.substring(tmpIndex+1);
   //end of JRE1.3

  Date d = new Date();
  Calendar cal = Calendar.getInstance();
  String cdate = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
  if ( d.parse(cdate) > d.parse(newExpDate)) {
    failed = true;
    gd.setMsg("The date you entered is invalid.\n Please check and re-enter another date.");
    gd.setVisible(true);
    return;
  }

  if (BothHelpObjs.isBlankString(lastNameT.getText())) {
    failed = true;
    gd.setMsg("Please enter 'Name Appears on Credit Card'.");
    gd.setVisible(true);
    return;
  }

  if (!failed) {
    if (updateRequest()) {
      setVisible(false);
    }
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

class CreditCardInfoDlg_bOk_actionAdapter implements java.awt.event.ActionListener{
  CreditCardInfoDlg adaptee;

  CreditCardInfoDlg_bOk_actionAdapter(CreditCardInfoDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.bOk_actionPerformed(e);
  }
}

class CreditCardInfoDlg_bOk_keyAdapter extends java.awt.event.KeyAdapter {
  CreditCardInfoDlg adaptee;

  CreditCardInfoDlg_bOk_keyAdapter(CreditCardInfoDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.bOk_keyPressed(e);
  }
}

class CreditCardInfoDlg_panel1_keyAdapter extends java.awt.event.KeyAdapter {
  CreditCardInfoDlg adaptee;

  CreditCardInfoDlg_panel1_keyAdapter(CreditCardInfoDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.panel1_keyPressed(e);
  }
}

class CreditCardInfoDlg_cancelB_actionAdapter implements java.awt.event.ActionListener {
  CreditCardInfoDlg adaptee;

  CreditCardInfoDlg_cancelB_actionAdapter(CreditCardInfoDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelB_actionPerformed(e);
  }
}

class CreditCardInfoLoadThread extends Thread {
  CreditCardInfoDlg parent;
  public CreditCardInfoLoadThread(CreditCardInfoDlg parent){
     super("CreditCardInfoLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}
