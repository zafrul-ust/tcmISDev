package radian.tcmis.both1.reports;

import java.util.*;

/** this is a template for setting up an object to be used as a table by
    the ERW reporting programs.*/
public class MrNotes {
  public static final int NUM_COLUMNS = 2;
  public static final int item_int = 2;
  public static final int notes_int = 6;

  String item;
  String notes;
  String line;

  public MrNotes(Hashtable h) {
    this.item = h.get("ITEM_NUM").toString();
    this.notes = h.get("NOTES").toString();
    this.line = h.get("LINE_NUM").toString();
  }

  public String getitem(){return item;}
  public String getnotes(){return notes;}
  public String getline(){return line;}

  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("item = getitem");
    v.addElement("notes = getnotes");
    v.addElement("line = getline");
    return v;
  }

  public static Vector getVector(Vector in) {
    Vector v = new Vector();
    for(int i=0;i<in.size();i++) {
      Hashtable h = (Hashtable)in.elementAt(i);
      v.addElement(new MrNotes(h));
    }
    return v;
  }
}