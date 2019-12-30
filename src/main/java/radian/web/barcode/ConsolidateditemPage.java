package radian.web.barcode;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Random;
import java.util.Vector;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
//ACJEngine
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;
import com.tcmis.common.util.ResourceLibrary;

import radian.tcmis.both1.reports.RegisterTable;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */

public class ConsolidateditemPage
{
    private TcmISDBModel db = null;
	private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");
    public ConsolidateditemPage()
    {

    }

    public String generatePicklist( String picklistID,Vector VFromSession,String orderby,String opsEntityId )
       throws Exception,IOException
    {
      Random rand=new Random();
      int tmpReq=rand.nextInt();
      Integer tmpReqNum=new Integer( tmpReq );
      
      if(opsEntityId != null && opsEntityId.equals("HAASTCMDEU"))
    	  db=new RayTcmISDBModel( "Tcm_Ops", "2", "de_DE");     
      else
    	  db=new RayTcmISDBModel( "Tcm_Ops");

      DBResultSet dbrs=null;
      ResultSet rs=null;
      Vector resultF=new Vector();
      Hashtable data=new Hashtable();

      String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
      String url=wwwHome + "labels/consolidatedlist" + tmpReqNum.toString() + ".pdf";

      String Where=" PICKLIST.PICKLIST_ID =" + picklistID + "";
      String Query_Statement="";
	  Query_Statement="Select PICKLIST_ID,item_id,CATALOG_ID, CAT_PART_NO, PART_GROUP_NO,RECEIPT_ID,bin,sum(picklist_quantity) QUANTITY,PART_DESCRIPTION from table (pkg_pick.fx_picklist("+picklistID+")) where ";
	  Query_Statement+=" PICKLIST_QUANTITY is not null group by PICKLIST_ID,item_id,CATALOG_ID, CAT_PART_NO, PART_GROUP_NO,RECEIPT_ID,bin,PART_DESCRIPTION order by bin";

	  /*decode(expire_date,to_date('01/01/3000','mm/dd/yyyy'),'INDEFINITE',to_char(expire_date,'mm/dd/yyyy')) EXPIRE_DATE, "; //to_char(EXPIRE_DATE,'mm/dd/yyyy') EXPIRE_DATE
      Query_Statement+=" PICKLIST_QUANTITY,QUANTITY_PICKED,APPLICATION,FACILITY_ID,PART_DESCRIPTION,PACKAGING,INVENTORY_QUANTITY,CATALOG_ID,CAT_PART_NO, ";
      Query_Statement+=" PART_GROUP_NO,COMPANY_ID,DELIVERY_POINT,REQUESTOR,TRANSFER_REQUEST_ID,SHIP_TO_LOCATION_ID,STOCKING_METHOD from picklist_view where qc_date is null and PICKLIST_ID = " + picklistID + " ";
	  if (orderby.trim().length() > 0)
	  {
		Query_Statement+="order by "+orderby+"";
	  }*/

      dbrs=db.doQuery( Query_Statement );
      rs=dbrs.getResultSet();

      try
      {
        while ( rs.next() )
        {
          data=new Hashtable();
          data.put( "PICKLIST_ID",rs.getString( "PICKLIST_ID" ) == null ? " " : rs.getString( "PICKLIST_ID" ) );
          /*data.put( "MR_LINE",rs.getString( "MR_LINE" ) == null ? " " : rs.getString( "MR_LINE" ) );
          data.put( "FACILITY_ID",rs.getString( "FACILITY_ID" ) == null ? " " : rs.getString( "FACILITY_ID" ) );
          data.put( "APPLICATION",rs.getString( "APPLICATION" ) == null ? " " : rs.getString( "APPLICATION" ) );
          data.put( "REQUESTOR",rs.getString( "REQUESTOR" ) == null ? " " : rs.getString( "REQUESTOR" ) );
          data.put( "CATALOG_ID",rs.getString( "CATALOG_ID" ) == null ? " " : rs.getString( "CATALOG_ID" ) );*/
          data.put( "CAT_PART_NO",rs.getString( "CAT_PART_NO" ) == null ? " " : rs.getString( "CAT_PART_NO" ) );
          data.put( "PART_DESCRIPTION",rs.getString( "PART_DESCRIPTION" ) == null ? " " : rs.getString( "PART_DESCRIPTION" ) );
          data.put( "ITEM_ID",rs.getString( "ITEM_ID" ) == null ? " " : rs.getString( "ITEM_ID" ) );
          /*data.put( "LOT_STATUS",rs.getString( "LOT_STATUS" ) == null ? " " : rs.getString( "LOT_STATUS" ) );
          data.put( "EXPIRE_DATE",rs.getString( "EXPIRE_DATE" ) == null ? " " : rs.getString( "EXPIRE_DATE" ) );
          data.put( "MFG_LOT",rs.getString( "MFG_LOT" ) == null ? " " : rs.getString( "MFG_LOT" ) );*/
          data.put( "BIN",rs.getString( "BIN" ) == null ? " " : rs.getString( "BIN" ) );
		  data.put( "QUANTITY",rs.getString( "QUANTITY" ) == null ? " " : rs.getString( "QUANTITY" ) );
          data.put( "RECEIPT_ID",rs.getString( "RECEIPT_ID" ) == null ? " " : rs.getString( "RECEIPT_ID" ) );
          /*data.put( "INVENTORY_QUANTITY",rs.getString( "INVENTORY_QUANTITY" ) == null ? " " : rs.getString( "INVENTORY_QUANTITY" ) );
          data.put( "PICKLIST_QUANTITY",rs.getString( "PICKLIST_QUANTITY" ) == null ? " " : rs.getString( "PICKLIST_QUANTITY" ) );
          data.put( "QUANTITY_PICKED",rs.getString( "QUANTITY_PICKED" ) == null ? " " : rs.getString( "QUANTITY_PICKED" ) );
          data.put( "DELIVERY_POINT",rs.getString( "DELIVERY_POINT" ) == null ? " " : rs.getString( "DELIVERY_POINT" ) );
          data.put( "SHIP_TO_LOCATION_ID",rs.getString( "SHIP_TO_LOCATION_ID" ) == null ? "&nbsp;" : rs.getString( "SHIP_TO_LOCATION_ID" ) );
          data.put( "STOCKING_METHOD",rs.getString( "STOCKING_METHOD" ) == null ? " " : rs.getString( "STOCKING_METHOD" ) );
          data.put( "PR_NUMBER",rs.getString( "PR_NUMBER" ) == null ? " " : rs.getString( "PR_NUMBER" ) );
          data.put( "LINE_ITEM",rs.getString( "LINE_ITEM" ) == null ? " " : rs.getString( "LINE_ITEM" ) );
          data.put( "PACKAGING",rs.getString( "PACKAGING" ) == null ? " " : rs.getString( "PACKAGING" ) );
          data.put( "END_USER",rs.getString( "END_USER" ) == null ? " " : rs.getString( "END_USER" ) );

		  String mrnotes = rs.getString( "MR_NOTES" ) == null ? " " : rs.getString( "MR_NOTES" );
		  String workareacomment = rs.getString( "FAC_LOC_APP_PART_COMMENT" ) == null ? " " : rs.getString( "FAC_LOC_APP_PART_COMMENT" );
		  String catalogpartcomment = rs.getString( "CAT_PART_COMMENT" ) == null ? " " : rs.getString( "CAT_PART_COMMENT" );
		  mrnotes = mrnotes + "\n" + workareacomment + "\n" + catalogpartcomment;
		  data.put( "MR_NOTES",mrnotes );*/

          resultF.addElement( data );
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
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
	    //en.setCacheOption(true, ""+writefilepath+"consolidatedlist"+tmpReqNum.toString()+".joi");
      eClient.setPathForFontMapFile( fontmappath );

      try
      {
        en.readTemplate( ""+templatpath+"consolidatedlist.jod" );
      }
      catch ( Exception e )
      {
        System.out.println( "ERROR loading template" );
        e.printStackTrace();
        return "";
      }

      tm=en.getTemplateManager();
      tm.setWHEREClause( "SEC_00",Where );
	  /*String qryorderby = "";
	  if ("FACILITY_ID,APPLICATION".equalsIgnoreCase(orderby))
	  {
		qryorderby = "PICKLIST.FACILITY_ID,PICKLIST.APPLICATION";
	  }
	  else if ("PR_NUMBER,LINE_ITEM".equalsIgnoreCase(orderby))
	  {
		qryorderby = "PICKLIST.PR_NUMBER,PICKLIST.LINE_ITEM";
	  }
	  else if (orderby.trim().length() > 0)
	  {
		qryorderby = "PICKLIST."+orderby+"";
	  }

	  if ( qryorderby.trim().length() > 0 )
	  {
		tm.setORDERBYClause( "SEC_00",qryorderby );
      }

	  reoprtlog.info("generatePicklist    Start "+tmpReqNum.toString()+"    Size: "+resultF.size()+" ");*/
      
      // load German translations for HAASTCMDEU
      if(opsEntityId != null && opsEntityId.equals("HAASTCMDEU")){
    	  ResourceLibrary res = null;
          res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", new Locale("de", "DE"));
          
          tm.setLabel("LBL017", res.getString("consolidatedpick.title"));
          tm.setLabel("LBL000", res.getString("label.picklistno")+": ");
          tm.setLabel("LBL008", res.getString("label.item"));
          tm.setLabel("LBL001", res.getString("label.partnum"));
          tm.setLabel("LBL007", res.getString("label.description"));
          tm.setLabel("LBL012", res.getString("label.bin"));
          tm.setLabel("RECEIPTIDLBL", res.getString("label.receiptid"));
          tm.setLabel("LBL014", res.getString("label.qty"));
      }
      
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
        eClient.setPDFProperty( "FileName",""+writefilepath+"consolidatedlist" + tmpReqNum.toString() + ".pdf" );
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
        rt[0]=new RegisterTable( ConsolidateditemData.getVector( reportData1 ),"PICKLIST",ConsolidateditemData.getFieldVector(),null );
      }
      catch ( Exception e1 )
      {
        e1.printStackTrace();
        throw e1;
      }
      return rt;
    }
  }