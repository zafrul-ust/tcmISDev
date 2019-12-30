// This snippet creates a new dialog box
// with buttons on the bottom.

//Title:
//Version:
//Copyright:
//Author:
//Company:
//Description:

package  radian.tcmis.client.share.gui;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.helpers.*;
import com.borland.jbcl.layout.*;

public class PriceRequestDlg extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JPanel JPanel2 = new JPanel();
  JButton button1 = new JButton();
  JPanel jPanel1 = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  StaticJLabel jLabel1 = new StaticJLabel();
  StaticJLabel jLabel2 = new StaticJLabel();
  StaticJLabel jLabel3 = new StaticJLabel();
  StaticJLabel jLabel4 = new StaticJLabel();
  StaticJLabel jLabel5 = new StaticJLabel();
  DataJLabel dateL = new DataJLabel();
  DataJLabel facilityL = new DataJLabel();
  DataJLabel reqNameL = new DataJLabel();
  DataJLabel itemIdL = new DataJLabel();
  DataJLabel quoterL = new DataJLabel();
  DataJLabel confirmL = new DataJLabel();
  DataJLabel requestIdL = new DataJLabel();

  Integer itemId = null;
  String facPartNum = null;
  String facId = null;
  CmisApp cmis = null;
  StaticJLabel jLabel6 = new StaticJLabel();

  public PriceRequestDlg(Frame frame) {
    super(frame, "Price Request", true);
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    this.getContentPane().add(panel1);
    pack();
  }

  void setItemId(int i) {
    itemId = new Integer(i);
  }
  void setFacPartNum(String s) {
    facPartNum = new String(s);
  }
  void setFacId(String s) {
    facId = new String(s);
  }
  void setCmisApp(CmisApp c) {
    cmis = c;
  }

  private void jbInit() throws Exception {
    panel1.setPreferredSize(new Dimension(450, 205));
    xYLayout1.setWidth(449);
    xYLayout1.setHeight(204);
    JPanel2.setBorder(new BevelBorder(BevelBorder.LOWERED));
    JPanel2.setMaximumSize(new Dimension(450, 160));
    JPanel2.setPreferredSize(new Dimension(450, 160));
    JPanel2.setMinimumSize(new Dimension(450, 160));
    button1.setText("OK");
    jLabel1.setText("Requested by:");
    jLabel1.setHorizontalAlignment(4);
    jLabel2.setText("Facility:");
    jLabel2.setHorizontalAlignment(4);
    jLabel3.setText("Quoter:");
    jLabel3.setHorizontalAlignment(4);
    jLabel4.setText("Item ID:");
    jLabel4.setHorizontalAlignment(4);
    jLabel5.setText("Date Requested:");
    jLabel5.setHorizontalAlignment(4);
    confirmL.setHorizontalAlignment(0);
    jLabel6.setText("Request ID:");
    jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
    JPanel2.setLayout(gridBagLayout1);
    button1.addActionListener(new PriceRequestDlg_button1_actionAdapter(this));
    jPanel1.setLayout(flowLayout1);
    this.addWindowListener(new PriceRequestDlg_this_windowAdapter(this));
    panel1.setLayout(xYLayout1);
    panel1.add(JPanel2, new XYConstraints(10, 10, 431, 132));
    JPanel2.add(jLabel1, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 3), 0, 0));
    JPanel2.add(jLabel2, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 3), 0, 0));
    JPanel2.add(jLabel3, new GridBagConstraints2(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 3), 0, 0));
    JPanel2.add(jLabel4, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 3), 0, 0));
    JPanel2.add(jLabel5, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 3), 0, 0));
    JPanel2.add(dateL, new GridBagConstraints2(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 5, 0), 0, 0));
    JPanel2.add(facilityL, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 5, 0), 0, 0));
    JPanel2.add(reqNameL, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 5, 0), 60, 0));
    JPanel2.add(itemIdL, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 0, 0), 0, 0));
    JPanel2.add(quoterL, new GridBagConstraints2(3, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 5, 0), 0, 0));
    JPanel2.add(confirmL, new GridBagConstraints2(0, 3, 3, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(30, 0, 0, 0), 0, 0));
    JPanel2.add(jLabel6, new GridBagConstraints2(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 3), 0, 0));
    JPanel2.add(requestIdL, new GridBagConstraints2(3, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 3, 0, 0), 0, 0));
    panel1.add(jPanel1, new XYConstraints(10, 153, 431, 40));
    jPanel1.add(button1, null);

    this.setResizable(false);
  }

  public synchronized  void loadIt(){
    PriceRequestDlgLoadThread iT = new PriceRequestDlgLoadThread(this);
    iT.start();
  }

  public void loadItAction() {
      //CursorChange.setCursor(this.getContentPane(),Cursor.WAIT_CURSOR);
      try {
        LoadInitialFields();
      }
        catch (Exception e) {
        e.printStackTrace();
      }
      CenterComponent.centerCompOnScreen(this);
      this.setVisible(true);
      //CursorChange.setCursor(this.getContentPane(),Cursor.DEFAULT_CURSOR);
      //CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
  }

  protected void LoadInitialFields(){
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.CATALOG,cmis);
    Hashtable query = new Hashtable();
    query.put("ACTION","PRICE_REQUEST");
    query.put("ITEM",itemId.toString());
    query.put("FACID",facId);
    query.put("USERID",cmis.getMain().getUserId().toString());

    Hashtable result = cgi.getResultHash(query);

    if (result == null){
         String no = new String("Access denied. Your session is corrupted, please restart the application.");
         GenericDlg err = new GenericDlg(cmis.getMain(),"Denied",true);
         err.setMsg(no);
         err.setVisible(true);
         return;
    }
    Vector v = (Vector)result.get("PRICE_REQUEST_INFO");

    itemIdL.setText(v.elementAt(0).toString());
    facilityL.setText(v.elementAt(1).toString());
    quoterL.setText(v.elementAt(2).toString());
    dateL.setText(v.elementAt(3).toString());
    reqNameL.setText(v.elementAt(4).toString());
    requestIdL.setText(v.elementAt(5).toString());
    if(v.elementAt(6).toString().equalsIgnoreCase("NEW")) {
      confirmL.setText("Your request for a price was submitted.");
    }else{
      confirmL.setText("");
    }
    this.validate();
    ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));

  }


  // OK
  void button1_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    dispose();
  }

  void this_windowClosing(WindowEvent e) {
    dispose();
  }
}

class PriceRequestDlg_button1_actionAdapter implements ActionListener{
  PriceRequestDlg adaptee;

  PriceRequestDlg_button1_actionAdapter(PriceRequestDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.button1_actionPerformed(e);
  }
}


class PriceRequestDlg_this_windowAdapter extends WindowAdapter {
  PriceRequestDlg adaptee;

  PriceRequestDlg_this_windowAdapter(PriceRequestDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}
class PriceRequestDlgLoadThread extends Thread {
  PriceRequestDlg parent;

  public PriceRequestDlgLoadThread(PriceRequestDlg parent){
     super("PriceRequestDlgLoadThread");
     this.parent = parent;
  }

  public void run(){
     parent.loadItAction();
  }
}
