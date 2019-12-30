/**
 * Title:        Resoource Loader
 * Copyright:    Copyright (c) 2001
 * Company:      Radian
 * @author Nawaz Shaik
 * @version
 *
 * Moving All the methods I call when I login to a page here.
 * 01-30-04 - Showing only Active Payment Terms for PO pages and showing all for AP pages
 * 02-18-04 - Adding VAPOR_PRESSURE_DETECT, VAPOR_PRESSURE, VAPOR_PRESSURE_UNIT, VAPOR_PRESSURE_TEMP, VAPOR_PRESSURE_TEMP_UNIT to page.
 *            Made changes to the Buyorder status to work with DBuy
 * 04-07-04 - Getting vv vector for statuses that are not assignable from the buy page.
 * 11-10-04 - Gettting vv Vector for data sources
 * 01-15-05 - VV procedures for Dana Projects page and the newsupplier page
 */
package radian.web;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;

import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;

public abstract class vvHelpObj
{

 public static Vector getLabelColors(TcmISDBModel dbObj3,String requestId)
 {
	String query="select iglc.label_color from inventory_group_label_color iglc, catalog_add_request_new b,customer.facility c ";
	query +=" where b.request_id = "+requestId+" and b.eng_eval_facility_id = c.facility_id and ";
	query +=" c.inventory_group_default = iglc.inventory_group order by iglc.label_color";

	DBResultSet dbrs=null;
	ResultSet rs=null;
	Hashtable result=null;
	Vector ResultV=new Vector();
	try
	{
		dbrs=dbObj3.doQuery( query );
		rs=dbrs.getResultSet();
		while ( rs.next() )
		{
		result=new Hashtable();
		String labelColor= ( rs.getString( "label_color" ) == null ? "" : rs.getString( "label_color" ) );
		result.put( labelColor,labelColor );
		ResultV.addElement( result );
		}
	}
	catch ( Exception e )
	{
		e.printStackTrace();
	}
	finally
	{
		if ( dbrs != null )
		{
		dbrs.close();
		}
	}
	return ResultV;
	}

  public static Vector getvvContectType(TcmISDBModel dbObj3)
   {
	 String query="select * from vv_supplier_contact_type order by CONTACT_TYPE asc";
	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 Hashtable result=null;
	 Vector ResultV=new Vector();
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 result=new Hashtable();
		 String faci= ( rs.getString( "CONTACT_TYPE" ) == null ? "" : rs.getString( "CONTACT_TYPE" ) );
		 String facn= ( rs.getString( "CONTACT_TYPE" ) == null ? "" : rs.getString( "CONTACT_TYPE" ) );
		 result.put( facn,faci );
		 ResultV.addElement( result );
	   }
	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }
	 return ResultV;
   }

   public static Hashtable getfob(TcmISDBModel dbObj3)
   {
	 String query="select FREIGHT_ON_BOARD,DESCRIPTION from vv_freight_on_board where STATUS = 'ACTIVE' order by DESCRIPTION";
	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 Hashtable result=new Hashtable();
	 //Vector ResultV=new Vector();
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 //result=new Hashtable();
		 String faci= ( rs.getString( "FREIGHT_ON_BOARD" ) == null ? "" : rs.getString( "FREIGHT_ON_BOARD" ) );
		 String facn= ( rs.getString( "DESCRIPTION" ) == null ? "" : rs.getString( "DESCRIPTION" ) );
		 result.put( faci,facn );
		 //ResultV.addElement( result );
	   }
	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }
	 return result;
   }

   public static Vector getaddchargeType(TcmISDBModel dbObj3)
   {
	 String query="select ITEM_TYPE,TYPE_DESC from global.vv_item_type where MA_ADD_CHARGE = 'y'";
	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 Hashtable result=null;
	 Vector ResultV=new Vector();
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 //result = new Hashtable();
		 //String faci  = (rs.getString("FREIGHT_ON_BOARD")==null?"":rs.getString("FREIGHT_ON_BOARD"));
		 String facn= ( rs.getString( "ITEM_TYPE" ) == null ? "" : rs.getString( "ITEM_TYPE" ) );
		 //result.put(facn,faci);
		 ResultV.addElement( facn );
	   }
	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }
	 return ResultV;
   }

   public static Vector getSecondarySupplierTypes(TcmISDBModel dbObj3)
   {
	 String query="select ITEM_TYPE,TYPE_DESC from global.vv_item_type where SECONDARY_SUPPLIER_ON_PO = 'Y'";
	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 Hashtable result=null;
	 Vector ResultV=new Vector();
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 //result = new Hashtable();
		 //String faci  = (rs.getString("FREIGHT_ON_BOARD")==null?"":rs.getString("FREIGHT_ON_BOARD"));
		 String facn= ( rs.getString( "ITEM_TYPE" ) == null ? "" : rs.getString( "ITEM_TYPE" ) );
		 //result.put(facn,faci);
		 ResultV.addElement( facn );
	   }
	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }
	 return ResultV;
   }


   public static Vector getcategory(TcmISDBModel dbObj3)
   {
	 String query="select CATEGORY_ID,CATEGORY_DESC  from vv_category order by CATEGORY_DESC";
	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 Hashtable result=null;
	 Vector ResultV=new Vector();
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 result=new Hashtable();
		 String faci= ( rs.getString( "CATEGORY_ID" ) == null ? "" : rs.getString( "CATEGORY_ID" ) );
		 String facn= ( rs.getString( "CATEGORY_DESC" ) == null ? "" : rs.getString( "CATEGORY_DESC" ) );
		 result.put( facn,faci );
		 ResultV.addElement( result );
	   }
	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }
	 return ResultV;
   }

   public static Vector gethubcompanyFacilities(TcmISDBModel dbObj3)
   {
	 String query="select distinct BRANCH_PLANT, HUB, COMPANY_ID, FACILITY_ID from hub_facilities_view";
	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 Hashtable result=null;
	 Vector ResultV=new Vector();
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 result=new Hashtable();
		 String faci= ( rs.getString( "BRANCH_PLANT" ) == null ? "" : rs.getString( "BRANCH_PLANT" ) );
		 String facn= ( rs.getString( "FACILITY_ID" ) == null ? "" : rs.getString( "FACILITY_ID" ) );

		 result.put( facn,faci );

		 String hubnum= ( rs.getString( "HUB" ) == null ? "" : rs.getString( "HUB" ) );
		 String companyid= ( rs.getString( "COMPANY_ID" ) == null ? "" : rs.getString( "COMPANY_ID" ) );

		 result.put( "BRANCHPLANT",faci );
		 result.put( "HUB",hubnum );
		 result.put( "FACILITYID",facn );
		 result.put( hubnum,companyid );

		 ResultV.addElement( result );
	   }
	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }
	 return ResultV;
   }

   public static Vector getcatCompanyIds(TcmISDBModel dbObj3)
  {
	String query="select distinct COMPANY_ID from catalog where catalog_id not like '%TCM%' and CATALOG_DESC is not null order by COMPANY_ID";
	DBResultSet dbrs=null;
	ResultSet rs=null;

	Vector ResultV=new Vector();
	ResultV.addElement( "ALL" );
	try
	{
	  dbrs=dbObj3.doQuery( query );
	  rs=dbrs.getResultSet();
	  while ( rs.next() )
	  {
		ResultV.addElement( rs.getString( "COMPANY_ID" ) == null ? "" : rs.getString( "COMPANY_ID" ) );
	  }

	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}
	finally
	{
	  if ( dbrs != null )
	  {
		dbrs.close();
	  }
	}
	return ResultV;
  }

  public static Vector getCompanyIds(TcmISDBModel dbObj3)
   {
	 String query="select distinct x.COMPANY_ID,y.company_name from catalog x, company y where x.catalog_id not like '%TCM%' and x.CATALOG_DESC is not null and x.company_id = y.company_id order by COMPANY_ID";
	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 Hashtable result=null;
	 Vector ResultV=new Vector();
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 result=new Hashtable();
		 String companyid= ( rs.getString( "COMPANY_ID" ) == null ? "" : rs.getString( "COMPANY_ID" ) );
         String companyName= ( rs.getString( "COMPANY_NAME" ) == null ? "" : rs.getString( "COMPANY_NAME" ) );
         result.put( companyid,companyid );
		 ResultV.addElement( result );
	   }
	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }
	 return ResultV;
   }

   public static Vector getsortby(TcmISDBModel dbObj3)
   {
	 String query="select * from vv_buypage_sort order by SORT_DESC";
	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 Hashtable result=null;
	 Vector ResultV=new Vector();
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 result=new Hashtable();
		 String faci= ( rs.getString( "SORT_ID" ) == null ? "" : rs.getString( "SORT_ID" ) );
		 String facn= ( rs.getString( "SORT_DESC" ) == null ? "" : rs.getString( "SORT_DESC" ) );
		 result.put( facn,faci );
		 ResultV.addElement( result );
	   }
	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }
	 return ResultV;
   }
   public static Vector getorderstatus(TcmISDBModel dbObj3)
   {
	 return getorderstatus(dbObj3,"");
   }

   public static Vector getorderstatus(TcmISDBModel dbObj3,String lockstatus)
   {
	 String query="select STATUS from vv_buy_order_status ";

	 if ("setonly".equalsIgnoreCase(lockstatus))
	 {
	   query+=" where BUYPAGE_ASSIGNABLE = 'Y' ";
	 }
	 else if (lockstatus.length() >0)
	 {
	   query+=" where LOCK_STATUS = '"+lockstatus+"' ";
	 }

	query += "order by DISPLAY_SORT";

	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 Hashtable result=null;
	 Vector ResultV=new Vector();
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 result=new Hashtable();
		 String faci= ( rs.getString( "STATUS" ) == null ? "" : rs.getString( "STATUS" ) );
		 String facn= ( rs.getString( "STATUS" ) == null ? "" : rs.getString( "STATUS" ) );
		 result.put( facn,facn );
		 if ( "Y".equalsIgnoreCase( lockstatus ) )
		 {
		   ResultV.addElement( faci );
		 }
		 else
		 {
		   ResultV.addElement( result );
		 }
	   }
	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }
	 return ResultV;
   }

   public static Vector nonassignablestatuses(TcmISDBModel dbObj3)
  {
	String query="select STATUS from vv_buy_order_status where BUYPAGE_ASSIGNABLE = 'N' ";
	DBResultSet dbrs=null;
	ResultSet rs=null;
	Vector ResultV=new Vector();
	try
	{
	  dbrs=dbObj3.doQuery( query );
	  rs=dbrs.getResultSet();
	  while ( rs.next() )
	  {
		String facn= ( rs.getString( "STATUS" ) == null ? "" : rs.getString( "STATUS" ) );
		ResultV.addElement( facn );
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}
	finally
	{
	  dbrs.close();
	}
	return ResultV;
  }

   public static Vector getmfgs(TcmISDBModel dbObj3)
   {
	 String query="select MFG_ID,MFG_DESC  from manufacturer order by MFG_DESC";
	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 Hashtable result=null;
	 Vector ResultV=new Vector();
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 result=new Hashtable();
		 String faci= ( rs.getString( "MFG_ID" ) == null ? "" : rs.getString( "MFG_ID" ) );
		 String facn= ( rs.getString( "MFG_DESC" ) == null ? "" : rs.getString( "MFG_DESC" ) );
		 result.put( facn,facn );
		 ResultV.addElement( result );
	   }
	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }
	 return ResultV;
   }


   public static Vector getPaymentTerms(TcmISDBModel dbObj3,boolean showinactive)
   {
	 String query= "";
	 if (showinactive)
	 {
	   query="select * from vv_payment_terms where nvl(PREPAY_TERMS,'N') <> 'Y' order by PAYMENT_TERMS desc";
	 }
	 else
	 {
	   query="select * from vv_payment_terms where STATUS = 'ACTIVE' and nvl(PREPAY_TERMS,'N') <> 'Y' order by PAYMENT_TERMS desc";
	 }

	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 Hashtable result=null;
	 Vector ResultV=new Vector();
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 result=new Hashtable();
		 String faci= ( rs.getString( "PAYMENT_TERMS" ) == null ? "" : rs.getString( "PAYMENT_TERMS" ) );
		 //String facn  = (rs.getString("PAYMENT_TERMS")==null?"":rs.getString("PAYMENT_TERMS"));
		 result.put( faci,faci );
		 ResultV.addElement( result );
	   }
	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }
	 return ResultV;
   }

   public static Vector getvvvouvherstatus(TcmISDBModel dbObj3)
   {
	 String query="select VOUCHER_STATUS from vv_voucher_status order by VOUCHER_STATUS";
	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 Hashtable result=null;
	 Vector ResultV=new Vector();
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 result=new Hashtable();
		 String facn= ( rs.getString( "VOUCHER_STATUS" ) == null ? "" : rs.getString( "VOUCHER_STATUS" ) );
		 result.put( facn,facn );
		 ResultV.addElement( result );
	   }
	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   dbrs.close();
	 }
	 return ResultV;
   }

   public static Vector getsphazard(TcmISDBModel dbObj3)
   {
	 String query="select SPECIFIC_HAZARD,SPECIFIC_HAZARD_DESCRIPTION from global.vv_specific_hazard order by SPECIFIC_HAZARD";
	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 Hashtable result=null;
	 Vector ResultV=new Vector();
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 result=new Hashtable();
		 String faci= ( rs.getString( "SPECIFIC_HAZARD" ) == null ? "" : rs.getString( "SPECIFIC_HAZARD" ) );
		 String facn= ( rs.getString( "SPECIFIC_HAZARD_DESCRIPTION" ) == null ? "" : rs.getString( "SPECIFIC_HAZARD_DESCRIPTION" ) );
		 result.put( facn,faci );
		 ResultV.addElement( result );
	   }

	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }
	 return ResultV;
   }

   public static Vector getppe(TcmISDBModel dbObj3)
   {
	 String query="select PERSONAL_PROTECTIVE_EQUIPMENT,PPE_DESCRIPTION  from global.vv_ppe order by PERSONAL_PROTECTIVE_EQUIPMENT";
	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 Hashtable result=null;
	 Vector ResultV=new Vector();
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 result=new Hashtable();
		 String faci= ( rs.getString( "PERSONAL_PROTECTIVE_EQUIPMENT" ) == null ? "" : rs.getString( "PERSONAL_PROTECTIVE_EQUIPMENT" ) );
		 //String facn= ( rs.getString( "CATEGORY_DESC" ) == null ? "" : rs.getString( "CATEGORY_DESC" ) );
		 result.put( faci,faci );
		 ResultV.addElement( result );
	   }

	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }
	 return ResultV;
   }

   //Builds the JavaScript arryas containing the names of the companies and the catalog names
   public static StringBuffer getCatalogJs( TcmISDBModel dbObj3, Vector companyId )
   {
	 StringBuffer MsgJs=new StringBuffer();
	 MsgJs.append( "var companynames = new Array(" );

	 String test1="";
	 for ( int i=0; i < companyId.size(); i++ )
	 {
	   test1+="\"" + companyId.elementAt( i ).toString() + "\"" + ",";
	 }
	 test1=test1.substring( 0,test1.length() - 1 );

	 MsgJs.append( test1 );
	 MsgJs.append( ");\n" );
	 MsgJs.append( "var altName = new Array();\n" );

	 for ( int i=0; i < companyId.size(); i++ )
	 {
	   MsgJs.append( "" + getCatalogArray( dbObj3,companyId.elementAt( i ).toString() ) + "" );
	 }

	 return MsgJs;
   }


   public static String getCatalogArray( TcmISDBModel dbObj3, String company_Id )
   {
	 String JsArray="altName[\"" + company_Id + "\"] = new Array(";
	 String query="select distinct COMPANY_ID,CATALOG_DESC,CATALOG_ID from catalog where catalog_id not like '%TCM%' ";

	 if ( "ALL".equalsIgnoreCase( company_Id ) )
	 {
	   JsArray+="\"First Choose a Company\"";
	   JsArray+=");\n";
	   return JsArray;
	 }
	 else
	 {
	   query+="and COMPANY_ID='" + company_Id + "' ";
	 }
	 query+="and CATALOG_DESC is not null order by COMPANY_ID,CATALOG_DESC";

	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 JsArray+="\"All\",";

	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 //String test = rs.getString("CATALOG_DESC")==null?" ":rs.getString("CATALOG_DESC");
		 String test=rs.getString( "CATALOG_ID" ) == null ? " " : rs.getString( "CATALOG_ID" );
		 JsArray+="\"" + test + "\",";
	   }
	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }

	 JsArray=JsArray.substring( 0,JsArray.length() - 1 );
	 JsArray+=");\n";
	 return JsArray;

   }

   public static Vector getCatalogDescs( TcmISDBModel dbObj3,String companyId1 )
   {
	 String query="select distinct COMPANY_ID,CATALOG_DESC,CATALOG_ID from catalog where catalog_id not like '%TCM%' and COMPANY_ID='" + companyId1 + "' and CATALOG_DESC is not null order by COMPANY_ID,CATALOG_DESC";
	 DBResultSet dbrs=null;
	 ResultSet rs=null;

	 Vector ResultV=new Vector();
	 ResultV.addElement( "ALL" );
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 //ResultV.addElement(rs.getString("CATALOG_DESC")==null?"":rs.getString("CATALOG_DESC"));
		 ResultV.addElement( rs.getString( "CATALOG_ID" ) == null ? "" : rs.getString( "CATALOG_ID" ) );
	   }

	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }
	 return ResultV;
   }

   public static Vector getweightunit(TcmISDBModel dbObj3)
   {
//	 String query="select from_unit from size_unit_conversion order by from_unit";
	 String query="select SIZE_UNIT from vv_size_unit where unit_type in ('weight e','weight m','volume e','volume m') ";
	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 Hashtable result=null;
	 Vector ResultV=new Vector();
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 result=new Hashtable();
		 //String faci  = (rs.getString("CATEGORY_ID")==null?"":rs.getString("CATEGORY_ID"));
		 String facn= ( rs.getString( "SIZE_UNIT" ) == null ? "" : rs.getString( "SIZE_UNIT" ) );
		 result.put( facn,facn );
		 ResultV.addElement( result );
	   }

	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }
	 return ResultV;
   }

   public static Vector getitemtype(TcmISDBModel dbObj3)
   {
	 String query="select ITEM_TYPE from global.vv_item_type order by item_type";
	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 Hashtable result=null;
	 Vector ResultV=new Vector();
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 result=new Hashtable();
		 String facn= ( rs.getString( "ITEM_TYPE" ) == null ? "" : rs.getString( "ITEM_TYPE" ) );
		 result.put( facn,facn );
		 ResultV.addElement( result );
	   }

	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }
	 return ResultV;
   }

   public static Vector getphysicalstate(TcmISDBModel dbObj3)
   {
	 String query="select PHYSICAL_STATE from vv_physical_state order by PHYSICAL_STATE";
	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 Hashtable result=null;
	 Vector ResultV=new Vector();
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 result=new Hashtable();
		 String facn= ( rs.getString( "PHYSICAL_STATE" ) == null ? "" : rs.getString( "PHYSICAL_STATE" ) );
		 result.put( facn,facn );
		 ResultV.addElement( result );
	   }

	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }
	 return ResultV;
   }


   public static Vector getdensityunit(TcmISDBModel dbObj3)
   {
	 String query="select DENSITY_UNIT from global.VV_DENSITY_UNIT order by DENSITY_UNIT";
	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 Hashtable result=null;
	 Vector ResultV=new Vector();
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 result=new Hashtable();
		 String facn= ( rs.getString( "DENSITY_UNIT" ) == null ? "" : rs.getString( "DENSITY_UNIT" ) );
		 result.put( facn,facn );
		 ResultV.addElement( result );
	   }

	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }
	 return ResultV;
   }

   public static Vector getvapourpressdetect(TcmISDBModel dbObj3)
   {
    String query="select DETECT,DESCRIPTION from vv_detect order by DESCRIPTION";
	DBResultSet dbrs=null;
	ResultSet rs=null;
	Hashtable result=null;
	Vector ResultV=new Vector();
	try
	{
	  dbrs=dbObj3.doQuery( query );
	  rs=dbrs.getResultSet();
	  while ( rs.next() )
	  {
		result=new Hashtable();
		String facn= ( rs.getString( "DETECT" ) == null ? "" : rs.getString( "DETECT" ) );
		String fac1= ( rs.getString( "DESCRIPTION" ) == null ? "" : rs.getString( "DESCRIPTION" ) );
		result.put( fac1,facn );
		ResultV.addElement( result );
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}
	finally
	{
	  if ( dbrs != null ) {dbrs.close();}
	}
	return ResultV;
   }

   public static Vector getvapourpressunit(TcmISDBModel dbObj3)
  {
	String query="select VAPOR_PRESSURE_UNIT from global.VV_VAPOR_PRESSURE_UNIT order by VAPOR_PRESSURE_UNIT";
	DBResultSet dbrs=null;
	ResultSet rs=null;
	Hashtable result=null;
	Vector ResultV=new Vector();
	try
	{
	  dbrs=dbObj3.doQuery( query );
	  rs=dbrs.getResultSet();
	  while ( rs.next() )
	  {
		result=new Hashtable();
		String facn= ( rs.getString( "VAPOR_PRESSURE_UNIT" ) == null ? "" : rs.getString( "VAPOR_PRESSURE_UNIT" ) );
		result.put( facn,facn );
		ResultV.addElement( result );
	  }

	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}
	finally
	{
	  if ( dbrs != null ) {dbrs.close();}
	}
	return ResultV;
  }

  public static Vector getvocsource(TcmISDBModel dbObj3)
   {

	 String query="select DATA_SOURCE from global.vv_data_source order by DATA_SOURCE";
	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 Hashtable result=null;
	 Vector ResultV=new Vector();
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 result=new Hashtable();
		 String facn= ( rs.getString( "DATA_SOURCE" ) == null ? "" : rs.getString( "DATA_SOURCE" ) );
		 //String fac1  = (rs.getString("CATEGORY_DESC")==null?"":rs.getString("CATEGORY_DESC"));
		 result.put( facn,facn );
		 ResultV.addElement( result );
	   }

	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }
	 return ResultV;
   }

   public static Vector getvocunit(TcmISDBModel dbObj3)
   {

	 String query="select VOC_UNIT from global.vv_voc_unit where VOC_UNIT <> '%(v/v)' order by VOC_UNIT";
	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 Hashtable result=null;
	 Vector ResultV=new Vector();
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 result=new Hashtable();
		 String facn= ( rs.getString( "VOC_UNIT" ) == null ? "" : rs.getString( "VOC_UNIT" ) );
		 //String fac1  = (rs.getString("CATEGORY_DESC")==null?"":rs.getString("CATEGORY_DESC"));
		 result.put( facn,facn );
		 ResultV.addElement( result );
	   }

	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }
	 return ResultV;
   }

   public static Vector getstoragetemp(TcmISDBModel dbObj3)
   {
	 String query="select DISPLAY_ORDER,TEMPERATURE from global.vv_storage_temp order by DISPLAY_ORDER";
	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 Hashtable result=null;
	 Vector ResultV=new Vector();
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 result=new Hashtable();
		 //String faci  = (rs.getString("DISPLAY_ORDER")==null?"":rs.getString("DISPLAY_ORDER"));
		 String temperature= ( rs.getString( "TEMPERATURE" ) == null ? "" : rs.getString( "TEMPERATURE" ) );
		 result.put( temperature,temperature );
		 ResultV.addElement( result );
	   }

	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }
	 return ResultV;
   }

   public static Vector getpkgstyle(TcmISDBModel dbObj3)
   {
	 String query="select PKG_STYLE from global.vv_pkg_style order by PKG_STYLE";
	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 Hashtable result=null;
	 Vector ResultV=new Vector();
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 result=new Hashtable();
		 String pkgStyle= ( rs.getString( "PKG_STYLE" ) == null ? "" : rs.getString( "PKG_STYLE" ) );
		 result.put( pkgStyle,pkgStyle );
		 ResultV.addElement( result );
		 //ResultV.addElement((rs.getString("PKG_STYLE")==null?"":rs.getString("PKG_STYLE")));
	   }

	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }
	 return ResultV;
   }

   public static Vector getsizeunit(TcmISDBModel dbObj3)
   {
	 String query="select SIZE_UNIT from global.vv_size_unit order by SIZE_UNIT";
	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 Hashtable result=null;
	 Vector ResultV=new Vector();
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 result=new Hashtable();
		 String sizeUnit= ( rs.getString( "SIZE_UNIT" ) == null ? "" : rs.getString( "SIZE_UNIT" ) );
		 result.put( sizeUnit,sizeUnit );
		 ResultV.addElement( result );
		 //ResultV.addElement((rs.getString("SIZE_UNIT")==null?"":rs.getString("SIZE_UNIT")));
	   }

	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }
	 return ResultV;
   }

    public static Vector getNetWtSizeUnit(TcmISDBModel dbObj3)
    {
      String query="select size_unit from global.size_unit_view where net_wt_required <> 'Y' order by size_unit";
      DBResultSet dbrs=null;
      ResultSet rs=null;
      Hashtable result=null;
      Vector ResultV=new Vector();
      try
      {
        dbrs=dbObj3.doQuery( query );
        rs=dbrs.getResultSet();
        while ( rs.next() ) {
            result=new Hashtable();
		    String sizeUnit= ( rs.getString( "size_unit" ) == null ? "" : rs.getString( "size_unit" ) );
		    result.put( sizeUnit,sizeUnit );
		    ResultV.addElement( result );
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
      }
      finally
      {
        if ( dbrs != null )
        {
          dbrs.close();
        }
      }
      return ResultV;
    }

    public static Vector getSizeUnitNetWtRequired(TcmISDBModel dbObj3)
    {
      String query="select size_unit from global.size_unit_view where net_wt_required = 'Y' order by size_unit";
      DBResultSet dbrs=null;
      ResultSet rs=null;
      Vector ResultV=new Vector();
      try
      {
        dbrs=dbObj3.doQuery( query );
        rs=dbrs.getResultSet();
        while ( rs.next() ) {
          ResultV.addElement(rs.getString("size_unit"));
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
      }
      finally
      {
        if ( dbrs != null )
        {
          dbrs.close();
        }
      }
      return ResultV;
    }


   public static Hashtable getmsdsStatus(TcmISDBModel dbObj3)
	  {
		String query="select STATUS from vv_customer_msds_status where display='Y'";
		DBResultSet dbrs=null;
		ResultSet rs=null;
		Hashtable result= new Hashtable();
		try
		{
		  dbrs=dbObj3.doQuery( query );
		  rs=dbrs.getResultSet();
		  while ( rs.next() )
		  {
			String faci= ( rs.getString( "STATUS" ) == null ? "" : rs.getString( "STATUS" ) );
			//String facn= ( rs.getString( "STATUS" ) == null ? "" : rs.getString( "STATUS" ) );
			result.put( faci,faci );
		  }
		}
		catch ( Exception e )
		{
		  e.printStackTrace();
		}
		finally
		{
		  if ( dbrs != null )
		  {
			dbrs.close();
		  }
		}
		return result;
	  }

  public static Vector getcurrency( TcmISDBModel dbObj3 )
  {
	String query="select distinct CURRENCY_ID, CURRENCY_DESCRIPTION from vv_currency order by CURRENCY_ID";
	DBResultSet dbrs=null;
	ResultSet rs=null;
	Hashtable result=null;
	Vector ResultV=new Vector();
	try
	{
	  dbrs=dbObj3.doQuery( query );
	  rs=dbrs.getResultSet();
	  while ( rs.next() )
	  {
		result=new Hashtable();
		String faci= ( rs.getString( "CURRENCY_ID" ) == null ? "" : rs.getString( "CURRENCY_ID" ) );
		//String facn= ( rs.getString( "DESCRIPTION" ) == null ? "" : rs.getString( "DESCRIPTION" ) );
		result.put( faci,faci );
		ResultV.addElement( result );
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}
	finally
	{
	  if ( dbrs != null )
	  {
		dbrs.close();
	  }
	}
	return ResultV;
  }

  public static Hashtable getvvprjpriorityType(TcmISDBModel dbObj3)
  {
	String query="select PROJECT_PRIORITY,PROJECT_PRIORITY_DESC from vv_project_priority order by PROJECT_PRIORITY_DESC asc";
	DBResultSet dbrs=null;
	ResultSet rs=null;
	Hashtable result= new Hashtable();
	try
	{
	  dbrs=dbObj3.doQuery( query );
	  rs=dbrs.getResultSet();
	  while ( rs.next() )
	  {
	    String faci = rs.getString("PROJECT_PRIORITY") != null ? rs.getString("PROJECT_PRIORITY") : "";
		String facn = rs.getString("PROJECT_PRIORITY_DESC") != null ? rs.getString("PROJECT_PRIORITY_DESC") : "";
		result.put( faci,facn );
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

	return result;
  }

  public static Hashtable getvvprjstatusType(TcmISDBModel dbObj3)
  {
	String query="select PROJECT_STATUS,PROJECT_STATUS_DESC,DISPLAY_ORDER from VV_PEI_PROJECT_STATUS order by DISPLAY_ORDER asc";
	DBResultSet dbrs=null;
	ResultSet rs=null;
	Hashtable result= new Hashtable();
	Hashtable statusPriorityList = new Hashtable();

	try
	{
	  dbrs=dbObj3.doQuery( query );
	  rs=dbrs.getResultSet();
	  while ( rs.next() )
	  {
		String faci = rs.getString("PROJECT_STATUS") != null ? rs.getString("PROJECT_STATUS") : "";
		String facn = rs.getString("PROJECT_STATUS_DESC") != null ? rs.getString("PROJECT_STATUS_DESC") : "";
		String prority = rs.getString("DISPLAY_ORDER") != null ? rs.getString("DISPLAY_ORDER") : "1000";
		result.put( faci,facn );
		statusPriorityList.put(faci,new Integer (prority));
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

	Hashtable projectStatusSet = new Hashtable();
	projectStatusSet.put( "PROJECT_STATUS_LIST" , result );
	projectStatusSet.put( "PROJECT_STATUS_PRORITY_LIST" , statusPriorityList );

	return projectStatusSet;
  }

  public static Hashtable getprjfacilities (TcmISDBModel dbObj3)
  {
	String query="select FACILITY_ID, FACILITY_NAME from facility where BRANCH_PLANT is null and COMPANY_ID = '"+dbObj3.getClient().toUpperCase()+"' order by FACILITY_ID asc";
	DBResultSet dbrs=null;
	ResultSet rs=null;
	Hashtable result= new Hashtable();
	try
	{
	  dbrs=dbObj3.doQuery( query );
	  rs=dbrs.getResultSet();
	  while ( rs.next() )
	  {
		String faci = rs.getString("FACILITY_ID") != null ? rs.getString("FACILITY_ID") : "";
		String facn = rs.getString("FACILITY_NAME") != null ? rs.getString("FACILITY_NAME") : "";
		result.put( faci,facn );
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

	return result;
  }

  public static Hashtable getvvtaskType(TcmISDBModel dbObj3)
  {
	String query="select PROJECT_CATEGORY,PROJECT_CATEGORY_DESC from vv_project_category order by PROJECT_CATEGORY_DESC asc";
	DBResultSet dbrs=null;
	ResultSet rs=null;
	Hashtable result= new Hashtable();
	try
	{
	  dbrs=dbObj3.doQuery( query );
	  rs=dbrs.getResultSet();
	  while ( rs.next() )
	  {
		String faci = rs.getString("PROJECT_CATEGORY") != null ? rs.getString("PROJECT_CATEGORY") : "";
		String facn = rs.getString("PROJECT_CATEGORY_DESC") != null ? rs.getString("PROJECT_CATEGORY_DESC") : "";
		result.put( faci,facn );
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

	return result;
  }

  public static Hashtable getvvkeywords(TcmISDBModel dbObj3)
  {
	String query="select KEYWORD_ID,KEYWORD_DESC from Vv_pei_project_keyword order by KEYWORD_DESC asc";
	DBResultSet dbrs=null;
	ResultSet rs=null;
	Hashtable result= new Hashtable();
	try
	{
	  dbrs=dbObj3.doQuery( query );
	  rs=dbrs.getResultSet();
	  while ( rs.next() )
	  {
		String faci = rs.getString("KEYWORD_ID") != null ? rs.getString("KEYWORD_ID") : "";
		String facn = rs.getString("KEYWORD_DESC") != null ? rs.getString("KEYWORD_DESC") : "";
		result.put( faci,facn );
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

	return result;
  }

  public static Hashtable getvvprojectpersonnel(TcmISDBModel dbObj3)
  {
	String query="select distinct x.PERSONNEL_ID,x.FIRST_NAME || ' ' || x.LAST_NAME FULL_NAME from personnel x, user_group_member y where x.personnel_id = y.personnel_id and y.user_group_id = 'ProjectMgmt' order by FULL_NAME asc";
	DBResultSet dbrs=null;
	ResultSet rs=null;
	Hashtable result= new Hashtable();
	try
	{
	  dbrs=dbObj3.doQuery( query );
	  rs=dbrs.getResultSet();
	  while ( rs.next() )
	  {
		String faci = rs.getString("PERSONNEL_ID") != null ? rs.getString("PERSONNEL_ID") : "";
		String facn = rs.getString("FULL_NAME") != null ? rs.getString("FULL_NAME") : "";
		result.put( faci,facn );
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

	return result;
  }

public static Hashtable getprjvvsortby()
{
	Hashtable result = new Hashtable();
	try
	{
		result.put("PRIORITY desc", "Priority");
		result.put("PROJECT_STATUS asc", "Status");
		result.put("PROJECT_CATEGORY asc", "Category");
		result.put("PROJECTED_FINISTED_DATE desc", "Finish Date");
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	finally { }
	return result;
}

  public static Hashtable getvvqualificationlevel(TcmISDBModel dbObj3)
	{
	  String query="select QUALIFICATION_LEVEL, QUALIFICATION_LEVEL_DESC from vv_qualification_level where DISPLAY_AT_CREATION = 'Y' order by QUALIFICATION_LEVEL_DESC asc";
	  DBResultSet dbrs=null;
	  ResultSet rs=null;
	  Hashtable result= new Hashtable();
	  try
	  {
		dbrs=dbObj3.doQuery( query );
		rs=dbrs.getResultSet();
		while ( rs.next() )
		{
		  String faci = rs.getString("QUALIFICATION_LEVEL") != null ? rs.getString("QUALIFICATION_LEVEL") : "";
		  String facn = rs.getString("QUALIFICATION_LEVEL_DESC") != null ? rs.getString("QUALIFICATION_LEVEL_DESC") : "";
		  result.put( faci,facn );
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

	  return result;
	}


 }