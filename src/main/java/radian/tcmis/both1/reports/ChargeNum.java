package radian.tcmis.both1.reports;

import java.util.*;
import radian.tcmis.both1.helpers.*;

/** this is a template for setting up an object to be used as a "table" by
    the ERW reporting programs.*/
public class ChargeNum {
  String item;
  String cNum;
  String cNum2;
  String percent;
  String none;
  String line;

  /** this is a constructor for the object*/
  public ChargeNum(String item, String cnum1,String cnum2,String percent,String line){
    this.item = new String(item);
    cNum = cnum1;
    cNum2 = cnum2;
    this.percent = percent;
    none = "";
    this.line = line;
  }
  public ChargeNum(String item,String line){
    this.item = item;
    cNum = "";
    cNum2 = "";
    this.percent = "";
    none = "There is no charge information for this line.";
    this.line = line;
  }

  /** you must supply a GET method for every Class variable. the method cannot
      take any arguments. */
  public String getCNum(){return cNum;}
  public String getCNum2(){return cNum2;}
  public String getPercent(){return percent;}
  public String getItem(){return item;}
  public String getNone(){return none;}
  public String getLine(){return line;}

  /** this method returns a vector of name/method pairs where the name will be
      used as the column head for the object and the method is the method needed
      to get the value. The GET method cannot take any arguments.*/
  public static Vector getFieldVector(){
    Vector v = new Vector();
    v.addElement("cNum = getCNum");
    v.addElement("cNum2 = getCNum2");
    v.addElement("percent = getPercent");
    v.addElement("item = getItem");
    v.addElement("none = getNone");
    v.addElement("line = getLine");
    return v;
  }

  /** this method returns a vector of this Object taken from a 2D object Array*/
  public static Vector getVector(Vector lineItems){
    Vector v = new Vector();
    for(int i=0;i<lineItems.size();i++){
      Hashtable h = (Hashtable)lineItems.elementAt(i);

      String item = h.get("ITEM_NUM").toString();
      String tmpLine = h.get("LINE_NUM").toString();
      String type = h.get("CHARGE_TYPE").toString();
      Hashtable h2 = new Hashtable();
      Vector v2 = new Vector();
      if(type.equalsIgnoreCase("i")){
        v2 = (Vector)h.get("CHARGE_NUM_INDIRECT");
      }else if(type.equalsIgnoreCase("d")){
        v2 = (Vector)h.get("CHARGE_NUM_DIRECT");
      }else{
        //System.out.println("\n\n----- shouldn't be here");
      }
      boolean hasOne = false;
      for(int j=0;j<v2.size();j++){
        Hashtable cHash = (Hashtable)v2.elementAt(j);
        if(!cHash.containsKey("PERCENTAGE"))continue;
        String percentage = cHash.get("PERCENTAGE").toString();
        if(BothHelpObjs.isBlankString(percentage))continue;
        String cnum1 = cHash.get("ACCT_NUM_1").toString();
        String cnum2 = "";
        if(cHash.containsKey("ACCT_NUM_2")){
          cnum2 = cHash.get("ACCT_NUM_2").toString();
        }else{
          cnum2 = "";
        }
        hasOne = true;
        v.addElement(new ChargeNum(item,cnum1,cnum2,percentage,tmpLine));
      }
      if(!hasOne){
        //System.out.println("doesn't have one");
        v.addElement(new ChargeNum(item,tmpLine));
      }
    }
    // big,stinkin',kludge
    //v.addElement(new ChargeNum("99999", "cnum1","cnum2","100"));
    return v;
  }
}