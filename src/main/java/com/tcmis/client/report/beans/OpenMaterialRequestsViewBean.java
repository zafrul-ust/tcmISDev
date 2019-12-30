package com.tcmis.client.report.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class OpenMaterialRequestsViewBean extends BaseDataBean {

	
	private String companyId;
    private String facilityId;
    private String facilityName;
    private BigDecimal prNumber;
	private String lineItem;
    private String application;
    private String applicationDesc;
    private String facPartNo;
    private String partDescription;
    private String packaging;
	private BigDecimal orderQty;
	private BigDecimal allocatedQty;
	private BigDecimal docNum;
    private String docType;
    private Date projDeliverDate;
    private BigDecimal uosOrderQty;
    private BigDecimal uosAllocatedQty;
    private String unitOfSale;
    private String programId;
    private String ownerSegmentId;
    private BigDecimal catalogPrice;
    private String currencyId;

    //constructor
	public OpenMaterialRequestsViewBean() {
	}
	
    public BigDecimal getCatalogPrice() {
        return catalogPrice;
    }

    public void setCatalogPrice(BigDecimal catalogPrice) {
        this.catalogPrice = catalogPrice;
    }
    
    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
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

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public BigDecimal getPrNumber() {
        return prNumber;
    }

    public void setPrNumber(BigDecimal prNumber) {
        this.prNumber = prNumber;
    }

    public String getLineItem() {
        return lineItem;
    }

    public void setLineItem(String lineItem) {
        this.lineItem = lineItem;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getApplicationDesc() {
        return applicationDesc;
    }

    public void setApplicationDesc(String applicationDesc) {
        this.applicationDesc = applicationDesc;
    }

    public String getFacPartNo() {
        return facPartNo;
    }

    public void setFacPartNo(String facPartNo) {
        this.facPartNo = facPartNo;
    }

    public String getPartDescription() {
        return partDescription;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public BigDecimal getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(BigDecimal orderQty) {
        this.orderQty = orderQty;
    }

    public BigDecimal getAllocatedQty() {
        return allocatedQty;
    }

    public void setAllocatedQty(BigDecimal allocatedQty) {
        this.allocatedQty = allocatedQty;
    }

    public BigDecimal getDocNum() {
        return docNum;
    }

    public void setDocNum(BigDecimal docNum) {
        this.docNum = docNum;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public Date getProjDeliverDate() {
        return projDeliverDate;
    }

    public void setProjDeliverDate(Date projDeliverDate) {
        this.projDeliverDate = projDeliverDate;
    }

    public BigDecimal getUosOrderQty() {
        return uosOrderQty;
    }

    public void setUosOrderQty(BigDecimal uosOrderQty) {
        this.uosOrderQty = uosOrderQty;
    }

    public BigDecimal getUosAllocatedQty() {
        return uosAllocatedQty;
    }

    public void setUosAllocatedQty(BigDecimal uosAllocatedQty) {
        this.uosAllocatedQty = uosAllocatedQty;
    }

    public String getUnitOfSale() {
        return unitOfSale;
    }

    public void setUnitOfSale(String unitOfSale) {
        this.unitOfSale = unitOfSale;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getOwnerSegmentId() {
        return ownerSegmentId;
    }

    public void setOwnerSegmentId(String ownerSegmentId) {
        this.ownerSegmentId = ownerSegmentId;
    }
}
