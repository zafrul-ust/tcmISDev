package com.tcmis.client.common.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CompanyMsdsLogBean <br>
 * @version: 1.0, Aug 28, 2012 <br>
 *****************************************************************************/


public class CompanyMsdsLogBean extends BaseDataBean {

	private String companyId;
	private BigDecimal materialId;
	private Date revisionDate;
	private BigDecimal updatedBy;
	private Date updatedOn;
	private String propertyName;
	private String oldValue;
	private String newValue;
	private String updatedByName;


	//constructor
	public CompanyMsdsLogBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}
	public void setUpdatedBy(BigDecimal updatedBy) {
		this.updatedBy = updatedBy;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public Date getRevisionDate() {
		return revisionDate;
	}
	public BigDecimal getUpdatedBy() {
		return updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public String getOldValue() {
		return oldValue;
	}
	public String getNewValue() {
		return newValue;
	}

	public String getUpdatedByName() {
		return updatedByName;
	}

	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}

}