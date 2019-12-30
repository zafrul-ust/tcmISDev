package radian.tcmis.both1.reports;

import java.util.*;

/** this is a template for setting up an object to be used as a table by
    the ERW reporting programs.*/
public class Specification {
  String ID;
  String revision;
  String amendment;
  String title;

  public Specification(Hashtable h) {
    this.ID = h.get("NAME").toString();
    this.revision = h.get("REVISION").toString();
    this.amendment = h.get("AMENDMENT").toString();
    this.title = h.get("TITLE").toString();
  }

  public String getID(){return ID;}
  public String getRevision(){return revision;}
  public String getAmendment(){return amendment;}
  public String getTitle(){return title;}

  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("ID = getID");
    v.addElement("revision = getRevision");
    v.addElement("amendment = getAmendment");
    v.addElement("title = getTitle");
    return v;
  }

  public static Vector getVector(Vector in) {
    Vector v = new Vector();
    for(int i=0;i<in.size();i++) {
      Hashtable h = (Hashtable)in.elementAt(i);
      v.addElement(new Specification(h));
    }
    return v;
  }
}