package radian.tcmis.common.util;

import java.math.*;

/***************************************************************************
 * CLASSNAME: StringHandler<BR>
 * Function: <BR>
 ***************************************************************************/
public class StringHandler {
    public StringHandler() {
    } //end constructor

    /******************************************************************************
     * Returns an <code>Object</code> as a <code>null</null> if it turns out to be
     * an empty <code>String</code>.  Otherwise returns it as a <code>String</code>
     *
     * @param input  the <code>Object</code> to convert
     * @return a <code>String</code> representation of the input, which will be a
     *          <code>null</code> if the input was null or zero length
     *****************************************************************************/
    public static synchronized String nullIfEmpty(Object input) {
        // definition of empty includes the last part because this is how
        // the Struts html:hidden tag seems to demonstrate null
        if (input == null || input.toString().trim().length() == 0 ||
            (input.toString().length() > 2 &&
             input.toString().substring(0, 2).equals("[L") &&
             input.toString().indexOf("@") > 0)) {
            return null;
        }
        else {
            return input.toString();
        }
    }

    /******************************************************************************
     * Returns an empty string if the object is null. Otherwise returns it as
     * a <code>String</code>
     * @param input  the <code>Object</code> to convert
     * @return a <code>String</code> representation of the input, which will be a
     *          <code>null</code> if the input was null or zero length
     *****************************************************************************/
    public static synchronized String emptyIfNull(Object input) {
        if (input == null) {
            return "";
        }
        else {
            return input.toString();
        }
    }

    /******************************************************************************
     * Returns an "0" string if the object is null. Otherwise returns it as
     * a <code>String</code>
     * @param input  the <code>Object</code> to convert
     * @return a <code>String</code> representation of the input, which will be a
     *          <code>null</code> if the input was null or zero length
     *****************************************************************************/
    public static synchronized String zeroIfNull(Object input) {
        if (input == null) {
            return "0";
        }
        else {
            return input.toString();
        }
    }


    /******************************************************************************
     * Returns an <code>int</code> as a <code>null</null> if it is equal to zero.
     * Otherwise returns it as a <code>String</code>
     *
     * @param inputInt  the <code>int</code> to convert
     * @return  a <code>String</code> representation of the <code>int</code>
     *          that is <code>null</code> if the <code>int</code> was equal to 0
     *****************************************************************************/
    public static synchronized String nullIfZero(BigDecimal bigDecimal) {
        if (bigDecimal == null || bigDecimal.compareTo(new BigDecimal(0))
            == 0) {
            return null;
        }
        else {
            return bigDecimal.toString();
        }
    }

    /******************************************************************************
     * Returns an <code>int</code> as a <code>null</null> if it is equal to zero.
     * Otherwise returns it as a String
     *
     * @param inputInt  the <code>int</code> to convert
     * @return  a <code>String</code> representation of the <code>int</code>
     *          that is <code>null</code> if the <code>int</code> was equal to 0
     *****************************************************************************/
    public static synchronized String nullIfZero(int inputInt) {
        if (inputInt == 0) {
            return null;
        }
        else {
            return "" + inputInt;
        }
    }

    /******************************************************************************
     *           Checks if string consists of only letters.<BR>
     * @param s  String to be checked.
     ****************************************************************************/
    public static synchronized boolean testValidLetter(String s) {
        char charArray[] = s.toCharArray();
        for (int i = 0; i < charArray.length; ++i) {
            if (!Character.isLetter(charArray[i])) {
                return false;
            } //end if
        } //end for
        return true;
    } //end testValidLetter

    /******************************************************************************
     *           Checks if string consists of only digits.<BR>
     * @param s  String to be checked.
     ****************************************************************************/
    public static synchronized boolean testValidDigit(String s) {
        char charArray[] = s.toCharArray();
        for (int i = 0; i < charArray.length; ++i) {
            if (!Character.isDigit(charArray[i])) {
                return false;
            } //end if
        } //end for
        return true;
    } //end testValidDigit

    /******************************************************************************
     *           Checks if string consists of only letters and digits.<BR>
     * @param s  String to be checked.
     ****************************************************************************/
    public static synchronized boolean testValidLetterOrDigit(String s) {
        char charArray[] = s.toCharArray();
        for (int i = 0; i < charArray.length; ++i) {
            if (!Character.isLetter(charArray[i]) &&
                !Character.isDigit(charArray[i])) {
                return false;
            } //end if
        } //end for
        return true;
    } //end testValidLetterOrDigit

    /******************************************************************************
     *           Checks if it's of valid double primitive data type.<BR>
     * @param s  String to be checked.
     ****************************************************************************/
    public static synchronized boolean isValidDouble(String s) {
        try {
            Double d = new Double(s);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    } //end testValidDouble

    /******************************************************************************
	 * Returns false if it turns out to be an empty <code>String</code>. Otherwise returns true
	 *
	 * @param input
	 *                the <code>String</code> to convert
	 * @return a boolean
	 *****************************************************************************/

	public static boolean isBlankString(String s) {
		if (s == null || s.length() < 1) {
			return true;
		}
		if (s.trim().length() > 0) {
			return false;
		}
		return true;
	}

} //end StringHandler
