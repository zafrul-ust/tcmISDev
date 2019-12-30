package radian.tcmis.both1.reports;

import java.util.*;

/** this is a template for setting up an object to be used as a table by
    the ERW reporting programs.*/
public class ManifestLine {
  String manifestNo;
  String COD;


  public ManifestLine(Hashtable h) {
    //System.out.println("\n\n================ got here materialsize: "+h);
    this.manifestNo = (String)h.get("MANIFEST_LINE_NO");
    this.COD = (String)h.get("COD");
  }

  public String getManifestNo(){return manifestNo;}
  public String getCOD(){return COD;}

  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("manifestNo = getManifestNo");
    v.addElement("COD = getCOD");
    return v;
  }

  public static Vector getVector(Vector in) {
    Vector v = new Vector();
    for (int i = 0; i < in.size(); i++) {
      Hashtable h = (Hashtable)in.elementAt(i);
      v.addElement(new ManifestLine(h));
    }
    return v;
  }
}