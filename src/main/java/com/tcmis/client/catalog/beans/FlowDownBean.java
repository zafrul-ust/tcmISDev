package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class FlowDownBean extends BaseDataBean{

	private BigDecimal requestId;
 	private String flowDown;
	private String catalogId;
	private String companyId;
	private String dataSource;
	private String calledFrom;
	private String flowDownDesc;
	private String flowDownType;
	private String content;
	private String revisionDate;
	private String currentVersion;
    private String displayStatus;
    private BigDecimal color;
    private String ok;
    private String onLine;
    private String changed;


     //constructor
	public FlowDownBean() {
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

	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}
	public void setColor(BigDecimal color) {
		this.color = color;
	}
	
	public void setOk(String ok) {
		this.ok = ok;
	}

	public void setOnLine(String onLine) {
		this.onLine = onLine;
	}
	
	//getters
	public String getOnLine() {
		return onLine;
	}
	
	public String getOk() {
		return ok;
	}

	public BigDecimal getColor() {
		return color;
	}
	
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

	public String getRevisionDate() {
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

	public String getChanged() {
		return changed;
	}

	public void setChanged(String changed) {
		this.changed = changed;
	}
	
}
