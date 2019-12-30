package com.tcmis.client.common.beans;

import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.HubBaseInputBean;

public class ChargeNumberInputBean extends HubBaseInputBean{

    private String areaId;
    private String buildingId;
    private String floorId;
    private String roomId;
    private String facilityId;
    private String reportingEntityId;
    private String deptId;
	private String accountSysId;
	
	public ChargeNumberInputBean(ActionForm inputForm) {
		super(inputForm);
	}

	public ChargeNumberInputBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub
		
	}
	
    public String getAccountSysId() {
        return accountSysId;
    }
    public void setAccountSysId(String accountSysId) {
        this.accountSysId = accountSysId;
    }
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptId() {
		return deptId;
	}
	public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

	public String getFloorId() {
		return floorId;
	}

	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}

	public String getReportingEntityId() {
		return reportingEntityId;
	}
	
	public void setReportingEntityId(String reportingEntityId) {
		this.reportingEntityId = reportingEntityId;
	}
}
