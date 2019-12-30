package radian.tcmis.client.share.reports.appData;

import java.util.*;

/** this is a template for setting up an object to be used as a table by
    the ERW reporting programs.*/
public class InvFloatOnOrder {
  String inventory;
  String onOrder;
  String po;
  String dueDate;
  String noMsg;

  public InvFloatOnOrder(String inventory,String onOrder,String po,String dueDate) {
    this.inventory = inventory;
    this.onOrder = onOrder;
    this.po = po;
    this.dueDate = dueDate;
  }
  public InvFloatOnOrder() {
    this("","","","");
    noMsg = "There are no orders for this item.";
  }

  public String getinventory(){return inventory;}
  public String getonOrder(){return onOrder;}
  public String getpo(){return po;}
  public String getdueDate(){return dueDate;}
  public String getnoMsg(){return noMsg;}


  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("inventory = getinventory");
    v.addElement("onOrder = getonOrder");
    v.addElement("po = getpo");
    v.addElement("dueDate = getdueDate");
    v.addElement("noMsg = getnoMsg");
    return v;
  }

  public static Vector getVector(Object[][] oa) {
    Vector v = new Vector();
    for(int i=0;i<oa.length;i++) {
      v.addElement(new InvFloatOnOrder(oa[i][0].toString(),
                                 oa[i][1].toString(),
                                 oa[i][2].toString(),
                                 oa[i][3].toString()));
    }
    if(v.isEmpty()) v.addElement(new InvFloatOnOrder());
    return v;
  }
}
