package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;

@SuppressWarnings("serial")
public class ExpertReviewItemInputBean extends BaseInputBean {

	private String uAction;
	private BigDecimal item;
	private BigDecimal expertReviewId;
	
	public ExpertReviewItemInputBean() {
		super();
	}
	
	public ExpertReviewItemInputBean(ActionForm form) {
		super(form);
	}
	
	public String getuAction() {
		return uAction;
	}
	
	public void setuAction(String uAction) {
		this.uAction = uAction;
	}
	
	public BigDecimal getItem() {
		return item;
	}
	
	public void setItem(BigDecimal item) {
		this.item = item;
	}

	public BigDecimal getExpertReviewId() {
		return expertReviewId;
	}

	public void setExpertReviewId(BigDecimal expertReviewId) {
		this.expertReviewId = expertReviewId;
	}

	@Override
	public void setHiddenFormFields() {
		
	}
}
