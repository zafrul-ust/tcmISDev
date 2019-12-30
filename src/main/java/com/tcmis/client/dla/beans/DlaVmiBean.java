package com.tcmis.client.dla.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DlaVmiBean <br>
 * @version: 1.0, Mar 28, 2008 <br>
 *****************************************************************************/


public class DlaVmiBean extends BaseDataBean {

	private String catPartNo;
	private BigDecimal quantity;
	private Date transactionDate;
	private String transactionRefNum;
	private String transactionType;
	private String docIdCode;
	private BigDecimal transactionRefLineNum;


	//constructor
	public DlaVmiBean() {
	}

	//setters
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public void setTransactionRefNum(String transactionRefNum) {
		this.transactionRefNum = transactionRefNum;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public void setDocIdCode(String docIdCode) {
		this.docIdCode = docIdCode;
	}
	public void setTransactionRefLineNum(BigDecimal transactionRefLineNum) {
		this.transactionRefLineNum = transactionRefLineNum;
	}


	//getters
	public String getCatPartNo() {
		return catPartNo;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public String getTransactionRefNum() {
		return transactionRefNum;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public String getDocIdCode() {
		return docIdCode;
	}
	public BigDecimal getTransactionRefLineNum() {
		return transactionRefLineNum;
	}
}