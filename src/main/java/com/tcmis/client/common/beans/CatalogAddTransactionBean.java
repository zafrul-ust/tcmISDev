package com.tcmis.client.common.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class CatalogAddTransactionBean  extends BaseDataBean {
    private BigDecimal catAddRequestId;
    private String companyId;
    private String facilityId;
    private String catalogCompanyId;
    private String catalogId;
    private String activityPartNumber;
    private String activityPartNumber2;
    private String activityPartNumber3;
    private String activityPartDescription;
    private String activityPartDescription2;
    private String activityPartDescription3;
    private String activityVendorName;
    private String activitySource;
    private String requestorName;
    private String requestorPhone;
    private String requestorEmail;
    private String unitOfMeasure;
    private BigDecimal activityQuantity;
    private String casNumber;
    private String manufacturerName;
    private String substanceName;
    private String approvalPath;
    

    public CatalogAddTransactionBean() {
    }
    public void setActivityQuantity(BigDecimal activityQuantity) {
    	this.activityQuantity = activityQuantity;
    }
    
    public BigDecimal getActivityQuantity() {
    	return activityQuantity;
     }
    public void setUnitOfMeasure(String unitOfMeasure) {
    	this.unitOfMeasure = unitOfMeasure;
    }
    
    public String getUnitOfMeasure() {
    	return unitOfMeasure;
     }
    
    public BigDecimal getCatAddRequestId() {
        return catAddRequestId;
    }

    public void setCatAddRequestId(BigDecimal catAddRequestId) {
        this.catAddRequestId = catAddRequestId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getCatalogCompanyId() {
        return catalogCompanyId;
    }

    public void setCatalogCompanyId(String catalogCompanyId) {
        this.catalogCompanyId = catalogCompanyId;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getActivityPartNumber() {
        return activityPartNumber;
    }

    public void setActivityPartNumber(String activityPartNumber) {
        this.activityPartNumber = activityPartNumber;
    }

    public String getActivityPartNumber2() {
        return activityPartNumber2;
    }

    public void setActivityPartNumber2(String activityPartNumber2) {
        this.activityPartNumber2 = activityPartNumber2;
    }

    public String getActivityPartNumber3() {
        return activityPartNumber3;
    }

    public void setActivityPartNumber3(String activityPartNumber3) {
        this.activityPartNumber3 = activityPartNumber3;
    }

    public String getActivityPartDescription() {
        return activityPartDescription;
    }

    public void setActivityPartDescription(String activityPartDescription) {
        this.activityPartDescription = activityPartDescription;
    }

    public String getActivityPartDescription2() {
        return activityPartDescription2;
    }

    public void setActivityPartDescription2(String activityPartDescription2) {
        this.activityPartDescription2 = activityPartDescription2;
    }

    public String getActivityPartDescription3() {
        return activityPartDescription3;
    }

    public void setActivityPartDescription3(String activityPartDescription3) {
        this.activityPartDescription3 = activityPartDescription3;
    }

    public String getActivityVendorName() {
        return activityVendorName;
    }

    public void setActivityVendorName(String activityVendorName) {
        this.activityVendorName = activityVendorName;
    }

    public String getActivitySource() {
        return activitySource;
    }

    public void setActivitySource(String activitySource) {
        this.activitySource = activitySource;
    }

    public String getRequestorName() {
        return requestorName;
    }

    public void setRequestorName(String requestorName) {
        this.requestorName = requestorName;
    }

    public String getRequestorPhone() {
        return requestorPhone;
    }

    public void setRequestorPhone(String requestorPhone) {
        this.requestorPhone = requestorPhone;
    }

    public String getRequestorEmail() {
        return requestorEmail;
    }

    public void setRequestorEmail(String requestorEmail) {
        this.requestorEmail = requestorEmail;
    }

    public String getSubstanceName() {
        return substanceName;
    }

    public void setSubstanceName(String substanceName) {
        this.substanceName = substanceName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getCasNumber() {
        return casNumber;
    }

    public void setCasNumber(String casNumber) {
        this.casNumber = casNumber;
    }

    public String getApprovalPath() {
        return approvalPath;
    }

    public void setApprovalPath(String approvalPath) {
        this.approvalPath = approvalPath;
    }
}
