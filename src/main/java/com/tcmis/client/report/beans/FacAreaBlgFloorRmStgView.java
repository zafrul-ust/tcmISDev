package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: FacAreaBlgFloorRmStgView <br>
 * @version: 1.0, July 7, 2012 <br>
 *****************************************************************************/


public class FacAreaBlgFloorRmStgView extends BaseDataBean {

	private String companyId;
	private String facilityId;
	private BigDecimal areaId;
	private BigDecimal buildingId;
	private BigDecimal floorId;
	private BigDecimal roomId;
	private String storageAreaId;
	private String storageAreaDesc;
	
	private String deptId;
	private String reportingEntityId;
    private String application;
    private String applicationDesc;
    private BigDecimal personnelId;
    private String status;
    private BigDecimal applicationId;

    //constructor
	public FacAreaBlgFloorRmStgView() {
	}


	public BigDecimal getAreaId() {
		return areaId;
	}


	public void setAreaId(BigDecimal areaId) {
		this.areaId = areaId;
	}


	public BigDecimal getBuildingId() {
		return buildingId;
	}


	public void setBuildingId(BigDecimal buildingId) {
		this.buildingId = buildingId;
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


	public BigDecimal getFloorId() {
		return floorId;
	}


	public void setFloorId(BigDecimal floorId) {
		this.floorId = floorId;
	}


	public BigDecimal getRoomId() {
		return roomId;
	}


	public void setRoomId(BigDecimal roomId) {
		this.roomId = roomId;
	}


	public String getStorageAreaDesc() {
		return storageAreaDesc;
	}


	public void setStorageAreaDesc(String storageAreaDesc) {
		this.storageAreaDesc = storageAreaDesc;
	}


	public String getStorageAreaId() {
		return storageAreaId;
	}


	public void setStorageAreaId(String storageAreaId) {
		this.storageAreaId = storageAreaId;
	}


	public String getDeptId() {
		return deptId;
	}


	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}


	public String getReportingEntityId() {
		return reportingEntityId;
	}


	public void setReportingEntityId(String reportingEntityId) {
		this.reportingEntityId = reportingEntityId;
	}

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getApplicationDesc() {
        return applicationDesc;
    }

    public void setApplicationDesc(String applicationDesc) {
        this.applicationDesc = applicationDesc;
    }

    public BigDecimal getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(BigDecimal personnelId) {
        this.personnelId = personnelId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(BigDecimal applicationId) {
        this.applicationId = applicationId;
    }
}