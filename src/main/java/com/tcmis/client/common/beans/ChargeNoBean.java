package com.tcmis.client.common.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME:  <br>
 * @version: 1.0, March 25, 2011 <br>
 *****************************************************************************/


public class ChargeNoBean extends BaseDataBean {

	private String chargeId1;
	private String status;
	private String originalStatus;
	private String chargeNumber1;
	private String chargeNumber2;
	private String chargeNumber3;
	private String chargeNumber4;
	private String originalChargeNo1;
	private String originalChargeNo2;
	private String originalChargeNo3;
	private String originalChargeNo4;
	private String changed;
	private BigDecimal chargeNo1Alias;
	private BigDecimal chargeNo2Alias;
	private BigDecimal chargeNo3Alias;
	private BigDecimal chargeNo4Alias;
	private String description;
	private String originalDescription;

	public String getOriginalDescription() {
		return originalDescription;
	}


	public void setOriginalDescription(String originalDescription) {
		this.originalDescription = originalDescription;
	}

	public String getChanged() {
		return changed;
	}


	public void setChanged(String changed) {
		this.changed = changed;
	}


	public String getOriginalChargeNo1() {
		return originalChargeNo1;
	}


	public void setOriginalChargeNo1(String originalChargeNo1) {
		this.originalChargeNo1 = originalChargeNo1;
	}


	public String getOriginalChargeNo2() {
		return originalChargeNo2;
	}


	public void setOriginalChargeNo2(String originalChargeNo2) {
		this.originalChargeNo2 = originalChargeNo2;
	}


	public String getOriginalChargeNo3() {
		return originalChargeNo3;
	}


	public void setOriginalChargeNo3(String originalChargeNo3) {
		this.originalChargeNo3 = originalChargeNo3;
	}


	public String getOriginalChargeNo4() {
		return originalChargeNo4;
	}


	public void setOriginalChargeNo4(String originalChargeNo4) {
		this.originalChargeNo4 = originalChargeNo4;
	}


	//	constructor
	public ChargeNoBean() {
	}

	
	public String getChargeId1() {
		return chargeId1;
	}

	public void setChargeId1(String chargeId1) {
		this.chargeId1 = chargeId1;
	}

	public String getChargeNumber1() {
		return chargeNumber1;
	}

	public void setChargeNumber1(String chargeNumber1) {
		this.chargeNumber1 = chargeNumber1;
	}

	public String getChargeNumber2() {
		return chargeNumber2;
	}

	public void setChargeNumber2(String chargeNumber2) {
		this.chargeNumber2 = chargeNumber2;
	}

	public String getChargeNumber3() {
		return chargeNumber3;
	}

	public void setChargeNumber3(String chargeNumber3) {
		this.chargeNumber3 = chargeNumber3;
	}

	public String getChargeNumber4() {
		return chargeNumber4;
	}

	public void setChargeNumber4(String chargeNumber4) {
		this.chargeNumber4 = chargeNumber4;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getOriginalStatus() {
		return originalStatus;
	}


	public void setOriginalStatus(String originalStatus) {
		this.originalStatus = originalStatus;
	}
	
	public BigDecimal getChargeNo1Alias() {
		return chargeNo1Alias;
	}

	public void setChargeNo1Alias(BigDecimal chargeNo1Alias) {
		this.chargeNo1Alias = chargeNo1Alias;
	}
	
	public BigDecimal getChargeNo2Alias() {
		return chargeNo2Alias;
	}

	public void setChargeNo2Alias(BigDecimal chargeNo2Alias) {
		this.chargeNo2Alias = chargeNo2Alias;
	}
	
	public BigDecimal getChargeNo3Alias() {
		return chargeNo3Alias;
	}

	public void setChargeNo3Alias(BigDecimal chargeNo3Alias) {
		this.chargeNo3Alias = chargeNo3Alias;
	}
	
	public BigDecimal getChargeNo4Alias() {
		return chargeNo4Alias;
	}

	public void setChargeNo4Alias(BigDecimal chargeNo4Alias) {
		this.chargeNo4Alias = chargeNo4Alias;
	}
	
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
	
}