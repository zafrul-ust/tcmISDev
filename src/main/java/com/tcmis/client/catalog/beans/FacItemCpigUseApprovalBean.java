package com.tcmis.client.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;

/******************************************************************************
 * CLASSNAME: FacItemCpigUseApprovalBean <br>
 * @version: 1.0, Jan 24, 2011 <br>
 *****************************************************************************/

public class FacItemCpigUseApprovalBean
 extends BaseDataBean {

 private String catPartNo;
 private String partDescription;
 private BigDecimal partGroupNo;
 private BigDecimal itemId;
 private String packaging;
 private BigDecimal tierIITemperatureCode;

 //constructor
 public FacItemCpigUseApprovalBean() {
 }

    public BigDecimal getTierIITemperatureCode() {
        return tierIITemperatureCode;
    }

    public void setTierIITemperatureCode(BigDecimal tierIITemperatureCode) {
        this.tierIITemperatureCode = tierIITemperatureCode;
    }

    public String getCatPartNo() {
		return catPartNo;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}

	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}
} //end of class