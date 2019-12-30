package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ChemSynonymViewBean <br>
 * @version: 1.0, Oct 10, 2005 <br>
 *****************************************************************************/


public class ChemSynonymViewBean extends BaseDataBean {

	private String preferredName;
	private String alternateName;
	private String casNumber;


	//constructor
	public ChemSynonymViewBean() {
	}

	//setters
	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
	}
	public void setAlternateName(String alternateName) {
		this.alternateName = alternateName;
	}
	public void setCasNumber(String casNumber) {
		this.casNumber = casNumber;
	}


	//getters
	public String getPreferredName() {
		return preferredName;
	}
	public String getAlternateName() {
		return alternateName;
	}
	public String getCasNumber() {
		return casNumber;
	}
}