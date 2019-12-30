package radian.tcmis.both1.reports;

import java.util.*;

/** this is a template for setting up an object to be used as a table by
    the ERW reporting programs.*/
public class QualityPackaging {
  String desc;

  public QualityPackaging(Hashtable h) {
    this.desc = h.get("DESC").toString();
  }
  public QualityPackaging() {
    this.desc = "";
  }

  public String getDesc(){return desc;}

  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("desc = getDesc");
    return v;
  }

  public static Vector getVector(Hashtable in, String type) {
    Vector v = new Vector();
    Vector tmp = (Vector)in.get(type);
    if (tmp.size() > 0) {
      for(int i=0;i<tmp.size();i++) {
        Hashtable h = new Hashtable();
        String d = (String)tmp.elementAt(i);
        h.put("DESC",d);
        v.addElement(new QualityPackaging(h));
      }
    }else {
      v.addElement(new QualityPackaging());
    }
    return v;
  }
}