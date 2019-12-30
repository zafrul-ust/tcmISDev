package radian.tcmis.client.share.reports.appData;

import java.util.*;

/** this is a template for setting up an object to be used as a "table" by
    the ERW reporting programs.*/
public class FacilityRoles {
  String facility;
  boolean preferred;
  boolean releaser;
  boolean approver;
  String amount;

  boolean admin;
  boolean canOrder;
  boolean newChem;

  /** this is a constructor for the object*/
  public FacilityRoles(String n, boolean pref,boolean rel, boolean app, String amt,boolean canOrder,boolean newChem,boolean admin){
    facility = new String(n);
    preferred = pref;
    releaser = rel;
    approver = app;
    amount = new String(amt);
    this.admin = admin;
    this.canOrder = canOrder;
    this.newChem = newChem;
  }

  /** you must supply a GET method for every Class variable. the method cannot
      take any arguments. */
  public String getFacility() {
    return facility;
  }
  public String getPreferred(){
    if(preferred)return "Yes";
    return "-";
  }
  public String getReleaser(){
    if(releaser)return "Yes";
    return "-";
  }
  public String getApprover(){
    if(approver)return "Yes";
    return "-";
  }
  public String getAmount(){
    return amount;
  }
  public String getCanOrder(){
    if(canOrder)return "Yes";
    return "-";
  }
  public String getAdmin(){
    if(admin)return "Yes";
    return "-";
  }
  public String getNewChem(){
    if(newChem)return "Yes";
    return "-";
  }

  /** this method returns a vector of name/method pairs where the name will be
      used as the column head for the object and the method is the method needed
      to get the value. The GET method cannot take any arguments.*/
  public static Vector getFieldVector(){
    Vector v = new Vector();
    v.addElement("facility = getFacility");
    v.addElement("preferred = getPreferred");
    v.addElement("approver = getApprover");
    v.addElement("releaser = getReleaser");
    v.addElement("amount = getAmount");
    v.addElement("canOrder = getCanOrder");
    v.addElement("admin = getAdmin");
    v.addElement("newChem = getNewChem");
    return v;
  }

  /** this method returns a vector of this Object taken from a 2D object Array*/
  /*public static Vector getVector(Object[][] oa){
    Vector v = new Vector();
    for(int i=0;i<oa.length;i++) {
      v.addElement(new TableObjectTemplate(oa[i][0].toString(),oa[i][1].toString()));
    }
    return v;
  } */
}
