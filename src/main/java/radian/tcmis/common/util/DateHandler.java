package radian.tcmis.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import radian.tcmis.common.exceptions.BaseException;

/***************************************************************************
 * CLASSNAME: DateHandler<BR>
 * Function: <BR>
 * instances: <BR>
 * methods: getDaySpan, isValidDate, isValidDateSpan, formatOracleDate.<BR>
 ***************************************************************************/

public class DateHandler {

  public DateHandler() {
  } //end constructor

      /******************************************************************************
   *           Checks if it's a valid date of format "mm/dd/yyyy".<BR>
   * @param s  Date string to be checked.
       ****************************************************************************/
  public static synchronized boolean isValidDate(String s) {
    String year = null;
    String month = null;
    String date = null;
    StringTokenizer dateString = new StringTokenizer(s, "/");
    int numberOfTokens = dateString.countTokens();
    //check format of data string
    if (numberOfTokens != 3) {
      return false;
    }
    else {
      month = dateString.nextToken();
      date = dateString.nextToken();
      year = dateString.nextToken();
      //check for valid number of characters
      if (month.length() != 2 || date.length() != 2 || year.length() != 4) {
        return false;
      }
      else {
        try {
          Calendar calendar = Calendar.getInstance();
          //disable calendar wrapping of dates
          calendar.setLenient(false);
          //account for the calendar month handling
          calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1,
                       Integer.parseInt(date));
          //need to use the calendar for it to throw an exception
          Date testDate = calendar.getTime();
        }
        catch (Exception e) {
          return false;
        }
      }
    }
    return true;
  } //end of testValidDate

      /******************************************************************************
   *           Checks if starting date is before or equal to ending date. Date
   *           format must be "mm/dd/yyyy".<BR>
   * @param start  Starting date.
   * @param end  Ending date.
       ****************************************************************************/
  public static synchronized boolean isValidDateSpan(String start, String end) {
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    df.setLenient(false);
    try {
      Date startDate = df.parse(start);
      Date endDate = df.parse(end);
      if (startDate.compareTo(endDate) > 0) {
        return false;
      }
    }
    catch (ParseException e) {
      return false;
    }
    return true;
  } //end of testValidDateSpan

      /******************************************************************************
   *           Gets difference in days between 2 dates. Date format must be
   *           "mm/dd/yyyy".<BR> NOTE: COMMENTED OUT TO WORK WITH jdk 1.3
   * @param start  Starting date.
   * @param end  Ending date.
       ****************************************************************************/
/*
  public static synchronized int getDaySpan(String start, String end) {
    Calendar startCal = Calendar.getInstance();
    Calendar endCal = Calendar.getInstance();
    String startYear = null;
    String endYear = null;
    String startMonth = null;
    String endMonth = null;
    String startDate = null;
    String endDate = null;
    StringTokenizer dateString = null;
    long longDiff = 0;
    dateString = new StringTokenizer(start, "/");
    startMonth = dateString.nextToken();
    startDate = dateString.nextToken();
    startYear = dateString.nextToken();
    dateString = new StringTokenizer(end, "/");
    endMonth = dateString.nextToken();
    endDate = dateString.nextToken();
    endYear = dateString.nextToken();
    //account for the calendar month handling
    startCal.set(Integer.parseInt(startYear),
                 Integer.parseInt(startMonth) - 1,
                 Integer.parseInt(startDate));
    endCal.set(Integer.parseInt(endYear),
               Integer.parseInt(endMonth) - 1,
               Integer.parseInt(endDate));
    longDiff = ( (endCal.getTimeInMillis() -
                  startCal.getTimeInMillis()) / (1000 * 60 * 60 * 24));

    return (int) longDiff;
  } //end of getDaySpan
*/
      /*****************************************************************************
   *           Takes the default Oracle date format "yyyy/mm/dd hh.mm.ss.t" and
   *           translates it to the format "mm/dd/yyyy".<BR>
   *           Note that it only deals with strings. Returns null if submitted
   *           date is of invalid format.
   * @param s  Date string to be checked.
       ****************************************************************************/
  public static synchronized String formatOracleDate(String s) throws
      ParseException {

    Date date = null;
    SimpleDateFormat simpleDateFormat =
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
    date = simpleDateFormat.parse(s);
    return formatOracleDate(date);
  } //end of formatOracleDate

      /*****************************************************************************
   *           Takes the default Oracle date format "yyyy/mm/dd hh.mm.ss.t" and
   *           translates it to the format "mm/dd/yyyy".<BR>
   * @param d  Date to be checked.
       ****************************************************************************/
  public static synchronized String formatOracleDate(Date date) {
    SimpleDateFormat simpleDateFormat = null;
    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
    simpleDateFormat.applyPattern("MM/dd/yyyy");
    return (simpleDateFormat.format(date));
  } //end of formatOracleDate

      /*****************************************************************************
   *           Takes a date  and translates it to the specified format.<BR>
   * @param date  Date to be translated.
   * @param fromFormat Current format of the date.
   * @param toFormat Format to translate date to.
       ****************************************************************************/
  public static synchronized String formatDate(Date date, String toFormat) {
    SimpleDateFormat dateFormatter = new SimpleDateFormat(toFormat);
    return dateFormatter.format(date);
  } //end of formatOracleDate

      /*****************************************************************************
   * Takes a <code>String</code> in the default Oracle date format
   * "yyyy-mm-dd hh.mm.ss.t" and parses it into a <code>Date</code>
   *
   * @param
       *****************************************************************************/
  public static synchronized Date parseOracleDateString(
      String oracleDateString) throws ParseException {
    SimpleDateFormat dateParser =
        new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.S");
    return dateParser.parse(oracleDateString);
  } //end of parseOracleDateString

      /******************************************************************************
   * Takes a <code>Date</date> and formats it in the default Oracle date format
   *
   * @param inputDate  the date you want formatted
       *****************************************************************************/
  public static synchronized String getOracleDate(Date inputDate) {
    SimpleDateFormat dateFormatter =
        new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
    return dateFormatter.format(inputDate);
  }

      /******************************************************************************
   * Takes a <code>Date</code> and returns the appropriate string to use that
   * date in an SQL insert/update statement for Oracle
       * (i.e. <code>TO_DATE( '2002-03-12 15:49:01', 'MM/DD/RRRR HH:MM:SS.N' )</code>
   * )
   *
   * @param inputDate  the date you want formatted
       *****************************************************************************/
  public static synchronized String getOracleToDateFunction(Date inputDate) {
    String returnString;
    if (inputDate == null) {
      returnString = "null";
    }
    else {
      returnString = "TO_DATE('" +
          DateHandler.getOracleDate(inputDate) +
          "', 'MM/DD/RRRR hh24:mi:ss')";
    }
    return returnString;
  }

/******************************************************************************
 * Takes a int representation of a month and returns the appropriate string
 * (i.e. 2 returns "March"). The int range is 0-11, any other int will throw a
 * <code>BaseException</code>
 *
 * @param intMonth  the month you want converted
*****************************************************************************/
  public static synchronized String getMonthString(int intMonth)
      throws BaseException {
    String returnString = "";
    if (intMonth < 0 || intMonth > 11) {
      throw new BaseException("Invalid integer");
    }
    else {
      if (intMonth == 0) {
        returnString = "January";
      }
      else if (intMonth == 1) {
        returnString = "February";
      }
      else if (intMonth == 2) {
        returnString = "March";
      }
      else if (intMonth == 3) {
        returnString = "April";
      }
      else if (intMonth == 4) {
        returnString = "May";
      }
      else if (intMonth == 5) {
        returnString = "June";
      }
      else if (intMonth == 6) {
        returnString = "July";
      }
      else if (intMonth == 7) {
        returnString = "August";
      }
      else if (intMonth == 8) {
        returnString = "September";
      }
      else if (intMonth == 9) {
        returnString = "October";
      }
      else if (intMonth == 10) {
        returnString = "November";
      }
      else if (intMonth == 11) {
        returnString = "December";
      }
    }
    return returnString;
  }

  /******************************************************************************
   * Takes a int representation of a month in form of a string
   * and returns the appropriate string (i.e. "2" returns "March").
   * The int range is 0-11, any other int will throw a
   * <code>BaseException</code>
   *
   * @param stringMonth  the month you want converted
  *****************************************************************************/
    public static synchronized String getMonthString(String stringMonth)
        throws BaseException, Exception {

      int month = new Integer(stringMonth).intValue();
      return getMonthString(month);
    }


} //end DateHandler
