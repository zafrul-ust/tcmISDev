package com.tcmis.client.report.beans;

import java.math.*;
import java.util.*;

import com.tcmis.common.framework.*;

/******************************************************************************
 * CLASSNAME: ShippedPartsReportBean for pkg_report_shipped_parts.fx_shipped_parts_report
 * @version: 1.0,Aug 22, 2012 <br>
 *****************************************************************************/

public class ShippedPartsReportBean
    extends BaseDataBean {

  private String companyId;
  private String FacilityId;
  private Date dateShipped;
  private String cabinetName;
  private String catPartNo;
  private BigDecimal quantityDelivered;
  private BigDecimal unitCost;

  //constructor
  public ShippedPartsReportBean() {
  }

public String getCabinetName() {
	return cabinetName;
}

public void setCabinetName(String cabinetName) {
	this.cabinetName = cabinetName;
}

public String getCatPartNo() {
	return catPartNo;
}

public void setCatPartNo(String catPartNo) {
	this.catPartNo = catPartNo;
}

public String getCompanyId() {
	return companyId;
}

public void setCompanyId(String companyId) {
	this.companyId = companyId;
}

public Date getDateShipped() {
	return dateShipped;
}

public void setDateShipped(Date dateShipped) {
	this.dateShipped = dateShipped;
}

public String getFacilityId() {
	return FacilityId;
}

public void setFacilityId(String facilityId) {
	FacilityId = facilityId;
}

public BigDecimal getQuantityDelivered() {
	return quantityDelivered;
}

public void setQuantityDelivered(BigDecimal quantityDelivered) {
	this.quantityDelivered = quantityDelivered;
}

public BigDecimal getUnitCost() {
	return unitCost;
}

public void setUnitCost(BigDecimal unitCost) {
	this.unitCost = unitCost;
}


}