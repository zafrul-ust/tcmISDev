package radian.tcmis.internal.admin.beans;


import radian.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * Used to hold password data when changing password<br>
 * @version: 1.0, Apr 2, 2004 <br>
 *****************************************************************************/


public class PasswordBean extends BaseDataBean {

  private int personnelId;
  private String logon;
  private String passwordOld;
  private String passwordNew;
  private String passwordCopy;

  public PasswordBean() {
    super();
  }

  //setter
  public void setPersonnelId(int personnelId) {
    this.personnelId = personnelId;
  }

  public void setLogon(String logon) {
    this.logon = logon;
  }

  public void setPasswordOld(String passwordOld) {
    this.passwordOld = passwordOld;
  }

  public void setPasswordNew(String passwordNew) {
    this.passwordNew = passwordNew;
  }

  public void setPasswordCopy(String passwordCopy) {
    this.passwordCopy = passwordCopy;
  }

  //getter
  public int getPersonnelId() {
    return personnelId;
  }

  public String getLogon() {
    return logon;
  }

  public String getPasswordOld() {
    return passwordOld;
  }

  public String getPasswordNew() {
    return passwordNew;
  }

  public String getPasswordCopy() {
    return passwordCopy;
  }

}