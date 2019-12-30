package radian.tcmis.common.framework;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

import radian.tcmis.common.framework.BaseDataBean;
import radian.tcmis.common.exceptions.BaseException;
import radian.tcmis.common.exceptions.DataBeanCreationException;

import radian.tcmis.common.util.DataSetRow;
import radian.tcmis.common.util.SearchCriteria;
import radian.tcmis.common.util.SearchCriterion;
import radian.tcmis.common.util.SqlHandler;

/*****************************************************************************
 * Base class for Bean Factories.  Provides static attributes with the names
 * of common table columns, in the form
 * <code>ATTRIBUTE_&lt;column name&gt;</code>.  These can then be used
 * when writing SQL queries, so that a column name change in the database
 * can easily be accomodated in the system.<p>
 * The base class also stores a DbManager for the factory to use when
 * processing .  Every subclass should have
 * a constructor with dbManager as parameter that calls <code>
 * super(dbManager)</code>.<p>
 *
 ****************************************************************************/
public abstract class BaseBeanFactory {

    public static final int TYPE_BIGDECIMAL = 0;
    public static final int TYPE_DATE = 1;
    public static final int TYPE_INT = 2;
    public static final int TYPE_STRING = 3;

    private String client;
    //private DbManager dbManager;

/*****************************************************************************
 * Constructor.
 *
 * @param dbManager the <code>DbManager</code> to be used to communicate with
 *                  the database
****************************************************************************/
    public BaseBeanFactory(String client) {
      super();
      setClient(client);
    }

    public void setClient(String client) {
      this.client = client;
    }

    public String getClient() {
      return this.client;
    }

/*    public BaseBeanFactory(DbManager dbManager) {
        super();
        setDbManager(dbManager);
    }

    // setters
    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    // getters
    public DbManager getDbManager() {
        return this.dbManager;
    }
*/
/*****************************************************************************
 * Returns the table column name associated with a data bean attribute.  Each
 * factory should have it's own version of this, which calls the base version
 * as its last resort (in its final ELSE clause).<p>
 *
 * Returns a null if the attribute name is not found.
 *
 * @param attributeName  the name of the data bean attribute
 * @return  the name of the database table column corresponding to the
 *          attribute
****************************************************************************/

    public String getColumnName(String attributeName) {
        return null;
    }

/*****************************************************************************
 * Returns an int representing the Java data type associated with a database
 * table column from the type constants in this class.  Uses bean
 * introspection to find the attribute specified and return an int
 * representing the data type of that attribute.  Defaults to String if the
 * attribute is not found.<p>
 *
 * If you want to be able to use attributes in your SearchCriteria that are
 * not in your data bean, add an if statement to your factory's <code>
 * getType( attributeName )</code> method that catches that attribute name
 * and returns a type constant, with the call to this method in your final
 * else.  For instance:<p>
 *
 * <code>
 * if (attributeName.equals("myNonDataBeanVariable") {<br>
 * <dd>return TYPE_BIGDECIMAL;<br>
 * }<br>
 * else {<br>
 * <dd>return super(attributeName, MyBean.class);<br>
 * }<br>
 * </code><p>
 *
 * Defaults to TYPE_STRING if column name is not found.<p>
 *
 * Each factory should implement this method with at least the call to <code>
 * super(attributeName, class)</code> (as in the example above ).
 *
 * @param columnName  the name of the database table column to return a type
 *                    for
 * @return an int corresponding to a constant representing the java data type
 *                (String, BigDecimal, int, or Date)
****************************************************************************/

    public int getType(String attributeName) {
        return getType(attributeName, BaseDataBean.class);
    }

/*****************************************************************************
 * Returns an int representing the Java data type associated with a database
 * table column from the type constants in this class.  Uses bean
 * introspection to find the attribute specified and return an int
 * representing the data type of that attribute.  Defaults to String if the
 * attribute is not found.<p>
 *
 * If you want to be able to use attributes in your SearchCriteria that are
 * not in your data bean, add an if statement to your factory's <code>
 * getType(attributeName)</code> method that catches that attribute name
 * and returns a type constant, with the call to this method in your final
 * else.  For instance:<p>
 *
 * <code>
 * if (attributeName.equals("myNonDataBeanVariable") {<br>
 * <dd>return TYPE_BIGDECIMAL;<br>
 * }<br>
 * else {<br>
 * <dd>return super(attributeName, PhoneBean.class);<br>
 * }<br>
 * </code><p>
 *
 * Defaults to TYPE_STRING if column name is not found.
 *
 * @param columnName  the name of the database table column to return a type
 *              for
 * @param beanClass  the <code>Class</code> to obtain type information from
 *              (i.e. PhoneBean.class )
 * @return an int corresponding to a constant representing the java data type
 *              (String, BigDecimal, int, or Date)
****************************************************************************/
    public int getType(String attributeName, Class beanClass) {
        try {
            PropertyDescriptor pDesc = new PropertyDescriptor(attributeName,
                beanClass);
            String className = pDesc.getPropertyType().getName();
            if (className == null) {
                return TYPE_STRING;
            }
            else if (className.equals("java.math.BigDecimal")) {
                return TYPE_BIGDECIMAL;
            }
            else if (className.equals("java.util.Date")) {
                return TYPE_DATE;
            }
            else if (className.equals("int")) {
                return TYPE_INT;
            }
            else {
                return TYPE_STRING;
            }
        }
        catch (IntrospectionException e) {
            return TYPE_STRING;
        }
    }

/*****************************************************************************
 * Takes a <code>SearchCriteria</code> object and turns it into the "where"
 * clause of an SQL query.  If a <code>SearchCriterion</code> has more than
 * one value, the values are "or"ed.  For instance, if the criterion is for
 * "someID", the comparator and the "400", and "401" have been put in the
 * values collection, then they will be output as:<p>
 * <code>
 * (SOME_ID = 400 or SOME_ID = 401)
 * </code>
 * <p>
 * Multiple criteria will be put together with "and".<p>
 *
 * @param criteria  the <code>SearchCriteria</code> object to convert. A null
 *                  will return an empty where clause.
 * @return a String with a SQL "where" clause representing the criteria
****************************************************************************/
    public String getWhereClause(SearchCriteria criteria) {
        boolean createWhereClause = false;
        StringBuffer sb = new StringBuffer();
        Iterator criterionIter = null;
        //first check if criteria is null or empty
        if(criteria == null) {
            createWhereClause = false;
        }
        else {
            createWhereClause = true;
        }
        if(createWhereClause) {
            criterionIter = criteria.iterator();
            while (criterionIter.hasNext()) {
                SearchCriterion criterion = (SearchCriterion) criterionIter.
                                            next();
                String columnName = getColumnName(criterion.getVariableName());
                String comparator = criterion.getComparator();
                Collection values = criterion.getValues();

/*
                if (comparator.indexOf(SearchCriterion.IGNORE_CASE) >= 0) {
                    comparator = comparator.replaceAll(SearchCriterion.
                        IGNORE_CASE,
                        "");
                    columnName = "UPPER(" + columnName + ")";
                }
*/
                if (comparator.equalsIgnoreCase(SearchCriterion.IS) ||
                    comparator.equalsIgnoreCase(SearchCriterion.IS_NOT)) {
                    sb.append("and " + columnName + " " + comparator + " NULL ");
                }
                else if (values.size() == 0) {
                }
                else if (values.size() == 1) {
                    if(values.iterator().next() != null) {
                        String compareValue = values.iterator().next().toString();
                        sb.append("and " + columnName + " " + comparator + " ");
                        switch (getType(criterion.getVariableName())) {
                            case TYPE_BIGDECIMAL:
                                sb.append(compareValue);
                                break;
                            case TYPE_DATE:
                                if (compareValue.equalsIgnoreCase("sysdate")) {
                                    sb.append(compareValue);
                                }
                                else if (compareValue.startsWith("<<") &&
                                         compareValue.endsWith(">>")) {
                                    sb.append(compareValue.substring(2,
                                        compareValue.length() - 2));
                                }
                                else {
                                    sb.append("TO_DATE('");
                                    sb.append(compareValue);
                                    sb.append("', 'MM/DD/RRRR hh24:mi:ss')");
                                }
                                break;
                            case TYPE_INT:
                                sb.append(compareValue);
                                break;
                            case TYPE_STRING:
                                if (compareValue.startsWith("<<") &&
                                    compareValue.endsWith(">>")) {
                                    sb.append(compareValue.substring(2,
                                        compareValue.length() - 2));
                                }
                                else {
                                    if(comparator.equalsIgnoreCase(SearchCriterion.LIKE)) {
                                      if(compareValue.length() > 0) {
                                        compareValue = "%" + compareValue + "%";
                                      }
                                    }
                                    sb.append(SqlHandler.delimitString(
                                        compareValue));
                                }
                                break;
                        }
                        sb.append(" ");
                    }
                }
                else {
                    Iterator valueIter = values.iterator();
                    StringBuffer sb2 = new StringBuffer();
                    while (valueIter.hasNext()) {
                        String compareValue = (String) valueIter.next();
                        if (compareValue == null) {
                            if (comparator.equals(SearchCriterion.IS_NOT) ||
                                comparator.equals(SearchCriterion.NOT_EQUAL)) {
                                comparator = SearchCriterion.IS_NOT;
                            }
                            else {
                                comparator = SearchCriterion.IS;
                            }
                        }
                        sb2.append("or " + columnName + " " + comparator + " ");
                        if (compareValue == null) {
                            sb2.append("NULL");
                        }
                        else if (compareValue.startsWith("<<") &&
                                 compareValue.endsWith(">>")) {
                            sb.append(compareValue.substring(2,
                                compareValue.length() - 2));
                        }
                        else {
                            switch (getType(criterion.getVariableName())) {
                                case TYPE_BIGDECIMAL:
                                    sb2.append(compareValue);
                                    break;
                                case TYPE_DATE:
                                    if (compareValue.equalsIgnoreCase("sysdate")) {
                                        sb2.append(compareValue);
                                    }
                                    else {
                                        sb2.append("TO_DATE( '");
                                        sb2.append(compareValue);
                                        sb2.append(
                                            "', 'MM/DD/RRRR hh24:mi:ss')");
                                    }
                                    break;
                                case TYPE_INT:
                                    sb2.append(compareValue);
                                    break;
                                case TYPE_STRING:
                                    if(comparator.equalsIgnoreCase(SearchCriterion.LIKE)) {
                                      if(compareValue.length() > 0) {
                                        compareValue = "%" + compareValue + "%";
                                      }
                                    }
                                    sb2.append(SqlHandler.delimitString(
                                        compareValue));
                                    break;
                            }
                        }
                        sb2.append(" ");
                    }
                    sb.append("and ( " + sb2.substring(3) + ") ");
                }
            }
        }
        String whereClause;
        if(sb.length() > 0) {
            whereClause = "where " + sb.substring(4);
        }
        else {
            whereClause = "";
        }
        return whereClause;
    }

/*****************************************************************************
 * Sets the values for the audit fields in any DataBean with the values
 * found in a <code>DataSetRow</code>
 *
 * @param dsr  the <code>DataSetRow</code> object containing the audit data
 * @param dataBean  the DataBean whose audit attributes should be set
 * @throws BaseException  If there is difficulty contacting the database
***************************************************************************/

    public void load(DataSetRow dsr, BaseDataBean dataBean)
        throws BaseException {
        load(dsr, dataBean, true);
    }

/*****************************************************************************
 * Sets the values for the audit fields in any DataBean with the values
 * found in a <code>DataSetRow</code>
 *
 * @param dsr  the <code>DataSetRow</code> object containing the audit data
 * @param dataBean  the DataBean whose audit attributes should be set
 * @param loadAllAttributes  if true, attributes that have no corresponding
 *          value in the <code>DataSetRow</code> will be set to null or 0
 * @throws BaseException  If there is difficulty contacting the database
****************************************************************************/

    public void load(DataSetRow dsr,
                     BaseDataBean dataBean,
                     boolean loadAllAttributes) throws BaseException {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(dataBean.getClass());
            PropertyDescriptor[] pdesc = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < pdesc.length; i++) {
                String propertyName = pdesc[i].getName();
                String columnName = getColumnName(propertyName);
                Method writeMethod = pdesc[i].getWriteMethod();
                if (columnName != null && writeMethod != null &&
                    (loadAllAttributes || dsr.containsKey(columnName))) {
                    Object[] args = new Object[1];
                    switch (getType(propertyName)) {
                        case TYPE_BIGDECIMAL:
                            args[0] = dsr.getBigDecimal(columnName);
                            break;
                        case TYPE_DATE:
                            args[0] = dsr.getDate(columnName);
                            break;
                        case TYPE_INT:
                            args[0] = new Integer(dsr.getInt(columnName));
                            break;
                        case TYPE_STRING:
                            args[0] = dsr.getString(columnName);
                            break;
                    }
                    writeMethod.invoke(dataBean, args);
                }
            }
        }
        catch (Exception e) {
            throw new DataBeanCreationException(e.getMessage());
        }
    }
}
