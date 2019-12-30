package com.tcmis.internal.hub.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.util.Date;
import java.math.BigDecimal;

/******************************************************************************
 * CLASSNAME: HubPrefBean <br>
 * @version: 1.0, Jun 9, 2004 <br>
 *****************************************************************************/

public class PassThroughReportInputBean
	extends BaseDataBean {

  private String hub;
  private String inventoryGroup;
  private String invoiceDate;
  private String unitsOrDollars;
  private String facilityId;
  private String submitSearch;
  private String buttonCreateExcel;
//  private String dateDelivered;
  private Date dateDelivered; 
	private String companyId;
	private String sort;
  private String billingMethod;

  //constructor
  public PassThroughReportInputBean() {
  }

  //setters
  public void setHub(String hub) {
	this.hub = hub;
  }

  public void setInventoryGroup(String inventoryGroup) {
	this.inventoryGroup = inventoryGroup;
  }

  public void setinvoiceDate(String invoiceDate) {
	this.invoiceDate = invoiceDate;
  }

  public void setUnitsOrDollars(String unitsOrDollars) {
	this.unitsOrDollars = unitsOrDollars;
  }

  public void setFacilityId(String facilityId) {
	this.facilityId = facilityId;
  }

  public void setSubmitSearch(String submitSearch) {
	this.submitSearch = submitSearch;
  }

  public void setButtonCreateExcel(String buttonCreateExcel) {
	this.buttonCreateExcel = buttonCreateExcel;
  }

//	public void setDateDelivered(String dateDelivered) {
  public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}

  public void setCompanyId(String companyId) {
	this.companyId = companyId;
	}

  public void setSort(String sort) {
	 this.sort = sort;
	}
   public void setBillingMethod(String billingMethod) {
	 this.billingMethod = billingMethod;
	}

  //getters
  public String getHub() {
	return hub;
  }

  public String getInventoryGroup() {
	return inventoryGroup;
  }

  public String getInvoiceDate() {
	return invoiceDate;
  }

  public String getUnitsOrDollars() {
	return unitsOrDollars;
  }

  public String getFacilityId() {
	return facilityId;
  }

  public String getSubmitSearch() {
	return submitSearch;
  }

  public String getButtonCreateExcel() {
	return buttonCreateExcel;
  }
  
  
  public Date getDateDelivered() {
		return dateDelivered;
  }
/*	public String getDateDelivered() {
			return dateDelivered;
	}  */

  public String getCompanyId() {
	return companyId;
	}

  public String getSort() {
	 return sort;
	}
	public String getBillingMethod() {
	 return billingMethod;
	}

}
