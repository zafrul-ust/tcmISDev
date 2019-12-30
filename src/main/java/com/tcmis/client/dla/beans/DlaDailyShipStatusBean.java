package com.tcmis.client.dla.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DlaDailyShipStatusBean <br>
 * @version: 1.0, Mar 28, 2008 <br>
 *****************************************************************************/


public class DlaDailyShipStatusBean extends BaseDataBean {

	private String customerPo;
	private Date needDate;
	private BigDecimal radianPo;
        private Date shipConfirmDate;
        private String supplierName;
        private String locationDesc;


	//constructor
	public DlaDailyShipStatusBean() {
	}

	//setters
	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}
	public void setNeedDate(Date needDate) {
		this.needDate = needDate;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}


	//getters
	public String getCustomerPo() {
		return customerPo;
	}
	public Date getNeedDate() {
		return needDate;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
        
        /**
         * Getter for property shipConfirmDate.
         * @return Value of property shipConfirmDate.
         */
        public java.util.Date getShipConfirmDate() {
           return shipConfirmDate;
        }
        
        /**
         * Setter for property shipConfirmDate.
         * @param shipConfirmDate New value of property shipConfirmDate.
         */
        public void setShipConfirmDate(java.util.Date shipConfirmDate) {
           this.shipConfirmDate = shipConfirmDate;
        }
        
        /**
         * Getter for property supplierName.
         * @return Value of property supplierName.
         */
        public java.lang.String getSupplierName() {
           return supplierName;
        }
        
        /**
         * Setter for property supplierName.
         * @param supplierName New value of property supplierName.
         */
        public void setSupplierName(java.lang.String supplierName) {
           this.supplierName = supplierName;
        }
        
        /**
         * Getter for property locationDesc.
         * @return Value of property locationDesc.
         */
        public java.lang.String getLocationDesc() {
           return locationDesc;
        }
        
        /**
         * Setter for property locationDesc.
         * @param locationDesc New value of property locationDesc.
         */
        public void setLocationDesc(java.lang.String locationDesc) {
           this.locationDesc = locationDesc;
        }
        
}