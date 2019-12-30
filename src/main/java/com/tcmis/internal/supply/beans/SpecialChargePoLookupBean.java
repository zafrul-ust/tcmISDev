package com.tcmis.internal.supply.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class SpecialChargePoLookupBean extends BaseDataBean {
	private String blockPo;
	private String blockSupplier;
	private Date dateInserted;
	private BigDecimal insertedBy;
	private String insertedByName;
	private BigDecimal lastUpdatedBy;
	private String lastUpdatedByName;
	private Date lastUpdatedDate;
	private String product;
	private BigDecimal radianPo;
	private String status;
	private String supplier;
	private String supplierName;
	
	public String getBlockPo() {
		return blockPo;
	}
	public void setBlockPo(String blockPo) {
		this.blockPo = blockPo;
	}
	public String getBlockSupplier() {
		return blockSupplier;
	}
	public void setBlockSupplier(String blockSupplier) {
		this.blockSupplier = blockSupplier;
	}
	public Date getDateInserted() {
		return dateInserted;
	}
	public void setDateInserted(Date dateInserted) {
		this.dateInserted = dateInserted;
	}
	public BigDecimal getInsertedBy() {
		return insertedBy;
	}
	public void setInsertedBy(BigDecimal insertedBy) {
		this.insertedBy = insertedBy;
	}
	public String getInsertedByName() {
		return insertedByName;
	}
	public void setInsertedByName(String insertedByName) {
		this.insertedByName = insertedByName;
	}
	public BigDecimal getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(BigDecimal lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getLastUpdatedByName() {
		return lastUpdatedByName;
	}
	public void setLastUpdatedByName(String lastUpdatedByName) {
		this.lastUpdatedByName = lastUpdatedByName;
	}
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
}
