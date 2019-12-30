package com.tcmis.client.het.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HetRecipeBean <br>
 * @version: 1.0, May 27, 2011 <br>
 *****************************************************************************/

public class HetCartBean extends BaseDataBean {

	private BigDecimal applicationId;
	private BigDecimal cartId;
	private String cartName;
	private String cartStatus;
	private Date checkedOut;
	private String companyId;
	private String employee;
	private Date lastLogged;

	//constructor
	public HetCartBean() {
	}

	public BigDecimal getApplicationId() {
		return applicationId;
	}

	public BigDecimal getCartId() {
		return cartId;
	}

	public String getCartName() {
		return cartName;
	}

	public String getCartStatus() {
		return cartStatus;
	}

	public Date getCheckedOut() {
		return checkedOut;
	}

	//getters
	public String getCompanyId() {
		return companyId;
	}

	public String getEmployee() {
		return employee;
	}

	public Date getLastLogged() {
		return lastLogged;
	}

	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}

	public void setCartId(BigDecimal cartId) {
		this.cartId = cartId;
	}

	public void setCartName(String cartName) {
		this.cartName = cartName;
	}

	public void setCartStatus(String cartStatus) {
		this.cartStatus = cartStatus;
	}

	public void setCheckedOut(Date checkedOut) {
		this.checkedOut = checkedOut;
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public void setLastLogged(Date lastLogged) {
		this.lastLogged = lastLogged;
	}
}