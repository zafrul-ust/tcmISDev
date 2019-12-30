package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.share.dbObjs.Facility;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public abstract class LoadFacs extends HttpServlet implements SingleThreadModel {
//Initialize global variables

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }
//Process the HTTP Post request
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String client = "";
    String dbInst = "";
    try {
          client = request.getParameter("client");
          dbInst = request.getParameter("db");
    } catch (Exception e) { e.printStackTrace(); }
    response.setContentType("text/html");
    PrintWriter out = new PrintWriter (response.getOutputStream());
    // Open db
    TcmISDBModel db = null;
    Hashtable data = null;
    try {
      db = getDBModel(client);
      Facility fac = new Facility(db);
      data = fac.getAllFacXRef();
      db.close();
    } catch (Exception e) {
      System.out.println("*** Error on open DB on LoadFacs ***");
      e.printStackTrace();
      if (db!= null) db.close();
    }
    Enumeration E;
    for (E=data.keys(); E.hasMoreElements();){
      String key = (String) E.nextElement();
      out.println("<option value=\""+data.get(key).toString()+"\">"+key+"</option>");
    }
    out.close();
  }
//Get Servlet information

  public String getServletInfo() {
    return "radian.web.servlets.SearchMsds Information";
  }

  protected abstract ServerResourceBundle getBundle();

  protected abstract TcmISDBModel getDBModel(String client);

}

