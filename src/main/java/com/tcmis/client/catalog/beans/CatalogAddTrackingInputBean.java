package com.tcmis.client.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CatalogAddTrackingInputBean <br>
 * @version: 1.0, Jun 23, 2009 <br>
 *****************************************************************************/


public class CatalogAddTrackingInputBean extends BaseDataBean {

	private static final long serialVersionUID = 8820760262204637098L;
	
	private String[] requestStatusChkbxArray;
	private BigDecimal requestor;
	private BigDecimal approver;
	private String applicationId;
	private String facilityId;
	private String searchField;
    private String searchMode;
    private String searchArgument;
    private BigDecimal requestId;
    private String action;
	private String requestNeedingMyApproval;
    private String companyId;
    private String pendingApprovalRole;
    private String dateType;
    private Date beginDateJsp;
    private Date endDateJsp;


    /**
	 * @return the requestStatusChkbxArray
	 */
	public String[] getRequestStatusChkbxArray() {
		return requestStatusChkbxArray;
	}
	/**
	 * @param requestStatusChkbxArray the requestStatusChkbxArray to set
	 */
	public void setRequestStatusChkbxArray(String[] requestStatusChkbxArray) {
		this.requestStatusChkbxArray = requestStatusChkbxArray;
	}
	/**
	 * @return the requestor
	 */
	public BigDecimal getRequestor() {
		return requestor;
	}
	/**
	 * @param requestor the requestor to set
	 */
	public void setRequestor(BigDecimal requestor) {
		this.requestor = requestor;
	}
	/**
	 * @return the approver
	 */
	public BigDecimal getApprover() {
		return approver;
	}
	/**
	 * @param approver the approver to set
	 */
	public void setApprover(BigDecimal approver) {
		this.approver = approver;
	}
	/**
	 * @return the application
	 */
	public String getApplicationId() {
		return applicationId;
	}
	/**
	 * @param applicationId the application to set
	 */
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	/**
	 * @return the facilityId
	 */
	public String getFacilityId() {
		return facilityId;
	}
	/**
	 * @param facilityId the facilityId to set
	 */
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	/**
	 * @return the searchField
	 */
	public String getSearchField() {
		return searchField;
	}
	/**
	 * @param searchField the searchField to set
	 */
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	/**
	 * @return the searchMode
	 */
	public String getSearchMode() {
		return searchMode;
	}
	/**
	 * @param searchMode the searchMode to set
	 */
	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}
	/**
	 * @return the searchArgument
	 */
	public String getSearchArgument() {
		return searchArgument;
	}
	/**
	 * @param searchArgument the searchArgument to set
	 */
	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}
	/**
	 * @return the requestId
	 */
	public BigDecimal getRequestId() {
		return requestId;
	}
	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	public void setRequestNeedingMyApproval(String requestNeedingMyApproval) {
		this.requestNeedingMyApproval = requestNeedingMyApproval;
	}

	public String getRequestNeedingMyApproval() {
		return requestNeedingMyApproval;
	}

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getPendingApprovalRole() {
        return pendingApprovalRole;
    }

    public void setPendingApprovalRole(String pendingApprovalRole) {
        this.pendingApprovalRole = pendingApprovalRole;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public Date getBeginDateJsp() {
        return beginDateJsp;
    }

    public void setBeginDateJsp(Date beginDateJsp) {
        this.beginDateJsp = beginDateJsp;
    }

    public Date getEndDateJsp() {
        return endDateJsp;
    }

    public void setEndDateJsp(Date endDateJsp) {
        this.endDateJsp = endDateJsp;
    }
}