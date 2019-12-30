package radian.tcmis.common.util;
/***************************************************************************
  * CLASSNAME: CommonHandler<BR>
  * Function: Miscellaneous utility methods.<BR>
  * methods: isValidEmail, booleanToInd, indToBoolean.<BR>
***************************************************************************/

public class CommonHandler  {

    public CommonHandler() {
    }//end constructor

/******************************************************************************
  *           Checks if it's a valid email address.<BR>
  * @param s  Email string to be checked.
  ****************************************************************************/
    public static synchronized boolean isValidEmail(String s) {
        if(s.indexOf("@")<1 || s.indexOf(".")<3 || s.length()<6)
            return false;
        return true;
    }//end testValidEmail

/*****************************************************************************
 * Returns a Y or N in place of a boolean
 *
 * @param booleanValue  the boolean value to be expressed as a Y or N
 ****************************************************************************/
    public static synchronized String booleanToShortIndicator(boolean booleanValue) {
        if (booleanValue) {
            return "Y";
        }
        else {
            return "N";
        }
    }

/*****************************************************************************
 * Returns  "Yes" or "No" in place of a boolean
 *
 * @param booleanValue  the boolean value to be expressed as a Yes or No
****************************************************************************/
    public static synchronized String booleanToLongIndicator(boolean booleanValue) {
        if (booleanValue) {
            return "Yes";
        }
        else {
            return "No";
        }
    }


/*****************************************************************************
 * Returns the boolean equivalent of an 'Y', 'Yes', 'N', or 'No' indicator.
 * Returns false if something other than 'Y' or 'Yes' is passed.
 *
 * @param indValue  the value of the indicator (should be Y or N)
 ****************************************************************************/
    public static synchronized boolean indToBoolean(String indValue) {
        if (indValue != null && (indValue.equalsIgnoreCase("Y") ||
                                 indValue.equalsIgnoreCase("YES"))) {
            return true;
        }
        else {
            return false;
        }
    }
}//end CommonHandler
