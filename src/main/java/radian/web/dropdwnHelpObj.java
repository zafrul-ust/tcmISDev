/**
 * Title:        MSDS Viewer
 * Copyright:    Copyright (c) 2004
 * Company:      Radian
 * @author Nawaz Shaik
 * @version
 */
package radian.web;

import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;

public  abstract class dropdwnHelpObj
{
  public static Hashtable getdepareaData( TcmISDBModel cabdb,String facilityid,String company_id ) throws Exception
 {
   DBResultSet dbrs=null;
   ResultSet rs=null;

   String query="select distinct AREA_ID, DEPT_ID, PROCESS_ID from customer.fac_loc_app ";
   if (facilityid.length() > 0 && company_id.length() > 0 )
   {
	 query +="where FACILITY_ID = '" + facilityid + "' and COMPANY_ID = '"+company_id+"'";
   }
   query += " order by DEPT_ID,AREA_ID,PROCESS_ID";

   Hashtable resultdata=new Hashtable();
   Vector depID=new Vector();
   Vector areaID=new Vector();
   Vector processID=new Vector();

try
   {
	 dbrs=cabdb.doQuery( query );
	 rs=dbrs.getResultSet();

	 while ( rs.next() )
	 {
	   String tmpdepID=rs.getString( "DEPT_ID" );
	   String tmparea=rs.getString( "AREA_ID" );
	   String tmpprocess=rs.getString( "PROCESS_ID" );

	   if ( !depID.contains( tmpdepID ) )
	   {
		 depID.addElement( tmpdepID );
	   }

	   if ( !areaID.contains( tmparea ) )
	   {
		 areaID.addElement( tmparea );
	   }

	   if ( !processID.contains( tmpprocess ) )
	   {
		 processID.addElement( tmpprocess );
	   }
	 }
   }
   catch ( Exception e )
   {
	 e.printStackTrace();
   }
   finally
   {
	 if ( dbrs != null ){ dbrs.close(); }
   }

   resultdata.put( "DEPT_ID",depID );
   resultdata.put( "AREA_ID",areaID );
   resultdata.put( "PROCESS_ID",processID );

   return resultdata;
 }

 public static Hashtable getgmInitialData( TcmISDBModel cabdb,Hashtable hublist ) throws Exception
 {
   DBResultSet dbrs=null;
   ResultSet rs=null;

   Hashtable hubsetdetails= ( Hashtable ) ( hublist.get( "HUB_LIST" ) );
   String allowedhubs="";
   for ( Enumeration e=hubsetdetails.keys(); e.hasMoreElements(); )
   {
	 String branch_plant= ( String ) ( e.nextElement() );
	 String hub_name= ( String ) ( hubsetdetails.get( branch_plant ) );
	 allowedhubs+="'" + branch_plant + "',";
   }
   allowedhubs=allowedhubs.substring( 0,allowedhubs.length() - 1 );

   String query="select distinct BRANCH_PLANT,PREFERRED_WAREHOUSE,PLANT_ID,BLDG_ID from item_inventory_header_view where BRANCH_PLANT in (" + allowedhubs + ") and PLANT_ID is not null and BLDG_ID is not null order by PREFERRED_WAREHOUSE,PLANT_ID,BLDG_ID";
   Hashtable resultdata=new Hashtable();
   Vector hubID=new Vector();
   Vector hubDesc=new Vector();

   try
   {
	 dbrs=cabdb.doQuery( query );
	 rs=dbrs.getResultSet();

	 String lastHub="";

	 while ( rs.next() )
	 {
	   String tmpFacID=rs.getString( "PLANT_ID" );
	   String tmpHub=rs.getString( "BRANCH_PLANT" );
	   String tmpHubdesc=rs.getString( "PREFERRED_WAREHOUSE" );

	   if (!hubID.contains(tmpHub))
	   {
		 //hub info
		 hubID.addElement( tmpHub );
		 hubDesc.addElement( tmpHubdesc );
	   }

	   if ( !tmpHub.equalsIgnoreCase( lastHub ) )
	   {

		 Hashtable fh=new Hashtable();
		 //facility data
		 Vector facID=new Vector();
		 Vector facDesc=new Vector();
		 facID.addElement( tmpFacID );
		 facDesc.addElement( rs.getString( "PLANT_ID" ) );

		 Hashtable h=new Hashtable();
		 //application data
		 Vector application=new Vector();
		 Vector applicationDesc=new Vector();
		 application.addElement( rs.getString( "BLDG_ID" ) );
		 applicationDesc.addElement( rs.getString( "BLDG_ID" ) );
		 h.put( "BLDG",application );
		 h.put( "BLDG_DESC",applicationDesc );
		 fh.put( tmpFacID,h );
		 //putting them all together
		 fh.put( "PLANT_ID",facID );
		 fh.put( "PLANT_DESC",facDesc );
		 //resultdata.put( "DATA",fh );
		 resultdata.put( tmpHub,fh );
	   }
	   else
	   { //hub already exist
		 Hashtable fh= ( Hashtable ) resultdata.get( tmpHub );
		 Vector facID= ( Vector ) fh.get( "PLANT_ID" );
		 Vector facDesc= ( Vector ) fh.get( "PLANT_DESC" );
		 if ( !facID.contains( tmpFacID ) )
		 {
		   facID.addElement( tmpFacID );
		   facDesc.addElement( rs.getString( "PLANT_ID" ) );

		   Hashtable h=new Hashtable( 3 );
		   Vector application=new Vector();
		   Vector applicationDesc=new Vector();
		   application.addElement( rs.getString( "BLDG_ID" ) );
		   applicationDesc.addElement( rs.getString( "BLDG_ID" ) );
		   h.put( "BLDG",application );
		   h.put( "BLDG_DESC",applicationDesc );
		   fh.put( tmpFacID,h );
		 }
		 else
		 {
		   Hashtable h= ( Hashtable ) fh.get( tmpFacID );
		   Vector application= ( Vector ) h.get( "BLDG" );
		   Vector applicationDesc= ( Vector ) h.get( "BLDG_DESC" );
		   if ( !application.contains( rs.getString( "BLDG_ID" ) ) )
		   {
			 application.addElement( rs.getString( "BLDG_ID" ) );
			 applicationDesc.addElement( rs.getString( "BLDG_ID" ) );
		   }
		   h.put( "BLDG",application );
		   h.put( "BLDG_DESC",applicationDesc );
		   fh.put( tmpFacID,h );
		 }
		 fh.put( "PLANT_ID",facID );
		 fh.put( "PLANT_DESC",facDesc );
		 resultdata.put( tmpHub,fh );
	   }
	   lastHub=tmpHub;
	 }
   }
   catch ( Exception e )
   {
	 e.printStackTrace();
   }
   finally
   {
	 if ( dbrs != null ){ dbrs.close(); }
   }

   resultdata.put( "HUB_ID",hubID );
   resultdata.put( "HUB_DESC",hubDesc );

   return resultdata;
 }

 public static Hashtable getOperatingMethodData( TcmISDBModel cabdb,Hashtable hublist ) throws Exception
  {
	DBResultSet dbrs=null;
	ResultSet rs=null;

	Hashtable hubsetdetails= ( Hashtable ) ( hublist.get( "HUB_LIST" ) );
	String allowedhubs="";
	for ( Enumeration e=hubsetdetails.keys(); e.hasMoreElements(); )
	{
	  String branch_plant= ( String ) ( e.nextElement() );
	  String hub_name= ( String ) ( hubsetdetails.get( branch_plant ) );
	  allowedhubs+="'" + branch_plant + "',";
	}
	allowedhubs=allowedhubs.substring( 0,allowedhubs.length() - 1 );

	String query="select distinct INVENTORY_GROUP,RECEIPT_PROCESSING_METHOD, RECEIPT_PROCESSING_METHOD_DESC from IG_RECEIPT_PROCESSING_VIEW where HUB in (" + allowedhubs + ") order by INVENTORY_GROUP";
	Hashtable resultdata=new Hashtable();
	Vector invenGroupV=new Vector();

	try
	{
	  dbrs=cabdb.doQuery( query );
	  rs=dbrs.getResultSet();

	  String lastInvenGroup="";

	  while ( rs.next() )
	  {
		String operatingValue=rs.getString( "RECEIPT_PROCESSING_METHOD" );
		String tmpInvenGroup=rs.getString( "INVENTORY_GROUP" );

		if (!invenGroupV.contains(tmpInvenGroup))
		{
		  invenGroupV.addElement( tmpInvenGroup );
		}

		if ( !tmpInvenGroup.equalsIgnoreCase( lastInvenGroup ) )
		{
		  Hashtable fh=new Hashtable();
		  //facility data
		  Vector oprValueV=new Vector();
		  Vector oprMethodV=new Vector();
		  oprValueV.addElement( operatingValue );
		  oprMethodV.addElement( rs.getString( "RECEIPT_PROCESSING_METHOD_DESC" ) );

		  fh.put( "OPERATIONAL_VALUE",oprValueV );
		  fh.put( "OPERATIONAL_METHOD",oprMethodV );
		  resultdata.put( tmpInvenGroup,fh );
		}
		else
		{ //hub already exist
		  Hashtable fh= ( Hashtable ) resultdata.get( tmpInvenGroup );
		  Vector oprValueV= ( Vector ) fh.get( "OPERATIONAL_VALUE" );
		  Vector oprMethodV= ( Vector ) fh.get( "OPERATIONAL_METHOD" );
		  if ( !oprValueV.contains( operatingValue ) )
		  {
			oprValueV.addElement( operatingValue );
			oprMethodV.addElement( rs.getString( "RECEIPT_PROCESSING_METHOD_DESC" ) );
		  }

		  fh.put( "OPERATIONAL_VALUE",oprValueV );
		  fh.put( "OPERATIONAL_METHOD",oprMethodV );
		  resultdata.put( tmpInvenGroup,fh );
		}
		lastInvenGroup=tmpInvenGroup;
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}
	finally
	{
	  if ( dbrs != null ){ dbrs.close(); }
	}

	return resultdata;
  }

	public static Hashtable getBillingMethodData( TcmISDBModel cabdb,Hashtable hublist ) throws Exception
		{
		DBResultSet dbrs=null;
		ResultSet rs=null;

		Hashtable hubsetdetails= ( Hashtable ) ( hublist.get( "HUB_LIST" ) );
		String allowedhubs="";
		for ( Enumeration e=hubsetdetails.keys(); e.hasMoreElements(); )
		{
			String branch_plant= ( String ) ( e.nextElement() );
			String hub_name= ( String ) ( hubsetdetails.get( branch_plant ) );
			allowedhubs+="'" + branch_plant + "',";
		}
		allowedhubs=allowedhubs.substring( 0,allowedhubs.length() - 1 );

		String query="select distinct INVENTORY_GROUP,BILLING_METHOD, BILLING_METHOD_DESC from IG_BILLING_METHOD_VIEW where HUB in (" + allowedhubs + ") order by INVENTORY_GROUP";
		Hashtable resultdata=new Hashtable();
		Vector invenGroupV=new Vector();

		try
		{
			dbrs=cabdb.doQuery( query );
			rs=dbrs.getResultSet();

			String lastInvenGroup="";

			while ( rs.next() )
			{
			String operatingValue=rs.getString( "BILLING_METHOD" );
			String tmpInvenGroup=rs.getString( "INVENTORY_GROUP" );

			if (!invenGroupV.contains(tmpInvenGroup))
			{
				invenGroupV.addElement( tmpInvenGroup );
			}

			if ( !tmpInvenGroup.equalsIgnoreCase( lastInvenGroup ) )
			{
				Hashtable fh=new Hashtable();
				//facility data
				Vector oprValueV=new Vector();
				Vector oprMethodV=new Vector();
				oprValueV.addElement( operatingValue );
				oprMethodV.addElement( rs.getString( "BILLING_METHOD_DESC" ) );

				fh.put( "BILLING_VALUE",oprValueV );
				fh.put( "BILLING_METHOD",oprMethodV );
				resultdata.put( tmpInvenGroup,fh );
			}
			else
			{ //hub already exist
				Hashtable fh= ( Hashtable ) resultdata.get( tmpInvenGroup );
				Vector oprValueV= ( Vector ) fh.get( "BILLING_VALUE" );
				Vector oprMethodV= ( Vector ) fh.get( "BILLING_METHOD" );
				if ( !oprValueV.contains( operatingValue ) )
				{
				oprValueV.addElement( operatingValue );
				oprMethodV.addElement( rs.getString( "BILLING_METHOD_DESC" ) );
				}

				fh.put( "BILLING_VALUE",oprValueV );
				fh.put( "BILLING_METHOD",oprMethodV );
				resultdata.put( tmpInvenGroup,fh );
			}
			lastInvenGroup=tmpInvenGroup;
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			if ( dbrs != null ){ dbrs.close(); }
		}

		return resultdata;
		}

 public static String createComboBoxData( Hashtable initialData )
 {
   String tmp="";
   String tmpHub="var hubID = new Array(";
   String altFacID="var altFacID = new Array();\n ";
   String altFacDesc="var altFacDesc = new Array();\n ";
   String altWAID="var altWAID = new Array();\n ";
   String altWADesc="var altWADesc = new Array();\n ";
   int i=0;
   Vector hubIDV= ( Vector ) initialData.get( "HUB_ID" );
   for ( int ii=0; ii < hubIDV.size(); ii++ )
   {
	 String hubID= ( String ) hubIDV.elementAt( ii );
	 tmpHub+="\"" + hubID + "\"" + ",";

	 Hashtable fh= ( Hashtable ) initialData.get( hubID );

	 Vector facIDV= ( Vector ) fh.get( "PLANT_ID" );
	 Vector facDescV= ( Vector ) fh.get( "PLANT_DESC" );
	 if ( facIDV.size() > 1 && !facIDV.contains( "All Plants" ) )
	 {
	   facIDV.insertElementAt( "All Plants",0 );
	   facDescV.insertElementAt( "All Plants",0 );
	   Vector wAreaID=new Vector( 1 );
	   wAreaID.addElement( "All Buildings" );
	   Vector wAreaDesc=new Vector( 1 );
	   wAreaDesc.addElement( "All Buildings" );
	   Hashtable tmpFacWA=new Hashtable( 3 );
	   tmpFacWA.put( "BLDG",wAreaID );
	   tmpFacWA.put( "BLDG_DESC",wAreaDesc );
	   fh.put( "All Plants",tmpFacWA );
	 }
	 altFacID+="altFacID[\"" + hubID + "\"] = new Array(";
	 altFacDesc+="altFacDesc[\"" + hubID + "\"] = new Array(";
	 for ( i=0; i < facIDV.size(); i++ )
	 {
	   String facID= ( String ) facIDV.elementAt( i );
	   tmp+="\"" + facID + "\"" + ",";
	   altFacID+="\"" + facID + "\"" + ",";
	   altFacDesc+="\"" + ( String ) facDescV.elementAt( i ) + "\"" + ",";
	   //build work area
	   Hashtable h= ( Hashtable ) fh.get( facID );
	   Vector application= ( Vector ) h.get( "BLDG" );
	   Vector applicationDesc= ( Vector ) h.get( "BLDG_DESC" );
	   if ( !application.contains( "All Buildings" ) )
	   {
		 application.insertElementAt( "All Buildings",0 );
		 applicationDesc.insertElementAt( "All Buildings",0 );
	   }
	   altWAID+="altWAID[\"" + facID + "\"] = new Array(";
	   altWADesc+="altWADesc[\"" + facID + "\"] = new Array(";
	   for ( int j=0; j < application.size(); j++ )
	   {
		 altWAID+="\"" + ( String ) application.elementAt( j ) + "\"" + ",";
		 altWADesc+="\"" + HelpObjs.findReplace( ( String ) application.elementAt( j ),"\"","'" ) + "\"" + ",";
	   }
	   altWAID=altWAID.substring( 0,altWAID.length() - 1 ) + ");\n";
	   altWADesc=altWADesc.substring( 0,altWADesc.length() - 1 ) + ");\n";
	 }
	 //removing the last commas ','
	 altFacID=altFacID.substring( 0,altFacID.length() - 1 ) + ");\n";
	 altFacDesc=altFacDesc.substring( 0,altFacDesc.length() - 1 ) + ");\n";
   }

   if ( tmpHub.indexOf( "," ) > 1 )
   {
	 tmpHub=tmpHub.substring( 0,tmpHub.length() - 1 ) + ");\n";
   }

   return ( tmpHub + " " + altFacID + " " + altFacDesc + " " + altWAID + " " + altWADesc + "" );
 }
}