package radian.tcmis.server7.share.servlets;

import java.util.*;
import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.both1.helpers.*;



public class HttpInput {
  protected boolean authenticated;
  protected Hashtable inputData;
  protected Hashtable inputHash;
  protected static final String NAMEDELIM = BothHelpObjs.NAME_VALUE_DEL;
  protected static final String NAMEVALUEDELIM = BothHelpObjs.NAME_NAME_DEL;
  protected static final String VALUEDELIM = BothHelpObjs.VALUE_VALUE_DEL;
  protected static final String HASHTOKEN = new String("TOKEN");
  protected String ip = null;
  protected String token = null;
  protected TcmISDBModel db = null;
  protected Object sentObj = null;


  // Constructors
  public HttpInput(Hashtable s,TcmISDBModel db){
    inputData = s;
    setDB(db);
    //System.out.println("httpI set data:"+s);
    setData();
    //System.out.println("httpI set data done:");

  }
  public HttpInput(String i,Hashtable s,TcmISDBModel db) {
    inputData = s;
    ip = i;
    //System.out.println("httpI set data:"+s);
    setDB(db);
    setData();
  }

  public HttpInput(Hashtable s,String i,String t,TcmISDBModel db) {
    inputData = s;
    //System.out.println("httpI set data:"+s);
    ip = i;
    setDB(db);
    inputHash = createHashtable();
    setToken(t);
    authenticated = setAuthentication();
  }

  void setData(){
    inputHash = createHashtable();
    authenticated = setAuthentication();
  }

  void setToken(String token){
    inputHash.remove("TOKEN");
    inputHash.put("TOKEN",token);
  }

  //trong 10-4
  public String getClientIP() {
    return inputData.get("CLIENT_IP").toString();
  }

  // Private methods
  private Hashtable createHashtable() {
     Hashtable temp = new Hashtable();
     temp.put("TOKEN",inputData.get("TOKEN"));
     temp.put("IP",inputData.get("CLIENT_IP"));

     if (inputData.get("OBJECT") instanceof java.util.Hashtable){
        Hashtable temp2 =  (Hashtable) inputData.get("OBJECT");
        Enumeration E;
        for (E=temp2.keys();E.hasMoreElements();){
           Object k = E.nextElement();
           if ( k instanceof java.lang.String || k instanceof java.lang.Integer){
              Object v = temp2.get(k);
              if ( v instanceof java.lang.String || v instanceof java.lang.Integer){
                 Vector vv = new Vector();
                 if (v.toString().indexOf(VALUEDELIM) > -1){
                    vv = getVectorFromData(v.toString());
                 } else {
                    vv.addElement(v.toString()==" "?"":v.toString());
                 }
                 temp.put(k,vv);
                 //System.out.println("k and v:"+k+" "+v.toString());
              }
           }
        }
     }

     return temp;

  }

  protected Vector getVectorFromData(String v){
     /* Vector temp = new Vector();
      StringTokenizer values = new StringTokenizer(v,VALUEDELIM,false);
      while(values.hasMoreElements()) {
        String tmpS = values.nextElement().toString();
        temp.addElement(tmpS.equals(" ")?new String(""):tmpS.toString());
      }
      return temp;
      */
      Vector temp = new Vector();
      StringBuffer sb = new StringBuffer(v);
      String hold = "";
      String last = "";
      boolean yo = false;
      for(int i=0;i<sb.length();i++){
        yo = true;
        char[] cx = new char[]{sb.charAt(i)};
        String s = new String(cx);
        last = new String(s);
        if(s.equals(VALUEDELIM)){
          temp.addElement(hold);
          hold = "";
        }else{
          hold = hold + s;
        }
      }
      if(yo && !hold.equals(VALUEDELIM) && hold.length()>0){
        temp.addElement(hold);
      }
      return temp;
  }

  public String getString(String key){
  //System.out.println("httpI get string:"+key);
     String res = new String("");
     if (inputHash.containsKey(key)){
       if (inputHash.get(key) != null && inputHash.get(key) instanceof java.util.Vector)
         if (((Vector) inputHash.get(key)).size()>0)
           res = new String((((Vector) inputHash.get(key)).elementAt(0)).toString());
     }
     //if (ServerConstants.DEBUG)HelpObjs.monitor(0,"##DEBUG##HttpInput: return "+key+"="+res);
     return (res.equals(" ")?new String(""):res);
     //return res;
  }

  public Vector getVector(String key){
     Vector res = new Vector();
     if (inputHash.containsKey(key)){
       if (inputHash.get(key) != null && inputHash.get(key) instanceof java.util.Vector) {
           res = (Vector) inputHash.get(key);
       }
     }
     //if (ServerConstants.DEBUG)HelpObjs.monitor(0,"##DEBUG##HttpInput: return "+key+"="+res);
     return res;
  }

  private boolean setAuthentication() {
    boolean flag = false;
    try {
       Authenticator auth = new Authenticator(db);
       flag = auth.checkAuth((String) inputData.get("TOKEN"),(String) inputData.get("CLIENT_IP"));
    } catch (Exception e) { e.printStackTrace();}
    return flag;
  }

  // Public methods
  public Hashtable getHash() {
    return this.inputHash;
  }

  public Object getSentObj() {

    return (Object) inputData.get("OBJECT");
  }

  public boolean isAuthenticated() {
    return this.authenticated;
  }

  public Hashtable getInputData() {
    return this.inputData;
  }

  public void setIp(String ip){
    this.ip = ip;
  }

  public void setDB(TcmISDBModel db){
    this.db = db;
  }
  
}

































