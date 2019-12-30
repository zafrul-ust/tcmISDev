package com.tcmis.internal.supply.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: OperatingEntityBean <br>
 * @version: 1.0, Jul 16, 2009 <br>
 *****************************************************************************/


public class OperatingEntityBean extends BaseDataBean {

	private String opsEntityId;
	private String homeCurrencyId;
	private String opsCompanyId;
	private String operatingEntityName;
	private String poImageUrl;
	private String billToCompanyId;
	private String billToLocationId;
	private String operatingEntityShortName;
	private String remitToCompanyId;
	private String remitToLocationId;
	private String poTermsAndConditions;
	private String logoImageUrl;
	private String weekend1;
	private String weekend2;
	private BigDecimal apContactPersonnelId;
	private String eHomeCompanyId;
	private String sapCompanyCode;
	private String remitToBankCompanyId;
	private String remitToBankLocationId;
	private String remitToBankAccountNumber;
	private String remitToBankRoutingNumber;
	private String taxRegistrationType;
	private String taxRegistrationNumber;
	private String taxRegistrationType2;
	private String taxRegistrationNumber2;
	private String taxRegistrationType3;
	private String taxRegistrationNumber3;
	private String taxRegistrationType4;
	private String taxRegistrationNumber4;
	private String supplierCode;
	private String remittanceInstructionLine1;
	private String remittanceInstructionLine2;
	private String remittanceInstructionLine3;
	private String remittanceInstructionLine4;
	private String remittanceInstructionLine5;
	private String remittanceInstructionLine6;


	//constructor
	public OperatingEntityBean() {
	}

	//setters
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setHomeCurrencyId(String homeCurrencyId) {
		this.homeCurrencyId = homeCurrencyId;
	}
	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
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
	public void setOperatingEntityShortName(String operatingEntityShortName) {
		this.operatingEntityShortName = operatingEntityShortName;
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
	public void setLogoImageUrl(String logoImageUrl) {
		this.logoImageUrl = logoImageUrl;
	}
	public void setWeekend1(String weekend1) {
		this.weekend1 = weekend1;
	}
	public void setWeekend2(String weekend2) {
		this.weekend2 = weekend2;
	}
	public void setApContactPersonnelId(BigDecimal apContactPersonnelId) {
		this.apContactPersonnelId = apContactPersonnelId;
	}
	public void setEHomeCompanyId(String eHomeCompanyId) {
		this.eHomeCompanyId = eHomeCompanyId;
	}
	public void setSapCompanyCode(String sapCompanyCode) {
		this.sapCompanyCode = sapCompanyCode;
	}
	public void setRemitToBankCompanyId(String remitToBankCompanyId) {
		this.remitToBankCompanyId = remitToBankCompanyId;
	}
	public void setRemitToBankLocationId(String remitToBankLocationId) {
		this.remitToBankLocationId = remitToBankLocationId;
	}
	public void setRemitToBankAccountNumber(String remitToBankAccountNumber) {
		this.remitToBankAccountNumber = remitToBankAccountNumber;
	}
	public void setRemitToBankRoutingNumber(String remitToBankRoutingNumber) {
		this.remitToBankRoutingNumber = remitToBankRoutingNumber;
	}
	public void setTaxRegistrationType(String taxRegistrationType) {
		this.taxRegistrationType = taxRegistrationType;
	}
	public void setTaxRegistrationNumber(String taxRegistrationNumber) {
		this.taxRegistrationNumber = taxRegistrationNumber;
	}
	public void setTaxRegistrationType2(String taxRegistrationType2) {
		this.taxRegistrationType2 = taxRegistrationType2;
	}
	public void setTaxRegistrationNumber2(String taxRegistrationNumber2) {
		this.taxRegistrationNumber2 = taxRegistrationNumber2;
	}
	public void setTaxRegistrationType3(String taxRegistrationType3) {
		this.taxRegistrationType3 = taxRegistrationType3;
	}
	public void setTaxRegistrationNumber3(String taxRegistrationNumber3) {
		this.taxRegistrationNumber3 = taxRegistrationNumber3;
	}
	public void setTaxRegistrationType4(String taxRegistrationType4) {
		this.taxRegistrationType4 = taxRegistrationType4;
	}
	public void setTaxRegistrationNumber4(String taxRegistrationNumber4) {
		this.taxRegistrationNumber4 = taxRegistrationNumber4;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public void setRemittanceInstructionLine1(String remittanceInstructionLine1) {
		this.remittanceInstructionLine1 = remittanceInstructionLine1;
	}
	public void setRemittanceInstructionLine2(String remittanceInstructionLine2) {
		this.remittanceInstructionLine2 = remittanceInstructionLine2;
	}
	public void setRemittanceInstructionLine3(String remittanceInstructionLine3) {
		this.remittanceInstructionLine3 = remittanceInstructionLine3;
	}
	public void setRemittanceInstructionLine4(String remittanceInstructionLine4) {
		this.remittanceInstructionLine4 = remittanceInstructionLine4;
	}
	public void setRemittanceInstructionLine5(String remittanceInstructionLine5) {
		this.remittanceInstructionLine5 = remittanceInstructionLine5;
	}
	public void setRemittanceInstructionLine6(String remittanceInstructionLine6) {
		this.remittanceInstructionLine6 = remittanceInstructionLine6;
	}


	//getters
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getHomeCurrencyId() {
		return homeCurrencyId;
	}
	public String getOpsCompanyId() {
		return opsCompanyId;
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
	public String getOperatingEntityShortName() {
		return operatingEntityShortName;
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
	public String getLogoImageUrl() {
		return logoImageUrl;
	}
	public String getWeekend1() {
		return weekend1;
	}
	public String getWeekend2() {
		return weekend2;
	}
	public BigDecimal getApContactPersonnelId() {
		return apContactPersonnelId;
	}
	public String getEHomeCompanyId() {
		return eHomeCompanyId;
	}
	public String getSapCompanyCode() {
		return sapCompanyCode;
	}
	public String getRemitToBankCompanyId() {
		return remitToBankCompanyId;
	}
	public String getRemitToBankLocationId() {
		return remitToBankLocationId;
	}
	public String getRemitToBankAccountNumber() {
		return remitToBankAccountNumber;
	}
	public String getRemitToBankRoutingNumber() {
		return remitToBankRoutingNumber;
	}
	public String getTaxRegistrationType() {
		return taxRegistrationType;
	}
	public String getTaxRegistrationNumber() {
		return taxRegistrationNumber;
	}
	public String getTaxRegistrationType2() {
		return taxRegistrationType2;
	}
	public String getTaxRegistrationNumber2() {
		return taxRegistrationNumber2;
	}
	public String getTaxRegistrationType3() {
		return taxRegistrationType3;
	}
	public String getTaxRegistrationNumber3() {
		return taxRegistrationNumber3;
	}
	public String getTaxRegistrationType4() {
		return taxRegistrationType4;
	}
	public String getTaxRegistrationNumber4() {
		return taxRegistrationNumber4;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public String getRemittanceInstructionLine1() {
		return remittanceInstructionLine1;
	}
	public String getRemittanceInstructionLine2() {
		return remittanceInstructionLine2;
	}
	public String getRemittanceInstructionLine3() {
		return remittanceInstructionLine3;
	}
	public String getRemittanceInstructionLine4() {
		return remittanceInstructionLine4;
	}
	public String getRemittanceInstructionLine5() {
		return remittanceInstructionLine5;
	}
	public String getRemittanceInstructionLine6() {
		return remittanceInstructionLine6;
	}

}