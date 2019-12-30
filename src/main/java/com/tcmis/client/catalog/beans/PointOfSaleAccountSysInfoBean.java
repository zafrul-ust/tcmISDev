package com.tcmis.client.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.util.Collection;
import java.math.BigDecimal;

/******************************************************************************
 * CLASSNAME: PointOfSaleAccountSysInfoBean <br>
 * @version: 1.0, Sep 15, 2010 <br>
 *****************************************************************************/

public class PointOfSaleAccountSysInfoBean extends BaseDataBean {

	private String accountSysId;
	private String facilityId;
	private String orderingLimit;
	private Collection directedChargeAppColl;
	private Collection prRulesColl;
	private Collection facAccountSysPoForDirectColl;
	private Collection facAccountSysPoForIndirectColl;
	private Collection chargeNumberForDirectColl;
	private Collection chargeNumberForIndirectColl;
	private String workAreaOption;
	private String financeApproverRequired;
	private Collection directedChargeForDirectColl;
	private Collection directedChargeForIndirectColl;
	private String poNumberForDirect = "";
	private String poNumberForIndirect = "";
	private String poLineForDirect = "";
	private String poLineForIndirect = "";
	private String selectedChargeType = "d";
    //the following variable is to keep track of how user can enter data for directed_charge
    private String allowBothChargeType = "";
    private String facLocAppDirectedChargeAllowNull = "N";


     //constructor
 	public PointOfSaleAccountSysInfoBean() {
 	}

	public String getAccountSysId() {
		return accountSysId;
	}

	public void setAccountSysId(String accountSysId) {
		this.accountSysId = accountSysId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getOrderingLimit() {
		return orderingLimit;
	}

	public void setOrderingLimit(String orderingLimit) {
		this.orderingLimit = orderingLimit;
	}

	public Collection getDirectedChargeAppColl() {
		return directedChargeAppColl;
	}

	public void setDirectedChargeAppColl(Collection directedChargeAppColl) {
		this.directedChargeAppColl = directedChargeAppColl;
	}

	public Collection getPrRulesColl() {
		return prRulesColl;
	}

	public void setPrRulesColl(Collection prRulesColl) {
		this.prRulesColl = prRulesColl;
	}

	public Collection getFacAccountSysPoForDirectColl() {
		return facAccountSysPoForDirectColl;
	}

	public void setFacAccountSysPoForDirectColl(Collection facAccountSysPoForDirectColl) {
		this.facAccountSysPoForDirectColl = facAccountSysPoForDirectColl;
	}

	public Collection getFacAccountSysPoForIndirectColl() {
		return facAccountSysPoForIndirectColl;
	}

	public void setFacAccountSysPoForIndirectColl(Collection facAccountSysPoForIndirectColl) {
		this.facAccountSysPoForIndirectColl = facAccountSysPoForIndirectColl;
	}

	public Collection getChargeNumberForDirectColl() {
		return chargeNumberForDirectColl;
	}

	public void setChargeNumberForDirectColl(Collection chargeNumberForDirectColl) {
		this.chargeNumberForDirectColl = chargeNumberForDirectColl;
	}

	public Collection getChargeNumberForIndirectColl() {
		return chargeNumberForIndirectColl;
	}

	public void setChargeNumberForIndirectColl(Collection chargeNumberForIndirectColl) {
		this.chargeNumberForIndirectColl = chargeNumberForIndirectColl;
	}

	public String getWorkAreaOption() {
		return workAreaOption;
	}

	public void setWorkAreaOption(String workAreaOption) {
		this.workAreaOption = workAreaOption;
	}

	public String getFinanceApproverRequired() {
		return financeApproverRequired;
	}

	public void setFinanceApproverRequired(String financeApproverRequired) {
		this.financeApproverRequired = financeApproverRequired;
	}

	public Collection getDirectedChargeForDirectColl() {
		return directedChargeForDirectColl;
	}

	public void setDirectedChargeForDirectColl(Collection directedChargeForDirectColl) {
		this.directedChargeForDirectColl = directedChargeForDirectColl;
	}

	public Collection getDirectedChargeForIndirectColl() {
		return directedChargeForIndirectColl;
	}

	public void setDirectedChargeForIndirectColl(Collection directedChargeForIndirectColl) {
		this.directedChargeForIndirectColl = directedChargeForIndirectColl;
	}

	public String getPoLineForIndirect() {
		return poLineForIndirect;
	}

	public void setPoLineForIndirect(String poLineForIndirect) {
		this.poLineForIndirect = poLineForIndirect;
	}

	public String getPoLineForDirect() {
		return poLineForDirect;
	}

	public void setPoLineForDirect(String poLineForDirect) {
		this.poLineForDirect = poLineForDirect;
	}

	public String getPoNumberForIndirect() {
		return poNumberForIndirect;
	}

	public void setPoNumberForIndirect(String poNumberForIndirect) {
		this.poNumberForIndirect = poNumberForIndirect;
	}

	public String getPoNumberForDirect() {
		return poNumberForDirect;
	}

	public void setPoNumberForDirect(String poNumberForDirect) {
		this.poNumberForDirect = poNumberForDirect;
	}

	public String getSelectedChargeType() {
		return selectedChargeType;
	}

	public void setSelectedChargeType(String selectedChargeType) {
		this.selectedChargeType = selectedChargeType;
	}

    public String getAllowBothChargeType() {
        return allowBothChargeType;
    }

    public void setAllowBothChargeType(String allowBothChargeType) {
        this.allowBothChargeType = allowBothChargeType;
    }

    public String getFacLocAppDirectedChargeAllowNull() {
        return facLocAppDirectedChargeAllowNull;
    }

    public void setFacLocAppDirectedChargeAllowNull(String facLocAppDirectedChargeAllowNull) {
        this.facLocAppDirectedChargeAllowNull = facLocAppDirectedChargeAllowNull;
    }
} //end of class