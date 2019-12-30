package com.tcmis.client.het.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: VvHetSubstrateBean <br>
 * @version: 1.0, Aug 11, 2011 <br>
 *****************************************************************************/


public class VvHetSubstrateBean extends BaseDataBean {

	private String companyId;
	private String substrateCode;
	private String substrate;
	private BigDecimal substrateId;


	//constructor
	public VvHetSubstrateBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setSubstrateCode(String substrateCode) {
		this.substrateCode = substrateCode;
	}
	public void setSubstrate(String substrate) {
		this.substrate = substrate;
	}
	public void setSubstrateId(BigDecimal substrateId) {
		this.substrateId = substrateId;
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public String getSubstrateCode() {
		return substrateCode;
	}
	public String getSubstrate() {
		return substrate;
	}
	public BigDecimal getSubstrateId() {
		return substrateId;
	}

}