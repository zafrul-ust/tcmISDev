package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseDataBean;

public class ReceivingReceipt extends BaseDataBean {
	private String						accountNumber;
	private String						accountNumber2;
	private String						accountNumber3;
	private String						accountNumber4;
	private String						bin;
	private String						catalogCompanyId;
	private String						catalogId;
	private String						catPartNo;
	private String						customerPo;
	private String						customerReceiptId;
	private BigDecimal					customerRmaId;
	private Date						dateOfManufacture;
	private Date						dateOfReceipt;
	private Date						dateOfShipment;
	private String						definedShelfLifeItem;
	private BigDecimal					docNum;
	private String						docType;
	private Date						expireDate;
	private boolean						ghsHazardStatement;
	private boolean						ghsPictogram;
	private boolean						ghsPrecautionaryStatement;
	private boolean						ghsProductName;
	private boolean						ghsSignalWord;
	private boolean						ghsSupplierInfo;
	private String						hazcomLabelFlag;
	private String						internalReceiptNotes;
	private String						inventoryGroup;
	private BigDecimal					itemId;
	private Collection<KitComponent>	kitComponents;
	private String						lastBin;
	private Date						lastUpdated;
	private String						lot;
	private String						notes;
	private BigDecimal					originalReceiptId;
	private String						ownerCompanyId;
	private String						ownerSegmentId;
	private BigDecimal					partGroupNo;
	private BigDecimal					po;
	private BigDecimal					poLine;
	private BigDecimal						qaStatement;
	private BigDecimal					quantity;
	private BigDecimal					receiptGroup;
	private BigDecimal					receiptId;
	private BigDecimal						receivedPurchasingUnits;
	private String						receivingStatus	= "QC Ready";
	private boolean						separableKit;
	private String						shipmentDetailId;
	private BigDecimal					transferRequestId;
	private Date						vendorShipDate;

	public ReceivingReceipt() {
		super();
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

	public String getBin() {
		return bin;
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

	public String getCustomerPo() {
		return customerPo;
	}

	public String getCustomerReceiptId() {
		return customerReceiptId;
	}

	public BigDecimal getCustomerRmaId() {
		return customerRmaId;
	}

	public Date getDateOfManufacture() {
		return dateOfManufacture;
	}

	public Date getDateOfReceipt() {
		return dateOfReceipt;
	}

	public Date getDateOfShipment() {
		return dateOfShipment;
	}

	public String getDefinedShelfLifeItem() {
		return this.definedShelfLifeItem;
	}

	public BigDecimal getDocNum() {
		return docNum;
	}

	public String getDocType() {
		return docType;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public String getHazcomLabelFlag() {
		return this.hazcomLabelFlag;
	}

	public String getInternalReceiptNotes() {
		return internalReceiptNotes;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public Collection<KitComponent> getKitComponents() {
		return kitComponents;
	}

	public String getLastBin() {
		return lastBin;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public String getLot() {
		return lot;
	}

	public String getNotes() {
		return notes;
	}

	public BigDecimal getOriginalReceiptId() {
		return originalReceiptId;
	}

	public String getOwnerCompanyId() {
		return ownerCompanyId;
	}

	public String getOwnerSegmentId() {
		return ownerSegmentId;
	}

	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	};

	public BigDecimal getPo() {
		return po;
	}

	public BigDecimal getPoLine() {
		return poLine;
	}

	public BigDecimal getQaStatement() {
		return this.qaStatement;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public BigDecimal getReceiptGroup() {
		return this.receiptGroup;
	}

	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public BigDecimal getReceivedPurchasingUnits() {
		return this.receivedPurchasingUnits;
	}

	public String getReceivingStatus() {
		return receivingStatus;
	}

	public String getShipmentDetailId() {
		return shipmentDetailId;
	}

	public BigDecimal getTransferRequestId() {
		return transferRequestId;
	}

	public Date getVendorShipDate() {
		return vendorShipDate;
	}

	public boolean hasCustomerRmaId() {
		return customerRmaId != null;
	}

	public boolean hasDocNum() {
		return docNum != null;
	}

	public boolean hasKitComponents() {
		return kitComponents != null && !kitComponents.isEmpty();
	}

	public boolean hasPo() {
		return po != null;
	}

	public boolean hasTransferRequestId() {
		return transferRequestId != null;
	}

	public boolean isCITR() {
		return hasDocNum();
	}

	public boolean isGhsHazardStatement() {
		return ghsHazardStatement;
	}

	public boolean isGhsPictogram() {
		return ghsPictogram;
	}

	public boolean isGhsPrecautionaryStatement() {
		return ghsPrecautionaryStatement;
	}

	public boolean isGhsProductName() {
		return ghsProductName;
	}

	public boolean isGhsSignalWord() {
		return ghsSignalWord;
	}

	public boolean isGhsSupplierInfo() {
		return ghsSupplierInfo;
	}

	public boolean isSeparableKit() {
		return separableKit;
	}

	public boolean IsValidReceipt() {
		return true;
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

	public void setBin(String bin) {
		this.bin = bin;
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

	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}

	public void setCustomerReceiptId(String customerReceiptId) {
		this.customerReceiptId = customerReceiptId;
	}

	public void setCustomerRmaId(BigDecimal po) {
		this.customerRmaId = po;
	}

	public void setDateOfManufacture(Date dateOfManufacture) {
		this.dateOfManufacture = dateOfManufacture;
	}

	public void setDateOfReceipt(Date dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}

	public void setDateOfShipment(Date dateOfShipment) {
		this.dateOfShipment = dateOfShipment;
	}

	public void setDefinedShelfLifeItem(String definedShelfLifeItem) {
		this.definedShelfLifeItem = definedShelfLifeItem;
	}

	public void setDocNum(BigDecimal docNum) {
		this.docNum = docNum;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public void setGhsHazardStatement(boolean ghsHazardStatement) {
		this.ghsHazardStatement = ghsHazardStatement;
	}

	public void setGhsPictogram(boolean ghsPictogram) {
		this.ghsPictogram = ghsPictogram;
	}

	public void setGhsPrecautionaryStatement(boolean ghsPrecautionaryStatement) {
		this.ghsPrecautionaryStatement = ghsPrecautionaryStatement;
	}

	public void setGhsProductName(boolean ghsProductName) {
		this.ghsProductName = ghsProductName;
	}

	public void setGhsSignalWord(boolean ghsSignalWord) {
		this.ghsSignalWord = ghsSignalWord;
	}

	public void setGhsSupplierInfo(boolean ghsSupplierInfo) {
		this.ghsSupplierInfo = ghsSupplierInfo;
	}

	public void setHazcomLabelFlag(String hazcomLabelFlag) {
		this.hazcomLabelFlag = hazcomLabelFlag;
	}

	public void setInternalReceiptNotes(String internalReceiptNotes) {
		this.internalReceiptNotes = internalReceiptNotes;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setKitComponents(Collection<KitComponent> kitComponents) {
		this.kitComponents = kitComponents;
	}

	public void setLastBin(String lastBin) {
		this.lastBin = lastBin;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setOriginalReceiptId(BigDecimal originalReceiptId) {
		this.originalReceiptId = originalReceiptId;
	}

	public void setOwnerCompanyId(String ownerCompanyId) {
		this.ownerCompanyId = ownerCompanyId;
	}

	public void setOwnerSegmentId(String ownerSegmentId) {
		this.ownerSegmentId = ownerSegmentId;
	}

	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}

	public void setPo(BigDecimal po) {
		this.po = po;
	}

	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}

	public void setQaStatement(BigDecimal qaStatement) {
		this.qaStatement = qaStatement;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public void setReceiptGroup(BigDecimal receiptGroup) {
		this.receiptGroup = receiptGroup;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public void setReceivedPurchasingUnits(BigDecimal receivedPurchasingUnits) {
		this.receivedPurchasingUnits = receivedPurchasingUnits;
	}

	public void setReceivingStatus(String receivingStatus) {
		this.receivingStatus = receivingStatus;
	}

	public void setSeparableKit(boolean separableKit) {
		this.separableKit = separableKit;
	}

	public void setShipmentDetailId(String shipmentDetailId) {
		this.shipmentDetailId = shipmentDetailId;
	}

	public void setTransferRequestId(BigDecimal po) {
		this.transferRequestId = po;
	}

	public void setVendorShipDate(Date vendorShipDate) {
		this.vendorShipDate = vendorShipDate;
	}
}
