package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

import radian.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: CustomerMsdsQueueInputBean <br>
 * @version: 1.0, March 19, 2015 <br>
 *****************************************************************************/

public class CustomerMsdsQueueInputBean extends BaseDataBean {

	private String companyId;
	private String searchField;
	private String searchMode;
	private String searchArgument;
    private String status;
    private BigDecimal assignedTo;
    private BigDecimal personnelId;
    private String personnelName;


    //constructor
	public CustomerMsdsQueueInputBean() {
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchMode() {
		return searchMode;
	}

	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	public String getSearchArgument() {
		return searchArgument;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public BigDecimal getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(BigDecimal assignedTo) {
		this.assignedTo = assignedTo;
	}
	
	public boolean isAssignedToUnassigned() {
		return this.assignedTo.intValue() == -1;
	}
	
	public boolean isAssignedToAll() {
		return this.assignedTo.intValue() == -10;
	}

	public BigDecimal getPersonnelId() {
		return personnelId;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public boolean isMyCompanies() {
    	return StringHandler.isBlankString(companyId);
    }
	
	public boolean isGlobalIndexingStatus() {
		return "globalIndexing".equalsIgnoreCase(status);
	}
	
	public boolean isGlobalQcStatus() {
		return "globalQC".equalsIgnoreCase(status);
	}
    
	public boolean isCustomerIndexingStatus() {
		return "customerIndexing".equalsIgnoreCase(status);
	}
	
	public boolean isCustomerQcStatus() {
		return "customerQc".equalsIgnoreCase(status);
	}

	public boolean isMaxcomIndexingStatus() {
		return "maxcomIndexing".equalsIgnoreCase(status);
	}
}