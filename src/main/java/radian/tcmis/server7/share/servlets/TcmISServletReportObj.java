package radian.tcmis.server7.share.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.dbObjs.*;


public abstract class TcmISServletReportObj {

  TcmISDBModel db = null;
  TcmisOutputStreamServer out = null;
  HttpServletRequest reqInfo = null;

  //trong 8-7
  HttpServletResponse response = null;

  String ip = null;
  String token = null;
  Object myRecvObj = null;

  ServerResourceBundle bundle=null;

  public TcmISServletReportObj(){

  }


  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    reqInfo = request;

    //trong 8-7
    this.response = response;


    resetAllVars();
    token = null;
    out = null;
    ip = null;
    myRecvObj = null;

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
    out = new TcmisOutputStreamServer (response);
    out.setToken(token);
    //token = new String(httpI.getString("TOKEN"));

    if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"Cgi User Profile reveived the data:",getBundle());

    if (!httpI.isAuthenticated()) {
       out.printDenied();
       out.close();
       return;
    }

    // the whole object sent
    myRecvObj = httpI.getSentObj();

    setInData((Hashtable)myRecvObj);

    try {
      print(out);

      out.close();

    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
       getResult();
    } catch (Exception e){
       e.printStackTrace();
       HelpObjs.monitor(1,"Error on:getResult" + e.getMessage()+e.getLocalizedMessage()+e.toString(),null);
       //out.close();
       return;
    }

  }


  //Get Servlet information
  public String getServletInfo(){
    return "radian.tcmis.server7.share.servlets.TcmISServletReportObj Information";
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

  protected abstract void setInData(Hashtable myRecvObj);
  protected abstract void print(TcmisOutputStreamServer out);
  protected abstract void getResult() throws Exception ;
  protected abstract void resetAllVars();
  protected ServerResourceBundle getBundle(){ return bundle; }
  protected TcmISDBModel getDBModel(String client) { return db; }

}