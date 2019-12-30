package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import com.tcmis.common.framework.BaseDataBean;

public class QualitySummaryViewBean extends BaseDataBean {
	
	private String facilityId;
	private String facPartNo;
	private Collection facItemColl;
	private Collection specificationsColl;
	private Collection purchasingNotesColl;
	private Collection receivingNotesColl;
	private String qualityIdLabel;
	
	public QualitySummaryViewBean() {
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getFacPartNo() {
		return facPartNo;
	}

	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}

	

	public Collection getFacItemColl() {
		return facItemColl;
	}

	public void setFacItemColl(Collection facItemColl) {
		this.facItemColl = facItemColl;
	}

	
	public Collection getSpecificationsColl() {
		return specificationsColl;
	}

	public void setSpecificationsColl(Collection specificationsColl) {
		this.specificationsColl = specificationsColl;
	}

	public Collection getPurchasingNotesColl() {
		return purchasingNotesColl;
	}

	public void setPurchasingNotesColl(Collection purchasingNotesColl) {
		this.purchasingNotesColl = purchasingNotesColl;
	}

	public Collection getReceivingNotesColl() {
		return receivingNotesColl;
	}

	public void setReceivingNotesColl(Collection receivingNotesColl) {
		this.receivingNotesColl = receivingNotesColl;
	}

	public String getQualityIdLabel() {
		return qualityIdLabel;
	}

	public void setQualityIdLabel(String qualityIdLabel) {
		this.qualityIdLabel = qualityIdLabel;
	}
}
