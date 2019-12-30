package radian.tcmis.server7.share.servlets;

import java.io.*;
import java.util.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.dbObjs.*;


public class Catalog extends TcmisServlet{
  //Client Version
  String action = null;
  String userid = null;
  String facid = null;
  String colnum = null;
  String appid = null;
  String item = null;
  String fac = null;
  String work = null;
  String appr =null;
  Vector itemnum = null;
  Vector coldescr = null;
  Vector colval = null;
  String itemid_cat = null;
  String facid_cat = null;
  String partid_cat = null;
  String material_desc = null;
  Object[][] tabledata = null;
  protected String username = null;
  protected String username2 = null;
  protected String username3 = null;
  protected String[] facids = null;
  protected String[] facnames = null;
  protected Vector facxapp = null;
  protected Vector appids = null;
  protected Vector appnames = null;
  protected Vector rreturn = null;
  protected String specid = null;
  protected String matid = null;
  protected String userFac = null;
  protected String facPartNum = null;
  protected Vector priceInfo = null;

  //trong 4/8
  protected String accSysId = null;


  public Catalog(ServerResourceBundle b, TcmISDBModel d ){
     super();
     bundle = b;
     db = d;
  }

  protected void resetAllVars(){
    action = null;
    userid = null;
    username = null;
    username2 = null;
    username3 = null;
    facids = null;
    facnames = null;
    facxapp = null;
    appids = null;
    appnames = null;
    rreturn = null;
    tabledata = null;
    out = null;
    specid = null;
    matid = null;
    userFac = null;
    facPartNum = null;
    priceInfo = null;
  }

  protected void getResult(HttpInput httpI){
    action = httpI.getString("ACTION");
    userid = httpI.getString("USERID");
    facid = httpI.getString("FACID");
    colnum = httpI.getString("COLNUM");
    appid = httpI.getString("APPID");
    fac = httpI.getString("FAC");
    work = httpI.getString("WORK");
    appr = httpI.getString("APPR");
    item = httpI.getString("ITEM");
    itemnum = httpI.getVector("ITEMNUM");
    coldescr = httpI.getVector("COLDESCR");
    colval = httpI.getVector("COLVAL");
    itemid_cat = httpI.getString("ITEMID_CAT");
    facid_cat =  httpI.getString("FACID_CAT");
    partid_cat =  httpI.getString("PARTID_CAT");
    material_desc =  httpI.getString("MATERIAL_DESC");

    //trong 4/8
    accSysId = httpI.getString("ACC_SYS");

    try{
      if (action.equals("GET_APPLICATION")){
        if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"Catalog:getResult = Going application",getBundle());
        getApplication();
      }else if (action.equals("LOAD_ALL_DATA")) {
        if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"Catalog:getResult = Going loaddata",getBundle());
        loadAllData();
      }else if (action.equals("GET_SPEC_AND_MATERIAL")) {
        if (getBundle().getString("DEBUG").equals("true")) out.printDebug("Catalog:getResult = Going getspec");
        getSpecMat();
      }else if (action.equals("BUILD_REQUEST")) {
        if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"atalog:getResult = Going tabledata",getBundle());
        buildRequest();
      }else if (action.equals("PRICE_REQUEST")) {
        if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"atalog:getResult = Going tabledata",getBundle());
        priceRequest();
      }
    }catch(Exception e) {e.printStackTrace();}
  }

  protected void print(TcmisOutputStreamServer out){
    try {
      out.printStart();
      printUserNames();
      out.printEnd();

      out.printStart();
      printFacsIds();
      out.printEnd();

      out.printStart();
      printFacsNames();
      out.printEnd();

      out.printStart();
      printFacXApp();
      out.printEnd();

      out.printStart();
      if (userFac!=null) out.println(userFac);
      out.printEnd();

      out.printStart();
      printAppsIds();
      out.printEnd();

      out.printStart();
      printAppsNames();
      out.printEnd();

      out.printStart();
      printRequestReturn();
      out.printEnd();

      out.printStart();
      printSpec();
      out.printEnd();

      out.printStart();
      printMatId();
      out.printEnd();

      out.printStart();
      printPriceRequest();
      out.printEnd();


    }catch(Exception e){}
  }

  protected void getApplication() throws Exception {
  }
  
  protected void priceRequest() throws Exception {
    PriceRequest pr = new PriceRequest(db);
    pr.setFacId(facid);
    pr.setItemId(Integer.parseInt(item));
    pr.setRequestorId(Integer.parseInt(userid));
    pr.updateCatalogSnapshot();
    priceInfo = pr.getRequestPriceInfo();
  }

  protected void buildRequest() throws Exception{
      //2-19-01 patch until client is fix. The data is off coz the item does not have lpp
      //itemnum =  [2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0]
      //coldescr = [current_lppitemID, application, partID, qty, current_lpp, itemID, application, partID, qty, current_lpp, itemID, application, partID, qty]
      //colval =   [158140, F002, MIS-39014, 1, , 155504, F002, L3137AS351, 1, , 196189, F002, 500-01-1093, 1]
      //System.out.println("\n\n-------- before input data: "+itemnum+"\n"+coldescr+"\n"+colval);
      if (coldescr.size() > 0) {
        String firstValue = (String)coldescr.firstElement();
        if (firstValue.equalsIgnoreCase("current_lppItemID")) {
          //first remove current_lppitemID
          coldescr.removeElementAt(0);
          //add the right value there
          coldescr.insertElementAt("itemID",0);
          //add current_lpp to the front of the list coldescr and colval
          coldescr.insertElementAt("current_lpp",0);
          colval.insertElementAt("0",0);
        }
        //System.out.println("\n\n-------- after input data: "+coldescr+"\n");
      }
      
      // Build items hastable with hashtable elements
      Hashtable items = new Hashtable();
      Hashtable itemLine = new Hashtable();
      String last = itemnum.elementAt(0).toString();
      rreturn = new Vector();
      rreturn.addElement("");
      for (int i=0;i<itemnum.size();i++){
         if (!itemnum.elementAt(i).toString().equals(last)){
           items.put(new Integer(last),itemLine);
           itemLine = new Hashtable();
         }
         if (coldescr.elementAt(i).toString().equals("qty") || coldescr.elementAt(i).toString().equals("itemID")){
              itemLine.put(coldescr.elementAt(i).toString(),new Integer(colval.elementAt(i).toString()));
         } else {
              itemLine.put(coldescr.elementAt(i).toString(),colval.elementAt(i).toString());
         }
         out.printDebug("Going: i:"+i+"itenmu:"+ itemnum.elementAt(i).toString()+" itemline:"+coldescr.elementAt(i).toString());
         last = itemnum.elementAt(i).toString();
      }
      items.put(new Integer(last),itemLine);
      out.printDebug("Items:"+items.toString());
      SearchPScreen scr = new SearchPScreen(db);
      String[] temp = new String[2];

      //System.out.println("\n\n---------------- my line item: "+items);

      temp = scr.buildRequest((new Integer(userid)).intValue(),facid,items,accSysId,false);
      rreturn = new Vector();
      rreturn.addElement(temp[0]);
      rreturn.addElement(temp[1]);
  }

  protected void loadAllData() throws Exception  {
      SearchPScreen scr = new SearchPScreen(db);

      Hashtable result = new Hashtable();
      result = scr.loadAllDataNew((new Integer(userid)).intValue());

      username = new String((String) result.get("userName"));

      Enumeration E;
      Hashtable facXref = (Hashtable) result.get("facility");
      facids = new String[facXref.size()];
      facids[0]="";
      facnames = new String[facXref.size()];
      facnames[0]="";
      int i=0;
      for(E=facXref.keys();E.hasMoreElements();){
           String facNameTmp = new String((String) E.nextElement());
           String facIDTmp = new String((String)facXref.get(facNameTmp));
           facids[i] = facIDTmp ;
           facnames[i] = facNameTmp ;
           i++;
      }
      //application
      Hashtable appXref2D = (Hashtable) result.get("application");
      facxapp = new Vector();
      i=0;
      appids = new Vector();
      appnames = new Vector();
      for(E=appXref2D.keys();E.hasMoreElements();){
           String facIdTmp = new String((String) E.nextElement());
           Hashtable appXref = (Hashtable) appXref2D.get(facIdTmp);
           Enumeration E2;
           for (E2=appXref.keys();E2.hasMoreElements();){
             String appNameTmp = new String((String) E2.nextElement());
             String appIDTmp = new String((String)appXref.get(appNameTmp));
             appids.addElement(appIDTmp==null?"":appIDTmp) ;
             appnames.addElement(appNameTmp==null?"":appNameTmp);
             facxapp.addElement(facIdTmp==null?"":facIdTmp);
             i++;
           }
      }
      facxapp = new Vector();
      appids = new Vector();
      appnames = new Vector();

      if (appids.isEmpty()) appids.addElement("");
      if (appnames.isEmpty()) appids.addElement("");
      if (facxapp.isEmpty()) facxapp.addElement("");

      /*
      Personnel personnel = new Personnel(db);
      personnel.setPersonnelId((new Integer(userid)).intValue());
      personnel.load();
      username2 = new String(personnel.getFullName());
      username3 = new String(personnel.getFullName());
      */
      username2 = new String(username);
      username3 = new String(username);

      //preferred fac
      Personnel per = new Personnel(db);
      per.setPersonnelId((new Integer(userid)).intValue());
      per.load();
      userFac = per.getFacilityId();

      //System.out.println("\n\n\n my other queries: "+username+" fac "+facXref+" fac name "+facnames+" fac_user "+userFac);
  }

  protected void getSpecMat() throws Exception {
     ClientPart  cp = new ClientPart(db);
     cp.setItemId((new Integer(itemid_cat)).intValue());
     cp.setFacilityId(facid_cat);
     cp.setPartId(partid_cat);
     cp.load();
     String temp = cp.getSpecId();
     specid = new String((temp==null?"":temp));

     temp = cp.getMaterialId(material_desc).toString();

     matid =  new String((temp==null?"":temp));
     
  }


  void printFacsIds() throws IOException {
    if (facids == null) return;
    for(int i=0;i<facids.length;i++){
           out.println(facids[i]);
    }
  }

  void printFacsNames() throws IOException {
    if (facnames == null) return;
    for(int i=0;i<facnames.length;i++){
      out.println(facnames[i]);
    }

  }


   void printFacXApp() throws IOException {
    if (facxapp == null) return;
    for(int i=0;i<facxapp.size();i++){
           out.println(facxapp.elementAt(i).toString());
    }
  }


  void printAppsIds() throws IOException {
    if (appids == null) return;
    for(int i=0;i<appids.size();i++){
           out.println((String)appids.elementAt(i));
    }
  }

   void printAppsNames() throws IOException {
    if (appnames == null) return;
    for(int i=0;i<appnames.size();i++){
           out.println((String)appnames.elementAt(i));
    }
  }

   void printRequestReturn() throws IOException {
     if (rreturn == null) return;
     out.println(rreturn.elementAt(0).toString());
     out.println(rreturn.elementAt(1).toString());
   }

   void printSpec() throws IOException {
     if (specid == null) return;
     out.println(specid);
   }

   void printMatId() throws IOException {
     if (matid == null) return;
     out.println(matid);
   }

   void printUserNames() throws IOException {
     if (userid == null) return;
     if (userid.length() == 0) return;
     out.println(username==null?"":username); // one format
     out.println(username2 == null?"":username2);
     out.println(username3==null?"":username3);
   }

   void printPriceRequest(){
     try{
       if (priceInfo != null) out.println(priceInfo);
     }catch(Exception e){e.printStackTrace();}
   }

   protected  int getServInt(){
    return TcmisOutputStreamServer.CATALOG;
  }

 
}

































