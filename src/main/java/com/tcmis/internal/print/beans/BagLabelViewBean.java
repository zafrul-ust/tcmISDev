package com.tcmis.internal.print.beans;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import radian.tcmis.common.util.StringHandler;

import com.tcmis.common.framework.BaseDataBean;


public class BagLabelViewBean extends BaseDataBean 
{
	private String application;
	private String catPartNo;
	private String companyId;
	private String customerPoNo;
	private String deliveryPoint;
	private String facilityId;
	private String facilityName;
	private String inventoryGroup;
	private String lineItem;
	private String mfgLot;
	private String partDesc;	
	private BigDecimal picklistId;
	private BigDecimal prNumber;
	private BigDecimal quantity;
	private String shippingReference;
	
	//constructor
	public BagLabelViewBean() {
	}

	public String getApplication() {
		return application;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getCustomerPoNo() {
		return customerPoNo;
	}

	public String getDeliveryPoint() {
		return deliveryPoint;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getLineItem() {
		return lineItem;
	}

	public String getMfgLot() {
		return mfgLot;
	}

	public String getPartDesc() {
		return partDesc;
	}

	public BigDecimal getPicklistId() {
		return picklistId;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public String getShippingReference() {
		return shippingReference;
	}
	
	public String getDeliveryShipment() {	
		String ret = "";
		
		if(!StringHandler.isBlankString(getShippingReference())){
			Pattern p = Pattern.compile("\\d+.\\d+");
			Matcher m = p.matcher(getShippingReference());
			
			if(m.find())
				ret = m.group();
		}
		
		return ret;
	}
	
	public String getCustomerPoLine() {
		String ret = "";
		
		if(!StringHandler.isBlankString(getShippingReference())){
			Pattern p = Pattern.compile("Customer TO Line #:\\s+\\d*");
			Matcher m = p.matcher(getShippingReference());
			
			if(m.find()){
				String subStr = m.group();
				
				p = p.compile("\\d+");
				m = p.matcher(subStr);
				
				if(m.find())
					ret = m.group();
			}
		}
		
		return ret;
	}
	
	public String getCustomerShipTo() {
		String ret = "";
		
		if(!StringHandler.isBlankString(getShippingReference())){
			Pattern p = Pattern.compile("Customer Ship To #:\\s+\\d*");
			Matcher m = p.matcher(getShippingReference());
			
			if(m.find()){
				String subStr = m.group();
				
				p = p.compile("\\d+");
				m = p.matcher(subStr);
				
				if(m.find())
					ret = m.group();
			}
		}
		
		return ret;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCustomerPoNo(String customerPoNo) {
		this.customerPoNo = customerPoNo;
	}

	public void setDeliveryPoint(String deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}

	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}

	public void setPicklistId(BigDecimal picklistId) {
		this.picklistId = picklistId;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public void setShippingReference(String shippingReference) {
		this.shippingReference = shippingReference;
	}

	
}