package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PoLineSpecDraftBean <br>
 * @version: 1.0, Aug 11, 2008 <br>
 *****************************************************************************/


public class PoLineSpecDraftBean extends BaseDataBean {

	private String specId;
	private String specLibrary;
	private String detail;
	private BigDecimal radianPo;
	private BigDecimal poLine;
	private BigDecimal amendment;
	private String coc;
	private String coa;
	private String cocReq;
	private String coaReq;
	private BigDecimal color;
	private String currentCocRequirement;
	private String currentCoaRequirement;


	//constructor
	public PoLineSpecDraftBean() {
	}

	//setters
	public void setSpecId(String specId) {
		this.specId = specId;
	}
	public void setSpecLibrary(String specLibrary) {
		this.specLibrary = specLibrary;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public void setAmendment(BigDecimal amendment) {
		this.amendment = amendment;
	}
	public void setCoc(String coc) {
		this.coc = coc;
	}
	public void setCoa(String coa) {
		this.coa = coa;
	}
	public void setCocReq(String cocReq) {
		this.cocReq = cocReq;
	}
	public void setCoaReq(String coaReq) {
		this.coaReq = coaReq;
	}
	public void setColor(BigDecimal color) {
		this.color = color;
	}

	public void setCurrentCocRequirement(String currentCocRequirement) {
		this.currentCocRequirement = currentCocRequirement;
	}

	public void setCurrentCoaRequirement(String currentCoaRequirement) {
		this.currentCoaRequirement = currentCoaRequirement;
	}

	//getters
	public String getSpecId() {
		return specId;
	}
	public String getSpecLibrary() {
		return specLibrary;
	}
	public String getDetail() {
		return detail;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public BigDecimal getAmendment() {
		return amendment;
	}
	public String getCoc() {
		return coc;
	}
	public String getCoa() {
		return coa;
	}
	public String getCocReq() {
		return cocReq;
	}
	public String getCoaReq() {
		return coaReq;
	}
	public BigDecimal getColor() {
		return color;
	}

	public String getCurrentCocRequirement() {
		return currentCocRequirement;
	}

	public String getCurrentCoaRequirement() {
		return currentCoaRequirement;
	}
}