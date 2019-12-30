package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HubBean <br>
 * 
 * @version: 1.0, Jun 1, 2004 <br>
 *****************************************************************************/

public class IataDotLabel extends BaseDataBean {

	private String		dot;
	private String		iata;
	private BigDecimal	itemId;

	// constructor
	public IataDotLabel() {
	}

	public String getDot() {
		return this.dot;
	}

	public String getIata() {
		return this.iata;
	}

	public BigDecimal getItemId() {
		return this.itemId;
	}

	public void setDot(String dot) {
		this.dot = dot;
	}

	public void setIata(String iata) {
		this.iata = iata;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

}