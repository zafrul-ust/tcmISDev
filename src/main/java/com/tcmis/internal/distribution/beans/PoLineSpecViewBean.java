package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CatalogItemSpecListBean <br>
 * @version: 1.0, Sep 7, 2009 <br>
 *****************************************************************************/


public class PoLineSpecViewBean extends BaseDataBean {

	private BigDecimal radianPo;
	private BigDecimal poLine;
	private String specId;
	private String specName;
	private String specLibrary;
	private String specLibraryDesc;
	private String detail;
	private String coc;
	private String coa;


	//constructor
	public PoLineSpecViewBean() {
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
	public void setCoc(String coc) {
		this.coc = coc;
	}
	public void setCoa(String coa) {
		this.coa = coa;
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
	public String getCoc() {
		return coc;
	}
	public String getCoa() {
		return coa;
	}

	public String getSpecLibraryDesc() {
		return specLibraryDesc;
	}

	public void setSpecLibraryDesc(String specLibraryDesc) {
		this.specLibraryDesc = specLibraryDesc;
	}

	public BigDecimal getPoLine() {
		return poLine;
	}

	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}

	public BigDecimal getRadianPo() {
		return radianPo;
	}

	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

}