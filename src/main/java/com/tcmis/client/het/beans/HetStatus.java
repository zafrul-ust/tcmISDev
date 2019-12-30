package com.tcmis.client.het.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;

/******************************************************************************
 * CLASSNAME: HetPermitBean <br>
 * @version: 1.0, May 31, 2011 <br>
 *****************************************************************************/

public class HetStatus extends BaseInputBean {

	private Date captureTime;
	private String message;
	private String success;
	private String typeOfChange;

	public HetStatus() {
	}

	public HetStatus(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}

	public Date getCaptureTime() {
		return captureTime;
	}

	public String getMessage() {
		return message;
	}

	public String getSuccess() {
		return success;
	}

	public String getTypeOfChange() {
		return typeOfChange;
	}

	public void setCaptureTime(Date captureTime) {
		this.captureTime = captureTime;
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub

	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public void setTypeOfChange(String typeOfChange) {
		this.typeOfChange = typeOfChange;
	}

}