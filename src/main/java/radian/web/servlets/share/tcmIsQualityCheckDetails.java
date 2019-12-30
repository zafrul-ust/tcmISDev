package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.CatAddQc;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.share.servlets.NChemObj;

import com.tcmis.common.admin.beans.PersonnelBean;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * 11-04-02
 * loading formcheck.js to facilitate checking for integer values of specific gravity density etc. Operator entering NA in numeric fields which
 * was causing errors.
 *
 * Also added  MAXLENGTH FOR SOME FIELDS ON THE CLIENT SIDE
 *
 *
 * 11-05-02
 * Made Some changes to avoid the nullpointer Exception when looking for PART_ID. Added PART_ID to the hiddenElements and getting it back
 * checking for VVCATALOGSTUFF and doing the VVStuff if it is not already Done
 *
 * 11-12-02
 * Not updating ITEM_VERIFIED_BY in global.part if it is already verified once
 *
 * 11-19-02
 * Making the material Description Editable
 * 04-24-03 Decosing LOCKHEED company id when calling tcmisdbmodel
 * 06-16-03 - Code Clanup
 * 07-30-03 - Added functionality to add buyer notes for item ID
 * 09-25-03 - If the item is verified I ignore everything that is on the page.
 * 12-22-03 - Code Refactoring, moved vv_stuff to different class
 * 04-27-04 - Changes to store storage temperature in min max fields instead of one storage temp field
 * 11-10-04 - Added new VOC fields
 */

public class tcmIsQualityCheckDetails
{
	private ServerResourceBundle bundle = null;
	private TcmISDBModel db = null;
	private String Msds_details_Servlet = "";
	private String Msds_orig_Servlet = "";
	private String Msds_orig_Values_Servlet = "";
	private Vector part_size_unit = null;
	private Vector weight_unit = null;
	private Vector pgk_style = null;
	private Vector storage_temp = null;
	private Vector item_type = null;
	private Vector category;
	private Vector labelColorVector = null;
	private boolean completeSuccess = true ;
	private boolean intcmIsApplication = false;
	public tcmIsQualityCheckDetails(ServerResourceBundle b, TcmISDBModel d)
	{
		bundle = b;
		db = d;
	}

	public void doResult( HttpServletRequest request,HttpServletResponse response,HttpSession detailsession )
	throws ServletException,IOException
	{
		PrintWriter out=response.getWriter();
		response.setContentType( "text/html" );
		PersonnelBean personnelBean = (PersonnelBean) detailsession.getAttribute("personnelBean");
		Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
		if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
		{
			intcmIsApplication = true;
		}

		String personnelid=detailsession.getAttribute( "PERSONNELID" ) == null ? "" : detailsession.getAttribute( "PERSONNELID" ).toString();
		CatAddQc cataddqcObj=new CatAddQc( db );

		Msds_details_Servlet=bundle.getString( "QUALITY_CONTROL_DETAILS" );
		Msds_orig_Servlet=bundle.getString( "QUALITY_CONTROL" );
		Msds_orig_Values_Servlet=bundle.getString( "QUALITY_CONTROL_ORIGINAL" );

		String User_Action=null;
		String itemOrSpec="Item";
		String StatusofReq="";
		String CompanyId="";
		String request_id=null;
		String line_item=null;

		User_Action=request.getParameter( "Action" );
		if ( User_Action == null )
			User_Action="New";

		request_id=request.getParameter( "request_id" );
		if ( request_id == null )
			request_id="";

		line_item=request.getParameter( "line_item" );
		if ( line_item == null )
			line_item="";

		itemOrSpec=request.getParameter( "isTab" );
		if ( itemOrSpec == null )
			itemOrSpec="Item";

		CompanyId=request.getParameter( "Company" );
		if ( CompanyId == null )
			CompanyId=" ";

		String userpartnumber=request.getParameter( "userpartnumber" );
		if ( userpartnumber == null )
			userpartnumber="";
		userpartnumber=userpartnumber.trim();

		String company_selected=request.getParameter( "company" );
		if ( company_selected == null )
			company_selected="0";
		if ( company_selected.length() < 1 )
		{
			company_selected="0";
		}

		String itemID=request.getParameter( "item_id" );
		if ( itemID == null )
			itemID="";
		itemID = itemID.trim();

		String mngkitassingl=request.getParameter( "mngkitassingl" );
		if ( mngkitassingl == null )
			mngkitassingl="";
		mngkitassingl = mngkitassingl.trim();

		detailsession.setAttribute( "REQUEST_ID",request_id );
		detailsession.setAttribute( "LINE_ITEM",line_item );
		try
		{
			int isItemVerified=0;
			String donevvstuff=detailsession.getAttribute( "VVCATALOGSTUFF" ) == null ? "" : detailsession.getAttribute( "VVCATALOGSTUFF" ).toString();
			if ( !donevvstuff.equalsIgnoreCase( "Yes" ) )
			{
				detailsession.setAttribute( "CATEGORY",radian.web.vvHelpObj.getcategory(db) );
				detailsession.setAttribute( "PKG_STYLE",radian.web.vvHelpObj.getpkgstyle(db) );
				detailsession.setAttribute( "SIZE_UNIT",radian.web.vvHelpObj.getsizeunit(db) );
				detailsession.setAttribute( "STORAGE_TEMP",radian.web.vvHelpObj.getstoragetemp(db) );
				detailsession.setAttribute( "WEIGHT_UNIT",radian.web.vvHelpObj.getNetWtSizeUnit(db) );
				detailsession.setAttribute( "ITEM_TYPE",radian.web.vvHelpObj.getitemtype(db) );
				detailsession.setAttribute( "PHYSICAL_STATE",radian.web.vvHelpObj.getphysicalstate(db) );
				detailsession.setAttribute( "VOC_UNIT",radian.web.vvHelpObj.getvocunit(db) );
				detailsession.setAttribute( "DENSITY_UNIT",radian.web.vvHelpObj.getdensityunit(db) );
				detailsession.setAttribute( "SP_HAZARD",radian.web.vvHelpObj.getsphazard(db) );
				detailsession.setAttribute( "PPE",radian.web.vvHelpObj.getppe(db) );
				detailsession.setAttribute( "VAPOR_PRESSURE_DETECT",radian.web.vvHelpObj.getvapourpressdetect(db) );
				detailsession.setAttribute( "VAPOR_PRESSURE_UNIT",radian.web.vvHelpObj.getvapourpressunit(db) );
				detailsession.setAttribute( "VOC_SOURCE",radian.web.vvHelpObj.getvocsource(db) );


				Vector compnayids=new Vector();
				String companyArrayJs="";
				String CompanyName1="";

				compnayids=radian.web.vvHelpObj.getcatCompanyIds(db);
				companyArrayJs=radian.web.vvHelpObj.getCatalogJs( db,compnayids ).toString();

				int index=Integer.parseInt( company_selected );
				CompanyName1=compnayids.elementAt( index ).toString();
				detailsession.setAttribute( "COMPANY_IDS",compnayids );
				detailsession.setAttribute( "CATALOGS_JS",companyArrayJs );

				detailsession.setAttribute( "VVCATALOGSTUFF","Yes" );
			}
			detailsession.setAttribute( "LABEL_COLOR",radian.web.vvHelpObj.getLabelColors(db,request_id) );

			if (itemID.length() > 0)
			{
				try
				{
					isItemVerified=DbHelpers.countQuery( db,"select count(*) from global.part where item_id = " + itemID + " and ITEM_VERIFIED = 'Y'" );
				}
				catch ( Exception ex )
				{
					isItemVerified=0;
				}
			}

			if ( User_Action.equalsIgnoreCase( "Process" ) )
			{
				//Update the values
				part_size_unit= ( Vector ) detailsession.getAttribute( "SIZE_UNIT" );
				pgk_style= ( Vector ) detailsession.getAttribute( "PKG_STYLE" );
				storage_temp= ( Vector ) detailsession.getAttribute( "STORAGE_TEMP" );
				category= ( Vector ) detailsession.getAttribute( "CATEGORY" );
				weight_unit= ( Vector ) detailsession.getAttribute( "WEIGHT_UNIT" );
				item_type= ( Vector ) detailsession.getAttribute( "ITEM_TYPE" );
				labelColorVector = ( Vector ) detailsession.getAttribute( "LABEL_COLOR" );

				String caseQty=request.getParameter( "caseQty" );
				if ( caseQty == null )
					caseQty="";

				String itemstoragetemp=request.getParameter( "item_storage_temp" );
				if ( itemstoragetemp == null )
					itemstoragetemp="";

				String itemcategory=request.getParameter( "item_category" );
				if ( itemcategory == null )
					itemcategory="";

				String itemtype=request.getParameter( "item_type" );
				if ( itemtype == null )
					itemtype="MA";

				String samplesize=request.getParameter( "samplesize" );
				if ( samplesize == null )
					samplesize="N";

				Hashtable retrn_data= ( Hashtable ) detailsession.getAttribute( "COMPONENT_DATA" );
				Vector ComponentData1= ( Vector ) retrn_data.get( "DATA" );
				Vector synch_data=SynchServerData( request,ComponentData1 );

				int total1= ( ( Integer ) ( retrn_data.get( "TOTAL_NUMBER" ) ) ).intValue();
				Vector updateresults=UpdateDetails( synch_data,total1,request_id,line_item,"Process",caseQty );
				boolean processrequest=false;

                
                if ( itemID.length() > 0 )
				{
					processrequest=updateprocess( synch_data,personnelid,total1,request_id,line_item,itemID,itemstoragetemp,itemcategory,itemtype,CompanyId,samplesize,caseQty,isItemVerified,mngkitassingl );
				}

				if ( processrequest )
				{
					//Update Catalog Add item QC status "Passed QC"
					String updatecataddqc="update customer.catalog_add_request_new set CAT_PART_NO='" + userpartnumber +
					"' where REQUEST_ID = " + request_id + " ";

					String updatecataddqc2="update customer.catalog_add_request_new set QC_STATUS='Passed Item QC',QC_DATE=sysdate where qc_status in ('Pending Item QC','Pending Item','Pending QC','Pending [M]SDS Indexing') and REQUEST_ID = " + request_id + " ";

					String updatecatadditem="update customer.catalog_add_item_qc set ITEM_APPROVED_BY=" + personnelid +
					",item_id = "+itemID+
					",ITEM_APPROVED_ON=sysdate, item_qc_status='Approved' where REQUEST_ID = " + request_id + " and LINE_ITEM = " + line_item + " ";

					String updatecatadditemqc="update customer.catalog_add_item set ITEM_APPROVED_BY=" + personnelid +
					",item_id = "+itemID+",item_qc_status = 'Approved'"+
					",ITEM_APPROVED_ON=sysdate where REQUEST_ID = " + request_id + " and LINE_ITEM = " + line_item + " ";

					try
					{
                        if (userpartnumber != null && userpartnumber.length() > 0) {
                            db.doUpdate( updatecataddqc);
                        }
                        db.doUpdate( updatecatadditem );
						db.doUpdate( updatecatadditemqc );
                        String countQuery = "select count(*) from customer.catalog_add_item_qc where REQUEST_ID = " + request_id +
						" and  item_qc_status = 'Pending'";
						if(HelpObjs.countQuery(db, countQuery) == 0) {
                            //all lines are approved then continue with approving request
                            processrequest = approvalRequest(request_id,personnelid,CompanyId);
                            if (processrequest)
                                db.doUpdate( updatecataddqc2 );
                        }
					}
					catch ( Exception e )
					{
						e.printStackTrace();
						processrequest=false;
					}
				}

				retrn_data.remove( "ITEM_ID" );
				retrn_data.put( "ITEM_ID",itemID );

				retrn_data.remove( "CASE_QTY" );
				retrn_data.put( "CASE_QTY",caseQty );

				retrn_data.remove( "MANAGE_KITS_AS_SINGLE_UNIT" );
				retrn_data.put( "MANAGE_KITS_AS_SINGLE_UNIT",mngkitassingl );

				retrn_data.remove( "DATA" );
				retrn_data.put( "DATA",updateresults );

				Hashtable HeaderData=new Hashtable();
				HeaderData=cataddqcObj.getHeaderData( request_id, line_item );

				Hashtable Data=cataddqcObj.getHashtableData( request_id, line_item, CompanyId );
				detailsession.setAttribute( "COMPONENT_DATA",Data );

				StatusofReq=cataddqcObj.getRequestStatus( request_id, line_item );
				if ( processrequest )
				{
					buildDetails( out,request_id,line_item,Data,itemOrSpec,CompanyId,HeaderData,"Processed The request Successfully",StatusofReq );
				}
				else
				{
					buildDetails( out,request_id,line_item,Data,itemOrSpec,CompanyId,HeaderData,"An Error Occured when Procesing The request",StatusofReq );
				}

				retrn_data=null;
			}
			else if ( User_Action.equalsIgnoreCase( "Reverse" ) )
			{
				//Update the values and reverse it to MSDS
				part_size_unit= ( Vector ) detailsession.getAttribute( "SIZE_UNIT" );
				pgk_style= ( Vector ) detailsession.getAttribute( "PKG_STYLE" );
				storage_temp= ( Vector ) detailsession.getAttribute( "STORAGE_TEMP" );
				category= ( Vector ) detailsession.getAttribute( "CATEGORY" );
				weight_unit= ( Vector ) detailsession.getAttribute( "WEIGHT_UNIT" );
				item_type= ( Vector ) detailsession.getAttribute( "ITEM_TYPE" );
				labelColorVector = ( Vector ) detailsession.getAttribute( "LABEL_COLOR" );

				String itemstoragetemp=request.getParameter( "item_storage_temp" );
				if ( itemstoragetemp == null )
					itemstoragetemp="None";

				String itemcategory=request.getParameter( "item_category" );
				if ( itemcategory == null )
					itemcategory="None";

				String itemtype=request.getParameter( "item_type" );
				if ( itemtype == null )
					itemtype="MA";

				String caseQty=request.getParameter( "caseQty" );
				if ( caseQty == null )
					caseQty="";

				Hashtable retrn_data= ( Hashtable ) detailsession.getAttribute( "COMPONENT_DATA" );
				Vector ComponentData1= ( Vector ) retrn_data.get( "DATA" );
				Vector synch_data=SynchServerData( request,ComponentData1 );
				int total1= ( ( Integer ) ( retrn_data.get( "TOTAL_NUMBER" ) ) ).intValue();

				Vector updateresults=UpdateDetails( synch_data,total1,request_id,line_item,"Reverse",caseQty );

				boolean processrequest=false;
				try{
                    //Update Catalog Add item QC status
                    String updatecataddqc="update customer.catalog_add_item_qc set material_qc_status='Pending',item_qc_status = null,material_approved_on=null,material_approved_by=null where REQUEST_ID = " + request_id+" and line_item ="+line_item;
                    db.doUpdate( updatecataddqc );
                    //Update Catalog Add item status
                    updatecataddqc="update customer.catalog_add_item set material_qc_status='Pending',item_qc_status = null where REQUEST_ID = " + request_id+" and line_item ="+line_item;
                    db.doUpdate( updatecataddqc );
                    //catalog add request
				    updatecataddqc="update customer.catalog_add_request_new set approval_group_seq = 1,QC_STATUS='Pending Material QC',QC_DATE=null where REQUEST_ID = " + request_id;
                    db.doUpdate( updatecataddqc );
                    //delete catalog_add_approval
                    updatecataddqc = "delete from customer.catalog_add_approval where approval_role = 'Material QC' and request_id = "+request_id;
                    db.doUpdate( updatecataddqc );
                    processrequest=true;
				}catch ( Exception e ){
					e.printStackTrace();
					processrequest=false;
				}

				retrn_data.remove( "ITEM_ID" );
				retrn_data.put( "ITEM_ID",itemID );

				retrn_data.remove( "CASE_QTY" );
				retrn_data.put( "CASE_QTY",caseQty );

				retrn_data.remove( "MANAGE_KITS_AS_SINGLE_UNIT" );
				retrn_data.put( "MANAGE_KITS_AS_SINGLE_UNIT",mngkitassingl );

				retrn_data.remove( "DATA" );
				retrn_data.put( "DATA",updateresults );

				Hashtable HeaderData=new Hashtable();
				HeaderData=cataddqcObj.getHeaderData( request_id, line_item );

				Hashtable Data=cataddqcObj.getHashtableData( request_id, line_item,CompanyId );
				detailsession.setAttribute( "COMPONENT_DATA",Data );

				StatusofReq=cataddqcObj.getRequestStatus( request_id, line_item );
				if ( processrequest )
				{
					buildDetails( out,request_id,line_item,Data,itemOrSpec,CompanyId,HeaderData,"Reversed The request Successfully",StatusofReq );
				}
				else
				{
					buildDetails( out,request_id,line_item,Data,itemOrSpec,CompanyId,HeaderData,"An Error Occured when Procesing The request",StatusofReq );
				}

				retrn_data=null;

			}
			else if ( User_Action.equalsIgnoreCase( "save" ) )
			{
				part_size_unit= ( Vector ) detailsession.getAttribute( "SIZE_UNIT" );
				pgk_style= ( Vector ) detailsession.getAttribute( "PKG_STYLE" );
				storage_temp= ( Vector ) detailsession.getAttribute( "STORAGE_TEMP" );
				category= ( Vector ) detailsession.getAttribute( "CATEGORY" );
				weight_unit= ( Vector ) detailsession.getAttribute( "WEIGHT_UNIT" );
				item_type= ( Vector ) detailsession.getAttribute( "ITEM_TYPE" );
				labelColorVector = ( Vector ) detailsession.getAttribute( "LABEL_COLOR" );

				Hashtable retrn_data= ( Hashtable ) detailsession.getAttribute( "COMPONENT_DATA" );
				Vector ComponentData1= ( Vector ) retrn_data.get( "DATA" );
				Vector synch_data=SynchServerData( request,ComponentData1 );
				int total1= ( ( Integer ) ( retrn_data.get( "TOTAL_NUMBER" ) ) ).intValue();

				String caseQty=request.getParameter( "caseQty" );
				if ( caseQty == null )
					caseQty="";

				Vector updateresults=UpdateDetails( synch_data,total1,request_id,line_item,"save",caseQty );

				retrn_data.remove( "ITEM_ID" );
				retrn_data.put( "ITEM_ID",itemID );

				retrn_data.remove( "CASE_QTY" );
				retrn_data.put( "CASE_QTY",caseQty );

				retrn_data.remove( "MANAGE_KITS_AS_SINGLE_UNIT" );
				retrn_data.put( "MANAGE_KITS_AS_SINGLE_UNIT",mngkitassingl );

				retrn_data.remove( "DATA" );
				retrn_data.put( "DATA",updateresults );

				//Update Catalog Add Request New User Part NO
				String updateuserpartno="update customer.catalog_add_request_new set CAT_PART_NO='" + userpartnumber + "' where REQUEST_ID = " + request_id + " ";
				try
				{
                    db.doUpdate( updateuserpartno );
                }
				catch ( Exception e )
				{
					e.printStackTrace();
				}

				StatusofReq=cataddqcObj.getRequestStatus( request_id, line_item );
				Hashtable HeaderData=new Hashtable();
				HeaderData=cataddqcObj.getHeaderData( request_id, line_item);
				Hashtable allData=cataddqcObj.getHashtableData( request_id, line_item,CompanyId );

				if ( completeSuccess )
				{
					buildDetails( out,request_id,line_item,allData,itemOrSpec,CompanyId,HeaderData,"Saved the Data Successfully",StatusofReq );
				}
				else
				{
					buildDetails( out,request_id,line_item,allData,itemOrSpec,CompanyId,HeaderData,"An Error Occured when saving Data",StatusofReq );
				}

				retrn_data=null;
			}
			else if ( User_Action.equalsIgnoreCase( "details" ) )
			{
				part_size_unit=new Vector();
				pgk_style=new Vector();
				storage_temp=new Vector();
				category=new Vector();

				part_size_unit= ( Vector ) detailsession.getAttribute( "SIZE_UNIT" );
				pgk_style= ( Vector ) detailsession.getAttribute( "PKG_STYLE" );
				storage_temp= ( Vector ) detailsession.getAttribute( "STORAGE_TEMP" );
				category= ( Vector ) detailsession.getAttribute( "CATEGORY" );
				weight_unit= ( Vector ) detailsession.getAttribute( "WEIGHT_UNIT" );
				item_type= ( Vector ) detailsession.getAttribute( "ITEM_TYPE" );
				labelColorVector = ( Vector ) detailsession.getAttribute( "LABEL_COLOR" );

				Hashtable Data=new Hashtable();
				Hashtable HeaderData=new Hashtable();
				Data=cataddqcObj.getHashtableData( request_id, line_item,CompanyId );
				HeaderData=cataddqcObj.getHeaderData( request_id, line_item );
				detailsession.setAttribute( "COMPONENT_DATA",Data );
				StatusofReq=cataddqcObj.getRequestStatus( request_id, line_item );

				buildDetails( out,request_id,line_item,Data,itemOrSpec,CompanyId,HeaderData,"",StatusofReq );
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

    private boolean approvalRequest(String requestId, String logonid, String Company) {
        boolean result = true;
        Hashtable h=new Hashtable();
        h.put( "REQ_ID",requestId );
        h.put( "USER_ID",logonid );
        String[] vec=new String[2];

        /*
        //if client is seagate the QC otherwise TCM QC
        if ( "Seagate".equalsIgnoreCase( Company ) ){
            vec[0]="QC";
        }else{
            vec[0]="TCM QC";
        }
        */
        vec[0]="Item QC";
        vec[1]="";
        h.put( "ROLES_DATA",vec );

        RayTcmISDBModel dbObj=null;
        try{
            if ( "Raytheon".equalsIgnoreCase( Company ) ){
                dbObj=new RayTcmISDBModel( "Ray" );
            }else if ( "LOCKHEED".equalsIgnoreCase( Company ) ){
                dbObj=new RayTcmISDBModel( "LMCO" );
            }else if ( "SAUER_DANFOSS".equalsIgnoreCase( Company ) ){
                dbObj=new RayTcmISDBModel( "SD" );
            }else if ( "ARVIN_MERITOR".equalsIgnoreCase( Company ) ){
                dbObj=new RayTcmISDBModel( "AM" );
            }else if ( "QUALITY_ON_SITE".equalsIgnoreCase( Company ) ){
                dbObj=new RayTcmISDBModel( "QOS" );
            }else if ( "DETROIT_DIESEL".equalsIgnoreCase( Company ) ){
                dbObj=new RayTcmISDBModel( "DD" );
            }else if ( "HAAS".equalsIgnoreCase( Company ) ){
                dbObj=new RayTcmISDBModel( "TCM_OPS" );
            }else if ( "BOEING_CO".equalsIgnoreCase( Company ) ){
                dbObj=new RayTcmISDBModel( "BOEING_CO" );
            }else if ( "FINMECCANICA".equalsIgnoreCase( Company ) ){
                dbObj=new RayTcmISDBModel( "FNC" );
            }else{
                dbObj=new RayTcmISDBModel( Company );
            }

            NChemObj NcObj=new NChemObj( bundle,dbObj );
            String temp=NcObj.approveRequest( h );
            if ( "Error".equalsIgnoreCase( temp ) ){
                result=false;
            }
        }catch ( Exception e ){
            e.printStackTrace();
            result=false;
            //Send Email to Trong
            HelpObjs.sendMail( db,"Error in Cataloging Update From Item QC Web Page","Error in Calling method in NchemObj from QC Web Page for: " + requestId,86030,false );
        }finally{
            dbObj.close();
        }
        return result;
    } //end of method

    public void buildArrayOfNetWtRequired(PrintWriter out) {
		out.println( "<SCRIPT>\n" );
		out.println( "var netWtRequired = new Array();\n" );
        Vector tempV = radian.web.vvHelpObj.getSizeUnitNetWtRequired(db);
        for ( int count = 0; count < tempV.size(); count++ ) {
			out.println( "netWtRequired["+count+"] = \""+tempV.elementAt(count)+"\";\n" );
		}
		out.println( "</SCRIPT>\n" );

		return;
	}

    public void buildArrayofCom( int totalNum,PrintWriter out )
	{
		//StringBuffer Msgd2=new StringBuffer();

		out.println( "<SCRIPT>\n" );
		out.println( "var cmds = new Array();\n" );
		for ( int count=0; count < totalNum; count++ )
		{
			out.println( "cmds[" + count + "] = \"Component" + ( count + 1 ) + "\";\n" );
		}
		out.println( "</SCRIPT>\n" );

		return;
	}

    public void buildTabs( int totalNum,Vector DataT,PrintWriter out )
	{
		//StringBuffer Msgd3=new StringBuffer();
		int numofTabs=totalNum * 2 + 2;
		int usedWith=0;

		out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\" WIDTH=\"800\">\n" );
		out.println( "<TR>\n" );
		out.println( "<TD WIDTH=\"3\" HEIGHT=\"15\" CLASS=\"tab_buffer\"></TD>\n" );

		Hashtable hD1=new Hashtable();
		hD1= ( Hashtable ) DataT.elementAt( 0 );

		Boolean result= ( Boolean ) hD1.get( "UPDATE_FLAG" );
		if ( result == null )
		{
			result=new Boolean( false );
		}

		if ( result.booleanValue() )
		{
			out.println("<TD WIDTH=\"30\" HEIGHT=\"15\" ID=\"Component1_tabchild\" ALIGN=\"CENTER\" CLASS=\"tab_selected_tab\" onClick=\"togglePage('Component1', '');\"> 1\n" );
		}
		else
		{
			out.println("<TD WIDTH=\"30\" HEIGHT=\"15\" ID=\"Component1_tabchild\" ALIGN=\"CENTER\" CLASS=\"tab_nomatID_selected\" onClick=\"togglePage('Component1', '');\"> 1\n" );
		}

		out.println( "</TD>\n" );
		out.println( "<TD WIDTH=\"3\" HEIGHT=\"15\" CLASS=\"tab_buffer\"></TD>\n" );

		usedWith+=36;

		if ( totalNum > 1 )
		{
			for ( int count=1; count < totalNum; count++ )
			{
				hD1=new Hashtable();
				hD1= ( Hashtable ) DataT.elementAt( count );

				result= ( Boolean ) hD1.get( "UPDATE_FLAG" );
				if ( result == null )
				{
					result=new Boolean( false );
				}

				if ( result.booleanValue() )
				{
					out.println( "<TD WIDTH=\"30\" HEIGHT=\"15\" ID=\"Component" + ( count + 1 ) + "_tabchild\" ALIGN=\"CENTER\" CLASS=\"tab_tab\" onClick=\"togglePage('Component" + ( count + 1 ) + "', '');\"> " + ( count + 1 ) + "\n" );
				}
				else
				{
					out.println( "<TD WIDTH=\"30\" HEIGHT=\"15\" ID=\"Component" + ( count + 1 ) + "_tabchild\" ALIGN=\"CENTER\" CLASS=\"tab_nomatID\" onClick=\"togglePage('Component" + ( count + 1 ) + "', '');\"> " + ( count + 1 ) + "\n" );
				}
				out.println( "</TD>\n" );
				out.println( "<TD WIDTH=\"3\" HEIGHT=\"15\" CLASS=\"tab_buffer\"></TD>\n" );
				usedWith+=33;
			}
		}

		out.println( "<TD WIDTH=\"" + ( 800 - usedWith ) + "\" HEIGHT=\"15\" CLASS=\"tab_buffer\">&nbsp;</TD>\n" );
		out.println( "</TR>\n" );
		out.println( "<TR><TD CLASS=\"tab\" COLSPAN=" + numofTabs + " HEIGHT=\"2\"></TD></TR>\n" );
		out.println( "</TABLE>\n" );

		return;
	}

	public void buildDetails( PrintWriter out,String requestID, String lineItem, Hashtable DataH,
			String ItemorSpec,String CompanyId1,Hashtable HeaderData1,String Message,String statusofReq )
	{
		int total= ( ( Integer ) ( DataH.get( "TOTAL_NUMBER" ) ) ).intValue();
		Vector ComponentData= ( Vector ) DataH.get( "DATA" );
		//StringBuffer Msgd1=new StringBuffer();
		out.println( radian.web.HTMLHelpObj.printHTMLHeader( "" + CompanyId1 + " Cataloging","cataddqcdetails.js",intcmIsApplication ) );
		out.println( "<SCRIPT SRC=\"/clientscripts/FormChek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );

		out.println( "</HEAD>\n" );
		if ( "Processed The request Successfully".equalsIgnoreCase( Message ) || "Reversed The request Successfully".equalsIgnoreCase( Message ) || statusofReq.equalsIgnoreCase("Passed QC") )
		{
			out.println( "<BODY TEXT=\"#000000\" LINK=\"#FFFFFF\" ALINK=\"\" VLINK=\"#FFFF66\" onload=\"resetApplicationTimer()\">\n" );
		}
		else if ( total == 0 )
		{
			out.println( "<BODY TEXT=\"#000000\" LINK=\"#FFFFFF\" ALINK=\"\" VLINK=\"#FFFF66\" onload=\"resetApplicationTimer()\">\n" );
		}
		else
		{
			out.println( "<BODY TEXT=\"#000000\" LINK=\"#FFFFFF\" ALINK=\"\" VLINK=\"#FFFF66\" onload =\"initTabs()\">\n" );
		}

		buildArrayofCom( total,out );
        buildArrayOfNetWtRequired(out);
        out.println("<div class=\"topNavigation\" id=\"topNavigation\">\n");
		out.println( "<TABLE BORDER=0 WIDTH=100% CELLSPACING=\"0\" CELLPADDING=\"0\">\n" );
		out.println( "<TR VALIGN=\"TOP\">\n" );
		out.println( "<TD WIDTH=\"200\">\n" );
		out.println( "<img src=\"/images/tcmtitlegif.gif\" border=0 align=\"left\">\n" );
		out.println( "</TD>\n" );
		out.println( "<TD WIDTH=\"525\" ALIGN=\"right\">\n" );
		out.println( "<img src=\"/images/tcmistcmis32.gif\" border=0 align=\"right\">\n" );
		out.println( "</TD>\n" );
		out.println( "</TR>\n" );
		out.println( "</Table>\n" );

		out.println( "<TABLE WIDTH=\"100%\" BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\">\n<TR>\n" );
		out.println( "<TD ALIGN=\"LEFT\" BGCOLOR=#000066 VALIGN=\"MIDDLE\" WIDTH=\"5%\" HEIGHT=\"20\">\n" );
		out.println( "<B><FONT FACE=\"Arial\" COLOR=\"#FFFFFF\">" );
		out.println( "<A HREF=\"" + Msds_details_Servlet + "isTab=Item&Action=details&request_id=" + requestID + "&line_item=" + lineItem + "&Company=" + CompanyId1 + "\" STYLE=\"color:#FFFFFF\">Item</A></FONT></B>\n" );
		out.println( "</TD>\n" );
		out.println( "<TD ALIGN=\"LEFT\" BGCOLOR=#000066 VALIGN=\"MIDDLE\" WIDTH=\"5%\" HEIGHT=\"20\">\n" );
		out.println( "<B><FONT FACE=\"Arial\" COLOR=\"#FFFFFF\">" );
		out.println( "Spec</A></FONT></B>\n" );
		out.println( "</TD>\n" );
		out.println( "<TD WIDTH=\"70%\" ALIGN=\"RIGHT\" HEIGHT=\"20\" CLASS=\"headingr\">\n" );
		out.println( "<A HREF=\"/tcmIS/Catalog?Action=active\" STYLE=\"color:#FFFFFF\">Home</A>\n" );
		out.println( "</TD>\n" );
		out.println( "<TD ALIGN=\"RIGHT\" BGCOLOR=#000066 VALIGN=\"MIDDLE\" WIDTH=\"20%\" HEIGHT=\"20\">\n" );
		out.println( "<B><FONT FACE=\"Arial\" COLOR=\"#FFFFFF\"><I>tcm</I>IS Catalog Add QC</FONT></B>\n" );
		out.println( "</TD>\n" );
		out.println( "</TR>\n" );
		out.println( "</TABLE>\n" );
		out.println( "</div>\n" );

		if (intcmIsApplication)
		{
			out.println( "<TABLE WIDTH=\"100%\" BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\">\n<TR>\n" );
			out.println( "<TD BGCOLOR=#000066 VALIGN=\"MIDDLE\" WIDTH=\"95%\" HEIGHT=\"20\">\n" );
			out.println( "</TD>\n</TR>\n</TABLE>\n" );
		}

		out.println( "<FORM METHOD=\"POST\" id=\"MainForm\" name=\"MainForm\" onSubmit=\"return SubmitOnlyOnce()\" action=\"" + Msds_details_Servlet + "\" >\n" );
		out.println( "<TABLE BORDER=0 WIDTH=100% CELLSPACING=\"0\" CELLPADDING=\"5\">\n" );

		if ( Message.length() > 1 )
		{
			out.println( "<TR>\n<TD WIDTH=\"100%\" COLSPAN=\"7\" ALIGN=\"CENTER\">\n" );
			out.println( "<FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>" + Message + "</B></FONT>&nbsp;\n" );
			out.println( "</TD>\n</TR>\n" );
		}

		//QCStatus
		out.println( "<TR>\n<TD WIDTH=\"70%\" ALIGN=\"LEFT\">\n" );
		out.println( "<FONT SIZE=\"2\" FACE=\"Arial\" COLOR=\"#000000\"><B>" + ItemorSpec + " Detail QC</B></FONT>&nbsp;\n" );
		out.println( "</TD>\n" );

		if ( ! ( "Processed The request Successfully".equalsIgnoreCase( Message ) || "Reversed The request Successfully".equalsIgnoreCase( Message ) || statusofReq.equalsIgnoreCase("Passed QC") ) )
		{
			out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\"><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"Save\" id=\"Save\" name=\"Save\" onclick= \"return actionValue(name,this)\"></TD>\n" );
			out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\"><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"Approve\" id=\"Process\" name=\"Process\" onclick= \"return actionValue(name,this)\"></TD>\n" );
			out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\"><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"Reverse\" id=\"Reverse\" name=\"Reverse\" onclick= \"return actionValue(name,this)\"></TD>\n" );
		}
		out.println( "<TD WIDTH=\"8%\" ALIGN=\"RIGHT\"><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"button\" id=\"searchmfgidlike\" name=\"searchmfgidlike\" value=\"View Request\" OnClick=\"ShowOriginal(this.form)\"></TD>\n" );

		out.println( "</TR>\n" );
		out.println( "</TABLE>\n" );

		//HEADER Information
		out.println( "<TABLE BORDER=0 WIDTH=100% CELLSPACING=\"0\" CELLPADDING=\"0\">\n" );
		out.println( "<TR>\n<TD HEIGHT=\"20\" CLASS=\"Inbluer\" WIDTH=\"10%\"><B>Request Id:&nbsp;&nbsp;</B></TD>\n" );
		out.println( "<TD HEIGHT=\"20\" WIDTH=\"15%\" CLASS=\"Inbluel\">" );
		out.println( HeaderData1.get( "REQUEST_ID" )  + " - " + lineItem);
		out.println( "</TD>\n" );

		out.println( "<TD HEIGHT=\"20\" CLASS=\"Inbluer\" WIDTH=\"10%\"><B>Requestor Phone:&nbsp;&nbsp;</B></TD>\n" );
		out.println( "<TD HEIGHT=\"20\" WIDTH=\"15%\" CLASS=\"Inbluel\">" );
		out.println( HeaderData1.get( "REQUESTOR_PHONE" ) );
		out.println( "</TD>\n" );

		out.println( "<TD HEIGHT=\"20\" CLASS=\"Inbluer\" WIDTH=\"10%\"><B>Company:&nbsp;&nbsp;</B></TD>\n" );
		out.println( "<TD HEIGHT=\"20\" WIDTH=\"10%\" CLASS=\"Inbluel\">" );
		out.println( HeaderData1.get( "COMPANY" ) );
		out.println( "</TD>\n" );

		out.println( "<TD HEIGHT=\"20\" CLASS=\"Inbluer\" WIDTH=\"15%\"><B>Date Submitted:&nbsp;&nbsp;</B></TD>\n" );
		out.println( "<TD HEIGHT=\"20\" WIDTH=\"15%\" CLASS=\"Inbluel\">" );
		out.println( HeaderData1.get( "DATE_SUBMITTED" ) );
		out.println( "</TD>\n</TR>\n" );

		out.println( "<TR>\n<TD HEIGHT=\"20\" CLASS=\"Inwhiter\" WIDTH=\"10%\"><B>Requestor:&nbsp;&nbsp;</B></TD>\n" );
		out.println( "<TD HEIGHT=\"20\" WIDTH=\"15%\" CLASS=\"Inwhitel\">" );
		out.println( HeaderData1.get( "REQUESTOR" ) );
		out.println( "</TD>\n" );

		out.println( "<TD HEIGHT=\"20\" CLASS=\"Inwhiter\" WIDTH=\"10%\"><B>Requestor email:&nbsp;&nbsp;</B></TD>\n" );
		out.println( "<TD HEIGHT=\"20\" WIDTH=\"15%\" CLASS=\"Inwhitel\">" );
		out.println( HeaderData1.get( "REQUESTOR_EMAIL" ) );
		out.println( "</TD>\n" );

		out.println( "<TD HEIGHT=\"20\" CLASS=\"Inwhiter\" WIDTH=\"10%\"><B>Catalog Id:&nbsp;&nbsp;</B></TD>\n" );
		out.println( "<TD HEIGHT=\"20\" WIDTH=\"10%\" CLASS=\"Inwhitel\">" );
		out.println( HeaderData1.get( "CATALOGID" ) );
		out.println( "</TD>\n" );

		out.println( "<TD HEIGHT=\"20\" CLASS=\"Inwhiter\" WIDTH=\"15%\"><B>Evaluation:&nbsp;&nbsp;</B></TD>\n" );
		out.println( "<TD HEIGHT=\"20\" WIDTH=\"15%\" CLASS=\"Inwhitel\">" );
		out.println( HeaderData1.get( "EVALUATION" ) );
		out.println( "</TD>\n</TR>\n" );

		//User Part Number
		out.println( "<TR>\n<TD HEIGHT=\"20\" CLASS=\"Inbluer\" WIDTH=\"15%\"><B>User Part Number:&nbsp;&nbsp;</B></TD>\n" );
		out.println( "<TD HEIGHT=\"20\" WIDTH=\"85%\" CLASS=\"Inbluel\" COLSPAN=\"5\">" );
		out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" id=\"userpartnumber\" name=\"userpartnumber\" value=\"" );
		out.println( HeaderData1.get( "CAT_PART_NO" ).toString().trim() );
		out.println( "\" SIZE=\"20\" MAXLENGTH=\"30\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"showExistingItemsfor\" name=\"showExistingItemsfor\" value=\"...\" OnClick=\"showExistingItems()\"></TD>\n" );
		out.println( "<TD HEIGHT=\"20\" CLASS=\"Inbluer\" WIDTH=\"15%\"><B>Starting View:&nbsp;&nbsp;</B></TD>\n" );
		out.println( "<TD HEIGHT=\"20\" WIDTH=\"15%\" CLASS=\"Inbluel\">" );
		out.println( HeaderData1.get( "STARTING_VIEW" ) );
		out.println( "</TD>\n</TR>\n" );

		out.println( "</TABLE>\n" );

		//Item ID
		out.println( "<TABLE BORDER=0 WIDTH=100% CELLSPACING=\"0\" CELLPADDING=\"0\">\n" );
		out.println( "<TR><TD COLSPAN=\"10\" CLASS=\"Inwhitel\" HEIGHT=\"10\"><B>Item Data:</B></TD></TR>\n" );

		String itemID = DataH.get( "ITEM_ID" ).toString().trim();
		String veryfieditem = "";
		int isItemVerified = 0;
		String itemTypeSelected = "MA";

		if (itemID.length() > 0)
		{
			try
			{
				isItemVerified=DbHelpers.countQuery( db,"select count(*) from global.part where item_id = " + itemID + " and ITEM_VERIFIED = 'Y'" );
			}
			catch ( Exception ex )
			{
				isItemVerified=0;
			}

			DBResultSet dbrs = null;
			ResultSet rs = null;
			String itemType = "";
			try
			{
				String query="select item_type from item where item_id = " + itemID + "  ";

				dbrs=db.doQuery( query );
				rs=dbrs.getResultSet();
				while ( rs.next() )
				{
					itemType= ( rs.getString( "item_type" ) == null ? "" : rs.getString( "item_type" ) );
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
			if (itemType !=null && itemType.length() > 0)
			{
				itemTypeSelected =itemType;
			}
		}

		if (isItemVerified > 0) {veryfieditem = "Y";}

		out.println( "<TR>\n<TD CLASS=\"Inbluer\" WIDTH=\"10%\"><B>Item ID: </B></TD><TD WIDTH=\"10%\" CLASS=\"Inbluel\">" );
		out.println( "<INPUT type=\"text\" CLASS=\"INVISIBLEHEADBLUE\" id=\"item_id\" name=\"item_id\" value=\"" );
		out.println( itemID );
		out.println( "\" SIZE=\"10\" readonly><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchitemidlike\" name=\"searchitemidlike\" value=\"...\" OnClick=\"getitemID(this.form)\"></TD>\n" );

		// Item Mfg Storage Temperature
		out.println("<INPUT type=\"hidden\" id=\"item_storage_temp\" name=\"item_storage_temp\" value=\"None\"><INPUT type=\"hidden\" name=\"itemisVerified\" id=\"itemisVerified\" value=\""+veryfieditem+"\">" );

		// Item Category
		out.println( "<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Category: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inbluel\">" );
		out.println( "<SELECT CLASS=\"HEADER\" ID=\"item_category\" NAME=\"item_category\" size=\"1\">\n" );
		out.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
		out.println( radian.web.HTMLHelpObj.getDropDown( category,"" ) );
		out.println( "</SELECT>\n" );
		out.println( "</TD>\n" );

		// Item Type
		out.println( "<TD WIDTH=\"5%\" CLASS=\"Inbluer\"><B>Type: </B></TD><TD WIDTH=\"10%\" CLASS=\"Inbluel\">" );
		out.println( "<SELECT CLASS=\"HEADER\" ID=\"item_type\" NAME=\"item_type\" size=\"1\">\n" );
		//out.println("<OPTION VALUE=\"None\">Please Select</OPTION>\n");
		out.println( radian.web.HTMLHelpObj.getDropDown( item_type,itemTypeSelected ) );
		out.println( "</SELECT>\n" );
		out.println( "</TD>\n" );

		//CASE_QTY
		out.println( "<TD CLASS=\"Inbluer\" WIDTH=\"10%\"><B>Case Qty: </B></TD><TD WIDTH=\"10%\" CLASS=\"Inbluel\">" );
		out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" id=\"caseQty\" name=\"caseQty\" value=\"" );
		out.println( DataH.get( "CASE_QTY" ) );
		out.println( "\" SIZE=\"3\"></TD>\n" );

		//Sample Size
		out.println( "<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Sample Size: </B></TD><TD WIDTH=\"10%\" CLASS=\"Inbluel\">" );
		out.println( "<SELECT CLASS=\"HEADER\" ID=\"samplesize\" NAME=\"samplesize\" size=\"1\">\n" );
		out.println( "<option selected value=\"N\">N</option>\n" );
		out.println( "<option value=\"Y\">Y</option>\n" );
		out.println( "</SELECT>\n" );
		out.println( "</TD>\n" );
		out.println( "</TR>\n" );

		//Seperable Kit Item
		out.println( "<TR>\n" );
		out.println( "<TD HEIGHT=\"20\" WIDTH=\"10%\" CLASS=\"Inwhiter\">" );
		out.println( "<B>Inseparable Kit:</B>" );
		out.println( "</TD>\n" );
		out.println( "<TD HEIGHT=\"20\" COLSPAN=\"9\" CLASS=\"Inwhitel\">\n" );
		String showneedingprint = DataH.get( "MANAGE_KITS_AS_SINGLE_UNIT" ).toString();
		out.println( "<INPUT type=\"checkbox\" id=\"mngkitassingl\" name=\"mngkitassingl\" value=\"Y\" " + ( showneedingprint.equalsIgnoreCase( "Y" ) ? "checked" : "" ) + ">\n" );
		out.println( "</TD>\n" );
		out.println( "</TR>\n" );

		//Item Notes
		out.println( "<TR>\n" );
		out.println( "<TD HEIGHT=\"20\" WIDTH=\"10%\" CLASS=\"Inwhitel\">" );
		out.println( "<B>Item Notes: </B> <A HREF=\"javascript:getItemNotes('1')\" STYLE=\"color:#0000ff;cursor:hand\"><IMG ID=\"itemnotesimage1\" src=\"/images/plus.jpg\" alt=\"Item Notes\"></A><BR><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"additemnotes\" name='additemnotes' OnClick=editItemNotes('1') value=\"Add\">" );
		out.println( "</TD>\n" );

		out.println( "<TD HEIGHT=\"20\" COLSPAN=\"9\" CLASS=\"Inwhiter\" ID=\"row4detaildivrow11\"></TD>\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"itemnotestatus1\" NAME=\"itemnotestatus1\" VALUE=\"No\"></TR>\n" );

		out.println( "</TABLE>\n" );

		out.println( "<INPUT TYPE=\"hidden\" ID=\"Action\" NAME=\"Action\" VALUE=\"Details\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"request_id\" NAME=\"request_id\" VALUE=\"" + requestID + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"line_item\" NAME=\"line_item\" VALUE=\"" + lineItem + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"totalComps\" NAME=\"totalComps\" VALUE=\"" + total + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"Company\" NAME=\"Company\" VALUE=\"" + CompanyId1 + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"CompanyId\" NAME=\"CompanyId\" VALUE=\"" + CompanyId1 + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"catalogId\" NAME=\"catalogId\" VALUE=\"" + HeaderData1.get( "CATALOGID" ) + "\">\n" );

		if ( "Processed The request Successfully".equalsIgnoreCase( Message ) || statusofReq.equalsIgnoreCase("Passed QC") )
		{
			out.println( "<br><br>This Request Has passed QC stage.\n<br><br>" );
		}
		else if ( "Reversed The request Successfully".equalsIgnoreCase( Message ) )
		{
			out.println( "<br><br>This Request is at pending MSDS stage.\n<br><br>" );
		}
		else if ( total == 0 )
		{
			out.println( "<br><br>No Records Found for this Request Id.\n<br><br>" );
		}
		else
		{
			for ( int i=0; i < total; i++ )
			{
				Hashtable hD=new Hashtable();
				hD= ( Hashtable ) ComponentData.elementAt( i );
				buildHiddenElements( hD,i,out );
			}

			out.println( "</FORM>\n" );

			buildTabs( total,ComponentData,out );

			for ( int i=0; i < total; i++ )
			{
				Hashtable hD=new Hashtable();
				hD= ( Hashtable ) ComponentData.elementAt( i );

				out.println( "<DIV ID=\"Component" + ( i + 1 ) + "\" STYLE=\"display: none;\">\n" );
				out.println( "<FORM METHOD=\"POST\" id=\"ComponentForm" + ( i + 1 ) + "\"  name=\"ComponentForm" + ( i + 1 ) + "\" action=\"" + Msds_details_Servlet + "Session=Update\" onsubmit =\"return CheckValues(this)\">\n" );
				buildDivs( hD,i,out );
				out.println( "</FORM>\n" );
				out.println( "</DIV>\n" );
			}
		}

		//if (!intcmIsApplication)
		{
			out.println( "<TABLE WIDTH=\"100%\" BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\">\n<TR>\n" );
			out.println( "<TD BGCOLOR=#000066 VALIGN=\"MIDDLE\" WIDTH=\"95%\" HEIGHT=\"20\">\n" );
			out.println( "</TD>\n</TR>\n</TABLE>\n" );
		}
		out.println( "</BODY>\n</HTML>\n" );

		//out.println( Msgd1 );
	}

	public void buildHiddenElements( Hashtable DataH,int TabNumber,PrintWriter out )
	{
		//StringBuffer MsgHE=new StringBuffer();
		out.println( "<INPUT TYPE=\"hidden\" ID=\"partid" + TabNumber + "\" NAME=\"partid" + TabNumber + "\" VALUE=\"" + DataH.get( "PART_ID" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"mfg_id" + TabNumber + "\" NAME=\"mfg_id" + TabNumber + "\" VALUE=\"" + DataH.get( "MANUFACTURER_ID" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"manufacturer" + TabNumber + "\" NAME=\"manufacturer" + TabNumber + "\" VALUE=\"" + DataH.get( "MANUFACTURER" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"material_id" + TabNumber + "\" NAME=\"material_id" + TabNumber + "\" VALUE=\"" + DataH.get( "MATERIAL_ID" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"material_desc" + TabNumber + "\" NAME=\"material_desc" + TabNumber + "\" VALUE=\"" + DataH.get( "MATERIAL_DESC" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"grade" + TabNumber + "\" NAME=\"grade" + TabNumber + "\" VALUE=\"" + DataH.get( "GRADE" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"mfg_shelf_life" + TabNumber + "\" NAME=\"mfg_shelf_life" + TabNumber + "\" VALUE=\"" + DataH.get( "SHELF_LIFE" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"mfg_storage_temp" + TabNumber + "\" NAME=\"mfg_storage_temp" + TabNumber + "\" VALUE=\"" + DataH.get( "STORAGE_TEMP" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"shelf_life_basis" + TabNumber + "\" NAME=\"shelf_life_basis" + TabNumber + "\" VALUE=\"" + DataH.get( "SHELF_LIFE_BASIS" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"noofcomponents" + TabNumber + "\" NAME=\"noofcomponents" + TabNumber + "\" VALUE=\"" + DataH.get( "NO_OF_COMPONENTS" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"part_size" + TabNumber + "\" NAME=\"part_size" + TabNumber + "\" VALUE=\"" + DataH.get( "PART_SIZE" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"size_units" + TabNumber + "\" NAME=\"size_units" + TabNumber + "\" VALUE=\"" + DataH.get( "PART_SIZE_UNIT" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"pkg_style" + TabNumber + "\" NAME=\"pkg_style" + TabNumber + "\" VALUE=\"" + DataH.get( "PACKAGING_STYLE" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"dimension" + TabNumber + "\" NAME=\"dimension" + TabNumber + "\" VALUE=\"" + DataH.get( "DIMENSION" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"net_wt" + TabNumber + "\" NAME=\"net_wt" + TabNumber + "\" VALUE=\"" + DataH.get( "NET_WEIGHT" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"net_weight_unit" + TabNumber + "\" NAME=\"net_weight_unit" + TabNumber + "\" VALUE=\"" + DataH.get( "NET_WEIGHT_UNIT" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"mfg_part_no" + TabNumber + "\" NAME=\"mfg_part_no" + TabNumber + "\" VALUE=\"" + DataH.get( "MFG_PART_NO" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"Comments" + TabNumber + "\" NAME=\"Comments" + TabNumber + "\" VALUE=\"" + DataH.get( "COMMENTS" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"material_category" + TabNumber + "\" VNAME=\"material_category" + TabNumber + "\" VALUE=\"" + DataH.get( "CATEGORY" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"sample_size" + TabNumber + "\" NAME=\"sample_size" + TabNumber + "\" VALUE=\"" + DataH.get( "SAMPLE_SIZE" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"trade_name" + TabNumber + "\" NAME=\"trade_name" + TabNumber + "\" VALUE=\"" + DataH.get( "TRADE_NAME" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"recerts" + TabNumber + "\" NAME=\"recerts" + TabNumber + "\" VALUE=\"\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"sizevaries" + TabNumber + "\" NAME=\"sizevaries" + TabNumber + "\" VALUE=\"" + DataH.get( "SIZE_VARIES" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"itemverified" + TabNumber + "\" NAME=\"itemverified" + TabNumber + "\" VALUE=\"" + DataH.get( "ITEM_VERIFIED" ).toString() + "\">\n" );

		out.println( "<INPUT TYPE=\"hidden\" ID=\"mintemp" + TabNumber + "\" NAME=\"mintemp" + TabNumber + "\" VALUE=\"" + DataH.get( "MIN_TEMP" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"maxtemp" + TabNumber + "\" NAME=\"maxtemp" + TabNumber + "\" VALUE=\"" + DataH.get( "MAX_TEMP" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"tempunit" + TabNumber + "\" NAME=\"tempunit" + TabNumber + "\" VALUE=\"" + DataH.get( "TEMP_UNITS" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"labelColor" + TabNumber + "\" NAME=\"labelColor" + TabNumber + "\" VALUE=\"" + DataH.get( "LABEL_COLOR" ).toString() + "\">\n" );

		return;
	}

	public void buildDivs( Hashtable DataH,int TabNumber,PrintWriter out )
	{
		//StringBuffer Msgd=new StringBuffer();
		try
		{
			out.println( "\n" );
			//SEGATE MSDS No

			out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
			//Mfg ID
			out.println( "<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inbluer\"><B>Mfg ID: </B></TD><TD WIDTH=\"27%\" CLASS=\"Inbluel\">" );
			//out.println("<INPUT type=\"text\" CLASS=\"INVISIBLEHEADBLUE\"  name=\"mfg_id"+TabNumber+"\" value=\"");
			out.println( "<INPUT type=\"hidden\" id=\"mfg_id" + TabNumber + "\" name=\"mfg_id" + TabNumber + "\" value=\"" + DataH.get( "MANUFACTURER_ID" ) + "\">" );
			out.println( DataH.get( "MANUFACTURER_ID" ) );
			//out.println("\" SIZE=\"8\" readonly></TD>\n");
			out.println( "</TD>\n" );
			//MANUFACTURER
			out.println( "<TD WIDTH=\"9%\" CLASS=\"Inbluer\"><B>Manufacturer: </B></TD><TD CLASS=\"Inbluel\" WIDTH=\"43%\" >" );
			//out.println("<INPUT type=\"text\" CLASS=\"INVISIBLEHEADBLUE\"  name=\"manufacturer"+TabNumber+"\" value=\"");
			out.println( "<INPUT type=\"hidden\" id=\"manufacturer" + TabNumber + "\" name=\"manufacturer" + TabNumber + "\" value=\"" + DataH.get( "MANUFACTURER" ) + "\">" );
			out.println( DataH.get( "MANUFACTURER" ) );
			//out.println("\" SIZE=\"60\" readonly></TD></TR>\n");
			out.println( "</TD>\n</TR>\n" );

			String mfgurl=DataH.get( "MFG_URL" ) == null ? "" : DataH.get( "MFG_URL" ).toString().trim();
			if ( !( ( mfgurl.startsWith( "http://" ) ) || ( mfgurl.startsWith( "https://" ) ) ) && ( mfgurl.length() > 0 ) )
			{
				mfgurl="http://" + mfgurl;
			}
			String mfgcontact=DataH.get( "CONTACT" ).toString();

			//Mfg Phone
			out.println( "<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhiter\"><B>Mfg Phone: </B></TD><TD WIDTH=\"27%\" CLASS=\"Inwhitel\">" );
			out.println( DataH.get( "PHONE" ) );
			out.println( "</TD>\n" );
			//MANUFACTURER Contact
			out.println( "<TD WIDTH=\"9%\" CLASS=\"Inwhiter\"><B>Mfg Contact: </B></TD><TD CLASS=\"Inwhitel\" WIDTH=\"43%\" >" );
			if ( ( mfgcontact.length() > 0 ) && ( mfgurl.length() > 0 ) )
			{
				out.println( "<A onclick=\"javascript:window.open('" + mfgurl + "')\" STYLE=\"color:#0000ff;cursor:hand\">" + mfgcontact + "</A>" );
			}
			else if ( mfgurl.length() > 0 )
			{
				out.println( "<A onclick=\"javascript:window.open('" + mfgurl + "')\" STYLE=\"color:#0000ff;cursor:hand\">" + mfgurl + "</A>" );
			}
			out.println( "</TD>\n</TR>\n" );

			//Mfg email
			String mfgEmail=DataH.get( "EMAIL" ).toString();
			out.println( "<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Mfg Email: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inbluel\">" );
			out.println( mfgEmail );
			out.println( "<INPUT TYPE=\"hidden\" id=\"mfgemail" + TabNumber + "\" name=\"mfgemail" + TabNumber + "\" VALUE=\""+mfgEmail+"\"></TD>\n" );
			//MANUFACTURER Notes
			out.println( "<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Mfg Notes: </B></TD><TD CLASS=\"Inbluel\" ID=\"mfgnotes"+TabNumber+"\" WIDTH=\"25%\" >" );
			out.println( DataH.get( "NOTES" ) );
			out.println( "</TD>\n" );
			out.println( "</TR>\n" );

			//Material ID
			out.println( "<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhiter\"><B>Material ID: </B></TD><TD WIDTH=\"27%\" CLASS=\"Inwhitel\">" );
			//out.println("<INPUT type=\"text\" CLASS=\"INVISIBLEHEADWHITE\"  name=\"material_id"+TabNumber+"\" value=\"");
			out.println( "<INPUT type=\"hidden\" id=\"material_id" + TabNumber + "\" name=\"material_id" + TabNumber + "\" value=\"" + DataH.get( "MATERIAL_ID" ) + "\">" );
			out.println( DataH.get( "MATERIAL_ID" ) );
			//out.println("\" SIZE=\"8\" readonly>\n");
			//out.println("\" SIZE=\"8\" readonly><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchmatlike"+TabNumber+"\" value=\"...\" OnClick=\"getmaterialID(this.form,"+TabNumber+")\">\n");
			out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"button\"  CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"msdsbutton" + TabNumber + "\"  name=\"msdsbutton" + TabNumber + "\" value=\"MSDS\" OnClick=\"ShowMSDS(this.form," + TabNumber + ")\" type=\"button\"></TD>\n" );
			//MATERIAL_DESC
			out.println( "<TD WIDTH=\"9%\" CLASS=\"Inwhiter\"><B>Material Description: </B></TD><TD WIDTH=\"43%\" CLASS=\"Inwhitel\">" );
			out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" id=\"material_desc" + TabNumber + "\"  name=\"material_desc" + TabNumber + "\" value=\"" );
			//out.println("<INPUT type=\"hidden\" name=\"material_desc"+TabNumber+"\" value=\""+DataH.get("MATERIAL_DESC")+"\">");
			out.print( HelpObjs.findReplace( DataH.get( "MATERIAL_DESC" ).toString().trim(),"\"","&#34;" ) );
			out.println( "\" SIZE=\"50\" MAXLENGTH=\"1000\"></TD></TR>\n" );
			out.println( "</TD>\n</TR>\n" );
			//out.println("<INPUT type=\"hidden\" name=\"material_desc"+TabNumber+"\" value=\""+DataH.get("MATERIAL_DESC").toString()+"\"></TD></TR>\n");

			//Mfg Shelf Life
			out.println( "<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inbluer\"><B>Mfg Shelf Life: </B></TD><TD WIDTH=\"27%\" VALIGN=\"MIDDLE\" CLASS=\"Inbluel\">" );
			out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" id=\"mfg_shelf_life" + TabNumber + "\"  name=\"mfg_shelf_life" + TabNumber + "\" value=\"" );
			out.println( DataH.get( "SHELF_LIFE" ) );
			out.println( "\" SIZE=\"5\"> Days\n</TD>\n" );

			//MFG_TRADE_NAME
			out.println( "<TD WIDTH=\"9%\" CLASS=\"Inbluer\"><B>Trade Name: </B></TD><TD WIDTH=\"43%\" CLASS=\"Inbluel\">" );
			//out.println("<INPUT type=\"text\" CLASS=\"INVISIBLEHEADBLUE\"  name=\"trade_name"+TabNumber+"\" value=\"");
			out.println( "<INPUT type=\"hidden\" id=\"trade_name" + TabNumber + "\" name=\"trade_name" + TabNumber + "\" value=\"" + DataH.get( "TRADE_NAME" ) + "\">" );
			out.println( DataH.get( "TRADE_NAME" ) );
			//out.println("\" SIZE=\"60\" readonly></TD></TR>\n");
			out.println( "</TD>\n</TR>\n" );

			//Shelf Life Basis:
			out.println( "<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhiter\"><B>Basis:</B></TD><TD WIDTH=\"27%\" CLASS=\"Inwhitel\">" );
			String ShelfLifeBasis=DataH.get( "SHELF_LIFE_BASIS" ).toString();
			out.println( "<SELECT CLASS=\"HEADER\" ID=\"shelf_life_basis" + TabNumber + "\" NAME=\"shelf_life_basis" + TabNumber + "\" size=\"1\">\n" );
			out.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
			if ( "R".equalsIgnoreCase( ShelfLifeBasis ) )
			{
				out.println( "<option selected value=\"R\">DOR</option>\n" );
				out.println( "<option value=\"M\">DOM</option>\n" );
				out.println( "<option value=\"S\">DOS</option>\n" );
				out.println( "<option value=\"T\">DOT</option>\n" );
			}
			else if ( "M".equalsIgnoreCase( ShelfLifeBasis ) )
			{
				out.println( "<option value=\"R\">DOR</option>\n" );
				out.println( "<option selected value=\"M\">DOM</option>\n" );
				out.println( "<option value=\"S\">DOS</option>\n" );
				out.println( "<option value=\"T\">DOT</option>\n" );
			}
			else if ( "S".equalsIgnoreCase( ShelfLifeBasis ) )
			{
				out.println( "<option value=\"R\">DOR</option>\n" );
				out.println( "<option value=\"M\">DOM</option>\n" );
				out.println( "<option selected value=\"S\">DOS</option>\n" );
				out.println( "<option value=\"T\">DOT</option>\n" );
			}
			else if ( "T".equalsIgnoreCase( ShelfLifeBasis ) )
			{
				out.println( "<option value=\"R\">DOR</option>\n" );
				out.println( "<option value=\"M\">DOM</option>\n" );
				out.println( "<option value=\"S\">DOS</option>\n" );
				out.println( "<option selected value=\"T\">DOT</option>\n" );
			}
			else
			{
				out.println( "<option value=\"R\">DOR</option>\n" );
				out.println( "<option value=\"M\">DOM</option>\n" );
				out.println( "<option value=\"S\">DOS</option>\n" );
				out.println( "<option value=\"T\">DOT</option>\n" );
			}


			out.println( "</SELECT>\n" );
			out.println( "</TD>" );

			//Mfg Storage Temperature
			out.println( "<TD WIDTH=\"9%\" CLASS=\"Inwhiter\"><B>Mfg Storage Temp: </B></TD><TD WIDTH=\"43%\" CLASS=\"Inwhitel\">" );
			/*String selectedMfgStorgae=DataH.get( "STORAGE_TEMP" ).toString();
        out.println( "<SELECT CLASS=\"HEADER\" NAME=\"mfg_storage_temp" + TabNumber + "\" size=\"1\">\n" );
        out.println( "<OPTION VALUE=\"None\">Please Select</OPTION>\n" );
        out.println( radian.web.HTMLHelpObj.getDropDown( storage_temp,selectedMfgStorgae ) );
        out.println( "</SELECT>\n" );*/
			out.println( "Min:&nbsp;&nbsp;<INPUT type=\"text\" CLASS=\"HEADER\" id=\"mintemp" + TabNumber + "\"  name=\"mintemp" + TabNumber + "\" value=\"" );
			out.println( DataH.get( "MIN_TEMP" ) );
			out.println( "\" SIZE=\"3\" MAXLENGTH=\"30\">\n" );
			out.println( "Max:&nbsp;&nbsp;<INPUT type=\"text\" CLASS=\"HEADER\" id=\"maxtemp" + TabNumber + "\" name=\"maxtemp" + TabNumber + "\" value=\"" );
			out.println( DataH.get( "MAX_TEMP" ) );
			out.println( "\" SIZE=\"3\" MAXLENGTH=\"30\">\n" );
			out.println( "Units:&nbsp;&nbsp;<SELECT CLASS=\"HEADER\" ID=\"tempunit" + TabNumber + "\" NAME=\"tempunit" + TabNumber + "\" size=\"1\">\n" );
			String storagetempunit=DataH.get( "TEMP_UNITS" ).toString();
			if ( "F".equalsIgnoreCase( storagetempunit ) )
			{
				out.println( "<option value=\"\">None</option>\n" );
				out.println( "<option selected value=\"F\">F</option>\n" );
				out.println( "<option value=\"C\">C</option>\n" );
			}
			else if ( "C".equalsIgnoreCase( storagetempunit ) )
			{
				out.println( "<option value=\"\">None</option>\n" );
				out.println( "<option value=\"F\">F</option>\n" );
				out.println( "<option selected value=\"C\">C</option>\n" );
			}
			else
			{
				out.println( "<option selected value=\"\">None</option>\n" );
				out.println( "<option value=\"F\">F</option>\n" );
				out.println( "<option value=\"C\">C</option>\n" );
			}

			out.println( "</SELECT>\n" );
			out.println( "</TD></TR>\n" );

			//Grade
			out.println( "<TR><TD WIDTH=\"15%\" CLASS=\"Inbluer\"><B>Grade: </B></TD><TD WIDTH=\"27%\" CLASS=\"Inbluel\">" );
			out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" id=\"grade" + TabNumber + "\" name=\"grade" + TabNumber + "\" value=\"" );
			out.println( DataH.get( "GRADE" ) );
			out.println( "\" SIZE=\"5\" MAXLENGTH=\"60\"></TD>\n" );

			//MFG_PART_NO
			out.println( "<TD WIDTH=\"9%\" CLASS=\"Inbluer\"><B>Mfg Part #: </B></TD><TD WIDTH=\"43%\" CLASS=\"Inbluel\">" );
			out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" id=\"mfg_part_no" + TabNumber + "\" name=\"mfg_part_no" + TabNumber + "\" value=\"" );
			out.print( HelpObjs.findReplace( DataH.get( "MFG_PART_NO" ).toString().trim(),"\"","&#34;" ) );
			out.println( "\" SIZE=\"30\" MAXLENGTH=\"30\">\n" );
			out.println( "</TD></TR>\n" );

			out.println( "<TR>\n" );
			//Label Color
			out.println( "<TD WIDTH=\"15%\" CLASS=\"Inwhitel\"><B>Label Color: </B><BR>" );
			if (labelColorVector.size() > 0)
			{
				out.println( "<SELECT CLASS=\"HEADER\" ID=\"labelColor" + TabNumber + "\" NAME=\"labelColor" + TabNumber + "\" size=\"1\">\n" );
				out.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
				String labelColor=DataH.get( "LABEL_COLOR" ).toString();
				out.println( radian.web.HTMLHelpObj.getDropDown( labelColorVector,labelColor ) );
				out.println( "</SELECT>\n" );
			}
			else
			{
				out.println( "<INPUT TYPE=\"hidden\" ID=\"labelColor" + TabNumber + "\" NAME=\"labelColor" + TabNumber + "\" VALUE=\"" + DataH.get( "LABEL_COLOR" ).toString() + "\">\n" );
			}
			out.println( "</TD>\n" );

			//# OF COMPONENTS
			out.println( "<TD WIDTH=\"27%\" CLASS=\"Inwhitel\"><B># Comp: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Part Size: </B><BR>" );
			out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" id=\"noofcomponents" + TabNumber + "\" name=\"noofcomponents" + TabNumber + "\" value=\"" );
			out.println( DataH.get( "NO_OF_COMPONENTS" ) );
			out.println( "\" SIZE=\"5\">\n" );
			//Part Size
			//out.println("<TD WIDTH=\"27%\" CLASS=\"Inwhitel\"><B>Part Size: </B><BR>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT type=\"text\" CLASS=\"HEADER\"  name=\"part_size" +
					TabNumber + "\" id=\"part_size" + TabNumber + "\" value=\"" );
			out.println( DataH.get( "PART_SIZE" ) );
			out.println( "\" SIZE=\"5\">\n" );
			out.println( "</TD>\n" );

			//Size Unit
			out.println( "<TD WIDTH=\"9%\" CLASS=\"Inwhitel\"><B>Size Unit: </B><BR>" );
			out.println( "<SELECT CLASS=\"HEADER\" ID=\"size_units" + TabNumber + "\" NAME=\"size_units" + TabNumber + "\" size=\"1\">\n" );
			out.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
			String size_units=DataH.get( "PART_SIZE_UNIT" ).toString();
			out.println( radian.web.HTMLHelpObj.getDropDown( part_size_unit,size_units ) );
			out.println( "</SELECT>\n" );
			out.println( "</TD>\n" );

			//Packaging Style
			out.println( "<TD WIDTH=\"43%\" CLASS=\"Inwhitel\"><B>Packaging Style: </B>\n" );
			out.println( "<BR>" );
			out.println( "<SELECT CLASS=\"HEADER\" ID=\"pkg_style" + TabNumber + "\" NAME=\"pkg_style" + TabNumber + "\" size=\"1\">\n" );
			out.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
			String packaging_style=DataH.get( "PACKAGING_STYLE" ).toString();
			out.println( radian.web.HTMLHelpObj.getDropDown( pgk_style,packaging_style ) );
			out.println( "</SELECT>\n" );
			out.println( "</TD></TR>\n" );

			//Dimension
			out.println( "<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inbluel\"><B>Dimension: </B><BR>" );
			out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" id=\"dimension" + TabNumber + "\" name=\"dimension" + TabNumber + "\" value=\"" );
			out.print( HelpObjs.findReplace( DataH.get( "DIMENSION" ).toString().trim(),"\"","&#34;" ) );
			out.println( "\" SIZE=\"10\" MAXLENGTH=\"40\"></TD>\n" );
			//Net Wt
			out.println( "<TD WIDTH=\"27%\" CLASS=\"Inbluel\"><B>Net Wt: </B><BR>" );
			out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" id=\"net_wt" + TabNumber + "\" name=\"net_wt" + TabNumber + "\" value=\"" );
			out.println( DataH.get( "NET_WEIGHT" ) );
			out.println( "\" SIZE=\"5\"></TD>\n" );
			//Net Wt Unit
			out.println( "<TD WIDTH=\"9%\" CLASS=\"Inbluel\"><B>Net Wt Unit: </B><BR>" );
			out.println( "<SELECT CLASS=\"HEADER\" ID=\"net_weight_unit" + TabNumber + "\" NAME=\"net_weight_unit" + TabNumber + "\" size=\"1\">\n" );
			out.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
			String net_weight_unit=DataH.get( "NET_WEIGHT_UNIT" ).toString();
			out.println( radian.web.HTMLHelpObj.getDropDown( weight_unit,net_weight_unit ) );
			out.println( "</SELECT>\n" );
			out.println( "</TD>\n" );
			//Item Verified
			out.println( "<TD WIDTH=\"43%\" CLASS=\"Inbluel\"><B>Item Verified: </B>" );
			//Size Varies
			//out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println( "<SELECT CLASS=\"HEADER\" ID=\"itemverified" + TabNumber + "\" NAME=\"itemverified" + TabNumber + "\" size=\"1\">\n" );

			if ( "Y".equalsIgnoreCase( DataH.get( "ITEM_VERIFIED" ).toString() ) )
			{
				out.println( "<option selected value=\"Y\">Y</option>\n" );
				out.println( "<option value=\"N\">N</option>\n" );
			}
			else
			{
				out.println( "<option value=\"Y\">Y</option>\n" );
				out.println( "<option selected value=\"N\">N</option>\n" );
			}
			out.println( "</SELECT>\n" );

			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			//Sample Size
			out.println( "<B>Sample Size: </B>"+DataH.get( "SAMPLE_SIZE" ).toString()+"" );
			/*out.println( "<SELECT CLASS=\"HEADER\" NAME=\"samplesize" + TabNumber + "\" size=\"1\">\n" );
		if ( "Y".equalsIgnoreCase( DataH.get( "ITEM_VERIFIED" ).toString() ) )
		{
		  out.println( "<option selected value=\"Y\">Y</option>\n" );
		  out.println( "<option value=\"N\">N</option>\n" );
		}
		else
		{
		  out.println( "<option value=\"Y\">Y</option>\n" );
		  out.println( "<option selected value=\"N\">N</option>\n" );
		}
		out.println( "</SELECT>\n" );*/

			out.println( "</TD>\n" );
			out.println( "</TR>\n" );

			//Comments
			out.println( "<TR>\n" );
			out.println( "<TD WIDTH=\"15%\" CLASS=\"Inwhiter\"><B>Comments: </B></TD><TD WIDTH=\"80%\" COLSPAN=\"3\" CLASS=\"Inwhitel\">" );
			out.println( "<TEXTAREA id=\"Comments" + TabNumber + "\" name=\"Comments" + TabNumber + "\" rows=\"3\" cols=\"80\">" );
			out.print( HelpObjs.findReplace( DataH.get( "COMMENTS" ).toString().trim(),"\"","&#34;" ) );
			out.println( "</TEXTAREA></TD>\n" );
			out.println( "</TR>\n" );
			out.println( "</TABLE>\n" );
		}
		catch ( Exception e1 )
		{
			e1.printStackTrace();
			out.println( radian.web.HTMLHelpObj.printMessageinTable( "An Error Occured While Building Divs" ) );
			return;
		}

		return;
	}

	private Vector UpdateDetails( Vector in_data,int total,String requestId,String lineItem, String action,String caseQuant )
	{
		CatAddQc cataddqcObj1=new CatAddQc( db );
		Vector new_data=new Vector();

		try
		{
			for ( int i=0; i < total; i++ )
			{
				Hashtable hD=new Hashtable();
				hD= ( Hashtable ) in_data.elementAt( i );

				boolean line_result=false;
				line_result=cataddqcObj1.UpdateLine( hD,requestId,lineItem,action,caseQuant ); // update database

				if ( false == line_result )
				{
					completeSuccess=false;
					hD.remove( "UPDATE_FLAG" );
					hD.put( "UPDATE_FLAG",new Boolean( false ) );
				}

				new_data.addElement( hD );
			} //end of for
		}
		catch ( Exception e )
		{
			completeSuccess=false;
			e.printStackTrace();
		}

		return new_data;
	}

	private boolean updateprocess( Vector in_data,String logonid,int total,String requestId, String lineItem, String itemID,String itemstoragetemp,String itemcategory,
			String itemtype,String Company,String itemsamplesize,String caseQty,int isItemVerified,String magkitassing  )
	{
		boolean result=false;
		CatAddQc cataddqcObj1=new CatAddQc( db );
		Vector new_data=new Vector();
		boolean updateorinsert=false;
		PreparedStatement pstmt=null;

		if (isItemVerified == 0)
		{
			try
			{
				int test_number=DbHelpers.countQuery( db,"select count(*) from global.item where item_id = " + itemID + "" );
				if ( test_number > 0 )
				{
					updateorinsert=true;
				}

				Connection con=db.getConnection();
				if ( updateorinsert )
				{
					//Update
					pstmt=con.prepareStatement("update item set CATEGORY_ID=?,STORAGE_TEMP=?,SAMPLE_ONLY=?,ITEM_TYPE=?,STOCKING_TYPE='S',CASE_QTY=?,INSEPARABLE_KIT=? where ITEM_ID = ?" );
					/*pstmt.setInt( 1,Integer.parseInt( itemID ) );*/

					if ( "None".equalsIgnoreCase( itemcategory ) || itemcategory.trim().length() == 0 )
					{
						pstmt.setNull( 1,java.sql.Types.INTEGER );
					}
					else
					{
						pstmt.setInt( 1,Integer.parseInt( itemcategory ) );
					}

					if ( "None".equalsIgnoreCase( itemstoragetemp ) || itemstoragetemp.trim().length() == 0 )
					{
						pstmt.setNull( 2,java.sql.Types.VARCHAR );
					}
					else
					{
						pstmt.setString( 2,itemstoragetemp );
					}

					if ( "N".equalsIgnoreCase( itemsamplesize ) || itemsamplesize.trim().length() == 0)
					{
						pstmt.setNull( 3,java.sql.Types.VARCHAR );
					}
					else
					{
						pstmt.setString( 3,itemsamplesize );
					}

					pstmt.setString( 4,itemtype );

					if ( caseQty.length() > 0 )
					{
						pstmt.setInt( 5,Integer.parseInt( caseQty ) );
					}
					else
					{
						pstmt.setNull( 5,java.sql.Types.INTEGER );
					}

					if ( magkitassing.length() > 0 )
					{
						pstmt.setString( 6, magkitassing );
					}
					else
					{
						pstmt.setNull( 6,java.sql.Types.VARCHAR );
					}

					pstmt.setInt( 7,Integer.parseInt( itemID ) );

					pstmt.executeQuery();
				}
				else
				{
					//Insert
					pstmt=con.prepareStatement("insert into item (ITEM_ID,CATEGORY_ID,STORAGE_TEMP,SAMPLE_ONLY,ITEM_TYPE,STOCKING_TYPE,CASE_QTY,INSEPARABLE_KIT) values (?,?,?,?,?,'S',?,?)" );
					pstmt.setInt( 1,Integer.parseInt( itemID ) );

					if ( "None".equalsIgnoreCase( itemcategory ) || itemcategory.trim().length() == 0 )
					{
						pstmt.setNull( 2,java.sql.Types.INTEGER );
					}
					else
					{
						pstmt.setInt( 2,Integer.parseInt( itemcategory ) );
					}

					if ( "None".equalsIgnoreCase( itemstoragetemp ) || itemstoragetemp.trim().length() == 0 )
					{
						pstmt.setNull( 3,java.sql.Types.VARCHAR );
					}
					else
					{
						pstmt.setString( 3,itemstoragetemp );
					}

					if ( "N".equalsIgnoreCase( itemsamplesize ) || itemsamplesize.trim().length() == 0 )
					{
						pstmt.setNull( 4,java.sql.Types.VARCHAR );
					}
					else
					{
						pstmt.setString( 4,itemsamplesize );
					}

					pstmt.setString( 5,itemtype );

					if ( caseQty.length() > 0 )
					{
						pstmt.setInt( 6,Integer.parseInt( caseQty ) );
					}
					else
					{
						pstmt.setNull( 6,java.sql.Types.INTEGER );
					}

					if ( magkitassing.length() > 0 )
					{
						pstmt.setString( 7, magkitassing );
					}
					else
					{
						pstmt.setNull( 7,java.sql.Types.VARCHAR );
					}

					pstmt.executeQuery();
				}
			}
			catch ( Exception e )
			{
				completeSuccess=false;
				result=false;
				e.printStackTrace();
			}
			finally
			{
				if ( pstmt != null )
				{
					try
					{
						pstmt.close();
					}
					catch ( Exception se )
					{
						se.printStackTrace();
					}
				}
			}
		}

		try
		{
			for ( int i=0; i < total; i++ )
			{
				Hashtable hD=new Hashtable();
				hD= ( Hashtable ) in_data.elementAt( i );

				result=cataddqcObj1.processrequest( hD,logonid,requestId,lineItem, itemID ); // update database
				if ( false == result )
				{
					completeSuccess=false;
					hD.remove( "UPDATE_FLAG" );
					hD.put( "UPDATE_FLAG",new Boolean( false ) );
				}

				new_data.addElement( hD );
			} //end of for
		}
		catch ( Exception e )
		{
			completeSuccess=false;
			e.printStackTrace();
			result=false;
		}
		return result;
	}

	private Vector SynchServerData( HttpServletRequest request,Vector in_data )
	{
		Vector new_data=new Vector();

		try
		{
			int i=0; //used for detail lines
			for ( int loop=0; loop < in_data.size(); loop++ )
			{
				i++;
				Hashtable hD=new Hashtable();
				hD= ( Hashtable ) ( in_data.elementAt( loop ) );

				String partid="";
				try
				{
					partid=request.getParameter( ( "partid" + loop ) ).toString();
				}
				catch ( Exception e )
				{
					partid="";
				}
				hD.remove( "PART_ID" );
				hD.put( "PART_ID",partid.trim() );

				String mfg_id="";
				mfg_id=BothHelpObjs.makeBlankFromNull( request.getParameter( "mfg_id" + loop ) );
				hD.remove( "MANUFACTURER_ID" );
				hD.put( "MANUFACTURER_ID",mfg_id );

				String manufacturer="";
				manufacturer=BothHelpObjs.makeBlankFromNull( request.getParameter( "manufacturer" + loop ) );
				hD.remove( "MANUFACTURER" );
				hD.put( "MANUFACTURER",manufacturer );

				String material_category="";
				material_category=BothHelpObjs.makeBlankFromNull( request.getParameter( "material_category" + loop ) );
				hD.remove( "CATEGORY" );
				hD.put( "CATEGORY",material_category );

				String material_id="";
				material_id=BothHelpObjs.makeBlankFromNull( request.getParameter( "material_id" + loop ) );
				hD.remove( "MATERIAL_ID" );
				hD.put( "MATERIAL_ID",material_id );

				String material_desc="";
				material_desc=BothHelpObjs.makeBlankFromNull( request.getParameter( "material_desc" + loop ) );
				hD.remove( "MATERIAL_DESC" );
				hD.put( "MATERIAL_DESC",material_desc );

				String trade_name="";
				trade_name=BothHelpObjs.makeBlankFromNull( request.getParameter( "trade_name" + loop ) );
				hD.remove( "TRADE_NAME" );
				hD.put( "TRADE_NAME",trade_name );

				String grade="";
				grade=BothHelpObjs.makeBlankFromNull( request.getParameter( "grade" + loop ) );
				hD.remove( "GRADE" );
				hD.put( "GRADE",grade );

				String mfg_shelf_life="";
				mfg_shelf_life=BothHelpObjs.makeBlankFromNull( request.getParameter( "mfg_shelf_life" + loop ) );
				hD.remove( "SHELF_LIFE" );
				hD.put( "SHELF_LIFE",mfg_shelf_life );

				String mfg_storage_temp="";
				mfg_storage_temp=BothHelpObjs.makeBlankFromNull( request.getParameter( "mfg_storage_temp" + loop ) );
				hD.remove( "STORAGE_TEMP" );
				hD.put( "STORAGE_TEMP",mfg_storage_temp );

				String shelf_life_basis="";
				shelf_life_basis=BothHelpObjs.makeBlankFromNull( request.getParameter( "shelf_life_basis" + loop ) );
				hD.remove( "SHELF_LIFE_BASIS" );
				hD.put( "SHELF_LIFE_BASIS",shelf_life_basis );

				String noofcomponents="";
				noofcomponents=BothHelpObjs.makeBlankFromNull( request.getParameter( "noofcomponents" + loop ) );
				hD.remove( "NO_OF_COMPONENTS" );
				hD.put( "NO_OF_COMPONENTS",noofcomponents );

				String part_size="";
				part_size=BothHelpObjs.makeBlankFromNull( request.getParameter( "part_size" + loop ) );
				hD.remove( "PART_SIZE" );
				hD.put( "PART_SIZE",part_size );

				String sample_size="";
				sample_size=BothHelpObjs.makeBlankFromNull( request.getParameter( "sample_size" + loop ) );
				hD.remove( "SAMPLE_SIZE" );
				hD.put( "SAMPLE_SIZE",sample_size );

				String size_units="";
				size_units=BothHelpObjs.makeBlankFromNull( request.getParameter( "size_units" + loop ) );
				hD.remove( "PART_SIZE_UNIT" );
				hD.put( "PART_SIZE_UNIT",size_units );

				String pkg_style="";
				pkg_style=BothHelpObjs.makeBlankFromNull( request.getParameter( "pkg_style" + loop ) );
				hD.remove( "PACKAGING_STYLE" );
				hD.put( "PACKAGING_STYLE",pkg_style );

				String dimension="";
				dimension=BothHelpObjs.makeBlankFromNull( request.getParameter( "dimension" + loop ) );
				hD.remove( "DIMENSION" );
				hD.put( "DIMENSION",dimension );

				String net_wt="";
				net_wt=BothHelpObjs.makeBlankFromNull( request.getParameter( "net_wt" + loop ) );
				hD.remove( "NET_WEIGHT" );
				hD.put( "NET_WEIGHT",net_wt );

				String net_weight_unit="";
				net_weight_unit=BothHelpObjs.makeBlankFromNull( request.getParameter( "net_weight_unit" + loop ) );
				hD.remove( "NET_WEIGHT_UNIT" );
				hD.put( "NET_WEIGHT_UNIT",net_weight_unit );

				String mfg_part_no="";
				mfg_part_no=BothHelpObjs.makeBlankFromNull( request.getParameter( "mfg_part_no" + loop ) );
				hD.remove( "MFG_PART_NO" );
				hD.put( "MFG_PART_NO",mfg_part_no );

				String Comments="";
				Comments=BothHelpObjs.makeBlankFromNull( request.getParameter( "Comments" + loop ) );
				hD.remove( "COMMENTS" );
				hD.put( "COMMENTS",Comments.trim() );

				String recerts="";
				recerts=BothHelpObjs.makeBlankFromNull( request.getParameter( "recerts" + loop ) );
				hD.remove( "RECERTS" );
				hD.put( "RECERTS",recerts.trim() );

				String sizevaries="";
				sizevaries=BothHelpObjs.makeBlankFromNull( request.getParameter( "sizevaries" + loop ) );
				hD.remove( "SIZE_VARIES" );
				hD.put( "SIZE_VARIES",sizevaries.trim() );

				String itemverified="";
				itemverified=BothHelpObjs.makeBlankFromNull( request.getParameter( "itemverified" + loop ) );
				hD.remove( "ITEM_VERIFIED" );
				hD.put( "ITEM_VERIFIED",itemverified.trim() );

				String mintemp="";
				mintemp=BothHelpObjs.makeBlankFromNull( request.getParameter( "mintemp" + loop ) );
				hD.remove( "MIN_TEMP" );
				hD.put( "MIN_TEMP",mintemp.trim() );

				String maxtemp="";
				maxtemp=BothHelpObjs.makeBlankFromNull( request.getParameter( "maxtemp" + loop ) );
				hD.remove( "MAX_TEMP" );
				hD.put( "MAX_TEMP",maxtemp.trim() );

				String tempunit="";
				tempunit=BothHelpObjs.makeBlankFromNull( request.getParameter( "tempunit" + loop ) );
				hD.remove( "TEMP_UNITS" );
				hD.put( "TEMP_UNITS",tempunit.trim() );

				String labelColor="";
				labelColor=BothHelpObjs.makeBlankFromNull( request.getParameter( "labelColor" + loop ) );
				hD.remove( "LABEL_COLOR" );
				hD.put( "LABEL_COLOR",labelColor.trim() );

				new_data.addElement( hD );
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		return new_data;
	}
}
