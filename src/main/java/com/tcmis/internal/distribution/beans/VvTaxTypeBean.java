package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: Global.vvTaxTypeBean <br>
 * @version: 1.0, Aug 23, 2009 <br>
 *****************************************************************************/


public class VvTaxTypeBean extends BaseDataBean {

	private String taxType;
	private String taxTypeDesc;
	private String taxTypeJkey;


	//constructor
	public VvTaxTypeBean() {
	}

	//setters
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
	public void setTaxTypeDesc(String taxTypeDesc) {
		this.taxTypeDesc = taxTypeDesc;
	}
	public void setTaxTypeJkey(String taxTypeJkey) {
		this.taxTypeJkey = taxTypeJkey;
	}


	//getters
	public String getTaxType() {
		return taxType;
	}
	public String getTaxTypeDesc() {
		return taxTypeDesc;
	}
	public String getTaxTypeJkey() {
		return taxTypeJkey;
	}

}