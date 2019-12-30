package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class ContactLogMaterialBean extends BaseDataBean {
	
	private BigDecimal materialId;
	private Date revisionDate;
	
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
}
