package com.tcmis.client.common.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: FloorBean <br>
 * @version: 1.0, Jan 12, 2012 <br>
 *****************************************************************************/

public class FloorBean extends BaseDataBean {

	private BigDecimal buildingId;
	private String companyId;
	private String description;
	private BigDecimal floorId = null;

	//constructor
	public FloorBean() {
	}

	public BigDecimal getBuildingId() {
		return buildingId;
	}

	//getters
	public String getCompanyId() {
		return companyId;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getFloorId() {
		return floorId;
	}

	public boolean hasDescription() {
		return !StringHandler.isBlankString(description);
	}

	public boolean isNewFloor() {
		return floorId == null;
	}


	public void setBuildingId(BigDecimal buildingId) {
		this.buildingId = buildingId;
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFloorId(BigDecimal floorId) {
		this.floorId = floorId;
	}
}