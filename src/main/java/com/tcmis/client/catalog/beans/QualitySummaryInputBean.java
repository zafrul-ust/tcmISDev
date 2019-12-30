package com.tcmis.client.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;

public class QualitySummaryInputBean extends BaseDataBean {

	 private String catalogId;
	 private String catPartNo;
	 private String catalogCompanyId;
     private String partGroupNo;
     private String facilityId;

     public QualitySummaryInputBean() {
	 }

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

    public String getPartGroupNo() {
        return partGroupNo;
    }

    public void setPartGroupNo(String partGroupNo) {
        this.partGroupNo = partGroupNo;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }
}
