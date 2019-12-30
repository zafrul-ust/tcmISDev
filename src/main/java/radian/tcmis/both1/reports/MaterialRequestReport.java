package radian.tcmis.both1.reports;

import java.util.*;
import java.math.BigDecimal;
import radian.tcmis.both1.helpers.*;

/** this is a template for setting up an object to be used as a table by
    the ERW reporting programs.*/
public class MaterialRequestReport {
  public static final int NUM_COLUMNS = 22;
  public static final int line_int = 0;
  public static final int chargeType_int = 1;
  public static final int item_int = 2;
  public static final int partNum_int = 3;
  public static final int qty_int = 4;
  public static final int crit_int = 5;
  public static final int notes_int = 6;
  public static final int type_int = 7;
  public static final int workArea_int = 8;
  public static final int itemDesc_int = 9;
  public static final int price_int = 10;
  public static final int delQty_int = 11;
  public static final int delFreq_int = 12;
  public static final int delType_int = 13;
  public static final int delDate_int = 14;
  public static final int Dock_int = 15;
  public static final int DelTo_int = 16;
  public static final int po_int = 17;
  public static final int refNum_int = 18;
  public static final int chargeColName1_int = 19;
  public static final int chargeColName2_int = 20;
  public static final int unitPrice_int = 21;

  String line;
  String chargeType;
  String item;
  String partNum;
  String qty;
  boolean crit;
  String notes;
  String type;
  String workArea;
  String itemDesc;
  String price;
  String delQty;
  String delFreq;
  String delType;
  String delDate;
  String Dock;
  String DelTo;
  String po;
  String refNum;
  String chargeColName1 = "";
  String chargeColName2 = "";
  String unitPrice;
  String ePrice;
  String totalLines;


  public MaterialRequestReport(Hashtable h, int i, Hashtable headerInfo) {
    this.line = h.get("LINE_NUM").toString();
    this.chargeType = h.get("CHARGE_TYPE").toString();
    this.item = h.get("ITEM_NUM").toString();
    this.partNum = h.get("PART_NUM").toString();
    this.qty = h.get("QTY").toString();
    this.crit = ((Boolean)h.get("CRIT_FLAG")).booleanValue();
    this.notes = h.get("NOTES").toString();
    this.type = h.get("ITEM_TYPE").toString();
    this.workArea = h.get("WORK_AREA").toString();
    this.itemDesc = h.get("ITEM_DESC").toString();
    this.delQty = "DEL_QTY";
    this.delFreq = "DEL_FREQ";
    this.delType = h.get("DELIVERY_TYPE").toString();
    this.delDate = h.get("DEL_DATE").toString();
    this.Dock = h.get("DOCK").toString();
    this.DelTo = h.get("DELIVER_TO").toString();
    this.po = h.get("PO"+chargeType).toString();
    this.refNum = h.get("RELEASE_NUM"+chargeType).toString();
    this.unitPrice = h.get("UNIT_PRICE").toString();
    String lineCurrencyID = " "+h.get("CURRENCY_ID").toString();
    totalLines = String.valueOf(i);

    String accSysId = (String)headerInfo.get("ACC_SYS_ID");
    String key1 = accSysId + chargeType;
    Hashtable packH = (Hashtable)headerInfo.get("PACK");
    Hashtable accSysTypeH = (Hashtable)packH.get(key1);
    Vector v = (Vector)accSysTypeH.get("LABELS");

    if(v.size() == 2){
      chargeColName1 = v.elementAt(0).toString();
      chargeColName2 = v.elementAt(1).toString();
    }else{
      chargeColName1 = v.elementAt(0).toString();
      chargeColName2 = "";
    }

    // compute extended price
    try{
      int q = new Integer(qty).intValue();
      float f = new Float(unitPrice).floatValue();
      double d = q*f;
      BigDecimal minAmt = new BigDecimal(d);
      minAmt = minAmt.setScale(4,BigDecimal.ROUND_HALF_UP);
      ePrice = minAmt.toString()+lineCurrencyID;
      BigDecimal unitAmt = new BigDecimal(f);
      unitAmt = unitAmt.setScale(4,BigDecimal.ROUND_HALF_UP);
      unitPrice = unitAmt.toString()+lineCurrencyID;
    }catch(Exception e){
      this.unitPrice = "";
      this.ePrice = "";
    }
  }

  public String getline(){return line;}
  public String getchargeType(){
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
  public String getitem(){return item;}
  public String getpartNum(){return partNum;}
  public String getqty(){return qty;}
  public String getcrit(){
    if(crit){
      return "Critical";
    }
    return "Normal";
  }
  public String getnotes(){
    if(BothHelpObjs.isBlankString(notes)){
      return "There are no notes for this line.";
    }
    return notes;
  }
  public String gettype(){return type;}
  public String getworkArea(){return workArea;}
  public String getitemDesc(){return itemDesc;}
  public String getprice(){return price;}
  public String getdelQty(){return delQty;}
  public String getdelFreq(){return delFreq;}
  public String getdelType(){return delType;}
  public String getdelDate(){return delDate;}
  public String getDock(){return Dock;}
  public String getDelTo(){return DelTo;}
  public String getpo(){return po;}
  public String getrefNum(){return refNum;}
  public String getchargeColName1(){return chargeColName1;}
  public String getchargeColName2(){return chargeColName2;}
  public String getunitPrice(){return unitPrice;}
  public String getePrice(){return ePrice;}
  public String getlineText() {return "Line "+line+" of "+totalLines;}

  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("line = getline");
    v.addElement("chargeType = getchargeType");
    v.addElement("item = getitem");
    v.addElement("partNum = getpartNum");
    v.addElement("qty = getqty");
    v.addElement("crit = getcrit");
    v.addElement("notes = getnotes");
    v.addElement("type = gettype");
    v.addElement("workArea = getworkArea");
    v.addElement("itemDesc = getitemDesc");
    v.addElement("price = getprice");
    v.addElement("delQty = getdelQty");
    v.addElement("delFreq = getdelFreq");
    v.addElement("delType = getdelType");
    v.addElement("delDate = getdelDate");
    v.addElement("Dock = getDock");
    v.addElement("DelTo = getDelTo");
    v.addElement("po = getpo");
    v.addElement("refNum = getrefNum");
    v.addElement("chargeColName1 = getchargeColName1");
    v.addElement("chargeColName2 = getchargeColName2");
    v.addElement("unitPrice = getunitPrice");
    v.addElement("ePrice = getePrice");
    v.addElement("lineText = getlineText");
    return v;
  }

  public static Vector getVector(Vector in, Hashtable header) {
    //System.out.println("\n\n"+in);
    Vector v = new Vector();
    for(int i=0;i<in.size();i++) {
      Hashtable h = (Hashtable)in.elementAt(i);
      v.addElement(new MaterialRequestReport(h, in.size(),header));
    }
    return v;
  }
}