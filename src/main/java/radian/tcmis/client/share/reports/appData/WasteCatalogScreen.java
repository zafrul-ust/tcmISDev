package radian.tcmis.client.share.reports.appData;

import java.util.*;

public class WasteCatalogScreen {
  String radId;
  String facility;
  String vendor;
  String vendorProfile;
  String desc;
  String wmo;
  String packaging;
  String wc;
  String wasteType;

  /** this is a constructor for the object*/
  public WasteCatalogScreen(String item, String fac,String vend,
                       String vendorP,String desc,String wmo,
                       String pack, String wc,String wtype){
    this.radId = new String(item);
    this.facility = new String(fac);
    this.vendor = new String(vend);
    this.vendorProfile = new String(vendorP);
    this.desc = new String(desc);
    this.desc = "\""+this.desc+"\"";
    System.out.println("------------------------------ double quote: "+ this.desc);
    this.wmo = new String(wmo);
    this.packaging = new String(pack);
    this.wc = new String(wc);
    this.wasteType = new String(wtype);
  }

  public String getRadId(){
    return radId;
  }
  public String getFacility(){
    return facility;
  }
  public String getVendor(){
    return vendor;
  }
  public String getVendorProfile(){
    return vendorProfile;
  }
  public String getDesc(){
    return desc;
  }
  public String getWmo(){
    return wmo;
  }
  public String getPackaging(){
    return packaging;
  }
  public String getWc(){
    return wc;
  }
  public String getWasteType(){
    return wasteType;
  }

  /** this method returns a vector of name/method pairs where the name will be
      used as the column head for the object and the method is the method needed
      to get the value. The GET method cannot take any arguments.*/
  public static Vector getFieldVector(){
    Vector v = new Vector();
    v.addElement("radId = getRadId");
    v.addElement("facility = getFacility");
    v.addElement("vendor = getVendor");
    v.addElement("vendorProfile = getVendorProfile");
    v.addElement("desc = getDesc");
    v.addElement("wmo = getWmo");
    v.addElement("packaging = getPackaging");
    v.addElement("wc = getWc");
    v.addElement("wasteType = getWasteType");
    return v;
  }

  /** this method returns a vector of this Object taken from a 2D object Array*/
  public static Vector getVector(Object[][] oa){
    Vector v = new Vector();
    for(int i=0;i<oa.length;i++) {
      float f = 0;
      try{
        Float F = new Float(oa[i][7].toString());
        f = F.floatValue();
      }catch(Exception x) {}
      v.addElement(new WasteCatalogScreen(oa[i][0].toString(),
                                     oa[i][1].toString(),
                                     oa[i][2].toString(),
                                     oa[i][3].toString(),
                                     oa[i][4].toString(),
                                     oa[i][5].toString(),
                                     oa[i][6].toString(),
                                     oa[i][7].toString(),
                                     oa[i][8].toString()));
    }
    return v;
  }

}
