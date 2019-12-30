package radian.tcmis.client.share.reports.appData;

import java.util.*;

/** this is a template for setting up an object to be used as a "table" by
    the ERW reporting programs.*/
public class TableObjectTemplate {
  String s1;
  String s2;

  /** this is a constructor for the object*/
  public TableObjectTemplate(String n, String q){
    s1 = new String(n);
    s2 = new String(q);
  }

  /** you must supply a GET method for every Class variable. the method cannot
      take any arguments. */
  public String getS1() {
    return s1;
  }
  public String getS2(){
    return s2;
  }

  /** this method returns a vector of name/method pairs where the name will be
      used as the column head for the object and the method is the method needed
      to get the value. The GET method cannot take any arguments.*/
  public static Vector getFieldVector(){
    Vector v = new Vector();
    v.addElement("s1 = getS1");
    v.addElement("s2 = getS2");
    return v;
  }

  /** this method returns a vector of this Object taken from a 2D object Array*/
  public static Vector getVector(Object[][] oa){
    Vector v = new Vector();
    for(int i=0;i<oa.length;i++) {
      v.addElement(new TableObjectTemplate(oa[i][0].toString(),oa[i][1].toString()));
    }
    return v;
  }

}
