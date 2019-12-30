package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: PurchasingMethodBean <br>
 * @version: 1.0, Jul 11, 2011 <br>
 *****************************************************************************/

public class PurchasingMethodBean extends BaseDataBean {

	private String companyId;
    private String facilityId;
    private BigDecimal purchasingMethodId;
    private String purchasingMethodName;

    //constructor
	public PurchasingMethodBean() {
	}

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public BigDecimal getPurchasingMethodId() {
        return purchasingMethodId;
    }

    public void setPurchasingMethodId(BigDecimal purchasingMethodId) {
        this.purchasingMethodId = purchasingMethodId;
    }

    public String getPurchasingMethodName() {
        return purchasingMethodName;
    }

    public void setPurchasingMethodName(String purchasingMethodName) {
        this.purchasingMethodName = purchasingMethodName;
    }
}