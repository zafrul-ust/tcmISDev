package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: InvoiceCorrPrintHcViewBean <br>
 * @version: 1.0, May 12, 2010 <br>
 *****************************************************************************/


public class InvoiceCorrPrintHcViewBean extends BaseDataBean {

	private BigDecimal invoice;
	private BigDecimal prNumber;
	private String lineItem;
	private String chargeDescription;
	private BigDecimal catalogPrice;
	private BigDecimal taxRate;
	private String deleteCharge;
	private BigDecimal itemId;
	private BigDecimal adjustedPrice;
	// from page.
	private String changed;
	
	//constructor
	public InvoiceCorrPrintHcViewBean() {
	}

	//setters
	public void setInvoice(BigDecimal invoice) {
		this.invoice = invoice;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setChargeDescription(String chargeDescription) {
		this.chargeDescription = chargeDescription;
	}
	public void setCatalogPrice(BigDecimal catalogPrice) {
		this.catalogPrice = catalogPrice;
	}
	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}


	//getters
	public BigDecimal getInvoice() {
		return invoice;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getChargeDescription() {
		return chargeDescription;
	}
	public BigDecimal getCatalogPrice() {
		return catalogPrice;
	}
	public BigDecimal getTaxRate() {
		return taxRate;
	}
	public String getDeleteCharge() {
		return deleteCharge;
	}
	public void setDeleteCharge(String deleteCharge) {
		this.deleteCharge = deleteCharge;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public String getLineItem() {
		return lineItem;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public String getChanged() {
		return changed;
	}
	public void setChanged(String changed) {
		this.changed = changed;
	}
	public BigDecimal getAdjustedPrice() {
		return adjustedPrice;
	}
	public void setAdjustedPrice(BigDecimal adjustedPrice) {
		this.adjustedPrice = adjustedPrice;
	}

}