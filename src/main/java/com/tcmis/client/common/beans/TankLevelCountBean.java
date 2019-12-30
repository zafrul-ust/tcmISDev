package com.tcmis.client.common.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;;

public class TankLevelCountBean extends BaseDataBean {

	private String companyId;
	private String updated;
	private BigDecimal binId;
	private String processErrorMsg;
	private String errorRowNumber;
	private BigDecimal id;
	private Date countDatetime;
	private String countType;
	private BigDecimal afterReplLevelQty; //AFTER_REPL_LEVEL_QTY
	private BigDecimal countQuantity; //COUNT_QUANTITY
	private BigDecimal uploadSequence; //UPLOAD_SEQUENCE
	private String processingStatus;
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public BigDecimal getBinId() {
		return binId;
	}
	public void setBinId(BigDecimal binId) {
		this.binId = binId;
	}
	public String getProcessErrorMsg() {
		return processErrorMsg;
	}
	public void setProcessErrorMsg(String processErrorMsg) {
		this.processErrorMsg = processErrorMsg;
	}
	public String getErrorRowNumber() {
		return errorRowNumber;
	}
	public void setErrorRowNumber(String errorRowNumber) {
		this.errorRowNumber = errorRowNumber;
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public Date getCountDatetime() {
		return countDatetime;
	}
	public void setCountDatetime(Date countDatetime) {
		this.countDatetime = countDatetime;
	}	
	public String getCountType() {
		return countType;
	}
	public void setCountType(String countType) {
		this.countType = countType;
	}
	public BigDecimal getAfterReplLevelQty() {
		return afterReplLevelQty;
	}
	public void setAfterReplLevelQty(BigDecimal afterReplLevelQty) {
		this.afterReplLevelQty = afterReplLevelQty;
	}
	public BigDecimal getCountQuantity() {
		return countQuantity;
	}
	public void setCountQuantity(BigDecimal countQuantity) {
		this.countQuantity = countQuantity;
	}
	public BigDecimal getUploadSequence() {
		return uploadSequence;
	}
	public void setUploadSequence(BigDecimal uploadSequence) {
		this.uploadSequence = uploadSequence;
	}
	public String getProcessingStatus() {
		return processingStatus;
	}
	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
	}
	
	
}
