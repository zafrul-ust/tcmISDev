package com.tcmis.client.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CatalogSpecViewBean <br>
 * 
 * @version: 1.0, Dec 1, 2006 <br>
 *****************************************************************************/

public class CatalogSpecViewBean extends BaseDataBean {

	private String	catalogId;
	private String	catPartNo;
	private String	coa;
	private String	coc;
	private String	content;
	private boolean	itar	= false;
	private String	onLine;
	private String	specId;
	private String	specification;

	// constructor
	public CatalogSpecViewBean() {
	}

	// getters
	public String getCatalogId() {
		return catalogId;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public String getCoa() {
		return coa;
	}

	public String getCoc() {
		return coc;
	}

	public String getContent() {
		return content;
	}

	public String getOnLine() {
		return onLine;
	}

	public String getSpecId() {
		return specId;
	}

	public String getSpecification() {
		return specification;
	}

	public boolean isItar() {
		return itar;
	}

	// setters
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public void setCoa(String coa) {
		this.coa = coa;
	}

	public void setCoc(String coc) {
		this.coc = coc;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setItar(boolean itar) {
		this.itar = itar;
	}

	public void setOnLine(String onLine) {
		this.onLine = onLine;
	}

	public void setSpecId(String s) {
		this.specId = s;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}
}