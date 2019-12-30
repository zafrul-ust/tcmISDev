package com.tcmis.client.lmco.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: VocetChemicalSearchViewBean <br>
 * @version: 1.0, Jul 17, 2012 <br>
 *****************************************************************************/


public class VocetChemicalSearchViewBean extends BaseDataBean {

	private String companyId;
	private String facilityId;
	private BigDecimal materialId;
	private BigDecimal mixtureId;
	private String customerMsdsNumber;
	private String customerMsdsDb;
	private String casNumber;
	private String vocetSourceId;
	private String vocetCategoryId;
	private BigDecimal shortTermEsl;
	private BigDecimal longTermEsl;
    private String preferredName;
    private String searchField;
    private String searchText;
    private String matchType;
    private BigDecimal maxData;
    
    private String updatedByName;
    private Date updatedOn;
    private BigDecimal updatedBy;

    private String customerMsdsOrMixtureNo;
	private String changed;
	
	private String materialIdString;
	
	private String entryType;
	private BigDecimal uploadSeqId;
	private Date entryStartDate;
	private Date entryEndDate;


	public String getChanged() {
		return changed;
	}

	public void setChanged(String changed) {
		this.changed = changed;
	}

	public String getCustomerMsdsOrMixtureNo() {
		return customerMsdsOrMixtureNo;
	}

	public void setCustomerMsdsOrMixtureNo(String customerMsdsOrMixtureNo) {
		this.customerMsdsOrMixtureNo = customerMsdsOrMixtureNo;
	}

	public String getMaterialIdString() {
		return materialIdString;
	}

	public void setMaterialIdString(String materialIdString) {
		this.materialIdString = materialIdString;
	}

	//constructor
	public VocetChemicalSearchViewBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public void setMixtureId(BigDecimal mixtureId) {
		this.mixtureId = mixtureId;
	}
	public void setCustomerMsdsNumber(String customerMsdsNumber) {
		this.customerMsdsNumber = customerMsdsNumber;
	}
	public void setCustomerMsdsDb(String customerMsdsDb) {
		this.customerMsdsDb = customerMsdsDb;
	}
	public void setCasNumber(String casNumber) {
		this.casNumber = casNumber;
	}

	public void setShortTermEsl(BigDecimal shortTermEsl) {
		this.shortTermEsl = shortTermEsl;
	}
	public void setLongTermEsl(BigDecimal longTermEsl) {
		this.longTermEsl = longTermEsl;
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public BigDecimal getMixtureId() {
		return mixtureId;
	}
	public String getCustomerMsdsNumber() {
		return customerMsdsNumber;
	}
	public String getCustomerMsdsDb() {
		return customerMsdsDb;
	}
	public String getCasNumber() {
		return casNumber;
	}

	public BigDecimal getShortTermEsl() {
		return shortTermEsl;
	}
	public BigDecimal getLongTermEsl() {
		return longTermEsl;
	}

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String getVocetSourceId() {
        return vocetSourceId;
    }

    public void setVocetSourceId(String vocetSourceId) {
        this.vocetSourceId = vocetSourceId;
    }

    public String getVocetCategoryId() {
        return vocetCategoryId;
    }

    public void setVocetCategoryId(String vocetCategoryId) {
        this.vocetCategoryId = vocetCategoryId;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public BigDecimal getMaxData() {
        return maxData;
    }

    public void setMaxData(BigDecimal maxData) {
        this.maxData = maxData;
    }

	public String getUpdatedByName() {
		return updatedByName;
	}

	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
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

	public Date getEntryEndDate() {
		return entryEndDate;
	}

	public void setEntryEndDate(Date entryEndDate) {
		this.entryEndDate = entryEndDate;
	}

	public Date getEntryStartDate() {
		return entryStartDate;
	}

	public void setEntryStartDate(Date entryStartDate) {
		this.entryStartDate = entryStartDate;
	}

	public String getEntryType() {
		return entryType;
	}

	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}

	public BigDecimal getUploadSeqId() {
		return uploadSeqId;
	}

	public void setUploadSeqId(BigDecimal uploadSeqId) {
		this.uploadSeqId = uploadSeqId;
	}
}