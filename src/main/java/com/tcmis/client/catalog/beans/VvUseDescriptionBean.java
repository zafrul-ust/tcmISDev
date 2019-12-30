package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: VvUseDescriptionBean <br>
 * @version: 1.0, Jul 11, 2011 <br>
 *****************************************************************************/

public class VvUseDescriptionBean extends BaseDataBean {

	private BigDecimal useId;
    private String useName;
    private String companyId;
    private String catalogCompanyId;
    private String catalogId;

    //constructor
	public VvUseDescriptionBean() {
	}

    public BigDecimal getUseId() {
        return useId;
    }

    public void setUseId(BigDecimal useId) {
        this.useId = useId;
    }

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName;
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