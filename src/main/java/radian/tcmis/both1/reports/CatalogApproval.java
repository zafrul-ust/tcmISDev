package radian.tcmis.both1.reports;

import java.util.*;
import radian.tcmis.both1.helpers.*;

/** this is a template for setting up an object to be used as a table by
    the ERW reporting programs.*/
public class CatalogApproval {
  String facilityId;
  String workArea;
  String desc;
  String userGroup;
  String restriction1;
  String restriction2;
  String line;
  Integer tmpQty = null;

  public CatalogApproval(Hashtable h) {
    this.facilityId = h.get("FACID").toString();
    this.workArea = h.get("WA").toString();
    this.desc = h.get("PROCESS").toString();
    this.userGroup = h.get("UGID").toString();
    String qty = h.get("QTY1").toString();
    String per = h.get("PER1").toString();
    String unit = h.get("UNIT1").toString();
    String tmp = qty+" per "+per+" "+unit;
    try {tmpQty = new Integer(qty);} catch (Exception e) {//e.printStackTrace();
    }

    if (BothHelpObjs.isBlankString(qty) || qty == null || tmpQty.intValue() == 0) {
      tmp = "Unlimited";
    }
    this.restriction1 = tmp;
    qty = h.get("QTY2").toString();
    per = h.get("PER2").toString();
    unit = h.get("UNIT2").toString();
    tmp = qty+" per "+per+" "+unit;
    try {tmpQty = new Integer(qty);} catch (Exception e) {e.printStackTrace();}
    if (BothHelpObjs.isBlankString(qty) || qty == null || tmpQty.intValue() == 0) {
      tmp = "Unlimited";
    }
    this.restriction2 = tmp;
    this.line = h.get("LINE").toString();
  }

  public String getFacilityId(){return facilityId;}
  public String getWorkArea(){return workArea;}
  public String getDesc(){return desc;}
  public String getUserGroup(){return userGroup;}
  public String getRestriction1(){return restriction1;}
  public String getRestriction2(){return restriction2;}
  public String getLine(){return line;}

  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("facility = getFacilityId");
    v.addElement("workArea = getWorkArea");
    v.addElement("desc = getDesc");
    v.addElement("userGroup = getUserGroup");
    v.addElement("restriction1 = getRestriction1");
    v.addElement("restriction2 = getRestriction2");
    v.addElement("lineItem = getLine");
    return v;
  }

  public static Vector getVector(Vector in) {
    Vector v = new Vector();
    for(int i=0;i<in.size();i++) {
      Hashtable h = (Hashtable)in.elementAt(i);
      Integer count = new Integer(i+1);
      h.put("LINE",count.toString());
      v.addElement(new CatalogApproval(h));
    }
    return v;
  }
}