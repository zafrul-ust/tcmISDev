package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class CatalogPart extends BaseDataBean {

	private String		catalogId;
	private String		catalogItemId;
	private String		catPartNo;
	private String		comments;
	private String		companyId;
	private BigDecimal	cpigId;
	private Date		dateInserted;
	private BigDecimal	itemId;
	private Date		lastUpdated;
	private BigDecimal	partGroupNo;
	private BigDecimal	priority;
	private String		status;

	public CatalogPart() {

	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getCatalogItemId() {
		return this.catalogItemId;
	}

	public String getCatPartNo() {
		return this.catPartNo;
	}

	public String getComments() {
		return this.comments;
	}

	public String getCompanyId() {
		return companyId;
	}

	public BigDecimal getCpigId() {
		return this.cpigId;
	}

	public Date getDateInserted() {
		return this.dateInserted;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public BigDecimal getPartGroupNo() {
		return this.partGroupNo;
	}

	public BigDecimal getPriority() {
		return this.priority;
	}

	public String getStatus() {
		return this.status;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCatalogItemId(String catalogItemId) {
		this.catalogItemId = catalogItemId;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCpigId(BigDecimal cpigId) {
		this.cpigId = cpigId;
	}

	public void setDateInserted(Date dateInserted) {
		this.dateInserted = dateInserted;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}

	public void setPriority(BigDecimal priority) {
		this.priority = priority;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
