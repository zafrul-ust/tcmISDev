package com.tcmis.client.common.beans;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.BeanHandler;
import org.apache.struts.action.ActionForm;

import java.util.Locale;

/******************************************************************************
 * CLASSNAME: SamplePageBean <br>
 * 
 * @version: 1.0, Sep 12, 2017 <br>
 *****************************************************************************/

public class EmissionPointManagementBean extends BaseInputBean {
    private String companyId;
    private String facilityId;
    private String application;
    
    private String active;
	private String appEmissionPoint;
	private String appEmissionPointDesc;
	private String facEmissionPoint;
	private String isNew;
	private String changed;
    
    //constructor
	public EmissionPointManagementBean() {}
	
    public EmissionPointManagementBean(ActionForm form, Locale locale) throws Exception {
        try {
            BeanHandler.copyAttributes(form, this, locale);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
	@Override
	public void setHiddenFormFields() {
		addHiddenFormField("facilityId");
		addHiddenFormField("companyId");
		addHiddenFormField("application");
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getAppEmissionPoint() {
		return appEmissionPoint;
	}

	public void setAppEmissionPoint(String appEmissionPoint) {
		this.appEmissionPoint = appEmissionPoint;
	}

	public String getAppEmissionPointDesc() {
		return appEmissionPointDesc;
	}

	public void setAppEmissionPointDesc(String appEmissionPointDesc) {
		this.appEmissionPointDesc = appEmissionPointDesc;
	}

	public String getFacEmissionPoint() {
		return facEmissionPoint;
	}

	public void setFacEmissionPoint(String facEmissionPoint) {
		this.facEmissionPoint = facEmissionPoint;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public String getChanged() {
		return changed;
	}

	public void setChanged(String changed) {
		this.changed = changed;
	}
}