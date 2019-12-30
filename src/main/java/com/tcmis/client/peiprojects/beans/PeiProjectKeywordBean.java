package com.tcmis.client.peiprojects.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PeiProjectKeywordBean <br>
 * @version: 1.0, Dec 14, 2005 <br>
 *****************************************************************************/


public class PeiProjectKeywordBean extends BaseDataBean {

	private BigDecimal projectId;
	private String keywordId;
	private String companyId;

	//constructor
	public PeiProjectKeywordBean() {
	}

	//setters
	public void setProjectId(BigDecimal projectId) {
		this.projectId = projectId;
	}
	public void setKeywordId(String keywordId) {
		this.keywordId = keywordId;
	}
  public void setCompanyId(String companyId) {
	 this.companyId = companyId;
	}

	//getters
	public BigDecimal getProjectId() {
		return projectId;
	}
	public String getKeywordId() {
		return keywordId;
	}
  public String getCompanyId() {
	 return companyId;
	}
}