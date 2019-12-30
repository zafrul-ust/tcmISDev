package com.tcmis.client.catalog.beans;

import java.util.Collection;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: PersonnelCatalogViewBean <br>
 * @version: 1.0, Dec 5, 2006 <br>
 *****************************************************************************/

public class FacilityApprovalRolesBean extends BaseDataBean {

    private String facilityId;
    private String approvalRole;
    private Collection approvalRoleColl;

    //constructor
    public FacilityApprovalRolesBean() {
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getApprovalRole() {
        return approvalRole;
    }

    public void setApprovalRole(String approvalRole) {
        this.approvalRole = approvalRole;
    }

    public Collection getApprovalRoleColl() {
        return approvalRoleColl;
    }

    public void setApprovalRoleColl(Collection approvalRoleColl) {
        this.approvalRoleColl = approvalRoleColl;
    }
}