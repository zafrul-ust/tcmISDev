package radian.tcmis.common.util;
/***************************************************************************
  * CLASSNAME: SqlHandler<BR>
  * Function: <BR>
  * instances: <BR>
  * methods: delimitString, validQuery.<BR>
  ***************************************************************************/

public class SqlHandler  {

    public SqlHandler() {
    }//end constructor

/******************************************************************************
 * Returns a single-quote delimited string if String input is not null,
 * otherwise returns null.  Also calls validQuery method. For use in
 * constructing SQL statements.
 *
 * @param inputString the string to be converted
 *****************************************************************************/
    public static synchronized String delimitString(String inputString) {
        if (inputString == null) {
            return null;
        }
        else {
            return "'" + validQuery(inputString) + "'";
        }
    }

/******************************************************************************
  *           Replaces any ' characters with '' to allow for querying Oracle.<BR>
  * @param s  String to be checked.
  ****************************************************************************/
    public static synchronized String validQuery(String s) {
        //s = s.replaceAll("'","''");
        //since running on 1.3 we can't use above method...
        if(s.indexOf('\'') >= 0) {
          s = replace(s, "'", "''");
        }
        return s;
    }//end validQuery

/******************************************************************************
 *  Equivalent of the String.replaceAll method in java 1.4<BR>
 * @param str  String to be checked.
 * @param from substring to be replaced
 * @param to substring to be put in place
****************************************************************************/
    public static synchronized String replace(String str,
                                              String from,
                                              String to) {
      StringBuffer sb = new StringBuffer();
      int cursor = 0;
      int pos = str.indexOf(from);
      while ((pos >= 0) && (cursor < str.length()))    {
        if (pos > cursor)
          sb.append(str.substring(cursor, pos));
        sb.append(to);
        cursor = pos + from.length();
        if (cursor < str.length())
          pos = str.indexOf(from, cursor);
      }
      if (cursor < str.length())
        sb.append(str.substring(cursor));
      return sb.toString();
    }

}//end SqlHandler
