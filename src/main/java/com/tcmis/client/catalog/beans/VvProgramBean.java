package com.tcmis.client.catalog.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: VvProgramBean <br>
 * @version: 1.0, May 12, 2014 <br>
 *****************************************************************************/


public class VvProgramBean extends BaseDataBean {

	private String companyId;
	private String facilityId;
	private BigDecimal programId;
	private String programName;
	private String emapRequired;
	private String jspLabel;
	private BigDecimal displayOrder;
	private String status;


	//constructor
	public VvProgramBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setProgramId(BigDecimal programId) {
		this.programId = programId;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public void setEmapRequired(String emapRequired) {
		this.emapRequired = emapRequired;
	}
	public void setJspLabel(String jspLabel) {
		this.jspLabel = jspLabel;
	}
	public void setDisplayOrder(BigDecimal displayOrder) {
		this.displayOrder = displayOrder;
	}
	public void setStatus(String status) {
		this.status = status;
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public BigDecimal getProgramId() {
		return programId;
	}
	public String getProgramName() {
		return programName;
	}
	public String getEmapRequired() {
		return emapRequired;
	}
	public String getJspLabel() {
		return jspLabel;
	}
	public BigDecimal getDisplayOrder() {
		return displayOrder;
	}
	public String getStatus() {
		return status;
	}

}