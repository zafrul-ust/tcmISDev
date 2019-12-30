package com.tcmis.client.common.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: VvPersonalProtectionBean <br>
 * @version: 1.0, July 7, 2011 <br>
 *****************************************************************************/

public class VvPersonalProtectionBean
    extends BaseDataBean {

  private String personalProtectCode;
  private String personalProtectDesc;

  //constructor
  public VvPersonalProtectionBean() {
  }

public String getPersonalProtectCode() {
	return personalProtectCode;
}

public void setPersonalProtectCode(String personalProtectCode) {
	this.personalProtectCode = personalProtectCode;
}

public String getPersonalProtectDesc() {
	return personalProtectDesc;
}

public void setPersonalProtectDesc(String personalProtectDesc) {
	this.personalProtectDesc = personalProtectDesc;
}

}