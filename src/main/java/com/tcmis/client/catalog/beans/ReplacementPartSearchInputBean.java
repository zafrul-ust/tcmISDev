package com.tcmis.client.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ReplacementPartSearchInputBean <br>
 * @version: 1.0, Oct 23, 2006 <br>
 *****************************************************************************/

public class ReplacementPartSearchInputBean
    extends BaseDataBean {

  private String catalogId;
  private String companyId;
  private String catalogCompanyId;
  private String searchArgument;
  private String sourcePage;
  private String application;
  private String facilityId;

  //constructor
  public ReplacementPartSearchInputBean() {
  }

  //setters
  public void setCatalogId(String catalogId) {
    this.catalogId = catalogId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	//getters
  public String getCatalogId() {
    return catalogId;
  }

  public String getCompanyId() {
    return companyId;
  }

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public String getSearchArgument() {
		return searchArgument;
	}

	public String getSourcePage() {
		return sourcePage;
	}

	public void setSourcePage(String sourcePage) {
		this.sourcePage = sourcePage;
	}

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }
}