package radian.tcmis.both1.reports;

import java.util.*;
import radian.tcmis.both1.helpers.*;

/** this is a template for setting up an object to be used as a table by
    the ERW reporting programs.*/
public class Delivery {
  public static final int delQty_int = 0;
  public static final int delType_int = 1;
  public static final int delDate_int = 2;

  String delQty;
  String delType;
  String delDate;
  String item;
  String line;

  public Delivery(Hashtable h) {
    this.delQty = h.get("DELIVERY_QTY").toString();
    this.delType = h.get("DELIVERY_TYPE").toString();
    this.delDate = h.get("DELIVERY_DATE").toString();
    this.item = h.get("ITEM").toString();
    this.line = h.get("LINE").toString();
  }
  public Delivery() {
    this.delQty = "";
    this.delType = "";
    this.delDate = "";
    this.item = "";
    this.line = "";
  }

  public String getdelQty(){return delQty;}
  public String getdelType(){return delType;}
  public String getdelDate(){return delDate;}
  public String getItem(){return item;}
  public String getLine(){return line;}

  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("delQty = getdelQty");
    v.addElement("delType = getdelType");
    v.addElement("delDate = getdelDate");
    v.addElement("item = getItem");
    v.addElement("line = getLine");
    return v;
  }

  public static Vector getVector(Vector in) {
    Vector v = new Vector();
    for(int i=0;i<in.size();i++) {
      Hashtable h = (Hashtable)in.elementAt(i);
      String type = (String)h.get("DELIVERY_TYPE");
      if (type.equalsIgnoreCase("Schedule")) {
        Vector delInfo = (Vector)h.get("DELIVERY_SCHEDULE");
        if (delInfo.size() > 0) {
          for (int j = 0; j < delInfo.size(); j++) {
            Hashtable temp = new Hashtable();
            Hashtable sched = (Hashtable)delInfo.elementAt(j);
            String myQty = sched.get("QTY").toString();
            Calendar cal = (Calendar)sched.get("DATE");
            String myDate = BothHelpObjs.formatDate("toCharfromNum",new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR)));
            temp.put("ITEM",h.get("ITEM_NUM").toString());
            temp.put("LINE",h.get("LINE_NUM").toString());
            temp.put("DELIVERY_QTY",myQty);
            temp.put("DELIVERY_DATE",myDate);
            temp.put("DELIVERY_TYPE","On");
            v.addElement(new Delivery(temp));
          }
        }else {
          v.addElement(new Delivery());
        }
      }else {
        Hashtable by = new Hashtable();
        by.put("ITEM",h.get("ITEM_NUM").toString());
        by.put("LINE",h.get("LINE_NUM").toString());
        by.put("DELIVERY_QTY",(String)h.get("QTY"));
        by.put("DELIVERY_DATE",(String)h.get("DEL_DATE"));
        by.put("DELIVERY_TYPE","By");
        v.addElement(new Delivery(by));
      }
    }
    return v;
  }
}
