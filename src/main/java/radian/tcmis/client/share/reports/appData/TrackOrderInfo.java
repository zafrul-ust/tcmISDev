package radian.tcmis.client.share.reports.appData;

import java.util.*;
import javax.swing.table.*;

/** this is a template for setting up an object to be used as a table by
    the ERW reporting programs.*/
public class TrackOrderInfo {
  String qty;
  String status;
  String estShipDate;
  String notes;
  String msg = "";
  public TrackOrderInfo(String qty,String status,String estShipDate,String notes) {
    this.qty = qty;
    this.status = status;
    this.estShipDate = estShipDate;
    this.notes = notes;
  }
  public TrackOrderInfo(){
    this.qty = "";
    this.status = "";
    this.estShipDate = "";
    this.notes = "";
    this.msg = "There is no order/inventory information.";
  }
  public String getqty(){return qty;}
  public String getstatus(){return status;}
  public String getestShipDate(){return estShipDate;}
  public String getnotes(){return notes;}
  public String getmsg(){return msg;}


  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("qty = getqty");
    v.addElement("status = getstatus");
    v.addElement("estShipDate = getestShipDate");
    v.addElement("notes = getnotes");
    v.addElement("msg = getmsg");
    return v;
  }

  public static Vector getVector(TableModel tm, int qty, int status, int estShipDate, int notes) {
    Vector v = new Vector();
    if(tm != null){
      for(int i=0;i<tm.getRowCount();i++) {
        v.addElement(new TrackOrderInfo(tm.getValueAt(i,qty).toString(),
                                     tm.getValueAt(i,status).toString(),
                                     tm.getValueAt(i,estShipDate).toString(),
                                     tm.getValueAt(i,notes).toString()));
      }
    }
    if(v.size()<1){
      v.addElement(new TrackOrderInfo());
    }
    return v;
  }
}
