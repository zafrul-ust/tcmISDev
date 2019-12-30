package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ReceivingViewBean <br>
 * @version: 1.0, Jun 9, 2004 <br>
 *****************************************************************************/

public class ReceivingKitBean
	extends BaseDataBean {

/*  private int radianPo;
  private BigDecimal lineItem;
  private BigDecimal qtyOpen;*/
  private BigDecimal itemId;
  /*private Date expected;
  private BigDecimal qty;
  private String branchPlant;
  private String itemDescription;
  private String poNotes;
  private String transType;
  private BigDecimal transferRequestId;
  private String lastSupplier;
  private String indefiniteShelfLife;
  private String critical;*/
  private BigDecimal originalReceiptId;
  private String originalMfgLot;
  private String mfgLot;
  //private String inventoryGroup;
  private String manageKitsAsSingleUnit;
  private BigDecimal componentId;
  /*private BigDecimal materialId;*/
  private String packaging;
  private String materialDesc;
  /*private String inventoryGroupName;
  private BigDecimal numberOfKits;*/
  private String bin;
  //private Collection receiptItemPriorBinViewBeanCollection;
  private String ok;
  private Date supplierShipDate;
  private Date dateOfReceipt;
  private Date dom;
  private Date dos;
  private Date expirationDate;
  private BigDecimal quantityReceived;
  private String receiptNotes;
  private String skipReceivingQc;
  private String lotStatus;
  private BigDecimal receivedReceipt1;
  private BigDecimal receivedReceipt2;
  /*private String updateStatus;
  private String errorMessage;
  private Collection kitCollection;
  private BigDecimal rowSpan;*/
  private String deliveryTicket;
  private String packagedQty;
  private String packagedSize;
  private BigDecimal transferReceiptId;
  //private BigDecimal pendingReceiptId;
	private String updateStatus;
  private String errorMessage;
  private String rowNumber;
	private String orderQtyUpdateOnReceipt;
	private String closePoLine;
	private String mvItem;
	private BigDecimal purchasingUnitsPerItem;
	private String purchasingUnitOfMeasure;
  private BigDecimal receivingPagePackagedSize;
  private BigDecimal mrNumber;
  private String mrLineItem;
  
  private BigDecimal qaStatement;
  private BigDecimal originalQty;
  private BigDecimal intercompanyPo;
  private BigDecimal intercompanyPoLine;
  
  private String countryAbbrev;
  
  private String definedShelfLifeItem;
  private String definedShelfLifeBasis;
  private Date dateOfRepackaging;

  public BigDecimal getIntercompanyPo() {
        return intercompanyPo;
    }

    public void setIntercompanyPo(BigDecimal intercompanyPo) {
        this.intercompanyPo = intercompanyPo;
    }

    public BigDecimal getIntercompanyPoLine() {
        return intercompanyPoLine;
    }

    public void setIntercompanyPoLine(BigDecimal intercompanyPoLine) {
        this.intercompanyPoLine = intercompanyPoLine;
    }

  public BigDecimal getOriginalQty() {
	return originalQty;
}

public void setOriginalQty(BigDecimal originalQty) {
	this.originalQty = originalQty;
}

//constructor
  public ReceivingKitBean() {
  }

  //setters
  /*public void setRadianPo(BigDecimal radianPo) {
	this.radianPo = radianPo;
  }

  public void setLineItem(BigDecimal lineItem) {
	this.lineItem = lineItem;
  }

  public void setQtyOpen(BigDecimal qtyOpen) {
	this.qtyOpen = qtyOpen;
  }*/

  public void setItemId(BigDecimal itemId) {
	this.itemId = itemId;
  }

  /*public void setExpected(Date expected) {
	this.expected = expected;
  }

  public void setQty(BigDecimal qty) {
	this.qty = qty;
  }

  public void setBranchPlant(String branchPlant) {
	this.branchPlant = branchPlant;
  }

  public void setItemDescription(String itemDescription) {
	this.itemDescription = itemDescription;
  }

  public void setPoNotes(String poNotes) {
	this.poNotes = poNotes;
  }

  public void setTransType(String transType) {
	this.transType = transType;
  }

  public void setTransferRequestId(BigDecimal transferRequestId) {
	this.transferRequestId = transferRequestId;
  }

  public void setLastSupplier(String lastSupplier) {
	this.lastSupplier = lastSupplier;
  }

  public void setIndefiniteShelfLife(String indefiniteShelfLife) {
	this.indefiniteShelfLife = indefiniteShelfLife;
  }

  public void setCritical(String critical) {
	this.critical = critical;
  }*/

  public void setOriginalReceiptId(BigDecimal originalReceiptId) {
	this.originalReceiptId = originalReceiptId;
  }

  public void setOriginalMfgLot(String originalMfgLot) {
	this.originalMfgLot = originalMfgLot;
  }

  public void setMfgLot(String mfgLot) {
	 this.mfgLot = mfgLot;
   }

  /*public void setInventoryGroup(String inventoryGroup) {
	this.inventoryGroup = inventoryGroup;
  }*/

  public void setManageKitsAsSingleUnit(String manageKitsAsSingleUnit) {
	this.manageKitsAsSingleUnit = manageKitsAsSingleUnit;
  }

  public void setComponentId(BigDecimal componentId) {
	this.componentId = componentId;
  }

  /*public void setMaterialId(BigDecimal materialId) {
	this.materialId = materialId;
  }*/

  public void setPackaging(String packaging) {
	this.packaging = packaging;
  }

  public void setMaterialDesc(String materialDesc) {
	this.materialDesc = materialDesc;
  }

  /*public void setInventoryGroupName(String inventoryGroupName) {
	this.inventoryGroupName = inventoryGroupName;
  }

  public void setNumberOfKits(BigDecimal numberOfKits) {
	this.numberOfKits = numberOfKits;
  }*/

  public void setBin(String bin) {
	this.bin = bin;
  }

  /*public void setReceiptItemPriorBinViewBeanCollection(Collection
	  receiptItemPriorBinViewBeanCollection) {
	this.receiptItemPriorBinViewBeanCollection =
		receiptItemPriorBinViewBeanCollection;
  }*/

  public void setOk(String ok) {
	this.ok = ok;
  }

  public void setSupplierShipDate(Date date) {
	this.supplierShipDate = date;
  }

  public void setDateOfReceipt(Date dateOfReceipt) {
	this.dateOfReceipt = dateOfReceipt;
  }

  public void setDom(Date dom) {
	this.dom = dom;
  }

  public void setDos(Date dos) {
	this.dos = dos;
  }

  public void setExpirationDate(Date date) {
	this.expirationDate = date;
  }

  public void setQuantityReceived(BigDecimal quantityReceived) {
	this.quantityReceived = quantityReceived;
  }

  public void setReceiptNotes(String receiptNotes) {
	this.receiptNotes = receiptNotes;
  }

  public void setSkipReceivingQc(String skipReceivingQc) {
	this.skipReceivingQc = skipReceivingQc;
  }

  public void setLotStatus(String lotStatus) {
	this.lotStatus = lotStatus;
  }

  public void setReceivedReceipt1(BigDecimal receivedReceipt1) {
	this.receivedReceipt1 = receivedReceipt1;
  }

  public void setReceivedReceipt2(BigDecimal receivedReceipt2) {
	this.receivedReceipt2 = receivedReceipt2;
  }

  /*public void setUpdateStatus(String updateStatus) {
	this.updateStatus = updateStatus;
  }

  public void setErrorMessage(String errorMessage) {
	this.errorMessage = errorMessage;
  }

  public void setKitCollection(Collection
								kitCollection) {
	this.kitCollection =
		kitCollection;
	}

  public void setRowSpan(BigDecimal rowSpan) {
	this.rowSpan = rowSpan;
  }*/

  public void setMvItem(String mvItem) {
	this.mvItem = mvItem;
  }
  public void setPurchasingUnitsPerItem(BigDecimal purchasingUnitsPerItem) {
	this.purchasingUnitsPerItem = purchasingUnitsPerItem;
  }
  public void setPurchasingUnitOfMeasure(String purchasingUnitOfMeasure) {
	this.purchasingUnitOfMeasure = purchasingUnitOfMeasure;
  }
  public void setDeliveryTicket(String deliveryTicket) {
	this.deliveryTicket = deliveryTicket;
  }
  public void setPackagedQty(java.lang.String packagedQty) {
	this.packagedQty = packagedQty;
  }
  public void setPackagedSize(java.lang.String packagedSize) {
	this.packagedSize = packagedSize;
  }
  public void setTransferReceiptId(BigDecimal transferReceiptId) {
	  this.transferReceiptId = transferReceiptId;
  }
  /*public void setPendingReceiptId(BigDecimal pendingReceiptId) {
	  this.pendingReceiptId = pendingReceiptId;
  }*/
	public void setUpdateStatus(String updateStatus) {
	 this.updateStatus = updateStatus;
	}
	public void setErrorMessage(String errorMessage) {
	 this.errorMessage = errorMessage;
	}
	public void setRowNumber(String rowNumber) {
	 this.rowNumber = rowNumber;
	}
	public void setOrderQtyUpdateOnReceipt(String orderQtyUpdateOnReceipt) {
	 this.orderQtyUpdateOnReceipt = orderQtyUpdateOnReceipt;
	}
	public void setClosePoLine(String closePoLine) {
	 this.closePoLine = closePoLine;
	}
  public void setReceivingPagePackagedSize(BigDecimal receivingPagePackagedSize) {
    this.receivingPagePackagedSize = receivingPagePackagedSize;
  }
  public void setMrNumber(BigDecimal mrNumber) {
    this.mrNumber = mrNumber;
  }
  public void setMrLineItem(String mrLineItem) {
    this.mrLineItem = mrLineItem;
  }

  public void setDateOfRepackaging(Date dateOfRepackaging) {
	    this.dateOfRepackaging = dateOfRepackaging;
	  }
	//getters
	public Date getDateOfRepackaging() {
	    return this.dateOfRepackaging;
	  }
 /* public BigDecimal getRadianPo() {
	return radianPo;
  }

  public BigDecimal getLineItem() {
	return lineItem;
  }

  public BigDecimal getQtyOpen() {
	return qtyOpen;
  }*/

  public BigDecimal getItemId() {
	return itemId;
  }

  /*public Date getExpected() {
	return expected;
  }

  public BigDecimal getQty() {
	return qty;
  }

  public String getBranchPlant() {
	return branchPlant;
  }

  public String getItemDescription() {
	return itemDescription;
  }

  public String getPoNotes() {
	return poNotes;
  }

  public String getTransType() {
	return transType;
  }

  public BigDecimal getTransferRequestId() {
	return transferRequestId;
  }

  public String getLastSupplier() {
	return lastSupplier;
  }

  public String getIndefiniteShelfLife() {
	return indefiniteShelfLife;
  }

  public String getCritical() {
	return critical;
  }*/

  public BigDecimal getOriginalReceiptId() {
	return originalReceiptId;
  }

  public String getOriginalMfgLot() {
	return originalMfgLot;
  }

  public String getMfgLot() {
	return mfgLot;
  }

 /* public String getInventoryGroup() {
	return inventoryGroup;
  }*/

  public String getManageKitsAsSingleUnit() {
	return manageKitsAsSingleUnit;
  }

  public BigDecimal getComponentId() {
	return componentId;
  }

  /*public BigDecimal getMaterialId() {
	return materialId;
  }*/

  public String getPackaging() {
	return packaging;
  }

  public String getMaterialDesc() {
	return materialDesc;
  }

 /* public String getInventoryGroupName() {
	return inventoryGroupName;
  }

  public BigDecimal getNumberOfKits() {
	return numberOfKits;
  }*/

  public String getBin() {
	return bin;
  }

 /* public Collection getReceiptItemPriorBinViewBeanCollection() {
	return receiptItemPriorBinViewBeanCollection;
  }*/

  public String getOk() {
	return ok;
  }

  public Date getSupplierShipDate() {
	return this.supplierShipDate;
  }

  public Date getDateOfReceipt() {
	return dateOfReceipt;
  }

  public Date getDom() {
	return dom;
  }

  public Date getDos() {
	return dos;
  }

  public Date getExpirationDate() {
	return this.expirationDate;
  }

  public BigDecimal getQuantityReceived() {
	return this.quantityReceived;
  }

  public String getReceiptNotes() {
  return receiptNotes;
  }

  public String getSkipReceivingQc() {
	return skipReceivingQc;
  }

  public String getLotStatus() {
	return lotStatus;
  }

  public BigDecimal getReceivedReceipt1() {
	return receivedReceipt1;
  }

  public BigDecimal getReceivedReceipt2() {
	return receivedReceipt2;
  }

  /*public String getUpdateStatus() {
	return updateStatus;
  }

  public String getErrorMessage() {
	return errorMessage;
	}

  public Collection getKitCollection() {
	return kitCollection;
  }

  public BigDecimal getRowSpan() {
	return rowSpan;
  }*/

 public String getMvItem() {
   return mvItem;
 }
 public BigDecimal getPurchasingUnitsPerItem() {
   return purchasingUnitsPerItem;
 }
 public String getPurchasingUnitOfMeasure() {
   return purchasingUnitOfMeasure;
 }
 public String getDeliveryTicket() {
  return deliveryTicket;
}
public java.lang.String getPackagedQty() {
  return packagedQty;
}
public java.lang.String getPackagedSize() {
  return packagedSize;
}
public BigDecimal getTransferReceiptId() {
	return transferReceiptId;
}
/*public BigDecimal getPendingReceiptId() {
	return pendingReceiptId;
}*/
 public String getUpdateStatus() {
	return updateStatus;
}
public String getErrorMessage() {
	return errorMessage;
}
public String getRowNumber() {
 return rowNumber;
}
public String getOrderQtyUpdateOnReceipt() {
 return orderQtyUpdateOnReceipt;
}
public String getClosePoLine() {
 return closePoLine;
}
public BigDecimal getReceivingPagePackagedSize() {
 return receivingPagePackagedSize;
}
public BigDecimal getMrNumber() {
  return mrNumber;
}
public String getMrLineItem() {
  return mrLineItem;
}

public BigDecimal getQaStatement() {
	return qaStatement;
}

public void setQaStatement(BigDecimal qaStatement) {
	this.qaStatement = qaStatement;
}

public String getCountryAbbrev() {
	return countryAbbrev;
}

public void setCountryAbbrev(String countryAbbrev) {
	this.countryAbbrev = countryAbbrev;
}

public String getDefinedShelfLifeItem() {
	return definedShelfLifeItem;
}

public void setDefinedShelfLifeItem(String definedShelfLifeItem) {
	this.definedShelfLifeItem = definedShelfLifeItem;
}

public String getDefinedShelfLifeBasis() {
	return definedShelfLifeBasis;
}

public void setDefinedShelfLifeBasis(String definedShelfLifeBasis) {
	this.definedShelfLifeBasis = definedShelfLifeBasis;
}

}
