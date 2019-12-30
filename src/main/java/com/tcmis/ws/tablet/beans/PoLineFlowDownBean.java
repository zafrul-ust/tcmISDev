package com.tcmis.ws.tablet.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class PoLineFlowDownBean extends BaseDataBean{

	private String flowDown;
	private String catalogId;
	private String companyId;
	private String flowDownDesc;
	private String flowDownType;
	private String content;
	private Date revisionDate;
	private String currentVersion;
	private BigDecimal radianPo;
	private BigDecimal poLine;

	//constructor
	public PoLineFlowDownBean() {
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
	public String getFlowDown() {
		return flowDown;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getCompanyId() {
		return companyId;
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

	public BigDecimal getRadianPo() {
		return radianPo;
	}

	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}

	public BigDecimal getPoLine() {
		return poLine;
	}

	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	
}
