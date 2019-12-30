package com.tcmis.supplier.dbuy.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DbuySpecDataDisplayViewBean <br>
 * @version: 1.0, Jun 5, 2006 <br>
 *****************************************************************************/


public class DbuySpecDataDisplayViewBean extends BaseDataBean {

	private BigDecimal itemId;
	private String inventoryGroup;
	private String shipToCompanyId;
	private String shipToLocationId;
	private BigDecimal priority;
	private BigDecimal dbuyContractId;
	private String supplier;
	private String supplierName;
	private String status;
	private String supplyPath;
	private String specLibrary;
	private String specId;
	private String companyId;
	private String coc;
	private String coa;
	private String genericCoa;
	private String genericCoc;
	private String detail;


	//constructor
	public DbuySpecDataDisplayViewBean() {
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
	public void setPriority(BigDecimal priority) {
		this.priority = priority;
	}
	public void setDbuyContractId(BigDecimal dbuyContractId) {
		this.dbuyContractId = dbuyContractId;
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
	public void setSupplyPath(String supplyPath) {
		this.supplyPath = supplyPath;
	}
	public void setSpecLibrary(String specLibrary) {
		this.specLibrary = specLibrary;
	}
	public void setSpecId(String specId) {
		this.specId = specId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCoc(String coc) {
		this.coc = coc;
	}
	public void setCoa(String coa) {
		this.coa = coa;
	}
	public void setGenericCoa(String genericCoa) {
		this.genericCoa = genericCoa;
	}
	public void setGenericCoc(String genericCoc) {
		this.genericCoc = genericCoc;
	}
	public void setDetail(String detail) {
		this.detail = detail;
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
	public BigDecimal getPriority() {
		return priority;
	}
	public BigDecimal getDbuyContractId() {
		return dbuyContractId;
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
	public String getSupplyPath() {
		return supplyPath;
	}
	public String getSpecLibrary() {
		return specLibrary;
	}
	public String getSpecId() {
		return specId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getCoc() {
		return coc;
	}
	public String getCoa() {
		return coa;
	}
	public String getGenericCoa() {
		return genericCoa;
	}
	public String getGenericCoc() {
		return genericCoc;
	}
	public String getDetail() {
		return detail;
	}
}