
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui.delivery;

import java.math.BigDecimal;
import javax.swing.*;
import java.util.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.client.share.gui.*;
import radian.tcmis.client.share.helpers.*;
import java.text.*;
import java.awt.event.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class DeliveryDayDlg extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JButton okB = new JButton();
  JButton cancelB = new JButton();
  StaticJLabel jLabel1 = new StaticJLabel();
  StaticJLabel jLabel3 = new StaticJLabel();
  StaticJLabel jLabel5 = new StaticJLabel();
  StaticJLabel jLabel7 = new StaticJLabel();
  DataJLabel partNumL = new DataJLabel();
  DataJLabel descL = new DataJLabel();
  DataJLabel itemL = new DataJLabel();
  JTextField dateL = new JTextField();
  JTextField qtyL = new JTextField();
  XYLayout xYLayout2 = new XYLayout();
  StaticJLabel jLabel2 = new StaticJLabel();
  Calendar firstActiveDay;

  CmisApp cmis;
  boolean canceled = true;

  public DeliveryDayDlg(CmisApp cmis,String partNum, String itemId, String desc,Calendar firstActiveDay) {
    super(cmis.getMain(), "Delivery", true);
    try  {
      this.cmis = cmis;
      this.firstActiveDay = firstActiveDay;
      jbInit();
      partNumL.setText(partNum);
      itemL.setText(itemId);
      descL.setText(desc);
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  public DeliveryDayDlg(CmisApp cmis,String partNum, String itemId, String desc, Calendar date, BigDecimal qty,Calendar firstActiveDay) {
    this(cmis,partNum,itemId,desc,firstActiveDay);
    SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
    df.setCalendar(date);
    dateL.setText(df.toPattern());
    qtyL.setText(qty.toString());
  }


  void jbInit() throws Exception {
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR");
    */
    ImageIcon ss = null;
    ss = ResourceLoader.getImageIcon("images/button/ok.gif");
    okB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/cancel.gif");
    cancelB.setIcon(ss);
    panel1.setLayout(xYLayout1);
    jPanel2.setLayout(xYLayout2);
    xYLayout1.setHeight(215);
    xYLayout1.setWidth(400);
    setResizable(false);
    okB.setText("OK");
    okB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });
    cancelB.setText("Cancel");
    cancelB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });
    jLabel1.setText("Part Num:");
    jLabel3.setText("Description:");
    jLabel5.setText("Item ID:");
    jLabel7.setText("Delivery Date:");
    jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel2.setText("Quantity:");
    dateL.setToolTipText("\"MM/DD/YYYY format\"");
    getContentPane().add(panel1);
    panel1.add(jPanel1, new XYConstraints(10, 160, 380, 45));
    jPanel1.add(okB, null);
    jPanel1.add(cancelB, null);
    panel1.add(jPanel2, new XYConstraints(10, 10, 380, 140));
    jPanel2.add(jLabel1, new XYConstraints(47, 23, -1, -1));
    jPanel2.add(partNumL, new XYConstraints(108, 23, 150, 19));
    jPanel2.add(jLabel3, new XYConstraints(36, 46, -1, -1));
    jPanel2.add(descL, new XYConstraints(108, 46, 246, 19));
    jPanel2.add(jLabel5, new XYConstraints(263, 23, -1, -1));
    jPanel2.add(itemL, new XYConstraints(311, 23, 60, 19));
    jPanel2.add(jLabel7, new XYConstraints(27, 71, -1, -1));
    jPanel2.add(dateL, new XYConstraints(108, 69, 110, 19));
    jPanel2.add(qtyL, new XYConstraints(108, 96, 60, 19));
    jPanel2.add(jLabel2, new XYConstraints(35, 104, 56, 9));
  }
  public boolean isCanceled(){
    return canceled;
  }
  public Calendar getDay()throws Exception{
    SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
    Calendar c = Calendar.getInstance();
    try{
      c.setTime(df.parse(dateL.getText()));
    }catch(Exception e){throw e;}
    return c;
  }
  public BigDecimal getQty(){
    return new BigDecimal(qtyL.getText());
  }

  boolean auditInput(){
    // if not valid date return false
    try{
      SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
      df.parse(dateL.getText());
      Calendar c = getDay();
      int y = c.get(Calendar.YEAR);
      if(y < 1900){
        y = 5/0; // force an exception
      }
      if(c.before(firstActiveDay)){
        String s;
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        String monthName = months[firstActiveDay.get(Calendar.MONTH)];
        s = monthName +" "+firstActiveDay.get(Calendar.DAY_OF_MONTH) + ", "+firstActiveDay.get(Calendar.YEAR);
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg("You may not schedule a delivery before "+s+".");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
        return false;
      }
      if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
         c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg("You may not schedule a delivery on a weekend.");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
        return false;
      }
    }catch(Exception e){
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg("Please enter the date in mm/dd/yyyy format.");
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
      return false;
    }

    try{
      //BigDecimal test = new BigDecimal(qtyL.getText());
      int test = Integer.parseInt(qtyL.getText());
    }catch (Exception e){
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg("Please enter a valid number for the quantity.");
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
      return false;
    }
    return true;
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if(!auditInput())return;
    canceled = false;
    setVisible(false);
  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    dateL.setText("");
    qtyL.setText("0");
    setVisible(false);
  }
}

