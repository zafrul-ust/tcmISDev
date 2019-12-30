package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tcmis.common.framework.BaseDataBean;

@SuppressWarnings("serial")
public class InvoiceGeWhippanyBean extends BaseDataBean
{

	private String accountNumber;
	private BigDecimal invoice;
	private BigDecimal invoiceAmount;
	private Date invoiceDate;
	private String invoiceGroup;
	private BigDecimal invoicePeriod;
	private BigDecimal lineAmount;
	private String lineItem;
	private String poNumber;

	// constructor
	public InvoiceGeWhippanyBean()
	{
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public BigDecimal getInvoice()
	{
		return invoice;
	}

	public BigDecimal getInvoiceAmount()
	{
		return invoiceAmount;
	}

	public Date getInvoiceDate()
	{
		return invoiceDate;
	}

	public String getInvoiceGroup()
	{
		return invoiceGroup;
	}

	public BigDecimal getInvoicePeriod()
	{
		return invoicePeriod;
	}

	public BigDecimal getLineAmount()
	{
		return lineAmount;
	}

	public String getLineItem()
	{
		return lineItem;
	}

	public String getPoNumber()
	{
		return poNumber;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public void setInvoice(BigDecimal invoice)
	{
		this.invoice = invoice;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount)
	{
		this.invoiceAmount = invoiceAmount;
	}

	public void setInvoiceDate(Date invoiceDate)
	{
		this.invoiceDate = invoiceDate;
	}

	public void setInvoiceGroup(String invoiceGroup)
	{
		this.invoiceGroup = invoiceGroup;
	}

	public void setInvoicePeriod(BigDecimal invoicePeriod)
	{
		this.invoicePeriod = invoicePeriod;
	}

	public void setLineAmount(BigDecimal lineAmount)
	{
		this.lineAmount = lineAmount;
	}

	public void setLineItem(String lineItem)
	{
		this.lineItem = lineItem;
	}

	public void setPoNumber(String poNumber)
	{
		this.poNumber = poNumber;
	}

}
