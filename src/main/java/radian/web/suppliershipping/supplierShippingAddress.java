package radian.web.suppliershipping;

import com.tcmis.common.admin.beans.PersonnelBean;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.db.DBResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Hashtable;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */

public class supplierShippingAddress  extends HttpServlet implements SingleThreadModel
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
    * @exception ServletException  A wrapper for all exceptions thrown by the
    *      servlet.
    */

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
        PrintWriter apout = response.getWriter();
        RayTcmISDBModel db = null;
        HttpSession session = request.getSession();
        response.setContentType( "text/html" );

				PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
				Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
				boolean intcmIsApplication = false;
				if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
				{
				 intcmIsApplication = true;
				}

        try
        {
          db = new RayTcmISDBModel("supplier");
          if (db == null)
          {
              PrintWriter out2 = new PrintWriter (response.getOutputStream());
              out2.println("Starting DB");
              out2.println("DB is null");
              out2.close();
              return;
          }

          Hashtable loginresult = new Hashtable();
          loginresult = radian.web.loginhelpObj.logintopage(session,request,db,apout);
          String auth = (String)loginresult.get("AUTH");
          String errormsg = (String)loginresult.get("ERROSMSG");

          if (auth == null) {auth = "challenge";}
          if (errormsg == null) {errormsg = "";}


          if ( ! ( auth.equalsIgnoreCase( "authenticated" ) ) )
          {
            apout.println( radian.web.HTMLHelpObj.printLoginScreen( errormsg,"Ship To Address" ) );
            apout.flush();
          }
          else
          {
            session.setAttribute( "SUPPLIERPAGE","Yes" );
			session.setAttribute( "COMPANYID","SUPPLIER" );

            String shiptolocid=request.getParameter( "shiptolocid" );
            if ( shiptolocid == null )
              shiptolocid="";

		    String shiptocompid=request.getParameter( "shiptocompid" );
		    if ( shiptocompid == null )
			  shiptocompid="";

			DBResultSet dbrs = null;
			ResultSet rs = null;
			String countryabbrev = "";
			String stateabbrev = "";
			String addressline1 = "";
			String addressline2 = "";
			String addressline3 = "";
			String city = "";
			String zip = "";
			String locationdesc = "";
			String billinglocationid = "";

			try
			{
			  dbrs = db.doQuery("select loc.* from customer.location loc where loc.location_id = '"+shiptolocid+"' AND COMPANY_ID = '"+shiptocompid+"'");
			  rs=dbrs.getResultSet();
			  while(rs.next())
			  {
				countryabbrev = rs.getString("COUNTRY_ABBREV")==null?"":rs.getString("COUNTRY_ABBREV").trim();
				stateabbrev = rs.getString("STATE_ABBREV")==null?"":rs.getString("STATE_ABBREV").trim();
				addressline1 = rs.getString("ADDRESS_LINE_1")==null?"":rs.getString("ADDRESS_LINE_1").trim();
				addressline2 = rs.getString("ADDRESS_LINE_2")==null?"":rs.getString("ADDRESS_LINE_2").trim();
				addressline3 = rs.getString("ADDRESS_LINE_3")==null?"":rs.getString("ADDRESS_LINE_3").trim();
				city = rs.getString("CITY")==null?"":rs.getString("CITY").trim();
				zip = rs.getString("ZIP")==null?"":rs.getString("ZIP").trim();
				locationdesc = rs.getString("LOCATION_DESC")==null?"":rs.getString("LOCATION_DESC").trim();
				billinglocationid = rs.getString("LOCATION_ID")==null?"":rs.getString("LOCATION_ID").trim();
			  }
			}
			catch ( Exception e )
			{
			  e.printStackTrace();
			}
			finally
			{
			  if ( dbrs != null ){dbrs.close();}
			}


		apout.println( radian.web.HTMLHelpObj.printHTMLHeader( "Ship To Address","suppliershipping.js",intcmIsApplication ) );
		apout.println("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/poglobal.css\"></LINK>\n");
		apout.println("<BODY>\n");
		apout.println("<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"3\" CELLSPACING=\"0\">\n");
		apout.println("<TR><TD COLSPAN=\"4\"><B>Ship To Address:</B></TD></TR>\n");

		apout.println("<TR>\n");
		apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline1\" CLASS=\"texareaannounce\">\n");
		apout.println(""+locationdesc+"</TD>\n");
		apout.println("</TR>\n");

		apout.println("<TR>\n");
		apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline2\" CLASS=\"texareaannounce\">\n");
		apout.println(""+addressline1+"</TD>\n");
		apout.println("</TR>\n");

		apout.println("<TR>\n");
		apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline3\" CLASS=\"texareaannounce\">\n");
		apout.println(""+addressline2+"</TD>\n");
		apout.println("</TR>\n");

		apout.println("<TR>\n");
		apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline4\" CLASS=\"texareaannounce\">\n");
		apout.println(""+addressline3+"</TD>\n");
		apout.println("</TR>\n");

		apout.println("<TR>\n");
		apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline5\" CLASS=\"texareaannounce\">\n");
		apout.println("City: "+city+"\n");
		apout.println("&nbsp;&nbsp;State: "+stateabbrev+"\n");
		apout.println("&nbsp;&nbsp;Zip: "+zip+"\n");
		apout.println("&nbsp;&nbsp;Country: "+countryabbrev+"</TD>\n");
		apout.println("</TR>\n");

		apout.println("</TABLE>\n");
		apout.println("<CENTER><BR><BR>\n");
		apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Close\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
		apout.println("</BODY></HTML>\n");

	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	  return;
	}
	finally
	{
	  db.close();
	  try
	  {
		apout.close();
	  }
	  catch ( Exception e )
	  {
		//ignore
	  }
	  //Runtime.getRuntime().gc();
	  return;
	}
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
    {
        doPost(request,response);
    }
}

