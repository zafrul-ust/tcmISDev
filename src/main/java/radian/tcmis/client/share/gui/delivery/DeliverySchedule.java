//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui.delivery;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.math.BigDecimal;
import java.util.*;
import java.text.*;
import com.borland.jbcl.layout.*;
import com.borland.jbcl.layout.XYLayout;
import java.awt.event.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.client.share.gui.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class DeliverySchedule extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JPanel qtyP = new JPanel();
  JPanel calendarP = new JPanel();
  JPanel butonP = new JPanel();
  JButton okB = new JButton();
  JButton cancelB = new JButton();
  StaticJLabel jLabel1 = new StaticJLabel();
  DataJLabel dateL = new DataJLabel();
  JComboBox qtyC = new JComboBox();
  XYLayout xYLayout2 = new XYLayout();
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel2 = new JPanel();
  JPanel jPanel3 = new JPanel();
  JButton prevB = new JButton();
  DataJLabel monthYearL = new DataJLabel();
  JButton nextB = new JButton();
  FlowLayout flowLayout1 = new FlowLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel4 = new JPanel();
  GridLayout gridLayout1 = new GridLayout(1, 7);
  Calendar now;

  JPanel sundayP = new JPanel();
  JPanel mondayP = new JPanel();
  JPanel tuesdayP = new JPanel();
  JPanel wednesdayP = new JPanel();
  JPanel thursdayP = new JPanel();
  JPanel fridayP = new JPanel();
  JPanel saturdayP = new JPanel();

  StaticJLabel jLabel2 = new StaticJLabel();
  StaticJLabel jLabel3 = new StaticJLabel();
  StaticJLabel jLabel4 = new StaticJLabel();
  StaticJLabel jLabel5 = new StaticJLabel();
  StaticJLabel jLabel6 = new StaticJLabel();
  StaticJLabel jLabel7 = new StaticJLabel();
  StaticJLabel jLabel8 = new StaticJLabel();
  JPanel gridP = new JPanel();
  CmisApp cmis;
  GridLayout gridLayout2 = new GridLayout(6, 7);
  BorderLayout borderLayout3 = new BorderLayout();
  JPanel jPanel5 = new JPanel();
  JPanel jPanel6 = new JPanel();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JButton summaryB = new JButton();
  JButton freqB = new JButton();
  DataJLabel descL = new DataJLabel();
  DataJLabel partL = new DataJLabel();
  DataJLabel mrL = new DataJLabel();
  DataJLabel statusL = new DataJLabel();
  DataJLabel typeL = new DataJLabel();
  DataJLabel itemL = new DataJLabel();
  DataJLabel qtyOrderedL = new DataJLabel();
  DataJLabel qtyScheduledL = new DataJLabel();
  StaticJLabel jLabel15 = new StaticJLabel();
  StaticJLabel jLabel17 = new StaticJLabel();
  StaticJLabel jLabel18 = new StaticJLabel();
  StaticJLabel jLabel20 = new StaticJLabel();
  StaticJLabel jLabel21 = new StaticJLabel();
  StaticJLabel jLabel23 = new StaticJLabel();
  StaticJLabel jLabel12 = new StaticJLabel();
  StaticJLabel jLabel13 = new StaticJLabel();

  Vector delInfo; // will be a vector of hashtables with keys DATE and QTY
//  int qty = 0;
  BigDecimal qty;
  String mrNum;
  String lineNum;
  String partNum;
  String itemDesc;
  String status;
  String type;
  String itemId;
  Calendar cal;
  Calendar firstActiveDay;
  DeliveryDay[] deliveryDay;
  DeliveryDay selectedDay;
  boolean submitted = false;
  boolean updatingQtyCombo = false;
  boolean qtyChanged = false;
  // default to canceled until the OK button is clicked
  boolean canceled = true;
  boolean viewOnly = false;
  XYLayout xYLayout3 = new XYLayout();

  boolean allowQtyChange = false;
  boolean weekend = false;

  public DeliverySchedule(CmisApp cmis,
                          Vector delInfo,
                          BigDecimal qty,
                          String mrNum,
                          String partNum,
                          String itemDesc,
                          String status,
                          String type,
                          String itemId,
                          Calendar now,
                          boolean submitted,
                          String lineNum) {
    super(cmis.getMain(), "Delivery Schedule", true);
    this.cmis = cmis;
    this.delInfo = (Vector) delInfo.clone();
    this.qty = qty;
    this.mrNum = mrNum;
    this.partNum = partNum;
    this.itemDesc = itemDesc;
    this.status = status;
    this.type = type;
    this.itemId = itemId;
    this.now = now;
    this.submitted = submitted;
    this.lineNum = lineNum;
    try {
      jbInit();
      pack();
      //1-27-03
      if (submitted) {
        freqB.setEnabled(false);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setWeekend(boolean b) {
    weekend = b;
  }

  public void setAllowQtyChange(boolean b) {
    allowQtyChange = b;
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
    //ss = ResourceLoader.getImageIcon("images/button/left.gif");
    //prevB.setIcon(ss);
    //ss = ResourceLoader.getImageIcon("images/button/right.gif");
    //nextB.setIcon(ss);
    setResizable(false);

    Vector v = new Vector();
//    for(int i=0;i<=qty;i++){
    v.addElement(qty.toString());
//    }
    qtyC = new JComboBox(v);
    qtyC.setEditable(true);

    panel1.setLayout(xYLayout1);
    xYLayout1.setHeight(524);
    xYLayout1.setWidth(450);
    updateQtyScheduled();
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
    jLabel1.setText("Quantity to deliver on");
    dateL.setHorizontalAlignment(SwingConstants.CENTER);
    qtyC.setMaximumSize(new Dimension(80, 24));
    qtyC.setMinimumSize(new Dimension(80, 24));
    qtyC.setPreferredSize(new Dimension(80, 24));
    qtyC.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        qtyC_actionPerformed(e);
      }
    });
    qtyP.setLayout(xYLayout2);
    calendarP.setLayout(borderLayout1);
    jPanel2.setMaximumSize(new Dimension(430, 45));
    jPanel2.setMinimumSize(new Dimension(430, 45));
    jPanel2.setPreferredSize(new Dimension(430, 45));
    jPanel2.setLayout(flowLayout1);
    prevB.setText("Previous");
    prevB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        prevB_actionPerformed(e);
      }
    });
    monthYearL.setMaximumSize(new Dimension(200, 17));
    monthYearL.setMinimumSize(new Dimension(200, 17));
    monthYearL.setPreferredSize(new Dimension(200, 17));
    monthYearL.setHorizontalAlignment(SwingConstants.CENTER);
    monthYearL.setText("jLabel2");
    nextB.setMaximumSize(new Dimension(83, 27));
    nextB.setMinimumSize(new Dimension(83, 27));
    nextB.setPreferredSize(new Dimension(83, 27));
    nextB.setText("Next");
    nextB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        nextB_actionPerformed(e);
      }
    });
    jPanel3.setLayout(borderLayout2);
    jPanel1.setLayout(borderLayout3);
    jPanel4.setMaximumSize(new Dimension(430, 20));
    jPanel4.setMinimumSize(new Dimension(430, 20));
    jPanel4.setPreferredSize(new Dimension(430, 20));
    jPanel4.setLayout(gridLayout1);

    sundayP.setLayout(new BorderLayout());
    mondayP.setLayout(new BorderLayout());
    tuesdayP.setLayout(new BorderLayout());
    wednesdayP.setLayout(new BorderLayout());
    thursdayP.setLayout(new BorderLayout());
    fridayP.setLayout(new BorderLayout());
    saturdayP.setLayout(new BorderLayout());
    jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel2.setText("Sunday");
    sundayP.add(jLabel2, BorderLayout.CENTER);
    jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel3.setText("Saturday");
    saturdayP.add(jLabel3, BorderLayout.CENTER);
    jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel4.setText("Monday");
    mondayP.add(jLabel4, BorderLayout.CENTER);
    jLabel5.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel5.setText("Friday");
    fridayP.add(jLabel5, BorderLayout.CENTER);
    jLabel6.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel6.setText("Thursday");
    thursdayP.add(jLabel6, BorderLayout.CENTER);
    jLabel7.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel7.setText("Tuesday");
    tuesdayP.add(jLabel7, BorderLayout.CENTER);
    jLabel8.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel8.setText("Wednesday");
    wednesdayP.add(jLabel8, BorderLayout.CENTER);

    sundayP.setBorder(new BevelBorder(BevelBorder.RAISED));
    mondayP.setBorder(new BevelBorder(BevelBorder.RAISED));
    tuesdayP.setBorder(new BevelBorder(BevelBorder.RAISED));
    wednesdayP.setBorder(new BevelBorder(BevelBorder.RAISED));
    thursdayP.setBorder(new BevelBorder(BevelBorder.RAISED));
    fridayP.setBorder(new BevelBorder(BevelBorder.RAISED));
    saturdayP.setBorder(new BevelBorder(BevelBorder.RAISED));

    gridP.setLayout(gridLayout2);
    jPanel5.setMaximumSize(new Dimension(110, 120));
    jPanel5.setMinimumSize(new Dimension(110, 120));
    jPanel5.setPreferredSize(new Dimension(110, 120));
    jPanel5.setLayout(verticalFlowLayout1);
    jPanel6.setLayout(xYLayout3);
    summaryB.setText("Summary");
    summaryB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        summaryB_actionPerformed(e);
      }
    });
    freqB.setText("Frequency");
    freqB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        freqB_actionPerformed(e);
      }
    });
    jLabel12.setText("Request #:");
    jLabel13.setText("Status:");
    jLabel15.setText("Type:");
    jLabel17.setText("Part #:");
    jLabel18.setText("Item ID:");
    jLabel20.setText("Desc:");
    jLabel21.setText("Qty Ordered:");
    jLabel23.setText("Qty Scheduled:");
    getContentPane().add(panel1);

    setFirstActiveDay();
    setCalendar();
    buildGrid();
    //buildCenterSection();
    setSelectedDay(firstActiveDay);

    panel1.add(qtyP, new XYConstraints(10, 130, 430, 35));
    qtyP.add(jLabel1, new XYConstraints(60, 12, -1, -1));
    qtyP.add(dateL, new XYConstraints(173, 12, -1, -1));
    qtyP.add(qtyC, new XYConstraints(290, 9, -1, -1));
    panel1.add(calendarP, new XYConstraints(10, 165, 430, 315));
    calendarP.add(jPanel3, BorderLayout.CENTER);
    jPanel3.add(jPanel4, BorderLayout.NORTH);
    jPanel4.add(sundayP, null);
    jPanel4.add(mondayP, null);
    jPanel4.add(tuesdayP, null);
    jPanel4.add(wednesdayP, null);
    jPanel4.add(thursdayP, null);
    jPanel4.add(fridayP, null);
    jPanel4.add(saturdayP, null);

    setLabels();
    jPanel3.add(gridP, BorderLayout.CENTER);
    calendarP.add(jPanel2, BorderLayout.NORTH);
    jPanel2.add(prevB, null);
    jPanel2.add(monthYearL, null);
    jPanel2.add(nextB, null);
    panel1.add(butonP, new XYConstraints(10, 482, 430, 39));
    butonP.add(okB, null);
    butonP.add(cancelB, null);
    panel1.add(jPanel1, new XYConstraints(10, 10, 430, 120));
    jPanel1.add(jPanel5, BorderLayout.EAST);
    jPanel5.add(summaryB, null);
    jPanel5.add(freqB, null);
    jPanel1.add(jPanel6, BorderLayout.CENTER);
    jPanel6.add(mrL, new XYConstraints(58, 13, -1, -1));
    jPanel6.add(jLabel12, new XYConstraints(2, 14, -1, -1));
    jPanel6.add(jLabel13, new XYConstraints(118, 14, -1, -1));
    jPanel6.add(statusL, new XYConstraints(153, 13, -1, -1));
    jPanel6.add(jLabel15, new XYConstraints(245, 14, -1, -1));
    jPanel6.add(typeL, new XYConstraints(275, 13, -1, -1));
    jPanel6.add(jLabel17, new XYConstraints(21, 37, -1, -1));
    jPanel6.add(jLabel18, new XYConstraints(235, 37, -1, -1));
    jPanel6.add(itemL, new XYConstraints(275, 36, -1, -1));
    jPanel6.add(jLabel20, new XYConstraints(27, 60, -1, -1));
    jPanel6.add(jLabel21, new XYConstraints(37, 90, -1, -1));
    jPanel6.add(qtyOrderedL, new XYConstraints(103, 89, -1, -1));
    jPanel6.add(qtyScheduledL, new XYConstraints(275, 89, -1, -1));
    jPanel6.add(jLabel23, new XYConstraints(199, 90, -1, -1));
    jPanel6.add(partL, new XYConstraints(58, 36, -1, -1));
    jPanel6.add(descL, new XYConstraints(58, 59, -1, -1));
  }

  boolean storeSchedule() {
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.
        MATERIAL_REQUEST, cmis);
    Hashtable query = new Hashtable();
    query.put("USER_ID", cmis.getUserId());
    query.put("FUNCTION", "SAVE_DELIVERY_SCHEDULE");
    query.put("REQ_NUM", (new Integer(mrNum))); //String, String
    query.put("LINE_NUM", lineNum);
    query.put("DELIVERY_SCHEDULE", delInfo);
    if (allowQtyChange) {
      query.put("QTY", qty);
    }
    Hashtable result = cgi.getResultHash(query);
    if (result == null) {
      GenericDlg.showAccessDenied(cmis.getMain());
      return false;
    }
    if (result.containsKey("RETURN_CODE")) {
      return ( (Boolean) result.get("RETURN_CODE")).booleanValue();
    }
    return false;
  }

  void setLabels() {
    descL.setText(itemDesc);
    partL.setText(partNum);
    mrL.setText(mrNum);
    statusL.setText(status);
    typeL.setText(type);
    itemL.setText(itemId);
    qtyOrderedL.setText(String.valueOf(getQty()));
  }

  BigDecimal getQtyScheduled() {
    BigDecimal qty = new BigDecimal("0");
    String lastCombineRow = "";
    for (int i = 0; i < delInfo.size(); i++) {
      Hashtable h = (Hashtable) delInfo.elementAt(i);
      BigDecimal I = new BigDecimal("0");
      //combine row
      if (h.containsKey("COMBINE_ROW")) {
        String currentCombineRow = (String) h.get("COMBINE_ROW");
        if (lastCombineRow.equalsIgnoreCase(currentCombineRow)) { //ship to next row if it is a combine row
          lastCombineRow = currentCombineRow;
          continue;
        }
        else {
          lastCombineRow = currentCombineRow;
        }
      }
      if (h.containsKey("REVISED_QTY")) {
        String tmp = h.get("REVISED_QTY").toString();
        if (tmp.length() > 0) {
          I = new BigDecimal(h.get("REVISED_QTY").toString());
        }
        else {
          I = new BigDecimal(h.get("QTY").toString());
        }
      }
      else {
        I = new BigDecimal(h.get("QTY").toString());
      }
      qty = qty.add(I);
    }
    return qty;
  }

  public Vector getDeliverySchedule() {
    return delInfo;
  }

  public boolean qtyChanged() {
    return qtyChanged;
  }

  public boolean canceled() {
    return canceled;
  }

  public BigDecimal getQty() {
    return qty;
  }

  void buildCenterSection() {
    Calendar c = null;
    if (selectedDay == null) {
      c = (Calendar) cal.clone();
    }
    else {
      c = selectedDay.getCalendar();
    }
    DateFormatSymbols dfs = new DateFormatSymbols();
    String[] months = dfs.getMonths();
    String monthName = months[c.get(Calendar.MONTH)];
    dateL.setText(monthName + " " + c.get(Calendar.DAY_OF_MONTH) + ", " +
                  c.get(Calendar.YEAR));
    updatingQtyCombo = true;
    if (selectedDay == null) {
      qtyC.setSelectedItem("0");
    }
    else {
      qtyC.setSelectedItem(String.valueOf(selectedDay.getQty()));
    }
    updatingQtyCombo = false;
  }

  void buildGrid() {
    Calendar myCal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
//System.out.println("year:"+year+" month:"+month);
    myCal.set(year, month, 1);
    int leading = 0;
    switch (myCal.get(Calendar.DAY_OF_WEEK)) {
      case Calendar.SUNDAY:
        leading = 0;
        break;
      case Calendar.MONDAY:
        leading = 1;
        break;
      case Calendar.TUESDAY:
        leading = 2;
        break;
      case Calendar.WEDNESDAY:
        leading = 3;
        break;
      case Calendar.THURSDAY:
        leading = 4;
        break;
      case Calendar.FRIDAY:
        leading = 5;
        break;
      case Calendar.SATURDAY:
        leading = 6;
        break;
      default:
    }
    deliveryDay = new DeliveryDay[42];
    int ctr = 0;
    for (int i = 0; i < leading; i++) {
      deliveryDay[ctr++] = new DeliveryDay(this,weekend);
    }
    for (int i = ctr; i < 42; i++) {
      if (myCal.get(Calendar.MONTH) == month) {
        Calendar cx = Calendar.getInstance();
        cx.set(myCal.get(Calendar.YEAR), myCal.get(Calendar.MONTH),
               myCal.get(Calendar.DAY_OF_MONTH));
        //same month
        deliveryDay[ctr++] = new DeliveryDay(this, cx);
        if (submitted) {
          deliveryDay[ctr - 1].setDelivered(getDelivered(cx));
        }
      }
      else {
        // different Month
        deliveryDay[ctr++] = new DeliveryDay(this,weekend);
      }
      myCal.add(Calendar.DATE, 1);
    }

    gridP.setVisible(false);
    gridP.removeAll();
    for (int i = 0; i < 42; i++) {
      gridP.add(deliveryDay[i]);
    }
    gridP.setVisible(true);
    for (int i = delInfo.size(); i > 0; i--) {
      Hashtable h = (Hashtable) delInfo.elementAt(i - 1);
      Calendar c = (Calendar) h.get("DATE");
      if (c.get(Calendar.MONTH) == month &&
          c.get(Calendar.YEAR) == year) {
        if (submitted) {
          if (h.containsKey("REVISED_QTY")) {
            String tmp = h.get("REVISED_QTY").toString();
            if (tmp.length() > 0) {
              this.setQty(c.get(Calendar.DAY_OF_MONTH),
                          new BigDecimal(h.get("REVISED_QTY").toString()));
            }
            else {
              this.setQty(c.get(Calendar.DAY_OF_MONTH),
                          new BigDecimal(h.get("QTY").toString()));
            }
          }
          else {
            this.setQty(c.get(Calendar.DAY_OF_MONTH),
                        new BigDecimal(h.get("QTY").toString()));
          }
        }
        else {
          this.setQty(c.get(Calendar.DAY_OF_MONTH),
                      new BigDecimal(h.get("QTY").toString()));
        }
      }
    }

    DateFormatSymbols dfs = new DateFormatSymbols();
    String[] months = dfs.getMonths();
    String monthName = months[month];
    monthYearL.setText(monthName + ", " + year);
  }

  public void setViewOnly(boolean b) {
    viewOnly = b;
    qtyC.setEnabled(!b);
    freqB.setEnabled(!b);
  }

  public boolean isCanceled() {
    return canceled;
  }

  private boolean getDelivered(Calendar c) {
    for (int i = 0; i < delInfo.size(); i++) {
      Hashtable h = (Hashtable) delInfo.elementAt(i);
      if (!h.containsKey("STATUS")) {
        continue;
      }
      if (!h.get("STATUS").toString().equalsIgnoreCase("delivered")) {
        continue;
      }
      Calendar x = (Calendar) h.get("DATE");
      if (BothHelpObjs.sameDay(x, c)) {
        return true;
      }
    }
    return false;
  }

  void setFirstActiveDay() {
    Calendar c = (Calendar) now.clone();
    if (submitted) {
      if (c.get(Calendar.HOUR_OF_DAY) < 13) {
        c.add(Calendar.DATE, 1);
      }
      else {
        c.add(Calendar.DATE, 2);
      }
    }
    else {
      if (BothHelpObjs.isMinMax(type)) {
        if (c.get(Calendar.HOUR_OF_DAY) < 13) {
          c.add(Calendar.DATE, 1);
        }
        else {
          c.add(Calendar.DATE, 2);
        }
      }
      else {
        c.add(Calendar.DATE, 21);
      }
    }
    if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
      c.add(Calendar.DATE, 2);
    }
    if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
      c.add(Calendar.DATE, 1);
    }
    firstActiveDay = c;
  }

  public static BigDecimal getQtyForSchedule(Vector v) {
    BigDecimal q = new BigDecimal("0");
    for (int i = 0; i < v.size(); i++) {
      Hashtable h = (Hashtable) v.elementAt(i);
      if (h.containsKey("REVISED_QTY")) {
        if (h.get("REVISED_QTY").toString().length() > 0) {
          q = q.add(new BigDecimal(h.get("REVISED_QTY").toString()));
        }
        else {
          q = q.add(new BigDecimal(h.get("QTY").toString()));
        }
      }
      else {
        q = q.add(new BigDecimal(h.get("QTY").toString()));
      }
    }
    return q;
  }

  void setCalendar() {
    // this routine sets the starting point
    Calendar now = (Calendar)this.now.clone();
    Calendar c = (Calendar)this.now.clone();

    // New schedule, start at the earliest delivery day,
    // 1 day for MM, 21 days for OOR
    if (delInfo == null || delInfo.size() < 1) {
      if (BothHelpObjs.isMinMax(type)) {
        if (c.get(Calendar.HOUR_OF_DAY) < 13) {
          c.add(Calendar.DATE, 1);
        }
        else {
          c.add(Calendar.DATE, 2);
        }
      }
      else {
        c.add(Calendar.DATE, 21);
      }
      if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
        c.add(Calendar.DATE, 2);
      }
      if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
        c.add(Calendar.DATE, 1);
      }
      cal = c;
      return;
    }

    //existing schedule
    Calendar next = null;
    Calendar lastDelDate = null;
    for (int i = 0; i < delInfo.size(); i++) {
      Hashtable h = (Hashtable) delInfo.elementAt(i);
      Calendar q = (Calendar) h.get("DATE");
      if (q.before(now)) {
        // q is in the past
        if (lastDelDate == null || lastDelDate.before(q)) {
          lastDelDate = q;
        }
      }
      else {
        // q is in the future
        if (next == null || q.before(next)) {
          next = q;
        }
      }
    }
    if (next == null && lastDelDate == null) {
      cal = (Calendar) c.clone();
    }
    else if (next == null) {
      cal = (Calendar) lastDelDate.clone();
    }
    else {
      cal = (Calendar) next.clone();
    }
  }

  void setSelectedDay(Calendar c) {
    int day = c.get(Calendar.DAY_OF_MONTH);
    for (int i = 0; i < 42; i++) {
      if (deliveryDay[i].getDay() == day) {
        setSelectedDay(deliveryDay[i]);
        break;
      }
    }
  }

  public void setSelectedDay(DeliveryDay dd) {
    if (selectedDay != null) {
      selectedDay.setSelected(false);
    }
    selectedDay = dd;
    dd.setSelected(true);
    buildCenterSection();
    if (selectedDay.getCalendar().before(firstActiveDay) || dd.getDelivered()) {
      qtyC.setEnabled(false);
    }
    else {
      qtyC.setEnabled(!viewOnly);
    }
  }

  void saveSchedule() {
    if (!submitted) {
      // erase entries for the current month
      int m = cal.get(Calendar.MONTH);
      int y = cal.get(Calendar.YEAR);
      for (int i = delInfo.size(); i > 0; i--) {
        Hashtable h = (Hashtable) delInfo.elementAt(i - 1);
        Calendar c = (Calendar) h.get("DATE");
        if (c.get(Calendar.MONTH) == m && c.get(Calendar.YEAR) == y) {
          delInfo.removeElementAt(i - 1);
        }
      }
    }
    for (int i = 0; i < deliveryDay.length; i++) {
      if (deliveryDay[i].getQty().intValue() == -1) {
        continue;
      }
      if (deliveryDay[i].getDelivered()) {
        continue;
      }
      Hashtable h = new Hashtable();
      if (submitted) {
        boolean foundIt = false;
        for (int j = 0; j < delInfo.size(); j++) {
          Hashtable h1 = (Hashtable) delInfo.elementAt(j);
          if (BothHelpObjs.sameDay(deliveryDay[i].getCalendar(),
                                   (Calendar) h1.get("DATE"))) {
            BigDecimal q = new BigDecimal(h1.get("QTY").toString());
            if (q.compareTo(deliveryDay[i].getQty()) == 0) {
              h1.remove("STATUS");
              h1.remove("REVISED_QTY");
              if (q.compareTo(new BigDecimal("0")) == 0) {
                delInfo.removeElementAt(j);
                foundIt = true;
                break;
              }
            }
            else {
              String status = "Changed";
              if (h1.get("QTY").equals(new BigDecimal("0"))) {
                status = "Added";
              }
              h1.put("STATUS", status);
              h1.put("REVISED_QTY", deliveryDay[i].getQty());
              h1.put("DELIVERED", "");
              h1.put("DELIVERED_QTY", "");
            }
            delInfo.setElementAt(h1, j);
            foundIt = true;
            break;
          }
        }
        if (!foundIt &&
            deliveryDay[i].getQty().compareTo(new BigDecimal("0")) > 0) {
          h.put("DATE", (Calendar) deliveryDay[i].getCalendar().clone());
          h.put("QTY", new BigDecimal(0));
          h.put("STATUS", "Changed");
          h.put("REVISED_QTY", deliveryDay[i].getQty());
          h.put("DELIVERED", "");
          h.put("DELIVERED_QTY", "");
          delInfo.addElement(h);
        }
      }
      else {
        if (deliveryDay[i].getQty().compareTo(new BigDecimal("0")) > 0) {
          h.put("DATE", deliveryDay[i].getCalendar());
          h.put("QTY", deliveryDay[i].getQty());
          delInfo.addElement(h);
        }
      }
    }
  }

  void goNewMonth() {
    buildGrid();
    //buildCenterSection();
    if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
      cal.add(Calendar.DATE, 2);
    }
    if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
      cal.add(Calendar.DATE, 1);
    }
    setSelectedDay(cal);
  }

  void setQty(int day, BigDecimal qty) {
    for (int i = 0; i < deliveryDay.length; i++) {
      if (deliveryDay[i].getDay() == day) {
        deliveryDay[i].setQty(qty);
        break;
      }
    }
  }

  void removeOldDeliveryDays() {
    for (int i = delInfo.size(); i > 0; i--) {
      Hashtable h = (Hashtable) delInfo.elementAt(i - 1);
      if (!h.containsKey("STATUS")) {
        continue;
      }
      if (h.get("STATUS").toString().equalsIgnoreCase("delivered")) {
        continue;
      }
      if (!h.containsKey("REVISED_QTY")) {
        continue;
      }
      BigDecimal rq = (BigDecimal) h.get("REVISED_QTY");
      BigDecimal q = (BigDecimal) h.get("QTY");
      if (rq.toString().length() > 0) {
        if (rq.intValue() < 1) {
          delInfo.removeElementAt(i - 1);
          continue;
        }
      }
      h.remove("STATUS");
      h.remove("REVISED_QTY");
      h.put("QTY", rq);
      delInfo.setElementAt(h, i - 1);
    }
  }

  public Calendar getFirstActiveDay() {
    return firstActiveDay;
  }

  void updateQtyScheduled() {
    qtyScheduledL.setText(String.valueOf(getQtyScheduled()));
  }

  public boolean auditForSubmit() {
    if (submitted) {
      if (getQtyScheduled().compareTo(qty) != 0) {
        GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
        gd.setMsg("The quantity scheduled must be equal to the quantity ordered.");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
        return false;
      }
    }
    else {
      BigDecimal b = getQtyScheduled();
      if (b.compareTo(qty) != 0) {
        String upDown = "";
        if (b.compareTo(qty) < 0) {
          upDown = "The quantity scheduled is less than the quantity ordered. Would you like to decrease the quantity ordered?";
        }
        else {
          upDown = "The quantity scheduled is more than the quantity ordered. Would you like to increase the quantity ordered?";
        }
        ConfirmDlg cd = new ConfirmDlg(cmis.getMain(), "Quantity Scheduled", true);
        cd.setMsg(upDown);
        CenterComponent.centerCompOnScreen(cd);
        cd.setVisible(true);
        if (cd.getConfirmationFlag()) {
          qty = b;
          qtyChanged = true;
        }
        else {
          return false;
        }
      }
    }
    return true;
  }

  void qtyC_actionPerformed(ActionEvent e) {
    if (updatingQtyCombo) {
      return;
    }
    try {
      if (BothHelpObjs.isBlankString(qtyC.getSelectedItem().toString())) {
        qtyC.setSelectedItem("0");
      }
      //BigDecimal i = new BigDecimal(qtyC.getSelectedItem().toString());
      int num = Integer.parseInt(qtyC.getSelectedItem().toString());
      //if (i.compareTo(new BigDecimal("0")) <= 0) {
      if (num < 0) {
        GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
        gd.setMsg("Quantity must be a number greater than or equal 0.");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
        return;
      }
    }catch (Exception ex) {
      //String q = String.valueOf(selectedDay.getQty());
      //updatingQtyCombo = true;
      //qtyC.setSelectedItem(q);
      //updatingQtyCombo = false;
      GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
      gd.setMsg("Quantity must be a number greater than or equal 0.");
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
      return;
    }
    selectedDay.setQty(new BigDecimal(qtyC.getSelectedItem().toString()));
    saveSchedule();
    updateQtyScheduled();
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    saveSchedule();
    if (auditForSubmit()) {
      if (submitted) {
        if (!storeSchedule()) {
          GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
          gd.setMsg(
              "The schedule changes were not stored. Please check your data and try again.");
          CenterComponent.centerCompOnScreen(gd);
          gd.setVisible(true);
          return;
        }
        GenericDlg gd = new GenericDlg(cmis.getMain(), "Schedule Stored", true);
        gd.setMsg("The schedule changes have been stored and\n forwarded to our hub and purchasing staff.\nWe will do our best to accommodate your request.\nIf there is a problem meeting your need, we wil contact you.");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
        removeOldDeliveryDays();
      }
      else if (allowQtyChange) {
        if (!storeSchedule()) {
          GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
          gd.setMsg(
              "The schedule changes were not stored. Please check your data and try again.");
          CenterComponent.centerCompOnScreen(gd);
          gd.setVisible(true);
          return;
        }
        GenericDlg gd = new GenericDlg(cmis.getMain(), "Information", true);
        gd.setMsg("The schedule changes stored.");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
      }
      canceled = false;
      setVisible(false);
    }
  }

  void summaryB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    saveSchedule();
    DeliverySummary ds = new DeliverySummary(cmis,
                                             delInfo,
                                             mrNum,
                                             status,
                                             type,
                                             partNum,
                                             itemId,
                                             itemDesc,
                                             qty,
                                             firstActiveDay,
                                             viewOnly,
                                             submitted);
    CenterComponent.centerCompOnScreen(ds);
    ds.setVisible(true);
    if (ds.isCanceled()) {
      return;
    }
    delInfo = ds.getSchedule();
    if (ds.getGotoAction()) {
      cal = ds.getGotoCalendar();
    }
    updateQtyScheduled();
    goNewMonth();
  }

  void freqB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    saveSchedule();
    DeliveryFrequency df = new DeliveryFrequency(cmis,
                                                 partL.getText(),
                                                 itemL.getText(),
                                                 descL.getText(),
                                                 firstActiveDay,
                                                 new BigDecimal(qtyOrderedL.getText()));
    if (selectedDay != null) {
      df.setDefaultDate(selectedDay.getCalendar());
      if (selectedDay.getQty().compareTo(new BigDecimal("0")) > 0) {
        df.setDefaultQty(selectedDay.getQty());
      }
    }
    CenterComponent.centerCompOnScreen(df);
    df.setVisible(true);
    if (df.isCanceled()) {
      return;
    }
    if (submitted) {
      // frequency when submitted
      try {
        //default is no delivery on weekend
        Vector v = df.getSchedule(true, true, true, true, true, false, false);
        String lastCombineRow = "";
        for (int i = 0; i < delInfo.size(); i++) {
          Hashtable h = (Hashtable) delInfo.elementAt(i);
          //combine row
          if (h.containsKey("COMBINE_ROW")) {
            String currentCombineRow = (String) h.get("COMBINE_ROW");
            if (lastCombineRow.equalsIgnoreCase(currentCombineRow)) { //ship to next row if it is a combine row
              lastCombineRow = currentCombineRow;
              continue;
            }
            else {
              lastCombineRow = currentCombineRow;
            }
          }
          if (h.containsKey("STATUS") &&
              h.get("STATUS").toString().equalsIgnoreCase("delivered")) {
            // do nothing
          }
          else {
            h.put("STATUS", "Changed");
            h.put("REVISED_QTY", new BigDecimal(0));
            h.put("DELIVERED", "");
            h.put("DELIVERED_QTY", "");
            delInfo.setElementAt(h, i);
          }
        }
        lastCombineRow = "";
        for (int i = delInfo.size(); i > 0; i--) {
          Hashtable h = (Hashtable) delInfo.elementAt(i - 1);
          //combine row
          if (h.containsKey("COMBINE_ROW")) {
            String currentCombineRow = (String) h.get("COMBINE_ROW");
            if (lastCombineRow.equalsIgnoreCase(currentCombineRow)) { //ship to next row if it is a combine row
              lastCombineRow = currentCombineRow;
              continue;
            }
            else {
              lastCombineRow = currentCombineRow;
            }
          }
          if (h.containsKey("STATUS") &&
              h.get("STATUS").toString().equalsIgnoreCase("delivered")) {
            // do nothing
          }
          else {
            if (h.get("QTY").equals(h.get("REVISED_QTY"))) {
              delInfo.removeElementAt(i - 1);
            }
          }
        }
        for (int i = 0; i < v.size(); i++) {
          Hashtable h = (Hashtable) v.elementAt(i);
          Calendar c = (Calendar) h.get("DATE");

          boolean gotIt = false;
          lastCombineRow = "";
          for (int j = 0; j < delInfo.size(); j++) {
            Hashtable h1 = (Hashtable) delInfo.elementAt(j);
            //combine row
            if (h.containsKey("COMBINE_ROW")) {
              String currentCombineRow = (String) h.get("COMBINE_ROW");
              if (lastCombineRow.equalsIgnoreCase(currentCombineRow)) { //ship to next row if it is a combine row
                lastCombineRow = currentCombineRow;
                continue;
              }
              else {
                lastCombineRow = currentCombineRow;
              }
            }
            Calendar c1 = (Calendar) h1.get("DATE");
            if (BothHelpObjs.sameDay(c, c1)) {
              h1.put("REVISED_QTY", h.get("QTY"));
              h1.put("STATUS", "Changed");
              h1.put("DELIVERED", "");
              h1.put("DELIVERED_QTY", "");
              delInfo.setElementAt(h1, j);
              gotIt = true;
              break;
            }
          }
          if (!gotIt) {
            Hashtable h1 = new Hashtable();
            h1.put("DATE", h.get("DATE"));
            h1.put("STATUS", "Added");
            h1.put("REVISED_QTY", h.get("QTY"));
            h1.put("QTY", new BigDecimal(0));
            h1.put("DELIVERED", "");
            h1.put("DELIVERED_QTY", "");
            delInfo.addElement(h1);
          }
        }
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    else {
      try {
        //default is no delivery on weekend
        delInfo = df.getSchedule(true, true, true, true, true, false, false);
        //printDelInfo();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    updateQtyScheduled();
    goNewMonth();
  }

  void nextB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    saveSchedule();
    cal.add(Calendar.MONTH, 1);
    goNewMonth();
  }

  void prevB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    saveSchedule();
    cal.add(Calendar.MONTH, -1);
    goNewMonth();
  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    delInfo = null;
    setVisible(false);
  }

  void printDelInfo() {
    for (int i = 0; i < delInfo.size(); i++) {
      Hashtable h = (Hashtable) delInfo.elementAt(i);
      Calendar c = (Calendar) h.get("DATE");
      BigDecimal q = (BigDecimal) h.get("QTY");
      DateFormatSymbols dfs = new DateFormatSymbols();
      String[] months = dfs.getMonths();
      String monthName = months[c.get(Calendar.MONTH)];
      System.out.println("qty:" + q.toString() + "\t" + getCalendar(c));
    }
  }

  String getCalendar(Calendar c) {
    DateFormatSymbols dfs = new DateFormatSymbols();
    String[] months = dfs.getMonths();
    String monthName = months[c.get(Calendar.MONTH)];
    return monthName + " " + c.get(Calendar.DAY_OF_MONTH) + ", " +
        c.get(Calendar.YEAR);
  }

  void printDelVector(Vector v, String s) {
    System.out.println(s);
    for (int i = 0; i < v.size(); i++) {
      Hashtable h = (Hashtable) v.elementAt(i);
      String date = getCalendar( (Calendar) h.get("DATE"));
      String qty = h.get("QTY").toString();
      String status = "-";
      String rq = "-";
      if (h.containsKey("STATUS")) {
        status = h.get("STATUS").toString();
      }
      if (h.containsKey("REVISED_QTY")) {
        rq = h.get("REVISED_QTY").toString();
      }
      System.out.println("Date:" + date + "\tqty:" + qty + "\tstatus:" + status +
                         "\trevised qty:" + rq);
    }
  }
}
