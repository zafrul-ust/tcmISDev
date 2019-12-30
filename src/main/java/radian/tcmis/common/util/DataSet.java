package radian.tcmis.common.util;

import java.util.*;

/*****************************************************************************
 * Holds a collection of <code>DataSetRow</code> objects, and gives
 * convenience methods to access those objects.  Generally a <code>DataSet
 * </code> contains the results of an SQL query, with a collection of column
 * names, and a collection of <code>DataSetRow</code> objects, each
 * containing a row from the <code>ResultSet</code>.<p>
 *
 * Access to these <code>DataRow</code> objects is provided either
 * directly (through the <code>getDataSetRow</code> method or through an
 * iterator, either sorted or unsorted (through the <code>sortedIterator
 * </code> and <code> iterator</code> methods, respectively).
 *
 ****************************************************************************/

public class DataSet extends AbstractCollection {

    private ArrayList columnNames = new ArrayList();
    private ArrayList dataSetRowArrayList = new ArrayList();
    private String[] defaultSortColumns = null;

    /*****************************************************************************
     * Default empty constructor.  Creates a <code>DataSet</code> with no default
     * sort column (so sorts will default to sorting on the first column).
     ****************************************************************************/
    public DataSet() {
        super();
    }

    /*****************************************************************************
     * Constructor that allows you to specify which column will be sorted on by
     * default.
     *
     * @param defaultSortColumn the name of the column that you want the <code>
     *          sortedIterator</code> method to sort on, if one is not specified
     ****************************************************************************/
    public DataSet(String defaultSortColumn) {
        this(new String[] {defaultSortColumn});
    }

    public DataSet(String[] defaultSortColumns) {
        this();
        dataSetRowArrayList = new ArrayList();
        this.defaultSortColumns = defaultSortColumns;
    }

    /*****************************************************************************
     * Adds the <code>DataSetRow</code> objects in the specified <code>DataSet
     * </code> to the end of the this object's collection of <code>DataSetRow
     * </code> objects.
     *
     * @param dataSet   the <code>DataSet</code> whose rows you wish to add
     ****************************************************************************/
    public void addDataSet(DataSet dataSet) {
        Iterator rowIter = dataSet.iterator();
        while (rowIter.hasNext()) {
            this.dataSetRowArrayList.add((DataSetRow) rowIter.next());
        }
    }

    /*****************************************************************************
     * Adds a <code>DataSetRow</code> object (usually representing a row in a
     * <code>ResultSet</code>) to this object's collection of <code>DataSetRow
     * </code> objects.
     *
     * @param dataSetRow  the <code>DataSetRow</code> object to add
     ****************************************************************************/
    public void addDataSetRow(DataSetRow dataSetRow) {
        this.dataSetRowArrayList.add(dataSetRow);
    }

    /*****************************************************************************
     * Adds a column name to the internal collection of column names.  If there
     * is not a default sort column already set, it will be set to this column
     * name.
     *
     * @param columnName  the column name to add
     ****************************************************************************/
    public void addColumnName(String columnName) {
        if (defaultSortColumns == null && columnNames.size() == 0) {
            this.defaultSortColumns = new String[] {columnName};
        }
        this.columnNames.add(columnName);
    }

    /*****************************************************************************
     * Gets the column name specified.
     *
     * @param columnNumber  the position of the column name you want to get
     * @return the name of the column in the specified position
     ****************************************************************************/
    public String getColumnName(int columnNumber) {
        return (String)this.columnNames.get(columnNumber - 1);
    }

    /*****************************************************************************
     * Gets the column names as a <code>String</code> array
     *
     * @return a <code>String</code> array containing all the column names in
     *          this <code>Lookup</code> in order.
     ****************************************************************************/
    public String[] getColumnNameArray() {
        return (String[])this.columnNames.toArray(
            new String[columnNames.size()]);
    }

    /*****************************************************************************
     * Returns a <code>Collection</code> containing the values for a particular
     * column in the contained <code>DataSetRow</code> objects.  Currently does
     * not throw an exception if the columnName is invalid.  Values that are ints
     * will be wrapped in an <code>Integer</code>.
     *
     * @param columnName  the name of the column to get the values for
     * @returns a <code>Collection</code> containing the values for that column
     ****************************************************************************/
    public Collection getColumnValues(String columnName) {
        Collection values = new ArrayList();
        Iterator dsrIter = iterator();
        while (dsrIter.hasNext()) {
            values.add(((DataSetRow) dsrIter.next()).get(columnName));
        }
        return values;
    }

    /*****************************************************************************
     * Returns a <code>Collection</code> containing the values for a particular
     * column in the contained <code>DataSetRow</code> objects.  Currently does
     * not throw an exception if the columnName is invalid.  Converts all values
     * to strings.
     *
     * @param columnName  the name of the column to get the values for
     * @returns a <code>Collection</code> of <code>String</code> representations
     *          of the values for that column
     ****************************************************************************/
    public Collection getColumnValuesAsStrings(String columnName) {
        Collection values = new ArrayList();
        Iterator dsrIter = iterator();
        while (dsrIter.hasNext()) {
            values.add(((DataSetRow) dsrIter.next()).get(columnName).toString());
        }
        return values;
    }

    /*****************************************************************************
     * Gets the <code>DataSetRow</code> in the position specified.  Numbering
     * starts from 1, rather than 0.
     *
     * @param indexNo  the row number of the <code>DataSetRow</code> you want
     * @return the <code>DataRow</code> in the specified position
     * @throws IndexOutOfBoundsException  if the indexNo specified is < 1 or
     *          greater than the number of <code>DataSetRow</code> objects in
     *          this <code>DataSet</code>
     ****************************************************************************/
    public DataSetRow getDataSetRow(int indexNo)
        throws IndexOutOfBoundsException {

        return (DataSetRow) dataSetRowArrayList.get(indexNo - 1);
    }

    /*****************************************************************************
     * Gets the count of <code>DataSetRow</code> objects in this collection.
     *
     * @return the number of <code>DataSetRow</code> objects
     ****************************************************************************/
    public int getRowCount() {
        return this.dataSetRowArrayList.size();
    }

    /*****************************************************************************
     * Returns an <code>Iterator</code> of all the <code>DataRow</code>
     * objects in this collection, in no guaranteed order ( although it will
     * generally be in the order they were loaded, which is presumably the order
     * they were in the original <code>ResultSet</code>
     *
     * @return an <code>Iterator</code> of <code>DataRow</code> objects
     ****************************************************************************/
    public Iterator iterator() {
        return dataSetRowArrayList.iterator();
    }

    /*****************************************************************************
     * Returns an <code>Iterator</code> of all the <code>DataRow</code>
     * objects in this collection, sorted by either the default sort order (if
     * one was set) or ordered by the first column of the <code>DataRow</code>
     * objects.
     *
     * @return an <code>Iterator</code> of <code>DataRow</code> objects
     ****************************************************************************/
    public Iterator sortedIterator() {
        Iterator iterator = null;
        // if user has stated default sort columns, sort on those columns
        if (defaultSortColumns != null) {
            iterator = this.sortedIterator(defaultSortColumns);
        }
        // otherwise, if there is any data in this object, get the name of
        // the first key in one of our LookupData objects and sort on that
        // column
        else if (getRowCount() > 0) {
            String sortColumn =
                (String) ((DataSetRow) dataSetRowArrayList.get(0)).keySet()
                .iterator().next();
            iterator = this.sortedIterator(sortColumn);
        }
        // if there is no data, may as well just return a non-sorted
        // iterator, since there's nothing to sort
        else {
            iterator = this.iterator();
        }
        return iterator;
    }

    /*****************************************************************************
     * Returns an <code>Iterator</code> of all the <code>DataSetRow</code>
     * objects in this collection, sorted by the column specified.
     *
     * @param sortColumn  the name of the column to sort by
     * @return an <code>Iterator</code> of <code>DataSetRow</code> objects
     ****************************************************************************/
    public Iterator sortedIterator(String sortColumn) {
        return this.sortedIterator(new String[] {sortColumn});
    }

    public int size() {
        return this.getRowCount();
    }

    /*****************************************************************************
     * Returns an <code>Iterator</code> of all the <code>DataSetRow</code>
     * objects in this collection, sorted by the columns specified.  The column
     * names to sort by are specified in a String array.  The easiest way to do
     * this is to create the array and populate it in the method call, for
     * instance:<p>
     * <code>dataSet.sortedIterator( new String[] { "COLUMN1", "COLUMN2" } )</code>
     * <p>
     * The <code>Iterator</code> will be sorted by the column names in major to
     * minor order, i.e. in the example above by COLUMN2 within COLUMN1.
     *
     * @param sortColumns  a <code>String</code> array containing the names
     *          of the columns to sort by, in major to minor order
     * @return an <code>Iterator</code> of <code>DataSetRow</code> objects
     ****************************************************************************/
    public Iterator sortedIterator(String[] sortColumns) {
        DataSetRowComparator comp =
            new DataSetRowComparator(sortColumns);
        ArrayList sortedList = new ArrayList();
        sortedList.addAll(dataSetRowArrayList);
        Collections.sort(sortedList, comp);
        return sortedList.iterator();
    }

    /*****************************************************************************
     * Returns the contents of the <code>DataSet</code> in a readable form
     ****************************************************************************/
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("COLUMNS:\n");
        String[] columnNames = this.getColumnNameArray();
        for (int i = 0; i < columnNames.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(columnNames[i]);
        }
        sb.append("\n\nROWS:\n");
        Iterator rowIter = this.iterator();
        while (rowIter.hasNext()) {
            sb.append(rowIter.next() + "\n");
        }
        return sb.toString();
    }

}
