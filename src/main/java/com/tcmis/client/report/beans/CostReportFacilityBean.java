package com.tcmis.client.report.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.util.Collection;
import java.util.Vector;

import com.tcmis.client.catalog.beans.FacLocAppBean;


/******************************************************************************
 * CLASSNAME: CostReportFacilityBean <br>
 * @version: 1.0, Oct 6, 2005 <br>
 *****************************************************************************/


public class CostReportFacilityBean extends BaseDataBean {

	private String facilityId;
	private String facilityName;
	private Collection applicationList = new Vector();
	private Collection accountSysList = new Vector();


	//constructor
	public CostReportFacilityBean() {
	}

	//setters
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public void setApplicationList(Collection applicationList) {
		this.applicationList = applicationList;
	}
	public void setAccountSysList(Collection accountSysList) {
		this.accountSysList = accountSysList;
	}
	public void addFacLocAppBean (FacLocAppBean bean) {
		this.applicationList.add(bean);
	}
	public void addAccountSysBean(CostReportAccountSysBean bean) {
		this.accountSysList.add(bean);
	}

	//getters
	public String getFacilityId() {
		return facilityId;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public Collection getApplicationList() {
		return applicationList;
	}
	public Collection getAccountSysList() {
		return accountSysList;
	}
}