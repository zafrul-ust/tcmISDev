package com.tcmis.ws.tablet.beans;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: ReceiptDescriptionViewBean <br>
 * 
 * @version: 1.0, Aug 23, 2005 <br>
 *****************************************************************************/
public class HubCycleCountViewBean extends BaseDataBean {
	private static SimpleDateFormat	dateParser	= new SimpleDateFormat("MM/dd/yyyy");
	public static Log				log			= LogFactory.getLog(HubCycleCountViewBean.class);
	
	private BigDecimal				receiptId;
	private BigDecimal				itemId;
	private String					mfgLot;
	private String					bin;
	private String					room;
	private Date					dateOfReceipt;
	private Date					expireDate;	
	private BigDecimal				expectedQuantity;
	private BigDecimal				actualQuantity;
	private String					lotStatus;
	private String					hub;
	private BigDecimal				counterId;
	private String					comments;
	private BigDecimal				countId;
	private Date					dateUpdated;
	private BigDecimal				approvedBy;
	private Date					dateApproved;
	private Date					countDatetime;
	private String					countStatus;
	private String					countType;
	private BigDecimal				countMonth;
	private String					inventoryGroup;
	private String					packaging;
	private String					itemDesc;

	// constructor
	public HubCycleCountViewBean() {
	}

	public static SimpleDateFormat getDateParser() {
		return dateParser;
	}

	public static Log getLog() {
		return log;
	}

	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public String getMfgLot() {
		return mfgLot;
	}

	public String getBin() {
		return bin;
	}

	public String getRoom() {
		return room;
	}

	public Date getDateOfReceipt() {
		return dateOfReceipt;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public BigDecimal getExpectedQuantity() {
		return expectedQuantity;
	}

	public BigDecimal getActualQuantity() {
		return actualQuantity;
	}

	public String getLotStatus() {
		return lotStatus;
	}

	public String getHub() {
		return hub;
	}

	public BigDecimal getCounterId() {
		return counterId;
	}

	public String getComments() {
		return comments;
	}

	public BigDecimal getCountId() {
		return countId;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public BigDecimal getApprovedBy() {
		return approvedBy;
	}

	public Date getDateApproved() {
		return dateApproved;
	}

	public Date getCountDatetime() {
		return countDatetime;
	}

	public String getCountStatus() {
		return countStatus;
	}

	public String getCountType() {
		return countType;
	}

	public BigDecimal getCountMonth() {
		return countMonth;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getPackaging() {
		return packaging;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public static void setDateParser(SimpleDateFormat dateParser) {
		HubCycleCountViewBean.dateParser = dateParser;
	}

	public static void setLog(Log log) {
		HubCycleCountViewBean.log = log;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public void setDateOfReceipt(Date dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public void setExpectedQuantity(BigDecimal expectedQuantity) {
		this.expectedQuantity = expectedQuantity;
	}

	public void setActualQuantity(BigDecimal actualQuantity) {
		this.actualQuantity = actualQuantity;
	}

	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setCounterId(BigDecimal counterId) {
		this.counterId = counterId;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setCountId(BigDecimal countId) {
		this.countId = countId;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public void setApprovedBy(BigDecimal approvedBy) {
		this.approvedBy = approvedBy;
	}

	public void setDateApproved(Date dateApproved) {
		this.dateApproved = dateApproved;
	}

	public void setCountDatetime(Date countDatetime) {
		this.countDatetime = countDatetime;
	}

	public void setCountStatus(String countStatus) {
		this.countStatus = countStatus;
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}

	public void setCountMonth(BigDecimal countMonth) {
		this.countMonth = countMonth;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	
}
