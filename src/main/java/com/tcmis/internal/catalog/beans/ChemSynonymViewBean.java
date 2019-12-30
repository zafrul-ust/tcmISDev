package com.tcmis.internal.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ChemSynonymViewBean <br>
 * @version: 1.0, Jan 10, 2008 <br>
 *****************************************************************************/


public class ChemSynonymViewBean extends BaseDataBean {

	private String alternateName;
	private String casNumber;
	private String ecNumber;
	private String preferredName;

	//constructor
	public ChemSynonymViewBean() {
	}

	public String getAlternateName() {
		return alternateName;
	}
	public String getCasNumber() {
		return casNumber;
	}
	public String getEcNumber() {
		return ecNumber;
	}


	//getters
	public String getPreferredName() {
		return preferredName;
	}
	public void setAlternateName(String alternateName) {
		this.alternateName = alternateName;
	}
	public void setCasNumber(String casNumber) {
		this.casNumber = casNumber;
	}

	public void setEcNumber(String ecNumber) {
		this.ecNumber = ecNumber;
	}

	//setters
	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
	}
}