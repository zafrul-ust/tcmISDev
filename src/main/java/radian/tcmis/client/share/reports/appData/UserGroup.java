package radian.tcmis.client.share.reports.appData;

import java.util.*;

/** this is a template for setting up an object to be used as a "table" by
    the ERW reporting programs.*/
public class UserGroup {
  String facility;
  String groupName;
  String groupDesc;
  String none = "";


  /** this is a constructor for the object*/
  public UserGroup(String f,String n, String q ){
    facility = new String(f);
    groupName = new String(n);
    groupDesc = new String(q);
  }
  public UserGroup(String s){
    none = new String(s);
  }

  /** you must supply a GET method for every Class variable. the method cannot
      take any arguments. */
  public String getName() {
    return groupName;
  }
  public String getDesc(){
    return groupDesc;
  }
  public String getFacility(){
    return facility;
  }
  public String getNone(){
    return none;
  }

  /** this method returns a vector of name/method pairs where the name will be
      used as the column head for the object and the method is the method needed
      to get the value. The GET method cannot take any arguments.*/
  public static Vector getFieldVector(){
    Vector v = new Vector();
    v.addElement("groupName = getName");
    v.addElement("groupDesc = getDesc");
    v.addElement("facility  = getFacility");
    v.addElement("none      = getNone");
    return v;
  }

  /** this method returns a vector of this Object taken from a 2D object Array*/
  public static Vector getVector(Object[][] oa){
    Vector v = new Vector();
    for(int i=0;i<oa.length;i++){
      v.addElement(new UserGroup(oa[i][0].toString(),oa[i][1].toString(),oa[i][2].toString()));
    }
    return v;
  }

}