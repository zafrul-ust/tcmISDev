package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.TimeZone;

import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: CabinetInventoryCountStageBean <br>
 * 
 * @version: 1.0, Jul 27, 2006 <br>
 *****************************************************************************/

public class ScanDataBean {

	private BigDecimal	binId;
	private String		companyId;
	private String		containerCondition; // [null, NRFI, Expired]
	private BigDecimal	containerNumber;
	private String		containerStatus;	// [New, Open, Unlabeled, Damaged]
	private Date		countDatetime;
	private String		countDatetimeStr;
	private BigDecimal	countQuantity;
	private String		countType;
	private BigDecimal	personnelId;
	private BigDecimal	receiptId;

	// constructor
	public ScanDataBean() {
	}

	// getters
	public BigDecimal getBinId() {
		return binId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getContainerCondition() {
		return containerCondition;
	}

	public BigDecimal getContainerNumber() {
		return containerNumber;
	}

	public String getContainerStatus() {
		return containerStatus;
	}

	// the reason for this method is to convert container type to match our
	// database
	public String getContainerTypeDbStr() {
		if (isCountByContainer()) return "CONTAINER";
		else if (isCountByKanban()) return "KanBan";
		else if (isCountByItem()) return "ITEM_ID";
		else if (isCountByReceipt()) return "RECEIPT_ID";
		else
			return getCountType();
	}

	public Date getCountDatetime() {
		return countDatetime;
	}

	public String getCountDatetimeStr() {
		return countDatetimeStr;
	}

	public BigDecimal getCountQuantity() {
		return countQuantity;
	}

	public String getCountType() {
		return countType;
	}

	public BigDecimal getPersonnelId() {
		return personnelId;
	}

	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public boolean isContainerExpired() {
		return "Expired".equals(this.getContainerCondition());
	}

	public boolean isContainerOpened() {
		return "Open".equals(this.getContainerStatus());
	}

	public boolean isCountByAdvancedReceipt() {
		return "ADVRECEIPT".equals(getCountType());
	}

	public boolean isCountByContainer() {
		return "C".equals(getCountType());
	}

	public boolean isCountByItem() {
		return "I".equals(getCountType());
	}

	public boolean isCountByKanban() {
		return "K".equals(getCountType());
	}

	public boolean isCountByReceipt() {
		return StringHandler.isBlankString(getCountType()) || "R".equals(getCountType());
	}

	// setters
	public void setBinId(BigDecimal binId) {
		this.binId = binId;
	}

	public void setBinIdStr(String s) {
		if (s != null && s.length() != 0) setBinId(new BigDecimal(s));
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setContainerCondition(String containerCondition) {
		this.containerCondition = containerCondition;
	}

	public void setContainerNumber(BigDecimal containerNumber) {
		this.containerNumber = containerNumber;
	}

	public void setContainerNumberStr(String s) {
		if (s != null && s.length() != 0) setContainerNumber(new BigDecimal(s));
	}

	public void setContainerStatus(String containerStatus) {
		this.containerStatus = containerStatus;
	}

	public void setCountDatetime(Date countDatetime) {
		this.countDatetime = countDatetime;
	}

	public void setCountDatetimeStr(String countDatetime) {
		try {
			setCountDatetime(DateHandler.getDateFromString("yyyy-MM-dd'T'HH:mm:ss", countDatetime, TimeZone.getTimeZone("GMT")));
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	} // end of method

	public void setCountQuantity(BigDecimal countQuantity) {
		this.countQuantity = countQuantity;
	}

	public void setCountQuantityStr(String s) {
		if (s != null && s.length() != 0) setCountQuantity(new BigDecimal(s));
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	public void setPersonnelIdStr(String s) {
		if (s != null && s.length() != 0) setPersonnelId(new BigDecimal(s));
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public void setReceiptIdStr(String s) {
		if (s != null && s.length() != 0) setReceiptId(new BigDecimal(s));
	}
}