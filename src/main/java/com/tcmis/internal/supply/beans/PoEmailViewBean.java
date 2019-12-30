package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PoEmailViewBean <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class PoEmailViewBean extends BaseDataBean {

	private BigDecimal radianPo;
	private BigDecimal userId;
	private Date emailDate;
	private String email;
	private String emailUserName;


	//constructor
	public PoEmailViewBean() {
	}

	//setters
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}
	public void setEmailDate(Date emailDate) {
		this.emailDate = emailDate;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setEmailUserName(String emailUserName) {
		this.emailUserName = emailUserName;
	}


	//getters
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getUserId() {
		return userId;
	}
	public Date getEmailDate() {
		return emailDate;
	}
	public String getEmail() {
		return email;
	}
	public String getEmailUserName() {
		return emailUserName;
	}
}