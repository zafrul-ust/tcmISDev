package com.tcmis.internal.invoice.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DrsPalmbayInvoiceDtlViewBean <br>
 * @version: 1.0, May 8, 2010 <br>
 *****************************************************************************/


public class DrsPalmbayInvoiceDtlViewBean extends BaseDataBean {

	private String invoice;
	private Date invoiceDate;
	private BigDecimal invoicePeriod;
	private String poNumber;
	private BigDecimal materialCost;
	private BigDecimal materialRebate;
	private BigDecimal wasteCost;
	private BigDecimal wasteRebate;
	private BigDecimal gasCost;
	private BigDecimal cylinderRentalCost;
	private BigDecimal serviceFee;
	private BigDecimal totalDue;


	//constructor
	public DrsPalmbayInvoiceDtlViewBean() {
	}

	//setters
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public void setInvoicePeriod(BigDecimal invoicePeriod) {
		this.invoicePeriod = invoicePeriod;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setMaterialCost(BigDecimal materialCost) {
		this.materialCost = materialCost;
	}
	public void setMaterialRebate(BigDecimal materialRebate) {
		this.materialRebate = materialRebate;
	}
	public void setWasteCost(BigDecimal wasteCost) {
		this.wasteCost = wasteCost;
	}
	public void setWasteRebate(BigDecimal wasteRebate) {
		this.wasteRebate = wasteRebate;
	}
	public void setGasCost(BigDecimal gasCost) {
		this.gasCost = gasCost;
	}
	public void setCylinderRentalCost(BigDecimal cylinderRentalCost) {
		this.cylinderRentalCost = cylinderRentalCost;
	}
	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}
	public void setTotalDue(BigDecimal totalDue) {
		this.totalDue = totalDue;
	}


	//getters
	public String getInvoice() {
		return invoice;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public BigDecimal getInvoicePeriod() {
		return invoicePeriod;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public BigDecimal getMaterialCost() {
		return materialCost;
	}
	public BigDecimal getMaterialRebate() {
		return materialRebate;
	}
	public BigDecimal getWasteCost() {
		return wasteCost;
	}
	public BigDecimal getWasteRebate() {
		return wasteRebate;
	}
	public BigDecimal getGasCost() {
		return gasCost;
	}
	public BigDecimal getCylinderRentalCost() {
		return cylinderRentalCost;
	}
	public BigDecimal getServiceFee() {
		return serviceFee;
	}
	public BigDecimal getTotalDue() {
		return totalDue;
	}

}