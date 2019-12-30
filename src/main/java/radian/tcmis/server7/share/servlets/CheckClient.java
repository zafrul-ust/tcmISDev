package radian.tcmis.server7.share.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.dbObjs.*;


public class CheckClient { //extends HttpServlet implements SingleThreadModel {
//Initialize global variables
  String action = null;
  String token = null;
  String clients = null;
  TcmisOutputStreamServer out = null;
  boolean sendingMessage=false;
  boolean sendingSysDown=false;
  boolean sendingUpdate=false;

  TcmISDBModel db = null;

  ServerResourceBundle bundle=null;


  public CheckClient(ServerResourceBundle b, TcmISDBModel d ){
     bundle = b;
     db = d;
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    //System.out.println("\n\n---------- here in check client");
    token = null;
    clients = null;

    String data = null;
    String ip = null;

    sendingSysDown=false;
    sendingMessage=false;
    sendingUpdate=false;   // put back after update trong 4/11
    //sendingUpdate = true;    //block user from logon to tcmis

    // Use this to block the system and give a message
    if (sendingSysDown){
       try {
        response.setContentType("text/plain");
        out = new TcmisOutputStreamServer (this.getServInt(),response);

        out.printStart();
        out.println("ERROR");
        out.printEnd();
        out.printStart();
        out.println("tcmIS Database is down! \n\nYou do not have to contact the administrator, we are working on the problem.\n\n Expected down time: 20 min.");
        out.printEnd();
        out.close();
        return;
      } catch (Exception e){
        e.printStackTrace();
        out.close();
        return;
      }
    }

    TcmisInputStreamServer rq = new TcmisInputStreamServer(request,db);
    rq.setFirstTimeFlag(true);
    Hashtable passed_obj = rq.readData();
    try {
        ip    = (String)rq.getParameterObject("CLIENT_IP");
        token = (String)rq.getParameterObject("TOKEN");
    } catch (Exception e) {
      //System.out.println("ERROR:" + e.getMessage());
        e.printStackTrace();
        HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
    }

    // Use this to block the system during updates
    //if (sendingUpdate && !ip.startsWith("129.160.17.253") ){      // Rodrigo
    if (sendingUpdate && (!ip.startsWith("129.160.225.116") && !ip.startsWith("129.160.17.182x")
        && !ip.startsWith("129.160.17.38x")
        && !ip.startsWith("209.51.19.11x")              // david meyer
        && !ip.startsWith("209.51.19.xx"))) {              //dallas network
       try {
        response.setContentType("text/plain");
        out = new TcmisOutputStreamServer (this.getServInt(),response);

        out.printStart();
        out.println("ERROR");
        out.printEnd();
        out.printStart();
        out.println("tcmIS is down for update! \n\nYou can not access the system at this time. \n\nWe're sorry for the inconvenience.\n\n");
        out.printEnd();
        out.close();
        return;
      } catch (Exception e){
        e.printStackTrace();
        out.close();
        return;
      }
    }

    try {
      HttpInput httpI = new HttpInput(passed_obj,db);   // first time will not come from client
      response.setContentType("text/plain");
      out = new TcmisOutputStreamServer (this.getServInt(),response);
      out.setFirstTimeFlag(true);
      out.setIp(ip);

      action = httpI.getString("ACTION");
      if (!action.equals("SET_TOKEN")){
       out.printDenied();
       return;
      }
      out.printStart();
      printClient();
      out.printEnd();
      out.printStart();
      printToken();
      out.printEnd();
    } catch (Exception e){
      e.printStackTrace();
      return;
    } finally {
      out.close();
    }

  }

  public String getServletInfo() {
    return "radian.tcmis.server7.share.servlets.CheckClient Information";
  }

  protected void printClient() throws IOException {
    if (sendingMessage){
      String cc = "MSG~";
      cc += getBundle().getString("DB_CLIENT");
      out.println(cc);
    } else {
      out.println(getBundle().getString("DB_CLIENT"));
    }
  }

   protected void printToken() throws IOException {
     if (token == null) return;
     if (sendingMessage){
       String cc = "Good morning, Chuck.\n\n This is just a meesage for you... keep going~";
       cc += token;
       out.println(cc);
     } else {
       out.println(token);
     }
  }

  protected ServerResourceBundle getBundle(){ return bundle; }
  protected TcmISDBModel getDBModel(String client) { return db; }

  protected  int getServInt(){
    return TcmisOutputStreamServer.CHECK_CLIENT;
  }

}

































