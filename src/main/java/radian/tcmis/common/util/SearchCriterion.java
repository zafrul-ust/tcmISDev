package radian.tcmis.common.util;

import java.util.*;

/*****************************************************************************
 * Class that holds the search criteria for one database column.<p>
 *
 * Generally, you will not work with this class directly, but rather will
 * add a <code>SearchCriterion</code> to a <code>SearchCriteria</code>
 * collection with the <code>addCriterion</code> method of that class, and
 * add values to compare to with the <code>addValueToCriterion</code> method,
 * if you have more than one value to compare to.<p>
 *
 * See the javadocs of {@link SearchCriteria} for more information
 *
 ****************************************************************************/
public class SearchCriterion {
    public static String EQUALS = "=";
    public static String GREATER_THAN = ">";
    public static String GREATER_THAN_OR_EQUAL_TO = ">=";
    //public static String IGNORE_CASE = "EIC";
    public static String IS = "IS";
    public static String IS_NOT = "IS NOT";
    public static String LESS_THAN = "<";
    public static String LESS_THAN_OR_EQUAL_TO = "<=";
    public static String LIKE = "LIKE";
    public static String NOT_EQUAL = "!=";
    private Object[] internalArray = new Object[3];

    /*****************************************************************************
     * Default (and only) constructor.  Takes a variable name, a comparator and a
     * collection of values to compare to, and creates a new
     * <code>SearchCriterion</code> object.  Generally, it will be easier to use
     * the <code>addCriterion</code> method in <code>SearchCriteria</code> as this
     * will allow you to put in a single value instead of a collection and will
     * add the criterion to the criteria collection when it has been created.
     *
     * @param variableName  the name of the variable/attribute that will be
     *          translated into a column name when an SQL query is created from
     *          this criterion
     * @param comparator  the comparator that expresses the relationship you
     *          wish to have between the variable and the values
     * @param values  a collection of values you wish the variable/attribute to
     *          be compared to.  A valid comparison to any one of them will
     *          cause a row to be returned from the database.
     ****************************************************************************/
    public SearchCriterion(String variableName, String comparator,
                           Collection values) {
        super();
        setVariableName(variableName);
        setComparator(comparator);
        setValues(values);
    }

    //setters
    public void setComparator(String comparator) {
        internalArray[1] = comparator;
    }

    public void setValues(Collection values) {
        internalArray[2] = values;
    }

    public void setVariableName(String variableName) {
        internalArray[0] = variableName;
    }

    // getters
    public String getComparator() {
        return (String) internalArray[1];
    }

    public Collection getValues() {
        return (Collection) internalArray[2];
    }

    public String getVariableName() {
        return (String) internalArray[0];
    }
}
