package radian.tcmis.common.util;

import java.util.*;

/*****************************************************************************
 * Class to hold a collection of <code>SearchCriterion</code> objects to be
 * used to create the "where" clause of an SQL statement.<p>
 *
 * An object of this class is passed to the <code>select</code> method of a
 * bean factory, which uses the <code>getWhereClause</code> method of <code>
 * BaseBeanFactory</code> to get the "where" clause.  It then adds it the
 * rest of the SQL query and runs it.  For instance:<p>
 *
 * <code>
 * PhoneBeanFactory factory = new PhoneBeanFactory( databaseManager, username );
 * <br>
 * SearchCriteria criteria = new SearchCriteria();<br>
 * criteria.addCriterion( "violatorID", SearchCriterion.EQUALS, "2001" );<br>
 * criteria.addCriterion( "phoneType", SearchCriterion.EQUALS, "H" );<br>
 * criteria.addValueToCriterion( "phoneType", "W" );<br>
 * criteria.addCriterion( "endDate", SearchCriteria.IS, "null" );<br>
 * return factory.getWhereClause( criteria );<br>
 * </code><p>
 * would return<p>
 * <code>
 * "where VIOLTR_ID = 2001 and ( PHONE_TYPE = 'H' or PHONE_TYPE = 'W' ) and
 * BEGIN_DATE IS NULL"<br>
 * </code><p>
 * The variable/attribute name should have been added to the factory's
 * <code>getColumnName</code> and <code>getType</code> methods, which return
 * the database column name and an integer representing the java Type of that
 * column (using constants in BaseBeanFactory for String, int, BigDecimal,
 * and Date).<p>
 * The <code>addCriterion</code> method takes either a String or a Collection
 * of Strings to represent the values to compare the variable/attribute to.
 * Dates should be in the form "MM/DD/YYYY hh:mm:ss" (i.e. 5 minutes past noon
 * on March 24th, 2003 would be "03/24/2002 12:05:00", or if you don't need
 * the time "03/24/2003" will work).<p>
 *
 ****************************************************************************/

public class SearchCriteria {

    private ArrayList criteria = new ArrayList();

    /*****************************************************************************
     * Empty constructor
     ****************************************************************************/
    public SearchCriteria() {
        super();
    }

    /*****************************************************************************
     * Constructor that adds a <code>SearchCriterion</code> to itself based on
     * the parameters.  Especially handy when creating a <code>SearchCriteria
     * </code> with only one criterion.
     *
     * @param variableName  the name of the variable/attribute
     * @param comparator  the comparator to use when comparing the variable and
     *          the value/s.  Valid comparators include all the ones specified by
     *          static variables in <code>SearchCriterion</code> (EQUALS, etc.)
     * @param value  a single value to compare the variable/attribute to
     ****************************************************************************/
    public SearchCriteria(String variableName,
                          String comparator,
                          String value) {
        this();
        addCriterion(variableName, comparator, value);
    }

    /*****************************************************************************
     * Constructor that adds a <code>SearchCriterion</code> to itself based on
     * the parameters.  Especially handy when creating a <code>SearchCriteria
     * </code> with only one criterion.
     *
     * @param variableName  the name of the variable/attribute
     * @param comparator  the comparator to use when comparing the variable and
     *          the value/s.  Valid comparators include all the ones specified by
     *          static variables in <code>SearchCriterion</code> (EQUALS, etc.)
     * @param values  a collection of values to compare the variable/attribute to
     ****************************************************************************/
    public SearchCriteria(String variableName,
                          String comparator,
                          Collection values) {
        this();
        addCriterion(variableName, comparator, values);
    }

    /*****************************************************************************
     * Creates a <code>SearchCriterion</code> object and adds it to the internal
     * collection of criteria.
     *
     * @param variableName  the name of the variable/attribute
     * @param comparator  the comparator to use when comparing the variable and
     *          the value/s.  Valid comparators include all the ones specified by
     *          static variables in <code>SearchCriterion</code> (EQUALS, etc.)
     * @param value  a single value to compare the variable/attribute to
     ****************************************************************************/
    public void addCriterion(String variableName,
                             String comparator,
                             String value) {
        ArrayList values = new ArrayList();
        values.add(value);
        addCriterion(variableName, comparator, values);
    }

    /*****************************************************************************
     * Creates a <code>SearchCriterion</code> object and adds it to the internal
     * collection of criteria.
     *
     * @param variableName  the name of the variable/attribute
     * @param comparator  the comparator to use when comparing the variable and
     *          the value/s.  Valid comparators include all the ones specified by
     *          static variables in <code>SearchCriterion</code> (EQUALS, etc.)
     * @param values  a collection of values to compare the variable/attribute to.
     *          An "or" will be placed between the comparison for each value
     ****************************************************************************/
    public void addCriterion(String variableName,
                             String comparator,
                             Collection values) {
        SearchCriterion criterion = new SearchCriterion(variableName,
                                                        comparator,
                                                        values);
        criteria.add(criterion);
    }

    /*****************************************************************************
     * Adds a value to the collection of values to be compared to a
     * variable/attribute.  If there is not already a criterion for that
     * variable/attribute, one will not be created, and no exception will be
     * thrown, yielding unexpected results, so be careful
     *
     * @param variableName  the name of the variable/attribute
     * @param value  a single value to compare the variable/attribute to, which
     *          will be added to other values already specified
     ****************************************************************************/
    public void addValueToCriterion(String variableName, String value) {
        SearchCriterion criterion = getCriterion(variableName);
        if (criterion != null) {
            criterion.getValues().add(value);
        }
    }

    /*****************************************************************************
     * Gets the <code>SearchCriterion</code> in the internal collection that
     * has the variable name specified in the parameter.  If there is no criterion
     * for that variable/attribute, returns a <code>null</code>.
     *
     *
     * @param variableName  the variable/attribute in the criterion to be
     *          returned
     * @param the <code>SearchCriterion</code> object for the variable/attribute
     *          specified
     ****************************************************************************/
    public SearchCriterion getCriterion(String variableName) {
        Iterator criterionIter = criteria.iterator();
        while (criterionIter.hasNext()) {
            SearchCriterion criterion = (SearchCriterion) criterionIter.next();
            if (criterion.getVariableName().equals(variableName)) {
                return criterion;
            }
        }
        return null;
    }

    /*****************************************************************************
     * Returns an <code>Iterator</code> containing all <code>SearchCriterion</code>
     * objects in the internal criteria collection.
     *
     * @return an Iterator of <code>SearchCriterion</code> objects
     ****************************************************************************/
    public Iterator iterator() {
        return criteria.iterator();

    }
}
