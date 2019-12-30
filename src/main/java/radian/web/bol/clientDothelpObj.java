package radian.web.bol;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 1998
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 08-04-03 Not updating HAZARD_CLASS.GROUND_SHIPPING_NAME,PACKING_GROUP if their length is zero
 * 08-06-03 Ordering by Part ID instead of hazard class which is causing confusion. Aslo showing the last updated and last updated by
 * 08-11-03 Updating anything they put in
 * 02-23-04 Cant see a reason why Hazard_class can not be 0
 */

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;

public class clientDothelpObj {

	protected TcmISDBModel db;
	private   Vector vMsdsNoList = null;

	public clientDothelpObj() {
		vMsdsNoList = new Vector();
	}

	public clientDothelpObj(TcmISDBModel db ){
		this.db = db;
		vMsdsNoList = new Vector();
	}

	public void setDB(TcmISDBModel db){
		this.db = db;
	}

	//Get Header Information for a particular request Id
	public Hashtable getHeaderData(String item_Id)
	{
		Hashtable ResultData = new Hashtable();

		DBResultSet dbrs = null;
		ResultSet rs = null;

		String detailquery = "select fx_item_desc('"+item_Id+"','MA') item_desc from dual ";

		try
		{
			dbrs = db.doQuery(detailquery);
			rs=dbrs.getResultSet();
			//System.out.print("Finished The Header Querry\n ");

			while(rs.next())
			{
				//ITEM_DESC
				ResultData.put("ITEM_DESC",BothHelpObjs.makeSpaceFromNull(rs.getString("ITEM_DESC")));
			}
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
			return ResultData;
		}
		finally
		{
			if (dbrs != null)  { dbrs.close(); }
		}

		return ResultData;
	}

	public Hashtable getMSDSData( String item_id )
	{
	  Hashtable ResultData=new Hashtable();
	  Vector result=new Vector();
	  DBResultSet dbrs=null;
	  DBResultSet compdbrs=null;
	  ResultSet rs=null;
	  ResultSet comprs=null;

	  Hashtable MaterialData=new Hashtable();
	  Hashtable MSDSData=new Hashtable();
	  Hashtable CompositionData=new Hashtable();
	  Vector compresult=new Vector();

	  String msdsdetailquery=" select * from dot_part_material_view where item_id= " + item_id + " order by PART_ID asc";

	  int totalrecords=0;
	  int comprecords=0;

	  try
	  {
		dbrs=db.doQuery( msdsdetailquery );
		rs=dbrs.getResultSet();
		//System.out.print( "Finished The Querry\n " );
		String materialid="";
		String revisionDate="";
		String revisionDate1="";

	   while ( rs.next() )
	   {
		 totalrecords+=1;
		 ResultData=new Hashtable();
		 MaterialData=new Hashtable();
		 materialid="";

		 //PART_ID
		 ResultData.put("PART_ID",rs.getString("PART_ID")==null?"":rs.getString("PART_ID"));
		 //UPDATE_FLAG
		 ResultData.put( "UPDATE_FLAG",new Boolean( true ) );

		 materialid=rs.getString( "MATERIAL_ID" ) == null ? "" : rs.getString( "MATERIAL_ID" );
		 ResultData.put( "MATERIAL_ID",materialid );

		 //Material Data
		 MaterialData.put( "MATERIAL_ID",materialid );
		 MaterialData.put( "SHIPPING_INFO_REMARKS",rs.getString( "SHIPPING_INFO_REMARKS" ) == null ? "" : rs.getString( "SHIPPING_INFO_REMARKS" ) );
		 MaterialData.put("PART_ID",rs.getString("PART_ID")==null?"":rs.getString("PART_ID"));
		 MaterialData.put("ITEM_ID",rs.getString("ITEM_ID")==null?"":rs.getString("ITEM_ID"));
		 MaterialData.put("PKG_GT_RQ",rs.getString("PKG_GT_RQ")==null?"":rs.getString("PKG_GT_RQ"));
		 MaterialData.put("BULK_PKG_MARINE_POLLUTANT",rs.getString("BULK_PKG_MARINE_POLLUTANT")==null?"":rs.getString("BULK_PKG_MARINE_POLLUTANT"));
		 MaterialData.put( "ORM_D",rs.getString( "ORM_D" ) == null ? "" : rs.getString( "ORM_D" ) );
		 MaterialData.put( "WEIGHT_LB",rs.getString( "WEIGHT_LB" ) == null ? "" : rs.getString( "WEIGHT_LB" ) );
		 MaterialData.put( "MATERIAL_DESC",rs.getString( "MATERIAL_DESC" ) == null ? "" : rs.getString( "MATERIAL_DESC" ) );
		 MaterialData.put( "HAZARD_CLASS",rs.getString( "HAZARD_CLASS" ) == null ? "" : rs.getString( "HAZARD_CLASS" ) );
		 MaterialData.put( "SUBSIDIARY_HAZARD_CLASS",rs.getString( "SUBSIDIARY_HAZARD_CLASS" ) == null ? "" : rs.getString( "SUBSIDIARY_HAZARD_CLASS" ) );
		 MaterialData.put( "PACKING_GROUP",rs.getString( "PACKING_GROUP" ) == null ? "" : rs.getString( "PACKING_GROUP" ) );
		 MaterialData.put( "UN_NUMBER",rs.getString( "UN_NUMBER" ) == null ? "" : rs.getString( "UN_NUMBER" ) );
		 MaterialData.put( "GROUND_SHIPPING_NAME",rs.getString( "GROUND_SHIPPING_NAME" ) == null ? "" : rs.getString( "GROUND_SHIPPING_NAME" ) );
		 MaterialData.put( "DRY_ICE",rs.getString( "DRY_ICE" ) == null ? "" : rs.getString( "DRY_ICE" ) );
		 MaterialData.put( "ERG",rs.getString( "ERG" ) == null ? "" : rs.getString( "ERG" ) );
		 MaterialData.put( "PASSENGER_AIR_LIMIT",rs.getString( "PASSENGER_AIR_LIMIT" ) == null ? "" : rs.getString( "PASSENGER_AIR_LIMIT" ) );
		 MaterialData.put( "PASSENGER_AIR_UNIT",rs.getString( "PASSENGER_AIR_UNIT" ) == null ? "" : rs.getString( "PASSENGER_AIR_UNIT" ) );
		 MaterialData.put( "CARGO_AIR_LIMIT",rs.getString( "CARGO_AIR_LIMIT" ) == null ? "" : rs.getString( "CARGO_AIR_LIMIT" ) );
		 MaterialData.put( "CARGO_AIR_UNIT",rs.getString( "CARGO_AIR_UNIT" ) == null ? "" : rs.getString( "CARGO_AIR_UNIT" ) );
		 MaterialData.put( "MARINE_POLLUTANT",rs.getString( "MARINE_POLLUTANT" ) == null ? "" : rs.getString( "MARINE_POLLUTANT" ) );
		 MaterialData.put( "REPORTABLE_QUANTITY_LB",rs.getString( "REPORTABLE_QUANTITY_LB" ) == null ? "" : rs.getString( "REPORTABLE_QUANTITY_LB" ) );
		 MaterialData.put( "GROUND_HAZARD",rs.getString( "GROUND_HAZARD" ) == null ? "" : rs.getString( "GROUND_HAZARD" ) );
		 MaterialData.put( "AIR_HAZARD",rs.getString( "AIR_HAZARD" ) == null ? "" : rs.getString( "AIR_HAZARD" ) );
		 MaterialData.put( "WATER_HAZARD",rs.getString( "WATER_HAZARD" ) == null ? "" : rs.getString( "WATER_HAZARD" ) );
		 MaterialData.put( "PACKAGING",rs.getString( "PACKAGING" ) == null ? "" : rs.getString( "PACKAGING" ) );
		 MaterialData.put( "TEST_PKG_GT_RQ",rs.getString( "TEST_PKG_GT_RQ" ) == null ? "" : rs.getString( "TEST_PKG_GT_RQ" ) );
		 MaterialData.put( "LAST_CHANGED_BY",rs.getString( "LAST_CHANGED_BY" ) == null ? "" : rs.getString( "LAST_CHANGED_BY" ) );
		 MaterialData.put( "LAST_UPDATED",rs.getString( "LAST_UPDATED" ) == null ? "" : rs.getString( "LAST_UPDATED" ) );
		 MaterialData.put( "SCHEDULE_B","" );

		 //MATERIAL_DATA
		 ResultData.put( "MATERIAL_DATA",MaterialData );

		 result.addElement( ResultData );
	   }
	 }
	 catch ( Exception e1 )
	 {
	   e1.printStackTrace();
	 }
	 finally
	 {
	   if ( dbrs != null )
	   {
		 dbrs.close();
	   }
	 }

	Hashtable recsum=new Hashtable();
	recsum.put( "TOTAL_NUMBER",new Integer( totalrecords ) );
	//ITEM_ID
	recsum.put( "ITEM_ID",item_id );
	//PART_DESC
	recsum.put( "PART_DESC","" );
	recsum.put( "DATA",result );

	return recsum;

  }

  public boolean updateMsdsData( Hashtable DataH,String loginID,String action )
  {
	// get main information
	boolean result=true;

	Hashtable materialData=null;

	materialData= ( Hashtable ) DataH.get( "MATERIAL_DATA" );

	String part_id=materialData.get( "PART_ID" ).toString();
	String material_id=materialData.get( "MATERIAL_ID" ).toString();
	String item_id=materialData.get( "ITEM_ID" ).toString();
	String pkg_gt_rq=materialData.get( "PKG_GT_RQ" ).toString();
	String bulk_pkg_marine_pollutant=materialData.get( "BULK_PKG_MARINE_POLLUTANT" ).toString();
	String orm_d=materialData.get( "ORM_D" ).toString();
	String hazard_class=materialData.get( "HAZARD_CLASS" ).toString();
	String subsidiary_hazard_class=materialData.get( "SUBSIDIARY_HAZARD_CLASS" ).toString();
	String un_number=materialData.get( "UN_NUMBER" ).toString();
	String ground_shipping_name=materialData.get( "GROUND_SHIPPING_NAME" ).toString();
	ground_shipping_name = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(ground_shipping_name);
	String dry_ice=materialData.get( "DRY_ICE" ).toString();
	String erg=materialData.get( "ERG" ).toString();
	String passenger_air_limit=materialData.get( "PASSENGER_AIR_LIMIT" ).toString();
	String passenger_air_unit=materialData.get( "PASSENGER_AIR_UNIT" ).toString();
	String cargo_air_limit=materialData.get( "CARGO_AIR_LIMIT" ).toString();
	String cargo_air_unit=materialData.get( "CARGO_AIR_UNIT" ).toString();
	String marine_pollutant=materialData.get( "MARINE_POLLUTANT" ).toString();
	String reportable_quantity_lb=materialData.get( "REPORTABLE_QUANTITY_LB" ).toString();
	String ground_hazard=materialData.get( "GROUND_HAZARD" ).toString();
	String air_hazard=materialData.get( "AIR_HAZARD" ).toString();
	String water_hazard=materialData.get( "WATER_HAZARD" ).toString();
	String shipping_info_remarks=materialData.get( "SHIPPING_INFO_REMARKS" ).toString();
	//if ( shipping_info_remarks.length() > 200 ) {shipping_info_remarks=shipping_info_remarks.substring( 0,199 );}
	shipping_info_remarks = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(shipping_info_remarks);
	//String scheduleB=materialData.get( "SCHEDULE_B" ).toString();

	String packing_group=materialData.get( "PACKING_GROUP" ).toString();

	boolean updateorinsert = false;

	try
	{
	  String materialquery="";
	  String partquery="";

	  if ( material_id.length() > 0 )
	  {
		materialquery="update dot_material_view set ";
		if (reportable_quantity_lb.length() > 0 && !"0".equalsIgnoreCase(reportable_quantity_lb))
		{
		  materialquery+=" REPORTABLE_QUANTITY_LB="+reportable_quantity_lb+",";
		}

		if (erg.length() > 0)
		{
		  materialquery+=" ERG="+erg+",";
		}
		else
		{
		  materialquery+=" ERG=null,";
		}

		if ( hazard_class.length() > 0 )
		{
		  materialquery+=" HAZARD_CLASS='" + hazard_class + "',";
		}
		else
		{
		  materialquery+=" HAZARD_CLASS=null,";
		}

		if ( ground_shipping_name.length() > 0 )
		{
		  materialquery+=" GROUND_SHIPPING_NAME='" + ground_shipping_name + "',";
		}
		else
		{
		  materialquery+=" GROUND_SHIPPING_NAME=null,";
		}

		if ( packing_group.length() > 0 )
		{
		  materialquery+=" PACKING_GROUP='" + packing_group + "',";
		}
		else
		{
		 materialquery+=" PACKING_GROUP=null,";
		}


		if ( subsidiary_hazard_class.length() > 0 )
		{
		  materialquery+=" SUBSIDIARY_HAZARD_CLASS='" + subsidiary_hazard_class + "',";
		}
		else
		{
		 materialquery+=" SUBSIDIARY_HAZARD_CLASS=null,";
		}

		if ( un_number.length() > 0 )
		{
		  materialquery+=" UN_NUMBER='" + un_number + "',";
		}
		else
		{
		materialquery+=" UN_NUMBER=null,";
		}

		/*if (scheduleB.length() > 0)
		{
		  materialquery+=" SCHEDULE_B="+scheduleB+",";
		}
		else
		{
		  materialquery+=" SCHEDULE_B=null,";
		}*/

		materialquery+=" SHIP_CHANGE_ID="+loginID+",MARINE_POLLUTANT='"+marine_pollutant+"',GROUND_HAZARD='"+ground_hazard+"',AIR_HAZARD='"+air_hazard+"',WATER_HAZARD='"+water_hazard+"',SHIPPING_INFO_REMARK='"+shipping_info_remarks+"'";
		/*PASSENGER_AIR_LIMIT='"+passenger_air_limit+"',PASSENGER_AIR_UNIT='"+passenger_air_unit+"',CARGO_AIR_LIMIT='"+cargo_air_limit+"',
		CARGO_AIR_UNIT='"+cargo_air_unit+"',DRY_ICE='"+dry_ice+"',*/

		materialquery+=" where MATERIAL_ID = " + material_id + " ";

		partquery = "update dot_part_view set ";
		//partquery = "update global.part set SHIP_CHANGE_ID="+loginID+",ORM_D='"+orm_d+"',PKG_GT_RQ='"+pkg_gt_rq+"',BULK_PKG_MARINE_POLLUTANT='"+bulk_pkg_marine_pollutant+"' where MATERIAL_ID = " + material_id + " and PART_ID='"+part_id+"' and ITEM_ID = "+item_id+"";

		 if ( orm_d.length() > 0 )
		 {
		  partquery+=" ORM_D='" + orm_d + "',";
		 }

		 if ( pkg_gt_rq.length() > 0 )
		 {
		  partquery+=" PKG_GT_RQ='" + pkg_gt_rq + "',";
		 }

		 if ( bulk_pkg_marine_pollutant.length() > 0 )
		 {
		  partquery+=" BULK_PKG_MARINE_POLLUTANT='" + bulk_pkg_marine_pollutant + "',";
		 }

		 partquery += " SHIP_CHANGE_ID="+loginID+" where MATERIAL_ID = " + material_id + " and PART_ID="+part_id+" and ITEM_ID = "+item_id+"";

		//System.out.println(materialquery);
		//System.out.println(partquery);
		db.doUpdate( materialquery );
		db.doUpdate( partquery );
	  }

	  result=true;
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	  result=false;
	}

	return result;
  }
 }
