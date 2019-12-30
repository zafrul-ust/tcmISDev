package com.tcmis.client.report.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.util.Date;
import java.math.BigDecimal;


/******************************************************************************
 * CLASSNAME: BatchReportViewerInputBean <br>
 * @version: 1.0, Aug 5, 2005 <br>
 *****************************************************************************/


public class BatchReportViewerInputBean extends BaseDataBean {

	private String searchText;
	private String modified;
	private String reportDate;
	private String reportName;
	private BigDecimal personnelId;


	//constructor
	public BatchReportViewerInputBean() {
	}

	//setters
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	//getters
	public String getSearchText() {
		return searchText;
	}

	public String getModified() {
		return modified;
	}

	public String getReportDate() {
		return reportDate;
	}

	public String getReportName() {
		return reportName;
	}

	public BigDecimal getPersonnelId() {
		return personnelId;
	}
}