package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class CatalogSpecInputBean {
	private String		catalogId;
	private String		catPartNo;
	private String		coa = "N";
	private String		coc = "N";
	private String		content;
	private String		criteria;
	private String		criterion;
	private String		delete;
	private String		historyCatalogId;
	private String		historyCatPartNo;
	private String		historySpecId;
	private boolean		itar	= false;
	private String		newRow;
	private String		oldCoa;
	private String		oldCoc;
	private boolean		oldItar	= false;
	private String		online;
	private BigDecimal	personnelId;
	private String		search;
	private String		sortBy;
	private String		specId;
	private String		specification;

	// constructor
	public CatalogSpecInputBean() {
	}

	public String getCatalogId() {
		return this.catalogId;
	}

	public String getCatPartNo() {
		return this.catPartNo;
	}

	public String getCoa() {
		return this.coa;
	}

	public String getCoc() {
		return this.coc;
	}

	public String getContent() {
		return this.content;
	}

	public String getCriteria() {
		return this.criteria;
	}

	public String getCriterion() {
		return this.criterion;
	}

	public String getDelete() {
		return this.delete;
	}

	public String getHistoryCatalogId() {
		return this.historyCatalogId;
	}

	public String getHistoryCatPartNo() {
		return this.historyCatPartNo;
	}

	public String getHistorySpecId() {
		return this.historySpecId;
	}

	public String getNewRow() {
		return this.newRow;
	}

	public String getOldCoa() {
		return this.oldCoa;
	}

	public String getOldCoc() {
		return this.oldCoc;
	}

	public String getOnline() {
		return this.online;
	}

	// getters
	public BigDecimal getPersonnelId() {
		return this.personnelId;
	}

	public String getSearch() {
		return this.search;
	}

	public String getSortBy() {
		return this.sortBy;
	}

	public String getSpecId() {
		return this.specId;
	}

	public String getSpecification() {
		return this.specification;
	}

	public boolean isItarChanged () {
		return itar != oldItar;		
	}
	
	public boolean isCoaChanged () {
		return !(oldCoa + "").equals(coa);		
	}

	public boolean isCocChanged () {
		return !(oldCoc + "").equals(coc);		
	}
	
	public boolean isDeleted () {
		return "Y".equals(delete);		
	}

	public boolean isItar() {
		return itar;
	}

	public boolean isOldItar() {
		return oldItar;
	}

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

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public void setCriterion(String criterion) {
		this.criterion = criterion;
	}

	public void setDelete(String s) {
		this.delete = s;
	}

	public void setHistoryCatalogId(String historyCatalogId) {
		this.historyCatalogId = historyCatalogId;
	}

	public void setHistoryCatPartNo(String historyCatPartNo) {
		this.historyCatPartNo = historyCatPartNo;
	}

	public void setHistorySpecId(String s) {
		this.historySpecId = s;
	}

	public void setItar(boolean itar) {
		this.itar = itar;
	}

	public void setNewRow(String s) {
		this.newRow = s;
	}

	public void setOldCoa(String oldCoa) {
		this.oldCoa = oldCoa;
	}

	public void setOldCoc(String oldCoc) {
		this.oldCoc = oldCoc;
	}

	public void setOldItar(boolean oldItar) {
		this.oldItar = oldItar;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	// setters
	public void setPersonnelId(BigDecimal b) {
		this.personnelId = b;
	}

	public void setSearch(String s) {
		this.search = s;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public void setSpecId(String s) {
		this.specId = s;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}
}