package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.*;

/******************************************************************************
 * CLASSNAME: HubCabinetViewBean <br>
 * @version: 1.0, Jul 22, 2004 <br>
 *****************************************************************************/

public class PriceListInputBean
    extends BaseDataBean {

  private String searchField;
  private String searchMode;
  private String searchArgument;
  private String opsEntityId;
  private String companyId;
  private String priceGroupId;
  private String showExpired;
  private Date expireDate;
  
public String getSearchField() {
	return searchField;
}

public void setSearchField(String searchField) {
	this.searchField = searchField;
}

public String getSearchMode() {
	return searchMode;
}

public void setSearchMode(String searchMode) {
	this.searchMode = searchMode;
}

public String getSearchArgument() {
	return searchArgument;
}

public void setSearchArgument(String searchArgument) {
	this.searchArgument = searchArgument;
}

public String getOpsEntityId() {
	return opsEntityId;
}

public void setOpsEntityId(String opsEntityId) {
	this.opsEntityId = opsEntityId;
}

public String getCompanyId() {
	return companyId;
}

public void setCompanyId(String companyId) {
	this.companyId = companyId;
}

public String getPriceGroupId() {
	return priceGroupId;
}

public void setPriceGroupId(String priceGroupId) {
	this.priceGroupId = priceGroupId;
}

public String getShowExpired() {
	return showExpired;
}

public void setShowExpired(String showExpired) {
	this.showExpired = showExpired;
}

public Date getExpireDate() {
	return expireDate;
}

public void setExpireDate(Date expireDate) {
	this.expireDate = expireDate;
}


//constructor
  public PriceListInputBean() {
  }


}