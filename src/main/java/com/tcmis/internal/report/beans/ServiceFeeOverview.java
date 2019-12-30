package com.tcmis.internal.report.beans;

import java.math.BigDecimal;
import com.tcmis.common.framework.BaseDataBean;

public class ServiceFeeOverview extends BaseDataBean {
	private String			part;
	private String			partDescription;
	private BigDecimal	po;
	private BigDecimal	poLine;
	private BigDecimal	price;

	public String getPart() {
		return part;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public BigDecimal getPo() {
		return po;
	}

	public BigDecimal getPoLine() {
		return poLine;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public void setPo(BigDecimal po) {
		this.po = po;
	}

	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
