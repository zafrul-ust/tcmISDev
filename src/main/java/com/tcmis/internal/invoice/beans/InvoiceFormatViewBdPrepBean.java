package com.tcmis.internal.invoice.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: InvoiceFormatViewBdPrepBean <br>
 * @version: 1.0, Mar 24, 2005 <br>
 *****************************************************************************/


public class InvoiceFormatViewBdPrepBean extends BaseDataBean {

	private String companyId;
	private String invoiceGroup;
	private BigDecimal invoicePeriod;
	private BigDecimal invoice;
	private String description;
	private BigDecimal chemicalAmount;
	private BigDecimal percentOfTotal;
	private BigDecimal odc;
	private BigDecimal nonchem;
	private BigDecimal odcNonchem;
	private BigDecimal labor;
	private BigDecimal total;
	private BigDecimal journalEntry;
	private String department;
	private String ccn;
	private BigDecimal journalEntry2;
	private String department2;
	private String ccn2;
	private BigDecimal totalJournalEntries;


	//constructor
	public InvoiceFormatViewBdPrepBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setInvoiceGroup(String invoiceGroup) {
		this.invoiceGroup = invoiceGroup;
	}
	public void setInvoicePeriod(BigDecimal invoicePeriod) {
		this.invoicePeriod = invoicePeriod;
	}
	public void setInvoice(BigDecimal invoice) {
		this.invoice = invoice;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setChemicalAmount(BigDecimal chemicalAmount) {
		this.chemicalAmount = chemicalAmount;
	}
	public void setPercentOfTotal(BigDecimal percentOfTotal) {
		this.percentOfTotal = percentOfTotal;
	}
	public void setOdc(BigDecimal odc) {
		this.odc = odc;
	}
	public void setNonchem(BigDecimal nonchem) {
		this.nonchem = nonchem;
	}
	public void setOdcNonchem(BigDecimal odcNonchem) {
		this.odcNonchem = odcNonchem;
	}
	public void setLabor(BigDecimal labor) {
		this.labor = labor;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public void setJournalEntry(BigDecimal journalEntry) {
		this.journalEntry = journalEntry;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public void setCcn(String ccn) {
		this.ccn = ccn;
	}
	public void setJournalEntry2(BigDecimal journalEntry2) {
		this.journalEntry2 = journalEntry2;
	}
	public void setDepartment2(String department2) {
		this.department2 = department2;
	}
	public void setCcn2(String ccn2) {
		this.ccn2 = ccn2;
	}
	public void setTotalJournalEntries(BigDecimal totalJournalEntries) {
		this.totalJournalEntries = totalJournalEntries;
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public String getInvoiceGroup() {
		return invoiceGroup;
	}
	public BigDecimal getInvoicePeriod() {
		return invoicePeriod;
	}
	public BigDecimal getInvoice() {
		return invoice;
	}
	public String getDescription() {
		return description;
	}
	public BigDecimal getChemicalAmount() {
		return chemicalAmount;
	}
	public BigDecimal getPercentOfTotal() {
		return percentOfTotal;
	}
	public BigDecimal getOdc() {
		return odc;
	}
	public BigDecimal getNonchem() {
		return nonchem;
	}
	public BigDecimal getOdcNonchem() {
		return odcNonchem;
	}
	public BigDecimal getLabor() {
		return labor;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public BigDecimal getJournalEntry() {
		return journalEntry;
	}
	public String getDepartment() {
		return department;
	}
	public String getCcn() {
		return ccn;
	}
	public BigDecimal getJournalEntry2() {
		return journalEntry2;
	}
	public String getDepartment2() {
		return department2;
	}
	public String getCcn2() {
		return ccn2;
	}
	public BigDecimal getTotalJournalEntries() {
		return totalJournalEntries;
	}
}