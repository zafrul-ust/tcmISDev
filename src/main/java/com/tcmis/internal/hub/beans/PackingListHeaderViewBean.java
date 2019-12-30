package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PackingListHeaderViewBean <br>
 * @version: 1.0, Apr 20, 2005 <br>
 *****************************************************************************/


public class PackingListHeaderViewBean extends BaseDataBean {

	private BigDecimal shipmentId;
	private String datePicked;
	private String datePrinted;
	private String branchPlant;
	private String taxId;
        private String companyId;
	private String shipToLocationId;
        private String inventoryGroup;
        private String hubCompanyId;
	private String hubLocationId;
	private String shipToAddress;
	private String hubAddress;


	//constructor
	public PackingListHeaderViewBean() {
	}

	//setters
	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}
	public void setDatePicked(String datePicked) {
		this.datePicked = datePicked;
	}
	public void setDatePrinted(String datePrinted) {
		this.datePrinted = datePrinted;
	}
	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
        public void setCompanyId(String companyId) {
                this.companyId = companyId;
        }
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
        public void setInventoryGroup(String inventoryGroup) {
                this.inventoryGroup = inventoryGroup;
        }
        public void setHubCompanyId(String hubCompanyId) {
                this.hubCompanyId = hubCompanyId;
        }
	public void setHubLocationId(String hubLocationId) {
		this.hubLocationId = hubLocationId;
	}
	public void setShipToAddress(String shipToAddress) {
		this.shipToAddress = shipToAddress;
	}
	public void setHubAddress(String hubAddress) {
		this.hubAddress = hubAddress;
	}

	//getters
	public BigDecimal getShipmentId() {
		return shipmentId;
	}
	public String getDatePicked() {
		return datePicked;
	}
	public String getDatePrinted() {
		return datePrinted;
	}
	public String getBranchPlant() {
		return branchPlant;
	}
	public String getTaxId() {
		return taxId;
	}
        public String getCompanyId() {
                return companyId;
        }
	public String getShipToLocationId() {
		return shipToLocationId;
	}
        public String getInventoryGroup() {
                return inventoryGroup;
        }
        public String getHubCompanyId() {
                return hubCompanyId;
        }
	public String getHubLocationId() {
		return hubLocationId;
	}
	public String getShipToAddress() {
		return shipToAddress;
	}
	public String getHubAddress() {
		return hubAddress;
	}
}