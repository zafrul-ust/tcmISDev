package com.tcmis.client.report.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.util.Collection;
import java.util.Vector;

/******************************************************************************
 * CLASSNAME: CostReportGroupBean <br>
 * @version: 1.0, Oct 6, 2005 <br>
 *****************************************************************************/


public class CostReportGroupBean extends BaseDataBean {

	private String costReportGroup;
	private Collection facilityList = new Vector();
	private Collection groupAccountSysList = new Vector();

	//constructor
	public CostReportGroupBean() {
	}

	//setters
	public void setCostReportGroup(String costReportGroup) {
		this.costReportGroup = costReportGroup;
	}
	public void setFacilityList(Collection facilityList) {
		this.facilityList = facilityList;
	}
	public void addFacilityBean(CostReportFacilityBean bean) {
		this.facilityList.add(bean);
	}

	public void setGroupAccountSysList(Collection groupAccountSysList) {
		this.groupAccountSysList = groupAccountSysList;
	}

	//getters
	public String getCostReportGroup() {
		return costReportGroup;
	}
	public Collection getFacilityList() {
		return facilityList;
	}

	public Collection getGroupAccountSysList() {
		return groupAccountSysList;
	}
}