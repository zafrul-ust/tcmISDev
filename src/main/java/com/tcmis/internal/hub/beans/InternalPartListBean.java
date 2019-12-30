package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: InternalPartListBean <br>
 * @version: 1.0, Dec 9, 2005 <br>
 *****************************************************************************/


public class InternalPartListBean extends BaseDataBean {

	private String catPartNo;
	private String partDescription;
	private String catalogUos;
	private BigDecimal uosPerPackaging;
	private BigDecimal mrQty;
	private BigDecimal itemId;
	private String tcmPackaging;
	private String itemDesc;


	//constructor
	public InternalPartListBean() {
	}

	//setters
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setCatalogUos(String catalogUos) {
		this.catalogUos = catalogUos;
	}
	public void setUosPerPackaging(BigDecimal uosPerPackaging) {
		this.uosPerPackaging = uosPerPackaging;
	}
	public void setMrQty(BigDecimal mrQty) {
		this.mrQty = mrQty;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setTcmPackaging(String tcmPackaging) {
		this.tcmPackaging = tcmPackaging;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}


	//getters
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public String getCatalogUos() {
		return catalogUos;
	}
	public BigDecimal getUosPerPackaging() {
		return uosPerPackaging;
	}
	public BigDecimal getMrQty() {
		return mrQty;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getTcmPackaging() {
		return tcmPackaging;
	}
	public String getItemDesc() {
		return itemDesc;
	}
}