package com.tcmis.client.dana.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: UsageTrendViewBean <br>
 * @version: 1.0, Feb 1, 2005 <br>
 *****************************************************************************/

public class UsageTrendViewBean
 extends BaseDataBean {

 private String inventoryGroup;
 private BigDecimal itemId;
 private String itemDesc;
 private String packaging;
 private String manufacturer;
 private String companyId;
 private String facilityId;
 private String countUom;
 private BigDecimal baselineAnnualUsage;
 private BigDecimal baselineAnnualUsageYtd;
 private String contractStartDate;
 private String latestAniversaryDate;
 private String priorYearAniversaryDate;
 private BigDecimal month0;
 private BigDecimal month1;
 private BigDecimal month2;
 private BigDecimal month3;
 private BigDecimal month4;
 private BigDecimal month5;
 private BigDecimal month6;
 private BigDecimal month7;
 private BigDecimal month8;
 private BigDecimal month9;
 private BigDecimal month10;
 private BigDecimal month11;
 private BigDecimal priorYearUsage;
 private BigDecimal priorYearUsageYtd;
 private BigDecimal currentYearYtd;

 //constructor
 public UsageTrendViewBean() {
 }

 //setters
 public void setInventoryGroup(String inventoryGroup) {
	this.inventoryGroup = inventoryGroup;
 }

 public void setItemId(BigDecimal itemId) {
	this.itemId = itemId;
 }

 public void setItemDesc(String itemDesc) {
	this.itemDesc = itemDesc;
 }

 public void setPackaging(String packaging) {
	this.packaging = packaging;
 }

 public void setManufacturer(String manufacturer) {
	this.manufacturer = manufacturer;
 }

 public void setCompanyId(String companyId) {
	this.companyId = companyId;
 }

 public void setFacilityId(String facilityId) {
	this.facilityId = facilityId;
 }

 public void setCountUom(String countUom) {
	this.countUom = countUom;
 }

 public void setBaselineAnnualUsage(BigDecimal baselineAnnualUsage) {
	this.baselineAnnualUsage = baselineAnnualUsage;
 }

 public void setBaselineAnnualUsageYtd(BigDecimal baselineAnnualUsageYtd) {
	this.baselineAnnualUsageYtd = baselineAnnualUsageYtd;
 }

 public void setContractStartDate(String contractStartDate) {
	this.contractStartDate = contractStartDate;
 }

 public void setLatestAniversaryDate(String latestAniversaryDate) {
	this.latestAniversaryDate = latestAniversaryDate;
 }

 public void setPriorYearAniversaryDate(String priorYearAniversaryDate) {
	this.priorYearAniversaryDate = priorYearAniversaryDate;
 }

 public void setMonth0(BigDecimal month0) {
	this.month0 = month0;
 }

 public void setMonth1(BigDecimal month1) {
	this.month1 = month1;
 }

 public void setMonth2(BigDecimal month2) {
	this.month2 = month2;
 }

 public void setMonth3(BigDecimal month3) {
	this.month3 = month3;
 }

 public void setMonth4(BigDecimal month4) {
	this.month4 = month4;
 }

 public void setMonth5(BigDecimal month5) {
	this.month5 = month5;
 }

 public void setMonth6(BigDecimal month6) {
	this.month6 = month6;
 }

 public void setMonth7(BigDecimal month7) {
	this.month7 = month7;
 }

 public void setMonth8(BigDecimal month8) {
	this.month8 = month8;
 }

 public void setMonth9(BigDecimal month9) {
	this.month9 = month9;
 }

 public void setMonth10(BigDecimal month10) {
	this.month10 = month10;
 }

 public void setMonth11(BigDecimal month11) {
	this.month11 = month11;
 }

 public void setPriorYearUsage(BigDecimal priorYearUsage) {
	this.priorYearUsage = priorYearUsage;
 }

 public void setPriorYearUsageYtd(BigDecimal priorYearUsageYtd) {
	this.priorYearUsageYtd = priorYearUsageYtd;
 }

 public void setCurrentYearYtd(BigDecimal currentYearYtd) {
	this.currentYearYtd = currentYearYtd;
 }

 //getters
 public String getInventoryGroup() {
	return inventoryGroup;
 }

 public BigDecimal getItemId() {
	return itemId;
 }

 public String getItemDesc() {
	return itemDesc;
 }

 public String getPackaging() {
	return packaging;
 }

 public String getManufacturer() {
	return manufacturer;
 }

 public String getCompanyId() {
	return companyId;
 }

 public String getFacilityId() {
	return facilityId;
 }

 public String getCountUom() {
	return countUom;
 }

 public BigDecimal getBaselineAnnualUsage() {
	return baselineAnnualUsage;
 }

 public BigDecimal getBaselineAnnualUsageYtd() {
	return baselineAnnualUsageYtd;
 }

 public String getContractStartDate() {
	return contractStartDate;
 }

 public String getLatestAniversaryDate() {
	return latestAniversaryDate;
 }

 public String getPriorYearAniversaryDate() {
	return priorYearAniversaryDate;
 }

 public BigDecimal getMonth0() {
	return month0;
 }

 public BigDecimal getMonth1() {
	return month1;
 }

 public BigDecimal getMonth2() {
	return month2;
 }

 public BigDecimal getMonth3() {
	return month3;
 }

 public BigDecimal getMonth4() {
	return month4;
 }

 public BigDecimal getMonth5() {
	return month5;
 }

 public BigDecimal getMonth6() {
	return month6;
 }

 public BigDecimal getMonth7() {
	return month7;
 }

 public BigDecimal getMonth8() {
	return month8;
 }

 public BigDecimal getMonth9() {
	return month9;
 }

 public BigDecimal getMonth10() {
	return month10;
 }

 public BigDecimal getMonth11() {
	return month11;
 }

 public BigDecimal getPriorYearUsage() {
	return priorYearUsage;
 }

 public BigDecimal getPriorYearUsageYtd() {
	return priorYearUsageYtd;
 }

 public BigDecimal getCurrentYearYtd() {
	return currentYearYtd;
 }
}