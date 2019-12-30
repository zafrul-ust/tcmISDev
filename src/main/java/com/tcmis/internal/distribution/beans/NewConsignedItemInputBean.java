package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;
import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerContactSearchViewBean <br>
 * @version: 1.0, Mar 17, 2009 <br>
 *****************************************************************************/


public class NewConsignedItemInputBean extends BaseDataBean {
	private String sourceInventoryGroup;
	private BigDecimal itemId;
	private String specListId;
	private String specDetail;
	private String specCoc;
	private String specCoa;
	private String specLibrary;
	private BigDecimal unitPrice;
	private String currencyId;
	private String destInventoryGroup;
	private String supplyFrom;
	private String uAction;
	private String fromItemCount;
	private String hub;


	//constructor
	public NewConsignedItemInputBean() {
	}


	public String getCurrencyId() {
		return currencyId;
	}


	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}


	public BigDecimal getItemId() {
		return itemId;
	}


	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}


	public String getSpecCoa() {
		return specCoa;
	}


	public void setSpecCoa(String specCoa) {
		this.specCoa = specCoa;
	}


	public String getSpecCoc() {
		return specCoc;
	}


	public void setSpecCoc(String specCoc) {
		this.specCoc = specCoc;
	}


	public String getSpecDetail() {
		return specDetail;
	}


	public void setSpecDetail(String specDetail) {
		this.specDetail = specDetail;
	}


	public String getSpecLibrary() {
		return specLibrary;
	}


	public void setSpecLibrary(String specLibrary) {
		this.specLibrary = specLibrary;
	}


	public String getSpecListId() {
		return specListId;
	}


	public void setSpecListId(String specListId) {
		this.specListId = specListId;
	}


	public String getSupplyFrom() {
		return supplyFrom;
	}


	public void setSupplyFrom(String supplyFrom) {
		this.supplyFrom = supplyFrom;
	}


	public String getUAction() {
		return uAction;
	}


	public void setUAction(String action) {
		uAction = action;
	}


	public BigDecimal getUnitPrice() {
		return unitPrice;
	}


	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}


	public String getDestInventoryGroup() {
		return destInventoryGroup;
	}


	public void setDestInventoryGroup(String destInventoryGroup) {
		this.destInventoryGroup = destInventoryGroup;
	}


	public String getSourceInventoryGroup() {
		return sourceInventoryGroup;
	}


	public void setSourceInventoryGroup(String sourceInventoryGroup) {
		this.sourceInventoryGroup = sourceInventoryGroup;
	}


	public String getFromItemCount() {
		return fromItemCount;
	}


	public void setFromItemCount(String fromItemCount) {
		this.fromItemCount = fromItemCount;
	}


	public String getHub() {
		return hub;
	}


	public void setHub(String hub) {
		this.hub = hub;
	}
	
	
	
	

}