package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Collection;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: UserHubCarrierViewBean <br>
 * @version: 1.0, Oct 25, 2009 <br>
 *****************************************************************************/


public class UserHubCarrierViewBean extends BaseDataBean{

	private String companyId;
	private String branchPlant;
	private String hubName;
	private BigDecimal personnelId;
	private String carrierAccountId;
	private String carrierName;
	private Collection carrierCollection;

	//constructor
	public UserHubCarrierViewBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}

	public void setHubName(String hubName) {
		this.hubName = hubName;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	public void setCarrierAccountId(String carrierAccountId) {
		this.carrierAccountId = carrierAccountId;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public void setCarrierCollection(Collection carrierCollection) {
		this.carrierCollection = carrierCollection;
	}

	//getters
	public String getCompanyId() {
		return companyId;
	}

	public String getBranchPlant() {
		return branchPlant;
	}

	public String getHubName() {
		return hubName;
	}

	public BigDecimal getPersonnelId() {
		return personnelId;
	}

	public String getCarrierAccountId() {
		return carrierAccountId;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public Collection getCarrierCollection() {
		return carrierCollection;
	}
}