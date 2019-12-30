package radian.tcmis.both1.reports;

import java.util.*;
import radian.tcmis.both1.helpers.*;

/** this is a template for setting up an object to be used as a table by
    the ERW reporting programs.*/
public class WasteContainerReport {
  String containerId;
  String sealDate;
  String vendorProfile;
  String line;

  public WasteContainerReport(Hashtable h) {
    this.containerId = h.get("CONTAINER_ID").toString();
    this.sealDate = h.get("SEAL_DATE").toString();
    this.vendorProfile = h.get("VENDOR_PROFILE").toString();
    this.line = h.get("LINE_NUM").toString();
  }
  public WasteContainerReport() {
    this.containerId = "";
    this.sealDate = "";
    this.vendorProfile = "";
  }

  public String getContainerId(){return containerId;}
  public String getSealDate(){return sealDate;}
  public String getVendorProfile(){return vendorProfile;}
  public String getLine(){return line;}

  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("containerId = getContainerId");
    v.addElement("sealDate = getSealDate");
    v.addElement("vendorProfile = getVendorProfile");
    v.addElement("line = getLine");
    return v;
  }

  public static Vector getVector(Vector in) {
    Vector v = new Vector();
    for(int i=0;i<in.size();i++) {
      Hashtable h = (Hashtable)in.elementAt(i);
      Vector containerIDs = (Vector)h.get("CONTAINER_IDS");
      if (containerIDs.size() > 0) {
        for (int j = 0; j < containerIDs.size(); j++) {
          Hashtable temp = new Hashtable();
          String myDate = (String)h.get("SEAL_DATE");
          myDate = BothHelpObjs.formatDate("toCharfromNum",myDate);
          temp.put("VENDOR_PROFILE",(String)h.get("VENDOR_PROFILE"));
          temp.put("LINE_NUM",h.get("LINE_NUM").toString());
          Integer containerId = (Integer)containerIDs.elementAt(j);
          temp.put("CONTAINER_ID",containerId.toString());
          temp.put("SEAL_DATE",myDate);
          v.addElement(new WasteContainerReport(temp));
        }
      }else {
        v.addElement(new WasteContainerReport());
      }
    }
    return v;
  }
}