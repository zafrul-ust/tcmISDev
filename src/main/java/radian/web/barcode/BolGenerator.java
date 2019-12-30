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

import java.math.BigDecimal;
import java.util.Vector;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;

import radian.tcmis.both1.reports.RegisterTable;

import java.util.Hashtable;

import org.apache.commons.lang.StringUtils;

public class BolGenerator
{
    private boolean picklistid = false;
	//private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");
    public BolGenerator() {
    }

    public void setpickbol(boolean id)
    {
      this.picklistid = id;
    }

    private boolean getpickbol()  throws Exception
    {
     return this.picklistid;
    }
    public String buildTest(String Where,Vector resultF,String NameofFile, String bolDetailsh, boolean tusconhub) throws Exception
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

        boolean baeNashua = false;
        try
        {
            for(int i = 0; i < resultF.size(); i++)
            {
            //System.out.print(i);
	            Hashtable h = (Hashtable)resultF.elementAt(i);
	            String inventoryGroup = h.get("INVENTORY_GROUP").toString();
	            if ("Boston BAE-NH".equalsIgnoreCase(inventoryGroup) || "Hudson PTP".equalsIgnoreCase(inventoryGroup)
	                    || "Londonderry MAN".equalsIgnoreCase(inventoryGroup) || "Merrimack MER".equalsIgnoreCase(inventoryGroup)
	                    || "Nashua NCA".equalsIgnoreCase(inventoryGroup) || "Nashua NHQ".equalsIgnoreCase(inventoryGroup)
	                    || "Nashua MEC".equalsIgnoreCase(inventoryGroup)  ||  "BAE Austin".equalsIgnoreCase(inventoryGroup)){
	                baeNashua = true;                
	            }
	         
	            String weightString = (String) h.get("NET_WEIGHT");
                if(!StringUtils.isBlank(weightString)) {
                	String[] weight = weightString.split("\n\n");
                	String newWeightString = "";
                	for(int k=0; k< weight.length; k++) {
                		BigDecimal kg = (new BigDecimal(weight[k])).multiply(new BigDecimal(".453")).setScale(3, BigDecimal.ROUND_HALF_UP);
                    	if(k == 0)
                    		newWeightString = weight[k]+"lb\n("+kg+"kg)";
                    	else
                    		newWeightString += "\n"+weight[k]+"lb\n("+kg+"kg)";
                	}
                	h.put("NET_WEIGHT", newWeightString);
                }
	            
            }
      }
      catch(Exception e11)
      {
          e11.printStackTrace();
      }

        try
        {
           if ("Yes".equalsIgnoreCase(bolDetailsh) && baeNashua)
           {
           	   en.readTemplate( "" + templatpath + "Bolnashua.erw" );
           }
           else if ("Yes".equalsIgnoreCase(bolDetailsh))
           {
             /*if (tusconhub)
			 {
			   en.readTemplate( "" + templatpath + "Bol2103.erw" );
			 }
			 else*/
			 {
			   en.readTemplate( "" + templatpath + "Bol.erw" );
		     }
           }
           else if ("generic".equalsIgnoreCase(bolDetailsh))
           {
             en.readTemplate(""+templatpath+"Bolgeneric.erw");
           }
           else
           {
              if (baeNashua)
			  {
				en.readTemplate( "" + templatpath + "Bolnodetailsnashua.erw" );
			  }
			  else
			  {
				en.readTemplate( "" + templatpath + "Bolnodetails.erw" );
		      }
           }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return url;
        }

        tm = en.getTemplateManager();
        tm.setWHEREClause("SEC_00",Where);

        if (this.getpickbol())
        {
         String Where1 = "BOLDATA.PR_NUMBER = @PR_NUMBER_MASTER@ AND BOLDATA.LINE_ITEM = @MASTER_LINE_ITEM@ AND BOLDATA.PICKLIST_ID = @PICKLISTID@ ";
         String Where2 = "BOLDATA.PR_NUMBER = @PR_NUMBER_MASTER@ AND BOLDATA.LINE_ITEM = @MASTER_LINE_ITEM@ AND BOLDATA.PICKLIST_ID = @PICKLISTID@ and BOLDATA.ITEM_ID = @ITEMID@ ";
         tm.setWHEREClause("SEC_01",Where1);
         if ("Yes".equalsIgnoreCase(bolDetailsh))
         {
         tm.setWHEREClause("SEC_02",Where2);
         }
        }

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
          //System.out.println("\n\n---------ERROR: While generating report");
          ex.printStackTrace();
          return url;
      }

        try
        {
            eClient.setPDFProperty("FileName",""+writefilepath+"Bol"+NameofFile+".pdf");
            eClient.generatePDF();
        }
        catch (Throwable e)
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
              //e1.printStackTrace();
        throw e1;
      }
      return rt;
    }
}


