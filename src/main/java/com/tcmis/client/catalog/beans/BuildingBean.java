package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: BuildingBean <br>
 * @version: 1.0, Jul 11, 2011 <br>
 *****************************************************************************/

public class BuildingBean extends BaseDataBean {

	private String companyId;
    private String buildingName;
    private String buildingDescription;
	private BigDecimal buildingId;
	private BigDecimal areaId;

	//constructor
	public BuildingBean() {
	}

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingDescription() {
        return buildingDescription;
    }

    public void setBuildingDescription(String buildingDescription) {
        this.buildingDescription = buildingDescription;
    }

    public BigDecimal getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(BigDecimal buildingId) {
        this.buildingId = buildingId;
    }

    public BigDecimal getAreaId() {
        return areaId;
    }

    public void setAreaId(BigDecimal areaId) {
        this.areaId = areaId;
    }
}