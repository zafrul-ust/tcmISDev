package com.tcmis.internal.hub.beans;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: GHSLabelReqsBean <br>
 * @version: 1.0, May 18, 2013 <br>
 *****************************************************************************/


public class GHSLabelReqsBean extends BaseDataBean {

	private boolean								productName;
	private boolean								supplierInfo;
	private boolean								signalWord;
	private boolean								pictogram;
	private boolean								precautionaryStatement;
	private boolean								hazardStatement;


	//constructor
	public GHSLabelReqsBean() {
	}

	//getters
	public boolean getProductName() {
		return productName;
	}


	public boolean getSupplierInfo() {
		return supplierInfo;
	}


	public boolean getSignalWord() {
		return signalWord;
	}


	public boolean getPictogram() {
		return pictogram;
	}


	public boolean getPrecautionaryStatement() {
		return precautionaryStatement;
	}


	public boolean getHazardStatement() {
		return hazardStatement;
	}

	//setters
	public void setProductName(boolean productName) {
		this.productName = productName;
	}


	public void setSupplierInfo(boolean supplierInfo) {
		this.supplierInfo = supplierInfo;
	}


	public void setSignalWord(boolean signalWord) {
		this.signalWord = signalWord;
	}


	public void setPictogram(boolean pictogram) {
		this.pictogram = pictogram;
	}


	public void setPrecautionaryStatement(boolean precautionaryStatement) {
		this.precautionaryStatement = precautionaryStatement;
	}


	public void setHazardStatement(boolean hazardStatement) {
		this.hazardStatement = hazardStatement;
	}

}