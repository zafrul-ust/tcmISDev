package radian.tcmis.both1.reports;

import java.util.*;
import radian.tcmis.both1.helpers.*;
import java.math.BigDecimal;

/** this is a template for setting up an object to be used as a table by
    the ERW reporting programs.*/
public class WasteMgtRequest {
  String line;
  String chargeType;
  String vendorProfile;
  String desc;
  String qty;
  String notes;
  String packaging;
  String generationPt;
  String vendor;
  String price;
  String sealDate;
  String toLoc;
  String fromLoc;
  String reqTransDate;
  String reqTransTimeAm;
  String chargeColName1 = "";
  String chargeColName2 = "";
  String unitPrice;
  String ePrice;
  String totalLines;
  String currency;


  public WasteMgtRequest(Hashtable h, int i, Hashtable headerInfo) {
    this.line = h.get("LINE_NUM").toString();
    this.chargeType = h.get("CHARGE_TYPE").toString();
    this.vendorProfile = h.get("VENDOR_PROFILE").toString();
    this.desc = h.get("DESCRIPTION").toString();
    this.qty = h.get("QTY").toString();
    this.notes = h.get("NOTES").toString();
    this.packaging = h.get("PACKAGING").toString();
    this.generationPt = h.get("WORK_AREA_ID").toString();
    this.vendor = h.get("VENDOR").toString();

    this.sealDate = h.get("SEAL_DATE").toString();
    this.toLoc = h.get("TO_LOCATION_SELECTED_ID").toString();
    this.fromLoc = h.get("FROM_LOCATION_SELECTED_ID").toString();
    this.reqTransDate = h.get("REQUESTED_TRANSFER_DATE").toString();
    this.reqTransTimeAm = h.get("REQUESTED_TRANSFER_TIME_AM").toString();
    this.unitPrice = h.get("UNIT_PRICE").toString();
    this.currency = " "+headerInfo.get("CURRENCY").toString();
    totalLines = String.valueOf(i);

    String accSysId = (String)headerInfo.get("ACC_SYS_ID");
    String key1 = accSysId + chargeType;
    Hashtable packH = (Hashtable)headerInfo.get("PACK");
    Hashtable accSysTypeH = (Hashtable)packH.get(key1);
    Vector v = (Vector)accSysTypeH.get("LABELS");

    if(v.size() == 2){
      chargeColName1 = v.elementAt(0).toString();
      chargeColName2 = "";
    }else{
      chargeColName1 = v.elementAt(0).toString();
      chargeColName2 = v.elementAt(1).toString();
    }

    // compute extended price
    try{
      int q = new Integer(qty).intValue();
      float f = new Float(unitPrice).floatValue();
      double d = q*f;
      //this.ePrice = NumberFormat.getCurrencyInstance().format(d);
      //this.unitPrice = NumberFormat.getCurrencyInstance().format(f);
      BigDecimal minAmt = new BigDecimal(d);
      minAmt = minAmt.setScale(4,BigDecimal.ROUND_HALF_UP);
      ePrice = minAmt.toString()+currency;
      BigDecimal unitAmt = new BigDecimal(f);
      unitAmt = unitAmt.setScale(4,BigDecimal.ROUND_HALF_UP);
      unitPrice = unitAmt.toString()+currency;
    }catch(Exception e){
      this.unitPrice = "";
      this.ePrice = "";
    }
    //System.out.println("\n---------- in waste mgt request: "+this.line);
  }

  public String getLine(){return line;}
  public String getChargeType(){
    if(BothHelpObjs.isBlankString(chargeType)){
      return "Direct";
    }
    if(chargeType.equalsIgnoreCase("i")){
      return "Indirect";
    }
    if(chargeType.equalsIgnoreCase("d")){
      return "Direct";
    }
    return "Direct";
  }
  public String getVendorProfile(){return vendorProfile;}
  public String getDesc(){return desc;}
  public String getQty(){return qty;}
  public String getNotes(){
    if(BothHelpObjs.isBlankString(notes)){
      return "There are no notes for this line.";
    }
    return notes;
  }
  public String getPackaging(){return packaging;}
  public String getGenerationPt(){return generationPt;}
  public String getVendor(){return vendor;}
  public String getPrice(){return price;}
  public String getSealDate(){return sealDate;}
  public String getToLoc(){return toLoc;}
  public String getFromLoc(){return fromLoc;}
  public String getReqTransDate(){return reqTransDate;}
  public String getReqTransTimeAm(){return reqTransTimeAm;}
  public String getChargeColName1(){return chargeColName1;}
  public String getChargeColName2(){return chargeColName2;}
  public String getUnitPrice(){return unitPrice;}
  public String getePrice(){return ePrice;}
  public String getLineText() {return "Line "+line+" of "+totalLines;}

  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("line = getLine");
    v.addElement("chargeType = getChargeType");
    v.addElement("vendorProfile = getVendorProfile");
    v.addElement("desc = getDesc");
    v.addElement("qty = getQty");
    v.addElement("notes = getNotes");
    v.addElement("packaging = getPackaging");
    v.addElement("generationPt = getGenerationPt");
    v.addElement("vendor = getVendor");
    v.addElement("price = getPrice");
    v.addElement("sealDate = getSealDate");
    v.addElement("toLoc = getToLoc");
    v.addElement("fromLoc = getFromLoc");
    v.addElement("reqTransDate = getReqTransDate");
    v.addElement("reqTransTimeAm = getReqTransTimeAm");
    v.addElement("chargeColName1 = getChargeColName1");
    v.addElement("chargeColName2 = getChargeColName2");
    v.addElement("unitPrice = getUnitPrice");
    v.addElement("ePrice = getePrice");
    v.addElement("lineText = getLineText");
    return v;
  }

  public static Vector getVector(Vector in, Hashtable header) {
    Vector v = new Vector();
    for(int i=0;i<in.size();i++) {
      Hashtable h = (Hashtable)in.elementAt(i);
      v.addElement(new WasteMgtRequest(h, in.size(),header));
    }
    return v;
  }
}