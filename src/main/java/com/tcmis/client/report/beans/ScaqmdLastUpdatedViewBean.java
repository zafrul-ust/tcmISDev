package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ScaqmdLastUpdatedViewBean <br>
 * @version: 1.0, Feb 9, 2006 <br>
 *****************************************************************************/


public class ScaqmdLastUpdatedViewBean extends BaseDataBean {

	private Date lastUpdated;


	//constructor
	public ScaqmdLastUpdatedViewBean() {
	}

	//setters
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}


	//getters
	public Date getLastUpdated() {
		return lastUpdated;
	}
}