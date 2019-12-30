package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

@SuppressWarnings("serial")
public class HubPrinter extends BaseDataBean {

	private String hub;
	private BigDecimal printerId;
	private String name;
	private String printerPath;
	private String printerLocation;
	private Date lastUpdated;
	private String labelStock;
	
	public HubPrinter() {}

	public String getHub() {
		return hub;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public BigDecimal getPrinterId() {
		return printerId;
	}

	public void setPrinterId(BigDecimal printerId) {
		this.printerId = printerId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getPrinterPath() {
		return printerPath;
	}

	public void setPrinterPath(String printerPath) {
		//this.printerPath = printerPath;
		if (printerPath.contains("\\")) {
			this.name = printerPath.substring(printerPath.lastIndexOf('\\')+1,printerPath.length());
		}
		else {
			this.name = printerPath;
		}
	}

	public String getPrinterLocation() {
		return printerLocation;
	}

	public void setPrinterLocation(String printerLocation) {
		this.printerLocation = printerLocation;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getLabelStock() {
		return labelStock;
	}

	public void setLabelStock(String labelStock) {
		this.labelStock = labelStock;
	}
}
