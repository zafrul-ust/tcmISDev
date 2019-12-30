package radian.tcmis.server7.share.servlets;

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
                       String mat){
    this.itemID = new String(item);
    this.facility = new String(fac);
    this.partNum = new String(part);
    this.matDesc = new String(mat);

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
    return v;
  }
  public static Vector getVector(Object[][] oa){
    Vector v = new Vector();
    for(int i=0;i<oa.length;i++) {
      v.addElement(new CatalogSearch(oa[i][0].toString(),
                                     oa[i][1].toString(),
                                     oa[i][2].toString(),
                                     oa[i][3].toString()));
    }
    return v;
  }

}