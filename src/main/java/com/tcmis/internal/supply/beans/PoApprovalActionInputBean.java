package com.tcmis.internal.supply.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseInputBean;

@SuppressWarnings("serial")
public class PoApprovalActionInputBean extends BaseInputBean {

	private BigDecimal radianPo;
	private String poApprovalCode;
	private String actionComment;
	
	public BigDecimal getRadianPo() {
		return radianPo;
	}

	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}

	public String getPoApprovalCode() {
		return poApprovalCode;
	}

	public void setPoApprovalCode(String poApprovalCode) {
		this.poApprovalCode = poApprovalCode;
	}

	public String getActionComment() {
		return actionComment;
	}

	public void setActionComment(String actionComment) {
		this.actionComment = actionComment;
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub
		
	}
}
