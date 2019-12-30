package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import oracle.jdbc.OracleTypes;
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
 */

public class corssTabLevels
{
  private ServerResourceBundle bundle=null;
  private TcmISDBModel db=null;
  private boolean allowupdate;
  //private String SelectedHubName="";
  private static org.apache.log4j.Logger invenlog=org.apache.log4j.Logger.getLogger( InvenLevelCheck.class );
  private boolean intcmIsApplication = false;

  public void setupdateStatus( boolean id )
  {
	this.allowupdate=id;
  }

  private boolean getupdateStatus() throws Exception
  {
	return this.allowupdate;
  }

  public corssTabLevels( ServerResourceBundle b,TcmISDBModel d )
  {
	bundle=b;
	db=d;
  }

  public void doResult( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
  {
	PrintWriter out=response.getWriter();
	response.setContentType( "text/html" );

	HttpSession invsession=request.getSession();

        PersonnelBean personnelBean = (PersonnelBean) invsession.getAttribute("personnelBean");
        Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
        intcmIsApplication = false;
        if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
        {
         intcmIsApplication = true;
        }

	String branch_plant=invsession.getAttribute( "BRANCH_PLANT" ) == null ? "" : invsession.getAttribute( "BRANCH_PLANT" ).toString();
	String personnelid=invsession.getAttribute( "PERSONNELID" ) == null ? "" : invsession.getAttribute( "PERSONNELID" ).toString();

	String User_Search=request.getParameter( "Search" );
	if ( User_Search == null )
	  User_Search="";
	User_Search=User_Search.trim();

	String User_Action=request.getParameter( "UserAction" );
	if ( User_Action == null )
	  User_Action="New";
	User_Action=User_Action.trim();

	User_Search=request.getParameter( "SearchField" );
	if ( User_Search == null )
	  User_Search="";
	User_Search=User_Search.trim();

	String searchlike=request.getParameter( "searchlike" );
	if ( searchlike == null )
	  searchlike="";
	searchlike=searchlike.trim();

	String searchfor=request.getParameter( "searchfor" );
	if ( searchfor == null )
	  searchfor="";
	searchfor=searchfor.trim();

	String invengrp=request.getParameter( "invengrp" );
	if ( invengrp == null )
	  invengrp="";
	invengrp=invengrp.trim();

	String showonlyissuedin=request.getParameter( "showonlyissuedin" );
	if ( showonlyissuedin == null )
	  showonlyissuedin="";
	showonlyissuedin=showonlyissuedin.trim();

	String issuedindays=request.getParameter( "issuedindays" );
	if ( issuedindays == null )
	  issuedindays="";
	issuedindays=issuedindays.trim();

	String showonlyonhand=request.getParameter( "showonlyonhand" );
	if ( showonlyonhand == null )
	  showonlyonhand="";
	showonlyonhand=showonlyonhand.trim();

	boolean createXls=User_Action.equalsIgnoreCase( "CreateExl" );

	try
	{
	  if ( User_Action.equalsIgnoreCase( "showcrosstablevels" ) )
	  {
		buildDetails( User_Search,invengrp,out );
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}
	finally
	{
	  out.close();
	}
  }

  private void buildDetails( String Item_id,String selinvengrp,PrintWriter out ) throws Exception
  {
	DBResultSet dbrs=null;
	ResultSet searchRs=null;
	Connection connect1=null;
	CallableStatement cs=null;
	Vector partdata=new Vector();
	Vector itemdata=new Vector();
	Hashtable partDataH=new Hashtable();
	Hashtable itemDataH=new Hashtable();
	Hashtable finalpartdata=new Hashtable();
	Hashtable finalitemdata=new Hashtable();
	Vector Data=new Vector();
	Hashtable DataH=new Hashtable();
	int mmcount = 0;
	int oorcount = 0;

	try
	{
	  connect1=db.getConnection();
	  cs=connect1.prepareCall( "{call PKG_LEVEL_MANAGEMENT.PR_LEVEL_MANAGEMENT_CROSSTAB(?,?,?,?)}" );
	  if ("All".equalsIgnoreCase(selinvengrp)){cs.setString( 1,"ALL" );}else{cs.setString( 1,selinvengrp );}

	  if (Item_id.trim().length() > 0){cs.setInt(2,Integer.parseInt(Item_id));}else{cs.setNull(2,java.sql.Types.INTEGER);}

	  cs.registerOutParameter( 3,OracleTypes.VARCHAR );
	  cs.registerOutParameter( 4,OracleTypes.VARCHAR );
	  cs.execute();

	  String errormsg=cs.getObject( 3 ) == null ? "" :cs.getObject( 3 ).toString();

	  String resultQuery=cs.getObject( 4 ).toString();
	  dbrs=db.doQuery( resultQuery+" order by STOCKING_METHOD " );
	  searchRs=dbrs.getResultSet();

	  //invenlog.debug("Error Msg from Procedure PR_LEVEL_MANAGEMENT -  "+errormsg+" searchstring "+searchstring+" searchlike  "+searchlike+"");
/*
   ResultSetMetaData rsMeta1 = searchRs.getMetaData();
   System.out.println("Finished The Querry\n "+rsMeta1.getColumnCount());
   for(int i =1; i<=rsMeta1.getColumnCount(); i++)
   {
   //System.out.println("String "+rsMeta1.getColumnName(i).toString().toLowerCase()+" = hD.get(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"&nbsp;\":hD.get(\""+rsMeta1.getColumnName(i).toString()+"\").toString();");
   //System.out.println("String "+rsMeta1.getColumnName(i).toString().toLowerCase()+" = ");
   //System.out.println("DataH.put(\""+rsMeta1.getColumnName(i).toString()+"\",rs.getString(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"\":rs.getString(\""+rsMeta1.getColumnName(i).toString()+"\"));");
   //System.out.println("//"+rsMeta1.getColumnName(i).toString()+"");
   System.out.println("DataH.put(\""+rsMeta1.getColumnName(i).toString()+"\","+rsMeta1.getColumnName(i).toString().toLowerCase()+");");
   }
*/
	  while ( searchRs.next() )
	  {
		DataH=new Hashtable();

		String catpartno  = searchRs.getString( "CAT_PART_NO" ) == null ? "" : searchRs.getString( "CAT_PART_NO" );
		String itemid = searchRs.getString( "ITEM_ID" ) == null ? "" : searchRs.getString( "ITEM_ID" );
		String stockingmethod = searchRs.getString( "STOCKING_METHOD" ) == null ? "" : searchRs.getString( "STOCKING_METHOD" );
		String company_id = searchRs.getString( "COMPANY_ID" ) == null ? "" : searchRs.getString( "COMPANY_ID" );
		String catalog_id = searchRs.getString( "CATALOG_ID" ) == null ? "" : searchRs.getString( "CATALOG_ID" );
		String current_stocking_method = searchRs.getString( "CURRENT_STOCKING_METHOD" ) == null ? "" : searchRs.getString( "CURRENT_STOCKING_METHOD" );
		String reorder_point = searchRs.getString( "REORDER_POINT" ) == null ? "" : searchRs.getString( "REORDER_POINT" );
		String stocking_level = searchRs.getString( "STOCKING_LEVEL" ) == null ? "" : searchRs.getString( "STOCKING_LEVEL" );
		String status = searchRs.getString( "STATUS" ) == null ? "" : searchRs.getString( "STATUS" );
		String priority = searchRs.getString( "PRIORITY" ) == null ? "" : searchRs.getString( "PRIORITY" );
		String on_hand = searchRs.getString( "ON_HAND" ) == null ? "" : searchRs.getString( "ON_HAND" );

		String partkey = company_id + "/" + catalog_id + "<BR>" + catpartno;

		if (!partdata.contains(partkey))
		{
		  partdata.add(partkey);
		  partDataH=new Hashtable();

		  partDataH.put( "CURRENT_STOCKING_METHOD",current_stocking_method);
		  partDataH.put( "REORDER_POINT",reorder_point);
		  partDataH.put( "STOCKING_LEVEL",stocking_level);
		  partDataH.put( "COMPANY_ID",company_id);
		  partDataH.put( "CATALOG_ID",catalog_id);

		  if ("MM".equalsIgnoreCase(stockingmethod) )
		  {
			mmcount++;
			partDataH.put( "CELL_ID","MM"+mmcount+"");
		  }
		  else if ("OOR".equalsIgnoreCase(stockingmethod) )
		  {
			oorcount++;
			partDataH.put( "CELL_ID","OOR"+oorcount+"");
		  }
		  finalpartdata.put(partkey,partDataH);
		}

		if (!itemdata.contains(itemid))
		{
		  itemdata.add(itemid);
		  itemDataH=new Hashtable();

		  itemDataH.put( "ON_HAND",on_hand);
		  finalitemdata.put(itemid,itemDataH);
		}

		//DataH.put("LOOKUP_ITEM_ID",lookup_item_id);
		//DataH.put("INVENTORY_GROUP",inventory_group);
		DataH.put("COMPANY_ID",company_id);
		DataH.put("CATALOG_ID",catalog_id);
		DataH.put("CAT_PART_NO",catpartno);
		//DataH.put("STOCKING_METHOD",stocking_method);
		DataH.put("CURRENT_STOCKING_METHOD",current_stocking_method);
		DataH.put("REORDER_POINT",reorder_point);
		DataH.put("STOCKING_LEVEL",stocking_level);
		DataH.put("ITEM_ID",itemid);
		DataH.put("STATUS",status);
		DataH.put("PRIORITY",priority);
		DataH.put("ON_HAND",on_hand);

		Data.addElement( DataH );
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	  throw e;
	}
	finally
	{
	  if ( dbrs != null ){dbrs.close();}
	  if ( cs != null ){ try{cs.close();}catch ( SQLException se ){}}
	}

	out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Inventory Level Management Cross Reference","invenlevelmgmt.js",intcmIsApplication ) );
	//out.println("<SCRIPT SRC=\"/clientscripts/invengrpdropdown.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
	out.println( "<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n <!--\n" );

	out.println( "// -->\n </SCRIPT>\n" );
	out.println( "</HEAD>  \n" );


	try
	{
	  int total=partdata.size();
	  if ( total == 0 )
	  {
		out.println( "<BODY>\n" );
		out.println( "No Records Found " );
		return ;
	  }
	  else
	  {
		out.println( "<BODY onLoad=\"showcrosstabdata()\">\n" );
	  }
	  out.println( radian.web.HTMLHelpObj.PrintTitleBarNolink( "<B>Inventory Level Management Cross Reference:</B>\n" ) );
 	  out.println( "<TABLE  BORDER=\"0\" BGCOLOR=\"#a2a2a2\" CELLSPACING=1 CELLPADDING=2 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
	  out.println( "<tr align=\"center\">\n" );
	  out.println( "<TH width=\"1%\" ROWSPAN=\"2\" height=\"38\">Item</TH>\n" );
	  out.println( "<TH width=\"1%\" ROWSPAN=\"2\" height=\"38\">On Hand</TH>\n" );

	  if (mmcount > 0)
	  {
		out.println( "<TH width=\"50%\" COLSPAN=\""+mmcount+"\" height=\"38\">MM</TH>\n" );
	  }

	  if (oorcount > 0)
	  {
		out.println( "<TH width=\"50%\" COLSPAN=\""+oorcount+"\" height=\"38\">OOR</TH>\n" );
	  }
	  out.println( "</TR>\n" );

	  for ( int partloop=0; partloop < total; partloop++ )
	  {
		String partkey = ( String ) partdata.elementAt( partloop );

		Hashtable hD=new Hashtable();
		hD= ( Hashtable ) finalpartdata.get(partkey);

		String sscurrent_stocking_method=hD.get( "CURRENT_STOCKING_METHOD" ) == null ? "&nbsp;" : hD.get( "CURRENT_STOCKING_METHOD" ).toString();
		String ssreorder_point=hD.get( "REORDER_POINT" ) == null ? "&nbsp;" : hD.get( "REORDER_POINT" ).toString();
		String ssstocking_level=hD.get( "STOCKING_LEVEL" ) == null ? "&nbsp;" : hD.get( "STOCKING_LEVEL" ).toString();

		out.println( "<TH width=\"5%\" height=\"38\">"+partkey+"<BR>("+sscurrent_stocking_method+","+ssreorder_point+","+ssstocking_level+")</TH>\n" );
	  }

	  out.println( "</TR>\n" );


	  for ( int itemloop=0; itemloop < itemdata.size(); itemloop++ )
	  {
		String itemkey= ( String ) itemdata.elementAt( itemloop );

		Hashtable hD=new Hashtable();
		hD= ( Hashtable ) finalitemdata.get( itemkey );
		String Color=" ";
		if ( itemloop % 2 == 0 )
		{
		  Color="CLASS=\"white";
		}
		else
		{
		  Color="CLASS=\"blue";
		}

		String sson_hand=hD.get( "ON_HAND" ) == null ? "&nbsp;" : hD.get( "ON_HAND" ).toString();

		out.println( "<TR align=\"center\">\n" );
		out.println( "<td " + Color + "\" width=\"5%\"  height=\"38\">" +itemkey+ "</td>\n" );
		out.println( "<td " + Color + "\" width=\"5%\"  height=\"38\">" +sson_hand+ "</td>\n" );

		for ( int partloop=0; partloop < total; partloop++ )
		{
		String partkey = ( String ) partdata.elementAt( partloop );
		Hashtable parthD=new Hashtable();
		parthD= ( Hashtable ) finalpartdata.get(partkey);

		String cellid=parthD.get( "CELL_ID" ) == null ? "&nbsp;" : parthD.get( "CELL_ID" ).toString();

		out.println( "<td " + Color + "\" width=\"5%\"  height=\"38\" ID=\""+itemkey+"-"+cellid+"\">&nbsp;</td>\n" );
	    }
		out.println( "</TR>\n" );
	  }

	  out.println( "<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n <!--\n" );
	  out.println( "function showcrosstabdata()\n" );
	  out.println( "{\n" );
	  int i = 0;
	  for ( int finalloop=0; finalloop < Data.size(); finalloop++ )
	  {
		Hashtable hD=new Hashtable();
		hD= ( Hashtable ) Data.elementAt( finalloop );

		String sscompany_id=hD.get( "COMPANY_ID" ) == null ? "" : hD.get( "COMPANY_ID" ).toString();
		String sscatalog_id=hD.get( "CATALOG_ID" ) == null ? "" : hD.get( "CATALOG_ID" ).toString();
		String sscat_part_no=hD.get( "CAT_PART_NO" ) == null ? "" : hD.get( "CAT_PART_NO" ).toString();
		String itemkey=hD.get( "ITEM_ID" ) == null ? "" : hD.get( "ITEM_ID" ).toString();
		String ssstatus=hD.get( "STATUS" ) == null ? "" : hD.get( "STATUS" ).toString();
		String sspriority=hD.get( "PRIORITY" ) == null ? "" : hD.get( "PRIORITY" ).toString();

		String partkey = sscompany_id + "/" + sscatalog_id + "<BR>" + sscat_part_no;

	     Hashtable parthD=new Hashtable();
	     parthD= ( Hashtable ) finalpartdata.get(partkey);
		 String cellid=parthD.get( "CELL_ID" ) == null ? "" : parthD.get( "CELL_ID" ).toString();

		 out.println( "crosstab"+finalloop+" = document.getElementById(\""+itemkey+"-"+cellid+"\");\n" );
		 out.println( "crosstab"+finalloop+".innerHTML = \"" + ssstatus + " "+sspriority+"\";\n" );
	  }
      out.println( "}\n" );
	  out.println( "// -->\n </SCRIPT>\n" );

	  out.println( "</table>\n" );
	  out.println( "</body></html>\n" );
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	  out.println(radian.web.HTMLHelpObj.printHTMLError());
	  return;
	}

	return;
  }
}