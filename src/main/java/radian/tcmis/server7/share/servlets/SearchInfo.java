package radian.tcmis.server7.share.servlets;

import java.io.*;
import java.util.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.dbObjs.*;


public class SearchInfo extends TcmisServlet{
  //Client Version
  String action = null;
  String query = null;
  String searchBy = null;
  String facility = null;
  String personType = null;
  String locid = null;
  String manId = null;
  String manDesc = null;
  String type = null;
  Object[][] resultdata = null;
  Vector location = null;
  Vector facid = null;
  Vector facdesc = null;
  String catalog = null; //trong 3/20

  public SearchInfo(ServerResourceBundle b, TcmISDBModel d ){
     super();
     bundle = b;
     db = d;
  }

  protected void resetAllVars(){
    action = null;
    query = null;
    searchBy = null;
    facility = null;
    personType = null;
    locid = null;
    manId = null;
    manDesc = null;
    resultdata = null;
    location = null;
    facid = null;
    facdesc = null;
    type=null;
    catalog = null; //trong 3/20
  }

  protected void getResult(HttpInput httpI){
    action = httpI.getString("ACTION");
    query = httpI.getString("QUERY");
    searchBy = httpI.getString("SEARCHBY");
    personType = httpI.getString("PERSONTYPE");
    facility = httpI.getString("FACILITY");
    locid = httpI.getString("LOCID");
    type = httpI.getString("TYPE");
    catalog = httpI.getString("CATALOG");  //trong 3/20

    try{
      if (action.equals("SEARCH_ADDRESS")){;
        searchAddress();
      }else if(action.equals("SEARCH_PERSONNEL")) {
        searchPersonnel();
      }else if(action.equals("SEARCH_FACILITY")) {
        searchFacilities();
      }else if(action.equals("SEARCH_GROUPS")) {
        searchGroups();
      }else if(action.equals("GET_LOCATION")) {
        getLocation();
      }else if(action.equals("GET_LOCATION_FOR_FACILITY")) {
        getLocationForFacility();
      }else if(action.equals("GET_ALL_FACS")) {
        getXfacs();
      }else if(action.equals("SEARCH_MANUFACTURER")) {
        searchManufacturer();
      }else if(action.equals("NEXT_MANUFACTURER")) {
        getNextManufacturer();
      }else if(action.equals("SEARCH_FLOW_DOWN")) {
        searchFlowDown();
      }else if(action.equals("INITIAL_DATA")) {
        inditialData();
      }
    }catch(Exception e){e.printStackTrace();}
  }

  //Nawaz
  protected void inditialData()  throws Exception {
    Location l =null;
    try {
     //Nawaz 19/09/01 new changes to gui tables
     //resultdata = BothHelpObjs.getTableStyle();
    }catch(Exception e) {
      out.printDebug("Error on SearchAddress:getResult" + e.getMessage()+e.getLocalizedMessage()+e.toString());
      throw e;
       }
  }

  protected void print(TcmisOutputStreamServer out){
    try {
      out.printStart();
      printData();
      out.printEnd();

      out.printStart();
      printLocation();
      out.printEnd();

      out.printStart();
      printXfacs();
      out.printEnd();

    } catch (Exception e){e.printStackTrace();return;    }
  }

  protected void searchPersonnel()  throws Exception {
    try {
      Personnel p = new Personnel(db);
      resultdata = p.getAllNamesNew(personType,facility,query,searchBy);
    }catch(Exception e) {
      out.printDebug("Error on SearchPersonnel:getResult" + e.getMessage()+e.getLocalizedMessage()+e.toString());
      throw e;
    }
  }

  protected void searchAddress()  throws Exception {
    Location l =null;
    try {
      l = new Location(db);
      resultdata = l.getAllLocations(query, searchBy);
    }catch(Exception e) {
      out.printDebug("Error on SearchAddress:getResult" + e.getMessage()+e.getLocalizedMessage()+e.toString());
      throw e;
       }
  }

  protected void searchFacilities()  throws Exception {
    try {
      Facility f = new Facility(db);
      resultdata = f.getAllFacs(query, searchBy);
    }catch(Exception e) {
      out.printDebug("Error on SearchFacility:getResult" + e.getMessage()+e.getLocalizedMessage()+e.toString());
      throw e;
       }
  }

  protected void searchManufacturer()  throws Exception {
    try {
      resultdata = VVs.getAllManufacturers(db, query, searchBy);
    }catch(Exception e) {
      out.printDebug("Error on SearchManufacturer:getResult" + e.getMessage()+e.getLocalizedMessage()+e.toString());
      throw e;
       }
  }

  protected void searchFlowDown()  throws Exception {
    try {
      resultdata = VVs.getCatalogFlowDows(db, query, searchBy, type, catalog);   //trong 3/20
      //resultdata = VVs.getAllFlowDows(db, query, searchBy, type);
    }catch(Exception e) {
      out.printDebug("Error on SearchManufacturer:getResult" + e.getMessage()+e.getLocalizedMessage()+e.toString());
      throw e;
       }
  }

  protected void getNextManufacturer()  throws Exception {
    try {
      resultdata = VVs.getNextManufacturer(db, query);
    }catch(Exception e) {
      out.printDebug("Error on getNextManufacturer:getResult" + e.getMessage()+e.getLocalizedMessage()+e.toString());
      throw e;
       }
  }

  protected void searchGroups()  throws Exception {
    try {
      Personnel p = new Personnel(db);
      resultdata = p.getAllGroups(query, searchBy);
    }catch(Exception e) {
      out.printDebug("Error on SearchGroups:getResult" + e.getMessage()+e.getLocalizedMessage()+e.toString());
      throw e;
       }
  }

  protected void getLocationForFacility()  throws Exception {
    try {
    //Location
      Location loc = new Location(db);
      location = loc.getAddressForIdVector(locid);
    }catch(Exception e) {
      out.printDebug("Error on SearchGroups:getResult" + e.getMessage()+e.getLocalizedMessage()+e.toString());
      throw e;
       }
  }

  protected void getLocation()  throws Exception {
    try {
    //Location
      Facility FLA = new Facility(db);
      FLA.setFacilityId(locid);
      FLA.load();

      Location loc = new Location(db);
      location = loc.getAddressForIdVector((String) FLA.getShippingLocation());
    }catch(Exception e) {
      out.printDebug("Error on SearchGroups:getResult" + e.getMessage()+e.getLocalizedMessage()+e.toString());
      throw e;
       }
  }

  protected void getXfacs()  throws Exception {
    try {
      Facility fac = new Facility(db);
      Hashtable facTmp = fac.getAllFacXRef();
      facid = new Vector();
      facdesc = new Vector();
      for (Enumeration E=facTmp.keys();E.hasMoreElements();){
        Object tmpO = E.nextElement();
        facid.addElement(tmpO);
        facdesc.addElement(facTmp.get(tmpO));
      }
    } catch (Exception e1){ throw e1;}
  }

  protected void printData()  throws IOException {
    if (resultdata == null) return;

    for (int r=0;r<resultdata.length;r++){
      Object[] col = resultdata[r];
      for (int c=0;c<col.length;c++){
        String tmpStr = new String(col[c]==null?"":(col[c]).toString());
        out.println(tmpStr);
      }
      col = null;
    }
  }

   protected void printLocation()  throws IOException {
    if (location == null) return;
    for (int i=0;i<location.size();i++){
      out.println(location.elementAt(i).toString());
    }
  }

   protected void printXfacs()  throws IOException {
    if (facid != null){
       for (int i=0;i<facid.size();i++){
          out.println(facid.elementAt(i)==null?"":facid.elementAt(i).toString());
       }
    }
    out.printEnd();

    out.printStart();
    if (facdesc != null){
       for (int i=0;i<facdesc.size();i++){
          out.println(facdesc.elementAt(i)==null?"":facdesc.elementAt(i).toString());
       }
    }
  }

  protected  int getServInt(){
    return TcmisOutputStreamServer.SEARCH_INFO;
  }
}
