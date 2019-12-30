package com.tcmis.supplier.shipping.erw;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.NumberHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.supplier.shipping.beans.SupplierPackingViewBean;

/**
 * ***************************************************************************
 * CLASSNAME: PackConfirmData <br>
 *
 * @version: 1.0, Nov 26, 2007 <br>
 * ***************************************************************************
 */

public class PackConfirmData {

private String shipToLocationName;
private String ultimateShipToDodaac;
private String mrLine;
private String haasPoLine;
private String partNo;
private String partShortName;
private String packaging;
private String quantity;
private String boxLabelId;
private String boxId;
private String palletId;
private String dateShipped;
private String dateDelivered;
private String deliveryTicket;
private String trackingNumber;
private String carrierName;
private String supplierSalesOrderNo;

public PackConfirmData(SupplierPackingViewBean bean) {
	shipToLocationName = StringHandler.emptyIfNull(bean.getShipToCityCommaState())+" "+StringHandler.emptyIfNull(bean.getShipToZipcode());
	ultimateShipToDodaac = StringHandler.emptyIfNull(bean.getUltimateShipToDodaac());
	mrLine = StringHandler.emptyIfNull(bean.getPrNumber())+"-"+StringHandler.emptyIfNull(bean.getLineItem());
	haasPoLine = StringHandler.emptyIfNull(bean.getRadianPo())+"-"+StringHandler.emptyIfNull(bean.getPoLine());
	partNo = StringHandler.emptyIfNull(bean.getCatPartNo());
	partShortName = StringHandler.emptyIfNull(bean.getPartShortName());
	packaging = StringHandler.emptyIfNull(bean.getPackaging());
	quantity = NumberHandler.emptyIfNull(bean.getQuantity());
	boxLabelId = StringHandler.emptyIfNull(bean.getBoxLabelId());
	boxId = StringHandler.emptyIfNull(bean.getBoxId());
	palletId = StringHandler.emptyIfNull(bean.getPalletId());
	dateShipped = StringHandler.emptyIfNull(bean.getDateShipped());
	dateDelivered = StringHandler.emptyIfNull(bean.getDateDelivered());
	deliveryTicket = StringHandler.emptyIfNull(bean.getDeliveryTicket());
	trackingNumber = StringHandler.emptyIfNull(bean.getTrackingNumber());
	carrierName = StringHandler.emptyIfNull(bean.getCarrierName());
  supplierSalesOrderNo = StringHandler.emptyIfNull(bean.getSupplierSalesOrderNo());
}

public static Vector getFieldVector() {
	Vector v = new Vector(17);
	v.addElement("shipToLocationName=getShipToLocationName");
	v.addElement("ultimateShipToDodaac=getUltimateShipToDodaac");
	v.addElement("mrLine=getMrLine");
	v.addElement("haasPoLine=getHaasPoLine");
	v.addElement("partNo=getPartNo");
	v.addElement("partShortName=getPartShortName");
	v.addElement("packaging=getPackaging");
	v.addElement("quantity=getQuantity");
	v.addElement("boxLabelId=getBoxLabelId");
	v.addElement("boxId=getBoxId");
	v.addElement("palletId=getPalletId");
	v.addElement("dateShipped=getDateShipped");
	v.addElement("dateDelivered=getDateDelivered");
	v.addElement("deliveryTicket=getDeliveryTicket");
	v.addElement("trackingNumber=getTrackingNumber");
	v.addElement("carrierName=getCarrierName");
  v.addElement("supplierSalesOrderNo=getSupplierSalesOrderNo");
  return v;
}

public static Vector getVector(Collection in) {
	Vector out = new Vector();
	Iterator iterator = in.iterator();
	while (iterator.hasNext()) {
		out.addElement(new PackConfirmData((SupplierPackingViewBean) iterator.next()));
	}
	return out;
}

//getters
public String getShipToLocationName() {
	return shipToLocationName;
}

public String getUltimateShipToDodaac() {
	return ultimateShipToDodaac;
}

public String getMrLine() {
	return mrLine;
}

public String getHaasPoLine() {
	return haasPoLine;
}

public String getPartNo() {
	return partNo;
}

public String getPartShortName() {
	return partShortName;
}

public String getPackaging() {
	return packaging;
}

public String getQuantity() {
	return quantity;
}

public String getBoxLabelId() {
	return boxLabelId;
}

public String getBoxId() {
	return boxId;
}

public String getPalletId() {
	return palletId;
}

public String getDateShipped() {
	return dateShipped;
}

public String getDateDelivered() {
	return dateDelivered;
}

public String getDeliveryTicket() {
	return deliveryTicket;
}

public String getTrackingNumber() {
	return trackingNumber;
}

public String getCarrierName() {
	return carrierName;
}

public String getSupplierSalesOrderNo() {
  return supplierSalesOrderNo;
}
}