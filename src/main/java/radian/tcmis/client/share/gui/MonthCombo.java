
//Title:        TCM - CMIS
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian International
//Description:  TCM - CMIS - Gui components


package radian.tcmis.client.share.gui;

import javax.swing.*;
import java.util.*;
import java.text.*;
public class MonthCombo extends JComboBox {

  public MonthCombo() {
    super();
    DateFormatSymbols dfs = new DateFormatSymbols();
    String[] months = dfs.getMonths();
    for(int i=0;i<12;i++){
      this.addItem(months[i]);
    }
  }
  public MonthCombo(int i) {
    this();
    GregorianCalendar gc = new GregorianCalendar();
    gc.add(Calendar.MONTH,i);
    this.setSelectedIndex(gc.get(Calendar.MONTH));
  }

  public void reFresh(int i) {
    GregorianCalendar gc = new GregorianCalendar();
    gc.add(Calendar.MONTH,i);
    this.setSelectedIndex(gc.get(Calendar.MONTH));
  }
}
