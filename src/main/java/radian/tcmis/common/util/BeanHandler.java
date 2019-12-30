package radian.tcmis.common.util;

import java.beans.*;
import java.lang.reflect.*;
import java.math.*;
import java.text.*;
import java.util.*;
import org.apache.commons.beanutils.*;
import radian.tcmis.common.exceptions.*;

/*****************************************************************************
 * A helper class for doing things with beans.
 *
 ****************************************************************************/
public class BeanHandler {
/*****************************************************************************
 * The default date mask to use when parsing date strings or formatting dates
 * into strings.<p>
 * Currently, is set to "MM/dd/yyyy".
****************************************************************************/
    public static String DEFAULT_DATE_MASK = "MM/dd/yyyy";
/*****************************************************************************
 * Copies all same named attributes from one bean to another bean, doing data
 * type conversion as needed.<p>
 *
 * This class is meant primarily to deal with copying from FormBeans or
 * DynaBeans to beans extending <code>BaseDataBean</code>, and vice versa.
 * Thus, it currently only supports four data types in the target bean:
 * BigDecimal, Date, int, and String.<p>
 *
 * If a conversion error occurs, the method will continue copying all the
 * attributes it can, but will throw a <code>BeanCopyException</code> at the
 * end of the process.  This exception will contain other <code>
 * BeanCopyException</code> objects, one for each attribute which had a
 * conversion error.<p>
 *
 * This version uses the default date mask ("MM/dd/yyyy").
 *
 * @param fromBean  the bean to copy attributes from
 * @param toBean  the bean to copy attributes to
 * @throws BeanCopyException  If one of the toBean's attributes cannot be
 *      parsed into the data type of the fromBean attribute with the same
 *      name
****************************************************************************/
    public static synchronized void copyAttributes(Object fromBean,
                                                   Object toBean)
        throws BeanCopyException {
        BeanHandler.copyAttributes(fromBean, toBean, DEFAULT_DATE_MASK);
    }

    /*****************************************************************************
     * Copies all same named attributes from one bean to another bean, doing data
     * type conversion as needed.<p>
     * This class is meant primarily to deal with copying from FormBeans or
     * DynaBeans to beans extending <code>BaseDataBean</code>, and vice versa.
     * Thus, it currently only supports four data types in the target bean:
     * BigDecimal, Date, int, and String.<p>
     * If a conversion error occurs, the method will continue copying all the
     * attributes it can, but will throw a <code>BeanCopyException</code> at the
     * end of the process.  This exception will contain other <code>
     * BeanCopyException</code> objects, one for each attribute which had a
     * conversion error.<p>
     *
     * This version allows you to specify a date mask (see <code>
     * java.text.SimpleDateFormat</code> for instructions on constructing a
     * date mask ).
     *
     * @param fromBean  the bean to copy attributes from
     * @param toBean  the bean to copy attributes to
     * @param dateMask  the date mask to use when parsing a <code>String</code>
     *      into a <code>Date</code>
     * @throws BeanCopyException  If one of the toBean's attributes cannot be
     *      parsed into the data type of the fromBean attribute with the same
     *      name
     ****************************************************************************/
    public static synchronized void copyAttributes(Object fromBean,
                                                   Object toBean,
                                                   String dateMask)
        throws BeanCopyException {
        SimpleDateFormat dateParser = new SimpleDateFormat(dateMask);
        ArrayList badAttributes = new ArrayList();
        boolean isFromBeanDyna = fromBean instanceof DynaBean;
        boolean isToBeanDyna = toBean instanceof DynaBean;
        try {
            HashMap writeMethods = new HashMap();
            if (isToBeanDyna) {
                DynaProperty[] dynaProps =
                    ((DynaBean) toBean).getDynaClass().getDynaProperties();
                for (int i = 0; i < dynaProps.length; i++) {
                    writeMethods.put(dynaProps[i].getName(),
                                     toBean.getClass().getMethod("set",
                        new Class[] {String.class, Object.class}));
                }
            }
            else {
                BeanInfo beanInfo = Introspector.getBeanInfo(toBean.getClass());
                PropertyDescriptor[] pDescs = beanInfo.getPropertyDescriptors();
                for (int i = 0; i < pDescs.length; i++) {
                    PropertyDescriptor pDesc = pDescs[i];
                    Method writeMethod = pDesc.getWriteMethod();
                    if (writeMethod != null) {
                        writeMethods.put(pDesc.getName(), writeMethod);
                    }
                }
            }
            if (isFromBeanDyna) {
                DynaProperty[] dynaProps =
                    ((DynaBean) fromBean).getDynaClass().getDynaProperties();
                for (int i = 0; i < dynaProps.length; i++) {
                    DynaProperty dProp = dynaProps[i];
                    Method writeMethod = (Method) writeMethods.get(
                        dProp.getName());
                    if (writeMethod != null) {
                        Object readValue = ((DynaBean) fromBean).get(
                            dProp.getName());
                        Class fromClass = dProp.getType();
                        Class toClass;
                        if (isToBeanDyna) {
                            toClass = ((DynaBean) toBean).getDynaClass().
                                      getDynaProperty(dProp.getName()).getType();
                        }
                        else {
                            toClass = (writeMethod.getParameterTypes())[0];
                        }
                        try {
                            Object writeValue = convertValue(readValue,
                                fromClass, toClass, dateParser);
                            if (isToBeanDyna) {
                                ((DynaBean) toBean).set(dProp.getName(),
                                    writeValue);
                            }
                            else {
                                writeMethod.invoke(toBean,
                                    new Object[] {writeValue});
                            }
                        }
                        catch (BeanCopyException e) {
                            badAttributes.add(dProp.getName() + ": " +
                                              e.getMessage());
                        }
                    }
                }
            }
            else {
                BeanInfo beanInfo = Introspector.getBeanInfo(fromBean.getClass());
                PropertyDescriptor[] pDescs = beanInfo.getPropertyDescriptors();
                for (int i = 0; i < pDescs.length; i++) {
                    PropertyDescriptor pDesc = pDescs[i];
                    Method readMethod = pDesc.getReadMethod();
                    if (readMethod != null) {
                        Method writeMethod = (Method) writeMethods.get(
                            pDesc.getName());
                        if (writeMethod != null) {
                            Object readValue =
                                readMethod.invoke(fromBean);
                            Class fromClass = readMethod.getReturnType();
                            Class toClass;
                            if (isToBeanDyna) {
                                toClass = ((DynaBean) toBean).getDynaClass().
                                          getDynaProperty(pDesc.getName()).
                                          getType();
                            }
                            else {
                                toClass = (writeMethod.getParameterTypes())[0];
                            }
                            try {
                                Object writeValue = convertValue(readValue,
                                    fromClass, toClass, dateParser);
                                if (isToBeanDyna) {
                                    ((DynaBean) toBean).set(pDesc.getName(),
                                        writeValue);
                                }
                                else {
                                    writeMethod.invoke(toBean,
                                        new Object[] {writeValue});
                                }
                            }
                            catch (BeanCopyException e) {
                                badAttributes.add(pDesc.getName() + ": " +
                                                  e.getMessageKey());
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            BeanCopyException bce = new BeanCopyException("General error " +
                "copying bean");
            bce.setRootCause(e);
            throw bce;
        }
        if (badAttributes.size() > 0) {
            BeanCopyException bcException = new BeanCopyException(
                "Error converting attributes while copying bean");
            Iterator badAttrIter = badAttributes.iterator();
            while (badAttrIter.hasNext()) {
                bcException.addException(new BeanCopyException(
                    (String) badAttrIter.next()));
            }
            throw bcException;
        }
    }

    private static Object convertValue(Object value, Class fromClass,
                                       Class toClass,
                                       SimpleDateFormat dateParser)
        throws BeanCopyException {
        Object returnValue = null;
        if (toClass.getName().equals("int")) {
            if (value == null || ("" + value).length() == 0) {
                value = "0";
            }
            try {
                returnValue = new Integer("" + value);
            }
            catch (NumberFormatException e) {
                throw new BeanCopyException(" error converting " + value +
                                            " to int");
            }
        }
        else if (toClass.equals(fromClass)) {
            returnValue = value;
        }
        else if (toClass.equals(BigDecimal.class)) {
            if (value == null || ("" + value).length() == 0) {
                value = "0";
            }
            try {
                returnValue = new BigDecimal("" + value);
            }
            catch (NumberFormatException e) {
                throw new BeanCopyException("error converting " + value +
                                            " to BigDecimal");
            }
        }
        else if (toClass.equals(Date.class)) {
            if (value == null || ("" + value).length() == 0) {
                returnValue = null;
            }
            else {
                try {
                    returnValue = dateParser.parse("" + value);
                }
                catch (ParseException e) {
                    throw new BeanCopyException("error converting " + value +
                                                " to Date");
                }
                if(!DateHandler.isValidDate((String)value)) {
                  throw new BeanCopyException("error converting " + value +
                                                " to Date");
                }
            }
        }
        else if (toClass.equals(String.class)) {
            if (value == null) {
                returnValue = null;
            }
            else if (fromClass.equals(Date.class)) {
                returnValue = dateParser.format((Date) value);
            }
            else {
                returnValue = "" + value;
            }
        }
        return returnValue;
    }
}
