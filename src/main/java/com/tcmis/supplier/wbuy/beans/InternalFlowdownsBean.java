package com.tcmis.supplier.wbuy.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;


/******************************************************************************
 * CLASSNAME: InternalFlowdownsBean <br>
 * @version: 1.0, Sep 9, 2005 <br>
 *****************************************************************************/


public class InternalFlowdownsBean extends BaseDataBean {

	private String companyId;
	private String catalogId;
	private String flowDown;
	private String flowDownType;
	private String flowDownDesc;
	private String revisionDate;
	private String content;
	private String onLine;
	private String attach;
	private String ok;
	private BigDecimal itemId;
	private BigDecimal color;


	//constructor
	public InternalFlowdownsBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setFlowDown(String flowDown) {
		this.flowDown = flowDown;
	}
	public void setFlowDownDesc(String flowDownDesc) {
		this.flowDownDesc = flowDownDesc;
	}
	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setOnLine(String onLine) {
		this.onLine = onLine;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public void setOk(String ok) {
		this.ok = ok;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setColor(BigDecimal color) {
		this.color = color;
	}
	
	public void setFlowDownType(String flowDownType) {
		this.flowDownType = flowDownType;
	}

	//getters
	public String getFlowDownType() {
		return flowDownType;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getFlowDown() {
		return flowDown;
	}
	public String getFlowDownDesc() {
		return flowDownDesc;
	}
	public String getRevisionDate() {
		return revisionDate;
	}
	public String getContent() {
		return content;
	}
	public String getOnLine() {
		return onLine;
	}
	public String getAttach() {
		return attach;
	}
	public String getOk() {
		return ok;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getColor() {
		return color;
	}
}