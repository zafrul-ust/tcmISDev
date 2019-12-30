package com.tcmis.internal.supply.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class BoPoChangeReasonBean extends BaseDataBean {
// This bean reflects CREATE TABLE TCM_OPS.VV_BO_PO_CHANGE_REASON
//	(
//			  REASON_ID           INTEGER,
//			  REASON_DESCRIPTION  VARCHAR2(300 BYTE)        NOT NULL,
//			  STATUS              CHAR(1 BYTE)              DEFAULT 'A'                   NOT NULL,
//			  DISPLAY_ORDER       INTEGER                   NOT NULL,
//			  CREATED_BY          INTEGER,
//			  CREATED_DATE        DATE                      DEFAULT SYSDATE               NOT NULL,
//			  LAST_UPDATED_BY     INTEGER,
//			  LAST_UPDATED_DATE   DATE                      DEFAULT SYSDATE               NOT NULL
//			)
	public BoPoChangeReasonBean() {
    }

    private BigDecimal reasonId;
    private String reasonDescription;
    private BigDecimal displayOrder;
    private String status;
    private BigDecimal createdBy;
    private Date createdDate;
    private BigDecimal lastUpdatedBy;
    private Date lastUpdatedDate;

    public BigDecimal getReasonId() {
		return reasonId;
	}

	public void setReasonId(BigDecimal reasonId) {
		this.reasonId = reasonId;
	}

	public String getReasonDescription() {
		return reasonDescription;
	}

	public void setReasonDescription(String reasonDescription) {
		this.reasonDescription = reasonDescription;
	}

	public BigDecimal getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(BigDecimal displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public BigDecimal getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(BigDecimal lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}


} //end of class
