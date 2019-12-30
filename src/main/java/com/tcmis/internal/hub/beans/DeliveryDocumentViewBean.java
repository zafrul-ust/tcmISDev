package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DeliveryDocumentViewBean <br>
 * @version: 1.0, Oct 8, 2008 <br>
 *****************************************************************************/


public class DeliveryDocumentViewBean extends BaseDataBean {

	private BigDecimal picklistId;
	private BigDecimal prNumber;
	private String lineItem;
	private String fileName;
	private String documentType;
	private String printOk;

//constructor
	public DeliveryDocumentViewBean() {
	}

	//setters
	public void setPicklistId(BigDecimal picklistId) {
		this.picklistId = picklistId;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
  public void setPrintOk(String printOk) {
    this.printOk = printOk;
  }

	//getters
	public BigDecimal getPicklistId() {
		return picklistId;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getFileName() {
		return fileName;
	}
	public String getDocumentType() {
		return documentType;
	}
  public String getPrintOk() {
    return printOk;
  }
}