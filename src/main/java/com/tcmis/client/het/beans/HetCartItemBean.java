package com.tcmis.client.het.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HetRecipeItemBean <br>
 * @version: 1.0, May 31, 2011 <br>
 *****************************************************************************/

public class HetCartItemBean extends BaseDataBean {

	private BigDecimal cartId;
	private String companyId;
	private String containerId;
	private BigDecimal itemId;
	private BigDecimal sortOrder;

	public HetCartItemBean() {
	}

	public HetCartItemBean(String companyId, BigDecimal cartId, BigDecimal itemId, String containerId) {
		super();
		this.companyId = companyId;
		this.itemId = itemId;
		this.cartId = cartId;
		this.containerId = containerId;
	}

	public BigDecimal getCartId() {
		return cartId;
	}

	//getters
	public String getCompanyId() {
		return companyId;
	}

	public String getContainerId() {
		return containerId;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public BigDecimal getSortOrder() {
		return sortOrder;
	}

	public void setCartId(BigDecimal cartId) {
		this.cartId = cartId;
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setSortOrder(BigDecimal sortOrder) {
		this.sortOrder = sortOrder;
	}
}