package radian.tcmis.client.share.reports.appData;

import javax.swing.*;
import java.util.*;

/** this is a template for setting up an object to be used as a "table" by
    the ERW reporting programs.*/
public class ChemListData {
  String casNumber;
  String chemicalName;

  /** this is a constructor for the object*/
  public ChemListData(String n, String q){
    casNumber = new String(n);
    chemicalName = new String(q);
  }

  /** you must supply a GET method for every Class variable. the method cannot
      take any arguments. */
  public String getcasNumber() {
    return casNumber;
  }
  public String getchemicalName(){
    return chemicalName;
  }

  /** this method returns a vector of name/method pairs where the name will be
      used as the column head for the object and the method is the method needed
      to get the value. The GET method cannot take any arguments.*/
  public static Vector getFieldVector(){
    Vector v = new Vector();
    v.addElement("casNumber = getcasNumber");
    v.addElement("chemicalName = getchemicalName");
    return v;
  }

  /** this method returns a vector of this Object taken from a 2D object Array*/
  public static Vector getVector(JTable j){
    Vector v = new Vector();
    for(int i=0;i<j.getRowCount();i++){
      v.addElement(new ChemListData(j.getValueAt(i,0).toString(),j.getValueAt(i,1).toString()));
    }
    return v;
  }

}