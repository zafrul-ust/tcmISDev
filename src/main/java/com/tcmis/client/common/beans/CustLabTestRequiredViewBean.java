package com.tcmis.client.common.beans;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CustLabTestRequiredViewBean <br>
 * @version: 1.0, May 2, 2011 <br>
 *****************************************************************************/

public class CustLabTestRequiredViewBean
    extends BaseDataBean {

    private String companyId;
    private String catalogCompanyId;
    private String catalogId;
    private String catPartNo;
    private BigDecimal partGroupNo;
    private BigDecimal testId;
    private BigDecimal noSuccessTestReqForSkip;
    private String testAllowedSkip;
    private String testType;
    private BigDecimal updatedBy;
    private String updatedByName;
    private Date updatedOn;
    private String shortName;
    private String status;

    private BigDecimal frequency;
    private String frequencyType;
    private String active;
    private String requireCustomerResponse;
    private String frequencyUnit;


    //constructor
  	public CustLabTestRequiredViewBean() {
  	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getNoSuccessTestReqForSkip() {
		return noSuccessTestReqForSkip;
	}

	public void setNoSuccessTestReqForSkip(BigDecimal noSuccessTestReqForSkip) {
		this.noSuccessTestReqForSkip = noSuccessTestReqForSkip;
	}

	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}

	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}

	public String getTestAllowedSkip() {
		return testAllowedSkip;
	}

	public void setTestAllowedSkip(String testAllowedSkip) {
		this.testAllowedSkip = testAllowedSkip;
	}

	public BigDecimal getTestId() {
		return testId;
	}

	public void setTestId(BigDecimal testId) {
		this.testId = testId;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public BigDecimal getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(BigDecimal updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedByName() {
		return updatedByName;
	}

	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

    public BigDecimal getFrequency() {
        return frequency;
    }

    public void setFrequency(BigDecimal frequency) {
        this.frequency = frequency;
    }

    public String getFrequencyType() {
        return frequencyType;
    }

    public void setFrequencyType(String frequencyType) {
        this.frequencyType = frequencyType;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getRequireCustomerResponse() {
        return requireCustomerResponse;
    }

    public void setRequireCustomerResponse(String requireCustomerResponse) {
        this.requireCustomerResponse = requireCustomerResponse;
    }

    public String getFrequencyUnit() {
        return frequencyUnit;
    }

    public void setFrequencyUnit(String frequencyUnit) {
        this.frequencyUnit = frequencyUnit;
    }
}