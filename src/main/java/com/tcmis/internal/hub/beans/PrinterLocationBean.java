package com.tcmis.internal.hub.beans;

import java.util.Collection;

import com.tcmis.common.framework.BaseDataBean;

public class PrinterLocationBean extends BaseDataBean {
	
	private String printerLocation;
	private String companyId;
	private String supplier;
	private String supplierLocationId;
	private String facilityId;
	private String hub;
	private String uAction;
	
	 //constructor
	  public PrinterLocationBean() {
	  }

	public String getPrinterLocation() {
		return printerLocation;
	}

	public void setPrinterLocation(String printerLocation) {
		this.printerLocation = printerLocation;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getSupplierLocationId() {
		return supplierLocationId;
	}

	public void setSupplierLocationId(String supplierLocationId) {
		this.supplierLocationId = supplierLocationId;
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

	
	public String getuAction() {
		return uAction;
	}

	public void setuAction(String uAction) {
		this.uAction = uAction;
	}
	
		  

}
