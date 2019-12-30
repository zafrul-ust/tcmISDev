package radian.tcmis.client.share.reports.appData;

import java.util.*;
import javax.swing.*;

/** this is a template for setting up an object to be used as a table by
    the ERW reporting programs.*/
public class Inventory {
  String itemID;
  String partNum;
  String tradeName;
  String packaging;
  String manufacturer;
  String inventory;
  String onHand;
  String onOrder;

  public Inventory(String itemID,String partNum,String tradeName,String packaging,String manufacturer,String inventory,String onHand,String onOrder) {
    this.itemID = itemID;
    this.partNum = partNum;
    this.tradeName = tradeName;
    this.packaging = packaging;
    this.manufacturer = manufacturer;
    this.inventory = inventory;
    this.onHand = onHand;
    this.onOrder = onOrder;
  }

  public String getitemID(){return itemID;}
  public String getpartNum(){return partNum;}
  public String gettradeName(){return tradeName;}
  public String getpackaging(){return packaging;}
  public String getmanufacturer(){return manufacturer;}
  public String getinventory(){return inventory;}
  public String getonHand(){return onHand;}
  public String getonOrder(){return onOrder;}


  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("itemID = getitemID");
    v.addElement("partNum = getpartNum");
    v.addElement("tradeName = gettradeName");
    v.addElement("packaging = getpackaging");
    v.addElement("manufacturer = getmanufacturer");
    v.addElement("inventory = getinventory");
    v.addElement("onHand = getonHand");
    v.addElement("onOrder = getonOrder");
    return v;
  }

  public static Vector getVector(Object[][] oa) {
    Vector v = new Vector();
    for(int i=0;i<oa.length;i++) {
      if(oa[i][1] instanceof JTextArea) {
        oa[i][1] = ((JTextArea)oa[i][1]).getText();
      }
      v.addElement(new Inventory(oa[i][0].toString(),
                                 oa[i][1].toString(),
                                 oa[i][2].toString(),
                                 oa[i][3].toString(),
                                 oa[i][4].toString(),
                                 oa[i][5].toString(),
                                 oa[i][6].toString(),
                                 oa[i][7].toString()));
    }
    return v;
  }
}
