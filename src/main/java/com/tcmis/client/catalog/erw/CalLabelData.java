package com.tcmis.client.catalog.erw;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.client.catalog.beans.ClientLabelBean;
import com.tcmis.common.util.StringHandler;

public class CalLabelData {
 String CATPARTNO = "";
 String PARTDESCRIPTION = "";
 String CUSTOMERPO = "";
 String SHELFLIFE = "";
 String EXPIRATIONDATE = "";
 String RECERTEXPDATE = "";
 String EMPLOYEENUM = "";

 public CalLabelData(ClientLabelBean clientLabelBean) {
	CATPARTNO = StringHandler.emptyIfNull(clientLabelBean.getCatPartNo());
	PARTDESCRIPTION = StringHandler.emptyIfNull(clientLabelBean.
	 getPartDescription());
	CUSTOMERPO = StringHandler.emptyIfNull(clientLabelBean.getCustomerPo());
	SHELFLIFE = StringHandler.emptyIfNull(clientLabelBean.getShelfLife());
	EXPIRATIONDATE = StringHandler.emptyIfNull(clientLabelBean.getExpirationDate());
	EMPLOYEENUM = StringHandler.emptyIfNull(clientLabelBean.getEmployeeNum());
	RECERTEXPDATE = StringHandler.emptyIfNull(clientLabelBean.getRecertExpDate());
 }

 public String getCatPartNo() {
	return CATPARTNO;
 }

 public String getPartDescription() {
	return PARTDESCRIPTION;
 }

 public String getCustomerPo() {
	return CUSTOMERPO;
 }

 public String getShelfLife() {
	return SHELFLIFE;
 }

 public String getExpirationDate() {
	return EXPIRATIONDATE;
 }

 public String getEmployeeNum() {
	return EMPLOYEENUM;
 }

 public String getRecertExpDate() {
	return RECERTEXPDATE;
 }

 public static Vector getFieldVector() {
	Vector v = new Vector(17);
	v.addElement("CATPARTNO = getCatPartNo");
	v.addElement("PARTDESCRIPTION = getPartDescription");
	v.addElement("CUSTOMERPO = getCustomerPo");
	v.addElement("SHELFLIFE = getShelfLife");
	v.addElement("EXPIRATIONDATE = getExpirationDate");
	v.addElement("EMPLOYEENUM = getEmployeeNum");
	v.addElement("RECERTEXPDATE = getRecertExpDate");

	return v;
 }

 public static Vector getVector(Collection in) {
	Vector out = new Vector();
	Iterator iterator = in.iterator();
	while (iterator.hasNext()) {
	 ClientLabelBean clientLabelBean = (ClientLabelBean) iterator.next();
	 String labelQuantity = clientLabelBean.getLabelQty();
	 int labelQty = Integer.parseInt(labelQuantity);
	 for (int loop = 0; loop < labelQty; loop++) {
		out.addElement(new CalLabelData(clientLabelBean));
	 }
	}
	return out;
 }
}
