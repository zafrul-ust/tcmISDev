package com.tcmis.ws.tablet.beans;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: PoLineBean <br>
 * 
 * @version: 1.0, Mar 7, 2013 <br>
 *****************************************************************************/

public class InboundTransLineBean extends BaseDataBean {

	private String		accountNumber;
	private String		accountNumber2;
	private String		accountNumber3;
	private String		accountNumber4;
	private String		customerReceiptId;
	private BigDecimal	customerRmaId;
	private String		definedShelfLifeBasis;
	private String		definedShelfLifeItem;
	private String		displayPkgStyle;
	private BigDecimal	docNum;
	private Date		expectedDate;
	private String		hubId;
	private String		indefiniteShelfLife;
	private BigDecimal		interCompanyPo;
	private BigDecimal	interCompanyPoLine;
	private String		inventoryGroup;
	private BigDecimal	itemId;
	private BigDecimal	lineItem;
	private String		manageKitsAsSingleUnit;
	private String		nonIntegerReceiving;
	private String		notes;
	private String		ownerCompanyId;
	private String		ownerSegmentId;
	private String		polchemIg;
	private String		poNumber;
	private String		purchasingUnitOfMeasure;
	private BigDecimal	purchasingUnitsPerItem;
	private BigDecimal	qtyOpen;
	private String		qualityTrackingNumber;
	private BigDecimal	radianPo;
	private BigDecimal	transferReceiptId;
	private BigDecimal	transferRequestId;
	private String		transtype;

	// constructor
	public InboundTransLineBean() {
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getAccountNumber2() {
		return accountNumber2;
	}

	public String getAccountNumber3() {
		return accountNumber3;
	}

	public String getAccountNumber4() {
		return accountNumber4;
	}

	public String getCustomerReceiptId() {
		return customerReceiptId;
	}

	public BigDecimal getCustomerRmaId() {
		return customerRmaId;
	}

	public String getDefinedShelfLifeBasis() {
		return definedShelfLifeBasis;
	}

	public String getDefinedShelfLifeItem() {
		return definedShelfLifeItem;
	}

	public String getDisplayPkgStyle() {
		return displayPkgStyle;
	}

	public BigDecimal getDocNum() {
		return docNum;
	}

	public Date getExpectedDate() {
		return expectedDate;
	}

	public String getHubId() {
		return hubId;
	}

	public String getIndefiniteShelfLife() {
		return indefiniteShelfLife;
	}

	public BigDecimal getInterCompanyPo() {
		return interCompanyPo;
	}

	public BigDecimal getInterCompanyPoLine() {
		return interCompanyPoLine;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public BigDecimal getLineItem() {
		return lineItem;
	}

	public String getManageKitsAsSingleUnit() {
		return manageKitsAsSingleUnit;
	}

	public String getNonIntegerReceiving() {
		return nonIntegerReceiving;
	}

	public String getNotes() {
		return notes;
	}

	public String getOwnerCompanyId() {
		return ownerCompanyId;
	}

	public String getOwnerSegmentId() {
		return ownerSegmentId;
	}

	public String getPolchemIg() {
		return polchemIg;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public String getPurchasingUnitOfMeasure() {
		return purchasingUnitOfMeasure;
	}

	public BigDecimal getPurchasingUnitsPerItem() {
		return purchasingUnitsPerItem;
	}

	public BigDecimal getQtyOpen() {
		return qtyOpen;
	}

	public String getQualityTrackingNumber() {
		return qualityTrackingNumber;
	}

	public BigDecimal getRadianPo() {
		return radianPo;
	}

	public BigDecimal getTransferReceiptId() {
		return transferReceiptId;
	}

	public BigDecimal getTransferRequestId() {
		return transferRequestId;
	}

	public String getTranstype() {
		return transtype;
	}

	public boolean hasInventoryGroup() {
		return StringUtils.isNotBlank(inventoryGroup);
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setAccountNumber2(String accountNumber2) {
		this.accountNumber2 = accountNumber2;
	}

	public void setAccountNumber3(String accountNumber3) {
		this.accountNumber3 = accountNumber3;
	}

	public void setAccountNumber4(String accountNumber4) {
		this.accountNumber4 = accountNumber4;
	}

	public void setCustomerReceiptId(String customerReceiptId) {
		this.customerReceiptId = customerReceiptId;
	}

	public void setCustomerRmaId(BigDecimal customerRmaId) {
		this.customerRmaId = customerRmaId;
	}

	public void setDefinedShelfLifeBasis(String definedShelfLifeBasis) {
		this.definedShelfLifeBasis = definedShelfLifeBasis;
	}

	public void setDefinedShelfLifeItem(String definedShelfLifeItem) {
		this.definedShelfLifeItem = definedShelfLifeItem;
	}

	public void setDisplayPkgStyle(String displayPkgStyle) {
		this.displayPkgStyle = displayPkgStyle;
	}

	public void setDocNum(BigDecimal docNum) {
		this.docNum = docNum;
	}

	public void setExpectedDate(Date expectedDate) {
		this.expectedDate = expectedDate;
	}

	public void setHubId(String hub) {
		this.hubId = hub;
	}

	public void setIndefiniteShelfLife(String indefiniteShelfLife) {
		this.indefiniteShelfLife = indefiniteShelfLife;
	}

	public void setInterCompanyPo(BigDecimal interCompanyPo) {
		this.interCompanyPo = interCompanyPo;
	}

	public void setInterCompanyPoLine(BigDecimal interCompanyPoLine) {
		this.interCompanyPoLine = interCompanyPoLine;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setLineItem(BigDecimal lineItem) {
		this.lineItem = lineItem;
	}

	public void setManageKitsAsSingleUnit(String mangeKitsAsSingleUnit) {
		this.manageKitsAsSingleUnit = mangeKitsAsSingleUnit;
	}

	public void setNonIntegerReceiving(String nonIntegerReceiving) {
		this.nonIntegerReceiving = nonIntegerReceiving;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setOwnerCompanyId(String ownerCompanyId) {
		this.ownerCompanyId = ownerCompanyId;
	}

	public void setOwnerSegmentId(String ownerSegmentId) {
		this.ownerSegmentId = ownerSegmentId;
	}

	public void setPolchemIg(String polchemIg) {
		this.polchemIg = polchemIg;
	}

	public void setPoNumber(String customerPo) {
		this.poNumber = customerPo;
	}

	public void setPurchasingUnitOfMeasure(String purchasingUnitOfMeasure) {
		this.purchasingUnitOfMeasure = purchasingUnitOfMeasure;
	}

	public void setPurchasingUnitsPerItem(BigDecimal purchasingUnitsPerItem) {
		this.purchasingUnitsPerItem = purchasingUnitsPerItem;
	}

	public void setQtyOpen(BigDecimal quantityOpen) {
		this.qtyOpen = quantityOpen;
	}

	public void setQualityTrackingNumber(String qualityTrackingNumber) {
		this.qualityTrackingNumber = qualityTrackingNumber;
	}

	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}

	public void setTransferReceiptId(BigDecimal transferReceiptId) {
		this.transferReceiptId = transferReceiptId;
	}

	public void setTransferRequestId(BigDecimal transferRequestId) {
		this.transferRequestId = transferRequestId;
	}

	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}

}