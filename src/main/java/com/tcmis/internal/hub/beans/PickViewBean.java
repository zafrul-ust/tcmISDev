package com.tcmis.internal.hub.beans;

import java.util.Collection;
import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PickViewBean <br>
 * @version: 1.0, Mar 15, 2007 <br>
 *****************************************************************************/


public class PickViewBean extends BaseDataBean {

	private String hub;
	private BigDecimal prNumber;
	private String lineItem;
	private String mrLine;
	private BigDecimal receiptId;
	private BigDecimal itemId;
	private String bin;
	private String mfgLot;
	private Date expireDate;
	private BigDecimal totalQty;
	private BigDecimal pickQty;
	private String application;
	private String facilityId;
	private String itemType;
	private String partDescription;
	private String catPartDescription;
	private String packaging;
	private BigDecimal inventoryQuantity;
	private String catalogId;
	private String facPartNo;
	private BigDecimal partGroupNo;
	private String companyId;
	private String deliveryPoint;
	private String requestor;
	private BigDecimal transferRequestId;
	private String consignedFlag;
	private Date certificationDate;
	private String pickable;
	private BigDecimal certifiedBy;
	private String certificationNumber;
	private String qualityControlItem;
	private String inventoryGroup;
	private BigDecimal batchNumber;

        // for the form
        private BigDecimal quantityPicked;

  	// collections for relational views
  	private Collection items;
  	private Collection receipts;

  	private int itemRowspan;
  	private int receiptRowspan;

	//constructor
	public PickViewBean() {
	}

	//setters
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
  public void setBatchNumber(BigDecimal batchNumber) {
	 this.batchNumber = batchNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setMrLine(String mrLine) {
		this.mrLine = mrLine;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setBin(String bin) {
		this.bin = bin;
	}
	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public void setTotalQty(BigDecimal totalQty) {
		this.totalQty = totalQty;
	}
	public void setPickQty(BigDecimal pickQty) {
		this.pickQty = pickQty;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setCatPartDescription(String catPartDescription) {
		this.catPartDescription = catPartDescription;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setInventoryQuantity(BigDecimal inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}
	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setDeliveryPoint(String deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}
	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}
	public void setTransferRequestId(BigDecimal transferRequestId) {
		this.transferRequestId = transferRequestId;
	}
	public void setConsignedFlag(String consignedFlag) {
		this.consignedFlag = consignedFlag;
	}
	public void setCertificationDate(Date certificationDate) {
		this.certificationDate = certificationDate;
	}
	public void setPickable(String pickable) {
		this.pickable = pickable;
	}
	public void setCertifiedBy(BigDecimal certifiedBy) {
		this.certifiedBy = certifiedBy;
	}
	public void setCertificationNumber(String certificationNumber) {
		this.certificationNumber = certificationNumber;
	}
	public void setQualityControlItem(String qualityControlItem) {
		this.qualityControlItem = qualityControlItem;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}


	//getters
	public String getHub() {
		return hub;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
  public BigDecimal getBatchNumber() {
	 return batchNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getMrLine() {
		return mrLine;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getBin() {
		return bin;
	}
	public String getMfgLot() {
		return mfgLot;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public BigDecimal getTotalQty() {
		return totalQty;
	}
	public BigDecimal getPickQty() {
		return pickQty;
	}
	public String getApplication() {
		return application;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getItemType() {
		return itemType;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public String getCatPartDescription() {
		return catPartDescription;
	}
	public String getPackaging() {
		return packaging;
	}
	public BigDecimal getInventoryQuantity() {
		return inventoryQuantity;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getFacPartNo() {
		return facPartNo;
	}
	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getDeliveryPoint() {
		return deliveryPoint;
	}
	public String getRequestor() {
		return requestor;
	}
	public BigDecimal getTransferRequestId() {
		return transferRequestId;
	}
	public String getConsignedFlag() {
		return consignedFlag;
	}
	public Date getCertificationDate() {
		return certificationDate;
	}
	public String getPickable() {
		return pickable;
	}
	public BigDecimal getCertifiedBy() {
		return certifiedBy;
	}
	public String getCertificationNumber() {
		return certificationNumber;
	}
	public String getQualityControlItem() {
		return qualityControlItem;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}

        /**
         * Getter for property items.
         * @return Value of property items.
         */
        public java.util.Collection getItems() {
           return items;
        }

        /**
         * Setter for property items.
         * @param items New value of property items.
         */
        public void setItems(java.util.Collection items) {
           this.items = items;
        }

        /**
         * Getter for property receipts.
         * @return Value of property receipts.
         */
        public java.util.Collection getReceipts() {
           return receipts;
        }

        /**
         * Setter for property receipts.
         * @param receipts New value of property receipts.
         */
        public void setReceipts(java.util.Collection receipts) {
           this.receipts = receipts;
        }

        /**
         * Getter for property itemRowspan.
         * @return Value of property itemRowspan.
         */
        public int getItemRowspan() {
           return itemRowspan;
        }

        /**
         * Setter for property itemRowspan.
         * @param itemRowspan New value of property itemRowspan.
         */
        public void setItemRowspan(int itemRowspan) {
           this.itemRowspan = itemRowspan;
        }

        /**
         * Getter for property receiptRowspan.
         * @return Value of property receiptRowspan.
         */
        public int getReceiptRowspan() {
           return receiptRowspan;
        }

        /**
         * Setter for property receiptRowspan.
         * @param receiptRowspan New value of property receiptRowspan.
         */
        public void setReceiptRowspan(int receiptRowspan) {
           this.receiptRowspan = receiptRowspan;
        }

        /**
         * Getter for property quantityPicked.
         * @return Value of property quantityPicked.
         */
        public java.math.BigDecimal getQuantityPicked() {
           return quantityPicked;
        }

        /**
         * Setter for property quantityPicked.
         * @param quantityPicked New value of property quantityPicked.
         */
        public void setQuantityPicked(java.math.BigDecimal quantityPicked) {
           this.quantityPicked = quantityPicked;
        }

}