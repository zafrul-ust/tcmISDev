package radian.tcmis.server7.share.servlets;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import javax.servlet.http.*;


public class TcmisOutputStreamServer {

  /** All cgi objs to be called
  */
  public static final int CHECK_CLIENT = 1;
  public static final String[] CHECK_CLIENT_VALUES = new String[]{"CLIENTS","TOKEN"};

  public static final int CHECK_LOGON = 2;
  public static final String[] CHECK_LOGON_VALUES = new String[]{"PASSED","USER_ID","GROUPIDS","GROUPDESC","CONNECTION","GROUPFACS"};

  public static final int CHECK_VERSION = 3;
  public static final String[] CHECK_VERSION_VALUES = new String[]{"VERSIONS","RESTART","OBJECTS","LAST_VERSION","OBJECTS_ACTION"};

  public static final int CATALOG = 4;

  public static final String[] CATALOG_VALUES = new String[]{"USER_NAME","FAC_ID","FAC_NAME","FACXAPP","FAC_USER","APP_ID","APP_NAME",
                                                "REQUEST_RETURN","SPEC","MAT_ID","PRICE_REQUEST_INFO"};


  public static final int REQUEST = 5;
  public static final String[] REQUEST_VALUES = new String[]{"SHIP_ADDR","USER_NAME","PO_NUMBER","REQ_ITEMS","NOW_DATE",
                                                "FAC_ID","FAC_NAME","FACILITY","SHIP_TO","FORWARD_TO","REQ_APPROVER",
                                                "REQ_RELEASER","REQ_VIEW","REQ_VIEW_LABEL","TABLE_DATA","C_NUM_ITEM",
                                                "C_NUM_VALUE","C_POR_ITEM","C_POR_VALUE","MSG","APPR_NAMES","APPR_IDS","REL_NAMES","REL_IDS"};

  public static final int TRACK = 6;
  public static final String[] TRACK_VALUES = new String[]{"TABLE_DATA","GEN_TB","OOR_TB1","OOR_TB2","MM_TB1","MM_TB2","OK"};

  public static final int PERSONNEL = 7;
  public static final String[] PERSONNEL_VALUES = new String[]{"NEXT_NAMES","NEWID","MSG"};

  public static final int INVENTORY = 8;
  public static final String[] INVENTORY_VALUES = new String[]{"WARE_ID","WARE_NAME","PWARE_ID","PWARE_NAME","EXPIRE","PROMISSED","DATA","SUM","DATA1","DATA2","AUX"};

  public static final int USER_PROFILE = 9;
  public static final String[] USER_PROFILE_VALUES = new String[]{"DATA","APPRFACS","RELFACS","RELAMT","USER_FAC_NAME","USER_FAC_ID","UPMSG","PWCHANGE","USER_LOGON","GEN_ORDER_FACS","ADMIN_FACS","NEW_CHEM_FACS","WASTE_MANAGER","CREATE_REPORT"};

  public static final int SEARCH_INFO = 10;
  public static final String[] SEARCH_INFO_VALUES = new String[]{"DATA","LOCATION","FACIDS","FACNAMES"};

  public static final int CATALOG_TABLE = 11;
  public static final String[] CATALOG_TABLE_VALUES = new String[]{"DATA"};

  public static final int NEW_CHEM = 12;
  public static final String[] NEW_CHEM_VALUES = new String[]{"DATA","DATA2","DATA3","DATA4","VV_PKG","VV_SIZE","VV_PERIOD","USER_GROUPS_NAME","USER_GROUPS_DESC","USER_GROUPS_FACIDS","SPEC_IDS","FLOWS"};

  public static final int ADMIN = 13;
  //public static final String[] ADMIN_VALUES = new String[]{"FAC_IDS","FAC_NAMES","APP_IDS","APP_NAMES","FAC_X_APP","LOCATIONS","GROUPS","DATA","STATE_VECTOR","WAREHOUSE_ID","WAREHOUSE_NAME","OUT_DATA","ROLE_TYPE_DESC"};  //trong 3/31 add role_type_desc to this
  public static final String[] ADMIN_VALUES = new String[]{"FAC_IDS","FAC_NAMES","APP_IDS","APP_NAMES","FAC_X_APP","LOCATIONS","GROUPS","DATA","STATE_VECTOR","WAREHOUSE_ID","WAREHOUSE_NAME","OUT_DATA","ROLE_TYPE_DESC","STATUS"};  //trong 1-29-01 add status to this


  public static final int NEW_CHEM_TRACKING = 14;
  public static final String[] NEW_CHEM_TRACK_VALUES = new String[]{"DATA","MATERIAL_TABLE_DATA","ROLES","STATUS","APPROVERS","DATES","REASONS","APPROVERS_NUMS","NUM_RECS","ROLE_TYPE","SUPER_USER"};

  public static final int REPORT = 15;
  public static final String[] REPORT_VALUES = new String[]{"DATA","STATIC_DATA"};

  String inputData = null;
  String token = null;
  String ip = null;
  boolean firstTimeFlag = false;
  ObjectOutputStream oos = null;
  OutputStream out;
  HttpServletResponse resp;

  Hashtable result = new Hashtable();
  int nextValue = 0;
  String[] values = null;
  protected Vector elVec = null;

  public TcmisOutputStreamServer(int servletConst, HttpServletResponse r )  throws IOException {
    resp = r;
    out = (OutputStream) resp.getOutputStream();
    values = findServ(servletConst);
  }

  public TcmisOutputStreamServer(HttpServletResponse r)  throws IOException {
    resp = r;
    out = (OutputStream) resp.getOutputStream();
  }

  public void setToken(String  token) throws IOException {
    this.token = token;
  }

  public void setIp(String  ip){
    this.ip = ip;
  }

  public void setFirstTimeFlag(boolean flag){
    this.firstTimeFlag = flag;
  }

  public void setForceClear(boolean flag) throws IOException {
    //this.forceClear = flag;
  }

  public void println(Vector v) throws Exception{
    //System.out.println("\n\n**************** here in tcmisoutputstream: "+v.size());
    if(v == null) return;
    for(int x=0;x<v.size();x++){
      if(v.elementAt(x) instanceof Vector) {
        println((Vector)v.elementAt(x));
        continue;
      }
      if(v.elementAt(x) == null) {
        sendData("##DATA##","");
      }else{
        sendData("##DATA##",v.elementAt(x).toString());
      }
    }
  }
  public void println(String[] s) throws Exception{
    if (s==null) return;
    for(int x=0;x<Array.getLength(s);x++){
      sendData("##DATA##",s[x]);
    }
  }

  public void println(Object[][] oa) throws IOException {
    if (oa==null) return;
    for (int r=0;r<oa.length;r++){
      Object[] col = oa[r];
      for (int c=0;c<col.length;c++){
        String tmpStr = new String(col[c]==null?"":(col[c]).toString());
        sendData("##DATA##",tmpStr);
      }
      col = null;
    }
  }

  public void println(String x) throws IOException{
    sendData("##DATA##",x);
  }

  public void printStart() throws IOException {
    sendData("##START STREAM##",null);
  }

  public void printEnd() throws IOException  {
    sendData("##END STREAM##",null);
  }

   public void printDebug(String msg) {
    //sendData("##DEBUG##",msg);
  }

   public void printDenied() throws IOException {
    sendData("##DENIED##",null);
  }

   public void printClear(String x)   {
    //sendData("##CLEAR##",x);
  }

  protected void sendData(String type, String sendS)throws IOException{
    if (type.indexOf("DENIED")>0){
       result.put("DENIED","ON");
       return;
    }

    if (type.indexOf("##START STREAM##")>-1){
       elVec = new Vector();
       return;
    }
    if (type.indexOf("##END STREAM##")>-1){
       if (elVec==null || elVec.size()<1){
          elVec.addElement("");
       }
       //System.out.println("vector name:"+values[this.nextValue]+" vector:"+elVec);
       result.put(values[this.nextValue++],elVec);
       return;
    }

    if (sendS==null) sendS = new String("");
    
    elVec.addElement(sendS);

    //if (nextValue==values.length) sendObject();
  }

  public void sendObject(Hashtable h) throws IOException {
    result = h;
  }

  public void sendObject() throws IOException {
    OutputStream os = out;
    int length=0;
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    //System.out.println("REsult:"+result);
    if (result!=null){
      ObjectOutputStream oos = new ObjectOutputStream(bos);
      oos.writeObject(result);
      oos.flush();
      oos.close();
      length = bos.size();
    }

    //System.out.println("Sending from server "+length+" bytes.");
    // Write Reponse Header
    //resp.setContentType("text/plain");
    if (length>0) {
       resp.setContentLength(length);
       // Write the POST body
       bos.writeTo(os);
    }
    bos.close();
    os.flush();
    os.close();
  }



  public void close() throws IOException {
      // doing here to allow back combatible with old code
      sendObject();
  }

  protected String [] findServ(int con){
     //System.out.println("find int: "+con);
     String[] values = null;
     switch (con){
       case CHECK_CLIENT:
          values =  CHECK_CLIENT_VALUES;
          break;
       case CHECK_LOGON:
          values =  CHECK_LOGON_VALUES;
          break;
       case CHECK_VERSION:
          values =  CHECK_VERSION_VALUES;
          break;
       case CATALOG:
          values =  CATALOG_VALUES;
          break;
       case REQUEST:
          values =  REQUEST_VALUES;
          break;
       case TRACK:
          values =  TRACK_VALUES;
          break;
       case PERSONNEL:
          values =  PERSONNEL_VALUES;
          break;
       case INVENTORY:
          values =  INVENTORY_VALUES;
          break;
        case USER_PROFILE:
          values =  USER_PROFILE_VALUES;
          break;
        case SEARCH_INFO:
          values =  SEARCH_INFO_VALUES;
          break;
        case CATALOG_TABLE:
          values =  CATALOG_TABLE_VALUES;
          break;
        case NEW_CHEM:
          values =  NEW_CHEM_VALUES;
          break;
        case ADMIN:
          values =  ADMIN_VALUES;
          break;
        case NEW_CHEM_TRACKING:
          values =  NEW_CHEM_TRACK_VALUES;
          break;
        case REPORT:
          values =  REPORT_VALUES;
          break;
       default:
          values = null;
     }
     //System.out.println("values:"+values);
     return values;
  }

}






























