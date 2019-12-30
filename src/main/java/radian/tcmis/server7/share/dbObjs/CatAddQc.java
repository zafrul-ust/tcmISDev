package radian.tcmis.server7.share.dbObjs;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 1998
 * Company:      URS Corporation
 * @author       Rajendra Rajput
 * @version
 *
 * 11-01-02
 * Made changes so that msds_chemical_name has to be something to be able to enter into the chemical composition table it is not null in database
 * 11-04-02
 * Checking the length of the comments and remarks field before updating
 * also commented out some unwanted lines from the processrequest procedure
 *
 * Showing the suggested supplier information the user enters
 * 11-15-02
 * adding PERSONAL_PROTECTION to hmis data
 * 01-03-03
 * Adding a text area to enter ALT_DATA_SOURCE information
 * 01-07-03
 * if (msds_chemical_name.length() > 160){msds_chemical_name = msds_chemical_name.substring(0,159);}
 * 01-27-03
 * the Cas Number can be null
 * 02-13-03
 * Changing single Quote to Double quote for trade name
 * 03-12-03
 * Updating catalog_add_item and qc tables when a verified item is choosen
 * 03-20-03 - Adding part Description to the view original request
 * 05-13-03 - Added time to the Submit Date
 * 07-23-03 Code cleanup and removed DOT and Solids info from here
 * 08-11-03 - Getting the manufacturer information from the material Id istead of the mfg_id in catalog_add_item_qc which does not have mfg_id sometimes
 * 08-20-03 - Giving a message when a cas_number is not found and an error occures
 * 09-08-03 - Getting all manufacturer info from the global.manufacturer table
 * 09-09-03 - Converting from Ascii to normal text when updating Chemical ID and Chemila Name for Composition data
 * 09-19-03 - Bug says error occured if the request has more than 5 parts
 * 10-23-03 - Not truncating the remarks or length of the databse field
 * 02-18-04 - Adding VAPOR_PRESSURE_DETECT, VAPOR_PRESSURE, VAPOR_PRESSURE_UNIT, VAPOR_PRESSURE_TEMP, VAPOR_PRESSURE_TEMP_UNIT to page
 * 04-27-04 - Changes to store storage temperature in min max fields instead of one storage temp field
 * 11-10-04 - Added new VOC fields
 * 01-21-05 - Corrected the date format on QC and Submit dates
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.helpers.HelpObjs;

public class CatAddQc {

	private TcmISDBModel db;
	private Vector vMsdsNoList = null;
	private static org.apache.log4j.Logger cataddlog = org.apache.log4j.Logger.getLogger("tcmis.web");

	public CatAddQc() {
		vMsdsNoList = new Vector();
	}

	public CatAddQc(TcmISDBModel db ){
		this.db = db;
		vMsdsNoList = new Vector();
	}

	public void setDB(TcmISDBModel db){
		this.db = db;
	}

	public Vector getAllopenOrder(String companyID,String CatalogDesc,String SortBy, String Status,String searchlike,String searchfor,String searchField) throws Exception
	{
		DBResultSet dbrs=null;
		ResultSet rs=null;
		Vector result=new Vector();

		String user_query="select distinct car.SUBMIT_DATE,car.QC_DATE,car.request_id,car.qc_status,car.company_id,p.last_name || ', ' || p.first_name full_name, p.phone, p.email,car.catalog_id,to_char(car.qc_date,'dd Month yyyy HH:MI AM') qcdate, to_char(car.SUBMIT_DATE,'dd Month yyyy HH:MI AM') submitdate ";
		user_query+=",cai.MATERIAL_DESC,cai.MANUFACTURER,caiqc.COMMENTS, ";
		user_query+=" (select vv.REQUEST_STATUS_DESC from customer.vv_catalog_add_request_status vv where car.STARTING_VIEW=vv.request_status and car.company_id=vv.company_id) STARTING_VIEW ";
		user_query+=" from customer.catalog_add_request_new car,global.personnel p ";
		user_query+=",customer.catalog_add_item_qc caiqc,customer.catalog_add_item cai ";
		user_query+="where car.requestor = p.personnel_id ";

		if ( "ALL".equalsIgnoreCase( companyID ) )
		{
			user_query+=" and ( (car.company_id = 'SEAGATE' and car.request_status = 5) or (car.company_id <> 'SEAGATE' and car.request_status = 6)) ";
		}
		else if ( "Seagate".equalsIgnoreCase( companyID ) )
		{
			user_query+="and car.company_id = '" + companyID + "' and car.request_status = 5 ";
		}
		else
		{
			user_query+="and car.company_id = '" + companyID + "' and car.request_status = 6 ";
		}

		if ( !"ALL".equalsIgnoreCase( CatalogDesc ) )
		{
			user_query+="and car.catalog_id = '" + CatalogDesc + "' ";
		}

		if ( "Pending QC".equalsIgnoreCase( Status ) )
		{
			user_query+="and car.qc_status = 'Pending QC' ";
		}
		else if ( "Pending MSDS".equalsIgnoreCase( Status ) )
		{
			user_query+="and car.qc_status = 'Pending MSDS' ";
		}
		else
		{
			user_query+="and car.qc_status = 'Pending MSDS' ";
		}

		user_query+="and car.request_id = cai.request_id and car.request_id = caiqc.request_id AND car.company_id = cai.company_id AND car.company_id = caiqc.company_id ";
		user_query+="and car.company_id = cai.company_id and cai.company_id = caiqc.company_id and cai.request_id = caiqc.request_id and cai.line_item = caiqc.line_item and cai.part_id = caiqc.part_id ";

		if ( searchField.trim().length() > 0)
		{
			if ( "Like".equalsIgnoreCase( searchfor ) )
			{
				user_query+=" and lower(" + searchlike + ") like lower('%" + searchField.trim() + "%') ";
			}
			else if ( "Equals".equalsIgnoreCase( searchfor ) )
			{
				user_query+=" and " + searchlike + " = '" + searchField.trim() + "' ";
			}

		}
		if ( "1".equalsIgnoreCase( SortBy ) )
		{
			user_query+=" order by car.company_id,car.catalog_id,car.SUBMIT_DATE,car.QC_DATE ";
		}
		else if ( "2".equalsIgnoreCase( SortBy ) )
		{
			user_query+=" order by car.SUBMIT_DATE,car.QC_DATE,car.company_id,car.catalog_id ";
		}
		else
		{
			user_query+=" order by cai.MANUFACTURER,car.request_id,cai.MATERIAL_DESC ";
		}

		int num_rec=0;
		try
		{
			dbrs=db.doQuery( user_query );
			rs=dbrs.getResultSet();

			while ( rs.next() )
			{
				num_rec++;

				Hashtable hD=new Hashtable();
				hD.put( "REQUEST_ID",rs.getString( "REQUEST_ID" ) == null ? "" : rs.getString( "REQUEST_ID" ) );
				hD.put( "REQUEST_DATE",rs.getString( "submitdate" ) == null ? "" : rs.getString( "submitdate" ) );
				hD.put( "FULL_NAME",rs.getString( "FULL_NAME" ) == null ? "" : rs.getString( "FULL_NAME" ) );
				hD.put( "FACILITY_NAME",rs.getString( "CATALOG_ID" ) == null ? "" : rs.getString( "CATALOG_ID" ) );
				hD.put( "STATUS",rs.getString( "QC_STATUS" ) == null ? "" : rs.getString( "QC_STATUS" ) );
				hD.put( "PHONE",rs.getString( "PHONE" ) == null ? "" : rs.getString( "PHONE" ) );
				hD.put( "EMAIL",rs.getString( "EMAIL" ) == null ? "" : rs.getString( "EMAIL" ) );
				hD.put( "COMPANY",rs.getString( "COMPANY_ID" ) == null ? "" : rs.getString( "COMPANY_ID" ) );
				hD.put( "QCDATE",rs.getString( "qcdate" ) == null ? "" : rs.getString( "qcdate" ) );
				hD.put( "MATERIAL_ID"," " );
				hD.put( "COMMENTS",rs.getString( "COMMENTS" ) == null ? "" : rs.getString( "COMMENTS" ) );
				hD.put( "MANUFACTURER",rs.getString( "MANUFACTURER" ) == null ? "" : rs.getString( "MANUFACTURER" ) );
				hD.put( "MATERIAL_DESC",rs.getString( "MATERIAL_DESC" ) == null ? "" : rs.getString( "MATERIAL_DESC" ) );
				hD.put( "STARTING_VIEW",BothHelpObjs.makeBlankFromNull( rs.getString( "STARTING_VIEW" ) ) );

				result.addElement( hD );
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			result=null;
			throw e;
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

	//Get Header Information for a particular request Id
	public Hashtable getHeaderData( String requestID, String lineItem)
	{
		Hashtable ResultData=new Hashtable();
		DBResultSet dbrs=null;
		ResultSet rs=null;

		String detailquery="select car.MESSAGE_TO_APPROVERS,car.PART_DESCRIPTION,car.request_id,car.ENGINEERING_EVALUATION,car.CAT_PART_NO,p.last_name || ', ' || p.first_name full_name, ";
		detailquery+="cai.VENDOR_CONTACT_NAME,cai.VENDOR_CONTACT_EMAIL,cai.VENDOR_CONTACT_PHONE,cai.VENDOR_CONTACT_FAX,cai.SUGGESTED_VENDOR, ";
		detailquery+="p.phone, p.email,car.company_id, car.catalog_id, to_char(car.SUBMIT_DATE,'dd Month yyyy HH:Mi AM') submitdate, ";
		detailquery+=" (select vv.REQUEST_STATUS_DESC from customer.vv_catalog_add_request_status vv where car.STARTING_VIEW=vv.request_status and car.company_id=vv.company_id) STARTING_VIEW ";
		detailquery+="from customer.catalog_add_request_new car,customer.catalog_add_item cai,global.personnel p where ";
		detailquery+=" car.requestor = p.personnel_id and car.request_id = cai.request_id and cai.line_item = " + lineItem + " and cai.part_id = 1 and ";
		detailquery+="car.request_id = " + requestID + " ";

		try
		{
			dbrs=db.doQuery( detailquery );
			rs=dbrs.getResultSet();

			while ( rs.next() )
			{
				//Request ID
				ResultData.put( "REQUEST_ID",BothHelpObjs.makeBlankFromNull( rs.getString( "REQUEST_ID" ) ) );
				//Requestor
				ResultData.put( "REQUESTOR",BothHelpObjs.makeBlankFromNull( rs.getString( "FULL_NAME" ) ) );
				//Phone
				ResultData.put( "REQUESTOR_PHONE",BothHelpObjs.makeBlankFromNull( rs.getString( "PHONE" ) ) );
				//email
				ResultData.put( "REQUESTOR_EMAIL",BothHelpObjs.makeBlankFromNull( rs.getString( "EMAIL" ) ) );
				// COMPANY
				ResultData.put( "COMPANY",BothHelpObjs.makeBlankFromNull( rs.getString( "COMPANY_ID" ) ) );
				//CATALOGID
				ResultData.put( "CATALOGID",BothHelpObjs.makeBlankFromNull( rs.getString( "CATALOG_ID" ) ) );
				// DATE SUBMITTED
				ResultData.put( "DATE_SUBMITTED",BothHelpObjs.makeBlankFromNull( rs.getString( "SUBMITDATE" ) ) );
				// EVALUATION
				ResultData.put( "EVALUATION",BothHelpObjs.makeBlankFromNull( rs.getString( "ENGINEERING_EVALUATION" ) ) );
				//CAT_PART_NO
				ResultData.put( "CAT_PART_NO",BothHelpObjs.makeBlankFromNull( rs.getString( "CAT_PART_NO" ) ) );
				//Requestor
				ResultData.put( "VENDOR_CONTACT_NAME",BothHelpObjs.makeBlankFromNull( rs.getString( "VENDOR_CONTACT_NAME" ) ) );
				//Requestor
				ResultData.put( "VENDOR_CONTACT_EMAIL",BothHelpObjs.makeBlankFromNull( rs.getString( "VENDOR_CONTACT_EMAIL" ) ) );
				//Requestor
				ResultData.put( "VENDOR_CONTACT_PHONE",BothHelpObjs.makeBlankFromNull( rs.getString( "VENDOR_CONTACT_PHONE" ) ) );
				//Requestor
				ResultData.put( "VENDOR_CONTACT_FAX",BothHelpObjs.makeBlankFromNull( rs.getString( "VENDOR_CONTACT_FAX" ) ) );
				//Requestor
				ResultData.put( "SUGGESTED_VENDOR",BothHelpObjs.makeBlankFromNull( rs.getString( "SUGGESTED_VENDOR" ) ) );
				//PART_DESCRIPTION
				ResultData.put( "PART_DESCRIPTION",BothHelpObjs.makeBlankFromNull( rs.getString( "PART_DESCRIPTION" ) ) );
				//MESSAGE_TO_APPROVERS
				ResultData.put( "MESSAGE_TO_APPROVERS",BothHelpObjs.makeBlankFromNull( rs.getString( "MESSAGE_TO_APPROVERS" ) ) );
				//STARTING_VIEW
				ResultData.put( "STARTING_VIEW",BothHelpObjs.makeBlankFromNull( rs.getString( "STARTING_VIEW" ) ) );
			}
		}
		catch ( Exception e1 )
		{
			//e1.printStackTrace();
			cataddlog.error(e1.getMessage());
			return ResultData;
		}
		finally
		{
			if ( dbrs != null )
			{
				dbrs.close();
			}
		}

		return ResultData;
	}

	//Get details that are to be shown for a particular request_id
	public Hashtable getMSDSData( String requestID, String lineItem )
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

		String msdsdetailquery="select to_char(m.REVISION_DATE,'dd-MON-yy') REVISION_DATE,to_char(m.REVISION_DATE,'mm/dd/yyyy') REVISION_DATE1, x.*,mat.GROUND_DOT LANDDOT,mat.AIR_DOT AIRDOT,mat.WATER_DOT WATERDOT,mat.ERG,m.*,mfg.* ";
		msdsdetailquery+=" from  (select * from global.msds where revision_date = fx_msds_most_recent_date(material_id)) m, ";
		msdsdetailquery+=" (select MATERIAL_DESC,MANUFACTURER,MATERIAL_ID,PART_ID,MFG_ID,MFG_TRADE_NAME,COMMENTS from customer.catalog_add_item_qc where request_id = " + requestID + " and line_item = " + lineItem + ") x,global.material mat,global.manufacturer mfg where x.material_id  = m.material_id (+) ";
		msdsdetailquery+=" and x.material_id  = mat.material_id (+) and m.ON_LINE(+) = 'Y' and x.MFG_ID = mfg.MFG_ID(+)";

		int totalrecords=0;
		int comprecords=0;

		try
		{
			dbrs=db.doQuery( msdsdetailquery );
			rs=dbrs.getResultSet();

			String materialid="";
			String revisionDate="";
			String revisionDate1="";

			while ( rs.next() )
			{
				totalrecords+=1;
				ResultData=new Hashtable();
				MaterialData=new Hashtable();
				MSDSData=new Hashtable();
				compresult=new Vector();
				materialid="";
				revisionDate="";
				comprecords=0;

				//PART_ID
				ResultData.put( "PART_ID",rs.getString( "PART_ID" ) == null ? "" : rs.getString( "PART_ID" ) );
				//UPDATE_FLAG
				ResultData.put( "UPDATE_FLAG",new Boolean( true ) );
				materialid=rs.getString( "MATERIAL_ID" ) == null ? "" : rs.getString( "MATERIAL_ID" );
				revisionDate=rs.getString( "REVISION_DATE" ) == null ? "" : rs.getString( "REVISION_DATE" );
				revisionDate1=rs.getString( "REVISION_DATE1" ) == null ? "" : rs.getString( "REVISION_DATE1" );

				ResultData.put( "MATERIAL_ID",materialid );
				//Material Data
				//MATERIAL_DESC
				MaterialData.put( "MATERIAL_DESC",rs.getString( "MATERIAL_DESC" ) == null ? "" : rs.getString( "MATERIAL_DESC" ) );
				//MANUFACTURER
				MaterialData.put( "MANUFACTURER",rs.getString( "MANUFACTURER" ) == null ? "" : rs.getString( "MANUFACTURER" ) );
				//MATERIAL_ID
				MaterialData.put( "MATERIAL_ID",materialid );
				//MFG_ID
				MaterialData.put( "MFG_ID",rs.getString( "MFG_ID" ) == null ? "" : rs.getString( "MFG_ID" ) );
				//MFG_TRADE_NAME
				MaterialData.put( "MFG_TRADE_NAME",rs.getString( "MFG_TRADE_NAME" ) == null ? "" : rs.getString( "MFG_TRADE_NAME" ) );
				/*//LANDDOT
         MaterialData.put("LANDDOT",rs.getString("LANDDOT")==null?"":rs.getString("LANDDOT"));
         //AIRDOT
         MaterialData.put("AIRDOT",rs.getString("AIRDOT")==null?"":rs.getString("AIRDOT"));
         //WATERDOT
         MaterialData.put("WATERDOT",rs.getString("WATERDOT")==null?"":rs.getString("WATERDOT"));
         //MATERIAL_ID
         MaterialData.put("MATERIAL_ID",rs.getString("MATERIAL_ID")==null?"":rs.getString("MATERIAL_ID"));
         //ERG
         MaterialData.put("ERG",rs.getString("ERG")==null?"":rs.getString("ERG"));*/
				//COMMENTS
				MaterialData.put( "COMMENTS",BothHelpObjs.makeBlankFromNull( rs.getString( "COMMENTS" ) ) );
				//MFG_URL
				MaterialData.put( "MFG_URL",rs.getString( "MFG_URL" ) == null ? "" : rs.getString( "MFG_URL" ) );
				//CONTACT
				MaterialData.put( "CONTACT",rs.getString( "CONTACT" ) == null ? "" : rs.getString( "CONTACT" ) );
				//PHONE
				MaterialData.put( "PHONE",rs.getString( "PHONE" ) == null ? "" : rs.getString( "PHONE" ) );
				//EMAIL
				MaterialData.put( "EMAIL",rs.getString( "EMAIL" ) == null ? "" : rs.getString( "EMAIL" ) );
				//NOTES
				MaterialData.put( "NOTES",rs.getString( "NOTES" ) == null ? "" : rs.getString( "NOTES" ) );

				//MATERIAL_DATA
				ResultData.put( "MATERIAL_DATA",MaterialData );

				//MSDS DATA
				//REVISION_DATE
				MSDSData.put( "REVISION_DATE",rs.getString( "REVISION_DATE1" ) == null ? "" : rs.getString( "REVISION_DATE1" ) );
				//CONTENT
				MSDSData.put( "CONTENT",rs.getString( "CONTENT" ) == null ? "" : rs.getString( "CONTENT" ) );
				//EMERGENCY_PHONE
				//MSDSData.put("EMERGENCY_PHONE",rs.getString("EMERGENCY_PHONE")==null?"":rs.getString("EMERGENCY_PHONE"));
				//SPECIFIC_GRAVITY
				MSDSData.put( "SPECIFIC_GRAVITY",rs.getString( "SPECIFIC_GRAVITY" ) == null ? "" : rs.getString( "SPECIFIC_GRAVITY" ) );
				//HEALTH
				MSDSData.put( "HEALTH",rs.getString( "HEALTH" ) == null ? "" : rs.getString( "HEALTH" ) );
				//FLAMMABILITY
				MSDSData.put( "FLAMMABILITY",rs.getString( "FLAMMABILITY" ) == null ? "" : rs.getString( "FLAMMABILITY" ) );
				//REACTIVITY
				MSDSData.put( "REACTIVITY",rs.getString( "REACTIVITY" ) == null ? "" : rs.getString( "REACTIVITY" ) );

				//HMIS_HEALTH
				MSDSData.put( "HMIS_HEALTH",rs.getString( "HMIS_HEALTH" ) == null ? "" : rs.getString( "HMIS_HEALTH" ) );
				//HMIS_FLAMMABILITY
				MSDSData.put( "HMIS_FLAMMABILITY",rs.getString( "HMIS_FLAMMABILITY" ) == null ? "" : rs.getString( "HMIS_FLAMMABILITY" ) );
				//HMIS_REACTIVITY
				MSDSData.put( "HMIS_REACTIVITY",rs.getString( "HMIS_REACTIVITY" ) == null ? "" : rs.getString( "HMIS_REACTIVITY" ) );

				//SPECIFIC_HAZARD
				MSDSData.put( "SPECIFIC_HAZARD",rs.getString( "SPECIFIC_HAZARD" ) == null ? "" : rs.getString( "SPECIFIC_HAZARD" ) );
				//PERSONAL_PROTECTION
				MSDSData.put( "PERSONAL_PROTECTION",rs.getString( "PERSONAL_PROTECTION" ) == null ? "" : rs.getString( "PERSONAL_PROTECTION" ) );

				//STORAGE_TEMP
				//MSDSData.put("STORAGE_TEMP",rs.getString("STORAGE_TEMP")==null?"":rs.getString("STORAGE_TEMP"));
				//PPE
				//MSDSData.put("PPE",rs.getString("PPE")==null?"":rs.getString("PPE"));
				//SIGNAL_WORD
				//MSDSData.put("SIGNAL_WORD",rs.getString("SIGNAL_WORD")==null?"":rs.getString("SIGNAL_WORD"));
				//TARGET_ORGAN
				//MSDSData.put("TARGET_ORGAN",rs.getString("TARGET_ORGAN")==null?"":rs.getString("TARGET_ORGAN"));
				//ROUTE_OF_ENTRY
				//MSDSData.put("ROUTE_OF_ENTRY",rs.getString("ROUTE_OF_ENTRY")==null?"":rs.getString("ROUTE_OF_ENTRY"));
				//SKIN
				//MSDSData.put("SKIN",rs.getString("SKIN")==null?"":rs.getString("SKIN"));
				//EYES
				//MSDSData.put("EYES",rs.getString("EYES")==null?"":rs.getString("EYES"));
				//INHALATION
				//MSDSData.put("INHALATION",rs.getString("INHALATION")==null?"":rs.getString("INHALATION"));
				//INJECTION
				//MSDSData.put("INJECTION",rs.getString("INJECTION")==null?"":rs.getString("INJECTION"));
				//BOILING_POINT
				//MSDSData.put("BOILING_POINT",rs.getString("BOILING_POINT")==null?"":rs.getString("BOILING_POINT"));
				//FLASH_POINT
				MSDSData.put( "FLASH_POINT",rs.getString( "FLASH_POINT" ) == null ? "" : rs.getString( "FLASH_POINT" ) );
				//PH
				//MSDSData.put("PH",rs.getString("PH")==null?"":rs.getString("PH"));
				//FREEZING_POINT
				//MSDSData.put("FREEZING_POINT",rs.getString("FREEZING_POINT")==null?"":rs.getString("FREEZING_POINT"));
				//DENSITY
				MSDSData.put( "DENSITY",rs.getString( "DENSITY" ) == null ? "" : rs.getString( "DENSITY" ) );
				//INGESTION
				//MSDSData.put("INGESTION",rs.getString("INGESTION")==null?"":rs.getString("INGESTION"));
				//ON_LINE
				//MSDSData.put("ON_LINE",rs.getString("ON_LINE")==null?"":rs.getString("ON_LINE"));
				//DENSITY_UNIT
				MSDSData.put( "DENSITY_UNIT",rs.getString( "DENSITY_UNIT" ) == null ? "" : rs.getString( "DENSITY_UNIT" ) );
				//PHYSICAL_STATE
				MSDSData.put( "PHYSICAL_STATE",rs.getString( "PHYSICAL_STATE" ) == null ? "" : rs.getString( "PHYSICAL_STATE" ) );
				//VOC_UNIT
				MSDSData.put( "VOC_UNIT",rs.getString( "VOC_UNIT" ) == null ? "" : rs.getString( "VOC_UNIT" ) );
				//TSCA_12B
				//MSDSData.put("TSCA_12B",rs.getString("TSCA_12B")==null?"":rs.getString("TSCA_12B"));
				//SARA_311_312_ACUTE
				//MSDSData.put("SARA_311_312_ACUTE",rs.getString("SARA_311_312_ACUTE")==null?"":rs.getString("SARA_311_312_ACUTE"));
				//SARA_311_312_CHRONIC
				//MSDSData.put("SARA_311_312_CHRONIC",rs.getString("SARA_311_312_CHRONIC")==null?"":rs.getString("SARA_311_312_CHRONIC"));
				//SARA_311_312_FIRE
				//MSDSData.put("SARA_311_312_FIRE",rs.getString("SARA_311_312_FIRE")==null?"":rs.getString("SARA_311_312_FIRE"));
				//SARA_311_312_PRESSURE
				//MSDSData.put("SARA_311_312_PRESSURE",rs.getString("SARA_311_312_PRESSURE")==null?"":rs.getString("SARA_311_312_PRESSURE"));
				//SARA_311_312_REACTIVITY
				//MSDSData.put("SARA_311_312_REACTIVITY",rs.getString("SARA_311_312_REACTIVITY")==null?"":rs.getString("SARA_311_312_REACTIVITY"));
				//OSHA_HAZARD
				//MSDSData.put("OSHA_HAZARD",rs.getString("OSHA_HAZARD")==null?"":rs.getString("OSHA_HAZARD"));
				//TSCA_LIST
				//MSDSData.put("TSCA_LIST",rs.getString("TSCA_LIST")==null?"":rs.getString("TSCA_LIST"));
				//MIXTURE
				//MSDSData.put("MIXTURE",rs.getString("MIXTURE")==null?"":rs.getString("MIXTURE"));
				//VOC
				MSDSData.put( "VOC",rs.getString( "VOC" ) == null ? "" : rs.getString( "VOC" ) );
				//VOC_LOWER
				MSDSData.put( "VOC_LOWER",rs.getString( "VOC_LOWER" ) == null ? "" : rs.getString( "VOC_LOWER" ) );
				//VOC_UPPER
				MSDSData.put( "VOC_UPPER",rs.getString( "VOC_UPPER" ) == null ? "" : rs.getString( "VOC_UPPER" ) );
				//REVIEWED_BY
				//MSDSData.put("REVIEWED_BY",rs.getString("REVIEWED_BY")==null?"":rs.getString("REVIEWED_BY"));
				//REVIEW_DATE
				//MSDSData.put("REVIEW_DATE",rs.getString("REVIEW_DATE")==null?"":rs.getString("REVIEW_DATE"));

				//REMARK
				MSDSData.put( "REMARK",rs.getString( "REMARK" ) == null ? "" : rs.getString( "REMARK" ) );
				//FLASH_POINT_UNIT
				MSDSData.put( "FLASH_POINT_UNIT",rs.getString( "FLASH_POINT_UNIT" ) == null ? "" : rs.getString( "FLASH_POINT_UNIT" ) );
				//SOLIDS
				MSDSData.put("SOLIDS",rs.getString("SOLIDS")==null?"":rs.getString("SOLIDS"));
				//SOLIDS_LOWER
				MSDSData.put("SOLIDS_LOWER",rs.getString("SOLIDS_LOWER")==null?"":rs.getString("SOLIDS_LOWER"));
				//SOLIDS_UPPER
				MSDSData.put("SOLIDS_UPPER",rs.getString("SOLIDS_UPPER")==null?"":rs.getString("SOLIDS_UPPER"));
				//SOLIDS_UNIT
				MSDSData.put("SOLIDS_UNIT",rs.getString("SOLIDS_UNIT")==null?"":rs.getString("SOLIDS_UNIT"));
				MSDSData.put("SOLIDS_SOURCE",rs.getString("SOLIDS_SOURCE")==null?"":rs.getString("SOLIDS_SOURCE"));
				/*
         //SOLIDS_PERCENT
         MSDSData.put("SOLIDS_PERCENT",rs.getString("SOLIDS_PERCENT")==null?"":rs.getString("SOLIDS_PERCENT"));
         //SOLIDS_LOWER_PERCENT
         MSDSData.put("SOLIDS_LOWER_PERCENT",rs.getString("SOLIDS_LOWER_PERCENT")==null?"":rs.getString("SOLIDS_LOWER_PERCENT"));
         //SOLIDS_UPPER_PERCENT
         MSDSData.put("SOLIDS_UPPER_PERCENT",rs.getString("SOLIDS_UPPER_PERCENT")==null?"":rs.getString("SOLIDS_UPPER_PERCENT"));*/
				//VOC_LOWER_PERCENT
				MSDSData.put( "VOC_LOWER_PERCENT",rs.getString( "VOC_LOWER_PERCENT" ) == null ? "" : rs.getString( "VOC_LOWER_PERCENT" ) );
				//VOC_UPPER_PERCENT
				MSDSData.put( "VOC_UPPER_PERCENT",rs.getString( "VOC_UPPER_PERCENT" ) == null ? "" : rs.getString( "VOC_UPPER_PERCENT" ) );
				//VOC_PERCENT
				MSDSData.put( "VOC_PERCENT",rs.getString( "VOC_PERCENT" ) == null ? "" : rs.getString( "VOC_PERCENT" ) );
				//ALT_DATA_SOURCE
				MSDSData.put( "ALT_DATA_SOURCE",rs.getString( "ALT_DATA_SOURCE" ) == null ? "" : rs.getString( "ALT_DATA_SOURCE" ) );
				//VAPOR_PRESSURE_DETECT
				MSDSData.put( "VAPOR_PRESSURE_DETECT",rs.getString( "VAPOR_PRESSURE_DETECT" ) == null ? "" : rs.getString( "VAPOR_PRESSURE_DETECT" ) );
				//VAPOR_PRESSURE
				MSDSData.put( "VAPOR_PRESSURE",rs.getString( "VAPOR_PRESSURE" ) == null ? "" : rs.getString( "VAPOR_PRESSURE" ) );
				//VAPOR_PRESSURE_UNIT
				MSDSData.put( "VAPOR_PRESSURE_UNIT",rs.getString( "VAPOR_PRESSURE_UNIT" ) == null ? "" : rs.getString( "VAPOR_PRESSURE_UNIT" ) );
				//ALT_DATA_SOURCE
				MSDSData.put( "VAPOR_PRESSURE_TEMP",rs.getString( "VAPOR_PRESSURE_TEMP" ) == null ? "" : rs.getString( "VAPOR_PRESSURE_TEMP" ) );
				//VAPOR_PRESURE_TEMP_UNIT
				MSDSData.put( "VAPOR_PRESURE_TEMP_UNIT",rs.getString( "VAPOR_PRESSURE_TEMP_UNIT" ) == null ? "" : rs.getString( "VAPOR_PRESSURE_TEMP_UNIT" ) );

				MSDSData.put("VOC_SOURCE",rs.getString("VOC_SOURCE")==null?"":rs.getString("VOC_SOURCE"));
				MSDSData.put("VOC_LESS_H2O_EXEMPT",rs.getString("VOC_LESS_H2O_EXEMPT")==null?"":rs.getString("VOC_LESS_H2O_EXEMPT"));
				MSDSData.put("VOC_LESS_H2O_EXEMPT_UNIT",rs.getString("VOC_LESS_H2O_EXEMPT_UNIT")==null?"":rs.getString("VOC_LESS_H2O_EXEMPT_UNIT"));
				MSDSData.put("VOC_LESS_H2O_EXEMPT_LOWER",rs.getString("VOC_LESS_H2O_EXEMPT_LOWER")==null?"":rs.getString("VOC_LESS_H2O_EXEMPT_LOWER"));
				MSDSData.put("VOC_LESS_H2O_EXEMPT_UPPER",rs.getString("VOC_LESS_H2O_EXEMPT_UPPER")==null?"":rs.getString("VOC_LESS_H2O_EXEMPT_UPPER"));
				MSDSData.put("VOC_LESS_H2O_EXEMPT_SOURCE",rs.getString("VOC_LESS_H2O_EXEMPT_SOURCE")==null?"":rs.getString("VOC_LESS_H2O_EXEMPT_SOURCE"));

				MSDSData.put("VOC_LB_PER_SOLID_LB",rs.getString("VOC_LB_PER_SOLID_LB")==null?"":rs.getString("VOC_LB_PER_SOLID_LB"));
				MSDSData.put("VOC_LB_PER_SOLID_LB_SOURCE",rs.getString("VOC_LB_PER_SOLID_LB_SOURCE")==null?"":rs.getString("VOC_LB_PER_SOLID_LB_SOURCE"));
				MSDSData.put("VOC_LB_PER_SOLID_LB_LOWER",rs.getString("VOC_LB_PER_SOLID_LB_LOWER")==null?"":rs.getString("VOC_LB_PER_SOLID_LB_LOWER"));
				MSDSData.put("VOC_LB_PER_SOLID_LB_UPPER",rs.getString("VOC_LB_PER_SOLID_LB_UPPER")==null?"":rs.getString("VOC_LB_PER_SOLID_LB_UPPER"));
				MSDSData.put("VAPOR_PRESSURE_SOURCE",rs.getString("VAPOR_PRESSURE_SOURCE")==null?"":rs.getString("VAPOR_PRESSURE_SOURCE"));
				MSDSData.put("VAPOR_PRESSURE_LOWER",rs.getString("VAPOR_PRESSURE_LOWER")==null?"":rs.getString("VAPOR_PRESSURE_LOWER"));
				MSDSData.put("VAPOR_PRESSURE_UPPER",rs.getString("VAPOR_PRESSURE_UPPER")==null?"":rs.getString("VAPOR_PRESSURE_UPPER"));

				/*MSDSData.put("VOC_LESS_EXEMPT_LOWER",rs.getString("VOC_LESS_EXEMPT_LOWER")==null?"":rs.getString("VOC_LESS_EXEMPT_LOWER"));
		 MSDSData.put("VOC_LESS_EXEMPT_UPPER",rs.getString("VOC_LESS_EXEMPT_UPPER")==null?"":rs.getString("VOC_LESS_EXEMPT_UPPER"));

		 MSDSData.put("VOC_LB_PER_SOLID_LB_LOW_CALC",rs.getString("VOC_LB_PER_SOLID_LB_LOW_CALC")==null?"":rs.getString("VOC_LB_PER_SOLID_LB_LOW_CALC"));
		 MSDSData.put("VOC_LB_PER_SOLID_LB_CALC",rs.getString("VOC_LB_PER_SOLID_LB_CALC")==null?"":rs.getString("VOC_LB_PER_SOLID_LB_CALC"));
		 MSDSData.put("VOC_LB_PER_SOLID_LB_UP_CALC",rs.getString("VOC_LB_PER_SOLID_LB_UP_CALC")==null?"":rs.getString("VOC_LB_PER_SOLID_LB_UP_CALC"));
		 MSDSData.put("VOC_LESS_H2O_EXEMPT_LOWER_CALC",rs.getString("VOC_LESS_H2O_EXEMPT_LOWER_CALC")==null?"":rs.getString("VOC_LESS_H2O_EXEMPT_LOWER_CALC"));
		 MSDSData.put("VOC_LESS_H2O_EXEMPT_CALC",rs.getString("VOC_LESS_H2O_EXEMPT_CALC")==null?"":rs.getString("VOC_LESS_H2O_EXEMPT_CALC"));
		 MSDSData.put("VOC_LESS_H2O_EXEMPT_UPPER_CALC",rs.getString("VOC_LESS_H2O_EXEMPT_UPPER_CALC")==null?"":rs.getString("VOC_LESS_H2O_EXEMPT_UPPER_CALC"));*/

				//MSDS_DATA
				ResultData.put( "MSDS_DATA",MSDSData );

				//Getting Composition Data
				if ( materialid.length() > 0 )
				{
					try
					{
						compdbrs=db.doQuery( "select * from global.composition where material_id = " + materialid + " and REVISION_DATE=to_date('" + revisionDate1 + "','MM/DD/YYYY') order by lower(CAS_NUMBER) " );
						comprs=compdbrs.getResultSet();

						while ( comprs.next() )
						{
							comprecords++;
							CompositionData=new Hashtable();

							//PERCENT
							CompositionData.put( "PERCENT",comprs.getString( "PERCENT" ) == null ? "" : comprs.getString( "PERCENT" ) );
							//PERCENT_AS_CAS
							//CompositionData.put("PERCENT_AS_CAS",comprs.getString("PERCENT_AS_CAS")==null?"":comprs.getString("PERCENT_AS_CAS"));
							//PERCENT_LOWER
							CompositionData.put( "PERCENT_LOWER",comprs.getString( "PERCENT_LOWER" ) == null ? "" : comprs.getString( "PERCENT_LOWER" ) );
							//PERCENT_UPPER
							CompositionData.put( "PERCENT_UPPER",comprs.getString( "PERCENT_UPPER" ) == null ? "" : comprs.getString( "PERCENT_UPPER" ) );
							//HAZARDOUS
							CompositionData.put( "HAZARDOUS",comprs.getString( "HAZARDOUS" ) == null ? "" : comprs.getString( "HAZARDOUS" ) );
							//CAS_NUMBER
							CompositionData.put( "CAS_NUMBER",comprs.getString( "CAS_NUMBER" ) == null ? "" : comprs.getString( "CAS_NUMBER" ) );
							//TRADE_SECRET
							CompositionData.put( "TRADE_SECRET",comprs.getString( "TRADE_SECRET" ) == null ? "" : comprs.getString( "TRADE_SECRET" ) );
							//REMARK
							CompositionData.put( "REMARK",comprs.getString( "REMARK" ) == null ? "" : comprs.getString( "REMARK" ) );
							//MSDS_CHEMICAL_NAME
							CompositionData.put( "MSDS_CHEMICAL_NAME",comprs.getString( "MSDS_CHEMICAL_NAME" ) == null ? "" : comprs.getString( "MSDS_CHEMICAL_NAME" ) );

							CompositionData.put( "LINE_STATUS","" );

							compresult.addElement( CompositionData );
						}
					}
					catch ( Exception e11 )
					{
						//e11.printStackTrace();
						cataddlog.error(e11.getMessage());
					}
					finally
					{
						if ( compdbrs != null )
						{
							compdbrs.close();
						}
					}

					for ( int rem=comprecords; rem < 50; rem++ )
					{
						CompositionData=new Hashtable();

						//PERCENT
						CompositionData.put( "PERCENT","" );
						//PERCENT_LOWER
						CompositionData.put( "PERCENT_LOWER","" );
						//PERCENT_UPPER
						CompositionData.put( "PERCENT_UPPER","" );
						//HAZARDOUS
						CompositionData.put( "HAZARDOUS","" );
						//CAS_NUMBER
						CompositionData.put( "CAS_NUMBER","" );
						//TRADE_SECRET
						CompositionData.put( "TRADE_SECRET","" );
						//REMARK
						CompositionData.put( "REMARK","" );
						//MSDS_CHEMICAL_NAME
						CompositionData.put( "MSDS_CHEMICAL_NAME","" );

						CompositionData.put( "LINE_STATUS","" );

						compresult.addElement( CompositionData );
					}

				}
				else
				{
					for ( int rem=0; rem < 50; rem++ )
					{
						CompositionData=new Hashtable();

						//PERCENT
						CompositionData.put( "PERCENT","" );
						//PERCENT_LOWER
						CompositionData.put( "PERCENT_LOWER","" );
						//PERCENT_UPPER
						CompositionData.put( "PERCENT_UPPER","" );
						//HAZARDOUS
						CompositionData.put( "HAZARDOUS","" );
						//CAS_NUMBER
						CompositionData.put( "CAS_NUMBER","" );
						//TRADE_SECRET
						CompositionData.put( "TRADE_SECRET","" );
						//REMARK
						CompositionData.put( "REMARK","" );
						//MSDS_CHEMICAL_NAME
						CompositionData.put( "MSDS_CHEMICAL_NAME","" );

						CompositionData.put( "LINE_STATUS","" );

						compresult.addElement( CompositionData );
					}
				}

				ResultData.put( "COMPOSITION_DATA",compresult );

				result.addElement( ResultData );
			}
		}
		catch ( Exception e1 )
		{
			//e1.printStackTrace();
			cataddlog.error(e1.getMessage());
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
		recsum.put( "ITEM_ID","" );
		//PART_DESC
		recsum.put( "PART_DESC","" );
		recsum.put( "DATA",result );
		recsum.put( "REQUEST_ID",requestID );
		recsum.put( "LINE_ITEM",lineItem );

		return recsum;
	}

	//Get details that are to be shown for a particular request_id
	public Hashtable getHashtableData( String requestID, String lineItem, String CompanyID )
	{
		Hashtable ResultData=new Hashtable();
		Vector result=new Vector();
		DBResultSet dbrs=null;
		ResultSet rs=null;

		String detailquery="select x.*,p.RECERT,p.SIZE_VARIES,mfg.* from \n";
		detailquery+="(select i.MIN_TEMP, i.MAX_TEMP, i.TEMP_UNITS, car.MANAGE_KITS_AS_SINGLE_UNIT,cai.item_id,i.company_id,i.REQUEST_ID,i.MATERIAL_DESC,i.MANUFACTURER, \n";
		detailquery+="i.MATERIAL_ID,i.PART_ID,i.PART_SIZE, \n";
		detailquery+="i.SIZE_UNIT,i.PKG_STYLE,i.MFG_CATALOG_ID,i.CASE_QTY,i.DIMENSION, \n";
		detailquery+="i.GRADE,i.MFG_TRADE_NAME,i.NETWT,i.NETWT_UNIT,i.COMPONENTS_PER_ITEM,i.SAMPLE_ONLY, \n";
		detailquery+="i.MFG_SHELF_LIFE_BASIS,i.MFG_SHELF_LIFE,i.ITEM_VERIFIED,i.MFG_STORAGE_TEMP,i.COMMENTS,i.MATERIAL_TYPE,i.LABEL_COLOR \n";
		detailquery+=" from customer.catalog_add_item_qc i, customer.catalog_add_request_new car,customer.catalog_add_item cai \n";
		detailquery+="where i.request_id = car.request_id and car.request_id = cai.request_id and i.request_id = cai.request_id and i.line_item = cai.line_item and i.part_id = cai.part_id and cai.line_item = " + lineItem + ") x, \n";
		detailquery+="global.part p,global.material m,global.manufacturer mfg \n";
		detailquery+="where \n";
		detailquery+="x.item_id = p.item_id(+) and \n";
		detailquery+="x.material_id = p.material_id(+) and \n";
		detailquery+="x.material_id = m.material_id(+) and \n";
		detailquery+="x.company_id = '" + CompanyID + "' and \n";
		detailquery+="x.request_id = " + requestID + " and\nm.MFG_ID = mfg.MFG_ID(+)\n order by p.PART_ID asc \n";

		int totalrecords=0;
		String ItemID="";
		String caseqty="";
		String manassingleunit="";

		try
		{
			dbrs=db.doQuery( detailquery );
			rs=dbrs.getResultSet();

			while ( rs.next() )
			{
				totalrecords+=1;

				ResultData=new Hashtable();

				ItemID=BothHelpObjs.makeBlankFromNull( rs.getString( "ITEM_ID" ) );
				caseqty=BothHelpObjs.makeBlankFromNull( rs.getString( "CASE_QTY" ) );
				manassingleunit = BothHelpObjs.makeBlankFromNull( rs.getString( "MANAGE_KITS_AS_SINGLE_UNIT" ) );

				ResultData.put( "REQUEST_ID",BothHelpObjs.makeBlankFromNull( rs.getString( "REQUEST_ID" ) ) );
				//MANUFACTURER
				ResultData.put( "MANUFACTURER",BothHelpObjs.makeBlankFromNull( rs.getString( "MFG_DESC" ) ) );
				//MANUFACTURER_ID
				ResultData.put( "MANUFACTURER_ID",BothHelpObjs.makeBlankFromNull( rs.getString( "MFG_ID" ) ) );
				//MFG_URL
				ResultData.put( "MFG_URL",rs.getString( "MFG_URL" ) == null ? "" : rs.getString( "MFG_URL" ) );
				//CONTACT
				ResultData.put( "CONTACT",rs.getString( "CONTACT" ) == null ? "" : rs.getString( "CONTACT" ) );
				//PHONE
				ResultData.put( "PHONE",rs.getString( "PHONE" ) == null ? "" : rs.getString( "PHONE" ) );
				//EMAIL
				ResultData.put( "EMAIL",rs.getString( "EMAIL" ) == null ? "" : rs.getString( "EMAIL" ) );
				//NOTES
				ResultData.put( "NOTES",rs.getString( "NOTES" ) == null ? "" : rs.getString( "NOTES" ) );
				//GRADE
				ResultData.put( "GRADE",BothHelpObjs.makeBlankFromNull( rs.getString( "GRADE" ) ) );
				//MATERIAL_DESC
				ResultData.put( "MATERIAL_DESC",BothHelpObjs.makeBlankFromNull( rs.getString( "MATERIAL_DESC" ) ) );
				//MFG_TRADE_NAME
				ResultData.put( "TRADE_NAME",BothHelpObjs.makeBlankFromNull( rs.getString( "MFG_TRADE_NAME" ) ) );
				//Material ID
				ResultData.put( "MATERIAL_ID",BothHelpObjs.makeBlankFromNull( rs.getString( "MATERIAL_ID" ) ) );
				//MFG_CATALOG_ID
				ResultData.put( "MFG_PART_NO",BothHelpObjs.makeBlankFromNull( rs.getString( "MFG_CATALOG_ID" ) ) );
				//shelf life
				ResultData.put( "SHELF_LIFE",BothHelpObjs.makeBlankFromNull( rs.getString( "MFG_SHELF_LIFE" ) ) );
				//Shelf Life Basis
				ResultData.put( "SHELF_LIFE_BASIS",BothHelpObjs.makeBlankFromNull( rs.getString( "MFG_SHELF_LIFE_BASIS" ) ) );
				//storage temperature
				ResultData.put( "STORAGE_TEMP",BothHelpObjs.makeBlankFromNull( rs.getString( "MFG_STORAGE_TEMP" ) ) );
				//NO OF COMPONENTS
				ResultData.put( "NO_OF_COMPONENTS",BothHelpObjs.makeBlankFromNull( rs.getString( "COMPONENTS_PER_ITEM" ) ) );
				//PART SIZE
				ResultData.put( "PART_SIZE",BothHelpObjs.makeBlankFromNull( rs.getString( "PART_SIZE" ) ) );
				//PART_SIZE_UNIT
				ResultData.put( "PART_SIZE_UNIT",BothHelpObjs.makeBlankFromNull( rs.getString( "SIZE_UNIT" ) ) );
				//PACKAGING STYLE
				ResultData.put( "PACKAGING_STYLE",BothHelpObjs.makeBlankFromNull( rs.getString( "PKG_STYLE" ) ) );
				//Dimension
				ResultData.put( "DIMENSION",BothHelpObjs.makeBlankFromNull( rs.getString( "DIMENSION" ) ) );
				//NET WEIGHT
				ResultData.put( "NET_WEIGHT",BothHelpObjs.makeBlankFromNull( rs.getString( "NETWT" ) ) );
				//NET WEIGHT UNIT
				ResultData.put( "NET_WEIGHT_UNIT",BothHelpObjs.makeBlankFromNull( rs.getString( "NETWT_UNIT" ) ) );
				//COMMENTS
				ResultData.put( "COMMENTS",BothHelpObjs.makeBlankFromNull( rs.getString( "COMMENTS" ) ) );
				//CASE_QTY
				ResultData.put( "CASE_QTY",BothHelpObjs.makeBlankFromNull( rs.getString( "CASE_QTY" ) ) );
				//SAMPLE SIZE
				ResultData.put( "SAMPLE_SIZE",BothHelpObjs.makeBlankFromNull( rs.getString( "SAMPLE_ONLY" ) ) );
				// CATEGORY
				ResultData.put( "CATEGORY",BothHelpObjs.makeBlankFromNull( rs.getString( "MATERIAL_TYPE" ) ) );
				//NO_OF_CHEMICAL_COMP
				ResultData.put( "NO_OF_CHEMICAL_COMP"," " );
				//PART ID
				ResultData.put( "PART_ID",BothHelpObjs.makeBlankFromNull( rs.getString( "PART_ID" ) ) );
				//UPDATE_FLAG
				ResultData.put( "UPDATE_FLAG",new Boolean( true ) );
				//Recerts
				ResultData.put( "RECERTS",BothHelpObjs.makeBlankFromNull( rs.getString( "RECERT" ) ) );
				//Size Varies
				ResultData.put( "SIZE_VARIES",BothHelpObjs.makeBlankFromNull( rs.getString( "SIZE_VARIES" ) ) );
				//ITEM_VERIFIED
				ResultData.put( "ITEM_VERIFIED",BothHelpObjs.makeBlankFromNull( rs.getString( "ITEM_VERIFIED" ) ) );

				//MANAGE_KITS_AS_SINGLE_UNIT
				//ResultData.put( "MANAGE_KITS_AS_SINGLE_UNIT",BothHelpObjs.makeBlankFromNull( rs.getString( "MANAGE_KITS_AS_SINGLE_UNIT" ) ) );
				//MIN_TEMP
				ResultData.put( "MIN_TEMP",BothHelpObjs.makeBlankFromNull( rs.getString( "MIN_TEMP" ) ) );
				//MAX_TEMP
				ResultData.put( "MAX_TEMP",BothHelpObjs.makeBlankFromNull( rs.getString( "MAX_TEMP" ) ) );
				//TEMP_UNITS
				ResultData.put( "TEMP_UNITS",BothHelpObjs.makeBlankFromNull( rs.getString( "TEMP_UNITS" ) ) );
				//LABEL_COLOR
				ResultData.put( "LABEL_COLOR",BothHelpObjs.makeBlankFromNull( rs.getString( "LABEL_COLOR" ) ) );

				result.addElement( ResultData );
			}
		}
		catch ( Exception e1 )
		{
			//e1.printStackTrace();
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
		recsum.put( "ITEM_ID",ItemID );
		//CASE_QTY
		recsum.put( "CASE_QTY",caseqty );
		//PART_DESC
		recsum.put( "PART_DESC","" );
		//MANAGE_KITS_AS_SINGLE_UNIT
		recsum.put( "MANAGE_KITS_AS_SINGLE_UNIT","" );
		recsum.put( "DATA",result );
		recsum.put( "REQUEST_ID",requestID );
        recsum.put( "LINE_ITEM",lineItem );
		return recsum;
	}

	//Get details that are to be shown Original Submitted Data for a particular request_id
	public Hashtable getOrigHashtableData(String requestID)
	{
		Hashtable ResultData = new Hashtable();
		Vector result = new Vector();
		DBResultSet dbrs = null;
		ResultSet rs = null;

		String detailquery = "select distinct i.REQUEST_ID,i.MATERIAL_DESC,i.MANUFACTURER,i.MATERIAL_ID,i.PART_SIZE,i.SIZE_UNIT,i.PKG_STYLE,i.MFG_CATALOG_ID,i.CASE_QTY,i.DIMENSION, \n";
		detailquery +="i.GRADE,i.MFG_TRADE_NAME,i.NETWT,i.NETWT_UNIT,i.COMPONENTS_PER_ITEM,i.SAMPLE_ONLY,i.LABEL_COLOR \n";
		detailquery +="from \n";
		detailquery +="customer.catalog_add_item_orig i \n";
		detailquery +="where \n";
		detailquery +="i.request_id = "+requestID+" \n";

		int totalrecords = 0;

		try
		{
			dbrs = db.doQuery(detailquery);
			rs=dbrs.getResultSet();

			while(rs.next())
			{
				totalrecords +=1;
				ResultData = new Hashtable();
				ResultData.put("REQUEST_ID",BothHelpObjs.makeBlankFromNull(rs.getString("REQUEST_ID")));
				//MANUFACTURER
				ResultData.put("MANUFACTURER",BothHelpObjs.makeBlankFromNull(rs.getString("MANUFACTURER")));
				//MANUFACTURER_ID
				//ResultData.put("MANUFACTURER_ID",BothHelpObjs.makeBlankFromNull(rs.getString("MFG_ID")));
				//GRADE
				ResultData.put("GRADE",BothHelpObjs.makeBlankFromNull(rs.getString("GRADE")));
				//MATERIAL_DESC
				ResultData.put("MATERIAL_DESC",BothHelpObjs.makeBlankFromNull(rs.getString("MATERIAL_DESC")));
				//MFG_TRADE_NAME
				ResultData.put("TRADE_NAME",BothHelpObjs.makeBlankFromNull(rs.getString("MFG_TRADE_NAME")));
				//Material ID
				ResultData.put("MATERIAL_ID",BothHelpObjs.makeBlankFromNull(rs.getString("MATERIAL_ID")));
				//MFG_CATALOG_ID
				ResultData.put("MFG_PART_NO",BothHelpObjs.makeBlankFromNull(rs.getString("MFG_CATALOG_ID")));
				//shelf life
				ResultData.put("SHELF_LIFE","");
				//Shelf Life Basis
				ResultData.put("SHELF_LIFE_BASIS","");
				//storage temperature
				ResultData.put("STORAGE_TEMP","");
				//NO OF COMPONENTS
				ResultData.put("NO_OF_COMPONENTS",BothHelpObjs.makeBlankFromNull(rs.getString("COMPONENTS_PER_ITEM")));
				//PART SIZE
				ResultData.put("PART_SIZE",BothHelpObjs.makeBlankFromNull(rs.getString("PART_SIZE")));
				//PART_SIZE_UNIT
				ResultData.put("PART_SIZE_UNIT",BothHelpObjs.makeBlankFromNull(rs.getString("SIZE_UNIT")));
				//PACKAGING STYLE
				ResultData.put("PACKAGING_STYLE",BothHelpObjs.makeBlankFromNull(rs.getString("PKG_STYLE")));
				//Dimension
				ResultData.put("DIMENSION",BothHelpObjs.makeBlankFromNull(rs.getString("DIMENSION")));
				//NET WEIGHT
				ResultData.put("NET_WEIGHT",BothHelpObjs.makeBlankFromNull(rs.getString("NETWT")));
				//NET WEIGHT UNIT
				ResultData.put("NET_WEIGHT_UNIT",BothHelpObjs.makeBlankFromNull(rs.getString("NETWT_UNIT")));
				//COMMENTS
				//ResultData.put("COMMENTS",BothHelpObjs.makeBlankFromNull(rs.getString("COMMENTS")));
				//SAMPLE SIZE
				ResultData.put("SAMPLE_SIZE",BothHelpObjs.makeBlankFromNull(rs.getString("SAMPLE_ONLY")));
				// CATEGORY
				//ResultData.put("CATEGORY",BothHelpObjs.makeBlankFromNull(rs.getString("MATERIAL_TYPE")));
				//NO_OF_CHEMICAL_COMP
				ResultData.put("NO_OF_CHEMICAL_COMP","");
				//LABEL_COLOR
				ResultData.put( "LABEL_COLOR",BothHelpObjs.makeBlankFromNull( rs.getString( "LABEL_COLOR" ) ) );

				result.addElement(ResultData);
			}
		}
		catch (Exception e1)
		{
			//e1.printStackTrace();
		}
		finally
		{
			if (dbrs != null)  { dbrs.close(); }
		}

		Hashtable recsum  = new Hashtable();
		recsum.put("TOTAL_NUMBER", new Integer(totalrecords) );
		//ITEM_ID
		recsum.put("ITEM_ID","");
		//PART_DESC
		recsum.put("PART_DESC","");
		recsum.put("DATA",result );
		return recsum;

	}

	public boolean UpdateLine( Hashtable DataH,String requestID, String lineItem, String action,String caseQuantity )
	{
		// get main information
		boolean result=true;

		String mfg_id=DataH.get( "MANUFACTURER_ID" ).toString();
		String manufacturer=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( DataH.get( "MANUFACTURER" ).toString() );
		String material_id=DataH.get( "MATERIAL_ID" ).toString();
		String material_desc=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( DataH.get( "MATERIAL_DESC" ).toString() );
		String grade=DataH.get( "GRADE" ).toString();
		String mfg_shelf_life=DataH.get( "SHELF_LIFE" ).toString();
		//String mfg_storage_temp=DataH.get( "STORAGE_TEMP" ).toString();
		String shelf_life_basis=DataH.get( "SHELF_LIFE_BASIS" ).toString();
		String noofcomponents=DataH.get( "NO_OF_COMPONENTS" ).toString();
		String part_size=DataH.get( "PART_SIZE" ).toString();
		String size_units=DataH.get( "PART_SIZE_UNIT" ).toString();
		String pkg_style=DataH.get( "PACKAGING_STYLE" ).toString();
		String dimension=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( DataH.get( "DIMENSION" ).toString() );
		String net_wt=DataH.get( "NET_WEIGHT" ).toString();
		String net_weight_unit=DataH.get( "NET_WEIGHT_UNIT" ).toString();
		String mfg_part_no=BothHelpObjs.changeSingleQuotetoTwoSingleQuote(DataH.get( "MFG_PART_NO" ).toString());

		String Comments=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( DataH.get( "COMMENTS" ).toString() );
		/*if ( Comments.length() > 1000 )
      {
        Comments=Comments.substring( 0,999 );
      }*/

		//String material_category=DataH.get( "CATEGORY" ).toString();
		//String sample_size=DataH.get( "SAMPLE_SIZE" ).toString();
		String trade_name=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( DataH.get( "TRADE_NAME" ).toString() );
		String itemverified=DataH.get( "ITEM_VERIFIED" ).toString();
		String partid=DataH.get( "PART_ID" ).toString();

		String mintemp=DataH.get( "MIN_TEMP" ).toString();
		String maxtemp=DataH.get( "MAX_TEMP" ).toString();
		String tempunits=DataH.get( "TEMP_UNITS" ).toString();
		String labelColor=DataH.get( "LABEL_COLOR" ).toString();

		String materialquery="";
		if ( material_id.length() > 0 )
		{
			materialquery="update global.material set MATERIAL_DESC='" + material_desc + "' where MATERIAL_ID = " + material_id + " ";
		}

		String query  = "update customer.catalog_add_item_qc set \n";
		query += "MATERIAL_DESC='"+material_desc+"', MANUFACTURER='"+manufacturer+"', MATERIAL_ID='"+material_id+"', PART_SIZE='"+part_size+"', \n";
		query +="SIZE_UNIT='"+size_units+"', PKG_STYLE='"+pkg_style+"', MFG_CATALOG_ID='"+mfg_part_no+"', DIMENSION='"+dimension+"', \n";
		query +="GRADE='"+grade+"', MFG_TRADE_NAME='"+trade_name+"', NETWT='"+net_wt+"', NETWT_UNIT='"+net_weight_unit+"', COMPONENTS_PER_ITEM='"+noofcomponents+"', \n";
		query +="COMMENTS='"+Comments+"',MFG_SHELF_LIFE_BASIS='"+shelf_life_basis+"', \n";
		query +="mfg_shelf_life='"+mfg_shelf_life+"', mfg_id='"+mfg_id+"',ITEM_VERIFIED='"+itemverified+"',CASE_QTY="+(caseQuantity.length()==0?"null":caseQuantity)+", \n";
		//SAMPLE_ONLY='"+sample_size+"',MFG_SHELF_LIFE_BASIS='"+shelf_life_basis+"', MATERIAL_TYPE='"+material_category+"',MFG_STORAGE_TEMP='"+mfg_storage_temp+"',
		query += "MIN_TEMP='"+mintemp+"', MAX_TEMP='"+maxtemp+"', TEMP_UNITS='"+tempunits+"',LABEL_COLOR='"+labelColor+"' \n";
		query += " where REQUEST_ID = "+requestID+" and PART_ID = "+partid+" and LINE_ITEM = " + lineItem;

		String catadditemquery  = "update customer.catalog_add_item set \n";
		catadditemquery += "MATERIAL_DESC='"+material_desc+"', MANUFACTURER='"+manufacturer+"', MATERIAL_ID='"+material_id+"', PART_SIZE='"+part_size+"', \n";
		catadditemquery +="SIZE_UNIT='"+size_units+"', PKG_STYLE='"+pkg_style+"', MFG_CATALOG_ID='"+mfg_part_no+"', DIMENSION='"+dimension+"', \n";
		catadditemquery +="GRADE='"+grade+"', MFG_TRADE_NAME='"+trade_name+"', NETWT='"+net_wt+"', NETWT_UNIT='"+net_weight_unit+"', COMPONENTS_PER_ITEM='"+noofcomponents+"', \n";
		catadditemquery +="CASE_QTY="+(caseQuantity.length()==0?"null":caseQuantity)+",LABEL_COLOR='"+labelColor+"'";
		//SAMPLE_ONLY='"+sample_size+"',MFG_SHELF_LIFE_BASIS='"+shelf_life_basis+"', MATERIAL_TYPE='"+material_category+"',
		catadditemquery+=" where REQUEST_ID = " + requestID + " and PART_ID = " + partid +" and LINE_ITEM = " + lineItem;

		try
		{
			db.doUpdate( query );
			if ( "Process".equalsIgnoreCase( action ) )
			{
				db.doUpdate( catadditemquery );
			}

			if ( materialquery.length() > 0 )
			{
				db.doUpdate( materialquery );
			}

			result=true;
		}
		catch ( Exception e )
		{
			//e.printStackTrace();
			cataddlog.error(e.getMessage());
			result=false;
		}

		return result;
	}

	public Hashtable updateMsdsData( Hashtable DataH,String loginID,String requestID )
	{
		boolean msdsresult=true;
		Hashtable materialData=null;
		Hashtable msdsData=null;
		Hashtable compositionData=null;
		Vector ofCompositionData=null;
		String msdserrmsg = "";

		materialData= ( Hashtable ) DataH.get( "MATERIAL_DATA" );
		msdsData= ( Hashtable ) DataH.get( "MSDS_DATA" );
		ofCompositionData= ( Vector ) DataH.get( "COMPOSITION_DATA" );

		String material_desc=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( materialData.get( "MATERIAL_DESC" ).toString() );
		String manufacturer=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( materialData.get( "MANUFACTURER" ).toString() );
		String material_id=materialData.get( "MATERIAL_ID" ).toString();
		String mfg_id=materialData.get( "MFG_ID" ).toString();
		String mfg_trade_name=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( materialData.get( "MFG_TRADE_NAME" ).toString() );
		//String landdot = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(materialData.get("LANDDOT").toString());
		//String airdot = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(materialData.get("AIRDOT").toString());
		//String waterdot = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(materialData.get("WATERDOT").toString());
		//String erg = materialData.get("ERG").toString();
		String Comments=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( materialData.get( "COMMENTS" ).toString() );
		String revision_date=msdsData.get( "REVISION_DATE" ).toString();
		String content=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( msdsData.get( "CONTENT" ).toString() );
		//String emergency_phone = msdsData.get("EMERGENCY_PHONE").toString();
		String specific_gravity=msdsData.get( "SPECIFIC_GRAVITY" ).toString();
		String health=msdsData.get( "HEALTH" ).toString();
		String flammability=msdsData.get( "FLAMMABILITY" ).toString();
		String reactivity=msdsData.get( "REACTIVITY" ).toString();
		String hmishealth=msdsData.get( "HMIS_HEALTH" ).toString();
		String hmisflammability=msdsData.get( "HMIS_FLAMMABILITY" ).toString();
		String hmisreactivity=msdsData.get( "HMIS_REACTIVITY" ).toString();
		String personnelprotection=msdsData.get( "PERSONAL_PROTECTION" ).toString();
		String specific_hazard=msdsData.get( "SPECIFIC_HAZARD" ).toString();
		String flash_point=msdsData.get( "FLASH_POINT" ).toString();
		/*if ( flash_point.length() > 12 )
      {
        flash_point=flash_point.substring( 0,11 );
      }*/
		//String freezing_point = msdsData.get("FREEZING_POINT").toString();
		String density=msdsData.get( "DENSITY" ).toString();
		//String ingestion = msdsData.get("INGESTION").toString();
		//String on_line = msdsData.get("ON_LINE").toString();
		String density_unit=msdsData.get( "DENSITY_UNIT" ).toString();
		if ("None".equalsIgnoreCase(density_unit) || density_unit.length() == 0) {density_unit = "null";} else {density_unit = "'"+density_unit+"'";}

		String physical_state=msdsData.get( "PHYSICAL_STATE" ).toString();
		if ("None".equalsIgnoreCase(physical_state) || physical_state.length() == 0) {physical_state = "null";} else {physical_state = "'"+physical_state+"'";}

		String voc_unit=msdsData.get( "VOC_UNIT" ).toString();
		if ("None".equalsIgnoreCase(voc_unit) || voc_unit.length() == 0) {voc_unit = "null";} else {voc_unit = "'"+voc_unit+"'";}

		String voc=msdsData.get( "VOC" ).toString();
		String voc_lower=msdsData.get( "VOC_LOWER" ).toString();
		String voc_upper=msdsData.get( "VOC_UPPER" ).toString();
		//String reviewed_by = msdsData.get("REVIEWED_BY").toString();
		//String review_date = msdsData.get("REVIEW_DATE").toString();
		String msdsremark=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( msdsData.get( "REMARK" ).toString() );
		/*if ( msdsremark.length() > 60 )
      {
        msdsremark=msdsremark.substring( 0,59 );
      }*/

		String altdataSoucre=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( msdsData.get( "ALT_DATA_SOURCE" ).toString() );
		/*if ( altdataSoucre.length() > 100 )
      {
        altdataSoucre=altdataSoucre.substring( 0,99 );
      }*/
		String flash_point_unit=msdsData.get( "FLASH_POINT_UNIT" ).toString();
		String solids = msdsData.get("SOLIDS").toString();
		String solids_lower = msdsData.get("SOLIDS_LOWER").toString();
		String solids_upper = msdsData.get("SOLIDS_UPPER").toString();

		String solids_unit = msdsData.get("SOLIDS_UNIT").toString();
		if ("None".equalsIgnoreCase(solids_unit) || solids_unit.length() == 0) {solids_unit = "null";}else {solids_unit = "'"+solids_unit+"'";}

		String solids_source = msdsData.get("SOLIDS_SOURCE").toString();
		if ("None".equalsIgnoreCase(solids_source) || solids_source.length() == 0) {solids_source = "null";}else {solids_source = "'"+solids_source+"'";}

		String partid=DataH.get( "PART_ID" ).toString();
		String vappressdet=msdsData.get( "VAPOR_PRESSURE_DETECT" ).toString();
		String vappress=msdsData.get( "VAPOR_PRESSURE" ).toString();
		String vappressunit=msdsData.get( "VAPOR_PRESSURE_UNIT" ).toString();
		String vappresstemp=msdsData.get( "VAPOR_PRESSURE_TEMP" ).toString();
		String vappresstempunt=msdsData.get( "VAPOR_PRESURE_TEMP_UNIT" ).toString();

		String voc_source = msdsData.get("VOC_SOURCE").toString();
		String voc_less_h2o_exempt = msdsData.get("VOC_LESS_H2O_EXEMPT").toString();
		String voc_less_h2o_exempt_unit = msdsData.get("VOC_LESS_H2O_EXEMPT_UNIT").toString();
		String voc_less_h2o_exempt_lower = msdsData.get("VOC_LESS_H2O_EXEMPT_LOWER").toString();
		String voc_less_h2o_exempt_upper = msdsData.get("VOC_LESS_H2O_EXEMPT_UPPER").toString();
		String voc_less_h2o_exempt_source = msdsData.get("VOC_LESS_H2O_EXEMPT_SOURCE").toString();


		String voc_lb_per_solid_lb = msdsData.get("VOC_LB_PER_SOLID_LB").toString();
		String voc_lb_per_solid_lb_lower = msdsData.get("VOC_LB_PER_SOLID_LB_LOWER").toString();
		String voc_lb_per_solid_lb_upper = msdsData.get("VOC_LB_PER_SOLID_LB_UPPER").toString();
		String voc_lb_per_solid_lb_source = msdsData.get("VOC_LB_PER_SOLID_LB_SOURCE").toString();
		String vapor_pressure_source = msdsData.get("VAPOR_PRESSURE_SOURCE").toString();
		String vapor_pressure_lower = msdsData.get("VAPOR_PRESSURE_LOWER").toString();
		String vapor_pressure_upper = msdsData.get("VAPOR_PRESSURE_UPPER").toString();

		/*VOC_LESS_H20_EXEMPT_SOURCE='"+voc_less_h20_exempt_source+"',VOC_LESS_EXEMPT_LOWER='"+voc_less_exempt_lower+"',
VOC_LESS_EXEMPT_UPPER='"+voc_less_exempt_upper+"',,VOC_LB_PER_SOLID_LB_LOW_CALC='"+voc_lb_per_solid_lb_low_calc+"',
VOC_LB_PER_SOLID_LB_CALC='"+voc_lb_per_solid_lb_calc+"',VOC_LB_PER_SOLID_LB_UP_CALC='"+voc_lb_per_solid_lb_up_calc+"',
VOC_LESS_H2O_EXEMPT_LOWER_CALC='"+voc_less_h2o_exempt_lower_calc+"',
VOC_LESS_H2O_EXEMPT_CALC='"+voc_less_h2o_exempt_calc+"',VOC_LESS_H2O_EXEMPT_UPPER_CALC='"+voc_less_h2o_exempt_upper_calc+"'*/
		/*String voc_less_exempt_lower = msdsData.get("VOC_LESS_EXEMPT_LOWER").toString();
	  String voc_less_exempt_upper = msdsData.get("VOC_LESS_EXEMPT_UPPER").toString();

	  String voc_lb_per_solid_lb_low_calc = msdsData.get("VOC_LB_PER_SOLID_LB_LOW_CALC").toString();
	  String voc_lb_per_solid_lb_calc = msdsData.get("VOC_LB_PER_SOLID_LB_CALC").toString();
	  String voc_lb_per_solid_lb_up_calc = msdsData.get("VOC_LB_PER_SOLID_LB_UP_CALC").toString();
	  String voc_less_h2o_exempt_lower_calc = msdsData.get("VOC_LESS_H2O_EXEMPT_LOWER_CALC").toString();
	  String voc_less_h2o_exempt_calc = msdsData.get("VOC_LESS_H2O_EXEMPT_CALC").toString();
	  String voc_less_h2o_exempt_upper_calc = msdsData.get("VOC_LESS_H2O_EXEMPT_UPPER_CALC").toString();*/

		boolean updateorinsert=false;

		try
		{
			String materialquery="";
			String msdsquery="";

			if ( material_id.length() > 0 )
			{
				materialquery="update global.material set \n";
				materialquery+="MATERIAL_DESC='" + material_desc + "', \n"; //MANUFACTURER='"+manufacturer+"',
				if ( mfg_id.length() > 0 )
				{
					materialquery+="MFG_ID=" + mfg_id + ",";
				}
				materialquery+=" TRADE_NAME='" + mfg_trade_name + "'";
				materialquery+=" where MATERIAL_ID = " + material_id + " ";
			}

			String catadditemqcupdate  = "update customer.catalog_add_item_qc set \n";
			catadditemqcupdate += "MATERIAL_DESC='"+material_desc+"', MANUFACTURER='"+manufacturer+"', MATERIAL_ID='"+material_id+"',MFG_ID='"+mfg_id+"',MFG_TRADE_NAME='"+mfg_trade_name+"',COMMENTS='"+Comments+"' \n";
			catadditemqcupdate += " where REQUEST_ID = "+requestID+" and PART_ID = "+partid+"";

			String onLine = "Y";

			if (revision_date.length() == 10)
			{
				int test_number = DbHelpers.countQuery(db,"select count(*) from global.msds where MATERIAL_ID = "+material_id+" and REVISION_DATE=to_date('"+revision_date+"','MM/DD/YYYY')");
				int foreignMsds = DbHelpers.countQuery(db,"select count(*) from global.msds_locale where MATERIAL_ID = "+material_id+" and REVISION_DATE=to_date('"+revision_date+"','MM/DD/YYYY')");
				if ( foreignMsds > 0 )
				{
					onLine = "N";
				}

				if ( test_number > 0 )
				{
					updateorinsert=true;
				}

				if ( updateorinsert )
				{
					msdsquery  = "update global.msds set \n";
					msdsquery += "VAPOR_PRESSURE_LOWER='"+vapor_pressure_lower+"',VAPOR_PRESSURE_UPPER='"+vapor_pressure_upper+"',VAPOR_PRESSURE_SOURCE='"+vapor_pressure_source+"', \n";
					msdsquery += "ALT_DATA_SOURCE='"+altdataSoucre+"',PERSONAL_PROTECTION='"+personnelprotection+"',REMARK='"+msdsremark+"',CONTENT='"+content+"',ON_LINE='"+onLine+"',SPECIFIC_GRAVITY="+(specific_gravity.length()==0?"null":specific_gravity)+",HEALTH='"+health+"',FLAMMABILITY='"+flammability+"', \n";
					msdsquery += "REACTIVITY='"+reactivity+"',SPECIFIC_HAZARD='"+specific_hazard+"',FLASH_POINT='"+flash_point+"',FLASH_POINT_UNIT='"+flash_point_unit+"',DENSITY="+(density.length()==0?"null":density)+", \n";
					msdsquery += "HMIS_HEALTH='"+hmishealth+"',HMIS_FLAMMABILITY='"+hmisflammability+"',HMIS_REACTIVITY='"+hmisreactivity+"', \n";
					msdsquery += "DENSITY_UNIT="+density_unit+",PHYSICAL_STATE="+physical_state+",VOC="+(voc.length()==0?"null":voc)+",VOC_LOWER="+(voc_lower.length()==0?"null":voc_lower)+",VOC_UPPER="+(voc_upper.length()==0?"null":voc_upper)+", \n";
					msdsquery += "VOC_UNIT="+voc_unit+", SOLIDS="+(solids.length()==0?"null":solids)+",SOLIDS_LOWER="+(solids_lower.length()==0?"null":solids_lower)+",SOLIDS_UPPER="+(solids_upper.length()==0?"null":solids_upper)+",SOLIDS_UNIT="+solids_unit+",SOLIDS_SOURCE="+solids_source+", \n";
					msdsquery += "VAPOR_PRESSURE_DETECT="+(vappressdet.length()==0?"null":"'"+vappressdet+"'")+",VAPOR_PRESSURE="+(vappress.length()==0?"null":vappress)+",VAPOR_PRESSURE_UNIT="+(vappressunit.length()==0?"null":"'"+vappressunit+"'")+",VAPOR_PRESSURE_TEMP="+(vappresstemp.length()==0?"null":vappresstemp)+",VAPOR_PRESSURE_TEMP_UNIT="+(vappresstempunt.length()==0?"null":"'"+vappresstempunt+"'")+", \n";
					msdsquery += "VOC_SOURCE='"+voc_source+"',VOC_LESS_H2O_EXEMPT='"+voc_less_h2o_exempt+"',VOC_LESS_H2O_EXEMPT_UNIT='"+voc_less_h2o_exempt_unit+"',VOC_LESS_H2O_EXEMPT_LOWER='"+voc_less_h2o_exempt_lower+"',VOC_LESS_H2O_EXEMPT_UPPER='"+voc_less_h2o_exempt_upper+"',VOC_LESS_H2O_EXEMPT_SOURCE='"+voc_less_h2o_exempt_source+"', \n";
					msdsquery += "VOC_LB_PER_SOLID_LB_UPPER='"+voc_lb_per_solid_lb_upper+"',VOC_LB_PER_SOLID_LB='"+voc_lb_per_solid_lb+"',VOC_LB_PER_SOLID_LB_LOWER='"+voc_lb_per_solid_lb_lower+"',VOC_LB_PER_SOLID_LB_SOURCE='"+voc_lb_per_solid_lb_source+"', \n";
					msdsquery += " REVIEWED_BY="+loginID+",REVIEW_DATE=sysdate where MATERIAL_ID = "+material_id+" and REVISION_DATE=to_date('"+revision_date+"','MM/DD/YYYY') \n"; //'"+revision_date+"'";
				}
				else
				{
					msdsquery  = "insert into global.msds (VAPOR_PRESSURE_LOWER, VAPOR_PRESSURE_UPPER,VAPOR_PRESSURE_SOURCE,ALT_DATA_SOURCE,PERSONAL_PROTECTION,REMARK,MATERIAL_ID,REVISION_DATE,CONTENT,SPECIFIC_GRAVITY,HEALTH,FLAMMABILITY,REACTIVITY,HMIS_HEALTH,HMIS_FLAMMABILITY,HMIS_REACTIVITY,SPECIFIC_HAZARD,FLASH_POINT,DENSITY,ON_LINE,DENSITY_UNIT, \n";
					msdsquery += "PHYSICAL_STATE,VOC_UNIT,VOC,VOC_LOWER,VOC_UPPER,FLASH_POINT_UNIT,VAPOR_PRESSURE_DETECT, VAPOR_PRESSURE, VAPOR_PRESSURE_UNIT, VAPOR_PRESSURE_TEMP, VAPOR_PRESSURE_TEMP_UNIT,REVIEWED_BY,REVIEW_DATE,\n";
					msdsquery += "VOC_SOURCE,VOC_LESS_H2O_EXEMPT,VOC_LESS_H2O_EXEMPT_UNIT,VOC_LESS_H2O_EXEMPT_LOWER,VOC_LESS_H2O_EXEMPT_UPPER,VOC_LESS_H2O_EXEMPT_SOURCE,VOC_LB_PER_SOLID_LB_UPPER,VOC_LB_PER_SOLID_LB,VOC_LB_PER_SOLID_LB_LOWER,VOC_LB_PER_SOLID_LB_SOURCE,SOLIDS, SOLIDS_LOWER, SOLIDS_UPPER,SOLIDS_UNIT,SOLIDS_SOURCE) values ";
					msdsquery += "('"+vapor_pressure_lower+"','"+vapor_pressure_upper+"','"+vapor_pressure_source+"','"+altdataSoucre+"','"+personnelprotection+"','"+msdsremark+"',"+material_id+",to_date('"+revision_date+"','MM/DD/YYYY'),'"+content+"',"+(specific_gravity.length()==0?"null":specific_gravity)+",'"+health+"','"+flammability+"','"+reactivity+"','"+hmishealth+"','"+hmisflammability+"','"+hmisreactivity+"','"+specific_hazard+"','"+flash_point+"', \n";
					msdsquery += " "+(density.length()==0?"null":density)+",'"+onLine+"',"+density_unit+","+physical_state+","+voc_unit+","+(voc.length()==0?"null":voc)+","+(voc_lower.length()==0?"null":voc_lower)+","+(voc_upper.length()==0?"null":voc_upper)+",'"+flash_point_unit+"', \n";
					msdsquery += " "+(vappressdet.length()==0?"null":"'"+vappressdet+"'")+","+(vappress.length()==0?"null":vappress)+","+(vappressunit.length()==0?"null":"'"+vappressunit+"'")+","+(vappresstemp.length()==0?"null":vappresstemp)+","+(vappresstempunt.length()==0?"null":"'"+vappresstempunt+"'")+", \n";
					msdsquery += " "+loginID+",sysdate, ";
					msdsquery += "'"+voc_source+"','"+voc_less_h2o_exempt+"','"+voc_less_h2o_exempt_unit+"','"+voc_less_h2o_exempt_lower+"','"+voc_less_h2o_exempt_upper+"','"+voc_less_h2o_exempt_source+"', \n";
					msdsquery += "'"+voc_lb_per_solid_lb_upper+"','"+voc_lb_per_solid_lb+"','"+voc_lb_per_solid_lb_lower+"','"+voc_lb_per_solid_lb_source+"', \n";
					msdsquery += ""+(solids.length()==0?"null":solids)+","+(solids_lower.length()==0?"null":solids_lower)+","+(solids_upper.length()==0?"null":solids_upper)+","+solids_unit+","+solids_source+") \n";
				}
			}

			if ( material_id.length() > 0 )
			{
				db.doUpdate( materialquery );
			}
			db.doUpdate( catadditemqcupdate );

			/*if ( "Process".equalsIgnoreCase( action ) )
     {
       //db.doUpdate(catadditemupdate);
     }*/

			if ( revision_date.length() == 10 )
			{
				db.doUpdate( msdsquery );
			}

			/*boolean updatecomposition=false;
     for ( int rem=0; rem < 25; rem++ )
     {
       compositionData=new Hashtable();
       compositionData= ( Hashtable ) ofCompositionData.elementAt( rem );

       //String cas_number=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( compositionData.get( "CAS_NUMBER" ).toString() );
       String msds_chemical_name=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( compositionData.get( "MSDS_CHEMICAL_NAME" ).toString() );

       //01-27-03
       if ( msds_chemical_name.trim().length() > 0 )
       {
         updatecomposition=true;
       }
     }

     if ( ( material_id.length() > 0 ) && ( revision_date.length() == 10 ) && ( updatecomposition ) )
     {
       db.doUpdate( "delete from global.composition where MATERIAL_ID = " + material_id + " and REVISION_DATE=to_date('" + revision_date + "','MM/DD/YYYY') " );

       for ( int rem=0; rem < 25; rem++ )
       {
         compositionData=new Hashtable();
         compositionData= ( Hashtable ) ofCompositionData.elementAt( rem );

         String cas_number=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( compositionData.get( "CAS_NUMBER" ).toString() );
         String msds_chemical_name=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( compositionData.get( "MSDS_CHEMICAL_NAME" ).toString() );
         if ( msds_chemical_name.length() > 160 )
         {
           msds_chemical_name=msds_chemical_name.substring( 0,159 );
         }

         //01-27-03
         if ( msds_chemical_name.trim().length() > 0 )
         {
           String percent=compositionData.get( "PERCENT" ).toString();
           String percent_lower=compositionData.get( "PERCENT_LOWER" ).toString();
           String percent_upper=compositionData.get( "PERCENT_UPPER" ).toString();
           String hazardous=compositionData.get( "HAZARDOUS" ).toString();
           String trade_secret=compositionData.get( "TRADE_SECRET" ).toString();
           String remark=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( compositionData.get( "REMARK" ).toString() );
           if ( remark.length() > 32 )
           {
             remark=remark.substring( 0,31 );
           }

           String updatecomp = "";
           updatecomp = "insert into global.composition (MATERIAL_ID,REVISION_DATE,PERCENT,PERCENT_LOWER,PERCENT_UPPER,HAZARDOUS,CAS_NUMBER,TRADE_SECRET,REMARK,MSDS_CHEMICAL_NAME,CHEMICAL_ID) values \n";
           updatecomp += "("+material_id+",to_date('"+revision_date+"','MM/DD/YYYY'),"+(percent.length()==0?"null":percent)+","+(percent_lower.length()==0?"null":percent_lower)+","+(percent_upper.length()==0?"null":percent_upper)+",'"+hazardous+"',"+(cas_number.length()==0?"null":"'"+cas_number+"'")+",'"+trade_secret+"','"+remark+"','"+msds_chemical_name+"','"+msds_chemical_name+"') \n";

           db.doUpdate( updatecomp );
         }
         else
         {
           continue;
         }
       }
     }
			 */
			msdsresult=true;
		}
		catch ( Exception e )
		{
			//e.printStackTrace();
			msdserrmsg+=e.getMessage();
			msdsresult=false;
		}

		Hashtable finalresu = new Hashtable();
		finalresu.put( "MSDS_UPDATE_FLAG",new Boolean( msdsresult ) );
		finalresu.put( "MSDS_ERROR_MSG",msdserrmsg );
		return finalresu;
		//return result;
	}

	public Hashtable updatecompositionData( Vector ofCompositionData,String material_id,String revision_date )
	{
		Hashtable compositionData=null;
		Vector new_commpdata=new Vector();
		boolean updatecomposition=false;
		boolean finalresult=true;
		boolean lineresult=true;
		String errormsg = "";

		for ( int rem=0; rem < 50; rem++ )
		{
			compositionData=new Hashtable();
			compositionData= ( Hashtable ) ofCompositionData.elementAt( rem );

			String msds_chemical_name=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( compositionData.get( "MSDS_CHEMICAL_NAME" ).toString() );

			if ( msds_chemical_name.trim().length() > 0 )
			{
				updatecomposition=true;
			}
		}

		if ( ( material_id.length() > 0 ) && ( revision_date.length() == 10 ) && ( updatecomposition ) )
		{
			try
			{
				db.doUpdate( "delete from global.composition where MATERIAL_ID = " + material_id + " and REVISION_DATE=to_date('" + revision_date + "','MM/DD/YYYY') " );
			}
			catch ( Exception ex )
			{
				//ex.printStackTrace();
				//finalresult=false;
			}

			for ( int rem=0; rem < 50; rem++ )
			{
				compositionData=new Hashtable();
				lineresult=true;
				compositionData= ( Hashtable ) ofCompositionData.elementAt( rem );

				String cas_number=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( compositionData.get( "CAS_NUMBER" ).toString() );
				String msds_chemical_name=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( compositionData.get( "MSDS_CHEMICAL_NAME" ).toString() );


				if ( msds_chemical_name.trim().length() > 0 )
				{
					/*if ( msds_chemical_name.length() > 160 )
	  {
		msds_chemical_name=msds_chemical_name.substring( 0,159 );
	  }*/

					msds_chemical_name=HelpObjs.findReplace( msds_chemical_name,"&#34;","\"" );
					msds_chemical_name=HelpObjs.findReplace( msds_chemical_name,"&#40;","(" );
					msds_chemical_name=HelpObjs.findReplace( msds_chemical_name,"&#41;",")" );

					String percent=compositionData.get( "PERCENT" ).toString();
					String percent_lower=compositionData.get( "PERCENT_LOWER" ).toString();
					String percent_upper=compositionData.get( "PERCENT_UPPER" ).toString();
					/*String hazardous=compositionData.get( "HAZARDOUS" ).toString();
      String trade_secret=compositionData.get( "TRADE_SECRET" ).toString();*/
					String remark=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( compositionData.get( "REMARK" ).toString() );
					/*if ( remark.length() > 32 )
      {
        remark=remark.substring( 0,31 );
      }*/

					String updatecomp="";
					updatecomp="insert into global.composition (MATERIAL_ID,REVISION_DATE,PERCENT,PERCENT_LOWER,PERCENT_UPPER,CAS_NUMBER,REMARK,MSDS_CHEMICAL_NAME,CHEMICAL_ID) values \n";
					updatecomp+="(" + material_id + ",to_date('" + revision_date + "','MM/DD/YYYY')," + ( percent.length() == 0 ? "null" : percent ) + "," +
					( percent_lower.length() == 0 ? "null" : percent_lower ) + "," + ( percent_upper.length() == 0 ? "null" : percent_upper ) + "," + ( cas_number.length() == 0 ? "null" : "'" + cas_number + "'" ) +
					",'" + remark + "','" + msds_chemical_name +
					"','" + msds_chemical_name + "') \n";

					try
					{
						db.doUpdate( updatecomp );
					}
					catch ( Exception ex1 )
					{
						finalresult=false;
						lineresult=false;
						ex1.printStackTrace();
						errormsg+=ex1.getMessage() + "<BR>";
					}

					if ( !lineresult )
					{
						compositionData.remove( "LINE_STATUS" );
						compositionData.put( "LINE_STATUS","N" );
					}
					else
					{
						compositionData.remove( "LINE_STATUS" );
						compositionData.put( "LINE_STATUS","" );
					}

					new_commpdata.addElement( compositionData );
				}
				else
				{
					continue;
				}
			}
		}
		Hashtable finalres = new Hashtable();
		finalres.put("COMP_DATA_VECTOR",new_commpdata);
		finalres.put("COMP_UPDATE_FLAG",new Boolean( finalresult ) );
		finalres.put("COMP_ERROR_MSG",errormsg);

		return finalres;
	}


	public boolean processrequest( Hashtable DataH,String loginID,String requestID, String lineItem, String itemID )
	{
		boolean result=true;
		PreparedStatement pstmt=null;
		//String mfg_id=DataH.get( "MANUFACTURER_ID" ).toString();
		//String manufacturer = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(DataH.get("MANUFACTURER").toString());
		String material_id=DataH.get( "MATERIAL_ID" ).toString();
		//String material_desc = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(DataH.get("MATERIAL_DESC").toString());
		String grade=DataH.get( "GRADE" ).toString();
		String mfg_shelf_life=DataH.get( "SHELF_LIFE" ).toString();
		//String mfg_storage_temp=DataH.get( "STORAGE_TEMP" ).toString();
		String shelf_life_basis=DataH.get( "SHELF_LIFE_BASIS" ).toString();
		String noofcomponents=DataH.get( "NO_OF_COMPONENTS" ).toString();
		String part_size=DataH.get( "PART_SIZE" ).toString();
		String size_units=DataH.get( "PART_SIZE_UNIT" ).toString();
		String pkg_style=DataH.get( "PACKAGING_STYLE" ).toString();
		String dimension=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( DataH.get( "DIMENSION" ).toString() );
		String net_wt=DataH.get( "NET_WEIGHT" ).toString();
		String net_weight_unit=DataH.get( "NET_WEIGHT_UNIT" ).toString();
		String mfg_part_no=BothHelpObjs.changeSingleQuotetoTwoSingleQuote(DataH.get( "MFG_PART_NO" ).toString());
		//String Comments = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(DataH.get("COMMENTS").toString());
		//String material_category=DataH.get( "CATEGORY" ).toString();
		//String sample_size=DataH.get( "SAMPLE_SIZE" ).toString();
		//String trade_name = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(DataH.get("TRADE_NAME").toString());
		String partid=DataH.get( "PART_ID" ).toString();
		String recerts=DataH.get( "RECERTS" ).toString();
		String sizevaries=DataH.get( "SIZE_VARIES" ).toString();
		String itemverified=DataH.get( "ITEM_VERIFIED" ).toString();
		String mintemp=DataH.get( "MIN_TEMP" ).toString();
		String maxtemp=DataH.get( "MAX_TEMP" ).toString();
		String tempunits=DataH.get( "TEMP_UNITS" ).toString();

		Connection con=db.getConnection();
		boolean updateorinsert=false;

		try
		{
			int isItemVerified = DbHelpers.countQuery(db,"select count(*) from global.part where item_id = "+itemID+" and PART_ID="+partid+" and ITEM_VERIFIED = 'Y'");

			if ( isItemVerified > 0 )
			{
				String partinfo = "select PART_SIZE,SIZE_UNIT,PKG_STYLE,NET_WT_UNIT,NET_WT,DIMENSION  from global.part b where b.PART_ID=" + partid + " and b.item_id = " + itemID + "";
				String pipart_size="";
				String pisize_unit="";
				String pipkg_style="";
				String pinet_wt_unit="";
				String pinet_wt="";
				String pidimension="";

				DBResultSet dbrs=null;
				ResultSet getpartinfors=null;
				try
				{
					dbrs=db.doQuery( partinfo );
					getpartinfors=dbrs.getResultSet();

					while ( getpartinfors.next() )
					{
						pipart_size=getpartinfors.getString( "PART_SIZE" ) == null ? "null" : getpartinfors.getString( "PART_SIZE" ).toString();
						pisize_unit=getpartinfors.getString( "SIZE_UNIT" ) == null ? "" : getpartinfors.getString( "SIZE_UNIT" ).toString();
						pipkg_style=getpartinfors.getString( "PKG_STYLE" ) == null ? "" : getpartinfors.getString( "PKG_STYLE" ).toString();
						pinet_wt_unit=getpartinfors.getString( "NET_WT_UNIT" ) == null ? "" : getpartinfors.getString( "NET_WT_UNIT" ).toString();
						pinet_wt=getpartinfors.getString( "NET_WT" ) == null ? "null" : getpartinfors.getString( "NET_WT" ).toString();
						pidimension=BothHelpObjs.changeSingleQuotetoTwoSingleQuote(getpartinfors.getString( "DIMENSION" ) == null ? "" : getpartinfors.getString( "DIMENSION" ).toString());
					}
				}
				catch ( Exception e )
				{
					//e.printStackTrace();
				}
				finally
				{
					if ( dbrs != null )
					{
						dbrs.close();
					}
				}

				String updatecatadditemqc = "update customer.catalog_add_item_qc a set ";
				updatecatadditemqc += "PART_SIZE="+pipart_size+",SIZE_UNIT='"+pisize_unit+"',PKG_STYLE='"+pipkg_style+"',NETWT_UNIT='"+pinet_wt_unit+"',NETWT="+pinet_wt+",DIMENSION='"+pidimension+"'";
				updatecatadditemqc+=" where REQUEST_ID = " + requestID + " and PART_ID = " + partid + " and LINE_ITEM = " + lineItem;
				pstmt=con.prepareStatement( updatecatadditemqc );
				//System.out.println(updatecatadditemqc);
				pstmt.executeQuery();

				String updatecatadditem = "update customer.catalog_add_item a set ";
				updatecatadditem += "PART_SIZE="+pipart_size+",SIZE_UNIT='"+pisize_unit+"',PKG_STYLE='"+pipkg_style+"',NETWT_UNIT='"+pinet_wt_unit+"',NETWT="+pinet_wt+",DIMENSION='"+pidimension+"'";
				updatecatadditem += " where REQUEST_ID = "+requestID+" and PART_ID = "+partid+" and LINE_ITEM = " + lineItem;
				pstmt = con.prepareStatement(updatecatadditem);
				//System.out.println(updatecatadditem);
				pstmt.executeQuery();

			}
			else
			{
				int test_number=DbHelpers.countQuery( db,"select count(*) from global.part where item_id = " + itemID + " and PART_ID=" + partid + "" );
				if ( test_number > 0 )
				{
					updateorinsert=true;
				}

				if ( updateorinsert )
				{
					//Update
					pstmt = con.prepareStatement("update global.part set PART_ID=?,MATERIAL_ID=?,GRADE=?,PKG_STYLE=?,PART_SIZE=?,SIZE_UNIT=?,SHELF_LIFE_DAYS=?,SHELF_LIFE_BASIS=?,MFG_PART_NO=?,NET_WT_UNIT=?,NET_WT=?,DIMENSION=?,RECERT=?,COMPONENTS_PER_ITEM=?,SIZE_VARIES=?,STOCKING_TYPE=?,ITEM_VERIFIED_BY=?,DATE_ITEM_VERIFIED=sysdate,ITEM_VERIFIED=?,MIN_TEMP=?, MAX_TEMP=?, TEMP_UNITS=? where ITEM_ID = ? and PART_ID = ?");
					pstmt.setInt(1, Integer.parseInt(partid));
					pstmt.setInt(2, Integer.parseInt(material_id));

					if (grade.trim().length() == 0)
					{
						pstmt.setNull(3, java.sql.Types.VARCHAR);
					}
					else
					{
						pstmt.setString(3,grade);
					}

					if ("None".equalsIgnoreCase(pkg_style) || pkg_style.length() == 0)
					{
						pstmt.setNull(4, java.sql.Types.VARCHAR);
					}
					else
					{
						pstmt.setString(4,pkg_style);
					}

					if (part_size.trim().length() == 0)
					{
						pstmt.setNull(5, java.sql.Types.INTEGER);
					}
					else
					{
						pstmt.setFloat(5, Float.parseFloat(part_size));
					}

					if ("None".equalsIgnoreCase(size_units) || size_units.length() == 0)
					{
						pstmt.setNull(6, java.sql.Types.VARCHAR);
					}
					else
					{
						pstmt.setString(6,size_units);
					}

					if (mfg_shelf_life.trim().length() == 0)
					{
						pstmt.setNull(7, java.sql.Types.INTEGER);
					}
					else
					{
						pstmt.setFloat(7, Float.parseFloat(mfg_shelf_life));
					}

					if ("None".equalsIgnoreCase(shelf_life_basis) || shelf_life_basis.length() == 0)
					{
						pstmt.setNull(8, java.sql.Types.VARCHAR);
					}
					else
					{
						pstmt.setString(8,shelf_life_basis);
					}

					pstmt.setString(9, mfg_part_no);
					//pstmt.setString(9, mfg_storage_temp);

					if ("None".equalsIgnoreCase(net_weight_unit) || net_weight_unit.length() == 0)
					{
						pstmt.setNull(10, java.sql.Types.VARCHAR);
					}
					else
					{
						pstmt.setString(10,net_weight_unit);
					}

					if (net_wt.trim().length() == 0)
					{
						pstmt.setNull(11, java.sql.Types.INTEGER);
					}
					else
					{
						pstmt.setFloat(11, Float.parseFloat(net_wt));
					}

					if (dimension.trim().length() == 0)
					{
						pstmt.setNull(12, java.sql.Types.VARCHAR);
					}
					else
					{
						pstmt.setString(12,dimension);
					}

					if ("N".equalsIgnoreCase(recerts))
					{
						pstmt.setNull(13, java.sql.Types.VARCHAR);
					}
					else
					{
						pstmt.setString(13,recerts);
					}

					if (noofcomponents.trim().length() == 0)
					{
						pstmt.setNull(14, java.sql.Types.INTEGER);
					}
					else
					{
						pstmt.setInt(14, Integer.parseInt(noofcomponents));
					}


					if ("N".equalsIgnoreCase(sizevaries))
					{
						pstmt.setNull(15, java.sql.Types.VARCHAR);
					}
					else
					{
						pstmt.setString(15,sizevaries);
					}

					pstmt.setString(16,"S");
					pstmt.setString(17,loginID);

					if ("N".equalsIgnoreCase(itemverified))
					{
						pstmt.setNull(18, java.sql.Types.VARCHAR);
					}
					else
					{
						pstmt.setString( 18,itemverified );
					}

					if (mintemp.trim().length() == 0)
					{
						pstmt.setNull(19, java.sql.Types.VARCHAR);
					}
					else
					{
						pstmt.setString(19,mintemp);
					}

					if (maxtemp.trim().length() == 0)
					{
						pstmt.setNull(20, java.sql.Types.VARCHAR);
					}
					else
					{
						pstmt.setString(20,maxtemp);
					}
					pstmt.setString(21,tempunits);

					pstmt.setInt( 22,Integer.parseInt( itemID ) );
					pstmt.setInt( 23,Integer.parseInt( partid ) );

					pstmt.executeQuery();
				}
				else
				{
					//Update
					pstmt = con.prepareStatement("insert into global.part (PART_ID,MATERIAL_ID,GRADE,PKG_STYLE,PART_SIZE,SIZE_UNIT,SHELF_LIFE_DAYS,SHELF_LIFE_BASIS,MFG_PART_NO,NET_WT_UNIT,NET_WT,DIMENSION,RECERT,COMPONENTS_PER_ITEM,SIZE_VARIES,ITEM_ID,STOCKING_TYPE,ITEM_VERIFIED_BY,DATE_ITEM_VERIFIED,ITEM_VERIFIED,MIN_TEMP, MAX_TEMP, TEMP_UNITS) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?) " );

					pstmt.setInt(1, Integer.parseInt(partid));
					pstmt.setInt(2, Integer.parseInt(material_id));

					if (grade.trim().length() == 0)
					{
						pstmt.setNull(3, java.sql.Types.VARCHAR);
					}
					else
					{
						pstmt.setString(3,grade);
					}

					if ("None".equalsIgnoreCase(pkg_style) || pkg_style.length() == 0)
					{
						pstmt.setNull(4, java.sql.Types.VARCHAR);
					}
					else
					{
						pstmt.setString(4,pkg_style);
					}

					if (part_size.trim().length() == 0)
					{
						pstmt.setNull(5, java.sql.Types.INTEGER);
					}
					else
					{
						pstmt.setFloat(5, Float.parseFloat(part_size));
					}

					if ("None".equalsIgnoreCase(size_units) || size_units.length() == 0)
					{
						pstmt.setNull(6, java.sql.Types.VARCHAR);
					}
					else
					{
						pstmt.setString(6,size_units);
					}

					if (mfg_shelf_life.trim().length() == 0)
					{
						pstmt.setNull(7, java.sql.Types.INTEGER);
					}
					else
					{
						pstmt.setFloat(7, Float.parseFloat(mfg_shelf_life));
					}

					if ("None".equalsIgnoreCase(shelf_life_basis) || shelf_life_basis.length() == 0)
					{
						pstmt.setNull(8, java.sql.Types.VARCHAR);
					}
					else
					{
						pstmt.setString(8,shelf_life_basis);
					}

					pstmt.setString(9, mfg_part_no);
					//pstmt.setString(9, mfg_storage_temp);

					if ("None".equalsIgnoreCase(net_weight_unit) || net_weight_unit.length() == 0)
					{
						pstmt.setNull(10, java.sql.Types.VARCHAR);
					}
					else
					{
						pstmt.setString(10,net_weight_unit);
					}

					if (net_wt.trim().length() == 0)
					{
						pstmt.setNull(11, java.sql.Types.INTEGER);
					}
					else
					{
						pstmt.setFloat(11, Float.parseFloat(net_wt));
					}

					if (dimension.trim().length() == 0)
					{
						pstmt.setNull(12, java.sql.Types.VARCHAR);
					}
					else
					{
						pstmt.setString(12,dimension);
					}

					if ("N".equalsIgnoreCase(recerts))
					{
						pstmt.setNull(13, java.sql.Types.VARCHAR);
					}
					else
					{
						pstmt.setString(13,recerts);
					}

					if (noofcomponents.trim().length() == 0)
					{
						pstmt.setNull(14, java.sql.Types.INTEGER);
					}
					else
					{
						pstmt.setInt(14, Integer.parseInt(noofcomponents));
					}


					if ("N".equalsIgnoreCase(sizevaries))
					{
						pstmt.setNull(15, java.sql.Types.VARCHAR);
					}
					else
					{
						pstmt.setString(15,sizevaries);
					}
					pstmt.setInt(16, Integer.parseInt(itemID));
					pstmt.setString(17,"S");

					pstmt.setString(18,loginID);

					if ("N".equalsIgnoreCase(itemverified))
					{
						pstmt.setNull(19, java.sql.Types.VARCHAR);
					}
					else
					{
						pstmt.setString(19,itemverified);
					}

					if (mintemp.trim().length() == 0)
					{
						pstmt.setNull(20, java.sql.Types.VARCHAR);
					}
					else
					{
						pstmt.setString(20,mintemp);
					}

					if (maxtemp.trim().length() == 0)
					{
						pstmt.setNull(21, java.sql.Types.VARCHAR);
					}
					else
					{
						pstmt.setString(21,maxtemp);
					}
					pstmt.setString(22,tempunits);

					pstmt.executeQuery();
				}
			}
			result=true;
		}
		catch ( Exception e )
		{
			System.err.println(pstmt);
			e.printStackTrace();

			cataddlog.error(e.getMessage());
			result=false;
		}
		finally
		{
			if ( pstmt != null )
			{
				try
				{
					pstmt.close();
				}
				catch ( SQLException se )
				{}
			}
		}

		return result;
	}

	public String getRequestStatus( String request_id1, String lineItem)
	{
		//String query = "select STATUS from catalog_add_item_qc where request_id = "+request_id1+"";
		String query="select QC_STATUS from customer.catalog_add_request_new where request_id = " + request_id1;
		//String query = "select QC_STATUS from customer.catalog_add_request_new where company_id = '"+CompanyId+"' and request_id = "+request_id1+"";
		DBResultSet dbrs=null;
		ResultSet rs=null;
		String result="";

		try
		{
			dbrs=db.doQuery( query );
			rs=dbrs.getResultSet();
			while ( rs.next() )
			{
				result=BothHelpObjs.makeBlankFromNull( rs.getString( "QC_STATUS" ) );
			}
		}
		catch ( Exception e )
		{
			//e.printStackTrace();
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
}