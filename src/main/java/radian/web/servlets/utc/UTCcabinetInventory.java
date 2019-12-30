package radian.web.servlets.utc;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */

public class UTCcabinetInventory extends HttpServlet implements SingleThreadModel
{
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    /**
    * HTTP POST handler.
    *
    * @param request               An object implementing the request context
    * @param response              An object implementing the response context
    * @exception javax.servlet.ServletException  A wrapper for all exceptions thrown by the
    *      servlet.
    */

   public void doPost( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
   {
	  radian.web.HTMLHelpObj.redirectPage(response.getWriter(),"tcmIS/utc/newclientcabinetinventorymain.do",2013,2,1);
	 return;
   }

   public void doGet( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
   {
	 doPost( request,response );
   }
   //
 }