package radian.tcmis.client.share.reports.appData;

import java.util.*;

/** this is a template for setting up an object to be used as a table by
    the ERW reporting programs.*/
public class InvFloatOnHand {
  String inventory;
  String onHand;
  String lot;
  String expDate;
  String noMsg;

  public InvFloatOnHand(String inventory,String onHand,String lot,String expDate) {
    this.inventory = inventory;
    this.onHand = onHand;
    this.lot = lot;
    this.expDate = expDate;
  }
  public InvFloatOnHand() {
    this("","","","");
    noMsg = "This item is not currently in stock.";
  }

  public String getinventory(){return inventory;}
  public String getonHand(){return onHand;}
  public String getlot(){return lot;}
  public String getexpDate(){return expDate;}
  public String getnoMsg(){return noMsg;}


  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("inventory = getinventory");
    v.addElement("onHand = getonHand");
    v.addElement("lot = getlot");
    v.addElement("expDate = getexpDate");
    v.addElement("noMsg = getnoMsg");
    return v;
  }

  public static Vector getVector(Object[][] oa) {
    Vector v = new Vector();
    for(int i=0;i<oa.length;i++) {
      v.addElement(new InvFloatOnHand(oa[i][0].toString(),
                                 oa[i][1].toString(),
                                 oa[i][2].toString(),
                                 oa[i][3].toString()));
    }
    if(v.isEmpty())v.addElement(new InvFloatOnHand());
    return v;
  }
}
