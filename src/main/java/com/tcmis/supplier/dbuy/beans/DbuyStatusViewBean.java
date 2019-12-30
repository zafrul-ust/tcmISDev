package com.tcmis.supplier.dbuy.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DbuyStatusViewBean <br>
 * @version: 1.0, Jan 29, 2007 <br>
 *****************************************************************************/


public class DbuyStatusViewBean extends BaseDataBean {

	private BigDecimal prNumber;
	private BigDecimal radianPo;
	private Date dateAcknowledgement;
	private Date dateSent;
	private Date dateConfirmed;
	private String transactorMailBoxAddress;
	private String transactorId;
	private String supplier;
	private String supplierName;


	//constructor
	public DbuyStatusViewBean() {
	}

	//setters
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setDateAcknowledgement(Date dateAcknowledgement) {
		this.dateAcknowledgement = dateAcknowledgement;
	}
	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}
	public void setDateConfirmed(Date dateConfirmed) {
		this.dateConfirmed = dateConfirmed;
	}
	public void setTransactorMailBoxAddress(String transactorMailBoxAddress) {
		this.transactorMailBoxAddress = transactorMailBoxAddress;
	}
	public void setTransactorId(String transactorId) {
		this.transactorId = transactorId;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}


	//getters
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public Date getDateAcknowledgement() {
		return dateAcknowledgement;
	}
	public Date getDateSent() {
		return dateSent;
	}
	public Date getDateConfirmed() {
		return dateConfirmed;
	}
	public String getTransactorMailBoxAddress() {
		return transactorMailBoxAddress;
	}
	public String getTransactorId() {
		return transactorId;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getSupplierName() {
		return supplierName;
	}
}