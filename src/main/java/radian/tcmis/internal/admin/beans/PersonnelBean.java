package radian.tcmis.internal.admin.beans;

import java.util.Date;
import radian.tcmis.common.framework.BaseDataBean;
import radian.tcmis.internal.admin.beans.PermissionBean;
/******************************************************************************
 * CLASSNAME: PersonnelBean <br>
 * @version: 1.0, Apr 2, 2004 <br>
 *****************************************************************************/

public class PersonnelBean
    extends BaseDataBean {

  private int personnelId;
  private String mailLocation;
  private String facilityId;
  private String firstName;
  private String lastName;
  private String midInitial;
  private String phone;
  private String altPhone;
  private String pager;
  private String email;
  private String shippingLocation;
  private String fax;
  private String proxyName;
  private int proxyPort;
  private String logonId;
  private String password;
  private String status;
  private Date passwordExpireDate;
  private String preferredAccountSysId;
  private String companyId;
  private String client;
  private PermissionBean permissionBean;

  //constructor
  public PersonnelBean() {
    super();
  }

  //setters
  public void setPersonnelId(int personnelId) {
    this.personnelId = personnelId;
  }

  public void setMailLocation(String mailLocation) {
    this.mailLocation = mailLocation;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setMidInitial(String midInitial) {
    this.midInitial = midInitial;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setAltPhone(String altPhone) {
    this.altPhone = altPhone;
  }

  public void setPager(String pager) {
    this.pager = pager;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setShippingLocation(String shippingLocation) {
    this.shippingLocation = shippingLocation;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  public void setProxyName(String proxyName) {
    this.proxyName = proxyName;
  }

  public void setProxyPort(int proxyPort) {
    this.proxyPort = proxyPort;
  }

  public void setLogonId(String logonId) {
    this.logonId = logonId;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setPasswordExpireDate(Date passwordExpireDate) {
    this.passwordExpireDate = passwordExpireDate;
  }

  public void setPreferredAccountSysId(String preferredAccountSysId) {
    this.preferredAccountSysId = preferredAccountSysId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setClient(String client) {
    this.client = client;
  }

//USER_GROUP_MEMBER_IG
  public void setPermissionBean(PermissionBean bean) {
    this.permissionBean = bean;
  }
//USER_GROUP_MEMBER
  public void setFacPermissionBean(PermissionBean bean) {
	this.permissionBean = bean;
  }

  //getters
  public int getPersonnelId() {
    return personnelId;
  }

  public String getMailLocation() {
    return mailLocation;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getMidInitial() {
    return midInitial;
  }

  public String getPhone() {
    return phone;
  }

  public String getAltPhone() {
    return altPhone;
  }

  public String getPager() {
    return pager;
  }

  public String getEmail() {
    return email;
  }

  public String getShippingLocation() {
    return shippingLocation;
  }

  public String getFax() {
    return fax;
  }

  public String getProxyName() {
    return proxyName;
  }

  public int getProxyPort() {
    return proxyPort;
  }

  public String getLogonId() {
    return logonId;
  }

  public String getPassword() {
    return password;
  }

  public String getStatus() {
    return status;
  }

  public Date getPasswordExpireDate() {
    return passwordExpireDate;
  }

  public String getPreferredAccountSysId() {
    return preferredAccountSysId;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getClient() {
    return client;
  }

//USER_GROUP_MEMBER_IG
  public PermissionBean getPermissionBean() {
    return this.permissionBean;
  }

//USER_GROUP_MEMBER
  public void getFacPermissionBean(PermissionBean bean) {
	this.permissionBean = bean;
  }

/***************************************************************************
 * Method to check if user has permission to specified task
****************************************************************************/
  public boolean hasPermission(String userGroupId,
                               String facilityId,
                               String inventoryGroup) {

    if(permissionBean != null) {
      return permissionBean.hasPermission(userGroupId,
                                          facilityId,
                                          inventoryGroup);
    }
    else {
      return false;
    }
  }

  public boolean hasPermission(String userGroupId,
							   String facilityId) {

	if(permissionBean != null) {
	  return permissionBean.hasPermission(userGroupId,
										  facilityId);
	}
	else {
	  return false;
	}
  }
}