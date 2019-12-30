package com.tcmis.internal.airgas.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PolchemNsnVerificationBean <br>
 * @version: 1.0, Jul 1, 2008 <br>
 *****************************************************************************/


public class StockQueryResultViewBean extends BaseDataBean {

	String airgasPN;
	String availDate;
	String contractType;
	String custPrice;
	String errorMessage;
	String hazardData;
	String itemType;
	String minSellQty;
	String qtyAvailable;
	String ok;
	
	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public String getAirgasPN() {
		return airgasPN;
	}

	public void setAirgasPN(String airgasPN) {
		this.airgasPN = airgasPN;
	}

	public String getAvailDate() {
		return availDate;
	}

	public void setAvailDate(String availDate) {
		this.availDate = availDate;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getCustPrice() {
		return custPrice;
	}

	public void setCustPrice(String custPrice) {
		this.custPrice = custPrice;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getHazardData() {
		return hazardData;
	}

	public void setHazardData(String hazardData) {
		this.hazardData = hazardData;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getMinSellQty() {
		return minSellQty;
	}

	public void setMinSellQty(String minSellQty) {
		this.minSellQty = minSellQty;
	}

	public String getQtyAvailable() {
		return qtyAvailable;
	}

	public void setQtyAvailable(String qtyAvailable) {
		this.qtyAvailable = qtyAvailable;
	}
}