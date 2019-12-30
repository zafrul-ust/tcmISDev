package radian.tcmis.client.share.reports.appData;

import java.util.*;

public class CatalogSearch {
  String itemID;
  String facility;
  String partNum;
  String matDesc;
  String grade;
  String manufacturer;
  String packaging;
  float price;
  String type;
  String shelfLife;
  String status;

  /** this is a constructor for the object*/
  public CatalogSearch(String item, String fac,String part,
                       String mat,String grade,String manu,
                       String pack, float price,String type,
                       String sLife,String stat){
    this.itemID = new String(item);
    this.facility = new String(fac);
    this.partNum = new String(part);
    this.matDesc = new String(mat);
    this.grade = new String(grade);
    this.manufacturer = new String(manu);
    this.packaging = new String(pack);
    this.price = price;
    this.type = new String(type);
    this.shelfLife = new String(sLife);
    this.status = new String(stat);
  }
  

  public String getItemID(){
    return itemID;
  }
  public String getFacility(){
    return facility;
  }
  public String getPartNum(){
    return partNum;
  }
  public String getMatDesc(){
    return matDesc;
  }
  public String getGrade(){
    return grade;
  }
  public String getManufacturer(){
    return manufacturer;
  }
  public String getPackaging(){
    return packaging;
  }
  public float getPrice(){
    return price;
  }
  public String getType(){
    return type;
  }
  public String getShelfLife(){
    return shelfLife;
  }
  public String getStatus(){
    return status;
  }

  /** this method returns a vector of name/method pairs where the name will be
      used as the column head for the object and the method is the method needed
      to get the value. The GET method cannot take any arguments.*/
  public static Vector getFieldVector(){
    Vector v = new Vector();
    v.addElement("itemID = getItemID");
    v.addElement("facility = getFacility");
    v.addElement("partNum = getPartNum");
    v.addElement("matDesc = getMatDesc");
    v.addElement("grade = getGrade");
    v.addElement("manufacturer = getManufacturer");
    v.addElement("packaging = getPackaging");
    v.addElement("price = getPrice");
    v.addElement("type = getType");
    v.addElement("shelfLife = getShelfLife");
    v.addElement("status = getStatus");
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
      v.addElement(new CatalogSearch(oa[i][0].toString(),
                                     oa[i][1].toString(),
                                     oa[i][2].toString(),
                                     oa[i][3].toString(),
                                     oa[i][4].toString(),
                                     oa[i][5].toString(),
                                     oa[i][6].toString(),
                                     f,
                                     oa[i][8].toString(),
                                     oa[i][9].toString(),
                                     oa[i][10].toString()));
    }
    return v;
  }   

}
