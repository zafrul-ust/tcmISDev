package com.tcmis.client.common.beans;

import java.math.BigDecimal;
import java.util.Date;

public class MsdsIndexingQcDetailBean {
	
	private BigDecimal materialId;
	private Date revisionDate;
	private String companyId;
	private String columnName;
	private BigDecimal qcErrorTypeId;
	private String qcErrorType;
	private Date reviewDate;
	private BigDecimal reviewBy;
	
	public BigDecimal getMaterialId() {
		return materialId;
	}
	
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	
	public Date getRevisionDate() {
		return revisionDate;
	}
	
	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public BigDecimal getQcErrorTypeId() {
		return qcErrorTypeId;
	}
	
	public void setQcErrorTypeId(BigDecimal qcErrorTypeId) {
		this.qcErrorTypeId = qcErrorTypeId;
	}
	
	public String getQcErrorType() {
		return qcErrorType;
	}
	
	public void setQcErrorType(String qcErrorType) {
		this.qcErrorType = qcErrorType;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public BigDecimal getReviewBy() {
		return reviewBy;
	}

	public void setReviewBy(BigDecimal reviewBy) {
		this.reviewBy = reviewBy;
	}
}
