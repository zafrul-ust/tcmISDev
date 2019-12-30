package com.tcmis.client.report.beans;

import java.util.Date;
import java.util.Collection;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CostReportInputBean <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class CostReportInputBean extends BaseDataBean {

	//constructor
	public CostReportInputBean() {
	}

	public static final int NUMBER_TYPE = 1;
	public static final int STRING_TYPE = 2;
	public static final int DATE_TYPE = 3;

	//search fields
	private String requestorId;
	private String requestorName;
	private String companyId;
	private String companyName;
	private String reportingGroup;
	private String facilityId;
	private String facilityName;
	private String application;
	private String applicationName;
	private String accountSysId;
	private String accountSysName;
	private String chargeType;
	private String accountNumber;
	private String accountNumber2;
	private String accountNumber3;
	private String accountNumber4;
	private String po;
	private String searchBy;
	private String searchByName;
	private String searchType;
	private String searchText;
	private String searchByInvoiceNumber;
	private String searchByInvoicePeriod;
	private Date dateDeliveredBegin;
	private Date dateDeliveredEnd;
	private Date invoiceDateBegin;
	private Date invoiceDateEnd;
	private String[] searchByItemType;
	private String itemTypeName;
	private Collection itemTypeCollection;
	private String[] searchByInvoiceType;
	private String invoiceTypeName;
	private Collection invoiceTypeCollection;
	private String uom;
	private String uomName;

	//report fields checkboxes
	//column 1
	private String company;
	private String invoice;
	private String customerInvoiceNo;
	private String invoiceType;
	private String invoiceDate;
	private String invoicePeriod;
	private String invoiceLine;
	private String accountingSystem;
	private String chargeNumber1;
	private String chargeNumber2;
	private String chargeNumber3;
	private String chargeNumber4;
	private String userPo;
	private String splitCharge;
	private String facility;
	private String workArea;
	private String requestor;

	//column 2
	private String financeApprover;
	private String mrLine;
	private String wasteOrder;
	private String wasteManifest;
	private String partNumber;
	private String type;
	private String partDescription;
	private String packaging;
	private String item;
	private String itemDescription;
	private String manufacturer;
	private String haasPo;
	private String reference;

	//column 3
	private String receiptId;
	private String mfgLot;
	private String dateDelivered;
	private String dateDeliveredGroupBy;
	private String quantity;
	private String invoiceUnitPrice;
	private String unitRebate;
	private String totalFreightCharge;
	private String totalAdditionalCharge;
	private String totalSalesTax;
	private String serviceFee;
	private String peiAmount;
	private String netAmount;
	private String materialSavings;
	private String customerPartNo;
	private String shippingReference;

	private Collection reportFields;
	private Collection sqlFields;
	private Collection additionalFields = new Vector(0);
	private String orderBy;
	private String whereClause;
	private Collection totalPerCurrency;
	private Collection totalPerCurrencyDisplay;
	private Collection groupBy;
	private String templateName;
	private Collection reportFieldForTemplate;
	private String reportType;

	private String templateId;
    private String userGroupId;
    private String userGroupFacilityId;
    private String owner;
    private String status;
    private String templateDescription;
    private String urlPageArg;
	private String globalizationLabelLetter;

	private String		qualityIdLabel;
	private String		qualityId;
	private String		catPartAttributeHeader;
	private String		catPartAttribute;

	public String getQualityIdLabel() {
		return qualityIdLabel;
	}

	public void setQualityIdLabel(String qualityIdLabel) {
		this.qualityIdLabel = qualityIdLabel;
	}

	public String getQualityId() {
		return qualityId;
	}

	public void setQualityId(String qualityId) {
		this.qualityId = qualityId;
	}

	public String getCatPartAttributeHeader() {
		return catPartAttributeHeader;
	}

	public void setCatPartAttributeHeader(String catPartAttributeHeader) {
		this.catPartAttributeHeader = catPartAttributeHeader;
	}

	public String getCatPartAttribute() {
		return catPartAttribute;
	}

	public void setCatPartAttribute(String catPartAttribute) {
		this.catPartAttribute = catPartAttribute;
	}

	//store true columns type from database
	//so I can handle locale

	private Collection columnTypes;
	public void setColumnTypes (Collection col) {
		this.columnTypes = col;
	}
	public Collection getColumnTypes() {
		return columnTypes;
	}
	private int[] reportFieldType;
	public void setReportFieldType(int[] reportFieldType) {
		this.reportFieldType = reportFieldType;
	}
	public int[] getReportFieldType() {
		return reportFieldType;
	}

	//setters
	public void setRequestorId(String requestorId) {
		this.requestorId = requestorId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setReportingGroup(String reportingGroup) {
		this.reportingGroup = reportingGroup;
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

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setAccountNumber2(String accountNumber2) {
		this.accountNumber2 = accountNumber2;
	}

	public void setPo(String po) {
		this.po = po;
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

	public void setSearchByInvoiceNumber(String searchByInvoiceNumber) {
		this.searchByInvoiceNumber = searchByInvoiceNumber;
	}

	public void setSearchByInvoicePeriod(String searchByInvoicePeriod) {
		this.searchByInvoicePeriod = searchByInvoicePeriod;
	}

	public void setDateDeliveredBegin(Date dateDeliveredBegin) {
		this.dateDeliveredBegin = dateDeliveredBegin;
	}

	public void setDateDeliveredEnd(Date dateDeliveredEnd) {
		this.dateDeliveredEnd = dateDeliveredEnd;
	}

	public void setInvoiceDateBegin(Date invoiceDateBegin) {
		this.invoiceDateBegin = invoiceDateBegin;
	}

	public void setInvoiceDateEnd(Date invoiceDateEnd) {
		this.invoiceDateEnd = invoiceDateEnd;
	}

	public void setSearchByItemType(String[] searchByItemType) {
		this.searchByItemType = searchByItemType;
	}

	public void setSearchByInvoiceType(String[] searchByInvoiceType) {
		this.searchByInvoiceType = searchByInvoiceType;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public void setUomName(String uomName) {
		this.uomName = uomName;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public void setInvoicePeriod(String invoicePeriod) {
		this.invoicePeriod = invoicePeriod;
	}

	public void setInvoiceLine(String invoiceLine) {
		this.invoiceLine = invoiceLine;
	}

	public void setAccountingSystem(String accountingSystem) {
		this.accountingSystem = accountingSystem;
	}

	public void setChargeNumber1(String chargeNumber1) {
		this.chargeNumber1 = chargeNumber1;
	}

	public void setChargeNumber2(String chargeNumber2) {
		this.chargeNumber2 = chargeNumber2;
	}

	public void setUserPo(String userPo) {
		this.userPo = userPo;
	}

	public void setSplitCharge(String splitCharge) {
		this.splitCharge = splitCharge;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}

	public void setWorkArea(String workArea) {
		this.workArea = workArea;
	}

	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}

	public void setFinanceApprover(String financeApprover) {
		this.financeApprover = financeApprover;
	}

	public void setMrLine(String mrLine) {
		this.mrLine = mrLine;
	}

	public void setWasteOrder(String wasteOrder) {
		this.wasteOrder = wasteOrder;
	}

	public void setWasteManifest(String wasteManifest) {
		this.wasteManifest = wasteManifest;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setHaasPo(String haasPo) {
		this.haasPo = haasPo;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}

	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}

	public void setDateDelivered(String dateDelivered) {
		this.dateDelivered = dateDelivered;
	}

	public void setDateDeliveredGroupBy(String dateDeliveredGroupBy) {
		this.dateDeliveredGroupBy = dateDeliveredGroupBy;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public void setInvoiceUnitPrice(String invoiceUnitPrice) {
		this.invoiceUnitPrice = invoiceUnitPrice;
	}

	public void setUnitRebate(String unitRebate) {
		this.unitRebate = unitRebate;
	}

	public void setTotalAdditionalCharge(String totalAdditionalCharge) {
		this.totalAdditionalCharge = totalAdditionalCharge;
	}

	public void setTotalSalesTax(String totalSalesTax) {
		this.totalSalesTax = totalSalesTax;
	}

	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}

	public void setPeiAmount(String peiAmount) {
		this.peiAmount = peiAmount;
	}

	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}

	public void setMaterialSavings(String materialSavings) {
		this.materialSavings = materialSavings;
	}

	public void setReportFields(Collection reportFields) {
		this.reportFields = reportFields;
	}

	public void setSqlFields(Collection sqlFields) {
		this.sqlFields = sqlFields;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public void setAdditionalFields(Collection additionalFields) {
		this.additionalFields = additionalFields;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	public void setTotalPerCurrency (Collection totalPerCurrency) {
		this.totalPerCurrency = totalPerCurrency;
	}

	public void setGroupBy(Collection groupBy) {
		this.groupBy = groupBy;
	}

	public void setTotalPerCurrencyDisplay(Collection totalPerCurrencyDisplay) {
		this.totalPerCurrencyDisplay = totalPerCurrencyDisplay;
	}

	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public void setAccountSysName(String accountSysName) {
		this.accountSysName = accountSysName;
	}

	public void setSearchByName(String searchByName) {
		this.searchByName = searchByName;
	}

	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}

	public void setInvoiceTypeName(String invoiceTypeName) {
		this.invoiceTypeName = invoiceTypeName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public void setItemTypeCollection(Collection itemTypeCollection) {
		this.itemTypeCollection = itemTypeCollection;
	}

	public void setInvoiceTypeCollection(Collection invoiceTypeCollection) {
		this.invoiceTypeCollection = invoiceTypeCollection;
	}

	public void setReportFieldForTemplate(Collection reportFieldForTemplate) {
		this.reportFieldForTemplate = reportFieldForTemplate;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	//getters

	public String getRequestorId() {
		return requestorId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getReportingGroup() {
		return reportingGroup;
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

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getAccountNumber2() {
		return accountNumber2;
	}

	public String getPo() {
		return po;
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

	public String getSearchByInvoiceNumber() {
		return searchByInvoiceNumber;
	}

	public String getSearchByInvoicePeriod() {
		return searchByInvoicePeriod;
	}

	public Date getDateDeliveredBegin() {
		return dateDeliveredBegin;
	}

	public Date getDateDeliveredEnd() {
		return dateDeliveredEnd;
	}

	public Date getInvoiceDateBegin() {
		return invoiceDateBegin;
	}

	public Date getInvoiceDateEnd() {
		return invoiceDateEnd;
	}

	public String[] getSearchByItemType() {
		return searchByItemType;
	}

	public String[] getSearchByInvoiceType() {
		return searchByInvoiceType;
	}

	public String getUom() {
		return uom;
	}

	public String getUomName() {
		return uomName;
	}

	public String getCompany() {
		return company;
	}

	public String getInvoice() {
		return invoice;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public String getInvoicePeriod() {
		return invoicePeriod;
	}

	public String getInvoiceLine() {
		return invoiceLine;
	}

	public String getAccountingSystem() {
		return accountingSystem;
	}

	public String getChargeNumber1() {
		return chargeNumber1;
	}

	public String getChargeNumber2() {
		return chargeNumber2;
	}

	public String getUserPo() {
		return userPo;
	}

	public String getSplitCharge() {
		return splitCharge;
	}

	public String getFacility() {
		return facility;
	}

	public String getWorkArea() {
		return workArea;
	}

	public String getRequestor() {
		return requestor;
	}

	public String getFinanceApprover() {
		return financeApprover;
	}

	public String getMrLine() {
		return mrLine;
	}

	public String getWasteOrder() {
		return wasteOrder;
	}

	public String getWasteManifest() {
		return wasteManifest;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public String getType() {
		return type;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public String getPackaging() {
		return packaging;
	}

	public String getItem() {
		return item;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getHaasPo() {
		return haasPo;
	}

	public String getReference() {
		return reference;
	}

	public String getReceiptId() {
		return receiptId;
	}

	public String getMfgLot() {
		return mfgLot;
	}

	public String getDateDelivered() {
		return dateDelivered;
	}

	public String getDateDeliveredGroupBy() {
		return dateDeliveredGroupBy;
	}

	public String getQuantity() {
		return quantity;
	}

	public String getInvoiceUnitPrice() {
		return invoiceUnitPrice;
	}

	public String getUnitRebate() {
		return unitRebate;
	}

	public String getTotalAdditionalCharge() {
		return totalAdditionalCharge;
	}

	public String getTotalSalesTax() {
		return totalSalesTax;
	}

	public String getServiceFee() {
		return serviceFee;
	}

	public String getPeiAmount() {
		return peiAmount;
	}

	public String getNetAmount() {
		return netAmount;
	}

	public String getMaterialSavings() {
		return materialSavings;
	}

	public Collection getReportFields() {
		return reportFields;
	}

	public Collection getSqlFields() {
		return sqlFields;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public Collection getAdditionalFields() {
		return additionalFields;
	}

	public String getWhereClause() {
		return whereClause;
	}

	public Collection getTotalPerCurrency() {
		return totalPerCurrency;
	}

	public Collection getGroupBy() {
		return groupBy;
	}

	public Collection getTotalPerCurrencyDisplay() {
		return totalPerCurrencyDisplay;
	}

	public String getRequestorName() {
		return requestorName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public String getAccountSysName() {
		return accountSysName;
	}

	public String getSearchByName() {
		return searchByName;
	}

	public String getItemTypeName() {
		return itemTypeName;
	}

	public String getInvoiceTypeName() {
		return invoiceTypeName;
	}

	public String getTemplateName() {
		return templateName;
	}

	public Collection getItemTypeCollection() {
		return itemTypeCollection;
	}

	public Collection getInvoiceTypeCollection() {
		return invoiceTypeCollection;
	}

	public Collection getReportFieldForTemplate() {
		return reportFieldForTemplate;
	}

	public String getReportType() {
		return reportType;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}

	public String getUserGroupFacilityId() {
		return userGroupFacilityId;
	}

	public void setUserGroupFacilityId(String userGroupFacilityId) {
		this.userGroupFacilityId = userGroupFacilityId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTemplateDescription() {
		return templateDescription;
	}

	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}

	public String getUrlPageArg() {
		return urlPageArg;
	}

	public void setUrlPageArg(String urlPageArg) {
		this.urlPageArg = urlPageArg;
	}

	public String getGlobalizationLabelLetter() {
		return globalizationLabelLetter;
	}

	public void setGlobalizationLabelLetter(String globalizationLabelLetter) {
		this.globalizationLabelLetter = globalizationLabelLetter;
	}

	public String getAccountNumber3() {
		return accountNumber3;
	}

	public void setAccountNumber3(String accountNumber3) {
		this.accountNumber3 = accountNumber3;
	}

	public String getAccountNumber4() {
		return accountNumber4;
	}

	public void setAccountNumber4(String accountNumber4) {
		this.accountNumber4 = accountNumber4;
	}

	public String getTotalFreightCharge() {
		return totalFreightCharge;
	}

	public void setTotalFreightCharge(String totalFreightCharge) {
		this.totalFreightCharge = totalFreightCharge;
	}

	public String getChargeNumber3() {
		return chargeNumber3;
	}

	public void setChargeNumber3(String chargeNumber3) {
		this.chargeNumber3 = chargeNumber3;
	}

	public String getChargeNumber4() {
		return chargeNumber4;
	}

	public void setChargeNumber4(String chargeNumber4) {
		this.chargeNumber4 = chargeNumber4;
	}
	public String getCustomerPartNo() {
		return customerPartNo;
	}
	public void setCustomerPartNo(String customerPartNo) {
		this.customerPartNo = customerPartNo;
	}
	public String getShippingReference() {
		return shippingReference;
	}
	public void setShippingReference(String shippingReference) {
		this.shippingReference = shippingReference;
	}
	public String getCustomerInvoiceNo() {
		return customerInvoiceNo;
	}
	public void setCustomerInvoiceNo(String customerInvoiceNo) {
		this.customerInvoiceNo = customerInvoiceNo;
	}
}