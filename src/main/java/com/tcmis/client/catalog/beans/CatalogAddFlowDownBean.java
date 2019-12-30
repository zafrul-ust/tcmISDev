package com.tcmis.client.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;
import java.util.Date;

/******************************************************************************
 * CLASSNAME: CatalogAddFlowDownBean <br>
 * @version: 1.0, Jun 23, 2010 <br>
 *****************************************************************************/

public class CatalogAddFlowDownBean extends BaseDataBean {

 	private BigDecimal requestId;
 	private String flowDown;
	private String catalogId;
	private String companyId;
	private String dataSource;
	private String calledFrom;
	private String flowDownDesc;
	private String flowDownType;
	private String content;
	private Date revisionDate;
	private String currentVersion;
    private String displayStatus;


     //constructor
	public CatalogAddFlowDownBean() {
 	}

 	//setters
	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}

	public void setFlowDown(String flowDown) {
		this.flowDown = flowDown;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public void setCalledFrom(String calledFrom) {
		this.calledFrom = calledFrom;
	}

	public void setFlowDownDesc(String flowDownDesc) {
		this.flowDownDesc = flowDownDesc;
	}

	public void setFlowDownType(String flowDownType) {
		this.flowDownType = flowDownType;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}

	//getters
	public BigDecimal getRequestId() {
		return requestId;
	}

	public String getFlowDown() {
		return flowDown;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getDataSource() {
		return dataSource;
	}

	public String getCalledFrom() {
		return calledFrom;
	}

	public String getFlowDownDesc() {
		return flowDownDesc;
	}

	public String getFlowDownType() {
		return flowDownType;
	}

	public String getContent() {
		return content;
	}

	public Date getRevisionDate() {
		return revisionDate;
	}

	public String getCurrentVersion() {
		return currentVersion;
	}

    public String getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(String displayStatus) {
        this.displayStatus = displayStatus;
    }
}