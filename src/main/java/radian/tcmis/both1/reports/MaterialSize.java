package radian.tcmis.both1.reports;

import java.util.*;

/** this is a template for setting up an object to be used as a table by
    the ERW reporting programs.*/
public class MaterialSize {
  String partNum;
  String manuf;
  String desc;
  String trade;
  String grade;
  String part;
  String size;
  String unit;
  String pack;
  String dimension;
  String netWt;


  public MaterialSize(Hashtable h) {
    //System.out.println("\n\n================ got here materialsize: "+h);
    this.partNum = h.get("partNum").toString();
    this.manuf = h.get("manufT").toString();
    this.desc = h.get("matDescT").toString();
    this.trade = h.get("tradeT").toString();
    this.grade = h.get("gradeT").toString();
    this.part = h.get("manufPartT").toString();
    this.size = h.get("sizeT").toString();
    this.unit = h.get("unitC").toString();
    this.pack = h.get("packC").toString();
    this.dimension = h.get("dimT").toString();
    String tmp = h.get("netwtT").toString();
    tmp += " "+h.get("netwtC").toString();
    this.netWt = tmp;
  }

  public String getPartNum(){return partNum;}
  public String getManuf(){return manuf;}
  public String getDesc(){return desc;}
  public String getTrade(){return trade;}
  public String getGrade(){return grade;}
  public String getPart(){return part;}
  public String getSize(){return size;}
  public String getUnit(){return unit;}
  public String getPack(){return pack;}
  public String getDimension(){return dimension;}
  public String getNetWt(){return netWt;}

  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("partNum = getPartNum");
    v.addElement("manuf = getManuf");
    v.addElement("desc = getDesc");
    v.addElement("trade = getTrade");
    v.addElement("grade = getGrade");
    v.addElement("part = getPart");
    v.addElement("size = getSize");
    v.addElement("unit = getUnit");
    v.addElement("pack = getPack");
    v.addElement("dimension = getDimension");
    v.addElement("netWt = getNetWt");
    return v;
  }

  public static Vector getVector(Vector in) {
    Vector v = new Vector();
    for(int i=0;i<in.size();i++) {
      Hashtable h = (Hashtable)in.elementAt(i);
      h.put("partNum","Part "+(i+1));
      v.addElement(new MaterialSize(h));
    }
    return v;
  }
}