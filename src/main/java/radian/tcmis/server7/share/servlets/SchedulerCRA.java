package radian.tcmis.server7.share.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.dbObjs.*;

public class SchedulerCRA {
//Initialize global variables

  TcmISDBModel db = null;
  ServerResourceBundle bundle=null;

//Process the HTTP Post request

  public SchedulerCRA(ServerResourceBundle b, TcmISDBModel d ){
     bundle = b;
     db = d;
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //System.out.println("\n========= in scheduler");
    response.setContentType("text/plain");
    try {
       sendTestEmail();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //Get Servlet information
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     doPost(request,response);
  }

  protected  void sendTestEmail() throws Exception {
    try {
      HelpObjs.sendMail(db,"Test Scheduler","This is a test using JRun Scheduler",86030,true);
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String getServletInfo() {
    return "radian.tcmis.server7.share.servlets.SchedulerCRA Information";
  }

}