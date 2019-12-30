package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class ExpertReviewEntityBean extends BaseDataBean {
	
	private boolean ok;
	private String opsEntityId;
	private String operatingEntityName;
	private BigDecimal createdBy;
	private String createdByName;
	private Date dateCreated;
	private boolean poReview;
	private boolean mrReview;
	private boolean modified;
	
	public boolean isOk() {
		return ok;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public String getOperatingEntityName() {
		return operatingEntityName;
	}

	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}

	public BigDecimal getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public boolean isPoReview() {
		return poReview;
	}

	public void setPoReview(boolean poReview) {
		this.poReview = poReview;
	}

	public boolean isMrReview() {
		return mrReview;
	}

	public void setMrReview(boolean mrReview) {
		this.mrReview = mrReview;
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}
}
