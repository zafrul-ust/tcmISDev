package radian.web.barcode;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
//ACJEngine
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;
import radian.tcmis.both1.reports.RegisterTable;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 08-13-03 - adding END_USER to the picklist
 * 09-16-03 - Adding MR_NOTES to the picklist
 * 06-25-04 Adding log statements to trace a memory usage issue
 * 08-30-04 - Adding work area comments and catalog commetns to the picklist
 * 11-10-04 - Ability to sort the Picklist
 */

public class PicklistPage
{
    private TcmISDBModel db = null;
	private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");
    public PicklistPage()
    {

    }

    public String generatePicklist( String picklistID,Vector VFromSession,String orderby )
       throws Exception,IOException
    {
      Random rand=new Random();
      int tmpReq=rand.nextInt();
      Integer tmpReqNum=new Integer( tmpReq );
      db=new RayTcmISDBModel( "Tcm_Ops" );

      DBResultSet dbrs=null;
      ResultSet rs=null;
      Vector resultF=new Vector();
      Hashtable data=new Hashtable();

      String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
      String url=wwwHome + "labels/picklist" + tmpReqNum.toString() + ".pdf";
      String logoUrl="";

      if (orderby.equalsIgnoreCase("facilityId,application"))
      {
        orderby = "PR_NUMBER,LINE_ITEM,FACILITY_ID,APPLICATION";
      }
      else if (orderby.equalsIgnoreCase("customerServiceRepName"))
      {
        orderby = "PR_NUMBER,LINE_ITEM,CUSTOMER_SERVICE_REP_NAME";
      }
      else if (orderby.equalsIgnoreCase("itemId"))
      {
        orderby = "PR_NUMBER,LINE_ITEM,ITEM_ID";
      }
      else if (orderby.equalsIgnoreCase("prNumber"))
      {
        orderby = "PR_NUMBER,LINE_ITEM";
      }
      else if (orderby.equalsIgnoreCase("needdate"))
      {
        orderby = "PR_NUMBER,LINE_ITEM,NEED_DATE";
      }
      else if (orderby.equalsIgnoreCase("catPartNo"))
      {
        orderby = "PR_NUMBER,LINE_ITEM,CAT_PART_NO";
      }
      else if (orderby.equalsIgnoreCase("shipToLocationId"))
      {
        orderby = "PR_NUMBER,LINE_ITEM,SHIP_TO_LOCATION_DESC";
      } 

      String Where=" PICKLIST.picklistId =" + picklistID + "";
      String Query_Statement="";
      Query_Statement="Select CHARGE_DESC,INVENTORY_GROUP,MULTIPLE_CAT_PART_NO,DELIVERY_TYPE,to_char(NEED_DATE,'mm/dd/yyyy') NEED_DATE ,APPLICATION_DESC,LOGO_IMAGE_URL,CARRIER_NAME, PACKAGED_AS, DOT, CUSTOMER_SERVICE_REP_NAME, CUSTOMER_NAME, FACILITY_NAME, CUSTOMER_RECEIPT_ID, REQUESTOR_NAME, REQUESTOR_PHONE, REQUESTOR_FAX, REQUESTOR_EMAIL, ";
      Query_Statement+=" PAYMENT_TERMS, SPECIAL_INSTRUCTIONS, CARRIER_ACCOUNT_ID, CARRIER_CONTACT, CARRIER_SERVICE_TYPE, INCO_TERMS, FLASH_POINT, ADDRESS_LINE_1_DISPLAY, ADDRESS_LINE_2_DISPLAY, ";
      Query_Statement+=" ADDRESS_LINE_3_DISPLAY, ADDRESS_LINE_4_DISPLAY, ADDRESS_LINE_5_DISPLAY, SUBMITTED_BY, SUBMITTED_BY_NAME, HUB_BIN_ROOM, ";
      Query_Statement+=" SHIP_TO_LOCATION_DESC,PO_NUMBER,RECERT_NUMBER,FAC_LOC_APP_PART_COMMENT,CAT_PART_COMMENT,MR_NOTES,END_USER,PICKLIST_ID,HUB,PR_NUMBER,LINE_ITEM,MR_LINE,RECEIPT_ID,ITEM_ID,BIN,MFG_LOT,LOT_STATUS,DELIVERY_TYPE,NEED_DATE, ";
      Query_Statement+=" decode(expire_date,to_date('01/01/3000','mm/dd/yyyy'),'INDEFINITE',to_char(expire_date,'mm/dd/yyyy')) EXPIRE_DATE, "; //to_char(EXPIRE_DATE,'mm/dd/yyyy') EXPIRE_DATE
      Query_Statement+=" PICKLIST_QUANTITY,QUANTITY_PICKED,APPLICATION,FACILITY_ID,PART_DESCRIPTION,PACKAGING,INVENTORY_QUANTITY,CATALOG_ID,CAT_PART_NO, ";
      Query_Statement+=" PART_GROUP_NO,COMPANY_ID,DELIVERY_POINT,REQUESTOR,TRANSFER_REQUEST_ID,SHIP_TO_LOCATION_ID,STOCKING_METHOD,ALLOCATE_BY_MFG_LOT from table (pkg_pick.fx_picklist("+picklistID+")) where qc_date is null ";
	  if (orderby.trim().length() > 0)
	  {
		Query_Statement+="order by "+orderby+"";
	  }
	  else
		{
			 Query_Statement+="order by PR_NUMBER,LINE_ITEM";
	  }

      dbrs=db.doQuery( Query_Statement );
      rs=dbrs.getResultSet();

      try
      {
        while ( rs.next() )
        {
          data=new Hashtable();
          data.put( "PICKLIST_ID",rs.getString( "PICKLIST_ID" ) == null ? " " : rs.getString( "PICKLIST_ID" ) );
          data.put( "MR_LINE",rs.getString( "MR_LINE" ) == null ? " " : rs.getString( "MR_LINE" ) );
          data.put( "FACILITY_ID",rs.getString( "FACILITY_ID" ) == null ? " " : rs.getString( "FACILITY_ID" ) );
          data.put( "APPLICATION",rs.getString( "APPLICATION" ) == null ? " " : rs.getString( "APPLICATION" ) );
          data.put( "REQUESTOR",rs.getString( "REQUESTOR" ) == null ? " " : rs.getString( "REQUESTOR" ) );
          data.put( "CATALOG_ID",rs.getString( "CATALOG_ID" ) == null ? " " : rs.getString( "CATALOG_ID" ) );
          data.put( "CAT_PART_NO",rs.getString( "CAT_PART_NO" ) == null ? " " : rs.getString( "CAT_PART_NO" ) );
          data.put( "PART_DESCRIPTION",rs.getString( "PART_DESCRIPTION" ) == null ? " " : rs.getString( "PART_DESCRIPTION" ) );
          data.put( "ITEM_ID",rs.getString( "ITEM_ID" ) == null ? " " : rs.getString( "ITEM_ID" ) );
          data.put( "LOT_STATUS",rs.getString( "LOT_STATUS" ) == null ? " " : rs.getString( "LOT_STATUS" ) );
          data.put( "EXPIRE_DATE",rs.getString( "EXPIRE_DATE" ) == null ? " " : rs.getString( "EXPIRE_DATE" ) );
          data.put( "MFG_LOT",rs.getString( "MFG_LOT" ) == null ? " " : rs.getString( "MFG_LOT" ) );
          data.put( "BIN",rs.getString( "BIN" ) == null ? " " : rs.getString( "BIN" ) );
          data.put( "RECEIPT_ID",rs.getString( "RECEIPT_ID" ) == null ? " " : rs.getString( "RECEIPT_ID" ) );
          data.put( "INVENTORY_QUANTITY",rs.getString( "INVENTORY_QUANTITY" ) == null ? " " : rs.getString( "INVENTORY_QUANTITY" ) );
          data.put( "PICKLIST_QUANTITY",rs.getString( "PICKLIST_QUANTITY" ) == null ? " " : rs.getString( "PICKLIST_QUANTITY" ) );
          data.put( "QUANTITY_PICKED",rs.getString( "QUANTITY_PICKED" ) == null ? " " : rs.getString( "QUANTITY_PICKED" ) );
          data.put( "DELIVERY_POINT",rs.getString( "DELIVERY_POINT" ) == null ? " " : rs.getString( "DELIVERY_POINT" ) );
          data.put( "SHIP_TO_LOCATION_ID",rs.getString( "SHIP_TO_LOCATION_ID" ) == null ? "&nbsp;" : rs.getString( "SHIP_TO_LOCATION_ID" ) );
          data.put( "STOCKING_METHOD",rs.getString( "STOCKING_METHOD" ) == null ? " " : rs.getString( "STOCKING_METHOD" ) );
          data.put( "PR_NUMBER",rs.getString( "PR_NUMBER" ) == null ? " " : rs.getString( "PR_NUMBER" ) );
          data.put( "LINE_ITEM",rs.getString( "LINE_ITEM" ) == null ? " " : rs.getString( "LINE_ITEM" ) );
          data.put( "PACKAGING",rs.getString( "PACKAGING" ) == null ? " " : rs.getString( "PACKAGING" ) );
          data.put( "END_USER",rs.getString( "END_USER" ) == null ? " " : rs.getString( "END_USER" ) );
		  data.put( "TRANSFER_REQUEST_ID",rs.getString( "TRANSFER_REQUEST_ID" ) == null ? " " : rs.getString( "TRANSFER_REQUEST_ID" ) );
		  data.put( "RECERT_NUMBER",rs.getString( "RECERT_NUMBER" ) == null ? " " : rs.getString( "RECERT_NUMBER" ) );
          data.put( "PO_NUMBER",rs.getString( "PO_NUMBER" ) == null ? " " : rs.getString( "PO_NUMBER" ) );
          data.put( "SHIP_TO_LOCATION_DESC",rs.getString( "SHIP_TO_LOCATION_DESC" ) == null ? " " : rs.getString( "SHIP_TO_LOCATION_DESC" ) );
          data.put( "CARRIER_NAME",rs.getString( "CARRIER_NAME" ) == null ? " " : rs.getString( "CARRIER_NAME" ) );
          data.put( "PACKAGED_AS",rs.getString( "PACKAGED_AS" ) == null ? " " : rs.getString( "PACKAGED_AS" ) );
          data.put( "DOT",rs.getString( "DOT" ) == null ? " " : rs.getString( "DOT" ) );
          data.put( "CUSTOMER_SERVICE_REP_NAME",rs.getString( "CUSTOMER_SERVICE_REP_NAME" ) == null ? " " : rs.getString( "CUSTOMER_SERVICE_REP_NAME" ) );
          data.put( "CUSTOMER_NAME",rs.getString( "CUSTOMER_NAME" ) == null ? " " : rs.getString( "CUSTOMER_NAME" ) );
          data.put( "FACILITY_NAME",rs.getString( "FACILITY_NAME" ) == null ? " " : rs.getString( "FACILITY_NAME" ) );
          data.put( "CUSTOMER_RECEIPT_ID",rs.getString( "CUSTOMER_RECEIPT_ID" ) == null ? " " : rs.getString( "CUSTOMER_RECEIPT_ID" ) );
          data.put( "REQUESTOR_NAME",rs.getString( "REQUESTOR_NAME" ) == null ? " " : rs.getString( "REQUESTOR_NAME" ) );
          data.put( "REQUESTOR_PHONE",rs.getString( "REQUESTOR_PHONE" ) == null ? " " : rs.getString( "REQUESTOR_PHONE" ) );
          data.put( "REQUESTOR_FAX",rs.getString( "REQUESTOR_FAX" ) == null ? " " : rs.getString( "REQUESTOR_FAX" ) );
          data.put( "REQUESTOR_EMAIL",rs.getString( "REQUESTOR_EMAIL" ) == null ? " " : rs.getString( "REQUESTOR_EMAIL" ) );
          data.put( "PAYMENT_TERMS",rs.getString( "PAYMENT_TERMS" ) == null ? " " : rs.getString( "PAYMENT_TERMS" ) );
          data.put( "SPECIAL_INSTRUCTIONS",rs.getString( "SPECIAL_INSTRUCTIONS" ) == null ? " " : rs.getString( "SPECIAL_INSTRUCTIONS" ) );
          data.put( "CARRIER_ACCOUNT_ID",rs.getString( "CARRIER_ACCOUNT_ID" ) == null ? " " : rs.getString( "CARRIER_ACCOUNT_ID" ) );
          data.put( "CARRIER_CONTACT",rs.getString( "CARRIER_CONTACT" ) == null ? " " : rs.getString( "CARRIER_CONTACT" ) );
          data.put( "CARRIER_SERVICE_TYPE",rs.getString( "CARRIER_SERVICE_TYPE" ) == null ? " " : rs.getString( "CARRIER_SERVICE_TYPE" ) );
          data.put( "INCO_TERMS",rs.getString( "INCO_TERMS" ) == null ? " " : rs.getString( "INCO_TERMS" ) );
          data.put( "FLASH_POINT",rs.getString( "FLASH_POINT" ) == null ? " " : rs.getString( "FLASH_POINT" ) );
          data.put( "ADDRESS_LINE_1_DISPLAY",rs.getString( "ADDRESS_LINE_1_DISPLAY" ) == null ? " " : rs.getString( "ADDRESS_LINE_1_DISPLAY" ) );
          data.put( "ADDRESS_LINE_2_DISPLAY",rs.getString( "ADDRESS_LINE_2_DISPLAY" ) == null ? " " : rs.getString( "ADDRESS_LINE_2_DISPLAY" ) );
          data.put( "ADDRESS_LINE_3_DISPLAY",rs.getString( "ADDRESS_LINE_3_DISPLAY" ) == null ? " " : rs.getString( "ADDRESS_LINE_3_DISPLAY" ) );
          data.put( "ADDRESS_LINE_4_DISPLAY",rs.getString( "ADDRESS_LINE_4_DISPLAY" ) == null ? " " : rs.getString( "ADDRESS_LINE_4_DISPLAY" ) );
          data.put( "ADDRESS_LINE_5_DISPLAY",rs.getString( "ADDRESS_LINE_5_DISPLAY" ) == null ? " " : rs.getString( "ADDRESS_LINE_5_DISPLAY" ) );
          data.put( "SUBMITTED_BY_NAME",rs.getString( "SUBMITTED_BY_NAME" ) == null ? " " : rs.getString( "SUBMITTED_BY_NAME" ) );
          data.put( "HUB_BIN_ROOM",rs.getString( "HUB_BIN_ROOM" ) == null ? " " : rs.getString( "HUB_BIN_ROOM" ) );
          logoUrl = rs.getString( "LOGO_IMAGE_URL" ) == null ? " " : rs.getString( "LOGO_IMAGE_URL" );
          data.put( "LOGO_IMAGE_URL",logoUrl);
          //data.put( "NUMBER",rs.getString( "NUMBER" ) == null ? " " : rs.getString( "NUMBER" ) );
          String mrnotes = rs.getString( "MR_NOTES" ) == null ? " " : rs.getString( "MR_NOTES" );
		  String workareacomment = rs.getString( "FAC_LOC_APP_PART_COMMENT" ) == null ? " " : rs.getString( "FAC_LOC_APP_PART_COMMENT" );
		  String catalogpartcomment = rs.getString( "CAT_PART_COMMENT" ) == null ? " " : rs.getString( "CAT_PART_COMMENT" );
		  if (workareacomment.length() > 0)
          {
                  mrnotes = mrnotes + "\n" + workareacomment;
          }            
          if (catalogpartcomment.length() > 0)
          {
                  mrnotes = mrnotes + "\n" + catalogpartcomment;
          }
		  data.put( "MR_NOTES",mrnotes );
          data.put( "DELIVERY_TYPE",rs.getString( "DELIVERY_TYPE" ) == null ? " " : rs.getString( "DELIVERY_TYPE" ) );
          data.put( "NEED_DATE",rs.getString( "NEED_DATE" ) == null ? " " : rs.getString( "NEED_DATE" ) );
          data.put( "APPLICATION_DESC",rs.getString( "APPLICATION_DESC" ) == null ? " " : rs.getString( "APPLICATION_DESC" ) );
          data.put( "ALLOCATE_BY_MFG_LOT",rs.getString( "ALLOCATE_BY_MFG_LOT" ) == null ? " " : rs.getString( "ALLOCATE_BY_MFG_LOT" ) );
          data.put( "INVENTORY_GROUP",rs.getString( "INVENTORY_GROUP" ) == null ? " " : rs.getString( "INVENTORY_GROUP" ) );
          data.put( "MULTIPLE_CAT_PART_NO",rs.getString( "MULTIPLE_CAT_PART_NO" ) == null ? " " : rs.getString( "MULTIPLE_CAT_PART_NO" ) );
          data.put("CHARGE_DESCRIPTION",rs.getString("CHARGE_DESC")==null?"":rs.getString("CHARGE_DESC"));

          resultF.addElement( data );
        }
      }
      catch ( Exception e )
      {
        //e.printStackTrace();
        return "";
      }
      finally
      {
        if (dbrs != null)  { dbrs.close(); }
        db.close();
      }

      TemplateManager tm=null;
      ACJEngine en=new ACJEngine();
      en.setDebugMode( true );
      en.setX11GfxAvailibility( false );
      en.setTargetOutputDevice( ACJConstants.PDF );

      ACJOutputProcessor eClient=new ACJOutputProcessor();
      String fontmappath=radian.web.tcmisResourceLoader.getfontpropertiespath();
      String templatpath=radian.web.tcmisResourceLoader.getlabeltempplatepath();
      String writefilepath=radian.web.tcmisResourceLoader.getsavelabelpath();
	  //en.setCacheOption(true, ""+writefilepath+"picklist"+tmpReqNum.toString()+".joi");
      eClient.setPathForFontMapFile( fontmappath );

      try
      {
        en.readTemplate( ""+templatpath+"picklist.jod" );
      }
      catch ( Exception e )
      {
        System.out.println( "ERROR loading template" );
        //e.printStackTrace();
        return "";
      }

      tm=en.getTemplateManager();
      tm.setLabel( "IMG000",logoUrl );
      tm.setWHEREClause( "SEC_00",Where );
	  String qryorderby = "";
	  if ("FACILITY_ID,APPLICATION".equalsIgnoreCase(orderby))
	  {
		qryorderby = "PICKLIST.prNumber,PICKLIST.lineItem,PICKLIST.facilityId,PICKLIST.application";
	  }
	  else if ("PR_NUMBER,LINE_ITEM".equalsIgnoreCase(orderby) || orderby.equalsIgnoreCase("prNumber"))
	  {
		qryorderby = "PICKLIST.prNumber,PICKLIST.lineItem";
	  }
      else if (orderby.equalsIgnoreCase("customerServiceRepName"))
      {
        qryorderby = "PICKLIST.prNumber,PICKLIST.lineItem,PICKLIST.CUSTOMER_SERVICE_REP_NAME";
      }
      else if (orderby.equalsIgnoreCase("itemId"))
      {
        orderby = "PICKLIST.prNumber,PICKLIST.lineItem,PICKLIST.ITEM_ID";
      }

      else if (orderby.equalsIgnoreCase("needdate"))
      {
        orderby = "PICKLIST.prNumber,PICKLIST.lineItem,PICKLIST.NEED_DATE";
      }
      else if (orderby.equalsIgnoreCase("catPartNo"))
      {
        orderby = "PICKLIST.prNumber,PICKLIST.lineItem,PICKLIST.CAT_PART_NO";
      }
      else if (orderby.equalsIgnoreCase("shipToLocationId"))
      {
        orderby = "PICKLIST.prNumber,PICKLIST.lineItem,PICKLIST.SHIP_TO_LOCATION_DESC";
      }                  
      else if (orderby.trim().length() > 0)
	  {
          reoprtlog.info("orderby  "+orderby);
        qryorderby = "PICKLIST."+orderby+"";
	  }

	  if ( qryorderby.trim().length() > 0 )
	  {
		tm.setORDERBYClause( "SEC_00",qryorderby );
      }

	  //reoprtlog.info("generatePicklist    Start "+tmpReqNum.toString()+"    Size: "+resultF.size()+" ");
      try
      {
        AppDataHandler ds=new AppDataHandler();
        //register table...
        RegisterTable[] rt=getData( resultF );
        for ( int i=0; i < rt.length; i++ )
        {
          ds.RegisterTable( rt[i].getData(),rt[i].getName(),rt[i].getMethods(),rt[i].getWhere() );
        }
        en.setDataSource( ds );
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        throw e;
      }

      try
      {
        eClient.setReportData( en.generateReport() );
      }
      catch ( Exception ex )
      {
        System.out.println( "\n\n---------ERROR: While generating report" );
        ex.printStackTrace();
        return "";
      }


      try
      {
        eClient.setPDFProperty( "FileName",""+writefilepath+"picklist" + tmpReqNum.toString() + ".pdf" );
        eClient.generatePDF();
      }
      catch ( Exception e )
      {
        System.out.println( "\n\n---------ERROR generating report" );
        e.printStackTrace();
        return "";

      }
	  //reoprtlog.info("generatePicklist    Done  "+tmpReqNum.toString()+"");
      return url;
    }

    public RegisterTable[] getData( Vector reportData1 )
       throws Exception
    {
      RegisterTable[] rt=new RegisterTable[1];

      try
      {
        rt[0]=new RegisterTable( PickListData.getVector( reportData1 ),"PICKLIST",PickListData.getFieldVector(),null );
      }
      catch ( Exception e1 )
      {
        e1.printStackTrace();
        throw e1;
      }
      return rt;
    }
  }