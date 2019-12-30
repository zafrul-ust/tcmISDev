// FrontEnd Plus GUI for JAD
// DeCompiled : ReportObjNew.class

package radian.tcmis.server7.share.servlets;

///ACJEngine
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;
import radian.tcmis.both1.helpers.UsageReportDataServ;
import radian.tcmis.both1.reports.RegisterTable;
import radian.tcmis.server7.share.dbObjs.ReportData;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.share.helpers.HelpObjs;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 * 06-25-04 Adding log statements to trace a memory usage issue
 */
public class ReportObjNew extends TcmisServletObj
{
    public static final int KEYWORD = 0;
    public static final int CAS_NUM_SEARCH = 1;
    public static final int LIST = 2;
    public static final int ALL = 3;
    public static final int VOC = 4;
    public static final String groupString[] = {
        "Facility", "Work Area", "Cas #/SARA Group", "Delivery Point", "By Month"
    };
    public static final String groupStringLabel[] = {
        "Facility", "Work Area", "Cas #/SARA Group", "Delivery Point", "Month"
    };
    public static final String groupStringVoc[] = {
        "Facility", "Work Area", "By Month"
    };
    public static final String groupStringLabelVoc[] = {
        "Facility", "Work Area", "Month"
    };
    public static final int GROUP_BY_FAC = 0;
    public static final int GROUP_BY_WORK_AREA = 1;
    public static final int GROUP_BY_CAS_NUM = 2;
    public static final int GROUP_BY_DEL_POINT = 3;
    public static final int GROUP_BY_MONTH = 4;
    public static final String FACILITY = groupString[0];
    public static final String WORK_AREA = groupString[1];
    public static final String CAS_NUM = groupString[2];
    public static final String DEL_POINT = groupString[3];
    public static final String MONTH = groupString[4];
    //public static Hashtable ReportTable = new Hashtable();
    private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");

    private String function;
    private String facName;
    private String fileType;
    private String workAreaDesc;
    private String chemType;
    private String begMonth;
    private String begYear;
    private String endMonth;
    private String endYear;
    private Vector groupB;
    private Vector reportData;
    private String searchType;
    private String listId;
    private String keyword;
    private String cas_num;
    private String chemListDesc;
    private String URLS;
    private String newfilename;
    private int groupBy[];
    private int NoRecords;
    private Hashtable mySendObj;
    private Hashtable inData;
    private ACJEngine erw = null;
    private AppDataHandler ds = null;
    private OutputStream os = null;
    private TemplateManager tm = null;
    private ACJOutputProcessor ec = null;

    public ReportObjNew(ServerResourceBundle b, TcmISDBModel d)
    {
        function = null;
        mySendObj = null;
        inData = null;
        facName = "";
        fileType = "";
        workAreaDesc = "";
        chemType = "";
        begMonth = "";
        begYear = "";
        endMonth = "";
        endYear = "";
        groupB = new Vector();
        reportData = new Vector();
        searchType = "";
        listId = "";
        keyword = "";
        cas_num = "";
        chemListDesc = "";
        URLS = "";
        NoRecords = 0;
        erw = null;
        os = null;
        tm = null;
        ec = null;
        bundle = b;
        db = d;
    }

    public ReportObjNew(TcmISDBModel d, Hashtable ata)
    {
        function = null;
        mySendObj = null;
        inData = null;
        facName = "";
        fileType = "";
        workAreaDesc = "";
        chemType = "";
        begMonth = "";
        begYear = "";
        endMonth = "";
        endYear = "";
        groupB = new Vector();
        reportData = new Vector();
        searchType = "";
        listId = "";
        keyword = "";
        cas_num = "";
        chemListDesc = "";
        URLS = "";
        NoRecords = 0;
        erw = null;
        os = null;
        tm = null;
        ec = null;
        db = d;
        inData = ata;
    }

    protected void resetAllVars()
    {
        function = null;
        inData = null;
    }

    protected void print(TcmisOutputStreamServer out)
    {
        try
        {
            out.sendObject(mySendObj);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    protected void getResult() {
        inData = (Hashtable)myRecvObj;
        Hashtable retH = new Hashtable();
        mySendObj = new Hashtable();
        String action = inData.get("ACTION").toString().trim();
		  //record who's running this report
		  try {
			db.doUpdate("insert into formatted_report_usage(report_id,report_name,requested_by,requested_date,file_type) select formatted_report_usage_seq.nextval,'"+action+"',"+inData.get("USER_ID").toString().trim()+",sysdate,'"+inData.get("FILE_TYPE").toString()+"' from dual");
		  }catch (Exception e) {

		  }
		  //run report
		  if ("VOC_USAGE".equalsIgnoreCase(action) || "REPORTABLE_USAGE".equalsIgnoreCase(action)) {
          createReporttest();
        }
    }

    protected void createReporttest() {
      try {
        String client =  db.getClient().toString().trim();
        String userID = inData.get("USER_ID").toString().trim();
        ReportData repData = new ReportData(db);
        String sessionID = repData.createReportTemplate(userID,inData);
        Hashtable innerH = new Hashtable(2);
        innerH.put("SUCCEEDED",new Boolean(true));
        innerH.put("MSG","Your report was sent to the server for processing.\nIt will automatically be sent to you upon completion.\nYou may now resume other activities within tcmIS.");
        mySendObj.put("RETURN_CODE", innerH);
        String url = "/tcmIS/servlet/radian.web.servlets.reports.WebUsageFormated?sessionID="+sessionID+"&client="+client+"&userID="+userID;
        mySendObj.put("URL",url);
      }catch (Exception e) {
        Hashtable innerH = new Hashtable(2);
        innerH.put("SUCCEEDED",new Boolean(false));
        innerH.put("MSG","An error occurred while generating the report.\nIf the problem recurs, please contact your tcmIS Customer Service Representative(CSR).");
        mySendObj.put("RETURN_CODE", innerH);
      }
    } //end of method

    public Hashtable createReportNew(String sessionID, String userID) {
      Hashtable OutData = new Hashtable();
      try {
        ReportData repData = new ReportData(db);
        Hashtable tmpReportData = repData.loadReportTemplate(sessionID);
        OutData = createReport(tmpReportData);
        if (((Boolean)OutData.get("SUCCEED")).booleanValue()) {
          HelpObjs.deleteDataFromTable(db,"report_hash_table",sessionID);
        }
      }catch (Exception e) {
        e.printStackTrace();
        OutData.put("SUCCEED",new Boolean(false));
        OutData.put("MSG","An error occurred while generating the report.\nIf the problem recurs, please contact your tcmIS Customer Service Representative(CSR).");
      }
      return OutData;
    } //end of method


    public Hashtable createReport(Hashtable RepData) throws Exception
    {
        Hashtable OutData = new Hashtable();
        inData = RepData;

        try
        {
            function = RepData.get("ACTION").toString();
            facName = RepData.get("FACILITY").toString();
            workAreaDesc =  RepData.get("WORK_AREA").toString();
            chemType = RepData.get("KEYWORD").toString();
            begMonth = RepData.get("BEGMONTH").toString();
            begYear = RepData.get("BEGYEAR").toString();
            endMonth = RepData.get("ENDMONTH").toString();
            endYear = RepData.get("ENDYEAR").toString();
            searchType = RepData.get("SEARCH_TYPE").toString();
            listId = RepData.get("LIST_ID").toString();
            keyword = RepData.get("KEYWORD").toString();
            cas_num = RepData.get("CAS_NUM").toString();
            chemListDesc = RepData.get("CHEM_DESC").toString();
            groupBy = (int[])RepData.get("Group_Matrix");
            fileType = RepData.get("FILE_TYPE").toString();
            newfilename = RepData.get("USER_ID").toString().trim();
            if(function.equalsIgnoreCase("REPORTABLE_USAGE")) {
              OutData = buildUsageReport();
            }else if(function.equalsIgnoreCase("VOC_USAGE")) {
              OutData = buildVocUsageReport();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        return OutData;
    }

    //Nawaz 02-18-02
    public Hashtable createReportNew(Hashtable RepData) throws Exception {
      Hashtable OutData = new Hashtable();
      inData = RepData;
      try {
          function = RepData.get("ACTION").toString();
          facName = RepData.get("FACILITY").toString();
          workAreaDesc =  RepData.get("WORK_AREA").toString();
          chemType = RepData.get("KEYWORD").toString();
          begMonth = RepData.get("BEGMONTH").toString();
          begYear = RepData.get("BEGYEAR").toString();
          endMonth = RepData.get("ENDMONTH").toString();
          endYear = RepData.get("ENDYEAR").toString();
          searchType = RepData.get("SEARCH_TYPE").toString();
          listId = RepData.get("LIST_ID").toString();
          keyword = RepData.get("KEYWORD").toString();
          cas_num = RepData.get("CAS_NUM").toString();
          chemListDesc = RepData.get("CHEM_DESC").toString();
          String groupBys = RepData.get("GROUP_MATRIX").toString();
          if (groupBys.trim().length() == 1) {
              int g[] = new int[1];
              Integer em = new Integer(groupBys);
              int testin = em.intValue();
              g[0] = testin;
              groupBy = g;
          }else {
              StringTokenizer st = new StringTokenizer(groupBys,",");
              int i = 0;
              if (st.countTokens() > 1) {
                int g[] = new int[st.countTokens()];
                while (st.hasMoreTokens()) {
                    String tok = st.nextToken();
                    Integer em = new Integer(tok);
                    int testin = em.intValue();
                    g[i] = testin;
                    i++;
                }
                groupBy = g;
              }
          }
          fileType = RepData.get("FILE_TYPE").toString();
          newfilename = RepData.get("USER_ID").toString().trim();
          if(function.equalsIgnoreCase("REPORTABLE_USAGE")) {
            OutData = buildUsageReport();
          }else {
            if(function.equalsIgnoreCase("VOC_USAGE"))
              OutData = buildVocUsageReport();
          }
      }catch(Exception e) {
        e.printStackTrace();
        throw e;
      }
      return OutData;
    }

    protected Hashtable buildUsageReport() throws Exception {
      Hashtable retH = new Hashtable();
      Hashtable OutDataU = new Hashtable();
      String url = "";
      try {
        Vector reportData = ReportData.getReportableUsageReport(db, inData);
        if(reportData.size() > 0) {
          if(fileType.equalsIgnoreCase("PDF")){
            url = buildPDFUsageReport(reportData);
          }
          else if(fileType.equalsIgnoreCase("CSV")){
            url = buildXlsUsageReport(reportData);
          }

          reportData.clear();
        }
        else {
          NoRecords = 1;
        }
        if(url.length() > 0)
            OutDataU.put("SUCCEED", new Boolean(true));
        else OutDataU.put("SUCCEED", new Boolean(false));
        if(NoRecords == 0)
            OutDataU.put("MSG", " Generating report failed.\n Please try again.\n If problem recurs, contact tcmIS Customer Service Representative(CSR).");
        else if(NoRecords == 1)
            OutDataU.put("MSG", " No records were found matching your search criteria.\n Please choose different criteria and try again.");
        OutDataU.put("URL", url);

      }catch(Exception e) {
        e.printStackTrace();
        OutDataU.put("SUCCEED", new Boolean(false));
        OutDataU.put("MSG", " Generating report failed.\n Please try again.\n If problem recurs, contact tcmIS Customer Service Representative(CSR).");
        OutDataU.put("URL", url);
        throw e;
      }
      return OutDataU;
    }

    protected Hashtable buildVocUsageReport()
        throws Exception
    {
        Hashtable retH = new Hashtable();
        Hashtable OutDataU = new Hashtable();
        String url = "";
        try
        {
            Vector reportData = ReportData.getVocUsageReport(db,inData);
            if(reportData.size() > 0)
            {
                if(fileType.equalsIgnoreCase("PDF")){url = buildPDFUsageReport(reportData);}
                else if(fileType.equalsIgnoreCase("CSV")){url = buildXlsVocReport(reportData);}

                reportData.clear();
            }
            else
            {
                NoRecords = 1;
            }
            if(url.length() > 0)
                OutDataU.put("SUCCEED", new Boolean(true));
            else
                OutDataU.put("SUCCEED", new Boolean(false));
            if(NoRecords == 0)
                retH.put("MSG", " Generating report failed.\n Please try again.\n If problem recurs, contact tcmIS Customer Service Representative(CSR).");
            else
            if(NoRecords == 1)
                OutDataU.put("MSG", " No records were found matching your search criteria.\n Please choose different criteria and try again.");
            OutDataU.put("URL", url);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            OutDataU.put("SUCCEED", new Boolean(false));
            OutDataU.put("MSG", " Generating report failed.\n Please try again.\n If problem recurs, contact tcmIS Customer Service Representative(CSR).");
            OutDataU.put("URL", url);
            throw e;
        }
        return OutDataU;
    }

    //Nawaz 02-14-02
    protected String buildXlsVocReport(Vector FinalData) throws Exception
    {
        StringBuffer Msgxl = new StringBuffer();
        String url1 = "";

        Msgxl.append("<FONT FACE=\"Arial\">");
        Msgxl.append("<TABLE BORDER=\"0\">\n");
        Msgxl.append("<TR>\n");

        String s = "";
        if(searchType.equalsIgnoreCase("KEYWORD"))
            s = "Chemicals matching '" + keyword + "'";
        else
        if(searchType.equalsIgnoreCase("CAS_NUM"))
            s = "Cas Number" + cas_num + " Usage";
        else
        if(searchType.equalsIgnoreCase("LIST"))
            s = chemListDesc + " Usage";
        else
        if(searchType.equalsIgnoreCase("ALL"))
            s = "All Chemical Usage";
        else
        if(searchType.equalsIgnoreCase("VOC"))
            s = "VOC Usage Report";
        else
            s = "Usage Report";

        Msgxl.append("<TD COLSPAN=\"5\" ALIGN=\"CENTER\" ><B><FONT size=\"5\">"+s+"</FONT></B></TD>\n");
        Msgxl.append(getTimeDate());
        Msgxl.append("</TR>\n");
        Msgxl.append("<TR>\n");
        Msgxl.append("<TD COLSPAN=\"3\" ALIGN=\"LEFT\" ><B><FONT size=\"3\" >Selection Criterion</FONT></B></TD>\n");
        Msgxl.append("</TR>\n");
        Msgxl.append("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\"><FONT size=\"4\" >Facility: "+facName+"</FONT></TD></TR>\n");
        Msgxl.append("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\" ><FONT size=\"4\" >Work Area: "+workAreaDesc+"</FONT></TD></TR>\n");

        DateFormatSymbols dfs = new DateFormatSymbols();
        String months[] = dfs.getMonths();
        String desc = "";

        if(begMonth.equals(endMonth) && begYear.equals(endYear))
            desc = months[Integer.parseInt(begMonth)] + ", " + begYear;
        else
            desc = months[Integer.parseInt(begMonth)] + " " + begYear + " through " + months[Integer.parseInt(endMonth)] + " " + endYear;
        Msgxl.append("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\"><FONT size=\"4\" >Time Period: "+desc+"</FONT></TD></TR>\n");
        Msgxl.append("<TR><TD>&nbsp;</TD></TR>\n");
        Msgxl.append("<TR><TD>&nbsp;</TD></TR>\n");
        Msgxl.append("</TABLE>\n");

        Msgxl.append("<TABLE BORDER=\"1\">\n");
        Msgxl.append("<TR>\n");
        Msgxl.append("<TD ALIGN=\"CENTER\"><B>Facility</B></TD>\n");
        Msgxl.append("<TD ALIGN=\"CENTER\"><B>Work Area</B></TD>\n");
        Msgxl.append("<TD ALIGN=\"CENTER\"><B>Part Number</B></TD>\n");
        Msgxl.append("<TD ALIGN=\"CENTER\"><B>Trade Name</B></TD>\n");
        //Msgxl.append("<TD ALIGN=\"CENTER\"><B>Manufactures Name</B></TD>\n");
        Msgxl.append("<TD ALIGN=\"CENTER\"><B>Units Used</B></TD>\n");
        Msgxl.append("<TD ALIGN=\"CENTER\"><B>lbs. per Unit</B></TD>\n");
        Msgxl.append("<TD ALIGN=\"CENTER\"><B>lbs. Used</B></TD>\n");
        Msgxl.append("<TD ALIGN=\"CENTER\"><B>Wt % VOCs</B></TD>\n");
        Msgxl.append("<TD ALIGN=\"CENTER\"><B>Mixture VOC (lbs./unit)</B></TD>\n");
        Msgxl.append("<TD ALIGN=\"CENTER\"><B>lbs. Reportable</B></TD>\n");
		  Msgxl.append("<TD ALIGN=\"CENTER\"><B>Density (lb/gal)</B></TD>\n");
		  Msgxl.append("<TD ALIGN=\"CENTER\"><B>Volume (gal/unit)</B></TD>\n");
		  Msgxl.append("<TD ALIGN=\"CENTER\"><B>Volume Used (gal)</B></TD>\n");
		  Msgxl.append("</TR>\n");

        Hashtable Final = new Hashtable();
        Random rand = new Random();

        int tmpReq = rand.nextInt();
        Integer tmpReqNum = new Integer(tmpReq);

        String contents = "";
        contents += Msgxl;

        String file = "";
		String tempwritefilepath=radian.web.tcmisResourceLoader.getsaveltempreportpath();
        file = tempwritefilepath + newfilename+"VocUsageCsv"+tmpReqNum.toString()+".xls";

        File outFile = new File(file);

        try
        {
          FileOutputStream DBO = new FileOutputStream(file,true);
          byte outbuf[];
          outbuf = new byte[contents.length()];
          outbuf = contents.getBytes();
          DBO.write(outbuf);
          DBO.close();
        }
        catch(IOException ioe)
        {
        //System.out.println("\n\nError opening output file: ");
        System.out.println(String.valueOf(ioe));
        System.out.println("\n");
        return url1;
        }
        finally
        {
        Msgxl = new StringBuffer();
        }
        try
            {
                for(int j=0;j < FinalData.size();j++)
                {
                    String Color = " ";

                    if (j%2==0)
                    {
                        Color = "bgcolor=\"#dddddd\"";
                    }
                    else
                    {
                        Color = "bgcolor=\"#fcfcfc\"";
                    }

                    Final = (Hashtable)FinalData.elementAt(j);

                    Msgxl.append("<TR>\n");
                    Msgxl.append("<TD>");Msgxl.append(Final.get("FACILITY").toString().length()<1?"&nbsp;":Final.get("FACILITY").toString());Msgxl.append("</TD>\n");
                    Msgxl.append("<TD>");Msgxl.append(Final.get("WORK_AREA").toString().length()<1?"&nbsp;":Final.get("WORK_AREA").toString());Msgxl.append("</TD>\n");
                    Msgxl.append("<TD>");Msgxl.append(Final.get("FAC_PART_ID").toString().length()<1?"&nbsp;":Final.get("FAC_PART_ID").toString());Msgxl.append("</TD>\n");
                    Msgxl.append("<TD>");Msgxl.append(Final.get("TRADE_NAME").toString().length()<1?"&nbsp;":Final.get("TRADE_NAME").toString());Msgxl.append("</TD>\n");
                    //Msgxl.append("<TD>");Msgxl.append(Final.get("MFG_DESC").toString().length()<1?"&nbsp;":Final.get("MFG_DESC").toString());Msgxl.append("</TD>\n");
                    Msgxl.append("<TD>");Msgxl.append(Final.get("QTY_SHIPPED").toString().length()<1?"&nbsp;":Final.get("QTY_SHIPPED").toString());Msgxl.append("</TD>\n");
                    Msgxl.append("<TD>");Msgxl.append(Final.get("WT_PER_UNIT").toString().length()<1?"&nbsp;":Final.get("WT_PER_UNIT").toString());Msgxl.append("</TD>\n");
                    Msgxl.append("<TD>");Msgxl.append(Final.get("WT_SHIPPED").toString().length()<1?"&nbsp;":Final.get("WT_SHIPPED").toString());Msgxl.append("</TD>\n");
                    Msgxl.append("<TD>");Msgxl.append(Final.get("PERCENT").toString().length()<1?"&nbsp;":Final.get("PERCENT").toString());Msgxl.append("</TD>\n");
                    Msgxl.append("<TD>");Msgxl.append(Final.get("MIXTURE_VOC").toString().length()<1?"&nbsp;":Final.get("MIXTURE_VOC").toString());Msgxl.append("</TD>\n");
                    Msgxl.append("<TD>");Msgxl.append(Final.get("LBS_REPORTABLE").toString().length()<1?"&nbsp;":Final.get("LBS_REPORTABLE").toString());Msgxl.append("</TD>\n");
						  Msgxl.append("<TD>");Msgxl.append(Final.get("DENSITY_LB_PER_GAL").toString().length()<1?"&nbsp;":Final.get("DENSITY_LB_PER_GAL").toString());Msgxl.append("</TD>\n");
						  Msgxl.append("<TD>");Msgxl.append(Final.get("VOLUME_PER_UNIT_GAL").toString().length()<1?"&nbsp;":Final.get("VOLUME_PER_UNIT_GAL").toString());Msgxl.append("</TD>\n");
						  Msgxl.append("<TD>");Msgxl.append(Final.get("VOLUME_GAL").toString().length()<1?"&nbsp;":Final.get("VOLUME_GAL").toString());Msgxl.append("</TD>\n");
						  Msgxl.append("</TR>\n");

                    boolean status = true;

                    if ( (j%50==0) && (j != 0) )
                    {
                      contents = "";
                      contents += Msgxl;

                      try
                      {
                        FileOutputStream DBO = new FileOutputStream(file,status);
                        byte outbuf[];
                        outbuf = new byte[contents.length()];
                        outbuf = contents.getBytes();
                        DBO.write(outbuf);
                        DBO.close();
                      }
                      catch(IOException ioe)
                      {
                      System.out.println("\n\nError opening output file: ");
                      System.out.println(String.valueOf(ioe));
                      System.out.println("\n");
                      return url1;
                      }
                      finally
                      {
                      Msgxl = new StringBuffer();
                      }
                    }
                }
            }
            catch(Exception e1)
            {
                e1.printStackTrace();
                return url1;
            }
            finally
            {

            }

            Msgxl.append("</TABLE></FONT>");
            contents = "";
            contents += Msgxl;
            try
            {
              FileOutputStream DBO = new FileOutputStream(file,true);
              byte outbuf[];
              outbuf = new byte[contents.length()];
              outbuf = contents.getBytes();
              DBO.write(outbuf);
              DBO.close();
            }
            catch(IOException ioe)
            {
            System.out.println("\n\nError opening output file: ");
            System.out.println(String.valueOf(ioe));
            System.out.println("\n");
            return url1;
            }
            finally
            {
            Msgxl = new StringBuffer();
            }

			String tempurlpath=radian.web.tcmisResourceLoader.gettempreporturl();
			String nostname =radian.web.tcmisResourceLoader.gethosturl();
            url1 = nostname + tempurlpath + newfilename+"VocUsageCsv"+tmpReqNum.toString()+".xls";
            return url1;
    }

    //Nawaz 02-14-02
     protected String buildXlsUsageReport(Vector FinalData) throws Exception
    {
        StringBuffer Msgxl = new StringBuffer();
        String url1 = "";

        Msgxl.append("<FONT FACE=\"Arial\">");
        Msgxl.append("<TABLE BORDER=\"0\">\n");
        Msgxl.append("<TR>\n");

        String s = "";
        if(searchType.equalsIgnoreCase("KEYWORD"))
            s = "Chemicals matching '" + keyword + "'";
        else
        if(searchType.equalsIgnoreCase("CAS_NUM"))
            s = "Cas Number" + cas_num + " Usage";
        else
        if(searchType.equalsIgnoreCase("LIST"))
            s = chemListDesc + " Usage";
        else
        if(searchType.equalsIgnoreCase("ALL"))
            s = "All Chemical Usage";
        else
        if(searchType.equalsIgnoreCase("VOC"))
            s = "VOC Usage Report";
        else
            s = "Usage Report";

        Msgxl.append("<TD COLSPAN=\"5\" ALIGN=\"CENTER\" ><B><FONT size=\"5\">"+s+"</FONT></B></TD>\n");
        Msgxl.append(getTimeDate());
        Msgxl.append("</TR>\n");
        Msgxl.append("<TR>\n");
        Msgxl.append("<TD COLSPAN=\"3\" ALIGN=\"LEFT\" ><B><FONT size=\"3\" >Selection Criterion</FONT></B></TD>\n");
        Msgxl.append("</TR>\n");
        Msgxl.append("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\"><FONT size=\"4\" >Facility: "+facName+"</FONT></TD></TR>\n");
        Msgxl.append("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\" ><FONT size=\"4\" >Work Area: "+workAreaDesc+"</FONT></TD></TR>\n");

        DateFormatSymbols dfs = new DateFormatSymbols();
        String months[] = dfs.getMonths();
        String desc = "";

        if(begMonth.equals(endMonth) && begYear.equals(endYear))
            desc = months[Integer.parseInt(begMonth)] + ", " + begYear;
        else
            desc = months[Integer.parseInt(begMonth)] + " " + begYear + " through " + months[Integer.parseInt(endMonth)] + " " + endYear;
        Msgxl.append("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\"><FONT size=\"4\" >Time Period: "+desc+"</FONT></TD></TR>\n");
        Msgxl.append("<TR><TD>&nbsp;</TD></TR>\n");
        Msgxl.append("<TR><TD>&nbsp;</TD></TR>\n");
        Msgxl.append("</TABLE>\n");

        Msgxl.append("<TABLE BORDER=\"1\">\n");
        Msgxl.append("<TR>\n");
        Msgxl.append("<TD ALIGN=\"CENTER\"><B>Facility</B></TD>\n");
        Msgxl.append("<TD ALIGN=\"CENTER\"><B>Work Area</B></TD>\n");
        Msgxl.append("<TD ALIGN=\"CENTER\"><B>Cas #/SARA Group</B></TD>\n");
        Msgxl.append("<TD ALIGN=\"CENTER\"><B>Constituent</B></TD>\n");
        Msgxl.append("<TD ALIGN=\"CENTER\"><B>Part Number</B></TD>\n");
        Msgxl.append("<TD ALIGN=\"CENTER\"><B>Trade Name</B></TD>\n");
        //Msgxl.append("<TD ALIGN=\"CENTER\"><B>Manufactures Name</B></TD>\n");
        Msgxl.append("<TD ALIGN=\"CENTER\"><B>Delivery Point</B></TD>\n");
        Msgxl.append("<TD ALIGN=\"CENTER\"><B>Units Used</B></TD>\n");
        Msgxl.append("<TD ALIGN=\"CENTER\"><B>lbs. per Unit</B></TD>\n");
        Msgxl.append("<TD ALIGN=\"CENTER\"><B>lbs. Used</B></TD>\n");
        Msgxl.append("<TD ALIGN=\"CENTER\"><B>% Wt. of Constituent</B></TD>\n");
        Msgxl.append("<TD ALIGN=\"CENTER\"><B>lbs. Reportable</B></TD>\n");
        Msgxl.append("</TR>\n");

        Hashtable Final = new Hashtable();
        Random rand = new Random();

        int tmpReq = rand.nextInt();
        Integer tmpReqNum = new Integer(tmpReq);

        String contents = "";
        contents += Msgxl;

        String file = "";
		String tempwritefilepath=radian.web.tcmisResourceLoader.getsaveltempreportpath();
        file = tempwritefilepath + newfilename+"UsageCsv"+tmpReqNum.toString()+".xls";

        File outFile = new File(file);

        try
        {
          FileOutputStream DBO = new FileOutputStream(file,true);
          byte outbuf[];
          outbuf = new byte[contents.length()];
          outbuf = contents.getBytes();
          DBO.write(outbuf);
          DBO.close();
        }
        catch(IOException ioe)
        {
        System.out.println("\n\nError opening output file: ");
        System.out.println(String.valueOf(ioe));
        System.out.println("\n");
        return url1;
        }
        finally
        {
        Msgxl = new StringBuffer();
        }
        try
            {
                for(int j=0;j < FinalData.size();j++)
                {
                    String Color = " ";

                    if (j%2==0)
                    {
                        Color = "bgcolor=\"#dddddd\"";
                    }
                    else
                    {
                        Color = "bgcolor=\"#fcfcfc\"";
                    }

                    Final = (Hashtable)FinalData.elementAt(j);

                    Msgxl.append("<TR>\n");
                    Msgxl.append("<TD>");Msgxl.append(Final.get("FACILITY").toString().length()<1?"&nbsp;":Final.get("FACILITY").toString());Msgxl.append("</TD>\n");
                    Msgxl.append("<TD>");Msgxl.append(Final.get("WORK_AREA").toString().length()<1?"&nbsp;":Final.get("WORK_AREA").toString());Msgxl.append("</TD>\n");
                    Msgxl.append("<TD>");Msgxl.append(Final.get("CAS_NUMBER").toString().length()<1?"&nbsp;":Final.get("CAS_NUMBER").toString());Msgxl.append("</TD>\n");
                    Msgxl.append("<TD>");Msgxl.append(Final.get("RPT_CHEMICAL").toString().length()<1?"&nbsp;":Final.get("RPT_CHEMICAL").toString());Msgxl.append("</TD>\n");

                    Msgxl.append("<TD>");Msgxl.append(Final.get("FAC_PART_ID").toString().length()<1?"&nbsp;":Final.get("FAC_PART_ID").toString());Msgxl.append("</TD>\n");
                    Msgxl.append("<TD>");Msgxl.append(Final.get("TRADE_NAME").toString().length()<1?"&nbsp;":Final.get("TRADE_NAME").toString());Msgxl.append("</TD>\n");
                    //Msgxl.append("<TD>");Msgxl.append(Final.get("MFG_DESC").toString().length()<1?"&nbsp;":Final.get("MFG_DESC").toString());Msgxl.append("</TD>\n");
                    Msgxl.append("<TD>");Msgxl.append(Final.get("DELIVERY_POINT").toString().length()<1?"&nbsp;":Final.get("DELIVERY_POINT").toString());Msgxl.append("</TD>\n");
                    Msgxl.append("<TD>");Msgxl.append(Final.get("QTY_SHIPPED").toString().length()<1?"&nbsp;":Final.get("QTY_SHIPPED").toString());Msgxl.append("</TD>\n");
                    Msgxl.append("<TD>");Msgxl.append(Final.get("WT_PER_UNIT").toString().length()<1?"&nbsp;":Final.get("WT_PER_UNIT").toString());Msgxl.append("</TD>\n");
                    Msgxl.append("<TD>");Msgxl.append(Final.get("WT_SHIPPED").toString().length()<1?"&nbsp;":Final.get("WT_SHIPPED").toString());Msgxl.append("</TD>\n");
                    Msgxl.append("<TD>");Msgxl.append(Final.get("PERCENT").toString().length()<1?"&nbsp;":Final.get("PERCENT").toString());Msgxl.append("</TD>\n");
                    Msgxl.append("<TD>");Msgxl.append(Final.get("LBS_REPORTABLE").toString().length()<1?"&nbsp;":Final.get("LBS_REPORTABLE").toString());Msgxl.append("</TD>\n");

                    Msgxl.append("</TR>\n");

                    boolean status = true;

                    if ( (j%50==0) && (j != 0) )
                    {
                      contents = "";
                      contents += Msgxl;

                      try
                      {
                        FileOutputStream DBO = new FileOutputStream(file,status);
                        byte outbuf[];
                        outbuf = new byte[contents.length()];
                        outbuf = contents.getBytes();
                        DBO.write(outbuf);
                        DBO.close();
                      }
                      catch(IOException ioe)
                      {
                      System.out.println("\n\nError opening output file: ");
                      System.out.println(String.valueOf(ioe));
                      System.out.println("\n");
                      return url1;
                      }
                      finally
                      {
                      Msgxl = new StringBuffer();
                      }
                    }
                }
            }
            catch(Exception e1)
            {
                e1.printStackTrace();
                return url1;
            }
            finally
            {

            }

            Msgxl.append("</TABLE></FONT>");
            contents = "";
            contents += Msgxl;
            try
            {
              FileOutputStream DBO = new FileOutputStream(file,true);
              byte outbuf[];
              outbuf = new byte[contents.length()];
              outbuf = contents.getBytes();
              DBO.write(outbuf);
              DBO.close();
            }
            catch(IOException ioe)
            {
            System.out.println("\n\nError opening output file: ");
            System.out.println(String.valueOf(ioe));
            System.out.println("\n");
            return url1;
            }
            finally
            {
            Msgxl = new StringBuffer();
            }

			String tempurlpath=radian.web.tcmisResourceLoader.gettempreporturl();
			String nostname =radian.web.tcmisResourceLoader.gethosturl();
            url1 = nostname + tempurlpath + newfilename+"UsageCsv"+tmpReqNum.toString()+".xls";
            return url1;
    }

    //Nawaz 02-14-02
    public String getTimeDate()
    {
    Calendar cal = Calendar.getInstance();
    String cdate = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
    int am_pm = cal.get(Calendar.AM_PM);
    String am = "";
    if (am_pm == 0)
    {
    am = "AM";
    }
    else if (am_pm == 1)
    {
    am = "PM";
    }
    int min = cal.get(Calendar.MINUTE);
    int hours = cal.get(Calendar.HOUR);
    String time = "";

    if (hours < 10){ time += "0"+hours;} else {time += hours;}
    if (min < 10){time += ":0"+min;}else {time += ":"+min;}
    time += " "+am;

    return "<TD COLSPAN=\"8\" ALIGN=\"CENTER\"><FONT size=\"4\" >Date: "+cdate+" Time: "+time+"</FONT></TD>\n";
    }

    protected String buildPDFUsageReport(Vector lineItems)
    {
        String url = "";
        /*
        erw = new ACJEngine();
        ec = new ACJOutputProcessor();
        */
        erw = new ACJEngine();
        erw.setDebugMode(false);
        erw.setX11GfxAvailibility(false);
        erw.setTargetOutputDevice(ACJConstants.PDF);
        ec = new ACJOutputProcessor();
        ds = new AppDataHandler();

        String templatpath=radian.web.tcmisResourceLoader.getreportstempplatepath();
        String fontmappath=radian.web.tcmisResourceLoader.getfontpropertiespath();
        ec.setPathForFontMapFile(fontmappath);

        try
        {
            if(function.equalsIgnoreCase("REPORTABLE_USAGE")) {
              erw.readTemplate(""+templatpath+"UsageReport.erw");
            }else if(function.equalsIgnoreCase("VOC_USAGE")) {
              erw.readTemplate(""+templatpath+"VOC.erw");
            }
        }catch(Exception e) {
          //System.out.println("ERROR loading template");
          e.printStackTrace();
          String s = url;
          return s;
        }
        tm = erw.getTemplateManager();
        modifyTemplate(tm);

        Random rand = new Random();
        int tmpReq = rand.nextInt();
        Integer tmpReqNum = new Integer(tmpReq);
        reoprtlog.info("buildPDFUsageReport   Start "+tmpReqNum.toString()+"    Size: "+lineItems.size()+"  ");
        String tempwritefilepath=radian.web.tcmisResourceLoader.getsaveltempreportpath();
        erw.setCacheOption(true, ""+tempwritefilepath+"VocUsagePdf"+tmpReqNum.toString()+".joi");

        RegisterTable rt[] = getData(lineItems);
        for(int i = 0; i < rt.length; i++) {
          ds.RegisterTable(rt[i].getData(), rt[i].getName(), rt[i].getMethods(), rt[i].getWhere());
        }

        try {
          erw.setDataSource(ds);
        }catch(Exception e) {
          e.printStackTrace();
        }

        try {
          ec.setReportData(erw.generateReport());
          if(function.equalsIgnoreCase("REPORTABLE_USAGE"))
          {
            ec.setPDFProperty("FileName",""+tempwritefilepath+newfilename+"UsagePdf"+tmpReqNum.toString()+".pdf");
            ec.setPDFProperty("ZipCompressed",new Boolean(false));
          }else if(function.equalsIgnoreCase("VOC_USAGE")) {
            ec.setPDFProperty("FileName",""+tempwritefilepath+newfilename+"VocUsagePdf"+tmpReqNum.toString()+".pdf");
            ec.setPDFProperty("ZipCompressed",new Boolean(false));
          }

          ec.generatePDF();
        }catch(Exception e) {
          //System.out.println("ERROR generating report");
          e.printStackTrace();
          String s2 = url;
          return s2;
        }

        reoprtlog.info("buildPDFUsageReport    Done  "+tmpReqNum.toString()+"");

        String tempurlpath=radian.web.tcmisResourceLoader.gettempreporturl();
        String nostname =radian.web.tcmisResourceLoader.gethosturl();

        if(function.equalsIgnoreCase("REPORTABLE_USAGE")) {
          url = nostname + tempurlpath + newfilename+"UsagePdf"+tmpReqNum.toString()+".pdf";
        }else if(function.equalsIgnoreCase("VOC_USAGE")) {
          url = nostname + tempurlpath + newfilename+"VocUsagePdf"+tmpReqNum.toString()+".pdf";
        }

        return url;
    } //end of method

    public RegisterTable[] getData(Vector reportData1)
    {
        int g[] = new int[groupBy.length];
        for(int i = 0; i < g.length; i++)
            switch(groupBy[i])
            {
            case 0: // '\0'
                g[i] = 2;
                break;

            case 2: // '\002'
                g[i] = 0;
                break;

            case 4: // '\004'
                g[i] = 1;
                break;

            case 3: // '\003'
                g[i] = 4;
                break;

            case 1: // '\001'
                g[i] = 3;
                break;

            default:
                g[i] = -1;
                break;
            }

        RegisterTable rt[] = new RegisterTable[1];
        rt[0] = new RegisterTable(UsageReportDataServ.getVector(reportData1, g), "UsageReportData", UsageReportDataServ.getFieldVector(), null);
        return rt;
    }

    public void modifyTemplate(TemplateManager tm)
    {
        tm.setLabel("FACILITY_LABEL", facName);
        tm.setLabel("WORK_AREA_LABEL", workAreaDesc);
        String s = "";
        if(searchType.equalsIgnoreCase("KEYWORD"))
            s = "Chemicals matching '" + keyword + "'";
        else
        if(searchType.equalsIgnoreCase("CAS_NUM"))
            s = "Cas Number" + cas_num + " Usage";
        else
        if(searchType.equalsIgnoreCase("LIST"))
            s = chemListDesc + " Usage";
        else
        if(searchType.equalsIgnoreCase("ALL"))
            s = "All Chemical Usage";
        else
        if(searchType.equalsIgnoreCase("VOC"))
            s = "VOC Usage Report";
        else
            s = "Usage Report";
        tm.setLabel("REPORT_NAME", s);
        DateFormatSymbols dfs = new DateFormatSymbols();
        String months[] = dfs.getMonths();
        String desc = "";
        if(begMonth.equals(endMonth) && begYear.equals(endYear))
            desc = months[Integer.parseInt(begMonth)] + ", " + begYear;
        else
            desc = months[Integer.parseInt(begMonth)] + " " + begYear + " through " + months[Integer.parseInt(endMonth)] + " " + endYear;
        tm.setLabel("RP_HEAD_DESC", desc);
        int temp[] = {
            -1, -1, -1, -1, -1
        };
        temp[4] = groupBy[groupBy.length - 1];
        for(int i = 0; i < groupBy.length - 1; i++)
            temp[i] = groupBy[i];

        for(int i = 0; i < temp.length; i++)
        {
            String z = "GROUP_" + i + "_HEAD";
            if(temp[i] == -1)
            {
                tm.setVisible(z, false);
                z = "GROUP_" + i + "_FOOT";
                tm.setVisible(z, false);
            } else
            {
                tm.setVisible(z, true);
                z = "GROUP_" + i + "_FOOT";
                tm.setVisible(z, true);
                z = "HEAD_" + i + "_LABEL";
                tm.setLabel(z, groupStringLabel[temp[i]]);
            }
        }

    }

}
