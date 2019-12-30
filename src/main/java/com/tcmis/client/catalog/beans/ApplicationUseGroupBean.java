package com.tcmis.client.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.util.Collection;

/******************************************************************************
 * CLASSNAME: ApplicationUseGroupBean <br>
 * @version: 1.0, Oct 24, 2011 <br>
 *****************************************************************************/

public class ApplicationUseGroupBean extends BaseDataBean {

	private String companyId;
 	private String facilityId;
    private String application;
    private String applicationDesc;
    private String inventoryGroup;
    private String inventoryGroupName;
    private String applicationUseGroupId;
    private String applicationUseGroupName;
    private Collection applicationColl;
    private String applicationUseGroupDesc;

     //constructor
 	public ApplicationUseGroupBean() {
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

    public String getInventoryGroup() {
        return inventoryGroup;
    }

    public void setInventoryGroup(String inventoryGroup) {
        this.inventoryGroup = inventoryGroup;
    }

    public String getInventoryGroupName() {
        return inventoryGroupName;
    }

    public void setInventoryGroupName(String inventoryGroupName) {
        this.inventoryGroupName = inventoryGroupName;
    }

    public String getApplicationUseGroupId() {
        return applicationUseGroupId;
    }

    public void setApplicationUseGroupId(String applicationUseGroupId) {
        this.applicationUseGroupId = applicationUseGroupId;
    }

    public String getApplicationUseGroupName() {
        return applicationUseGroupName;
    }

    public void setApplicationUseGroupName(String applicationUseGroupName) {
        this.applicationUseGroupName = applicationUseGroupName;
    }

    public Collection getApplicationColl() {
        return applicationColl;
    }

    public void setApplicationColl(Collection applicationColl) {
        this.applicationColl = applicationColl;
    }

    public String getApplicationUseGroupDesc() {
        return applicationUseGroupDesc;
    }

    public void setApplicationUseGroupDesc(String applicationUseGroupDesc) {
        this.applicationUseGroupDesc = applicationUseGroupDesc;
    }
}