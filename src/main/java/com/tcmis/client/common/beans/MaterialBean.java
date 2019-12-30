package com.tcmis.client.common.beans;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * @version: 1.0, Feb 15, 2011 <br>
 *****************************************************************************/


public class MaterialBean extends BaseDataBean {

	private String manufacturer;
	private String materialDesc;
    private String tradeName;
    private String materialId;
    private String customerMsdsNo;

    //constructor
	public MaterialBean() {
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
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

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

	public String getCustomerMsdsNo() {
		return customerMsdsNo;
	}

	public void setCustomerMsdsNo(String customerMsdsNo) {
		this.customerMsdsNo = customerMsdsNo;
	}
}