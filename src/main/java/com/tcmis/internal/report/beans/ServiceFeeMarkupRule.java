package com.tcmis.internal.report.beans;

import java.math.BigDecimal;
import com.tcmis.common.framework.BaseDataBean;

public class ServiceFeeMarkupRule extends BaseDataBean {
	private String	buyType;
	private String	cost;
	private String	itemType;
	private String	orderType;
	private BigDecimal	percent;

	public String getBuyType() {
		return buyType;
	}

	public String getCost() {
		return cost;
	}

	public String getItemType() {
		return itemType;
	}

	public String getOrderType() {
		return orderType;
	}

	public BigDecimal getPercent() {
		return percent;
	}

	public void setBuyType(String buyType) {
		this.buyType = buyType;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}
}
