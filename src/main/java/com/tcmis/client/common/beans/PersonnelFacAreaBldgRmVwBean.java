package com.tcmis.client.common.beans;

import java.util.Collection;
import java.util.Collections;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: PersonnelFacAreaBldgRmVwBean <br>
 * @version: 1.0, March 12, 2013 <br>
 *****************************************************************************/

public class PersonnelFacAreaBldgRmVwBean extends BaseDataBean {

    private String companyId;
    private BigDecimal personnelId;
    private String facilityId;
    private String facilityName;
    private BigDecimal areaId;
    private String areaName;
    private String areaDescription;
    private BigDecimal buildingId;
    private String buildingName;
    private String buildingDescription;
    private BigDecimal floorId;
    private String floorDescription;
    private BigDecimal roomId;
    private String roomName;
    private String roomDescrition;
    private String interior;
    private String companyName;
    private boolean putAwayRequired;

    //constructor
	public PersonnelFacAreaBldgRmVwBean() {
	}

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public BigDecimal getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(BigDecimal personnelId) {
        this.personnelId = personnelId;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public BigDecimal getAreaId() {
        return areaId;
    }

    public void setAreaId(BigDecimal areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaDescription() {
        return areaDescription;
    }

    public void setAreaDescription(String areaDescription) {
        this.areaDescription = areaDescription;
    }

    public BigDecimal getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(BigDecimal buildingId) {
        this.buildingId = buildingId;
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

    public BigDecimal getFloorId() {
        return floorId;
    }

    public void setFloorId(BigDecimal floorId) {
        this.floorId = floorId;
    }

    public String getFloorDescription() {
        return floorDescription;
    }

    public void setFloorDescription(String floorDescription) {
        this.floorDescription = floorDescription;
    }

    public BigDecimal getRoomId() {
        return roomId;
    }

    public void setRoomId(BigDecimal roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomDescrition() {
        return roomDescrition;
    }

    public void setRoomDescrition(String roomDescrition) {
        this.roomDescrition = roomDescrition;
    }

    public String getInterior() {
        return interior;
    }

    public void setInterior(String interior) {
        this.interior = interior;
    }

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public boolean isPutAwayRequired() {
		return putAwayRequired;
	}

	public void setPutAwayRequired(boolean putAwayRequired) {
		this.putAwayRequired = putAwayRequired;
	}
}