package radian.tcmis.both1.reports;

import java.util.*;

/** this is a template for setting up an object to be used as a table by
    the ERW reporting programs.*/
public class MrDelivery {
  public static final int NUM_COLUMNS = 7;
  public static final int delQty_int = 0;
  public static final int delFreq_int = 1;
  public static final int delType_int = 2;
  public static final int delDate_int = 3;
  public static final int Dock_int = 4;
  public static final int DelTo_int = 5;
  public static final int item_int = 5;

  String delQty;
  String delFreq;
  String delType;
  String delDate;
  String Dock;
  String DelTo;
  String item;


  public MrDelivery(Hashtable h) {
    this.item = h.get("ITEM_NUM").toString();
    this.delQty = "DEL_QTY";
    this.delFreq = "DEL_FREQ";
    this.delType = h.get("DELIVERY_TYPE").toString();
    this.delDate = h.get("DEL_DATE").toString();
    this.Dock = h.get("DOCK").toString();
    this.DelTo = h.get("DELIVER_TO").toString();
  }

  public String getitem(){return item;}
  public String getdelQty(){return delQty;}
  public String getdelFreq(){return delFreq;}
  public String getdelType(){return delType;}
  public String getdelDate(){return delDate;}
  public String getDock(){return Dock;}
  public String getDelTo(){return DelTo;}

  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("item = getitem");
    v.addElement("qty = getdelQty");
    v.addElement("delFreq = getdelFreq");
    v.addElement("delType = getdelType");
    v.addElement("date = getdelDate");
    v.addElement("Dock = getDock");
    v.addElement("DeliverTo = getDelTo");
    return v;
  }

  public static Vector getVector(Vector in) {
    Vector v = new Vector();
    for(int i=0;i<in.size();i++) {
      Hashtable h = (Hashtable)in.elementAt(i);
      v.addElement(new MrDelivery(h));
    }
    return v;
  }
}