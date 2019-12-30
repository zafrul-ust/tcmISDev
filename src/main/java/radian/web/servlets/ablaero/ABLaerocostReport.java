package radian.web.servlets.ablaero;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ABLaerocostReport extends HttpServlet implements SingleThreadModel
{
  public void init( ServletConfig config ) throws ServletException
  {
    super.init( config );
  }

  public void doPost( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException {
	 radian.web.HTMLHelpObj.redirectPage(response.getWriter(),"tcmIS/ablaero/costreportmain.do",2009,3,1);
	 return;
  }

  public void doGet( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException {
    doPost( request,response );
  }
}
