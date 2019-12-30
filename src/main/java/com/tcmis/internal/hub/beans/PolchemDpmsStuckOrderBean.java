package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PolchemDpmsStuckOrderBean <br>
 * @version: 1.0, Feb 06, 2012 <br>
 *****************************************************************************/


public class PolchemDpmsStuckOrderBean extends BaseDataBean {

	private Date timeStamp;
	private String poNumber;
	private BigDecimal prNumber;
	private String lineItem;
	private String facPartNo;
	private BigDecimal openQuantity;
	private BigDecimal requisitionQuantity;
	private Date dateOrderCreated;
	private Date dateAllocated;

	//constructor
	public PolchemDpmsStuckOrderBean() {
	}

	//setters
	
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}

	public void setOpenQuantity(BigDecimal openQuantity) {
		this.openQuantity = openQuantity;
	}

	public void setRequisitionQuantity(BigDecimal requisitionQuantity) {
		this.requisitionQuantity = requisitionQuantity;
	}

	public void setDateOrderCreated(Date dateOrderCreated) {
		this.dateOrderCreated = dateOrderCreated;
	}

	public void setDateAllocated(Date dateAllocated) {
		this.dateAllocated = dateAllocated;
	}

	//getters
	
	public Date getTimeStamp() {
		return timeStamp;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public String getLineItem() {
		return lineItem;
	}

	public String getFacPartNo() {
		return facPartNo;
	}

	public BigDecimal getOpenQuantity() {
		return openQuantity;
	}

	public BigDecimal getRequisitionQuantity() {
		return requisitionQuantity;
	}

	public Date getDateOrderCreated() {
		return dateOrderCreated;
	}

	public Date getDateAllocated() {
		return dateAllocated;
	}
}