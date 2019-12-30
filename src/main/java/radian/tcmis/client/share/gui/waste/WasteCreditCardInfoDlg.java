package radian.tcmis.client.share.gui.waste;

import java.awt.*;
import java.awt.event.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.client.share.gui.*;
import java.util.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
import java.awt.Component;

public class WasteCreditCardInfoDlg extends JDialog {
  JPanel panel1 = new JPanel();
  JButton bOk = new JButton();

  Frame parent;
  JTextField ccNumberT = new JTextField();
  StaticJLabel creditCL = new StaticJLabel();
  JButton cancelB = new JButton();
  CmisApp cmis ;
  WastePickupRequest wastePickupRequest;
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
  String originalType;
  String originalNumber;
  String originalExpDate;
  String originalName;

  //public WasteCreditCardInfoDlg(JFrame frame, String title, WastePickupRequest wr,Hashtable ccInfo, String name) {
  public WasteCreditCardInfoDlg(JFrame frame, String title, Component c,Hashtable ccInfo, String name) {
    super(frame, title);
    parent = frame;
    this.comp = c;
    this.ccInfoH = ccInfo;
    //keep this info so I could compare if user make any changes
    originalType = BothHelpObjs.makeBlankFromNull((String)ccInfoH.get("CREDIT_CARD_TYPE"));
    originalNumber = BothHelpObjs.makeBlankFromNull((String)ccInfoH.get("CREDIT_CARD_NUMBER"));
    originalExpDate = BothHelpObjs.makeBlankFromNull((String)ccInfoH.get("CREDIT_CARD_EXP_DATE"));
    originalName = BothHelpObjs.makeBlankFromNull((String)ccInfoH.get("CREDIT_CARD_NAME"));
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

  public void loadIt(){
    WasteCreditCardInfoLoadThread x = new WasteCreditCardInfoLoadThread(this);
    x.start();
  }

  private void jbInit() throws Exception{
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });
    panel1.addKeyListener(new WasteCreditCardInfoDlg_panel1_keyAdapter(this));
    bOk.setMaximumSize(new Dimension(120, 24));
    bOk.setMinimumSize(new Dimension(120, 24));
    bOk.setPreferredSize(new Dimension(120, 24));
    bOk.setActionCommand("");
    bOk.setText("Ok");
    bOk.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    bOk.addKeyListener(new WasteCreditCardInfoDlg_bOk_keyAdapter(this));
    bOk.addActionListener(new WasteCreditCardInfoDlg_bOk_actionAdapter(this));

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
    cancelB.addActionListener(new WasteCreditCardInfoDlg_cancelB_actionAdapter(this));
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

    CursorChange.setCursor(cmis.getMain(),Cursor.DEFAULT_CURSOR);
 }

 boolean updateRequest() {
    boolean val = true;

    ccInfoH.put("CREDIT_CARD_TYPE",getSelectedCreditCardTypeID());
    ccInfoH.put("CREDIT_CARD_NUMBER",BothHelpObjs.makeBlankFromNull(ccNumberT.getText()));
    ccInfoH.put("CREDIT_CARD_EXP_DATE",BothHelpObjs.makeBlankFromNull(exDateT.getText()));
    ccInfoH.put("CREDIT_CARD_NAME",BothHelpObjs.makeBlankFromNull(lastNameT.getText()));

    return val;
 }

 void bOk_actionPerformed(ActionEvent e) {
  boolean failed = false;
  ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
  String ccType = this.getSelectedCreditCardTypeID();
  String ccNumber = ccNumberT.getText();
  String expDate = exDateT.getText().toString();
  String ccName = lastNameT.getText();
  GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);

  if (BothHelpObjs.isBlankString(ccNumber)) {
    failed = true;
    gd.setMsg("Please enter a 'Credit Card Number'.");
    gd.setVisible(true);
    return;
  }

  if (BothHelpObjs.isBlankString(expDate)) {
    failed = true;
    gd.setMsg("Please enter the 'Experation Date'.");
    gd.setVisible(true);
    return;
  } else if (!BothHelpObjs.isDateFormatRight(expDate)) {
    failed = true;
    gd.setMsg("Please enter 'Expiration Date' in the following format 'mm/dd/yyyy/.");
    gd.setVisible(true);
    return;
  }

  Date d = new Date();
  Calendar cal = Calendar.getInstance();
  String cdate = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
  if ( d.parse(cdate) > d.parse(expDate)) {
    failed = true;
    gd.setMsg("The date you entered is invalid.\n Please check and re-enter another date.");
    gd.setVisible(true);
    return;
  }

  if (BothHelpObjs.isBlankString(ccName)) {
    failed = true;
    gd.setMsg("Please enter 'Name Appears on Credit Card'.");
    gd.setVisible(true);
    return;
  }

  if (!failed) {
    //check to see if info change
    if (!BothHelpObjs.isBlankString(originalNumber)) {
      boolean infoChanged = false;
      if (!originalType.equals(ccType) ||
          !originalNumber.equals(ccNumber) ||
          !originalExpDate.equals(expDate) ||
          !originalName.equals(ccName) ) {
        infoChanged = true;
      }
      if (infoChanged) {
        ConfirmDlg cfd = new ConfirmDlg(cmis.getMain(),"Credit Card Data has Changed",true);
        cfd.setMsg("All waste material request lines using the previous credit card data will be updated.");
        cfd.setVisible(true);
        if (cfd.getConfirmationFlag()){    //if ok then update data
          if (updateRequest()) {
            setVisible(false);
          }
        }else {
          return;
        }
      }else {  //no change
        setVisible(false);
      }
    }else{
      if (updateRequest()) {
        setVisible(false);
      }
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

class WasteCreditCardInfoDlg_bOk_actionAdapter implements java.awt.event.ActionListener{
  WasteCreditCardInfoDlg adaptee;

  WasteCreditCardInfoDlg_bOk_actionAdapter(WasteCreditCardInfoDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.bOk_actionPerformed(e);
  }
}

class WasteCreditCardInfoDlg_bOk_keyAdapter extends java.awt.event.KeyAdapter {
  WasteCreditCardInfoDlg adaptee;

  WasteCreditCardInfoDlg_bOk_keyAdapter(WasteCreditCardInfoDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.bOk_keyPressed(e);
  }
}

class WasteCreditCardInfoDlg_panel1_keyAdapter extends java.awt.event.KeyAdapter {
  WasteCreditCardInfoDlg adaptee;

  WasteCreditCardInfoDlg_panel1_keyAdapter(WasteCreditCardInfoDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.panel1_keyPressed(e);
  }
}

class WasteCreditCardInfoDlg_cancelB_actionAdapter implements java.awt.event.ActionListener {
  WasteCreditCardInfoDlg adaptee;

  WasteCreditCardInfoDlg_cancelB_actionAdapter(WasteCreditCardInfoDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelB_actionPerformed(e);
  }
}

class WasteCreditCardInfoLoadThread extends Thread {
  WasteCreditCardInfoDlg parent;
  public WasteCreditCardInfoLoadThread(WasteCreditCardInfoDlg parent){
     super("WasteCreditCardInfoLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}