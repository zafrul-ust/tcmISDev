package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.ws.scanner.beans.ScanDataBean;

/******************************************************************************
 * CLASSNAME: CabinetInventoryCountStageBean <br>
 * 
 * @version: 1.0, Jul 27, 2006 <br>
 *****************************************************************************/

public class CabinetInventoryCountStageBean extends BaseDataBean {

	private BigDecimal	binId;
	private String		companyId;
	private Date		countDatetime;
	private BigDecimal	countQuantity;
	private String		countType;
	private Date		dateProcessed;
	private boolean		editableReceipt;
	private BigDecimal	personnelId;
	private String		receiptDamagedQty	= "";
	private String		receiptExpiredQty	= "";
	private BigDecimal	receiptId;
	private BigDecimal	uploadSequence;

	// constructor
	public CabinetInventoryCountStageBean() {
	}

	public CabinetInventoryCountStageBean(BigDecimal uploadSeq, JSONObject scan) throws JSONException, BaseException {
		setUploadSequence(uploadSeq);
		setReceiptId(new BigDecimal(scan.getInt("receiptId")));
		setCountDatetime(DateHandler.getDateFromIso8601String(scan.getString("scanDate")));
		setCountQuantity(new BigDecimal(scan.getInt("countQuantity")));
		setCompanyId(scan.getString("companyId"));
		setBinId(new BigDecimal(scan.getInt("binId")));
		setPersonnelId(new BigDecimal(scan.getInt("personnelId")));
		setCountType(scan.getString("countType"));
	}

	public CabinetInventoryCountStageBean(ScanDataBean scannedBean, BigDecimal uploadSeq) {
		this.setUploadSequence(uploadSeq);
		this.setReceiptId(scannedBean.getReceiptId());
		this.setCountQuantity(scannedBean.getCountQuantity());
		this.setCountDatetime(scannedBean.getCountDatetime());
		this.setCountType(scannedBean.getCountType());
		this.setCompanyId(scannedBean.getCompanyId());
		this.setBinId(scannedBean.getBinId());
		this.setPersonnelId(scannedBean.getPersonnelId());
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

	public String getCountType() {
		return countType;
	}

	public Date getDateProcessed() {
		return dateProcessed;
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

	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public BigDecimal getUploadSequence() {
		return uploadSequence;
	}

	public boolean isCountByItem() {
		return "I".equals(getCountType());
	}

	public boolean isCountByReceipt() {
		return StringHandler.isBlankString(getCountType()) || "R".equals(getCountType());
	}

	public boolean isEditableReceipt() {
		return editableReceipt;
	}

	// setters
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

	public void setCountType(String countType) {
		this.countType = countType;
	}

	public void setDateProcessed(Date dateProcessed) {
		this.dateProcessed = dateProcessed;
	}

	public void setEditableReceipt(boolean editableReceipt) {
		this.editableReceipt = editableReceipt;
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

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public void setUploadSequence(BigDecimal uploadSequence) {
		this.uploadSequence = uploadSequence;
	}
}