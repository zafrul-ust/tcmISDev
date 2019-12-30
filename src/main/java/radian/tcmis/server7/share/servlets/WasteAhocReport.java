package radian.tcmis.server7.share.servlets;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 *
 * 01-28-04 Making relative URLs to go to the property file to get hosturl
 * 06-25-04 Adding log statements to trace a memory usage issue
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.dbObjs.WasteAhocReportDBObj;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.share.helpers.HelpObjs;

public class WasteAhocReport extends TcmisServletObj{
    //Client Version
    private String function = null;
    private Hashtable mySendObj = null;
    private Hashtable inData = null;
    private Vector reportfields = new Vector();
    private Vector reportData = new Vector();
    private int reportsize = 0;
    private int NoRecords = 0;
    private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");

    public WasteAhocReport(ServerResourceBundle b, TcmISDBModel d ){
        super();
        bundle = b;
        db = d;
    }

    protected void resetAllVars(){
        function = null;
        inData = null;
    }
    protected void print(TcmisOutputStreamServer out){
        try {
            out.sendObject((Hashtable) mySendObj);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    protected void getResult(){
        mySendObj = new Hashtable();
        // using myRecvObj you get the function the way you want
        inData = (Hashtable)myRecvObj;
        //System.out.println("in data: " + inData);
        String function = (String)inData.get("ACTION");

        if (function.equalsIgnoreCase("GET_WASTE_TEMPLATE_INFO")) {
            getWasteTemplateInfo();
        }else if (function.equalsIgnoreCase("GET_TEMPLATES")) {
            getWasteTemplate();
        }else if (function.equalsIgnoreCase("UPDATE_TEMPLATE")) {
            updateWasteTemplate();
        }else if (function.equalsIgnoreCase("LOAD_WASTE_TEMPLATE_INFO")) {
            loadWasteTemplate();
        }else if (function.equalsIgnoreCase("CREATE_REPORT")) {
            //createReport();
            createReporttest();
        }else if (function.equalsIgnoreCase("SEARCH_WASTE_PROFILE")) {
            searchWasteProfile();
        }else if (function.equalsIgnoreCase("SEARCH_WASTE_MGMT_OPTION")) {
            searchWasteMgmtOpt();
        }
        //System.out.println("outData:"+mySendObj);
    }

    protected void searchWasteMgmtOpt() {
        Vector v = new Vector();
        try {
            WasteAhocReportDBObj wahr = new WasteAhocReportDBObj(db);
            v = wahr.getWasteMgmtOpt(inData);
            mySendObj.put("WASTE_MGMT_OPTION_INFO",v);
            //Nawaz 19/09/01 new changes to gui tables
            mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
        }catch(Exception e){
            e.printStackTrace();
            mySendObj.put("WASTE_MGMT_OPTION_INFO",v);
        }
    }

    protected void searchWasteProfile() {
        Vector v = new Vector();
        try {
            WasteAhocReportDBObj wahr = new WasteAhocReportDBObj(db);
            v = wahr.getWasteProfile(inData);
            mySendObj.put("WASTE_PROFILE_INFO",v);
            //Nawaz 19/09/01 new changes to gui tables
            mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
        }catch(Exception e){
            e.printStackTrace();
            mySendObj.put("WASTE_PROFILE_INFO",v);
        }
    }

    protected void createReporttest() {
      try {
        String client =  db.getClient().toString().trim();
        String userID = (String)inData.get("USER_ID").toString().trim();
        WasteAhocReportDBObj wDBObj = new WasteAhocReportDBObj(db);
        String sessionID = wDBObj.createReportTemplate(userID,inData);
        mySendObj.put("SUCCEED",new Boolean(true));
        mySendObj.put("MSG","Your report was sent to the server for processing.\nIt will automatically be sent to you upon completion.\nYou may now resume other activities within tcmIS.");
        String url = "/tcmIS/servlet/radian.web.servlets.reports.WebWasteAdhoc?sessionID="+sessionID+"&client="+client+"&userID="+userID;
        mySendObj.put("URL",url);
      }catch (Exception e) {
        mySendObj.put("SUCCEED",new Boolean(false));
        mySendObj.put("MSG","An error occurred while generating the report.\nIf the problem recurs, please contact your tcmIS Customer Service Representative(CSR).");
      }
    } //end of method

    public Hashtable createReportNew(String sessionID, String userID) {
      Hashtable OutData = new Hashtable();
      try {
        WasteAhocReportDBObj wDBObj = new WasteAhocReportDBObj(db);
        Hashtable tmpReportData = new Hashtable(5);
        Hashtable innerH = wDBObj.loadReportTemplate(sessionID);
        innerH.put("METHOD","Active");
        tmpReportData.put("SELECTED_DATA",innerH);
        tmpReportData.put("USER_ID",userID);
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


    public Hashtable createReport(Hashtable RepData)throws Exception
    {
        Hashtable OutData = new Hashtable();
        Hashtable SelectedData = new Hashtable();
        Hashtable Result = new Hashtable();
        StringBuffer Msg = new StringBuffer();
        String url = "";
        Random rand = new Random();
        int tmpReq = rand.nextInt();
        Integer tmpReqNum = new Integer(tmpReq);

        try
        {
            Hashtable retH = new Hashtable();
            SelectedData = (Hashtable)RepData.get("SELECTED_DATA");
            String userId = (String)RepData.get("USER_ID");
            String method = SelectedData.get("METHOD").toString();
            reportfields = (Vector)SelectedData.get("REPORT_FIELDS");
            //System.out.println("In  createreport   ----"+reportfields+"\n\n");
            reoprtlog.info("createReport   Start "+tmpReqNum.toString()+" ");
            WasteAhocReportDBObj ahr = new WasteAhocReportDBObj(db);

            Msg.append(modifyTemplate(SelectedData,"Waste Report"));

            Result = ahr.getReportData(db,SelectedData,userId);

            Msg.append(Result.get("STRING_BUFFER").toString());
            Msg.append("</TABLE></FONT>");

            if (!(Result.get("RECORDS").toString().trim().equalsIgnoreCase("0")))
            {
              String contents = "";
              contents += Msg;

              String file="";

              if ( method.equalsIgnoreCase( "Batch" ) )
              {
                    String writefilepath=radian.web.tcmisResourceLoader.getsavelreportpath();
                    file= writefilepath + "WasteAdhoc" + tmpReqNum.toString() + ".xls";
              }
              else
              {
                    String tempwritefilepath=radian.web.tcmisResourceLoader.getsaveltempreportpath();
                    file= tempwritefilepath + "WasteAdhoc" + tmpReqNum.toString() + ".xls";
              }

              try
              {
                File outFile = new File(file);
                FileOutputStream DBO = new FileOutputStream(outFile);
                byte outbuf[];
                outbuf = new byte[contents.length()];
                outbuf = contents.getBytes();
                DBO.write(outbuf);
                DBO.close();
              }
              catch(IOException ioe)
              {
                //System.out.println("\n\nError opening output file: ");
                throw ioe;
              }
			  reoprtlog.info("createReport    Done  "+tmpReqNum.toString()+"");
              String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
              wwwHome = wwwHome.substring(0,(wwwHome.length()-1));
              if ( method.equalsIgnoreCase( "Batch" ) )
              {
                    String urlpath=radian.web.tcmisResourceLoader.getreporturl();
                    url= wwwHome + urlpath + "WasteAdhoc" + tmpReqNum.toString() + ".xls";
              }
              else
              {
                    String tempurlpath=radian.web.tcmisResourceLoader.gettempreporturl();
                    url= wwwHome + tempurlpath + "WasteAdhoc" + tmpReqNum.toString() + ".xls";
              }
            }
            else
            {
                NoRecords = 1;
            }

            if (url.length() > 0)
            {
                OutData.put("SUCCEED",new Boolean(true));
            }
            else
            {
                OutData.put("SUCCEED",new Boolean(false));
                if ((Result.get("RECORDS").toString().trim().equalsIgnoreCase("Error")))
                {
                    OutData.put("MSG",
                                  " Generating report failed.\n Please try again.\n If problem recurs, contact tcmIS Customer Service Representative(CSR).");
                }
                else if ((Result.get("RECORDS").toString().trim().equalsIgnoreCase("0")))
                {
                    OutData.put("MSG",
                                  " No records were found matching your search criteria.\n Please choose different criteria and try again.");
                }
            }
            OutData.put("URL",url);
        }
        catch(Exception e){
            e.printStackTrace();
            OutData.put("SUCCEED",new Boolean(false));
            OutData.put("MSG",
                        "An error occurred while generating the report.\nIf the problem recurs, please contact your tcmIS Customer Service Representative(CSR).");
            throw e;
        }
        return OutData;
    }


    protected void loadWasteTemplate(){
        try {
            WasteAhocReportDBObj ahr = new WasteAhocReportDBObj(db);
            Hashtable h = ahr.loadTemplateInfo(inData);
            mySendObj.put("TEMPLATE_INFO",h);
            //Nawaz 19/09/01 new changes to gui tables
            mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    protected void updateWasteTemplate() {
        try {
            WasteAhocReportDBObj ahr = new WasteAhocReportDBObj(db);
            Hashtable h = ahr.saveTemplate(inData);
            Boolean b = (Boolean)h.get("SUCCESS");
            String msg = (String)h.get("MSG");
            Boolean b1 = (Boolean)h.get("OVERRIDE");
            mySendObj.put("SUCCESS",b);
            mySendObj.put("MSG",msg);
            mySendObj.put("OVERRIDE",b1);
            /*
            if (b.booleanValue()) {
              mySendObj.put("MSG","Template was successfully saved.");
            }else {
              mySendObj.put("MSG","Template name already exist.\nDo you want to override it?");
            } */
        }catch(Exception e){
            e.printStackTrace();
            mySendObj.put("SUCCESS",new Boolean(false));
            mySendObj.put("OVERRIDE",new Boolean(false));
            mySendObj.put("MSG",
                          "An error occurred while saving the template.\nIf the problem recurs, please contact your tcmIS Customer Service Representative(CSR).");
        }
    }

    protected void getWasteTemplate() {
        try {
            WasteAhocReportDBObj ahr = new WasteAhocReportDBObj(db);
            Vector v = ahr.getTemplate(inData);
            mySendObj.put("LIST_TEMPLATES",v);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    protected void getWasteTemplateInfo(){
        try {
            WasteAhocReportDBObj ahr = new WasteAhocReportDBObj(db);
            Hashtable h = ahr.getWasteTemplateInfo(inData);
            mySendObj.put("WASTE_TEMPLATE_INFO",h);
            //Nawaz 19/09/01 new changes to gui tables
            mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());

        }catch(Exception e){
            e.printStackTrace();
        }
    }
public StringBuffer modifyTemplate(Hashtable SelectedData, String nameofrpt)
    {
        reportfields = (Vector)SelectedData.get("REPORT_FIELDS");
        String endYear = SelectedData.get("END_YEARN").toString();
        String endMonth = SelectedData.get("END_MONTH").toString();
        String beginYear = SelectedData.get("BEGIN_YEARN").toString();
        String beginMonth = SelectedData.get("BEGIN_MONTH").toString();

        String type = " ";
        String format =" ";
        StringBuffer Msg = new StringBuffer();

        if (beginMonth.equalsIgnoreCase("0")){beginMonth = "JAN";}
        else if (beginMonth.equalsIgnoreCase("1")){beginMonth = "FEB";}
        else if (beginMonth.equalsIgnoreCase("2")){beginMonth = "MAR";}
        else if (beginMonth.equalsIgnoreCase("3")){beginMonth = "APR";}
        else if (beginMonth.equalsIgnoreCase("4")){beginMonth = "MAY";}
        else if (beginMonth.equalsIgnoreCase("5")){beginMonth = "JUN";}
        else if (beginMonth.equalsIgnoreCase("6")){beginMonth = "JUL";}
        else if (beginMonth.equalsIgnoreCase("7")){beginMonth = "AUG";}
        else if (beginMonth.equalsIgnoreCase("8")){beginMonth = "SEP";}
        else if (beginMonth.equalsIgnoreCase("9")){beginMonth = "OCT";}
        else if (beginMonth.equalsIgnoreCase("10")){beginMonth = "NOV";}
        else if (beginMonth.equalsIgnoreCase("11")){beginMonth = "DEC";}

        if (endMonth.equalsIgnoreCase("0")){endMonth = "JAN";}
        else if (endMonth.equalsIgnoreCase("1")){endMonth = "FEB";}
        else if (endMonth.equalsIgnoreCase("2")){endMonth = "MAR";}
        else if (endMonth.equalsIgnoreCase("3")){endMonth = "APR";}
        else if (endMonth.equalsIgnoreCase("4")){endMonth = "MAY";}
        else if (endMonth.equalsIgnoreCase("5")){endMonth = "JUN";}
        else if (endMonth.equalsIgnoreCase("6")){endMonth = "JUL";}
        else if (endMonth.equalsIgnoreCase("7")){endMonth = "AUG";}
        else if (endMonth.equalsIgnoreCase("8")){endMonth = "SEP";}
        else if (endMonth.equalsIgnoreCase("9")){endMonth = "OCT";}
        else if (endMonth.equalsIgnoreCase("10")){endMonth = "NOV";}
        else if (endMonth.equalsIgnoreCase("11")){endMonth = "DEC";}

        Date d = new Date();
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

        //String cTime = new String((cal.get(Calendar.HOUR))+":"+cal.get(Calendar.MINUTE)+" "+am);
        //System.out.println("\n The Date today is "+ cdate +"");

        String begin = beginMonth +" "+ beginYear;
        String End = endMonth +" "+ endYear;

        int sizeofcol = reportfields.size() - 2;

        Msg.append("<FONT FACE=\"Arial\">");
        Msg.append("<TABLE BORDER=\"0\">\n");
        Msg.append("<TR>\n");
        Msg.append("<TD COLSPAN=\""+reportfields.size()+"\" ALIGN=\"CENTER\" ><B><FONT size=\"5\" >"+nameofrpt+"</FONT></B></TD>\n");
        Msg.append("</TR>\n");
        Msg.append("<TR><TD COLSPAN=\""+reportfields.size()+"\" ALIGN=\"RIGHT\"><FONT size=\"4\" >Date: "+cdate+"</FONT></TD></TR\n");
        Msg.append("<TR><TD COLSPAN=\""+reportfields.size()+"\" ALIGN=\"RIGHT\" ><FONT size=\"4\" >Time: "+time+"</FONT></TD></TR>\n");
        Msg.append("</TABLE>\n");

        Msg.append("<TABLE BORDER=\"0\">\n");
        Msg.append("<TR>\n");
        Msg.append("<TD COLSPAN=\"2\" ALIGN=\"CENTER\" ><B><FONT size=\"4\" >Selection Criterion</FONT></B></TD>\n");
        Msg.append("</TR>\n");
        Msg.append("</TABLE>\n");
        Msg.append("<TABLE BORDER=\"1\" CELLPADDING=\"3\">\n");

        type = (String)SelectedData.get("TYPE");
        Msg.append("<TR>\n");
        Msg.append("<TD  ALIGN=\"RIGHT\" ><B>Waste: </B></TD>\n");

        if (type.equalsIgnoreCase("Profile"))
        {
            Msg.append("<TD  ALIGN=\"LEFT\"><I>"+((String)SelectedData.get("PROFILE_ID")==null?"&nbsp;":(String)SelectedData.get("PROFILE_ID"))+"</I></TD>\n");
        }
        else
        {
            Msg.append("<TD  ALIGN=\"LEFT\"><I>All</I></TD>\n");
        }

        Msg.append("</TR>\n");

        Msg.append("<TR>\n");
        Msg.append("<TD  ALIGN=\"RIGHT\"><B>Facility: </B></TD>\n");
        Msg.append("<TD  ALIGN=\"LEFT\"><I>"+((String)SelectedData.get("FACILITY_ID")==null?"&nbsp;":(String)SelectedData.get("FACILITY_ID"))+"</I></TD>\n");
        Msg.append("</TR>\n");

        Msg.append("<TR>\n");
        Msg.append("<TD  ALIGN=\"RIGHT\"><B>Work Area: </B></TD>\n");
        Msg.append("<TD  ALIGN=\"LEFT\"><I>"+((String)SelectedData.get("WORK_AREA")==null?"&nbsp;":(String)SelectedData.get("WORK_AREA"))+"</I></TD>\n");
        Msg.append("</TR>\n");

        Msg.append("<TR>\n");
        Msg.append("<TD  ALIGN=\"RIGHT\"><B>Accumulation Point: </B></TD>\n");
        Msg.append("<TD  ALIGN=\"LEFT\"><I>"+((String)SelectedData.get("ACCUME_PT")==null?"&nbsp;":(String)SelectedData.get("ACCUME_PT"))+"</I></TD>\n");
        Msg.append("</TR>\n");

        Msg.append("<TR>\n");
        Msg.append("<TD  ALIGN=\"RIGHT\"><B>Vendor: </B></TD>\n");
        Msg.append("<TD  ALIGN=\"LEFT\"><I>"+((String)SelectedData.get("VENDOR")==null?"&nbsp;":(String)SelectedData.get("VENDOR"))+"</I></TD>\n");
        Msg.append("</TR>\n");

        Msg.append("<TR>\n");
        Msg.append("<TD  ALIGN=\"RIGHT\"><B>Management Option: </B></TD>\n");
        Msg.append("<TD  ALIGN=\"LEFT\"><I>"+((String)SelectedData.get("MGT_OPTION_DESC")==null?"&nbsp;":(String)SelectedData.get("MGT_OPTION_DESC"))+"</I></TD>\n");
        Msg.append("</TR>\n");

        Msg.append("<TR>\n");
        Msg.append("<TD  ALIGN=\"RIGHT\"><B>Begin: </B></TD>\n");
        Msg.append("<TD  ALIGN=\"LEFT\"><I>"+begin+"</I></TD>\n");
        Msg.append("</TR>\n");

        Msg.append("<TR>\n");
        Msg.append("<TD  ALIGN=\"RIGHT\"><B>End: </B></TD>\n");
        Msg.append("<TD  ALIGN=\"LEFT\"><I>"+End+"</I></TD>\n");
        Msg.append("</TR>\n");
        Msg.append("</TABLE>\n");

        Msg.append("<TABLE BORDER=\"0\">\n");
        Msg.append("<TR><TD>&nbsp;</TD></TR\n");
        Msg.append("<TR><TD>&nbsp;</TD></TR\n");
        Msg.append("<TR>\n");
        Msg.append("<TD COLSPAN=\""+reportfields.size()+"\" ALIGN=\"CENTER\" ><B><FONT size=\"4\" >Results</FONT></B></TD>\n");
        Msg.append("</TR>\n");

        Msg.append("</TABLE>\n");
        Msg.append("<TABLE BORDER=\"1\">\n");

        try
        {
            Msg.append("<TR>\n");
            for(int i=0;i<reportfields.size();i++)
            {
                String lable0 = reportfields.elementAt(i).toString();
                Msg.append("<TD  ALIGN=\"CENTER\"><B><FONT size=\"2\" >"+lable0+"</FONT></B></TD>\n");
                //System.out.println("The Values for "+i+" are these  :"+lable0);
            }
            Msg.append("</TR>\n");
        }
        catch(Exception e1)
        {
            e1.printStackTrace();
        }
    return Msg;
    }
}

