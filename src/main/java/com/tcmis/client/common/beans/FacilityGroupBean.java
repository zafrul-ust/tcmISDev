package com.tcmis.client.common.beans;

import java.util.Collection;
import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: FacilityGroupBean <br>
 * @version: 1.0, Aug 11, 2012 <br>
 *****************************************************************************/

public class FacilityGroupBean extends BaseDataBean {

	private String companyId;
    private String facilityGroupId;
    private String facilityGroupDescription;
    private String facilityId;
    private Collection facilityList;
    private String facilityName;

    //constructor
	public FacilityGroupBean() {
	}

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getFacilityGroupId() {
        return facilityGroupId;
    }

    public void setFacilityGroupId(String facilityGroupId) {
        this.facilityGroupId = facilityGroupId;
    }

    public String getFacilityGroupDescription() {
        return facilityGroupDescription;
    }

    public void setFacilityGroupDescription(String facilityGroupDescription) {
        this.facilityGroupDescription = facilityGroupDescription;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public Collection getFacilityList() {
        return facilityList;
    }

    public void setFacilityList(Collection facilityList) {
        this.facilityList = facilityList;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }
}