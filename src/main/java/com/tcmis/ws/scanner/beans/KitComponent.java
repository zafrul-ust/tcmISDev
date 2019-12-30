package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class KitComponent extends BaseDataBean {
	private String		bin;
	private Date		expireDate;
	private String		lot;
	private BigDecimal	itemId;
	private BigDecimal	partId;
	private BigDecimal	quantity;

	public KitComponent() {
		super();
	};

	public String getBin() {
		return bin;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public String getLot() {
		return lot;
	}

	public BigDecimal getPartId() {
		return partId;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
}
