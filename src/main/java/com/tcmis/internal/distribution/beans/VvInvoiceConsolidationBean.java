package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: Global.vvInvoiceConsolidationBean <br>
 * @version: 1.0, Jul 28, 2009 <br>
 *****************************************************************************/


public class VvInvoiceConsolidationBean extends BaseDataBean {

	private String invoiceConsolidation;
	private String invoiceConsolidationDesc;
	private String invoiceConsolidationLabel;


	//constructor
	public VvInvoiceConsolidationBean() {
	}

	//setters
	public void setInvoiceConsolidation(String invoiceConsolidation) {
		this.invoiceConsolidation = invoiceConsolidation;
	}
	public void setInvoiceConsolidationDesc(String invoiceConsolidationDesc) {
		this.invoiceConsolidationDesc = invoiceConsolidationDesc;
	}
	public void setInvoiceConsolidationLabel(String invoiceConsolidationLabel) {
		this.invoiceConsolidationLabel = invoiceConsolidationLabel;
	}


	//getters
	public String getInvoiceConsolidation() {
		return invoiceConsolidation;
	}
	public String getInvoiceConsolidationDesc() {
		return invoiceConsolidationDesc;
	}
	public String getInvoiceConsolidationLabel() {
		return invoiceConsolidationLabel;
	}

}