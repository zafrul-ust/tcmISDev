package com.tcmis.internal.invoice.beans;

import java.util.Date;
import java.util.Collection;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: InvoiceFormatViewNrgBean <br>
 * @version: 1.0, Jul 31, 2008 <br>
 *****************************************************************************/


public class InvoiceFormatViewNrgBean extends BaseDataBean {
	private String locationDesc;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String city;
	private String stateAbbrev;
	private String countryAbbrev;
	private String zip;
	private String attention;
	private String invoiceInquiries;
	private String contactPhone;
	private String contactEmail;
	private BigDecimal invoice;
	private String invoiceGroup;
	private BigDecimal invoicePeriod;
	private Date startDate;
	private Date endDate;
	private Date invoiceDate;
	private String facilityId;
	private String region;
	private BigDecimal taxRate;
	private String partNumber;
	private String partDescription;
	private String itemType;
	private String packaging;
	private Date dateDelivered;
	private BigDecimal quantity;
	private BigDecimal invoiceUnitPrice;
	private BigDecimal totalAddCharge;
	private BigDecimal serviceFee;
	private BigDecimal totalSalesTax;
	private BigDecimal netAmount;
	private String currencyId;
	private BigDecimal prNumber;
	private String lineItem;
	private String poNumber;
	private BigDecimal radianPo;
	private String accountNumber;
	private String mfgLot;

    private Collection detail = new Vector();
    private BigDecimal lineCount;
    //constructor
	public InvoiceFormatViewNrgBean() {
	}
	//setters
	public void setLocationDesc(String locationDesc) {
	   this.locationDesc = locationDesc;
	}  
    public void setAddressLine1(String addressLine1) {
	    this.addressLine1 = addressLine1;
    }  
    public void setAddressLine2(String addressLine2) {
	    this.addressLine2 = addressLine2;
    }  
    public void setAddressLine3(String addressLine3) {
	  this.addressLine3 = addressLine3;
    }  
    public void setCity(String city) {
	  this.city = city;
    }  
    public void setStateAbbrev(String stateAbbrev) {
	  this.stateAbbrev = stateAbbrev;
    }  
    public void setCountryAbbrev(String countryAbbrev) {
	  this.countryAbbrev = countryAbbrev;
    }  
    public void setZip(String zip) {
	  this.zip = zip;
    }  
    public void setAttention(String attention) {
	  this.attention = attention;
    }  
    public void setInvoiceInquiries(String invoiceInquiries) {
	  this.invoiceInquiries = invoiceInquiries;
    }  
    public void setContactPhone(String contactPhone) {
	  this.contactPhone = contactPhone;
    }  
    public void setContactEmail(String contactEmail) {
	  this.contactEmail = contactEmail;
    }  
	public void setInvoice(BigDecimal invoice) {
		this.invoice = invoice;
	}
	public void setInvoiceGroup(String invoiceGroup) {
		this.invoiceGroup = invoiceGroup;
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
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setInvoiceUnitPrice(BigDecimal invoiceUnitPrice) {
		this.invoiceUnitPrice = invoiceUnitPrice;
	}
	public void setTotalAddCharge(BigDecimal totalAddCharge) {
		this.totalAddCharge = totalAddCharge;
	}
	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}
	public void setTotalSalesTax(BigDecimal totalSalesTax) {
		this.totalSalesTax = totalSalesTax;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
    public void setDetail(Collection c) {
		this.detail = c;
	}
    public void setLineCount(BigDecimal lineCount) {
        this.lineCount = lineCount;
    }
    public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
    public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}

    //getters
    public String getLocationDesc() {
    	return locationDesc;
    }	
    public String getAddressLine1() {
    	return addressLine1;
    }		
    public String getAddressLine2() {
    	return addressLine2;
    }	
    public String getAddressLine3() {
    	return addressLine3;
    }			
    public String getCity() {
    	return city;
    }	
    public String getStateAbbrev() {
    	return stateAbbrev;
    }	
    public String getCountryAbbrev() {
    	return countryAbbrev;
    }	
    public String getZip() {
    	return zip;
    }			
    public String getAttention() {
    	return attention;
    }	
    public String getInvoiceInquiries() {
    	return invoiceInquiries;
    }	
    public String getContactPhone() {
    	return contactPhone;
    }		
    public String getContactEmail() {
    	return contactEmail;
    }	
	public BigDecimal getInvoice() {
		return invoice;
	}
	public String getInvoiceGroup() {
		return invoiceGroup;
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
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getRegion() {
		return region;
	}
	public BigDecimal getTaxRate() {
		return taxRate;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public String getItemType() {
		return itemType;
	}
	public String getPackaging() {
		return packaging;
	}
	public Date getDateDelivered() {
		return dateDelivered;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public BigDecimal getInvoiceUnitPrice() {
		return invoiceUnitPrice;
	}
	public BigDecimal getTotalAddCharge() {
		return totalAddCharge;
	}
	public BigDecimal getServiceFee() {
		return serviceFee;
	}
	public BigDecimal getTotalSalesTax() {
		return totalSalesTax;
	}
	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
    public Collection getDetail() {
		return detail;
	}
    public BigDecimal getLineCount() {
        return lineCount;
    }
    public String getAccountNumber() {
		return accountNumber;
	}
	public String getMfgLot() {
		return mfgLot;
	}
}