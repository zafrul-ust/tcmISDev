package com.tcmis.client.het.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: VvHetApplicationMethodBean <br>
 * @version: 1.0, Jun 1, 2011 <br>
 *****************************************************************************/


public class VvHetApplicationMethodBean extends BaseDataBean {

	private String companyId;
	private boolean forSolvent = false;
	private String methodCode;
	private String method;
	private BigDecimal methodId;


	public BigDecimal getMethodId() {
		return methodId;
	}

	public void setMethodId(BigDecimal methodId) {
		this.methodId = methodId;
	}

	//constructor
	public VvHetApplicationMethodBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setMethodCode(String methodCode) {
		this.methodCode = methodCode;
	}
	public void setMethod(String method) {
		this.method = method;
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public String getMethodCode() {
		return methodCode;
	}
	public String getMethod() {
		return method;
	}

	public boolean isForSolvent() {
		return forSolvent;
	}

	public void setForSolvent(boolean forSolvent) {
		this.forSolvent = forSolvent;
	}
}