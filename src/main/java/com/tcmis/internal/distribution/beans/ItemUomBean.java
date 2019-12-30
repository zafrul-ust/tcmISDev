package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ItemUomBean <br>
 * @version: 1.0, Jan 27, 2010 <br>
 *****************************************************************************/


public class ItemUomBean extends BaseDataBean {

	private BigDecimal itemId;
	private String unitOfMeasure;
	private BigDecimal unitsPerItem;
	private String purchasingUnitOfMeasure;//PURCHASING_UNIT_OF_MEASURE

	//constructor
	public ItemUomBean() {
	}

	//setters
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	public void setUnitsPerItem(BigDecimal unitsPerItem) {
		this.unitsPerItem = unitsPerItem;
	}


	//getters
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}
	public BigDecimal getUnitsPerItem() {
		return unitsPerItem;
	}
	public String getPurchasingUnitOfMeasure() {
		return purchasingUnitOfMeasure;
	}
	public void setPurchasingUnitOfMeasure(String purchasingUnitOfMeasure) {
		this.purchasingUnitOfMeasure = purchasingUnitOfMeasure;
	}


}