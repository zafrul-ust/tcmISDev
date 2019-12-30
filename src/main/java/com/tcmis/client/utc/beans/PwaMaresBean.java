package com.tcmis.client.utc.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PwaMaresBean <br>
 * @version: 1.0, May 9, 2005 <br>
 *****************************************************************************/


public class PwaMaresBean extends BaseDataBean {

	private String dispositionCode;
	private String remark;
	private String dispositionClock;
	private String shipmentId;
	private String mclReceivingNo;
	private String dispositionDate;
	private String expirationDate;
	private Date tcmLoadDate;
	private String fileName;
	private String processedStatus;
        private String dup;


	//constructor
	public PwaMaresBean() {
	}

	//setters
	public void setDispositionCode(String dispositionCode) {
		this.dispositionCode = dispositionCode;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setDispositionClock(String dispositionClock) {
		this.dispositionClock = dispositionClock;
	}
	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}
	public void setMclReceivingNo(String mclReceivingNo) {
		this.mclReceivingNo = mclReceivingNo;
	}
	public void setDispositionDate(String dispositionDate) {
		this.dispositionDate = dispositionDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public void setTcmLoadDate(Date tcmLoadDate) {
		this.tcmLoadDate = tcmLoadDate;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setProcessedStatus(String processedStatus) {
		this.processedStatus = processedStatus;
	}
        public void setDup(String dup) {
                this.dup = dup;
        }


	//getters
	public String getDispositionCode() {
		return dispositionCode;
	}
	public String getRemark() {
		return remark;
	}
	public String getDispositionClock() {
		return dispositionClock;
	}
	public String getShipmentId() {
		return shipmentId;
	}
	public String getMclReceivingNo() {
		return mclReceivingNo;
	}
	public String getDispositionDate() {
		return dispositionDate;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public Date getTcmLoadDate() {
		return tcmLoadDate;
	}
	public String getFileName() {
		return fileName;
	}
	public String getProcessedStatus() {
		return processedStatus;
	}
        public String getDup() {
                return dup;
        }

}