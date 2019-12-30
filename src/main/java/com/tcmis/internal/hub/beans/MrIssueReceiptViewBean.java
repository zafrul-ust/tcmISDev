package com.tcmis.internal.hub.beans;

import java.util.Collection;
import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: MrIssueReceiptViewBean <br>
 * @version: 1.0, Nov 14, 2006 <br>
 *****************************************************************************/


public class MrIssueReceiptViewBean extends BaseDataBean {

	private String companyId;
	private BigDecimal prNumber;
	private String lineItem;
	private String sourceHub;
	private BigDecimal itemId;
	private BigDecimal receiptId;
	private String facilityId;
	private String catPartNo;
	private BigDecimal totalShipped;
	private BigDecimal quantityReturned;
	private BigDecimal quantity;
	private String mfgLot;
	private Date expireDate;
	private BigDecimal recertNumber;
	private String lotStatus;
	private String inventoryGroup;
	private String creditReturn;
    private Collection receiptItemPriorBinViewBeanCollection;
	private BigDecimal shipmentId;
	private Date dateShipped;
        
        // input bean fields
        private String ok;
    	private BigDecimal issueId;
        private Date dor;
        private String bin;
        private String replacementMaterial; // for inputs

    	private Date shipConfirmDate;
    	private BigDecimal orderQuantity;

	//constructor
	public MrIssueReceiptViewBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setSourceHub(String sourceHub) {
		this.sourceHub = sourceHub;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setTotalShipped(BigDecimal totalShipped) {
		this.totalShipped = totalShipped;
	}
	public void setQuantityReturned(BigDecimal quantityReturned) {
		this.quantityReturned = quantityReturned;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public void setRecertNumber(BigDecimal recertNumber) {
		this.recertNumber = recertNumber;
	}
	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setCreditReturn(String creditReturn) {
		this.creditReturn = creditReturn;
	}
        public void setReceiptItemPriorBinViewBeanCollection(Collection receiptItemPriorBinViewBeanCollection) {
           this.receiptItemPriorBinViewBeanCollection = receiptItemPriorBinViewBeanCollection;
        }


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getSourceHub() {
		return sourceHub;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public BigDecimal getTotalShipped() {
		return totalShipped;
	}
	public BigDecimal getQuantityReturned() {
		return quantityReturned;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public String getMfgLot() {
		return mfgLot;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public BigDecimal getRecertNumber() {
		return recertNumber;
	}
	public String getLotStatus() {
		return lotStatus;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getCreditReturn() {
		return creditReturn;
	}
        public Collection getReceiptItemPriorBinViewBeanCollection() {
           return receiptItemPriorBinViewBeanCollection;
        }        
        
        /**
         * Getter for property ok.
         * @return Value of property ok.
         */
        public java.lang.String getOk() {
           return ok;
        }
        
        /**
         * Setter for property ok.
         * @param ok New value of property ok.
         */
        public void setOk(java.lang.String ok) {
           this.ok = ok;
        }
                
        /**
         * Getter for property bin.
         * @return Value of property bin.
         */
        public java.lang.String getBin() {
           return bin;
        }
        
        /**
         * Setter for property bin.
         * @param bin New value of property bin.
         */
        public void setBin(java.lang.String bin) {
           this.bin = bin;
        }
                
        /**
         * Getter for property dor.
         * @return Value of property dor.
         */
        public java.util.Date getDor() {
           return dor;
        }
        
        /**
         * Setter for property dor.
         * @param dor New value of property dor.
         */
        public void setDor(java.util.Date dor) {
           this.dor = dor;
        }
    	public BigDecimal getShipmentId() {
    		return shipmentId;
    	}

    	public void setShipmentId(BigDecimal shipmentId) {
    		this.shipmentId = shipmentId;
    	}

    	public Date getDateShipped() {
    		return dateShipped;
    	}

    	public void setDateShipped(Date dateShipped) {
    		this.dateShipped = dateShipped;
    	}

    	public BigDecimal getIssueId() {
    		return issueId;
    	}

    	public void setIssueId(BigDecimal issueId) {
    		this.issueId = issueId;
    	}

    	public String getReplacementMaterial() {
			return replacementMaterial;
		}

		public void setReplacementMaterial(String replacementMaterial) {
			this.replacementMaterial = replacementMaterial;
		}
		public Date getShipConfirmDate() {
			return shipConfirmDate;
		}

		public void setShipConfirmDate(Date shipConfirmDate) {
			this.shipConfirmDate = shipConfirmDate;
		}

		public BigDecimal getOrderQuantity() {
			return orderQuantity;
		}

		public void setOrderQuantity(BigDecimal orderQuantity) {
			this.orderQuantity = orderQuantity;
		}

        
}