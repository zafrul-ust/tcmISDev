package radian.web.barcode;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 * 06-25-04 Adding log statements to trace a memory usage issue
 */
import java.util.Vector;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;
import radian.tcmis.both1.reports.RegisterTable;


public class seagateBarCodeGenerator
{
	private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");
    public seagateBarCodeGenerator() {
    }

    public void buildPdfFile(Vector AllTheData, String NameofFile,String PaperSizeO) throws Exception
    {
        ACJEngine en = new ACJEngine();
        en.setDebugMode(true);
        en.setX11GfxAvailibility(false);
        en.setTargetOutputDevice(ACJConstants.PDF);

        ACJOutputProcessor eClient = new ACJOutputProcessor();
        String fontmappath=radian.web.tcmisResourceLoader.getfontpropertiespath();
        String templatpath=radian.web.tcmisResourceLoader.getlabeltempplatepath();
        String writefilepath=radian.web.tcmisResourceLoader.getsavelabelpath();
		    //en.setCacheOption(true, ""+writefilepath+"Barcode"+NameofFile.toString()+".joi");
        eClient.setPathForFontMapFile(fontmappath);

        try
        {
           en.readTemplate(""+templatpath+"seagatebig.erw");
        }
        catch (Exception e)
        {
            System.out.println("ERROR loading template");
            e.printStackTrace();
            throw e;
        }

        TemplateManager tm = en.getTemplateManager();
		//reoprtlog.info("buildPdfFile    Start "+NameofFile.toString()+"    Size: "+AllTheData.size()+" ");
        try
        {
        AppDataHandler ds = new AppDataHandler();
        //register table...
        RegisterTable[] rt = getData(AllTheData);
        for(int i=0;i<rt.length;i++)
        {
            Vector v1 = rt[i].getData();
            Vector v2 = rt[i].getMethods();
            ds.RegisterTable(rt[i].getData(),
                             rt[i].getName(),
                             rt[i].getMethods(),rt[i].getWhere());
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
            throw ex;
        }

        try
        {
            eClient.setPDFProperty("FileName",""+writefilepath+"Barcode"+NameofFile+".pdf");
            eClient.generatePDF();
        }
        catch (Exception e)
        {
            System.out.println("\n\n---------ERROR generating report");
            e.printStackTrace();
            throw e;
        }
		//reoprtlog.info("buildPdfFile    Done "+NameofFile.toString()+"");
    }

    public RegisterTable[] getData(Vector reportData1) throws Exception
    {
      RegisterTable[] rt=new RegisterTable[1];

      try
      {

        rt[0]=new RegisterTable( seagateBarCodeData.getVector( reportData1,24 ),"BARCODE",seagateBarCodeData.getFieldVector( 24 ),null );
      }
      catch ( Exception e1 )
      {
        e1.printStackTrace();
        throw e1;
      }
      return rt;
    }
}


