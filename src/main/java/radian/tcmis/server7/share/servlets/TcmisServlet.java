package radian.tcmis.server7.share.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.dbObjs.*;

public abstract class TcmisServlet {

  TcmISDBModel db = null;
  TcmisOutputStreamServer out = null;
  HttpServletRequest reqInfo = null;

  String ip = null;
  String token = null;

  ServerResourceBundle bundle=null;

  public TcmisServlet(){

  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    reqInfo = request;

    resetAllVars();
    token = null;
    //db = null;
    out = null;
    ip = null;

    String data = null;

    TcmisInputStreamServer rq = new TcmisInputStreamServer(request,db);

    Hashtable passedObj = rq.readData();


    try {
        ip  = (String) rq.getParameterObject("CLIENT_IP");
        token  = (String) rq.getParameterObject("TOKEN");
    } catch (Exception e) {
        e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
    }

    HttpInput httpI = new HttpInput(ip,passedObj,db);

    response.setContentType("text/plain");
    out = new TcmisOutputStreamServer (getServInt(),response);
    out.setToken(token);
    //token = new String(httpI.getString("TOKEN"));
    out.setForceClear(rq.getParameter("CONNECTION").equals("CLEAR"));

    if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"Cgi User Profile reveived the data:"+data,getBundle());

    if (!httpI.isAuthenticated()) {
       out.printDenied();
       out.close();
       return;
    }


    //out.printDebug("Data: "+data);

    try {
       getResult(httpI);

    } catch (Exception e){
       e.printStackTrace();
       out.printDebug("Error on:getResult" + e.getMessage()+e.getLocalizedMessage()+e.toString());
       out.close();
       return;
    }



    try {
       print(out);

       out.close();
    } catch (Exception e){
       e.printStackTrace();
    }



  }


  //Get Servlet information
  public String getServletInfo(){
    return "radian.tcmis.server7.share.servlets.TcmisServlet Information";
  }


  public String getClientIP(){
      return this.ip;
  }
  public String getToken() {
    return this.token;
  }
  public String getServerName(){
      return reqInfo.getServerName();
  }

  public int getServerPort(){
      return reqInfo.getServerPort();
  }

  public String getServletURI(){
      return reqInfo.getRequestURI();
  }

  
  protected abstract void print(TcmisOutputStreamServer out);
  protected abstract void getResult(HttpInput httpI) throws Exception ;
  protected abstract void resetAllVars();

  protected ServerResourceBundle getBundle(){ return bundle; }
  protected TcmISDBModel getDBModel(String client) { return db; }

  protected abstract int getServInt();

}
