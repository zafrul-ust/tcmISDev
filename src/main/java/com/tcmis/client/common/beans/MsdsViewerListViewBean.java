package com.tcmis.client.common.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: MsdsViewerListViewBean <br>
 * @version: 1.0, Jul 12, 2011 <br>
 *****************************************************************************/


public class MsdsViewerListViewBean extends BaseDataBean {

	private String companyId;
	private String listId;
	private String lookupListId;
	private String listName;
	private BigDecimal materialId;
	private Date revisionDate;
	private BigDecimal displayLevel;
	private String onList;
	private String percent;
	private String sublist;
	private Collection<MsdsViewerListViewBean> sublistColl;
	private BigDecimal localeId;
	private String localeCode;


	//constructor
	public MsdsViewerListViewBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}
	public void setLookupListId(String lookupListId) {
		this.lookupListId = lookupListId;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}
	public void setDisplayLevel(BigDecimal displayLevel) {
		this.displayLevel = displayLevel;
	}
	public void setOnList(String onList) {
		this.onList = onList;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public void setSublist(String sublist) {
		this.sublist = sublist;
	}
	public void setSublistColl(Collection<MsdsViewerListViewBean> sublistColl) {
		this.sublistColl = sublistColl;
	}
	public void setLocaleId(BigDecimal localeId) {
		this.localeId = localeId;
	}
	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public String getListId() {
		return listId;
	}
	public String getLookupListId() {
		return lookupListId;
	}
	public String getListName() {
		return listName;
	}
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public Date getRevisionDate() {
		return revisionDate;
	}
	public BigDecimal getDisplayLevel() {
		return displayLevel;
	}
	public String getOnList() {
		return onList;
	}
	public String getPercent() {
		return percent;
	}
	public Collection<MsdsViewerListViewBean> getSublistColl() {
		return sublistColl;
	}
	public String getSublist() {
		return sublist;
	}
	public BigDecimal getLocaleId() {
		return localeId;
	}
	public String getLocaleCode() {
		return localeCode;
	}

}