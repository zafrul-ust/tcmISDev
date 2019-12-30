package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.ws.scanner.beans.ScanDataBean;

/******************************************************************************
 * CLASSNAME: WorkAreaBinCountBean <br>
 * 
 * @version: 1.0, Jan 21, 2014 <br>
 *****************************************************************************/

public class WorkAreaBinCountBean extends BaseDataBean {

	private BigDecimal	binId;
	private BigDecimal	cabinetCountId;
	private String		companyId;
	private String		containerCondition;
	private String		containerExpired;
	private BigDecimal	containerNumber;
	private String		containerOpen;
	private Date		countDatetime;
	private BigDecimal	countQuantity;
	private String		countSource;
	private String		countType;
	private Date		dateProcessed;
	private BigDecimal	inventoryQuantity;
	private Date		modifiedTimestamp;
	private BigDecimal	personnelId;
	private Date		priorCountDatetime;
	private BigDecimal	priorCountQuantity;
	private String		processErrorMsg;
	private String		processingStatus;
	private BigDecimal	receiptId;
	private String		receiptProcessingMethod;
	private BigDecimal	replenishQtySincePriorCt;
	private BigDecimal	reportingItemId;
	private String		unitOfMeasurement;
	private BigDecimal	uploadSequence;
	private BigDecimal	usageQuantity;

	// constructor
	public WorkAreaBinCountBean() {
	}

	public WorkAreaBinCountBean(BigDecimal uploadSeq, JSONObject scan) throws JSONException, BaseException {
		setBinId(new BigDecimal(scan.getInt("binId")));
		setCountSource("SCANNER");
		setUploadSequence(uploadSeq);
		setCompanyId(getJSONString(scan, "companyId"));
		setCountDatetime(DateHandler.getDateFromIso8601String(getJSONString(scan, "scanDate")));
		setCountQuantity(new BigDecimal(scan.getDouble("countQuantity")));
		setPersonnelId(new BigDecimal(scan.getInt("personnelId")));
		if (scan.has("countType") && (scan.getString("countType").startsWith("C") || scan.getString("countType").startsWith("A"))) {
			setContainerCondition(getJSONString(scan, "containerCondition"));
			if (scan.getString("countType").startsWith("C")) {
				setCountType("CONTAINER");
			}
			else {
				setCountType("ADVRECEIPT");
			}

			setReceiptId(new BigDecimal(scan.getInt("receiptId")));
			setContainerNumber(new BigDecimal(scan.getInt("containerNumber")));
			
			if (StringUtils.isBlank(containerCondition) && scan.has("containerStatus") && scan.getString("containerStatus").startsWith("D")) {
				setContainerCondition(getJSONString(scan, "containerStatus"));
			}
			
			String condition = getContainerCondition();
			if ("Expired".equals(condition)) {
				setContainerExpired("Y");
				setContainerCondition(null);
			}
			else {
				setContainerExpired("N");
			}
			if ("Open".equals(condition)) {
				setContainerOpen("Y");
			}
			else {
				setContainerOpen("N");
			}
		}
		else if (scan.has("countType") && scan.getString("countType").startsWith("D")) {
			setCountType("DISBURSEMENT");
			setContainerNumber(new BigDecimal(0));
			setUnitOfMeasurement(getJSONString(scan,"unitOfMeasurement"));
		}
		else {
			setCountType("RECEIPT_ID");
			setReceiptId(new BigDecimal(scan.getInt("receiptId")));
			setContainerNumber(new BigDecimal(0));
		}
	}

	public WorkAreaBinCountBean(ScanDataBean scannedBean, BigDecimal uploadSeq) {
		this.setBinId(scannedBean.getBinId());
		this.setCompanyId(scannedBean.getCompanyId());
		this.setContainerNumber(scannedBean.getContainerNumber() != null ? scannedBean.getContainerNumber() : new BigDecimal(0));
		if (scannedBean.isContainerExpired()) this.setContainerExpired("Y");
		else
			this.setContainerExpired("N");
		if (scannedBean.isContainerOpened()) this.setContainerOpen("Y");
		else
			this.setContainerOpen("N");
		this.setCountDatetime(scannedBean.getCountDatetime());
		this.setCountQuantity(scannedBean.getCountQuantity());
		this.setCountSource("SCANNER");
		this.setCountType(scannedBean.getContainerTypeDbStr());
		this.setPersonnelId(scannedBean.getPersonnelId());
		this.setReceiptId(scannedBean.getReceiptId());
		this.setUploadSequence(uploadSeq);
		this.setContainerCondition(scannedBean.getContainerCondition());
	}

	public BigDecimal getBinId() {
		return binId;
	}

	public BigDecimal getCabinetCountId() {
		return cabinetCountId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getContainerCondition() {
		return containerCondition;
	}

	public String getContainerExpired() {
		return containerExpired;
	}

	public BigDecimal getContainerNumber() {
		return containerNumber;
	}

	public String getContainerOpen() {
		return containerOpen;
	}

	public Date getCountDatetime() {
		return countDatetime;
	}

	public BigDecimal getCountQuantity() {
		return countQuantity;
	}

	public String getCountSource() {
		return countSource;
	}

	public String getCountType() {
		return countType;
	}

	public Date getDateProcessed() {
		return dateProcessed;
	}

	public BigDecimal getInventoryQuantity() {
		return inventoryQuantity;
	}

	private String getJSONString(JSONObject scan, String name) throws JSONException {
		if (scan.has(name) && !"null".equals(scan.getString(name))) {
			return scan.getString(name);
		}
		return null;
	}

	public Date getModifiedTimestamp() {
		return modifiedTimestamp;
	}

	public BigDecimal getPersonnelId() {
		return personnelId;
	}

	public Date getPriorCountDatetime() {
		return priorCountDatetime;
	}

	public BigDecimal getPriorCountQuantity() {
		return priorCountQuantity;
	}

	public String getProcessErrorMsg() {
		return processErrorMsg;
	}

	public String getProcessingStatus() {
		return processingStatus;
	}

	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public String getReceiptProcessingMethod() {
		return receiptProcessingMethod;
	}

	public BigDecimal getReplenishQtySincePriorCt() {
		return replenishQtySincePriorCt;
	}

	public BigDecimal getReportingItemId() {
		return reportingItemId;
	}

	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public BigDecimal getUploadSequence() {
		return uploadSequence;
	}

	public BigDecimal getUsageQuantity() {
		return usageQuantity;
	}

	public boolean hasContainerExpired() {
		return StringUtils.isNotBlank(containerExpired);
	}

	public boolean hasContainerOpen() {
		return StringUtils.isNotBlank(containerOpen);
	}

	public boolean hasCountSource() {
		return StringUtils.isNotBlank(countSource);
	}

	public boolean isCountByAdvancedReceipt() {
		return "ADVRECEIPT".equals(getCountType());
	}

	public boolean isCountByContainer() {
		return "CONTAINER".equals(getCountType());
	}

	public boolean isCountByReceipt() {
		return "RECEIPT_ID".equals(getCountType());
	}

	public void setBinId(BigDecimal binId) {
		this.binId = binId;
	}

	public void setCabinetCountId(BigDecimal cabinetCountId) {
		this.cabinetCountId = cabinetCountId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setContainerCondition(String containerCondition) {
		this.containerCondition = containerCondition;
	}

	public void setContainerExpired(String containerExpired) {
		this.containerExpired = containerExpired;
	}

	public void setContainerNumber(BigDecimal containerNumber) {
		this.containerNumber = containerNumber;
	}

	public void setContainerOpen(String containerOpen) {
		this.containerOpen = containerOpen;
	}

	public void setCountDatetime(Date countDatetime) {
		this.countDatetime = countDatetime;
	}

	public void setCountQuantity(BigDecimal countQuantity) {
		this.countQuantity = countQuantity;
	}

	public void setCountSource(String countSource) {
		this.countSource = countSource;
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}

	public void setDateProcessed(Date dateProcessed) {
		this.dateProcessed = dateProcessed;
	}

	public void setInventoryQuantity(BigDecimal inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}

	public void setModifiedTimestamp(Date modifiedTimestamp) {
		this.modifiedTimestamp = modifiedTimestamp;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	public void setPriorCountDatetime(Date priorCountDatetime) {
		this.priorCountDatetime = priorCountDatetime;
	}

	public void setPriorCountQuantity(BigDecimal priorCountQuantity) {
		this.priorCountQuantity = priorCountQuantity;
	}

	public void setProcessErrorMsg(String processErrorMsg) {
		this.processErrorMsg = processErrorMsg;
	}

	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public void setReceiptProcessingMethod(String receiptProcessingMethod) {
		this.receiptProcessingMethod = receiptProcessingMethod;
	}

	public void setReplenishQtySincePriorCt(BigDecimal replenishQtySincePriorCt) {
		this.replenishQtySincePriorCt = replenishQtySincePriorCt;
	}

	public void setReportingItemId(BigDecimal reportingItemId) {
		this.reportingItemId = reportingItemId;
	}

	public void setUnitOfMeasurement(String unit) {
		this.unitOfMeasurement = unit;
	}

	public void setUploadSequence(BigDecimal uploadSequence) {
		this.uploadSequence = uploadSequence;
	}

	public void setUsageQuantity(BigDecimal usageQuantity) {
		this.usageQuantity = usageQuantity;
	}

} // end of class