package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ScheduleDeliveryInputBean Bean <br>
 * @version: 1.0, May 3, 2007 <br>
 *****************************************************************************/

public class ScheduleDeliveryInputBean
 extends BaseDataBean {

 private String companyId;
 private BigDecimal prNumber;
 private String lineItem;
 private String lineStatus;
 private BigDecimal requestor;

 //constructor
 public ScheduleDeliveryInputBean() {
 }

 //setters
 public void setCompanyId(String companyId) {
   this.companyId = companyId;
 }

 public void setPrNumber(BigDecimal prNumber) {
	this.prNumber = prNumber;
 }

 public void setLineItem(String lineItem) {
	this.lineItem = lineItem;
 }

 public void setLineStatus(String lineStatus) {
        this.lineStatus = lineStatus;
 }

 public void setRequestor(BigDecimal requestor) {
	this.requestor = requestor;
 }

 //getters
 public String getCompanyId() {
   return companyId;
 }

 public BigDecimal getPrNumber() {
	return prNumber;
 }

 public String getLineItem() {
	return lineItem;
 }

 public String getLineStatus() {
        return lineStatus;
 }

 public BigDecimal getRequestor() {
	return requestor;
 }

}