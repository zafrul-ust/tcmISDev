package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: BpoEmailViewBean <br>
 * @version: 1.0, Nov 15, 2007 <br>
 *****************************************************************************/


public class BpoEmailViewBean extends BaseDataBean 
{

	private BigDecimal bpo;
	private BigDecimal userId;
	private Date emailDate;
	private String email;
	private String emailUserName;

	public BpoEmailViewBean() 
	{
	}
	
	//setters
	public void setBpo(BigDecimal bpo) {
		this.bpo = bpo;
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
	public BigDecimal getBpo() {
		return bpo;
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