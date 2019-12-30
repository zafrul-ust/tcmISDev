package radian.tcmis.server7.share.servlets;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Vector;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.tcmis.common.ftp.FtpClient;

import radian.tcmis.server7.client.utc.dbObjs.UTCTcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.both1.helpers.CreateTxtFileFromData;
import radian.tcmis.server7.share.dbObjs.PWAWeeklyUsageReportObj;
import radian.tcmis.server7.share.db.ResourceLibrary;

public class PWAWeeklyUsageReport extends HttpServlet {
  UTCTcmISDBModel db = null;

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    PrintWriter out = response.getWriter();
    response.setContentType("Text/html");
    //System.out.println("\n\n------- got here PWA weekly usage report");
    try {
      //get database connection
      db = new UTCTcmISDBModel("UTC","2");
      if (db == null) {
        out.println("Starting DB");
        out.println("DB is null");
        out.close();
        return;
      }

      //load data from properties file
      ResourceLibrary resourceLibrary = new ResourceLibrary("PWAUsageReport");
      String localFtpDir = resourceLibrary.getString("local.ftp.dir");
      String regularFileBaseName = resourceLibrary.getString("base.filename");
      String creditFileBaseName = resourceLibrary.getString("base.credit.filename");
      String reportStartDay = resourceLibrary.getString("start.day");
      String reportEndDay = resourceLibrary.getString("end.day");
      //in case PWA need to re-run a report
      int startDay = 0;
      int endDay = 0;
      String tmpStartDay = request.getParameter("start_day");
      String tmpEndDay = request.getParameter("end_day");
      if (tmpStartDay != null && tmpEndDay != null) {
        startDay = Integer.parseInt(tmpStartDay);
        endDay = Integer.parseInt(tmpEndDay);
      }else {
        startDay = Integer.parseInt(reportStartDay);
        endDay = Integer.parseInt(reportEndDay);
      }


      SimpleDateFormat prefixFmt = new SimpleDateFormat("yyyy-MM-dd-HH");
      String now = prefixFmt.format(new java.util.Date());
      String[] ftpFileName = new String[1];
      //now create report file

      //first get report data
      PWAWeeklyUsageReportObj pwaWeeklyUsageReportObj = new PWAWeeklyUsageReportObj(db);
      Hashtable reportDataH = pwaWeeklyUsageReportObj.getRegularPWAWeeklyReportData(startDay,endDay);

      //next create report file
      Vector rowDataV = (Vector)reportDataH.get("DATA");
      String fileName = localFtpDir + now + "-" + regularFileBaseName;
      CreateTxtFileFromData createTxtFileFromData = new CreateTxtFileFromData();
      createTxtFileFromData.createFile(rowDataV,(String[])reportDataH.get("HEADER_COLUMN"), fileName);
      ftpFileName[0] = fileName;

      /*SECOND get credit report data
      Hashtable creditReportDataH = pwaWeeklyUsageReportObj.getCreditPWAWeeklyReportData(startDay,endDay);
      //next create report file
      Vector creditRowDataV = (Vector)creditReportDataH.get("DATA");
      fileName = localFtpDir + now + "-" + creditFileBaseName;
      createTxtFileFromData.createFile(creditRowDataV,(String[])creditReportDataH.get("HEADER_COLUMN"), fileName);
      ftpFileName[1] = fileName;
      */
      //finally ftp file to the right place
      ftpData(resourceLibrary,ftpFileName);
      //System.out.println("------- PWA weekly usage report DONE");
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.sendMail(db, "JRun Scheduler", "Error occurred while scheduler processing PWA weekly usage report ", 86030, false);
    } finally {
      db.close();
    }
  } //end of doGet

  void ftpData(ResourceLibrary resourceLibrary, String[] ftpFileName) {
    FtpClient agent = null;
    try {
      /*
       * Setup calendars set to the start and end time of the reporting
       * period. Start and end times are always midnight, 00:00:00.
       */
      GregorianCalendar end = new GregorianCalendar();
      end.add(Calendar.HOUR_OF_DAY, ( -1 * end.get(Calendar.HOUR_OF_DAY)));
      end.add(Calendar.MINUTE, ( -1 * end.get(Calendar.MINUTE)));
      end.add(Calendar.SECOND, ( -1 * end.get(Calendar.SECOND)));
      GregorianCalendar start = new GregorianCalendar();
      start.setTime(end.getTime());
      start.add(Calendar.DATE, -7);

      String remoteFtpDir = resourceLibrary.getString("remote.ftp.dir");
      String ftpHost = resourceLibrary.getString("ftp.host");
      String ftpUser = resourceLibrary.getString("ftp.user");
      String ftpPassword = resourceLibrary.getString("ftp.pass");
      String fileBaseName = "";
      SimpleDateFormat dfmt = new SimpleDateFormat("yyyy-MM-dd-");

      //agent = new FTPImpl(ftpHost,ftpUser,ftpPassword);
      //agent.setMode(FTP.BINARY_TRANSFER | FTP.OVERWRITE);
      agent = new FtpClient(ftpHost,ftpUser,ftpPassword);
      agent.setFileType(FtpClient.BINARY_FILE_TYPE);
      for (int i = 0; i < ftpFileName.length; i++) {
        String currentFileName = ftpFileName[i];
        if ("NO".equalsIgnoreCase(currentFileName)) {
          continue;
        }
        //if first file then it is the regular
        if (i == 0) {
          fileBaseName = resourceLibrary.getString("base.filename");
        }else {
          fileBaseName = resourceLibrary.getString("base.credit.filename");
        }
        /*
        String remote = remoteFtpDir + fileBaseName;
        String lastReport = remoteFtpDir + dfmt.format(start.getTime()) + fileBaseName;
        try {
          agent.rename(remote, lastReport);
        } catch (Fault f) {
          f.printStackTrace();
          HelpObjs.sendMail(db, "JRun Scheduler - rename ftp file", "Error occurred while trying to rename ftp file to /home/guests/utc/usage PWA weekly usage report ", 86030, false);
        }
        */
        //agent.put(currentFileName, remote);

        GregorianCalendar today = new GregorianCalendar();
        String todayReport = remoteFtpDir + dfmt.format(today.getTime()) + fileBaseName;
        FileInputStream fis = new FileInputStream(currentFileName);
        agent.put(todayReport,fis);
      }
    } catch (Throwable e) {
      e.printStackTrace();
      HelpObjs.sendMail(db, "JRun Scheduler - ftp file", "Error occurred while ftp file to /home/guests/utc/usage PWA weekly usage report ", 86030, false);
    } finally {
      if (agent != null) {
        agent.disconnect();
      }
    }
  } //end of method

} //end of class