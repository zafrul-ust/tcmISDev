package radian.tcmis.both1.reports;

import java.util.*;

/** this is a template for setting up an object to be used as a table by
    the ERW reporting programs.*/
public class BulkInfo {
  String manifestNo;
  String copyReturned;
  String country;
  String state;
  String actualPickup;
  String vendorOrderNo;
  String shipmentId;
  String packaging;
  String disposition;
  String description;
  String actualAmount;
  String plannedPickup;
  String bulkId;
  String profile;
  String plannedAmount;


  public BulkInfo(Hashtable h) {
    //System.out.println("\n\n================ got here materialsize: "+h);
    this.manifestNo = (String)h.get("MANIFEST_NO");
    this.actualPickup = (String)h.get("ACTUAL_PICKUP");
    this.vendorOrderNo = (String)h.get("VENDOR_ORDER_NO");
    this.state = (String)h.get("STATE");
    this.shipmentId = (String)h.get("SHIPMENT_ID");
    this.copyReturned = (String)h.get("COPY_RETURNED");
    this.packaging = (String)h.get("PACKAGING");
    this.disposition = (String)h.get("DISPOSITION");
    this.profile = (String)h.get("PROFILE");
    this.plannedAmount = (String)h.get("PLANNED_AMOUNT");
    this.country = (String)h.get("COUNTRY");
    this.description = (String)h.get("DESCRIPTION");
    this.actualAmount = (String)h.get("ACTUAL_AMOUNT");
    this.plannedPickup = (String)h.get("PLANNED_PICKUP");
    this.bulkId = (String)h.get("BULK_ID");
  }

  public String getManifestNo(){return manifestNo;}
  public String getActualPickup(){return actualPickup;}
  public String getVendorOrderNo(){return vendorOrderNo;}
  public String getState(){return state;}
  public String getShipmentId(){return shipmentId;}
  public String getCopyReturned(){return copyReturned;}
  public String getPackaging(){return packaging;}
  public String getDisposition(){return disposition;}
  public String getProfile(){return profile;}
  public String getPlannedAmount(){return plannedAmount;}
  public String getCountry(){return country;}
  public String getDescription() {return description;}
  public String getActualAmount(){return actualAmount;}
  public String getPlannedPickup(){return plannedPickup;}
  public String getBulkId(){return bulkId;}

  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("manifestNo = getManifestNo");
    v.addElement("actualPickup = getActualPickup");
    v.addElement("vendorOrderNo = getVendorOrderNo");
    v.addElement("state = getState");
    v.addElement("shipmentId = getShipmentId");
    v.addElement("copyReturned = getCopyReturned");
    v.addElement("packaging = getPackaging");
    v.addElement("disposition = getDisposition");
    v.addElement("profile = getProfile");
    v.addElement("plannedAmount = getPlannedAmount");
    v.addElement("country = getCountry");
    v.addElement("description = getDescription");
    v.addElement("actualAmount = getActualAmount");
    v.addElement("plannedPickup = getPlannedPickup");
    v.addElement("bulkId = getBulkId");
    return v;
  }

  public static Vector getVector(Vector in) {
    Vector v = new Vector();
    for (int i = 0; i < in.size(); i++) {
      Hashtable h = (Hashtable)in.elementAt(i);
      v.addElement(new BulkInfo(h));
    }
    return v;
  }
}