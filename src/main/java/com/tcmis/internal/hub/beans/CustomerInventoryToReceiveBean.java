package com.tcmis.internal.hub.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerInventoryToReceiveBean <br>
 * @version: 1.0, Sep 5, 2008 <br>
 *****************************************************************************/


public class CustomerInventoryToReceiveBean extends BaseDataBean  {

	private String ownerCompanyId;
	private String poNumber;
	private BigDecimal quantityToReceive;
	private String catalogCompanyId;
	private String catalogId;
	private String catPartNo;
	private Date dateInserted;
	private Date expectedDeliveryDate;
	private String inventoryGroup;
	private BigDecimal totalQuantityReceived;
	private BigDecimal itemId;
	private String itemDesc;
	private String customerPoNo;
	private String customerPoLine;
	private BigDecimal docNum;
	private String notes;
	private BigDecimal buyerId;
	private String supplierName;
	private Date dateLastUpdated;
	private BigDecimal lastUpdatedBy;
	private String updatedByName;
	private BigDecimal partGroupNo;
	private String clausesSet11;
	private String clausesSet22;
	private String clausesSet33;
	private String clausesSet44;
	private String clausesSet55;
	private String supplierCode;
	private String msdsId;
	private BigDecimal enteredBy;
	private String enteredByName;
	private String opsEntityId;
	private String operatingEntityName;
	private String unitOfSale;
	private String packaging;
	private String ownerSegmentId;
	private String accountNumber;
	private String accountNumber2;
	private String accountNumber3;
	private String accountNumber4;
	private String customerReceiptId;
	private String qualityTrackingNumber;
	private String ok;
    private Collection ownerSegmentColl;
    private String automatedPutaway;
    private String dataTransferStatus;



    //constructor
	public CustomerInventoryToReceiveBean() {
	}

	//setters
	public void setOwnerCompanyId(String ownerCompanyId) {
		this.ownerCompanyId = ownerCompanyId;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setQuantityToReceive(BigDecimal quantityToReceive) {
		this.quantityToReceive = quantityToReceive;
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
	public void setDateInserted(Date dateInserted) {
		this.dateInserted = dateInserted;
	}
	public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setTotalQuantityReceived(BigDecimal totalQuantityReceived) {
		this.totalQuantityReceived = totalQuantityReceived;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setCustomerPoNo(String customerPoNo) {
		this.customerPoNo = customerPoNo;
	}
	public void setCustomerPoLine(String customerPoLine) {
		this.customerPoLine = customerPoLine;
	}
	public void setDocNum(BigDecimal docNum) {
		this.docNum = docNum;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public void setBuyerId(BigDecimal buyerId) {
		this.buyerId = buyerId;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setDateLastUpdated(Date dateLastUpdated) {
		this.dateLastUpdated = dateLastUpdated;
	}
	public void setLastUpdatedBy(BigDecimal lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}
	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}
	public void setClausesSet1(String clausesSet11) {
		this.clausesSet11 = clausesSet11;
	}
	public void setClausesSet2(String clausesSet22) {
		this.clausesSet22 = clausesSet22;
	}
	public void setClausesSet3(String clausesSet33) {
		this.clausesSet33 = clausesSet33;
	}
	public void setClausesSet4(String clausesSet44) {
		this.clausesSet44 = clausesSet44;
	}
	public void setClausesSet5(String clausesSet44) {
		this.clausesSet44 = clausesSet55;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	//getters
	public String getOwnerCompanyId() {
		return ownerCompanyId;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public BigDecimal getQuantityToReceive() {
		return quantityToReceive;
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
	public Date getDateInserted() {
		return dateInserted;
	}
	public Date getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public BigDecimal getTotalQuantityReceived() {
		return totalQuantityReceived;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getCustomerPoNo() {
		return customerPoNo;
	}
	public String getCustomerPoLine() {
		return customerPoLine;
	}
	public BigDecimal getDocNum() {
		return docNum;
	}
	public String getNotes() {
		return notes;
	}
	public BigDecimal getBuyerId() {
		return buyerId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public Date getDateLastUpdated() {
		return dateLastUpdated;
	}
	public BigDecimal getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public String getUpdatedByName() {
		return updatedByName;
	}
	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}
	public String getClausesSet1() {
		return clausesSet11;
	}
	public String getClausesSet2() {
		return clausesSet22;
	}
	public String getClausesSet3() {
		return clausesSet33;
	}
	public String getClausesSet4() {
		return clausesSet44;
	}
	public String getClausesSet5() {
		return clausesSet55;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public String getMsdsId() {
		return msdsId;
	}
	public void setMsdsId(String msdsId) {
		this.msdsId = msdsId;
	}
	public BigDecimal getEnteredBy() {
		return enteredBy;
	}
	public void setEnteredBy(BigDecimal enteredBy) {
		this.enteredBy = enteredBy;
	}
	public String getEnteredByName() {
		return enteredByName;
	}
	public void setEnteredByName(String enteredByName) {
		this.enteredByName = enteredByName;
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
	
	public String getUnitOfSale() {
		return unitOfSale;
	}

	public void setUnitOfSale(String unitOfSale) {
		this.unitOfSale = unitOfSale;
	}

	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
		
	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	
	
	public String getClausesSet11() {
		return clausesSet11;
	}

	public void setClausesSet11(String clausesSet11) {
		this.clausesSet11 = clausesSet11;
	}

	public String getClausesSet22() {
		return clausesSet22;
	}

	public void setClausesSet22(String clausesSet22) {
		this.clausesSet22 = clausesSet22;
	}

	public String getClausesSet33() {
		return clausesSet33;
	}

	public void setClausesSet33(String clausesSet33) {
		this.clausesSet33 = clausesSet33;
	}

	public String getClausesSet44() {
		return clausesSet44;
	}

	public void setClausesSet44(String clausesSet44) {
		this.clausesSet44 = clausesSet44;
	}

	public String getClausesSet55() {
		return clausesSet55;
	}

	public void setClausesSet55(String clausesSet55) {
		this.clausesSet55 = clausesSet55;
	}

	public String getOwnerSegmentId() {
		return ownerSegmentId;
	}

	public void setOwnerSegmentId(String ownerSegmentId) {
		this.ownerSegmentId = ownerSegmentId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountNumber2() {
		return accountNumber2;
	}

	public void setAccountNumber2(String accountNumber2) {
		this.accountNumber2 = accountNumber2;
	}

	public String getAccountNumber3() {
		return accountNumber3;
	}

	public void setAccountNumber3(String accountNumber3) {
		this.accountNumber3 = accountNumber3;
	}

	public String getAccountNumber4() {
		return accountNumber4;
	}

	public void setAccountNumber4(String accountNumber4) {
		this.accountNumber4 = accountNumber4;
	}

	public String getCustomerReceiptId() {
		return customerReceiptId;
	}

	public void setCustomerReceiptId(String customerReceiptId) {
		this.customerReceiptId = customerReceiptId;
	}

	public String getQualityTrackingNumber() {
		return qualityTrackingNumber;
	}

	public void setQualityTrackingNumber(String qualityTrackingNumber) {
		this.qualityTrackingNumber = qualityTrackingNumber;
	}

	
	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

    public Collection getOwnerSegmentColl() {
        return ownerSegmentColl;
    }

    public void setOwnerSegmentColl(Collection ownerSegmentColl) {
        this.ownerSegmentColl = ownerSegmentColl;
    }

	public String getAutomatedPutaway() {
		return automatedPutaway;
	}

	public void setAutomatedPutaway(String automatedPutaway) {
		this.automatedPutaway = automatedPutaway;
	}

	public String getDataTransferStatus() {
		return dataTransferStatus;
	}

	public void setDataTransferStatus(String dataTransferStatus) {
		this.dataTransferStatus = dataTransferStatus;
	}
}