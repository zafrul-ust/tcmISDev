package com.tcmis.internal.supply.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class OpenScheduleBean extends BaseDataBean {

	private String companyId; //COMPANY_ID,
	private BigDecimal prNumber; //PR_NUMBER,
	private String lineItem; //LINE_ITEM,
	private Date dateToDeliver; //DATE_TO_DELIVER,
	private BigDecimal qty; //QTY,
	private String qtyOpen; //QTY_OPEN,	
	private BigDecimal color; //COLOR
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public Date getDateToDeliver() {
		return dateToDeliver;
	}
	public void setDateToDeliver(Date dateToDeliver) {
		this.dateToDeliver = dateToDeliver;
	}
	public BigDecimal getQty() {
		return qty;
	}
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
	public String getQtyOpen() {
		return qtyOpen;
	}
	public void setQtyOpen(String qtyOpen) {
		this.qtyOpen = qtyOpen;
	}
	public BigDecimal getColor() {
		return color;
	}
	public void setColor(BigDecimal color) {
		this.color = color;
	}	
}
