package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CostReportTemplateBean <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class CostReportTemplateBean extends BaseDataBean {

	private String companyId;
	private BigDecimal personnelId;
	private String reportName;
	private String facilityId;
	private String application;
	private String accountSysId;
	private String chargeType;
	private String chargeNumber11;
	private String chargeNumber22;
	private String searchBy;
	private String searchType;
	private String searchText;
	private BigDecimal invoice;
	private BigDecimal invoicePeriod;
	private Date startDate;
	private Date endDate;
	private BigDecimal categoryId;
	private String reportField;
	private Date invoiceStartDate;
	private Date invoiceEndDate;
	private BigDecimal requestor;
	private String requestorName;
	private String commodity;
	private String dateDeliveredGroupBy;
	private String sourceHub;
	private String itemType;
	private String reportType;
	private String uom;


	//constructor
	public CostReportTemplateBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setAccountSysId(String accountSysId) {
		this.accountSysId = accountSysId;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public void setChargeNumber1(String chargeNumber11) {
		this.chargeNumber11 = chargeNumber11;
	}
	public void setChargeNumber2(String chargeNumber22) {
		this.chargeNumber22 = chargeNumber22;
	}
	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public void setInvoice(BigDecimal invoice) {
		this.invoice = invoice;
	}
	public void setInvoicePeriod(BigDecimal invoicePeriod) {
		this.invoicePeriod = invoicePeriod;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setCategoryId(BigDecimal categoryId) {
		this.categoryId = categoryId;
	}
	public void setReportField(String reportField) {
		this.reportField = reportField;
	}
	public void setInvoiceStartDate(Date invoiceStartDate) {
		this.invoiceStartDate = invoiceStartDate;
	}
	public void setInvoiceEndDate(Date invoiceEndDate) {
		this.invoiceEndDate = invoiceEndDate;
	}
	public void setRequestor(BigDecimal requestor) {
		this.requestor = requestor;
	}
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}
	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	public void setDateDeliveredGroupBy(String dateDeliveredGroupBy) {
		this.dateDeliveredGroupBy = dateDeliveredGroupBy;
	}
	public void setSourceHub(String sourceHub) {
		this.sourceHub = sourceHub;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	//getters
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public String getReportName() {
		return reportName;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getApplication() {
		return application;
	}
	public String getAccountSysId() {
		return accountSysId;
	}
	public String getChargeType() {
		return chargeType;
	}
	public String getChargeNumber1() {
		return chargeNumber11;
	}
	public String getChargeNumber2() {
		return chargeNumber22;
	}
	public String getSearchBy() {
		return searchBy;
	}
	public String getSearchType() {
		return searchType;
	}
	public String getSearchText() {
		return searchText;
	}
	public BigDecimal getInvoice() {
		return invoice;
	}
	public BigDecimal getInvoicePeriod() {
		return invoicePeriod;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public BigDecimal getCategoryId() {
		return categoryId;
	}
	public String getReportField() {
		return reportField;
	}
	public Date getInvoiceStartDate() {
		return invoiceStartDate;
	}
	public Date getInvoiceEndDate() {
		return invoiceEndDate;
	}
	public BigDecimal getRequestor() {
		return requestor;
	}
	public String getRequestorName() {
		return requestorName;
	}
	public String getCommodity() {
		return commodity;
	}
	public String getDateDeliveredGroupBy() {
		return dateDeliveredGroupBy;
	}
	public String getSourceHub() {
		return sourceHub;
	}
	public String getItemType() {
		return itemType;
	}

	public String getReportType() {
		return reportType;
	}

	public String getUom() {
		return uom;
	}
}