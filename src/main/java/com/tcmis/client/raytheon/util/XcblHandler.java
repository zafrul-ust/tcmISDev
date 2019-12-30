package com.tcmis.client.raytheon.util;

import java.io.*;
import java.util.Date;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class XcblHandler {
  public XcblHandler() {
  }

  /******************************************************************************
   *  Formats a xcbl date string of the format 20050802T12:00:00 or
   * 20050802T12:00:00-04:00 into a java.util.Date.<BR>
   * NOTE THAT THIS METHOD WILL EAT ANY ERROR THROWN!!
   * @param dateString  Date string to parse.
   ****************************************************************************/
  public static synchronized Date getXcblDate(String dateString) {
    Date date = null;
    String s = null;
    try {
      //look for date strings of the format 20050802T12:00:00 or 20050802T12:00:00-04:00
      if (dateString.length() == 17) {
        date = DateHandler.getDateFromString("yyyyMMdd'T'HH:mm:ss", dateString);
      }
      else if (dateString.length() == 23) {
        s = dateString.substring(0, 20).concat(dateString.substring(21, 23));
        date = DateHandler.getDateFromString("yyyyMMdd'T'HH:mm:ssZ", s);
      }
    }
    catch (Exception e) {
      //ignore
    }
    return date;
  } //end of testValidDateSpan

  /*
   * Returns the date as a string in the format of 20051020T16:33:24-05:00
   * @param date  Date to parse.
   */
  public static synchronized String getXcblDateString(Date date) {
    String dateString = "";
    if(date != null) {
      dateString = DateHandler.formatDate(date, "yyyyMMdd'T'HH:mm:ss");
      //dateString = DateHandler.formatDate(date, "yyyyMMdd'T'HH:mm:ssZ");
      //dateString = dateString.substring(0,20).concat(":").concat(dateString.substring(20,22));
    }
    return dateString;
  } 

  /*
   * Saves the xml document to the hard drive with "XCBL_" as prefix and ".XML" 
   * as suffix.
   * @param xcblDocument  String to save to file.
   * @param backupDir  Directory to save file to.
   */
  public static synchronized File saveDocument(String xcblDocument, String backupDir) throws
      IOException {
    if(xcblDocument == null) {
      xcblDocument = "";
    }
    return FileHandler.saveTempFile(xcblDocument, "XCBL_", ".XML", backupDir);
  }
}