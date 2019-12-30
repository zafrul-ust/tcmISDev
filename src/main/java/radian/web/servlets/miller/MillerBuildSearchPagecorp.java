package radian.web.servlets.miller;

import java.io.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

public class MillerBuildSearchPagecorp
    extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws
      ServletException, IOException {
    doPost(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws
      ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = new PrintWriter(response.getOutputStream());
    out.println("<html>");
    out.println("<head>");
    out.println("<meta http-equiv=\"refresh\" content=\"5; URL='/tcmIS/miller/showsearchmsds.do'\"/>");
    out.println("</head>");
    out.println("<body>");
    out.println("This page has moved, please update your bookmark.<br>");
    out.println("You will be redirected in 5 seconds, if it doesn't work click <a href=\"/tcmIS/miller/showsearchmsds.do\">here</a>");
    out.println("</body>");
    out.println("</html>");
    out.close();
  }
}
/*
 public class MillerBuildSearchPagecorp  extends radian.web.servlets.miller.msds.millerBuildSearchPage
 {
   public String getClient()
   {
      return "Miller";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new MillerServerResourceBundle();
   }
 }
 */