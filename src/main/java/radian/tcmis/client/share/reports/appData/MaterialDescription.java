package radian.tcmis.client.share.reports.appData;

import java.util.*;
import javax.swing.table.*;

/** this is a template for setting up an object to be used as a table by
    the ERW reporting programs.*/
public class MaterialDescription {
  public static final int NUM_COLUMNS = 4;

  String item;
  String desc;
  String pkg;
  String grade;
  String partId;

  public MaterialDescription(String item,String desc,String pkg,String grade,String partId) {
    this.item = item;
    this.desc = desc;
    this.pkg = pkg;
    this.grade = grade;
    this.partId = partId;
  }

  public String getitem(){return item;}
  public String getdesc(){return desc;}
  public String getpkg(){return pkg;}
  public String getgrade(){return grade;}
  public String getpartId(){return partId;}

  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("item = getitem");
    v.addElement("desc = getdesc");
    v.addElement("pkg = getpkg");
    v.addElement("grade = getgrade");
    v.addElement("grade = getpartId");
    return v;
  }

  public static Vector getVector(Vector lineItems) {
    Vector v = new Vector();
    for(int i=0;i<lineItems.size();i++){
      Hashtable myH = (Hashtable)lineItems.elementAt(i);
      Vector dV = (Vector)myH.get("MAT_DESC");
      String item = myH.get("ITEM_NUM").toString();
      for(int j=0;j<dV.size();j++){
        Hashtable h = (Hashtable)dV.elementAt(j);
        String desc = h.get("DESC").toString();
        String pkg = h.get("PKG").toString();
        String grade = h.get("GRADE").toString();
        String partId = h.get("PART_ID").toString();
        v.addElement(new MaterialDescription(item,desc,pkg,grade,partId));
      }
    }
    return v;
  }
  public static Vector getVector(TableModel tm,int desc,int pkg,int grade) {
    Vector v = new Vector();
    for(int i=0;i<tm.getRowCount();i++){
      MaterialDescription md = new MaterialDescription("",tm.getValueAt(i,desc).toString(),tm.getValueAt(i,pkg).toString(),tm.getValueAt(i,grade).toString(),"");
      v.addElement(md);
    }
    return v;
  }
}
