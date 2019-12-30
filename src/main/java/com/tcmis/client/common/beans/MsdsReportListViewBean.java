package com.tcmis.client.common.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: MsdsViewerListViewBean <br>
 * @version: 1.0, Jul 12, 2011 <br>
 *****************************************************************************/


public class MsdsReportListViewBean extends BaseDataBean {

	private String casNumber;
	private String rptChemical;
	private String percent;


	//constructor
	public MsdsReportListViewBean() {
	}


	public String getCasNumber() {
		return casNumber;
	}


	public void setCasNumber(String casNumber) {
		this.casNumber = casNumber;
	}


	public String getRptChemical() {
		return rptChemical;
	}


	public void setRptChemical(String rptChemical) {
		this.rptChemical = rptChemical;
	}


	public String getPercent() {
		return percent;
	}


	public void setPercent(String percent) {
		this.percent = percent;
	}
	
}