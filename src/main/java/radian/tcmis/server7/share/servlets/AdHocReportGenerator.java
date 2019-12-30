package radian.tcmis.server7.share.servlets;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Nawaz Shaik
 * @version
 * 01-28-04 Making relative URLs to go to the property file to get hosturl
 * 06-25-04 Adding log statements to trace a memory usage issue
 */
//ACJEngine
import java.util.Random;
import java.util.Vector;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;
import radian.tcmis.both1.helpers.AdhocUsageData;
import radian.tcmis.both1.reports.RegisterTable;

public class AdHocReportGenerator {
    //ACJEngine
    private Vector reportfields = new Vector();
    private Vector SizeItems1 = new Vector();
    private String url = "";
    private ACJEngine erw = null;
    private TemplateManager tm = null;
	private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");

    public AdHocReportGenerator()
    {

    }

    protected String buildHourlyVOC(Vector lineItems, String Facility, String Month, String Method,
    String Fianl1,String Fianl2,String Fianl3,String Fianl4,String Fianl5,String Fianl6) throws Exception
    {
        erw = new ACJEngine();
        erw.setDebugMode(true);
        erw.setX11GfxAvailibility(false);
        erw.setTargetOutputDevice(ACJConstants.PDF);

        ACJOutputProcessor ec = new ACJOutputProcessor();
        String fontmappath=radian.web.tcmisResourceLoader.getfontpropertiespath();
        String templatpath=radian.web.tcmisResourceLoader.getreportstempplatepath();
        String writefilepath=radian.web.tcmisResourceLoader.getsavelreportpath();
        String tempwritefilepath=radian.web.tcmisResourceLoader.getsaveltempreportpath();
        String urlpath=radian.web.tcmisResourceLoader.getreporturl();
        String tempurlpath=radian.web.tcmisResourceLoader.gettempreporturl();
        ec.setPathForFontMapFile(fontmappath);

        try
        {
          erw.readTemplate( "" + templatpath + "HourlyVocReport.erw" );
        }
        catch ( Exception e )
        {
          //System.out.println( "ERROR loading template" );
          e.printStackTrace();
          return url;
        }

        tm = erw.getTemplateManager();
        tm.setLabel("LAB008",Facility);
        tm.setLabel("LAB010",Month);
        tm.setLabel("FINAL1",Fianl1);
        tm.setLabel("FINAL2",Fianl2);
        tm.setLabel("FINAL3",Fianl3);
        tm.setLabel("FINAL4",Fianl4);
        tm.setLabel("FINAL5",Fianl6);
        tm.setLabel("FINAL6",Fianl5);

		Random rand=new Random();
		int tmpReq=rand.nextInt();
		Integer tmpReqNum=new Integer( tmpReq );
		reoprtlog.info("buildHourlyVOC   Start "+tmpReqNum.toString()+"    Size: "+lineItems.size()+"  ");
        try
        {
          AppDataHandler ds=new AppDataHandler();
          SizeItems1=reportfields;
          RegisterTable[] rt=getData( lineItems,9 );
          for ( int i=0; i < rt.length; i++ )
          {
            ds.RegisterTable( rt[i].getData(),rt[i].getName(),rt[i].getMethods(),rt[i].getWhere() );
          }
          erw.setDataSource( ds );
        }
        catch ( Exception e )
        {
          e.printStackTrace();
          throw e;
        }

        try
        {
		  erw.setCacheOption(true, ""+writefilepath+"VocHocAdh"+tmpReqNum.toString()+".joi");
          if ( Method.equalsIgnoreCase( "Batch" ) )
          {
            ec.setReportData( erw.generateReport() );
            ec.setPDFProperty( "FileName","" + writefilepath + "VocHocAdh" + tmpReqNum.toString() + ".pdf" );
            ec.setPDFProperty( "ZipCompressed",new Boolean( false ) );
            ec.generatePDF();
          }
          else
          {
            ec.setReportData( erw.generateReport() );
            ec.setPDFProperty( "FileName","" + tempwritefilepath + "VocHocAdh" + tmpReqNum.toString() + ".pdf" );
            ec.setPDFProperty( "ZipCompressed",new Boolean( false ) );
            ec.generatePDF();
          }
        }
        catch ( Exception e )
        {
          //System.out.println( "/n/n---------ERROR generating report" );
          e.printStackTrace();
          return url;
        }
		reoprtlog.info("buildHourlyVOC    Done  "+tmpReqNum.toString()+"");

		String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
		wwwHome = wwwHome.substring(0,(wwwHome.length()-1));
        if ( Method.equalsIgnoreCase( "Batch" ) )
        {
          url="" + wwwHome + urlpath + "VocHocAdh" + tmpReqNum.toString() + ".pdf";
        }
        else
        {
          url="" + wwwHome + tempurlpath + "VocHocAdh" + tmpReqNum.toString() + ".pdf";
        }

        return url;
    }

  protected String buildFiveHourVOC(Vector lineItems, Vector maxFiveHourV, String Facility, String Month, String Method, String reportType) throws Exception
  {
    erw = new ACJEngine();
    erw.setDebugMode(true);
    erw.setX11GfxAvailibility(false);
    erw.setTargetOutputDevice(ACJConstants.PDF);

    ACJOutputProcessor ec=new ACJOutputProcessor();
    String fontmappath=radian.web.tcmisResourceLoader.getfontpropertiespath();
    String templatpath=radian.web.tcmisResourceLoader.getreportstempplatepath();
    String writefilepath=radian.web.tcmisResourceLoader.getsavelreportpath();
    String tempwritefilepath=radian.web.tcmisResourceLoader.getsaveltempreportpath();
    String urlpath=radian.web.tcmisResourceLoader.getreporturl();
    String tempurlpath=radian.web.tcmisResourceLoader.gettempreporturl();
    ec.setPathForFontMapFile(fontmappath);

    try
    {
      //System.out.println( "-------- report type: " + reportType );
      if ( "FIVE_HOUR_6A".equalsIgnoreCase( reportType ) )
      {
        erw.readTemplate( "" + templatpath + "FiveHourVocReport6A.erw" );
      }
      else
      {
        erw.readTemplate( "" + templatpath + "FiveHourVocReport7A.erw" );
      }
    }
    catch ( Exception e )
    {
      //System.out.println( "ERROR loading template" );
      e.printStackTrace();
      return url;
    }

    tm = erw.getTemplateManager();
    tm.setLabel("LBL002",Facility);
    tm.setLabel("LBL004",Month);

	Random rand = new Random();
	int tmpReq = rand.nextInt();
	Integer tmpReqNum = new Integer(tmpReq);
	reoprtlog.info("buildFiveHourVOC    Start "+tmpReqNum.toString()+"   Size: "+lineItems.size()+" and "+maxFiveHourV.size()+"  ");

    try {
      AppDataHandler ds = new AppDataHandler();
      //register table...
      //Maximum Five - Hour Average
      RegisterTable[] rt0 = getDataMax(maxFiveHourV,3);
      for(int ii=0;ii<rt0.length;ii++) {
          ds.RegisterTable(rt0[ii].getData(),rt0[ii].getName(),rt0[ii].getMethods(),rt0[ii].getWhere());
      }
      erw.setDataSource(ds);

      //Five - Hour Average VOC
      SizeItems1 = reportfields;
      RegisterTable[] rt = getData(lineItems,4);
      for(int i=0;i<rt.length;i++) {
      ds.RegisterTable(rt[i].getData(),rt[i].getName(),rt[i].getMethods(),rt[i].getWhere());
      }
      erw.setDataSource(ds);
    }catch(Exception e) {
      e.printStackTrace();
      throw e;
    }

    try
    {
	    erw.setCacheOption(true, ""+writefilepath+"FiveHourVocHocAdh"+tmpReqNum.toString()+".joi");
        if (Method.equalsIgnoreCase("Batch")) {
          ec.setReportData(erw.generateReport());
          ec.setPDFProperty("FileName",""+writefilepath+"FiveHourVocHocAdh"+tmpReqNum.toString()+".pdf");
          ec.setPDFProperty("ZipCompressed",new Boolean(false));
          ec.generatePDF();
        }else {
          ec.setReportData(erw.generateReport());
          ec.setPDFProperty("FileName",""+tempwritefilepath+"FiveHourVocHocAdh"+tmpReqNum.toString()+".pdf");
          ec.setPDFProperty("ZipCompressed",new Boolean(false));
          ec.generatePDF();
        }
    }catch (Exception e) {
        //System.out.println("/n/n---------ERROR generating report");
        e.printStackTrace();
        return url;
    }
	reoprtlog.info("buildFiveHourVOC    Done "+tmpReqNum.toString()+"");

    String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
    wwwHome = wwwHome.substring(0,(wwwHome.length()-1));
    if ( Method.equalsIgnoreCase( "Batch" ) )
    {
      url="" + wwwHome + urlpath + "FiveHourVocHocAdh" + tmpReqNum.toString() + ".pdf";
    }
    else
    {
      url="" + wwwHome + tempurlpath + "FiveHourVocHocAdh" + tmpReqNum.toString() + ".pdf";
    }

    return url;
  }

    protected String buildFourHourVOC(Vector lineItems, Vector maxFourHourV, String Facility, String Month, String Method) throws Exception
    {
        erw = new ACJEngine();
        erw.setDebugMode(true);
        erw.setX11GfxAvailibility(false);
        erw.setTargetOutputDevice(ACJConstants.PDF);

        ACJOutputProcessor ec=new ACJOutputProcessor();
        String fontmappath=radian.web.tcmisResourceLoader.getfontpropertiespath();
        String templatpath=radian.web.tcmisResourceLoader.getreportstempplatepath();
        String writefilepath=radian.web.tcmisResourceLoader.getsavelreportpath();
        String tempwritefilepath=radian.web.tcmisResourceLoader.getsaveltempreportpath();
        String urlpath=radian.web.tcmisResourceLoader.getreporturl();
        String tempurlpath=radian.web.tcmisResourceLoader.gettempreporturl();
        ec.setPathForFontMapFile( fontmappath );

        try
        {
          erw.readTemplate( "" + templatpath + "FourHourVocReportNew.erw" );
        }
        catch ( Exception e )
        {
          //System.out.println( "ERROR loading template" );
          e.printStackTrace();
          return url;
        }

        tm = erw.getTemplateManager();
        tm.setLabel("LBL002",Facility);
        tm.setLabel("LBL004",Month);

		Random rand = new Random();
		int tmpReq = rand.nextInt();
		Integer tmpReqNum = new Integer(tmpReq);
		reoprtlog.info("buildFourHourVOC    Start "+tmpReqNum.toString()+"    Size: "+lineItems.size()+" and "+maxFourHourV.size()+"  ");

        try {
          AppDataHandler ds = new AppDataHandler();
          //register table...
          //Maximum Four - Hour Average
          RegisterTable[] rt0 = getDataMax(maxFourHourV,2);
          for(int ii=0;ii<rt0.length;ii++) {
              ds.RegisterTable(rt0[ii].getData(),rt0[ii].getName(),rt0[ii].getMethods(),rt0[ii].getWhere());
          }
          erw.setDataSource(ds);

          //Four - Hour Average VOC
          SizeItems1 = reportfields;
          RegisterTable[] rt = getData(lineItems,4);
          for(int i=0;i<rt.length;i++) {
           ds.RegisterTable(rt[i].getData(),rt[i].getName(),rt[i].getMethods(),rt[i].getWhere());
          }
          erw.setDataSource(ds);
        }catch(Exception e) {
          e.printStackTrace();
          throw e;
        }

        try
        {
   	      erw.setCacheOption(true, ""+writefilepath+"FourHourVocHocAdh"+tmpReqNum.toString()+".joi");
          if ( Method.equalsIgnoreCase( "Batch" ) )
          {
            ec.setReportData( erw.generateReport() );
            ec.setPDFProperty( "FileName","" + writefilepath + "FourHourVocHocAdh" + tmpReqNum.toString() + ".pdf" );
            ec.setPDFProperty( "ZipCompressed",new Boolean( false ) );
            ec.generatePDF();
          }
          else
          {
            ec.setReportData( erw.generateReport() );
            ec.setPDFProperty( "FileName","" + tempwritefilepath + "FourHourVocHocAdh" + tmpReqNum.toString() + ".pdf" );
            ec.setPDFProperty( "ZipCompressed",new Boolean( false ) );
            ec.generatePDF();
          }
        }catch (Exception e) {
            //System.out.println("/n/n---------ERROR generating report");
            e.printStackTrace();
            return url;
        }
		reoprtlog.info("buildFourHourVOC    Done "+tmpReqNum.toString()+"");

	String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
	wwwHome = wwwHome.substring(0,(wwwHome.length()-1));
        if ( Method.equalsIgnoreCase( "Batch" ) )
        {
          url="" + wwwHome + urlpath + "FourHourVocHocAdh" + tmpReqNum.toString() + ".pdf";
        }
        else
        {
          url="" + wwwHome + tempurlpath + "FourHourVocHocAdh" + tmpReqNum.toString() + ".pdf";
        }
        return url;
    }


    public RegisterTable[] getData(Vector reportData1, int size) throws Exception
    {
        RegisterTable[] rt = new RegisterTable[1];
        try {
          rt[0] = new RegisterTable(AdhocUsageData.getVector(reportData1,size),"ADHOCUSAGEDATA",AdhocUsageData.getFieldVector(size),null);
        }catch(Exception e1) {
          e1.printStackTrace();
          throw e1;
        }
        return rt;
    }

    public RegisterTable[] getDataMax(Vector reportData1, int size) throws Exception
    {
        RegisterTable[] rt = new RegisterTable[1];
        try {
          rt[0] = new RegisterTable(AdhocUsageData.getVector(reportData1,size),"ADHOCUSAGEDATAMAX",AdhocUsageData.getFieldVector(size),null);
        }catch(Exception e1) {
          e1.printStackTrace();
          throw e1;
        }
        return rt;
    }
}

