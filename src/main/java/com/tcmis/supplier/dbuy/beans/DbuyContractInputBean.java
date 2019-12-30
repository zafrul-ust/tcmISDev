package com.tcmis.supplier.dbuy.beans;

import java.util.Date; 
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DbuyContractBean <br>
 * @version: 1.0, May 3, 2006 <br>
 *****************************************************************************/


public class DbuyContractInputBean extends BaseDataBean {

	private BigDecimal itemId;
	private String inventoryGroup;
	private String shipToCompanyId;
	private String shipToLocationId;
	private String supplier;
	private String carrier;
	private BigDecimal buyer;
	private BigDecimal sourcer;
	private String paymentTerms;
	private String freightOnBoard;
	private BigDecimal multipleOf;
	private String supplierPartNo;
	private BigDecimal supplierContactId;
	private BigDecimal remainingShelfLifePercent;
	private String criticalOrderCarrier;
	private String refClientPartNo;
	private String refClientPoNo;
	private String roundToMultiple;
	private String consignment;
	private BigDecimal leadTimeDays;
	private String defaultShipmentOriginState;
	private BigDecimal transitTimeInDays;        
	private String supplyPath;

        private String contractType;
        private Date endDate;
        private String uptoQuantity;
        private String itemUnitPrice;
        private BigDecimal addChargeItemId1;
        private BigDecimal addChargeItemId1UnitPrice;
        private BigDecimal addChargeItemId2;
        private BigDecimal addChargeItemId2UnitPrice;
        private String insertSource;
        private String currencyId;
        
	//constructor
	public DbuyContractInputBean() {
	}

	//setters
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setShipToCompanyId(String shipToCompanyId) {
		this.shipToCompanyId = shipToCompanyId;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public void setBuyer(BigDecimal buyer) {
		this.buyer = buyer;
	}
	public void setSourcer(BigDecimal sourcer) {
		this.sourcer = sourcer;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public void setFreightOnBoard(String freightOnBoard) {
		this.freightOnBoard = freightOnBoard;
	}
	public void setMultipleOf(BigDecimal multipleOf) {
		this.multipleOf = multipleOf;
	}
	public void setSupplierPartNo(String supplierPartNo) {
		this.supplierPartNo = supplierPartNo;
	}
	public void setSupplierContactId(BigDecimal supplierContactId) {
		this.supplierContactId = supplierContactId;
	}
	public void setRemainingShelfLifePercent(BigDecimal remainingShelfLifePercent) {
		this.remainingShelfLifePercent = remainingShelfLifePercent;
	}
	public void setCriticalOrderCarrier(String criticalOrderCarrier) {
		this.criticalOrderCarrier = criticalOrderCarrier;
	}
	public void setRefClientPartNo(String refClientPartNo) {
		this.refClientPartNo = refClientPartNo;
	}
	public void setRefClientPoNo(String refClientPoNo) {
		this.refClientPoNo = refClientPoNo;
	}
	public void setRoundToMultiple(String roundToMultiple) {
		this.roundToMultiple = roundToMultiple;
	}
	public void setConsignment(String consignment) {
		this.consignment = consignment;
	}
	public void setLeadTimeDays(BigDecimal leadTimeDays) {
		this.leadTimeDays = leadTimeDays;
	}
	public void setDefaultShipmentOriginState(String defaultShipmentOriginState) {
		this.defaultShipmentOriginState = defaultShipmentOriginState;
	}
	public void setTransitTimeInDays(BigDecimal transitTimeInDays) {
		this.transitTimeInDays = transitTimeInDays;
	}
	public void setSupplyPath(String supplyPath) {
		this.supplyPath = supplyPath;
	}


	//getters
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getShipToCompanyId() {
		return shipToCompanyId;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getCarrier() {
		return carrier;
	}
	public BigDecimal getBuyer() {
		return buyer;
	}
	public BigDecimal getSourcer() {
		return sourcer;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public String getFreightOnBoard() {
		return freightOnBoard;
	}
	public BigDecimal getMultipleOf() {
		return multipleOf;
	}
	public String getSupplierPartNo() {
		return supplierPartNo;
	}
	public BigDecimal getSupplierContactId() {
		return supplierContactId;
	}
	public BigDecimal getRemainingShelfLifePercent() {
		return remainingShelfLifePercent;
	}
	public String getCriticalOrderCarrier() {
		return criticalOrderCarrier;
	}
	public String getRefClientPartNo() {
		return refClientPartNo;
	}
	public String getRefClientPoNo() {
		return refClientPoNo;
	}
	public String getRoundToMultiple() {
		return roundToMultiple;
	}
	public String getConsignment() {
		return consignment;
	}
	public BigDecimal getLeadTimeDays() {
		return leadTimeDays;
	}
	public String getDefaultShipmentOriginState() {
		return defaultShipmentOriginState;
	}
	public BigDecimal getTransitTimeInDays() {
		return transitTimeInDays;
	}
	public String getSupplyPath() {
		return supplyPath;
	}
        
        /**
         * Getter for property contractType.
         * @return Value of property contractType.
         */
        public java.lang.String getContractType() {
           return contractType;
        }
        
        /**
         * Setter for property contractType.
         * @param contractType New value of property contractType.
         */
        public void setContractType(java.lang.String contractType) {
           this.contractType = contractType;
        }
        
        /**
         * Getter for property endDate.
         * @return Value of property endDate.
         */
        public java.util.Date getEndDate() {
           return endDate;
        }
        
        /**
         * Setter for property endDate.
         * @param endDate New value of property endDate.
         */
        public void setEndDate(java.util.Date endDate) {
           this.endDate = endDate;
        }
        
        /**
         * Getter for property uptoQuantity.
         * @return Value of property uptoQuantity.
         */
        public java.lang.String getUptoQuantity() {
           return uptoQuantity;
        }
        
        /**
         * Setter for property uptoQuantity.
         * @param uptoQuantity New value of property uptoQuantity.
         */
        public void setUptoQuantity(java.lang.String uptoQuantity) {
           this.uptoQuantity = uptoQuantity;
        }
        
        /**
         * Getter for property itemUnitPrice.
         * @return Value of property itemUnitPrice.
         */
        public java.lang.String getItemUnitPrice() {
           return itemUnitPrice;
        }
        
        /**
         * Setter for property itemUnitPrice.
         * @param itemUnitPrice New value of property itemUnitPrice.
         */
        public void setItemUnitPrice(java.lang.String itemUnitPrice) {
           this.itemUnitPrice = itemUnitPrice;
        }
        
        /**
         * Getter for property addChargeItemId1.
         * @return Value of property addChargeItemId1.
         */
        public java.math.BigDecimal getAddChargeItemId1() {
           return addChargeItemId1;
        }
        
        /**
         * Setter for property addChargeItemId1.
         * @param addChargeItemId1 New value of property addChargeItemId1.
         */
        public void setAddChargeItemId1(java.math.BigDecimal addChargeItemId1) {
           this.addChargeItemId1 = addChargeItemId1;
        }
        
        /**
         * Getter for property addChargeItemId1UnitPrice.
         * @return Value of property addChargeItemId1UnitPrice.
         */
        public java.math.BigDecimal getAddChargeItemId1UnitPrice() {
           return addChargeItemId1UnitPrice;
        }
        
        /**
         * Setter for property addChargeItemId1UnitPrice.
         * @param addChargeItemId1UnitPrice New value of property addChargeItemId1UnitPrice.
         */
        public void setAddChargeItemId1UnitPrice(java.math.BigDecimal addChargeItemId1UnitPrice) {
           this.addChargeItemId1UnitPrice = addChargeItemId1UnitPrice;
        }
        
        /**
         * Getter for property addChargeItemId2.
         * @return Value of property addChargeItemId2.
         */
        public java.math.BigDecimal getAddChargeItemId2() {
           return addChargeItemId2;
        }
        
        /**
         * Setter for property addChargeItemId2.
         * @param addChargeItemId2 New value of property addChargeItemId2.
         */
        public void setAddChargeItemId2(java.math.BigDecimal addChargeItemId2) {
           this.addChargeItemId2 = addChargeItemId2;
        }
        
        /**
         * Getter for property addChargeItemId2UnitPrice.
         * @return Value of property addChargeItemId2UnitPrice.
         */
        public java.math.BigDecimal getAddChargeItemId2UnitPrice() {
           return addChargeItemId2UnitPrice;
        }
        
        /**
         * Setter for property addChargeItemId2UnitPrice.
         * @param addChargeItemId2UnitPrice New value of property addChargeItemId2UnitPrice.
         */
        public void setAddChargeItemId2UnitPrice(java.math.BigDecimal addChargeItemId2UnitPrice) {
           this.addChargeItemId2UnitPrice = addChargeItemId2UnitPrice;
        }
        
        /**
         * Getter for property insertSource.
         * @return Value of property insertSource.
         */
        public java.lang.String getInsertSource() {
           return insertSource;
        }
        
        /**
         * Setter for property insertSource.
         * @param insertSource New value of property insertSource.
         */
        public void setInsertSource(java.lang.String insertSource) {
           this.insertSource = insertSource;
        }
        
        /**
         * Getter for property currencyId.
         * @return Value of property currencyId.
         */
        public java.lang.String getCurrencyId() {
           return currencyId;
        }
        
        /**
         * Setter for property currencyId.
         * @param currencyId New value of property currencyId.
         */
        public void setCurrencyId(java.lang.String currencyId) {
           this.currencyId = currencyId;
        }
        
}