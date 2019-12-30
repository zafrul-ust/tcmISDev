package com.tcmis.supplier.shipping.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SupplierUsgovLabelViewBean <br>
 * @version: 1.0, Dec 1, 2007 <br>
 *****************************************************************************/


public class SupplierUsgovLabelViewBean extends BaseDataBean {

	private String boxLabelId;
	private String companyId;
	private String supplier;
	private String shipFromLocationId;
	private BigDecimal issueId;
	private BigDecimal prNumber;
	private String lineItem;
	private BigDecimal picklistId;
	private String nsn;
	private String customerPo;
	private String cageCode;
	private String formattedNsn;
	private String pidPartNo;
	private String formattedCustomerPo;
	private BigDecimal quantity;
	private String quantityInUnitOfSale;
	private String grossWeight;
	private String dateDelivered;
	private String dateShipped;
	private String unNumberAndProperShipName;
	private BigDecimal radianPo;
	private BigDecimal poLine;
	private String dateOfManufacture;
	private String expireDate;
	private String partShortName;
	private String inventoryGroup;
	private BigDecimal receiptId;
	private String shippedAsSingle;
	private String requiresOverpack;
  private String skipUnitLabels;
  private String quantityInUnitOfIssue;
	private String unitOfIssue;
	private String originInspectionRequired;
	private String mhmDate;
	private String mfgLot;
	private String unitOfSale;
	private BigDecimal numOfSerialNumber;
	private String serialNo1;
	private String serialNo2;
	private String serialNo3;
	private String serialNo4;
	private String serialNo5;
	private String serialNoComment;
	private String serialNumber;
	private String palletId;
	private String boxId;


  //constructor
	public SupplierUsgovLabelViewBean() {
	}

	//setters
	public void setBoxLabelId(String boxLabelId) {
		this.boxLabelId = boxLabelId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setShipFromLocationId(String shipFromLocationId) {
		this.shipFromLocationId = shipFromLocationId;
	}
	public void setIssueId(BigDecimal issueId) {
		this.issueId = issueId;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setPicklistId(BigDecimal picklistId) {
		this.picklistId = picklistId;
	}
	public void setNsn(String nsn) {
		this.nsn = nsn;
	}
	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}
	public void setCageCode(String cageCode) {
		this.cageCode = cageCode;
	}
	public void setFormattedNsn(String formattedNsn) {
		this.formattedNsn = formattedNsn;
	}
	public void setPidPartNo(String pidPartNo) {
		this.pidPartNo = pidPartNo;
	}
	public void setFormattedCustomerPo(String formattedCustomerPo) {
		this.formattedCustomerPo = formattedCustomerPo;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setQuantityInUnitOfSale(String quantityInUnitOfSale) {
		this.quantityInUnitOfSale = quantityInUnitOfSale;
	}
	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}
	public void setDateDelivered(String dateDelivered) {
		this.dateDelivered = dateDelivered;
	}
	public void setDateShipped(String dateShipped) {
		this.dateShipped = dateShipped;
	}
	public void setUnNumberAndProperShipName(String unNumberAndProperShipName) {
		this.unNumberAndProperShipName = unNumberAndProperShipName;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public void setDateOfManufacture(String dateOfManufacture) {
		this.dateOfManufacture = dateOfManufacture;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public void setPartShortName(String partShortName) {
		this.partShortName = partShortName;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setShippedAsSingle(String shippedAsSingle) {
		this.shippedAsSingle = shippedAsSingle;
	}
	public void setRequiresOverpack(String requiresOverpack) {
		this.requiresOverpack = requiresOverpack;
	}
  public void setSkipUnitLabels(String skipUnitLabels) {
    this.skipUnitLabels = skipUnitLabels;
  }
	public void setQuantityInUnitOfIssue(String quantityInUnitOfIssue) {
		this.quantityInUnitOfIssue = quantityInUnitOfIssue;
	}
	public void setUnitOfIssue(String unitOfIssue) {
		this.unitOfIssue = unitOfIssue;
	}
	public void setOriginInspectionRequired(String originInspectionRequired) {
		this.originInspectionRequired = originInspectionRequired;
	}
	public void setMhmDate(String mhmDate) {
		this.mhmDate = mhmDate;
	}
	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}
	public void setUnitOfSale(String unitOfSale) {
		this.unitOfSale = unitOfSale;
	}		
	public void setNumOfSerialNumber(BigDecimal numOfSerialNumber) {
		this.numOfSerialNumber = numOfSerialNumber;
	}	
	public void setSerialNo1(String serialNo1) {
		this.serialNo1 = serialNo1;
	}	
	public void setSerialNo2(String serialNo2) {
		this.serialNo2 = serialNo2;
	}	
	public void setSerialNo3(String serialNo3) {
		this.serialNo3 = serialNo3;
	}	
	public void setSerialNo4(String serialNo4) {
		this.serialNo4 = serialNo4;
	}	
	public void setSerialNo5(String serialNo5) {
		this.serialNo5 = serialNo5;
	}	
	public void setSerialNoComment(String serialNoComment) {
		this.serialNoComment = serialNoComment;
	}	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}	
	public void setPalletId(String palletId) {
		this.palletId = palletId;
	}
	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}	
	public String getBoxId() {
		return boxId;
	}
	public String getPalletId() {
		return palletId;
	}	
	public String getSerialNumber() {
		return serialNumber;
	}
	public String getSerialNo1() {
		return serialNo1;
	}
	public String getSerialNo2() {
		return serialNo2;
	}
	public String getSerialNo3() {
		return serialNo3;
	}
	public String getSerialNo4() {
		return serialNo4;
	}
	public String getSerialNo5() {
		return serialNo5;
	}
	public String getSerialNoComment() {
		return serialNoComment;
	}
	public BigDecimal getNumOfSerialNumber() {
		return numOfSerialNumber;
	}
	public String getBoxLabelId() {
		return boxLabelId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getShipFromLocationId() {
		return shipFromLocationId;
	}
	public BigDecimal getIssueId() {
		return issueId;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public BigDecimal getPicklistId() {
		return picklistId;
	}
	public String getNsn() {
		return nsn;
	}
	public String getCustomerPo() {
		return customerPo;
	}
	public String getCageCode() {
		return cageCode;
	}
	public String getFormattedNsn() {
		return formattedNsn;
	}
	public String getPidPartNo() {
		return pidPartNo;
	}
	public String getFormattedCustomerPo() {
		return formattedCustomerPo;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public String getQuantityInUnitOfSale() {
		return quantityInUnitOfSale;
	}
	public String getGrossWeight() {
		return grossWeight;
	}
	public String getDateDelivered() {
		return dateDelivered;
	}
	public String getDateShipped() {
		return dateShipped;
	}
	public String getUnNumberAndProperShipName() {
		return unNumberAndProperShipName;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public String getDateOfManufacture() {
		return dateOfManufacture;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public String getPartShortName() {
		return partShortName;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public String getShippedAsSingle() {
		return shippedAsSingle;
	}
	public String getRequiresOverpack() {
		return requiresOverpack;
	}
  public String getSkipUnitLabels() {
    return skipUnitLabels;
  }
	public String getQuantityInUnitOfIssue() {
		return quantityInUnitOfIssue;
	}
	public String getUnitOfIssue() {
		return unitOfIssue;
	}
	public String getOriginInspectionRequired() {
		return originInspectionRequired;
	}
	public String getMhmDate() {
		return mhmDate;
	}
	public String getMfgLot() {
		return mfgLot;
	}
	public String getUnitOfSale() {
		return unitOfSale;
	}  
}