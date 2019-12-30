package radian.tcmis.server7.share.servlets;

import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.Vector;

import radian.tcmis.server7.share.dbObjs.AdHocHourlyVocReportDB;
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

public class AdHocHourlyVocReport extends TcmisServletObj
{
    private Hashtable mySendObj = null;
    private Hashtable inData = null;
    private int NoRecords = 0;
    private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger(AdHocHourlyVocReport.class);
    public AdHocHourlyVocReport(ServerResourceBundle b, TcmISDBModel d ){
        super();
        bundle = b;
        db = d;
    }

    protected void resetAllVars()
    {
      inData=null;
    }

    protected void print( TcmisOutputStreamServer out )
    {
      try
      {
        out.sendObject( ( Hashtable ) mySendObj );
      }
      catch ( Exception e )
      {
        e.printStackTrace();
      }
    }

    protected void getResult()
    {
      mySendObj=new Hashtable();
      inData= ( Hashtable ) myRecvObj;
      reoprtlog.info("getResult in data: " + inData );
      String function= ( String ) inData.get( "ACTION" );
      if (function.equalsIgnoreCase("GET_USAGE_TEMPLATE_INFO"))
      {
            getUsageTemplateInfo();
      }
      else if ( function.equalsIgnoreCase( "CREATE_REPORT" ) )
      {
        createReporttest();
      }
    }

    protected void getUsageTemplateInfo()
    {
      try
      {
        AdHocHourlyVocReportDB ahr=new AdHocHourlyVocReportDB( db );
        Hashtable h=ahr.getUsageTemplateInfo( inData );
        mySendObj.put( "USAGE_TEMPLATE_INFO",h );

      }
      catch ( Exception e )
      {
        e.printStackTrace();
      }
    }

    protected void createReporttest() {
      try {
        String client =  db.getClient().toString().trim();
        String userID = (String)inData.get("USER_ID").toString().trim();
        AdHocHourlyVocReportDB hourlyDBObj = new AdHocHourlyVocReportDB(db);
        String sessionID = hourlyDBObj.createReportTemplate(userID,inData);
        mySendObj.put("SUCCEED",new Boolean(true));
        mySendObj.put("MSG","Your report was sent to the server for processing.\nIt will automatically be sent to you upon completion.\nYou may now resume other activities within tcmIS.");
        String url = "/tcmIS/servlet/radian.web.servlets.reports.WebHourlyUsage?sessionID="+sessionID+"&client="+client+"&userID="+userID;
        mySendObj.put("URL",url);
      }catch (Exception e) {
        mySendObj.put("SUCCEED",new Boolean(false));
        mySendObj.put("MSG","An error occurred while generating the report.\nIf the problem recurs, please contact your tcmIS Customer Service Representative(CSR).");
      }
    } //end of method

    public Hashtable createReportNew(String sessionID, String userID) {
      Hashtable OutData = new Hashtable();
      try {
        AdHocHourlyVocReportDB hourlyDBObj = new AdHocHourlyVocReportDB(db);
        Hashtable tmpReportData = new Hashtable(5);
        Hashtable innerH = hourlyDBObj.loadReportTemplate(sessionID);
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


    public String time(int i)
    {
     String Time = "";
     switch(i)
            {
            case 0:
                Time = "12:00 AM";
                break;
            case 1:
                Time = "01:00 AM";
                break;
            case 2:
                Time = "02:00 AM";
                break;
            case 3:
                Time = "03:00 AM";
                break;
            case 4:
                Time = "04:00 AM";
                break;
            case 5:
                Time = "05:00 AM";
                break;
            case 6:
                Time = "06:00 AM";
                break;
            case 7:
               Time = "07:00 AM";
                break;
            case 8:
               Time = "08:00 AM";
                break;
            case 9:
                Time = "09:00 AM";
                break;
            case 10:
                Time = "10:00 AM";
                break;
            case 11:
                Time = "11:00 AM";
                break;
            case 12:
                Time = "12:00 PM";
                break;
            case 13:
                Time = "01:00 PM";
                break;
            case 14:
               Time = "02:00 PM";
                break;
            case 15:
                Time = "03:00 PM";
                break;
            case 16:
               Time = "04:00 PM";
                break;
            case 17:
                Time = "05:00 PM";
                break;
            case 18:
                Time = "06:00 PM";
                break;
            case 19:
               Time = "07:00 PM";
                break;
            case 20:
                Time = "08:00 PM";
                break;
            case 21:
               Time = "09:00 PM";
                break;
            case 22:
               Time = "10:00 PM";
                break;
            case 23:
                Time = "11:00 PM";
                break;
      }
     return Time;
    }
    public Hashtable createReport(Hashtable RepData) throws Exception
    {
      Hashtable OutData = new Hashtable();
      try{
        //12-16-02
        Hashtable SelectedData = (Hashtable)RepData.get("SELECTED_DATA");
        String reportType = (String)SelectedData.get("REPORT_TYPE");
        //System.out.println("In  createreport report type: "+reportType);
        if ("HOURLY".equalsIgnoreCase(reportType)) {
          OutData = generateHourlyVoc(RepData);
        }else if (reportType.startsWith("FIVE_HOUR")) {
         OutData = generateFiveHourVoc(RepData);
        }else {
          OutData = generateFourHourVoc(RepData);
        }
      }catch (Exception e) {
        e.printStackTrace();
        OutData.put("SUCCEED",new Boolean(false));
        OutData.put("MSG","An error occurred while generating the report.\nIf the problem recurs, please contact your tcmIS Customer Service Representative(CSR).");
        throw e;
      }
      return OutData;
    }

  public Hashtable generateFiveHourVoc(Hashtable RepData) throws Exception {
      Hashtable OutData = new Hashtable();
      Hashtable Data = new Hashtable();
      Hashtable SelectedData = new Hashtable();
      Hashtable Result = new Hashtable();
      Vector ResultF = new Vector();
      Vector PDFResultF = new Vector();
      Hashtable PDFData = new Hashtable();
      String url = "";
      String reportType = "";

      try {

        SelectedData = (Hashtable)RepData.get("SELECTED_DATA");
        reportType = (String) SelectedData.get("REPORT_TYPE");
        //String whichascreen = (String)RepData.get("WHICH_SCREEN");
        //String userId = (String)RepData.get("USER_ID");
        String method = SelectedData.get("METHOD").toString();
        String facilityID = (String)SelectedData.get("FACILITY_ID").toString();
        String endMonth = SelectedData.get("END_MONTH").toString();
        String endYear = SelectedData.get("END_YEARN").toString();
        if (endMonth.equalsIgnoreCase("0")){endMonth = "January";}
        else if (endMonth.equalsIgnoreCase("1")){endMonth = "February";}
        else if (endMonth.equalsIgnoreCase("2")){endMonth = "March";}
        else if (endMonth.equalsIgnoreCase("3")){endMonth = "April";}
        else if (endMonth.equalsIgnoreCase("4")){endMonth = "May";}
        else if (endMonth.equalsIgnoreCase("5")){endMonth = "June";}
        else if (endMonth.equalsIgnoreCase("6")){endMonth = "July";}
        else if (endMonth.equalsIgnoreCase("7")){endMonth = "August";}
        else if (endMonth.equalsIgnoreCase("8")){endMonth = "September";}
        else if (endMonth.equalsIgnoreCase("9")){endMonth = "October";}
        else if (endMonth.equalsIgnoreCase("10")){endMonth = "November";}
        else if (endMonth.equalsIgnoreCase("11")){endMonth = "December";}
        endMonth += "  "+endYear;

        AdHocHourlyVocReportDB ahr = new AdHocHourlyVocReportDB(db);
        Vector resultData = ahr.getFiveHourReportData(db,SelectedData);
        Vector maxFiveHourV = (Vector)resultData.elementAt(0);
        Result = (Hashtable)resultData.elementAt(1);

	    Vector maxFiveHourVt = new Vector();
		Hashtable maxFourHourHNext=new Hashtable();
		String maxsource = "";
		String maxvocvalue = "";

        //Four - Hour Average
        int TotalRecs = 0;
        ResultF= (Vector)Result.get("STRING_BUFFER");
        if (!(Result.get("RECORDS").toString().trim().equalsIgnoreCase("0"))) {
            ResultF= (Vector)Result.get("STRING_BUFFER");
            TotalRecs = ResultF.size();
            int count = 0;
	    int icc=0;
	    Hashtable hDNext=new Hashtable();

        for(int j=0;j < ResultF.size();j++)
	    {
		icc++;
		String tempdate = "";
		String tempsource = "";

		if ( ! ( icc==1) )
		{
		  hDNext=new Hashtable();
		  hDNext= (Hashtable)ResultF.elementAt(j-1);
		  tempdate=hDNext.get( "DATE_TIME" ) == null ? "&nbsp;" : hDNext.get( "DATE_TIME" ).toString();
		  tempsource=hDNext.get( "SOURCE" ) == null ? "&nbsp;" : hDNext.get( "SOURCE" ).toString();
		}
		else
		{
		  tempdate=" ";
		  tempsource = " ";
		}

        Data = (Hashtable)ResultF.elementAt(j);
        PDFData = new Hashtable();

        String Usage = Data.get("VOC_WT_SHIPPED").toString();
        String Source =  Data.get("SOURCE").toString();
        String TimeDate = Data.get("DATE_TIME").toString();
		String dayhr = Data.get("DATE_HOUR").toString();

		if (!tempsource.equalsIgnoreCase(Source))
		{
		  for(int jm=0;jm < maxFiveHourV.size();jm++)
		  {
			Hashtable maxFourHourHNext1= ( Hashtable ) maxFiveHourV.elementAt( jm );
			String tmpmaxsource =  maxFourHourHNext1.get("DETAIL_0").toString();
			String tmpmaxvocvalue =  maxFourHourHNext1.get("DETAIL_1").toString();

			if (tmpmaxsource.equalsIgnoreCase(Source))
			{
			  maxsource =  maxFourHourHNext1.get("DETAIL_0").toString();
			  maxvocvalue =  maxFourHourHNext1.get("DETAIL_1").toString();

			  /*maxFourHourHNext=new Hashtable();
			  maxFourHourHNext.put( "DETAIL_0", ( rs.getString( "source" ) ) );
			  maxFourHourHNext.put( "DETAIL_1", ( rs.getString( "voc_per_hr_5hr_avg" ) ) );

			  maxFiveHourVt.addElement( maxFourHourHNext );*/
			}
		  }
		}
		else
		{
		  if (maxvocvalue.equalsIgnoreCase(Usage))
		  {
			maxFourHourHNext=new Hashtable();
			maxFourHourHNext.put( "DETAIL_0", ( maxsource ) );
			maxFourHourHNext.put( "DETAIL_1", ( maxvocvalue ) );
			maxFourHourHNext.put( "DETAIL_2", ""+TimeDate+"  "+dayhr+"");

			maxFiveHourVt.addElement( maxFourHourHNext );
		  }
		}

		if (tempdate.equalsIgnoreCase(TimeDate))
		{
		  PDFData.put( "DETAIL_0"," " );
		  PDFData.put( "DETAIL_1"," " );
		  //PDFData.put("DETAIL_2",time(count));
		  PDFData.put( "DETAIL_2",dayhr );
		  PDFData.put( "DETAIL_3",Usage );
		  PDFResultF.addElement( PDFData );
		  PDFData=new Hashtable();
		}
		else
		{
		  PDFData.put( "DETAIL_0",Source );
		  PDFData.put( "DETAIL_1",TimeDate );
		  //PDFData.put("DETAIL_2",time(count));
		  PDFData.put( "DETAIL_2",dayhr );
		  PDFData.put( "DETAIL_3",Usage );
		  PDFResultF.addElement( PDFData );
		  PDFData=new Hashtable();
		  tempdate = TimeDate;
		}

		/*if ( count == 0 )
		{
		  //System.out.println( "TimeDate   " + TimeDate );
		  PDFData.put( "DETAIL_0",Source );
		  PDFData.put( "DETAIL_1",TimeDate );
		  //PDFData.put("DETAIL_2",time(count));
		  PDFData.put( "DETAIL_2",dayhr );
		  PDFData.put( "DETAIL_3",Usage );
		  PDFResultF.addElement( PDFData );
		  PDFData=new Hashtable();
		  count++;
		}
		else
		{
		  PDFData.put( "DETAIL_0"," " );
		  PDFData.put( "DETAIL_1"," " );
		  //PDFData.put("DETAIL_2",time(count));
		  PDFData.put( "DETAIL_2",dayhr );
		  PDFData.put( "DETAIL_3",Usage );
		  PDFResultF.addElement( PDFData );
		  PDFData=new Hashtable();
		  if ( count == 23 )
		  {
			count=0;
		  }
		  else
		  {
			count++;
                  }
                }*/
             }
        }
		//System.out.println(maxFiveHourVt);
        //System.out.println("Done withe the Report Data   :");
        if (!(Result.get("RECORDS").toString().trim().equalsIgnoreCase("0"))) {
            AdHocReportGenerator arg = new AdHocReportGenerator();
            url = arg.buildFiveHourVOC(PDFResultF,maxFiveHourVt,facilityID,endMonth,method,reportType);
        }else {
            //System.out.println("\n\nIn BuildUsage Report No Resutls from the Querry");
            NoRecords = 1;
        }

        if (url.length() > 0) {
            OutData.put("SUCCEED",new Boolean(true));
        }else {
            OutData.put("SUCCEED",new Boolean(false));
            if ((Result.get("RECORDS").toString().trim().equalsIgnoreCase("Error"))) {
                OutData.put("MSG"," Generating report failed.\n Please try again.\n If problem recurs, contact tcmIS Hotline at 512-519-3918.");
            }else if ((Result.get("RECORDS").toString().trim().equalsIgnoreCase("0"))) {
                OutData.put("MSG"," No records were found matching your search criteria.\n Please choose different criteria and try again.");
            }
        }
        OutData.put("URL",url);
      }catch(Exception e){
          e.printStackTrace();
          OutData.put("SUCCEED",new Boolean(false));
          OutData.put("MSG","An error occurred while generating the report.\nIf the problem recurs, please contact your tcmIS Customer Service Representative(CSR).");
          throw e;
      }

	Data=null;
	SelectedData=null;
	Result=null;
	ResultF=null;
	PDFResultF=null;
	PDFData=null;

      return OutData;
    }

  public Hashtable generateFourHourVoc(Hashtable RepData) throws Exception {
    Hashtable OutData = new Hashtable();
    Hashtable Data = new Hashtable();
    Hashtable SelectedData = new Hashtable();
    Hashtable Result = new Hashtable();
    Vector ResultF = new Vector();
    Vector PDFResultF = new Vector();
    Hashtable PDFData = new Hashtable();
    String url = "";

    try {

      SelectedData = (Hashtable)RepData.get("SELECTED_DATA");
      //String whichascreen = (String)RepData.get("WHICH_SCREEN");
      //String userId = (String)RepData.get("USER_ID");
      String method = SelectedData.get("METHOD").toString();
      String facilityID = (String)SelectedData.get("FACILITY_ID").toString();
      String endMonth = SelectedData.get("END_MONTH").toString();
      String endYear = SelectedData.get("END_YEARN").toString();
      if (endMonth.equalsIgnoreCase("0")){endMonth = "January";}
      else if (endMonth.equalsIgnoreCase("1")){endMonth = "February";}
      else if (endMonth.equalsIgnoreCase("2")){endMonth = "March";}
      else if (endMonth.equalsIgnoreCase("3")){endMonth = "April";}
      else if (endMonth.equalsIgnoreCase("4")){endMonth = "May";}
      else if (endMonth.equalsIgnoreCase("5")){endMonth = "June";}
      else if (endMonth.equalsIgnoreCase("6")){endMonth = "July";}
      else if (endMonth.equalsIgnoreCase("7")){endMonth = "August";}
      else if (endMonth.equalsIgnoreCase("8")){endMonth = "September";}
      else if (endMonth.equalsIgnoreCase("9")){endMonth = "October";}
      else if (endMonth.equalsIgnoreCase("10")){endMonth = "November";}
      else if (endMonth.equalsIgnoreCase("11")){endMonth = "December";}
      endMonth += "  "+endYear;

      AdHocHourlyVocReportDB ahr = new AdHocHourlyVocReportDB(db);
      Vector resultData = ahr.getFourHourReportData(db,SelectedData);
      Vector maxFourHourV = (Vector)resultData.elementAt(0);
      Result = (Hashtable)resultData.elementAt(1);

      //Four - Hour Average
      int TotalRecs = 0;
      ResultF= (Vector)Result.get("STRING_BUFFER");
      if (!(Result.get("RECORDS").toString().trim().equalsIgnoreCase("0"))) {
          ResultF= (Vector)Result.get("STRING_BUFFER");
          TotalRecs = ResultF.size();
          int count = 0;
          for(int j=0;j < ResultF.size();j++) {
              Data = (Hashtable)ResultF.elementAt(j);
              PDFData = new Hashtable();

              String Usage = Data.get("VOC_WT_SHIPPED").toString();
              String Source =  Data.get("SOURCE").toString();
              String TimeDate = Data.get("DATE_TIME").toString();
              if (count == 0) {
                PDFData.put("DETAIL_0",Source);
                PDFData.put("DETAIL_1",TimeDate);
                PDFData.put("DETAIL_2",time(count));
                PDFData.put("DETAIL_3",Usage);
                PDFResultF.addElement(PDFData);
                PDFData = new Hashtable();
                count++;
              }else {
                PDFData.put("DETAIL_0"," ");
                PDFData.put("DETAIL_1"," ");
                PDFData.put("DETAIL_2",time(count));
                PDFData.put("DETAIL_3",Usage);
                PDFResultF.addElement(PDFData);
                PDFData = new Hashtable();
                if (count == 23) {
                  count = 0;
                }else {
                  count++;
                }
              }
           }
      }

      //System.out.println("Done withe the Report Data   :");
      if (!(Result.get("RECORDS").toString().trim().equalsIgnoreCase("0"))) {
          AdHocReportGenerator arg = new AdHocReportGenerator();
          url = arg.buildFourHourVOC(PDFResultF,maxFourHourV,facilityID,endMonth,method);
      }else {
          //System.out.println("\n\nIn BuildUsage Report No Resutls from the Querry");
          NoRecords = 1;
      }

      if (url.length() > 0) {
          OutData.put("SUCCEED",new Boolean(true));
      }else {
          OutData.put("SUCCEED",new Boolean(false));
          if ((Result.get("RECORDS").toString().trim().equalsIgnoreCase("Error"))) {
              OutData.put("MSG"," Generating report failed.\n Please try again.\n If problem recurs, contact tcmIS Hotline at 512-519-3918.");
          }else if ((Result.get("RECORDS").toString().trim().equalsIgnoreCase("0"))) {
              OutData.put("MSG"," No records were found matching your search criteria.\n Please choose different criteria and try again.");
          }
      }
      OutData.put("URL",url);
    }catch(Exception e){
        e.printStackTrace();
        OutData.put("SUCCEED",new Boolean(false));
        OutData.put("MSG","An error occurred while generating the report.\nIf the problem recurs, please contact your tcmIS Customer Service Representative(CSR).");
        throw e;
    }

   Data = null;
   SelectedData = null;
   Result = null;
   ResultF = null;
   PDFResultF = null;
   PDFData = null;

    return OutData;
  }

    public Hashtable generateHourlyVoc(Hashtable RepData) throws Exception {
      Hashtable OutData = new Hashtable();
      Hashtable Data = new Hashtable();
      Hashtable Data1 = new Hashtable();
      Hashtable SelectedData = new Hashtable();
      Hashtable Result = new Hashtable();
      Vector ResultF = new Vector();
      Vector PDFResultF = new Vector();
      Hashtable PDFData = new Hashtable();
      boolean WeeklyTotal = false;
      Hashtable PDFDataS = new Hashtable();
      String url = "";

      try {

        SelectedData = (Hashtable)RepData.get("SELECTED_DATA");
        //String whichascreen = (String)RepData.get("WHICH_SCREEN");
        //String userId = (String)RepData.get("USER_ID");
        String method = SelectedData.get("METHOD").toString();
        String facilityID = (String)SelectedData.get("FACILITY_ID").toString();
        String endMonth = SelectedData.get("END_MONTH").toString();
        String endYear = SelectedData.get("END_YEARN").toString();
        if (endMonth.equalsIgnoreCase("0")){endMonth = "January";}
        else if (endMonth.equalsIgnoreCase("1")){endMonth = "February";}
        else if (endMonth.equalsIgnoreCase("2")){endMonth = "March";}
        else if (endMonth.equalsIgnoreCase("3")){endMonth = "April";}
        else if (endMonth.equalsIgnoreCase("4")){endMonth = "May";}
        else if (endMonth.equalsIgnoreCase("5")){endMonth = "June";}
        else if (endMonth.equalsIgnoreCase("6")){endMonth = "July";}
        else if (endMonth.equalsIgnoreCase("7")){endMonth = "August";}
        else if (endMonth.equalsIgnoreCase("8")){endMonth = "September";}
        else if (endMonth.equalsIgnoreCase("9")){endMonth = "October";}
        else if (endMonth.equalsIgnoreCase("10")){endMonth = "November";}
        else if (endMonth.equalsIgnoreCase("11")){endMonth = "December";}
        endMonth += "  "+endYear;

        AdHocHourlyVocReportDB ahr = new AdHocHourlyVocReportDB(db);
        Result = ahr.getReportData(db,SelectedData);

        int dayCount = 0;
        int TotalRecs = 0;
        double Montly_total = 0.000;
        double Weekly_total = 0.000;
        double Daily_total = 0.000;
        double Final1_total = 0.000;
        double Final2_total = 0.000;
        double Final3_total = 0.000;
        double Final4_total = 0.000;
        double Final5_total = 0.000;
        double Final_Monthly = 0.000;
        Double Test_total = new Double(0.000);
        String output = "";
        String output1 = "";

        ResultF= (Vector)Result.get("STRING_BUFFER");
        if (!(Result.get("RECORDS").toString().trim().equalsIgnoreCase("0"))) {
            ResultF= (Vector)Result.get("STRING_BUFFER");
            TotalRecs = ResultF.size();
            PDFDataS = new Hashtable();
            int count = 0;
	    int icc=0;
            Hashtable hDNext=new Hashtable();

            for(int j=0;j < ResultF.size();j++)
	    {
                //making sure that I am working 24 hour a day and only display source and date at midnight only
		icc++;
		String tempdate = "";
		if ( ! ( icc==ResultF.size()) )
		{
		  hDNext=new Hashtable();
		  hDNext= (Hashtable)ResultF.elementAt(icc);
		  tempdate=hDNext.get( "DATE_TIME" ) == null ? "&nbsp;" : hDNext.get( "DATE_TIME" ).toString();
		}
		else
		{
		  tempdate=" ";
		}

                Data = (Hashtable)ResultF.elementAt(j);
                PDFData = new Hashtable();
                PDFDataS.put("DETAIL_0",Data.get("SOURCE").toString());
                String Usage = Data.get("VOC_WT_SHIPPED").toString();
                String Source =  Data.get("SOURCE").toString();
                String TimeDate = Data.get("DATE_TIME").toString();
                String hourDate = Data.get("HOUR_TIME").toString();

                Test_total = Double.valueOf(Usage);
                DecimalFormat myFormatter = new DecimalFormat("0.000");
                output = myFormatter.format(Test_total);
                if (count == 0) {
                     PDFData.put("DETAIL_0",Source);
                     PDFData.put("DETAIL_1",TimeDate);
                     PDFData.put("DETAIL_2",hourDate);
                     PDFData.put("DETAIL_3",output);
                     PDFData.put("DETAIL_4"," ");
                     PDFData.put("DETAIL_5"," ");
                     PDFData.put("DETAIL_6"," ");
                     PDFData.put("DETAIL_7","N");
                     PDFData.put("DETAIL_8"," ");
                     PDFResultF.addElement(PDFData);
                     PDFData = new Hashtable();
                }else {
                     PDFData.put("DETAIL_0"," ");
                     PDFData.put("DETAIL_1"," ");
                     PDFData.put("DETAIL_2",hourDate);
                     PDFData.put("DETAIL_3",output);
                     PDFData.put("DETAIL_4"," ");
                     PDFData.put("DETAIL_5"," ");
                     PDFData.put("DETAIL_6"," ");
                     PDFData.put("DETAIL_7","N");
                     PDFData.put("DETAIL_8"," ");
                     PDFResultF.addElement(PDFData);
                     PDFData = new Hashtable();
                }
                count++;
                Daily_total += Double.parseDouble(Usage);
                DecimalFormat myFormatter1 = new DecimalFormat("0.000");
                //display daily total
                //if (count == 24)
        	if (!tempdate.equalsIgnoreCase(TimeDate))
		{
                  output1 = myFormatter1.format(Daily_total);
                  PDFData.put("DETAIL_0"," ");
                  PDFData.put("DETAIL_1"," " );
                  PDFData.put("DETAIL_2","Daily Total:");
                  PDFData.put("DETAIL_3",output1);
                  PDFData.put("DETAIL_4"," ");
                  PDFData.put("DETAIL_5"," ");
                  PDFData.put("DETAIL_6"," ");
                  PDFData.put("DETAIL_7","N");
                  PDFData.put("DETAIL_8"," ");
                  PDFResultF.addElement(PDFData);
                  PDFData = new Hashtable();
                  Weekly_total = Weekly_total + Daily_total;
                  Montly_total = Montly_total + Daily_total;
                  dayCount += 1;
                  switch(dayCount) {
                    case 7:
                      //First Week
                      PDFDataS.put("DETAIL_1",myFormatter1.format(Weekly_total));
                      Final1_total = Final1_total + Weekly_total;
                      Weekly_total = 0.000;
                      break;
                    case 14:
                      //Second Week
                      PDFDataS.put("DETAIL_2",myFormatter1.format(Weekly_total));
                      Final2_total = Final2_total + Weekly_total;
                      Weekly_total = 0.000;
                      break;
                    case 21:
                      //Third Week
                      PDFDataS.put("DETAIL_3",myFormatter1.format(Weekly_total));
                      Final3_total = Final3_total + Weekly_total;
                      Weekly_total = 0.000;
                      break;
                    case 28:
                      //Fourth Week
                      PDFDataS.put("DETAIL_4",myFormatter1.format(Weekly_total));
                      Final4_total = Final4_total + Weekly_total;
                      WeeklyTotal = true;
                      Weekly_total = 0.000;
                      break;
                    default:
                      break;
                  }
                }

                if ( j + 1 == TotalRecs ) {
                  Final_Monthly=Final_Monthly + Montly_total;
                  if ( ( dayCount < 28 ) && ( dayCount > 21 ) )
                  {
                    PDFDataS.put( "DETAIL_8"," " );
                    PDFDataS.put( "DETAIL_5",myFormatter1.format( Montly_total ) );
                    PDFDataS.put( "DETAIL_6","N" );
                    PDFDataS.put( "DETAIL_7"," " );
                    PDFDataS.put( "DETAIL_4",myFormatter1.format( Weekly_total ) );
                    Final4_total=Final4_total + Weekly_total;
                  }
                  else
                  {
                    PDFDataS.put( "DETAIL_5",myFormatter1.format( Montly_total ) );
                    PDFDataS.put( "DETAIL_6","N" );
                    PDFDataS.put( "DETAIL_7"," " );
                    PDFDataS.put( "DETAIL_8",myFormatter1.format( Weekly_total ) );
                    Final5_total=Final5_total + Weekly_total;
                  }

                  PDFResultF.addElement( PDFDataS );
                  PDFDataS=new Hashtable();
                }else {
                  Data1= ( Hashtable ) ResultF.elementAt( j + 1 );
                  if ( Data.get( "SOURCE" ).toString().equalsIgnoreCase( Data1.get( "SOURCE" ).toString() ) ){

                  }else {
                    Final_Monthly=Final_Monthly + Montly_total;
                    if ( ( dayCount < 28 ) && ( dayCount > 21 ) ) {
                      PDFDataS.put( "DETAIL_8"," " );
                      PDFDataS.put( "DETAIL_5",myFormatter1.format( Montly_total ) );
                      PDFDataS.put( "DETAIL_6","N" );
                      PDFDataS.put( "DETAIL_7"," " );
                      PDFDataS.put( "DETAIL_4",myFormatter1.format( Weekly_total ) );
                      Final4_total=Final4_total + Weekly_total;
                    }else {
                      PDFDataS.put( "DETAIL_5",myFormatter1.format( Montly_total ) );
                      PDFDataS.put( "DETAIL_6","N" );
                      PDFDataS.put( "DETAIL_7"," " );
                      PDFDataS.put( "DETAIL_8",myFormatter1.format( Weekly_total ) );
                      Final5_total=Final5_total + Weekly_total;
                    }
                    PDFResultF.addElement( PDFDataS );
                    dayCount=0;
                    Montly_total=0.000;
                    Weekly_total=0.000;
                    PDFDataS=new Hashtable();
                  }
                }
		//if (j % 24 == 0)
		if (!tempdate.equalsIgnoreCase(TimeDate))
		{
		  count = 0;
		  Daily_total = 0.000;
		}

            }//end of for
        }

        //System.out.println("Done withe the Report Data   :");
        DecimalFormat myFormatter1 = new DecimalFormat("0.000");
        if (!(Result.get("RECORDS").toString().trim().equalsIgnoreCase("0"))) {
            AdHocReportGenerator arg = new AdHocReportGenerator();
            url = arg.buildHourlyVOC(PDFResultF,facilityID,endMonth,method,
            myFormatter1.format(Final1_total),myFormatter1.format(Final2_total),
            myFormatter1.format(Final3_total),myFormatter1.format(Final4_total),
            myFormatter1.format(Final5_total),myFormatter1.format(Final_Monthly));
        }else
        {
            //System.out.println("\n\nIn BuildUsage Report No Resutls from the Querry");
            NoRecords = 1;
        }

        if (url.length() > 0) {
            OutData.put("SUCCEED",new Boolean(true));
        }else {
            OutData.put("SUCCEED",new Boolean(false));
            if ((Result.get("RECORDS").toString().trim().equalsIgnoreCase("Error"))) {
                OutData.put("MSG"," Generating report failed.\n Please try again.\n If problem recurs, contact tcmIS Hotline at 512-519-3918.");
            }else if ((Result.get("RECORDS").toString().trim().equalsIgnoreCase("0"))) {
                OutData.put("MSG"," No records were found matching your search criteria.\n Please choose different criteria and try again.");
            }
        }
        OutData.put("URL",url);
      }catch(Exception e){
          e.printStackTrace();
          OutData.put("SUCCEED",new Boolean(false));
          OutData.put("MSG","An error occurred while generating the report.\nIf the problem recurs, please contact your tcmIS Customer Service Representative(CSR).");
          throw e;
      }


	   Data = null;
	   Data1 = null;
	   SelectedData = null;
	   Result = null;
	   ResultF = null;
	   PDFResultF = null;
	   PDFData = null;
	   PDFDataS = null;

      return OutData;
    }
}