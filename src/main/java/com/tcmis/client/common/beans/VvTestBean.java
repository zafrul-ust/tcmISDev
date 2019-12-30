package com.tcmis.client.common.beans;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: VvTestBean <br>
 * @version: 1.0, May 2, 2011 <br>
 *****************************************************************************/

public class VvTestBean
    extends BaseDataBean {

    private String companyId;
    private String catalogId;
    private BigDecimal testId;
    private String testDesc;
    private String Criteria;
    private String shortName;
    private String originalShortName;
    private String originalTestDesc;
    private String originalCriteria;

    private String status;
    private String active;
    private String defaultTest;

    //constructor
  	public VvTestBean() {
  	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCriteria() {
		return Criteria;
	}

	public void setCriteria(String criteria) {
		Criteria = criteria;
	}

	public String getTestDesc() {
		return testDesc;
	}

	public void setTestDesc(String testDesc) {
		this.testDesc = testDesc;
	}

	public BigDecimal getTestId() {
		return testId;
	}

	public void setTestId(BigDecimal testId) {
		this.testId = testId;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getOriginalCriteria() {
		return originalCriteria;
	}

	public void setOriginalCriteria(String originalCriteria) {
		this.originalCriteria = originalCriteria;
	}

	public String getOriginalShortName() {
		return originalShortName;
	}

	public void setOriginalShortName(String originalShortName) {
		this.originalShortName = originalShortName;
	}

	public String getOriginalTestDesc() {
		return originalTestDesc;
	}

	public void setOriginalTestDesc(String originalTestDesc) {
		this.originalTestDesc = originalTestDesc;
	}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getDefaultTest() {
        return defaultTest;
    }

    public void setDefaultTest(String defaultTest) {
        this.defaultTest = defaultTest;
    }
}