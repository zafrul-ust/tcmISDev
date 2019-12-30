package radian.tcmis.client.share.gui;

import javax.swing.*;
import java.util.*;
public class YearCombo extends JComboBox {
  public YearCombo() {
    this(1997,10);
  }

  public YearCombo(int begYear,int num) {
    super();
    for(int i=0;i<num;i++){
      this.addItem((new Integer(begYear + i)).toString());
    }
  }

  public YearCombo(int begYear,int num,int selected) {
    super();
    GregorianCalendar gc = new GregorianCalendar();
    gc.add(Calendar.YEAR,begYear);
    int myYear = gc.get(Calendar.YEAR);
    for(int i=0;i<num;i++){
      this.addItem((new Integer(myYear + i)).toString());
    }
    if(begYear > selected || selected - begYear > this.getItemCount()) {
      setSelectedIndex(0);
      return;
    }
    setSelectedIndex(selected - begYear);
  }

  public void reFresh(int begYear,int num,int selected) {
    GregorianCalendar gc = new GregorianCalendar();
    gc.add(Calendar.YEAR,begYear);
    int myYear = gc.get(Calendar.YEAR);
    for(int i=0;i<num;i++){
      this.addItem((new Integer(myYear + i)).toString());
    }
    if(begYear > selected || selected - begYear > this.getItemCount()) {
      setSelectedIndex(0);
      return;
    }
    setSelectedIndex(selected - begYear);
  }

}
