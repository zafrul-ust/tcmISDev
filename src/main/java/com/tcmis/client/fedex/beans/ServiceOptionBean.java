package com.tcmis.client.fedex.beans;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Oct 6, 2008
 * Time: 11:20:12 AM
 * To change this template use File | Settings | File Templates.
 */
import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;
import java.util.Date;


/******************************************************************************
 * CLASSNAME: ServiceOptionBean <br>
 * @version: 1.0, Jan 18, 2008 <br>
 *****************************************************************************/


public class ServiceOptionBean extends BaseDataBean {

	private String service;
    private Date deliveryDate;
    private String deliveryDateString;
    private String deliveryDay;
    private String destinationStationId;


    //constructor
	public ServiceOptionBean() {
	}

	//setters
	public void setService(String s) {
		this.service = s;
	}

	public void setDeliveryDateString(String s) {
		this.deliveryDateString = s;
	}

	public void setDeliveryDay(String s) {
		this.deliveryDay = s;
	}

	public void setDestinationStationId(String s) {
		this.destinationStationId = s;
	}

	public void setDeliveryDate(Date d) {
		this.deliveryDate = d;
	}

    //getters
	public String getService() {
		return this.service;
	}

	public String getDeliveryDateString() {
		return this.deliveryDateString;
	}

	public String getDeliveryDay() {
		return this.deliveryDay;
	}

	public String getDestinationStationId() {
		return this.destinationStationId;
	}

    public Date getDeliveryDate() {
        return this.deliveryDate;
    }

}
