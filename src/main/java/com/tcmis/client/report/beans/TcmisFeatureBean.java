package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: TcmisFeatureBean <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class TcmisFeatureBean extends BaseDataBean {

	private String companyId;
	private String feature;
	private BigDecimal featureMode;
	private String featureMessage;


	//constructor
	public TcmisFeatureBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public void setFeatureMode(BigDecimal featureMode) {
		this.featureMode = featureMode;
	}
	public void setFeatureMessage(String featureMessage) {
		this.featureMessage = featureMessage;
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public String getFeature() {
		return feature;
	}
	public BigDecimal getFeatureMode() {
		return featureMode;
	}
	public String getFeatureMessage() {
		return featureMessage;
	}
}