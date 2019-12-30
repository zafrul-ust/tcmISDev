package com.tcmis.client.common.beans;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: DeptBean <br>
 * @version: 1.0, Nov 29, 2011 <br>
 *****************************************************************************/

public class DeptBean extends BaseDataBean {

	private String companyId;
	private String deptId;
	private String deptName;

	//constructor
	public DeptBean() {
	}

	//getters
	public String getCompanyId() {
		return companyId;
	}

	public String getDeptId() {
		return deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public boolean isNewDepartment() {
		return StringHandler.isBlankString(deptId);
	}

	public boolean hasDeptName() {
		return !StringHandler.isBlankString(deptName);
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}