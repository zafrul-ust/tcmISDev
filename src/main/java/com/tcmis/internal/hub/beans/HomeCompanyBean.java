package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: HomeCompanyBean <br>
 * @version: 1.0, Apr 20, 2005 <br>
 *****************************************************************************/


public class HomeCompanyBean extends BaseDataBean {

	private String homeCompanyId;
	private String homeCurrencyId;
	private String operatingCompanyId;
	private String operatingEntityName;
	private String poImageUrl;
	private String billToCompanyId;
	private String billToLocationId;
	private String homeCompanyShortName;
	private String remitToCompanyId;
	private String remitToLocationId;
	private String poTermsAndConditions;
	private String taxId;
        private String logoImageUrl;

	//constructor
	public HomeCompanyBean() {
	}

	//setters
	public void setHomeCompanyId(String homeCompanyId) {
		this.homeCompanyId = homeCompanyId;
	}
	public void setHomeCurrencyId(String homeCurrencyId) {
		this.homeCurrencyId = homeCurrencyId;
	}
	public void setOperatingCompanyId(String operatingCompanyId) {
		this.operatingCompanyId = operatingCompanyId;
	}
	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}
	public void setPoImageUrl(String poImageUrl) {
		this.poImageUrl = poImageUrl;
	}
	public void setBillToCompanyId(String billToCompanyId) {
		this.billToCompanyId = billToCompanyId;
	}
	public void setBillToLocationId(String billToLocationId) {
		this.billToLocationId = billToLocationId;
	}
	public void setHomeCompanyShortName(String homeCompanyShortName) {
		this.homeCompanyShortName = homeCompanyShortName;
	}
	public void setRemitToCompanyId(String remitToCompanyId) {
		this.remitToCompanyId = remitToCompanyId;
	}
	public void setRemitToLocationId(String remitToLocationId) {
		this.remitToLocationId = remitToLocationId;
	}
	public void setPoTermsAndConditions(String poTermsAndConditions) {
		this.poTermsAndConditions = poTermsAndConditions;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
        public void setLogoImageUrl(String logoImageUrl) {
                this.logoImageUrl = logoImageUrl;
        }

	//getters
	public String getHomeCompanyId() {
		return homeCompanyId;
	}
	public String getHomeCurrencyId() {
		return homeCurrencyId;
	}
	public String getOperatingCompanyId() {
		return operatingCompanyId;
	}
	public String getOperatingEntityName() {
		return operatingEntityName;
	}
	public String getPoImageUrl() {
		return poImageUrl;
	}
	public String getBillToCompanyId() {
		return billToCompanyId;
	}
	public String getBillToLocationId() {
		return billToLocationId;
	}
	public String getHomeCompanyShortName() {
		return homeCompanyShortName;
	}
	public String getRemitToCompanyId() {
		return remitToCompanyId;
	}
	public String getRemitToLocationId() {
		return remitToLocationId;
	}
	public String getPoTermsAndConditions() {
		return poTermsAndConditions;
	}
	public String getTaxId() {
		return taxId;
	}
        public String getLogoImageUrl() {
                return logoImageUrl;
        }
}