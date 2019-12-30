package radian.tcmis.both1.reports;

import java.util.*;
import radian.tcmis.both1.helpers.*;

/** this is a template for setting up an object to be used as a table by
    the ERW reporting programs.*/
public class Fate {
  String product;
  String wasteWater;
  String air;
  String solidWaste;
  String recycling;
  String hazardousWaste;
  String other;
  String part;
  String line;

  final int PRODUCT = 0;
  final int WASTEWATER = 1;
  final int AIR = 2;
  final int SOLIDWASTE = 3;
  final int RECYCLING = 4;
  final int HAZARDOUSWASTE = 5;
  final int OTHER = 6;
  final int PART = 7;
  final int LINE = 8;

  public Fate(Vector v) {
    this.product = (String)v.elementAt(PRODUCT);
    this.wasteWater = (String)v.elementAt(WASTEWATER);
    this.air = (String)v.elementAt(AIR);
    this.solidWaste = (String)v.elementAt(SOLIDWASTE);
    this.recycling = (String)v.elementAt(RECYCLING);
    this.hazardousWaste = (String)v.elementAt(HAZARDOUSWASTE);
    this.other = (String)v.elementAt(OTHER);
    this.part = (String)v.elementAt(PART);
    this.line = (String)v.elementAt(LINE);
  }

  public String getProduct(){return product;}
  public String getWasteWater(){return wasteWater;}
  public String getAir(){return air;}
  public String getSolidWaste(){return solidWaste;}
  public String getRecycling(){return recycling;}
  public String getHazardousWaste(){return hazardousWaste;}
  public String getOther(){return other;}
  public String getPart(){return part;}
  public String getLine(){return line;}

  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("product = getProduct");
    v.addElement("wasteWater = getWasteWater");
    v.addElement("air = getAir");
    v.addElement("solidWaste = getSolidWaste");
    v.addElement("recycling = getRecycling");
    v.addElement("hazardousWaste = getHazardousWaste");
    v.addElement("other = getOther");
    v.addElement("part = getPart");
    v.addElement("lineItem = getLine");
    return v;
  }

  public static Vector getVector(Vector in, Vector partDesc) {
    Vector v = new Vector();
    for(int i=0;i<in.size();i++) {
      Vector tmp = new Vector();
      Hashtable h = (Hashtable)in.elementAt(i);
      //putting the fate in the right order
      Enumeration E;
      for (E = h.keys(); E.hasMoreElements();){
        String key = (String)E.nextElement();
        if (key.startsWith("FATE")) {
          tmp.addElement(key);
        }
      }

      tmp = BothHelpObjs.sort(tmp);
      for (int j = 0; j < tmp.size(); j++) {
        Vector fateV = new Vector();
        Vector myFate = (Vector)h.get(tmp.elementAt(j));
        for (int k = 1; k < myFate.size(); k+=2) {
          fateV.addElement(myFate.elementAt(k));
        }
        fateV.addElement((String)partDesc.elementAt(j));
        //fateV.addElement("Part "+(j+1));
        Integer count = new Integer(i+1);
        fateV.addElement(count.toString());
        v.addElement(new Fate(fateV));
      }
    }
    return v;
  }
}