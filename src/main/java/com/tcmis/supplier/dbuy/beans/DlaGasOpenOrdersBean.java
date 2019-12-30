package com.tcmis.supplier.dbuy.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DlaGasOpenOrdersBean <br>
 * @version: 1.0, Feb 21, 2008 <br>
 *****************************************************************************/


public class DlaGasOpenOrdersBean extends BaseDataBean {

	private BigDecimal radianPo;
	private String customerPo;
	private Date dateSent;
	private Date dateAcknowledgement;
	private Date dateConfirmed;
        private Date shipDate;
	private String supplier;
	private String supplierName;
	private String status;
        private String shipToCompanyId;
        private String shipToLocationId;


	//constructor
	public DlaGasOpenOrdersBean() {
	}

	//setters
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}
	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}
	public void setDateAcknowledgement(Date dateAcknowledgement) {
		this.dateAcknowledgement = dateAcknowledgement;
	}
	public void setDateConfirmed(Date dateConfirmed) {
		this.dateConfirmed = dateConfirmed;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setStatus(String status) {
		this.status = status;
	}


	//getters
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public String getCustomerPo() {
		return customerPo;
	}
	public Date getDateSent() {
		return dateSent;
	}
	public Date getDateAcknowledgement() {
		return dateAcknowledgement;
	}
	public Date getDateConfirmed() {
		return dateConfirmed;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public String getStatus() {
		return status;
	}
        
        /**
         * Getter for property shipDate.
         * @return Value of property shipDate.
         */
        public java.util.Date getShipDate() {
           return shipDate;
        }
        
        /**
         * Setter for property shipDate.
         * @param shipDate New value of property shipDate.
         */
        public void setShipDate(java.util.Date shipDate) {
           this.shipDate = shipDate;
        }
        
        /**
         * Getter for property shipToCompanyId.
         * @return Value of property shipToCompanyId.
         */
        public java.lang.String getShipToCompanyId() {
           return shipToCompanyId;
        }
        
        /**
         * Setter for property shipToCompanyId.
         * @param shipToCompanyId New value of property shipToCompanyId.
         */
        public void setShipToCompanyId(java.lang.String shipToCompanyId) {
           this.shipToCompanyId = shipToCompanyId;
        }
        
        /**
         * Getter for property shipToLocationId.
         * @return Value of property shipToLocationId.
         */
        public java.lang.String getShipToLocationId() {
           return shipToLocationId;
        }
        
        /**
         * Setter for property shipToLocationId.
         * @param shipToLocationId New value of property shipToLocationId.
         */
        public void setShipToLocationId(java.lang.String shipToLocationId) {
           this.shipToLocationId = shipToLocationId;
        }
        
}