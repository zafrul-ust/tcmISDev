package radian.tcmis.server7.share.servlets;

import java.util.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import radian.tcmis.server7.share.dbObjs.*;


public class InventoryObj extends TcmisServletObj{
  //Client Version
  String function = null;
  Hashtable mySendObj = null;
  Hashtable inData = null;

  //Table Main Tracking
  static final String[] colHeads = new String[]
        {"Item #","Part #","Part Desc.","Packaging","Manufacturer","Inventory",
         "Avail.","Held","On Order","In Purchasing","Facility","matID","specID","hasSpec","hasMSDS"};

  static final int []  colWidths = new int[]
       {   60   ,    94     ,  110      , 100      ,      100      ,     60    ,
           45      ,  45   ,   50      ,     70        ,0    ,   0  , 0     ,   0     ,   0    };

  static final String  colTypes = new String("191111222291111");

  static final Hashtable keyCols = new Hashtable();

  // Float
  final String[] colHeadsF1 = {"Status","Inventory","Qty","Lot","Exp Date","Ready to Ship"};
  static final int []  colWidthsF1 = new int[]
                              {  60    ,   50      ,25   ,  65  ,    65    ,   77      };
  final String[] colHeadsF2 = {"Status","Inventory","Qty","P.O.","Ready to Ship","Notes","real notes"};
  static final int []  colWidthsF2 = new int[]
                              {  60    ,   60      , 30   ,  60  ,     80       ,   50      , 0};
  static final String  colTypesF1 = new String("11213");
  static final String  colTypesF2 = new String("1121311");

  static final Hashtable keyColsF2 = new Hashtable();

  public InventoryObj(ServerResourceBundle b, TcmISDBModel d ){
     super();
     bundle = b;
     db = d;
  }

  protected void resetAllVars(){
    function = null;
    inData = null;

    keyCols.put("ITEM_ID","0");
    keyCols.put("HUB","5");
    keyCols.put("PACKAGING","3");
    keyCols.put("FAC_ID","10");
    keyCols.put("MAT_ID","11");
    keyCols.put("SPEC_ID","12");
    keyCols.put("SPEC_ONLINE","13");
    keyCols.put("MSDS_ONLINE","14");
    keyCols.put("PART_NUM","1");

    keyColsF2.put("NOTES2","5");
    keyColsF2.put("REAL_NOTES2","6");
  }

  protected void getResult(){
    mySendObj = new Hashtable();
    // using myRecvObj you get the function the way you want
    inData = (Hashtable)myRecvObj;
    function = inData.get("ACTION").toString();
    if(function.equalsIgnoreCase("GET_TABLE_DATA")) {
      doSearch();
    } else if(function.equalsIgnoreCase("GET_FLOAT_DATA")) {
      doFloat();
    } else if(function.equalsIgnoreCase("GET_PREF_WAREHOUSE")) {
      doPrefWareHouse();
    } else if (function.equalsIgnoreCase("GET_INVENTORY_ITEM_DETAIL")) {
      getInventItemDetail();
    }else if (function.equalsIgnoreCase("GET_INITIAL_DATA")) {
      getInitialData();
    }
  }

  void getInitialData() {
    try {
      InventoryTable tt = new InventoryTable(db);
      mySendObj.put("DATA",tt.getInitialData(inData));
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  void getInventItemDetail() {
    try {
      String itemID = (String)inData.get("ITEM");
      InventoryTable tt = new InventoryTable(db);
      mySendObj.put("DATA",tt.getInventItemDetail(itemID));
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
      //System.out.println("\n\n--------- inventory: "+mySendObj);
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  void doSearch(){
    try{
      InventoryTable tt = new InventoryTable(db);
      Hashtable h = tt.getTableDataNew((Hashtable) inData.get("DATA"));
      mySendObj.put("TABLE_DATA",(Hashtable) h.get("TABLE_DATA"));
      mySendObj.put("EXPIRE",BothHelpObjs.makeBlankFromNull((String) h.get("EXPIRE")));
      mySendObj.put("EXPIRE2",BothHelpObjs.makeBlankFromNull((String) h.get("EXPIRE2")));
      mySendObj.put("PROMISSED",BothHelpObjs.makeBlankFromNull((String) h.get("PROMISSED")));
      //System.out.println(mySendObj.get("EXPIRE")+ " "+mySendObj.get("EXPIRE2")+ " "+mySendObj.get("PROMISSED"));
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  void doFloat(){
    try{
      InventoryFloat tt = new InventoryFloat(db);
      mySendObj.put("SEARCH_DATA",tt.getData((Hashtable) inData.get("DATA")));
      mySendObj.put("COL_HEADS1",colHeadsF1);
      mySendObj.put("COL_TYPES1",colTypesF1);
      mySendObj.put("COL_WIDTHS1",colWidthsF1);
      mySendObj.put("COL_HEADS2",colHeadsF2);
      mySendObj.put("COL_TYPES2",colTypesF2);
      mySendObj.put("COL_WIDTHS2",colWidthsF2);
      mySendObj.put("KEY_COLS2",keyColsF2);
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  void doPrefWareHouse(){
    try{
      Facility f = new Facility(db);
      Hashtable h = f.getAllFacsNew();
      mySendObj.put("PREF_WAREHOUSE",h);
      //Nawaz 19/09/01 new changes to gui tables
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());

    }catch(Exception e){
      e.printStackTrace();
    }
  }/*
  void doPrefWareHouse(){
    try{
      Hashtable h = new Hashtable();
      Facility f = new Facility(db);
      Vector facs = f.getAllFacs();
      for(int i=0;i<facs.size();i++){
        f = (Facility)facs.elementAt(i);
        h.put(f.getFacilityId(),f.getPreferredWare());
      }
      mySendObj.put("PREF_WAREHOUSE",h);
    }catch(Exception e){
      e.printStackTrace();
    }
  }  */

  protected void print(TcmisOutputStreamServer out){
    try {
      out.sendObject((Hashtable) mySendObj);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
} //end of class