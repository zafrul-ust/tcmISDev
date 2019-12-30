package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SalesQuoteLineBean <br>
 * @version: 1.0, Apr 9, 2009 <br>
 *****************************************************************************/


public class SalesCatalogSearchBean extends BaseDataBean {

	private String labelSpec;
	private String partShortName;
	private String partDescription;
	private String catalogCompanyId;
	private String catalogId;
	private String facPartNo;
//	private String catPartNo;
	private String specifications;
	private BigDecimal itemId;
	private String packaging;
	private BigDecimal catalogPrice;
	private BigDecimal igQuantityAvailable;
	private BigDecimal regionQuantityAvailable;
	private BigDecimal globalQuantityAvailable;
	

	//constructor
	public SalesCatalogSearchBean() {
	}


	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}


	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}


	public String getCatalogId() {
		return catalogId;
	}


	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}


	public BigDecimal getCatalogPrice() {
		return catalogPrice;
	}


	public void setCatalogPrice(BigDecimal catalogPrice) {
		this.catalogPrice = catalogPrice;
	}

/*
	public String getCatPartNo() {
		return catPartNo;
	}


	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
*/

	public String getFacPartNo() {
		return facPartNo;
	}


	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}


	public BigDecimal getGlobalQuantityAvailable() {
		return globalQuantityAvailable;
	}


	public void setGlobalQuantityAvailable(BigDecimal globalQuantityAvailable) {
		this.globalQuantityAvailable = globalQuantityAvailable;
	}


	public BigDecimal getIgQuantityAvailable() {
		return igQuantityAvailable;
	}


	public void setIgQuantityAvailable(BigDecimal igQuantityAvailable) {
		this.igQuantityAvailable = igQuantityAvailable;
	}


	public BigDecimal getItemId() {
		return itemId;
	}


	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}


	public String getLabelSpec() {
		return labelSpec;
	}


	public void setLabelSpec(String labelSpec) {
		this.labelSpec = labelSpec;
	}


	public String getPackaging() {
		return packaging;
	}


	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}


	public String getPartDescription() {
		return partDescription;
	}


	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}


	public String getPartShortName() {
		return partShortName;
	}


	public void setPartShortName(String partShortName) {
		this.partShortName = partShortName;
	}


	public BigDecimal getRegionQuantityAvailable() {
		return regionQuantityAvailable;
	}


	public void setRegionQuantityAvailable(BigDecimal regionQuantityAvailable) {
		this.regionQuantityAvailable = regionQuantityAvailable;
	}


	public String getSpecifications() {
		return specifications;
	}


	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	

}