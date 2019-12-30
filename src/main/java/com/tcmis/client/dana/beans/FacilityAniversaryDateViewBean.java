package com.tcmis.client.dana.beans;

import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: FacilityAniversaryDateViewBean <br>
 * @version: 1.0, Feb 9, 2005 <br>
 *****************************************************************************/

public class FacilityAniversaryDateViewBean
 extends BaseDataBean {

 private String inventoryGroup;
 private String facilityId;
 private Date aniversaryDate;

 //constructor
 public FacilityAniversaryDateViewBean() {
 }

 //setters
 public void setInventoryGroup(String inventoryGroup) {
	this.inventoryGroup = inventoryGroup;
 }

 public void setFacilityId(String facilityId) {
	this.facilityId = facilityId;
 }

 public void setAniversaryDate(Date aniversaryDate) {
	this.aniversaryDate = aniversaryDate;
 }

 //getters
 public String getInventoryGroup() {
	return inventoryGroup;
 }

 public String getFacilityId() {
	return facilityId;
 }

 public Date getAniversaryDate() {
	return aniversaryDate;
 }
}