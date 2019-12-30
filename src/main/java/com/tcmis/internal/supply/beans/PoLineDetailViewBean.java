package com.tcmis.internal.supply.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PoLineDetailViewBean <br>
 * @version: 1.0, Aug 7, 2008 <br>
 *****************************************************************************/


public class PoLineDetailViewBean extends BaseDataBean {

	private BigDecimal radianPo;
	private BigDecimal poLine;
	private BigDecimal amendment;
	private BigDecimal itemId;
	private BigDecimal quantity;
	private BigDecimal unitPrice;
	private String currencyDisplay;
	private String currencyId;
	private BigDecimal extendedPrice;
	private Date needDate;
	private Date promisedDate;
	private BigDecimal allowedPriceVariance;
	private String mfgPartNo;
	private String supplierPartNo;
	private String dpasRating;
	private String qualityFlowDowns;
	private String packagingFlowDowns;
	private String poLineNote;
	private String itemDesc;
	private String itemType;
	private String secondarySupplierOnPo;
	private String packaging;
	private String poLineStatus;
	private BigDecimal quantityReceived;
	private BigDecimal quantityVouchered;
	private BigDecimal quantityReturned;
	private BigDecimal supplierQty;
	private String supplierPkg;
	private BigDecimal supplierUnitPrice;
	private String everConfirmed;
	private String msdsRequestedDate;
	private String prShipTo;
	private String prCompanyId;
	private String genericCoc;
	private String genericCoa;
	private BigDecimal remainingShelfLifePercent;
	private Date dateConfirmed;
	private String deliveryComments;
	private String archived;
	private Date vendorShipDate;
	private String itemVerifiedBy;
	private Date dateItemVerified;
	private String dbuyLockStatus;
	private BigDecimal maxPriceLimit;
	private Date maxVendorShipDate;
	private String nboCompanyId;
	private String supplier;
	private String secondarySupplierAddress;
	private String differentSupplierOnLine;
	private BigDecimal purchasingUnitsPerItem;
	private String purchasingUnitOfMeasure;
	private String buyerCompanyId;
	private String changeSupplier;
	private Date lastConfirmed;
	private String shipFromLocationId;
	private String shelfLifeBasis;
	private String shelfLifeDays;
	private BigDecimal dbuyContractPrice;
	private String expediteComments;
	private String currentLineStatus;
	private String expediteNoteChangeStatus;
	private String lineChangeStatus;
	private String ammendmentcomments;
	private String customerPo;
	private String supplierSalesOrderNo;
	private String oldExpediteComments;
	private Date oldVvendorShipDate;
	private Date oldPromisedDate;
	private BigDecimal oldQuantity;
	private BigDecimal oldUnitPrice;
	private String shipFromLocationName;
	private Date expediteDate;
	private String comments;
	private Date transactionDate;
	private String transDate;
	private String fullName;
	private String inventoryGroup;
	private String companyId;
	private String catalogId;
	private String catPartNo;
	private String catPartNoComment;

	private String lineAmendmentNum;
	private String status;
	private int poLineNumber;
	private String isMaterialTypeItem;
	private String shelfLifeBasisMsg;
	private String validItem;
	private int seqNumber;
	private String relatedPoLines;
	private boolean blnPoLineReadOnly;
	private String lineArchived;
	private String canAssignAddCharge;
	private BigDecimal lineTotalPrice;
	private BigDecimal supplierExtPrice;
	
	private Collection flowdownBeanCol;
	private Collection specBeanCol;
	private Collection lookAheadBeanCol;
	private Collection chargeListBeanCol;
	
	private String uom;
	private String unitPriceDisp;
	private String extendedPriceDisp;
	private String quantityVoc;
	private String itemDescription;
	private String type;
	private Date projectedDate;
	private String supplierPn;
	private String shelfLife;
	private String lineNotes;
	private String currency;
	private String itemUnitPrice;
	private String lineChange;
	private String dpas;

	private String flowdownChanged;
	private String specChanged;
	private String lookAheadChanged;
	private String chargeListChanged;
	private BigDecimal currencyConversionRate;
	
	private boolean poLineClosed;
	private boolean itemIdEditable;
	
	private String ghsCompliant;
	private BigDecimal oldAmendment;
		
  //constructor
	public PoLineDetailViewBean() {
	}

	//setters
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public void setAmendment(BigDecimal amendment) {
		this.amendment = amendment;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setCurrencyDisplay(String currencyDisplay) {
		this.currencyDisplay = currencyDisplay;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setExtendedPrice(BigDecimal extendedPrice) {
		this.extendedPrice = extendedPrice;
	}
	public void setNeedDate(Date needDate) {
		this.needDate = needDate;
	}
	public void setPromisedDate(Date promisedDate) {
		this.promisedDate = promisedDate;
	}
	public void setAllowedPriceVariance(BigDecimal allowedPriceVariance) {
		this.allowedPriceVariance = allowedPriceVariance;
	}
	public void setMfgPartNo(String mfgPartNo) {
		this.mfgPartNo = mfgPartNo;
	}
	public void setSupplierPartNo(String supplierPartNo) {
		this.supplierPartNo = supplierPartNo;
	}
	public void setDpasRating(String dpasRating) {
		this.dpasRating = dpasRating;
	}
	public void setQualityFlowDowns(String qualityFlowDowns) {
		this.qualityFlowDowns = qualityFlowDowns;
	}
	public void setPackagingFlowDowns(String packagingFlowDowns) {
		this.packagingFlowDowns = packagingFlowDowns;
	}
	public void setPoLineNote(String poLineNote) {
		this.poLineNote = poLineNote;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public void setSecondarySupplierOnPo(String secondarySupplierOnPo) {
		this.secondarySupplierOnPo = secondarySupplierOnPo;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setPoLineStatus(String poLineStatus) {
		this.poLineStatus = poLineStatus;
	}
	public void setQuantityReceived(BigDecimal quantityReceived) {
		this.quantityReceived = quantityReceived;
	}
	public void setQuantityVouchered(BigDecimal quantityVouchered) {
		this.quantityVouchered = quantityVouchered;
	}
	public void setQuantityReturned(BigDecimal quantityReturned) {
		this.quantityReturned = quantityReturned;
	}
	public void setSupplierQty(BigDecimal supplierQty) {
		this.supplierQty = supplierQty;
	}
	public void setSupplierPkg(String supplierPkg) {
		this.supplierPkg = supplierPkg;
	}
	public void setSupplierUnitPrice(BigDecimal supplierUnitPrice) {
		this.supplierUnitPrice = supplierUnitPrice;
	}
	public void setEverConfirmed(String everConfirmed) {
		this.everConfirmed = everConfirmed;
	}
	public void setMsdsRequestedDate(String msdsRequestedDate) {
		this.msdsRequestedDate = msdsRequestedDate;
	}
	public void setPrShipTo(String prShipTo) {
		this.prShipTo = prShipTo;
	}
	public void setPrCompanyId(String prCompanyId) {
		this.prCompanyId = prCompanyId;
	}
	public void setGenericCoc(String genericCoc) {
		this.genericCoc = genericCoc;
	}
	public void setGenericCoa(String genericCoa) {
		this.genericCoa = genericCoa;
	}
	public void setRemainingShelfLifePercent(BigDecimal remainingShelfLifePercent) {
		this.remainingShelfLifePercent = remainingShelfLifePercent;
	}
	public void setDateConfirmed(Date dateConfirmed) {
		this.dateConfirmed = dateConfirmed;
	}
	public void setDeliveryComments(String deliveryComments) {
		this.deliveryComments = deliveryComments;
	}
	public void setArchived(String archived) {
		this.archived = archived;
	}
	public void setVendorShipDate(Date vendorShipDate) {
		this.vendorShipDate = vendorShipDate;
	}
	public void setItemVerifiedBy(String itemVerifiedBy) {
		this.itemVerifiedBy = itemVerifiedBy;
	}
	public void setDateItemVerified(Date dateItemVerified) {
		this.dateItemVerified = dateItemVerified;
	}
	public void setDbuyLockStatus(String dbuyLockStatus) {
		this.dbuyLockStatus = dbuyLockStatus;
	}
	public void setMaxPriceLimit(BigDecimal maxPriceLimit) {
		this.maxPriceLimit = maxPriceLimit;
	}
	public void setMaxVendorShipDate(Date maxVendorShipDate) {
		this.maxVendorShipDate = maxVendorShipDate;
	}
	public void setNboCompanyId(String nboCompanyId) {
		this.nboCompanyId = nboCompanyId;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setSecondarySupplierAddress(String secondarySupplierAddress) {
		this.secondarySupplierAddress = secondarySupplierAddress;
	}
	public void setDifferentSupplierOnLine(String differentSupplierOnLine) {
		this.differentSupplierOnLine = differentSupplierOnLine;
	}
	public void setPurchasingUnitsPerItem(BigDecimal purchasingUnitsPerItem) {
		this.purchasingUnitsPerItem = purchasingUnitsPerItem;
	}
	public void setPurchasingUnitOfMeasure(String purchasingUnitOfMeasure) {
		this.purchasingUnitOfMeasure = purchasingUnitOfMeasure;
	}
	public void setBuyerCompanyId(String buyerCompanyId) {
		this.buyerCompanyId = buyerCompanyId;
	}
	public void setChangeSupplier(String changeSupplier) {
		this.changeSupplier = changeSupplier;
	}
	public void setLastConfirmed(Date lastConfirmed) {
		this.lastConfirmed = lastConfirmed;
	}
	public void setShipFromLocationId(String shipFromLocationId) {
		this.shipFromLocationId = shipFromLocationId;
	}
	public void setShelfLifeBasis(String shelfLifeBasis) {
		this.shelfLifeBasis = shelfLifeBasis;
	}
	public void setShelfLifeDays(String shelfLifeDays) {
		this.shelfLifeDays = shelfLifeDays;
	}
	public void setDbuyContractPrice(BigDecimal dbuyContractPrice) {
		this.dbuyContractPrice = dbuyContractPrice;
	}
	public void setExpediteComments(String expediteComments) {
		this.expediteComments = expediteComments;
	}
	 public void setCurrentLineStatus(String currentLineStatus) {
		this.currentLineStatus = currentLineStatus;
	}
  public void setLineChangeStatus(String lineChangeStatus) {
	 this.lineChangeStatus = lineChangeStatus;
	}
	public void setCustomerPo(String customerPo) {
	 this.customerPo = customerPo;
	}
	public void setSupplierSalesOrderNo(String supplierSalesOrderNo) {
		this.supplierSalesOrderNo = supplierSalesOrderNo;
	}
  public void setOldExpediteComments(String oldExpediteComments) {
    this.oldExpediteComments = oldExpediteComments;
  }

  public void setOldVvendorShipDate(Date oldVvendorShipDate) {
    this.oldVvendorShipDate = oldVvendorShipDate;
  }

  public void setOldPromisedDate(Date oldPromisedDate) {
    this.oldPromisedDate = oldPromisedDate;
  }

  public void setOldQuantity(BigDecimal oldQuantity) {
    this.oldQuantity = oldQuantity;
  }

  public void setOldUnitPrice(BigDecimal oldUnitPrice) {
    this.oldUnitPrice = oldUnitPrice;
  }

  public void setExpediteNoteChangeStatus(String expediteNoteChangeStatus) {
    this.expediteNoteChangeStatus = expediteNoteChangeStatus;
  }

  public void setShipFromLocationName(String shipFromLocationName) {
		this.shipFromLocationName = shipFromLocationName;
	}
  
  public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
  public void setCompanyId(String companyId)
  {
	  this.companyId = companyId;
  }
  public void setCatalogId(String catalogId)
  {
	  this.catalogId = catalogId;
  }
  public void setCatPartNo(String catPartNo)
  {
	  this.catPartNo = catPartNo;
  }
  public void setCatPartNoComment(String catPartNoComment)
  {
	  this.catPartNoComment = catPartNoComment;
  }
	 
  //getters  
  public String getCompanyId()
  {
	  return companyId;
  }
  public String getCatalogId()
  {
	  return catalogId;
  }
  public String getCatPartNo()
  {
	  return catPartNo;
  }
  public String getCatPartNoComment()
  {
	  return catPartNoComment;
  }
  public String getInventoryGroup()
  {
	  return inventoryGroup;
  }
  
	public String getComments() {
		return comments;
	}  
	public Date getTransactionDate() {
		return transactionDate;
	}  
	public String getTransDate() {
		return transDate;
	}  
	public String getFullName() {
		return fullName;
	}  
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public BigDecimal getAmendment() {
		return amendment;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public String getCurrencyDisplay() {
		return currencyDisplay;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public BigDecimal getExtendedPrice() {
		return extendedPrice;
	}
	public Date getNeedDate() {
		return needDate;
	}
	public Date getPromisedDate() {
		return promisedDate;
	}
	public BigDecimal getAllowedPriceVariance() {
		return allowedPriceVariance;
	}
	public String getMfgPartNo() {
		return mfgPartNo;
	}
	public String getSupplierPartNo() {
		return supplierPartNo;
	}
	public String getDpasRating() {
		return dpasRating;
	}
	public String getQualityFlowDowns() {
		return qualityFlowDowns;
	}
	public String getPackagingFlowDowns() {
		return packagingFlowDowns;
	}
	public String getPoLineNote() {
		return poLineNote;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public String getItemType() {
		return itemType;
	}
	public String getSecondarySupplierOnPo() {
		return secondarySupplierOnPo;
	}
	public String getPackaging() {
		return packaging;
	}
	public String getPoLineStatus() {
		return poLineStatus;
	}
	public BigDecimal getQuantityReceived() {
		return quantityReceived;
	}
	public BigDecimal getQuantityVouchered() {
		return quantityVouchered;
	}
	public BigDecimal getQuantityReturned() {
		return quantityReturned;
	}
	public BigDecimal getSupplierQty() {
		return supplierQty;
	}
	public String getSupplierPkg() {
		return supplierPkg;
	}
	public BigDecimal getSupplierUnitPrice() {
		return supplierUnitPrice;
	}
	public String getEverConfirmed() {
		return everConfirmed;
	}
	public String getMsdsRequestedDate() {
		return msdsRequestedDate;
	}
	public String getPrShipTo() {
		return prShipTo;
	}
	public String getPrCompanyId() {
		return prCompanyId;
	}
	public String getGenericCoc() {
		return genericCoc;
	}
	public String getGenericCoa() {
		return genericCoa;
	}
	public BigDecimal getRemainingShelfLifePercent() {
		return remainingShelfLifePercent;
	}
	public Date getDateConfirmed() {
		return dateConfirmed;
	}
	public String getDeliveryComments() {
		return deliveryComments;
	}
	public String getArchived() {
		return archived;
	}
	public Date getVendorShipDate() {
		return vendorShipDate;
	}
	public String getItemVerifiedBy() {
		return itemVerifiedBy;
	}
	public Date getDateItemVerified() {
		return dateItemVerified;
	}
	public String getDbuyLockStatus() {
		return dbuyLockStatus;
	}
	public BigDecimal getMaxPriceLimit() {
		return maxPriceLimit;
	}
	public Date getMaxVendorShipDate() {
		return maxVendorShipDate;
	}
	public String getNboCompanyId() {
		return nboCompanyId;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getSecondarySupplierAddress() {
		return secondarySupplierAddress;
	}
	public String getDifferentSupplierOnLine() {
		return differentSupplierOnLine;
	}
	public BigDecimal getPurchasingUnitsPerItem() {
		return purchasingUnitsPerItem;
	}
	public String getPurchasingUnitOfMeasure() {
		return purchasingUnitOfMeasure;
	}
	public String getBuyerCompanyId() {
		return buyerCompanyId;
	}
	public String getChangeSupplier() {
		return changeSupplier;
	}
	public Date getLastConfirmed() {
		return lastConfirmed;
	}
	public String getShipFromLocationId() {
		return shipFromLocationId;
	}
	public String getShelfLifeBasis() {
		return shelfLifeBasis;
	}
	public String getShelfLifeDays() {
		return shelfLifeDays;
	}
	public BigDecimal getDbuyContractPrice() {
		return dbuyContractPrice;
	}
	public String getExpediteComments() {
		return expediteComments;
	}
	public String getCurrentLineStatus() {
		return currentLineStatus;
	}
	
	public String getLineChangeStatus() {
		return lineChangeStatus;
	}

	public String getCustomerPo() {
	    return customerPo;
	}
	
	public String getSupplierSalesOrderNo() {
		return supplierSalesOrderNo;
	}
	
	public String getOldExpediteComments() {
	    return oldExpediteComments;
	}
	
	
	public Date getOldVvendorShipDate() {
	    return oldVvendorShipDate;
	}	
	
	public Date getOldPromisedDate() {
	    return oldPromisedDate;
	}

	public BigDecimal getOldQuantity() {
		return oldQuantity;
	}
	
	public BigDecimal getOldUnitPrice() {
		return oldUnitPrice;
	}
	
	public String getExpediteNoteChangeStatus() {
	    return expediteNoteChangeStatus;
	}
	
	public String getShipFromLocationName() {
		return shipFromLocationName;
	}
	
	public Date getExpediteDate() {
		return expediteDate;
	}
		
	public void setExpediteDate(Date expediteDate) {
	
		this.expediteDate = expediteDate;
	}
		
	public String getLineAmendmentNum() {
		return lineAmendmentNum;
	}
		
	public void setLineAmendmentNum(String lineAmendmentNum) {
		this.lineAmendmentNum = lineAmendmentNum;
	}
		
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getPoLineNumber() {
		return poLineNumber;
	}
		
	public void setPoLineNumber(int poLineNumber) {
		this.poLineNumber = poLineNumber;
	}

	public String getIsMaterialTypeItem() {
		return isMaterialTypeItem;
	}

	public void setIsMaterialTypeItem(String isMaterialTypeItem) {
		this.isMaterialTypeItem = isMaterialTypeItem;
	}

	public String getShelfLifeBasisMsg() {
		return shelfLifeBasisMsg;
	}

	public void setShelfLifeBasisMsg(String shelfLifeBasisMsg) {
		this.shelfLifeBasisMsg = shelfLifeBasisMsg;
	}

	public String getValidItem() {
		return validItem;
	}

	public void setValidItem(String validItem) {
		this.validItem = validItem;
	}

	public int getSeqNumber() {
		return seqNumber;
	}

	public void setSeqNumber(int seqNumber) {
		this.seqNumber = seqNumber;
	}

	public String getRelatedPoLines() {
		return relatedPoLines;
	}

	public void setRelatedPoLines(String relatedPoLines) {
		this.relatedPoLines = relatedPoLines;
	}

	public boolean isBlnPoLineReadOnly() {
		return blnPoLineReadOnly;
	}

	public void setBlnPoLineReadOnly(boolean blnPoLineReadOnly) {
		this.blnPoLineReadOnly = blnPoLineReadOnly;
	}

	public String getLineArchived() {
		return lineArchived;
	}

	public void setLineArchived(String lineArchived) {
		this.lineArchived = lineArchived;
	}

	public String getCanAssignAddCharge() {
		return canAssignAddCharge;
	}

	public void setCanAssignAddCharge(String canAssignAddCharge) {
		this.canAssignAddCharge = canAssignAddCharge;
	}

	public BigDecimal getLineTotalPrice() {
		return lineTotalPrice;
	}

	public void setLineTotalPrice(BigDecimal lineTotalPrice) {
		this.lineTotalPrice = lineTotalPrice;
	}

	public BigDecimal getSupplierExtPrice() {
		return supplierExtPrice;
	}

	public void setSupplierExtPrice(BigDecimal supplierExtPrice) {
		this.supplierExtPrice = supplierExtPrice;
	}

	public Collection getFlowdownBeanCol() {
		return flowdownBeanCol;
	}

	public void setFlowdownBeanCol(Collection flowdownBeanCol) {
		this.flowdownBeanCol = flowdownBeanCol;
	}

	public Collection getSpecBeanCol() {
		return specBeanCol;
	}

	public void setSpecBeanCol(Collection specBeanCol) {
		this.specBeanCol = specBeanCol;
	}

	public Collection getLookAheadBeanCol() {
		return lookAheadBeanCol;
	}

	public void setLookAheadBeanCol(Collection lookAheadBeanCol) {
		this.lookAheadBeanCol = lookAheadBeanCol;
	}

	public Collection getChargeListBeanCol() {
		return chargeListBeanCol;
	}

	public void setChargeListBeanCol(Collection chargeListBeanCol) {
		this.chargeListBeanCol = chargeListBeanCol;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getUnitPriceDisp() {
		return unitPriceDisp;
	}

	public void setUnitPriceDisp(String unitPriceDisp) {
		this.unitPriceDisp = unitPriceDisp;
	}

	public String getExtendedPriceDisp() {
		return extendedPriceDisp;
	}

	public void setExtendedPriceDisp(String extendedPriceDisp) {
		this.extendedPriceDisp = extendedPriceDisp;
	}

	public String getQuantityVoc() {
		return quantityVoc;
	}

	public void setQuantityVoc(String quantityVoc) {
		this.quantityVoc = quantityVoc;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getProjectedDate() {
		return projectedDate;
	}

	public void setProjectedDate(Date projectedDate) {
		this.projectedDate = projectedDate;
	}

	public String getSupplierPn() {
		return supplierPn;
	}

	public void setSupplierPn(String supplierPn) {
		this.supplierPn = supplierPn;
	}

	public String getShelfLife() {
		return shelfLife;
	}

	public void setShelfLife(String shelfLife) {
		this.shelfLife = shelfLife;
	}

	public String getLineNotes() {
		return lineNotes;
	}

	public void setLineNotes(String lineNotes) {
		this.lineNotes = lineNotes;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getItemUnitPrice() {
		return itemUnitPrice;
	}

	public void setItemUnitPrice(String itemUnitPrice) {
		this.itemUnitPrice = itemUnitPrice;
	}

	public String getLineChange() {
		return lineChange;
	}

	public void setLineChange(String lineChange) {
		this.lineChange = lineChange;
	}

	public String getAmmendmentcomments() {
		return ammendmentcomments;
	}

	public void setAmmendmentcomments(String ammendmentcomments) {
		this.ammendmentcomments = ammendmentcomments;
	}

	public String getFlowdownChanged() {
		return flowdownChanged;
	}

	public void setFlowdownChanged(String flowdownChanged) {
		this.flowdownChanged = flowdownChanged;
	}

	public String getSpecChanged() {
		return specChanged;
	}

	public void setSpecChanged(String specChanged) {
		this.specChanged = specChanged;
	}

	public String getLookAheadChanged() {
		return lookAheadChanged;
	}

	public void setLookAheadChanged(String lookAheadChanged) {
		this.lookAheadChanged = lookAheadChanged;
	}

	public String getChargeListChanged() {
		return chargeListChanged;
	}

	public void setChargeListChanged(String chargeListChanged) {
		this.chargeListChanged = chargeListChanged;
	}

	public String getDpas() {
		return dpas;
	}

	public void setDpas(String dpas) {
		this.dpas = dpas;
	}

	public BigDecimal getCurrencyConversionRate() {
		return currencyConversionRate;
	}

	public void setCurrencyConversionRate(BigDecimal currencyConversionRate) {
		this.currencyConversionRate = currencyConversionRate;
	}

	public boolean getPoLineClosed() {
		return poLineClosed;
	}

	public void setPoLineClosed(boolean poLineClosed) {
		this.poLineClosed = poLineClosed;
	}

	public boolean getItemIdEditable() {
		return itemIdEditable;
	}

	public void setItemIdEditable(boolean itemIdEditable) {
		this.itemIdEditable = itemIdEditable;
	}

	public String getGhsCompliant() {
		return ghsCompliant;
	}

	public void setGhsCompliant(String ghsCompliant) {
		this.ghsCompliant = ghsCompliant;
	}

	public BigDecimal getOldAmendment() {
		return oldAmendment;
	}

	public void setOldAmendment(BigDecimal oldAmendment) {
		this.oldAmendment = oldAmendment;
	}


}