package radian.tcmis.client.share.reports.appData;

import java.util.*;
import radian.tcmis.both1.helpers.*;
import javax.swing.table.*;

/** this is a template for setting up an object to be used as a table by
    the ERW reporting programs.*/
public class TrackDeliveryInfo {
  String qty;
  String date;
  String recieved;
  String qtyToDate;
  String noMsg;
  String lot;
  String expDate;

  public TrackDeliveryInfo(String qty,String date,String recieved,String qtyToDate,String lot,String expDate) {
    this.qty = qty;
    this.date = date;
    this.recieved = recieved;
    this.qtyToDate = qtyToDate;
    this.lot = lot;
    this.expDate = expDate;
    noMsg = "";
  }

  public TrackDeliveryInfo() {
    this("","","","","","");
    noMsg = "There are no deliveries for this order.";
  }
  public boolean isEmpty() {
    return (BothHelpObjs.isBlankString(qty) &&
        BothHelpObjs.isBlankString(date) &&
        BothHelpObjs.isBlankString(recieved) &&
        BothHelpObjs.isBlankString(qtyToDate));
  }

  public String getqty(){return qty;}
  public String getdate(){return date;}
  public String getrecieved(){return recieved;}
  public String getqtyToDate(){return qtyToDate;}
  public String getnoMsg(){return noMsg;}
  public String getlot(){return lot;}
  public String getexpDate(){return expDate;}


  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("qty = getqty");
    v.addElement("date = getdate");
    v.addElement("recieved = getrecieved");
    v.addElement("qtyToDate = getqtyToDate");
    v.addElement("noMsg = getnoMsg");
    v.addElement("expDate = getexpDate");
    v.addElement("lot = getlot");
    return v;
  }

  public static Vector getVector(Object[][] oa) {
    Vector v = new Vector();
    for(int i=0;i<oa.length;i++) {
      v.addElement(new TrackDeliveryInfo(oa[i][0].toString(),
                                 oa[i][1].toString(),
                                 oa[i][2].toString(),
                                 oa[i][3].toString(),
                                 "",""));
    }
    if(v.size() == 0 || ((TrackDeliveryInfo)v.elementAt(0)).isEmpty()){
      v.removeAllElements();
      v.addElement(new TrackDeliveryInfo());
    }
    return v;
  }
  public static Vector getVector(TableModel tm,int qty,int shipDate,int lot,int expDate) {
    Vector v = new Vector();
    if(tm != null){
      for(int i=0;i<tm.getRowCount();i++) {
        v.addElement(new TrackDeliveryInfo(tm.getValueAt(i,qty).toString(),
                                   tm.getValueAt(i,shipDate).toString(),
                                   "",
                                   "",
                                   tm.getValueAt(i,lot).toString(),
                                   tm.getValueAt(i,expDate).toString()));
      }
    }
    if(v.size() == 0 || ((TrackDeliveryInfo)v.elementAt(0)).isEmpty()){
      v.removeAllElements();
      v.addElement(new TrackDeliveryInfo());
    }
    return v;
  }
}
