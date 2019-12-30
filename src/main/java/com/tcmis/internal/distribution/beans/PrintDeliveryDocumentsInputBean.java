package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.*;

/******************************************************************************
 * CLASSNAME: HubCabinetViewBean <br>
 * @version: 1.0, Jul 22, 2004 <br>
 *****************************************************************************/

public class PrintDeliveryDocumentsInputBean
    extends BaseDataBean {

  private String documentType;
  private String idField;
  private String packingList;
  private String certs;
  private String msds;
 
//constructor
  public PrintDeliveryDocumentsInputBean() {
  }

public String getCerts() {
	return certs;
}

public void setCerts(String certs) {
	this.certs = certs;
}

public String getDocumentType() {
	return documentType;
}

public void setDocumentType(String documentType) {
	this.documentType = documentType;
}

public String getIdField() {
	return idField;
}

public void setIdField(String idField) {
	this.idField = idField;
}

public String getMsds() {
	return msds;
}

public void setMsds(String msds) {
	this.msds = msds;
}

public String getPackingList() {
	return packingList;
}

public void setPackingList(String packingList) {
	this.packingList = packingList;
}


}