package com.tcmis.client.fedex.beans;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Oct 6, 2008
 * Time: 11:18:36 AM
 * To change this template use File | Settings | File Templates.
 */
import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;


/******************************************************************************
 * CLASSNAME: ServiceAvailabilityBean <br>
 * @version: 1.0, Jan 18, 2008 <br>
 *****************************************************************************/


public class ServiceAvailabilityBean extends BaseDataBean {

    private String notification;
    private String message;

    private Collection<ServiceOptionBean> serviceOptionBeanCollection = new Vector();

    //constructor
	public ServiceAvailabilityBean() {
	}

	//setters
	public void setMessage(String s) {
		this.message = s;
	}

	public void setNotification(String s) {
		this.notification = s;
	}

	public void setServiceOptionBeanCollection(Collection<ServiceOptionBean> c) {
		this.serviceOptionBeanCollection = c;
	}

	public void addServiceOptionBean(ServiceOptionBean bean) {
		this.serviceOptionBeanCollection.add(bean);
	}

    //getters
	public String getMessage() {
		return this.message;
	}

	public String getNotification() {
		return this.notification;
	}

    public Collection<ServiceOptionBean> getServiceDetailBeanCollection() {
		return this.serviceOptionBeanCollection;
	}
}
