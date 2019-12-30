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
 * 06-25-04 Adding log statements to trace a memory usage issue
 */

public class tapidLabel
{
    private TcmISDBModel db = null;
	private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");
    public tapidLabel()
    {

    }

    public String generateTaplabel( String tapReceiptid )
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
      String url=wwwHome + "labels/tapid" + tmpReqNum.toString() + ".pdf";

      String Query_Statement="";
	  Query_Statement = "Select lpad(x.RECEIPT_ID,8,'0') RECEIPT_ID1,x.RECEIPT_ID,x.ITEM_ID,fx_kit_packaging(x.item_id) ITEM_PKG, (select i.item_desc from item i where i.item_id=x.item_id) ITEM_DESC  from receipt x where RECEIPT_ID = "+tapReceiptid+"";

      dbrs=db.doQuery( Query_Statement );
      rs=dbrs.getResultSet();

      try
      {
        while ( rs.next() )
        {
          data=new Hashtable();
          data.put( "RECEIPT_ID1",rs.getString( "RECEIPT_ID1" ) == null ? " " : rs.getString( "RECEIPT_ID1" ) );
          data.put( "RECEIPT_ID",rs.getString( "RECEIPT_ID" ) == null ? " " : rs.getString( "RECEIPT_ID" ) );
          data.put( "ITEM_PKG",rs.getString( "ITEM_PKG" ) == null ? " " : rs.getString( "ITEM_PKG" ) );
          data.put( "ITEM_DESC",rs.getString( "ITEM_DESC" ) == null ? " " : rs.getString( "ITEM_DESC" ) );

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
	    //en.setCacheOption(true, ""+writefilepath+"tapid"+tmpReqNum.toString()+".joi");
      eClient.setPathForFontMapFile( fontmappath );

      try
      {
        en.readTemplate( ""+templatpath+"tapid.jod" );
      }
      catch ( Exception e )
      {
        System.out.println( "ERROR loading template" );
        e.printStackTrace();
        return "";
      }

      tm=en.getTemplateManager();
	  //reoprtlog.info("generateTaplabel    Start "+tmpReqNum.toString()+"    Size: "+resultF.size()+" ");
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
        eClient.setPDFProperty( "FileName",""+writefilepath+"tapid" + tmpReqNum.toString() + ".pdf" );
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
        rt[0]=new RegisterTable( tapidData.getVector( reportData1 ),"TAPID",tapidData.getFieldVector(),null );
      }
      catch ( Exception e1 )
      {
        e1.printStackTrace();
        throw e1;
      }
      return rt;
    }
  }