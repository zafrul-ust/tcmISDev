package radian.tcmis.server7.share.servlets;

import java.io.*;
import java.util.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.dbObjs.*;


public class Inventory extends TcmisServlet{
  //Client Version
  String action = null;
  String userid = null;
  Vector datainput = null;
  Vector wareids = null ;
  Vector warenames = null;
  Vector pwareids = null ;
  Vector pwarenames = null;
  Object[][] tabledata = null;
  String expire = null;
  String promissed = null;
  Vector sum = null;
  String facid = null;
  String auxno = null;
  String colnum1 = null;
  String colnum2 = null;
  boolean fac = false;
  String itemid = null;
  String partnum = null;

  Object[][] data1 = null;
  Object[][] data2 = null;
  String[] aux = null;

  public Inventory(ServerResourceBundle b, TcmISDBModel d ){
     super();
     bundle = b;
     db = d;
  }

  protected void resetAllVars(){
    action = null;
    userid = null;
    out = null;
    wareids = null ;
    warenames = null;
    pwareids = null ;
    pwarenames = null;
    sum = null;
    tabledata = null;
    expire = null;
    promissed = null;
  }

  protected void getResult(HttpInput httpI){
    try {
      action = httpI.getString("ACTION");
      userid = httpI.getString("USERID");
      datainput = httpI.getVector("DATAINPUT");
      itemid =  httpI.getString("ITEMID");
      facid =  httpI.getString("FACID");
      fac =    httpI.getString("FAC").equals("1");
      auxno =  httpI.getString("AUXNO");
      colnum1 =  httpI.getString("COLNUM1");
      colnum2 =  httpI.getString("COLNUM2");
      partnum =  httpI.getString("PARTNUM");

      if(action.equals("GET_WARE")){
        if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"Track:getResult = Going getware",getBundle());
        getWare();
      }else if (action.equals("GET_PREF_WARE")){
        if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"Track:getResult = Going getpware",getBundle());
        getPWare();
      }else if (action.equals("GET_TABLE_DATA")){
        if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"Track:getResult = Going getdata",getBundle());
        getTableData();
      }else if (action.equals("GET_SUM")){
        if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"Track:getResult = Going getdata",getBundle());
       // System.out.println("getresult");
        getSum();
      }else if (action.equals("GET_FLOAT_DATA")){
        if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"Track:getResult = Going getdata",getBundle());
        getFloatData();
      }
    } catch (Exception e){e.printStackTrace();return;}
  }

  protected void print(TcmisOutputStreamServer out){
    try {
      out.printStart();
      printWare();
      out.printEnd();

      out.printStart();
      printPWare();
      out.printEnd();

      out.printStart();
      printData();
      out.printEnd();

      out.printStart();
      printSum();
      out.printEnd();

      out.printStart();
      printData1();
      out.printEnd();

      out.printStart();
      printData2();
      out.printEnd();

      out.printStart();
      printAux();
      out.printEnd();

    }catch(Exception e){}
  }

  protected void getWare() throws Exception {
    InventTable inv = new InventTable(db);
    Hashtable result = inv.loadWare();
    Hashtable wareXref = (Hashtable) result.get("warehouse");
    wareids = new Vector();
    warenames = new Vector();
    int i=0;
    for(Enumeration E=wareXref.keys();E.hasMoreElements();){
      String wareNameTmp = new String((String) E.nextElement());
      String wareIDTmp = new String((String)wareXref.get(wareNameTmp));
      wareids.addElement(wareIDTmp) ;
      warenames.addElement(wareNameTmp);
      i++;
    }
  }

  protected void printWare() throws IOException {
     if (wareids != null) {
       for(int i=0;i<wareids.size();i++){
           out.println(wareids.elementAt(i).toString());
       }
     }
     out.printEnd();
     out.printStart();
     if (warenames != null) {
       for(int i=0;i<warenames.size();i++){
           out.println(warenames.elementAt(i).toString());
       }
     }
  }

  protected void getPWare() throws Exception {
    InventTable inv = new InventTable(db);
    Hashtable result = inv.loadFacs((new Integer(userid)).intValue());
    Hashtable facXware = (Hashtable) result.get("preferred_ware");
    int i=0;
    pwareids = new Vector();
    pwarenames = new Vector();
    for(Enumeration E=facXware.keys();E.hasMoreElements();){
      String pwareNameTmp = new String(E.nextElement().toString());
      String pwareIDTmp = new String(facXware.get(pwareNameTmp).toString());
      pwareids.addElement(pwareIDTmp) ;
      pwarenames.addElement(pwareNameTmp);
      i++;
    }
  }

  protected void printPWare()  throws IOException {
     if (pwareids != null) {
       for(int i=0;i<pwareids.size();i++){
           out.println(pwareids.elementAt(i).toString());
       }
     }
     out.printEnd();
     out.printStart();
     if (pwarenames != null) {
       for(int i=0;i<pwarenames.size();i++){
           out.println(pwarenames.elementAt(i).toString());
       }
     }
  }

  protected void getTableData() throws Exception {
    InventTable inv = new InventTable(db);
    int colnum = (new Integer(datainput.elementAt(0).toString())).intValue();
    String facid = datainput.elementAt(1).toString();
    String preffac = datainput.elementAt(2).toString();
    String ware = datainput.elementAt(3).toString();
    String item = datainput.elementAt(4).toString();
    String expval = datainput.elementAt(5).toString();
    String promval = datainput.elementAt(6).toString();
    boolean fac = datainput.elementAt(7).toString().equals("1")?true:false;
    boolean wareb = datainput.elementAt(8).toString().equals("1")?true:false;
    boolean eD = datainput.elementAt(9).toString().equals("1")?true:false;
    boolean pD = datainput.elementAt(10).toString().equals("1")?true:false;
    boolean oH = datainput.elementAt(11).toString().equals("1")?true:false;
    boolean oO  = datainput.elementAt(12).toString().equals("1")?true:false;
    Hashtable result = inv.getTableData(colnum,facid,preffac,ware,item,expval,promval,fac,wareb,eD,pD,oH,oO);
    tabledata = (Object [][])result.get("DATA");
    if (result.get("EXPIRE") !=null) expire = new String(result.get("EXPIRE").toString());
    if (result.get("PROMISSED") !=null)promissed = new String(result.get("PROMISSED").toString());
  }

  protected void printData()  throws IOException {

    if (expire!=null) out.println(expire);
    out.printEnd();
    
    out.printStart();
    if (promissed!=null) out.println(promissed);
    out.printEnd();

    out.printStart();
    if (tabledata == null) return;

    for (int r=0;r<tabledata.length;r++){
      Object[] col = tabledata[r];
      for (int c=0;c<col.length;c++){
        String tmpStr = new String(col[c]==null?"":(col[c]).toString());
        out.println(tmpStr);
      }
      col = null;
    }
  }

  protected void getSum() throws Exception {
//System.out.println("sum 1="+sum);
      radian.tcmis.server7.share.dbObjs.Inventory inv =  new radian.tcmis.server7.share.dbObjs.Inventory(db);
      //System.out.println("sum2="+sum);
      Integer II = new Integer(itemid);
     // System.out.println("sum3="+sum);
      sum = inv.retrieveSum("item_id = " + II.toString() + " ",null,null,null);
     // System.out.println("sum="+sum);


  }

  void printSum()  throws IOException {
    //System.out.println("p sum1");
     if (sum == null) return;
    // System.out.println("p sum 2");
     for (int i=0;i<sum.size();i++){
       out.println(sum.elementAt(i).toString());
     //  System.out.println("sum"+i+"="+sum.elementAt(i).toString());
     }
  }

  protected void getFloatData() throws Exception {
       InvFloatScreen inv = new InvFloatScreen(db);
       // // System.out.println("************ here: "+partnum);
       Hashtable result = inv.getTableData((new Integer(colnum1)).intValue(),(new Integer(colnum2)).intValue(),itemid,facid,fac,partnum);
       aux = inv.getFieldData((new Integer(auxno)).intValue(),itemid,facid,fac);
       data1 = (Object [][])result.get("DATA1");
       data2 = (Object [][])result.get("DATA2");
    //   System.out.println("Data1="+data1.length);
    //   System.out.println("Data2="+data2.length);
  }

  void printData1()  throws IOException {
    if (data1==null) return;
    for (int r=0;r<data1.length;r++){
      Object[] col = data1[r];
      for (int c=0;c<col.length;c++){
        String tmpStr = new String(col[c]==null?"":(col[c]).toString());
        out.println(tmpStr);
      }
      col = null;
    }
  }

  void printData2()  throws IOException {
    if (data2==null) return;
    for (int r=0;r<data2.length;r++){
      Object[] col = data2[r];
      for (int c=0;c<col.length;c++){
        String tmpStr = new String(col[c]==null?"":(col[c]).toString());
        out.println(tmpStr);
      }
      col = null;
    }
  }

  void printAux()  throws IOException {
    if (aux==null) return;
    for(int i=0;i<aux.length;i++){
      out.println(aux[i]==null?"":aux[i]);
    }
  }

  protected  int getServInt(){
    return TcmisOutputStreamServer.INVENTORY;
  }
}

































