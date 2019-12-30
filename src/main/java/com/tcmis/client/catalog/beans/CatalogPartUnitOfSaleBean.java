package com.tcmis.client.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CatalogPartUnitOfSaleBean <br>
 * @version: 1.0, Apr 25, 2007 <br>
 *****************************************************************************/


public class CatalogPartUnitOfSaleBean extends BaseDataBean {

	private String companyId;
	private String catalogId;
	private String catPartNo;
	private String unitOfSale;
	private BigDecimal unitOfSaleQuantityPerEach;
	private String comments;
	private String cpiUnitOfSale;		//space holder when join with cpi


	//constructor
	public CatalogPartUnitOfSaleBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setUnitOfSale(String unitOfSale) {
		this.unitOfSale = unitOfSale;
	}
	public void setUnitOfSaleQuantityPerEach(BigDecimal unitOfSaleQuantityPerEach) {
		this.unitOfSaleQuantityPerEach = unitOfSaleQuantityPerEach;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setCpiUnitOfSale(String cpiUnitOfSale) {
		this.cpiUnitOfSale = cpiUnitOfSale;
	}

	//getters
	public String getCompanyId() {
		return companyId;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getUnitOfSale() {
		return unitOfSale;
	}
	public BigDecimal getUnitOfSaleQuantityPerEach() {
		return unitOfSaleQuantityPerEach;
	}
	public String getComments() {
		return comments;
	}

	public String getCpiUnitOfSale() {
		return cpiUnitOfSale;
	}
}