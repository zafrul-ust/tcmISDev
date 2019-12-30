package radian.tcmis.client.share.helpers;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
/***************************************************************************
  * CLASSNAME: DateHandler<BR>
  * Function: <BR>
  * instances: <BR>
  * methods: getDaySpan, isValidDate, isValidDateSpan, formatOracleDate.<BR>
  ***************************************************************************/

public class CalendarHandler  {

    public CalendarHandler() {
    }//end constructor

/******************************************************************************
  *           Checks if it's a valid date of format "mm/dd/yyyy".<BR>
  * @param s  Date string to be checked.
  ****************************************************************************/
    public static synchronized boolean isValidDate(String s) {
        String year = null;
        String month = null;
        String date = null;
        StringTokenizer dateString = new StringTokenizer(s,"/");
        int numberOfTokens = dateString.countTokens();
        //check format of data string
        if(numberOfTokens != 3)
            return false;
        else {
            month = dateString.nextToken();
            date = dateString.nextToken();
            year = dateString.nextToken();
            //check for valid number of characters
            if((month.length() != 2 && month.length() !=1) ||
               (date.length() != 1 && date.length() != 2) ||
               year.length() != 4)
                return false;
            else {
                try {
                    Calendar calendar = Calendar.getInstance();
                    //disable calendar wrapping of dates
                    calendar.setLenient(false);
                    //account for the calendar month handling
                    calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(date));
                    //need to use the calendar for it to throw an exception
                    Date testDate = calendar.getTime();
                }
                catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
    }//end of testValidDate


/******************************************************************************
 * Returns the number of days to next available delivery day based on boolean
 * paramaters passed to this method. If the currentDay is a valid delivery day
 * it returns 0.
 * @param currentDay  int representing the day to start looking at.
 * @param monday boolean indicating whether it's a delivery day
 * @param tuesday boolean indicating whether it's a delivery day
 * @param wednesday boolean indicating whether it's a delivery day
 * @param thursday boolean indicating whether it's a delivery day
 * @param friday boolean indicating whether it's a delivery day
 * @param saturday boolean indicating whether it's a delivery day
 * @param sunday boolean indicating whether it's a delivery day
****************************************************************************/
    public static synchronized int daysToNextAvailableDeliveryDay (int currentDay,
                                                                   boolean monday,
                                                                   boolean tuesday,
                                                                   boolean wednesday,
                                                                   boolean thursday,
                                                                   boolean friday,
                                                                   boolean saturday,
                                                                   boolean sunday){
      int count = 0;
      if(currentDay == Calendar.MONDAY) {
        if(monday)
          count = 0;
        else if(tuesday)
          count = 1;
        else if(wednesday)
          count = 2;
        else if(thursday)
          count = 3;
        else if(friday)
          count = 4;
        else if(saturday)
          count = 5;
        else if(sunday)
          count = 6;
      }
      else if(currentDay == Calendar.TUESDAY) {
        if(tuesday)
          count = 0;
        else if(wednesday)
          count = 1;
        else if(thursday)
          count = 2;
        else if(friday)
          count = 3;
        else if(saturday)
          count = 4;
        else if(sunday)
          count = 5;
        else if(monday)
          count = 6;
      }
      else if(currentDay == Calendar.WEDNESDAY) {
        if(wednesday)
          count = 0;
        else if(thursday)
          count = 1;
        else if(friday)
          count = 2;
        else if(saturday)
          count = 3;
        else if(sunday)
          count = 4;
        else if(monday)
          count = 5;
        else if(tuesday)
          count = 6;
      }
      else if(currentDay == Calendar.THURSDAY) {
        if(thursday)
          count = 0;
        else if(friday)
          count = 1;
        else if(saturday)
          count = 2;
        else if(sunday)
          count = 3;
        else if(monday)
          count = 4;
        else if(tuesday)
          count = 5;
        else if(wednesday)
          count = 6;
      }
      else if(currentDay == Calendar.FRIDAY) {
        if(friday)
          count = 0;
        else if(saturday)
          count = 1;
        else if(sunday)
          count = 2;
        else if(monday)
          count = 3;
        else if(tuesday)
          count = 4;
        else if(wednesday)
          count = 5;
        else if(thursday)
          count = 6;
      }
      else if(currentDay == Calendar.SATURDAY) {
        if(saturday)
          count = 0;
        else if(sunday)
          count = 1;
        else if(monday)
          count = 2;
        else if(tuesday)
          count = 3;
        else if(wednesday)
          count = 4;
        else if(thursday)
          count = 5;
        else if(friday)
          count = 6;
      }
      else if(currentDay == Calendar.SUNDAY) {
        if(sunday)
          count = 0;
        else if(monday)
          count = 1;
        else if(tuesday)
          count = 2;
        else if(wednesday)
          count = 3;
        else if(thursday)
          count = 4;
        else if(friday)
          count = 5;
        else if(saturday)
          count = 6;
      }

      return count;
    }

/******************************************************************************
 * Returns the number of days to previous available delivery day based on
 * boolean paramaters passed to this method. If the currentDay is a valid
 * delivery day it returns 0.
 * @param currentDay  int representing the day to start looking at.
 * @param monday boolean indicating whether it's a delivery day
 * @param tuesday boolean indicating whether it's a delivery day
 * @param wednesday boolean indicating whether it's a delivery day
 * @param thursday boolean indicating whether it's a delivery day
 * @param friday boolean indicating whether it's a delivery day
 * @param saturday boolean indicating whether it's a delivery day
 * @param sunday boolean indicating whether it's a delivery day
****************************************************************************/
    public static synchronized int daysToPreviousAvailableDevliveryDay(int currentDay,
                                                    boolean monday,
                                                    boolean tuesday,
                                                    boolean wednesday,
                                                    boolean thursday,
                                                    boolean friday,
                                                    boolean saturday,
                                                    boolean sunday){
      int count = 0;
      if(currentDay == Calendar.MONDAY) {
        if(monday)
          count = 0;
        else if(sunday)
          count = 1;
        else if(saturday)
          count = 2;
        else if(friday)
          count = 3;
        else if(thursday)
          count = 4;
        else if(wednesday)
          count = 5;
        else if(tuesday)
          count = 6;
      }
      else if(currentDay == Calendar.TUESDAY) {
        if(tuesday)
          count = 0;
        else if(monday)
          count = 1;
        else if(sunday)
          count = 2;
        else if(saturday)
          count = 3;
        else if(friday)
          count = 4;
        else if(thursday)
          count = 5;
        else if(wednesday)
          count = 6;
      }
      else if(currentDay == Calendar.WEDNESDAY) {
        if(wednesday)
          count = 0;
        else if(tuesday)
          count = 1;
        else if(monday)
          count = 2;
        else if(sunday)
          count = 3;
        else if(saturday)
          count = 4;
        else if(friday)
          count = 5;
        else if(thursday)
          count = 6;
      }
      else if(currentDay == Calendar.THURSDAY) {
        if(thursday)
          count = 0;
        else if(wednesday)
          count = 1;
        else if(tuesday)
          count = 2;
        else if(monday)
          count = 3;
        else if(sunday)
          count = 4;
        else if(saturday)
          count = 5;
        else if(friday)
          count = 6;
      }
      else if(currentDay == Calendar.FRIDAY) {
        if(friday)
          count = 0;
        else if(thursday)
          count = 1;
        else if(wednesday)
          count = 2;
        else if(tuesday)
          count = 3;
        else if(monday)
          count = 4;
        else if(sunday)
          count = 5;
        else if(saturday)
          count = 6;
      }
      else if(currentDay == Calendar.SATURDAY) {
        if(saturday)
          count = 0;
        else if(friday)
          count = 1;
        else if(thursday)
          count = 2;
        else if(wednesday)
          count = 3;
        else if(tuesday)
          count = 4;
        else if(monday)
          count = 5;
        else if(sunday)
          count = 6;
      }
      else if(currentDay == Calendar.SUNDAY) {
        if(sunday)
          count = 0;
        else if(saturday)
          count = 1;
        else if(friday)
          count = 2;
        else if(thursday)
          count = 3;
        else if(wednesday)
          count = 4;
        else if(tuesday)
          count = 5;
        else if(monday)
          count = 6;
      }

      return count;
    }



}