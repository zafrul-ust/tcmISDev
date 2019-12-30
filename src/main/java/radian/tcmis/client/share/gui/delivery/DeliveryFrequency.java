//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui.delivery;

import java.awt.*;
import javax.swing.*;
import java.math.BigDecimal;
import java.util.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.client.share.gui.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import java.text.*;
import java.awt.event.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
import radian.tcmis.client.share.helpers.CalendarHandler;

public class DeliveryFrequency
    extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JButton okB = new JButton();
  JButton cancelB = new JButton();
  StaticJLabel jLabel1 = new StaticJLabel();
  StaticJLabel jLabel3 = new StaticJLabel();
  StaticJLabel jLabel5 = new StaticJLabel();
  DataJLabel partNumL = new DataJLabel();
  DataJLabel descL = new DataJLabel();
  DataJLabel itemL = new DataJLabel();
  XYLayout xYLayout2 = new XYLayout();
  Calendar firstActiveDay;

  CmisApp cmis;
  //boolean canScheduleWeekend = false;
  boolean canceled = true;
  JPanel jPanel3 = new JPanel();
  StaticJLabel jLabel2 = new StaticJLabel();
  StaticJLabel jLabel4 = new StaticJLabel();
  StaticJLabel jLabel7 = new StaticJLabel();
  StaticJLabel daysL = new StaticJLabel();
  JTextField qtyT = new JTextField();
  JTextField dateL = new JTextField();
  JRadioButton monthlyB = new JRadioButton();
  JRadioButton dailyB = new JRadioButton();
  JTextField dayT = new JTextField();
  XYLayout xYLayout3 = new XYLayout();
  StaticJLabel everyL = new StaticJLabel();
  ButtonGroup bg = new ButtonGroup();
  JTextField totalT = new JTextField();
  StaticJLabel jLabel6 = new StaticJLabel();

  public DeliveryFrequency(CmisApp cmis,
                           String partNum,
                           String itemId,
                           String desc,
                           Calendar firstActiveDay,
                           BigDecimal total) {
    super(cmis.getMain(), "Delivery", true);
    try {
      this.cmis = cmis;
      this.firstActiveDay = firstActiveDay;
      jbInit();
      partNumL.setText(partNum);
      itemL.setText(itemId);
      descL.setText(desc);
      totalT.setText(String.valueOf(total));
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setDefaultDate(Calendar c) {
    int day = c.get(Calendar.DAY_OF_MONTH);
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH) + 1;
    String s = String.valueOf(month) + "/" + String.valueOf(day) + "/" +
        String.valueOf(year);
    dateL.setText(s);
  }

  public void setDefaultQty(BigDecimal i) {
    qtyT.setText(String.valueOf(i));
    totalT.setText(String.valueOf(i));
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
    xYLayout1.setHeight(322);
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
    jPanel3.setLayout(xYLayout3);
    jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel2.setText("Quantity:");
    jLabel4.setText("Frequency:");
    jLabel7.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel7.setText("Starting Date:");
    qtyT.setMaximumSize(new Dimension(63, 21));
    qtyT.setMinimumSize(new Dimension(63, 21));
    dateL.setMaximumSize(new Dimension(90, 21));
    dateL.setMinimumSize(new Dimension(90, 21));
    dateL.setPreferredSize(new Dimension(90, 21));
    monthlyB.setText("Monthly");
    monthlyB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        monthlyB_actionPerformed(e);
      }
    });
    dailyB.setText(" Daily -");
    dailyB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dailyB_actionPerformed(e);
      }
    });
    totalT.setMaximumSize(new Dimension(90, 21));
    totalT.setMinimumSize(new Dimension(90, 21));
    totalT.setPreferredSize(new Dimension(90, 21));
    jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel6.setText("Total to Deliver:");
    bg.add(monthlyB);
    bg.add(dailyB);
    monthlyB.setSelected(true);
    dayT.setEnabled(false);
    daysL.setText("days");
    everyL.setText("every");
    jPanel3.setBorder(BorderFactory.createEtchedBorder());
    getContentPane().add(panel1);
    panel1.add(jPanel2, new XYConstraints(10, 10, 380, 63));
    jPanel2.add(partNumL, new XYConstraints(101, 15, 150, 19));
    jPanel2.add(jLabel1, new XYConstraints(40, 15, -1, -1));
    jPanel2.add(jLabel5, new XYConstraints(256, 15, -1, -1));
    jPanel2.add(itemL, new XYConstraints(304, 15, 60, 19));
    jPanel2.add(jLabel3, new XYConstraints(29, 38, -1, -1));
    jPanel2.add(descL, new XYConstraints(101, 38, 246, 19));
    panel1.add(jPanel3, new XYConstraints(10, 75, 381, 191));
    jPanel3.add(monthlyB, new XYConstraints(162, 46, -1, -1));
    jPanel3.add(jLabel2, new XYConstraints(109, 14, -1, -1));
    jPanel3.add(qtyT, new XYConstraints(162, 12, 63, -1));
    jPanel3.add(jLabel4, new XYConstraints(100, 65, -1, -1));
    jPanel3.add(dailyB, new XYConstraints(162, 77, -1, -1));
    jPanel3.add(everyL, new XYConstraints(224, 82, -1, -1));
    jPanel3.add(dayT, new XYConstraints(258, 80, 63, -1));
    jPanel3.add(daysL, new XYConstraints(324, 82, -1, -1));
    jPanel3.add(jLabel7, new XYConstraints(87, 117, -1, -1));
    jPanel3.add(dateL, new XYConstraints(162, 115, -1, -1));
    jPanel3.add(totalT, new XYConstraints(162, 156, -1, -1));
    jPanel3.add(jLabel6, new XYConstraints(65, 156, 87, 18));
    panel1.add(jPanel1, new XYConstraints(10, 270, 380, 44));
    jPanel1.add(okB, null);
    jPanel1.add(cancelB, null);
    setDayLabels();
  }

  public boolean isCanceled() {
    return canceled;
  }

  public Vector getSchedule(boolean monday,
                            boolean tuesday,
                            boolean wednesday,
                            boolean thursday,
                            boolean friday,
                            boolean saturday,
                            boolean sunday) throws Exception {
    Vector v = new Vector();
    if (canceled) {
      return v;
    }
    try {
      // get basic info...
      Calendar c = getStartingDate();
      BigDecimal q = new BigDecimal(qtyT.getText());
      BigDecimal total = new BigDecimal(totalT.getText());
      Calendar cx = (Calendar) c.clone();
      Calendar tempCalendar = null;
      // now set the schedule
      while (total.compareTo(new BigDecimal("0")) > 0) {
        BigDecimal myQ = new BigDecimal("0");
        //calculate amount
        if (total.compareTo(q) > 0) {
          myQ = q;
        }
        else {
          myQ = total;
        }
        total = total.subtract(myQ);

        //calculate schedule
        Hashtable h = new Hashtable();
        if (monthlyB.isSelected()) {
          tempCalendar = (Calendar) cx.clone();
          //set calendar to valid delivery day
          tempCalendar.add(Calendar.DATE,
                           - (CalendarHandler.
                              daysToPreviousAvailableDevliveryDay(cx.get(
              Calendar.DAY_OF_WEEK),
              monday,
              tuesday,
              wednesday,
              thursday,
              friday,
              saturday,
              sunday)));
          cx.add(Calendar.MONTH, 1);
        }
        else {
          int interval = Integer.parseInt(dayT.getText());
          //add first delivery day
          cx.add(Calendar.DATE,
                 CalendarHandler.daysToNextAvailableDeliveryDay(
                 cx.get(Calendar.DAY_OF_WEEK),
                 monday,
                 tuesday,
                 wednesday,
                 thursday,
                 friday,
                 saturday,
                 sunday));
          tempCalendar = (Calendar) cx.clone();
          //now calculate what days are delivery days and increment calendar
          for (int i = 0; i < interval; i++) {
            cx.add(Calendar.DATE, 1);
            if (!monday && cx.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
              cx.add(Calendar.DATE,
                     CalendarHandler.daysToNextAvailableDeliveryDay(Calendar.
                  MONDAY,
                  monday,
                  tuesday,
                  wednesday,
                  thursday,
                  friday,
                  saturday,
                  sunday));
            }
            else if (!tuesday &&
                     cx.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
              cx.add(Calendar.DATE,
                     CalendarHandler.daysToNextAvailableDeliveryDay(Calendar.
                  TUESDAY,
                  monday,
                  tuesday,
                  wednesday,
                  thursday,
                  friday,
                  saturday,
                  sunday));

            }
            else if (!wednesday &&
                     cx.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
              cx.add(Calendar.DATE,
                     CalendarHandler.daysToNextAvailableDeliveryDay(Calendar.
                  WEDNESDAY,
                  monday,
                  tuesday,
                  wednesday,
                  thursday,
                  friday,
                  saturday,
                  sunday));

            }
            else if (!thursday &&
                     cx.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
              cx.add(Calendar.DATE,
                     CalendarHandler.daysToNextAvailableDeliveryDay(Calendar.
                  THURSDAY,
                  monday,
                  tuesday,
                  wednesday,
                  thursday,
                  friday,
                  saturday,
                  sunday));

            }
            else if (!friday && cx.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
              cx.add(Calendar.DATE,
                     CalendarHandler.daysToNextAvailableDeliveryDay(Calendar.
                  FRIDAY,
                  monday,
                  tuesday,
                  wednesday,
                  thursday,
                  friday,
                  saturday,
                  sunday));

            }
            else if (!saturday &&
                     cx.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
              cx.add(Calendar.DATE,
                     CalendarHandler.daysToNextAvailableDeliveryDay(Calendar.
                  SATURDAY,
                  monday,
                  tuesday,
                  wednesday,
                  thursday,
                  friday,
                  saturday,
                  sunday));

            }
            else if (!sunday && cx.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
              cx.add(Calendar.DATE,
                     CalendarHandler.daysToNextAvailableDeliveryDay(Calendar.
                  SUNDAY,
                  monday,
                  tuesday,
                  wednesday,
                  thursday,
                  friday,
                  saturday,
                  sunday));

            }
          }
        }
        h.put("DATE", tempCalendar);
        h.put("QTY", myQ);
        v.addElement(h);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return v;
  }

  private Calendar getStartingDate() throws Exception {
    SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
    Calendar c = Calendar.getInstance();
    if (CalendarHandler.isValidDate(dateL.getText())) {
      c.setTime(df.parse(dateL.getText()));
    }
    else {
      throw new Exception("Invalid date in DeliveryFrequency.java");
    }
    return c;
  }

  boolean auditInput() {
    String msg = "";
    BigDecimal I;
    try {
      Calendar c = getStartingDate();
      if (c.before(firstActiveDay) && !BothHelpObjs.sameDay(c, firstActiveDay)) {
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        String monthName = months[firstActiveDay.get(Calendar.MONTH)];
        String s = monthName + " " + firstActiveDay.get(Calendar.DAY_OF_MONTH) +
            ", " + firstActiveDay.get(Calendar.YEAR);
        msg = "You may not schedule a delivery before " + s + ".";
      }
    }
    catch (Exception e) {
      msg = "Please enter a date in mm/dd/yyyy format in 'Starting Date'.";
    }
    if (dailyB.isSelected()) {
      try {
        I = new BigDecimal(dayT.getText());
        int num = Integer.parseInt(dayT.getText());
        //if (I.compareTo(new BigDecimal("0")) <= 0) {
        if (num <= 0) {
          msg = "The daily frequency must be more than zero.";
        }
      }
      catch (Exception e) {
        msg = "Please enter a number for the daily frequency.";
      }
    }
    try {
      I = new BigDecimal(qtyT.getText());
      int num = Integer.parseInt(qtyT.getText());
      //if (I.compareTo(new BigDecimal("0")) <= 0) {
      if (num <= 0) {
        msg = "The quantity to be delivered must be more than zero.";
      }
    }catch (Exception e) {
      msg = "Please enter a number for 'Quantity'";
    }

    try {
      I = new BigDecimal(totalT.getText());
      int num = Integer.parseInt(totalT.getText());
      //if (I.compareTo(new BigDecimal("0")) <= 0) {
      if (num <= 0) {
        msg = "The total quantity to be delivered must be more than zero.";
      }
    }
    catch (Exception e) {
      msg = "Please enter a number for 'Total to Deliver'";
    }

    if (msg.length() > 0) {
      GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
      gd.setMsg(msg);
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
      return false;
    }
    return true;
  }

  void setDayLabels() {
    boolean b = dailyB.isSelected();
    everyL.setEnabled(b);
    daysL.setEnabled(b);
    dayT.setEnabled(b);
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (!auditInput()) {
      return;
    }
    canceled = false;
    setVisible(false);
  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    setVisible(false);
  }

  void monthlyB_actionPerformed(ActionEvent e) {
    setDayLabels();
  }

  void dailyB_actionPerformed(ActionEvent e) {
    setDayLabels();
  }
}
