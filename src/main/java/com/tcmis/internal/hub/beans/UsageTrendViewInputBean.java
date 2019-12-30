package com.tcmis.internal.hub.beans;

import com.tcmis.common.framework.BaseDataBean;
import java.util.*;
/******************************************************************************
 * CLASSNAME: HubPrefBean <br>
 * @version: 1.0, Jun 9, 2004 <br>
 *****************************************************************************/

public class UsageTrendViewInputBean
 extends BaseDataBean {

 private String facilityId;
 private String submitSearch;
 private String buttonCreateExcel;
 private String userAction;
 private Date aniversaryDate;
 private String inventoryGroup;
 private String hub;
 private String companyId;
 private String unitsOrDollars;

 //constructor
 public UsageTrendViewInputBean() {
 }

 //setters
 public void setFacilityId(String facilityId) {
	this.facilityId = facilityId;
 }

 public void setSubmitSearch(String submitSearch) {
	this.submitSearch = submitSearch;
 }

 public void setButtonCreateExcel(String buttonCreateExcel) {
	this.buttonCreateExcel = buttonCreateExcel;
 }

 public void setUserAction(String userAction) {
	this.userAction = userAction;
 }

 public void setAniversaryDate(Date aniversaryDate) {
	this.aniversaryDate = aniversaryDate;
 }

 public void setInventoryGroup(String inventoryGroup) {
	this.inventoryGroup = inventoryGroup;
 }

 public void setHub(String hub) {
	this.hub = hub;
 }

 public void setCompanyId(String companyId) {
 this.companyId = companyId;
 }

 public void setUnitsOrDollars(String unitsOrDollars) {
 this.unitsOrDollars = unitsOrDollars;
 }

 //getters
 public String getfacilityId() {
	return facilityId;
 }

 public String getSubmitSearch() {
	return submitSearch;
 }

 public String getButtonCreateExcel() {
	return buttonCreateExcel;
 }

 public String getUserAction() {
	return userAction;
 }

 public Date getAniversaryDate() {
	return aniversaryDate;
 }

 public String getInventoryGroup() {
	return inventoryGroup;
 }

 public String getHub() {
 return hub;
 }

 public String getCompanyId() {
 return companyId;
 }

 public String getUnitsOrDollars() {
 return unitsOrDollars;
 }

}
