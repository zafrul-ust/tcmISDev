package radian.tcmis.both1.beans;

import java.beans.*;

/*****************************************************************************
 * The base class for DataBeans.  Contains all (if any) attributes for tables,
 * as well as setters and getters for those attributes.  Also provides a
 * <code>toString</code> method that prints out the names and values of all the
 * bean attributes that are exposed through setters and getters (mostly for use
 * in debugging and testing).
 ****************************************************************************/

public abstract class BaseDataBean implements java.io.Serializable {

    public BaseDataBean() {
        super();
    }

    // setters

    // getters


    /*****************************************************************************
     * Returns all the publicly accessible properties of the DataBean in the
     * form:<p>
     *
     * <code>attribute1 = value</code><p>
     *
     * A debug tool to allow you to quickly see the attributes of a <code>
     * DataBean</code>
     ****************************************************************************/
    public String toString() {
        StringBuffer sb = new StringBuffer();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(this.getClass());
            PropertyDescriptor[] pdesc = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < pdesc.length; i++) {
                String propertyName = pdesc[i].getName();
                if (!propertyName.equals("class")) {
                    Object propertyValue =
                        pdesc[i].getReadMethod().invoke(this);
                    sb.append(propertyName + " = " + propertyValue + "\n");
                }
            }
        }
        catch (Exception e) {
            sb.append(super.toString());
        }
        return sb.toString();
    }
}
