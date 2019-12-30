package com.tcmis.client.raytheon.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: FtpMaterialRequisitionBean <br>
 * @version: 1.0, Sep 19, 2006 <br>
 *****************************************************************************/


public class FtpMaterialRequisitionBean extends BaseDataBean {

	private String ftpFilename;
	private BigDecimal fileLine;
	private String facilityId;
	private String application;
	private String deliveryPoint;
	private String catPartNo;
	private String chargeNo;
	private BigDecimal orderQuantity;
	private Date tcmLoadDate;
	private Date processedDate;
	private String processedStatus;


	//constructor
	public FtpMaterialRequisitionBean() {
	}

	//setters
	public void setFtpFilename(String ftpFilename) {
		this.ftpFilename = ftpFilename;
	}
	public void setFileLine(BigDecimal fileLine) {
		this.fileLine = fileLine;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setDeliveryPoint(String deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setChargeNo(String chargeNo) {
		this.chargeNo = chargeNo;
	}
	public void setOrderQuantity(BigDecimal orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public void setTcmLoadDate(Date tcmLoadDate) {
		this.tcmLoadDate = tcmLoadDate;
	}
	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}
	public void setProcessedStatus(String processedStatus) {
		this.processedStatus = processedStatus;
	}


	//getters
	public String getFtpFilename() {
		return ftpFilename;
	}
	public BigDecimal getFileLine() {
		return fileLine;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getApplication() {
		return application;
	}
	public String getDeliveryPoint() {
		return deliveryPoint;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getChargeNo() {
		return chargeNo;
	}
	public BigDecimal getOrderQuantity() {
		return orderQuantity;
	}
	public Date getTcmLoadDate() {
		return tcmLoadDate;
	}
	public Date getProcessedDate() {
		return processedDate;
	}
	public String getProcessedStatus() {
		return processedStatus;
	}
}