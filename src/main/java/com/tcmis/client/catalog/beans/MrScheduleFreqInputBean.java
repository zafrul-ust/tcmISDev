package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CatPartCommentBean <br>
 * @version: 1.0, Jul 11, 2005 <br>
 *****************************************************************************/

public class MrScheduleFreqInputBean
 extends BaseDataBean {

 private BigDecimal prNumber;
 private String lineItem;
 private String facPartNo;
 private String itemId;
 private BigDecimal quantity;
 private String frequency;
 private BigDecimal month;
 private BigDecimal week;
 private BigDecimal day;
 private Date startingDate;
 private BigDecimal total;
 private String companyId;
public BigDecimal getDay() {
	return day;
}
public void setDay(BigDecimal day) {
	this.day = day;
}
public String getFacPartNo() {
	return facPartNo;
}
public void setFacPartNo(String facPartNo) {
	this.facPartNo = facPartNo;
}
public String getFrequency() {
	return frequency;
}
public void setFrequency(String frequency) {
	this.frequency = frequency;
}
public String getItemId() {
	return itemId;
}
public void setItemId(String itemId) {
	this.itemId = itemId;
}
public String getLineItem() {
	return lineItem;
}
public void setLineItem(String lineItem) {
	this.lineItem = lineItem;
}
public BigDecimal getMonth() {
	return month;
}
public void setMonth(BigDecimal month) {
	this.month = month;
}
public BigDecimal getPrNumber() {
	return prNumber;
}
public void setPrNumber(BigDecimal prNumber) {
	this.prNumber = prNumber;
}
public BigDecimal getQuantity() {
	return quantity;
}
public void setQuantity(BigDecimal quantity) {
	this.quantity = quantity;
}
public Date getStartingDate() {
	return startingDate;
}
public void setStartingDate(Date startingDate) {
	this.startingDate = startingDate;
}
public BigDecimal getTotal() {
	return total;
}
public void setTotal(BigDecimal total) {
	this.total = total;
}
public BigDecimal getWeek() {
	return week;
}
public void setWeek(BigDecimal week) {
	this.week = week;
}
public String getCompanyId() {
	return companyId;
}
public void setCompanyId(String companyId) {
	this.companyId = companyId;
}
 
}