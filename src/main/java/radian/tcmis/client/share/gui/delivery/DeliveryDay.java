
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui.delivery;

import java.awt.*;
import javax.swing.*;
import javax.swing.JPanel;
import java.math.BigDecimal;
import java.util.*;
import com.borland.jbcl.layout.*;
import javax.swing.border.*;
import java.awt.event.*;

public class DeliveryDay extends JPanel {
  DeliverySchedule ds;
  Calendar cal = null;
  BigDecimal qty = new BigDecimal("0");
  XYLayout xYLayout1 = new XYLayout();
  JLabel dateL = new JLabel();
  JLabel qtyL = new JLabel();

  final Color NORMAL = Color.lightGray;
  final Color SELECTED = Color.cyan;
  final Color WEEKEND = Color.darkGray;
  final Color OLD = Color.darkGray;
  final Color DELIVERED = Color.magenta;
  final Color SCHEDULED = Color.green;
  final Color CHANGED = Color.pink;

  boolean selected = false;
  boolean changed = false;
  boolean weekend = false;
  boolean old = false;
  boolean delivered = false;
  boolean scheduled = false;

  public DeliveryDay(DeliverySchedule ds, boolean weekend) {
    super();
    qty = new BigDecimal("-1");
    this.ds = ds;
    this.weekend = weekend;
    try  {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  public DeliveryDay() {
    super();
    try  {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  public DeliveryDay(DeliverySchedule ds, Calendar cal) {
    super();
    this.ds = ds;
    this.cal = cal;
    this.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        this_mouseClicked(e);
      }
    });
    try  {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  public void setSelected(boolean b){
    selected = b;
    setBackgroundColor();
  }
  public boolean getDelivered(){
    return delivered;
  }
  public void setAllowScheduleOnWeekend(boolean b) {
    weekend = b;
  }

  public void setQty(BigDecimal q){
    qty = q;
    setQtyLabel();
    setBackgroundColor();
  }
  public Calendar getCalendar(){
    return (Calendar)cal.clone();
  }
  public BigDecimal getQty(){
    return qty;
  }
  private void setQtyLabel(){
    if(qty.compareTo(new BigDecimal("0")) > 0){
      if(delivered){
        qtyL.setText("Delivered: "+qty);
      }else{
        qtyL.setText("Qty: "+qty);
      }
    }else{
      qtyL.setText("");
    }
  }
  public int getDay(){
    if(cal == null)return 0;
    return cal.get(Calendar.DAY_OF_MONTH);
  }

  private void jbInit() throws Exception {
    dateL.setText("");
    setQtyLabel();
    if(cal != null)dateL.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
    //this.setMaximumSize(new Dimension(61, 42));
    //this.setMinimumSize(new Dimension(61, 42));
    //this.setPreferredSize(new Dimension(61, 42));
    this.setBorder(new BevelBorder(BevelBorder.RAISED));
    this.setLayout(xYLayout1);
    this.add(dateL, new XYConstraints(3, 3, 20, 16));
    this.add(qtyL, new XYConstraints(2, 24, 61, 12));
    setBackgroundColor();
  }
  public void setDelivered(boolean b){
    delivered = b;
    setQtyLabel();
    setBackgroundColor();
  }

  private void setBackgroundColor(){
    setProperties();
    if(cal == null){
      this.setBackground(NORMAL);
      return;
    }
    Color c = null;
    if(selected){
      c = SELECTED;
    }else if(changed){
      c = CHANGED;
    }else if(delivered){
       c = DELIVERED;
    }else if(scheduled){
      c = SCHEDULED;
    }else if(old){
      c = OLD;
    }else if(weekend){
      c = WEEKEND;
    }else{
      c = NORMAL;
    }
    this.setBackground(c);
    dateL.setBackground(c);
    qtyL.setBackground(c);
  }
  private void setProperties(){
    if(cal == null)return;
    Calendar myCal = ds.getFirstActiveDay();
    old = cal.before(myCal);
    scheduled = qty.compareTo(new BigDecimal("0")) > 0;
    weekend = (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
               cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
  }

  void this_mouseClicked(MouseEvent e){
    if(weekend)return;
    ds.setSelectedDay(this);
  }
}
