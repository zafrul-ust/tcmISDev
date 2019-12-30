package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.DateHandler;
import com.tcmis.ws.scanner.beans.ScanDataBean;

/******************************************************************************
 * CLASSNAME: CabinetItemInventoryCountBean <br>
 * 
 * @version: 1.0, Sep 7, 2007 <br>
 *****************************************************************************/

public class CabinetItemInventoryCountBean extends BaseDataBean {

	private BigDecimal	applicationId;
	private BigDecimal	binId;
	private String		companyId;
	private Date		countDatetime;
	private BigDecimal	countQuantity;
	private String		countSource;
	private String		countStatus;
	private String		countType;
	private Date		dateProcessed;
	private String		disbursedUnitOfMeasure;
	private BigDecimal	itemId;
	private BigDecimal	kanbanBinScanQty;
	private BigDecimal	personnelId;
	private String		receiptDamagedQty	= "";
	private String		receiptExpiredQty	= "";
	private String		receiptId;
	private String		receiptQty;
	private String		updatedStatus;
	private BigDecimal	uploadSequence;
	private boolean fullScan = true;

	// constructor
	public CabinetItemInventoryCountBean() {
	}

	public CabinetItemInventoryCountBean(BigDecimal uploadSeq, JSONObject scan) throws JSONException, BaseException {
		setBinId(scan.has("binId") ? new BigDecimal(scan.getInt("binId")) : null);
		setCountSource("SCANNER");
		setUploadSequence(uploadSeq);
		if (scan.has("receiptId")) {
			setReceiptId("" + scan.getInt("receiptId"));
		}
		setCountDatetime(DateHandler.getDateFromIso8601String(scan.getString("scanDate")));
		setCountQuantity(new BigDecimal(scan.getInt("countQuantity")));
		if (scan.has("itemId")) {
			setItemId(new BigDecimal(scan.getInt("itemId")));
		}
		else if (scan.has("receiptId")) {
			setItemId(new BigDecimal(scan.getInt("receiptId")));
		}
		setPersonnelId(new BigDecimal(scan.getInt("personnelId")));
		setCountType(scan.getString("countType"));
		setCompanyId(scan.getString("companyId"));
		if (scan.has("applicationId")) {
			setApplicationId(new BigDecimal(scan.getInt("applicationId")));
		}
		if (scan.has("countStatus")) {
			setCountStatus(scan.getString("countStatus"));
		}
		if (scan.has("fullScan")) {
			String value = scan.getString("fullScan"); 
			setFullScan(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("Y"));
		}
	}

	public CabinetItemInventoryCountBean(ScanDataBean scannedBean, BigDecimal uploadSeq) {
		setBinId(scannedBean.getBinId());
		this.setCountSource("SCANNER");
		this.setUploadSequence(uploadSeq);
		this.setReceiptId("" + scannedBean.getReceiptId());
		this.setItemId(scannedBean.getReceiptId());
		this.setCountType(scannedBean.getCountType());
		this.setCountQuantity(scannedBean.getCountQuantity());
		this.setCountDatetime(scannedBean.getCountDatetime());
		this.setPersonnelId(scannedBean.getPersonnelId());
		this.setCompanyId(scannedBean.getCompanyId());
	}

	public BigDecimal getApplicationId() {
		return this.applicationId;
	}

	// getters
	public BigDecimal getBinId() {
		return binId;
	}

	public String getCompanyId() {
		return companyId;
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

	public String getDisbursedUnitOfMeasure() {
		return disbursedUnitOfMeasure;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public BigDecimal getKanbanBinScanQty() {
		return kanbanBinScanQty;
	}

	public BigDecimal getPersonnelId() {
		return personnelId;
	}

	public String getReceiptDamagedQty() {
		return receiptDamagedQty;
	}

	public String getReceiptExpiredQty() {
		return receiptExpiredQty;
	}

	public String getReceiptId() {
		return receiptId;
	}

	public String getReceiptQty() {
		return receiptQty;
	}

	public String getUpdatedStatus() {
		return updatedStatus;
	}

	public BigDecimal getUploadSequence() {
		return uploadSequence;
	}

	public boolean hasApplicationId() {
		return applicationId != null;
	}

	public boolean hasCountDateTime() {
		return countDatetime != null;
	}

	public boolean hasCountQuantity() {
		return countQuantity != null;
	}

	public boolean hasKanbanQuantity() {
		return kanbanBinScanQty != null;
	}

	public boolean hasReceiptId() {
		return StringUtils.isNotBlank(receiptId);
	}

	public boolean isAdvancedReceipt() {
		return "ADVRECEIPT".equals(countType);
	}

	public boolean isCountByReceiptItem() {
		return "T".equals(countType);
	}

	public boolean isItemCountStatusWaste() {
		return StringUtils.isNotBlank(countStatus) && countStatus.startsWith("W");
	}
	
	public boolean isAutomaticRefill() {
		// return "AutomaticRefill".equals(countType);
		return !isAdvancedReceipt() && StringUtils.isNotBlank(countType) && countType.startsWith("A");
	}

	public boolean isCountByItemId() {
		return StringUtils.isNotBlank(countType) && countType.startsWith("I");
	}

	public boolean isCountByLevel() {
		return StringUtils.isNotBlank(countType) && countType.startsWith("L");
	}

	public boolean isCountByPart() {
		return StringUtils.isNotBlank(countType) && countType.startsWith("P");
	}

	public boolean isCountByReceipt() {
		return StringUtils.isNotBlank(countType) && countType.startsWith("R");
	}

	public boolean isDisbursementCount() {
		return "DISBURSEMENT".equals(countType);
	}

	public boolean isKanBanCount() {
		// return "KanBan".equals(countType);
		return StringUtils.isNotBlank(countType) && countType.startsWith("K");
	}

	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}

	public void setBinId(BigDecimal binId) {
		this.binId = binId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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

	public void setDisbursedUnitOfMeasure(String disbursedUnitOfMeasure) {
		this.disbursedUnitOfMeasure = disbursedUnitOfMeasure;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setKanbanBinScanQty(BigDecimal kanbanBinScanQty) {
		this.kanbanBinScanQty = kanbanBinScanQty;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	public void setReceiptDamagedQty(String receiptDamagedQty) {
		this.receiptDamagedQty = receiptDamagedQty;
	}

	public void setReceiptExpiredQty(String receiptExpiredQty) {
		this.receiptExpiredQty = receiptExpiredQty;
	}

	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}

	public void setReceiptQty(String receiptQty) {
		this.receiptQty = receiptQty;
	}

	public void setUpdatedStatus(String updatedStatus) {
		this.updatedStatus = updatedStatus;
	}

	public void setUploadSequence(BigDecimal uploadSequence) {
		this.uploadSequence = uploadSequence;
	}

	public boolean isFullScan() {
		return this.fullScan;
	}

	public void setFullScan(boolean fullScan) {
		this.fullScan = fullScan;
	}

	public String getCountStatus() {
		return this.countStatus;
	}

	public void setCountStatus(String countStatus) {
		this.countStatus = countStatus;
	}
}