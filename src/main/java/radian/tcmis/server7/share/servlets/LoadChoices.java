package radian.tcmis.server7.share.servlets;

import java.util.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.dbObjs.*;


public class LoadChoices extends TcmisServletObj {
  //Client Version
  String function = null;
  Hashtable mySendObj = null;
  Hashtable catXref = null;
  Hashtable catXfac = null;

  public LoadChoices(ServerResourceBundle b, TcmISDBModel d ){
     super();
     bundle = b;
     db = d;
  }

  protected void resetAllVars(){
    function = null;
    catXref = null;
    catXfac = null;
  }

  protected void getResult() throws Exception {
    mySendObj    = new Hashtable();
    Hashtable rcvHash = (Hashtable)myRecvObj;
    if (rcvHash==null) System.out.println("NULLLLLLLLLL");

    if (((String)((Hashtable)myRecvObj).get("ACTION")).equalsIgnoreCase("CATALOG")){
        int userid = Integer.parseInt((String)((Hashtable)myRecvObj).get("USERID"));
        getCatalog(userid);
        mySendObj.put("CATXREF",catXref);
        mySendObj.put("CATXFAC",catXfac);
        //System.out.println("Cats on server:"+ catXref +"\n"+catXfac);
    } else {
        mySendObj.put("ERROR","WRONG ACTION");
    }
  }


  protected void getCatalog(int u) throws Exception {
     Facility ff = new Facility(db);
     catXref = ff.getAllCatXRef(u);
     catXfac = ff.getAllcatXFac(u);
  }
  
  protected void print(TcmisOutputStreamServer out){
    try {
      out.sendObject((Hashtable) mySendObj);
    }catch(Exception e){
      e.printStackTrace();
    }
  }

}
