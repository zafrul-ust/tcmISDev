package com.tcmis.internal.distribution.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class CatalogItemSynonymViewBean extends BaseDataBean {
	
	private String catalogCompanyId;
	private String catalogId;
	private BigDecimal itemId;
	private String partSynonym;
	private String itemDesc;
	private String catalogDesc;
	
	public String getCatalogDesc() {
		return catalogDesc;
	}
	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
	}
	private String originalSynonym;
	
	
	public String getOriginalSynonym() {
		return originalSynonym;
	}
	public void setOriginalSynonym(String originalSynonym) {
		this.originalSynonym = originalSynonym;
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
	public BigDecimal getItemId() {
		return itemId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public String getPartSynonym() {
		return partSynonym;
	}
	public void setPartSynonym(String partSynonym) {
		this.partSynonym = partSynonym;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	

}
