package com.tcmis.client.catalog.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CatAddStatusViewBean <br>
 * @version: 1.0, Jun 8, 2009 <br>
 *****************************************************************************/


public class CatAddStatusViewBean extends BaseDataBean {

	private BigDecimal requestId;
	private String facilityId;
	private String approvalRole;
	private String status;
	private String chemicalApprovers;
	private Date approvalDate;
	private String reason;
	private BigDecimal approvalGroup;
	private BigDecimal approvalGroupSeq;
	private String application;
    private String restricted;
    private String beforeTcmQc;
    private String beforeTcmSpec;


    //constructor
	public CatAddStatusViewBean() {
	}

	//setters
	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setApprovalRole(String approvalRole) {
		this.approvalRole = approvalRole;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setChemicalApprovers(String chemicalApprovers) {
		this.chemicalApprovers = chemicalApprovers;
	}
	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public void setApprovalGroup(BigDecimal approvalGroup) {
		this.approvalGroup = approvalGroup;
	}
	public void setApprovalGroupSeq(BigDecimal approvalGroupSeq) {
		this.approvalGroupSeq = approvalGroupSeq;
	}
	public void setApplication(String application) {
		this.application = application;
	}

    public void setRestricted(String restricted) {
        this.restricted = restricted;
    }

    //getters
	public BigDecimal getRequestId() {
		return requestId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getApprovalRole() {
		return approvalRole;
	}
	public String getStatus() {
		return status;
	}
	public String getChemicalApprovers() {
		return chemicalApprovers;
	}
	public Date getApprovalDate() {
		return approvalDate;
	}
	public String getReason() {
		return reason;
	}
	public BigDecimal getApprovalGroup() {
		return approvalGroup;
	}
	public BigDecimal getApprovalGroupSeq() {
		return approvalGroupSeq;
	}
	public String getApplication() {
		return application;
	}

    public String getRestricted() {
        return restricted;
    }

    public String getBeforeTcmQc() {
        return beforeTcmQc;
    }

    public void setBeforeTcmQc(String beforeTcmQc) {
        this.beforeTcmQc = beforeTcmQc;
    }

    public String getBeforeTcmSpec() {
        return beforeTcmSpec;
    }

    public void setBeforeTcmSpec(String beforeTcmSpec) {
        this.beforeTcmSpec = beforeTcmSpec;
    }
}