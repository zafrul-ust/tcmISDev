package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ItemCountDisplayViewBean <br>
 * 
 * @version: 1.0, Apr 20, 2010 <br>
 *****************************************************************************/

public class ItemCountDisplayViewBean extends BaseDataBean {

	private String catalogCompanyId;
	private String catalogId;
	private String catPartNo;
	private String closed;
	private String companyId;
	private BigDecimal countedQuantity;
	private BigDecimal countId;
	private String countStatus;
	private String countType;
	private String creditAtOverCount;
	private Date dateCounted;
	private String description;
	private String hub;
	private BigDecimal inPurchasing;
	private String inventoryGroup;
	private String inventoryGroupName;
	private BigDecimal inventoryQuantity;
	private String itemId;
	private String lastBin;
	private Date lastDateCounted;
	private Date lastDateOfReceipt;
	private Date lastReceiptQcDate;
	private String packaging;
	private BigDecimal partGroupNo;
	private Date pickDate;
	private String processStatement;
	private String receiptProcessingMethod;
	private Date startDate;
	private String uom;
	private BigDecimal mdItemId;

	// constructor
	public ItemCountDisplayViewBean() {
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public String getClosed() {
		return closed;
	}

	public String getCompanyId() {
		return companyId;
	}

	public BigDecimal getCountedQuantity() {
		return countedQuantity;
	}

	public BigDecimal getCountId() {
		return countId;
	}

	public String getCountStatus() {
		return countStatus;
	}

	public String getCountType() {
		return countType;
	}

	public String getCreditAtOverCount() {
		return creditAtOverCount;
	}

	public Date getDateCounted() {
		return dateCounted;
	}

	public String getDescription() {
		return description;
	}

	public String getHub() {
		return hub;
	}

	public BigDecimal getInPurchasing() {
		return inPurchasing;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public BigDecimal getInventoryQuantity() {
		return inventoryQuantity;
	}

	public String getItemId() {
		return itemId;
	}

	public String getLastBin() {
		return lastBin;
	}

	public Date getLastDateCounted() {
		return lastDateCounted;
	}

	public Date getLastDateOfReceipt() {
		return lastDateOfReceipt;
	}

	public Date getLastReceiptQcDate() {
		return lastReceiptQcDate;
	}

	public String getPackaging() {
		return packaging;
	}

	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}

	public Date getPickDate() {
		return pickDate;
	}

	public String getProcessStatement() {
		return processStatement;
	}

	public String getReceiptProcessingMethod() {
		return receiptProcessingMethod;
	}

	public Date getStartDate() {
		return startDate;
	}

	public String getUom() {
		return uom;
	}
	
	public BigDecimal getMdItemId() {
		return mdItemId;
	}
	
	public boolean isIssueOnReceipt() {
		return "Issue On Receipt".equals(getReceiptProcessingMethod());
	}
	
	public boolean isPreReceipt() {
		if (getLastDateOfReceipt() != null && getLastDateOfReceipt().after(new Date())) {
			return true;
		}
		else if (getLastReceiptQcDate() != null && getStartDate() != null && getLastReceiptQcDate().after(getStartDate())) {
			return true;
		}
		return false;
	}

	public boolean isTreatAsCounted() {
		return isIssueOnReceipt() || isPreReceipt();
	}
		
	public void setMdItemId(BigDecimal mdItemId) {
		this.mdItemId = mdItemId;
	}
	
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public void setClosed(String closed) {
		this.closed = closed;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCountedQuantity(BigDecimal countedQuantity) {
		this.countedQuantity = countedQuantity;
	}

	public void setCountId(BigDecimal countId) {
		this.countId = countId;
	}

	public void setCountStatus(String countStatus) {
		this.countStatus = countStatus;
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}

	public void setCreditAtOverCount(String creditAtOverCount) {
		this.creditAtOverCount = creditAtOverCount;
	}

	public void setDateCounted(Date dateCounted) {
		this.dateCounted = dateCounted;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setInPurchasing(BigDecimal inPurchasing) {
		this.inPurchasing = inPurchasing;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	public void setInventoryQuantity(BigDecimal inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void setLastBin(String lastBin) {
		this.lastBin = lastBin;
	}

	public void setLastDateCounted(Date lastDateCounted) {
		this.lastDateCounted = lastDateCounted;
	}

	public void setLastDateOfReceipt(Date lastDateOfReceipt) {
		this.lastDateOfReceipt = lastDateOfReceipt;
	}

	public void setLastReceiptQcDate(Date lastReceiptQcDate) {
		this.lastReceiptQcDate = lastReceiptQcDate;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}

	public void setPickDate(Date pickDate) {
		this.pickDate = pickDate;
	}

	public void setProcessStatement(String processStatement) {
		this.processStatement = processStatement;
	}

	public void setReceiptProcessingMethod(String receiptProcessingMethod) {
		this.receiptProcessingMethod = receiptProcessingMethod;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}
}