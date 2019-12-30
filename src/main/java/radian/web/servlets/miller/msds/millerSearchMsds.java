package radian.web.servlets.miller.msds;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.sql.ResultSet;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.helpers.HelpObjs;

/**
 *
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 *
 *10-13-03 New Miller MSDS
 *03-01-04 Adding department and status to the result set. Also can search by Department
 */
public class millerSearchMsds extends HttpServlet implements SingleThreadModel
{
    private Vector data = null;
    private boolean union = true;
    private ServerResourceBundle bundle=null;
    private TcmISDBModel db = null;

    public millerSearchMsds()
    {

    }

    public millerSearchMsds(ServerResourceBundle b, TcmISDBModel d)
    {
     bundle = b;
     db = d;
    }

    public Hashtable GetResult(Hashtable SearchData,HttpSession SessionH) throws ServletException,
        IOException
    {
        String client = "";
        client = bundle.getString("DB_CLIENT");
        String Session_id = " ";
        String Second = "No";
        String recs = "";
        StringBuffer Msg = new StringBuffer();
        StringBuffer MsgBackNext = new StringBuffer();
        Hashtable ResultDdata = new Hashtable();

        ResultDdata.put("MSG_BODY",Msg.toString());
        ResultDdata.put("MSG_BACK_NEXT",MsgBackNext.toString());

        String fac = SearchData.get("fac").toString();
		String buuilding = SearchData.get("building").toString();
		String floor = SearchData.get("floor").toString();
		String sortby = SearchData.get("sortby").toString();

		//System.out.println("Facility   : "+fac+"   Building  : "+buuilding+"   Floor   : "+floor+"    sortby : "+sortby+"");
        String item = SearchData.get("item").toString();
        String limit = SearchData.get("limit").toString();

        String from = SearchData.get("from").toString();
        String interval = SearchData.get("interval").toString();
        String window = SearchData.get("window").toString();
        Session_id = SearchData.get("Session_ID").toString();
        Second = (SearchData.get("Second").toString()==null?"No":SearchData.get("Second").toString());
		String depID = SearchData.get("department").toString()==null?"All":SearchData.get("department").toString();
 	    String statusID = SearchData.get("StatusID").toString()==null?"All":SearchData.get("StatusID").toString();

        if (sortby.equalsIgnoreCase("Nu")) {sortby = "";}
        if (fac.equalsIgnoreCase("Nu")) {fac = "All";}
        if (Second==null){Second="No";}
        if (limit==null){limit="No";}


        data = null;
        Vector data1 = new Vector();
		if ( "No".equalsIgnoreCase( Second ) )
		{
		  try
		  {
			Connection connect1;
			CallableStatement cs=null;
			String searchquery="";

		try
		{
		  connect1=db.getConnection();
		  cs=connect1.prepareCall( "{call PR_MSDS_QUERY(?,?,?,?,?,?,?,?)}" );
		  if ( fac.length() > 3 )
		  {
			if ( fac.substring( 0,3 ).equalsIgnoreCase( "All" ) )
			{
			  cs.setString( 1,"All" );
			}
			else
		  {
			cs.setString( 1,fac );
		  }
		  }
		  else
		  {
			cs.setString( 1,fac );
		  }

		  if ( depID.length() > 3 )
		  {
			if ( depID.equalsIgnoreCase( "All" ) )
			{
			  cs.setString( 2,"All" );
			}
			else
		  {
			cs.setString( 2,depID );
		  }
		  }
		  else
		  {
			cs.setString( 2,depID );
		  }

		  if ( buuilding.length() > 3 )
		  {
			if ( buuilding.substring( 0,3 ).equalsIgnoreCase( "All" ) && !"All Washrooms".equalsIgnoreCase(buuilding))
			{
			  cs.setString( 3,"All" );
			}
			else
		  {
			cs.setString( 3,buuilding );
		  }
		  }
		  else
		  {
			cs.setString( 3,buuilding );
		  }

		  if ( floor.length() > 3 )
		  {
			if ( floor.substring( 0,3 ).equalsIgnoreCase( "All" ) )
			{
			  cs.setString( 4,"All" );
			}
			else
		  {
			cs.setString( 4,floor );
		  }

		  }
		  else
		  {
			cs.setString( 4,floor );
		  }
		  cs.setString( 5,"order by " + sortby + "" );
		  cs.setString( 6,item );

		  if ( statusID.length() > 3 )
		  {
			if ( statusID.equalsIgnoreCase( "All" ) )
			{
			  cs.setString( 7,"All" );
			}
			else
			{
			  cs.setString( 7,statusID );
			}
		  }
		  else
		  {
			cs.setString( 7,statusID );
		  }

		  cs.registerOutParameter( 8,Types.VARCHAR );

		  cs.execute();
		  connect1.commit();

		  searchquery=cs.getString( 8 );
		}
		catch ( Exception e )
			{
			  e.printStackTrace();
			  //radian.web.emailHelpObj.sendnawazemail("Error calling Procedure p_update_inventory_count in HubInvenReconcilTables","Error occured while running p_update_inventory_count  \nError Msg:\n "+e.getMessage()+ " for Receipt Id: " + Oreceipt_id + "\n CountDateS " + CountDateS + "\n PersonnelId " + PersonnelId + "\n exp_qty " + exp_qty + "\n act_onhand " + act_onhand + "" );
			}
			finally
			{
			  if ( cs != null )
			  {
				try
				{
				  cs.close();
				}
				catch ( Exception se )
				{
				  se.printStackTrace();
				}
			  }
			}

		//System.out.println("searchquery      :"+searchquery);
		Hashtable result= new Hashtable();
		DBResultSet dbrs = null;
        ResultSet rs = null;

		try
		{
		  dbrs=db.doQuery( searchquery );
		  rs=dbrs.getResultSet();
		  int i=0;

		  /*ResultSetMetaData rsMeta=rs.getMetaData();
		  for ( int ii=1; ii <= rsMeta.getColumnCount(); ii++ )
		  {
			// To retrieve data from hash
			//System.out.println( "String " + rsMeta.getColumnName( ii ).toString().toLowerCase() + " = materialData.get(\"" +rsMeta.getColumnName( ii ).toString() + "\").toString();" );
			System.out.println("DataH.put(\""+rsMeta.getColumnName(ii).toString()+"\",rs.getString(\""+rsMeta.getColumnName(ii).toString()+"\")==null?\"\":rs.getString(\""+rsMeta.getColumnName(ii).toString()+"\"));");

		  }*/

		  while ( rs.next() )
		  {
			i++;
			result=new Hashtable();

			result.put( "CUSTOMER_MSDS_NO",rs.getString( "CUSTOMER_MSDS_NO" ) == null ? "" : rs.getString( "CUSTOMER_MSDS_NO" ) );
			result.put( "TRADE_NAME",rs.getString( "TRADE_NAME" ) == null ? "" : rs.getString( "TRADE_NAME" ) );
			result.put( "CAT_PART_NO_STRING",rs.getString( "CAT_PART_NO_STRING" ) == null ? "" : rs.getString( "CAT_PART_NO_STRING" ) );
			result.put( "MANUFACTURER",rs.getString( "MANUFACTURER" ) == null ? "" : rs.getString( "MANUFACTURER" ) );
			result.put( "MATERIAL_ID",rs.getString( "MATERIAL_ID" ) == null ? "" : rs.getString( "MATERIAL_ID" ) );
			result.put( "CONTENT",rs.getString( "CONTENT" ) == null ? "" : rs.getString( "CONTENT" ) );
			result.put( "ON_LINE",rs.getString( "ON_LINE" ) == null ? "" : rs.getString( "ON_LINE" ) );
			result.put( "DEPARTMENT",rs.getString( "DEPARTMENT" ) == null ? "" : rs.getString( "DEPARTMENT" ) );
			result.put( "STATUS",rs.getString( "STATUS" ) == null ? "" : rs.getString( "STATUS" ) );

			data1.addElement( result );
		  }
		  // put qty on last 2 occur of the vector
		  data1.addElement( new Integer( i ) );
		  data1.trimToSize();
		}
		catch ( Exception e )
		{
		  e.printStackTrace();
		  radian.web.emailHelpObj.senddatabaseHelpemails( "Error in Query for Miller MSDS",
														  "Error occured while running Query for Miller MSDS\nError Msg:\n" +
														  e.getMessage() + "\n\n DB Clinet : " + db.getClient() + " \n\nQuery:  " + searchquery + " " );
		  throw e;
		}
		finally
		{
		  if ( dbrs != null ) {dbrs.close();}
		}

			/*int test = 0;
			data1.addElement( new Integer( test ) );
            data1.trimToSize();*/

            recs=data1.elementAt( data1.size() - 1 ).toString();
            if ( Integer.parseInt( recs ) == 0 )
            {
              Msg.append( printHtmlNoData() );
              ResultDdata.put( "MSG_BODY",Msg.toString() );
              ResultDdata.put( "MSG_BACK_NEXT",MsgBackNext.toString() );

              return ResultDdata;
            }
            else
            {
              SessionH.setAttribute( Session_id,data1 );
            }
          }
          catch ( Exception e )
          {
            e.printStackTrace();
            Msg.append( "An Error Occurred.<BR><BR>Please try again.<BR><BR><BR><BR>" );
            ResultDdata.put( "MSG_BODY",Msg.toString() );
            ResultDdata.put( "MSG_BACK_NEXT",MsgBackNext.toString() );

            return ResultDdata;
          }
        }

        try
        {
          data= ( Vector ) SessionH.getAttribute( Session_id );
          recs=data.elementAt( data.size() - 1 ).toString();

          Hashtable millerhD = null;
          Msg.append(printHtmlHeader());

          int f = Integer.parseInt(from);
          int r = Integer.parseInt(recs);
          int it = 0;
          try{it += Integer.parseInt(interval);}catch (Exception e122r){it=0;}
          int tmp = (r<f+it?(r):(it+f-1));

          for (int i= new Integer(from).intValue()-1;i<tmp;i++)
          {
			if ( i % 10 == 0 )
			{
			  Msg.append( "<TR>\n" );
			  Msg.append( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">MSDS No</TH>\n" );
			  Msg.append( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Department</TH>\n" );
			  Msg.append( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Status</TH>\n" );
			  Msg.append( "<TH WIDTH=\"25%\"  HEIGHT=\"25\">Trade Name</TH>\n" );
			  Msg.append( "<TH WIDTH=\"10%\"  HEIGHT=\"25\">Part Number</TH>\n" );
			  Msg.append( "<TH WIDTH=\"20%\"  HEIGHT=\"25\">Manufacturer</TH>\n" );
			  Msg.append( "</TR>\n" );
			}

			millerhD= ( Hashtable ) data.elementAt( i );
			String customer_msds_no=millerhD.get( "CUSTOMER_MSDS_NO" ).toString();
			String trade_name=millerhD.get( "TRADE_NAME" ).toString();
			String cat_part_no_string=millerhD.get( "CAT_PART_NO_STRING" ).toString();
			String manufacturer=millerhD.get( "MANUFACTURER" ).toString();
			String material_id=millerhD.get( "MATERIAL_ID" ).toString();
			String content=millerhD.get( "CONTENT" ).toString();
			String on_line=millerhD.get( "ON_LINE" ).toString();
			String department = millerhD.get("DEPARTMENT").toString();
			String status = millerhD.get("STATUS").toString();

            String Color=" ";
            if ( i % 2 == 0 )
            {
              Color="bgcolor=\"#E6E8FA\"";
            }
            Msg.append( "<TR>\n" );
            Msg.append( "<TD " + Color + " width=\"5%\">\n" );

            if (on_line.equalsIgnoreCase( "Y" ) && content.length() > 0)
            {
              if ( window.equalsIgnoreCase( "Same" ) )
              {
                Msg.append( "<A HREF=\"javascript:getMsds('" + buildMsdsUrl( material_id,client,trade_name,content,customer_msds_no ) + "');\">\n" );
              }
              else if ( window.equalsIgnoreCase( "New" ) )
              {
                Msg.append( "<A HREF=\"" +buildMsdsUrl( material_id,client,trade_name,content,customer_msds_no ) + "\" TARGET=\"msds\"  STYLE=\"color:#0000ff\">\n" );
              }
              else
              {
                Msg.append( "<A HREF=\"" + buildMsdsUrl( material_id,client,trade_name,content,customer_msds_no ) + "\" TARGET=\"msds" + i + "\"  STYLE=\"color:#0000ff\">\n" );
              }
              Msg.append( customer_msds_no );
              Msg.append( "</A>\n" );
            }
            else
            {
              Msg.append( customer_msds_no );
            }
            Msg.append( "</TD>\n" );

			Msg.append( "<TD " + Color + " width=\"5%\">" + department + "</TD>\n" );
			Msg.append( "<TD " + Color + " width=\"5%\">" + status + "</TD>\n" );
            Msg.append( "<TD " + Color + " width=\"25%\">" + trade_name + "</TD>\n" );
            Msg.append( "<TD " + Color + " width=\"10%\">" + cat_part_no_string + "</TD>\n" );
            Msg.append( "<TD " + Color + " width=\"20%\">" + manufacturer + "</TD>\n" );
            Msg.append( "</TR>\n" );
          }

          Msg.append("</TABLE>\n");
          Msg.append("<TABLE  border=\"0\" width=\"725\"><TR><TD ALIGN=\"CENTER\">\n");

          Msg.append(printBackRecsNext( recs, from, interval));
          MsgBackNext.append(printBackRecsNext(recs, from, interval));

          Msg.append("</TD></TR></TABLE>\n");

          Msg.append(buildBackForm(limit,Session_id,from,interval,sortby,fac,item,window,buuilding,floor,depID,statusID));
          Msg.append(buildNextForm(limit,Session_id,from,interval,sortby,fac,item,window,buuilding,floor,depID,statusID));

          ResultDdata.put("MSG_BODY",Msg.toString());
          ResultDdata.put("MSG_BACK_NEXT",MsgBackNext.toString());

          data = null;
          data1 = null;
          return ResultDdata;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Msg.append("*** Error on SearchMSds Building the page ***");
            ResultDdata.put("MSG_BODY",Msg.toString());
            ResultDdata.put("MSG_BACK_NEXT",MsgBackNext.toString());

            data = null;
            data1 = null;
            return ResultDdata;
        }
    }

    public String getServletInfo()
    {
        return "radian.web.servlets.share.SearchMsds Information";
    }

    protected String buildMsdsUrl(String matid,String clientc,String trade_name,String content,String customer_msds_no) throws Exception
    {
        String q = ("id=" + matid);
        String clientHttp = clientc.replace(' ','+');
        q = q + "&cl="+clientHttp;

		customer_msds_no = HelpObjs.addescapesForJavascript(customer_msds_no);
		q = q + "&customer_msds_no="+customer_msds_no;

        //trade_name = HelpObjs.addescapesForJavascript(trade_name);
		//q = q + "&trade_name="+trade_name;

		content = HelpObjs.addescapesForJavascript(content);
	    q = q + "&content="+content;

        String url = "";
        //String WWWhomeDirectory = bundle.getString("WEBS_HOME_WWWS") + bundle.getString("VIEW_MSDS");
		String WWWhomeDirectory = bundle.getString("WEBS_HOME_WWWS") + "/tcmIS/miller/corpViewMsds?";
        url = (""+WWWhomeDirectory+"");

        String qtmp = "";
        qtmp = ("act=msds&" + q);

        return (url + qtmp);
    }

    protected StringBuffer printHtmlHeader()
    {
      StringBuffer MsgHH=new StringBuffer();
      MsgHH.append( "<TABLE BORDER=\"0\" CELLPADDING=\"3\" WIDTH=\"725\" >" );

      return MsgHH;
    }

    protected StringBuffer printHtmlNoData()
    {
      StringBuffer MsgE=new StringBuffer();
      MsgE.append( "" + " No records found. " + "\n" );
      MsgE.append( "<P>&nbsp;</P><P>&nbsp;</P>\n" );
      return MsgE;
    }

    protected StringBuffer printError()
    {
      StringBuffer MsgER=new StringBuffer();
      MsgER.append( "<TR>\n" );
      MsgER.append( "<TD width=\"5%\">\n" );
      MsgER.append( "</TD>\n" );
      MsgER.append( "<TD width=\"16%\">" + "An Error Occurred.<BR>\n" );
      MsgER.append( "Please check either catalog or non-catalog box and try again." + "</TD>\n" );
      MsgER.append( "</TR></TABLE>\n" );
      MsgER.append( "</BODY></HTML>\n" );
      return MsgER;
    }

    protected StringBuffer printBackRecsNext(String recs,String from,String interval)
    {
      StringBuffer MsgBN=new StringBuffer();

      if ( recs == null || recs.length() < 1 )
        return MsgBN;
      int f=Integer.parseInt( from );
      int r=Integer.parseInt( recs );
      int it=Integer.parseInt( interval );
      if ( r == 0 )
        return MsgBN;

      if ( f > 1 )
      {
        MsgBN.append( "<A HREF=\"javascript:goBack();\" STYLE=\"color:#0000ff\">Back</A>&nbsp;&nbsp;&nbsp;" );
      }

      int tmp= ( r < it ? ( r + f - 1 ) : ( it + f - 1 ) );
      tmp= ( tmp > Integer.parseInt( recs ) ? Integer.parseInt( recs ) : tmp );

      MsgBN.append( "<FONT FACE=\"Arial\" color=\"Navy\">" + f + "-" + tmp + " of " + recs + "&nbsp;&nbsp;&nbsp;" );

      if ( r > ( f + it - 1 ) )
      {
        MsgBN.append( "<A href=\"javascript:goNext();\" STYLE=\"color:#0000ff\">Next</A></FONT>\n" );
      }
      return MsgBN;
    }

    protected StringBuffer buildBackForm( String limit,String Session_ID,String from,String interval,String sortby,String fac,String item,String window,String building,String floor,String depId,String statusID )
    {
      StringBuffer MsgBF=new StringBuffer();
      int f=Integer.parseInt( from );
      int it=Integer.parseInt( interval );
      f=f - it;

      MsgBF.append( "<FORM METHOD=\"POST\" NAME=\"backForm\" action=\"/tcmIS/miller/corpmsds?\">\n" );
      MsgBF.append( "<INPUT TYPE=\"hidden\" NAME=\"sortby\" VALUE=\"" + sortby + "\">\n" );
      MsgBF.append( "<INPUT TYPE=\"hidden\" NAME=\"limit\" VALUE=\"" + limit + "\">\n" );
      MsgBF.append( "<INPUT TYPE=\"hidden\" NAME=\"hubC\" VALUE=\"" + fac + "\">\n" );
	  MsgBF.append( "<INPUT TYPE=\"hidden\" NAME=\"facilityName\" VALUE=\"" + building + "\">\n" );
	  MsgBF.append( "<INPUT TYPE=\"hidden\" NAME=\"workAreaName\" VALUE=\"" + floor + "\">\n" );
      MsgBF.append( "<INPUT TYPE=\"hidden\" NAME=\"from\" VALUE=\"" + f + "\">\n" );
      MsgBF.append( "<INPUT TYPE=\"hidden\" NAME=\"interval\" VALUE=\"" + interval.toString() + "\">\n" );
      MsgBF.append( "<INPUT TYPE=\"hidden\" NAME=\"item\" VALUE=\"" + item + "\">\n" );
      MsgBF.append( "<INPUT TYPE=\"hidden\" NAME=\"Session_ID\" VALUE=\"" + Session_ID + "\">\n" );
      MsgBF.append( "<INPUT TYPE=\"hidden\" NAME=\"window\" VALUE=\"" + window + "\">\n" );
	  MsgBF.append( "<INPUT TYPE=\"hidden\" NAME=\"depId\" VALUE=\"" + depId + "\">\n" );
	  MsgBF.append( "<INPUT TYPE=\"hidden\" NAME=\"statusId\" VALUE=\"" + statusID + "\">\n" );
      MsgBF.append( "<INPUT TYPE=\"hidden\" NAME=\"Second\" VALUE=\"Yes\">\n" );
      MsgBF.append( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"Search\">\n" );
      MsgBF.append( "</FORM>\n" );

      return MsgBF;
    }

    protected StringBuffer buildNextForm( String limit,String Session_ID,String from,String interval,String sortby,String fac,String item,String window,String building,String floor,String depId,String statusID )
    {
      StringBuffer MsgNF=new StringBuffer();

      int f=Integer.parseInt( from );
      int it=Integer.parseInt( interval );
      f=f + it;

      MsgNF.append( "<FORM METHOD=\"POST\" NAME=\"nextForm\" action=\"/tcmIS/miller/corpmsds?\">\n" );
      MsgNF.append( "<INPUT TYPE=\"hidden\" NAME=\"sortby\" VALUE=\"" + sortby + "\">\n" );
      MsgNF.append( "<INPUT TYPE=\"hidden\" NAME=\"limit\" VALUE=\"" + limit + "\">\n" );
      MsgNF.append( "<INPUT TYPE=\"hidden\" NAME=\"hubC\" VALUE=\"" + fac + "\">\n" );
	  MsgNF.append( "<INPUT TYPE=\"hidden\" NAME=\"facilityName\" VALUE=\"" + building + "\">\n" );
	  MsgNF.append( "<INPUT TYPE=\"hidden\" NAME=\"workAreaName\" VALUE=\"" + floor + "\">\n" );
      MsgNF.append( "<INPUT TYPE=\"hidden\" NAME=\"from\" VALUE=\"" + f + "\">\n" );
      MsgNF.append( "<INPUT TYPE=\"hidden\" NAME=\"interval\" VALUE=\"" + it + "\">\n" );
      MsgNF.append( "<INPUT TYPE=\"hidden\" NAME=\"item\" VALUE=\"" + item + "\">\n" );
      MsgNF.append( "<INPUT TYPE=\"hidden\" NAME=\"window\" VALUE=\"" + window + "\">\n" );
	  MsgNF.append( "<INPUT TYPE=\"hidden\" NAME=\"depId\" VALUE=\"" + depId + "\">\n" );
	  MsgNF.append( "<INPUT TYPE=\"hidden\" NAME=\"statusId\" VALUE=\"" + statusID + "\">\n" );
      MsgNF.append( "<INPUT TYPE=\"hidden\" NAME=\"Session_ID\" VALUE=\"" + Session_ID + "\">\n" );
      MsgNF.append( "<INPUT TYPE=\"hidden\" NAME=\"Second\" VALUE=\"Yes\">\n" );
      MsgNF.append( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"Search\">\n" );
      MsgNF.append( "</FORM>\n" );

      return MsgNF;
    }
}
