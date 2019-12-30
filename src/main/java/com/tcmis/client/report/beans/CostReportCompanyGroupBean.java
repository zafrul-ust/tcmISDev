package com.tcmis.client.report.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.util.Collection;
import java.util.Vector;

/******************************************************************************
 * CLASSNAME: CostReportCompanyGroupBean <br>
 * @version: 1.0, Oct 6, 2005 <br>
 *****************************************************************************/


public class CostReportCompanyGroupBean extends BaseDataBean {

	private String companyId;
	private String companyName;
	private Collection costReportGroupList = new Vector();
	private Collection companyAccountSysList = new Vector();


	//constructor
	public CostReportCompanyGroupBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setCostReportGroupList (Collection costReportGroupList) {
		this.costReportGroupList = costReportGroupList;
	}
	public void addCostReportGroup(CostReportGroupBean bean) {
		this.costReportGroupList.add(bean);
	}

	public void setCompanyAccountSysList(Collection companyAccountSysList) {
		this.companyAccountSysList = companyAccountSysList;
	}

	//getters
	public String getCompanyId() {
		return companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public Collection getCostReportGroupList() {
		return costReportGroupList;
	}

	public Collection getCompanyAccountSysList() {
		return companyAccountSysList;
	}
}