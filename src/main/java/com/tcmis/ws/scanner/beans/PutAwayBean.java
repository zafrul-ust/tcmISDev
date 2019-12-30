package com.tcmis.ws.scanner.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class PutAwayBean extends BaseDataBean {
	BigDecimal	applicationId;
	BigDecimal	cabinetId;
	BigDecimal	containerNumber;
	BigDecimal	issueId;
	String		processErrorMsg;
	String		processingStatus;
	BigDecimal	receiptId;
	BigDecimal	shipmentId;
	BigDecimal	stageId;
	BigDecimal	uploadSequence;

	public BigDecimal getApplicationId() {
		return applicationId;
	}

	public BigDecimal getContainerNumber() {
		return this.containerNumber;
	}

	public BigDecimal getIssueId() {
		return this.issueId;
	}
	
	public String getError() {
		if (StringUtils.isBlank(processErrorMsg)) {
			return getProcessingStatus() + ("IGNORED".equals(processingStatus) ? " - Duplicate" : " - Not Processed");
		}
		else {
			return getProcessErrorMsg();
		}
	}

	public String getProcessErrorMsg() {
		return processErrorMsg;
	}

	public String getProcessingStatus() {
		return processingStatus;
	}

	public BigDecimal getReceiptId() {
		return this.receiptId;
	}

	public BigDecimal getShipmentId() {
		return shipmentId;
	}

	public BigDecimal getStageId() {
		return stageId;
	}

	public BigDecimal getUploadSequence() {
		return uploadSequence;
	}

	public boolean isContainerPutAway() {
		return containerNumber != null && containerNumber.intValue() != 0;
	}

	public boolean matches(JSONObject json) {
		try {
			if (isContainerPutAway()) {
				if (containerNumber.intValue() == json.getInt("containerId") && issueId.intValue() == json.getInt("issueId") && receiptId.intValue() == json.getInt("receiptId")) {
					return true;
				}
			}
			else {
				if (applicationId.intValue() == json.getInt("cabinetId") && shipmentId.intValue() == json.getInt("shipmentId")) {
					return true;
				}				
			}
		}
		catch (JSONException e) {
		}
		return false;
	}

	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}

	public void setContainerNumber(BigDecimal containerNumber) {
		this.containerNumber = containerNumber;
	}

	public void setIssueId(BigDecimal issueId) {
		this.issueId = issueId;
	}

	public void setProcessErrorMsg(String processingErrorMsg) {
		this.processErrorMsg = processingErrorMsg;
	}

	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}

	public void setStageId(BigDecimal stageId) {
		this.stageId = stageId;
	}

	public void setUploadSequence(BigDecimal uploadSequence) {
		this.uploadSequence = uploadSequence;
	}
}
