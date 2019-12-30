package com.tcmis.client.catalog.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CaiLineObjBean <br>
 * @version: 1.0, Oct 2, 2007 <br>
 *****************************************************************************/

public class CaiLineObjBean extends BaseDataBean {

  private BigDecimal lineItem;
  private BigDecimal partId;
  private BigDecimal itemId;
  private BigDecimal materialId;
  private String materialDesc;
  private String tradeName;
  private String casNumber;
  private String chemicalName;
  private BigDecimal percentage;
  private String manufacturerDesc;
  private String sqlType = "CUSTOMER.CAI_LINE_LIST_OBJ";

  //constructor
  public CaiLineObjBean() {
  }

  public BigDecimal getLineItem() {
		return lineItem;
	}

	public void setLineItem(BigDecimal lineItem) {
		this.lineItem = lineItem;
	}

	public BigDecimal getPartId() {
		return partId;
	}

	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getCasNumber() {
		return casNumber;
	}

	public void setCasNumber(String casNumber) {
		this.casNumber = casNumber;
	}

	public String getChemicalName() {
		return chemicalName;
	}

	public void setChemicalName(String chemicalName) {
		this.chemicalName = chemicalName;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

    public String getManufacturerDesc() {
        return manufacturerDesc;
    }

    public void setManufacturerDesc(String manufacturerDesc) {
        this.manufacturerDesc = manufacturerDesc;
    }
}