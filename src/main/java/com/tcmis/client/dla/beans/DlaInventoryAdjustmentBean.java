package com.tcmis.client.dla.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionForm;
import com.tcmis.common.framework.HubBaseInputBean;

public class DlaInventoryAdjustmentBean extends HubBaseInputBean {

	public DlaInventoryAdjustmentBean() {
		super();
	}

	public DlaInventoryAdjustmentBean(ActionForm inputForm) {
		super(inputForm);
	}

	public DlaInventoryAdjustmentBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}
	
	private Date dateInsertedFromDate;
	private Date dateInsertedToDate;
	private String catPartNo;
	private BigDecimal quantity;
	private String supplyConditionCode;
 	private String previousConditionCode;
	private String transactionType;
 	private String docIdCode;
	private BigDecimal loadLine;
	private String status;
	private String uom;
	private String unitOfSale;
	private String lineStatus;
	private String transactionRefNum;
	private Date dateSent;
	
	public Date getDateInsertedFromDate() {
		return dateInsertedFromDate;
	}

	public Date getDateInsertedToDate() {
		return dateInsertedToDate;
	}
	
	public String getCatPartNo() {
		return catPartNo;
	}
	
	public BigDecimal getQuantity() {
		return quantity;
	}
	
	public String getSupplyConditionCode() {
		return supplyConditionCode;
	}
	
	public String getPreviousConditionCode() {
		return previousConditionCode;
	}
	
	public String getTransactionType() {
		return transactionType;
	}
	
	public String getDocIdCode() {
		return docIdCode;
	}
	
	public BigDecimal getLoadLine() {
		return loadLine;
	}
	
	public String getUom() {
		return uom;
	}
	
	public String getLineStatus() {
		return lineStatus;
	}
	
	public String getUnitofSale() {
		return unitOfSale;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getTransactionRefNum() {
		return transactionRefNum;
	}
	
	public Date getDateSent() {
		return dateSent;
	}
	
	public boolean hasDateInsertedFromDate() {
		return null != dateInsertedFromDate;
	}

	public boolean hasDateInsertedToDate() {
		return null != dateInsertedToDate;
	}
	
	public void setTransactionRefNum(String transactionRefNum) {
		this.transactionRefNum = transactionRefNum;
	}
	
	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}
	
	public void setLineStatus(String lineStatus) {
		this.lineStatus= lineStatus;
	}
	
	public void setUnitofSale(String unitOfSale) {
		this.unitOfSale= unitOfSale;
	}
	
	public void setUom(String uom) {
		this.uom = uom;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setLoadLine(BigDecimal loadLine) {
		this.loadLine = loadLine;
	}
	
	public void setDocIdCode(String docIdCode) {
		this.docIdCode = docIdCode;
	}
	
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	public void setPreviousConditionCode(String previousConditionCode) {
		this.previousConditionCode = previousConditionCode;
	}
	
	public void  setSupplyConditionCode(String supplyConditionCode) {
		this.supplyConditionCode = supplyConditionCode;
	}
	
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	
	public void setDateInsertedFromDate(Date dateInsertedFromDate) {
		this.dateInsertedFromDate = dateInsertedFromDate;
	}

	public void setDateInsertedToDate(Date dateInsertedToDate) {
		this.dateInsertedToDate = dateInsertedToDate;
	}

	@Override
	public void setHiddenFormFields() {
		addHiddenFormField("searchArgument");
		addHiddenFormField("searchField");
		addHiddenFormField("searchMode");
	}
	
}
