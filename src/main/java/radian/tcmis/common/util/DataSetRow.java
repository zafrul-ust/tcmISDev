package radian.tcmis.common.util;

import java.math.*;
import java.text.*;
import java.util.*;

/*****************************************************************************
 * Provides a convenient wrapper around a <code>HashMap</code>, generally one
 * containing information from a table row in a database.<p>
 * The convenience comes from multiple getXXX methods which allow you to get
 * a value from the HashMap as the correct type rather than having to cast it
 * and use appropriate conversion methods (i.e. you can call
 * <code>getInt(COLUMN_NAME)</code> and get back an <code>int</code>,
 * rather than using <code>get(COLUMN_NAME)</code> and getting back an
 * <code>Object</code>, which you then have to cast as a <code>Long</code>
 * or <code>Integer</code> and use its <code>intValue</code> method to get an
 * <code>int</code>).<p>
 * When contained in a <code>Lookup</code> multiple <code>Lookup</code>
 * objects can be sorted
 *
 *****************************************************************************/
public class DataSetRow
    extends HashMap {

    /*****************************************************************************
     * Default (and only) constructor

     *****************************************************************************/
    public DataSetRow() {
        super();
    }

    /*****************************************************************************
     * Adds a key/pair value to the <code>HashMap</code>, or overwrites a current
     * key/pair value if the key is already there.  Wraps the <code>
     * HashMap.put</code> method
     *
     * @param key the key of the key/value pair
     * @param value the value of the key/value pair
     *****************************************************************************/
    public void add(Object key, Object value) {
        this.put(key, value);
    }

    /*****************************************************************************
     * Allows you to put an <code>int</code> in the <code>HashMap</code>, by
     * calling an overloaded <code>put</code> method that wraps the <code>int
     * </code> in an <code>Integer</code>
     *
     * @param key the key of the key/value pair
     * @param value the value of the key/value pair, which will be converted to
     *          an <code>Integer</code>
     *****************************************************************************/
    public void add(Object key, int value) {
        this.put(key, value);
    }

    /*****************************************************************************
     * Returns a value from the <code>HashMap</code> as a <code>BigDecimal</code>
     *
     * @param columnName the key in the <code>HashMap</code>, corresponding to a
     *          column name in the database table the <code>LookupData</code>
     *          was built from
     * @return a <code>BigDecimal</code> representation of the value in the
     *          <code>HashMap</code>
     * @throws ParseException If the value wasn't a <code>BigDecimal</code>,
     *          <code>Float</code>, or <code>Double</code>
     *****************************************************************************/
    public BigDecimal getBigDecimal(String columnName)
        throws NumberFormatException {
        BigDecimal returnValue;
        Object columnValue = this.get(columnName);
        if (columnValue == null) {
            returnValue = new BigDecimal(0);
        }
        else if (columnValue instanceof BigDecimal) {
            returnValue = (BigDecimal) columnValue;
        }
        else if (columnValue instanceof Double) {
            returnValue = new BigDecimal(((Double) columnValue).doubleValue());
        }
        else if (columnValue instanceof Float) {
            returnValue = new BigDecimal(((Float) columnValue).doubleValue());
        }
        else {
            returnValue = new BigDecimal(columnValue.toString());
        }
        return returnValue;
    }

    /*****************************************************************************
     * Returns a value from the <code>HashMap</code> as a <code>Date</code>
     *
     * @param columnName the key in the <code>HashMap</code>, corresponding to a
     *          column name in the database table the <code>LookupData</code>
     *          was built from
     * @return a <code>Date</code> representation of the value in the
     *          <code>HashMap</code>
     * @throws ParseException If the value wasn't a <code>Date</code>,
     *          or a <code>String</code> that can be successfully parsed to a
     *          <code>Date</code>
     *****************************************************************************/
    public Date getDate(String columnName)
        throws ParseException {
        Date returnValue;
        Object columnValue = this.get(columnName);
        if (columnValue == null) {
            returnValue = null;
        }
        else if (columnValue instanceof Date) {
            returnValue = (Date) columnValue;
        }
        else {
            returnValue = DateHandler.parseOracleDateString(
                columnValue.toString());
        }
        return returnValue;
    }

    /*****************************************************************************
     * Returns a value from the <code>HashMap</code> as an <code>int</code>
     *
     * @param columnName the key in the <code>HashMap</code>, corresponding to a
     *          column name in the database table the <code>LookupData</code>
     *          was built from
     * @return an <code>int</code> representation of the value in the
     *          <code>HashMap</code>
     * @throws ParseException If the value wasn't a <code>Integer</code>,
     *          or a <code>Long</code>
     *****************************************************************************/
    public int getInt(String columnName)
        throws NumberFormatException {
        int returnValue;
        Object columnValue = this.get(columnName);
        if (columnValue == null) {
            returnValue = 0;
        }
        else if (columnValue instanceof Integer) {
            returnValue = ((Integer) columnValue).intValue();
        }
        else if (columnValue instanceof Long) {
            returnValue = ((Long) columnValue).intValue();
        }
        else {
            returnValue = Integer.parseInt(columnValue.toString());
        }
        return returnValue;
    }

    /*****************************************************************************
     * Returns a value from the <code>HashMap</code> as an <code>String</code>.
     * This one uses the <code>toString</code> method to get the <code>String
     * </code>, so it's safe to use no matter what the original class of the
     * value is.
     *
     * @param columnName the key in the <code>HashMap</code>, corresponding to a
     *          column name in the database table the <code>LookupData</code>
     *          was built from
     * @return an <code>String</code> representation of the value in the
     *          <code>HashMap</code>
     *****************************************************************************/
    public String getString(String columnName) {
        Object columnValue = this.get(columnName);
        if (columnValue == null) {
            return null;
        }
        else {
            return columnValue.toString();
        }
    }

    /*****************************************************************************
     * Checks to see if the value of a key/value pair is null
     *
     * @param columnName the key in the <code>HashMap</code>, corresponding to a
     *          column name in the database table the <code>LookupData</code>
     *          was built from
     * @return a <code>boolean</code> which is true if the value is null or false
     *          if it isn't
     *****************************************************************************/
    public boolean isNull(String columnName) {
        return (this.get(columnName) == null);
    }

    /*****************************************************************************
     * Wraps an <code>int</code> in an <code>Integer</code> and puts it in the
     * <code>HashMap</code>.  Overload of the <code>HashMap.put</code> method,
     * which only accepts an <code>Object</code>
     *
     * @param o the key in the <code>HashMap</code>, corresponding to a
     *          column name in the database table the <code>LookupData</code>
     *          was built from
     * @param i the <code>int</code> to be stored as an <code>Integer</code> value
     *****************************************************************************/
    public void put(Object o, int i) {
        this.put(o, new Integer(i));
    }
}
