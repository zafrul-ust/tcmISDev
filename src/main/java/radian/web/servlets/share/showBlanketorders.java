package radian.web.servlets.share;

//
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 */

public class showBlanketorders
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private DBResultSet dbrs = null;
    private ResultSet rs = null;
    private String newmfgid_servlet = "";
private boolean intcmIsApplication = false;
    public showBlanketorders(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

     public void doResult(HttpServletRequest request, HttpServletResponse response)  throws ServletException,  IOException
    {
    PrintWriter out = response.getWriter();
    response.setContentType("text/html");
	HttpSession session=request.getSession();
		PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
		Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
		if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
		{
		 intcmIsApplication = true;
		}

    String prnumber=request.getParameter( "prnumber" );
    if ( prnumber == null )
      prnumber="";

    String Action=request.getParameter( "Action" );
    if ( Action == null )
      Action="";

    if ("showblankets".equalsIgnoreCase(Action))
    {
      out.println(buildLikemfgid(prnumber));
    }

    out.close();
    }

     public StringBuffer buildLikemfgid(String prnumber)
     {
      StringBuffer Msgl = new StringBuffer();
      Msgl.append(radian.web.HTMLHelpObj.printHTMLHeader("Open Blanket Orders","purchasereq.js",intcmIsApplication));

      Msgl.append("<BODY>\n");
	  Msgl.append( radian.web.HTMLHelpObj.PrintTitleBarNolink( "<B>Open Blanket Orders</B>\n" ) );
      Msgl.append("<FORM METHOD=\"POST\" name=\"showopenblankets\" action=\"\">\n");
      Msgl.append("<INPUT TYPE=\"hidden\" NAME=\"prnumber\" VALUE=\""+prnumber+"\">\n");

      Msgl.append("<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\" bgcolor=\"white\">\n");

      int count = 0;

      String query = "";
      query += "select bpo,bpo_detail from associate_pr_view where pr_number = "+prnumber+"";

      if (query.length() > 0)
      {
          try
          {
              dbrs = db.doQuery(query);
              rs=dbrs.getResultSet();

              while(rs.next())
              {
				count += 1;

                String bpodetail = rs.getString( "BPO_DETAIL" ) == null ? "" : rs.getString( "BPO_DETAIL" ).trim();
				String Color=" ";
				if ( count % 2 == 0 )
				{
				  Color="CLASS=\"blue";
				}
				else
				{
				  Color="CLASS=\"white";
				}

                Msgl.append("<TR ALIGN=\"MIDDLE\">\n");
                Msgl.append("<TD WIDTH=\"15%\" "+ Color + "l\" <FONT FACE=\"Arial\" SIZE=\"3\">");
                Msgl.append(bpodetail);
                Msgl.append("</FONT></TD>\n");
                Msgl.append("</TR>\n");
              }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Msgl.append("An Error Occured While Querying the Databse");
            Msgl.append("</BODY>\n</HTML>\n");
            return Msgl;
        }
        finally
        {
            if ( dbrs != null ){ dbrs.close(); }
        }
      }

	  if ( count == 0 )
	  {
		Msgl.append( "<TR><TD>No Records Found</TD></TR>\n" );
	  }

      Msgl.append("</TABLE>\n");

      //close scrolling table
      Msgl.append("</DIV>\n");
      Msgl.append("</TD>\n");
      Msgl.append("</TR>\n");
      Msgl.append("</TBODY>\n");
      Msgl.append("</TABLE>\n");

      Msgl.append("<CENTER><BR><BR><BR><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"button\"  name=\"searchlike\" value=\"Close\" OnClick=\"cancel()\"></CENTER>\n");
      Msgl.append("</FORM></BODY></HTML>\n");

      return Msgl;
    }
}