package radian.tcmis.common.util;

import java.util.*;

/*****************************************************************************
 * A <code>Comparator</code> for use by the <code>DataSet.sortedIterator
 * </code> methods.  Currently, allows the specification of one or more
 * column names to compare.<p>
 *
 * Invalid column names will not cause an exception to be thrown, rather,
 * the <code>DataSetRowComparator</code> will retrieve <code>null</code> for
 * each of the <code>DataSetRow</code> objects being compared and will
 * therefore consider them equal.<p>
 *
 * A possible future refinement will allow the specification of whether
 * the sort should be ascending or descending, possibly by column.
 *
 ****************************************************************************/
public class DataSetRowComparator implements Comparator {
    private String[] compareItems;

    /*****************************************************************************
     * Constructor that takes only one column name to base the comparison on
     *
     * @param compareItem  the name of the column to sort on
     ****************************************************************************/
    public DataSetRowComparator(String compareItem) {
        this.compareItems = new String[] {compareItem};
    }

    /*****************************************************************************
     * Constructor that takes a <code>String</code> array of column names to base
     * the comparison on.  The column names should go from major to minor.
     *
     * @param compareItems  the <code>String</code> array of column names
     ****************************************************************************/
    public DataSetRowComparator(String[] compareItems) {
        this.compareItems = compareItems;
    }

    /*****************************************************************************
     * The actual <code>compare</code> method that will be used by the
     * <code>Collections.sort</code> method to determine what order to put the
     * <code>DataSetRow</code> objects in.<p>
     *
     * It works by iterating through the different column names specified as
     * sort columns when the <code>DataSetRowComparator</code> and comparing
     * the contents of that column.  If they are unequal, the method breaks
     * out of the loop and returns either a negative integer if object #1 is
     * less than object #2 or a positive integer if it is greater, otherwise it
     * keeps iterating farther through the column names.  If it runs out of columns
     * and they've all been equal, returns 0.<p>
     *
     * This method handles a <code>null</code> value as being less than any non-
     * null value, and two <code>null</code> values as being equal to each other.
     *
     * @param o1  the first <code>DataSetRow</code> to be compared
     * @param o2  the second <code>DataSetRow</code> to be compared
     * @return an integer which is positive if o1 > o2, negative if o1 < o2, and
     *          = 0 if o1 = o2
     * @throws ClassCastException If either of the objects passed to it can not
     *          be cast as <code>DataSetRow</code> objects
     ****************************************************************************/
    public int compare(Object o1, Object o2)
        throws ClassCastException {
        DataSetRow ld1 = (DataSetRow) o1;
        DataSetRow ld2 = (DataSetRow) o2;
        for (int i = 0; i < compareItems.length; i++) {
            // get the respective values for the current key
            Object ld1Value = ld1.get(compareItems[i]);
            Object ld2Value = ld2.get(compareItems[i]);
            // if both are null, they are equal, so do nothing yet
            if (ld1Value == null && ld2Value == null) {
            }
            // if first is null and second isn't, first is less than second
            // so return less than value
            else if (ld1Value == null) {
                return -1;
            }
            // if second is null and the first isn't, first is greater than
            // second, so return greater than value
            else if (ld2Value == null) {
                return 1;
            }
            else {
                int compareResult;
                // if the class that the values belong to implements
                // Comparable, use that classes' compareTo method to
                // compare the two values and return the result of that
                // method
                if (ld1Value instanceof Comparable) {
                    compareResult = ((Comparable) ld1Value).compareTo(
                        (Comparable) ld2Value);
                }
                // if the two values belong to a class that doesn't
                // implement Comparable, we have no useful way to compare
                // them, so let's regard them as equal
                else {
                    compareResult = 0;
                }
                if (compareResult != 0) {
                    return compareResult;
                }
            }
        }
        // if we got all the way through the loop without returning
        // from the method, then the two items must be equal (in
        // terms of sorting), so return the equal value
        return 0;
    }

    /*****************************************************************************
     * Lists the column names being used to sort on, by calling the <code>
     * toString</code> method of the array containing those column names.  This
     * method is primarily here for debugging purposes.
     *
     * @return a <code>String</code> representation of the array of column names
     ****************************************************************************/
    public String toString() {
        return compareItems.toString();
    }
}
