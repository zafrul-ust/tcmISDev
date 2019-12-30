package com.tcmis.client.report.beans;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: FacAppReportViewBean <br>
 * @version: 1.0, Mar 22, 2006 <br>
 *****************************************************************************/

public class MovementOfMaterialInputBean
    extends BaseDataBean {

  private String facilityId;
  private String fromApplication;
  private String fromApplicationDesc;
  private String toApplication;
  private BigDecimal qty;
  private BigDecimal prNumber;
  private String lineItem;
  private String accountSysId;
  private BigDecimal fromPrNumber;
  private String fromLineItem;
  
  private String uAction;

//constructor
  	public MovementOfMaterialInputBean() {
  	}
  
  	public String getFacilityId() {
  		return facilityId;
	}
	
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	
	public String getFromApplication() {
		return fromApplication;
	}
	
	public void setFromApplication(String fromApplication) {
		this.fromApplication = fromApplication;
	}
	
	public String getFromApplicationDesc() {
		return fromApplicationDesc;
	}
	
	public void setFromApplicationDesc(String fromApplicationDesc) {
		this.fromApplicationDesc = fromApplicationDesc;
	}
	
	public BigDecimal getQty() {
		return qty;
	}
	
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
	
	public String getToApplication() {
		return toApplication;
	}
	
	public void setToApplication(String toApplication) {
		this.toApplication = toApplication;
	}
	
	public String getuAction() {
		return uAction;
	}

	public void setuAction(String uAction) {
		this.uAction = uAction;
	}

	public String getLineItem() {
		return lineItem;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public String getAccountSysId() {
		return accountSysId;
	}

	public void setAccountSysId(String accountSysId) {
		this.accountSysId = accountSysId;
	}

	public String getFromLineItem() {
		return fromLineItem;
	}

	public void setFromLineItem(String fromLineItem) {
		this.fromLineItem = fromLineItem;
	}

	public BigDecimal getFromPrNumber() {
		return fromPrNumber;
	}

	public void setFromPrNumber(BigDecimal fromPrNumber) {
		this.fromPrNumber = fromPrNumber;
	}


}