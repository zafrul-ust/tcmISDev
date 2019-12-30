package com.tcmis.client.common.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: facilityInventoryGroupBean <br>
 * @version: 1.0, Aug 20, 2008 <br>
 *****************************************************************************/


public class FacilityInventoryGroupBean extends BaseDataBean {

	private String companyId;
	private String facilityId;
	private String inventoryGroup;
	private String status;

	//constructor
	public FacilityInventoryGroupBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setStatus(String status) {
		this.status = status;
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getStatus() {
		return status;
	}

}