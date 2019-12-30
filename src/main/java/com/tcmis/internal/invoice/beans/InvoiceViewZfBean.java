package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

@SuppressWarnings("serial")
public class InvoiceViewZfBean extends BaseDataBean
{

	private BigDecimal invoice;
	private BigDecimal invoicePeriod;
	private BigDecimal invoiceAmount;
	private Date invoiceDate;
	private String poNumber;
	private String itemType;
	private String period;
	private String partDescription;
	private BigDecimal quantity;
	private BigDecimal unitPrice;
	private BigDecimal amount;
	private String currencyId;
	private String plantCode;

	// constructor
	public InvoiceViewZfBean()
	{
	}

	public BigDecimal getInvoice()
	{
		return invoice;
	}

	public void setInvoice(BigDecimal invoice)
	{
		this.invoice = invoice;
	}

	public BigDecimal getInvoicePeriod()
	{
		return invoicePeriod;
	}

	public void setInvoicePeriod(BigDecimal invoicePeriod)
	{
		this.invoicePeriod = invoicePeriod;
	}

	public BigDecimal getInvoiceAmount()
	{
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount)
	{
		this.invoiceAmount = invoiceAmount;
	}

	public Date getInvoiceDate()
	{
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate)
	{
		this.invoiceDate = invoiceDate;
	}

	public String getPoNumber()
	{
		return poNumber;
	}

	public void setPoNumber(String poNumber)
	{
		this.poNumber = poNumber;
	}

	public String getItemType()
	{
		return itemType;
	}

	public void setItemType(String itemType)
	{
		this.itemType = itemType;
	}

	public String getPeriod()
	{
		return period;
	}

	public void setPeriod(String period)
	{
		this.period = period;
	}

	public String getPartDescription()
	{
		return partDescription;
	}

	public void setPartDescription(String partDescription)
	{
		this.partDescription = partDescription;
	}

	public BigDecimal getQuantity()
	{
		return quantity;
	}

	public void setQuantity(BigDecimal quantity)
	{
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice()
	{
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice)
	{
		this.unitPrice = unitPrice;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public String getCurrencyId()
	{
		return currencyId;
	}

	public void setCurrencyId(String currencyId)
	{
		this.currencyId = currencyId;
	}

	public String getPlantCode() {
		return plantCode;
	}

	public void setPlantCode(String plantCode) {
		this.plantCode = plantCode;
	}
}