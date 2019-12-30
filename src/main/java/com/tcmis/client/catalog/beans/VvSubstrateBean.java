package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: VvSubstrateBean <br>
 * @version: 1.0, Jul 11, 2011 <br>
 *****************************************************************************/

public class VvSubstrateBean extends BaseDataBean {

	private BigDecimal substrateId;
    private String substrateName;
    private String companyId;
    private String catalogCompanyId;
    private String catalogId;

	//constructor
	public VvSubstrateBean() {
	}

    public BigDecimal getSubstrateId() {
        return substrateId;
    }

    public void setSubstrateId(BigDecimal substrateId) {
        this.substrateId = substrateId;
    }

    public String getSubstrateName() {
        return substrateName;
    }

    public void setSubstrateName(String substrateName) {
        this.substrateName = substrateName;
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