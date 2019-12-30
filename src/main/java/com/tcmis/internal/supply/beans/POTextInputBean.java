package com.tcmis.internal.supply.beans;

import com.tcmis.common.framework.BaseDataBean;
import java.math.BigDecimal;
//import java.util.Date;

public class POTextInputBean extends BaseDataBean 
{
	private String 		phase;	
	private BigDecimal 	pONumber;
	private String 		text;
	private BigDecimal  textUserId;
	
	public POTextInputBean()
	{		  
	}

	public BigDecimal getTextUserId() {
		return textUserId;
	}

	public void setTextUserId(BigDecimal textUserId) {
		this.textUserId = textUserId;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public BigDecimal getPONumber() {
		return pONumber;
	}

	public void setPONumber(BigDecimal number) {
		pONumber = number;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
