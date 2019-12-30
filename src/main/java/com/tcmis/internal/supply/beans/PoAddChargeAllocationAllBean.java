package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PoAddChargeAllocationAllBean <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class PoAddChargeAllocationAllBean extends BaseDataBean {

	private BigDecimal radianPo;
	private BigDecimal poLine;
	private BigDecimal poAddChargeLine;
	private BigDecimal amendment;


	//constructor
	public PoAddChargeAllocationAllBean() {
	}

	//setters
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public void setPoAddChargeLine(BigDecimal poAddChargeLine) {
		this.poAddChargeLine = poAddChargeLine;
	}
	public void setAmendment(BigDecimal amendment) {
		this.amendment = amendment;
	}


	//getters
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public BigDecimal getPoAddChargeLine() {
		return poAddChargeLine;
	}
	public BigDecimal getAmendment() {
		return amendment;
	}
}