package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.exceptions.BeanCopyException;
import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.BeanHandler;

public class ShippingSampleInputBean extends BaseInputBean {
	private String companyId;
	private String facilityId;
	private String hub;
	private BigDecimal labelQuantity;
	private String labelType;
	private BigDecimal quantity;
	private BigDecimal receiptId;
	private String shipToLocationAddressDisplay;
	private String shipToLocationId;

	// constructor
	public ShippingSampleInputBean() {
	}
	
	public ShippingSampleInputBean(ActionForm form) throws BeanCopyException {
		BeanHandler.copyAttributes(form, this);
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isStoreAndPrintSampleData() {
		return "storeAndPrintSampleData".equalsIgnoreCase(getuAction());
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public String getFacilityId() {
		return facilityId;
	}
	
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getHub() {
		return hub;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public BigDecimal getLabelQuantity() {
		return labelQuantity;
	}

	public void setLabelQuantity(BigDecimal labelQuantity) {
		this.labelQuantity = labelQuantity;
	}

	public String getLabelType() {
		return labelType;
	}

	public void setLabelType(String labelType) {
		this.labelType = labelType;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public String getShipToLocationAddressDisplay() {
		return shipToLocationAddressDisplay;
	}

	public void setShipToLocationAddressDisplay(String shipToLocationAddressDisplay) {
		this.shipToLocationAddressDisplay = shipToLocationAddressDisplay;
	}

	public String getShipToLocationId() {
		return shipToLocationId;
	}

	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
}