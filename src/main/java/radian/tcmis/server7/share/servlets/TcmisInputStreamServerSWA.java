package radian.tcmis.server7.share.servlets;

import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;


public class TcmisInputStreamServerSWA {

   protected HttpServletRequest rq = null;
   protected String data = null;
   protected String ip   = null;
   protected String connection = "SSL";
   InputStream in = null;

   Hashtable dataIn = null;

   protected boolean firstTimeFlag = true;
   protected TcmISDBModel db = null;

   protected String token = null;
   protected Object sent_obj = null;

   public TcmisInputStreamServerSWA(HttpServletRequest r ,TcmISDBModel db) throws IOException  {
      this.in = r.getInputStream();
      this.db = db;
      this.rq = r;
   }


   public  Hashtable readData(){
      ip = null;
      String result = null;
      String data = new String("");
      try {
        String name = rq.getContentType();
        int len = rq.getContentLength();

        byte[] b = new byte[len];
        int count = 0;
        int c;
        while ((c = in.read()) != -1) {
          b[count++] = (byte)c;
         // System.out.println("Reading:"+(char)c);
          if (count > len) {
            break;
          }
        }
        //System.out.println("****** Read "+count+" of "+b.length+" of "+len);
        // Read the content body into buffer
        ByteArrayInputStream bis = new ByteArrayInputStream(b);
        ObjectInputStream objIn = new ObjectInputStream(bis);
        dataIn = (Hashtable) objIn.readObject();

        //System.out.println("\n==== TcmisInputStreamServer Got hash:"+dataIn);

        ip = (String) dataIn.get("CLIENT_IP");
        token = (String) dataIn.get("TOKEN");
        sent_obj = dataIn.get("OBJECT");

        // System.out.println("ip"+(ip==null?"null":ip));
        //System.out.println("token"+(token==null?"null":token));

        //HelpObjs.monitor(1,"On input token is before firt time:"+token+" and "+dataIn.get("TOKEN"),null);

        if (this.firstTimeFlag){
          cleanAuthTable();
          setToken();
          //HelpObjs.monitor(1,"On input token is after firt time:"+token+" and "+dataIn.get("TOKEN"),null);
        }
        //System.out.println("Went thru the clean table.");
        getTokenAndConnection(); // null if not found
        //System.out.println("Went thru the tokoen connection.");
        if(token==null || token.equalsIgnoreCase("NONE")) setToken();  // create record on table for ip.
        //System.out.println("Went thru the sent token."+dataIn);
        //HelpObjs.monitor(1,"On input token is end of method:"+token+" and "+dataIn.get("TOKEN"),null);
        return dataIn;
      } catch (Exception e){ e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null); }
      return null;
   }

   public  void loadOldData(){
        // this is done to allow us to use old code, by passing
        // data as string on String getParameter() call. New code
        // should use the new Object getParameter() call, where SENT_OBJ
        // whould bring the object sent by the client
        data = new String("");
        data = data + "IP" + BothHelpObjs.NAME_NAME_DEL + ip;
        data = data + BothHelpObjs.NAME_VALUE_DEL + "TOKEN" + BothHelpObjs.NAME_NAME_DEL;
        if (sent_obj instanceof java.util.Hashtable){
            Enumeration E;
            Hashtable temp = (Hashtable) sent_obj;
            for (E=temp.keys();E.hasMoreElements();){
              String k = E.nextElement().toString();
              data = data + BothHelpObjs.NAME_VALUE_DEL + k + BothHelpObjs.NAME_NAME_DEL + temp.get(k).toString();
            }
        }

        //System.out.println("data:"+data);
   }

   public String getParameter(String name){
      //System.out.println("Parameter:"+name);
      if (name.equals("IP")) return ip;
      if (name.equals("DATA")) return data;
      if (name.equals("TOKEN")) return token;
      if (name.equals("CONNECTION")) return connection;
      return null;
   }

   public Object getParameterObject(Object name){
      return dataIn.get(name);
   }

   protected void getTokenAndConnection(){
     //System.out.println("\n------- here in tcmisinputstreamserver getTokenAndConnection");
      try {
       Authenticator auth = new Authenticator(db);
       auth.setIp(ip);
       auth.load();
       this.token = auth.getToken();
       this.connection = auth.getConnection();
       dataIn.remove("TOKEN");
       dataIn.put("TOKEN",token);
      } catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage()+" for ip:"+ip,null);
      }
      return;
  }

  private void setToken() {
    //System.out.println("\n------- here in tcmisinputstreamserver setToken");
    try {
        Authenticator auth = new Authenticator(db);
        token = auth.addToken(ip);
        dataIn.remove("TOKEN");
        dataIn.put("TOKEN",token);
    } catch (Exception e){
        e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
    }
  }

  private void cleanAuthTable(){
    try {
        //System.out.println("stage1.");
        Authenticator auth = new Authenticator(db);
        auth.cleanUpTable(ip);
        //System.out.println("stage2.");
    } catch (Exception e){
        e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
    }
  }

  public void setFirstTimeFlag(boolean flag){
    this.firstTimeFlag = flag;
  }

}




























