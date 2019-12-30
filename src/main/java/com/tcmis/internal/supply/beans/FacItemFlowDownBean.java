package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: FacItemFlowDownBean <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class FacItemFlowDownBean extends BaseDataBean {

	private String catalogId;
	private String facPartNo;
	private String flowDown;
	private String companyId;


	//constructor
	public FacItemFlowDownBean() {
	}

	//setters
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}
	public void setFlowDown(String flowDown) {
		this.flowDown = flowDown;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	//getters
	public String getCatalogId() {
		return catalogId;
	}
	public String getFacPartNo() {
		return facPartNo;
	}
	public String getFlowDown() {
		return flowDown;
	}
	public String getCompanyId() {
		return companyId;
	}
}