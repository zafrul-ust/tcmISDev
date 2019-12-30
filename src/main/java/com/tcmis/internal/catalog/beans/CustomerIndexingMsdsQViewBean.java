package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CustomerIndexingMsdsQViewBean <br>
 * @version: 1.0, March 19, 2015 <br>
 *****************************************************************************/

public class CustomerIndexingMsdsQViewBean extends BaseDataBean {

	private String companyId;
	private String companyName;
	private String comments;
	private Date currentCustRevisionDate;
	private String customerDec;
	private String globalDec;
	private String materialDesc;
	private BigDecimal materialId;
	private BigDecimal mfgId;
	private String mfgDesc;
	private Date revisionDate;
	private String tradeName;
    private String submittedBy;
    private BigDecimal assignedTo;
    private boolean assigneeUpdated;

    //constructor
	public CustomerIndexingMsdsQViewBean() {
	}

	public String getCompanyId() {
		return companyId;
	}


	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}


	public Date getCurrentCustRevisionDate() {
		return currentCustRevisionDate;
	}


	public void setCurrentCustRevisionDate(Date currentCustRevisionDate) {
		this.currentCustRevisionDate = currentCustRevisionDate;
	}


	public String getCustomerDec() {
		return customerDec;
	}


	public void setCustomerDec(String customerDec) {
		this.customerDec = customerDec;
	}


	public String getGlobalDec() {
		return globalDec;
	}


	public void setGlobalDec(String globalDec) {
		this.globalDec = globalDec;
	}


	public String getMaterialDesc() {
		return materialDesc;
	}


	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}


	public BigDecimal getMaterialId() {
		return materialId;
	}


	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}


	public String getMfgDesc() {
		return mfgDesc;
	}


	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}


	public Date getRevisionDate() {
		return revisionDate;
	}


	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}


	public String getTradeName() {
		return tradeName;
	}


	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public BigDecimal getMfgId() {
		return mfgId;
	}

	public void setMfgId(BigDecimal mfgId) {
		this.mfgId = mfgId;
	}

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

	public BigDecimal getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(BigDecimal assignedTo) {
		this.assignedTo = assignedTo;
	}

	public boolean isAssigneeUpdated() {
		return assigneeUpdated;
	}

	public void setAssigneeUpdated(boolean assigneeUpdated) {
		this.assigneeUpdated = assigneeUpdated;
	}
}