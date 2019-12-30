package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CostReportFieldBean <br>
 * @version: 1.0, Jul 15, 2008 <br>
 *****************************************************************************/


public class CostReportFieldBean extends BaseDataBean {

	private String fieldName;


	//constructor
	public CostReportFieldBean() {
	}

	//setters
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	//getters
	public String getListName() {
		return fieldName;
	}
}