package com.tcmis.client.common.beans;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: TestDefinitionInputBean <br>
 * @version: 1.0, May 2, 2011 <br>
 *****************************************************************************/

public class TestDefinitionInputBean
    extends BaseDataBean {

	private String facilityId;	
    private String companyId;
    private String catalogCompanyId;
    private String catalogId;
    private String catPartNo;
    private BigDecimal partGroupNo;
    private BigDecimal testId;
  
    private String uAction;
    private BigDecimal frequency;
    private String frequencyType;
    private String frequencyTypeDisplay;
    private String active;
    private String requireCustomerResponse;
    private String frequencyUnit;
    private String frequencyUnitDisplay;
    
    private String activeTestsOnly;


    //constructor
  	public TestDefinitionInputBean() {
  	}

	
	public String getuAction() {
		return uAction;
	}

	public void setuAction(String uAction) {
		this.uAction = uAction;
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

	public String getCatPartNo() {
		return catPartNo;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}

	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}


	public String getFacilityId() {
		return facilityId;
	}


	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}


	public BigDecimal getTestId() {
		return testId;
	}


	public void setTestId(BigDecimal testId) {
		this.testId = testId;
	}

    public BigDecimal getFrequency() {
        return frequency;
    }

    public void setFrequency(BigDecimal frequency) {
        this.frequency = frequency;
    }

    public String getFrequencyType() {
        return frequencyType;
    }

    public void setFrequencyType(String frequencyType) {
        this.frequencyType = frequencyType;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getRequireCustomerResponse() {
        return requireCustomerResponse;
    }

    public void setRequireCustomerResponse(String requireCustomerResponse) {
        this.requireCustomerResponse = requireCustomerResponse;
    }

    public String getFrequencyTypeDisplay() {
        return frequencyTypeDisplay;
    }

    public void setFrequencyTypeDisplay(String frequencyTypeDisplay) {
        this.frequencyTypeDisplay = frequencyTypeDisplay;
    }

    public String getFrequencyUnit() {
        return frequencyUnit;
    }

    public void setFrequencyUnit(String frequencyUnit) {
        this.frequencyUnit = frequencyUnit;
    }

    public String getFrequencyUnitDisplay() {
        return frequencyUnitDisplay;
    }

    public void setFrequencyUnitDisplay(String frequencyUnitDisplay) {
        this.frequencyUnitDisplay = frequencyUnitDisplay;
    }


	public String getActiveTestsOnly() {
		return activeTestsOnly;
	}


	public void setActiveTestsOnly(String activeTestsOnly) {
		this.activeTestsOnly = activeTestsOnly;
	}
}