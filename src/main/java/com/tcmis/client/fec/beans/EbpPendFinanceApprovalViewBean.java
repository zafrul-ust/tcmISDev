package com.tcmis.client.fec.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: EbpPendFinanceApprovalViewBean <br>
 * @version: 1.0, Jun 23, 2005 <br>
 *****************************************************************************/


public class EbpPendFinanceApprovalViewBean extends BaseDataBean {

	private String newItemDescription;
	private BigDecimal newItemQuantity;
	private String newItemUnit;
	private BigDecimal newItemPrice;
	private String newItemCurrency;
	private String newItemExtSchemaType;
	private String newItemExtCategoryId;
	private String newItemCustField1;
	private BigDecimal newItemCustField2;
	private String newItemCustField3;
	private String newItemCustField4;
	private String newItemCustField5;
	private String poNumber;
	private String releaseNumber;
	private String payloadId;


	//constructor
	public EbpPendFinanceApprovalViewBean() {
	}

	//setters
	public void setNewItemDescription(String newItemDescription) {
		this.newItemDescription = newItemDescription;
	}
	public void setNewItemQuantity(BigDecimal newItemQuantity) {
		this.newItemQuantity = newItemQuantity;
	}
	public void setNewItemUnit(String newItemUnit) {
		this.newItemUnit = newItemUnit;
	}
	public void setNewItemPrice(BigDecimal newItemPrice) {
		this.newItemPrice = newItemPrice;
	}
	public void setNewItemCurrency(String newItemCurrency) {
		this.newItemCurrency = newItemCurrency;
	}
	public void setNewItemExtSchemaType(String newItemExtSchemaType) {
		this.newItemExtSchemaType = newItemExtSchemaType;
	}
	public void setNewItemExtCategoryId(String newItemExtCategoryId) {
		this.newItemExtCategoryId = newItemExtCategoryId;
	}
	public void setNewItemCustField1(String newItemCustField1) {
		this.newItemCustField1 = newItemCustField1;
	}
	public void setNewItemCustField2(BigDecimal newItemCustField2) {
		this.newItemCustField2 = newItemCustField2;
	}
	public void setNewItemCustField3(String newItemCustField3) {
		this.newItemCustField3 = newItemCustField3;
	}
	public void setNewItemCustField4(String newItemCustField4) {
		this.newItemCustField4 = newItemCustField4;
	}
	public void setNewItemCustField5(String newItemCustField5) {
		this.newItemCustField5 = newItemCustField5;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setReleaseNumber(String releaseNumber) {
		this.releaseNumber = releaseNumber;
	}
	public void setPayloadId(String payloadId) {
		this.payloadId = payloadId;
	}


	//getters
	public String getNewItemDescription() {
		return newItemDescription;
	}
	public BigDecimal getNewItemQuantity() {
		return newItemQuantity;
	}
	public String getNewItemUnit() {
		return newItemUnit;
	}
	public BigDecimal getNewItemPrice() {
		return newItemPrice;
	}
	public String getNewItemCurrency() {
		return newItemCurrency;
	}
	public String getNewItemExtSchemaType() {
		return newItemExtSchemaType;
	}
	public String getNewItemExtCategoryId() {
		return newItemExtCategoryId;
	}
	public String getNewItemCustField1() {
		return newItemCustField1;
	}
	public BigDecimal getNewItemCustField2() {
		return newItemCustField2;
	}
	public String getNewItemCustField3() {
		return newItemCustField3;
	}
	public String getNewItemCustField4() {
		return newItemCustField4;
	}
	public String getNewItemCustField5() {
		return newItemCustField5;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public String getReleaseNumber() {
		return releaseNumber;
	}
	public String getPayloadId() {
		return payloadId;
	}
}