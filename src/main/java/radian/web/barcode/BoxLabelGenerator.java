package radian.web.barcode;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 06-25-04 Adding log statements to trace a memory usage issue
 */

import java.util.Vector;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;
import radian.tcmis.both1.reports.RegisterTable;

public class BoxLabelGenerator
{

  public BoxLabelGenerator()
  {
  }
  private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");
  public String buildTest( Vector AllTheData,String NameofFile ) throws Exception
  {
  if (AllTheData.size() > 0)
  {
  return buildTest(AllTheData,NameofFile,"");
  }
  else
  {
    return "";  
  }
  }

  public String buildTest( Vector AllTheData,String NameofFile,String PaperSize ) throws Exception
  {
    ACJEngine en=new ACJEngine();
    en.setDebugMode( true );
    en.setX11GfxAvailibility( false );
    en.setTargetOutputDevice( ACJConstants.PDF );
    String url="";

    ACJOutputProcessor eClient=new ACJOutputProcessor();
    String fontmappath=radian.web.tcmisResourceLoader.getfontpropertiespath();
    String templatpath=radian.web.tcmisResourceLoader.getlabeltempplatepath();
    String writefilepath=radian.web.tcmisResourceLoader.getsavelabelpath();
    String hosturl =radian.web.tcmisResourceLoader.gethosturl();
	//en.setCacheOption(true, ""+writefilepath+"boxlabel"+NameofFile+".joi");
    eClient.setPathForFontMapFile(fontmappath);

    try
    {
      en.readTemplate( ""+templatpath+"boxlabel"+PaperSize+".erw" );
    }
    catch ( Exception e )
    {
      //e.printStackTrace();
      return url;
    }
	//reoprtlog.info("buildTest    Start "+NameofFile.toString()+"    Size: "+AllTheData.size()+" ");
    try
    {
      AppDataHandler ds=new AppDataHandler();
      //register table...
      RegisterTable[] rt=getData( AllTheData );
      for ( int i=0; i < rt.length; i++ )
      {
        ds.RegisterTable( rt[i].getData(),rt[i].getName(),rt[i].getMethods(),rt[i].getWhere() );
      }
      en.setDataSource( ds );
    }
    catch ( Exception e )
    {
      //e.printStackTrace();
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
      return url;
    }

    try
    {
      eClient.setPDFProperty( "FileName",""+writefilepath+"boxlabel" + NameofFile + ".pdf" );
      eClient.generatePDF();
    }
    catch ( Exception e )
    {
      System.out.println( "\n\n---------ERROR generating report" );
      e.printStackTrace();
      return url;

    }
	//reoprtlog.info("buildTest    Done "+NameofFile.toString()+"");
    url=""+hosturl+"labels/" + NameofFile + ".pdf";
    return url;
  }

  public RegisterTable[] getData( Vector reportData1 ) throws Exception
  {
    RegisterTable[] rt=new RegisterTable[1];

    try
    {
      rt[0]=new RegisterTable( BoxLabelData.getVector( reportData1,7 ),"BOXLABEL",BoxLabelData.getFieldVector( 7 ),null );
    }
    catch ( Exception e1 )
    {
      e1.printStackTrace();
      throw e1;
    }
    return rt;
  }

}