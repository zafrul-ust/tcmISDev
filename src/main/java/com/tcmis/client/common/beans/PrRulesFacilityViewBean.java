package com.tcmis.client.common.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: Pr_Rules_Facility_View <br>
 * @version: 1.0, March 24, 2011 <br>
 *****************************************************************************/


public class PrRulesFacilityViewBean extends BaseDataBean {

	private String facilityId;
	private String facilityName;
	private String accountSysId;
	private String accountSysName;
	private String chargeType;
	private String companyId;
	private BigDecimal personnelId;
	private String chargeLabel1;
	private String chargeLabel2;
	private String chargeLabel3;
	private String chargeLabel4;
	private String chargeId1;
	private String chargeId2;
	private String chargeId3;
	private String chargeId4;
    private String chargeValid1;
    private String chargeValid2;
    private String chargeValid3;
    private String chargeValid4;

    private String printChargeDesc1;
    private String printChargeDesc2;
    private String printChargeDesc3;
    private String printChargeDesc4;
    private String chargeNumberFormat1; 
    private String chargeNumberFormat2;
    private String chargeNumberFormat3; 
    private String chargeNumberFormat4; 

    //constructor
	public PrRulesFacilityViewBean() {
	}
	
	public String getAccountSysId() {
		return accountSysId;
	}

	public void setAccountSysId(String accountSysId) {
		this.accountSysId = accountSysId;
	}

	public String getAccountSysName() {
		return accountSysName;
	}

	public void setAccountSysName(String accountSysName) {
		this.accountSysName = accountSysName;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public BigDecimal getPersonnelId() {
		return personnelId;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	public String getChargeLabel1() {
		return chargeLabel1;
	}

	public void setChargeLabel1(String chargeLabel1) {
		this.chargeLabel1 = chargeLabel1;
	}

	public String getChargeLabel2() {
		return chargeLabel2;
	}

	public void setChargeLabel2(String chargeLabel2) {
		this.chargeLabel2 = chargeLabel2;
	}

	public String getChargeLabel3() {
		return chargeLabel3;
	}

	public void setChargeLabel3(String chargeLabel3) {
		this.chargeLabel3 = chargeLabel3;
	}

	public String getChargeLabel4() {
		return chargeLabel4;
	}

	public void setChargeLabel4(String chargeLabel4) {
		this.chargeLabel4 = chargeLabel4;
	}
	
	public String getChargeId1() {
		return chargeId1;
	}

	public void setChargeId1(String chargeId1) {
		this.chargeId1 = chargeId1;
	}

	public String getChargeId2() {
		return chargeId2;
	}

	public void setChargeId2(String chargeId2) {
		this.chargeId2 = chargeId2;
	}

	public String getChargeId3() {
		return chargeId3;
	}

	public void setChargeId3(String chargeId3) {
		this.chargeId3 = chargeId3;
	}

	public String getChargeId4() {
		return chargeId4;
	}

	public void setChargeId4(String chargeId4) {
		this.chargeId4 = chargeId4;
	}

    public String getChargeValid1() {
        return chargeValid1;
    }

    public void setChargeValid1(String chargeValid1) {
        this.chargeValid1 = chargeValid1;
    }

    public String getChargeValid2() {
        return chargeValid2;
    }

    public void setChargeValid2(String chargeValid2) {
        this.chargeValid2 = chargeValid2;
    }

    public String getChargeValid3() {
        return chargeValid3;
    }

    public void setChargeValid3(String chargeValid3) {
        this.chargeValid3 = chargeValid3;
    }

    public String getChargeValid4() {
        return chargeValid4;
    }

    public void setChargeValid4(String chargeValid4) {
        this.chargeValid4 = chargeValid4;
    }
 
    public String getChargeNumberFormat1() {
        return chargeNumberFormat1;
    }

    public void setChargeNumberFormat1(String chargeNumberFormat1) {
        this.chargeNumberFormat1 = chargeNumberFormat1;
    }
    public String getChargeNumberFormat2() {
        return chargeNumberFormat2;
    }

    public void setChargeNumberFormat2(String chargeNumberFormat2) {
        this.chargeNumberFormat2 = chargeNumberFormat2;
    }
    public String getChargeNumberFormat3() {
        return chargeNumberFormat3;
    }

    public void setChargeNumberFormat3(String chargeNumberFormat3) {
        this.chargeNumberFormat3 = chargeNumberFormat3;
    }
    public String getChargeNumberFormat4() {
        return chargeNumberFormat4;
    }

    public void setChargeNumberFormat4(String chargeNumberFormat4) {
        this.chargeNumberFormat4 = chargeNumberFormat4;
    }
    public String getPrintChargeDesc1() {
        return printChargeDesc1;
    }

    public void setPrintChargeDesc1(String printChargeDesc1) {
        this.printChargeDesc1 = printChargeDesc1;
    }
    public String getPrintChargeDesc2() {
        return printChargeDesc2;
    }

    public void setPrintChargeDesc2(String printChargeDesc2) {
        this.printChargeDesc2 = printChargeDesc2;
    }
    public String getPrintChargeDesc3() {
        return printChargeDesc3;
    }

    public void setPrintChargeDesc3(String printChargeDesc3) {
        this.printChargeDesc3 = printChargeDesc3;
    }
    public String getPrintChargeDesc4() {
        return printChargeDesc4;
    }

    public void setPrintChargeDesc4(String printChargeDesc4) {
        this.printChargeDesc4 = printChargeDesc4;
    }
}