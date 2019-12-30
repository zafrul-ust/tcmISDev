package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: InvoiceCorrPrintLcViewBean <br>
 * @version: 1.0, May 12, 2010 <br>
 *****************************************************************************/


public class InvoiceCorrPrintLcViewBean extends BaseDataBean {

	private BigDecimal invoice;
	private BigDecimal prNumber;
	private String lineItem;
	private String chargeDescription;
	private BigDecimal price;
	private BigDecimal taxRate;
	private BigDecimal adjustedPrice;
	private String deleteCharge;
	private BigDecimal itemId;
	
// from page.
	private String changed;

	//constructor
	public InvoiceCorrPrintLcViewBean() {
	}

	//setters
	public void setInvoice(BigDecimal invoice) {
		this.invoice = invoice;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setChargeDescription(String chargeDescription) {
		this.chargeDescription = chargeDescription;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
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
	public String getLineItem() {
		return lineItem;
	}
	public String getChargeDescription() {
		return chargeDescription;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public BigDecimal getTaxRate() {
		return taxRate;
	}
	public BigDecimal getAdjustedPrice() {
		return adjustedPrice;
	}
	public void setAdjustedPrice(BigDecimal adjustedPrice) {
		this.adjustedPrice = adjustedPrice;
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
	public String getChanged() {
		return changed;
	}
	public void setChanged(String changed) {
		this.changed = changed;
	}

}