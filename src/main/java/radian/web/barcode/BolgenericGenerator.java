package radian.web.barcode;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Nawaz Shaik
 * @version
 *
 * 06-12-03 Code cleanup and added the option to generate generic BOLs
 * 06-25-04 Adding log statements to trace a memory usage issue
 */

import java.util.Vector;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;
import radian.tcmis.both1.reports.RegisterTable;

public class BolgenericGenerator
{
    private boolean picklistid = false;
	private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");
    public BolgenericGenerator() {
    }

    public void setpickbol(boolean id)
    {
      this.picklistid = id;
    }

    private boolean getpickbol()  throws Exception
    {
     return this.picklistid;
    }
    public String buildTest(Vector resultF,String NameofFile, String bolDetailsh) throws Exception
    {
        ACJEngine en = new ACJEngine();
        en.setDebugMode(true);
        en.setX11GfxAvailibility(false);
        en.setTargetOutputDevice(ACJConstants.PDF);
        TemplateManager tm = null;
		String url = "";

        ACJOutputProcessor eClient = new ACJOutputProcessor();
        String fontmappath=radian.web.tcmisResourceLoader.getfontpropertiespath();
        String templatpath=radian.web.tcmisResourceLoader.getlabeltempplatepath();
        String writefilepath=radian.web.tcmisResourceLoader.getsavelabelpath();
        String hosturl =radian.web.tcmisResourceLoader.gethosturl();
		//en.setCacheOption(true, ""+writefilepath+"Bol"+NameofFile+".joi");
        eClient.setPathForFontMapFile(fontmappath);

        try
        {
           if ("genericinv".equalsIgnoreCase(bolDetailsh))
           {
             en.readTemplate(""+templatpath+"Bolgenericinv.erw");
           }
           else if ("generic".equalsIgnoreCase(bolDetailsh))
           {
             en.readTemplate(""+templatpath+"Bolgeneric.erw");
           }
           else
           {
             en.readTemplate(""+templatpath+"Bolnodetails.erw");
           }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return url;
        }

        tm = en.getTemplateManager();

	//reoprtlog.info("buildTest    Start "+NameofFile.toString()+"    Size: "+resultF.size()+" ");

      try
      {
      AppDataHandler ds = new AppDataHandler();
      //register table...
      RegisterTable[] rt = getData(resultF);
      for(int i=0;i<rt.length;i++)
      {
          ds.RegisterTable(rt[i].getData(),rt[i].getName(),rt[i].getMethods(),rt[i].getWhere());
      }

      RegisterTable[] rt2 = getItemData(resultF);
      for(int i=0;i<rt2.length;i++)
      {
          ds.RegisterTable(rt2[i].getData(),rt2[i].getName(),rt2[i].getMethods(),rt2[i].getWhere());
      }

      if ("genericinv".equalsIgnoreCase(bolDetailsh))
      {
        RegisterTable[] rt1=getInvData( resultF );
        for ( int i=0; i < rt1.length; i++ )
        {
          ds.RegisterTable( rt1[i].getData(),rt1[i].getName(),rt1[i].getMethods(),rt1[i].getWhere() );
        }
      }

      en.setDataSource(ds);
      }
      catch(Exception e)
      {
          e.printStackTrace();
          throw e;
      }

      try
      {
          eClient.setReportData(en.generateReport());
      }
      catch(Exception ex)
      {
          System.out.println("\n\n---------ERROR: While generating report");
          ex.printStackTrace();
          return url;
      }

        try
        {
            eClient.setPDFProperty("FileName",""+writefilepath+"Bol"+NameofFile+".pdf");
            eClient.generatePDF();
        }
        catch (Exception e)
        {
            System.out.println("/n/n---------ERROR generating report");
            e.printStackTrace();
            return url;
        }
		//reoprtlog.info("buildTest    Done "+NameofFile.toString()+"");
        url = ""+hosturl+"labels/Bol"+NameofFile+".pdf";
        return url;
    }

    public RegisterTable[] getData( Vector reportData1 ) throws Exception
    {
      RegisterTable[] rt=new RegisterTable[1];

      try
      {
        rt[0]=new RegisterTable( BolData.getVector( reportData1,14 ),"BOLDATA",BolData.getFieldVector( 14 ),null );
      }
      catch ( Exception e1 )
      {
        e1.printStackTrace();
        throw e1;
      }
      return rt;
    }

    public RegisterTable[] getInvData( Vector reportData2 ) throws Exception
    {
      RegisterTable[] rt=new RegisterTable[1];

      try
      {
        rt[0]=new RegisterTable( BolInvData.getVector( reportData2 ),"BOLINVDATA",BolInvData.getFieldVector(),null );
      }
      catch ( Exception e1 )
      {
        e1.printStackTrace();
        throw e1;
      }
      return rt;
    }

    public RegisterTable[] getItemData( Vector reportData3 ) throws Exception
    {
      RegisterTable[] rt=new RegisterTable[1];

      try
      {
        rt[0]=new RegisterTable( BolitemData.getVector( reportData3 ),"BOLITEMDATA",BolitemData.getFieldVector(),null );
      }
      catch ( Exception e1 )
      {
        e1.printStackTrace();
        throw e1;
      }
      return rt;
    }

}


