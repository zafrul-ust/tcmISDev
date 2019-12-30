package com.tcmis.client.report.beans;

import java.util.Collection;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CostReportAccountSysBean <br>
 * @version: 1.0, Oct 6, 2005 <br>
 *****************************************************************************/


public class CostReportAccountSysBean extends BaseDataBean {

	private String accountSysId;
	private Collection chargeTypeList = new Vector();


	//constructor
	public CostReportAccountSysBean() {
	}

	//setters
	public void setAccountSysId(String accountSysId) {
		this.accountSysId = accountSysId;
	}
	public void setChargeTypeList(Collection chargeTypeList) {
		this.chargeTypeList = chargeTypeList;
	}
	public void addChargeTypeBean(CostReportChargeTypeBean bean) {
		this.chargeTypeList.add(bean);
	}


	//getters
	public String getAccountSysId() {
		return accountSysId;
	}
	public Collection getChargeTypeList() {
		return chargeTypeList;
	}
}