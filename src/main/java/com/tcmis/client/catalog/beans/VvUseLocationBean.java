package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: VvUseLocationBean <br>
 * @version: 1.0, Jul 11, 2011 <br>
 *****************************************************************************/

public class VvUseLocationBean extends BaseDataBean {

	private BigDecimal useLocationId;
    private String useLocationName;
    private String companyId;
    private String catalogCompanyId;
    private String catalogId;

	//constructor
	public VvUseLocationBean() {
	}

    public BigDecimal getUseLocationId() {
        return useLocationId;
    }

    public void setUseLocationId(BigDecimal useLocationId) {
        this.useLocationId = useLocationId;
    }

    public String getUseLocationName() {
        return useLocationName;
    }

    public void setUseLocationName(String useLocationName) {
        this.useLocationName = useLocationName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCatalogCompanyId() {
        return catalogCompanyId;
    }

    public void setCatalogCompanyId(String catalogCompanyId) {
        this.catalogCompanyId = catalogCompanyId;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }
}